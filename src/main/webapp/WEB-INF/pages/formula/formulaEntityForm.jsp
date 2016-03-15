<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="formulaEntityDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='formulaEntityDetail.heading'/>" />
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
											jQuery("#formulaEntityForm").attr("action","saveFormulaEntity?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
											jQuery("#formulaEntityForm").submit();
										});
					});
</script>
</head>


<div class="page">
	<div class="pageContent">
		<form id="formulaEntityForm" method="post"
			action="saveFormulaEntity?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden name="editType" value="%{editType}" label="editType" />

					<s:if test="%{editType==1}">
						<s:textfield key="formulaEntity.formulaEntityId" cssClass="required"
							maxlength="30" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield key="formulaEntity.formulaEntityId" cssClass="required"
							maxlength="30" onblur="checkId(this,'FormulaEntity','formulaEntityId')"/>
					</s:else>
				</div>
				<div class="unit">
					<%--     <s:textfield key="formulaEntity.formulaEntityId" required="true" cssClass="text medium"/> --%>
					<s:textfield key="formulaEntity.entityName" cssClass="required"
						maxlength="50" />
				</div>
				<div class="unit">
					<s:textfield key="formulaEntity.tableName" cssClass="required"
						maxlength="50" />
				</div>
				<div class="unit">
					<s:textarea key="formulaEntity.entityDesc" required="false"
						maxlength="500" rows="5" cols="50" />
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
