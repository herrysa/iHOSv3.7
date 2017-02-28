<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bugetAcctMapForm").attr("action","saveBugetAcctMap?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bugetAcctMapForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bugetAcctMapForm" method="post"	action="saveBugetAcctMap?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bugetAcctMap.mapId"/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_acctCode" key="bugetAcctMap.acctCode" name="bugetAcctMap.acctCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_acctId" key="bugetAcctMap.acctId" name="bugetAcctMap.acctId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_acctName" key="bugetAcctMap.acctName" name="bugetAcctMap.acctName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_addDirect" key="bugetAcctMap.addDirect" name="bugetAcctMap.addDirect" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_bmSubjId" key="bugetAcctMap.bmSubjId" name="bugetAcctMap.bmSubjId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_remark" key="bugetAcctMap.remark" name="bugetAcctMap.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bugetAcctMap_subDirect" key="bugetAcctMap.subDirect" name="bugetAcctMap.subDirect" cssClass="				
				
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





