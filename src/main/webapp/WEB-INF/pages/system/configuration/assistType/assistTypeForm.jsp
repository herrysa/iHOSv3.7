<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#assistTypeForm").attr("action","saveAssistType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#assistTypeForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="assistTypeForm" method="post"	action="saveAssistType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="assistType.typeCode" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="assistType.typeName" key="assistType.typeName" name="assistType.typeName" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="assistType.filter" key="assistType.filter" name="assistType.filter" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="memo" key="assistType.memo" name="assistType.memo" />
				</div>
				<div class="unit">
				<s:textfield id="bdInfo.bdInfoName" key="assistType.bdInfo.bdInfoName" name="assistType.bdInfo.bdInfoName" />
				</div>
				<div class="unit">
				<span style="margin-left: 100px"><s:text name="assistType.disabled"></s:text>:</span><s:checkbox id="assistType.disabled" theme="simple" key="assistType.disabled" name="assistType.disabled" />
				
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





