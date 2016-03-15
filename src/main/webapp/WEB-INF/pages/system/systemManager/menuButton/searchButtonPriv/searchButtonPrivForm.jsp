<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#searchButtonPrivForm").attr("action","saveSearchButtonPriv?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#searchButtonPrivForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchButtonPrivForm" method="post"	action="saveSearchButtonPriv?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="searchButtonPriv.privId"/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPriv_roleId" key="searchButtonPriv.roleId" name="searchButtonPriv.roleId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPriv_searchLabel" key="searchButtonPriv.searchLabel" name="searchButtonPriv.searchLabel" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPriv_searchName" key="searchButtonPriv.searchName" name="searchButtonPriv.searchName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPriv_searchUrl" key="searchButtonPriv.searchUrl" name="searchButtonPriv.searchUrl" cssClass="				
				
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





