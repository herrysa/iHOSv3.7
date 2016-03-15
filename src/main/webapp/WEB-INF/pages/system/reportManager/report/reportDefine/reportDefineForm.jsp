<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportDefineForm").attr("action","saveReportDefine?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportDefineForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportDefineForm" method="post"	action="saveReportDefine?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="reportDefine.defineId" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:checkbox id="reportDefine_configurable" key="reportDefine.configurable" name="reportDefine.configurable" />
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_defineName" key="reportDefine.defineName" name="reportDefine.defineName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_displayType" key="reportDefine.displayType" name="reportDefine.displayType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_filters" key="reportDefine.filters" name="reportDefine.filters" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_groups" key="reportDefine.groups" name="reportDefine.groups" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_noItems" key="reportDefine.noItems" name="reportDefine.noItems" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_remark" key="reportDefine.remark" name="reportDefine.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_requiredFilter" key="reportDefine.requiredFilter" name="reportDefine.requiredFilter" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_requiredItems" key="reportDefine.requiredItems" name="reportDefine.requiredItems" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_reverseColumn" key="reportDefine.reverseColumn" name="reportDefine.reverseColumn" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDefine_subSystem" key="reportDefine.subSystem" name="reportDefine.subSystem" cssClass="				
				
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





