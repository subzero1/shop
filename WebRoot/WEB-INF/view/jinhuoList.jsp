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
		<link rel="stylesheet" href="css/style.css"/>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$(".nicetable").niceTable();
			});
			function quit(){
			}
		</script>
	</head>
	<body style="background-color:transparent">	
	<form id="searchForm" method="post" action="jinhuodan.do">
		<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }"/>
		<input type="hidden" id="totalRows" name="totalRows" value="${totalRows }"/>
		<input type="hidden" id="totalPages" name="totalPages" value="${totalPages }"/>
		<input type="hidden" id="pageRowSize" name="pageRowSize" value="${pageRowSize }"/>
		<br/>
			<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin-right: auto;margin-left: auto" class="jinhuodantab nicetable">
						<caption><b>进货记录</b><input type="button" value="返回进货" onclick="javascript:window.location.href='jinhuoview.do'" style="float:right;"/></caption>
						<!-- 遍历列名 -->
							<tr>
								<c:forEach items="${jinhuodan_titleList}" var="title">
									<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
								</c:forEach>
							</tr>
							<!-- 遍历行 -->
							<c:forEach items="${jinhuodan_list}" var="jinhuodan">
								<tr style="cursor:pointer" onclick="window.location.href='jinhuomingxi.do?jhdh=${jinhuodan['jinhuo.jhdh'] }&pageRowSize=${param.pageRowSize }'">
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${jinhuodan_titleList}" var="title">
										<td style="text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="￥0.00" value="${jinhuodan[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price'}"><fmt:formatDate pattern="${title.pattern}" value="${jinhuodan[title.key] }"/></c:when>
										<c:otherwise>${jinhuodan[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
						<div id="paginationDiv" style="position:fixed;bottom:10px;width:100%">
			</form>
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
	</body>
</html>
