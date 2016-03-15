<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
	     var planId = '${planId}';
	     jQuery("#planId").val(planId);
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzAccountPlanItemForm" method="post"	action="saveGzAccountPlanItem?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="gzAccountPlanItem.colId"/>
				<s:hidden name="gzAccountPlanItem.planId" id="planId"/>
				</div>
				<div class="unit">
				<s:select id="gzAccountPlanItem_colWidth" key="gzAccountPlanItem.itemd" name="gzAccountPlanItem.itemCode"
				 headerKey=""   style="font-size:12px"
				  list="#request.itemList" listKey="itemCode" listValue="itemName"
				       />
				           
				</div>
				<div class="unit">
				<s:textfield id="gzAccountPlanItem_colName" key="gzAccountPlanItem.colName" name="gzAccountPlanItem.colName" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="gzAccountPlanItem_colWidth" key="gzAccountPlanItem.colWidth" name="gzAccountPlanItem.colWidth" cssClass="				
				digits
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





