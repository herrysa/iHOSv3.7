<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetIndexForm").attr("action","saveBudgetIndex?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetIndexForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetIndexForm" method="post"	action="saveBudgetIndex?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="budgetIndex.indexCode" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetIndex.budgetType.id" list="budgetTypeList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:checkbox id="budgetIndex_disabeld" key="budgetIndex.disabeld" name="budgetIndex.disabeld" />
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_exceedBudgetType" key="budgetIndex.exceedBudgetType" name="budgetIndex.exceedBudgetType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_indexName" key="budgetIndex.indexName" name="budgetIndex.indexName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_indexType" key="budgetIndex.indexType" name="budgetIndex.indexType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_parentId" key="budgetIndex.parentId" name="budgetIndex.parentId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_warningPercent" key="budgetIndex.warningPercent" name="budgetIndex.warningPercent" cssClass="				
				digits
				       "/>
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





