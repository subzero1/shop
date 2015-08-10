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
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="css/demos.css" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
				if ($("#keyword").val()==""){
					$(window.parent.frames["contentiframe1"].document).find("#numberinput").focus();
				}else{
				$("#keyword").focus();
				}
				$("#keyword").keyup(function(e){
					if (e.which==8 && $(this).val()==""){
						$("#searchForm").submit();
					}
				});
				$(".nicetable").niceTable();
				$("#zongxiaofei").text(parseFloat($(window.parent.frames["contentiframe1"].document).find("#zongxiaofei").val()).toFixed(2));
				$("#searchForm").submit(function(){
					if ("${param.keyword}"!=$("#keyword").val()){
						$("#pageNum").val("1");
					}
					$("#numberinput").keyup(function(e){
					if(e.which==13){
						$("#number").val($("#numberinput").val());
						$("#operator").val("+");
						$("#searchForm").submit();
					}
				});
				});
				$(".goumaibutton").click(function(){
					$(window.parent.frames["contentiframe1"].document).find("#numberinput").val($(this).attr("flag"));
					$(window.parent.frames["contentiframe1"].document).find("#xiaoshouform").submit();
				});
				$("#hygl").click(function(){
					window.open('htgl.do?firstclick=huiyuanList.do','htgl');
				});
			});
		</script>
	</head>
	<body style="background-color:;overflow:hidden;position:relative;height:300px">
	<form method="post" id="searchForm" action="good.do">
	<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }"/>
	<input type="hidden" id="totalRows" name="totalRows" value="${totalRows }"/>
	<input type="hidden" id="totalPages" name="totalPages" value="${totalPages }"/>
	<input type="hidden" id="pageRowSize" name="pageRowSize" value="${pageRowSize }"/>
						<table style="width:800px;margin:auto;height:25px"><tr><td style="text-align:left;width:150px"><span>操作员：<span style="font-weight: bolder">${yonghu.nickname }</span></span></td><td><span>当前消费：</span><span id="zongxiaofei" style="color:red;font-weight:bolder;font-size:16px;"></span>
						<span style="margin-left:20px;">数量合计：</span><span id="shuliangheji" style="color:red;font-weight:bolder;font-size:16px;"></span>
						</td><td style="text-align:right">
						<select name="zero" onchange="searchForm.submit()">
						<option value="1">全部商品</option>
						<option value="0" <c:if test="${param.zero == 0 }">selected="selected"</c:if>>非零商品</option>
						</select>
						<span>查询商品：</span><span><input type="text" id="keyword" style="color: blue" name="keyword" value="${param.keyword }" onfocus="this.select()"/>&nbsp;&nbsp;<input type="button" value="会员管理" id="hygl"/></span></td></tr></table>
						<div  style="height:270px;margin-right: auto;margin-left: auto;overflow:auto;width:100%;text-align:center">
						
		<table border="0" cellspacing="0" cellpadding="0" style="margin-right: auto;margin-left: auto;border-collapse:collapse;" class="goodtable nicetable">
							<!-- 遍历列名 -->
							<tr>
								<c:forEach items="${shangpin_titleList}" var="title">
									<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
								</c:forEach>
								<th style="width: 80px">操作</th>
							</tr>
							<!-- 遍历行 -->
							<c:forEach items="${shangpin_list}" var="shangpin">
								<tr>
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${shangpin_titleList}" var="title">
										<td style="text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="￥0.00" value="${shangpin[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price'}"><fmt:formatDate pattern="${title.pattern}" value="${shangpin[title.key] }"/></c:when>
										<c:otherwise>${shangpin[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
									<td style="text-align:center"><input type="button" value=" 购 买 " class="goumaibutton" flag="${shangpin['shangpin.number'] }"/></td>
								</tr>
							</c:forEach>
						</table>
						</div>
					</form>
					<div id="paginationDiv" style="position:absolute;bottom:10px;width:100%">
			<table style="margin-right: auto;margin-left: auto;text-align:center">
			
				<c:if test="${pageRowSize!=-1&&totalPages!=1}">
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

