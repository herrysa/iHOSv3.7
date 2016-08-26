<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="chargeItemDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='chargeItemDetail.heading'/>" />

<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			if("${globalParam.paramKey}"=="ansyOrgDeptPerson"&&"${globalParam.paramValue}"!="1"&&jQuery("#globalParam_paramValue").val()=="1"){
				$("#progressBar").text("同步数据中，请稍等...");
			}
			var options = jQuery("#${random }globalParam_selectOptions").val();
			if(options.indexOf("key1:")!=-1){
				jQuery("#${random }globalParam_selectOptions").val("");
			}
			jQuery("#${random}globalParamForm").attr("action","saveGlobalparam?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#${random}globalParamForm").submit();
			//$("#progressBar").text("数据加载中，请稍等...");
		});
	});
	jQuery(document).ready(function() {
		var selectStr = "${globalParam.selectOptions}";//jQuery("#${random}globalParam_selectOptions").val()
		var entityIsNew = "${entityIsNew}";
		if(entityIsNew == "false" && selectStr) {
			var selectOptions = selectStr.split(";");
			if(selectOptions.length > 1) {
				jQuery("#${random}globalParam_paramValue_input_div").remove();
				//jQuery("#${random}globalParam_paramValue_input_div").empty();
				//jQuery("#${random}globalParam_paramValue_input_div").hide();
				jQuery("#${random}globalParam_paramValue_select_div").show();
				for(var i = 0;i < selectOptions.length;i ++) {
					var selectKey = selectOptions[i].split(":")[0];
					var selectValue = selectOptions[i].split(":")[1];
					jQuery("#${random }globalParam_paramValue_selectOptions").append("<option value='"+selectKey+"'>"+selectValue+"</option>");
				}
				jQuery("#${random }globalParam_paramValue_selectOptions").val("${globalParam.paramValue}");
				
			}
		}
	});
	function checkParamKey(obj){
		if('${entityIsNew}'!="true"){
			var oldParamKey = "${globalParam.paramKey}";
			if(obj.value==oldParamKey){
				return;
			}
		}
		checkId(obj,'GlobalParam','paramKey','此参数key已存在');
	}
	function saveGlobalParamFormCallback(data){
		formCallBack(data);
		$("#progressBar").text("数据加载中，请稍等...");
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}globalParamForm" method="post"
			action="" class="pageForm required-validate"
			onsubmit="return validateCallback(this,saveGlobalParamFormCallback);">
			<div class="pageFormContent" layoutH="56">
				<%-- <div class="unit">
					<s:hidden key="chargeItem.chargeItemNo" />
					<s:if test="chargeItem.chargeItemId==null">
					<s:textfield key="chargeItem.chargeItemId" cssClass="required"
						maxlength="255" onblur="checkId(this,'ChargeItem','chargeItemId')"/>
					</s:if>
					<s:else>
					<s:textfield key="chargeItem.chargeItemId" cssClass="required"
						maxlength="255" readonly="true"/>
					</s:else>
					
				</div> --%>
				<c:choose>
					<c:when test="${entityIsNew }">
						<div class="unit">
							<s:hidden key="globalParam.paramId"/>
							<s:textfield key="globalParam.paramKey" cssClass="required"
								maxlength="100" notrepeat='此参数key已存在' validatorParam="from GlobalParam param where param.paramKey=%value%"/>
						</div>
						<div class="unit">
							<s:textfield key="globalParam.paramName" cssClass="required"
								maxlength="100" notrepeat='此参数名称已存在' validatorParam="from GlobalParam param where param.paramName=%value%"/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="unit">
							<s:hidden key="globalParam.paramId"/>
							<s:textfield key="globalParam.paramKey" cssClass="required"
								maxlength="100" readonly="true"/>
						</div>
						<div class="unit">
							<%-- <s:textfield key="globalParam.paramName" cssClass="required"
								maxlength="100" readonly="true"/> --%>
							<s:textfield key="globalParam.paramName" cssClass="required"
								maxlength="100" oldValue="${globalParam.paramName }" notrepeat='此参数名称已存在' validatorParam="from GlobalParam param where param.paramName=%value%"/>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="unit" id="${random }globalParam_paramValue_input_div">
					<s:textfield id="globalParam_paramValue" key="globalParam.paramValue" cssClass="required"
						maxlength="100" />
				</div>
				<div class="unit" id="${random }globalParam_paramValue_select_div" style="display: none;">
					<label><s:text name="globalParam.paramValue">:</s:text></label>
					<select id="${random }globalParam_paramValue_selectOptions" name="globalParam.paramValue" class="required"></select>
				</div>
				<div class="unit">
					<s:select list="#{'1':'业务操作','2':'数据格式' }" key="globalParam.paramType" id="globalParam_paramType" listKey="key"
						listValue="value" emptyOption="false" maxlength="50" width="50px"></s:select>
				</div>
				<c:choose>
					<c:when test="${entityIsNew }">
						<div class="unit">
							<label><s:text name="globalParam.selectOptions"></s:text>:</label>
							<input name="globalParam.selectOptions" maxlength="100" id="${random }globalParam_selectOptions" class="textInput" style="width:146px;color:#999999" value="key1:value1;key2:value2" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}"/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="unit" style="display:none;">
							<label><s:text name="globalParam.selectOptions"></s:text>:</label>
							<input name="globalParam.selectOptions" value="${globalParam.selectOptions }" maxlength="100" id="${random }globalParam_selectOptions"  class="textInput" style="width:146px;"  />
						</div>
					</c:otherwise>
				</c:choose>
				<c:if test="${empty subSystem}">
					<div class="unit">
						<label><fmt:message key='menu.subSystem' />:</label>
						<s:select name="globalParam.subSystemId" id="subSystemC"  maxlength="20"
						list="subSystems"  listKey="menuName"
						listValue="menuName" emptyOption="true" theme="simple"></s:select>
					</div>
				</c:if>
				<c:if test="${!empty subSystem}">
					<s:hidden key="globalParam.subSystemId"/>
				</c:if>
				<div class="unit">
					<s:textarea key="globalParam.description" cssStyle="width:246px;height:35px"></s:textarea>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>
