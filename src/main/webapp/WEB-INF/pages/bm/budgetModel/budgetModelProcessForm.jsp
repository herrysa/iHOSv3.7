<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bmModelProcessForm").attr("action","savebmModelProcess?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bmModelProcessForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bmModelProcessForm" method="post"	action="saveBmModelProcess?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="bmModelProcess.bmProcessId"></s:hidden>
				<s:hidden key="bmModelProcess.budgetModel.modelId"></s:hidden>
				<div class="unit">
					<s:textfield key="bmModelProcess.stepCode" readonly="true" cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="bmModelProcess_stepName" key="bmModelProcess.stepName" name="bmModelProcess.stepName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_state" key="bmModelProcess.state" name="bmModelProcess.state" cssClass="				
				digits
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_okName" key="bmModelProcess.okName" name="bmModelProcess.okName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="bmModelProcess.okStep"/>:</label>
    				<s:select name="bmModelProcess.okStep.bmProcessId" list="okStepList" listKey="bmProcessId" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_noName" key="bmModelProcess.noName" name="bmModelProcess.noName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="bmModelProcess.noStep"/>:</label>
					<s:select name="bmModelProcess.noStep.bmProcessId" list="noStepList" listKey="bmProcessId" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
					<label><s:text name="bmModelProcess.unionCheck"/>:</label>
					<s:checkbox id="bmModelProcess_unionCheck" key="bmModelProcess.unionCheck" name="bmModelProcess.unionCheck" theme="simple"/>
				</div>
				<div class="unit">
				<label><s:text name="bmModelProcess.stepInfo"/>:</label>
				<s:checkbox id="bmModelProcess_stepInfo" key="bmModelProcess.stepInfo" name="bmModelProcess.stepInfo" theme="simple"/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_condition" key="bmModelProcess.condition" name="bmModelProcess.condition" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:hidden id="bmModelProcess_role_id" name="bmModelProcess.roleId"/>
				<s:textfield id="bmModelProcess_role" key="bmModelProcess.roleName" name="bmModelProcess.roleName" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:hidden id="bmModelProcess_checkDeptName_id" name="bmModelProcess.checkDeptId"/>
				<s:textfield id="bmModelProcess_checkDeptName" key="bmModelProcess.checkDeptName" name="bmModelProcess.checkDeptName" cssClass="				
				
				       "/>
				<script>
				jQuery("#bmModelProcess_checkDeptName").treeselect({
					optType : "single",
					dataType : 'sql',
					sql : "select deptId id,name,parentDept_id parent from t_department where disabled=0 order by deptCode asc",
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
				<s:hidden id="bmModelProcess_checkPersonName_id" name="bmModelProcess.checkPersonId"/>
				<s:textfield id="bmModelProcess_checkPersonName" key="bmModelProcess.checkPersonName" name="bmModelProcess.checkPersonName" cssClass="				
				
				       "/>
				<script>
				jQuery("#bmModelProcess_checkPersonName").treeselect({
					optType : "single",
					dataType : 'sql',
					sql : "select personId id,name,dept_id parent,personCode code from t_person where disabled=0 union select deptId id,name,parentDept_id parent,deptCode code from t_department where disabled=0 and leaf=1 order by code asc",
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
				<s:textarea id="bmModelProcess_remark" key="bmModelProcess.remark" name="bmModelProcess.remark" style="width:350px;heigh:30px"/>
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





