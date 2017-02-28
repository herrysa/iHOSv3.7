<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			//检查是否无形资产供应商(四个)，必填一个[如果停用则可以不填]
			var disabled = jQuery("#vendor_disabled").attr("checked");
			if(!disabled){
				if(jQuery("#vendor_isMate").attr("checked") || jQuery("#vendor_isImma").attr("checked") || jQuery("#vendor_isEquip").attr("checked") || jQuery("#vendor_isDrug").attr("checked")){
					
				}else{
					alertMsg.error("属性[是否药品供应商、是否设备供应商、是否无形资产供应商、是否物资供应商]至少勾选其一！");
					return;
				}
			}
			jQuery("#vendorForm").attr("action","saveVendor?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#vendorForm").submit();
		});
		var orgForVendorType = "${fns:userContextParam('orgCode')}";
		if(!orgForVendorType){
			orgForVendorType = "${fns:userContextParam('loginOrgId')}";
		}
		var vendorTypeSql = "SELECT  vendorTypeId id,vendorTypeName name,parent_id parent FROM sy_vendorType where parent_id is not null and disabled = 0 and orgCode='";
		vendorTypeSql += orgForVendorType+"'";
		jQuery("#vendor_vendorType").treeselect({
			dataType:"sql",
			optType:"single",
			sql:vendorTypeSql,
			exceptnullparent:false,
			lazy:false
		});
	});
	
	function checkVendorCode(obj){
		$.ajax({
		    url:"${ctx}/checkVendorCode",
		    type: 'post',
		    data:{vendorCode:obj.value},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data.exists){
		        	 alertMsg.error("供应商编码已存在！");
				     obj.value="";
		        }
		    }
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="vendorForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="38" style="padding: 0px;" >
				<div class="tabs" currentIndex="0" eventType="click" style="overflow:hidden">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>主信息</span></a></li>
								<li><a href="javascript:;"><span>辅助信息</span></a></li>
								<li><a href="javascript:;"><span>其它信息</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent pageContent" layoutH="72">
						<div>
							<div class="unit">
								<s:if test="%{entityIsNew}">
									<s:textfield id="vendor_vendorCode" key="vendor.vendorCode" name="vendor.vendorCode" cssClass="required" onblur="checkVendorCode(this)"/>
								</s:if>
								<s:else>
									<s:textfield id="vendor_vendorCode" key="vendor.vendorCode" name="vendor.vendorCode" readonly="true" cssClass="textInput readonly"/>
								</s:else>
								<s:hidden name="vendor.orgCode"/>
								<s:hidden id="vendor_vendorType_id" key="vendor.vendorType" name="vendor.vendorType.id"  />
								<s:textfield id="vendor_vendorType" key="vendor.vendorType" name="vendor.vendorType.vendorTypeName"  cssClass="required"/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_vendorName" key="vendor.vendorName" name="vendor.vendorName" cssClass="required"/>
								<s:textfield id="vendor_cnCode" key="vendor.cnCode" name="vendor.cnCode" readonly="true" cssClass="readonly"/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_shortName" key="vendor.shortName" name="vendor.shortName" cssClass=""/>
								<s:textfield id="vendor_venTrade" key="vendor.venTrade" name="vendor.venTrade" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_province" key="vendor.province" name="vendor.province" cssClass=""/>
								<s:textfield id="vendor_city" key="vendor.city" name="vendor.city" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_distCode" key="vendor.distCode" name="vendor.distCode" cssClass=""/>
								<s:textfield id="vendor_venPostCode" key="vendor.venPostCode" name="vendor.venPostCode" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venAddress" key="vendor.venAddress" name="vendor.venAddress" cssClass=""/>
								<s:textfield id="vendor_venEmail" key="vendor.venEmail" name="vendor.venEmail" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venPhone" key="vendor.venPhone" name="vendor.venPhone" cssClass=""/>
								<s:textfield id="vendor_venFax" key="vendor.venFax" name="vendor.venFax" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venPerson" key="vendor.venPerson" name="vendor.venPerson" cssClass=""/>
								<s:textfield id="vendor_venMobile" key="vendor.venMobile" name="vendor.venMobile" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_products" key="vendor.products" name="vendor.products" cssClass=""/>
								<s:textfield id="vendor_venDirAddress" key="vendor.venDirAddress" name="vendor.venDirAddress" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_trafCode" key="vendor.trafCode" name="vendor.trafCode" cssClass=""/>
								<s:textfield id="vendor_venDirCode" key="vendor.venDirCode" name="vendor.venDirCode" cssClass=""/>
							</div>
							
							<div class="unit">
								<s:textfield id="vendor_venDevDate" key="vendor.venDevDate" name="vendor.venDevDate" style="height:15px" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
								<s:textfield id="vendor_endDate" key="vendor.endDate" name="vendor.endDate" style="height:15px" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
							</div>
							<div class="unit">
								<label><s:text name="vendor.isDrug"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_isDrug" key="vendor.isDrug" name="vendor.isDrug" theme="simple"/></span>
								<label><s:text name="vendor.isEquip"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_isEquip" key="vendor.isEquip" name="vendor.isEquip" theme="simple"/></span>
							</div>
							<div class="unit">
								<label><s:text name="vendor.isImma"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_isImma" key="vendor.isImma" name="vendor.isImma" theme="simple"/></span>
								<label><s:text name="vendor.isMate"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_isMate" key="vendor.isMate" name="vendor.isMate" theme="simple"/></span>
							</div>
							<div class="unit">
								<label><s:text name="vendor.bvenTax"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_bvenTax" key="vendor.bvenTax" name="vendor.bvenTax" theme="simple"/></span>
								<label><s:text name="vendor.disabled"></s:text>:</label><span style="float: left; width: 134px">
								<s:checkbox id="vendor_disabled" key="vendor.disabled" name="vendor.disabled" theme="simple"/></span>
							</div>
							<div class="unit">
							</div>
						</div>
						<div>
							<div class="unit">
								<s:textfield id="vendor_venBank" key="vendor.venBank" name="vendor.venBank" cssClass=""/>
								<s:textfield id="vendor_venBankAccount" key="vendor.venBankAccount" name="vendor.venBankAccount" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venLperson" key="vendor.venLperson" name="vendor.venLperson" cssClass=""/>
								<s:textfield id="vendor_venRegCode" key="vendor.venRegCode" name="vendor.venRegCode" cssClass=""/>
							</div>
							<!-- 付款方式、付款条件 -->
							<div class="unit">
								<s:select list="payModeList"  listValue="payModeName" listKey="payModeId"   id="vendor_payMode" key="vendor.payMode" name="vendor.payMode.payModeId" headerKey="" headerValue="---" cssStyle="float: left; width: 134px" ></s:select>
								<s:select list="payConList"  listValue="payConName" listKey="payConId"   id="vendor_payCon" key="vendor.payCon" name="vendor.payCon.payConId" headerKey="" headerValue="---" cssStyle="float: left; width: 134px" ></s:select>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venCreDate" key="vendor.venCreDate" name="vendor.venCreDate" cssClass="digits"/>
								<s:textfield id="vendor_venCreGrade" key="vendor.venCreGrade" name="vendor.venCreGrade" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_venCreLine" key="vendor.venCreLine" name="vendor.venCreLine" cssClass="number"/>
								<s:textfield id="vendor_venDisRate" key="vendor.venDisRate" name="vendor.venDisRate" cssClass="number"/>
							</div>
							
							
							<div class="unit">
								<s:textfield id="vendor_venPperson" key="vendor.venPperson" name="vendor.venPperson" cssClass=""/>
							</div>
							
							<div class="unit">
								<s:textfield id="vendor_businessCharter" key="vendor.businessCharter" name="vendor.businessCharter" cssClass=""/>
								<s:textfield id="vendor_frequency" key="vendor.frequency" name="vendor.frequency" cssClass=""/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_accPayMoney" key="vendor.accPayMoney" name="vendor.accPayMoney" cssClass="number"/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_lastDate" key="vendor.lastDate" name="vendor.lastDate" cssClass=""/>
								<s:textfield id="vendor_lastMoney" key="vendor.lastMoney" name="vendor.lastMoney" cssClass="number"/>
							</div>
							<div class="unit">
								<s:textfield id="vendor_lastPayDate" key="vendor.lastPayDate" name="vendor.lastPayDate" cssClass=""/>
								<s:textfield id="vendor_lastPayMoney" key="vendor.lastPayMoney" name="vendor.lastPayMoney" cssClass="number"/>
							</div>
						</div>
						<div>
							<div class="unit">
								<s:hidden key="vendor.vendorId" required="true" cssClass="validate[required]"/>
							</div>
						</div>
						
					</div>
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





