<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/* jQuery('#savelink').click(function() {
			jQuery("#inventoryTypeForm").attr("action","saveInventoryType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#inventoryTypeForm").submit();
		}); */
		/* jQuery("#inventoryType_parentNode_name").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT InvtypeId id,Invtype + '(' + InvtypeCode + ')' as name,parent_id parent FROM mm_invType",
			exceptnullparent:true,
			lazy:false
		}); */
		jQuery("#inventoryType_costItem_costItemId").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT costItemId id,costItem as name,parentId parent FROM t_costitem",
			exceptnullparent:false,
			lazy:false
		});
	});
	function setParentCode(obj){
		var currentParentCode = jQuery("#inventoryType_parentNode_invTypeCode").val();
		jQuery("#inventoryType_invTypeCode").val(currentParentCode=="-1"?"":currentParentCode);
	}
	function validateInvTypeCode(obj){
		if(${entityIsNew}==false){
			return;
		}
		var currentParentCode = jQuery("#inventoryType_parentNode_invTypeCode").val();
		currentParentCode = (currentParentCode=="-1"?"":currentParentCode);
		var invTypeCodeRule = jQuery("#invTypeCodeRule_id").val();
		//alert("编码方案"+invTypeCodeRule);
		if(invTypeCodeRule){
			var invTypeCode = obj.value;
			var validateFatherCode = getFather(invTypeCode,invTypeCodeRule);
			if(invTypeCode!=""){
				if(!isMatch(invTypeCode,invTypeCodeRule)){
					alertMsg.error("物资类别编码格式不正确，请重新输入！");
					obj.value=currentParentCode;
					return;
				}else if(currentParentCode && currentParentCode!=validateFatherCode){
					alertMsg.error("上级类别编码不正确");
		    		obj.value=currentParentCode;
		    		return;
				}
			}
		}
		var invTypeIdTemp = "${fns:userContextParam('orgCode')}_${fns:userContextParam('copyCode')}_"+obj.value;
		var url = 'checkId';
   		url = encodeURI(url);
   		$.ajax({
   		    url: url,
   		    type: 'post',
   		    data:{entityName:'InventoryType',searchItem:'InvtypeId',searchValue:invTypeIdTemp,returnMessage:'材料类别编码已存在'},
   		    dataType: 'json',
   		    aysnc:false,
   		    error: function(data){
   		        
   		    },
   		    success: function(data){
   		        if(data!=null){
   		        	 alertMsg.error(data.message);
   		        	 obj.value=currentParentCode;
   		        }
   		    }
   		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="inventoryTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,addOrUpdateInvType);">
			<div class="pageFormContent" layoutH="120">
				<div class="unit">
					<%-- <s:hidden key="inventoryType.id" required="true" cssClass="validate[required]"/> --%>
					<s:hidden  id = "currentInvTypeEntityState" value="%{entityIsNew}" />
					<s:hidden  id = "invTypeCodeRule_id" value="%{codeRule}" />
					<s:textfield value="%{inventoryType.parentNode.invTypeNameWithCode}" id="inventoryType_parentNode_name"  label="父级"  readonly="true" cssClass="textInput readonly"  size="50"/>
					<s:hidden value="%{inventoryType.parentNode.id}" id="inventoryType_parentNode_id" />
					<s:hidden value="%{inventoryType.parentNode.invTypeCode}" id="inventoryType_parentNode_invTypeCode" />
				</div>
				<s:if test="%{entityIsNew}">
					<div class="unit">
						<s:textfield id="inventoryType_invTypeCode" key="inventoryType.invTypeCode" name="inventoryType.invTypeCode" cssClass="required" size="50" onfocus="setParentCode()" onblur="validateInvTypeCode(this)"/>
						<s:if test="%{codeRule!='' && codeRule!=null}">
							<span>编码规则:${codeRule}</span>
						</s:if>
					</div>
				</s:if>
				<s:else>
					<div class="unit">
						<s:textfield id="inventoryType_invTypeCode" key="inventoryType.invTypeCode" name="inventoryType.invTypeCode" readonly="true" size="50" cssClass="textInput readonly" onblur="validateInvTypeCode(this)"/>
					</div>
				</s:else>
				<div class="unit">
					<s:textfield id="inventoryType_invTypeName" key="inventoryType.invTypeName" name="inventoryType.invTypeName" cssClass="required" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="inventoryType_cnCode" key="inventoryType.cnCode" name="inventoryType.cnCode" readonly="true" cssClass="textInput readonly" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="inventoryType_costItem_costItemId" key="inventoryType.costItem.costItemName" name="inventoryType.costItem.costItemName" cssClass="textInput" size="50"/>
					<s:hidden id="inventoryType_costItem_costItemId_id" key="inventoryType.costItem.costItemId" name="inventoryType.costItem.costItemId"/>
				</div>
				<div class="unit">
					<s:textarea id="inventoryType_remark" key="inventoryType.remark" name="inventoryType.remark" cssClass="textInput" cols="43"/>
				</div>
				<div class="unit">
					<label class="checkbox">末级 :</label>
					<s:checkbox id="inventoryType_leaf" key="inventoryType.leaf" name="inventoryType.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label class="checkbox">停用 :</label>
					<s:checkbox id="inventoryType_disabled" key="inventoryType.disabled" name="inventoryType.disabled" theme="simple" />
				</div>
					
				<div class="unit">
					<label class="checkbox">预算 :</label>
					<s:checkbox id="inventoryType_isBudg" key="inventoryType.isBudg" name="inventoryType.isBudg" theme="simple"/>
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
		<div id="invTypeParent"></div>
	</div>
</div>





