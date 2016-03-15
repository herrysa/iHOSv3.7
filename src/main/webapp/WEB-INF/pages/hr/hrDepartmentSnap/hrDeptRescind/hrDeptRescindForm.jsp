<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=='view'){
			readOnlyForm("hrDeptRescindForm");
		}else{
			jQuery("#hrDeptRescind_moveToDept").addClass("required");
			jQuery('#saveHrDeptRescindlink').click(function() {
				jQuery("#hrDeptRescindForm").attr("action","saveHrDeptRescind?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
				jQuery("#hrDeptRescindForm").submit();
			});
			var deptId = "${hrDeptRescind.hrDept.departmentId}";
			jQuery("#hrDeptRescind_moveToDept").treeselect({
				dataType : "url",
				optType : "single",
				url : "getOrgDeptTreeSelect?execludeDeptId="+deptId,
				exceptnullparent : false,
				lazy : false,
				minWidth:'230px'
			});
			// 当撤销的部门下有人员时，人员归属部门必填
			
		}
	});
	
	function saveHrDeptRescindCallback(data){
		formCallBack(data);
		if(data.statusCode==200){
			if(data.navTabId=='hrDepartmentCurrent_gridtable'){
				var deptNodes = data.deptTreeNodes;
				var deptEditNodes = data.deptEditTreeNodes;
				if(deptNodes){
					for(var deptIndex in deptNodes){
						dealHrTreeNode('hrDepartmentCurrentLeftTree',deptNodes[deptIndex],'rescind','dept');
					}
				}
				if(deptEditNodes){
					for(var deptIndex in deptEditNodes){
						dealHrTreeNode('hrDepartmentCurrentLeftTree',deptEditNodes[deptIndex],'editPC','dept');
					}
				}
			}else{
				var state = "${hrDeptRescind.state}";
				if(state=='3'){
					var deptNodes = data.deptTreeNodes;
					var deptEditNodes = data.deptEditTreeNodes;
					if(deptNodes){
						for(var deptIndex in deptNodes){
							dealHrTreeNode('hrDeptTreeInDeptRescind',deptNodes[deptIndex],'rescind','dept');
						}
					}
					if(deptEditNodes){
						for(var deptIndex in deptEditNodes){
							dealHrTreeNode('hrDeptTreeInDeptRescind',deptEditNodes[deptIndex],'editPC','dept');
						}
					}
				}
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrDeptRescindForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveHrDeptRescindCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="hrDeptRescind.id"/>
					<s:hidden key="hrDeptRescind.state"/>
					<s:hidden key="hrDeptRescind.rescindNo"/>
					<s:hidden key="hrDeptRescind.snapCode"/>
					<s:hidden key="hrDeptRescind.hrDept.departmentId"/>
					<s:hidden key="hrDeptRescind.rescindDate"/>
					<s:hidden key="hrDeptRescind.makeDate"/>
					<s:hidden key="hrDeptRescind.checkDate"/>
					<s:hidden key="hrDeptRescind.confirmDate"/>
					<s:hidden key="hrDeptRescind.rescindPerson.personId"/>
					<s:hidden key="hrDeptRescind.checkPerson.personId"/>
					<s:hidden key="hrDeptRescind.confirmPerson.personId"/>
					<s:hidden key="hrDeptRescind.moveToSnapCode"/>
					<s:hidden key="hrDeptRescind.yearMonth"/>
				</div>
				<div class="unit">
					<s:textfield key="hrDeptRescind.hrDept" name="hrDeptRescind.hrDept.name" readonly="true"/>
					<s:textfield id="hrDeptRescind_moveToDept" key="hrDeptRescind.moveToDept" name="hrDeptRescind.moveToDept.name"/>
					<s:hidden id="hrDeptRescind_moveToDept_id" name="hrDeptRescind.moveToDept.departmentId"/>
				</div>
				<%-- <div class="unit">
					<s:textfield key="hrDeptRescind.rescindDate" name="hrDeptRescind.rescindDate" cssClass="Wdate" style="height:15px" 
						onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/>
				</div> --%>
				<div class="unit">
					<s:textarea key="hrDeptRescind.rescindReason" name="hrDeptRescind.rescindReason" maxlength="200" rows="3" cols="53"/>
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${deptNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="hrDeptRescind.rescindPerson"></s:text>:</label>
								<input type="text" value="${hrDeptRescind.rescindPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptRescind.checkPerson"></s:text>:</label>
								<input type="text" value="${hrDeptRescind.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptRescind.confirmPerson"></s:text>:</label>
								<input type="text" value="${hrDeptRescind.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="hrDeptRescindFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveHrDeptRescindlink"><s:text name="button.save" /></button>
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





