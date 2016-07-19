<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#businessProcessForm").attr("action","saveBusinessProcess?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessProcessForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessProcessForm" method="post"	action="saveBusinessProcess?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="businessProcess.processCode" cssClass="required" notrepeat='流程编码已存在' validatorParam="from BusinessProcess entity where entity.processCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="businessProcess.processCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="businessProcess_processName" key="businessProcess.processName" name="businessProcess.processName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcess_processType" key="businessProcess.processType" name="businessProcess.processType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcess_businessTable" key="businessProcess.businessTable" name="businessProcess.businessTable" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcess_remark" key="businessProcess.remark" name="businessProcess.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcess_stateColumn" key="businessProcess.stateColumn" name="businessProcess.stateColumn" cssClass="				
				
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





