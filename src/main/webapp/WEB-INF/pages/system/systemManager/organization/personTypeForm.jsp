<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#personTypeForm").attr("action","savePersonType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#personTypeForm").submit();
		});
// 		jQuery("#personType_parentType").treeselect({
// 			   dataType:"sql",
// 			   optType:"single",
// 			   sql:"SELECT id,name,parentType parent  FROM hr_personType  where disabled = 0 and leaf=0 ORDER BY sn",
// 			   exceptnullparent:false,
// 			   lazy:false
// 			  });
		if(jQuery('#personType_disabled_h').val()=='true'){
			jQuery('#personType_disabled').attr('checked','true');
		}
		if("${personType.sysFiled}"=="true"){
			jQuery(".personTypeFormInput").attr('readOnly',"true");
			jQuery(".personTypeFormInput").attr("onfocus","");
			jQuery(".personTypeFormInput").attr("onclick","");
			jQuery('#personType_sumFlag').click(function() {
				return false;
			});
			jQuery('#personType_leaf').click(function() {
				return false;
			});
			jQuery('#personType_disabled').attr('disabled','true');
		}
		if("${oper}"=='view'){
			   readOnlyForm("personTypeForm");
			   jQuery("#personTypeButtonActive").hide();
		} 
	});
	function savePersonTypeForm(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var personType = data.personType;
		var parentId = data.parentId;
		var personTypeTreeObj = $.fn.zTree.getZTreeObj("personTypeTreeLeft");
		var selectedNodes = personTypeTreeObj.getSelectedNodes();
		if(selectedNodes != null && selectedNodes.length >= 1) {
			parentId = selectedNodes[0].id;
		}
		if('${entityIsNew}'=="true"){//  add 获取父级节点，绑定至父级节点上
			var pNode = personTypeTreeObj.getNodeByParam("id", parentId, null);
			var nodeName = personType.name;
			var newNode = {id:personType.id,pId:parentId,name:nodeName,isParent:!personType.leaf}
			personTypeTreeObj.addNodes(pNode, newNode,false);//参数最后一个为true时不自动展开父节点
		
		}else{
			// 如果修改了名字 ，则需要更新节点
			var oldTypeName = "${personType.name}";
			var newTypeName = personType.name;
			if(oldTypeName != newTypeName){
			var typeNodeId = "${personType.id}";
			var updateNode = personTypeTreeObj.getNodeByParam("id", typeNodeId, null);
			updateNode.name = newTypeName;
			personTypeTreeObj.updateNode(updateNode);
			}
		}
		setTimeout(function() {
			reloadPersonTypeGrid(parentId);
		},100);
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="personTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savePersonTypeForm);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="personType.id"/>
				<s:hidden key="personType.sysFiled"/>
				<s:hidden key="personType.leaf"/>
				<s:hidden key="personType.sn"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="personType_code" key="personType.code" name="personType.code" cssClass="required personTypeFormInput" maxlength="50" 
				notrepeat='人员类别编码已存在' validatorParam="from PersonType pt where pt.code=%value%"/>
				<s:textfield id="personType_name" key="personType.name" name="personType.name" cssClass="required personTypeFormInput" maxlength="50" 
				notrepeat='人员类别名称已存在' validatorParam="from PersonType pt where pt.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="personType_code" key="personType.code" name="personType.code" cssClass="required personTypeFormInput" maxlength="50" readonly="true"/>
				<s:textfield id="personType_name" key="personType.name" name="personType.name" cssClass="required personTypeFormInput" maxlength="50" oldValue="'${personType.name}'"
				notrepeat='人员类别名称已存在' validatorParam="from PersonType pt where pt.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name='personType.parentType' />:</label>
						<input type="text" id="personType_parentType" name="personType.parentType.name" value="${personType.parentType.name}" class="personTypeFormInput" readonly="readonly"/>
						<input type="hidden" id="personType_parentType_id" name="personType.parentType.id" value="${personType.parentType.id}"/>						
				<s:if test="%{!entityIsNew}">
					<label><s:text name='personType.disabled' />:</label>
					<s:checkbox id="personType_disabled" name="personType.disabled" theme="simple"/>
					<s:hidden id="personType_disabled_h" value="%{personType.disabled}"/>
				</s:if>
				</div>
				<div class="unit">
						<s:textarea key="personType.remark" required="false" maxlength="200" rows="4" cols="54" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="personTypeButtonActive">
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





