<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#colSearchForm").attr("action","saveColSearch?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#colSearchForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="colSearchForm" method="post"	action="saveColSearch?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="colSearch.id"/>
				</div>
				<div class="unit">
				<s:textfield id="colSearch_col" key="colSearch.col" name="colSearch.col" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colSearch_label" key="colSearch.label" name="colSearch.label" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colSearch_operator" key="colSearch.operator" name="colSearch.operator" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colSearch_templetName" key="colSearch.templetName" name="colSearch.templetName" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colSearch_value" key="colSearch.value" name="colSearch.value" cssClass="required 				
				
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





