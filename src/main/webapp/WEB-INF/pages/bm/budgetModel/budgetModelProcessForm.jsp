<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#bmModelProcess_save').click(function() {
			//jQuery("#bmModelProcessForm").attr("action","savebmModelProcess?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			var bmDept = jQuery("#bmModelProcess_bmDept").attr("checked");
			var unionCheck = jQuery("#bmModelProcess_unionCheck").attr("checked");
			var checkDept = jQuery("#bmModelProcess_checkDeptName_id").val();
			var checkPerson = jQuery("#bmModelProcess_checkPersonName_id").val();
			if(bmDept=='checked'){
				if(unionCheck=='checked'){
					alertMsg.error("预算部门审核的情况下，不能联合审核！");
					return ;
				}
				if(checkDept){
					alertMsg.error("预算部门审核的情况下，执行部门应该为空！");
					return ;
				}
				if(checkPerson){
					alertMsg.error("预算部门审核的情况下，执行人应该为空！");
					return ;
				}
			}
			if(unionCheck=='checked'){
				if(checkDept&&checkPerson){
					alertMsg.error("联合审核的情况下，执行部门和执行人不能同时存在！");
					return ;
				}
			}else{
			if(checkDept&&checkDept.indexOf(",")!=-1){
				alertMsg.error("执行部门为多个，请勾选联合审核！");
				return ;
			}
			if(checkPerson&&checkPerson.indexOf(",")!=-1){
				alertMsg.error("执行人为多个，请勾选联合审核！");
				return ;
			}
			}
			jQuery("#bmModelProcessForm").submit();
		});
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
					<s:textfield id="bmModelProcess_stepName" key="bmModelProcess.stepName" name="bmModelProcess.stepName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_okName" key="bmModelProcess.okName" name="bmModelProcess.okName" cssClass="				
				
				       "/>
					<label><s:text name="bmModelProcess.okStep"/>:</label>
					<span style="float:left;width:140px">
	    				<s:select name="bmModelProcess.okStep.bmProcessId" list="okStepList" listKey="bmProcessId" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
					</span>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_noName" key="bmModelProcess.noName" name="bmModelProcess.noName" cssClass="				
				
				       "/>
					<label><s:text name="bmModelProcess.noStep"/>:</label>
				<span style="float:left;width:140px">
					<s:select name="bmModelProcess.noStep.bmProcessId" list="noStepList" listKey="bmProcessId" listValue="stepName" headerKey="" headerValue="--" theme="simple"></s:select>
				</span>
				</div>
				<div class="unit">
				<label><s:text name="bmModelProcess.bmDept"/>:</label>
				<span style="float:left;width:140px">
					<s:checkbox id="bmModelProcess_bmDept" key="bmModelProcess.bmDept" name="bmModelProcess.bmDept" theme="simple"/>
				</span>
				<s:textfield id="bmModelProcess_state" key="bmModelProcess.state" name="bmModelProcess.state" cssClass="				
				digits
				       " readonly="true"/>
				</div>
				<div class="unit">
					<label><s:text name="bmModelProcess.unionCheck"/>:</label>
					<span style="float:left;width:140px">
					<s:checkbox id="bmModelProcess_unionCheck" key="bmModelProcess.unionCheck" name="bmModelProcess.unionCheck" theme="simple"/>
					</span>
				<label><s:text name="bmModelProcess.stepInfo"/>:</label>
				<span style="float:left;width:140px">
				<s:checkbox id="bmModelProcess_stepInfo" key="bmModelProcess.stepInfo" name="bmModelProcess.stepInfo" theme="simple"/>
				</span>
				</div>
				<div class="unit">
					<label><s:text name="bmModelProcess.isEnd"/>:</label>
					<span style="float:left;width:140px">
					<s:checkbox id="bmModelProcess_isEnd" key="bmModelProcess.isEnd" name="bmModelProcess.isEnd" theme="simple"/>
					</span>
				</div>
				<div class="unit">
				<s:textfield id="bmModelProcess_condition" key="bmModelProcess.condition" name="bmModelProcess.condition" style="width:350px" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:hidden id="bmModelProcess_role_id" name="bmModelProcess.roleId"/>
				<s:textfield id="bmModelProcess_role" key="bmModelProcess.roleName" name="bmModelProcess.roleName" style="width:400px" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:hidden id="bmModelProcess_checkDeptName_id" name="bmModelProcess.checkDeptId"/>
				<s:textfield id="bmModelProcess_checkDeptName" key="bmModelProcess.checkDeptName" name="bmModelProcess.checkDeptName" style="width:400px" cssClass="				
				
				       "/>
				<script>
				jQuery("#bmModelProcess_checkDeptName").treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : "select deptId id,'('+deptCode+')'+name name,parentDept_id parent from t_department where disabled=0 order by deptCode asc",
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
				<s:textfield id="bmModelProcess_checkPersonName" key="bmModelProcess.checkPersonName" name="bmModelProcess.checkPersonName" style="width:400px" cssClass="				
				
				       "/>
				<script>
				jQuery("#bmModelProcess_checkPersonName").treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : "select personId id,name,dept_id parent,personCode code from t_person where disabled=0 and personId<>'XT' union select deptId id,name,parentDept_id parent,deptCode code from t_department where disabled=0 and deptId<>'XT' and leaf=1 order by code asc",
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
				<s:textarea id="bmModelProcess_remark" key="bmModelProcess.remark" name="bmModelProcess.remark" style="width:400px;heigh:30px"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button id="bmModelProcess_save" type="Button"><s:text name="button.save" /></button>
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





