<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#projNatureForm").attr("action","saveProjNature?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#projNatureForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="projNatureForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="projNature.natureId"/>
				</div>
				<div class="unit">
				<s:checkbox id="projNature_disabled" key="projNature.disabled" name="projNature.disabled" />
				</div>
				<div class="unit">
				<s:checkbox id="projNature_isSys" key="projNature.isSys" name="projNature.isSys" />
				</div>
				<div class="unit">
				<s:textfield id="projNature_natureName" key="projNature.natureName" name="projNature.natureName" cssClass="				
				
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





