<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title><fmt:message key="entityDetail.title" /></title>
<meta name="heading" content="<fmt:message key='entityDetail.heading'/>" />
<script>
	jQuery(document).ready(
			function() {
				jQuery('#cancellink').click(function() {
					window.close();
				});
				//jQuery("#progressBar").css("width","160px");
				jQuery('#${random}savelink').click(
						function() {
							jQuery("#progressBar").html("数据库备份中，请稍等...");
							jQuery("#dbBackForm").attr(
									"action",
									"saveDbBackup?popup=true&navTabId="
											+ '${navTabId}');
							jQuery("#dbBackForm").submit();
						});
			});
	function formCallBackForDbback(json){
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			jQuery("#progressBar").html("数据加载中，请稍等...");
			if (json.navTabId){
				//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
				jQuery('#'+json.navTabId).trigger("reloadGrid");
			} else if (json.rel) {
				navTabPageBreak({}, json.rel);
			}
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
			}
		}
		//jQuery('#infodialog').html(json.message);
		//jQuery('#infodialog').dialog('open');
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="dbBackForm" method="post"
			action="saveDbBack?popup=true"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBackForDbback);">
			<div class="pageFormContent" layoutH="56">


				<s:url id="saveDbBack" action="saveDbBack" />

				<s:hidden key="dbBackup.bkid"/>
				<div class="unit">
					<s:textfield key="dbBackup.backupDataTime"	cssClass="required" readonly="true" size="40"/>
				</div>
				<div class="unit">
					<s:textfield key="dbBackup.dbBackupFileName" 						maxlength="100"  readonly="true" size="40"/>(ZIP格式)
				</div>
<%-- 				<div class="unit">
					<s:textfield key="dbBackup.dbBackupFileName" 						maxlength="30" />
				</div> --%>
				<div class="unit">
					<s:textfield key="dbBackup.operatorName" 						maxlength="50"  readonly="true" size="40"/>
					<s:hidden name="dbBackup.operatorId" 						/>
				</div>
				<div class="unit">
					<s:textarea key="dbBackup.remark" 						cols="50" rows="10"/>
				</div>
				
			</div>





			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</s:form>
	</div>
</div>
