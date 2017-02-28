<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#projForm").attr("action","saveProj?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#projForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="projForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="proj.projId"  cssClass="validate[required]"/>
				</div>
				<div class="unit">
					<s:textfield id="proj_projCode" key="proj.projCode" name="proj.projCode" cssClass=""  readonly="!entityIsNew"/>
					<s:textfield id="proj_projName" key="proj.projName" name="proj.projName" cssClass=""/>
				</div>
				<div class="unit">
					<s:textfield id="proj_beginDate" key="proj.beginDate" name="proj.beginDate" cssClass="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
					<s:textfield id="proj_endDate" key="proj.endDate" name="proj.endDate" cssClass="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
				</div>
				<div class="unit">
					<s:textfield id="proj_principal" key="proj.principal" name="proj.principal" cssClass=""/>
					<s:select list="projTypeList" key="projType.typeName" listKey="typeId" listValue="typeName" name="proj.projType.typeId" value="proj.projType.typeId" cssStyle="width:135px"></s:select>
				</div>
				<div class="unit">
					<s:select list="projNatureList" key="projNature.natureName" listKey="natureId" listValue="natureName" name="proj.projNature.natureId" value="proj.projNature.natureId" cssStyle="width:133px"></s:select>
					<s:select list="projUseList" key="projUse.useName" listKey="useCode" listValue="useName" name="proj.projUse.useCode" value="proj.projUse.useId" cssStyle="width:135px"></s:select>
				</div>
				<div class="unit">
					<span style="margin-left: 100px"><s:text name="proj.disabled"></s:text>:</span><s:checkbox  key="proj.disabled" name="proj.disabled" theme="simple" />
					<span style="margin-left: 20px"><s:text name="proj.leaf"></s:text>:</span><s:checkbox  key="proj.leaf" name="proj.leaf" theme="simple" />
				</div>
				<div class="unit">
				<s:textarea id="proj_memo" key="proj.memo" name="proj.memo" cssClass="" cssStyle="width:392px"/>
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





