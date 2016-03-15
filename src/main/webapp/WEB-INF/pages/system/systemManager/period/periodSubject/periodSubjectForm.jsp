<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#periodSubjectForm").attr("action","savePeriodSubject?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#periodSubjectForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="periodSubjectForm" method="post"	action="" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="periodSubject.periodId"/>
				</div>
				<div class="unit">
				<s:textfield id="periodSubject_beginDate" key="periodSubject.beginDate" name="periodSubject.beginDate" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="periodSubject_endDate" key="periodSubject.endDate" name="periodSubject.endDate" cssClass=""/>
				</div>
				<div class="unit">
				</div>
				<div class="unit">
				<s:textfield id="periodSubject_periodNum" key="periodSubject.periodNum" name="periodSubject.periodNum" cssClass="digits"/>
				</div>
				<div class="unit">
				<s:textfield id="periodSubject_periodYear" key="periodSubject.periodYear" name="periodSubject.periodYear" cssClass=""/>
				</div>
				<div class="unit">
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





