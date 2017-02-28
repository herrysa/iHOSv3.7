<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#voucherDetailAssistForm").attr("action","saveVoucherDetailAssist?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#voucherDetailAssistForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="voucherDetailAssistForm" method="post"	action="saveVoucherDetailAssist?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="voucherDetailAssist.voucherDetailAssistId"/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetailAssist_abstractStr" key="voucherDetailAssist.abstractStr" name="voucherDetailAssist.abstractStr" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetailAssist_assist" key="voucherDetailAssist.assist" name="voucherDetailAssist.assist" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="voucherDetailAssist.assistType.id" list="assistTypeList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="voucherDetailAssist_assistValue" key="voucherDetailAssist.assistValue" name="voucherDetailAssist.assistValue" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetailAssist_money" key="voucherDetailAssist.money" name="voucherDetailAssist.money" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetailAssist_num" key="voucherDetailAssist.num" name="voucherDetailAssist.num" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="voucherDetailAssist.voucherDetail.id" list="voucherDetailList" listKey="id" listValue="id"></s:select> -->
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





