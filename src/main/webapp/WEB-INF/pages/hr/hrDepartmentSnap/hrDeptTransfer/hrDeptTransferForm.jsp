<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=='view'){
			readOnlyForm("hrDeptTransferForm");
		}else{
			loadHrDeptTreeInDeptTransferFrom();
		}
		jQuery('#saveHrDeptTransferlink').click(function() {
			if(!validateCheckedNode()){
				return;
			}
			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeFromDepts");
			var nodes = hrDeptTreeObj.getCheckedNodes(true);
			var fromNode = nodes[0];
			jQuery("#hrDeptTransfer_deptIds").val(fromNode.id);
			jQuery("#hrDeptTransfer_deptNames").val(fromNode.nameWithoutPerson);
			
			var toTreeObj = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeToDepts");
			nodes = toTreeObj.getCheckedNodes(true);
			var toNode = nodes[0];
			jQuery("#hrDeptTransfer_deptName").val(toNode.nameWithoutPerson);
			jQuery("#hrDeptTransfer_parentId").val(toNode.id);
			if(toNode.subSysTem=='ORG'){
				jQuery("#hrDeptTransfer_orgCode").val(toNode.id);
				jQuery("#hrDeptTransfer_deptCode").val(fromNode.deptCode);
			}else{
				jQuery("#hrDeptTransfer_deptCode").val(toNode.deptCode);
				jQuery("#hrDeptTransfer_orgCode").val(toNode.orgCode);
			}
			jQuery("#hrDeptTransferForm").attr("action","saveHrDeptMerge?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#hrDeptTransferForm").submit();
		});
	});
	
	function validateCheckedNode(){
		var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeFromDepts");
		var fromCheckNodes = hrDeptTreeObj.getCheckedNodes(true);
		if(fromCheckNodes.length==0){
			 alertMsg.error("请选择被划转部门。");
			 return false;
		}
		var TreeObj = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeToDepts");
		var toNodes = TreeObj.getCheckedNodes(true);
		if(toNodes.length==0){
			 alertMsg.error("请选择划转到单位/部门。");
			 return false;
		}
		var treeNode = toNodes[0];
		var fromNode = fromCheckNodes[0];
		if(fromNode.id==treeNode.id){
			alertMsg.error("不能划转到原部门。");
		    return false;
		}
		if(fromNode.isParent){
			var resultNodes = new Array();
			resultNodes = getNodesByPNode(fromNode,resultNodes);
			for(var index in resultNodes){
				var node = resultNodes[index];
				if(treeNode.id==node.id){
					alertMsg.error("被划转部门的下级不能作为划转到的部门。");
				    return false;
				}
			}
		}
		var fromPNode = fromNode.getParentNode();
		if(fromPNode.id==treeNode.id){
			alertMsg.error("部门已在机构下，无需划转。");
		    return false;
		}
		return true;
	}
	
	function loadHrDeptTreeInDeptTransferFrom(){
		var url = "makeHrDeptTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDeptTransfer_mergeFromDepts"),ztreesetting_hrDeptTreeInDeptTransfer, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			toggleDisabledOrCount({treeId:'hrDeptTransfer_mergeFromDepts',
				showCode:false,
				disabledDept:false,
				disabledPerson:false,
				showCount:true,
			});
			nodes = hrDeptTree.transformToArray(nodes);
			for(index in nodes){
				node = nodes[index];
// 				if(node.subSysTem!="ALL"){
// 					if(node.subSysTem=="ORG"){
// 						node.name = node.name+"("+node.personCountD+")";
// 					}else{
// 						node.name = node.name+"("+node.personCount+")";
// 					}
// 					hrDeptTree.updateNode(node);
// 				}
				if(node.subSysTem!="DEPT"){
					node.nocheck=true;
					hrDeptTree.updateNode(node);
				}
				if(node.actionUrl == '1'){
					hrDeptTree.removeNode(node);
				}
			}
			var hrToDeptTree = $.fn.zTree.init($("#hrDeptTransfer_mergeToDepts"),ztreesetting_hrDeptTreeInDeptTransfer_toDept, hrDeptTreeData);
			var toNodes = hrToDeptTree.getNodes();
			hrToDeptTree.expandNode(toNodes[0], true, false, true);
			toggleDisabledOrCount({treeId:'hrDeptTransfer_mergeToDepts',
				showCode:false,
				disabledDept:false,
				disabledPerson:false,
				showCount:true,
			});
			toNodes = hrToDeptTree.transformToArray(toNodes);
			for(index in toNodes){
				node = toNodes[index];
// 				if(node.subSysTem!="ALL"){
// 					if(node.subSysTem=="ORG"){
// 						node.name = node.name+"("+node.personCountD+")";
// 					}else{
// 						node.name = node.name+"("+node.personCount+")";
// 					}
// 					hrToDeptTree.updateNode(node);
// 				}
				if(node.subSysTem=="ALL" || (node.subSysTem=="DEPT" && !node.isParent)){
					node.nocheck=true;
					hrToDeptTree.updateNode(node);
				}
				if(node.actionUrl == '1'){
					hrToDeptTree.removeNode(node);
				}
			}
			if(!${entityIsNew}){
				var node = hrToDeptTree.getNodeByParam("id", "${hrDeptMerge.parentId}", null);
				hrToDeptTree.checkNode(node, true, false,false);
				if(!node.isParent){
					node = node.getParentNode();						
				}
				hrToDeptTree.expandNode(node,true);
				var deptIds = "${hrDeptMerge.deptIds}";
				var deptIdArr = deptIds.split(",");
				for(var index in deptIdArr){
					node = hrDeptTree.getNodeByParam("id", deptIdArr[index], null);
					hrDeptTree.checkNode(node, true, false,false);
					if(!node.isParent){
						node = node.getParentNode();						
					}
					hrDeptTree.expandNode(node,true,false,true);
				}
			}
		});
	}
	var ztreesetting_hrDeptTreeInDeptTransfer = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				txtSelectedEnable:false
			},
			check: {
				enable: true,
				chkStyle: "radio",
				radioType : "all"
			},
			callback : {
				beforeDrag:function(){return false;}
				,beforeClick:function(){return false;}
				,onCheck : selectFromDept
				,onExpand: ansyExpandToTree
				,onCollapse: ansyCollapseToTree
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
			}
	};
	
	function ansyExpandToTree(event, treeId, treeNode){
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeToDepts");
		var toDept = toDeptTree.getNodeByParam("id", treeNode.id, null);
		toDeptTree.expandNode(toDept,true);
	}
	function ansyCollapseToTree(event, treeId, treeNode){
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeToDepts");
		var toDept = toDeptTree.getNodeByParam("id", treeNode.id, null);
		toDeptTree.expandNode(toDept,false);
	}
	function selectFromDept(event, treeId, treeNode) {
		if(treeNode.checked==true){
			jQuery("#hrDeptMerge_fromDept").val(treeNode.nameWithoutPerson);
		}else{
			jQuery("#hrDeptMerge_fromDept").val("");
		}
	};
	
	var ztreesetting_hrDeptTreeInDeptTransfer_toDept = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : true,
				txtSelectedEnable:false
			},
			check: {
				enable: true,
				chkStyle: "radio",
				radioType : "all"
			},
			callback : {
				beforeDrag:function(){return false;}
				,beforeClick : function(){return false;}
				,beforeCheck:beforeCheckToDept
				,onCheck : selectToDept
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
			}
	};
	function beforeCheckToDept(treeId, treeNode){
		var fromDeptTree = $.fn.zTree.getZTreeObj("hrDeptTransfer_mergeFromDepts");
		var fromCheckNodes = fromDeptTree.getCheckedNodes(true);
		if(fromCheckNodes.length==0){
			alertMsg.error("请选择被划转的部门。");
		    return false;
		}else{
			var fromNode = fromCheckNodes[0];
			if(fromNode.id==treeNode.id){
				alertMsg.error("不能划转到原部门。");
			    return false;
			}
			if(fromNode.isParent){
				var resultNodes = new Array();
				resultNodes = getNodesByPNode(fromNode,resultNodes);
				for(var index in resultNodes){
					var node = resultNodes[index];
					if(treeNode.id==node.id){
						alertMsg.error("被划转部门的下级不能作为划转到的部门。");
					    return false;
					}
				}
			}
			var fromPNode = fromNode.getParentNode();
			if(fromPNode.id==treeNode.id){
				alertMsg.error("部门已在机构下，无需划转。");
			    return false;
			}
		}
		return true;
	}
	
	/*
		向下找到所有子孙节点
	*/
	function getNodesByPNode(pNode,resultNodes){
		var childNodes = pNode.children;
		for(var index in childNodes){
			var node = childNodes[index];
			resultNodes.push(node);
			if(node.isParent){
				getNodesByPNode(node,resultNodes);
			}
		}
		return resultNodes;
	}
	function selectToDept(event, treeId, treeNode) {
		if(treeNode.checked==true){
			jQuery("#hrDeptMerge_toDept").val(treeNode.nameWithoutPerson);
		}else{
			jQuery("#hrDeptMerge_toDept").val("");
		}
	};
	
	function saveHrDeptTransferCallback(data){
		formCallBack(data);
		if(data.statusCode==200){
			if(data.navTabId=='hrDepartmentCurrent_gridtable'){
				var toDeptId = data.toDeptId;
				var fromDeptId = data.fromDeptId;
				if(toDeptId&&fromDeptId){
					dealHrTreeNode('hrDepartmentCurrentLeftTree',{id:fromDeptId,toId:toDeptId},'transfer','dept');
				}
			}
		}
	}
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrDeptTransferForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,saveHrDeptTransferCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="hrDeptMerge.id"/>
					<s:hidden key="hrDeptMerge.state"/>
					<s:hidden key="hrDeptMerge.type"/>
					<s:hidden key="hrDeptMerge.mergeNo"/>
					<s:hidden key="hrDeptMerge.makeDate"/>
					<s:hidden key="hrDeptMerge.makePerson.personId"/>
					<s:hidden key="hrDeptMerge.checkDate"/>
					<s:hidden key="hrDeptMerge.confirmDate"/>
					<s:hidden key="hrDeptMerge.checkPerson.personId"/>
					<s:hidden key="hrDeptMerge.confirmPerson.personId"/>
					<s:hidden key="hrDeptMerge.yearMonth"/>
					
					<s:hidden key="hrDeptMerge.deptIds" id="hrDeptTransfer_deptIds"/>
					<s:hidden key="hrDeptMerge.deptNames" id="hrDeptTransfer_deptNames"/>
					<s:hidden key="hrDeptMerge.orgCode" id="hrDeptTransfer_orgCode"/>
					<s:hidden key="hrDeptMerge.parentId" id="hrDeptTransfer_parentId"/>
					<s:hidden key="hrDeptMerge.deptName" id="hrDeptTransfer_deptName"/>
					<s:hidden key="hrDeptMerge.deptCode" id="hrDeptTransfer_deptCode"/>
				</div>
				<div class="unit">
					<label><s:text name='hrDeptMerge.transferPostAndPerson' />:</label> 
					<s:checkbox key="hrDeptMerge.transferPostAndPerson" name="hrDeptMerge.mergePostAndPerson" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name='被划转部门' />:</label> 
					<span style="float: left; width: 140px"> 
						<input type="text" id="hrDeptMerge_fromDept" readonly="readonly" value="${hrDeptMerge.deptNames}"/>
					</span>
					<label><s:text name='划转到单位/部门' />:</label> 
					<span style="float: left; width: 140px"> 
						<input type="text" id="hrDeptMerge_toDept" readonly="readonly" value="${hrDeptMerge.deptName}"/>
					</span>
					<%-- <s:textfield key="hrDeptMerge.mergeDate" name="hrDeptMerge.mergeDate" cssClass="Wdate required" style="height:15px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
					 --%>
				</div>
				<div class="unit">
					<s:textarea key="hrDeptMerge.transferReason" name="hrDeptMerge.mergeReason" cssClass="" rows="2" cols="53" maxlength="200"/>
				</div>
				<s:if test="%{oper!='view'}">
					<div class="unit">
						<div style="border:1px solid black;width:280px;float:left;margin-left:50px;height: 400px;overflow:auto;" class="unitBox">
							<span style="margin:2px 2px 2px 2px;display:inline;">选择被划转的部门:</span>
							<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:120px" onclick="toggleExpandTree(this,'hrDeptTransfer_mergeFromDepts')">展开</span>
							<div id="hrDeptTransfer_mergeFromDepts" class="ztree"></div>
						</div>
						<div style="border:1px solid black;float:left;width:280px;margin-left:20px;height: 400px;overflow:auto;">
							<span style="margin:2px 2px 2px 2px;display:inline;">选择划转到的单位/部门:</span>
							<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:90px" onclick="toggleExpandTree(this,'hrDeptTransfer_mergeToDepts')">展开</span>
							<div id="hrDeptTransfer_mergeToDepts" class="ztree"></div>
						</div>
					</div>
				</s:if>
				<s:else>
					<c:if test="${deptNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="hrDeptMerge.makePerson"></s:text>:</label>
								<input type="text" value="${hrDeptMerge.makePerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptMerge.checkPerson"></s:text>:</label>
								<input type="text" value="${hrDeptMerge.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptMerge.confirmPerson"></s:text>:</label>
								<input type="text" value="${hrDeptMerge.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:else>
			</div>
			<div class="formBar">
				<ul>
					<li id="hrDeptTransferFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveHrDeptTransferlink"><s:text name="button.save" /></button>
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
