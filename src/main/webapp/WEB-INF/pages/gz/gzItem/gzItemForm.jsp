<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
// 	var gzItemObj = {};
	jQuery(document).ready(function() {
		if("${gzItem.warning}" == "true"){
			jQuery("#gzItem_warning_span").show();
		}
		if("${entityIsNew}" != "true"){
			switch("${gzItem.itemType}"){
			case '0':
				jQuery("#gzItem_itemType_value").val("数值型");
				break;
			case '1':
				jQuery("#gzItem_itemType_value").val("字符型");
				jQuery("#gzItem_scale_span").hide();
				break;
			case '2':
				jQuery("#gzItem_itemType_value").val("日期型");
				jQuery("#gzItem_scaleLengthDiv").hide();
				break;
			case '3':
				jQuery("#gzItem_itemType_value").val("整型");
				jQuery("#gzItem_scaleLengthDiv").hide();
				break;
			}
			jQuery("#gzItem_itemLength").attr("readOnly",true);
			jQuery("#gzItem_scale").attr("readOnly",true);
		}
        if("${oper}"=="view" || "${oper}" == "viewUpdate"){
			readOnlyForm("gzItemForm");
			jQuery("#gz_itembuttonActive").hide();
			if("${oper}" == "viewUpdate"){
				jQuery("#gzItem_statistics_span").hide();
			}
		}else{
			if("${oper}"=="itemUpdate"){
				jQuery("#gzItem_statistics_span").hide();
			}
// 			else{
// 				var gzItemsJsonStr = jQuery("#gzItemForm_gzItemsJsonStr").val();
// 				if(gzItemsJsonStr){
// 					var gzItems = JSON.parse(gzItemsJsonStr);
// 					jQuery.each(gzItems,function(index,gzItem){
// 						gzItemObj[gzItem.itemCode] = gzItem;
// 					});
// 				}
// 			}
// 			if("${gzItem.sysField}" == "true"){
// 				if("${gzItem.calculateType}"=="0"){
// 					jQuery("#gzItem_calculateType_value").val("手动");
// 				}else if("${gzItem.calculateType}"=="1"){
// 					jQuery("#gzItem_calculateType_value").val("计算");
// 				}
// 				jQuery("#gzItem_isInherit").bind("click", function(){return false;});
// 				jQuery("#gzItem_itemName").attr("readOnly",true).removeClass("required");
// 			}
			jQuery("#gzItem_isTax").bind("click", function(){
				var checked = jQuery("#gzItem_isTax").attr("checked");
				if(checked == "checked"){
	 				var itemId = "${gzItem.itemId}";
	 				var gzTypeId = "${gzItem.gzType.gzTypeId}";
	 			       jQuery.ajax({
	 						url: 'gzItemIsTaxExist',
	 						data: {itemId:itemId,gzTypeId:gzTypeId},
	 						type: 'post',
	 						dataType: 'json',
	 						async:true,
	 						error: function(data){
	 							alertMsg.error("系统错误!");
	 						},
	 						success: function(data){
	 							if(data.message){
	 								alertMsg.error(data.message);
	 								jQuery("#gzItem_isTax").removeAttr("checked");
	 							}
	 						}
	 					});
				}
			});
			jQuery("#gzItem_warning").bind("click", function(){
				var checked = jQuery("#gzItem_warning").attr("checked");
				if(checked == "checked"){
					jQuery("#gzItem_warning_span").show();
					jQuery("#gzItem_warningValue").addClass("required");
				}else{
					jQuery("#gzItem_warning_span").hide();
					jQuery("#gzItem_warningValue").removeClass("required");
				}
			});
		}
        adjustFormInput("gzItemForm");
	});
	
	/*保存工资项*/
	function saveGzItem(){
		var oldName = "${gzItem.itemName}";
		var check = jQuery("#gzItem_check").val();
		if(check){
			alertMsg.error(check);
       	  	return;
		}
		jQuery("#gzItemForm").attr("action","saveGzItem?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"+"&oldName="+oldName);
		jQuery("#gzItemForm").submit();
	}
	function gzItemItemTypeChange(obj){
		jQuery("#gzItem_itemLength").val("");
		jQuery("#gzItem_scale").val("");
		jQuery("#gzItem_itemLength_span").show();
		jQuery("#gzItem_scale_span").show();
		jQuery("#gzItem_scaleLengthDiv").show();
		var value = jQuery(obj).val();
		switch(value){
			case '0'://数值型
				jQuery("#gzItem_itemLength").val("18").show();
				jQuery("#gzItem_scale").val("2").show();
				break;
			case '1'://字符型
				jQuery("#gzItem_itemLength").val("50").show();
				jQuery("#gzItem_scale_span").hide();
				break;
			case '2'://日期型
				jQuery("#gzItem_scaleLengthDiv").hide();
				break;
			case '3'://整形
				jQuery("#gzItem_scaleLengthDiv").hide();
				break;
		}
	}
	function gzItemFormCallBack(data){
		formCallBack(data);
		if(data.statusCode == 200){
			var itemId = data.itemId;
			if(itemId){
				setTimeout(function(){
					jQuery("#${navTabId}").jqGrid('setSelection',itemId);
					}, 500);
			}
		}
	}
	
/* 	function gzItemUserDefinedChange(obj){
		var val = jQuery(obj).val();
		if(val == "0"){
			var selectHtml = '<select id="gzItem_itemName" onchange="itemNameChange(this)">';
			if(gzItemObj){
				for(var itemCode in gzItemObj){
					var itemName = gzItemObj[itemCode].itemName;
					selectHtml += '<option value ="'+itemCode+'">'+itemName+'</option>';
				}
			}
			selectHtml += '</select>';
			jQuery("#gzItem_itemName_span").html(selectHtml);
			var hideSpanHtml = '<s:hidden key="gzItem.itemName" id="gzItem_itemName_hide"/>';
			hideSpanHtml += '<s:hidden key="gzItem.calculateType" id="gzItem_calculateType_hide"/>';
			hideSpanHtml += '<s:hidden key="gzItem.itemType" id="gzItem_itemType_hide"/>';
			jQuery("#gzItem_hideSpan").html(hideSpanHtml);
			var code = jQuery("#gzItem_itemName").val();
			var gzItem = gzItemObj[code];
			var calculateType = "";
			var itemName = "";
			var itemCode = "";
			var itemType = "";
			var itemLength = "";
			var scale = "";
			if(gzItem){
				calculateType = gzItem.calculateType;
				itemName = gzItem.itemName;
				itemCode = gzItem.itemCode;
				itemType = gzItem.itemType;
				itemLength = gzItem.itemLength;
				scale = gzItem.scale;
			}
			jQuery("#gzItem_itemType").val(itemType).attr("disabled","true");
			gzItemItemTypeChange("gzItem_itemType");
			jQuery("#gzItem_itemCode").val(itemCode).attr("readonly","readonly").removeAttr("notrepeat");
			jQuery("#gzItem_calculateType").val(calculateType).attr("disabled","true");
			jQuery("#gzItem_itemLength").val(itemLength).attr("readonly","true");
			jQuery("#gzItem_scale").val(scale).attr("readonly","true");
			jQuery("#gzItem_itemName_hide").val(itemName);
			jQuery("#gzItem_calculateType_hide").val(calculateType);
			jQuery("#gzItem_itemType_hide").val(itemType);
		}else{
			var inputHtml = '<input id="gzItem_itemName" type="text" name="gzItem.itemName" notrepeat="工资项名称已存在" validatorParam="from GzItem item where item.itemName=%value% and item.gzType.gzTypeId=\'${gzItem.gzType.gzTypeId}\'" class="required" />';
			jQuery("#gzItem_itemName_span").html(inputHtml);
			jQuery("#gzItem_hideSpan").html("");
			jQuery("#gzItem_itemCode").val("${gzItem.itemCode}").removeAttr("readonly").attr("notrepeat",
					"工资项代码已存在");
			jQuery("#gzItem_calculateType").removeAttr("disabled");
			jQuery("#gzItem_itemType").val("0").removeAttr("disabled");
			gzItemItemTypeChange("gzItem_itemType");
			jQuery("#gzItem_itemLength").val("18").removeAttr("readonly");
			jQuery("#gzItem_scale").val("2").removeAttr("readonly");
		}
	} */
/* 	function itemNameChange(obj){
		var itemCode = jQuery(obj).val();
		var gzItem = gzItemObj[itemCode];
		var calculateType = gzItem.calculateType;
		var itemName = gzItem.itemName;
		var itemCode = gzItem.itemCode;
		var itemType = gzItem.itemType;
		var itemLength = gzItem.itemLength;
		var scale = gzItem.scale;
		jQuery("#gzItem_itemCode").val(itemCode);
		jQuery("#gzItem_calculateType").val(calculateType);
		jQuery("#gzItem_itemType").val(itemType);
		gzItemItemTypeChange("gzItem_itemType");
		jQuery("#gzItem_itemLength").val(itemLength);
		jQuery("#gzItem_scale").val(scale);
		jQuery("#gzItem_itemName_hide").val(itemName);
		jQuery("#gzItem_calculateType_hide").val(calculateType);
		jQuery("#gzItem_itemType_hide").val(itemType);
	} */
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,gzItemFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
<%-- 					<input type="hidden" id="gzItemForm_gzItemsJsonStr" value='<s:property value="gzItemsJsonStr" escapeHtml="false"/>'> --%>
					<s:hidden key="gzItem.itemId"/>
					<s:hidden key="gzItem.gzType.gzTypeId" /> 
					<s:hidden key="gzItem.sn"/>
					<s:hidden key="gzItem.isUsed"/>
					<s:hidden key="gzItem.sysField"/>
					<span id="gzItem_hideSpan">
					</span>
				</div>
				<div class="unit">
					<input id="gzItem_check" type="hidden">
					<s:if test="%{!entityIsNew}">
						<s:textfield id="gzItem_itemName" key="gzItem.itemName" name="gzItem.itemName" readonly="true"/>
						<s:textfield id="gzItem_itemCode" key="gzItem.itemCode" name="gzItem.itemCode" cssClass="" readonly="true"/>
					</s:if>
					<s:else>
						<label><s:text name="gzItem.itemName"></s:text></label>
						<span id="gzItem_itemName_span" class="formspan" style="float: left; width: 140px">
							<input id="gzItem_itemName" type="text" name="gzItem.itemName" notrepeat="工资项名称已存在" validatorParam="from GzItem item where item.itemName=%value% and item.gzType.gzTypeId='${gzItem.gzType.gzTypeId}'" class="required" />
						</span>
						<s:textfield id="gzItem_itemCode" key="gzItem.itemCode" notrepeat='工资项代码已存在 ' validatorParam="from GzItem item where item.itemCode=%value%" name="gzItem.itemCode" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name="gzItem.gzTypeId"></s:text></label>
					<span class="formspan" style="float: left; width: 140px">
						<input type="text" value="${gzItem.gzType.gzTypeName}" readonly="readonly">
					</span>
					<s:if test="%{!entityIsNew}">
						<s:textfield id="gzItem_itemShowName" key="gzItem.itemShowName" name="gzItem.itemShowName" cssClass="required" oldValue="'${gzItem.itemShowName}'" notrepeat='显示名称已存在' validatorParam="from GzItem item where item.itemShowName=%value% and item.gzType.gzTypeId='${gzItem.gzType.gzTypeId}'"/>
					</s:if>
					<s:else>
						<s:textfield id="gzItem_itemShowName" key="gzItem.itemShowName" name="gzItem.itemShowName" cssClass="required" notrepeat='显示名称已存在' validatorParam="from GzItem item where item.itemShowName=%value% and item.gzType.gzTypeId='${gzItem.gzType.gzTypeId}'"/>
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name='gzItem.calculateType'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						 <s:select id="gzItem_calculateType" key="gzItem.calculateType" headerKey=""   
							list="#{'0':'手动','1':'计算'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" theme="simple">
						</s:select>
					</span>
					<label><s:text name='gzItem.itemType'/>:</label>
					<s:if test="%{!entityIsNew}">
						<input id="gzItem_itemType_value" type="text" readonly="readonly">
						<s:hidden key="gzItem.itemType"/>
					</s:if>
					<s:else>
						<span class="formspan" style="float: left; width: 140px">
						<s:select id="gzItem_itemType" key="gzItem.itemType"  headerKey=""  onchange="gzItemItemTypeChange(this)" 
							list="#{'0':'数值型','1':'字符型','2':'日期型','3':'整型'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" theme="simple">
						</s:select>
					</span>
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name='gzItem.isInherit'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<s:checkbox id="gzItem_isInherit" key="gzItem.isInherit" theme="simple" />  
					</span>
					<label><s:text name='gzItem.isTax'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<s:checkbox id="gzItem_isTax" key="gzItem.isTax" theme="simple" />  
					</span>
				</div>
				<div class="unit" id="gzItem_scaleLengthDiv">
					<span id="gzItem_itemLength_span">
						<s:textfield id="gzItem_itemLength" key="gzItem.itemLength" name="gzItem.itemLength" cssClass="digits"/>
					</span>
					<span id="gzItem_scale_span">
						<s:textfield id="gzItem_scale" key="gzItem.scale" name="gzItem.scale" cssClass="number"/>
					</span>
				</div>
				<div class="unit">
					<label><s:text name="gzItem.gzContentHide"/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="gzItem.gzContentHide" name="gzItem.gzContentHide"  theme="simple" />
					</span>
					<span id="gzItem_statistics_span">
						<label><s:text name="gzItem.statistics"/>:</label>
						<span class="formspan" style="float: left; width: 140px">
						<s:checkbox key="gzItem.statistics" name="gzItem.statistics"  theme="simple"/>
						</span>	
					</span>
				</div>
				<s:if test="entityIsNew">
					<s:hidden key="gzItem.disabled"/>
				</s:if>
				<s:else>
					<div class="unit">
						<label><s:text name='gzItem.disabled'/>:</label>
						<span class="formspan" style="float: left; width: 140px">
							<s:checkbox id="gzItem_disabled" key="gzItem.disabled" theme="simple" />
						</span>
					</div>
				</s:else>
				<div class="unit">
					<label><s:text name='gzItem.warning'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<s:checkbox id="gzItem_warning" key="gzItem.warning" theme="simple" />
					</span>
					<span id="gzItem_warning_span" style="display: none;">
					<label><s:text name='gzItem.warningValue'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<s:select key="gzItem.warningType" headerKey=""   
							list="#{'1':'>','2':'≥','3':'<','4':'≤'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" theme="simple">
						</s:select>
						<input type="text" id="gzItem_warningValue" name="gzItem.warningValue" style="margin-left:5px;;width: 90px;" value="${gzItem.warningValue}" class="number">
					</span>
					</span>
					
				</div>
				<div class="unit">
					<s:textarea id="gzItem_remark" key="gzItem.remark" name="gzItem.remark" cssClass="" cssStyle="width:350px;height:60px;" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div id="gz_itembuttonActive" class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="saveGzItem()"><s:text name="button.save" /></button>
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





