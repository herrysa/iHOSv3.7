<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#gzItemPersonTypeForm").attr("action","savegzItemPersonType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#gzItemPersonTypeForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzItemPersonTypeForm" method="post"	action="savegzItemPersonType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="gzItemPersonType.mappingId"/>
				</div>
				<div class="unit">
				<s:checkbox id="gzItemPersonType_disabled" key="gzItemPersonType.disabled" name="gzItemPersonType.disabled" />
				</div>
				<div class="unit">
				<s:textfield id="gzItemPersonType_formulaId" key="gzItemPersonType.formulaId" name="gzItemPersonType.formulaId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemPersonType_gzTypeId" key="gzItemPersonType.gzTypeId" name="gzItemPersonType.gzTypeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemPersonType_itemId" key="gzItemPersonType.itemId" name="gzItemPersonType.itemId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzItemPersonType_personType" key="gzItemPersonType.personType" name="gzItemPersonType.personType" cssClass="" />
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





