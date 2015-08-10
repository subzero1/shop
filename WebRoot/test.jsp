<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="js/jquery/jquery.draggable.js" type="text/javascript"></script>
		
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		$(function(){
			$("#switch").click(function(){
				if ($(this).val()=="开"){
					$(this).val("关");
					$(this).css("background-color","red");
					$(".draggable").draggable({
						enable:false 
					});
				} else {
					$(this).val("开");
					$(this).css("background-color","lightgreen");
					$(".draggable").draggable();
				}
			});
			var i = 0;
			$("#button1").click(function(){
				$("#test").val(++i);
			});
		});
	</script>
  </head>
  
  <body>
    <input type="button" class="draggable" id="button1"/>
    <input type="button" id="switch" value="关" style="background-color: red"/>
    <input type="button" id="bt2"/>
    <input type="text" id="test"/>
  </body>
</html>
