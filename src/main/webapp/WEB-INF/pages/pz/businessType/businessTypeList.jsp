
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessTypeLayout;
	var businessTypeGridIdString="#businessType_gridtable";
	
	jQuery(document).ready(function() { 
		var businessTypeFullSize = jQuery("#container").innerHeight() - 116;
		setLeftTreeLayout('businessType_container','businessType_gridtable',businessTypeFullSize);
		var businessTypeChangeData = function(selectedBusinessId) {
			reloadTab(selectedBusinessId);
		}
		
		businessTypeLayout = makeLayout(
					{
						'baseName' : 'businessTypeDetail',
						'tableIds' : 'businessType_gridtable;businessType_southTabs',
						'key' : 'businessId',
						'activeGridTable':'businessType_gridtable',
						'fullSize':97,
						'proportion' : 2
					}, businessTypeChangeData);
			jQuery("#businessTypeTabsContent").css('padding', 0);
		
		makeBusinessTypeLeftTree();
		
		var businessTypeGrid = jQuery(businessTypeGridIdString);
    	businessTypeGrid.jqGrid({
    		url : "businessTypeGridList",
    		editurl:"businessTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'businessId',index:'businessId',width:120,align:'left',label : '<s:text name="businessType.businessId" />',hidden:false,key:true},
{name:'businessName',index:'businessName',width:200,align:'left',label : '<s:text name="businessType.businessName" />',hidden:false},
{name:'dataSourceType',index:'dataSourceType',align:'left',width:150,label : '<s:text name="businessType.dataSourceType" />',hidden:false,formatter:'select',editoptions:{value:"0:本地;1:中间库"}},
// {name:'contrastTable',index:'contrastTable',align:'left',width:200,label:'<s:text name="businessType.contrastTable"/>',hidden:false},
{name:'sn',index:'sn',align:'right',width:50,label : '<s:text name="businessType.sn" />',hidden:false,formatter:'integer'},
{name:'searchName',index:'searchName',width:275,align:'left',label : '<s:text name="businessType.searchName" />',hidden:false},
{name:'collectTempTable',index:'collectTempTable',width:120,align:'left',label : '<s:text name="businessType.collectTempTable" />',hidden:false},
{name:'leaf',index:'leaf',align:'center',width:50,label : '<s:text name="businessType.leaf" />',hidden:false,formatter:'checkbox'},
{name:'disabled',index:'disabled',width:50,align:'center',label : '<s:text name="businessType.disabled" />',hidden:false,formatter:'checkbox'},
{name:'icon',index:'icon',align:'center',width:150,label : '<s:text name="businessType.icon" />',hidden:true},
{name:'remark',index:'remark',width:300,align:'left',label : '<s:text name="businessType.remark" />',hidden:false}
			],jsonReader : {
				root : "businessTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sn',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="businessTypeList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(rowid,iRow,iCol,e){
				var rowData = jQuery(this).getRowData(rowid);
				if(rowData["leaf"] == "Yes") {
					businessTypeLayout.optDblclick();
				} else {
					return;
				}
			},
			onSelectRow: function(rowid) {
				var rowData = jQuery(this).getRowData(rowid);
				if(rowData["leaf"] == "Yes") {
					setTimeout(function(){
						businessTypeLayout.optClick();
					},100);
				}
			},
		 	gridComplete:function(){
		 		gridContainerResize("businessType","layout");
		 		var rowNum = jQuery(this).getDataIDs().length;
				if(rowNum>0){
					/*var rowIds = jQuery(this).getDataIDs();
					 var ret = jQuery(this).jqGrid('getRowData');
					var id='';
					for(var i=0;i<rowNum;i++){
						id=rowIds[i];
						var snapId=ret[i]["departmentId"]+'_'+ret[i]["snapCode"];
						setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["name"]+'</a>');
					} */
					var ztree = $.fn.zTree.getZTreeObj("businessTypeLeftTree");
					if(ztree){
						var selectNode = ztree.getSelectedNodes();
						if(selectNode && selectNode.length==1){
							var selectid = selectNode[0].id;
							jQuery(this).jqGrid('setSelection',selectid);
						}
					}

				}else{
					var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
				}
				var dataTest = {"id":"businessType_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				makepager("businessType_gridtable");
			} 

		});
		jQuery(businessTypeGrid).jqGrid('bindKeys');
		
		/***************************************buttonBar start***************************************/
		var businessType_menuButtonArrJson = "${menuButtonArrJson}";
		var businessType_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(businessType_menuButtonArrJson,false)));
		var businessType_toolButtonBar = new ToolButtonBar({el:$('#businessType_buttonBar'),collection:businessType_toolButtonCollection,attributes:{
			tableId : 'businessType_gridtable',
			baseName : 'businessType',
			width : 425,
			height : 375,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="businessTypeNew.title"/>',
			editTitle : null
		}}).render();
		
		var businessType_function = new scriptFunction();
		businessType_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#businessType_gridtable").jqGrid('getGridParam','selarrrow');
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
		businessType_toolButtonBar.addCallBody('0920020101',function() {
			var urlString = "editBusinessType?popup=true&navTabId=businessType_gridtable";
			var businessTypeTreeObj = $.fn.zTree.getZTreeObj("businessTypeLeftTree");
			var selectedNodes = businessTypeTreeObj.getSelectedNodes();
			if(selectedNodes.length == 1 ) { //&& selectedNodes[0].subSysTem == 'TYPE'
				var selectedNode = selectedNodes[0];
				if(selectedNodes[0].id != "-1") {
					urlString += "&parentId=" + selectedNode.id;
				}
				urlString = encodeURI(urlString);
				var winTitle = '<s:text name="businessTypeNew.title"/>';
				$.pdialog.open(urlString,"addBusinessType",winTitle,{mask:true,width:425,height:430,maxable:false,resizable:false});
			} else {
				alertMsg.error("请选择一个业务节点！");
				return;
			}
		},{});
		businessType_toolButtonBar.addBeforeCall('0920020101',function(e,$this,param){
			return businessType_function.optBeforeCall(e,$this,param);
		},{});
		/*删除*/
		businessType_toolButtonBar.addCallBody('0920020102',function() {
			var sid = jQuery(businessTypeGridIdString).jqGrid("getGridParam","selarrrow");
			var urlString = "businessTypeGridEdit?id="+sid+"&navTabId=businessType_gridtable&oper=del";
			for(var i = 0 ;i<sid.length;i++) {
				var rowId = sid[i]; 
				var row = jQuery(businessTypeGridIdString).jqGrid("getRowData",rowId);
				if(row['leaf'] == "No"){
					alertMsg.error("只能删除末级业务类别！");
					return;
				}
			}
			jQuery.ajax({
				url : 'checkBusinessTypeDel',
				dataType : 'json',
				data : {id : sid},
				type : 'post',
				error: function(data){
				},
				success: function(data){
					if(data.message){
						alertMsg.error(data.message);
						return;
					} else {
						alertMsg.confirm("确认删除？",{
							okCall:function() {
								jQuery.post(urlString,function(data) {
									formCallBack(data);
									if(data.statusCode != 200) {
										return;
									}
									for(var i = 0;i < sid.length; i++) {
										dealTreeNodeC("businessTypeLeftTree",{id:sid[i]},"del");
									}
								});
							}
						});
					}
				}
			});
		},{});
		businessType_toolButtonBar.addBeforeCall('0920020102',function(e,$this,param){
			return businessType_function.optBeforeCall(e,$this,param);
		},{selectRecord:"selectRecord"});
		/*修改*/
		businessType_toolButtonBar.addCallBody('0920020103',function() {
			var urlString = "editBusinessType?popup=true&navTabId=businessType_gridtable";
			var sid = jQuery(businessTypeGridIdString).jqGrid("getGridParam","selarrrow");
			if(sid && sid.length == 1) {
				urlString += "&businessId=" + sid[0];
				urlString = encodeURI(urlString);
				var winTitle = '<s:text name="businessTypeEdit.title"/>';
				$.pdialog.open(urlString,"editbusinessType",winTitle,{mask:true,width:425,height:430,maxable:false,resizable:false});
			}
		},{});
		businessType_toolButtonBar.addBeforeCall('0920020103',function(e,$this,param){
			return businessType_function.optBeforeCall(e,$this,param);
		},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
		//上移
		businessType_toolButtonBar.addCallBody('0920020104',function() {
			colMoveCommon(businessTypeGridIdString,"up");
		});
		/* businessType_toolButtonBar.addBeforeCall('0920020104',function(e,$this,param){
			return businessType_function.optBeforeCall(e,$this,param);
		},{selectRecord:"selectRecord",singleSelect:"singleSelect"}); */
		//下移
		businessType_toolButtonBar.addCallBody('0920020105',function() {
			colMoveCommon(businessTypeGridIdString,"down");
		});
		/* businessType_toolButtonBar.addBeforeCall('0920020105',function(e,$this,param){
			return businessType_function.optBeforeCall(e,$this,param);
		},{selectRecord:"selectRecord",singleSelect:"singleSelect"}); */
		//保存
		businessType_toolButtonBar.addCallBody('0920020106',function() {
			var rowDatas = jQuery(businessTypeGridIdString).jqGrid("getRowData");
			var dataStr = "";
			for(var i = 0;i < rowDatas.length; i++) {
				var row = rowDatas[i];
				var businessId = row["businessId"];
		    	var rowSn = row["sn"];
		    	dataStr += businessId + ":" + rowSn + ",";
			}
			dataStr = dataStr.substr(0,dataStr.length-1);
			var page = jQuery(businessTypeGridIdString).jqGrid("getGridParam","page");
			jQuery.ajax({
				url:"saveBusinessTypeSn",
				data:{dataStr:dataStr},
				dataType:"json",
				type:"post",
				success:function(data) {
					if(data.statusCode == 200) {
						alertMsg.correct("保存成功！");
						jQuery(businessTypeGridIdString).jqGrid("setGridParam",{page:page}).trigger("reloadGrid");
					}
				}
			});
		});
		/***************************************buttonBar end***************************************/
	});
	
	//上下移动
	function colMoveCommon(tableId,dir) {
		var sid = jQuery(tableId).jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length != 1) {
 			return;
		}
		var rowId = sid[0];
		var rowData = jQuery(tableId).getRowData(rowId);
		var sn = rowData["sn"];
    	var rowIds = jQuery(tableId).jqGrid("getDataIDs");
    	var rowSn = jQuery.inArray(rowId, rowIds);
    	var rowIdsLength = rowIds.length;
    	if(dir == "down") {
		    var nextRowSn = rowSn+1;
	    	if(nextRowSn == rowIdsLength){
	    		return ;
	    	}
	    	var nextRowId = rowIds[nextRowSn];
	    	var nextRowData = jQuery(tableId).getRowData(nextRowId);
	    	jQuery(tableId).jqGrid("setRowData",rowId,{sn:nextRowData["sn"]},{});
	    	jQuery(tableId).jqGrid("setRowData",nextRowId,{sn:sn},{});
			rowData = jQuery(tableId).getRowData(rowId);
	    	nextRowData = jQuery(tableId).getRowData(nextRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var nextRowDataClone = cloneObj(nextRowData);
	    	jQuery(tableId).delRowData(rowId);
	    	jQuery(tableId).delRowData(nextRowId);
	    	if(rowSn == 0){
	    		jQuery(tableId).addRowData(nextRowId, nextRowDataClone, "first");
	        	jQuery(tableId).addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn-1];
	    		jQuery(tableId).addRowData(nextRowId, nextRowDataClone, "after",rowIdTemp);
	    		jQuery(tableId).addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}
			jQuery(tableId).jqGrid("setSelection",rowId);
    	} else if(dir == "up") {
    		if(rowSn == 0) {
    			return;
    		}
    		var preRowSn = rowSn - 1;
    		var preRowId = rowIds[preRowSn];
	    	var preRowData = jQuery(tableId).getRowData(preRowId);
	    	jQuery(tableId).jqGrid("setRowData",rowId,{sn:preRowData["sn"]},{});
	    	jQuery(tableId).jqGrid("setRowData",preRowId,{sn:sn},{});
	    	rowData = jQuery(tableId).getRowData(rowId);
	    	preRowData = jQuery(tableId).getRowData(preRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var preRowDataClone = cloneObj(preRowData);
	    	jQuery(tableId).delRowData(rowId);
	    	jQuery(tableId).delRowData(preRowId);
	    	if(rowSn == rowIdsLength - 1){
	    		jQuery(tableId).addRowData(preRowId, preRowDataClone, "last");
	        	jQuery(tableId).addRowData(rowId, rowDataClone, "before",preRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn+1];
	    		jQuery(tableId).addRowData(preRowId, preRowDataClone, "before",rowIdTemp);
	    		jQuery(tableId).addRowData(rowId, rowDataClone, "before",preRowId);
	    	}
			jQuery(tableId).jqGrid("setSelection",rowId);
    	}
    	
	}
	
	function dealTreeNodeC(treeId,node,opt) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodeId = node.id;
		var oldNode = treeObj.getNodeByParam("id", nodeId, null);
		if(node) {
			switch(opt) {
			case 'add' :
				var parentNode = treeObj.getNodeByParam("id", node.pId, null);
				node = treeObj.addNodes(parentNode,node);
				break;
			case 'change' :
				if(oldNode) {
					oldNode.name = node.name;
					oldNode.isParent = node.isParent;
					treeObj.updateNode(oldNode);
				}
				break;
			case 'del' :
				treeObj.removeNode(oldNode);
				break;
			}
		}
	}
	
	function reloadTab(selectedBusinessId) {
		var url = null;
		var selected;
		var i = 0;
		$("#businessType_southTabs").find("li").each(
				function() {
					if (i == 0) {
						url = "businessTypeJList?popup=true&businessId="
								+ selectedBusinessId;

					} else if (i == 2) {
						url = "businessTypeCollectTable?popup=true&businessId="
								+ selectedBusinessId;

					} else if (i == 1) {
						url = "businessTypeDList?popup=true&businessId="
							+ selectedBusinessId;

					}
					$(this).find("a").eq(0).attr("href", url);
					if (jQuery(this).attr("class") == "selected") {
						selected = i;
						$(this).find("a").eq(0).trigger('click');
					}
					i++;
				});

		$("#background,#progressBar").hide();
	}

	
	function makeBusinessTypeLeftTree() {
		var url = "makeBusinessTypeTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var businessTypeTreeData = data.businessTypeTreeNodes;
			var businessTypeTree = $.fn.zTree.init($("#businessTypeLeftTree"),ztreesetting_businessTypeTree, businessTypeTreeData);
			var nodes = businessTypeTree.getNodes();
			businessTypeTree.expandNode(nodes[0], true, false, true);
			businessTypeTree.selectNode(nodes[0]);
		});
		jQuery("#businessType_expandTree").text("展开");
	}
	var ztreesetting_businessTypeTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "businessTypeGridList?1=1";
					var nodeId = treeNode.id;
					if(nodeId!="-1"){
						urlString += "&businessId="+nodeId;
					}
					urlString=encodeURI(urlString);
					jQuery("#businessType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	function toggleExpandBusinessTypeTree(obj,treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId); 
		var obj = jQuery(obj);
		var text = obj.html();
			if(text=='展开'){
			obj.html("折叠");
			zTree.expandAll(true);
		}else{
			obj.html("展开");
			var allNodes = zTree.transformToArray(zTree.getNodes());
			for(var nodeIndex in allNodes){
				var node = allNodes[nodeIndex];
				if(node.subSysTem=='TYPE'){
					zTree.expandNode(node,false,true,true);
				}
			}
		}
	}
</script>

<div class="page">
	<div id="businessType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="businessType_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='businessType.businessId'/>:
						<input type="text" name="filter_LIKES_businessId"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='businessType.businessName'/>:
						<input type="text" name="filter_LIKES_businessName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='businessType.disabled'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_disabled" theme="simple" />
					</label>
					<label class="queryarea-label" >
						<s:text name='businessType.leaf'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_leaf" theme="simple" />
					</label>
					<label class="queryarea-label" >
						<s:text name='businessType.searchName'/>:
						<input type="text" name="filter_LIKES_searchName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='businessType.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('businessType_search_form','businessType_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="businessType_buttonBar">
			<%-- <ul class="toolBar">
				<li><a id="businessType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="businessType_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul> --%>
		</div>
		<div id="businessType_container">
			<div id="businessType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<label id="businessType_expandTree" onclick="toggleExpandBusinessTypeTree(this,'businessTypeLeftTree')">展开</label>
				</div>
				<div id="businessTypeLeftTree" class="ztree"></div>
			</div>
			<div id="businessType_layout-center" class="pane ui-layout-center">
				<div id="businessTypeDetail_container">
					<div id="businessTypeDetail_layout-center" class="pane ui-layout-center">
						<div id="businessType_gridtable_div" class="grid-wrapdiv" buttonBar="width:425;height:350;optId:businessId;">
						<input type="hidden" id="businessType_gridtable_navTabId" value="${sessionScope.navTabId}">
						<label style="display: none" id="businessType_gridtable_addTile">
							<s:text name="businessTypeNew.title"/>
						</label> 
						<label style="display: none"
							id="businessType_gridtable_editTile">
							<s:text name="businessTypeEdit.title"/>
						</label>
						<div id="load_businessType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
			 			<table id="businessType_gridtable"></table>
						</div>
						<div class="panelBar" id="businessType_pageBar">
							<div class="pages">
								<span><s:text name="pager.perPage" /></span> <select id="businessType_gridtable_numPerPage">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessType_gridtable_totals"></label><s:text name="pager.items" /></span>
							</div>
							<div id="businessType_gridtable_pagination" class="pagination"
								targetType="navTab" totalCount="200" numPerPage="20"
								pageNumShown="10" currentPage="1">
							</div>
						</div>
					</div>
					<div id="businessTypeDetail_layout-south" class="pane ui-layout-south" style="padding: 2px;">
						<div class="panelBar">
							<ul class="toolBar">
								<li style="float: right;">
									<a id="businessTypeDetail_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="businessTypeDetail_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="businessTypeDetail_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div class="tabs" currentIndex="0" eventType="click" id="businessType_southTabs" tabcontainer="businessTypeDetail_layout-south" extraHeight=28 extraWidth=15>
								<div class="tabsHeader">
									<div class="tabsHeaderContent">
										<ul>
											<li><a href="businessTypeJList?popup=true" class="j-ajax">
													<span><s:text name='businessTypeJList.title' /> </span> 
												</a>
											</li>
											<li><a href="businessTypeDList?popup=true" class="j-ajax">
													<span><s:text name='businessTypeDList.title' /> </span> 
												</a>
											</li>
											<%-- <li><a href="businessTypeParamList?popup=true" class="j-ajax">
													<span><s:text name='businessTypeParamList.title' /> </span> 
												</a>
											</li> --%>
											<li><a href="businessTypeCollectTable?popup=true" class="j-ajax">
													<span>汇总表结构</span> 
												</a>
											</li>
										</ul>
									</div>
								</div>
								<div id="businessTypeTabsContent" class="tabsContent" >
									<div id="tab1"></div>
									<div id="tab2"></div>
									<div id="tab3"></div>
								</div>
								<div class="tabsFooter">
									<div class="tabsFooterContent"></div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>