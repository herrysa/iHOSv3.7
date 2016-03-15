<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="dataCollectionTaskDefineDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataCollectionTaskDefineDetail.heading'/>" />
<script>
	jQuery(document)
			.ready(
					function() {
						
						/*部门树*/
						var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
						var herpType = "${fns:getHerpType()}";
						if(herpType == "M") {
							sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
						}
						sql += " ORDER BY orderCol ";
						jQuery("#dataCollectionTaskDefine_dept").treeselect({
							optType : "single",
							dataType : 'sql',
							sql : sql,
							exceptnullparent : true,
							lazy : false,
							minWidth : '180px',
							selectParent : false
						});
						/* jQuery("#dataCollectionTaskDefine_dept").treeselect({
							dataType:"sql",
							optType:"single",
							sql:"select deptId id,name ,parentDept_id parent from t_department where disabled=0",
							exceptnullparent:false,
							lazy:false,
							callback : {}
						}); */
						
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink')
								.click(
										function() {
											jQuery("#dataCollectionTaskDefineForm").attr("action","saveDataCollectionTaskDefine?popup=true&navTabId="+'${navTabId}');
											jQuery("#dataCollectionTaskDefineForm").submit();
										});
					});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="dataCollectionTaskDefineForm" method="post"
			action="saveDataCollectionTaskDefine"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">

				<s:url id="saveDataCollectionTaskDefine"
					action="saveDataCollectionTaskDefine" />
				<s:url id="dsdSelectList" action="dataSourceDefineSelectList" />


				<s:hidden key="dataCollectionTaskDefine.dataCollectionTaskDefineId" />
				<div class="unit">
					<s:textfield
						key="dataCollectionTaskDefine.dataCollectionTaskDefineName"
						cssClass="required" maxlength="50" style="width:200px"/>
				</div>
				<div class="unit">
					<label><fmt:message key='dataSourceDefine.dataSourceName' />:</label>	
					<sj:select href="%{dsdSelectList}"
						key="dataSourceDefine.dataSourceName"
						name="dataCollectionTaskDefine.dataSourceDefine.dataSourceDefineId"
						value="%{dataCollectionTaskDefine.dataSourceDefine.dataSourceDefineId}"
						required="true" maxlength="50" list="dataSourceDefineSelectList"
						listKey="dataSourceDefineId" listValue="dataSourceName" cssClass="required" />
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskDefine.dept" name="dataCollectionTaskDefine.department.name" id="dataCollectionTaskDefine_dept"
						cssClass="required" maxlength="50" style="width:200px"/>
					<s:hidden key="dataCollectionTaskDefine.department.departmentId" name="dataCollectionTaskDefine.department.departmentId" id="dataCollectionTaskDefine_dept_id"/>
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskDefine.taskNo"
						cssClass="required digits" maxlength="50" style="width:200px"/>
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskDefine.temporaryTableName"
						cssClass="required" maxlength="50" style="width:200px"/>
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskDefine.targetTableName"
						cssClass="required" maxlength="50" style="width:200px"/>
				</div>
				<div class="unit">
					<s:textarea key="dataCollectionTaskDefine.remark" cols="54" rows="5"/>
				</div>
				<s:if test="%{dataCollectionTaskDefine.dataCollectionTaskDefineId!=null}">
				<div class="unit">
					<label>是否使用:</label><s:checkbox key="dataCollectionTaskDefine.isUsed" theme="simple"></s:checkbox>
				</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>

