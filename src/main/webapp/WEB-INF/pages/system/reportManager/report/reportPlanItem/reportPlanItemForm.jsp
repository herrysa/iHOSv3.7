<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportPlanItemForm").attr("action","saveReportPlanItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportPlanItemForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportPlanItemForm" method="post"	action="saveReportPlanItem?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="reportPlanItem.colId"/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_colName" key="reportPlanItem.colName" name="reportPlanItem.colName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_colSn" key="reportPlanItem.colSn" name="reportPlanItem.colSn" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_colWidth" key="reportPlanItem.colWidth" name="reportPlanItem.colWidth" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_fontIndex" key="reportPlanItem.fontIndex" name="reportPlanItem.fontIndex" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_headerFontIndex" key="reportPlanItem.headerFontIndex" name="reportPlanItem.headerFontIndex" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_headerTextColor" key="reportPlanItem.headerTextColor" name="reportPlanItem.headerTextColor" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_isThousandSeparat" key="reportPlanItem.isThousandSeparat" name="reportPlanItem.isThousandSeparat" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_itemCode" key="reportPlanItem.itemCode" name="reportPlanItem.itemCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanItem_planId" key="reportPlanItem.planId" name="reportPlanItem.planId" cssClass="				
				
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





