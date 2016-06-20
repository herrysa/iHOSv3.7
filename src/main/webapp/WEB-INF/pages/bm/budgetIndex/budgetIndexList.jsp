
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
				urlString += "&group_on={op:'or',filter:['indexCode','prentId.indexCode']}";
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
		jQuery("#gzPerson_expandTree").text("展开");
    	budgetIndexGrid.jqGrid({
    		url : "budgetIndexGridList",
    		editurl:"budgetIndexGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel:[
			{name:'indexCode',index:'indexCode',align:'left',label : '<s:text name="budgetIndex.indexCode" />',hidden:false,key:true},
			{name:'indexName',index:'indexName',align:'left',label : '<s:text name="budgetIndex.indexName" />',hidden:false},
			{name:'parentId',index:'parentId',align:'left',label : '<s:text name="budgetIndex.parentId" />',hidden:false},
			{name:'indexType',index:'indexType',align:'left',label : '<s:text name="budgetIndex.indexType" />',hidden:false},
			{name:'budgetIndex.budgetTypeName',index:'budgetIndex.budgetTypeName',align:'left',label : '<s:text name="budgetIndex.budgetType" />',hidden:false},
			{name:'exceedBudgetType',index:'exceedBudgetType',align:'left',label : '<s:text name="budgetIndex.exceedBudgetType" />',hidden:false},
			{name:'warningPercent',index:'warningPercent',align:'left',label : '<s:text name="budgetIndex.warningPercent" />',hidden:false,formatter:'integer'},
			{name:'leaf',index:'leaf',align:'center',label : '<s:text name="budgetIndex.leaf" />',hidden:false,formatter:'checkbox'},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="budgetIndex.disabled" />',hidden:false,formatter:'checkbox'}
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
			shrinkToFit:true,
			autowidth:true,
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
  	});
</script>

<div class="page" id="budgetIndex_page">
	<div id="budgetIndex_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetIndex_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexCode'/>:
						<input type="text" name="filter_EQS_indexCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexName'/>:
						<input type="text" name="filter_EQS_indexName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexType'/>:
						<input type="text" name="filter_EQS_indexType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.budgetType'/>:
						<input type="text" name="filter_EQS_budgetType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.exceedBudgetType'/>:
						<input type="text" name="filter_EQS_exceedbudgetType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.warningPercent'/>:
						<input type="text" name="filter_EQS_warningPercent"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.disabled'/>:
						<input type="text" name="filter_EQS_disabled"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetIndex_search_form,budgetIndex_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
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
				<li><a id="budgetIndex_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="budgetIndex_gridtable_edit" class="changebutton"  href="javaScript:"
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
					var budgetIndexTee = $.fn.zTree.getZTreeObj("budgetIndex_expandTree");
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