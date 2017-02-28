<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#userReportDefineForm").attr("action","saveUserReportDefine?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#userReportDefineForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="userReportDefineForm" method="post"	action="saveUserReportDefine?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="userReportDefine.defineId"/>
				</div>
				<div class="unit">
				<s:textfield id="userReportDefine_defineClass" key="userReportDefine.defineClass" name="userReportDefine.defineClass" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="userReportDefine_reportXml" key="userReportDefine.reportXml" name="userReportDefine.reportXml" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="userReportDefine_userId" key="userReportDefine.userId" name="userReportDefine.userId" cssClass="				
				
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





