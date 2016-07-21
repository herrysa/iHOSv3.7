<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bmModelProcessLogForm").attr("action","saveBmModelProcessLog?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bmModelProcessLogForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bmModelProcessLogForm" method="post"	action="saveBmModelProcessLog?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bmModelProcessLog.logId"/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_deptId" key="bmModelProcessLog.deptId" name="bmModelProcessLog.deptId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_deptName" key="bmModelProcessLog.deptName" name="bmModelProcessLog.deptName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_info" key="bmModelProcessLog.info" name="bmModelProcessLog.info" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_modelId" key="bmModelProcessLog.modelId" name="bmModelProcessLog.modelId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_operation" key="bmModelProcessLog.operation" name="bmModelProcessLog.operation" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_optTime" key="bmModelProcessLog.optTime" name="bmModelProcessLog.optTime" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_personId" key="bmModelProcessLog.personId" name="bmModelProcessLog.personId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_personName" key="bmModelProcessLog.personName" name="bmModelProcessLog.personName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_state" key="bmModelProcessLog.state" name="bmModelProcessLog.state" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_stepCode" key="bmModelProcessLog.stepCode" name="bmModelProcessLog.stepCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcessLog_updataId" key="bmModelProcessLog.updataId" name="bmModelProcessLog.updataId" cssClass="				
				
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





