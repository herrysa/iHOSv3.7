<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#modelStatusForm").attr("action","saveModelStatus?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#modelStatusForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="modelStatusForm" method="post"	action="saveModelStatus?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="modelStatus.id"/>
				</div>
				<div class="unit">
				<s:textfield id="modelStatus_checkperiod" key="modelStatus.checkperiod" name="modelStatus.checkperiod" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="modelStatus_modelId" key="modelStatus.modelId" name="modelStatus.modelId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="modelStatus_status" key="modelStatus.status" name="modelStatus.status" cssClass="				
				
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





