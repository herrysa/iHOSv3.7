<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			jQuery("#specialItemForm").attr("action","saveSpecialItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#specialItemForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="specialItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:if test="%{specialItem.itemId==null}">
				<s:textfield key="specialItem.itemId" cssClass="required" size="50" onblur="checkId(this,'SpecialItem','ItemId')"/>
				</s:if>
				<s:else>
				<s:textfield key="specialItem.itemId" cssClass="required" size="50" readonly="true"/>
				</s:else>
				
				</div>
				
				<div class="unit">
				<s:textfield id="specialItem_itemName" key="specialItem.itemName" name="specialItem.itemName" cssClass="required" size="50"/>
				</div>
				<div class="unit">
				<s:select key="specialItem.itemType" name="specialItem.itemType" value="%{specialItem.itemType}"  style="width:133px;" cssClass="required"
					list="dicSpecialItemList" listKey="value" listValue="content"
					emptyOption="false"></s:select>
				</div>
				<div class="unit">
				<s:textarea id="specialItem_remark" key="specialItem.remark" cols="38" rows="2" name="specialItem.remark"/>
				</div>
			
				<s:if test="%{specialItem.itemId!=null}">
				<div class="unit">
				<label><s:text name="specialItem.disabled"></s:text>:</label>
					<span style="float:left;width:140px">
				<s:checkbox id="specialItem_disabled" key="specialItem.disabled" name="specialItem.disabled"  theme="simple"/></span>
				</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
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





