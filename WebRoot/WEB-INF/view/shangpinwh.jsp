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
			});
			function setIframeSrc(url){
				$("#contentiframe2").attr("src",url);
			}
		</script>
	</head>
<body style="background-color:transparent;text-align:center;">
<div style="height:450px;width:950px;overflow:none;border:solid 1px #ccc;margin:auto;padding-top:5px;">
		<div style="height:30px;width:300px;">
		<form method="post" action="shangpinwh.do">
		<input type="test" value="${param.keyword }" name="keyword"/>
			<select name="tbl05_id">
			<option></option>
				<c:forEach items="${leixingList}" var="leixing">
					<option value="${leixing.id }" <c:if test="${leixing.id==param.tbl05_id }">selected="selected"</c:if>>${leixing.name }</option>
				</c:forEach>
			</select><input type="submit" value="查询"/>
		</form>
		</div>
	<div style="width:320px;height:93%;float:left;overflow:auto;" class="shangpinlist">
		<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;" class="spwh" width="100%">
			<c:set scope="page" var="offset" value="0"/>
			<c:forEach items="${shangpinList}" var="shangpin">
			<c:set scope="page" var="offset" value="${offset+1}"/>
				<tr>
					<th style="width:40px">${offset }.</th>
					<td>
						<a href="javascript:setIframeSrc('shangpin.do?tbl01_id=${shangpin.id }')" <c:if test="${shangpin.kcsl==0}">style="color:black"</c:if><c:if test="${shangpin.kcsl!=0&&empty shangpin.spsm}">style="color:red"</c:if>>${shangpin.name }</a><br />
					</td>
				</tr>	
			</c:forEach>
		</table>
	</div>
		<div style="width:600;height:100%;float:left;">
			<iframe src="shangpin.do?tbl01_id=${param.tbl01_id }" name="contentiframe2" id="contentiframe2" allowTransparency="true" frameborder="0" style="border-width:0px;height:98%;width:600px;overflow:hidden;"></iframe>
		</div>
</div>
</body>
</html>
