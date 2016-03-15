
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var searchButtonPrivLayout;
	var searchButtonPrivGridIdString="#${random}_buttonPriv_gridtable";
	jQuery(document).ready(function() { 
		var searchButtonPrivGrid = jQuery(searchButtonPrivGridIdString);
    	searchButtonPrivGrid.jqGrid({
    		url : "buttonPrivGridList?roleId=${roleId}&buttonType=${buttonType}",
    		editurl:"buttonPrivGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'privId',index:'privId',align:'center',label : '<s:text name="searchButtonPriv.privId" />',hidden:true,key:true},
				{name:'roleId',index:'roleId',align:'center',label : '<s:text name="searchButtonPriv.roleId" />',hidden:true},
				{name:'searchOrMenuId',index:'searchOrMenuId',align:'center',label : '<s:text name="searchButtonPriv.searchId" />',hidden:true},
				{name:'label',index:'label',align:'left',label : '<s:text name="buttonPriv.label" />',hidden:false},
				{name:'searchName',index:'searchName',align:'left',label : '<s:text name="buttonPriv.searchName" />',hidden:false}
				],
        	jsonReader : {
				root : "buttonPrivs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'searchOrMenuId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="searchButtonPrivList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: false,
			multiboxonly:false,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
        		var reloadUrl = "${ctx}/buttonPrivDetailGridList";
        		var rowData = jQuery("#${random}_buttonPriv_gridtable").jqGrid('getRowData',rowid);
        		var privId = rowData['privId'];
        		reloadUrl += "?filter_EQS_buttonPriv.privId="+privId;
        		jQuery("#${random}_buttonPrivDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid"); 
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"${random}_buttonPriv_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("${random}_buttonPriv_gridtable");
       		} 

    	});
    	var searchButtoPrivDetailGridIdString="#${random}_buttonPrivDetail_gridtable";
    	var searchButtoPrivDetailGrid = jQuery(searchButtoPrivDetailGridIdString);
    	searchButtoPrivDetailGrid.jqGrid({
    		url : "buttonPrivDetailGridList?filter_EQS_buttonPriv.privId=ihos",
    		editurl:"buttonPrivDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'bpDetailId',index:'bpDetailId',align:'center',label : '<s:text name="searchButtoPrivDetail.sbpDetailId" />',hidden:true,key:true},
			{name:'buttonLabel',index:'buttonLabel',align:'left',label : '<s:text name="buttonPrivDetail.buttonLabel" />',hidden:false},        	
			{name:'buttonURL',index:'buttonURL',align:'left',label : '<s:text name="buttonPrivDetail.buttonURL" />',width:200,hidden:false},
			/* {name:'right',index:'right',align:'center',label : '<s:text name="searchButtoPrivDetail.right" />',hidden:false,width:50,formatter:'checkbox'},
			 */{name:'order',index:'order',align:'center',label : '<s:text name="buttonPrivDetail.order" />',hidden:false,width:50,formatter:'integer'},
			{name:'buttonId',index:'buttonId',align:'center',label : '<s:text name="searchButtoPrivDetail.searchURLId" />',hidden:true}
			],
        	jsonReader : {
				root : "buttonPrivDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'order',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="searchButtoPrivDetailList.title" />',
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
           	var dataTest = {"id":"${random}_buttonPrivDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("${random}_buttonPrivDetail_gridtable");
       		} 

    	});
    jQuery(searchButtonPrivGrid).jqGrid('bindKeys');
  	});
	
	function reloadSearchButtonPrivGrid(){
		propertyFilterSearch('${random}_buttonPriv_search_form','${random}_buttonPriv_gridtable');
		var reloadUrl = "${ctx}/buttonPrivDetailGridList?filter_EQS_buttonPriv.privId=ihos";
		jQuery("#${random}_buttonPrivDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid"); 
	}
	function disableSearchButton(){
		var ids = jQuery("#role_gridtable").jqGrid('getGridParam','selarrrow');
		if(ids == null || ids.length == 0){
			alertMsg.error("请选择角色。");
			return;
		}else if(ids.length > 1){
			alertMsg.error("请只选择一个角色。");
			return;
		}
		var sid = "<s:property value='#parameters.roleId'/>";
		var url = "selectButtonPrivs?buttonType=${buttonType}&random=${random}&roleId="+sid;
		$.pdialog.open(url, "${random}_selectButtonPrivs", "选择禁用按钮", {mask:true,height:600});　
	}
</script>

<div class="page">
	<div id="${random}_buttonPriv_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}_buttonPriv_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='buttonPriv.label'/>:
						<input type="text" name="filter_LIKES_label"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='buttonPriv.searchName'/>:
						<input type="text" name="filter_LIKES_searchName"/>
					</label>
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadSearchButtonPrivGrid();"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadSearchButtonPrivGrid();"><s:text name='button.search'/></button>
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
				<li><a class="changebutton"  href="javaScript:disableSearchButton()"
					><span>选择禁用按钮
					</span>
				</a>
			</ul>
		</div>
	<div style="width:50%;float:left">
		<div id="${random}_buttonPriv_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_buttonPriv_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_buttonPriv_gridtable_addTile">
				<s:text name="searchButtonPrivNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_buttonPriv_gridtable_editTile">
				<s:text name="searchButtonPrivEdit.title"/>
			</label>
			<div id="${random}_load_buttonPriv_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_buttonPriv_gridtable"></table>
			<!--<div id="searchButtonPrivPager"></div>-->
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_buttonPriv_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_buttonPriv_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_buttonPriv_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
		<div style="width:49.7%;float:right">
	<div class="pageContent">
		<div id="${random}_buttonPrivDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_buttonPrivDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_buttonPrivDetail_gridtable_addTile">
				<s:text name="searchButtonPrivDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_buttonPrivDetail_gridtable_editTile">
				<s:text name="searchButtonPrivDetailEdit.title"/>
			</label>
			<div id="${random}_load_buttonPrivDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_buttonPrivDetail_gridtable"></table>
			<!--<div id="searchButtoPrivDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_buttonPrivDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_buttonPrivDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_buttonPrivDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
</div>
