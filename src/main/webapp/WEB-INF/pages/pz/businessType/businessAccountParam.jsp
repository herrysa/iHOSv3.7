<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#${random}businessAccountParam_gridtable_save").click(function() {
			if(jQuery("#${random}businessAccountParam_voucherType").val()) {
				jQuery("#${random}businessAccountParam_form").submit();
			}
		});
	});
</script>
<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="${random}businessAccountParam_gridtable_save" class="savebutton" href="javaScript:" ><span><fmt:message
								key="button.save" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div class="pageFormContent">
		<form id="${random}businessAccountParam_form" action="saveAccountParam" onsubmit="return validateCallback(this,formCallBack);" method="post">
			<s:hidden key="businessId"/>
			<label><s:text name="凭证类型" />:</label>
			<s:select id="%{random}businessAccountParam_voucherType" key="voucherType" name="voucherType" list="voucherMap" listKey="key" listValue="value" emptyOption="true" theme="simple"></s:select>
		</form>
		</div>
	</div>
</div>