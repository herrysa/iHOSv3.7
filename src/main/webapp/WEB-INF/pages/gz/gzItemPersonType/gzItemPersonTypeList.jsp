<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
	//取出当前的工资类别
	var gzItemPersonTypeCurGzTypeId = '${now}';
	jQuery(document).ready(function() {
		if(gzItemPersonTypeCurGzTypeId){
			jQuery("#gzItemPersonType_gzType").val(gzItemPersonTypeCurGzTypeId);
		}else{
			gzItemPersonTypeCurGzTypeId = jQuery("#gzItemPersonType_gzType").val();
		}
		gzItemPersonTypeLayout();
		personTypeLeftTree();
		formulamiddleTree(gzItemPersonTypeCurGzTypeId);
		formulaRightTree(gzItemPersonTypeCurGzTypeId);
	});
	function formulaRightTree(gzType) {
		$.get("makeformulaMiddleTree?gzType=" + gzType, {
			"_" : $.now()
		}, function(data) {
			var FormulaTreeData = data.formulaTreeNodes;
			var formulaRightTree = $.fn.zTree.init($("#formulaTreeRight"),
					ztreesetting_formulaRight, FormulaTreeData);
			function filter(node) {
				return (node.id != "-1");
			}
			var nodes = formulaRightTree.getNodesByFilter(filter); // 查找节点集合
			formulaRightTree.expandNode(nodes[0], true, false, true);
			formulaRightTree.hideNodes(nodes);
		});
	}
	var ztreesetting_formulaRight = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag : zTreeBeforeDrag
		},
		check : {
			enable : true,
			chkStyle : "checkbox",
			radioType : "all"
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

	function formulamiddleTree(gzType) {
		$.get("makeformulaMiddleTree?gzType=" + gzType, {
			"_" : $.now()
		}, function(data) {
			var FormulaTreeData = data.formulaTreeNodes;
			var formulaMiddleTree = $.fn.zTree.init($("#formulaTreeMiddle"),
					ztreesetting_formulaMiddle, FormulaTreeData);
			var nodes = formulaMiddleTree.getNodes()
			formulaMiddleTree.expandNode(nodes[0], true, false, true);
			if (nodes.length == 1) {
				return;
			}
			for ( var i = 0; i < nodes[0].children.length; i++) {
				var node = nodes[0].children[i];
				if (node.isParent == true && node.check_Child_State == "-1") {
					formulaMiddleTree.setChkDisabled(node, true, false, false);
					formulaMiddleTree.updateNode(node);
				}
			}
		});
	}
	var ztreesetting_formulaMiddle = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : true,
		},
		check : {
			enable : true,
			chkStyle : "checkbox",
			radioType : "all"
		},
		callback : {
			beforeDrag : zTreeBeforeDrag
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

	function personTypeLeftTree() {
		$.get("makePersonTypeTree", {
			"_" : $.now()
		}, function(data) {
			var personTypeTreeData = data.personTypeTreeNodes;
			var personTypeTree = $.fn.zTree.init($("#personTypeTreeLeft"),
					ztreesetting_personTypeLeft, personTypeTreeData);
			var nodes = personTypeTree.getNodes();
			personTypeTree.expandNode(nodes[0], true, false, true);
		});

	}
	var ztreesetting_personTypeLeft = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			onClick : PersonTypeTreeOnClick,
			beforeDrag : zTreeBeforeDrag
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
	function zTreeBeforeDrag(treeId, treeNodes) {
		return false;
	};
	function allToRight() {
		var formulaMiddleTree = $.fn.zTree.getZTreeObj("formulaTreeMiddle");
		var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
		var nodes = formulaRightTree.getNodesByParam("isHidden", true, null);
		formulaRightTree.showNodes(nodes);
		for ( var i = 0; i < nodes.length; i++) {
			var leftNode = formulaMiddleTree.getNodesByParam("id", nodes[i].id,
					null);
			formulaMiddleTree.hideNodes(leftNode);
		}
		extraNodes = formulaMiddleTree.getNodesByParam("isHidden", false, null);
		for ( var j = 0; j < extraNodes.length; j++) {
			if (extraNodes[j].id != "-1") {
				var leftNode = formulaMiddleTree.getNodesByParam("id",
						extraNodes[j].id, null);
				formulaRightTree.hideNodes(leftNode);
			}

		}
		var showNodes = formulaRightTree.getNodesByParam("isHidden", false);
		for ( var z = 0; z < showNodes.length; z++) {
			showNodes[z].nocheck = false;
			formulaMiddleTree.updateNode(showNodes[z]);
		}
		//将展开的形式进行复制
		var hideNodes = formulaMiddleTree.getNodesByParam("isHidden", true,
				null);
		for ( var k = 0; k < nodes.length; k++) {
			var nds = formulaRightTree.getNodesByParam("id", hideNodes[k].id);
			nds[0].checked = hideNodes[k].checked;
			formulaRightTree.updateNode(nds[0]);
			if (nds[0].checked == true) {
				var root = formulaRightTree.getNodeByParam("id", "-1");
				root.checked = true;
				formulaRightTree.updateNode(root);
			}
			if (hideNodes[k].open == false) {
				formulaRightTree.expandNode(nds[0], false, true, false);
			} else {
				formulaRightTree.expandNode(nds[0], true, true, true);
			}
		}

	}
	function allToleft() {
		var formulaMiddleTree = $.fn.zTree.getZTreeObj("formulaTreeMiddle");
		var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
		var nodes = formulaMiddleTree.getNodesByParam("isHidden", true, null);
		formulaMiddleTree.showNodes(nodes);
		for ( var i = 0; i < nodes.length; i++) {
			var leftNode = formulaRightTree.getNodesByParam("id", nodes[i].id,
					null);
			formulaRightTree.hideNodes(leftNode);
		}
		extraNodes = formulaRightTree.getNodesByParam("isHidden", false, null);
		for ( var j = 0; j < extraNodes.length; j++) {
			if (extraNodes[j].id != "-1") {
				var leftNode = formulaRightTree.getNodesByParam("id",
						extraNodes[j].id, null);
				formulaRightTree.hideNodes(leftNode);
			}

		}
		var showNodes = formulaMiddleTree.getNodesByParam("isHidden", false);
		for ( var z = 0; z < showNodes.length; z++) {
			showNodes[z].nocheck = false;
			formulaMiddleTree.updateNode(showNodes[z]);
		}
		//将展开的形式进行复制
		var hideNodes = formulaRightTree
				.getNodesByParam("isHidden", true, null);
		for ( var k = 0; k < nodes.length; k++) {
			var nds = formulaMiddleTree.getNodesByParam("id", hideNodes[k].id);
			nds[0].checked = hideNodes[k].checked;
			formulaMiddleTree.updateNode(nds[0]);
			if (nds[0].checked == true) {
				var root = formulaMiddleTree.getNodeByParam("id", "-1");
				root.checked = true;
				formulaMiddleTree.updateNode(root);
			}
			if (hideNodes[k].open == false) {
				formulaMiddleTree.expandNode(nds[0], false, true, false);
			} else {
				formulaMiddleTree.expandNode(nds[0], true, true, true);
			}
		}
	}
	function toRight() {

		var formulaMiddleTree = $.fn.zTree.getZTreeObj("formulaTreeMiddle");
		var selectNodes = formulaMiddleTree.getCheckedNodes(true);
		for ( var i = 0; i < selectNodes.length; i++) {
			if (selectNodes[i].isParent == true) {
				continue;
			}
			var treeNode = selectNodes[i];
			//以下是删除原先树节点的操作
			if (treeNode.getNextNode() == null && treeNode.getPreNode() == null) {
				formulaMiddleTree.hideNode(treeNode.getParentNode());
			} else {
				if (treeNode.getNextNode() == null
						|| treeNode.getPreNode() == null) {
					if (treeNode.getNextNode() == null
							&& treeNode.getPreNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					} else if (treeNode.getPreNode() == null
							&& treeNode.getNextNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					}
				} else {
					if (treeNode.getNextNode().isHidden == true
							&& treeNode.getPreNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					}
				}
			}
			formulaMiddleTree.hideNode(treeNode);
			//该显示的东西
			var nodes = formulaMiddleTree.getNodesByParam("isHidden", false,
					null);

			//以下操作是对新树的插入节点操作
			var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
			for ( var j = 1; j < nodes.length; j++) {
				var rightNode = formulaRightTree.getNodesByParam("id",
						nodes[j].id, null);
				formulaRightTree.hideNode(rightNode[0]);
			}
			//再将已经隐藏掉的父节点展现出来 
			var nodes2 = formulaMiddleTree.getNodesByParam("isHidden", true,
					null);
			for ( var k = 0; k < nodes2.length; k++) {
				var rightNode = formulaRightTree.getNodesByParam("id",
						nodes2[k].id, null);
				var mParent  = rightNode[0].getParentNode()
				rightNode[0].checked = nodes2[k].checked;
				formulaRightTree.updateNode(rightNode[0]);
				formulaRightTree.showNode(mParent); //展现父节点
				mParent.checked = nodes2[k].getParentNode().checked;
				formulaRightTree.updateNode(mParent);
				rightNode[0].getParentNode().nocheck = false;
				formulaRightTree.showNode(rightNode[0]); //展现子节点
				rightNode[0].nocheck = false;
				formulaRightTree.updateNode(rightNode[0], false);
				if (nodes2[k].open == false) {
					formulaRightTree.expandNode(rightNode[0], false, true,
							false);
			    } else {
					formulaRightTree.expandNode(rightNode[0], true, true, true);	
				
			    }
				if(nodes2[k].getParentNode().open == false){
					formulaRightTree.expandNode(mParent, false, true,
							false);
				}else{
					formulaRightTree.expandNode(mParent, true, true, true);	
				}
		}

	}
  }
	function toLeft() {
		var formulaMiddleTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
		var selectNodes = formulaMiddleTree.getCheckedNodes(true);
		for ( var i = 0; i < selectNodes.length; i++) {
			if (selectNodes[i].isParent == true) {
				continue;
			}
			var treeNode = selectNodes[i];
			//以下是删除原先树节点的操作
			if (treeNode.getNextNode() == null && treeNode.getPreNode() == null) {
				formulaMiddleTree.hideNode(treeNode.getParentNode());
			} else {
				if (treeNode.getNextNode() == null
						|| treeNode.getPreNode() == null) {
					if (treeNode.getNextNode() == null
							&& treeNode.getPreNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					} else if (treeNode.getPreNode() == null
							&& treeNode.getNextNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					}
				} else {
					if (treeNode.getNextNode().isHidden == true
							&& treeNode.getPreNode().isHidden == true) {
						formulaMiddleTree.hideNode(treeNode.getParentNode());
					}
				}

			}
			formulaMiddleTree.hideNode(treeNode);
			//该显示的东西
			var nodes = formulaMiddleTree.getNodesByParam("isHidden", true,
					null);

			//以下操作是对新树的插入节点操作
			var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeMiddle");
			for ( var j = 0; j < nodes.length; j++) {
				var rightNode = formulaRightTree.getNodesByParam("id",
						nodes[j].id, null);
				formulaRightTree.showNode(rightNode[0]);
			}
			var nodes2 = formulaMiddleTree.getNodesByParam("isHidden", true,
					null);
			for ( var k = 0; k < nodes2.length; k++) {
				var rightNode = formulaRightTree.getNodesByParam("id",
						nodes2[k].id, null);
				var mParent  = rightNode[0].getParentNode()
				rightNode[0].checked = nodes2[k].checked;
				formulaRightTree.updateNode(rightNode[0]);
				formulaRightTree.showNode(mParent); //展现父节点
				mParent.checked = nodes2[k].getParentNode().checked;
				formulaRightTree.updateNode(mParent);
				rightNode[0].getParentNode().nocheck = false;
				formulaRightTree.showNode(rightNode[0]); //展现子节点
				rightNode[0].nocheck = false;
				formulaRightTree.updateNode(rightNode[0], false);
				if (nodes2[k].open == false) {
						formulaRightTree.expandNode(rightNode[0], false, true,
								false);
				} else {
						formulaRightTree.expandNode(rightNode[0], true, true, true);	
					
				}
				if(nodes2[k].getParentNode().open == false){
					formulaRightTree.expandNode(mParent, false, true,
							false);
				}else{
					formulaRightTree.expandNode(mParent, true, true, true);	
				}
			}
		}
	}
	/*保存*/
	function addgzPersonType() {

		//给form里的内容赋值
		var fomulaIds = "";
		//抓到右面的树
		var personTypeTree = $.fn.zTree.getZTreeObj("personTypeTreeLeft");
		var selectNodes = personTypeTree.getSelectedNodes();
		if (selectNodes.length == 0) {
			alertMsg.warn("请选择人员类别！");
			return;
		}
		var personType = selectNodes[0].name;
		var gzType = jQuery("#gzItemPersonType_gzType").val();
		var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
		var nodes = formulaRightTree.getNodesByParam("isHidden", false, null);
		for ( var i = 0; i < nodes.length; i++) {
			if (nodes[i].level == 2) {
				var fomulaId = nodes[i].id;
				fomulaIds += fomulaId + ",";
			} else {
				continue;
			}

		}
		if(fomulaIds){
			fomulaIds = fomulaIds.substring(0,fomulaIds.length-1);
		}
		//提交
		jQuery.post("saveGzPersonType", {
			fomulaIds:fomulaIds,
			personTypeId:personType,
			gzTypeId:gzType
		}, function(data) {
			alertMsg.correct("保存成功。");
		});

	}
	function PersonTypeTreeOnClick(event, treeId, treeNode) {
		var gzType = jQuery("#gzItemPersonType_gzType").val(); //获取工资类型
		var formulaRightTree = $.fn.zTree.getZTreeObj("formulaTreeRight");
		var formulaMiddleTree = $.fn.zTree.getZTreeObj("formulaTreeMiddle");
		$.post("getShowNodes",{empType : treeNode.name,gzType : gzType},function(data) {
							//先将已展现的结点屏蔽掉

							var showNodes = formulaRightTree.getNodesByParam(
									"isHidden", false);
							for ( var z = 1; z < showNodes.length; z++) {
								formulaRightTree.hideNode(showNodes[z]);
							}
							//建立一个数组
							var formulaIds = new Array();
							//获得数据先将右面的树展现出来
							var data = eval('(' + data + ')');
							if (data.nodes == "null") {
								allToleft();
								return;
							}
							var fomulaId = data.nodes;
							if (fomulaId.indexOf(',') > 0) {
								formulaIds = fomulaId.split(",");
							} else {
								formulaIds[0] = fomulaId;
							}
							for ( var i = 0; i < formulaIds.length; i++) {
								var rightNode = formulaRightTree
										.getNodesByParam("id", formulaIds[i],
												null);
								formulaRightTree.showNode(rightNode[0]
										.getParentNode()); //展现父节点
								rightNode[0].getParentNode().nocheck = false;
								formulaRightTree.showNode(rightNode[0]); //展现子节点
								rightNode[0].nocheck = false;
							}
							for ( var j = 0; j < formulaIds.length; j++) {
								var treeNode = formulaMiddleTree
										.getNodesByParam("id", formulaIds[j],
												null)[0];
								if (treeNode.getNextNode() == null
										&& treeNode.getPreNode() == null) {
									formulaMiddleTree.hideNode(treeNode
											.getParentNode());
								} else {
									if (treeNode.getNextNode() == null
											|| treeNode.getPreNode() == null) {
										if (treeNode.getNextNode() == null
												&& treeNode.getPreNode().isHidden == true) {
											formulaMiddleTree.hideNode(treeNode
													.getParentNode());
										} else if (treeNode.getPreNode() == null
												&& treeNode.getNextNode().isHidden == true) {
											formulaMiddleTree.hideNode(treeNode
													.getParentNode());
										}
									} else {
										if (treeNode.getNextNode().isHidden == true
												&& treeNode.getPreNode().isHidden == true) {
											formulaMiddleTree.hideNode(treeNode
													.getParentNode());
										}
									}

								}
								formulaMiddleTree.hideNode(treeNode);
							}
							var nodes2 = formulaRightTree.getNodesByParam(
									"isHidden", true, null);
							for ( var k = 0; k < nodes2.length; k++) {
								var rightNode = formulaMiddleTree
										.getNodesByParam("id", nodes2[k].id,
												null);
								formulaMiddleTree.showNode(rightNode[0]
										.getParentNode()); //展现父节点
								rightNode[0].getParentNode().nocheck = false;
								formulaMiddleTree.showNode(rightNode[0]); //展现子节点
								rightNode[0].nocheck = false;
							}
						});
	};

	function gzItemPersonTypeLayout() {
		var gzItemPersonTypeFullSize = jQuery("#container").innerHeight()-56;
		jQuery("#gzItemPersonType_container").outerHeight(gzItemPersonTypeFullSize);
		jQuery("#readyToChooseItemDiv").height(gzItemPersonTypeFullSize-80);
		jQuery("#readyToChooseItemDiv > fieldset").height(gzItemPersonTypeFullSize-80);
		jQuery("#choosedItemDiv").height(gzItemPersonTypeFullSize-80);
		jQuery("#choosedItemDiv > fieldset").height(gzItemPersonTypeFullSize-80);
		jQuery("#gzItemPersonType_container").layout({
			applyDefaultStyles : false,
			west__size : 185,
			center__size : 906,
			spacing_open : 5,//边框的间隙  
			spacing_closed : 5,//关闭时边框的间隙 
			resizable : true,
			resizerClass : "ui-layout-resizer-blue",
			slidable : true,
			resizerDragOpacity : 1,
			resizerTip : "可调整大小",
			onresize_end : function(paneName, element, state, options,
					layoutName) {
				if ("center" == paneName) {
				}
			}
		});
	}
	function changeGzType() {
		alertMsg.confirm("切换后您未保存工资项将会清空，确认切换？", {
			okCall : function() {
				var gzType = jQuery("#gzItemPersonType_gzType").val();
				$.fn.zTree.destroy("formulaTreeMiddle");
				$.fn.zTree.destroy("formulaTreeMiddle");
				formulamiddleTree(gzType);
				formulaRightTree(gzType);
			}
		})
	}
	function toggleExpandGzPersonTree(obj, treeId) {
		var hrTree = $.fn.zTree.getZTreeObj(treeId);
		var obj = jQuery(obj);
		var text = obj.html();
		if (text == '展开') {
			obj.html("折叠");
			var expendArr = new Array();
			expandHrNode(hrTree, hrTree.getNodes()[0], expendArr);
			/*for(var i=0;i<expendArr.length;i++){
				var node = expendArr[i];
				setTimeout(function(){
					hrTree.expandNode(node,true,false,true);
				},10);
			}*/
			var nodeIndex = 0, nodeLength = expendArr.length;
			var expendTimer = setInterval(function() {
				if (nodeIndex > nodeLength - 1) {
					clearInterval(expendTimer);
				} else {
					var node = expendArr[nodeIndex];
					if (node) {
						hrTree.expandNode(node, true, false, false);
					}
					nodeIndex++;
				}
			}, 10);
			//hrTree.expandAll(true);
		} else {
			obj.html("展开");
			//zTree.expandAll(false);
			var allNodes = hrTree.transformToArray(hrTree.getNodes());
			for ( var nodeIndex in allNodes) {
				var node = allNodes[nodeIndex];
				hrTree.expandNode(node, false, true, false);
			}
		}
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="savebutton" href="javaScript:addgzPersonType()"><span><fmt:message
								key="button.save" /> </span> </a></li>
			</ul>
		</div>
		<div id="gzItemPersonType_container">
			<div id="gzItemPersonType_layout-west" class="pane ui-layout-west"
				style="width: 200px; float: left; overflow: auto;">
				<div class="treeTopCheckBox">
					<label id="gzItemPersonType_expandTree"
						onclick="toggleExpandGzPersonTree(this,'personTypeTreeLeft')">展开</label>
				</div>
				<div id="personTypeTreeLeft" class="ztree"></div>
			</div>
			<div id="personLeftTree_layout-center" class="pane ui-layout-center" style="vertical-align: middle;">
				<div style="height: 30px;margin: 10px;">
					<span style="">工资类别:</span>
					<s:select id="gzItemPersonType_gzType" headerKey="" list="#request.gztypes"
						listKey="gzTypeId" listValue="gzTypeName" emptyOption="false"
						maxlength="50" width="50px" onchange="changeGzType()">
					</s:select>
				</div>
				<div id="readyToChooseItemDiv" style="float: left;margin-top: -20px;margin-bottom: 10px">
					<fieldset style="overflow: auto;width: 220px;padding: 10px;margin: 10px;color: #333;border: #06c solid 1px;">
						<legend style="color: #06c;font-weight: 800;background: #fff;">待选择的工资项</legend>
						<div style="width: 215px;height: 95%;overflow: auto;">
							<ul id="formulaTreeMiddle" class="ztree"></ul>
						</div>
					</fieldset>
				</div>
				<table style="float: left;margin-top:50px">
					<tr style="height:50px">
						<td>
							<button id="toRight" onclick="allToRight()" style="float: left;width: 45px;">>></button>
						</td>
					</tr>
					<tr style="height:50px">
						<td>
							<button id="toRightone" onclick="toRight()" style="float: left;width: 45px;">></button>
						</td>
					</tr>
					<tr style="height:50px">
						<td>
							<button id="toLeftone" onclick="toLeft()" style="float: left;width: 45px;"><</button>
						</td>
					</tr>
					<tr style="height:50px">
						<td>
							<button id="toLeft" onclick="allToleft()" style="float: left;width: 45px;"><<</button>
						</td>
					</tr>
				</table>
				<div id="choosedItemDiv" style="float: left;margin-top: -20px;">
					<fieldset style="width: 220px;padding: 10px;margin: 10px;color: #333;border: #06c solid 1px;">
						<legend style="color: #06c;font-weight: 800;background: #fff;">已经选择的工资项</legend>
						<div style="width: 215px;height: 95%;overflow: auto;">
							<ul id="formulaTreeRight" class="ztree"></ul>
						</div>
					</fieldset>
				</div>
			</div>
			<!-- center -->
		</div>
		<!-- layout -->
	</div>
</div>