<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script>
jQuery(document).ready(function() {
	jQuery("#periodList").find("option").each(function(){
		jQuery(this).last().attr("selected","true");
	});
	jQuery('#savelink').click(function() {
		var cp="${currentPeriod }";
		var sp=jQuery("#periodList").val();
		jQuery("#inheritanceAllotItemForm").attr("action","inheritanceAlloItem?popup=true&taskName=sp_allotItemInh&proArgsStr="+cp+","+sp+"&navTabId="+'${navTabId}');
		jQuery("#inheritanceAllotItemForm").submit();
	});
});
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="inheritanceAllotItemForm" method="post"
			action="saveChargeItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<label style="font-weight: bold;">根据历史数据生成</label><br><br>
				<hr>
				<div class="unit">
					<label>历史数据核算期间：</label>
					<s:select id="periodList" list="periods" listKey="periodCode" listValue="periodCode"></s:select>
					<label>新核算期间：</label><input type="text" id="cp" readonly="true" value="${currentPeriod }" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink">提交</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>