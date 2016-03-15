<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveMenuButtonlink').click(function() {
			jQuery("#menuButtonForm").attr("action","saveMenuButton?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#menuButtonForm").submit();
		});
	});
	function checkButtonId(obj){
		// 
		checkId(obj,'MenuButton','id','此按钮ID已存在');
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="menuButtonForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<%-- <div>
					<s:hidden key="menuButton.id"/>
				</div> --%>
				<div class="unit">
					<s:textfield key="menuButton.menuId" name="menuButton.menuId" cssClass="readonly" readonly="true"/>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="menuButton.id" name="menuButton.id" cssClass="required" onblur="checkButtonId(this)"/>
					</s:if>
					<s:else>
						<s:textfield key="menuButton.id" name="menuButton.id" cssClass="readonly" readonly="true"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield id="menuButton_buttonLabel" key="menuButton.buttonLabel" name="menuButton.buttonLabel" cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="menuButton_oorder" key="menuButton.order" name="menuButton.oorder" cssClass="required digits"/>
				</div>
				<div class="unit">
					<s:textfield key="menuButton.className" name="menuButton.className" cssClass="required"/>
				</div>
				<%-- <div class="unit">
					<s:select list="#{'button':'按钮','select':'下拉按钮' }" key="menuButton.buttonType"></s:select>
				</div> --%>
				<div class="unit">
					<s:textfield id="menuButton_buttonUrl" key="menuButton.buttonUrl" name="menuButton.buttonUrl" cssClass=" "/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveMenuButtonlink"><s:text name="button.save" /></button>
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





