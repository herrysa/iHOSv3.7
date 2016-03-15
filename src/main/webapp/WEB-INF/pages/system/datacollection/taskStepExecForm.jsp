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
											jQuery("#dataCollectionTaskStepForm").attr("action","saveTaskStepExec?popup=true&navTabId="+'${navTabId}');
											jQuery("#dataCollectionTaskStepForm").submit();
										});
					});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="dataCollectionTaskStepForm" method="post"
			action="saveTaskStepExec"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:url id="saveTaskStepExec"
					action="saveTaskStepExec" />
				<s:url id="list" action="dataCollectionTaskStepList" />

				<s:hidden name="stepExecId" label="stepExecId"/>
				<s:hidden name="taskExecId" label="taskExecId"/>
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
				</div>
				<div class="unit">
					<s:select key="dataCollectionTaskStep.stepType" required="false"
						maxlength="20" list="stepTypeList" listKey="content"
						cssClass="required" listValue="content" emptyOption="true"></s:select>
				</div>
				<!-- //<div  class="unit"> -->
				<%-- 	<s:if test="%{editType==0}">
						<s:checkbox key="dataCollectionTaskStep.isUsed" readonly="true" />
					</s:if>
					<s:else>
						<s:checkbox key="dataCollectionTaskStep.isUsed"/>
					</s:else> --%>
				<div class="unit">
					<s:if test="%{dataCollectionTaskStep.stepId!=null}">
						<label><fmt:message key='dataCollectionTaskStep.isUsed' />:</label>
						<s:checkbox key="dataCollectionTaskStep.isUsed" theme="simple"></s:checkbox>
					</s:if>
					<%-- <s:else>
						<s:checkbox key="dataCollectionTaskStep.isUsed" theme="simple" ></s:checkbox>
					</s:else>  --%>
				</div>
				<!-- //</div> -->
				<div class="unit">
					<s:textarea key="dataCollectionTaskStep.execSql" cssClass="required"
						maxlength="5000" cols="50" rows="20" />

				</div>
				<div class="unit">
					<s:textarea key="dataCollectionTaskStep.note"
						maxlength="500" cols="50" rows="4" />
				</div>
				
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



