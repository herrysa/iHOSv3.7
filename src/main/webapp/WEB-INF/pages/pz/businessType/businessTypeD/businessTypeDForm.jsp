<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#businessTypeDForm").attr("action","saveBusinessTypeD?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessTypeDForm").submit();
		});*/
		//alert(jQuery("#businessTypeD_businessId").val());
		
		jQuery("#businessTypeD_rowToCol").click(function() {
			var accColIsHave = "${accColIsHave}";
			if(jQuery(this).is(":checked")) {
				if(accColIsHave == "true") {
					jQuery(this).attr("checked",false);
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				jQuery("#businessTypeD_accCol").attr("checked",true);
			} else {
				if(accColIsHave == "true") {
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
			}
			/* if(flag) {
				jQuery("#businessTypeD_accCol").attr("checked",true);
				var sqlStr = jQuery("#businessTypeD_acctTableSql").val();
				jQuery("#businessTypeD_sourceTable").val(sqlStr);
				jQuery("#businessTypeD_sourceTable").attr("readonly","true");
			} */
		});
		
		jQuery("#businessTypeD_accCol").click(function() {
			var accColIsHave = "${accColIsHave}";
			var flag = jQuery("#businessTypeD_rowToCol").is(":checked");
			if(jQuery(this).is(":checked")) {
				if(accColIsHave == "true") {
					jQuery(this).attr("checked",false);
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				var sqlStr = jQuery("#businessTypeD_acctTableSql").val();
				jQuery("#businessTypeD_sourceTable").val(sqlStr);
				jQuery("#businessTypeD_sourceTable").attr("readonly","true");
				if(flag) {
					jQuery("#businessTypeD_fieldName").removeAttr("readonly");
					return;
				} else {
					jQuery("#businessTypeD_fieldName").val("acctId");
					jQuery("#businessTypeD_fieldName").attr("readonly","true");
				}
			} else {
				if(accColIsHave == "true") {
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				jQuery("#businessTypeD_sourceTable").removeAttr("readonly");
				jQuery("#businessTypeD_fieldName").removeAttr("readonly");
				if(flag) {
					jQuery(this).attr("checked",true);
				}
			}
		});
		if("${entityIsNew}" == "true") {
			var dataSourceType = "${dataSourceType}";
			jQuery("#businessTypeD_dataSourceType").val(dataSourceType);
			var businessId = "${businessId}";
			var sn = "${businessTypeD.sn}";
			var jId = businessId + "_D" + sn;
			jQuery("#businessTypeD_id").val(jId);
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessTypeDForm" method="post"	action="saveBusinessTypeD?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden name="businessTypeD.businessType.businessId" value="%{businessId}" id="businessTypeD_businessId" />
				<input type="hidden" id="businessTypeD_acctTableSql" value="<s:property value='acctTableSql' escapeHtml="true" />" />
				<div class="unit">
				<s:if test="%{!entityIsNew}">
						<s:textfield key="businessTypeD.id" cssClass="" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield id="businessTypeD_id" key="businessTypeD.id" cssClass="required"
							notrepeat='贷方ID已存在 '
							validatorParam="from BusinessTypeD type where type.id=%value% and type.businessType.businessId = '${businessId }'" />
					</s:else>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
						<s:textfield id="businessTypeD_colName"
							key="businessTypeD.colName" name="businessTypeD.colName"
							cssClass="required" notrepeat='显示名称已存在 ' oldValue="'${businessTypeD.colName }'"
							validatorParam="from BusinessTypeD type where type.colName=%value% and businessType.businessId = '${businessTypeD.businessType.businessId}'" />
					</s:if>
					<s:else>
						<s:textfield id="businessTypeD_colName"
							key="businessTypeD.colName" name="businessTypeD.colName"
							cssClass="required" notrepeat='显示名称已存在 '
							validatorParam="from BusinessTypeD type where type.colName=%value% and businessType.businessId = '${businessId}'" />
					</s:else></div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
					<s:textfield id="businessTypeD_fieldName"
						key="businessTypeD.fieldName" name="businessTypeD.fieldName" readonly="true" />
					</s:if>
					<s:else>
					<s:textfield id="businessTypeD_fieldName"  notrepeat='对照字段名已存在 '
							validatorParam="from BusinessTypeD type where type.fieldName=%value% and businessType.businessId = '${businessId}'" 
						key="businessTypeD.fieldName" name="businessTypeD.fieldName"
						cssClass="required" />
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="businessTypeD_sn" key="businessTypeD.sn" name="businessTypeD.sn" cssClass="required digits"/>
				</div>
				<div class="unit">
				<s:select id="businessTypeD_dataSourceType" list="#{'0':'本地','1':'中间库','2':'财务库'}" listKey="key" listValue="value" emptyOption="false" key="businessTypeD.dataSourceType" name="businessTypeD.dataSourceType"></s:select>
				</div>
				<div class="unit">
				<s:textarea  id="businessTypeD_sourceTable" key="businessTypeD.sourceTable" name="businessTypeD.sourceTable" cssClass="required"  cssStyle="width:220px;height:45px;"/>
				</div>
				<div class="unit">
				<label><s:text name="businessTypeD.rowToCol" />:</label>
				<s:checkbox id="businessTypeD_rowToCol" key="businessTypeD.rowToCol" name="businessTypeD.rowToCol" theme="simple" />
				</div>
				<div class="unit">
				<label><s:text name="businessTypeD.accCol" />:</label>
				<s:checkbox id="businessTypeD_accCol" key="businessTypeD.accCol" name="businessTypeD.accCol" theme="simple" />
				</div>
				<%-- <s:if test="%{!entityIsNew}"> --%>
				<div class="unit">
				<label><s:text name="businessTypeD.disabled" />:</label>
				<s:checkbox id="businessTypeD_disabled" key="businessTypeD.disabled" name="businessTypeD.disabled" theme="simple" />
				</div>
				<%-- </s:if> --%>
				<div class="unit">
				<s:textarea id="businessTypeD_remark" key="businessTypeD.remark" name="businessTypeD.remark" cssStyle="width:220px;height:40px;" />
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





