<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#invBillNumberSetting_needPrefix').change(function() { 
			var checked = $(this).attr("checked");
			if(checked){
				jQuery('#invBillNumberSetting_prefix').addClass("required").removeAttr("readonly");
			}else{
				jQuery('#invBillNumberSetting_prefix').val("");
				jQuery('#invBillNumberSetting_prefix').attr("readonly","true").removeClass("required");
			}
		}); 
		jQuery('#savelink').click(function() {
			jQuery("#invBillNumberSettingForm").attr("action","saveInvBillNumberSetting?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#invBillNumberSettingForm").submit();
		});
	});
	
	function checkSerialCode(obj){
		$.ajax({
		    url:"${ctx}/checkSerialCode",
		    type: 'post',
		    data:{serialCode:obj.value},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data.exists){
		        	 alertMsg.error("序列号编码已存在！");
				     obj.value="";
		        }
		    }
		});
	}
	function checkSerialLength(obj){
		if(!(/^[4-9]*$/.test(obj.value) || obj.value==10)){
			alertMsg.error("长度需在4-10之间！");
		    obj.value="";
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="invBillNumberSettingForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:select id="invBillNumberSetting_serialCode" key="invBillNumberSetting.serialCode" 
						 	name="invBillNumberSetting.serialCode"  cssStyle="float: left; width: 100px" cssClass="required" 
						 	listKey="key" listValue="value" list="#{'MMRK':'材料入库','MMPD':'库存盘点','MMCK':'材料出库','DPSL':'科室申领','MMYK':' 材料移库','CGJH':'采购计划','KSXQ':'科室需求','CGDD':'采购订单','MRS':'需求汇总'}" />
					</s:if>
					<s:else>
						<s:textfield id="invBillNumberSetting_serialCode" key="invBillNumberSetting.serialCode" name="invBillNumberSetting.serialCode" readonly="true" cssClass="textInput readonly"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield id="invBillNumberSetting_serialName" key="invBillNumberSetting.serialName" name="invBillNumberSetting.serialName" cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="invBillNumberSetting_serialLenth" key="invBillNumberSetting.serialLenth" name="invBillNumberSetting.serialLenth" cssClass="required digits" onblur="checkSerialLength(this)"/>
				</div>
				<div class="unit">
					<label class="checkbox"><s:text name="invBillNumberSetting.needPrefix" />:</label>
					<s:checkbox id="invBillNumberSetting_needPrefix" key="invBillNumberSetting.needPrefix" name="invBillNumberSetting.needPrefix" theme="simple"/>
				</div>
				<div class="unit">
					<s:textfield id="invBillNumberSetting_prefix" key="invBillNumberSetting.prefix" name="invBillNumberSetting.prefix" cssClass="required" readonly="" />
				</div>
				<div class="unit">
					<label class="checkbox"><s:text name="invBillNumberSetting.needYearMonth" />:</label>
					<s:checkbox id="invBillNumberSetting_needYearMonth" key="invBillNumberSetting.needYearMonth" name="invBillNumberSetting.needYearMonth" theme="simple"/>
				</div>
				<%-- <div class="unit">
					<label class="checkbox"><s:text name="invBillNumberSetting.inArow" />:</label>
					<s:checkbox id="invBillNumberSetting_inArow" key="invBillNumberSetting.inArow" name="invBillNumberSetting.inArow" theme="simple"/>
				</div> --%>
				<div class="unit">
					<s:hidden key="invBillNumberSetting.id"/>
					<s:hidden key="invBillNumberSetting.copyCode" name="invBillNumberSetting.copyCode" />
					<s:hidden key="invBillNumberSetting.orgCode" name="invBillNumberSetting.orgCode"/>
					<s:hidden key="invBillNumberSetting.inArow" name="invBillNumberSetting.inArow"/>
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





