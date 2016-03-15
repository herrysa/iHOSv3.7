<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#kqUpItemFormulaFilterForm").attr("action","saveKqUpItemFormulaFilter?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#kqUpItemFormulaFilterForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqUpItemFormulaFilterForm" method="post"	action="saveKqUpItemFormulaFilter?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqUpItemFormulaFilter.filterId"/>
				</div>
				<div class="unit">
				<s:textfield id="kqUpItemFormulaFilter_code" key="kqUpItemFormulaFilter.code" name="kqUpItemFormulaFilter.code" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="kqUpItemFormulaFilter.kqUpItemFormula.id" list="kqUpItemFormulaList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="kqUpItemFormulaFilter_name" key="kqUpItemFormulaFilter.name" name="kqUpItemFormulaFilter.name" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqUpItemFormulaFilter_oper" key="kqUpItemFormulaFilter.oper" name="kqUpItemFormulaFilter.oper" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqUpItemFormulaFilter_searchValue" key="kqUpItemFormulaFilter.searchValue" name="kqUpItemFormulaFilter.searchValue" cssClass="				
				
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





