<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bmModelAssistForm").attr("action","saveBmModelAssist?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bmModelAssistForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bmModelAssistForm" method="post"	action="saveBmModelAssist?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bmModelAssist.id"/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelAssist_assistCode" key="bmModelAssist.assistCode" name="bmModelAssist.assistCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelAssist_item" key="bmModelAssist.item" name="bmModelAssist.item" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelAssist_itemName" key="bmModelAssist.itemName" name="bmModelAssist.itemName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmModelAssist_model" key="bmModelAssist.model" name="bmModelAssist.model" cssClass="				
				
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





