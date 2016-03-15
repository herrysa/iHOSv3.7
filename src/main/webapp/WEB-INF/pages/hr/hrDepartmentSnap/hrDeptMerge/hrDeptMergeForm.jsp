<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=='view'){
			readOnlyForm("hrDeptMergeForm");
		}else{
			jQuery("#hrDeptMerge_deptCode").addClass("required");
			jQuery("#hrDeptMerge_deptName").addClass("required");
			loadHrDeptTreeInDeptMergeFrom();
		}
		jQuery('#saveHrDeptMergelink').click(function() {
			if(validateSelectNodes()==false){
				return;
			}
			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeFromDepts");
			var nodes = hrDeptTreeObj.getCheckedNodes(true);
			var deptIds = '',deptNames='';
			for(var nodeIndex in nodes){
				var node = nodes[nodeIndex];
				deptIds += node.id+",";
				deptNames += node.nameWithoutPerson +",";
			}
			deptIds = deptIds.substring(0,deptIds.length-1);
			deptNames = deptNames.substring(0,deptNames.length-1);
			jQuery("#hrDeptMerge_deptIds").val(deptIds);
			jQuery("#hrDeptMerge_deptNames").val(deptNames);
			jQuery("#hrDeptMergeForm").attr("action","saveHrDeptMerge?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#hrDeptMergeForm").submit();
		});
	});
	
	function loadHrDeptTreeInDeptMergeFrom(){
		var url = "makeHrDeptTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDeptMerge_mergeFromDepts"),ztreesetting_hrDeptTreeInDeptMerge, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			toggleDisabledOrCount({treeId:'hrDeptMerge_mergeFromDepts',
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
			var hrToDeptTree = $.fn.zTree.init($("#hrDeptMerge_mergeToDepts"),ztreesetting_hrDeptTreeInDeptMerge_toDept, hrDeptTreeData);
			var toNodes = hrToDeptTree.getNodes();
			hrToDeptTree.expandNode(toNodes[0], true, false, true);
			toggleDisabledOrCount({treeId:'hrDeptMerge_mergeToDepts',
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
			if('${entityIsNew}'!="true"){
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
					hrDeptTree.expandNode(node,true);
				}
				
			}
		});
	}
	function validateSelectNodes(){
		var fromDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeFromDepts");
		var fromCheckNodes = fromDeptTree.getCheckedNodes(true);
		if(fromCheckNodes.length==0){
			alertMsg.error("请选择被合并的部门。");
		    return false;
		}else if(fromCheckNodes.length==1){
			alertMsg.error("至少选择两个被合并的部门。");
		    return false;
		}else{
			// 检查左侧树选中的节点能否被合并,只要不在同一条路径上即可
			var nodeLevel = fromCheckNodes[0].level;
			for(var index in fromCheckNodes){
				var node = fromCheckNodes[index];
				if(node.level<nodeLevel){
					nodeLevel = node.level;
				}
			}
			var nodes = new Array();
			for(var index in fromCheckNodes){
				var node = fromCheckNodes[index];
				if(node.level==nodeLevel){
					nodes.push(node);
				}
			}
			if(nodes.length==1){
				var node = nodes[0];
				if(node.isParent){
					var resultNodes = new Array();
					resultNodes = getNodesByPNode(node,resultNodes);
					var childNodesMap = new Map();
					childNodesMap.put(node.id,true);
					for(var index in resultNodes){
						var node = resultNodes[index];
						childNodesMap.put(node.id,true);
					}
					var inOnePath = true;
					for(var index in fromCheckNodes){
						var node = fromCheckNodes[index];
						if(!childNodesMap.get(node.id)){
							inOnePath = false;
							break;
						}
					}
					if(inOnePath == true){
						alertMsg.error("您选择的部门在同一路径上，不能合并。");
					    return false;
					}
				}
			}
		}
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
		var toCheckNodes = toDeptTree.getCheckedNodes(true);
		if(toCheckNodes.length==0){
			alertMsg.error("请选择合并到的单位/部门。");
		    return false;
		}else{
			var treeNode = toCheckNodes[0];
			for(var index in fromCheckNodes){
				var node = fromCheckNodes[index];
				if(node.id == treeNode.id){
					alertMsg.error("不能合并到原部门。");
				    return false;
				}
			}
		}
		
		return true;
	}
	var ztreesetting_hrDeptTreeInDeptMerge = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				txtSelectedEnable:false
			},
			check: {
				enable: true,
				chkboxType : { "Y" : "s", "N" : "s" },
				nocheckInherit: false
			},
			callback : {
				beforeDrag:function(){return false;}
				,beforeClick : function(){return false;}
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
	
	function selectFromDept(event, treeId, treeNode) {
		var fromDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeFromDepts");
		var pNode = treeNode.getParentNode();
		if(pNode.subSysTem=='DEPT'){
			if(treeNode.checked==true){
				var childNodes = pNode.children;
				var childAllCheck = true;
				for(var index in childNodes){
					var node = childNodes[index];
					if(node.checked==false){
						childAllCheck = false;
						break;
					}
				}
				if(childAllCheck == true){
					pNode.checked=true;
					fromDeptTree.updateNode(pNode);
				}
			}else{
				var pNodes = new Array();
				pNodes.push(pNode);
				pNodes = getPNodesByNode(pNode,pNodes);
				for(var index in pNodes){
					pNode = pNodes[index];
					if(pNode.checked==true){
						pNode.checked=false;
						fromDeptTree.updateNode(pNode);
					}
				}
			}
		}
	};
	
	function ansyExpandToTree(event, treeId, treeNode){
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
		var toDept = toDeptTree.getNodeByParam("id", treeNode.id, null);
		toDeptTree.expandNode(toDept,true);
	}
	function ansyCollapseToTree(event, treeId, treeNode){
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
		var toDept = toDeptTree.getNodeByParam("id", treeNode.id, null);
		toDeptTree.expandNode(toDept,false);
	}
	
	var ztreesetting_hrDeptTreeInDeptMerge_toDept = {
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
				,onClick : onClickToDept
				,beforeCheck:beforeCheckToDept
				,onCheck:onCheckToDept
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
	
	function onClickToDept(event, treeId, treeNode){
		if(treeNode.subSysTem=='ALL'){
			return false;
		}else{
			var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
			if(treeNode.checked==true){
				toDeptTree.checkNode(treeNode,false,false,true);
			}else{
				toDeptTree.checkNode(treeNode,true,false,true);
			}
		}
	}
	
	function beforeCheckToDept(treeId, treeNode){
		
		var fromDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeFromDepts");
		var fromCheckNodes = fromDeptTree.getCheckedNodes(true);
		if(fromCheckNodes.length==0){
			alertMsg.error("请选择被合并的部门。");
		    return false;
		}else if(fromCheckNodes.length==1){
			alertMsg.error("至少选择两个被合并的部门。");
		    return false;
		}else{
			// 检查左侧树选中的节点能否被合并,只要不在同一条路径上即可
			var nodeLevel = fromCheckNodes[0].level;
			for(var index in fromCheckNodes){
				var node = fromCheckNodes[index];
				if(node.level<nodeLevel){
					nodeLevel = node.level;
				}
			}
			var nodes = new Array();
			for(var index in fromCheckNodes){
				var node = fromCheckNodes[index];
				if(node.level==nodeLevel){
					nodes.push(node);
				}
			}
			if(nodes.length==1){
				var node = nodes[0];
				if(node.isParent){
					var resultNodes = new Array();
					resultNodes = getNodesByPNode(node,resultNodes);
					var childNodesMap = new Map();
					childNodesMap.put(node.id,true);
					for(var index in resultNodes){
						var node = resultNodes[index];
						childNodesMap.put(node.id,true);
					}
					var inOnePath = true;
					for(var index in fromCheckNodes){
						var node = fromCheckNodes[index];
						if(!childNodesMap.get(node.id)){
							inOnePath = false;
							break;
						}
					}
					if(inOnePath == true){
						alertMsg.error("您选择的部门在同一路径上，不能合并。");
					    return false;
					}
				}
			}
		}
		for(var index in fromCheckNodes){
			var node = fromCheckNodes[index];
			if(node.id == treeNode.id){
				alertMsg.error("不能合并到原部门。");
			    return false;
			}
		}
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
		var toCheckNodes = toDeptTree.getCheckedNodes(true);
		if(toCheckNodes){
			for(var index in toCheckNodes){
				var node = toCheckNodes[index];
				toDeptTree.cancelSelectedNode(node);
			}
			jQuery("#hrDeptMerge_deptCode").val("");
			jQuery("#hrDeptMerge_deptName").val("");
			jQuery("#hrDeptMerge_orgCode").val("");
			jQuery("#hrDeptMerge_parentId").val("");
			jQuery("#hrDeptMerge_deptCode").attr("validatorParam","");
			jQuery("#hrDeptMerge_deptName").attr("validatorParam","");
		}
		return true;
	}
	/*
		向上找出所有父级节点
	*/
	function getPNodesByNode(node,resultNodes){
		var pNode = node.getParentNode();
		if(pNode){
			resultNodes.push(pNode);
			getPNodesByNode(pNode,resultNodes);
		}
		return resultNodes;
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
	
	function onCheckToDept(event, treeId, treeNode){
		var orgCode;
		if(treeNode.subSysTem=='ORG'){
			orgCode = treeNode.id;
		}else if(treeNode.subSysTem=='DEPT'){
			orgCode = treeNode.orgCode;
		}
		jQuery("#hrDeptMerge_orgCode").val(orgCode);
		jQuery("#hrDeptMerge_parentId").val(treeNode.id);
		var deptCodevalidatorParam = "SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.deptCode = %value% AND v.orgCode ='"+orgCode+"' UNION SELECT hdm.mergeNo AS checkCode FROM hr_department_merge hdm WHERE hdm.deptCode = %value% AND hdm.orgCode = '"+orgCode+"' AND hdm.state IN ('1','2')";
		var namevalidatorParam = "SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.name = %value% AND v.orgCode ='"+orgCode+"' UNION SELECT hdm.mergeNo AS checkCode FROM hr_department_merge hdm WHERE hdm.deptName = %value% AND hdm.orgCode = '"+orgCode+"' AND hdm.state IN ('1','2')";
		jQuery("#hrDeptMerge_deptCode").attr("validatorParam",deptCodevalidatorParam);
		jQuery("#hrDeptMerge_deptName").attr("validatorParam",namevalidatorParam);
	}
	
	function checkDuplicateFieldInDeptMerge(obj,fieldName){
		var fieldValue = obj.value;
		if(!fieldValue){
			return;
		}
		var returnMessage = "该部门";
		if("deptCode"===fieldName){
			var orignalDeptCode = "${hrDeptMerge.deptCode}";
			if(orignalDeptCode===fieldValue){
				return;
			}
			returnMessage += "编码";
		}else if("name"===fieldName){
			var orignalDeptName = "${hrDeptMerge.deptName}";
			if(orignalDeptName===fieldValue){
				return;
			}
			returnMessage += "名称";
		}
		
		returnMessage += "已存在。";
		var orgCode = jQuery("#hrDeptMerge_orgCode").val();
		$.ajax({
		    url: "checkHrDeptMergeDuplicateField",
		    type: 'post',
		    data:{fieldName:fieldName,fieldValue:fieldValue,orgCode:orgCode,returnMessage:returnMessage},
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
	
	function checkSelectToDept(){
		var toDeptTree = $.fn.zTree.getZTreeObj("hrDeptMerge_mergeToDepts");
		var toCheckNodes = toDeptTree.getCheckedNodes(true);
		if(toCheckNodes.length==0){
			alertMsg.error("请选择合并到的单位/部门。");
		    return;
		}
	}
	
	function saveHrDeptMergeCallback(data){
		formCallBack(data);
		if(data.statusCode==200){
			if(data.navTabId=='hrDepartmentCurrent_gridtable'){
				var disableLeafTreeNodes = data.disableLeafTreeNodes;
				var disableParentTreeNodes = data.disableParentTreeNodes;
				var addTreeNode = data.addTreeNode;
				if(addTreeNode){
// 					console.log("add:"+json2str(addTreeNode));
					dealHrTreeNode("hrDepartmentCurrentLeftTree",addTreeNode,"add",'dept');
				}
				if(disableLeafTreeNodes){
					for(var deptIndex in disableLeafTreeNodes){
// 						console.log("leaf:"+json2str(disableLeafTreeNodes[deptIndex]));
						dealHrTreeNode('hrDepartmentCurrentLeftTree',disableLeafTreeNodes[deptIndex],'rescind','dept');
					}
				}
				if(disableParentTreeNodes){
					for(var deptIndex in disableParentTreeNodes){
// 						console.log("parent:"+json2str(disableParentTreeNodes[deptIndex]));
						dealHrTreeNode('hrDepartmentCurrentLeftTree',disableParentTreeNodes[deptIndex],'rescindParent','dept');
					}
				}
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrDeptMergeForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,saveHrDeptMergeCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="hrDeptMerge.id"/>
					<s:hidden key="hrDeptMerge.state"/>
					<s:hidden key="hrDeptMerge.mergeNo"/>
					<s:hidden key="hrDeptMerge.makeDate"/>
					<s:hidden key="hrDeptMerge.checkDate"/>
					<s:hidden key="hrDeptMerge.confirmDate"/>
					<s:hidden key="hrDeptMerge.yearMonth"/>
					<s:hidden key="hrDeptMerge.type"/>
					<s:hidden key="hrDeptMerge.makePerson.personId"/>
					<s:hidden key="hrDeptMerge.checkPerson.personId"/>
					<s:hidden key="hrDeptMerge.confirmPerson.personId"/>
					<s:hidden key="hrDeptMerge.deptIds" id="hrDeptMerge_deptIds"/>
					<s:hidden key="hrDeptMerge.deptNames" id="hrDeptMerge_deptNames"/>
					<s:hidden key="hrDeptMerge.orgCode" id="hrDeptMerge_orgCode"/>
					<s:hidden key="hrDeptMerge.parentId" id="hrDeptMerge_parentId"/>
				</div>
				<div class="unit">
					<%-- <s:textfield id="hrDeptMerge_mergeType" key="hrDeptMerge.mergeType" cssClass="readonly" readonly="true"/>
					 --%>
					<label><s:text name='hrDeptMerge.mergePostAndPerson' />:</label> 
					<s:checkbox key="hrDeptMerge.mergePostAndPerson" name="hrDeptMerge.mergePostAndPerson" theme="simple"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield id="hrDeptMerge_deptCode" key="hrDeptMerge.deptCode" name="hrDeptMerge.deptCode"  onfocus="checkSelectToDept()" 
					notrepeat='部门编码已存在' validatorType="sql"  validatorParam=""/>
					<s:textfield id="hrDeptMerge_deptName" key="hrDeptMerge.deptName" name="hrDeptMerge.deptName"  onfocus="checkSelectToDept()" 
					notrepeat='部门名称已存在' validatorType="sql"  validatorParam=""/>
				</s:if>
				<s:else>
					<s:textfield id="hrDeptMerge_deptCode" key="hrDeptMerge.deptCode" name="hrDeptMerge.deptCode"  onfocus="checkSelectToDept()" 
					notrepeat='部门编码已存在' validatorType="sql"  validatorParam="" oldValue="'${hrDeptMerge.deptCode}'"/>
					<s:textfield id="hrDeptMerge_deptName" key="hrDeptMerge.deptName" name="hrDeptMerge.deptName"  onfocus="checkSelectToDept()" 
					notrepeat='部门名称已存在' validatorType="sql"  validatorParam="" oldValue="'${hrDeptMerge.deptName}'"/>
				</s:else>
				</div>
				<s:if test="%{oper=='view'}">
					<div class="unit">
					<s:textarea key="hrDeptMerge.deptIds"  value="%{hrDeptMerge.deptNames}" maxlength="200" rows="2" cols="53"/>
					</div>
				</s:if>
				<div class="unit">
					<%-- <s:textfield key="hrDeptMerge.mergeDate" name="hrDeptMerge.mergeDate" cssClass="Wdate required" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
					 --%>
					 <s:textarea key="hrDeptMerge.mergeReason" name="hrDeptMerge.mergeReason" maxlength="200" rows="2" cols="53"/>
				</div>
				<s:if test="%{oper!='view'}">
					<div class="unit">
						<div style="border:1px solid black;width:280px;float:left;margin-left:50px;height: 400px;overflow:auto;" class="unitBox">
							<span style="margin:2px 2px 2px 2px;display:inline;">选择被合并的部门:</span>
							<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:120px" onclick="toggleExpandTree(this,'hrDeptMerge_mergeFromDepts')">展开</span>
							<div id="hrDeptMerge_mergeFromDepts" class="ztree"></div>
						</div>
						<div style="border:1px solid black;float:left;width:280px;margin-left:20px;height: 400px;overflow:auto;">
							<span style="margin:2px 2px 2px 2px;display:inline;">选择合并到的单位/部门:</span>
							<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:90px" onclick="toggleExpandTree(this,'hrDeptMerge_mergeToDepts')">展开</span>
							<div id="hrDeptMerge_mergeToDepts" class="ztree"></div>
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
					<li id="hrDeptMergeFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveHrDeptMergelink"><s:text name="button.save" /></button>
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
