<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			var fileName = jQuery("#strategyFile").val();
			//alert(fileName);
			jQuery("#strategyForm").attr("action","saveStrategy?popup=true&fileName="+fileName+"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#strategyForm").submit();
		});
	});
	function changeFile(){
		jQuery("#strategy_attachment").text("");
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="strategyForm" method="post"	action="" class="pageForm required-validate" enctype="multipart/form-data"	onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="strategy.strategyId"/>
				</div>
				
				<div class="unit">
				<s:textfield id="strategy_strategyTitle" key="strategy.strategyTitle" name="strategy.strategyTitle" cssStyle="width:308px" cssClass="required" />
				</div>
				<div class="unit">
				<s:textfield id="strategy_periodBegin" key="strategy.periodBegin" name="strategy.periodBegin" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" readonly="true" cssStyle="width:86px"/>
				<s:textfield id="strategy_periodEnd" key="strategy.periodEnd"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" readonly="true" name="strategy.periodEnd" cssStyle="width:86px"/>
				</div>
				<div class="unit">
				<s:textarea id="strategy_remark" key="strategy.remark" name="strategy.remark" cssStyle="width:308px;height:35px" />
				</div>
				
				<s:if test="%{strategy.strategyId!=null}">
				<div class="unit">
				<label><fmt:message key='strategy.disabled' />:</label>
						<span style="float:left;width:140px">
				<s:checkbox id="strategy_disabled" key="strategy.disabled" name="strategy.disabled"  theme="simple"/></span>
				</div>
				</s:if>
				<div class="unit">
				<s:file key="strategy.attachment" id="strategyFile" name="strategyFile" onchange="changeFile();"></s:file>
				<span id="strategy_attachment" style="margin-left:5px">${strategy.attachment}</span>
				</div>
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





