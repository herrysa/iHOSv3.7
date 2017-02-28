<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var storeInvSetFormGrid = jQuery("#batchAddOutInv_gridtable");
	    storeInvSetFormGrid.jqGrid({
	    	url : "invOutStoreGridList?filter_EQS_storeId=${storeId}",
			datatype : "json",
			mtype : "GET",
	        colModel:[
	{name:'id',index:'id',align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:true,key:true},	
	{name:'batchId',index:'batchId',align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:true,key:false},	
	{name:'invId',index:'invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,key:false},	
	{name:'invCode',index:'invCode',width:80,align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:false,sortable:false},				
	{name:'invName',index:'invName',width:120,align:'left',label : '<s:text name="inventoryDict.invName" />',hidden:false,sortable:false},				
	{name:'invModel',index:'invModel',width:80,align:'left',label : '<s:text name="inventoryDict.invModel" />',hidden:false,sortable:false},				
	{name:'firstUnit',index : 'firstUnit',width:60,align : 'center',label : '<s:text name="inventoryDict.firstUnit" />',hidden : false,sortable:false},
	{name:'invTypeName',index : 'invTypeName',width:100,align : 'left',label : '<s:text name="物资类别" />',hidden : false,sortable:false},
	{name:'vendorName',index : 'vendorName',width:110,align : 'left',label : '<s:text name="供应商" />',hidden : false,sortable:false},
	{name:'batchNo',index:'batchNo',width:120,align:'left',label : '<s:text name="invDetail.batchNo" />',hidden:false,sortable:false},				
	{name:'price',index:'price',width:100,align:'right',label : '<s:text name="单价" />',hidden:false,formatter:'currency',formatoptions:{thousandsSeparator:',',decimalPlaces:2}},
	{name:'curAmount',index:'curAmount',width:120,align:'right',label : '<s:text name="inventoryDict.currentCount" />',hidden:false,sortable:false,formatter:'number'}				
	        ],
	        jsonReader : {
				root : "invOutStores", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        viewrecords: true,
	        sortname : 'invCode,productionDate,batchNo,',
	        sortorder: 'asc',
	        //caption:'<s:text name="storeInvSetList.title" />',
	        height:300,
	        gridview:true,
	        rowNum:500,
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
	           var dataTest = {"id":"batchAddOutInv_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("batchAddOutInv_gridtable");
	       	} 

	    });
	    jQuery(storeInvSetFormGrid).jqGrid('bindKeys');
	    jQuery("#batchAddOutInv_gridtable_search_invType").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
	    jQuery("#batchAddOutInv_gridtable_search_vendor").combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "SELECT v.vendorId vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where disabled = 0 and isMate = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
				cloumns : 'vendorName,cncode,vendorCode'
			},
			rows:10,
			sidx:"VID",
			showOn:false,
			colModel : [ {
				'columnName' : 'VID',
				'width' : '10',
				'label' : 'VID',
				hidden : true,
			}, {
				'columnName' : 'VCODE',
				'width' : '30',
				'align' : 'left',
				'label' : '供应商编码'
			},{
				'columnName' : 'VNAME',
				'width' : '60',
				'align' : 'left',
				'label' : '供应商名称'
			}
			],
			select : function(event, ui) {
				 $(event.target).val(ui.item.VNAME);
				$("#batchAddOutInv_gridtable_search_vendor_id").val(ui.item.VID);
				return false; 
			}
		});
	});
	
	function saveInvOutSelect(){
		var addIds = jQuery("#batchAddOutInv_gridtable").jqGrid('getGridParam','selarrrow');
		if(addIds.length==0){
			alertMsg.error("请选择记录");
			return;
		}
		//
		var rowDatas = new Array();
		var colMap = new Map();
		colMap.put("invId","invDict.invId");
		colMap.put("invCode","invDict.invCode");
		colMap.put("invName","invDict.invName");
		colMap.put("invModel","invDict.invModel");
		colMap.put("firstUnit","invDict.firstUnit");
		colMap.put("price","inPrice");
		colMap.put("batchId","invBatch.id");
		colMap.put("batchNo","invBatch.batchNo");
		for(var i=0;i<addIds.length;i++){
			var row = jQuery("#batchAddOutInv_gridtable").jqGrid('getRowData',addIds[i]);
			//row = reMapData(row,colMap);
			rowDatas[i] = row;
		} 
		var tableId = "${navTabId}";
		if(!tableId){
			tableId = "${random}_invMainOutTable";
		}
		jQuery("#"+tableId).addLocalData(rowDatas,colMap);
		//jQuery("#${random}_invMainOutTable").addLocalData(rowDatas,colMap);
		$.pdialog.closeCurrent();
	}
</script>
</head>

<div class="page">
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="batchAddOutInvForm_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.inventoryType'/>:
							<input id="batchAddOutInv_gridtable_search_invType" type="text"/>
							<input id="batchAddOutInv_gridtable_search_invType_id" type="hidden" name="filter_EQS_invTypeId"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invCode'/>:
							<input id="batchAddOutInv_gridtable_invCode" type="text" name="filter_LIKES_invCode"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invName'/>:
							<input id="batchAddOutInv_gridtable_invName" type="text" name="filter_LIKES_invName"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='供应商'/>:
							<input id="batchAddOutInv_gridtable_search_vendor" type="text"  value="拼音/汉字/编码" size="14"
							class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#batchAddOutInv_gridtable_search_vendor_id'))" 
							onfocus="clearInput(this,jQuery('#batchAddOutInv_gridtable_search_vendor_id'))" onkeyDown="setTextColor(this)"/>
							<s:hidden id="batchAddOutInv_gridtable_search_vendor_id" name="filter_EQS_vendorId"/>
						</label>
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									 <button type="button" onclick="propertyFilterSearch('batchAddOutInvForm_search_form','batchAddOutInv_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="batchAddOutInv_gridtable_confirm_selected" class="confirmbutton" href="javaScript:saveInvOutSelect()"><span>确认</span> </a></li>
			</ul>
		</div>
		<div id="batchAddOutInv_gridtable_div" layoutH="120" class="grid-wrapdiv" class="unitBox"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:storeInvSetId;width:500;height:300">
			<input type="hidden" id="batchAddOutInv_gridtable_navTabId" value="${sessionScope.navTabId}">
			</label>
			<div id="load_batchAddOutInv_gridtable" style="display:none" class='loading ui-state-default ui-state-active'></div>
			 <table id="batchAddOutInv_gridtable"></table>
			<div id="storeInvSetPager"></div>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="batchAddOutInv_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="batchAddOutInv_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="batchAddOutInv_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	<%-- <div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="saveInvOutSelectButton" onclick="saveInvOutSelect()"><s:text name="确认" /></button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="取消" /></button>
					</div>
				</div>
			</li>
		</ul>
	</div> --%>
</div>