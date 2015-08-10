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
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css"/>
		<link rel="stylesheet" href="css/demos.css"/>
		<link rel="stylesheet" href="css/style.css"/>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#otherchengbendiv").height(parseInt((document.documentElement.clientHeight-250)));
				$(".nicetable").niceTable();
				$("#nicetable").niceTable({
					oddbgcolor:"#white",
					evenbgcolor:"#CAFF70",
					hoverable:false
				});
				$(".datepicker").datepicker({
						changeMonth: true,
						changeYear: true
				});
				$("#addchengbenbutton").click(function(){
					if ($.trim($("#date").val())==""){
						alert("成本产生日期不能为空!");
						$("#date").focus();
						return;
					} else if ($.trim($("#name").val())==""){
						$("#date").val($.trim($("#date").val()));
						alert("成本名称不能为空!");
						$("#name").focus();
						return;
					} else if ($.trim($("#money").val())==""){
						$("#name").val($.trim($("#name").val()));
						alert("成本金额不能为空!");
						$("#money").focus();
						return;
					}
					$("#money").val($.trim($("#money").val()));
					$("#chengbenform").submit();
				});
				$("#date").change(function(){
						if ($("#money").val()!="-"&&isNaN($("#money").val())){
							$("#money").val("");
						} else{
							$("#name").focus();
						}
				});
				$("#name").keyup(function(e){
					if (e.which==13){
						if ($.trim($("#name").val())!=""){
							$("#money").focus();
						}
					}
				});
				$("#money").keyup(function(e){
					if ($("#money").val()!="-"&&isNaN($("#money").val())){
						$("#money").val("");
					}
					if (e.which==13){
						if (isNaN($("#money").val())){
							$("#money").val("");
						} else{
							$("#addchengbenbutton").click();
						}
					}
				});
			});
			function delchengben(tbl06_id){
				if (confirm("确认删除该行记录吗？")){
					window.location.href="delchengben.do?tbl06_id="+tbl06_id;
				}
			}
			function editchengben(tbl06_id){
				$.ajax({
					url:'getChengbenAjax.do?tbl06_id='+tbl06_id,
					type:'post',
					success:function(msg){
						msg = $.trim(msg);
						if (msg != "fail"){
							var args = msg.split(",");
							$("#tbl06_id").val(args[0]);
							$("#date").val(args[1]);
							$("#name").val(args[2]);
							$("#money").val(args[3]);
						}
						}
				});
			}
		</script>
	</head>
	<body style="background-color:transparent;" >
	<div>
	<br/>
	<table border="0" cellspacing="0" cellpadding="0"  style="border-collapse:collapse;margin:auto" class="chengbennum" id="nicetable">
	<tr style="height:23px"><th style="width: 50px"></th><th style="width: 140px">总额</th><th style="width: 140px">成本</th><th style="width: 140px">毛利</th><th style="width: 140px">其他成本</th><th style="width: 140px">利润</th></tr>
	<tr>
	<td style="text-align: center">销售</td>
	<td><span><fmt:formatNumber pattern='0.00' value='${selled_zongjia }'/></span></td>
	<td><span><fmt:formatNumber pattern='0.00' value='${selled_chengben }'/></span></td>
	<td><span style="color:red;font-weight: bolder"><fmt:formatNumber pattern='0.00' value='${selled_zongjia-selled_chengben }'/></span></td>
	<td><span><fmt:formatNumber pattern="0.00" value="${other_chengben }"/></span></td>
	<td><span style="color:red;font-weight: bolder"><fmt:formatNumber pattern='0.00' value='${selled_zongjia-selled_chengben-other_chengben }'/></span></td>
	</tr>
	<tr style="height:23px">
	<td style="text-align: center">库存</td>
	<td><span><fmt:formatNumber pattern='0.00' value='${stored_zongjia }'/></span></td>
	<td><span><fmt:formatNumber pattern='0.00' value='${stored_chengben }'/></span></td>
	<td><span style="color:red;font-weight: bolder"><fmt:formatNumber pattern='0.00' value='${stored_zongjia-stored_chengben }'/></span></td>
	<td><span>/</span></td>
	<td><span><fmt:formatNumber pattern='0.00' value='${stored_zongjia-stored_chengben }'/></span></td>
	</tr>
	<tr style="height:23px">
	<td style="text-align: center">总合</td>
	<td><span><fmt:formatNumber pattern='0.00' value="${stored_zongjia+selled_zongjia }"/></span></td>
	<td><span><fmt:formatNumber pattern="0.00" value="${stored_chengben+selled_chengben }"/></span></td>
	<td><span style="color:red;font-weight: bolder"><fmt:formatNumber pattern='0.00' value='${stored_zongjia+selled_zongjia-stored_chengben-selled_chengben }'/></span></td>
	<td><span><fmt:formatNumber pattern="0.00" value="${other_chengben }"/></span></td>
	<td><span><fmt:formatNumber pattern="0.00" value="${selled_zongjia+stored_zongjia-other_chengben-selled_chengben-stored_chengben }"/></span></td>
	</tr>
	</table>
	</div>
	<div style="height:10%;width:100%;text-align:center" class="chengbensearch">
	<h3>其他成本明细</h3>	
	<form action="save.do" id="chengbenform" method="post">
	<input type="hidden" name="tableName" value="com.netsky.shop.domain.base.Tbl06_chengben" />
	<input type="hidden" name="forwardurl" value="/chengbenList.do"/>
	<input type="hidden" id="tbl06_id" name="Tbl06_chengben.id"/>
	日期：<input type="text" readonly="readonly" id="date" name="Tbl06_chengben.date" class="datepicker" style="width:100px;text-align:center;font-size:14px"/>&nbsp;&nbsp;
	名称：<input type="text" id="name" name="Tbl06_chengben.name" style="width:200px;text-align:left;font-size:14px"/>&nbsp;&nbsp;
	金额：<input type="text" id="money" name="Tbl06_chengben.money" style="width:50px;text-align:right;font-size:14px"/>&nbsp;&nbsp;
	<input type="button" value="提交" id="addchengbenbutton" />
	</form>
	</div>
	<div id="chengbenList" style="height:270px;background-color:;position:relative">
	<br/>
	<form id="searchForm" method="post" action="chengbenList.do">
		<div style="overflow: auto;" id="otherchengbendiv">
			<table border="0" cellspacing="0" cellpadding="0"  style="border-collapse:collapse;margin-right: auto;margin-left: auto" class="chengbentab nicetable">
						<!-- 遍历列名 -->
							<tr>
								<c:forEach items="${chengben_titleList}" var="title">
									<th style="width:${title.width }px;display:${title.display }">${title.title }</th>
								</c:forEach>
								<th>&nbsp;</th>
							</tr>
							<!-- 遍历行 -->
							<c:forEach items="${chengben_list}" var="chengben">
								<tr style="cursor:pointer" onclick="editchengben('${chengben['chengben.id'] }')">
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${chengben_titleList}" var="title">
										<td style="text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="￥0.00" value="${chengben[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price'}"><fmt:formatDate pattern="${title.pattern}" value="${chengben[title.key] }"/></c:when>
										<c:otherwise>${chengben[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
									<td ><input type="button" onclick="delchengben('${chengben['chengben.id'] }')" value=" 删 除 "/></td>
								</tr>
							</c:forEach>
						</table>
		</div>
						</form>
	</div>
						
	</body>
</html>
