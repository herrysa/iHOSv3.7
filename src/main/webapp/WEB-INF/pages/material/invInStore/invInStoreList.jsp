
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var invInStoreLayout;
	var invInStoreGridIdString="#invInStore_gridtable";
	
	jQuery(document).ready(function() { 
		var invInStoreGrid = jQuery(invInStoreGridIdString);
    	invInStoreGrid.jqGrid({
    		url : "invInStoreGridList",
    		editurl:"invInStoreGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="invInStore.id" />',hidden:true,key:true},
				{name:'invId',index:'invId',align:'center',label : '<s:text name="invInStore.invId" />',hidden:true},
				{name:'invCode',index:'invCode',align:'left',width:80,label : '<s:text name="invInStore.invCode" />',hidden:false},
				{name:'invName',index:'invName',align:'left',label : '<s:text name="invInStore.invName" />',hidden:false},
				{name:'invModel',index:'invModel',align:'left',width:80,label : '<s:text name="invInStore.invModel" />',hidden:false},
				{name:'firstUnit',index:'firstUnit',align:'center',width:60,label : '<s:text name="invInStore.firstUnit" />',hidden:false},
				//{name:'isBar',index:'isBar',align:'center',width:100,label : '<s:text name="invInStore.isBar" />',hidden:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"}},
				//{name:'isQuality',index:'isQuality',align:'center',width:100,label : '<s:text name="invInStore.isQuality" />',hidden:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"}},
				{name:'refCost',index:'refCost',align:'right',width:100,label : '<s:text name="invInStore.refCost" />',hidden:false,formatter:'currency',formatoptions:{thousandsSeparator:',',decimalPlaces:2}},
				{name:'invTypeId',index:'invTypeId',align:'center',label : '<s:text name="invInStore.invTypeId" />',hidden:true},
				{name:'invTypeName',index:'invTypeName',align:'left',width:100,label : '<s:text name="invInStore.invTypeName" />',hidden:false},
				{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="invInStore.orgCode" />',hidden:true},
				{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="invInStore.copyCode" />',hidden:true},
				{name:'storeId',index:'storeId',align:'center',label : '<s:text name="invInStore.storeId" />',hidden:true},
				{name:'vendorId',index:'vendorId',align:'center',label : '<s:text name="invInStore.vendorId" />',hidden:true},
				{name:'factory',index:'factory',align:'center',label : '<s:text name="invInStore.factory" />',hidden:false},
				{name:'vendorName',index:'vendorName',align:'left',label : '<s:text name="invInStore.vendorName" />',hidden:false}        	],
        	jsonReader : {
				root : "invInStores", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'invId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="invInStoreList.title" />',
        	postData:{storeId:"${storeId}",filter_EQS_vendorId:"${vendorId}"},
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"invInStore_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("invInStore_gridtable");
       		} 

    	});
    jQuery(invInStoreGrid).jqGrid('bindKeys');
    jQuery("#invInStore_search_form_invType").treeselect({
		dataType : "sql",
		optType : "single",
		sql : "SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
		exceptnullparent : false,
		lazy : false
	});
    jQuery("#invInStore_search_form_vendor").combogrid({
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
			$("#invInStore_search_form_vendor_id").val(ui.item.VID);
			return false; 
		}
	});
 });
	
	function invInStoreGridReload(){
		var urlString = "invInStoreGridList?storeId=${storeId}";
		var search_invType = jQuery("#invInStore_search_form_invType_id").val();
		var search_invCode = jQuery("#invInStore_search_form_invCode").val();
		var search_invName = jQuery("#invInStore_search_form_invName").val();
		var search_vendor_id = "";
		var search_vendor = jQuery("#invInStore_search_form_vendor").val();
		if(search_vendor){
			search_vendor_id = jQuery("#invInStore_search_form_vendor_id").val();
		}	
		urlString=urlString+"&filter_EQS_invTypeId="+search_invType
				+"&filter_LIKES_invCode="+search_invCode
				+"&filter_LIKES_invName="+search_invName
				+"&filter_EQS_vendorId="+search_vendor_id;
		//alert(urlString);
		urlString=encodeURI(urlString);
		
		jQuery("#invInStore_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	//点击“确认”后要关闭窗口
	function batchAddInvMainInit(){
		var addIds = jQuery("#invInStore_gridtable").jqGrid('getGridParam','selarrrow');
		if(addIds.length==0){
			alertMsg.error("请选择记录");
			return;
		}
		var rowDatas = new Array();
		for(var i=0;i<addIds.length;i++){
			var row = jQuery("#invInStore_gridtable").jqGrid('getRowData',addIds[i]);
			rowDatas[i] = row;
		}
		
		var colMap = new Map();
		colMap.put("invId","invDict.invId");
		colMap.put("invCode","invDict.invCode");
		colMap.put("invName","invDict.invName");
		colMap.put("invModel","invDict.invModel");
		colMap.put("firstUnit","invDict.firstUnit");
		debugger;
		var fromType = "${fromType}";
		if(fromType=="invMainIn"){
			colMap.put("isBar","invDict.isBar");
			colMap.put("isQuality","invDict.isQuality");
			colMap.put("refCost","inPrice");
			/* for(var i=0;i<rowDatas.length;i++){
				var row = rowDatas[i];
				row = reMapData(row,colMap);
				rowDatas[i] = row;
			} */
			jQuery("#${random}_invMainInTable").addLocalData(rowDatas,colMap);
		}else if(fromType=="invMainInit"){
			colMap.put("isBar","invDict.isBar");
			colMap.put("isQuality","invDict.isQuality");
			colMap.put("refCost","inPrice");
			/* for(var i=0;i<rowDatas.length;i++){
				var row = rowDatas[i];
				row = reMapData(row,colMap);
				rowDatas[i] = row;
			} */
			jQuery("#${random}_invMainInitTable").addLocalData(rowDatas,colMap);
		}else if(fromType=="deptNeedPlan"){
			colMap.put("refCost","price");
			colMap.put("lastAmount","lastAmount");
			colMap.put("lastCostAmount","lastCostAmount");
			colMap.put("sumAmount","sumAmount");
			colMap.put("storeAmount","storeAmount");
			colMap.put("realBuyAmount","realBuyAmount");
			/* for(var i=0;i<rowDatas.length;i++){
				var row = rowDatas[i];
				//ajax获取对应材料的对应数量，并赋值，然后带回
				jQuery.ajax({
				    url: 'getLastAndStoreAmount',
				    data: {invId:row['invId'],storeId:"${storeId}"},
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    },
				    success: function(data){
				    	var amounts = data.amounts;
				    	row.lastAmount = amounts['lastAmount'];
				    	row.lastCostAmount = amounts['lastCostAmount'];
				    	row.sumAmount = amounts['sumAmount'];
				    	row.storeAmount = amounts['storeAmount'];
				    	row.realBuyAmount = amounts['realBuyAmount'];
				    }
				});
				/* row = reMapData(row,colMap);
				rowDatas[i] = row; 
				//alert(json2str(row));
			} */
			
			jQuery("#${random}_deptNeedPlanTable").addZhLocalData(rowDatas,colMap);
		}else if(fromType=="purchasePlan"){
			colMap.put("refCost","price");
			colMap.put("factory","invDict.factory");
			colMap.put("curStock","curStock");
			colMap.put("storeStock","storeStock");
			colMap.put("needAmount","needAmount");
			/* for(var i=0;i<rowDatas.length;i++){
				var row = rowDatas[i];
				//ajax获取对应材料的对应数量，并赋值，然后带回
				jQuery.ajax({
				    url: 'getStoreAndNeedAmount',
				    data: {invId:row['invId'],storeId:"${storeId}"},
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    },
				    success: function(data){
				    	var amounts = data.amounts;
				    	row.curStock = amounts['curStock'];
				    	row.storeStock = amounts['storeStock'];
				    	row.needAmount = amounts['needAmount'];
				    }
				});
			} */
			jQuery("#${random}_purchasePlanTable").addLocalData(rowDatas,colMap);
		}else if(fromType=="order"){
			colMap.put("refCost","price");
			jQuery("#${random}_orderTable").addLocalData(rowDatas,colMap);
		}else if(fromType=="deptApp"){
			colMap.put("refCost","appPrice");
			colMap.put("factory","invDict.factory");
			/* for(var i=0;i<rowDatas.length;i++){
				var row = rowDatas[i];
				row = reMapData(row,colMap);
				rowDatas[i] = row;
			} */
			jQuery("#${random}_deptAppTable").addLocalData(rowDatas,colMap);
		}
		$.pdialog.closeCurrent();
		//alert(addIds);
	}
</script>

<div class="page">
	<div id="invInStore_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="invInStore_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='invInStore.invTypeName'/>:
						<input type="hidden" id="invInStore_search_form_invType_id" name="filter_EQS_invTypeId"/>
						<input type="text" id="invInStore_search_form_invType"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invInStore.invCode'/>:
						<input type="text" id="invInStore_search_form_invCode" name="filter_LIKES_invCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invInStore.invName'/>:
						<input type="text" id="invInStore_search_form_invName" name="filter_LIKES_invName"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invInStore.vendorName'/>:
						<input type="hidden" id="invInStore_search_form_vendor_id" name="filter_EQS_vendorId"/>
						<input type="text" id="invInStore_search_form_vendor" value="拼音/汉字/编码" 
						class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#invInStore_search_form_vendor_id'))" 
						onfocus="clearInput(this,jQuery('#invInStore_search_form_vendor_id'))" onkeyDown="setTextColor(this)"/>
					</label>&nbsp;&nbsp;
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('invInStore_search_form','invInStore_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('invInStore_search_form','invInStore_gridtable')"><s:text name='button.search'/></button>
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
				<li><a id="invInStore_gridtable_confirm_selected" class="confirmbutton" href="javaScript:batchAddInvMainInit();"><span>确认</span> </a></li>
			</ul>
		</div>
		<div id="invInStore_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="invInStore_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invInStore_gridtable_addTile">
				<s:text name="invInStoreNew.title"/>
			</label> 
			<label style="display: none"
				id="invInStore_gridtable_editTile">
				<s:text name="invInStoreEdit.title"/>
			</label>
			<div id="load_invInStore_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="invInStore_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invInStore_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invInStore_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="invInStore_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	<%-- <div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<!-- 点击确认，刷新该页面(带查询条件)和期初入库单页面 -->
						<button type="button" id="" onclick="javascript:batchAddInvMainInit()"><s:text name="确认" /></button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="Button" onclick="$.pdialog.close('batchAddStoreInvs');"><s:text name="取消" /></button>
					</div>
				</div>
			</li>
		</ul>
	</div> --%>
</div>