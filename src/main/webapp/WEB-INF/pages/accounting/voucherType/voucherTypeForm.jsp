<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#voucherTypeForm").attr("action","saveVoucherType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#voucherTypeForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="voucherTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="voucherType.voucherTypeId" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="voucherType_copyCode" key="voucherType.copyCode" name="voucherType.copyCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="voucherType_isUsed" key="voucherType.isUsed" name="voucherType.isUsed" />
				</div>
				<div class="unit">
				<s:textfield id="voucherType_orgId" key="voucherType.orgId" name="voucherType.orgId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherType_voucherCode" key="voucherType.voucherCode" name="voucherType.voucherCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherType_voucherTypeShort" key="voucherType.voucherTypeShort" name="voucherType.voucherTypeShort" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherType_vouchertype" key="voucherType.vouchertype" name="voucherType.vouchertype" cssClass="				
				
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





