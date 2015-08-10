package com.netsky.shop.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.netsky.shop.dao.BaseDao;
import com.netsky.shop.domain.base.Tbl01_shangpin;
import com.netsky.shop.domain.base.Tbl02_jinhuo;
import com.netsky.shop.domain.base.Tbl03_huiyuan;
import com.netsky.shop.domain.base.Tbl04_xiaoshou;
import com.netsky.shop.domain.base.Tbl05_leixing;
import com.netsky.shop.domain.base.Tbl06_chengben;
import com.netsky.shop.domain.base.Tbl09_duihuanshangpin;
import com.netsky.shop.domain.base.Tbl10_yonghu;
import com.netsky.shop.domain.base.Tbl13_duihuan;
import com.netsky.shop.util.ConvertUtil;

@Controller
public class OriginalDataController {

	@Autowired
	private BaseDao dao;
	// private final static String DB_URL =
	// "jdbc:mysql://121.199.61.224:3306/zp50x1_db?useUnicode=true&characterEncoding=UTF-8";
	private final static String DB_URL = "jdbc:mysql://192.168.0.1:3306/shop?useUnicode=true&characterEncoding=GBK";
	/** MySql数据库连接驱动 */
	private final static String DB_DRIVER = "com.mysql.jdbc.Driver";
	/** 数据库用户名 */
	// private final static String DB_USERNAME = "zp50x1";
	private final static String DB_USERNAME = "root";
	/** 数据库密码 */
	// private final static String DB_PASSWORD = "asefx1kl";
	private final static String DB_PASSWORD = "netsky";

	@RequestMapping("/getOriginalData.do")
	public void getOriginalData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;
		// 获得用户列表
//		sql = "select id,m_password from managers";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl10_yonghu> yonghuList = new ArrayList<Tbl10_yonghu>();
//		while (rs.next()) {
//			Tbl10_yonghu yonghu = new Tbl10_yonghu();
//			yonghu.setUsername(rs.getString("id"));
//			yonghu.setPassword(rs.getString("m_password"));
//			yonghuList.add(yonghu);
//		}
//		if (yonghuList.size() != 0) {
//			dao.update("delete from Tbl10_yonghu");
//			for (Tbl10_yonghu tbl10_yonghu : yonghuList) {
//				dao.save(tbl10_yonghu);
//			}
//		}
//		// 获得商品类型列表
//		sql = "select distinct(g_type) as type from goods";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl05_leixing> leixingList = new ArrayList<Tbl05_leixing>();
//		while (rs.next()) {
//			Tbl05_leixing leixing = new Tbl05_leixing();
//			leixing.setName(rs.getString("type"));
//			leixingList.add(leixing);
//		}
//		if (leixingList.size() != 0) {
//			dao.update("delete from Tbl05_leixing");
//			for (Tbl05_leixing tbl05_leixing : leixingList) {
//				dao.save(tbl05_leixing);
//			}
//		}
//		// 获得会员卡
//		sql = "select number,currentbonus as dqjf,totalbonus as ljjf,registdate as zcrq,activedate as jhrq,status from membershipcard";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl03_huiyuan> huiyuaList = new ArrayList<Tbl03_huiyuan>();
//		while (rs.next()) {
//			Tbl03_huiyuan huiyuan = new Tbl03_huiyuan();
//			huiyuan.setNumber(rs.getString("number"));
//			huiyuan.setDqjf(rs.getDouble("dqjf"));
//			huiyuan.setLjjf(rs.getDouble("ljjf"));
//			huiyuan.setZcrq(rs.getTimestamp("zcrq"));
//			huiyuan.setJhrq(rs.getTimestamp("jhrq"));
//			huiyuan.setStatus(rs.getInt("status") == 0 ? "未激活" : "已激活");
//			huiyuaList.add(huiyuan);
//		}
//		if (huiyuaList.size() != 0) {
//			dao.update("delete from Tbl03_huiyuan");
//			for (Tbl03_huiyuan tbl03_huiyuan : huiyuaList) {
//				dao.save(tbl03_huiyuan);
//			}
//		}
//		// 获得成本表
//		sql = "select date,title,money from kuaiji where id>4";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl06_chengben> chengbenList = new ArrayList<Tbl06_chengben>();
//		while (rs.next()) {
//			Tbl06_chengben chengben = new Tbl06_chengben();
//			chengben.setDate(rs.getTimestamp("date"));
//			chengben.setMoney(rs.getDouble("money"));
//			chengben.setName(rs.getString("title"));
//			chengbenList.add(chengben);
//		}
//		if (chengbenList.size() != 0) {
//			dao.update("delete from Tbl06_chengben");
//			for (Tbl06_chengben tbl06_chengben : chengbenList) {
//				dao.save(tbl06_chengben);
//			}
//		}
//		// 获得商品表
//		sql = "select * from goods";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl01_shangpin> shangpinList = new ArrayList<Tbl01_shangpin>();
//		leixingList = (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing");
//		Map<String, Integer> leixingMap = new HashMap<String, Integer>();
//		for (Tbl05_leixing tbl05_leixing : leixingList) {
//			leixingMap.put(tbl05_leixing.getName(), tbl05_leixing.getId());
//		}
//		while (rs.next()) {
//			Tbl01_shangpin shangpin = new Tbl01_shangpin();
//			shangpin.setKcsl(rs.getInt("g_num"));
//			shangpin.setName(rs.getString("g_name"));
//			shangpin.setNumber(rs.getString("id"));
//			shangpin.setPrice(rs.getDouble("g_sale_price"));
//			shangpin.setYuzhi(rs.getInt("yuzhi"));
//			shangpin.setTbl05_id(leixingMap.get(rs.getString("g_type")));
//			shangpin.setChengben(rs.getDouble("g_jhjg"));
//			shangpinList.add(shangpin);
//		}
//		if (shangpinList.size() != 0) {
//			dao.update("delete from Tbl01_shangpin");
//			for (Tbl01_shangpin tbl01_shangpin : shangpinList) {
//				dao.save(tbl01_shangpin);
//			}
//		}
//		// 获得销售表
//		sql = "select * from account";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl04_xiaoshou> xiaoshouList = new ArrayList<Tbl04_xiaoshou>();
//		List<Tbl03_huiyuan> huiyuanList = (List<Tbl03_huiyuan>) dao.searchList("from Tbl03_huiyuan");
//		Map<String, Integer> huiyuanMap = new HashMap<String, Integer>();
//		for (Tbl03_huiyuan tbl03_huiyuan : huiyuanList) {
//			huiyuanMap.put(tbl03_huiyuan.getNumber(), tbl03_huiyuan.getId());
//		}
//		yonghuList = (List<Tbl10_yonghu>) dao.searchList("from Tbl10_yonghu");
//		Map<String, Integer> yonghuMap = new HashMap<String, Integer>();
//		for (Tbl10_yonghu tbl10_yonghu : yonghuList) {
//			yonghuMap.put(tbl10_yonghu.getUsername(), tbl10_yonghu.getId());
//		}
//		shangpinList = (List<Tbl01_shangpin>) dao.searchList("from Tbl01_shangpin");
//		Map<String, Integer> shangpinMap = new HashMap<String, Integer>();
//		for (Tbl01_shangpin tbl01_shangpin : shangpinList) {
//			shangpinMap.put(tbl01_shangpin.getNumber(), tbl01_shangpin.getId());
//		}
//		leixingList = (List<Tbl05_leixing>) dao.searchList("from Tbl05_leixing");
//		leixingMap = new HashMap<String, Integer>();
//		for (Tbl05_leixing tbl05_leixing : leixingList) {
//			leixingMap.put(tbl05_leixing.getName(), tbl05_leixing.getId());
//		}
//		while (rs.next()) {
//			Tbl04_xiaoshou xiaoshou = new Tbl04_xiaoshou();
//			xiaoshou.setChengben(rs.getDouble("a_jhjg"));
//			xiaoshou.setGmsl(rs.getInt("a_num"));
//			xiaoshou.setPrice(rs.getDouble("a_out_price"));
//			xiaoshou.setTime(rs.getTimestamp("a_out_time"));
//			xiaoshou.setTbl03_id(huiyuanMap.get(rs.getString("cardnumber")));
//			if (shangpinMap.get(rs.getString("a_id")) == null) {
//				Tbl01_shangpin shangpin = new Tbl01_shangpin();
//				shangpin.setName(rs.getString("a_name"));
//				shangpin.setNumber(rs.getString("a_id"));
//				shangpin.setKcsl(0);
//				shangpin.setPrice(rs.getDouble("a_out_price"));
//				shangpin.setTbl05_id(leixingMap.get(rs.getString("a_type")));
//				shangpin.setYuzhi(5);
//				shangpin.setChengben(ConvertUtil.toDouble(rs.getDouble("a_jhjg")));
//				try {
//					dao.save(shangpin);
//				} catch (RuntimeException e) {
//					// TODO Auto-generated catch block
//					continue;
//				}
//				xiaoshou.setTbl01_id(shangpin.getId());
//				shangpinMap.put(shangpin.getNumber(), shangpin.getId());
//			} else {
//				xiaoshou.setTbl01_id(shangpinMap.get(rs.getString("a_id")));
//			}
//			xiaoshou.setTbl10_id(ConvertUtil.toInteger(yonghuMap.get(rs.getString("a_manager")), 8));
//			xiaoshou.setChengben(ConvertUtil.toDouble(rs.getDouble("a_jhjg")));
//			xiaoshouList.add(xiaoshou);
//		}
//		if (xiaoshouList.size() != 0) {
//			dao.update("delete from Tbl04_xiaoshou");
//			for (Tbl04_xiaoshou tbl04_xiaoshou : xiaoshouList) {
//				try {
//					dao.save(tbl04_xiaoshou);
//				} catch (RuntimeException e) {
//					// TODO Auto-generated catch block
//					System.out.println(tbl04_xiaoshou.getTime());
//					throw e;
//				}
//			}
//		}
//		// 获得进货表
//		sql = "select * from jinhuomingxi mx left join jinhuodan dan on mx.jinhuodanhao=dan.danhao";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl02_jinhuo> jinhuoList = new ArrayList<Tbl02_jinhuo>();
//		yonghuList = (List<Tbl10_yonghu>) dao.searchList("from Tbl10_yonghu");
//		yonghuMap = new HashMap<String, Integer>();
//		for (Tbl10_yonghu tbl10_yonghu : yonghuList) {
//			yonghuMap.put(tbl10_yonghu.getUsername(), tbl10_yonghu.getId());
//		}
//		shangpinList = (List<Tbl01_shangpin>) dao.searchList("from Tbl01_shangpin");
//		shangpinMap = new HashMap<String, Integer>();
//		for (Tbl01_shangpin tbl01_shangpin : shangpinList) {
//			shangpinMap.put(tbl01_shangpin.getName(), tbl01_shangpin.getId());
//		}
//		while (rs.next()) {
//			Tbl02_jinhuo jinhuo = new Tbl02_jinhuo();
//			jinhuo.setDate(rs.getTimestamp("shijian"));
//			jinhuo.setJhdh(rs.getString("dan.danhao"));
//			jinhuo.setJhsl(rs.getInt("mx.jinhuoshuliang"));
//			jinhuo.setMoney(rs.getDouble("zongjia"));
//			if (shangpinMap.get(rs.getString("goods")) != null)
//				jinhuo.setTbl01_id(shangpinMap.get(rs.getString("goods")));
//			else {
//				continue;
//			}
//			jinhuo.setTbl10_id(ConvertUtil.toInteger(yonghuMap.get(rs.getString("username")), 8));
//			jinhuo.setJhqsl(null);
//			jinhuoList.add(jinhuo);
//		}
//		if (jinhuoList.size() != 0) {
//			dao.update("delete from Tbl02_jinhuo");
//			for (Tbl02_jinhuo tbl02_jinhuo : jinhuoList) {
//				dao.save(tbl02_jinhuo);
//			}
//		}
		// 制造伪进货表 目的:
		List<Tbl01_shangpin> shangpinList = (List<Tbl01_shangpin>)dao.searchList("from Tbl01_shangpin");
		List<Tbl02_jinhuo> jinhuoList = new ArrayList<Tbl02_jinhuo>();
		Date now = new Date();
		for (Tbl01_shangpin shangpin : shangpinList) {
			if (shangpin.getKcsl() != null && shangpin.getKcsl() != 0) {
				Tbl02_jinhuo jinhuo = new Tbl02_jinhuo();
				jinhuo.setBz("[hidden]");
				jinhuo.setDate(now);
				jinhuo.setJhdh("初始化数据");
				jinhuo.setJhqsl(0);
				jinhuo.setJhsl(shangpin.getKcsl());
				jinhuo.setMoney(shangpin.getChengben()*shangpin.getKcsl());
				jinhuo.setTbl01_id(shangpin.getId());
				jinhuo.setTbl10_id(null);
				jinhuoList.add(jinhuo);
			}
		}
		for (Tbl02_jinhuo tbl02_jinhuo : jinhuoList) {
			dao.save(tbl02_jinhuo);
		}
		// // 获得兑换商品
//		sql = "select * from changegoods";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl09_duihuanshangpin> dhspList = new ArrayList<Tbl09_duihuanshangpin>();
//		shangpinList = (List<Tbl01_shangpin>) dao.searchList("from Tbl01_shangpin");
//		shangpinMap = new HashMap<String, Integer>();
//		for (Tbl01_shangpin tbl01_shangpin : shangpinList) {
//			shangpinMap.put(tbl01_shangpin.getNumber(), tbl01_shangpin.getId());
//		}
//		while (rs.next()) {
//			Tbl09_duihuanshangpin dhsp = new Tbl09_duihuanshangpin();
//			dhsp.setDhjf(0D);
//			dhsp.setDhsl(1);
//			dhsp.setTbl01_id(shangpinMap.get(rs.getString("number")));
//			dhspList.add(dhsp);
//		}
//		if (dhspList.size() != 0) {
//			dao.update("delete from Tbl09_duihuanshangpin");
//			for (Tbl09_duihuanshangpin tbl09_duihuanshangpin : dhspList) {
//				dao.save(tbl09_duihuanshangpin);
//			}
//		}
//		// 获得兑换明细表
//		sql = "select * from bonus b left join membershipcard m on b.card_id=m.id";
//		ps = conn.prepareStatement(sql);
//		rs = ps.executeQuery();
//		List<Tbl13_duihuan> duihuanList = new ArrayList<Tbl13_duihuan>();
//		huiyuanList = (List<Tbl03_huiyuan>) dao.searchList("from Tbl03_huiyuan");
//		huiyuanMap = new HashMap<String, Integer>();
//		for (Tbl03_huiyuan tbl03_huiyuan : huiyuanList) {
//			huiyuanMap.put(tbl03_huiyuan.getNumber(), tbl03_huiyuan.getId());
//		}
//		List<Map<String, Object>> dhspObjectList = dao
//				.search(
//						"select shangpin.name,dhsp.id from Tbl09_duihuanshangpin dhsp left join Tbl01_shangpin shangpin on dhsp.tbl01_id=shangpin.id")
//				.getResultList();
//		Map<String, Integer> dhspMap = new HashMap<String, Integer>();
//		for (Map<String, Object> map : dhspObjectList) {
//			dhspMap.put((String) map.get("shangpin.name"), (Integer) map.get("dhsp.id"));
//		}
//		xiaoshouList = (List<Tbl04_xiaoshou>) dao.searchList("from Tbl04_xiaoshou");
//		Map<String, Integer> xiaoshouMap = new HashMap<String, Integer>();
//		for (Tbl04_xiaoshou xiaoshou : xiaoshouList) {
//			xiaoshouMap.put(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(xiaoshou.getTime()) + ","
//					+ xiaoshou.getPrice(), xiaoshou.getId());
//		}
//		while (rs.next()) {
//			Tbl13_duihuan duihuan = new Tbl13_duihuan();
//			duihuan.setDhqjf(rs.getDouble("beforebonus"));
//			duihuan.setTime(rs.getTimestamp("exchangedate"));
//			duihuan.setTbl03_id(huiyuanMap.get(rs.getString("m.number")));
//			if (xiaoshouMap.get(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("exchangedate"))
//					+ ",0.0") == null) {
//				System.out.println(rs.getInt("card_id"));
//			}
//			Integer tbl04_id = xiaoshouMap.get(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs
//					.getTimestamp("exchangedate"))
//					+ ",0.0");
//			if (tbl04_id==null){
//				System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs
//					.getTimestamp("exchangedate"))
//					+ ",0.0");
//			}
//			duihuan.setTbl04_id(tbl04_id);
//			Integer tbl09_id = dhspMap.get(rs.getString("remark"));
//			if (tbl09_id==null){
//				tbl09_id = dhspMap.get("迷你"+rs.getString("remark"));
//			}
//			duihuan.setTbl09_id(tbl09_id);
//			duihuanList.add(duihuan);
//		}
//		if (duihuanList.size() != 0) {
//			dao.update("delete from Tbl13_duihuan");
//			for (Tbl13_duihuan tbl13_duihuan : duihuanList) {
//				dao.save(tbl13_duihuan);
//			}
//		}
		// 范例
		// sql = "select ";
		// ps = conn.prepareStatement(sql);
		// rs = ps.executeQuery();
		// List<?> ?List = new ArrayList<?>();
		// while (rs.next()) {
		// yonghuList.add(?);
		// }
		// if (List.size() != 0) {
		// dao.update("delete from ");
		// for ( : List) {
		// dao.save();
		// }
		// }
		// 关闭
		close(conn, ps, rs);
	}

	private Connection getConnection() {
		/** 声明Connection连接对象 */
		Connection conn = null;
		try {
			/** 使用Class.forName()方法自动创建这个驱动程序的实例且自动调用DriverManager来注册它 */
			Class.forName(DB_DRIVER);
			/** 通过DriverManager的getConnection()方法获取数据库连接 */
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void close(Connection conn, Statement st, ResultSet rs) throws Exception {
		if (conn != null) {
			conn.close();
		}
		if (st != null) {
			st.close();
		}
		if (rs != null) {
			rs.close();
		}
	}
}
