<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}saveLink').click(function() {
			if(!jQuery("#businessType_leaf").is(":checked")) {
				jQuery("#businessType_searchName").removeClass("required");
			}
			jQuery("#businessTypeForm").attr("action","saveBusinessType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessTypeForm").submit();
		});
		jQuery("#businessType_businessId").blur(function() {
			var businessId = jQuery(this).val();
			if(businessId) {
				jQuery.ajax({
					url : 'checkBusinessTypeAdd',
					dataType : 'json',
					type : 'post',
					data : {businessId : businessId},
					error : function(data) {
						
					},
					success : function(data) {
						if(data.message) {
							alertMsg.error(data.message);
							jQuery("#businessType_businessId").val('');
							return;
						}
					}
				});
			}
		});
	});
	function businessTypeFormCallBack(data) {
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var treeNode = data.treeNode;
		if(!treeNode){
			return ;
		}
		if("${entityIsNew}"=="true"){
			dealTreeNodeC("businessTypeLeftTree",treeNode,"add");
		}else{
			dealTreeNodeC("businessTypeLeftTree",treeNode,"change");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessTypeForm" method="post"	action="saveBusinessType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,businessTypeFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="businessType.parentType.businessId" />
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="businessType_businessId" key="businessType.businessId" cssClass="" readonly="true"/>
				</s:if>
				<s:else>
				<s:textfield id="businessType_businessId" key="businessType.businessId" cssClass="required" notrepeat='业务ID已存在 ' validatorParam="from BusinessType type where type.businessId=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="businessType_businessName" key="businessType.businessName" name="businessType.businessName" cssClass="required" oldValue="'${businessType.businessName}'" notrepeat='业务名称已存在 ' validatorParam="from BusinessType type where type.businessName=%value%" />
				</s:if>
				<s:else>
				<s:textfield id="businessType_businessName" key="businessType.businessName" name="businessType.businessName" cssClass="required" notrepeat='业务名称已存在 ' validatorParam="from BusinessType type where type.businessName=%value%" />
				</s:else>
				</div>
				<div class="unit">
				<s:select list="#{'0':'本地','1':'中间库'}" listKey="key" listValue="value" emptyOption="false" key="businessType.dataSourceType" name="businessType.dataSourceType"></s:select>
				</div>
				<%-- <div class="unit">
				<s:textarea  key="businessType.contrastTable" name="businessType.contrastTable" cssClass="required" cssStyle="width:220px;height:35px;" />
				</div> --%>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="businessType_sn" key="businessType.sn" name="businessType.sn" cssClass="required digits" oldValue="${businessType.sn}" notrepeat='序号已存在 ' validatorParam="from BusinessType type where type.sn=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="businessType_sn" key="businessType.sn" name="businessType.sn" cssClass="required digits" notrepeat='序号已存在 ' validatorParam="from BusinessType type where type.sn=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name="businessType.leaf" />:</label>
				<s:checkbox id="businessType_leaf" key="businessType.leaf" name="businessType.leaf" theme="simple" />
				</div>
				<div class="unit">
				<s:textfield id="businessType_searchName" key="businessType.searchName" name="businessType.searchName" cssClass="required"/>
				</div>
				<div class="unit">
				<s:textfield id="businessType_collectTempTable" key="businessType.collectTempTable" name="businessType.collectTempTable" />
				</div>
				<div class="unit">
				<s:textfield id="businessType_icon" key="businessType.icon" name="businessType.icon" cssClass=""/>
				</div>
				<s:if test="%{!entityIsNew}">
				<div class="unit">
				<label><s:text name="businessType.disabled" />:</label>
				<s:checkbox id="businessType_disabled" key="businessType.disabled" name="businessType.disabled" theme="simple" />
				</div>
				</s:if>
				<div class="unit">
				<s:textarea id="businessType_remark" key="businessType.remark" name="businessType.remark" cssStyle="width:220px;height:40px;" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="${random}saveLink"><s:text name="button.save" /></button>
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





