<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#invCheckImportDict_search_inventoryType").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
		var invCheckImportDictGrid = jQuery("#invCheckImportDict_gridtable");
		invCheckImportDictGrid.jqGrid({
	    	url : "invCheckImportDictGridList?filter_EQS_store.id=${store.id}&ivbhIds=${ivbhIds}",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="invBalanceBatch.id" />',hidden:true,key:true},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : '<s:text name="inventoryDict.invCode" />',hidden:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : '<s:text name="inventoryDict.invName" />',hidden:false},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : '<s:text name="inventoryDict.invModel" />',hidden:false},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : '<s:text name="inventoryDict.firstUnit" />',hidden:false},	
				{name:'invBatch.batchNo',index:'invBatch.batchNo',align:'left',width:120,label : '<s:text name="invBalanceBatch.batchNo" />',hidden:false},	
				{name:'invBatch.id',index:'invBatch.id',align:'left',label : '<s:text name="invBalanceBatch.id" />',hidden:true},	
				{name:'price',index:'price',align:'right',width:100,label : '<s:text name="invBalanceBatch.price" />',hidden:false,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}},	
				{name:'curAmount',index:'curAmount',align:'right',width:100,label : '<s:text name="账面数量" />',hidden:false,formatter : 'number'},	
				{name:'curMoney',index:'curMoney',align:'right',width:120,label : '<s:text name="金额" />',hidden:false,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}  },	
				{name:'storePosition.posName',index:'storePosition.posName',align:'left',width:130,label : '<s:text name="货位" />',hidden:true},	
				{name:'invDict.guarantee',index:'invDict.guarantee',align:'center',width:100,label : '<s:text name="inventoryDict.guarantee" />',hidden:false},	
				{name:'barCode',index:'barCode',align:'left',width:120,label : '<s:text name="invBalanceBatch.barCode" />',hidden:false},
				{name:'invDict.factory',index:'invDict.factory',align:'left',width:160,label : '<s:text name="inventoryDict.factory" />',hidden:false},	
				{name:'invDict.inventoryType.invTypeName',index:'invDict.inventoryType.id',align:'left',width:80,label : '<s:text name="inventoryDict.inventoryType" />',hidden:false},	
				{name:'invDict.vendor.vendorName',index:'invDict.vendor.vendorName',width:160,align:'left',label : '<s:text name="供应商" />',hidden:false}	
	        ],
	        jsonReader : {
				root : "invCheckImportDicts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'curAmount,invDict.invCode',
	        viewrecords: true,
	        sortorder: 'desc',
	        height:300,
	        width:100,
	        gridview:true,
	        rowNum:500,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			 gridComplete:function(){
	           if(jQuery(this).getDataIDs().length>0){
	              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"invCheckImportDict_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("invCheckImportDict_gridtable");
	       	} 

	    });
	    jQuery(invCheckImportDictGrid).jqGrid('bindKeys');
	});  
	
	function addDictToInvCheck(){
		var addIds = jQuery("#invCheckImportDict_gridtable").jqGrid('getGridParam','selarrrow');
		if(addIds.length==0){
			alertMsg.error("请选择记录");
			return;
		}
		var rowDatas = new Array();
		var colMap = new Map();
		colMap.put("invDict.invId","invDict.invId");
		colMap.put("invDict.invCode","invDict.invCode");
		colMap.put("invDict.invName","invDict.invName");
		colMap.put("invDict.invModel","invDict.invModel");
		colMap.put("invDict.firstUnit","invDict.firstUnit");
		colMap.put("invBatch.id","invBatch.id");
		colMap.put("invBatch.batchNo","invBatch.batchNo");
		colMap.put("price","price");
		colMap.put("curMoney","acctMoney");
		colMap.put("curAmount","acctAmount");
		/*colMap.put("accountAmount","chkAmount");
		colMap.put("currentMoney","chkMoney");
		colMap.put("invName","diffAmount");
		colMap.put("invName","diffMoney");
		colMap.put("invName","note"); */
		colMap.put("invDict.guarantee","invDict.guarantee");
		colMap.put("barCode","barCode");
		for(var i=0;i<addIds.length;i++){
			var row = jQuery("#invCheckImportDict_gridtable").jqGrid('getRowData',addIds[i]);
			//row = reMapData(row,colMap);
			rowDatas[i] = row;
		}
		jQuery("#${random}_invCheckTable").addLocalData(rowDatas,colMap);
		$.pdialog.closeCurrent();
	}
	
	 function reloadInvCheckImportDictGrid(){
		var reloadUrl = "invCheckImportDictGridList?filter_EQS_store.id=${store.id}&ivbhIds=${ivbhIds}";
		var invTypeId = jQuery("#invCheckImportDict_search_inventoryType").val();
		var invName = jQuery("#invCheckImportDict_search_invName").val();
		 if(invTypeId){
			reloadUrl += "&filter_EQS_invDict.inventoryType.id="+jQuery("#invCheckImportDict_search_inventoryType_id").val();
		}
		if(invName){
			reloadUrl += "&filter_LIKES_invDict.invName="+invName;
		}
		if(jQuery("#invCheckImportDict_search_zero").attr("checked")){
			reloadUrl += "&filter_GEI_curAmount=0";
		}
		jQuery("#invCheckImportDict_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid"); 
	}
</script>
<script>
	
</script>
</head>

<div class="page">
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="invCheckImportDict_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.store'/>:
							<%-- <input id="invCheckImportDict_search_store_id" type="hidden" value="${store.id}"/> --%>
							<input id="invCheckImportDict_search_store" type="text" readonly="true" value="${store.storeName}"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.inventoryType'/>:
							<input id="invCheckImportDict_search_inventoryType" type="text"/>
							<input id="invCheckImportDict_search_inventoryType_id" type="hidden" name=""/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invName'/>:
							<input id="invCheckImportDict_search_invName" type="text" name=""/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='包含零库存'/>:
							<s:checkbox id="invCheckImportDict_search_zero" name="" theme="simple"/>
						</label>
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadInvCheckImportDictGrid()"><s:text name='button.search'/></button>
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
				<li><a id="invCheckImportDict_gridtable_confirm_selected" class="confirmbutton" href="javaScript:addDictToInvCheck()"><span>确认</span> </a></li>
			</ul>
		</div>
		<div id="invCheckImportDict_gridtable_div" layoutH="120" class="grid-wrapdiv" class="unitBox"  
			style="margin-left: 1px; margin-top: 1px; overflow: hidden"
			buttonBar="optId:invCheckImportDictId;width:900;height:600">
			<input type="hidden" id="invCheckImportDict_gridtable_navTabId" value="${sessionScope.navTabId}"/>
			<div id="load_invCheckImportDict_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			 <table id="invCheckImportDict_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invCheckImportDict_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invCheckImportDict_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invCheckImportDict_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	<%-- <div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="" onclick="javascript:addDictToInvCheck()"><s:text name="确认" /></button>
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