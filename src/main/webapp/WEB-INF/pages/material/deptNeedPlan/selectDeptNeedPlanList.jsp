
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function selectDeptNeedPlanGridReload(){
		var urlString = "deptNeedPlanGridList?filter_EQS_state=1";
		if("${needDeptId}"!=""){
			urlString += "&filter_EQS_dept.departmentId=${needDeptId}";
    	}else{
			var deptId = jQuery("#${random}_selectDeptNeedPlan_search_dept_id").val();
			urlString += "&filter_EQS_dept.departmentId="+deptId;
    	}
		//var makeDate_from = jQuery("#selectDeptNeedPlan_search_make_date_from").val();
		//var makeDate_to = jQuery("#selectDeptNeedPlan_search_make_date_to").val();
		var checkDate_from = jQuery("#${random}_selectDeptNeedPlan_search_check_date_from").val();
		var checkDate_to = jQuery("#${random}_selectDeptNeedPlan_search_check_date_to").val();
		var planType = jQuery("#${random}_selectDeptNeedPlan_search_planType").val();
		
		urlString = urlString
			//+ "&filter_GED_makeDate=" + makeDate_from+ "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			//+ "&filter_EQS_dept.departmentId=" + deptId
			+ "&filter_EQS_planType=" + planType;
		urlString=encodeURI(urlString);
		jQuery("#${random}_selectDeptNeedPlan_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var selectDeptNeedPlanGridIdString="#${random}_selectDeptNeedPlan_gridtable";
	jQuery(document).ready(function() { 
		var gridUrl = "deptNeedPlanGridList?filter_EQS_state=1&filter_EQS_store.id=${storeId}&filter_GED_checkDate="+jQuery("#${random}_selectDeptNeedPlan_search_check_date_from").val()+"&filter_LED_checkDate=${currentSystemVariable.businessDate}";
    	if("${needDeptId}"!=""){
    		gridUrl += "&filter_EQS_dept.departmentId=${needDeptId}";
    	}
		var selectDeptNeedPlanGrid = jQuery(selectDeptNeedPlanGridIdString);
		selectDeptNeedPlanGrid.jqGrid({
	    	url : gridUrl,
	    	editurl:"deptNeedPlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'needId',index:'needId',align:'center',label : '<s:text name="deptNeedPlan.needId" />',hidden:true,key:true},				
				{name:'needNo',index:'needNo',align:'left',width : 120,label : '<s:text name="deptNeedPlan.needNo" />',hidden:false},				
				{name:'planType',index:'planType',align:'left',width : 100,label : '<s:text name="deptNeedPlan.planType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:月计划;2:追加计划'}},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="deptNeedPlan.store" />',hidden:false},				
				{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="deptNeedPlan.dept" />',hidden:false},				
				//{name:'makePerson.name',index:'makePerson.name',align:'left',width : 60,label : '<s:text name="deptNeedPlan.makePerson" />',hidden:false},				
				//{name:'makeDate',index:'makeDate',align:'center',width : 100,label : '<s:text name="deptNeedPlan.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width : 60,label : '<s:text name="deptNeedPlan.checkPerson" />',hidden:false},				
				{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="deptNeedPlan.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="deptNeedPlan.remark" />',hidden:false}				
				//{name:'state',index:'state',align:'center',width : 100,label : '<s:text name="deptNeedPlan.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;4:已中止'}},				
	        ],
	        jsonReader : {
				root : "deptNeedPlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'needId',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="deptNeedPlanList.title" />',
	        height:300,
	        rowNum:500,
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
	              	  editUrl = "'${ctx}/editDeptNeedPlan?needId="+ret[i]["needId"]+"&docPreview=refer'";
	   	        	  setCellText(this,id,'needNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showSelectDeptNeedPlan('+editUrl+');">'+ret[i]["needNo"]+'</a>');
	              }
	            }
	           var dataTest = {"id":"${random}_selectDeptNeedPlan_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("${random}_selectDeptNeedPlan_gridtable");
	       	} 
	    });
   		jQuery(selectDeptNeedPlanGrid).jqGrid('bindKeys');
	    jQuery("#${random}_selectDeptNeedPlan_search_dept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	    jQuery("#${random}_selectDeptNeedPlan_gridtable_confirm").unbind( 'click' ).bind("click",function(){
	    	var sid = jQuery("#${random}_selectDeptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				if("${needDeptId}"!=""){
		    		if(sid.length != 1 ){
		    			alertMsg.error("只能选择一条记录。");
						return;
		    		}else{
		    			var colMap = new Map();
		    			colMap.put("invId","invDict.invId");
		    			colMap.put("invCode","invDict.invCode");
		    			colMap.put("invName","invDict.invName");
		    			colMap.put("invModel","invDict.invModel");
		    			colMap.put("firstUnit","invDict.firstUnit");
		    			colMap.put("factory","invDict.factory");
		    			colMap.put("appPrice","appPrice");
		    			colMap.put("appAmount","appAmount");
		    			colMap.put("appMoney","appMoney");
		    			var rowDatas = new Array();
		    			var row = jQuery("#${random}_selectDeptNeedPlan_gridtable").jqGrid('getRowData',sid[0]);
		    			jQuery.ajax({
						    url: "deptNeedPlanDetailGridList?filter_EQS_deptNeedPlan.needId="+row['needId'],
						    type: 'get',
						    dataType: 'json',
						    async:false,
						    error: function(data){
						    },
						    success: function(data){
						    	var deptNeedPlanDetails = data.deptNeedPlanDetails;
						    	var deptNeedPlanDetail,rowData;
						    	for(var i=0,len = deptNeedPlanDetails.length;i<len;i++){
						    		deptNeedPlanDetail = deptNeedPlanDetails[i];
						    		var invDict = deptNeedPlanDetail['invDict'];
						    		rowData = new Object();
						    		rowData.invId=invDict['invId'];
						    		rowData.invCode=invDict['invCode'];
						    		rowData.invName=invDict['invName'];
						    		rowData.invModel=invDict['invModel'];
						    		rowData.firstUnit=invDict['firstUnit'];
						    		rowData.factory=invDict['factory'];
						    		var amount = deptNeedPlanDetail['amount']
						    		var price = deptNeedPlanDetail['price']
						    		rowData.appPrice=price;
						    		rowData.appAmount=amount;
						    		amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
									price = isNaN(parseFloat(price))?0:parseFloat(price);
						    		rowData.appMoney=amount*price;
						    		rowDatas.push(rowData);
						    	}
						    }
						});
		    			jQuery("#${random}_deptAppTable").addLocalData(rowDatas,colMap);
		    			$.pdialog.closeCurrent();
		    		}
		    	}else{
		    		var url = "${ctx}/createPurchasePlanByNeed?id="+sid+"&navTabId=purchasePlan_gridtable";
					$.post(url,function(data) {
						if(data.statusCode==200){
							$.pdialog.closeCurrent();
							$.pdialog.open('${ctx}/editPurchasePlan?popup=true&purchaseId='+data.purchaseId,'editPurchasePlan',"采购计划单明细", {mask:true,width : 960,height : 628,resizable:false});
						}
						formCallBack(data);
					});
		    	}
			}
		});
  	});
	function showSelectDeptNeedPlan(url){
		$.pdialog.open(url,'editDeptNeedPlan','科室需求计划单明细', {mask:true,width : 960,height : 628});
	}
</script>

<div class="page">
	<div id="${random}_selectDeptNeedPlan_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}_selectDeptNeedPlan_search_form" >
					<c:if test="${empty needDeptId}" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptNeedPlan.dept'/>:
							<input type="hidden" id="${random}_selectDeptNeedPlan_search_dept_id">
						 	<input type="text" id="${random}_selectDeptNeedPlan_search_dept" >
						</label>&nbsp;&nbsp;
					</c:if>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.makeDate'/>:
						<input type="text"	id="selectDeptNeedPlan_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="<fmt:formatDate value='${currentSystemVariable.periodBeginDate}' pattern='yyyy-MM-dd' />" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'selectDeptNeedPlan_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="selectDeptNeedPlan_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${currentSystemVariable.businessDate}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'selectDeptNeedPlan_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp; --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.checkDate'/>:
						<input type="text"	id="${random}_selectDeptNeedPlan_search_check_date_from" style="width:80px;height:15px" class="Wdate"  value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'${random}_selectDeptNeedPlan_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="${random}_selectDeptNeedPlan_search_check_date_to" style="width:80px;height:15px" class="Wdate"  value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'${random}_selectDeptNeedPlan_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.planType'/>:
						<select id="${random}_selectDeptNeedPlan_search_planType" style="width:80px">
							<option value="">--</option>
							<option value="1">月计划</option>
							<option value="2">追加计划</option>
						</select>
						<%-- <s:select   list="#{'':'--','1':'月计划','2':'追加计划'}"  ></s:select> --%>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="selectDeptNeedPlanGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="selectDeptNeedPlanGridReload()"><s:text name='button.search'/></button>
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
					<a id="${random}_selectDeptNeedPlan_gridtable_confirm" class="confirmbutton"  href="javaScript:"><span><s:text name="确认" /></span></a>
				</li>
			</ul>
		</div>
		<div id="${random}_selectDeptNeedPlan_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:needId;width:960;height:628">
			<input type="hidden" id="${random}_selectDeptNeedPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_${random}_selectDeptNeedPlan_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="${random}_selectDeptNeedPlan_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_selectDeptNeedPlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_selectDeptNeedPlan_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_selectDeptNeedPlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>