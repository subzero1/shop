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
		<link rel="stylesheet" href="css/style.css" type="text/css"
			media="screen" />
		<link rel="stylesheet" href="css/slide.css" type="text/css"
			media="screen" />
		<link rel="stylesheet" type="text/css" href="css/default.css" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/slide.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("#container").fadeIn(1000);
				var bodyheight = document.documentElement.clientHeight;    // 浏览器可见区域宽  
				$("#container").css("height",bodyheight-120);
			});
			function quit(){
				$("#container").hide(1000,function(){
					window.close();
				});
			}
		</script>
	</head>
	<body style="overflow: hidden;width: 100%;height: 100%">
		<div id="toppanel">
			<!-- The tab on top -->
			<div class="tab">
				<div style="position: absolute; left: 0px; top: 0">
					<img src="images/yijiarenlogo.png" />
				</div>
				<ul class="login">
					<li class="left">
						&nbsp;
					</li>
					<li>
						超市管理系统 v1.0
					</li>
					<li class="sep">
						|
					</li>
					<li id="toggle">
						<a id="open" class="open" href="javascript:quit()">关闭</a>
					</li>
					<li class="right">
						&nbsp;
					</li>
				</ul>
			</div>
		</div>
		<div id="container" style="position: fixed;top:70px;width: 100%;overflow: auto;display:none">
			
		</div>
	<div id="bottom"
			style="width: 100%; position: fixed; bottom: 0px; text-align: center">
			<hr size="5" color="grey" />
			<center>
				 ©版权所有，违者必究
				<br />
				电话：########
			</center>
		</div>
	</body>
</html>
