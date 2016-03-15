<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#assign dateExists = false>
<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#${pojoNameLower}Form").attr("action","save${pojo.shortName}?popup=true&navTabId="+'${'$'}{navTabId}&entityIsNew=${'$'}{entityIsNew}');
			jQuery("#${pojoNameLower}Form").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${pojoNameLower}Form" method="post"	action="save${pojo.shortName}?popup=true&navTabId=${'$'}{navTabId}&entityIsNew=${'$'}{entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
			<#foreach field in pojo.getAllPropertiesIterator()>
				<div class="unit">
				<#if field.equals(pojo.identifierProperty)>
				    <#assign idFieldName = field.name>
				    <#if field.value.identifierGeneratorStrategy == "assigned">
				<s:textfield key="${pojoNameLower}.${field.name}" required="true" cssClass="validate[required]"/>
				    <#else>
				<s:hidden key="${pojoNameLower}.${field.name}"/>
				    </#if>
				<#elseif !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
				    <#foreach column in field.getColumnIterator()>
				        <#if field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean">
				<s:checkbox id="${pojoNameLower}_${field.name}" key="${pojoNameLower}.${field.name}" name="${pojoNameLower}.${field.name}" />
				        <#else>
				<s:textfield id="${pojoNameLower}_${field.name}" key="${pojoNameLower}.${field.name}" name="${pojoNameLower}.${field.name}" cssClass="<#if !column.nullable>required <#else></#if>
				<#if  field.value.typeName == "float" || field.value.typeName == "double" ||  field.value.typeName == "java.lang.Float" || field.value.typeName == "java.lang.Double" || field.value.typeName == "java.math.BigDecimal">number</#if>
				<#if  field.value.typeName == "int" || field.value.typeName == "long" ||  field.value.typeName == "short" || field.value.typeName == "java.lang.Integer" || field.value.typeName == "java.lang.Long" || field.value.typeName == "java.lang.Short">digits</#if>
				       "/>
				       </#if>
				    </#foreach>
				<#elseif c2h.isManyToOne(field)>
				    <#foreach column in field.getColumnIterator()>
				            <#lt/>    <!-- todo: change this to read the identifier field from the other pojo -->
				            <#lt/>    <!-- <s:select name="${pojoNameLower}.${field.name}.id" list="${field.name}List" listKey="id" listValue="id"></s:select> -->
				    </#foreach>
				</#if>
				</div>
			</#foreach>	
			
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





