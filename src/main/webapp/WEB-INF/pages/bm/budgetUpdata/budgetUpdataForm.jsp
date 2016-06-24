<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetUpdataForm").attr("action","saveBudgetUpdata?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetUpdataForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetUpdataForm" method="post"	action="saveBudgetUpdata?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="budgetUpdata.updataId"/>
				</div>
				<div class="unit">
				<s:textfield id="budgetUpdata_checkDate" key="budgetUpdata.checkDate" name="budgetUpdata.checkDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetUpdata.checker.id" list="checkerList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetUpdata.department.id" list="departmentList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetUpdata.modelId.id" list="modelIdList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetUpdata.operator.id" list="operatorList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="budgetUpdata_optDate" key="budgetUpdata.optDate" name="budgetUpdata.optDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetUpdata_periodYear" key="budgetUpdata.periodYear" name="budgetUpdata.periodYear" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetUpdata_state" key="budgetUpdata.state" name="budgetUpdata.state" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetUpdata_submitDate" key="budgetUpdata.submitDate" name="budgetUpdata.submitDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetUpdata.submitter.id" list="submitterList" listKey="id" listValue="id"></s:select> -->
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





