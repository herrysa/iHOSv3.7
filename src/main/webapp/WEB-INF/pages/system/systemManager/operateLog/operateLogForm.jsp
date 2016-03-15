<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#operateLogForm").attr("action","saveOperateLog?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#operateLogForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="operateLogForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="operateLog.operateLogId"/>
				</div>
				<div class="unit">
				<s:textfield id="operateLog_operateObject" key="operateLog.operateObject" name="operateLog.operateObject" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="operateLog_operateTime" key="operateLog.operateTime" name="operateLog.operateTime" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="operateLog_operator" key="operateLog.operator" name="operateLog.operator" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="operateLog_userMachine" key="operateLog.userMachine" name="operateLog.userMachine" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="operateLog_userName" key="operateLog.userName" name="operateLog.userName" cssClass="required 				
				
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





