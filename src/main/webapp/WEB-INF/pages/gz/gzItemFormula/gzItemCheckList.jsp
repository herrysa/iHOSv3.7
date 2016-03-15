<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#gzItemCheckList_saveLink').click(function() {
			var gzItemCheckTreeObj = $.fn.zTree.getZTreeObj("gzItemCheckListTree");
			var nodes = gzItemCheckTreeObj.transformToArray(gzItemCheckTreeObj.getNodes());
			var cValue = "";
			var cIndex = 1;
			for(index in nodes){
				var node = nodes[index];
				if(node.checked){
			 		 var mydate = new Date();
			 		 jQuery("#${navTabId}").addRowData("formulaFilter_"+mydate.getTime()+index, {
			 			 "filterId":"formulaFilter_"+mydate.getTime()+index,
			 			 "name":node.name,
			 			 "code":node.id,
			 			 "oper":"=",
			 			 "searchValue":""
			 			 }, "last");  
			 		cValue += cIndex +"*";
			 		cIndex++;
				}
			}
			if("${oper}"){
				var operValue = jQuery("#${oper}").val();
				if(cValue&&!operValue){
					cValue = cValue.substring(0,cValue.length-1);
					jQuery("#${oper}").val(cValue);
				}
			}
			closeGzItemCheckListDialog();
		});
		 var url = "getGzItemNodesForFormula?gzTypeId="+"${gzTypeId}&itemCode=${filterItemCode}";
		 url = encodeURI(url);
			$.get(url, {"_" : $.now()}, function(data) {
				var gzItemCheckListTreeData = data.gzItemNodes;
				$.fn.zTree.init($("#gzItemCheckListTree"),ztreesetting_gzItemCheckList, gzItemCheckListTreeData);
			});
	});
	var ztreesetting_gzItemCheckList= {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var treeObj = $.fn.zTree.getZTreeObj(treeId);
					treeObj.checkNode(treeNode, true, false);
				}
			},
			data : {
				key : {
					name : "name"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: {"Y":"ps", "N":"ps"}
			}
		};
	function closeGzItemCheckListDialog(){
		$.pdialog.close('checkGzItemCheckList');
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzItemCheckList" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div style="position:absolute;left:40px;top:5px;">
					工资项目列表
				</div>
				<div style="border:1px solid #5bc0de;position:absolute;left:40px;top:20px;width:200px;height:300px;">
					<div  style="float:left;margin-left:6px;margin-top:6px">
						检索
						<input type="text" style="float:none;width: 100px;" onKeyUp="searchTree('gzItemCheckListTree',this)"/>
					</div>
					<div style="float:left;margin-top:6px;overflow:auto;height:266px;width:195px;">
						<div id="gzItemCheckListTree" style="float:left;margin-top:6px;" class="ztree"></div>
					</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="gzItemCheckList_saveLink">确定</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closeGzItemCheckListDialog()"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





