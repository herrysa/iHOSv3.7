<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#businessTypeJForm").attr("action","saveBusinessTypeJ?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessTypeJForm").submit();
		});*/
		jQuery("#businessTypeJ_rowToCol").click(function() {
			var accColIsHave = "${accColIsHave}";
			if(jQuery(this).is(":checked")) {
				if(accColIsHave == "true") {
					jQuery(this).attr("checked",false);
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				jQuery("#businessTypeJ_accCol").attr("checked",true);
				/* var sqlStr = jQuery("#businessTypeJ_acctTableSql").val();
				alert(sqlStr.length);
				jQuery("#businessTypeJ_sourceTable").val(sqlStr);
				jQuery("#businessTypeJ_sourceTable").attr("readonly","true"); */
			} else {
				if(accColIsHave == "true") {
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
			}
		}); 
		jQuery("#businessTypeJ_accCol").click(function() {
			var accColIsHave = "${accColIsHave}";
			var flag = jQuery("#businessTypeJ_rowToCol").is(":checked");
			if(jQuery(this).is(":checked")) {
				if(accColIsHave == "true") {
					jQuery(this).attr("checked",false);
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				
				var sqlStr = jQuery("#businessTypeJ_acctTableSql").val();
				jQuery("#businessTypeJ_sourceTable").val(sqlStr);
				jQuery("#businessTypeJ_sourceTable").attr("readonly","true");
				if(flag) {
					jQuery("#businessTypeJ_fieldName").removeAttr("readonly");
					return;
				} else {
					jQuery("#businessTypeJ_fieldName").val("acctId");
					jQuery("#businessTypeJ_fieldName").attr("readonly","true");
				}
			} else {
				if(accColIsHave == "true") {
					alertMsg.error("只能配置一个会计科目列。");
					return;
				}
				jQuery("#businessTypeJ_sourceTable").removeAttr("readonly");
				jQuery("#businessTypeJ_fieldName").removeAttr("readonly");
				if(flag) {
					jQuery(this).attr("checked",true);
				}
			}
		});
		if("${entityIsNew}" == "true") {
			var dataSourceType = "${dataSourceType}";
			jQuery("#businessTypeJ_dataSourceType").val(dataSourceType);
			var businessId = "${businessId}";
			var sn = "${businessTypeJ.sn}";
			var jId = businessId + "_J" + sn;
			jQuery("#businessTypeJ_id").val(jId);
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessTypeJForm" method="post"
			action="saveBusinessTypeJ?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden name="businessTypeJ.businessType.businessId"
					value="%{businessId}" id="businessTypeJ_businessId" />
				<input type="hidden" id="businessTypeJ_acctTableSql" value="<s:property value='acctTableSql' escapeHtml="true" />" />
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield key="businessTypeJ.id" cssClass="" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield id="businessTypeJ_id" key="businessTypeJ.id" cssClass="required"
							notrepeat='借方ID已存在 '
							validatorParam="from BusinessTypeJ type where type.id=%value% and type.businessType.businessId = '${businessId }'" />
					</s:else>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id="businessTypeJ_colName"
							key="businessTypeJ.colName" name="businessTypeJ.colName"
							cssClass="required" notrepeat='显示名称已存在 ' oldValue="'${businessTypeJ.colName }'"
							validatorParam="from BusinessTypeJ type where type.colName=%value% and businessType.businessId = '${businessTypeJ.businessType.businessId}'" />
					</s:if>
					<s:else>
						<s:textfield id="businessTypeJ_colName"
							key="businessTypeJ.colName" name="businessTypeJ.colName"
							cssClass="required" notrepeat='显示名称已存在 '
							validatorParam="from BusinessTypeJ type where type.colName=%value% and businessType.businessId = '${businessId}'" />
					</s:else>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
					<s:textfield id="businessTypeJ_fieldName"
						key="businessTypeJ.fieldName" name="businessTypeJ.fieldName" readonly="true" />
					</s:if>
					<s:else>
					<s:textfield id="businessTypeJ_fieldName" notrepeat='对照字段名已存在 '
							validatorParam="from BusinessTypeJ type where type.fieldName=%value% and businessType.businessId = '${businessId}'" 
						key="businessTypeJ.fieldName" name="businessTypeJ.fieldName" 
						cssClass="required" />
					</s:else>
				</div>
				<div class="unit">
					<s:textfield id="businessTypeJ_sn" key="businessTypeJ.sn"
						name="businessTypeJ.sn" cssClass="required digits" />
				</div>
				<div class="unit">
					<s:select id="businessTypeJ_dataSourceType"
						list="#{'0':'本地','1':'中间库','2':'财务库'}" listKey="key"
						listValue="value" emptyOption="false"
						key="businessTypeJ.dataSourceType"
						name="businessTypeJ.dataSourceType"></s:select>
				</div>
				<div class="unit">
					<s:textarea id="businessTypeJ_sourceTable"
						key="businessTypeJ.sourceTable" name="businessTypeJ.sourceTable"
						cssClass="required" cssStyle="width:220px;height:45px;" />
				</div>
				<div class="unit">
					<label><s:text name="businessTypeJ.rowToCol" />:</label>
					<s:checkbox id="businessTypeJ_rowToCol"
						key="businessTypeJ.rowToCol" name="businessTypeJ.rowToCol"
						theme="simple" />
				</div>
				<div class="unit">
					<label><s:text name="businessTypeJ.accCol" />:</label>
					<s:checkbox id="businessTypeJ_accCol" key="businessTypeJ.accCol"
						name="businessTypeJ.accCol" theme="simple" />
				</div>
				<%-- <s:if test="%{!entityIsNew}"> --%>
				<div class="unit">
					<label><s:text name="businessTypeJ.disabled" />:</label>
					<s:checkbox id="businessTypeJ_disabled"
						key="businessTypeJ.disabled" name="businessTypeJ.disabled"
						theme="simple" />
				</div>
				<%-- </s:if> --%>
				<div class="unit">
					<s:textarea id="businessTypeJ_remark" key="businessTypeJ.remark"
						name="businessTypeJ.remark" cssStyle="width:220px;height:40px;" />
				</div>

			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<s:text name="button.save" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





