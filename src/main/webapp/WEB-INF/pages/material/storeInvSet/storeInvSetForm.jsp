<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var storeInvSetFormGrid = jQuery("#storeInvSetForm_gridtable");
	    storeInvSetFormGrid.jqGrid({
	    	url : "storeInvSetSearchGridList?storeId="+selectStoreInvSetTreeId,
			datatype : "json",
			mtype : "GET",
	        colModel:[
	{name:'inventoryDict.invId',index:'inventoryDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,key:true},				
	{name:'inventoryDict.invCode',index:'invCode',width:60,align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:false},				
	{name:'inventoryDict.invName',index:'inventoryDict.invName',width:120,align:'left',label : '<s:text name="inventoryDict.invName" />',hidden:false},				
	{name:'inventoryDict.inventoryType.invTypeName',index:'inventoryDict.inventoryType.invTypeName',width:80,align:'left',label : '<s:text name="inventoryType.invTypeName" />',hidden:false},				
	{name:'inventoryDict.invModel',index:'inventoryDict.invModel',width:130,align:'left',label : '<s:text name="inventoryDict.invModel" />',hidden:false,key:true},				
	{name:'inventoryDict.vendor.vendorName',index:'inventoryDict.vendor.vendorName',width:150,align:'left',label : '<s:text name="inventoryDict.vendor" />',hidden:false},				
	{name:'storeNames',index:'storeNames',width:80,align:'left',label : '<s:text name="所在仓库" />',hidden:false}
	/* {name:'store.id',index:'store.id',align:'center',label : '<s:text name="store.id" />',hidden:true}
	 */
	        ],
	        jsonReader : {
				root : "storeInvSets", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'invCode',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="storeInvSetList.title" />',
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
	           if(jQuery(this).getDataIDs().length>0){
	              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"storeInvSetForm_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("storeInvSetForm_gridtable");
	       	} 

	    });
	    jQuery(storeInvSetFormGrid).jqGrid('bindKeys');
	    treeSelect_invTypeInStoreInvSet(jQuery("#storeInvSetForm_search_inventoryType"));
	    combogrid_vendorInStoreInvSet("storeInvSetForm_search_vendor");
	});
	//保存选中的记录
	function saveStoreInvSet(){
		selectStoreInvSetIds = jQuery("#storeInvSetForm_gridtable").jqGrid('getGridParam','selarrrow');
		if(selectStoreInvSetIds.length==0){
			alertMsg.error("请选择要保存的行");
			return;
		}
		var invIds = '';
		for(var i=0,len = selectStoreInvSetIds.length;i<len;i++){
			invIds += jQuery("#storeInvSetForm_gridtable").jqGrid('getRowData',selectStoreInvSetIds[i])['inventoryDict.invId'] +',';
		}
		invIds = invIds.substr(0,invIds.length-1);
		var url = "saveStoreInvSet?invIds="+invIds+"&storeId="+selectStoreInvSetTreeId;
		url = encodeURI(url);
		refreshStoreInvSetGrid(url);
		$.pdialog.closeCurrent();
	}
	
	function refreshStoreInvSetGrid(doUrl){
		$.post(doUrl, function(result) {
			/* var urlString = "storeInvSetSearchGridList?storeId="+selectStoreInvSetTreeId;
			urlString = encodeURI(urlString);
			jQuery("#storeInvSetForm_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); */
			jQuery("#storeInvSet_gridtable").trigger('reloadGrid',[{page: 1 }]);
			alertMsg.correct("材料仓库保存成功!");
		});
	}
	//保存全部
	function saveStoreInvSetAll(){
		//获得当前的查询条件
		var invTypeId = jQuery("#storeInvSetForm_search_inventoryType_id").val();
		var invName = jQuery("#storeInvSetForm_search_invName").val();
		var vendId = jQuery("#storeInvSetForm_search_vendor_id").val();
		var saveAllUrl = "saveStoreInvSetAll?storeId="+selectStoreInvSetTreeId; 
		var filterUrl = "&filter_EQS_inventoryType.id="+invTypeId;
		filterUrl += "&filter_LIKES_invName="+invName;
		filterUrl += "&filter_EQS_vendor.vendorId="+vendId;
		saveAllUrl += filterUrl;
		saveAllUrl = encodeURI(saveAllUrl);
		//alert(saveAllUrl);
		refreshStoreInvSetGrid(saveAllUrl);
		$.pdialog.closeCurrent();
	}
</script>
</head>

<div class="page">
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="storeInvSetForm_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.inventoryType'/>:
							<input id="storeInvSetForm_search_inventoryType" type="text"/>
							<input id="storeInvSetForm_search_inventoryType_id" type="hidden" name="filter_EQS_inventoryType.id"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invName'/>:
							<input id="storeInvSetForm_search_invName" type="text" name="filter_LIKES_invName"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.vendor'/>:
							<input id="storeInvSetForm_search_vendor" type="text"  value="拼音/汉字/编码" size="14"
							class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#storeInvSetForm_search_vendor_id'))" 
							onfocus="clearInput(this,jQuery('#storeInvSetForm_search_vendor_id'))" onkeyDown="setTextColor(this)"/>
							<s:hidden id="storeInvSetForm_search_vendor_id" name="filter_EQS_vendor.vendorId"/>
						</label>&nbsp;&nbsp;
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									 <button type="button" onclick="propertyFilterSearch('storeInvSetForm_search_form','storeInvSetForm_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="storeInvSetForm_gridtable_confirmAll_selected" class="confirmbutton" href="javaScript:saveStoreInvSetAll();"><span>确认全部</span> </a></li>
				<li><a id="storeInvSetForm_gridtable_confirm_selected" class="confirmbutton" href="javaScript:saveStoreInvSet();"><span>确认</span> </a></li>
			</ul>
		</div>
		<div id="storeInvSetForm_gridtable_div" layoutH="130" class="grid-wrapdiv" class="unitBox"  
			buttonBar="optId:storeInvSetId;width:500;height:300">
			<input type="hidden" id="storeInvSetForm_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="storeInvSetForm_gridtable_addTile">
				<s:text name="storeInvSetNew.title"/>
			</label> 
			<label style="display: none"
				id="storeInvSetForm_gridtable_editTile">
				<s:text name="storeInvSetEdit.title"/>
			</label>
			<label style="display: none"
				id="storeInvSetForm_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="storeInvSetForm_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_storeInvSetForm_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			 <table id="storeInvSetForm_gridtable"></table>
			<div id="storeInvSetPager"></div>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="storeInvSetForm_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="storeInvSetForm_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="storeInvSetForm_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>