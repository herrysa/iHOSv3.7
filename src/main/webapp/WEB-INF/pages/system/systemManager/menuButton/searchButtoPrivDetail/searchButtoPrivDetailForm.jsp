<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#searchButtoPrivDetailForm").attr("action","saveSearchButtoPrivDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#searchButtoPrivDetailForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchButtoPrivDetailForm" method="post"	action="saveSearchButtoPrivDetail?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="searchButtoPrivDetail.sbpDetailId"/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtoPrivDetail_order" key="searchButtoPrivDetail.order" name="searchButtoPrivDetail.order" cssClass="required 				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="searchButtoPrivDetail_right" key="searchButtoPrivDetail.right" name="searchButtoPrivDetail.right" />
				</div>
				<div class="unit">
				</div>
				<div class="unit">
				<s:textfield id="searchButtoPrivDetail_searchURL" key="searchButtoPrivDetail.searchURL" name="searchButtoPrivDetail.searchURL" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtoPrivDetail_searchURLId" key="searchButtoPrivDetail.searchURLId" name="searchButtoPrivDetail.searchURLId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="searchButtoPrivDetail_searchURLTitle" key="searchButtoPrivDetail.searchURLTitle" name="searchButtoPrivDetail.searchURLTitle" cssClass="				
				
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





