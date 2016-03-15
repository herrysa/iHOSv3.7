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
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink')
								.click(
										function() {
											jQuery("#dataCollectionTaskDefineForm").attr("action","saveTaskExec?popup=true&navTabId="+'${navTabId}');
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
				<s:hidden name="taskExecId" />

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
					<s:textfield key="dataCollectionTaskDefine.dept" name="dataCollectionTaskDefine.department.name" id="dataCollectionTaskDefine_dept" readonly="true"
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

