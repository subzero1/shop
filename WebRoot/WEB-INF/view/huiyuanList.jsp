<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/style.css"/>
		<style>
			#searchForm input[type=text],select{
				background-color:#eaffff;
			}
		</style>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
			$(".nicetable").niceTable();
			$("#number").focus();
			$("td").each(function(){
			var var0 = $(this).attr("flag");
			if (var0 != undefined){
				if (var0.indexOf("[colorchange]")!=-1){
					var var1 = $.trim($(this).html());
					if (parseFloat(var1)>=100){
						$(this).css("color","red");
						$(this).css("font-weight","bolder");
					} else if (parseFloat(var1)>=50){
						$(this).css("color","blue");
						$(this).css("font-weight","bolder");
					}
				} else if (var0.indexOf("[statussymbol]")!=-1){
					var var1 = $.trim($(this).html());
					if (var1=="未激活"){
						$(this).html("");
					}else if (var1=="已注销"){
						$(this).html("×");
					}else if (var1=="已激活"){
						$(this).html("√");
					}
				}
				}
			});
			$("#searchForm").submit(function(){
				if ("${param.number}"!=$("#number").val()){
					$("#pageNum").val("1");
				} else if ("${param.dqjf}"!=$("#dqjf").val()){
					$("#pageNum").val("1");
				} else if ("${param.status}"!=$("#status").val()){
					$("#pageNum").val("1");
				}
			});
			$("#cxdhmx").click(function(){
				window.location.href="tongji.do?cxlx=jfdhmx&time1=";
			});
			});
		</script>
	</head>
	<body style="background-color:transparent;">
	<div style="width:100%;position:relative;">
		<form id="searchForm" method="post" action="huiyuanList.do">
			<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }"/>
			<input type="hidden" id="totalRows" name="totalRows" value="${totalRows }"/>
			<input type="hidden" id="totalPages" name="totalPages" value="${totalPages }"/>
			<input type="hidden" id="pageRowSize" name="pageRowSize" value="${pageRowSize }"/>
			<br/>
			<div style="text-align:center">会员卡号末位：<input type="text" value="${param.number }" onfocus="this.select()" name="number" id="number" style="width:70px"/>&nbsp;&nbsp;
			卡状态：<select name="status" id="status">
			<option value="已激活" <c:if test="${param.status=='已激活' }">selected="selected"</c:if>>已激活</option>
			<option value="未激活" <c:if test="${param.status=='未激活' }">selected="selected"</c:if>>未激活</option>
			<option value="已注销" <c:if test="${param.status=='已注销' }">selected="selected"</c:if>>已注销</option>
			</select>&nbsp;&nbsp;
			<select name="jflx">
			<option value="dqjf" <c:if test="${param.jflx=='dqjf' }">selected="selected"</c:if>>当前积分</option>
			<option value="ljjf" <c:if test="${param.jflx=='ljjf' }">selected="selected"</c:if>>累计积分</option>
			</select>&nbsp;&nbsp;
			不小于：<input id="dqjf" type="text" name="dqjf" value="${param.dqjf }" style="width:50px;text-align:right"/>&nbsp;&nbsp;
			<select name="ydh" style="width:80px">
			<option></option>
			<option value="曾经兑换" <c:if test="${param.ydh=='曾经兑换' }">selected="selected"</c:if>>曾经兑换</option>
			<option value="从未兑换" <c:if test="${param.ydh=='从未兑换' }">selected="selected"</c:if>>从未兑换</option>
			</select>&nbsp;&nbsp;
			<input type="submit" value=" 查 询 "/>&nbsp;&nbsp;
			<input type="button" value="查询兑换明细" id="cxdhmx"/><br/><br/>
			会员卡总数：<span style="color:red;font-weight:bolder">${cardcount }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已激活数：<span style="color:red;font-weight:bolder">${yjhcount }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			未激活数：<span style="color:red;font-weight:bolder">${wjhcount }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已注销数：<span style="color:red;font-weight:bolder">${yzxcount }</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			当前总积分：<span style="color:red;font-weight:bolder"><fmt:formatNumber pattern="0.00" value="${totaldqjf }"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			累计总积分：<span style="color:red;font-weight:bolder"><fmt:formatNumber pattern="0.00" value="${totalljjf }"/></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			兑换卡数：<span style="color:red;font-weight:bolder"><fmt:formatNumber pattern="0" value="${dhs }"/></span><br/><br/></div>
			<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin-right: auto;margin-left: auto" class="goodtable nicetable">
				<!-- 遍历列名 -->
					<tr>
						<c:forEach items="${huiyuan_titleList}" var="title">
							<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
						</c:forEach>
					</tr>
					<!-- 遍历行 -->
					<c:forEach items="${huiyuan_list}" var="huiyuan">
						<tr onclick="window.open('huiyuan.do?tbl03_id=${huiyuan['huiyuan.id'] }','huiyuan')" style="cursor:pointer">
							<!-- 根据列头 遍历每个单元格-->
							<c:forEach items="${huiyuan_titleList}" var="title">
								<td flag="${title.remark }" style="text-align: ${title.align };display:${title.display }">
								<c:choose>
								<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="0.00" value="${huiyuan[title.key] }"/></c:when>
								<c:when test="${not empty title.pattern && title.pattern!='price'}">${huiyuan[title.key] }</c:when>
								<c:otherwise>${huiyuan[title.key] }</c:otherwise>
								</c:choose>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</table>
				</form>
				
			</div>
			<div id="paginationDiv" style="position:absolute;bottom:0px;width:100%">
			
			<table style="margin-right: auto;margin-left: auto">
				<c:if test="${pageRowSize!=-1}">
				<tr>
						<td>
							<c:if test="${pageNum!=1}">
								<a style="color: black; text-decoration: none;"
									href="javascript:turnToPage('1')">首页</a>
								<a style="color: black; text-decoration: none;"
									href="javascript:turnToPage('${pageNum-10>1 ? pageNum-10 : 1 }')">前十页</a>
							</c:if>
							<span id="pagesNearBy"></span>
							<c:if test="${pageNum<totalPages}">
								<a style="color: black; text-decoration: none;"
									href="javascript:turnToPage(${pageNum+10<totalPages ? pageNum+10 : totalPages })">后十页</a>
								<a style="color: black; text-decoration: none;" href="javascript:turnToPage(${totalPages })">
									尾页[${totalPages}]
								</a>
							</c:if>
						</td>
						<c:if test="${totalPages>1}">
						<td>
							<input type="text" style="width: 30px" id="paginationInput"/>
							<input type="button" id="paginationButton" value="转到" />
						</td>
						</c:if>
					</tr>
				</c:if>
			</table>
			</div>
	</body>
</html>
