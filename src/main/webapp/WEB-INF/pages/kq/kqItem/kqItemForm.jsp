<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#btnSaveKqItem").click(function(){
			kqItemFormSave();
		});
		jQuery("#kqItem_itemIcon").treeselect({
			dataType:"url",
			optType:"single",
			url:"kqItemIconList",
			exceptnullparent:false,
			selectParent:false,
			minWidth : '220px',
			lazy:false
		});
		
		if("${kqItem.isUsed}" == "true"){
			jQuery("#kqItem_kqItemName").attr("readonly","true");
			jQuery("#kqItem_shortName").attr("readonly","true");
		}
		
		if("${entityIsNew}" == "false") {
			var treeObj =  $.fn.zTree.getZTreeObj("kqItem_itemIcon_tree");
			var treeId = "${kqItem.itemIcon}";
			var treeNode = treeObj.getNodeByParam("id",treeId,null);
			treeObj.selectNode(treeNode);
			jQuery("#kqItem_itemIcon_id").val(treeId);
			if(treeNode) {
				jQuery("#kqItem_itemIcon").val(treeNode.name);
			}
		}
	});
	/*保存*/
	function kqItemFormSave() {
		var urlString = "saveKqItem?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}";
		jQuery("#kqItemForm").attr("action",urlString);
		jQuery("#kqItemForm").submit();
	}
	/*保存后的回调函数*/
	function kqItemFormSaveCallBack(data) {
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var itemNode = data.itemNode;
		if(!itemNode){
			return ;
		}
		
		if("${entityIsNew}"=="true"){
			dealKqItemTreeLoad("kqItemTree",itemNode,"add");
			
		}else{
			dealKqItemTreeLoad("kqItemTree",itemNode,"edit");
		}
		/* debugger
		treeObj.selectNode(node); */

		//reloadKqItemGrid();
	}
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,kqItemFormSaveCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqItem.isUsed"/>
				<s:hidden key="kqItem.kqItemId"/>
				<s:hidden key="kqItem.parentId"></s:hidden>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="kqItem_kqItemCode" key="kqItem.kqItemCode" name="kqItem.kqItemCode" cssClass="" readonly="true"/>
				</s:if>
				<s:else>
				<s:textfield id="kqItem_kqItemCode" key="kqItem.kqItemCode" name="kqItem.kqItemCode" cssClass="required" notrepeat='编码已存在 ' validatorParam="from KqItem item where item.kqItemCode=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="kqItem_kqItemName" key="kqItem.kqItemName"  name="kqItem.kqItemName" cssClass="required" oldValue="'${kqItem.kqItemName}'" notrepeat='名称已存在 ' validatorParam="from KqItem item where item.kqItemName=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="kqItem_kqItemName" key="kqItem.kqItemName" name="kqItem.kqItemName" cssClass="required" notrepeat='名称已存在 ' validatorParam="from KqItem item where item.kqItemName=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="kqItem_shortName" key="kqItem.shortName" name="kqItem.shortName" cssClass="required"  oldValue="'${kqItem.shortName}'" notrepeat='简称已存在 ' validatorParam="from KqItem item where item.shortName=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="kqItem_shortName" key="kqItem.shortName" name="kqItem.shortName" cssClass="required"  notrepeat='简称已存在 ' validatorParam="from KqItem item where item.shortName=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name="kqItem.frequency"></s:text>:</label>
				<s:select list="#{'0':'天数','1':'次数','2':'小时'}" name="kqItem.frequency" listKey="key" listValue="value"
						emptyOption="false"  maxlength="20" width="50px" cssClass="required textInput"/>
				</div>
				<div class="unit">
				<label><s:text name="kqItem.itemIcon"></s:text>:</label>
				<input type="text" id="kqItem_itemIcon" style="width:146px" class="required"/>
				<input type="hidden" id="kqItem_itemIcon_id" name="kqItem.itemIcon"/>
				</div>
				<div class="unit">
					<label><s:text name='kqItem.isDefault'/>:</label>
					<span class="formspan" style="float: left;">
						<s:checkbox id="kqItem_isDefault" key="kqItem.isDefault" theme="simple" />  
					</span>
					<label><s:text name='kqItem.isYearHoliday'/>:</label>
					<span class="formspan" style="float: left;">
						<s:checkbox id="kqItem_isYearHoliday" key="kqItem.isYearHoliday" theme="simple" />  
					</span>
				</div>
				<div class="unit">
					<label><s:text name='kqItem.isCoveroffGeneralHoliday'/>:</label>
						<span class="formspan" style="float: left;">
							<s:checkbox id="kqItem_isCoveroffGeneralHoliday" key="kqItem.isCoveroffGeneralHoliday" theme="simple" />  
					</span>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="btnSaveKqItem"><s:text name="button.save" /></button>
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





