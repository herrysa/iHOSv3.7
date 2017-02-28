<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#formDesignerForm").attr("action","saveFormDesigner?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#formDesignerForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="formDesignerForm" method="post"	action="saveFormDesigner?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="formDesigner.formId" cssClass="required"/>
				</div>
				<div class="unit">
				<s:textfield id="formDesigner_formName" key="formDesigner.formName" name="formDesigner.formName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="formDesigner_bdInfoId" key="formDesigner.bdInfoId" name="formDesigner.bdInfoId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="formDesigner_detailBdinfoId" key="formDesigner.detailBdinfoId" name="formDesigner.detailBdinfoId" cssClass="				
				
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





