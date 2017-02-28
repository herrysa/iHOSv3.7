<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bugetNormForm").attr("action","saveBugetNorm?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bugetNormForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bugetNormForm" method="post"	action="saveBugetNorm?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bugetNorm.normId"/>
				</div>
				<div class="unit">
				<s:textfield id="bugetNorm_amount" key="bugetNorm.amount" name="bugetNorm.amount" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetNorm_bmSubjtId" key="bugetNorm.bmSubjtId" name="bugetNorm.bmSubjtId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetNorm_de" key="bugetNorm.de" name="bugetNorm.de" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetNorm_deptId" key="bugetNorm.deptId" name="bugetNorm.deptId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetNorm_rs" key="bugetNorm.rs" name="bugetNorm.rs" cssClass="				number
				
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





