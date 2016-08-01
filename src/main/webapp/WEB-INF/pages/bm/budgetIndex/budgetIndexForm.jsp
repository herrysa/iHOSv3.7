<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetIndexForm").attr("action","saveBudgetIndex?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetIndexForm").submit();
		});*/
	});
	function saveBudgetIndexCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var indexNode = data.indexNode;
		var zTree = $.fn.zTree.getZTreeObj("budgetIndexTreeLeft"); 
		if("${entityIsNew}"=="true"){
			var parentNode = zTree.getNodeByParam("id", indexNode.pId, null);
			node = zTree.addNodes(parentNode,indexNode);
			var indexNodeList = data.indexNodeList;
			indexNode = zTree.getNodeByParam("id", indexNode.id, null);
			for(var i in indexNodeList){
				var iNode = indexNodeList[i];
				zTree.addNodes(indexNode,iNode);
			}
		}else{
			var oldNode = zTree.getNodeByParam("id", indexNode.id, null);
			oldNode.name = indexNode.name;
			zTree.updateNode(oldNode);
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetIndexForm" method="post"	action="saveBudgetIndex?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveBudgetIndexCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="budgetIndex.indexCode" cssClass="required" notrepeat='预算指标编码已存在' validatorParam="from BudgetIndex entity where entity.indexCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="budgetIndex.indexCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_indexName" key="budgetIndex.indexName" name="budgetIndex.indexName" cssClass="		required		
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.indexType"/>:</label>
					<s:select list="#{'月度':'月度','季度':'季度','半年':'半年','年度':'年度'}" key="budgetIndex.indexType" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
				<label><s:text name="budgetIndex.parentId"/>:</label>
					<s:hidden key="budgetIndex.parentId_indexCode" name="budgetIndex.parentId.indexCode"></s:hidden>
				<s:textfield id="budgetIndex_parentId_indexName" key="budgetIndex.parentId.indexName" name="budgetIndex.parentId.indexName" readonly="true" cssClass="				
				
				       " theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.budgetType"/>:</label>
					<s:hidden id="budgetIndexForm_budgetType_id" name="budgetIndex.budgetType.budgetTypeCode"/>
					<s:textfield id="budgetIndexForm_budgetType"></s:textfield>
					<script>
					jQuery("#budgetIndexForm_budgetType").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "select type_code id,type_name name,parent_id parent from bm_budgetType",
						exceptnullparent : true,
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						callback : {
							afterClick:function(treeId,vi,v){
								$.post("findBudgetType",{budgetTypeCode:vi},function(data){
									var budgetTypeNode = data.budgetTypeNode;
									jQuery("#budgetIndex_warningPercent").val(budgetTypeNode.warningPercent);
									jQuery("select[name='budgetIndex.exceedBudgetType']").val(budgetTypeNode.exceedBudgetType);
								});
							}
						}
					});
					</script>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.exceedBudgetType"/>:</label>
					<select name="budgetIndex.exceedBudgetType">
							<option value="">--</option>
							<option value="禁止">禁止</option>
							<option value="警告">警告</option>
						</select>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_warningPercent" key="budgetIndex.warningPercent" name="budgetIndex.warningPercent" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.leaf"/>:</label>
					<s:checkbox id="budgetIndex_leaf" key="budgetIndex.leaf" name="budgetIndex.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.disabled"/>:</label>
					<s:checkbox id="budgetIndex_disabled" key="budgetIndex.disabled" name="budgetIndex.disabled" theme="simple"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
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





