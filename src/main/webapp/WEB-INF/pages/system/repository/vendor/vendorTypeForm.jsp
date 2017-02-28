<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/* jQuery('#savelink').click(function() {
			jQuery("#vendorTypeForm").attr("action","saveVendorType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#vendorTypeForm").submit();
		}); */
	});
	function setVendorTypeParentCode(obj){
		var currentParentCode = jQuery("#vendorType_parentNode_vendorTypeCode").val();
		jQuery("#vendorType_vendorTypeCode").val(currentParentCode=="-1"?"":currentParentCode);
	}
	function validateVendorTypeCode(obj){
		if(${entityIsNew}==false){
			return;
		}
		var currentParentCode = jQuery("#vendorType_parentNode_vendorTypeCode").val();
		currentParentCode=(currentParentCode=="-1"?"":currentParentCode);
		var vendorTypeCodeRule = jQuery("#vendorTypeCodeRule_id").val();
		if(vendorTypeCodeRule){
			var vendorTypeCode = obj.value;
			var validateFatherCode = getFather(vendorTypeCode,vendorTypeCodeRule);
			if(vendorTypeCode!=""){
				if(!isMatch(vendorTypeCode,vendorTypeCodeRule)){
					alertMsg.error("供应商类别编码格式不正确，请重新输入！");
					obj.value=currentParentCode;
					return;
				}else if(currentParentCode && currentParentCode!=validateFatherCode){
					alertMsg.error("上级类别编码不正确");
		    		obj.value=currentParentCode;
		    		return;
				}
			}
		}
		var vendorTypeIdTemp = "${fns:userContextParam('orgCode')}"+"_"+obj.value;
   		//检查类别编码是否重复需要通过id检查,重写checkId()
   		var url = 'checkId';
   		url = encodeURI(url);
   		$.ajax({
   		    url: url,
   		    type: 'post',
   		    data:{entityName:'VendorType',searchItem:'vendorTypeId',searchValue:vendorTypeIdTemp,returnMessage:'供应商类别编码已存在'},
   		    dataType: 'json',
   		    aysnc:false,
   		    error: function(data){
   		        
   		    },
   		    success: function(data){
   		        if(data!=null){
   		        	 alertMsg.error(data.message);
   		        	 obj.value="";
   		        }
   		    }
   		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="vendorTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,addOrUpdateVendorType);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden  id = "currentVendorTypeEntityState" value="%{entityIsNew}" />
					<s:hidden  id = "vendorTypeCodeRule_id" value="%{codeRule}" />
					<s:textfield value="%{vendorType.parentNode.vendorTypeNameWithCode}" id="vendorType_parentNode_name"  label="父级"  readonly="true" cssClass="textInput readonly"  size="50"/>
					<s:hidden value="%{vendorType.parentNode.id}" id="vendorType_parentNode_id" />
					<s:hidden value="%{vendorType.parentNode.vendorTypeCode}" id="vendorType_parentNode_vendorTypeCode" />
				</div>
				<s:if test="%{entityIsNew}">
					<div class="unit">
						<s:textfield id="vendorType_vendorTypeCode" key="vendorType.vendorTypeCode" name="vendorType.vendorTypeCode" cssClass="required" size="50" onfocus="setVendorTypeParentCode()" onblur="validateVendorTypeCode(this)"/>
						<s:if test="%{codeRule!='' && codeRule!=null}">
							<span>编码规则:${codeRule}</span>
						</s:if>
					</div>
				</s:if>
				<s:else>
					<div class="unit">
						<s:textfield id="vendorType_vendorTypeCode" key="vendorType.vendorTypeCode" name="vendorType.vendorTypeCode" readonly="true" size="50" cssClass="textInput readonly" onblur="validateVendorTypeCode(this)"/>
					</div>
				</s:else>
				<div class="unit">
					<s:textfield id="vendorType_vendorTypeName" key="vendorType.vendorTypeName" name="vendorType.vendorTypeName" cssClass="required" size="50"/>
				</div>
				<div class="unit">
					<label class="checkbox">末级 :</label>
					<s:checkbox id="vendorType_leaf" key="vendorType.leaf" name="vendorType.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label class="checkbox">停用 :</label>
					<s:checkbox id="vendorType_disabled" key="vendorType.disabled" name="vendorType.disabled" theme="simple"/>
				</div>
			</div>
			<%-- <div class="formBar">
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
			</div> --%>
		</form>
	</div>
</div>





