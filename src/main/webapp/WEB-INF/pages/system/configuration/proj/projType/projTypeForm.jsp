<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#projTypeForm").attr("action","saveProjType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#projTypeForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="projTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="projType.typeId"/>
				</div>
				<div class="unit">
				<s:textfield id="projType_cnCode" key="projType.cnCode" name="projType.cnCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="projType_disabled" key="projType.disabled" name="projType.disabled" />
				</div>
				<div class="unit">
				<s:checkbox id="projType_isSys" key="projType.isSys" name="projType.isSys" />
				</div>
				<div class="unit">
				<s:textfield id="projType_projDetailKind" key="projType.projDetailKind" name="projType.projDetailKind" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="projType_typeName" key="projType.typeName" name="projType.typeName" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





