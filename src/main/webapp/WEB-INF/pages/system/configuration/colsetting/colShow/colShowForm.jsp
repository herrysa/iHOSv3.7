<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#colShowForm").attr("action","saveColShow?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#colShowForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="colShowForm" method="post"	action="saveColShow?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="colShow.id"/>
				</div>
				<div class="unit">
				<s:textfield id="colShow_col" key="colShow.col" name="colShow.col" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colShow_label" key="colShow.label" name="colShow.label" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="colShow_order" key="colShow.order" name="colShow.order" cssClass="required 				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="colShow_show" key="colShow.show" name="colShow.show" />
				</div>
				<div class="unit">
				<s:textfield id="colShow_templetName" key="colShow.templetName" name="colShow.templetName" cssClass="required 				
				
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





