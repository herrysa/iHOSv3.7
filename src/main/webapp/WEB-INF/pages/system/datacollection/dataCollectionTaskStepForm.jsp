<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="dataCollectionTaskStepDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataCollectionTaskStepDetail.heading'/>" />
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
											jQuery("#dataCollectionTaskStepForm").attr("action","saveDataCollectionTaskStep?popup=true&navTabId="+'${navTabId}');
											jQuery("#dataCollectionTaskStepForm").submit();
										});
					});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="dataCollectionTaskStepForm" method="post"
			action="saveDataCollectionTaskStep"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:url id="saveDataCollectionTaskStep"
					action="saveDataCollectionTaskStep" />
				<s:url id="list" action="dataCollectionTaskStepList" />

				<s:hidden key="dataCollectionTaskStep.stepId" />
				<s:hidden
					name="dataCollectionTaskStep.dataCollectionTaskDefine.dataCollectionTaskDefineId"
					value="%{dataCollectionTaskStep.dataCollectionTaskDefine.dataCollectionTaskDefineId}" />
				<div class="unit">
					<s:textfield
						key="dataCollectionTaskDefine.dataCollectionTaskDefineName"
						value="%{dataCollectionTaskStep.dataCollectionTaskDefine.dataCollectionTaskDefineName}"
						cssClass="required" readonly="true" style="width:300px"/>
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskStep.stepName" cssClass="required"
						maxlength="50" style="width:300px" />
				</div>
				<div class="unit">
					<s:textfield key="dataCollectionTaskStep.execOrder"
						cssClass="required digits" maxlength="2" />
					
					<s:url id="dicSelectList1" action="dictionaryItemSelectList"
					namespace="/">
					<s:param name="dicCode">dataCollectionStepType</s:param>
				</s:url>
				<div class="unit">
				
						<s:select label="%{getText('dataCollectionTaskStep.stepType')}"
					name="dataCollectionTaskStep.stepType"
					value="%{dataCollectionTaskStep.stepType}"
					id="dataCollectionTaskStep_stepType_id" cssClass="required"
					maxlength="50" list="dataCollectionStepTypeList" listKey="content"
					listValue="content" emptyOption="false"></s:select>
				</div>
				</div>
				<div class="unit">
					<s:textarea key="dataCollectionTaskStep.execSql" cssClass="required"
						maxlength="5000" cols="50" rows="20" />

				</div>
				<s:url id="dicSelectList" action="dictionaryItemSelectList"
					namespace="/">
					<s:param name="dicCode">radioyesno</s:param>
				</s:url>
				<%-- <sj:radio key="dataCollectionTaskStep.isUsed" href="%{dicSelectList}"
		list="dictionaryItemsSelectList" listKey="value" listValue="content"
		required="true" buttonset="false"/> --%>
				<div class="unit">
					<s:textarea key="dataCollectionTaskStep.note"
						maxlength="500" cols="50" rows="5" />
				</div>
				<s:if test="%{dataCollectionTaskStep.stepId!=null}">
				<div class="unit">
					<label><fmt:message key='dataCollectionTaskStep.isUsed' />:</label>
					<s:checkbox key="dataCollectionTaskStep.isUsed" theme="simple"></s:checkbox>
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
<%-- <sj:a id="savelink" formIds="dataCollectionTaskStepForm" indicator="indicator"	button="true"><fmt:message key="button.save" /></sj:a>
<sj:a id="cancellink" value="Cancel" button="true" href="%{list}"><fmt:message key="button.cancel" /></sj:a> --%>



