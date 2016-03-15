<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#sysTableKindForm").attr("action","saveSysTableKind?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#sysTableKindForm").submit();
		});
		if("${sysTableKind.sysFiled}"=="true"){
			jQuery(".sysTableKindFormInput").attr('readOnly',"true").attr("onfocus","").attr("onclick","").attr("onclick","").attr("onblur","");
			jQuery('#sysTableKind_disabled').click(function() {
				return false;
			});
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sysTableKindForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
			   <div>
			   <s:hidden key="sysTableKind.id"/>
			   <s:hidden key="sysTableKind.sysFiled"/>
			   <s:hidden name="editType" value="%{editType}" />										
						<s:if test="%{editType==1}">
							<s:textfield key="sysTableKind.code" id="sysTableKind_code" cssClass="required"	maxlength="30" readonly="true" />
							<s:textfield key="sysTableKind.name" required="false" maxlength="50" cssClass="required input-small sysTableKindFormInput" oldValue="${sysTableKind.name}" notrepeat='名称已存在' validatorParam="from SysTableKind kind where kind.name=%value%"/>
						</s:if>
						<s:else>
							<s:textfield key="sysTableKind.code" id="sysTableKind_code" cssClass="required" notrepeat="类型号已存在" validatorParam="from SysTableKind kind where kind.code=%value%"	maxlength="30" />
							<s:textfield key="sysTableKind.name" required="false" maxlength="50" cssClass="required input-small sysTableKindFormInput" notrepeat="名称已存在" validatorParam="from SysTableKind kind where kind.name=%value%"/>
						</s:else>
			   </div>
			    <div class="unit">
			   <s:textfield key="sysTableKind.sn" required="false"
							 cssClass="input-small sysTableKindFormInput" />
			   <s:textfield key="sysTableKind.mainTable" required="false"
							maxlength="50" cssClass="input-small sysTableKindFormInput" />
			   </div>
			   <div class="unit">
			   <s:textfield key="sysTableKind.mainTablePK" required="false"
							maxlength="50" cssClass="input-small sysTableKindFormInput" />
			   <s:textfield key="sysTableKind.mainTablePKLength" required="false"
							 cssClass="input-small sysTableKindFormInput" />
			   </div>
			   <div class="unit">
			   <label><s:text name='sysTableKind.disabled' />:</label> <span
							style="float: left; width: 140px"> <s:checkbox id="sysTableKind_disabled"
								key="sysTableKind.disabled" required="false" theme="simple" />
						</span>
			   </div>
			   <div class="unit">
						<s:textarea key="sysTableKind.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
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





