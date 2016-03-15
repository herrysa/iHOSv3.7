<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#searchButtonPrivUserForm").attr("action","saveSearchButtonPrivUser?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#searchButtonPrivUserForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchButtonPrivUserForm" method="post"	action="saveSearchButtonPrivUser?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="searchButtonPrivUser.privId"/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUser_searchId" key="searchButtonPrivUser.searchId" name="searchButtonPrivUser.searchId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUser_searchLabel" key="searchButtonPrivUser.searchLabel" name="searchButtonPrivUser.searchLabel" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUser_searchName" key="searchButtonPrivUser.searchName" name="searchButtonPrivUser.searchName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUser_userId" key="searchButtonPrivUser.userId" name="searchButtonPrivUser.userId" cssClass="				
				
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





