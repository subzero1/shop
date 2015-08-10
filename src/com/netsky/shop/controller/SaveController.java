package com.netsky.shop.controller;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.SlaveObject;

@Controller
public class SaveController {

	@Autowired
	private BaseDao dao;

	private static String ftpConfigFile = "/config/ftpConfig.xml";

	@RequestMapping("/save.do")
	public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String tableName = ConvertUtil.toString(request.getParameter("tableName"));
		if (!tableName.equals("")) {
			Object o = Class.forName(tableName).newInstance();
			int num = request.getParameterValues(tableName.split("\\.")[tableName.split("\\.").length - 1] + ".id").length;
			Method[] methods = o.getClass().getDeclaredMethods();
			Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			try {

				Object[] objects = new Object[num];
				for (int i = 0; i < num; i++) {
					o = dao.searchById(o.getClass(), ConvertUtil.toInteger(request
							.getParameterValues(tableName.split("\\.")[tableName.split("\\.").length - 1] + ".id")[i]));
					if (o == null){
						o = Class.forName(tableName).newInstance();
					}
					for (Method method : methods) {
						if (method.getName().toLowerCase().startsWith("set")) {
							Class clazz = method.getParameterTypes()[0];
							int j = i;
							String s = "";
							// 多对象保存时 后边的对象的某些字段如果没有 则用前边的代替
							boolean continueflag = false;
							while (j >= 0) {
								if (method.getName().replace("set", "").toLowerCase().equals("id")){
									continueflag = true;
									break;
								}
								try {
									s = request
											.getParameterValues(tableName.split("\\.")[tableName.split("\\.").length - 1]
													+ "." + method.getName().replace("set", "").toLowerCase())[j];
									break;
								} catch (ArrayIndexOutOfBoundsException e) {
									// TODO Auto-generated catch block
									j--;
								} catch (NullPointerException e) {
									continueflag = true;
									break;
								}
							}
							if (s == null || s.equals("") && continueflag) {
								continue;
							} else {
								if (clazz.getName().equals("java.lang.Integer")) {
									method.invoke(o, Integer.valueOf(s));
								} else if (clazz.getName().equals("java.lang.Long")) {
									method.invoke(o, Long.valueOf(s));
								} else if (clazz.getName().equals("java.lang.Float")) {
									method.invoke(o, Float.valueOf(s));
								} else if (clazz.getName().equals("java.lang.Double")) {
									method.invoke(o, Double.valueOf(s));
								} else if (clazz.getName().equals("java.lang.Short")) {
									method.invoke(o, Short.valueOf(s));
								} else if (clazz.getName().equals("java.lang.String")) {
									method.invoke(o, s);
								} else if (clazz.getName().equals("java.util.Date")) {
									SimpleDateFormat sdf = new SimpleDateFormat();
									Date date = null;
									try {
										sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
										date = sdf.parse(s);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										try {
											sdf.applyPattern("yyyy-MM-dd");
											date = sdf.parse(s);
										} catch (ParseException e1) {
											// TODO Auto-generated catch block
											System.out.println(s);
										}
									}
									method.invoke(o, date);
								}
							}
						}
					}
					session.saveOrUpdate(o);
					objects[i] = o;
				}
				if (request.getContentType().toLowerCase().indexOf("multipart/form-data") != -1) {
					MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
					Iterator<String> it = mRequest.getFileNames();
					int slavei = 0;
					while (it.hasNext()) {
						String fileName = it.next();
						MultipartFile file = mRequest.getFile(fileName);
						if (file.getName() != null && !file.getName().equals("")
								&& file.getInputStream().available() > 0) {
							SlaveObject so = (SlaveObject) objects[slavei++];
							fileName = so.getId().toString();
							String extends_name = "";
							if (!"".equals(so.getExtName())){
								fileName += so.getExtName();
							} else if (file.getOriginalFilename().indexOf(".") != -1) {
								extends_name = file.getOriginalFilename().substring(
										file.getOriginalFilename().lastIndexOf("."));
								if (extends_name.length() == extends_name.getBytes().length)
									fileName += extends_name;
							}
							String slaveType = so.getSlaveType();
							Map<String, String> ftpConfig = this.getFtpConfig(request);
							String folder = ConvertUtil.toString(this.getSlaveFolder(request).get(slaveType), ftpConfig
									.get("folder"));
							FTPClient ftp = new FTPClient();
							ftp.connect(ftpConfig.get("address"));
							ftp.login(ftpConfig.get("username"), ftpConfig.get("password"));
							ftp.enterLocalActiveMode();
							ftp.setFileType(FTP.BINARY_FILE_TYPE);
							// ftp.setFileTransferMode(FTP.BLOCK_TRANSFER_MODE);
							String folders[] = folder.split("/");
							for (int j = 0; j < folders.length; j++) {
								if (!ftp.changeWorkingDirectory(folders[j])) {
									if (!ftp.makeDirectory(folders[j])) {
										throw new Exception("创建目录失败");
									}
									ftp.changeWorkingDirectory(folders[j]);
								}
							}
							ftp.storeFile(fileName, file.getInputStream());
							file.getInputStream().close();
							ftp.disconnect();
							so.setFileName(folder + "/" + fileName);
							session.saveOrUpdate(so);
						}
					}
				}
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				transaction.rollback();
			} finally {
				session.close();
			}
		}
		String forwardurl = ConvertUtil.toString(request.getParameter("forwardurl"));
		return new ModelAndView(forwardurl);
	}

	private Map<String, String> getFtpConfig(HttpServletRequest request) throws Exception {

		Map<String, String> ftpConfig = new HashMap<String, String>();
		/**
		 * 通过xml文件获取配置信息
		 */
		String filePath = request.getSession().getServletContext().getRealPath("WEB-INF") + ftpConfigFile;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("配置文件不存在，路径：" + filePath);
		}
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			ftpConfig.put("folder", root.elementText("folder"));
			ftpConfig.put("address", root.elementText("address"));
			ftpConfig.put("username", root.elementText("username"));
			ftpConfig.put("password", root.elementText("password"));
		} catch (DocumentException ex) {
			throw new Exception("错误的xml格式，路径：" + filePath + "错误:" + ex);
		}
		return ftpConfig;
	}

	private Map<String, String> getSlaveFolder(HttpServletRequest request) throws Exception {

		Map<String, String> slaveFolder = new HashMap<String, String>();
		/**
		 * 通过xml文件获取配置信息
		 */
		String filePath = request.getSession().getServletContext().getRealPath("WEB-INF") + ftpConfigFile;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("配置文件不存在，路径：" + filePath);
		}
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(file);
			Element root = doc.getRootElement();
			for (Iterator<Element> j = root.elementIterator("typeFolder"); j != null && j.hasNext();) {
				Element element = (Element) j.next();
				slaveFolder.put(element.elementText("type"), element.elementText("folder"));
			}
		} catch (DocumentException ex) {
			throw new Exception("错误的xml格式，路径：" + filePath + "错误:" + ex);
		}
		return slaveFolder;
	}

}
