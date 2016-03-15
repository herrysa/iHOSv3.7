<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#gzItemFormulaFilterForm").attr("action","saveGzItemFormulaFilter?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#gzItemFormulaFilterForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzItemFormulaFilterForm" method="post"	action="saveGzItemFormulaFilter?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="gzItemFormulaFilter.filterId"/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemFormulaFilter_code" key="gzItemFormulaFilter.code" name="gzItemFormulaFilter.code" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="gzItemFormulaFilter.gzItemFormula.id" list="gzItemFormulaList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="gzItemFormulaFilter_name" key="gzItemFormulaFilter.name" name="gzItemFormulaFilter.name" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemFormulaFilter_oper" key="gzItemFormulaFilter.oper" name="gzItemFormulaFilter.oper" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemFormulaFilter_searchValue" key="gzItemFormulaFilter.searchValue" name="gzItemFormulaFilter.searchValue" cssClass="				
				
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





