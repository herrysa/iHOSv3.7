
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 var width = 960,height = 638;
	function deptNeedPlanGridReload(){
		var urlString = "deptNeedPlanGridList";
		var storeId = jQuery("#deptNeedPlan_search_store_id").val();
		var deptId = jQuery("#deptNeedPlan_search_dept_id").val();
		var makeDate_from = jQuery("#deptNeedPlan_search_make_date_from").val();
		var makeDate_to = jQuery("#deptNeedPlan_search_make_date_to").val();
		var checkDate_from = jQuery("#deptNeedPlan_search_check_date_from").val();
		var checkDate_to = jQuery("#deptNeedPlan_search_check_date_to").val();
		var planType = jQuery("#deptNeedPlan_search_planType").val();
		var state = jQuery("#deptNeedPlan_search_state").val();
		
		urlString = urlString
			+ "?filter_GED_makeDate=" + makeDate_from+ "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			+ "&filter_EQS_store.id=" + storeId+ "&filter_EQS_dept.departmentId=" + deptId
			+ "&filter_EQS_planType=" + planType+ "&filter_EQS_state=" + state;
		urlString=encodeURI(urlString);
		jQuery("#deptNeedPlan_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptNeedPlanGridIdString="#deptNeedPlan_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var deptNeedPlanGrid = jQuery(deptNeedPlanGridIdString);
	    deptNeedPlanGrid.jqGrid({
	    	url : "deptNeedPlanGridList?filter_GED_makeDate="+jQuery("#deptNeedPlan_search_make_date_from").val()+"&filter_LED_makeDate=${currentSystemVariable.businessDate}",
	    	editurl:"deptNeedPlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'needId',index:'needId',align:'center',label : '<s:text name="deptNeedPlan.needId" />',hidden:true,key:true},				
				{name:'needNo',index:'needNo',align:'left',width : 130,label : '<s:text name="deptNeedPlan.needNo" />',hidden:false,highsearch:true},				
				/* {name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="deptNeedPlan.orgCode" />',hidden:true},				
				{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="deptNeedPlan.copyCode" />',hidden:true},				
				{name:'periodMonth',index:'periodMonth',align:'center',label : '<s:text name="deptNeedPlan.periodMonth" />',hidden:true},				
				 */{name:'planType',index:'planType',align:'left',width : 100,label : '<s:text name="deptNeedPlan.planType" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:月计划;2:追加计划'}},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="deptNeedPlan.store" />',hidden:false,highsearch:true},				
				{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="deptNeedPlan.dept" />',hidden:false,highsearch:true},				
				{name:'makePerson.name',index:'makePerson.name',align:'left',width : 60,label : '<s:text name="deptNeedPlan.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width : 100,label : '<s:text name="deptNeedPlan.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width : 60,label : '<s:text name="deptNeedPlan.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="deptNeedPlan.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 120,label : '<s:text name="deptNeedPlan.remark" />',hidden:false,highsearch:true},				
				{name:'state',index:'state',align:'center',width : 100,label : '<s:text name="deptNeedPlan.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已完成;3:已中止;4:已作废'}},				
	        ],
	        jsonReader : {
				root : "deptNeedPlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'needNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="deptNeedPlanList.title" />',
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
	              	  if(ret[i]['state']=="0"){
	              		  setCellText(this,id,'state','<span >新建</span>');
	              	  }else if(ret[i]['state']=="1"){
	              		  setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['state']=="2"){
	              		  setCellText(this,id,'state','<span style="color:blue" >已完成</span>');
	              	  }else if(ret[i]['state']=="3"){
	              		  setCellText(this,id,'state','<span style="color:red" >已中止</span>');
	              	  }else if(ret[i]['state']=="4"){
	              		  setCellText(this,id,'state','<span style="color:#888888" >已作废</span>');
	              	  }
	              	  editUrl = "'${ctx}/editDeptNeedPlan?needId="+ret[i]["needId"]+"'";
	   	        	  setCellText(this,id,'needNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showDeptNeedPlan('+editUrl+');">'+ret[i]["needNo"]+'</a>');
	              }
	            }
	           var dataTest = {"id":"deptNeedPlan_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("deptNeedPlan_gridtable");
	      	 initFlag = initColumn('deptNeedPlan_gridtable','com.huge.ihos.material.deptplan.model.DeptNeedPlan',initFlag);
	       	} 
	    });
   		jQuery(deptNeedPlanGrid).jqGrid('bindKeys');
   		jQuery("#deptNeedPlan_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#deptNeedPlan_search_dept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	    jQuery("#deptNeedPlan_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/deptNeedPlanGridEdit?oper=del"
			var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='0'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
	    jQuery("#deptNeedPlan_gridtable_check").unbind( 'click' ).bind("click",function(){
	    	var url = "${ctx}/deptNeedPlanGridEdit?oper=check"
				var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
				if (sid == null || sid.length == 0) {
					alertMsg.error("请选择记录。");
					return;
				} else {
					for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
						
						if(row['state']!='0'){
							alertMsg.error("只能审核处于新建状态的记录!");
							return;
						}
					}
					url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
					alertMsg.confirm("确认审核？", {
						okCall : function() {
							$.post(url,function(data) {
								formCallBack(data);
							});
						}
					});
				}
	    });
	    jQuery("#deptNeedPlan_gridtable_cancelCheck").unbind( 'click' ).bind("click",function(){
 			var url = "${ctx}/deptNeedPlanGridEdit?oper=cancelCheck"
 				var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
 				if (sid == null || sid.length == 0) {
 					alertMsg.error("请选择记录。");
 					return;
 				} else {
 					for(var i = 0;i < sid.length; i++){
 						var rowId = sid[i];
 						var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
 						
 						if(row['state']!='1'){
 							alertMsg.error("只有已审核的记录才能够被销审!");
 							return;
 						}
 					}
 					url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
 					alertMsg.confirm("确认销审？", {
 						okCall : function() {
 							$.post(url,function(data) {
 								formCallBack(data);
 							});
 						}
 					});
 				}
	    });
	    jQuery("#deptNeedPlan_gridtable_stop").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/deptNeedPlanGridEdit?oper=stop"
			var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
					if(row['state']=='3'){
						alertMsg.error("您选择的科室需求计划单已中止!");
						return;
					}else if(row['state']=='4'){
						alertMsg.error("您选择的科室需求计划单已作废!");
						return;
					}else if(row['state']!='0'){
						alertMsg.error("只能中止处于新建状态记录!");
						return;
					}
				}
				 url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
				alertMsg.confirm("确认中止计划？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				}); 
			}
		});
	    jQuery("#deptNeedPlan_gridtable_copy_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/deptNeedPlanGridEdit?oper=copy"
			var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else{
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']=='1'||row['state']=='2'){						
					}else{
						alertMsg.error("只能复制处于已审核或者已完成状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
				alertMsg.confirm("确认复制？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
	    jQuery("#deptNeedPlan_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
			var url = "editDeptNeedPlan";
			$.pdialog.open(url,'addDeptNeedPlan','添加科室需求计划单', {mask:true,width : width,height : height});
		});
		 jQuery("#deptNeedPlan_gridtable_abandon").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/deptNeedPlanGridEdit?oper=abandon"
			var sid = jQuery("#deptNeedPlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#deptNeedPlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']=='4'){
						alertMsg.error("您选择的科室需求计划单已作废!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=deptNeedPlan_gridtable";
				alertMsg.confirm("确认作废计划？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
  	});
	function showDeptNeedPlan(url){
		$.pdialog.open(url,'editDeptNeedPlan','科室需求计划单明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
<div id="deptNeedPlan_container">
	<div id="deptNeedPlan_layout-center" class="pane ui-layout-center">
		<div id="deptNeedPlan_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="deptNeedPlan_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.store'/>:
						<input type="hidden" id="deptNeedPlan_search_store_id">
					 	<input type="text" id="deptNeedPlan_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.dept'/>:
						<input type="hidden" id="deptNeedPlan_search_dept_id">
					 	<input type="text" id="deptNeedPlan_search_dept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.makeDate'/>:
						<input type="text"	id="deptNeedPlan_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptNeedPlan_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="deptNeedPlan_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptNeedPlan_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.checkDate'/>:
						<input type="text"	id="deptNeedPlan_search_check_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptNeedPlan_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="deptNeedPlan_search_check_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptNeedPlan_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.planType'/>:
						<s:select id="deptNeedPlan_search_planType"  list="#{'':'--','1':'月计划','2':'追加计划'}" style="width:80px" ></s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.state'/>:
						<s:select id="deptNeedPlan_search_state"  list="#{'':'--','0':'新建','1':'已审核','2':'已完成','3':'已中止'}" style="width:80px" ></s:select>
					</label>&nbsp;&nbsp;
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='deptNeedPlan.isDiff'/>:
					 	<select id="deptNeedPlan_search_isDiff">
					 		<option value="">--</option>
					 		<option value="1">是</option>
					 		<option value="0">否</option>
					 	</select>
					</label>&nbsp;&nbsp; --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="deptNeedPlanGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptNeedPlanGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div> --%>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="deptNeedPlan_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_copy_custom" class="savebutton"  href="javaScript:"><span><s:text name="button.copy" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_abandon" class="disablebutton"  href="javaScript:" style="display: none"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a id="deptNeedPlan_gridtable_stop" class="confirmbutton"  href="javaScript:"><span><s:text name="中止计划" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('deptNeedPlan_gridtable','com.huge.ihos.material.deptplan.model.DeptNeedPlan')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
			</div>
			<div id="deptNeedPlan_gridtable_div" layoutH="120"
				style="margin-left: 2px; margin-top: 2px; overflow: hidden"
				buttonBar="optId:needId;width:960;height:628">
				<input type="hidden" id="deptNeedPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
				<label style="display: none" id="deptNeedPlan_gridtable_addTile">
					<s:text name="deptNeedPlanNew.title"/>
				</label> 
				<label style="display: none"
					id="deptNeedPlan_gridtable_editTile">
					<s:text name="deptNeedPlanEdit.title"/>
				</label>
				<label style="display: none"
					id="deptNeedPlan_gridtable_selectNone">
					<s:text name='list.selectNone'/>
				</label>
				<label style="display: none"
					id="deptNeedPlan_gridtable_selectMore">
					<s:text name='list.selectMore'/>
				</label>
				<div id="load_deptNeedPlan_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
	 			<table id="deptNeedPlan_gridtable"></table>
				<div id="deptNeedPlanPager"></div>
			</div>
		</div>
		<div class="panelBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="deptNeedPlan_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptNeedPlan_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
	
			<div id="deptNeedPlan_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>
</div>