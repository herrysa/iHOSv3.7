<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="dataCollectionTaskDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='dataCollectionTaskDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			var action=dataCollectionTaskForm.action="saveDataCollectionTask?popup=true";
			dataCollectionTaskForm.submit;
		});
	});
</script>
</head>
<s:url id="saveDataCollectionTask" action="saveDataCollectionTask" />
<s:url id="list" action="dataCollectionTaskList" />
<s:form id="dataCollectionTaskForm" action="saveDataCollectionTask"
	method="post" validate="true" cssClass="well  form-horizontal">

	<s:hidden key="dataCollectionTask.dataCollectionTaskId" />
	<s:hidden key="dataCollectionTask.dataCollectionPeriod.periodId" />
	<sj:textfield key="period.periodCode"
		value="%{dataCollectionTask.dataCollectionPeriod.periodCode}"></sj:textfield>

	<s:hidden
		key="dataCollectionTask.dataCollectionTaskDefine.dataCollectionTaskDefineId" />
	<sj:textfield
		key="dataCollectionTaskDefine.dataCollectionTaskDefineName"
		value="%{dataCollectionTask.dataCollectionTaskDefine.dataCollectionTaskDefineName}" style="width:200px"></sj:textfield>




	<sj:textfield key="dataCollectionTask.dataFile" required="false"
		maxlength="200" style="width:200px"/>



	<s:url id="dicSelectList" action="dictionaryItemSelectList"
		namespace="/system">
		<s:param name="dicCode">dataCollectionTaskStatus</s:param>
	</s:url>

	<sj:radio key="dataCollectionTask.status" href="%{dicSelectList}"
		list="dictionaryItemsSelectList" listKey="value" listValue="content"
		required="true" />

	<fieldset class="form-actions">
		<s:submit id="${random}savelink" key="button.save" method="save"
			cssClass="btn btn-primary" theme="simple" />
		<s:submit onclick="$.pdialog.closeCurrent();" key="button.cancel" method="cancel"
			cssClass="btn" theme="simple" />
	</fieldset>
</s:form>
<%-- <sj:a id="savelink" formIds="dataCollectionTaskForm" indicator="indicator"	button="true"><fmt:message key="button.save" /></sj:a>
<sj:a id="cancellink" value="Cancel" button="true" href="%{list}"><fmt:message key="button.cancel" /></sj:a> --%>



