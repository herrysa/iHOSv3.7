
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 	var width = 960,height = 628;
 	var cons = parseMap("${importOrderMap}");
	function orderMainAndDetailGridReload(){
		var urlString = "orderGridList?filter_INS_state=1,2&filter_EQS_procType.typeId="+cons.get("procTypeId")+"&filter_INS_orderNo="+cons.get("orderNo");
		var deptId = jQuery("#orderMainAndDetail_search_dept_id").val();
		var personId = jQuery("#orderMainAndDetail_search_buyPerson_id").val();
		var checkDate_from = jQuery("#orderMainAndDetail_search_check_date_from").val();
		var checkDate_to = jQuery("#orderMainAndDetail_search_check_date_to").val();
		var vendorId = jQuery("#orderMainAndDetail_search_vendor_id").val();
		
		urlString = urlString
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			+ "&filter_EQS_vendor.id=" + vendorId+ "&filter_EQS_dept.departmentId=" + deptId
			+ "&filter_EQS_buyPerson.personId="+personId;
		urlString=encodeURI(urlString);
		jQuery("#orderMainAndDetail_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var orderMainAndDetailGridIdString="#orderMainAndDetail_gridtable";
	var postData = {
		//'storeId':cons.get("storeId"),
		'filter_EQS_procType.typeId':cons.get("procTypeId"),
		'filter_GED_checkDate':jQuery("#orderMainAndDetail_search_check_date_from").val(),
		'filter_LED_checkDate':'${currentSystemVariable.businessDate}',
		'filter_EQS_dept.departmentId':cons.get("buyDeptId"),
		'filter_EQS_buyPerson.personId':cons.get("buyPersonId"),
		'filter_EQS_vendor.id':cons.get("vendorId"),
		'filter_INS_orderNo':cons.get("orderNo")
	}
	function initSelectArea(){
		if(cons.get("buyDeptId")){
			jQuery("#orderMainAndDetail_search_dept").val(cons.get("buyDeptName"));
			jQuery("#orderMainAndDetail_search_dept_id").val(cons.get("buyDeptId"));
		}
		if(cons.get("buyPersonId")){
			jQuery("#orderMainAndDetail_search_buyPerson").val(cons.get("buyPersonName"));
			jQuery("#orderMainAndDetail_search_buyPerson_id").val(cons.get("buyPersonId"));
		}
		if(cons.get("vendorId")){
			jQuery("#orderMainAndDetail_search_vendor").val(cons.get("vendorName"));
			jQuery("#orderMainAndDetail_search_vendor_id").val(cons.get("vendorId"));
		}
	}
	var importIds;
	jQuery(document).ready(function() { 
		initSelectArea();
		var orderMainAndDetailGrid = jQuery(orderMainAndDetailGridIdString);
		orderMainAndDetailGrid.jqGrid({
	    	url : "orderGridList?filter_INS_state=1,2",//?filter_GED_checkDate="+jQuery("#orderMainAndDetail_search_check_date_from").val()+"&filter_LED_checkDate=${currentSystemVariable.businessDate}",
	    	editurl:"orderGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
			{name:'orderId',index:'orderId',align:'center',label : '<s:text name="order.orderId" />',hidden:true,key:true},				
			{name:'orderNo',index:'orderNo',align:'left',width:150,label : '<s:text name="order.orderNo" />',hidden:false,highsearch:true},				
			{name:'vendor.vendorName',index:'vendor.vendorName',width : 140,align:'left',label : '<s:text name="order.vendor" />',hidden:false,highsearch:true},				
			{name:'vendor.vendorId',index:'vendor.vendorId',width : 140,align:'left',label : '<s:text name="order.vendorId" />',hidden:true,highsearch:false},				
			{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="order.dept" />',hidden:false,highsearch:true},				
			{name:'buyPerson.name',index:'buyPerson.name',width : 80,align:'left',label : '<s:text name="order.buyPerson" />',hidden:false,highsearch:true},				
			{name:'buyPerson.personId',index:'buyPerson.personId',width : 80,align:'left',label : '<s:text name="order.buyPerson" />',hidden:true,highsearch:false},				
			{name:'arrAddr',index:'arrAddr',align:'center',width : 120,label : '<s:text name="order.arrAddr" />',hidden:false,highsearch:true},				
			{name:'procType.typeName',index:'procType.typeName',width : 100,align:'left',label : '<s:text name="order.procType" />',hidden:false,highsearch:true},				
			{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="order.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
			{name:'remark',index:'remark',align:'left',width : 130,label : '<s:text name="order.remark" />',hidden:false,highsearch:true}				
	        ],
	        jsonReader : {
				root : "orders", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			postData:postData,
	        sortname: 'orderNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="orderList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: false,
			multiboxonly:false,
			shrinkToFit:true,
			autowidth:true,
			subGrid:true,
			subGridRowExpanded:function(subgrid_id,row_id){
				closeOtherSubGrid(row_id);
				loadImportOrderToInvMainInSubGrid(subgrid_id,row_id);
				importIds = row_id;
			},
	        onSelectRow: function(rowid) {
	       		
	       	},
			 gridComplete:function(){
				 var rowNum = jQuery(this).getDataIDs().length;
	             if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  setCellText(this,id,'orderNo','<span style="color:blue" >'+ret[i]["orderNo"]+'</span>');
	              }
	            }
	           var dataTest = {"id":"orderMainAndDetail_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("orderMainAndDetail_gridtable");
	       	} 
   		});
    	jQuery(orderMainAndDetailGrid).jqGrid('bindKeys');
    	
    	jQuery("#orderMainAndDetail_search_dept").treeselect({
 			dataType:"sql",
 			optType:"single",
 			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
 			exceptnullparent:false,
 			lazy:false,
			callback : {
				afterClick : function() {
					$("#orderMainAndDetail_search_buyPerson_id").val("");
					$("#orderMainAndDetail_search_buyPerson").val("");
				}
			}
 		});
    	jQuery("#orderMainAndDetail_search_buyPerson").treeselect({
 			dataType:"sql",
 			optType:"single",
 			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
 			exceptnullparent:false,
 			lazy:false
 		});
    	jQuery("#orderMainAndDetail_search_vendor").combogrid({
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
					$("#orderMainAndDetail_search_vendor_id").val(ui.item.VID);
				return false; 
			}
		});		 
  	});
	
	function beforeEditBuyPerson(){
		var buyDept = jQuery("#orderMainAndDetail_search_dept").val();
		if(buyDept){
			jQuery("#orderMainAndDetail_search_buyPerson").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT p.personId id ,p.name name FROM t_person p where p.disabled = 0 and p.dept_id='"+$("#orderMainAndDetail_search_dept_id").val()+"'",
				exceptnullparent:false,
				lazy:false
			}); 
		}else{
			alertMsg.error("请先填写采购科室");
			return;
		}
	}
	function closeOtherSubGrid(row_id){
		var sid = jQuery("#orderMainAndDetail_gridtable").getDataIDs();
		for(var i=0;i<sid.length;i++){
			if(row_id==sid[i]){
				continue;
			}
			jQuery("#orderMainAndDetail_gridtable").collapseSubGridRow(sid[i]);
		}
	}
	
	function loadImportOrderToInvMainInSubGrid(subgrid_id,row_id){
		var subgrid_table_id = subgrid_id+"_t";
		jQuery("#"+subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll' border='0' style='margin-bottom:1px'></table>");
        var importOrderToInvMainInSubGrid = jQuery("#"+subgrid_table_id).jqGrid({
        	url : "orderDetailGridList?filter_EQS_order.orderId="+row_id+"&storeId="+cons.get("storeId"),
    		datatype : "json",
    		mtype : "GET",
    		colModel:[
				{name:'orderDetailId',index:'orderDetailId',align:'center',label : '<s:text name="orderDetail.orderDetailId" />',hidden:true,key:true},				
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : '材料名称',hidden:false},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : '材料编码',hidden:false},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : '型号规格',hidden:false},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : '计量单位',hidden:false},	
				{name:'amount',index:'amount',align:'right',width:100,label : "数量",hidden:false,formatter : 'number'},				
				{name:'price',index:'price',align:'right',width:100,label : "单价",hidden:false,formatter : 'number'},				
				{name:'money',index:'money',align:'right',width:110,label : "金额",hidden:false,formatter : 'currency',sortable:false},	
				{name:'inAmount',index:'inAmount',align:'right',width:120,label : "本次入库数量",hidden:false,formatter : 'number',sortable:false,edittype:"text",editable : true,editrules:{number:true},editoptions:{dataEvents:[{type:'blur',fn: function(e) { validateImportOrderInAmount(this); }}]}},	
				{name:'lastAmount',index:'lastAmount',align:'right',width:100,label : "未入库数量",hidden:false,formatter : 'number',sortable:false},	
				{name:'remark',index:'remark',align:'left',width:150,label : "备注",hidden:false},				
				{name:'state',index:'state',align:'left',width:12,label : "状态",hidden:false}				
			],
			jsonReader : {
				root : "orderDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
    		sortname: 'invDict.invCode',
            sortorder: "desc",
            height:"100%", 
        	multiselect: true,
    		multiboxonly:true,
            shrinkToFit:true,
            autowidth:true,
            beforeSelectRow:function(rowid){
            	var state = jQuery(this).jqGrid("getCell",rowid,"state");
    			if(state=="0"){
    				return false;
    			}else{
    				return true;
    			}
            },
            onSelectRow : function(rowid) {
            	
    		},
    		onSelectAll:function(rowid){
    			for(var i=0;i<rowid.length;i++){
    				var state = jQuery(this).jqGrid("getCell",rowid[i],"state");
    				if(state=="0"){
    					jQuery("#"+rowid[i]).find("td").eq(0).find("input").eq(0).removeAttr("checked");
    				}
    			}
    		},
    		ondblClickRow: function (rowid, iRow, iCol) {
    			var state = jQuery(this).jqGrid("getCell",rowid,"state");
    			if(state=="0"){
    				return false;
    			}else{
	    			importOrderToInvMainInSubGrid.jqGrid('editRow', rowid);
    			}
    		},
            gridComplete:function(){
            	var dataTest = {"id":subgrid_table_id};
           	    jQuery.publish("onLoadSelect",dataTest,null);
           	 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['state']=="0"){
	              		jQuery("#"+id).find("td").css("background-color","#DDDDDD");
	              		var che = jQuery("#"+id).find("td").eq(0).find("input").eq(0).attr("disabled","true");
	              	  }
	              }
	            }
            }
        });
	}
	function numberValidate(obj){
  		var objValue = jQuery(obj).val();
  		if(!isFloatOrNull(objValue)){
  			jQuery(obj).val("")
  			alertMsg.error("请输入数字！");
  			return false;
  		}else{
  			return true;
  		}
  	}
	function validateImportOrderInAmount(obj){
		if(!numberValidate(obj)){
  			return;
  		}
		var tdObj = jQuery(obj).parent();//td
		var desc = tdObj.attr("aria-describedby");
		var subGridId = desc.substring(0,desc.lastIndexOf('_'));
		var trObj = tdObj.parent();
		var trId = trObj.children().eq(1).text();//tr
		var lastAmount = jQuery("#"+subGridId).jqGrid("getCell",trId,"lastAmount");
		var inAmount = jQuery(obj).val();
		lastAmount = convertStringToFloat(lastAmount);
		inAmount = convertStringToFloat(inAmount);
		if(inAmount>lastAmount){
			jQuery(obj).val("");
			alertMsg.error("入库数量大于未入库数量！");
			return;
		}
		jQuery("#"+subGridId).jqGrid("saveRow",trId,{url:'clientArray'});
	}
	function doImportOrderToInvMainIn(){
		var importMap = new Map();
		var subGridId = "#orderMainAndDetail_gridtable_"+importIds+"_t";
		if(jQuery(subGridId).length > 0){
			var allSubIds = jQuery(subGridId).getDataIDs();
			var subIds = new Array();
			for(var i=0;i<allSubIds.length;i++){
				var checked = jQuery("#"+allSubIds[i]).find("td").eq(0).find("input").eq(0).attr("checked");
				if(checked){
					subIds.push(allSubIds[i]);	
				}
			}
			if(subIds.length>0){
				var orderDetailArray = new Array();
				for(var j=0;j<subIds.length;j++){
					var inAmount = jQuery(subGridId).jqGrid("getCell",subIds[j],"inAmount");
					var orderDetail = {
						'orderDetailId':subIds[j],
						'inAmount':inAmount
					}
					orderDetailArray.push(orderDetail);
				}
				importMap.put(importIds,orderDetailArray);
			}else{
				alertMsg.error("请选择记录");
				return;
			}
		}
		if(importMap.size()==0){
			alertMsg.error("无引入数据");
			return;
		}
		var importJson =JSON.stringify(importMap);
		//console.log(importJson);
		var url = "${ctx}/importOrderToInvMainIn?importJson="+importJson;
		$.post(url,function(data) {
			//data.order、data.invDetails
			//获取生成的入库明细，以及附带的科室、采购员、供货商、订单号信息，回写至入库单并关闭该窗口
			fromOrderData = importJson;
			var order = data.order;
			//alert(order.orderNo);
			$("#${random}_invMainIn_buyDept").val(order.dept.name);
			$("#${random}_invMainIn_buyDept_id").val(order.dept.departmentId);
			$("#${random}_invMainIn_buyPerson").val(order.buyPerson.name);
			$("#${random}_invMainIn_buyPerson_id").val(order.buyPerson.personId);
			$("#${random}_invMainIn_vendorName").val(order.vendor.vendorName);
			$("#${random}_invMainIn_vendorName_id").val(order.vendor.vendorId);
			$("#${random}_invMainIn_orderNo").val(data.orderNos);
			var invDetails = data.invDetails;
			var colMap = new Map();
			colMap.put("invId","invDict.invId");
			colMap.put("invCode","invDict.invCode");
			colMap.put("invName","invDict.invName");
			colMap.put("invModel","invDict.invModel");
			colMap.put("firstUnit","invDict.firstUnit");
			colMap.put("inAmount","inAmount");
			colMap.put("inPrice","inPrice");
			colMap.put("inMoney","inMoney");
			var rowDatas = new Array();
			$.each(invDetails,function(index,content){
				var rowData = new Object();
				rowData.invId = content.invDict.invId;
				rowData.invCode = content.invDict.invCode;
				rowData.invName = content.invDict.invName;
				rowData.invModel = content.invDict.invModel;
				rowData.firstUnit = content.invDict.firstUnit;
				rowData.inAmount = content.inAmount;
				rowData.inPrice = content.inPrice;
				rowData.inMoney = content.inMoney;
				rowDatas.push(rowData);
			});
			for(var i=0;i<rowDatas.length;i++){
				row = reMapData(row,colMap);
				rowDatas[i] = row;
			}
			jQuery("#${random}_invMainInTable").addLocalData(rowDatas);
			$.pdialog.closeCurrent();
		});
	}
	
</script>

<div class="page">
<div id="orderMainAndDetail_container">
	<div id="orderMainAndDetail_layout-center" class="pane ui-layout-center">
	<div id="orderMainAndDetail_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="orderMainAndDetail_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.dept'/>:
						<input type="hidden" id="orderMainAndDetail_search_dept_id">
					 	<input type="text" id="orderMainAndDetail_search_dept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.buyPerson'/>:
						<input type="hidden" id="orderMainAndDetail_search_buyPerson_id" >
						<input type="text" id="orderMainAndDetail_search_buyPerson" onfocus="beforeEditBuyPerson()">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.checkDate'/>:
						<input type="text"	id="orderMainAndDetail_search_check_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'orderMainAndDetail_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="orderMainAndDetail_search_check_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'orderMainAndDetail_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >供货商:
						<input type="hidden" id="orderMainAndDetail_search_vendor_id" >
					 	<input type="text" id="orderMainAndDetail_search_vendor" value="拼音/汉字/编码"
					 	class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#orderMainAndDetail_search_vendor_id'))" 
						onfocus="clearInput(this,jQuery('#orderMainAndDetail_search_vendor_id'))" onkeyDown="setTextColor(this)" >
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="orderMainAndDetailGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
					</form>
				</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="orderMainAndDetailGridReload()"><s:text name='button.search'/></button>
							</div>
						</div></li>
				
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:doImportOrderToInvMainIn()" ><span><s:text name="引入" /></span></a>
				</li>
			</ul>
		</div>
		<div id="orderMainAndDetail_gridtable_div" layoutH="120" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="orderMainAndDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_orderMainAndDetail_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			<table id="orderMainAndDetail_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="orderMainAndDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="orderMainAndDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="orderMainAndDetail_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>
</div>
</div>