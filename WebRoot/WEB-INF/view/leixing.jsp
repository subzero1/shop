<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统 v1.0</title>
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
				$("#name").focus();
				$("#xinjian").click(function(){
					$("#id").val("");
					$("#name").val("");
					$("#flag").val("");
					$("#name").focus();
				});
				if ("${param.save}"=="yes"){
					parent.location.reload();
				}
				$("#submitbutton").click(function(){
					if ($("#name").val()!=""){
						$("#leixingform").submit();
					}else{
						$("#name").focus();
					}
				});
			});
			function setIframeSrc(url){
				$("#contentiframe2").attr("src",url);
			}
		</script>
	</head>
	<body style="background-color:transparent;overflow:hidden;width:280px;padding:50px 0 0 30px;">
		<form method="post" action="save.do" id="leixingform">
			修改或新建商品类别：<br/>
			<input type="hidden" name="tableName" value="com.netsky.shop.domain.base.Tbl05_leixing" />
			<input type="hidden" name="forwardurl" value="/leixing.do?save=yes"/>
			<input type="hidden" id="id" name="Tbl05_leixing.id" value="${leixing.id }"/>
			<input type="hidden" id="flag" name="Tbl05_leixing.flag" value="${leixing.flag }"/>
			<input type="text" id="name" name="Tbl05_leixing.name" value="${leixing.name }"/>
			<br/>
			<input type="button" id="submitbutton" value=" 提 交 "/><input type="button" id="xinjian" value=" 新 建 "/>
		</form>
	</body>
</html>
