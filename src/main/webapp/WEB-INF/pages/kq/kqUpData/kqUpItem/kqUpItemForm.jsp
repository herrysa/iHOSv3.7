<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var kqUpItemObj = {};
	jQuery(document).ready(function() {
		jQuery('#kqUpItemFormSaveLink').click(function() {
			if("${initKqUpItem}"&&"${entityIsNew}" == "true"){
				alertMsg.confirm("首次添加时自动添加系统考勤项，确认添加?", {
					okCall : function() {
						jQuery("#kqUpItemForm").attr("action","saveKqUpItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}&oper=init');
						jQuery("#kqUpItemForm").submit();
					}
				});
			}else{
				jQuery("#kqUpItemForm").attr("action","saveKqUpItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
				jQuery("#kqUpItemForm").submit();
			}
		});
		if("${entityIsNew}" != "true"){
			switch("${kqUpItem.itemType}"){
			case '0':
				jQuery("#kqUpItem_itemType_value").val("数值型");
				break;
			case '1':
				jQuery("#kqUpItem_itemType_value").val("字符型");
				jQuery("#kqUpItem_scale_span").hide();
				break;
			case '2':
				jQuery("#kqUpItem_itemType_value").val("日期型");
				jQuery("#kqUpItem_scaleLengthDiv").hide();
				break;
			case '3':
				jQuery("#kqUpItem_itemType_value").val("整型");
				jQuery("#kqUpItem_scaleLengthDiv").hide();
				break;
			}
			if("${kqUpItem.showType}"=="kqItem"){
// 				if("${kqUpItem.calculateType}"=="0"){
// 					jQuery("#kqUpItem_calculateType_value").val("手动");
// 				}else if("${kqUpItem.calculateType}"=="1"){
// 					jQuery("#kqUpItem_calculateType_value").val("计算");
// 				}
				jQuery("#kqUpItem_itemName").attr("readOnly",true).removeClass("required");
			}
		}
        if("${oper}"=="view"){
        	if("${kqUpItem.calculateType}"=="0"){
				jQuery("#kqUpItem_calculateType_value").val("手动");
			}else if("${kqUpItem.calculateType}"=="1"){
				jQuery("#kqUpItem_calculateType_value").val("计算");
			}
			readOnlyForm("kqUpItemForm");
			jQuery("#kqUpItemFormSaveLi").hide();
		}else{
			if("${oper}"=="update"){
				jQuery("#kqUpItem_itemLength").attr("readOnly",true);
				jQuery("#kqUpItem_scale").attr("readOnly",true);
			}
		}
        adjustFormInput("kqUpItemForm");
	});
	function kqUpItemItemTypeChange(obj){
		jQuery("#kqUpItem_itemLength").val("");
		jQuery("#kqUpItem_scale").val("");
		jQuery("#kqUpItem_itemLength_span").show();
		jQuery("#kqUpItem_scale_span").show();
		jQuery("#kqUpItem_scaleLengthDiv").show();
		var value = jQuery(obj).val();
		switch(value){
			case '0'://数值型
				jQuery("#kqUpItem_itemLength").val("18").show();
				jQuery("#kqUpItem_scale").val("2").show();
				break;
			case '1'://字符型
				jQuery("#kqUpItem_itemLength").val("50").show();
				jQuery("#kqUpItem_scale_span").hide();
				break;
			case '2'://日期型
				jQuery("#kqUpItem_scaleLengthDiv").hide();
				break;
			case '3'://整形
				jQuery("#kqUpItem_scaleLengthDiv").hide();
				break;
		}
	}
	function kqUpItemFormCallBack(data){
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
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqUpItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,kqUpItemFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="kqUpItem.itemId"/>
				<s:hidden key="kqUpItem.kqType.kqTypeId"/>
				<s:hidden key="kqUpItem.isInherit"/>
				<s:hidden key="kqUpItem.sn"/>
				<s:hidden key="kqUpItem.showType"/>
				</div>
				<div class="unit">
					<s:if test="entityIsNew">
					<label><s:text name="kqUpItem.itemName"></s:text></label>
						<span id="kqUpItem_itemName_span" class="formspan" style="float: left; width: 140px">
							<input id="kqUpItem_itemName" type="text" name="kqUpItem.itemName" notrepeat="项目名称已存在" validatorParam="from KqUpItem item where item.itemName=%value% and item.kqType.kqTypeId='${kqUpItem.kqType.kqTypeId}'" class="required" />
						</span>
					<s:textfield id="kqUpItem_itemCode" key="kqUpItem.itemCode" name="kqUpItem.itemCode" cssClass="required" notrepeat='项目编码已存在' 
						validatorParam="from KqUpItem item where item.itemCode=%value%"/>
					</s:if>
					<s:else>
					<s:textfield id="kqUpItem_itemName" key="kqUpItem.itemName" name="kqUpItem.itemName" cssClass="required"/>
					<s:textfield id="kqUpItem_itemCode" key="kqUpItem.itemCode" name="kqUpItem.itemCode" cssClass="" readonly="true"/>
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name='kqUpItem.kqTypeId'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<input type="text" value="${kqUpItem.kqType.kqTypeName}" readonly="readonly">
					</span>
					<label><s:text name='kqUpItem.itemType'/>:</label>
					<s:if test="entityIsNew">
						<span class="formspan" style="float: left; width: 140px">
						<s:select id="kqUpItem_itemType" key="kqUpItem.itemType"  headerKey=""  onchange="kqUpItemItemTypeChange(this)" 
							list="#{'0':'数值型','1':'字符型','2':'日期型','3':'整型'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" theme="simple">
						</s:select>
						</span>
					</s:if>
					<s:else>
						<input id="kqUpItem_itemType_value" type="text" readonly="readonly">
						<s:hidden key="kqUpItem.itemType"/>
					</s:else>
				</div>
				<div class="unit" id="kqUpItem_scaleLengthDiv">
					<span id="kqUpItem_itemLength_span">
						<s:textfield id="kqUpItem_itemLength" key="kqUpItem.itemLength" name="kqUpItem.itemLength" cssClass="digits"/>
					</span>
					<span id="kqUpItem_scale_span">
						<s:textfield id="kqUpItem_scale" key="kqUpItem.scale" name="kqUpItem.scale" cssClass="number"/>
					</span>
				</div>
				<div class="unit">
					<label><s:text name='kqUpItem.calculateType'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						 <s:select id="kqUpItem_calculateType" key="kqUpItem.calculateType" headerKey=""   
							list="#{'0':'手动','1':'计算'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" theme="simple">
						</s:select>
					</span>
					<label><s:text name="kqUpItem.kqUpDataHide"/>:</label>
					<span class="formspan" style="float: left; width: 140px">
						<s:checkbox key="kqUpItem.kqUpDataHide" name="kqUpItem.kqUpDataHide"  theme="simple" />
					</span>
				</div>
					<s:if test="entityIsNew">
						<s:hidden key="kqUpItem.disabled"/>
					</s:if>
					<s:else>
						<div class="unit">
						<label><s:text name='kqUpItem.disabled'/>:</label>
						<span class="formspan" style="float: left; width: 140px">
							<s:checkbox id="kqUpItem_disabled" key="kqUpItem.disabled" theme="simple" />
						</span>
						</div>
					</s:else>
				<div class="unit">
					<s:textarea id="kqUpItem_remark" key="kqUpItem.remark" name="kqUpItem.remark" cssClass="" cssStyle="width:350px;height:60px;" />
				</div>
				<div class="unit">
					<s:if test="entityIsNew">
						<s:hidden key="kqUpItem.sysField"/>
					</s:if>
					<s:else>
						<label><s:text name="kqUpItem.sysField"/>:</label>
						<span class="formspan" style="float: left; width: 140px">
							<s:checkbox key="kqUpItem.sysField" name="kqUpItem.sysField"  theme="simple" onclick="return false"/>
						</span>
					</s:else>
					
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="kqUpItemFormSaveLi"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="kqUpItemFormSaveLink"><s:text name="button.save" /></button>
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





