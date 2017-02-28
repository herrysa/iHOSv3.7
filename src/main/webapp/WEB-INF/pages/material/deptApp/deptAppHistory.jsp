
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function deptAppHisGridReload(){
		var urlString = "deptAppGridList?filter_NIS_appState=0,1,2";
		var appDeptId = jQuery("#${random}_deptAppHis_search_appDept_id").val();
		var storeId = jQuery("#${random}_deptAppHis_search_store_id").val();
		var sendDateFrom = jQuery("#${random}_deptAppHis_search_confirm_date_from").val();
		var sendDateTo = jQuery("#${random}_deptAppHis_search_confirm_date_to").val();
		var appState = jQuery("#${random}_deptAppHis_search_appState").val();
	
		urlString=urlString+"&filter_EQS_store.id="+storeId+"&filter_EQS_appDept.departmentId="+appDeptId
				+"&filter_GED_confirmDate="+sendDateFrom+"&filter_LED_confirmDate="+sendDateTo
				+"&filter_EQS_appState="+appState;
		urlString=encodeURI(urlString);
		jQuery("#${random}_deptAppHis_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptAppHisGridIdString="#${random}_deptAppHis_gridtable";
	jQuery(document).ready(function() {
		var multiSelect = false;
		if("${docPreview}"==null || "${docPreview}"==""){
			multiSelect = true;
		}
		var deptAppHisGrid = jQuery(deptAppHisGridIdString);
   		deptAppHisGrid.jqGrid({
	    	url : "deptAppGridList?filter_NIS_appState=0,1,2&filter_GED_confirmDate="+jQuery("#${random}_deptAppHis_search_confirm_date_from").val()+"&filter_LED_confirmDate=${currentSystemVariable.businessDate}",
	    	editurl:"deptAppGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'deptAppId',index:'deptAppId',align:'center',label : '<s:text name="deptApp.deptAppId" />',hidden:true,key:true},				
				{name:'appNo',index:'appNo',align:'left',width : 120,label : '<s:text name="deptApp.appNo" />',hidden:false,highsearch:true},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="deptApp.store" />',hidden:false,highsearch:true},				
				{name:'appDept.name',index:'appDept.name',align:'left',width : 100,label : '<s:text name="deptApp.appDept" />',hidden:false,highsearch:true},				
				{name:'appPerson.name',index:'appPerson.name',align:'left',width : 60,label : '<s:text name="deptApp.appPerson" />',hidden:false,highsearch:true},				
				//{name:'appDate',index:'appDate',align:'center',width : 100,label : '<s:text name="deptApp.appDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				//{name:'appChecker.name',index:'appChecker.name',align:'left',width : 60,label : '<s:text name="deptApp.appChecker" />',hidden:false,highsearch:true},				
				//{name:'checkDate',index:'appDate',align:'center',width : 100,label : '<s:text name="deptApp.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmDate',index:'confirmDate',align:'center',width : 100,label : '<s:text name="deptApp.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'storeChecker.name',index:'storeChecker.name',align:'left',width : 86,label : '<s:text name="deptApp.storeChecker" />',hidden:false,highsearch:true},				
				{name:'storeCheckDate',index:'storeCheckDate',align:'center',width : 100,label : '<s:text name="deptApp.storeCheckDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="deptApp.remark" />',hidden:false,highsearch:true},			
				{name:'appState',index:'appState',align:'center',width : 140,label : '<s:text name="deptApp.appState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '3:未处理;5:通过;6:部分通过-其余待处理;7:部分通过-其余未通过;8:未通过'}}				
	        ],
	        jsonReader : {
				root : "deptApps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'appNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: multiSelect,
			multiboxonly:multiSelect,
			shrinkToFit:true,
			autowidth:false,
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
	              	if(ret[i]['appState']=="3"){
	              		  setCellText(this,id,'appState','<span >未处理</span>');
	              	  }else if(ret[i]['appState']=="5"){
	              		  setCellText(this,id,'appState','<span style="color:blue" >通过</span>');
	              	  }else if(ret[i]['appState']=="6"){
	              		  setCellText(this,id,'appState','<span style="color:green" >部分通过-其余待处理</span>');
	              	  }else if(ret[i]['appState']=="7"){
	              		  setCellText(this,id,'appState','<span style="color:orange" >部分通过-其余未通过</span>');
	              	  }else if(ret[i]['appState']=="8"){
	              		  setCellText(this,id,'appState','<span style="color:red" >未通过</span>');
	              	  }
	              	  editUrl = "'${ctx}/editDeptApp?deptAppId="+ret[i]["deptAppId"]+"&docPreview=refer'";
	   	        	  setCellText(this,id,'appNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showDeptAppHis('+editUrl+');">' +ret[i]["appNo"]+'</a>');
	   	        	 
	              }
	            }
	           var dataTest = {"id":"${random}_deptAppHis_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("${random}_deptAppHis_gridtable");
	       	} 
   		});
   		jQuery(deptAppHisGrid).jqGrid('bindKeys');
   		
   		//--------------------------------------------------------pageHeader initData
   		jQuery("#${random}_deptAppHis_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#${random}_deptAppHis_search_appDept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	    jQuery("#${random}_deptAppHis_gridtable_comfirm").unbind( 'click' ).bind("click",function(){
			var sid = jQuery("#${random}_deptAppHis_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				var url = "${ctx}/deptAppGridEdit?oper=import&id="+sid+"&navTabId=deptApp_gridtable";
				url=encodeURI(url);
				alertMsg.confirm("确认引入？", {
					okCall : function() {
						$.post(url,function(data) {
							$.pdialog.closeCurrent();
							formCallBack(data);
						});
					}
				});
			}    	
	    });
  	});
	//-----------------------------appNo link function
	function showDeptAppHis(url){
		$.pdialog.open(url,'editDeptApp','科室申领单明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
	<div id="${random}_deptAppHis_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}_deptAppHis_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptApp.store'/>:
						<input type="hidden" id="${random}_deptAppHis_search_store_id">
					 	<input type="text" id="${random}_deptAppHis_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptApp.appDept'/>:
						<input type="hidden" id="${random}_deptAppHis_search_appDept_id">
					 	<input type="text" id="${random}_deptAppHis_search_appDept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptApp.confirmDate'/>:
						<input type="text"	id="${random}_deptAppHis_search_confirm_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'${random}_deptAppHis_search_confirm_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="${random}_deptAppHis_search_confirm_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'${random}_deptAppHis_search_confirm_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptApp.appState'/>:
						<select id="${random}_deptAppHis_search_appState" style="width:148px">
							<option value="">--</option>
							<option value="3">未处理</option>
							<option value="5">通过</option>
							<option value="6">部分通过-其余待处理</option>
							<option value="7">部分通过-其余未通过</option>
							<option value="8">未通过</option>
						</select>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="deptAppHisGridReload()"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="deptAppHisGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<c:if test="${empty docPreview}" >
			<div class="panelBar">
				<ul class="toolBar">
					<li>
						<a id="${random}_deptAppHis_gridtable_comfirm" class="confirmbutton" href="javaScript:" ><span><s:text name="引入" /></span></a>
					</li>
				</ul>
			</div>
		</c:if>
		<div id="${random}_deptAppHis_gridtable_div" layoutH="117" style="margin-left: 2px; margin-top: 0px; overflow: hidden"
			buttonBar="optId:deptAppId;width:960;height:628">
			<input type="hidden" id="${random}_deptAppHis_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_${random}_deptAppHis_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="${random}_deptAppHis_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_deptAppHis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_deptAppHis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_deptAppHis_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>