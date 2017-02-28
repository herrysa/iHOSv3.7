<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#projUseForm").attr("action","saveProjUse?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#projUseForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="projUseForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="projUse.useCode"/>
				</div>
				<div class="unit">
				<s:checkbox id="projUse_disabled" key="projUse.disabled" name="projUse.disabled" />
				</div>
				<div class="unit">
				<s:textfield id="projUse_useName" key="projUse.useName" name="projUse.useName" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





