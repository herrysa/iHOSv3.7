<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title><fmt:message key="entityDetail.title" /></title>
<meta name="heading" content="<fmt:message key='entityDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#entityForm").attr("action","saveSearchEntity?popup=true&navTabId="+'${navTabId}');
			jQuery("#entityForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="entityForm" method="post" action="saveSearchEntity?popup=true"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">

				
<s:url id="saveEntity" action="saveEntity" />
	
	<s:hidden key="entity.entityId" name="searchEntity.entityId" />
	<div class="unit">
	<s:textfield key="entity.entityName" name="searchEntity.entityName" cssClass="required" />
</div>
<div class="unit">
	<s:textfield key="entity.entityDesc" name="searchEntity.entityDesc"  maxlength="30" />
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
