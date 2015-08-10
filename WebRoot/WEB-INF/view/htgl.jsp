<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
		<script src="js/zy.js" type="text/javascript"></script>
		<script src="js/slide.js" type="text/javascript"></script>
		<script type="text/javascript">
			
			$(function(){
				$(".button").click(function(){
					$(".button2").attr("class","button");
					$(this).attr("class","button2");
				});
				if ("${param.firstclick}"!=""){
					setIframeSrc("${param.firstclick}");
				}else{
					$("#firstclick").click();
				}
			});
			function setIframeSrc(url){
				var pageRowSize = parseInt((document.documentElement.clientHeight-300)/25);
				$("#contentiframe").attr("src",url+"?pageRowSize="+pageRowSize);
			}
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
					<li style="color:white;font-weight:lighter;">
						超市管理系统 v1.0
					</li>
					<li class="sep">
						|
					</li>
					<li>
						<a class="open" id="open" href="javascript:quit()">关闭</a>
					</li>
					<li class="right">
						&nbsp;
					</li>
				</ul>
			</div>
		</div>
		<div style="position:absolute;top:45px;bottom:30px;width:100%;overflow: hidden">
			<div style="position:relative;width:100%;height:100%;">
				<div style="width:1000px;text-align:right;margin:auto">
				<c:forEach items="${htglList}" var="htgl">
					<input style="float:right" class="button" type="button" value="${htgl.title }" <c:if test="${fn:contains(htgl.remark , '[firstclick]') }">id="firstclick"</c:if> onclick="javascript:setIframeSrc('${htgl.key }')"/>
				</c:forEach>
			</div>
			<div style="position:absolute;height:94%;left:0px;bottom:0px;width:100%;overflow:hidden;background-color:;text-align: center">
			<iframe name="contentiframe" id="contentiframe" allowTransparency="true" frameborder="0" style="border-width:0px;height:98%;width:98%;overflow:auto;background-color: ;"></iframe>
			</div>
			</div>
		</div>
		<div id="bottom"
			style="width: 100%; position: fixed; bottom: 0px; text-align: center;margin-bottom:10px;">
			<hr size="5" style="color:#666;" />
			<center>
				 版权所有，违者必究&nbsp;&nbsp;	电话：83946863
			</center>
		</div>
	</body>
</html>
