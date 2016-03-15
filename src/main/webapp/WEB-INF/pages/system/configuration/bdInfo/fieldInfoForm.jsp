<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		adjustFormInput("fieldInfoForm");
	});
	/*保存*/
	function fieldInfoFormSave() {
		var urlString = "saveFieldInfo?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}";
		jQuery("#fieldInfoForm").attr("action",urlString);
		jQuery("#fieldInfoForm").submit();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="fieldInfoForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="fieldInfo.sysField"/>
					<s:hidden key="fieldInfo.statistics"/>
					<s:hidden key="fieldInfo.changer.personId"/>
					<s:hidden key="fieldInfo.changeDate"/>
					<s:hidden key="fieldInfo.statisticsSingle"/>
					<s:hidden key="fieldInfo.bdInfo.bdInfoId"/>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield key="fieldInfo.fieldId" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield key="fieldInfo.fieldId" cssClass="required" maxlength="50" notrepeat='id已存在 ' validatorParam="from FieldInfo field where field.fieldId=%value%"/>
					</s:else>
					<s:textfield key="fieldInfo.fieldCode" cssClass="required" maxlength="50"/>
				</div>
				<div class="unit">
					<s:textfield key="fieldInfo.fieldName" cssClass="required" maxlength="50"/>
					<s:textfield key="fieldInfo.entityFieldName" maxlength="50"/>
				</div>
				<div class="unit">
					<label><s:text name="bdInfo.tableName"/></label>
					<span class="formspan" style="float: left; width: 140px">
						<input type="text" value="${fieldInfo.bdInfo.tableName}" readonly="readonly">
					</span>
					<label><s:text name='fieldInfo.isPkCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isPkCol" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<s:textfield key="fieldInfo.sn" cssClass="digits"/>
					<s:select key="fieldInfo.dataType" list="#{'':'--','1':'字符型','2':'浮点型','3':'布尔型','4':'日期型','5':'整数型','6':'货币型','7':'图片型'}" style="width:140px" ></s:select>
				</div>
				<div class="unit">
					<s:textfield key="fieldInfo.dataLength" cssClass="digits"/>
					<s:textfield key="fieldInfo.dataDecimal" cssClass="digits"/>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isNameCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isNameCol" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isCodeCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isCodeCol" theme="simple" />
					</span>
					
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isCnCodeCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isCnCodeCol" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isDisabledCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isDisabledCol" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isOrgCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isOrgCol" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isDeptCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isDeptCol" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isCopyCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isCopyCol" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isPeriodYearCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isPeriodYearCol" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isPeriodMonthCol'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isPeriodMonthCol" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isParentPk'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isParentPk" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.isTypePk'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isTypePk" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.isFk'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.isFk" theme="simple" />
					</span>
					<s:textfield key="fieldInfo.fkTable" maxlength="20"/>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.readable'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.readable" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.notNull'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.notNull" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<s:textfield key="fieldInfo.userTag" maxlength="50"/>
					<s:textfield key="fieldInfo.fieldDefault" maxlength="50"/>
				</div>
				<div class="unit">
					<label><s:text name='fieldInfo.batchEdit'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.batchEdit" theme="simple" />
					</span>
					<label><s:text name='fieldInfo.disabled'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="fieldInfo.disabled" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<s:textarea key="fieldInfo.parameter1" name="fieldInfo.parameter1" cssStyle="width:350px;height:60px;" />
				</div>
				<div class="unit">
					<s:textarea key="fieldInfo.parameter2" name="fieldInfo.parameter2" cssStyle="width:350px;height:60px;" />
				</div>
				<div class="unit">
					<s:textarea key="fieldInfo.remark" name="fieldInfo.remark" cssStyle="width:350px;height:60px;" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="fieldInfoFormSave()"><s:text name="button.save" /></button>
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





