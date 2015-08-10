<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css"/>
		<link rel="stylesheet" href="css/demos.css"/>
		<link rel="stylesheet" href="css/style.css"/>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
			});
			function setIframeSrc(url){
				$("#contentiframe1").attr("src",url);
			}
		</script>
	</head>
	<body style="background-color:transparent;text-align:center">
		<br/>
		<input type="button" id="shangpinwh" value="商品维护" onclick="setIframeSrc('shangpinwh.do')"/>
		<input type="button" id="leixingwh" value="商品类别维护" onclick="setIframeSrc('leixingwh.do')"/>
		<input type="button" id="mmxg" value="密码修改" onclick="setIframeSrc('dispath.do?url=mmxg.jsp')"/>
		<div style="height:90%;position:absolute;top:10%;left:0px;width:100%;overflow:hidden;">
		<iframe src="shangpinwh.do?tbl01_id=${param.tbl01_id }" name="contentiframe1" id="contentiframe1" allowTransparency="true" frameborder="0" style="border-width:0px;height:98%;width:98%;overflow:hidden;"></iframe>
		</div>
	</body>
</html>
