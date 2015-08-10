<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="java.util.Date"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/style.css" type="text/css" media="screen" />
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet" href="css/demos.css" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/datepickerhelp.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
			if ('${msg}'!='') {
			alert('${msg}');
			}
			$(window.parent.document).find("#zongxiaofei").text('<fmt:formatNumber value='${sum_money}' pattern='0.00'/>');
			$(window.parent.document).find("#shuliangheji").text('${sum_count}');
			$(window.parent.frames["contentiframe"].document).find("#zongxiaofei").text('<fmt:formatNumber value='${sum_money}' pattern='0.00'/>');
			$(window.parent.frames["contentiframe"].document).find("#shuliangheji").text('${sum_count}');
			$("#numberinput").focus();
				if ('${param.jiezhang}'=='yes'){
					jiezhang();
					$("#numberinput").focus(function(){
						$(window.parent.document).find("#cardnumber").focus();
					});
				}
				$("#timeStamp").val(new Date().getTime());
				$("#clear").click(function(){
					if ($("#zongxiaofei").val()!="0.0"&&$("#zongxiaofei").val()!=""){
						window.parent.location.href="clear.do";
					}
				});
				$(".inputcount").keyup(function(e){
					var count = $(this).val();
					if (isNaN(count)){
						$(this).val("");
						return;
					}
					if (count.indexOf(".")!=-1){
						$(this).val("");
						return;
					}
					if (count=="0"){
						$(this).val("");
						return;
					}
					if (parseInt($(this).parent().prev().text())<(parseInt($(this).val())+1)){
						$(this).val(parseInt($(this).parent().prev().text()));
					}
					if (count!=""){
						xiaoshou($(this).attr("flag"),$(this).val());
					}
				});
				$("#htgl").click(function(){
					htgl = window.open('htgl.do','htgl');
				});
				$("#pay").click(function(){
					var trnumber = 0;
					$(".buytr").each(function(){
						if ($(this).css("display")!="none"){
							trnumber++;
						}
					});
					if (trnumber>=1){
					window.parent.location.href="xiaoshou.do?jiezhang=yes";
					}
				});
				
				
				$("#jiezhang").click(function(){
					if ($("#card_status").html()=="已注销"){
						$("#card").val("");
						if (!confirm("该会员卡已经注销，继续操作不会积累积分，是否继续？")){
							return;
						}
					}
					$("#jiezhangform").submit();
				});
				$(".add").click(function(){
					var count = parseInt($(this).next().val());
					if (parseInt($(this).parent().prev().prev().prev().text())>=(count+1)) {
						$(this).next().val(count+1);
						xiaoshou($(this).attr("flag"),count+1);
					}
				});
				$(".reduce").click(function(){
					var count = parseInt($(this).prev().val());
					if (count>1){
						$(this).prev().val(count-1);
						xiaoshou($(this).attr("flag"),count-1);
					}
				});
				$(".del").click(function(){
					xiaoshou($(this).attr("flag"),0);
					$(this).parent().parent().hide();
					$(this).parent().parent().next().css("color","red");
					$(this).parent().parent().next().css("font-size","16px");
					$(this).parent().parent().next().css("font-weight","bolder");
				});
			});
			function jiezhang(){
				$(window.parent.document).find("#contentiframe").hide();
				$(window.parent.document).find("#payinfo").show();
				$(".add").hide();
				$(".reduce").hide();
				$(".del").hide();
				$(window.parent.document).find("#cardnumber").focus();
			}	
			
			function xiaoshou(tbl01_id,count) {
			$.ajax({
				url:'addbuyAjax.do?tbl01_id='+tbl01_id+"&count="+count,
				type:'post',
				success:function(msg){
					if ($.trim(msg).indexOf("fail")!=-1){
						alert(msg);
					} else {
						msgs = msg.split(",");
						$(window.parent.frames["contentiframe"].document).find("#zongxiaofei").text(msgs[0]);
						$("#zongxiaofei").val(msgs[0]);
						$(window.parent.frames["contentiframe"].document).find("#shuliangheji").text(msgs[1]);
						$("#shuliangheji").val(msgs[1]);
					}
				}
			});
			}
			
			
			/*****************************************************************
			/*功能:iframe自适应其加载的网页
			******************************************************************/
			function iframeAutoFit()
			{
			 if(window!=parent)
			 {
			  var a = parent.document.getElementsByTagName("IFRAME");
			    for(var i=0; i<a.length; i++)
			    {
			       if(a[i].contentWindow==window)
			       {
			           var h = document.body.scrollHeight;
			           var sh = a[i].height;
			          
			           if(h>sh){
				           if(document.all) {h += 4;}
				           if(window.opera) {h += 1;}
				           a[i].height = h;
			           }
			       }
			    }
			 }
			}
			
		</script>
	</head>
	<body style="background-color:transparent;" onload="javascript:iframeAutoFit();">
	<input type="hidden" id="zongxiaofei" value="<fmt:formatNumber value='${sum_money}' pattern='0.00'/>"/>
	<input type="hidden" id="shuliangheji" value="${sum_count}"/>
	<div id="head" style="height: 9%;text-align:center" class="head">
			<form method="post" action="buy.do" id="xiaoshouform">
			<input type="hidden" name="tbl01_id" id="tbl01_id"/>
			<input type="hidden" name="timeStamp" id="timeStamp"/>
			<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin:auto">
				<tr>
					<td height="30"><b style="font-size:18px">商品编码：</b></td>
					<td><input type="text" name="number" id="numberinput" class="buyinput"/></td>
					<td><input type="button" value="&nbsp;" id="pay" class="buybutton"/></td>
					<td><input type="button" value="&nbsp;" id="clear" class="clearbutton"/></td>
					<td><input type="button" value="&nbsp;" id="htgl" class="htbutton"/></td>
					<td></td>
				</tr>
				<tr style="height:5px"><td></td></tr>
			</table>
			</form>
			</div>
			<div id="buy">
			<table id="buytable" style="margin-right: auto;margin-left: auto;table-layout:fixed" class="buy">
					<tr>
						<th style="width:120px" height="25">商品编码</th>
						<th style="text-align:left;width:250px">商品名称</th>
						<th style="width:60px">库存</th>
						<th style="width:90px">商品类别</th>
						<th style="width:60px">单价</th>
						<th style="width:120px">购买数量</th>
						<th style="width:80px">操作</th>
					</tr>
					<c:set scope="page" var="offset" value="0"/>
					<c:forEach items="${xiaoshouList}" var="o">
					<c:set scope="page" var="offset" value="${offset+1}"/>
						<tr class="buytr" style="<c:if test='${offset == 1}'>color:red;font-size:16px;font-weight:bolder</c:if>">
						<td style="text-align: left;padding-left:15px;">${o[0].number }</td>
						<td style="text-align: left;" title="${o[0].name }">${o[0].name }</td>
						<td style="text-align: right;">${o[0].kcsl }</td>
						<td style="text-align: left;">${o[2].name }</td>
						<td style="text-align: right;"><fmt:formatNumber value="${o[0].price }" pattern="0.00"/></td>
						
						<td>
							<input type="button" value="+" style="width:30px" class="add" flag="${o[0].id }"/>
							<input type="text" style="width:30px;text-align:center;<c:if test='${offset == 1}'>color:red;font-size:16px;font-weight:bolder</c:if>" class="inputcount" value="${o[1].gmsl }" flag="${o[0].id }"/>
							<input type="button" value="-" style="width:30px" class="reduce" flag="${o[0].id }"/>
							</td><td style="text-align:center"><input type="button" value=" 删 除 " style="" class="del" flag="${o[0].id }"/>
						</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<div class="ticket" id="ticket" style="width: 800px;font-weight: bolder;font-size: 10px;display: none;margin-right: auto;margin-left: auto">
			<table style="width:90%">
				<caption>【欢迎光临】</caption>
				<tr><td colspan="6" style="text-align: center;font-size: 20px;font-family: 黑体">便利店</td></tr>
				<tr><td colspan="3" style="text-align: left">店员：${sessionScope.yonghu.nickname }</td><td colspan="3" style="text-align: right">购买时间：<fmt:formatDate value="<%=new Date()%>" pattern="yyyy/MM/dd HH:mm"/></td></tr>
				<tr style="height: 2px"><td colspan="6"><div style="width: 100% ;height: 2px;background-color:black"><img src="../images/blackpoint.bmp" style="width: 100%;height: 2px;" /></div></td></tr>
				<tr><td style="width: 6%">序号</td><td colspan="2" style="text-align: center">商品名称</td><td style="width: 10%;text-align: right">数量</td><td style="width: 12%;text-align: right">单价</td><td style="width: 12%;text-align: right">金额</td></tr>
				<tr style="height: 2px"><td colspan="6"><div style="width: 100% ;height: 2px;background-color:black"><img src="../images/blackpoint.bmp" style="width: 100%;height: 2px;" /></div></td></tr>
				<c:set scope="page" var="offset" value="0" />
				<c:forEach items="${xiaoshouList}" var="o">
				<c:set scope="page" var="offset" value="${offset+1}" />
									<tr>
										<td style="text-align:center">
											${offset }
										</td>
										<td colspan="2" style="text-align: left">
											${o[0].name }
										</td>
										<td style="text-align: right">
											${o[1].gmsl }
										</td>
										<td style="text-align: right">
											<fmt:formatNumber pattern="0.00元" value="${o[0].price }"/>
										</td>
										<td style="text-align: right">
											<fmt:formatNumber pattern="0.00元" value="${o[0].price*o[1].gmsl }"/>
										</td>
										</tr>
				</c:forEach>
				<tr style="height: 2px"><td colspan="6"><div style="width: 100% ;height: 2px;background-color:black"><img src="../images/blackpoint.bmp" style="width: 100%;height: 2px;" /></div></td></tr>
				<tr><td colspan="2" style="text-align: left;width: 30%">实收：<span id="payinticket"></span></td><td colspan="2" style="width: 40%">找零：<span id="chargeinticket"></span></td><td colspan="2" style="text-align: right">合计：<fmt:formatNumber value="${sum_money }" pattern="0.00"/>元</td></tr>
				<tr style="height: 2px"><td colspan="6"><div style="width: 100% ;height: 2px;background-color:black"><img src="../images/blackpoint.bmp" style="width: 100%;height: 2px;" /></div></td></tr>
				<tr><td colspan="6">地址：天津市新技术产业园区华科三路1号华鼎科技创业中心2号楼1门210</td></tr>
				<tr><td colspan="2">电话：########</td><td colspan="2">QQ：########</td></tr>
			</table>
		</div>
	</body>
</html>
