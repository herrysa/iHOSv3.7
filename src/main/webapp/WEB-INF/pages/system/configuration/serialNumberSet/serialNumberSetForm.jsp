<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#${random}_serialNumberSetForm").find("select").css("font-size","12px");
		jQuery('#${random}_saveSerialNumberSet').click(function() {
			jQuery("#${random}_serialNumberSetForm").attr("action","saveSerialNumberSet?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#${random}_serialNumberSetForm").submit();
		});
	});
	function checkSerialNumberLength(obj){
		if(!(/^[4-9]*$/.test(obj.value) || obj.value==10)){
			alertMsg.error("长度需在4-10之间！");
		    obj.value="";
		}
	}
	function toogleNeedPrefix(obj){
		var checked = obj.checked
		if(checked){
			jQuery('#${random}_serialNumberSet_prefix').addClass("required").removeAttr("readonly");
		}else{
			jQuery('#${random}_serialNumberSet_prefix').val("");
			jQuery('#${random}_serialNumberSet_prefix').attr("readonly","true").removeClass("required");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_serialNumberSetForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="serialNumberSet.id"/>
					<s:hidden key="serialNumberSet.subSystem"/>
					<s:hidden key="serialNumberSet.orgCode"/>
					<s:hidden key="serialNumberSet.copyCode"/>
					<s:hidden key="serialNumberSet.disabled"/>
				</div>
				<div class="unit">
					<label><s:text name="serialNumberSet.businessCode" />:</label>
					<s:if test="%{entityIsNew}">
						<s:select key="serialNumberSet.businessCode" cssClass="textInput"
							list="businessCodeMap" listKey="key" listValue="value"
							emptyOption="false" theme="simple">
						</s:select>
					</s:if>
					<s:else>
						<s:select key="serialNumberSet.businessCode" cssClass="textInput"
							list="businessCodeMap" listKey="key" listValue="value"
							emptyOption="false" theme="simple" disabled="true">
						</s:select>	
						<s:hidden key="serialNumberSet.businessCode"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="serialNumberSet.businessName" name="serialNumberSet.businessName" cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="serialNumberSet_serialLenth" key="serialNumberSet.serialLenth" name="serialNumberSet.serialLenth" cssClass="digits required" onblur="checkSerialNumberLength(this)"/>
				</div>
				<div class="unit">
					<label><s:text name="serialNumberSet.needYearMonth" />:</label>
					<s:checkbox key="serialNumberSet.needYearMonth" name="serialNumberSet.needYearMonth" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="serialNumberSet.needPrefix" />:</label>
					<s:checkbox key="serialNumberSet.needPrefix" name="serialNumberSet.needPrefix" theme="simple" onchange="toogleNeedPrefix(this)"/>
				</div>
				<div class="unit">
					<label><s:text name="serialNumberSet.prefix" />:</label>
					<input id="${random}_serialNumberSet_prefix" name="serialNumberSet.prefix" value="${serialNumberSet.prefix}" class="textInput required"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}_saveSerialNumberSet"><s:text name="button.save" /></button>
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





