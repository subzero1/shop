package com.netsky.shop.controller;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.net.TelnetInputStream;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.util.ConvertUtil;
import com.netsky.shop.util.SlaveObject;

@Controller
public class DownloadController {
	@Autowired
	private BaseDao dao;
	private static String ftpConfigFile = "/config/ftpConfig.xml";

	// 下载文件
	@RequestMapping("/download.do")
	public void downloadRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		FTPClient client = new FTPClient();
		try {
			Map<String, String> ftpConfig = this.getFtpConfig(request);
			client.connect(ftpConfig.get("address"));
			client.login(ftpConfig.get("username"), ftpConfig.get("password"));
			client.setFileType(FTP.BINARY_FILE_TYPE);
			client.enterLocalActiveMode();
			String classname = ConvertUtil.toString(request.getParameter("classname"));
			Integer id = ConvertUtil.toInteger(request.getParameter("id"));
			Object o = Class.forName(classname).newInstance();
			o = dao.searchById(o.getClass(), id);
			if (o instanceof SlaveObject) {
				SlaveObject so = (SlaveObject) o;
				String filepath = ConvertUtil.toString(so.getFilePath());
				String filename = ConvertUtil.toString(so.getFileName());
				if (filename == null || filename.equals("")) {
					filename = "未命名";
				}
					InputStream is;
					is = client.retrieveFileStream(filepath);
					if (is == null){
						if (filepath.equals("")){
							filepath = so.getDefaultpkg();
						}
						filepath = filepath.substring(0,filepath.lastIndexOf("/")+1)+ so.getId()+so.getExtName();
						is = client.retrieveFileStream(filepath);
						if (is == null){
							filepath = filepath.substring(0,filepath.lastIndexOf("/")+1)+ so.getDefaultsrc();
							is = client.retrieveFileStream(filepath);
						}
					}
				response.reset();
				response.setHeader("Content-Disposition", "attachment;filename="
						+ URLEncoder.encode(filename, "UTF-8"));
				byte[] bytes = new byte[1024];
				int readBye;
				while ((readBye = is.read(bytes)) != -1) {
					response.getOutputStream().write(bytes, 0, readBye);
				}
				is.close();
				response.getOutputStream().close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client.isConnected()){
				client.disconnect();
			}
		}

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
}
