<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportFunctionForm").attr("action","saveReportFunction?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportFunctionForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportFunctionForm" method="post"	action="saveReportFunction?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="reportFunction.code" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_category" key="reportFunction.category" name="reportFunction.category" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_dataType" key="reportFunction.dataType" name="reportFunction.dataType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_funcSql" key="reportFunction.funcSql" name="reportFunction.funcSql" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_name" key="reportFunction.name" name="reportFunction.name" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_params" key="reportFunction.params" name="reportFunction.params" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_remark" key="reportFunction.remark" name="reportFunction.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_rsType" key="reportFunction.rsType" name="reportFunction.rsType" cssClass="				
				
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





