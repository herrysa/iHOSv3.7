<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#orgPrivilegeForm").attr("action","saveOrgPrivilege?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#orgPrivilegeForm").submit();
		});*/
		jQuery("#orgPrivi_person").treeselect({
			dataType : "sql",
			sql      : "select personId id,name,dept_id parent,0 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon from t_person where personId<>'XT' and disabled=0 "
						+"union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon from t_department where disabled=0 and deptId<>'XT'"
						+"union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT'",
			optType  : "single",
			minWidth : "200px"
			
		});
		jQuery("#orgPriviNames").treeselect({
			dataType : "sql",
			sql      : "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT'",
			optType  : "multi"
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="orgPrivilegeForm" method="post"	action="saveOrgPrivilege?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="orgPrivilege.privilegeId"/>
				</div>
				<div class="unit">
					<label><s:text name="orgPrivilege.person"/>:</label>
					<s:hidden id="orgPrivi_person_id" key="orgPrivilege.personId.personId" name="orgPrivilege.personId.personId" cssClass=""/>
					<s:textfield  id="orgPrivi_person" key="orgPrivilege.personId.name" name="orgPrivilege.personId.name" cssClass="" theme="simple"/>
				</div>
				<div class="unit">
					<s:hidden id="orgPriviNames_id" key="orgPrivilege.orgIds" name="orgPrivilege.orgIds" cssClass=""/>
					<s:textarea id="orgPriviNames" key="orgPrivilege.orgNames" cols="45" rows="3"></s:textarea>
				</div>
				<div class="unit">
					<s:textarea key="orgPrivilege.remark" cols="45" rows="3"></s:textarea>
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





