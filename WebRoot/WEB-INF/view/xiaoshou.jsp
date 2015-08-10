<%@ page isELIgnored="false"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title>
超市管理系统 v1.0
</title>
<!-- stylesheets -->
<link rel="stylesheet" href="css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="css/slide.css" type="text/css"
	media="screen" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
<script src="js/slide.js" type="text/javascript"></script>
<script src="js/pagination.js" type="text/javascript"></script>
<script type="text/javascript">
			$(function(){
				var containterheight = document.documentElement.clientHeight-125;
				$("#container").height(containterheight+"px");
				$("body").show();
				$("#back").click(function(){
					window.parent.location.href="xiaoshou.do";
				});
				$("#print").click(function(){
					$(window.frames["contentiframe1"].document).find("#payinticket").html($("#shishou").val()+"");
					$(window.frames["contentiframe1"].document).find("#chargeinticket").html($("#zhaoling").html());
					var w = window.open("dispath.do?url=MyJsp.jsp");
					w.document.body.innerHTML = $(window.parent.frames["contentiframe1"].document).find("#ticket").html();
					w.print();
					w.close();
				});
				$("#shishou").keyup(function(e){
					var shishou = $(this).val();
					var sum_money = $("#zongxiaofei").text();
					if (shishou==""||isNaN(shishou)){
						$(this).val("");
						$("#zhaoling").text("");
						return;
					}
					var zhaoling = (parseFloat(shishou)-parseFloat(sum_money)).toFixed(2);
					if (zhaoling < 0){
						zhaoling = (0).toFixed(2);
					}
					$("#zhaoling").html(zhaoling + "");
				});
				$("#cardnumber").keyup(function(){ 
					if ($(this).val().length == 8){
						$.ajax({
							url:'getCardInfoAjax.do?cardnumber='+$(this).val(),
							type:'post',
							success:function(msg){
								msg = $.trim(msg);
								if (msg != "fail") {
									var args = msg.split(",");
									$("#card_number").html($("#cardnumber").val());
									$("#card_status").html(args[0]);
									$("#card_dqjf").html(args[1]);
									$("#card_ljjf").html(args[2]);
									$("#card_zjxfrq").html(args[3]);
									$("#card_xfcs").html(args[4]);
									$("#chakanbutton").attr("target",args[5]);
									$("#chakanbutton").show();
									$("#card").val($("#cardnumber").val());
								} else {
									$("#cardnumber").val("");
									alert("查询会员卡失败！");
								}
							}
						});
					} else {
						$("#card").val("");
						$("#card_number").html("/");
						$("#card_status").html("");
						$("#card_dqjf").html("");
						$("#card_ljjf").html("");
						$("#card_zjxfrq").html("");
						$("#card_xfcs").html("");
						$("#chakanbutton").hide();
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
			});
			function quit(){
				$("#container").hide(1000,function(){
					window.location.href='index.do';
				});
			}
			function chakan(){
				window.open("huiyuan.do?tbl03_id="+$("#chakanbutton").attr("target"),"huiyuan");
			}
		</script>
</head>
<body style="overflow-x: hidden; width: 100%; height: 100%;display:none;">
<div id="toppanel">
<!-- The tab on top -->
<div class="tab">
<div style="position: absolute; left: 0px; top: 0;">
<img src="images/yijiarenlogo.png" border="0"/>
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
<a id="open" class="open" href="javascript:quit()">
退出系统
</a>
</li>
<li class="right">
&nbsp;
</li>
				</ul>
			</div>
		</div>
		
		<div id="container"	style="position: fixed; top: 70px; width: 100%;overflow:auto">
				
				<iframe src="buy.do?jiezhang=${param.jiezhang }" name="contentiframe1" id="contentiframe1" allowTransparency="true" frameborder="0" height="200" style="border-width:0px;width:98%;overflow:auto"></iframe>
				
				<iframe src="good.do" name="contentiframe" id="contentiframe" allowTransparency="true" frameborder="0" style="border-width:0px;height:300px;width:98%;overflow:auto"></iframe>
				
				
				<div id="payinfo" style="height: 55%;text-align: center;overflow:auto;display:none" class="payinfo">
					<br /><table border="0" cellspacing="0" cellpadding="0"  style="margin-right: auto;margin-left: auto;border-collapse:collapse;">
					<tr>
						<th style="text-align:center;width:100px;">总消费</th>
						<th style="text-align:center;width:100px">实&nbsp;&nbsp;&nbsp;&nbsp;收</th>
						<th style="text-align:center;width:100px">找&nbsp;&nbsp;&nbsp;&nbsp;零</th>
						<th style="text-align:center;width:100px">会员卡</th>
					</tr>
						<tr><td style="width:100px;text-align:center;color:red;font-size:16px;font-weight:bolder"><span id="zongxiaofei"></span><span style="color:black"></span></td>
						<td style="text-align:center;font-size:14px"><input type="text" id="shishou" style="width:85px;border:none;text-align:center;font-size:16px"/></td>
						<td style="text-align:center;font-size:16px;font-weight:bolder;color:red;" id="zhaoling"></td>
						<td style="text-align:center;height:30px"><input id="cardnumber" type="text" style="font-size:14px;width:85px;border:none"/></td></tr>
					</table><br />
					<table border="0" cellspacing="0" cellpadding="0" style="margin-right: auto;margin-left: auto;border-collapse:collapse;">
									<tr>
										<th style="width:120px">
											卡号
										</th>
										<th style="width:100px">
											状态
										</th>
										<th style="width:120px">
											当前积分
										</th>
										<th style="width:120px">
											累计积分
										</th>
										<th style="width:180px">
											最近消费日期
										</th>
										<th style="width:120px">
											消费次数
										</th>
										<th style="width:80px">
										操作
										</th>
									</tr>
									<tr style="height:30px">
										<td id="card_number">
										/
										</td>
										<td id="card_status">
										</td>
										<td style="text-align: right;color: red;font-weight: bolder;" id="card_dqjf">
										</td>
										<td style="text-align: right;color: red;font-weight: bolder;" id="card_ljjf">
										</td>
										<td style="text-align: center" id="card_zjxfrq">
										</td>
										<td style="text-align: center" id="card_xfcs">
										</td>
										<td style="text-align: center"><input id="chakanbutton" style="display:none" type="button" onclick="chakan()" value="查看"/></td>
									</tr>
								</table><br/>
								<form method="post" action="jiezhang.do" id="jiezhangform">
								<input type="hidden" id="card" name="card"/>
								<span><input id="print" type="button" value="打  印" style="width:60px"/>&nbsp;&nbsp;<input id="jiezhang" type="button" value="下一位" style="width:60px"/>&nbsp;&nbsp;<input type="button" value=" 继续消费 " id="back"/></span>
								</form>
				</div>
		</div>
		<div id="bottom"
			style="width: 100%; position: fixed; bottom: 0px; text-align: center;margin-bottom:10px;">
			<hr size="5" style="color:#666;" />
			<center>
				 版权所有，违者必究&nbsp;&nbsp;	电话：########
			</center>
		</div>
	</body>
</html>
