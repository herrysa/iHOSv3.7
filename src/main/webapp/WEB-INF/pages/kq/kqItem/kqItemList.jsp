
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqItemLayout;
	var kqItemGridIdString="#kqItem_gridtable";
	
	jQuery(document).ready(function() {
		//layout
		var kqItemFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('kqItem_container','kqItem_gridtable',kqItemFullSize);
		//leftTree
		loadKqItemTree();
		//jqGrid
		var kqItemGrid = jQuery(kqItemGridIdString);
    	kqItemGrid.jqGrid({
    		url : "kqItemGridList?1=1",
    		editurl:"kqItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'kqItemId',index:'kqItemId',align:'center',label : '<s:text name="kqItem.kqItemId" />',hidden:true,key:true},
{name:'kqItemCode',index:'kqItemCode',width:80,align:'left',label : '<s:text name="kqItem.kqItemCode" />',hidden:false},
{name:'kqItemName',index:'kqItemName',width:110,align:'left',label : '<s:text name="kqItem.kqItemName" />',hidden:false},
{name:'shortName',index:'shortName',width:30,align:'left',label : '<s:text name="kqItem.shortName" />',hidden:false},
{name:'frequency',index:'frequency',width:50,align:'center',label : '<s:text name="kqItem.frequency" />',hidden:false,formatter:'select',editoptions:{value:"0:天数;1:次数;2:小时"}},
{name:'isCoveroffGeneralHoliday',index:'isCoveroffGeneralHoliday',width:80,align:'center',label : '<s:text name="kqItem.isCoveroffGeneralHoliday" />',hidden:false,formatter:'checkbox'},
{name:'isDefault',index:'isDefault',width:80,align:'center',label : '<s:text name="kqItem.isDefault" />',hidden:false,formatter:'checkbox'},
{name:'isYearHoliday',index:'isYearHoliday',width:80,align:'center',label : '<s:text name="kqItem.isYearHoliday" />',hidden:false,formatter:'checkbox'},
{name:'isUsed',index:'isUsed',width:80,align:'center',label : '<s:text name="kqItem.isUsed" />',hidden:false,formatter:'checkbox'},
{name:'parentId',index:'parentId',align:'center',label : '<s:text name="kqItem.parentId" />',hidden:true}
			],jsonReader : {
				root : "kqItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'kqItemId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="kqItemList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
        		var treeObj = $.fn.zTree.getZTreeObj("kqItemTree");
        		var selectNode = treeObj.getNodeByParam("id",rowid,null);
        		treeObj.selectNode(selectNode);
       		},
		 	gridComplete:function(){
		 		//layout
		 		gridContainerResize('kqItem','layout');
           		//when select tree node
           		var rowNum = jQuery(this).getDataIDs().length;
           		if(rowNum > 0) {
					var treeObj = $.fn.zTree.getZTreeObj("kqItemTree");
					if(treeObj) {
						var selectedNode = treeObj.getSelectedNodes();
						if(selectedNode && selectedNode.length == 1) {
							var sid = selectedNode[0].id;
							if(sid != "-1" && selectedNode[0].pId != "-1") {
								jQuery(this).jqGrid("setSelection",sid);
							}
						}
					}
           		} else {
           			var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
           		}
           		
           		var dataTest = {"id":"kqItem_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		makepager("kqItem_gridtable");
       		} 

    	});
    jQuery(kqItemGrid).jqGrid('bindKeys');
  	});
  	/*----------------------------------tooBar start-----------------------------------------------*/
	var kqItem_menuButtonArrJson = "${menuButtonArrJson}";
	var kqItem_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(kqItem_menuButtonArrJson,false)));
	var kqItem_toolButtonBar = new ToolButtonBar({el:$('#kqItem_buttonBar'),collection:kqItem_toolButtonCollection,attributes:{
		tableId : 'kqItem_gridtable',
		baseName : 'kqItem',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="kqItemNew.title"/>',
		editTitle : null
	}}).render();
	
	var kqItem_function = new scriptFunction();
	kqItem_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#kqItem_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("只能选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};
	/*添加*/
	kqItem_toolButtonBar.addCallBody('1006010201',function() {
		var urlString = "editKqItem?popup=true&navTabId=kqItem_gridtable";
		var kqItemTreeObj = $.fn.zTree.getZTreeObj("kqItemTree");
		var selectedNodes = kqItemTreeObj.getSelectedNodes();
		if(selectedNodes.length == 1 && selectedNodes[0].subSysTem == 'PITEM') {
			var selectedNode = selectedNodes[0];
			urlString += "&parentId=" + selectedNode.id;
			urlString = encodeURI(urlString);
			var winTitle = '<s:text name="kqItemNew.title"/>';
			$.pdialog.open(urlString,"addKqItem",winTitle,{mask:true,width:385,height:300,maxable:false,resizable:false});
		} else {
			alertMsg.error("请选择一个考勤项目类别！");
			return;
		}
	},{});
	kqItem_toolButtonBar.addBeforeCall('1006010201',function(e,$this,param){
		return kqItem_function.optBeforeCall(e,$this,param);
	},{});
	/*删除*/
	kqItem_toolButtonBar.addCallBody('1006010202',function() {
		var sid = jQuery(kqItemGridIdString).jqGrid("getGridParam","selarrrow");
		 for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#kqItem_gridtable").jqGrid('getRowData',rowId);
				if(row['isUsed'] == "Yes"){
					alertMsg.error("该项目已使用！");
					return;
				}
		}
		var urlString = "kqItemGridEdit?id="+sid+"&navTabId=kqItem_gridtable&oper=del";
		alertMsg.confirm("确认删除？",{
			okCall:function() {
				jQuery.post(urlString,function(data) {
					formCallBack(data);
					if(data.statusCode != 200) {
						return;
					}
					reloadKqItemGrid();
					for(var i = 0;i < sid.length; i++) {
						dealKqItemTreeLoad("kqItemTree",{id:sid[i]},"del");
					}
				});
			}
		});
	},{});
	kqItem_toolButtonBar.addBeforeCall('1006010202',function(e,$this,param){
		return kqItem_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	/*修改*/
	kqItem_toolButtonBar.addCallBody('1006010203',function() {
		var urlString = "editKqItem?popup=true&navTabId=kqItem_gridtable";
		var sid = jQuery(kqItemGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid && sid.length == 1) {
			urlString += "&kqItemId=" + sid[0];
			urlString = encodeURI(urlString);
			var winTitle = '<s:text name="kqItemEdit.title"/>';
			$.pdialog.open(urlString,"editKqItem",winTitle,{mask:true,width:385,height:300,maxable:false,resizable:false});
		}
	},{});
	kqItem_toolButtonBar.addBeforeCall('1006010203',function(e,$this,param){
		return kqItem_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	/*----------------------------------tooBar end -----------------------------------------------*/
	function loadKqItemTree(){
		$.get("makeKqItemTree", function(data) {
			var kqItemTreeData = data.kqItemTreeNodes;
			var kqItemTree = $.fn.zTree.init($("#kqItemTree"),ztreesetting_kqItemTree, kqItemTreeData);
			var nodes = kqItemTree.getNodes();
			kqItemTree.expandNode(nodes[0], true, false, true);
			kqItemTree.selectNode(nodes[0]);
		});
		jQuery("#kqItem_expandTree").text("展开");
	}
	var ztreesetting_kqItemTree = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "kqItemGridList?1=1";
			    var nodeId = treeNode.id;
			    var parentId = treeNode.pId;
			    if(nodeId != "-1") {
			    	if(parentId == "-1") {
			    		urlString += "&parentId=" + nodeId; 
			    	} else {
			    		urlString += "&nodeId=" + nodeId;
			    	}
			    }
				urlString=encodeURI(urlString);
				jQuery("#kqItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
				/* if(nodeId != "-1" && parentId != "-1") {
					setTimeout(function(){
						jQuery("#kqItem_gridtable").jqGrid("setSelection",nodeId);
					},100);
				} */
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
		}
	};
	function dealKqItemTreeLoad(treeId,node,opt) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodeId = node.id;
		var oldNode = treeObj.getNodeByParam("id", nodeId, null);
		if(node) {
			switch(opt) {
				case "add" :
				var parentNode = treeObj.getNodeByParam("id",node.pId,null);
				treeObj.addNodes(parentNode,node);
				break;
				case "edit" :
				if(oldNode) {
					oldNode.name=node.name;
					oldNode.icon=node.icon;
					treeObj.updateNode(oldNode);
				}
				break;
				case "del" :
				if(oldNode) {
					treeObj.removeNode(oldNode);
				}
				break;
			}
		}
	}
	function kqItemSearchFormReaload() {
		var urlString = "kqItemGridList?1=1";
		propertyFilterSearch('kqItem_search_form','kqItem_gridtable',true);
		var postData = $("#kqItem_gridtable").jqGrid("getGridParam", "postData");
		var addFilters = postData['addFilters'];
		var treeObj = $.fn.zTree.getZTreeObj("kqItemTree");
		/* if(addFilters!=false){
			var selectedNode = treeObj.getNodeByParam('id','-1',null);
			treeObj.selectNode(selectedNode,false);
		} */
		var selectedNodes = treeObj.getSelectedNodes();
	    var selectedNode ,nodeId ;
	    if(selectedNodes){
	    	selectedNode = selectedNodes[0];
	    	if(selectedNode){
				nodeId = selectedNode.id;
	    	}
	    }
	    
	    if(nodeId&&nodeId!="-1"){
	    	if(selectedNode.subSysTem==='PITEM'){
		    	urlString += "&parentId="+nodeId;
	    	}else{
		    	urlString += "&kqItemId="+nodeId;
	    	}
	    }
		urlString=encodeURI(urlString);
		jQuery("#kqItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		
		/* var urlString = "kqItemGridList?1=1";
		propertyFilterSearch("kqItem_search_form","kqItem_gridtable",true);
		urlString=encodeURI(urlString);
		jQuery("#kqItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); */
	}
	function reloadKqItemGrid() {
		jQuery("#kqItem_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
	}
	/*
 	* 展开\折叠树
 	*/
	function toggleExpandKqItemTree(obj,treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId); 
		var obj = jQuery(obj);
		var text = obj.html();
			if(text=='展开'){
			obj.html("折叠");
			zTree.expandAll(true);
		}else{
			obj.html("展开");
			//zTree.expandAll(false);
			var allNodes = zTree.transformToArray(zTree.getNodes());
			for(var nodeIndex in allNodes){
				var node = allNodes[nodeIndex];
				if(node.subSysTem=='PITEM'){
					zTree.expandNode(node,false,true,true);
				}
			}
		}
	}
</script>

<div class="page">
	<div id="kqItem_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqItem_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='kqItem.kqItemCode'/>:
						<input type="text" name="filter_LIKES_kqItemCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqItem.kqItemName'/>:
						<input type="text" name="filter_LIKES_kqItemName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqItem.shortName'/>:
						<input type="text" name="filter_LIKES_shortName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqItem.frequency'/>:
						<s:select list="#{'':'--','0':'天数','1':'次数','2': '小时'}" name="filter_EQI_frequency" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='kqItem.isDefault'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_isDefault"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<label class="queryarea-label" >
						<s:text name='kqItem.isYearHoliday'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_isYearHoliday"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='kqItem.isCoveroffGeneralHoliday'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_isCoveroffGeneralHoliday"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="kqItemSearchFormReaload();"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqItem_buttonBar">
			<%-- <ul class="toolBar">
				<li><a id="kqItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="kqItem_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="kqItem_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul> --%>
		</div>
		<div id="kqItem_container">
			<div id="kqItem_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="float:right"> 
 					<span id="kqItem_expandTree" onclick="toggleExpandKqItemTree(this,'kqItemTree')" style="vertical-align:middle;text-decoration:underline;cursor:pointer;">展开</span>
				</div>
				<div id="kqItemTree" class="ztree"></div>
			</div>
			<div id="kqItem_layout-center" class="pane ui-layout-center">
				<div id="kqItem_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
					<input type="hidden" id="kqItem_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="kqItem_gridtable_addTile">
						<s:text name="kqItemNew.title"/>
					</label> 
					<label style="display: none"
						id="kqItem_gridtable_editTile">
						<s:text name="kqItemEdit.title"/>
					</label>
					<div id="load_kqItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="kqItem_gridtable"></table>
					<!--<div id="kqItemPager"></div>-->
				</div>
				<div class="panelBar" id="kqItem_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="kqItem_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqItem_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="kqItem_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>