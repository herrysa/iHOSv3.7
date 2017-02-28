<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#invOutStoreForm").attr("action","saveInvOutStore?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#invOutStoreForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="invOutStoreForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="invOutStore.id" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_barCode" key="invOutStore.barCode" name="invOutStore.barCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_batchId" key="invOutStore.batchId" name="invOutStore.batchId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_batchNo" key="invOutStore.batchNo" name="invOutStore.batchNo" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_cnCode" key="invOutStore.cnCode" name="invOutStore.cnCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_copyCode" key="invOutStore.copyCode" name="invOutStore.copyCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_curAmount" key="invOutStore.curAmount" name="invOutStore.curAmount" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_curMoney" key="invOutStore.curMoney" name="invOutStore.curMoney" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_factory" key="invOutStore.factory" name="invOutStore.factory" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_firstUnit" key="invOutStore.firstUnit" name="invOutStore.firstUnit" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_guarantee" key="invOutStore.guarantee" name="invOutStore.guarantee" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invCode" key="invOutStore.invCode" name="invOutStore.invCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invId" key="invOutStore.invId" name="invOutStore.invId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invModel" key="invOutStore.invModel" name="invOutStore.invModel" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invName" key="invOutStore.invName" name="invOutStore.invName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invTypeId" key="invOutStore.invTypeId" name="invOutStore.invTypeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_invTypeName" key="invOutStore.invTypeName" name="invOutStore.invTypeName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_orgCode" key="invOutStore.orgCode" name="invOutStore.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_price" key="invOutStore.price" name="invOutStore.price" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_productionDate" key="invOutStore.productionDate" name="invOutStore.productionDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_storeId" key="invOutStore.storeId" name="invOutStore.storeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_vendorId" key="invOutStore.vendorId" name="invOutStore.vendorId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_vendorName" key="invOutStore.vendorName" name="invOutStore.vendorName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="invOutStore_yearMonth" key="invOutStore.yearMonth" name="invOutStore.yearMonth" cssClass="				
				
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





