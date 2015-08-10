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
			#jinhuotable input,select{
				color:blue;
				width:98%;
			}
		</style>
		<script src="js/jquery/jquery-1.6.4.js" type="text/javascript"></script>
		<script src="js/jquery/jquery.niceTable.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$(".nicetable").niceTable();
			$(".ifnewthenunlock").attr("readonly","readonly");
			$("#tbl01_number").focus();
			$("#tbl01_number").keyup(function(e){
				if (e.which==13 && $.trim($("#tbl01_number").val())!=""){
					$.ajax({
						url:'getshangpinInfoAjax.do?tbl01_number='+$.trim($("#tbl01_number").val()),
						type:'post',
						success:function(msg){
							msg = $.trim(msg);
							$("#number").val($("#tbl01_number").val());
							if (msg=='new'){
								$(".ifnewthenunlock").removeAttr("readonly");
								$("#tbl01_id").val("");
								$("#name").val("");
								$("#price").val("");
								$("#kcsl").val("0");
								$("#spsm").val("");
								$("#tbl05_id").val("");
								$("#pic").val("");
								$("#money").val("");
								$("#jhsl").val("");
								$("#bz").val("");
								$("#name").focus();
							}else{
								$(".ifnewthenunlock").attr("readonly","readonly");
								var args = msg.split(",");
								$("#tbl01_id").val(args[0]);
								$("#name").val(args[1]);
								$("#price").val(args[2]);
								$("#kcsl").val(args[3]);
								$("#spsm").val(args[4]);
								$("#tbl05_id").val(args[5]);
								$("#pic").val(args[6]);
								$("#money").val(args[7]);
								$("#jhsl").val(args[8]);
								$("#bz").val(args[9]);
								$("#jhsl").focus();
							}
						}
					});
				}
			});
			$("#name").keyup(function(e){
				if (e.which==13){
					if ($(this).val()!="")
					$("#tbl05_id").focus();
				}
			});
			$("#tbl05_id").keyup(function(e){
				if (e.which==13){
					if ($(this).val()!="")
					$("#price").focus();
				}
			});
			$("#price").keyup(function(e){
				if (isNaN($(this).val())){
					$(this).val("");
				}
				if (e.which==13){
					if ($(this).val()!="")
					$("#spsm").focus();
				}
			});
			$("#kcsl").keyup(function(e){
				if (e.which==13){
					if ($(this).val()!="")
					$("#spsm").focus();
				}
			});
			$("#spsm").keyup(function(e){
				if (e.which==13){
					$("#jhsl").focus();
				}
			});
			$("#jhsl").keyup(function(e){
				if ($(this).val()!="-"&&isNaN($(this).val())||($(this).val().indexOf(".")!=-1)){
					$(this).val("");
				}
				if (e.which==13){
					if ($(this).val()!="")
					$("#money").focus();
				}
			});
			$("#money").keyup(function(e){
				if ($(this).val()!="-"&&isNaN($(this).val())){
					$(this).val("");
				}
				if (e.which==13){
					if ($(this).val()!="")
					$("#bz").focus();
				}
			});
			$("#bz").keyup(function(e){
				if (e.which==13){
					$("#rudan").click();
				}
			});
			$("#rudan").click(function(){
				if ($(".ifnewthenunlock").attr("readonly")!="readonly"){
					if ($("#name").val()==""){
						$("#name").focus();
						return;
					} else if ($("#name").val()==""){
						$("#name").focus();
						return;
					} else if ($("#tbl05_id").val()==""){
						$("#tbl05_id").focus();
						return;
					} else if ($("#price").val()==""){
						$("#price").focus();
						return;
					} else if ($("#kcsl").val()==""){
						$("#kcsl").focus();
						return;
					}
				}
				if ($("#jhsl").val()==""){
					$("#jhsl").focus();
					return;
				} else if ($("#money").val()==""){
					$("#money").focus();
					return;
				}
				$("#form1").submit();
			});
			$("#ruku").click(function(){
				if ("${empty jinhuoList}"=="true"){
					alert("进货单为空，不用提交。");
				}else{
					if (confirm("确认提交该进货单吗？")){
						$("#ruku").hide();
						window.location.href="jinhuo.do";
					}
				}
			});
		});
		function del(tbl01_id){
			if (confirm("确认删除该记录吗？")){
			window.location.href="jinhuodel.do?tbl01_id="+tbl01_id;
			}
		}
		</script>
	</head>
	<body style="background-color:transparent;overflow:auto;">
		<div id="jinhuoList" style="height:60%;overflow:auto" class="jinhuolist">
			<table border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin-right: auto;margin-left: auto" class="nicetable">
			<tr>
				<th style="width:120px">商品编码</th>
				<th style="width:250px">商品名称</th>
				<th style="width:100px">商品类别</th>
				<th style="width:80px">原库存数</th>
				<th style="width:80px">进货数量</th>
				<th style="width:80px">进货总价</th>
				<th style="width:200px">备注</th>
				<th style="width:50px">操作</th>
			</tr>
			<c:forEach items="${jinhuoList}" var="o">
				<tr>
				<td style="text-align:">${o[0].number }</td>
				<td style="text-align:">${o[0].name }</td>
				<td style="text-align:">${o[2].name }</td>
				<td style="text-align:">${o[0].kcsl }</td>
				<td style="text-align:">${o[1].jhsl }</td>
				<td style="text-align:"><fmt:formatNumber value="${o[1].money }" pattern="0.00"/></td>
				<td style="text-align:">${o[1].bz }</td>
				<td style="text-align:center"><input type="button" value="删除" onclick="javascript:del('${o[0].id }')"/></td>
			</tr>
			</c:forEach>
			</table>
		</div>
		<div id="jinhuo" style="height:40%;width:700px;margin:auto" >
		<div style="float:right">本次进货总额：<span style="color:red"><fmt:formatNumber pattern="0.00" value="${sum_money}"/></span></div>
		<div style="margin-right: auto;margin-left: auto;width:100%;text-align:center">商品编码<input type="text" id="tbl01_number" style="color:blue;font-size:14px"/></div>
		<form method="post" action="jinhuoview.do" id="form1">
			<table id="jinhuotable" border="0" cellspacing="0" cellpadding="0" style="border-collapse:collapse;margin-right: auto;margin-left: auto" class="jinhuotab">
				<tr>	
					<th style="width:80px;">商品名称</th>
					<td style="width:200px;">
					<input type="hidden" name="tbl01_id" id="tbl01_id"/>
					<input type="hidden" name="tbl01_number" id="number"/>
					<input class="ifnewthenunlock" type="text" name="name" id="name"/></td>
					<th style="width:80px;">商品类别</th>
					<td style="width:200px;"><select class="ifnewthenunlock" style="width:95%" id="tbl05_id" name="tbl05_id">
					<option></option>
					<c:forEach items="${leixingList}" var="leixing">
						<option value="${leixing.id }">${leixing.name }</option>
					</c:forEach>
				</select></td></tr>
				<tr>
					<th style="width:80px;">商品价格</th>
					<td style="width:200px;"><input class="ifnewthenunlock" type="text" name="price" id="price"/></td>
					<th style="width:80px;">原库存数</th>
					<td style="width:200px;"><input class="ifnewthenunlock" type="text" name="kcsl" id="kcsl"/></td>
				</tr>
				<tr>
					<th style="width:80px;">商品说明</th>
					<td colspan="3" style="width:200px;"><input class="ifnewthenunlock" type="text" name="spsm" id="spsm"/></td>
				</tr>
				<tr>
					<th style="width:80px;">进货数量</th>
					<td style="width:200px;"><input type="text" name="jhsl" id="jhsl"/></td>
					<th style="width:80px;">进货总价</th>
					<td style="width:200px;"><input type="text" name="money" id="money"/></td>
				</tr>
				<tr>
					<th style="width:80px;">商品备注</th>
					<td colspan="3"><input type="text" name="bz" id="bz"/></td>
				</tr>				
			</table>
		<br/>
			<div style="text-align:center;margin-top:5px;"><input type="button" value=" 入 单 " id="rudan" /><input type="button" value=" 入 库 " id="ruku"/><input type="button" value="进货记录" onclick="javascript:window.location.href='jinhuodan.do?pageRowSize=${param.pageRowSize }'"/></div>
			</form>
		</div>
	</body>
</html>
