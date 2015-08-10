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
				if ("${param.save}"=="yes"){
					parent.location.reload();
				}
				$("#submitbutton").click(function(){
					var flag = true;
					$("input[type='text']").each(function(){
						if ($(this).attr("id")!="guige"&&$(this).val()==""){
							$(this).focus();
							alert($(this).parent().prev().text()+"不能为空！");
							flag = false;
						}
					});
					if (flag){
						if (isNaN($("#price").val())||(parseFloat($("#price").val())<=0)){
							alert("售价必须为正数！");
							$("#price").focus();
							flag = false;
						}else if (isNaN($("#yuzhi").val())||($("#yuzhi").val().indexOf(".")!=-1)||(parseInt($("#yuzhi").val())<0)){
							alert("阈值必须为非负整数！");
							$("#yuzhi").focus();
							flag = false;
						}
					}
					if (flag){
					$("#shangpinform").submit();
					}
				});
			});
			function setIframeSrc(url){
				$("#contentiframe2").attr("src",url);
			}
		</script>
	</head>
	<body style="overflow:hidden;width:580px;background-color:transparent;">
		<form method="post" action="save.do" id="shangpinform" enctype="multipart/form-data">
			<input type="hidden" name="tableName" value="com.netsky.shop.domain.base.Tbl01_shangpin" />
			<input type="hidden" name="forwardurl" value="/shangpin.do?tbl01_id=${shangpin.id }"/>
			<input type="hidden" name="Tbl01_shangpin.id" value="${shangpin.id }"/>
			<table border="0" cellspacing="0" cellpadding="0" style="margin-left:40px;margin-top:50px;border-collapse:collapse;width:550px;" class="jinhuotab">
				<tr>
				<td style="width:15%;text-align:center">商品名称</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" type="text" name="Tbl01_shangpin.name" value="${shangpin.name }"/>
				</td>
				<td style="width:15%;text-align:center">商品编号</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" type="text" name="Tbl01_shangpin.number" value="${shangpin.number }"/>
				</td>
				</tr>
				<tr>
				<td style="width:15%;text-align:center">商品类别</td><td style="width:35%">
					<select name="Tbl01_shangpin.tbl05_id" style="width:98%;background-color:#eaffff"/>
						<c:forEach items="${leixingList}" var="leixing">
							<option value="${leixing.id }" <c:if test="${leixing.id==shangpin.tbl05_id }">selected="selected"</c:if>>${leixing.name }</option>
						</c:forEach>
					</select>
				</td>
				<td style="width:15%;text-align:center">库存数量</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" style="width:98%" type="text" readonly="readonly" name="Tbl01_shangpin.kcsl" value="${shangpin.kcsl }"/>
				</td>
				</tr><tr>
				<td style="width:15%;text-align:center">商品价格</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" type="text" id="price" name="Tbl01_shangpin.price" value="${shangpin.price }"/>
				</td>
				<td style="width:15%;text-align:center">商品成本</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" type="text" readonly="readonly" name="Tbl01_shangpin.chengben" value="${shangpin.chengben }"/>
				</td>
				</tr>
				<tr>
				<td style="width:15%;text-align:center">商品阈值</td><td style="width:35%">
					<input style="width:98%;background-color:#eaffff" id="yuzhi" type="text" name="Tbl01_shangpin.yuzhi" value="${shangpin.yuzhi }"/>
				</td><td style="width:15%;text-align:center">商品规格</td><td><input style="width:98%;background-color:#eaffff" id="guige" type="text" name="Tbl01_shangpin.guige" value="${shangpin.guige }"/></td>
				</tr>
				<tr><td style="width:15%;text-align:center">商品说明</td><td><textarea style="background-color:#eaffff;height:150px;" name="Tbl01_shangpin.spsm">${shangpin.spsm }</textarea></td>
				<td>商品图片</td><td>
				<img alt="暂无图片" style="width:150px;height:150px" src="download.do?id=${shangpin.id }&classname=com.netsky.shop.domain.base.Tbl01_shangpin"/>
				<input type="file" name="thefile" /></td>
				</tr>
				<tr><td colspan="4" style="text-align:center;height:40px"><input type="button" id="submitbutton" value=" 提 交 "/></td></tr>
			</table>
		</form>
	</body>
</html>
