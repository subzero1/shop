<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<title>超市管理系统 v1.0</title>
		<!-- stylesheets -->
		<link rel="stylesheet" href="css/style.css"/>
		<link rel="stylesheet" href="css/themes/base/jquery.ui.all.css"/>
		<link rel="stylesheet" href="css/demos.css" />
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.core.js"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script src="js/pagination.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.ui.datepicker.js"></script>
		<script src="js/highcharts.js"></script>
		<script src="js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
		<script type="text/javascript">
			$(function(){
			$(".orderable").click(function(){
				var oldorderby = $("#orderby").val();
				$("#orderby").val($(this).attr("orderby"));
				if (oldorderby!=$("#orderby").val()){
					$("#sequence").val("desc");
				} else {
					$("#sequence").val($("#sequence").val()=="desc"?"asc":"desc");
				}
				$("#searchForm").submit();
			});
			$.each($("th"), function () {
		if ("${param.orderby}" === $(this).attr("orderby")) {
			$(this).css("background-color", "orange");
			if ($("#sequence").val() === "desc") {
				$(this).html($(this).html() + "<span style='font-size:10px'>\u25bd</span>");
			} else {
				$(this).html($(this).html() + "<span style='font-size:10px'>\u25b3</span>");
			}
		}
	});
	var now = new Date();
	if ($("#time2").val() >= (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate())) {
			$(".datepickerbutton[target^='next']").attr("disabled","disabled");
		}
	$(".datepickerbutton").click(function () {
		
		if ($("#cxlx").val().indexOf("kc")==-1){
			if ($(this).attr("target").indexOf("current")==-1&&($(this).attr("target").indexOf("day")!=-1||$(this).attr("target").indexOf("week")!=-1)){
				$("#clickbutton").val($(this).attr("id"));
			}
		var date0 = $.trim($("#" + $(this).parent().attr("datepicker1")).val()) === "" ? (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate()) : $("#" + $(this).parent().attr("datepicker1")).val();
		var date1 = (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate());
		var date2 = $.trim($("#" + $(this).parent().attr("datepicker2")).val()) === "" ? (now.getFullYear() + "-" + (now.getMonth() < 9 ? "0" : "") + (now.getMonth() + 1) + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate()) : $("#" + $(this).parent().attr("datepicker2")).val();
		var target = $(this).attr("target");
		var prefix = target.split(" ")[0];
		var word = target.split(" ")[1];
		var date = "";
		var flag = 0;
		if (prefix === "next") {
			date = date2;
			flag = 1;
		} else if (prefix === "current") {
			date = date1;
		} else if (prefix === "last") {
			date = date0;
			flag = -1;
		} else {
			alert(prefix);
			return false;
		}
		if (word === "year") {
			$("#" + $(this).parent().attr("datepicker1")).val(parseInt(date.split("-")[0]) + flag + "-01-01");
			$("#" + $(this).parent().attr("datepicker2")).val(parseInt(date.split("-")[0]) + flag + "-12-31");
		} else if (word === "season") {
			var year = parseInt(date.split("-")[0]);
			var season = parseInt((date.split("-")[1] - 1) / 3) + 1;
			season += flag;
			if (season > 4) {
				year = year + 1;
				season = 1;
			} else if (season < 1) {
				year = year - 1;
				season = 4;
			}
			var end = 31;
			if (season === 2 || season === 3) {
				end = 30;
			}
			var startmonth = ((season * 3 - 2) < 10 ? "0" : "") + (season * 3 - 2);
			var endmonth = (season * 3 < 10 ? "0" : "") + season * 3;
			$("#" + $(this).parent().attr("datepicker1")).val(year + "-" + startmonth + "-01");
			$("#" + $(this).parent().attr("datepicker2")).val(year + "-" + endmonth + "-"+end);
		} else if (word === "month") {
			
			var year = date.split("-")[0];
			var month = date.split("-")[1];
			month -= -flag;
			if (month > 12) {
				year = parseInt(year) + 1;
				month = parseInt(month) - 12;
			} else if (month < 1) {
				year = parseInt(year) - 1;
				month = parseInt(month) + 12;
			}
			var end = 30;
			if (month === 1 || month === 3 || month === 5 || month === 7 || month === 8 || month === 10 || month === 12) {
				end = 31;
			} else if (month === 2) {
				end = 28;
				if (year % 4 === 0)	{
					end = 29;
					if (year % 100 === 0){
						end = 28;
						if (year % 400 === 0){
							end = 29;
						}
					}
				}		
			}
			month = (month < 10 ? "0" : "") + month;
			$("#" + $(this).parent().attr("datepicker1")).val(year + "-" + month + "-01");
			$("#" + $(this).parent().attr("datepicker2")).val(year + "-" + month + "-"+end);
		} else if (word === "week") {
			var datetmp1 = new Date(date.split("-")[0],date.split("-")[1]-1,date.split("-")[2]);
			datetmp1 = new Date(datetmp1.getTime() - 86400000 * ((datetmp1.getDay() - 1) < 0 ? datetmp1.getDay() + 6 : datetmp1.getDay() - 1));
			datetmp1 = new Date(datetmp1.getTime() + flag * 86400000 * 7);
			var datetmp2 = new Date(datetmp1.getTime() + 86400000 * 6);
			$("#" + $(this).parent().attr("datepicker1")).val(datetmp1.getFullYear() + "-" + (datetmp1.getMonth() < 9 ? "0" : "") + (datetmp1.getMonth() + 1) + "-" + (datetmp1.getDate() < 10 ? "0" : "") + datetmp1.getDate());
			$("#" + $(this).parent().attr("datepicker2")).val(datetmp2.getFullYear() + "-" + (datetmp2.getMonth() < 9 ? "0" : "") + (datetmp2.getMonth() + 1) + "-" + (datetmp2.getDate() < 10 ? "0" : "") + datetmp2.getDate());
		} else if (word === "day") {
			var datetmp = new Date(date.split("-")[0],date.split("-")[1]-1,date.split("-")[2]);
			datetmp = new Date(datetmp.getTime() + flag * 86400000);
			$("#" + $(this).parent().attr("datepicker1")).val(datetmp.getFullYear() + "-" + (datetmp.getMonth() < 9 ? "0" : "") + (datetmp.getMonth() + 1) + "-" + (datetmp.getDate() < 10 ? "0" : "") + datetmp.getDate());
			$("#" + $(this).parent().attr("datepicker2")).val(datetmp.getFullYear() + "-" + (datetmp.getMonth() < 9 ? "0" : "") + (datetmp.getMonth() + 1) + "-" + (datetmp.getDate() < 10 ? "0" : "") + datetmp.getDate());
		} else {
			alert(word);
			return false;
		}
		$("#" + $(this).parent().attr("clickbutton")).click();
		}
	});
if ("${none}"=="yes"){
		$("#${param.clickbutton}").click();
	}
			
			$("#contentdiv").height((document.documentElement.clientHeight-130)+"px");
			$(".nicetable").niceTable({
				hovercolor:"#A4D3EE",
				covertd:true
			});
			$(".nicetable").show();
			var nopieoptions = {
					chart: {
						renderTo: 'container',
						defaultSeriesType: '',
						marginRight: 130,
						marginBottom: 170
					},
					title: {
						text: '',
						x: -20 //center
					},
					subtitle: {
						text: '',
						x: -20
					},
					xAxis: {
						categories: [],
						min:0,
						labels:{
						x:0,
						y:90,
						rotation: -45,
						style: {
								 font: '12px 宋体'
							}
						}
					},
					yAxis: {
						title: {
							text: ''
						},
						plotLines: [{
							value: 0,
							width: 1,
							color: '#808080'
						}]
					},
					tooltip: {
						formatter: function() {
				                return '<b>'+ this.series.name +'：</b><br/>'+
								this.x +': '+ parseFloat(parseInt(parseFloat(this.y)*100)/100);
						}
					},
					legend: {
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: -10,
						y: 100,
						borderWidth: 0
					},
					series: [{},{},{},{}]
				};  
		var pieoptions={
					chart: {
						renderTo: 'container',
						plotBackgroundColor: null,
						plotBorderWidth: 0,
						plotShadow: false,
						marginTop: 70,
						marginBottom: 60				
					},
					title: {
						text: ''
					},
					subtitle:{
						text:''
					},
					tooltip: {
						formatter: function() {
							return '<b>'+this.point.name +'：</b><br/>'+ 
								 this.series.name +': '+ this.y;
						}
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								color: '#000000',
								connectorColor: '#000000',
								style: {
								 font: '12px 宋体, sans-serif'
								},
								formatter: function() {
									return '<b>'+ this.point.name +'：</b>: '+ this.y;
								}
							}
						}
					},
				    series: [{
						type: 'pie',
						name: '',
						innerSize: '0%',
						size: '100%',
						data: [],
						dataLabels: {
							enabled: true,
							formatter: function() {
							return '<b>'+this.point.name +'：</b><br/>';
						}
						}
					}]
				};
				var chartstyle = '${tbl11.chartstyle}';
				var name = '${names}'.split(",");
				var x = '${xstr}'.split(",");
				var datas = '${data}'.split("&");
			if (chartstyle == 'pie'){
				pieoptions.title.text = '${tbl11.title}';
				for(var i=0;i<datas.length;i++){
					pieoptions.series[i].name = name[i];
					var tmp = [];
					var d =	datas[i].split(",");
					for(var j=0;j<d.length;j++){
						var t = parseFloat(d[j]).toFixed(2);
						var sontmp = {name:x[j],y:t};
						tmp.push(sontmp);
					}
					pieoptions.series[i].data = tmp;
				}
				chart = new Highcharts.Chart(pieoptions);
			}else if (chartstyle!='none' && chartstyle!=''){
				var xstr = [];
				for(var j=0;j<datas.length;j++){
					var tmp = [];
					var d =	datas[j].split(",");
					for(var i=0;i<d.length;i++){
						tmp.push(parseFloat(d[i]));
					}
					nopieoptions.series[j].data = tmp;
					nopieoptions.series[j].name = name[j];
					}
				for (var i=0;i<x.length;i++){
					xstr.push([x[i]]);
				}
				nopieoptions.title.text = '${tbl11.title}';
				nopieoptions.chart.defaultSeriesType = chartstyle;
				nopieoptions.xAxis.categories = xstr;
				nopieoptions.xAxis.labels.rotation = '-90';
				chart = new Highcharts.Chart(nopieoptions);
				}
			$(".datepicker").datepicker({
						changeMonth: true,
						changeYear: true,
						minDate:new Date(2011, 1 - 1, 1)
						
				},
				"option", "minDate", new Date(2011, 1 - 1, 1)
				);
			$("#cxlx").change(function(){
				if ('${cxlx.name==xsmx}'=='true') {
					$("#pageNum").val("1");
				}
				var cxlx = $(this).val();
				if (cxlx=="xsmx"||cxlx=="xslbhz"||cxlx=="xssphz"){
					$("#currentday").click();
				} else if (cxlx=="jfdhmx"||cxlx=="dhsphz"||cxlx=="jharhz"||cxlx=="xsarhz"||cxlx=="jhmx"||cxlx=="jharhz"||cxlx=="jhlbhz"){
					$("#currentmonth").click();
				} else if (cxlx=="xsayhz"||cxlx=="jhayhz"){
					$("#currentyear").click();
				} else if (cxlx=="xsanhz"||cxlx=="jhanhz"){
					$("#time1").val("2011-01-01");
					$("#time2").val("");
					$("#searchForm").submit();
				} else {
					$("#searchForm").submit();
				}
			});
			$("td").each(function(){
			var var0 = $(this).attr("flag");
			if (var0 != undefined){
				if (var0.indexOf("[yuzhi]")!=-1){
					var var1 = $.trim($(this).html());
					var var2 = $.trim($(this).next().html());
					if (parseInt(var1)<=parseInt(var2)){
						$(this).css("color","red");
					}
				}
				}
			});
			$("#toexcel").click(function(){
				var action = $("#searchForm").attr("action");
				$("#searchForm").attr("action","tongji.do?toExcel=yes");
				$("#searchForm").submit();
				$("#searchForm").attr("action",action);
			});
			$(".edit").change(function(){
				$(this).parent().parent().find("#change").val("yes");
			});
			$(".edit").keyup(function(){
				$(this).parent().parent().find("#change").val("yes");
			});
			$("#shangpinbulkeditbutton").click(function(){
				$("#bulkupdateshangpin").submit();
			});
			
			$(".cxlxbutton").click(function(){
				$("#cxlx > option[value="+$(this).attr("flag")+"]").attr("selected","selected");
				$("#cxlx").change();
			});
			});
			function xsmxdel(tbl04_id){
				if (confirm("确实要删除该销售记录吗？")){
					$("#tbl04_id").val(tbl04_id);
					$("#cxlx").val("xsmx");
					$("time1").val('${param.time1}');
					$("time2").val('${param.time2}');
					$("tbl05_id").val('param.${tbl05_id}');
					$("#searchForm").submit();
				}
			}
			function kcxg(tbl01_id){
				window.location.href="jcxxwh.do?tbl01_id="+tbl01_id;
			}
		</script>
		<style>
		.edit{
			color:blue
		}
		</style>
	</head>
	<body style="background-color:transparent;overflow:hidden;">
	<form id="searchForm" method="post" action="tongji.do" style="text-align:center;margin:10px">
	<input type="hidden" name="clickbutton" id="clickbutton"/>
	<input type="hidden" id="pageNum" name="pageNum" value="${pageNum }"/>
	<input type="hidden" id="totalRows" name="totalRows" value="${totalRows }"/>
	<input type="hidden" id="totalPages" name="totalPages" value="${totalPages }"/>
	<input type="hidden" id="pageRowSize" name="pageRowSize" value="${pageRowSize }"/>
	<input type="hidden" id="tbl04_id" name="tbl04_id"/>
							<select name="cxlx" id="cxlx">
								<c:forEach items="${cxlx_list}" var="cxlx">
									<option value="${cxlx.name }"
										<c:if test="${cxlx.name==param.cxlx }">selected="selected"</c:if>>
										${cxlx.text }
									</option>
								</c:forEach>
							</select>
							<input class="datepicker" type="text" readonly="readonly" id="time1" name="time1" value="${time1 }" style="width:80px;text-align:center"/>至 
							<input class="datepicker" type="text" readonly="readonly" id="time2" name="time2" value="${time2 }" style="width:80px;text-align:center"/>&nbsp;&nbsp;
							<select name="leixing" id="leixing">
							<option></option>
							<c:forEach items="${leixingList}" var="leixing">
							<option value="${leixing.id }" <c:if test="${param.leixing==leixing.id }">selected="selected"</c:if>>${leixing.name }</option>
							</c:forEach>
							</select>&nbsp;&nbsp;<span id="spmc">商品名：<input type="text" style="width:150px" value="${param.spmc }" name="spmc"/>&nbsp;&nbsp;</span>
							<input id="querysubmit" type="submit" value=" 查 询 "/><input type="button" value="销售明细导EXCEL" id="toexcel"/><br/>
							<div style="height:10px"></div>
							<div>
								<c:forEach items="${cxlx_list}" var="cxlx">
							<input type="button" value="${cxlx.text }" class="cxlxbutton" flag="${cxlx.name }"/>
							</c:forEach>
							</div>
							<div style="text-align:center" class="datepickerhelp" target="year season month week day" clickbutton="querysubmit" datepicker2="time2" datepicker1="time1">
							<input id="lastyear" style="padding:0;" type="button" value=" 上一年 " class="datepickerbutton" target="last year"/>&nbsp;<input id="currentyear" style="padding:0;" type="button" value=" 本  年 " class="datepickerbutton" target="current year"/>&nbsp;<input id="nextyear" style="padding:0;" type="button" value=" 下一年 " class="datepickerbutton" target="next year"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="lastseason" style="padding:0;" type="button" value=" 上季度 " class="datepickerbutton" target="last season"/>&nbsp;<input id="currentseason" style="padding:0;" type="button" value=" 本季度 " class="datepickerbutton" target="current season"/>&nbsp;<input id="nextseason" style="padding:0;" type="button" value=" 下季度 " class="datepickerbutton" target="next season"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="lastmonth" style="padding:0;" type="button" value=" 上一月 " class="datepickerbutton" target="last month"/>&nbsp;<input id="currentmonth" style="padding:0;" type="button" value=" 本  月 " class="datepickerbutton" target="current month"/>&nbsp;<input id="nextmonth" style="padding:0;" type="button" value=" 下一月 " class="datepickerbutton" target="next month"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="lastweek" style="padding:0;" type="button" value=" 上一周 " class="datepickerbutton" target="last week"/>&nbsp;<input id="currentweek" style="padding:0;" type="button" value=" 本  周 " class="datepickerbutton" target="current week"/>&nbsp;<input id="nextweek" style="padding:0;" type="button" value=" 下一周 " class="datepickerbutton" target="next week"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="lastday" style="padding:0;" type="button" value=" 上一天 " class="datepickerbutton" target="last day"/>&nbsp;<input id="currentday" style="padding:0;" type="button" value=" 今  天 " class="datepickerbutton" target="current day"/>&nbsp;<input id="nextday" style="padding:0;" type="button" value=" 下一天 " class="datepickerbutton" target="next day"/>
							</div>
							<input type="hidden" name="orderby" id="orderby" value="${param.orderby }"/>
							<input type="hidden" name="sequence" id="sequence" value="${param.sequence }"/>
	</form>
	<c:if test="${empty param.cxlx||param.cxlx=='xsmx'||param.cxlx=='xsarhz'||param.cxlx=='xsanhz'||param.cxlx=='xsayhz'||param.cxlx=='xssphz'||param.cxlx=='xslbhz'}">
	<div style="width:100%;text-align:center">
					<label></label><font  style="font-size:12px"> <b>总额：</b></font><input value="<fmt:formatNumber value='${xse }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id='allsum' />&nbsp;&nbsp;
					<label></label><font  style="font-size:12px"> <b>卡销售：</b></font><input value="<fmt:formatNumber value='${kxs }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id='cardsum' />&nbsp;&nbsp;
					<label ></label><font style="font-size:12px"> <b>成本：</b></font><input value="<fmt:formatNumber value='${cb }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" id="chengben" type="text" readonly="readonly"/>&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>利润：</b></font><input value="<fmt:formatNumber value='${lr }' pattern='0.00'/>" style="width: 70px;font-size:12px;color: red;text-align: right;font-weight: bold" id="lirun" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>利润率：</b></font><input value="<fmt:formatNumber value='${lrl }' pattern='0.00%'/>" style="width: 50px;font-size:12px;color: red;text-align: right;font-weight: bold" id="lirunlv" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>数量：</b></font><input value="${xssl }" style="width: 40px;font-size:12px;color:red;text-align: right;font-weight: bold" id="allnum" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>人数：</b></font><input value="${ggrs }" style="width: 40px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id="persons" />
					<label ></label><font  style="font-size:12px"> <b>人均：</b></font><input value="<fmt:formatNumber value='${ggrs!=0 ? (xse/ggrs) : 0 }' pattern='0.00'/>" style="width: 40px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id="persons" />
					</div><div style="height:10px"></div>
					<c:if test="${(param.cxlx=='xsarhz'||param.cxlx=='xsanhz'||param.cxlx=='xsayhz'||param.cxlx=='xssphz'||param.cxlx=='xslbhz')&&fn:length(cxtj_list)!=0}">
	<div style="width:100%;text-align:center">
					<label></label><font  style="font-size:12px"> <b>均额：</b></font><input value="<fmt:formatNumber value='${xse/fn:length(cxtj_list) }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id='allsum' />&nbsp;&nbsp;
					<label></label><font  style="font-size:12px"> <b>卡均售：</b></font><input value="<fmt:formatNumber value='${kxs/fn:length(cxtj_list) }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id='cardsum' />&nbsp;&nbsp;
					<label ></label><font style="font-size:12px"> <b>均成本：</b></font><input value="<fmt:formatNumber value='${cb/fn:length(cxtj_list) }' pattern='0.00'/>" style="width: 70px;font-size:12px;color:red;text-align: right;font-weight: bold" id="chengben" type="text" readonly="readonly"/>&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>均利润：</b></font><input value="<fmt:formatNumber value='${lr/fn:length(cxtj_list) }' pattern='0.00'/>" style="width: 70px;font-size:12px;color: red;text-align: right;font-weight: bold" id="lirun" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>均量：</b></font><input value="${xssl/fn:length(cxtj_list) }" style="width: 40px;font-size:12px;color:red;text-align: right;font-weight: bold" id="allnum" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label ></label><font  style="font-size:12px"> <b>均人数：</b></font><input value="${ggrs/fn:length(cxtj_list) }" style="width: 40px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" id="persons" />
					</div>
					<div style="height:10px"></div></c:if>
	</c:if>
	<c:if test="${param.cxlx=='jhmx'||param.cxlx=='jharhz'||param.cxlx=='jhanhz'||param.cxlx=='jhayhz'||param.cxlx=='jhsphz'||param.cxlx=='jhlbhz'}">
	<div style="width:100%;text-align:center">
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>进货总额：</b></font><input value="<fmt:formatNumber value='${jhze }' pattern='0.00'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />
					</div>
					<div style="height:10px"></div>
	</c:if>
	<c:if test="${param.cxlx=='kc'}">
	<div style="width:100%;text-align:center">
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>商品总量：</b></font><input value="<fmt:formatNumber value='${spzl }' pattern='0'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>总金额：</b></font><input value="<fmt:formatNumber value='${zje }' pattern='0.00'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>总成本：</b></font><input value="<fmt:formatNumber value='${zcb }' pattern='0.00'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />
					<input type="button" id="shangpinbulkeditbutton" value=" 修 改 "/>
					</div>
					<div style="height:10px"></div>
	</c:if>
	<c:if test="${param.cxlx=='jfdhmx'||param.cxlx=='dhsphz'}">
	<div style="width:100%;text-align:center">
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>兑换总积分：</b></font><input value="<fmt:formatNumber value='${dhjf }' pattern='0.00'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />&nbsp;&nbsp;
					<label></label>&nbsp;&nbsp;<font  style="font-size:12px"> <b>兑换总成本：</b></font><input value="<fmt:formatNumber value='${dhcb }' pattern='0.00'/>" style="width: 80px;font-size:12px;color:red;text-align: right;font-weight: bold" type="text" readonly="readonly" />
					<c:if test="${param.cxlx=='jfdhmx'}"><br/><br/>${gspdhze }</c:if>
					</div>
					<div style="height:10px"></div>
	</c:if>
		<div id="contentdiv" style="width:100%;overflow:auto;position:relative;margin: auto">
		<form action="bulkupdateshangpin.do" id="bulkupdateshangpin" method="post">
		<input type="hidden" name="cxlx" value="${param.cxlx }"/>
		<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin-right: auto;margin-left: auto;overflow:auto" class="goodtable nicetable">
						<!-- 遍历列名 -->
							<tr>
							<th style="width:30px"></th>
								<c:forEach items="${cxtj_titleList}" var="title">
									<th class="orderable" style="width:${title.width }px;display:${title.display }" orderby="${title.key }">${title.title }</th>
								</c:forEach>
								<c:if test="${empty param.cxlx || param.cxlx=='xsmx'||param.cxlx=='kc'||param.cxlx=='lkc'}">
										<th style="width:45px">操作</th>
									</c:if>
							</tr>
							<c:set var="offset" value="0"/>
							<!-- 遍历行 -->
							<c:forEach items="${cxtj_list}" var="cxtj">
							<c:set var="offset" value="${offset+1}"/>
							<input type="hidden" name="change" id="change" value="no"/>
								<tr>
								<td style="text-align: center">${offset }</td>
									<!-- 根据列头 遍历每个单元格-->
									<c:forEach items="${cxtj_titleList}" var="title">
										<td flag="${title.remark }" style="${title.css }text-align: ${title.align };display:${title.display }">
										<c:choose>
										<c:when test="${(param.cxlx=='kc'||param.cxlx=='lkc') && title.title=='类别'}">
											<select name="tbl05_id" style="width:100%;background-color:transparent" class="edit">
												<c:forEach items="${leixingList}" var="leixing">
													<option value="${leixing.id }" <c:if test="${leixing.name==cxtj[title.key]}">selected="selected"</c:if>>${leixing.name }</option>
												</c:forEach>
											</select>
										</c:when>
										<c:when test="${(param.cxlx=='kc'||param.cxlx=='lkc')&&fn:indexOf(title.remark,'[edit]')!=-1&&title.pattern=='price'}">
											<input class="edit" type="text" style="width:100%;background-color:transparent;text-align: right" name="price" value="<fmt:formatNumber pattern="0.00" value="${cxtj[title.key] }"/>" />
										</c:when>
										<c:when test="${(param.cxlx=='kc'||param.cxlx=='lkc')&&fn:indexOf(title.remark,'[edit]')!=-1}">
											<input class="edit" type="text" style="width:100%;background-color:transparent" name="name" value="${cxtj[title.key]}" />
										</c:when>
										<c:when test="${title.pattern=='0.00%'}"><fmt:formatNumber pattern="0.00%" value="${cxtj[title.key] }"/></c:when>
										<c:when test="${title.pattern=='price'}"><fmt:formatNumber pattern="0.00" value="${cxtj[title.key] }"/></c:when>
										<c:when test="${not empty title.pattern && title.pattern!='price' && title.pattern!='0.00%'}"><fmt:formatDate pattern="${title.pattern}" value="${cxtj[title.key] }"/></c:when>
										<c:when test="${title.key=='shangpin.id'}"><input type="hidden" name="id" value="${cxtj[title.key] }"/></c:when>
										<c:otherwise>${cxtj[title.key] }</c:otherwise>
										</c:choose>
										</td>
									</c:forEach>
									<c:if test="${empty param.cxlx || param.cxlx=='xsmx'}">
										<td style="font-size:12px;"><input type="button" value="删" onclick="javascript:xsmxdel('${cxtj['xiaoshou.id'] }')"/></td>
									</c:if>
									<c:if test="${param.cxlx=='kc' || param.cxlx=='lkc'}">
										<td style="font-size:12px;"><input type="button" value="编辑" onclick="javascript:kcxg('${cxtj['shangpin.id'] }')"/></td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
						</form>
						<br/>
						<div id="container" style="width: 800px; height: 0px; margin: 0 auto"></div>
		</div>
		<c:if test="${pageRowSize!=-1}">
		<div id="paginationDiv" style=position:absolute;bottom:0px;width:100%;display:none;<c:if test="${param.cxlx=='xsmx' || empty param.cxlx }">display:block</c:if>>
			<table style="margin-right: auto;margin-left: auto">
			<tr>
						<td>
							<c:if test="${not empty pageNum && pageNum!=1}">
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
			</table>
		</div>
		</c:if>
	</body>
</html>
