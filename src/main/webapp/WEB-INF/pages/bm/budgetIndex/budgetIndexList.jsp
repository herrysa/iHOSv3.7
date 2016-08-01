
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetIndexLayout;
	var budgetIndexGridIdString="#budgetIndex_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetIndexGrid = jQuery(budgetIndexGridIdString);
		var budgetIndexFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('budgetIndex_container','budgetIndex_gridtable',budgetIndexFullSize);
		var ztreesetting_budgetIndex = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onbudgetIndexClick
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
		function onbudgetIndexClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "budgetIndexGridList";
			if(treeNode.id!="-1"){
				urlString=urlString+"?filter_EQS_indexCode="+treeNode.id+"&filter_EQS_parentId.indexCode="+treeNode.id;	
				urlString += "&group_on={op:'or',filter:['indexCode','parentId.indexCode']}";
			}
		    urlString = encodeURI(urlString);
			jQuery(budgetIndexGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		$.get("getBudgetIndexTree", {
			"_" : $.now()
		}, function(data) {
			var budgetIndexTreeData = data.budgetIndexTreeNodes;
			var budgetIndexTree = $.fn.zTree.init($("#budgetIndexTreeLeft"),
					ztreesetting_budgetIndex, budgetIndexTreeData);
			var nodes = budgetIndexTree.getNodes();
			budgetIndexTree.expandNode(nodes[0], true, false, true);
			budgetIndexTree.selectNode(nodes[0]);
			
		});
		jQuery("#budgetIndex_expandTree").text("展开");
    	budgetIndexGrid.jqGrid({
    		url : "budgetIndexGridList",
    		editurl:"budgetIndexGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel:[
			{name:'indexCode',index:'indexCode',align:'left',label : '<s:text name="budgetIndex.indexCode" />',hidden:false,width:100,key:true},
			{name:'indexName',index:'indexName',align:'left',label : '<s:text name="budgetIndex.indexName" />',hidden:false,width:150},
			{name:'parentId.indexName',index:'parentId.indexName',align:'left',label : '<s:text name="budgetIndex.parentId" />',hidden:false,width:150},
			{name:'indexType',index:'indexType',align:'left',label : '<s:text name="budgetIndex.indexType" />',hidden:false,width:70},
			{name:'budgetIndex.budgetTypeName',index:'budgetIndex.budgetTypeName',align:'left',label : '<s:text name="budgetIndex.budgetType" />',hidden:false,width:100},
			{name:'exceedBudgetType',index:'exceedBudgetType',align:'left',label : '<s:text name="budgetIndex.exceedBudgetType" />',hidden:false,width:100},
			{name:'warningPercent',index:'warningPercent',align:'left',label : '<s:text name="budgetIndex.warningPercent" />',hidden:false,formatter:'integer',width:100},
			{name:'leaf',index:'leaf',align:'center',label : '<s:text name="budgetIndex.leaf" />',hidden:false,formatter:'checkbox',width:60},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="budgetIndex.disabled" />',hidden:false,formatter:'checkbox',width:60}
			],
			jsonReader : {
				root : "budgetIndices", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'indexCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="budgetIndexList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	gridContainerResize('budgetIndex','layout');
           	var dataTest = {"id":"budgetIndex_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(budgetIndexGrid).jqGrid('bindKeys');
    jQuery("#budgetIndex_gridtable_add_c").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("budgetIndexTreeLeft"); 
        var nodes = zTree.getSelectedNodes();
        if(nodes.length!=1){
      		alertMsg.error("请选择上级预算指标。");
        	return;
     	}
        var rowData = jQuery("#budgetIndex_gridtable").jqGrid('getRowData',nodes[0].id);
        if(rowData.leaf=='Yes'){
        	alertMsg.error("末级预算指标不能添加下级预算指标。");
        	return;
        }
        var url = "editBudgetIndex?navTabId=budgetIndex_gridtable&parentId="+nodes[0].id;
		var winTitle='<s:text name="budgetIndexNew.title"/>';
		$.pdialog.open(url,'addBudgetIndex',winTitle, {mask:true,width : 500,height : 450});
    });
    
    jQuery("#budgetIndex_gridtable_edit_c").click(function(){
    	var sid = jQuery("#budgetIndex_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	if(sid.length>1){
    		alertMsg.error("只能选择一条记录！");
    		return;
    	}
        var url = "editBudgetIndex?navTabId=budgetIndex_gridtable&indexCode="+sid;
		var winTitle='<s:text name="budgetIndexEdit.title"/>';
		$.pdialog.open(url,'editBudgetIndex',winTitle, {mask:true,width : 500,height : 450});
    });
    
    jQuery("#budgetIndex_gridtable_del_c").click(function(){
    	var sid = jQuery("#budgetIndex_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	$.post("budgetIndexGridEdit", {
    		"_" : $.now(),id:sid,oper:'del',navTabId:'budgetIndex_gridtable'
		}, function(data) {
			formCallBack(data);
			var zTree = $.fn.zTree.getZTreeObj("budgetIndexTreeLeft"); 
			for(var i in sid){
				var oldNode = zTree.getNodeByParam("id", sid[i], null);
				zTree.removeNode(oldNode);
			}
		});
    });
    
    jQuery("#budgetIndex_gridtable_reload").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("budgetIndexTreeLeft");
		var treeNodes = zTree.getSelectedNodes();
		if(treeNodes.length==0){
			return ;
		}
		var treeNode = treeNodes[0];
		var nodeId = treeNode.id;
		var urlString = "budgetIndexGridList";
		if(treeNode.id!="-1"){
	    	urlString=urlString+"?filter_EQS_indexCode="+treeNode.id+"&filter_EQS_parentId.indexCode="+treeNode.id;	
	    	urlString += "&group_on={op:'and',filter:[{op:'or',filter:['indexCode','parentId.indexCode']},{op:'and',filter:['*']}]}";
		}
	    urlString = encodeURI(urlString);
		jQuery(budgetIndexGridIdString).jqGrid('setGridParam',{url:urlString});
    	propertyFilterSearch('budgetIndex_search_form','budgetIndex_gridtable')
    });
    
  	});
	
	
</script>

<div class="page" id="budgetIndex_page">
	<div id="budgetIndex_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetIndex_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexCode'/>:
						<input type="text" name="filter_LIKES_indexCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexName'/>:
						<input type="text" name="filter_LIKES_indexName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexType'/>:
						<s:select list="#{'月度':'月度','季度':'季度','半年':'半年','年度':'年度'}" key="budgetIndex.indexType" name="filter_EQS_indexType" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.budgetType'/>:
						<input type="hidden" id="budgetIndex_budgetType_id" name="filter_INS_budgetType.budgetTypeCode"/>
						<input type="text" id="budgetIndex_budgetType" name="_exclude_"/>
						<script>
						jQuery("#budgetIndex_budgetType").treeselect({
							optType : "multi",
							dataType : 'sql',
							sql : "SELECT type_code id,type_name name,parent_id parent FROM bm_budgetType where disabled=0 ORDER BY type_code asc",
							exceptnullparent : true,
							lazy : false,
							minWidth : '180px',
							selectParent : false,
							callback : {
							}
						});
						</script>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.exceedBudgetType'/>:
						<select name="filter_EQS_exceedBudgetType">
							<option value="">--</option>
							<option value="禁止">禁止</option>
							<option value="警告">警告</option>
						</select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.warningPercent'/>:
						<input type="text" name="filter_EQS_warningPercent"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.leaf'/>:
						<select name="filter_EQB_leaf">
							<option value="">--</option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.disabled'/>:
						<select name="filter_EQB_disabled">
							<option value="">--</option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" id="budgetIndex_gridtable_reload"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="budgetIndex_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetIndex_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="budgetIndex_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="budgetIndex_gridtable_edit_c" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="budgetIndex_container">
		<div id="budgetIndex_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			<a style="position: relative; float: right;top:5px" id="budgetIndex_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#budgetIndex_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var budgetIndexTee = $.fn.zTree.getZTreeObj("budgetIndexTreeLeft");
					if(thisText=="展开"){
						budgetIndexTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						budgetIndexTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="budgetIndexTreeLeft" class="ztree"></div>
		</div>
		<div id="budgetIndex_layout-center" class="pane ui-layout-center">
		<div id="budgetIndex_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetIndex_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetIndex_gridtable_addTile">
				<s:text name="budgetIndexNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetIndex_gridtable_editTile">
				<s:text name="budgetIndexEdit.title"/>
			</label>
 			<table id="budgetIndex_gridtable"></table>
			<!--<div id="budgetIndexPager"></div>-->
		</div>
	<div id="budgetIndex_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetIndex_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetIndex_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetIndex_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
	</div>
</div>