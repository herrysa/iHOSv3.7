<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#invInStoreForm").attr("action","saveInvInStore?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#invInStoreForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="invInStoreForm" method="post"	action="saveInvInStore?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="invInStore.id" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_copyCode" key="invInStore.copyCode" name="invInStore.copyCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_firstUnit" key="invInStore.firstUnit" name="invInStore.firstUnit" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invCode" key="invInStore.invCode" name="invInStore.invCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invId" key="invInStore.invId" name="invInStore.invId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invModel" key="invInStore.invModel" name="invInStore.invModel" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invName" key="invInStore.invName" name="invInStore.invName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invTypeId" key="invInStore.invTypeId" name="invInStore.invTypeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_invTypeName" key="invInStore.invTypeName" name="invInStore.invTypeName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_orgCode" key="invInStore.orgCode" name="invInStore.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_storeId" key="invInStore.storeId" name="invInStore.storeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_vendorId" key="invInStore.vendorId" name="invInStore.vendorId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invInStore_vendorName" key="invInStore.vendorName" name="invInStore.vendorName" cssClass="				
				
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





