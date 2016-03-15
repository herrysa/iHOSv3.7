<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#hrPersonTypeForm").attr("action","saveHrPersonType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#hrPersonTypeForm").submit();
		});
// 		jQuery("#hrPersonType_parentType").treeselect({
// 			   dataType:"sql",
// 			   optType:"single",
// 			   sql:"SELECT id,name,parentType parent  FROM hr_personType  where disabled = 0 and leaf=0 ORDER BY sn",
// 			   exceptnullparent:false,
// 			   lazy:false
// 			  });
		if("${hrPersonType.sysFiled}"=="true"){
			jQuery(".hrPersonTypeFormInput").attr('readOnly',"true");
			jQuery(".hrPersonTypeFormInput").attr("onfocus","");
			jQuery(".hrPersonTypeFormInput").attr("onclick","");
			jQuery('#hrPersonType_sumFlag').click(function() {
				return false;
			});
			jQuery('#hrPersonType_leaf').click(function() {
				return false;
			});
			jQuery('#hrPersonType_disabled').click(function() {
				return false;
			});
		}
		if("${oper}"=='view'){
			   readOnlyForm("hrPersonTypeForm");
			   jQuery("#hrPersonTypeButtonActive").hide();
		} 
	});
	function saveHrPersonTypeForm(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var hrPersonType = data.hrPersonType;
		var parentId = data.parentId;
		var hrPersonTypeTreeObj = $.fn.zTree.getZTreeObj("hrPersonTypeTreeLeft");
		if('${entityIsNew}'=="true"){//  add 获取父级节点，绑定至父级节点上
			var pNode = hrPersonTypeTreeObj.getNodeByParam("id", parentId, null);
			var nodeName = hrPersonType.name;
			var newNode = {id:hrPersonType.id,pId:parentId,name:nodeName,isParent:!hrPersonType.leaf}
			hrPersonTypeTreeObj.addNodes(pNode, newNode,false);//参数最后一个为true时不自动展开父节点
		}else{
			// 如果修改了名字 ，则需要更新节点
			var oldTypeName = "${hrPersonType.name}";
			var newTypeName = hrPersonType.name;
			if(oldTypeName != newTypeName){
			var typeNodeId = "${hrPersonType.id}";
			var updateNode = hrPersonTypeTreeObj.getNodeByParam("id", typeNodeId, null);
			updateNode.name = newTypeName;
			hrPersonTypeTreeObj.updateNode(updateNode);
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrPersonTypeForm" method="hrPersonType"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveHrPersonTypeForm);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="hrPersonType.id"/>
				<s:hidden key="hrPersonType.sysFiled"/>
				<s:hidden key="hrPersonType.leaf"/>
				<s:hidden key="hrPersonType.sn"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="hrPersonType_code" key="hrPersonType.code" name="hrPersonType.code" cssClass="required hrPersonTypeFormInput" maxlength="50" notrepeat='人员类别编码已存在' validatorParam="from HrPersonType type where type.code=%value%"/>
				<s:textfield id="hrPersonType_name" key="hrPersonType.name" name="hrPersonType.name" cssClass="required hrPersonTypeFormInput" maxlength="50" notrepeat='人员类别名称已存在' validatorParam="from HrPersonType type where type.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="hrPersonType_code" key="hrPersonType.code" name="hrPersonType.code" cssClass="required hrPersonTypeFormInput" maxlength="50" readonly="true"/>
				<s:textfield id="hrPersonType_name" key="hrPersonType.name" name="hrPersonType.name" cssClass="required hrPersonTypeFormInput" maxlength="50" oldValue="${hrPersonType.name}" notrepeat='人员类别名称已存在' validatorParam="from HrPersonType type where type.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name='hrPersonType.parentType' />:</label>
						<input type="text" id="hrPersonType_parentType" name="hrPersonType.parentType.name" value="${hrPersonType.parentType.name}" class="hrPersonTypeFormInput" readonly="readonly"/>
						<input type="hidden" id="hrPersonType_parentType_id" name="hrPersonType.parentType.id" value="${hrPersonType.parentType.id}"/>						
				<label><s:text name='hrPersonType.disabled' />:</label>
				<s:checkbox id="hrPersonType_disabled" theme="simple" key="hrPersonType.disabled" name="hrPersonType.disabled" />
				</div>
				<div class="unit">
						<s:textarea key="hrPersonType.remark" required="false" maxlength="200" rows="4" cols="54" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="hrPersonTypeButtonActive">
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





