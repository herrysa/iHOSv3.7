
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmUpdataLayout;
	var bmUpdataGridIdString="#bmUpdata_gridtable";
	
	jQuery(document).ready(function() { 
		var bmUpdataGrid = jQuery(bmUpdataGridIdString);
		var bmUpdataTypeFullSize = jQuery("#container").innerHeight()-116;
		//setLeftTreeLayout('bmUpdata_container','bmUpdata_gridtable',bmUpdataTypeFullSize);
		var setting_bmUpdataType = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onBmUpdataTypeClick
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
		function onBmUpdataTypeClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "budgetUpdataGridList?upType=0";
			if(treeNode.id!="-1"){
				urlString=urlString+"?filter_EQS_indexCode="+treeNode.id+"&filter_EQS_parentId.indexCode="+treeNode.id;	
				urlString += "&group_on={op:'or',filter:['indexCode','parentId.indexCode']}";
			}
		    urlString = encodeURI(urlString);
			jQuery(bmUpdataGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		/* $.get("getBudgetTypeTree", {
			"_" : $.now()
		}, function(data) {
			var budgetTypeTreeData = data.budgetTypeTreeNodes;
			var budgetTypeTree = $.fn.zTree.init($("#bmUpdataTypeTreeLeft"),
					setting_bmUpdataType, budgetTypeTreeData);
			var nodes = budgetTypeTree.getNodes();
			budgetTypeTree.expandNode(nodes[0], true, false, true);
			budgetTypeTree.selectNode(nodes[0]);
			
		}); */
		jQuery("#bmUpdataType_expandTree").text("展开");
    	bmUpdataGrid.jqGrid({
    		url : "budgetUpdataGridList?filter_EQS_modelXfId.modelId.modelType=1&upType=0",
    		editurl:"budgetUpdataGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'updataId',index:'updataId',align:'center',label : '<s:text name="bmUpdata.updataId" />',hidden:true,key:true},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetUpdata.periodYear" />',hidden:false,width:70},
			{name:'modelXfId.modelId.modelCode',index:'modelXfId.modelId.modelName',align:'left',label : '<s:text name="budgetUpdata.modelCode" />',hidden:false,width:250},
			{name:'modelXfId.modelId.modelName',index:'modelXfId.modelId.modelName',align:'left',label : '<s:text name="budgetUpdata.model" />',hidden:false,width:250},
			{name:'modelXfId.modelId.modelTypeTxt',index:'modelXfId.modelId.modelTypeTxt',align:'left',label : '<s:text name="budgetUpdata.budgetType" />',hidden:false,width:70},
			{name:'modelXfId.modelId.periodType',index:'modelXfId.modelId.periodType',align:'left',label : '<s:text name="budgetUpdata.periodType" />',hidden:false,width:70},
			{name:'department.name',index:'department.name',align:'left',label : '<s:text name="budgetUpdata.department" />',hidden:false,width:150},
			{name:'operator.name',index:'operator.name',align:'left',label : '<s:text name="budgetUpdata.operator" />',hidden:false,width:100},
			{name:'optDate',index:'optDate',align:'left',label : '<s:text name="budgetUpdata.optDate" />',hidden:false,align:"center",
				formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:100}
			],
        	jsonReader : {
				root : "budgetUpdatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'department.internalCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bmUpdataList.title" />',
        	height:300,
        	grouping:true,
    	   	groupingView : {
    	   		groupField : ['modelXfId.modelId.modelName'],
    	   		//groupSummary : [true],
    			groupColumnShow : [true],
    			groupText : ['<b>{0}</b> (记录数:{1}) '],
    			groupDataSorted :[false]
    	   	},
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
           	gridContainerResize('bmUpdata','div');
           	var dataTest = {"id":"bmUpdata_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(bmUpdataGrid).jqGrid('bindKeys');
    
    jQuery("#bmUpdata_gridtable_up").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var sid = jQuery("#bmUpdata_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0||sid.length>1){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
		$.pdialog.open('openBmReport?updataId='+sid,'bmReport',"预算上报", {mask:true,width : fullWidth,height : fullHeight});
    });
    jQuery("#bmUpdata_gridtable_comfirm").click(function(){
    	var sid = jQuery("#bmUpdata_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	$.get("confirmBmUpdata", {
			"_" : $.now(),updataId:sid,navTabId:'bmUpdata_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmUpdata_gridtable_groupByDept").click(function(){
    	var txt = jQuery(this).find("span").eq(0).text();
    	if(txt.indexOf("部门")!=-1){
    		jQuery('#bmUpdata_gridtable').jqGrid('setGridParam', {
    			groupingView : {
        	   		groupField : ['department.name']}
    		}).trigger("reloadGrid");
        	jQuery(this).find("span").eq(0).text("按预算模板填报");
    	}else{
    		jQuery('#bmUpdata_gridtable').jqGrid('setGridParam', {
    			groupingView : {
        	   		groupField : ['modelXfId.modelId.modelName']}
    		}).trigger("reloadGrid");
        	jQuery(this).find("span").eq(0).text("按预算部门填报");
    	}
    	
    });
  	});
</script>

<div class="page">
	<div id="bmUpdata_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmUpdata_search_form" >
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.checkDate'/>:
						<input type="text" name="filter_EQS_checkDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.checker'/>:
						<input type="text" name="filter_EQS_checker"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						预算模板编码:
						<input type="text" name="filter_LIKES_modelXfId.modelId.modelCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						预算模板名称:
						<input type="text" name="filter_LIKES_modelXfId.modelId.modelName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.department'/>:
						<input type="text" name="filter_LIKES_department.name"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.operator'/>:
						<input type="text" name="filter_EQS_operator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.optDate'/>:
						<input type="text" name="filter_EQS_optDate"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.submitDate'/>:
						<input type="text" name="filter_EQS_submitDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmUpdata.submitter'/>:
						<input type="text" name="filter_EQS_submitter"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bmUpdata_search_form','bmUpdata_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div id="bmUpdata_buttonBar" class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				 <li><a id="bmUpdata_gridtable_up" class="reportbutton" href="javaScript:" ><span>填报
					</span>
				</a>
				</li>
				<li><a id="bmUpdata_gridtable_comfirm" class="addbutton"  href="javaScript:"><span>确认</span>
				</a>
				</li>
				<li><a id="bmUpdata_gridtable_groupByDept" class="openbutton"  href="javaScript:"><span>按预算部门填报</span>
				</a>
				</li>
				<%-- <li><a id="bmUpdata_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
	</div>
		<div id="bmUpdata_container">
		<!-- <div id="bmUpdata_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			<a style="position: relative; float: right;top:5px" id="bmUpdataType_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#bmUpdataType_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var budgetTypeTee = $.fn.zTree.getZTreeObj("bmUpdataTypeTreeLeft");
					if(thisText=="展开"){
						budgetTypeTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						budgetTypeTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="bmUpdataTypeTreeLeft" class="ztree"></div>
		</div> -->
		<div id="bmUpdata_layout-center" class="pane ui-layout-center">
		<div id="bmUpdata_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmUpdata_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmUpdata_gridtable_addTile">
				<s:text name="bmUpdataNew.title"/>
			</label> 
			<label style="display: none"
				id="bmUpdata_gridtable_editTile">
				<s:text name="bmUpdataEdit.title"/>
			</label>
			<div id="load_bmUpdata_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmUpdata_gridtable"></table>
			<!--<div id="bmUpdataPager"></div>-->
		</div>
	<div id="bmUpdata_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmUpdata_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmUpdata_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmUpdata_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
</div>