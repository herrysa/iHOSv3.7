<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<head>
<title><fmt:message key="formulaFieldDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='formulaFieldDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
				window.close();
		});
		jQuery('#${random}savelink').click(function() {
				jQuery("#formulaFieldForm").attr("action","saveFormulaField?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
				jQuery("#formulaFieldForm").submit();
		});
		jQuery('#savePreviewlink').click(function() {
				var action = formulaFieldForm.action = "saveAndPreviewFormulaField?popup=true";
				formulaFieldForm.submit;
		});
		/* var inputWidth = jQuery("#formulaFieldForm").find(":text").eq(0).width();
		alert(jQuery("#formulaFieldForm").find(":text").eq(0).css("width"));
		var labelWidth = jQuery("#formulaFieldForm").find(":text").eq(0).prev().width();
		jQuery("#formulaFieldForm").find("select").each(function(){
			jQuery(this).parent().css("width",inputWidth);
		}); */
		
		adjustFormInput("formulaFieldForm");
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="formulaFieldForm" method="post"
			action="saveFormulaField?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">

					<s:url id="saveFormulaField" action="saveFormulaField" />

					<s:if test="entityIsNew==true">
						<s:textfield name="formulaField.formulaEntity.formulaEntityId"
						key="formulaEntity.formulaEntityId" />
						<s:textfield key="formulaField.formulaFieldId" cssClass="required"/>
					</s:if>
					<s:else>
						<s:textfield name="formulaField.formulaEntity.formulaEntityId"
						key="formulaEntity.formulaEntityId" readonly="true" />
						<s:textfield key="formulaField.formulaFieldId" cssClass="required"
						 readonly="true" />
					</s:else>
					
					
				</div>
				<div class="unit">
				<s:if test="entityIsNew==true">
					<s:textfield key="formulaField.fieldName" cssClass="required"
						maxlength="50" />
				</s:if>
				<s:else>
					<s:textfield key="formulaField.fieldName" cssClass="required"
						maxlength="50" readonly="true" />
				</s:else>

					<s:textfield key="formulaField.fieldTitle" cssClass="required"
						maxlength="50" />
				</div>
				<div class="unit">
					<label><fmt:message key='formulaField.calcType' />:</label>
					<span class="formspan" style="float:left;width:140px">
					<s:select list="#{'手工':'手工','公式计算':'公式计算'}"
						key="formulaField.calcType" listKey="key" listValue="value"
						cssClass="required" theme="simple"/>
					</span>
					<label><fmt:message key='formulaField.keyClass' />:</label>
					<span class="formspan" style="float:left;width:140px">
						<%-- list="#{'公用':'公用','指标':'指标','成本核算':'成本核算','奖金核算':'奖金核算','经济核算':'经济核算','收入核算':'收入核算','财务':'财务','其他':'其他'}"
					 --%><s:select
						list="indicatorClassList" emptyOption="true"
						key="formulaField.keyClass" listKey="value" listValue="content"
						cssClass="required" theme="simple"/>
					</span>
				</div>
				<div class="unit">
					<s:textfield key="formulaField.defaultValue" cssClass="required"
						maxlength="50" />
					<s:select list="#{'纵向':'纵向','横向':'横向'}"
						key="formulaField.direction" listKey="key" listValue="value"
						cssClass="required" />
				</div>
				<div class="unit">
					<label><fmt:message key='formulaField.disabled' />:</label>
					<span class="formspan" style="float:left;width:140px">
					<s:checkbox key="formulaField.disabled" theme="simple"></s:checkbox>
					</span>
					<label><fmt:message key='formulaField.inherited' />:</label>
					<span class="formspan" style="float:left;width:140px">
					<s:checkbox key="formulaField.inherited" theme="simple"></s:checkbox>
					</span>
				</div>
				<div class="unit">
					<s:textfield key="formulaField.execOrder" cssClass="required"
						maxlength="100" />
				</div>
				<div class="unit">
					<s:textarea key="formulaField.formula.formula" required="false"
						maxlength="4000" rows="4" cols="54"  />
				</div>
				<div class="unit">
					<s:textarea key="formulaField.formula.condition" required="false"
						maxlength="4000" rows="4" cols="54"  />
				</div>
				<div class="unit">
					<s:textarea
							key="formulaField.formula.sqlExpress" required="false"
							maxlength="4000" rows="4" cols="54" readonly="true"
							 />
				</div>
				<div class="unit">
					<s:textarea
							key="formulaField.formula.sqlCondition" required="false"
							maxlength="4000" rows="4" cols="54" readonly="true"
							/>
				</div>
				<div class="unit">
					<s:textarea key="formulaField.description"
							required="false" maxlength="500" rows="4" cols="54"
							 />
				</div>
			</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}savelink">保存</button>
								</div>
							</div></li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
				
				</s:form>
			</div>
	</div>