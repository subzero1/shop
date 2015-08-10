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
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css">
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
				$("#contentiframe2").attr("src",url);
			}
		</script>
	</head>
	<body style="background-color:transparent;">
		<div style="min-height:250;width:450px;overflow:none;border:solid 1px #ccc;margin:auto;padding-top:5px;">
			<div  style="width:300px;height:100%;float:right">
				<iframe src="leixing.do" name="contentiframe2" id="contentiframe2" allowTransparency="true" frameborder="0" style="border-width:0px;height:50%;width:auto;overflow:none"></iframe>
			</div>
			<div style="width:150px;height:100%;">
				<ul class="leixinglist">
				<c:forEach items="${leixingList}" var="leixing">
				<li onClick="javascript:setIframeSrc('leixing.do?tbl05_id=${leixing.id }')">${leixing.name }</li>
				</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>
