<%@ page isELIgnored="false"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>超市管理系统 v1.0</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/slide.css" type="text/css" media="screen" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.cookie.js" type="text/javascript"></script>
		<script src="js/slide.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$("input[type=text]").focus(function(){
				$(this).css("background-color","white");
			});
			$("input[type=password]").focus(function(){
				$(this).css("background-color","white");
			});
			$("#username").val($.cookie('username'));
			$("#password").val($.cookie('password'));
			if ($.cookie('savepwd')=="checked") {
				$("#savepwd").click();
			}
			if ('${err_msg}'!='') {
					alert('${err_msg}');
			}
			setTimeout("delay()",500);
			$("#open").click();
			
			$("#username").focus();
			$("#submitbutton").click(function(){
				submittest();
			});
			$("#username").keyup(function(e){
				if (e.which==13){
					$("#password").focus();
				}
			});
			$("#password").keyup(function(e){
				if (e.which==13){
					submittest();
				}
			});
		});
		function submitfunction(){
			$("#thisform").submit();
		}
		function submittest(){
			if($.trim($("#username").val())==""){
			alert("用户名不能为空!");
			return;
			}else if ($.trim($("#password").val())==""){
			alert("密码不能为空!");
			return;
			}
			$.cookie('username',$.trim($("#username").val()),{expires:365});
			if ($("#savepwd").attr("checked")=="checked"){
			$.cookie('password',$.trim($("#password").val()),{expires:365});
			$.cookie('savepwd',"checked",{expires:365});
			}else{
			$.cookie('password',null);
			$.cookie('savepwd',null);
			}
			$("div#panel").slideToggle(600,function(){
				setTimeout("submitfunction()",600);
			});
			
		}
		function delay(){
		}
		</script>
	</head>
	<body style="overflow: hidden;width: 100%;height: 100%">
		<!-- Login -->
		<div id="toppanel">
			<div id="panel">
				<div class="content clearfix">
					<div class="left">
					<br/>
						<img src="images/yijiaren.png" />
						<h2 style="color:#c00;">
							我们将用最好的服务回报您的惠顾
							<br/>
							真诚！耐心！品质！
						</h2>
					</div>
					<div class="left">
						<form action="login.do" method="post" id="thisform">
						<br/>					
							<h1 style="font-family:黑体">
								超市管理系统
							</h1>
							<label for="username">
								用户名：
							</label>
							<input class="field" type="text" name="username" id="username" value="" size="23" />
							<br/><br/>
							<label class="user" for="password">
								口&nbsp;&nbsp;&nbsp;&nbsp;令：
							</label>
							<input class="field" type="password" value="" name="password" id="password" size="23" />
							<br/>							
							<div class="clear"></div>
							<label class="user" for="password"></label><input type="checkbox" name="savepwd" id="savepwd" value="savepwd"  /> 记住密码<br/>
							<input type="button" id="submitbutton" value="登 录" class="bt_login" />
						</form>
					</div>
					<div class="shoppic">
						<img src="images/shop_pic.jpg"/>
					</div>
				</div>
			</div>
			<!-- /login -->
			<!-- The tab on top -->
			<div class="tab">
				<ul class="login">
					<li class="left">
						&nbsp;
					</li>
					<li style="color:white;font-weight:lighter;">
						超市管理系统 v1.0
					</li>
					<li class="sep">
						|
					</li>
					<li id="toggle">
						<a id="open" class="open">登 录</a>
						<a id="close" style="display: none;" class="close" href="#">关 闭</a>
					</li>
					<li class="right">
						&nbsp;
					</li>
				</ul>
			</div>
			<!-- / top -->
			
		</div>
		<!--panel -->
		<div id="container">
			<div id="content" style="padding-top: 100px;">
			</div>
			<!-- / content -->
		</div>
		<!-- / container -->
	</body>
</html>

