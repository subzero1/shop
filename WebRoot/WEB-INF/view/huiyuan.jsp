<%@ page isELIgnored="false"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/slide.css" type="text/css" media="screen" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script src="js/slide.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$(".nicetable").niceTable();
				var bodyheight = document.documentElement.clientHeight;    // 浏览器可见区域宽  
				$("#container").css("height",bodyheight-120);
				$("#tbl01_number").keyup(function(e){
					if (e.which==13){
						$.ajax({
							url:'getShangpinInfoAjax.do?number='+$(this).val(),
							success:function(msg){
								msg = $.trim(msg);
								if (msg!="fail"){
									var args = msg.split(",");
									if (confirm("确认添加"+args[1]+"为兑换奖品吗？")){
										var dhsl = -1;
										var dhjf = -1;
										while(true){
											dhsl = prompt("请输入兑换数量：","1");
											if (dhsl.indexOf(".")==-1&&!isNaN(dhsl)&&parseInt(dhsl)>0){
												break;
											}
										}
										while(true){
											dhjf = prompt("请输入兑换积分：","100");
											if (!isNaN(dhjf)&&parseInt(dhjf)>0){
												break;
											}
										}
										$("#dhsl").val(dhsl);
										$("#dhjf").val(dhjf);
										$("#tbl01_id").val(args[0]);
										$("#adddhspform").submit();
								}
							}
							}
						});
					}
				});
				$("#duihuan").click(function(){
				var optionHTML = "";
				var dhjf = 0;
				$("#tbl09_id").children().each(function(){
					if ($(this).attr("selected")=="selected"){
						optionHTML = $(this).text();
						dhjf = $(this).attr("dhjf");
					}
				});
				if ("${huiyuan.status}"!="已激活") {
					alert("对不起！只有已激活的会员卡才能积分兑换！");
					return;
				} else if (dhjf>parseFloat("${huiyuan.dqjf}")) {
					alert("对不起！该积分卡的积分不足兑换该奖品！");
					return;
				}
				if ($("#tbl09_id").val()!=""&&confirm("确认兑换"+optionHTML+"吗？")){
					$("#duihuanform").submit();
				}
			});
			$("#delete").click(function(){
				var optionHTML = "";
				$("#tbl09_id").children().each(function(){
					if ($(this).attr("selected")=="selected"){
						optionHTML = $(this).text();
					}
				});
				if ($("#tbl09_id").val()!=""&&confirm("确认删除"+optionHTML+"吗？")){
					$("#duihuanform").attr("action","deltbl09.do");
					$("#duihuanform").submit();
				}
			});
			});
			function zhuxiao(){
				var zxyy = prompt("请输入注销原因：","");
				if (zxyy!=null){
					if (confirm("确认注销该卡吗？")){
						$("#zxyy").val(zxyy);
						$("#zhuxiaoform").submit();
					}
				}
			}
			function quit(){
				$("#container").hide(1000,function(){
					window.close();
				});
			}
		</script>
	</head>
	<body style="overflow: hidden; width: 100%; height: 100%">
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
						<a id="open" class="open" href="javascript:quit()">关闭 </a>
					</li>
					<li class="right">
						&nbsp;
					</li>
				</ul>
			</div>
		</div>
		<form id="zhuxiaoform" method="post" action="zhuxiaocard.do">
			<input type="hidden" name="zxyy" id="zxyy" />
			<input type="hidden" name="tbl03_id" value="${huiyuan.id }" />
		</form>
		<form id="adddhspform" method="post" action="adddhsp.do">
			<input type="hidden" name="dhsl" id="dhsl" />
			<input type="hidden" name="dhjf" id="dhjf" />
			<input type="hidden" name="tbl01_id" id="tbl01_id" />
			<input type="hidden" name="tbl03_id" value="${huiyuan.id }" />
		</form>
		<div id="container"
			style="position: fixed; top: 70px; width: 100%; overflow: auto;">
			<div>
				会员卡号：
				<span style="color:#c00;font-weight:bold;font-size:16px;">${huiyuan.number } </span>
				<br />
				<br />
				<table style="margin-right: auto; margin-left: auto" class="buy">
					<!-- 遍历列名 -->
					<tr>
						<c:forEach items="${huiyuaninfo_titleList}" var="title">
							<th style="width: ${title.width}">
								${title.title }
							</th>
						</c:forEach>
						<th style="width: 60px">
							操作
						</th>
					</tr>
					<!-- 遍历行 -->
					<tr>
						<!-- 根据列头 遍历每个单元格-->
						<c:forEach items="${huiyuaninfo_titleList}" var="title">
							<td style="text-align: ${title.width};${title.css }">
								<c:choose>
									<c:when test="${title.pattern=='price'}">
										<fmt:formatNumber pattern="0.00" value="${huiyuaninfo[title.key] }" />
									</c:when>
									<c:when test="${not empty title.pattern && title.pattern!='price'}">
										<fmt:formatDate pattern="${title.pattern}" value="${huiyuaninfo[title.key] }" />
									</c:when>
									<c:otherwise>${huiyuaninfo[title.key] }</c:otherwise>
								</c:choose>
							</td>
						</c:forEach>
						<td <c:if test="${huiyuan.status=='已注销'||huiyuan.status==''}"> style="display:none" </c:if>>
							<input type="button" value=" 注 销 " onclick="javascript:zhuxiao()" />
						</td>
					</tr>
				</table>
				<div style="text-align: center">
					<form method="post" action="duihuan.do" id="duihuanform" style="margin:15px">
						<input type="hidden" name="tbl03_id" value="${huiyuan.id }" />
						<select name="tbl09_id" id="tbl09_id">
							<c:forEach items="${dhspObjectList}" var="dhspObject">
								<option dhjf="${dhspObject[0].dhjf}"
									value="${dhspObject[0].id }">${dhspObject[1].name }(${dhspObject[0].dhsl }件)(<fmt:formatNumber pattern="0.00" value="${dhspObject[0].dhjf}" />分)</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;
						<input id="delete" type="button" value=" 删 除 " />
						&nbsp;&nbsp;
						<input id="duihuan" type="button" value=" 兑 换 " />
						<input type="text" id="tbl01_number" style="width: 90px" />
						<input type="text" style="display: none" />
					</form>
				</div>
			</div>
			<div style="width:98%;height:380px;margin:auto">
				<div style="width:49.5%;height:100%;float:left;overflow:auto">
					<table border="0" cellspacing="0" cellpadding="0" style="font-size:12px;border-collapse:collapse;margin-right: auto;margin-left: auto" class="nicetable goodtable">
						<!-- 遍历列名 -->
							<tr>
								<c:forEach items="${huiyuanxiaoshou_titleList}" var="title">
									<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
								</c:forEach>
							</tr>
							<!-- 遍历行 -->
							<c:forEach items="${huiyuanxiaoshou_List}" var="huiyuanxiaoshou">
								<tr>
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${huiyuanxiaoshou_titleList}" var="title">
										<td style="text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="￥0.00" value="${huiyuanxiaoshou[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price'}"><fmt:formatDate pattern="${title.pattern}" value="${huiyuanxiaoshou[title.key] }"/></c:when>
										<c:otherwise>${huiyuanxiaoshou[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
				</div>
				<div style="width:49.5%;height:100%;float:right;overflow:auto;margin-right:5px;">
				<table border="0" cellspacing="0" cellpadding="0" style="font-size:12px;border-collapse:collapse;margin-right: auto;margin-left: auto;overflow:auto" class="nicetable goodtable">
						<!-- 遍历列名 -->
							<tr>
								<c:forEach items="${huiyuanduihuan_titleList}" var="title">
									<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
								</c:forEach>
							</tr>
							<!-- 遍历行 -->
							<c:forEach items="${huiyuanduihuan_list}" var="huiyuanduihuan">
								<tr>
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${huiyuanduihuan_titleList}" var="title">
										<td style="text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="0.00" value="${huiyuanduihuan[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price'}"><fmt:formatDate pattern="${title.pattern}" value="${huiyuanduihuan[title.key] }"/></c:when>
										<c:otherwise>${huiyuanduihuan[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
				</div>
			</div><br/>
			<div style="width:100%;height:0px;background-color:transparent"></div>
		</div>
		<div id="bottom"
			style="width: 100%; position: fixed; bottom: 0px; left: 0px; text-align: center">
			<hr size="5" color="grey" />
			<center>
				 ©版权所有，违者必究
				<br />
				电话：########
			</center>
		</div>
	</body>
</html>
