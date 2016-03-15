<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#branchForm").attr("action","saveBranch?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#branchForm").submit();
		});*/
	});
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="branchForm" method="post"	action="saveBranch?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,branchFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="branch.orgCode"/>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield key="branch.branchCode" notrepeat='院区编码已存在' validatorParam="from Branch b where b.branchCode=%value%" cssClass="required"/>
				</s:if>
				<s:else>
					<s:textfield key="branch.branchCode" readonly="true" cssClass="required"/>
				</s:else>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
					<s:textfield id="branch_branchName" key="branch.branchName" name="branch.branchName" notrepeat='院区名称已存在' validatorParam="from Branch b where b.branchName=%value%" cssClass="required"/>
				</s:if>
				<s:else>
					<s:textfield id="branch_branchName" key="branch.branchName" name="branch.branchName" oldValue="'${branch.branchName}'" notrepeat='院区名称已存在' validatorParam="from Branch b where b.branchName=%value%" cssClass="required"/>
				</s:else>
				</div>
				<s:if test="%{!entityIsNew}">
					<div class="unit">
						<label><s:text name="branch.disabled"/>:</label>
						<s:checkbox id="branch_disabled" key="branch.disabled" name="branch.disabled" theme="simple" />
					</div>
				</s:if>
				<s:else>
					<s:hidden key="branch.disabled"/>
				</s:else>
				<%-- <div class="unit">
				<s:textfield id="branch_orgCode" key="branch.orgCode" name="branch.orgCode" cssClass=""/>
				</div> --%>
			
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





