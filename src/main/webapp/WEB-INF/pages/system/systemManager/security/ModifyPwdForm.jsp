<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<title><fmt:message key="modifyPwd.title" />
</title>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#oldPwd").attr("value", "");
	})
	function validetePwd(o) {
		var p1 = o.value;
		var p2 = jQuery("#newPwd").attr("value");
		//alert("p1:"+p1+";       p2:"+p2);
		if (p1 != p2) {
			//jQuery("#savelink").attr("disabled", true);
			alert("两次密码输入不一致");

		} else {
			//jQuery("#savelink").attr("disabled", false);
			//return true;
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form name="modifyForm" method="post" action="ModifyPwdFormSave?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<label><fmt:message key='modifyPwd.oldPwd' />
					: </label><input type="password" name="oldPwd" id="oldPwd" class="required"/>
				</div>
				<div class="unit">
					<label><fmt:message key='modifyPwd.newPwd' />
					:</label><input type="password" name="newPwd" id="newPwd" class="required" />
				</div>
				<div class="unit">
					<label><fmt:message key='modifyPwd.newSurePwd' />
					: </label><input type="password" id="newSurePwd" class="required" equalto="#newPwd"/>
				</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" id="pasSavelink">保存</button>
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
		</form>
	</div>
</div>