
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetTypeLayout;
	var budgetTypeGridIdString="#budgetType_gridtable";
	
	jQuery(document).ready(function() {
		var budgetTypeFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('budgetType_container','budgetType_gridtable',budgetTypeFullSize);
		var ztreesetting_budgetType = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onBudgetTypeClick
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
		function onBudgetTypeClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "budgetTypeGridList";
			if(treeNode.id!="-1"){
				if(treeNode.children){
			    	var ids=treeNode.id;
			    	ids = findChildrenNode(ids,treeNode);
			    	urlString=urlString+"?filter_INS_budgetTypeCode="+ids;	
				}else{
					urlString=urlString+"?filter_EQS_budgetTypeCode="+treeNode.id;	
				}
			}
		    urlString = encodeURI(urlString);
			jQuery(budgetTypeGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		function findChildrenNode(ids,treeNode){
			$.each(treeNode.children,function(n,value) {  
				var n = treeNode.children[n];
	    		 ids+=","+n.id;
	    		 if(n.children&&n.children.length>0){
	    			 ids = findChildrenNode(ids,n);
	    		 }
	    	});
			return ids;
		}
		$.get("getBudgetTypeTree", {
			"_" : $.now()
		}, function(data) {
			var budgetTypeTreeData = data.budgetTypeTreeNodes;
			var budgetTypeTree = $.fn.zTree.init($("#budgetTypeTreeLeft"),
					ztreesetting_budgetType, budgetTypeTreeData);
			var nodes = budgetTypeTree.getNodes();
			budgetTypeTree.expandNode(nodes[0], true, false, true);
			budgetTypeTree.selectNode(nodes[0]);
			
		});
		jQuery("#gzPerson_expandTree").text("展开");
		var budgetTypeGrid = jQuery(budgetTypeGridIdString);
    	budgetTypeGrid.jqGrid({
    		url : "budgetTypeGridList",
    		editurl:"budgetTypeGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel:[
			{name:'budgetTypeCode',index:'budgetTypeCode',align:'left',label : '<s:text name="budgetType.budgetTypeCode" />',width:100,hidden:false,key:true},
			{name:'budgetTypeName',index:'budgetTypeName',align:'left',label : '<s:text name="budgetType.budgetTypeName" />',width:150,hidden:false},
			{name:'exceedBudgetType',index:'exceedBudgetType',align:'left',label : '<s:text name="budgetType.exceedBudgetType" />',width:100,hidden:false},
			{name:'warningPercent',index:'warningPercent',align:'left',label : '<s:text name="budgetType.warningPercent" />',width:60,hidden:false,formatter:'integer'},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="budgetType.disabled" />',width:50,hidden:false,formatter:'checkbox'}
			],
			jsonReader : {
				root : "budgetTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'budgetTypeCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="budgetTypeList.title" />',
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
           		gridContainerResize('budgetType','layout');
           		var dataTest = {"id":"budgetType_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(budgetTypeGrid).jqGrid('bindKeys');
    
    jQuery("#budgetType_gridtable_add_c").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("budgetTypeTreeLeft"); 
        var nodes = zTree.getSelectedNodes();
        if(nodes.length!=1){
      		alertMsg.error("请选择上级预算类型。");
        	return;
     	}
        var rowData = jQuery("#budgetType_gridtable").jqGrid('getRowData',nodes[0].id);
        if(rowData.leaf=='Yes'){
        	alertMsg.error("末级预算类型不能添加下级预算类型。");
        	return;
        }
        var url = "editBudgetType?navTabId=budgetType_gridtable&parentId="+nodes[0].id;
		var winTitle='<s:text name="budgetTypeNew.title"/>';
		$.pdialog.open(url,'addBudgetType',winTitle, {mask:true,width : 500,height : 400});
    });
    
  	});
	function budgetTypeReload(){
		var zTree = $.fn.zTree.getZTreeObj("budgetTypeTreeLeft");
		var treeNodes = zTree.getSelectedNodes();
		if(treeNodes.length==0){
			return ;
		}
		var treeNode = treeNodes[0];
		var nodeId = treeNode.id;
		var urlString = "budgetTypeGridList";
		if(treeNode.children){
	    	var ids=treeNode.id;
	    	$.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	});
	    	urlString=urlString+"?filter_INS_budgetTypeCode="+ids;	
		}else{
			urlString=urlString+"?filter_EQS_budgetTypeCode="+treeNode.id;	
		}
	    urlString = encodeURI(urlString);
		jQuery(budgetTypeGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="budgetType_page">
	<div id="budgetType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetType_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetType.budgetTypeCode'/>:
						<input type="text" name="filter_EQS_budgetTypeCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetType.budgetTypeName'/>:
						<input type="text" name="filter_EQS_budgetTypeName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetType.exceedBudgetType'/>:
						<!-- <input type="text" name="filter_EQS_exceedBudgetType"/> -->
						<select name="filter_EQS_exceedBudgetType">
							<option value="">--</option>
							<option value="禁止">禁止</option>
							<option value="警告">警告</option>
						</select>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetType.parentId'/>:
						<input type="text" name="filter_EQS_parentId"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetType.warningPercent'/>:
						<input type="text" name="filter_EQS_warningPercent"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetType_search_form,budgetType_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="budgetType_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetType_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="budgetType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="budgetType_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="budgetType_container">
		<div id="budgetType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			<a style="position: relative; float: right;top:5px" id="budgetType_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#budgetType_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var budgetTypeTee = $.fn.zTree.getZTreeObj("budgetType_expandTree");
					if(thisText=="展开"){
						budgetTypeTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						budgetTypeTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="budgetTypeTreeLeft" class="ztree"></div>
		</div>
		<div id="budgetType_layout-center" class="pane ui-layout-center">
			<div id="budgetType_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
				<input type="hidden" id="budgetType_gridtable_navTabId" value="${sessionScope.navTabId}">
				<label style="display: none" id="budgetType_gridtable_addTile">
					<s:text name="budgetTypeNew.title"/>
				</label> 
				<label style="display: none"
					id="budgetType_gridtable_editTile">
					<s:text name="budgetTypeEdit.title"/>
				</label>
	 			<table id="budgetType_gridtable"></table>
				<!--<div id="budgetTypePager"></div>-->
			</div>
	<div id="budgetType_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
	</div>
</div>