<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#voucherFromForm").attr("action","saveVoucherFrom?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#voucherFromForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="voucherFromForm" method="post"	action="saveVoucherFrom?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="voucherFrom.voucherFromCode" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="voucherFrom_voucherFromName" key="voucherFrom.voucherFromName" name="voucherFrom.voucherFromName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherFrom_voucherFromShort" key="voucherFrom.voucherFromShort" name="voucherFrom.voucherFromShort" cssClass="				
				
				       "/>
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





