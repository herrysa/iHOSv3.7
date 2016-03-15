<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveIndicatorTypeLink').click(function() {
			jQuery("#indicatorTypeForm").attr("action","saveIndicatorType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#indicatorTypeForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="indicatorTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="indicatorType.code" name="indicatorType.code" cssClass="required" onblur="checkId(this,'IndicatorType','code','此编码已存在')"/>
					</s:if>
					<s:else>
						<s:textfield key="indicatorType.code" name="indicatorType.code" cssClass="required readonly" readonly="true"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="indicatorType.name" name="indicatorType.name" cssClass="required"/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveIndicatorTypeLink"><s:text name="button.save" /></button>
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





