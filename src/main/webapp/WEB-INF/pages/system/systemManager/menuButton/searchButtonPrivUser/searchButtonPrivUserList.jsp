
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var searchButtonPrivUserLayout;
	var searchButtonPrivUserGridIdString="#${random}_buttonPrivUser_gridtable";
	
	jQuery(document).ready(function() { 
		var searchButtonPrivUserGrid = jQuery(searchButtonPrivUserGridIdString);
    	searchButtonPrivUserGrid.jqGrid({
    		url : "buttonPrivUserGridList?userId=${userId}&buttonType=${buttonType}",
    		editurl:"buttonPrivUserGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'privId',index:'privId',align:'center',label : '<s:text name="searchButtonPrivUser.privId" />',hidden:true,key:true},
			{name:'searchOrMenuId',index:'searchOrMenuId',align:'center',label : '<s:text name="searchButtonPrivUser.searchId" />',hidden:true},
			{name:'label',index:'label',align:'left',label : '<s:text name="buttonPrivUser.label" />',hidden:false},
			{name:'searchName',index:'searchName',align:'left',label : '<s:text name="buttonPrivUser.searchName" />',hidden:false},
			{name:'userId',index:'userId',align:'center',label : '<s:text name="searchButtonPrivUser.userId" />',hidden:true}        	
			],
        	jsonReader : {
				root : "buttonPrivUsers", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'searchOrMenuId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="searchButtonPrivUserList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: false,
			multiboxonly:false,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
        		var reloadUrl = "${ctx}/buttonPrivUserDetailGridList?userId=${userId}&buttonType=${buttonType}";
        		var rowData = jQuery("#${random}_buttonPrivUser_gridtable").jqGrid('getRowData',rowid);
        		var searchOrMenuId = rowData['searchOrMenuId'];
        		reloadUrl += "&searchOrMenuId="+searchOrMenuId;
        		jQuery("#${random}_buttonPrivUserDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid");
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"${random}_buttonPrivUser_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("${random}_buttonPrivUser_gridtable");
       		} 

    	});
    	var searchButtonPrivUserDetailLayout;
    	var searchButtonPrivUserDetailGridIdString="#${random}_buttonPrivUserDetail_gridtable";
    		var searchButtonPrivUserDetailGrid = jQuery(searchButtonPrivUserDetailGridIdString);
        	searchButtonPrivUserDetailGrid.jqGrid({
        		url : "buttonPrivUserDetailGridList?filter_EQS_buttonPrivUser.privId=ihos",
        		editurl:"buttonPrivUserDetailGridEdit",
    			datatype : "json",
    			mtype : "GET",
            	colModel:[
    {name:'bpuDetailId',index:'bpuDetailId',align:'center',label : '<s:text name="searchButtonPrivUserDetail.sbpDetailId" />',hidden:true,key:true},
    {name:'buttonLabel',index:'buttonLabel',align:'left',label : '<s:text name="buttonPrivUserDetail.buttonLabel" />',hidden:false}, 
    {name:'buttonURL',index:'buttonURL',align:'left',label : '<s:text name="buttonPrivUserDetail.buttonURL" />',hidden:false,width:200},
   /*  {name:'right',index:'right',align:'center',label : '<s:text name="searchButtonPrivUserDetail.right" />',hidden:false,formatter:'checkbox',width:50},
     */{name:'order',index:'order',align:'center',label : '<s:text name="buttonPrivUserDetail.order" />',hidden:false,formatter:'integer',width:50},
    {name:'buttonId',index:'buttonId',align:'center',label : '<s:text name="searchButtonPrivUserDetail.searchURLId" />',hidden:true}       	],
            	jsonReader : {
    				root : "buttonPrivUserDetails", // (2)
    				page : "page",
    				total : "total",
    				records : "records", // (3)
    				repeatitems : false
    				// (4)
    			},
            	sortname: 'order',
            	viewrecords: true,
            	sortorder: 'asc',
            	//caption:'<s:text name="searchButtonPrivUserDetailList.title" />',
            	height:300,
            	gridview:true,
            	rownumbers:true,
            	loadui: "disable",
            	multiselect: false,
    			multiboxonly:false,
    			shrinkToFit:true,
    			autowidth:true,
            	onSelectRow: function(rowid) {
           		},
    		 	gridComplete:function(){
               	//if(jQuery(this).getDataIDs().length>0){
               	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
               	// }
               	var dataTest = {"id":"${random}_buttonPrivUserDetail_gridtable"};
          	   	jQuery.publish("onLoadSelect",dataTest,null);
          	   	makepager("${random}_buttonPrivUserDetail_gridtable");
           		} 

        	});
    jQuery(searchButtonPrivUserGrid).jqGrid('bindKeys');
  	});
	
	function reloadSearchButtonPrivUserGrid(){
		propertyFilterSearch('${random}_buttonPrivUser_search_form','${random}_buttonPrivUser_gridtable');
		var reloadUrl = "${ctx}/buttonPrivUserDetailGridList?filter_EQS_buttonPrivUser.privId=ihos";
		jQuery("#${random}_buttonPrivUserDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid"); 
	}
	
	function disableSearchButtonPrivUser(){
		var ids = jQuery("#user_gridtable").jqGrid('getGridParam','selarrrow');
		if(ids == null || ids.length == 0){
			alertMsg.error("请选择用户。");
			return;
		}else if(ids.length > 1){
			alertMsg.error("请只选择一个用户。");
			return;
		}
		var uid = "<s:property value='#parameters.userId'/>";
		var url = "selectButtonPrivs?buttonType=${buttonType}&random=${random}&userId="+uid;
		$.pdialog.open(url, "${random}_selectButtonPrivs", "选择禁用按钮", {mask:true,height:600});　
	}
</script>

<div class="page">
	<div id="${random}_buttonPrivUser_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}_buttonPrivUser_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='buttonPrivUser.label'/>:
						<input type="text" name="filter_LIKES_label"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='buttonPrivUser.searchName'/>:
						<input type="text" name="filter_LIKES_searchName"/>
					</label>
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadSearchButtonPrivUserGrid();"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadSearchButtonPrivUserGrid();"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="changebutton"  href="javaScript:disableSearchButtonPrivUser()"
					><span>选择禁用按钮
					</span>
				</a>
			</ul>
		</div>
		<div style="width:50%;float:left">
		<div id="${random}_buttonPrivUser_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_buttonPrivUser_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_buttonPrivUser_gridtable_addTile">
				<s:text name="searchButtonPrivUserNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_buttonPrivUser_gridtable_editTile">
				<s:text name="searchButtonPrivUserEdit.title"/>
			</label>
			<div id="${random}_load_buttonPrivUser_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_buttonPrivUser_gridtable"></table>
			<!--<div id="searchButtonPrivUserPager"></div>-->
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_buttonPrivUser_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_buttonPrivUser_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_buttonPrivUser_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	<div style="width:49.7%;float:right">
	<div class="pageContent">
		<div id="${random}_buttonPrivUserDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_buttonPrivUserDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_buttonPrivUserDetail_gridtable_addTile">
				<s:text name="searchButtonPrivUserDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_buttonPrivUserDetail_gridtable_editTile">
				<s:text name="searchButtonPrivUserDetailEdit.title"/>
			</label>
			<div id="${random}_load_buttonPrivUserDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_buttonPrivUserDetail_gridtable"></table>
			<!--<div id="searchButtonPrivUserDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_buttonPrivUserDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_buttonPrivUserDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_buttonPrivUserDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
	</div>
</div>