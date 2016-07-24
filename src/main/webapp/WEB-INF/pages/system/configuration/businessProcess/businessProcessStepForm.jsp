<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#businessProcessStepForm").attr("action","saveBusinessProcessStep?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessProcessStepForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessProcessStepForm" method="post"	action="saveBusinessProcessStep?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="businessProcessStep.businessProcess.processCode"></s:hidden>
				<div class="unit">
					<s:if test="%{entityIsNew}">
					<s:textfield key="businessProcessStep.stepCode" cssClass="required" notrepeat='流程步骤编码已存在' validatorParam="from BusinessProcessStep entity where entity.stepCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="businessProcessStep.stepCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield id="businessProcessStep_stepName" key="businessProcessStep.stepName" name="businessProcessStep.stepName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcessStep_state" key="businessProcessStep.state" name="businessProcessStep.state" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcessStep_okName" key="businessProcessStep.okName" name="businessProcessStep.okName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="businessProcessStep.okStep"/>:</label>
    				<s:select name="businessProcessStep.okStep.stepCode" list="okStepList" listKey="stepCode" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
				<s:textfield id="businessProcessStep_noName" key="businessProcessStep.noName" name="businessProcessStep.noName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="businessProcessStep.noStep"/>:</label>
					<s:select name="businessProcessStep.noStep.stepCode" list="noStepList" listKey="stepCode" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
					<label><s:text name="businessProcessStep.unionCheck"/>:</label>
					<s:checkbox id="businessProcessStep_unionCheck" key="businessProcessStep.unionCheck" name="businessProcessStep.unionCheck" theme="simple"/>
				</div>
				<div class="unit">
				<s:hidden id="businessProcessStep_role_id" name="businessProcessStep.roleId"/>
				<s:textfield id="businessProcessStep_role" key="businessProcessStep.roleName" name="businessProcessStep.roleName" cssClass="				
				
				       "/>
				<script>
				jQuery("#businessProcessStep_role").treeselect({
					optType : "single",
					dataType : 'sql',
					sql : "select name id,chName name,'' parent from role",
					exceptnullparent : true,
					lazy : false,
					minWidth : '180px',
					selectParent : false,
					callback : {
					}
				});
				</script>
				</div>
				<div class="unit">
				<label><s:text name="businessProcessStep.stepInfo"/>:</label>
				<s:checkbox id="businessProcessStep_stepInfo" key="businessProcessStep.stepInfo" name="businessProcessStep.stepInfo" theme="simple"/>
				</div>
				<div class="unit">
				<s:textfield id="businessProcessStep_condition" key="businessProcessStep.condition" name="businessProcessStep.condition" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textarea id="businessProcessStep_remark" key="businessProcessStep.remark" name="businessProcessStep.remark" style="width:350px;heigh:30px"/>
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





