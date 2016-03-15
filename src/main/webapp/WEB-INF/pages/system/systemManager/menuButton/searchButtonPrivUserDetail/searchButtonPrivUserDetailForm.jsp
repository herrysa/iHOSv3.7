<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#searchButtonPrivUserDetailForm").attr("action","saveSearchButtonPrivUserDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#searchButtonPrivUserDetailForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchButtonPrivUserDetailForm" method="post"	action="saveSearchButtonPrivUserDetail?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="searchButtonPrivUserDetail.sbpDetailId"/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUserDetail_order" key="searchButtonPrivUserDetail.order" name="searchButtonPrivUserDetail.order" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="searchButtonPrivUserDetail_right" key="searchButtonPrivUserDetail.right" name="searchButtonPrivUserDetail.right" />
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="searchButtonPrivUserDetail.searchButtonPrivUser.id" list="searchButtonPrivUserList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUserDetail_searchURL" key="searchButtonPrivUserDetail.searchURL" name="searchButtonPrivUserDetail.searchURL" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUserDetail_searchURLId" key="searchButtonPrivUserDetail.searchURLId" name="searchButtonPrivUserDetail.searchURLId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtonPrivUserDetail_searchURLTitle" key="searchButtonPrivUserDetail.searchURLTitle" name="searchButtonPrivUserDetail.searchURLTitle" cssClass="				
				
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





