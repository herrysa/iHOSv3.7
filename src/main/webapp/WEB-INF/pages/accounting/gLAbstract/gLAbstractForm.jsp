<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#gLAbstractForm").attr("action","saveGLAbstract?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#gLAbstractForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gLAbstractForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="gLAbstract.abstractId"/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_acctcode" key="gLAbstract.acctcode" name="gLAbstract.acctcode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_cnCode" key="gLAbstract.cnCode" name="gLAbstract.cnCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_copycode" key="gLAbstract.copycode" name="gLAbstract.copycode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_kjYear" key="gLAbstract.kjYear" name="gLAbstract.kjYear" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_orgCode" key="gLAbstract.orgCode" name="gLAbstract.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gLAbstract_voucher_abstract" key="gLAbstract.voucher_abstract" name="gLAbstract.voucher_abstract" cssClass="				
				
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





