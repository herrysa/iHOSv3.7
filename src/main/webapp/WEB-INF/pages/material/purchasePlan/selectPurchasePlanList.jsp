
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	function selectPurchasePlanGridReload(){
		var urlString = "purchasePlanGridList?filter_EQS_state=1";
		var storeId = jQuery("#selectPurchasePlan_search_store_id").val();
		var deptId = jQuery("#selectPurchasePlan_search_dept_id").val();
		//var makeDate_from = jQuery("#selectPurchasePlan_search_make_date_from").val();
		//var makeDate_to = jQuery("#selectPurchasePlan_search_make_date_to").val();
		var checkDate_from = jQuery("#selectPurchasePlan_search_check_date_from").val();
		var checkDate_to = jQuery("#selectPurchasePlan_search_check_date_to").val();
		
		urlString = urlString
			//+ "&filter_GED_makeDate=" + makeDate_from+ "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			+ "&filter_EQS_store.id=" + storeId+ "&filter_EQS_dept.departmentId=" + deptId;
		urlString=encodeURI(urlString);
		jQuery("#selectPurchasePlan_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var selectPurchasePlanGridIdString="#selectPurchasePlan_gridtable";
	jQuery(document).ready(function() { 
		var selectPurchasePlanGrid = jQuery(selectPurchasePlanGridIdString);
		selectPurchasePlanGrid.jqGrid({
	    	url : "purchasePlanGridList?filter_EQS_state=1&filter_GED_checkDate="+jQuery("#selectPurchasePlan_search_check_date_from").val()+"&filter_LED_checkDate=${currentSystemVariable.businessDate}",
	    	editurl:"purchasePlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'purchaseId',index:'purchaseId',align:'center',label : '<s:text name="purchasePlan.purchaseId" />',hidden:true,key:true},				
				{name:'purchaseNo',index:'purchaseNo',align:'left',width : 120,label : '<s:text name="purchasePlan.purchaseNo" />',hidden:false},				
				{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="purchasePlan.dept" />',hidden:false},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="purchasePlan.store" />',hidden:false},				
				//{name:'makePerson.name',index:'makePerson.name',align:'left',width : 60,label : '<s:text name="purchasePlan.makePerson" />',hidden:false},				
				//{name:'makeDate',index:'makeDate',align:'center',width : 100,label : '<s:text name="purchasePlan.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width : 60,label : '<s:text name="purchasePlan.checkPerson" />',hidden:false},				
				{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="purchasePlan.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="purchasePlan.remark" />',hidden:false}				
				//{name:'state',index:'state',align:'center',width : 100,label : '<s:text name="purchasePlan.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;4:已中止'}}			
	        ],
	        jsonReader : {
				root : "purchasePlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'purchaseNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="purchasePlanList.title" />',
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
				var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  /* if(ret[i]['state']=="0"){
	              		  setCellText(this,id,'state','<span >新建</span>');
	              	  }else if(ret[i]['state']=="1"){
	              		  setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['state']=="4"){
	              		  setCellText(this,id,'state','<span style="color:red" >已中止</span>');
	              	  } */
	              	  editUrl = "'${ctx}/editPurchasePlan?purchaseId="+ret[i]["purchaseId"]+"&docPreview=refer'";
	   	        	  setCellText(this,id,'purchaseNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showSelectPurchasePlan('+editUrl+');">'+ret[i]["purchaseNo"]+'</a>');
	   	        	 
	              }
	            }
	           var dataTest = {"id":"selectPurchasePlan_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("selectPurchasePlan_gridtable");
	       	} 
   		});
   		jQuery(selectPurchasePlanGrid).jqGrid('bindKeys');
   		jQuery("#selectPurchasePlan_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#selectPurchasePlan_search_dept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	    jQuery("#selectPurchasePlan_gridtable_confirm").unbind( 'click' ).bind("click",function(){
	    	var sid = jQuery("#selectPurchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				var url = "${ctx}/extendOrder?id="+sid+"&navTabId=order_gridtable";
				$.post(url,function(data) {
					if(data.statusCode==200){
						$.pdialog.closeCurrent();
						/* $.pdialog.open("${ctx}/editOrder?popup=true&orderId="+data.orderId,'editOrder','订单明细', {mask:true,width : 960,height : 628}); */
					}
					formCallBack(data);
				});
			}
		});
  	});
	function showSelectPurchasePlan(url){
		$.pdialog.open(url,'editSelectPurchasePlan','采购计划单明细', {mask:true,width : 960,height : 628});
	}
</script>

<div class="page">
<div id="selectPurchasePlan_container">
	<div id="selectPurchasePlan_layout-center" class="pane ui-layout-center">
	 <div id="selectPurchasePlan_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="selectPurchasePlan_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.dept'/>:
						<input type="hidden" id="selectPurchasePlan_search_dept_id">
					 	<input type="text" id="selectPurchasePlan_search_dept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.store'/>:
						<input type="hidden" id="selectPurchasePlan_search_store_id">
					 	<input type="text" id="selectPurchasePlan_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.checkDate'/>:
						<input type="text"	id="selectPurchasePlan_search_check_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'selectPurchasePlan_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="selectPurchasePlan_search_check_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'selectPurchasePlan_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="selectPurchasePlanGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
				</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="selectPurchasePlanGridReload()"><s:text name='button.search'/></button>
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
				<li>
					<a id="selectPurchasePlan_gridtable_confirm" class="confirmbutton"  href="javaScript:"><span><s:text name="确认" /></span></a>
				</li>
			</ul>
		</div>
		<div id="selectPurchasePlan_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:purchaseId;width:960;height:628">
			<input type="hidden" id="selectPurchasePlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_selectPurchasePlan_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="selectPurchasePlan_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="selectPurchasePlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="selectPurchasePlan_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="selectPurchasePlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>
</div>
</div>