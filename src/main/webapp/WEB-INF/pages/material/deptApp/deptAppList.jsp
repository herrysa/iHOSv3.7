
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function deptAppGridReload(){
		var urlString = "deptAppGridList?filter_INS_appState=0,1,2";
		var appDeptId = jQuery("#deptApp_search_appDept_id").val();
		var storeId = jQuery("#deptApp_search_store_id").val();
		var appDateFrom = jQuery("#deptApp_search_app_date_from").val();
		var appDateTo = jQuery("#deptApp_search_app_date_to").val();
		var checkDateFrom = jQuery("#deptApp_search_check_date_from").val();
		var checkDateTo = jQuery("#deptApp_search_check_date_to").val();
		var appState = jQuery("#deptApp_search_appState").val();
	
		urlString=urlString+"&filter_EQS_store.id="+storeId+"&filter_EQS_appDept.departmentId="+appDeptId
				+"&filter_GED_appDate="+appDateFrom+"&filter_LED_appDate="+appDateTo
				+"&filter_GED_checkDate="+checkDateFrom+"&filter_LED_checkDate="+checkDateTo+"&filter_EQS_appState="+appState;
		urlString=encodeURI(urlString);
		jQuery("#deptApp_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptAppGridIdString="#deptApp_gridtable";
	var width = 960,height = 628;
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var deptAppGrid = jQuery(deptAppGridIdString);
   		deptAppGrid.jqGrid({
	    	url : "deptAppGridList?filter_INS_appState=0,1,2&filter_GED_appDate="+jQuery("#deptApp_search_app_date_from").val()+"&filter_LED_appDate=${currentSystemVariable.businessDate}",
	    	editurl:"deptAppGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'deptAppId',index:'deptAppId',align:'center',label : '<s:text name="deptApp.deptAppId" />',hidden:true,key:true},				
				{name:'appNo',index:'appNo',align:'left',width : 120,label : '<s:text name="deptApp.appNo" />',hidden:false,highsearch:true},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="deptApp.store" />',hidden:false,highsearch:true},				
				{name:'appDept.name',index:'appDept.name',align:'left',width : 100,label : '<s:text name="deptApp.appDept" />',hidden:false,highsearch:true},				
				{name:'appPerson.name',index:'appPerson.name',align:'left',width : 60,label : '<s:text name="deptApp.appPerson" />',hidden:false,highsearch:true},				
				{name:'appDate',index:'appDate',align:'center',width : 100,label : '<s:text name="deptApp.appDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'appChecker.name',index:'appChecker.name',align:'left',width : 60,label : '<s:text name="deptApp.appChecker" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'appDate',align:'center',width : 100,label : '<s:text name="deptApp.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="deptApp.remark" />',hidden:false,highsearch:true},			
				{name:'appState',index:'appState',align:'center',width : 100,label : '<s:text name="deptApp.appState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已作废'}}				
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
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
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
	              	  if(ret[i]['appState']=="0"){
	              		  setCellText(this,id,'appState','<span >新建</span>');
	              	  }else if(ret[i]['appState']=="1"){
	              		  setCellText(this,id,'appState','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['appState']=="2"){
	              		  setCellText(this,id,'appState','<span style="color:#888888" >已作废</span>');
	              	  }
	              	  editUrl = "'${ctx}/editDeptApp?deptAppId="+ret[i]["deptAppId"]+"'";
	   	        	  setCellText(this,id,'appNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showDeptApp('+editUrl+');">' +ret[i]["appNo"]+'</a>');
	   	        	 
	              }
	            }
	           var dataTest = {"id":"deptApp_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("deptApp_gridtable");
	      	 initFlag = initColumn('deptApp_gridtable','com.huge.ihos.material.deptapp.model.DeptApp',initFlag);
	       	} 

   		});
   		jQuery(deptAppGrid).jqGrid('bindKeys');
   		
   		//--------------------------------------------------------pageHeader initData
   		jQuery("#deptApp_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#deptApp_search_appDept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
  	});
	//-----------------------------appNo link function
	function showDeptApp(url){
		$.pdialog.open(url,'editDeptApp','科室申领单明细', {mask:true,width : width,height : height});
	}
    //---------------------------------------------del check cancelCheck send
	function deptAppListEditOper(type){
    	var msg = "";
    	switch(type){
	    	case "del":
	    		msg = "删除";break;
	    	case "check":
	    		msg = "审核";break;
	    	case "cancelCheck":
	    		msg = "销审";break;
	    	case "send":
	    		msg = "发送";break;
	    	case "abandon":
	    		msg = "作废";break;
	    	case "copy":
	    		msg = "复制";break;
    	}
		var url = "${ctx}/deptAppGridEdit?oper="+type
		var sid = jQuery("#deptApp_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#deptApp_gridtable").jqGrid('getRowData',rowId);
				if(type=="del"){
					if(row['appState']!='0'){
						alertMsg.error("只能删除处于  [新建] 状态的记录!");
						return;
					}
				}else if(type=="check"){
					if(row['appState']!='0'){
						alertMsg.error("只能审核处于  [新建] 状态的记录!");
						return;
					}
				}else if(type=="cancelCheck"){
					if(row['appState']!='1'){
						alertMsg.error("只有销审处于  [已审核] 状态的记录!");
						return;
					}
				}else if(type=="send"){
					if(row['appState']!='1'){
						alertMsg.error("只能发送处于  [已审核] 状态的记录!");
						return;
					}
				}else if(type=="copy"){
					if(row['appState']!='1'){
						alertMsg.error("只能复制处于  [已审核] 状态的记录!");
						return;
					}
				}else if(type=="abandon"){
					if(row['appState']==2){
						alertMsg.error("您选择的科室申领已作废!");
						return;
					}else if(row['appState']!='0'){
						alertMsg.error("只能作废处于  [新建] 状态的记录!");
						return;
					}
				}
			}
			url = url+"&id="+sid+"&navTabId=deptApp_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认"+msg+"？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
    function importDeptAppHis() {
    	$.pdialog.open('${ctx}/deptAppHisList','deptAppHis','科室申领单历史数据', {mask:true,width : width,height : height});
    }
</script>

<div class="page">
<div id="deptApp_container">
	<div id="deptApp_layout-center" class="pane ui-layout-center">
		<div id="deptApp_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="deptApp_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.store'/>:
							<input type="hidden" id="deptApp_search_store_id">
						 	<input type="text" id="deptApp_search_store" >
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appDept'/>:
							<input type="hidden" id="deptApp_search_appDept_id">
						 	<input type="text" id="deptApp_search_appDept" >
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appDate'/>:
							<input type="text"	id="deptApp_search_app_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptApp_search_app_date_to\')}'})">
							<s:text name='至'/>
						 	<input type="text"	id="deptApp_search_app_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptApp_search_app_date_from\')}'})">
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.checkDate'/>:
							<input type="text"	id="deptApp_search_check_date_from" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptApp_search_check_date_to\')}'})">
							<s:text name='至'/>
						 	<input type="text"	id="deptApp_search_check_date_to" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptApp_search_check_date_from\')}'})">
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appState'/>:
							<s:select id="deptApp_search_appState"  list="#{'':'--','0':'新建','1':'已审核','2':'已作废'}" style="width:70px" ></s:select>
						</label>&nbsp;&nbsp;
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="deptAppGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptAppGridReload()"><s:text name='button.search'/></button>
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
					<a id="deptApp_gridtable_add" class="addbutton" href="javaScript:" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a class="savebutton"  href="javaScript:deptAppListEditOper('copy')"><span><s:text name="button.copy" /></span></a>
				</li>
				<li>
					<a class="inheritbutton"  href="javaScript:importDeptAppHis()"><span><s:text name="历史引入" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:deptAppListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:deptAppListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:deptAppListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:deptAppListEditOper('send')"><span><s:text name="发送" /></span></a>
				</li>
				<li>
					<a class="disablebutton"  href="javaScript:deptAppListEditOper('abandon')"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('deptApp_gridtable','com.huge.ihos.material.deptapp.model.DeptApp')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div id="deptApp_gridtable_div" layoutH="146" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:deptAppId;width:960;height:628">
			<input type="hidden" id="deptApp_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptApp_gridtable_addTile">
				<s:text name="deptAppNew.title"/>
			</label> 
			<label style="display: none"
				id="deptApp_gridtable_editTile">
				<s:text name="deptAppEdit.title"/>
			</label>
			<label style="display: none"
				id="deptApp_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptApp_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_deptApp_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="deptApp_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptApp_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptApp_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="deptApp_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
</div>
</div>