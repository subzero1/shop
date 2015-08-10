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
		<link rel="stylesheet" href="css/demos.css">
		<link rel="stylesheet" href="css/style.css" type="text/css"
		media="screen" />
		<style>
			#mmxgtable input[type=password],select{
				background-color:#eaffff;
			}
		</style>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
			if ('${msg}'!=''){
				alert('${msg}');
			}
				$("#oldpassword").focus();
				$("#submitbutton").click(function(){
					$.ajax({
						url:'validatePwd.do?pwd='+$("#oldpassword").val(),
						type:'post',
						success:function(msg){
							msg = $.trim(msg);
							if (msg == 'false'){
								alert("原密码输入错误!");
								$("#oldpassword").val("");
								$("#oldpassword").focus();
								return false;
							}else if ($("#newpassword").val()=="") {
						alert("新密码不能为空!");
						$("#newpassword").focus();
						return false;
					} else if ($("#newpassword").val()!=$("#repassword").val()) {
						alert("两次密码输入不一致!");
						return false;
					}
					$("#form").submit();
						}
					});
					
				});
			});
			function setIframeSrc(url){
				$("#contentiframe2").attr("src",url);
			}
		</script>
	</head>
	<body style="background-color:transparent">
		<form action="changepwd.do" method="post" id="form">
		<table id="mmxgtable" border="0" cellspacing="0" cellpadding="0" style="margin:auto;width:300px;border-collapse:collapse;" class="goodtable">
			<tr>
				<td width="30%" height="35" class="" style="text-align:right;">
					原始密码：
					<br />
				</td>
				<td width="70%">
					<input  style="width:99%" name="oldpassword" id="oldpassword" type="password" size="30" />
				</td>
			</tr>
			<tr>
				<td height="35" class=""style="text-align:right;">
					设置密码：<br />
				</td>
				<td>
					<input  style="width:99%" name="password" id="newpassword" type="password" size="33" />
				</td>
			</tr>
			<tr>
				<td height="35" class=""style="text-align:right;">
					确认密码：<br />
				</td>
				<td>
					<input  style="width:99%" name="repassword" id="repassword" type="password" size="33" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center">
					<input id="submitbutton" type="button" class="right-button01"
						value="修 改" />
					<input name="reset1" type="reset" class="right-button02"
						value="重 置" />
					<input name="return" type="button" class="right-button02" onclick="javascript:history.go(-1)"
						value="返 回" />
				</td>
			</tr>
		</table>
			</form>
	</body>
</html>
