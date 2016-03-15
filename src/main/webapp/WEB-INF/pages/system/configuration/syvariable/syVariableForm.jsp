<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#syVariableForm").attr("action","saveSyVariable?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#syVariableForm").submit();
		});*/
		jQuery("#syVariable_classCode").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"select classCode id,className name,ISNULL(parentId,'-1') parent from t_privilegeClass where disabled=0 order by sn",
			exceptnullparent:false,
			selectParent:true,
			lazy:false
		});
	});
	function syVariableFormSaveCallBack(data) {
		formCallBack(data);
		reloadSyVariableGrid();
	}
	function syVariableFormSaveFunction() {
		var classCode = jQuery("#syVariable_classCode_id").val();
		var rightType = jQuery("#syVariable_rightType").val();
		var dataFormat = jQuery("#syVariable_dataFormat").val();
		if(classCode) {
			if(!rightType){
				jQuery("#syVariable_rightType").val("2");
			}
			if(!dataFormat){
				jQuery("#syVariable_dataFormat").val("1");
			}
		}
		jQuery("#syVariableForm").submit();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="syVariableForm" method="post"	action="saveSyVariable?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,syVariableFormSaveCallBack);">
			<div class="pageFormContent" layoutH="56">
					<div class="unit">
					<s:if test="%{!entityIsNew}">
					<s:textfield key="syVariable.variableName" cssStyle="width:146px" readonly="true" cssClass="required"/>
					<s:textfield key="syVariable.sn" cssStyle="width:146px" readonly="true"> </s:textfield>
					</s:if>
					<s:else>
						<s:textfield key="syVariable.variableName" cssStyle="width:146px" cssClass="required" notrepeat='变量名称已存在' validatorParam="from Variable sy where sy.variableName=%value%"/>
						<s:textfield key="syVariable.sn" cssStyle="width:146px" cssClass="required" notrepeat='序号已存在' validatorParam="from Variable sy where sy.sn=%value%"> </s:textfield>
					</s:else>
					</div>
					<div class="unit">
						<label><s:text name="syVariable.classCode"></s:text>:</label>
						<input type="text" id="syVariable_classCode" value="${syVariable.privaleClass.className}" style="width:146px"/>
						<input type="hidden" id="syVariable_classCode_id" name="syVariable.privaleClass.classCode" value="${syVariable.privaleClass.classCode}"/>
							<label><s:text name='syVariable.sys'/>:</label>
							<span class="formspan" style="float: left;">
								<s:checkbox id="syVariable_sys" key="syVariable.sys" theme="simple" disabled="false"/>  
							</span>
					</div>
					<div class="unit">
						<s:select list="#{'其他':'其他','期间':'期间','组织结构':'组织结构','结账状态':'结账状态','数据权限':'数据权限','环境变量':'环境变量' }"
							key="syVariable.variableType" id="syVariable_variableType" listKey="key"
							listValue="value" emptyOption="false" maxlength="50" cssStyle="width:152px" cssClass="textInput"></s:select>
						<s:select list="#{'':'','1':'登录单位','2':'登录帐套','3':'登录期间年' }"
							key="syVariable.variableConstraint" id="syVariable_variableConstraint" listKey="key"
							listValue="value" emptyOption="false" maxlength="50" cssStyle="width:152px" cssClass="textInput"></s:select>
					</div>
					<div class="unit">
						<s:select list="#{'':'','1':'读','2':'写' }"
							key="syVariable.rightType" id="syVariable_rightType" listKey="key"
							listValue="value" emptyOption="false" maxlength="50" cssStyle="width:152px" cssClass="textInput"></s:select>
						<s:select list="#{'':'','1':'逗号字符串','2':'sql形式' }"
							key="syVariable.dataFormat" id="syVariable_dataFormat" listKey="key"
							listValue="value" emptyOption="false" maxlength="50" cssStyle="width:152px" cssClass="textInput"></s:select>
					</div>
					<div class="unit">
						<label><s:text name="syVariable.variableDesc"></s:text>:</label>
						<s:textarea id="syVariable_variableDesc" name="syVariable.variableDesc" cssStyle="width:292px;height: 45px;"></s:textarea>
					</div>
					<div  class="unit">
						<label><s:text name="syVariable.value"></s:text>:</label>
						<s:textarea id="syVariable_value" name="syVariable.value" cssStyle="width:292px;height: 45px;" readonly="true"></s:textarea>
					</div>
					<div class="unit">
						<label><s:text name="syVariable.sqlExpression"></s:text>:</label>
						<s:textarea id="syVariable_sqlExpression" name="syVariable.sqlExpression" cssStyle="width:292px;height: 70px;" readonly="true"></s:textarea>
					</div>
					<div class="unit">
						<label><s:text name="syVariable.remark"></s:text>:</label>
						<s:textarea id="syVariable_remark" name="syVariable.remark" cssStyle="width:292px;height: 45px;" ></s:textarea>
					</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button onclick="javascript:syVariableFormSaveFunction();" type="Button"><s:text name="button.save" /></button>
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





