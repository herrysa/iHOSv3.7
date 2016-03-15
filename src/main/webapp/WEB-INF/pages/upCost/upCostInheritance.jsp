<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
jQuery(document).ready(function() {
	jQuery("#periodList").find("option").each(function(){
		jQuery(this).last().attr("selected","true");
	});
	jQuery('#savelink').click(function() {
		var sp=jQuery("#periodList").val();
		var containAmount = jQuery("#upCostInheritance_amount").attr("checked");
		if(containAmount){
			containAmount = true;
		}else{
			containAmount = false;
		}
		jQuery("#upCostInheritanceForm").attr("action","inheritanceUpCost?popup=true&checkPeriod="+sp+"&containAmount="+containAmount+"&upItemId="+'${upItemId}&upItemType=${upItemType}'+"&navTabId="+'${navTabId}');
		jQuery("#upCostInheritanceForm").submit();
	});
});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="upCostInheritanceForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<%-- <s:hidden key="upCost.upitemid.id" required="true" cssClass="required"/>
					<s:hidden key="upCost.costitemid.costItemId" required="true" cssClass=""/> --%>
				</div>
				<div class="unit">
					<label>历史数据核算期间：</label>
					<s:select id="periodList" list="periods" listKey="periodCode" listValue="periodCode"></s:select>
					<label>新核算期间：</label><input type="text" id="cp" readonly="true" value="${currentPeriod }"  style="width:80px"/>
				</div>
				<div class="unit">
					<label><s:if test="%{upItemType==0}">科室</s:if><s:else>人员</s:else>：</label><span style="float:left;width:72px">
					<s:checkbox id="" key="" name="" theme="simple" checked="checked" onclick="return false;"/></span>
					<label>金额：</label><span style="float:left;width:72px">
					<s:checkbox id="upCostInheritance_amount" key="" name="" theme="simple"/></span>
				</div>
				<div class="unit">
					
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="确定" /></button>
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





