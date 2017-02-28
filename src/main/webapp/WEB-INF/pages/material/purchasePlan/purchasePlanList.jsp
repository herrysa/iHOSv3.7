
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	function purchasePlanGridReload(){
		var urlString = "purchasePlanGridList";
		var storeId = jQuery("#purchasePlan_search_store_id").val();
		var deptId = jQuery("#purchasePlan_search_dept_id").val();
		var makeDate_from = jQuery("#purchasePlan_search_make_date_from").val();
		var makeDate_to = jQuery("#purchasePlan_search_make_date_to").val();
		var checkDate_from = jQuery("#purchasePlan_search_check_date_from").val();
		var checkDate_to = jQuery("#purchasePlan_search_check_date_to").val();
		var state = jQuery("#purchasePlan_search_state").val();
		
		urlString = urlString
			+ "?filter_GED_makeDate=" + makeDate_from+ "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			+ "&filter_EQS_store.id=" + storeId+ "&filter_EQS_dept.departmentId=" + deptId
			+ "&filter_EQS_state=" + state;
		urlString=encodeURI(urlString);
		jQuery("#purchasePlan_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var purchasePlanGridIdString="#purchasePlan_gridtable";
	var purchasePlanFormWidth = 960,purchasePlanFormHeight=628;
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var purchasePlanGrid = jQuery(purchasePlanGridIdString);
	    purchasePlanGrid.jqGrid({
	    	url : "purchasePlanGridList?filter_GED_makeDate="+jQuery("#purchasePlan_search_make_date_from").val()+"&filter_LED_makeDate=${currentSystemVariable.businessDate}",
	    	editurl:"purchasePlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'purchaseId',index:'purchaseId',align:'center',label : '<s:text name="purchasePlan.purchaseId" />',hidden:true,key:true},				
				{name:'purchaseNo',index:'purchaseNo',align:'left',width : 120,label : '<s:text name="purchasePlan.purchaseNo" />',hidden:false,highsearch:true},				
				{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="purchasePlan.dept" />',hidden:false,highsearch:true},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="purchasePlan.store" />',hidden:false,highsearch:true},				
				{name:'makePerson.name',index:'makePerson.name',align:'left',width : 60,label : '<s:text name="purchasePlan.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width : 100,label : '<s:text name="purchasePlan.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width : 60,label : '<s:text name="purchasePlan.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="purchasePlan.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="purchasePlan.remark" />',hidden:false,highsearch:true},				
				{name:'state',index:'state',align:'center',width : 100,label : '<s:text name="purchasePlan.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已完成;3:已中止;4:已作废'}},
				{name:'orderNo',index:'orderNo',align:'left',width : 220,label : '<s:text name="purchasePlan.orderNo" />',hidden:false,highsearch:true}
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
	              	  editUrl = "'${ctx}/editPurchasePlan?purchaseId="+ret[i]["purchaseId"]+"'";
	   	        	  setCellText(this,id,'purchaseNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showPurchasePlan('+editUrl+');">'+ret[i]["purchaseNo"]+'</a>');
	   	        	
	   	        	  var ordernoarr = ret[i]["orderNo"].split(',');
	   	        	  var ordernotext="";	   
	   	        	  for(var index =0;index <ordernoarr.length;index++ ){
	   	        		if(ordernoarr[index]){
	   	        		editUrlorder = "'${ctx}/editOrder?orderNo="+ordernoarr[index]+"&docPreview=refer'";	
	   	        		ordernotext += '<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showOrder('+editUrlorder+');">'+ordernoarr[index]+'</a>&nbsp;';
		   	        	setCellText(this,id,'orderNo',ordernotext);
	   	        		}
	   	        	  }	   	        	  
	              }
	            }
	           var dataTest = {"id":"purchasePlan_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("purchasePlan_gridtable");
	      	 initFlag = initColumn('purchasePlan_gridtable','com.huge.ihos.material.purchaseplan.model.PurchasePlan',initFlag);
	       	} 
   		});
   		jQuery(purchasePlanGrid).jqGrid('bindKeys');
   		jQuery("#purchasePlan_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#purchasePlan_search_dept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	    jQuery("#purchasePlan_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
			var url = "editPurchasePlan";
			$.pdialog.open(url,'addPurchasePlan','添加采购计划单', {mask:true,width : purchasePlanFormWidth,height : purchasePlanFormHeight});
		});
	    jQuery("#purchasePlan_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/purchasePlanGridEdit?oper=del"
			var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='0'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
	    jQuery("#purchasePlan_gridtable_check").unbind( 'click' ).bind("click",function(){
	    	var url = "${ctx}/purchasePlanGridEdit?oper=check"
				var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
				if (sid == null || sid.length == 0) {
					alertMsg.error("请选择记录。");
					return;
				} else {
					for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
						
						if(row['state']!='0'){
							alertMsg.error("只能审核处于新建状态的记录!");
							return;
						}
					}
					url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
					alertMsg.confirm("确认审核？", {
						okCall : function() {
							$.post(url,function(data) {
								formCallBack(data);
							});
						}
					});
				}
	    });
	    jQuery("#purchasePlan_gridtable_cancelCheck").unbind( 'click' ).bind("click",function(){
 			var url = "${ctx}/purchasePlanGridEdit?oper=cancelCheck"
 				var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
 				if (sid == null || sid.length == 0) {
 					alertMsg.error("请选择记录。");
 					return;
 				} else {
 					for(var i = 0;i < sid.length; i++){
 						var rowId = sid[i];
 						var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
 						
 						if(row['state']=='0'){
 							alertMsg.error("只有已审核的记录才能被销审!");
 							return;
 						}
 					}
 					url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
 					alertMsg.confirm("确认销审？", {
 						okCall : function() {
 							$.post(url,function(data) {
 								formCallBack(data);
 							});
 						}
 					});
 				}
	    });
	    jQuery("#purchasePlan_gridtable_stop").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/purchasePlanGridEdit?oper=stop"
			var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']=='3'){
						alertMsg.error("您选择的记录已中止!");
						return;
					}else if(row['state']=='4'){
						alertMsg.error("您选择的记录已作废!");
						return;
					}else if(row['state']!='0'){
						alertMsg.error("只能中止处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
				alertMsg.confirm("确认中止计划？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
	    jQuery("#purchasePlan_gridtable_createByBase").unbind( 'click' ).bind("click",function(){
			var url = "redirectCreatePurchasePlanByBase";
			$.pdialog.open(url,'createPurchasePlanByBase','据基数生成', {mask:true,width : 400,height : 300,maxable:false,resizable:false});
		});
	    
	    jQuery("#purchasePlan_gridtable_copy_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/purchasePlanGridEdit?oper=copy"
			var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else{
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']=='1'||row['state']=='2'){						
					}else{
						alertMsg.error("只能复制处于已审核或者已完成状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
				alertMsg.confirm("确认复制？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		});
	    
	    jQuery("#purchasePlan_gridtable_abandon").unbind( 'click' ).bind("click",function(){
	    	var url = "${ctx}/purchasePlanGridEdit?oper=abandon"
    		var sid = jQuery("#purchasePlan_gridtable").jqGrid('getGridParam','selarrrow');
    		if (sid == null || sid.length == 0) {
    			alertMsg.error("请选择记录。");
    			return;
    		} else {
    			for(var i = 0;i < sid.length; i++){
    				var rowId = sid[i]; 
    				var row = jQuery("#purchasePlan_gridtable").jqGrid('getRowData',rowId);
    				if(row['state']=='4'){
    					alertMsg.error("您选择的记录已作废!");
    					return;
    				}
    			}
    			url = url+"&id="+sid+"&navTabId=purchasePlan_gridtable";
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
	function showPurchasePlan(url){
		$.pdialog.open(url,'editPurchasePlan','采购计划单明细', {mask:true,width : purchasePlanFormWidth,height : purchasePlanFormHeight});
	}
	function showOrder(url){
		$.pdialog.open(url,'editOrder','订单明细', {mask:true,width : purchasePlanFormWidth,height : purchasePlanFormHeight});
	}
 	/* function extendPurchasePlan(){ */
		/* var storeId = jQuery("#${random}_purchasePlan_store_id").val();
		if(storeId && storeId!=''){
			$.pdialog.open('${ctx}/selectDeptNeedPlanList?storeId='+storeId,'selectDeptNeedPlan',"选择需求计划", {mask:true,width : 960,height : 628,resizable:false});
		}else{
			alertMsg.error("请选择仓库");
			return;
		} */
	/* 	$.pdialog.open('${ctx}/selectDeptNeedPlanList?random=${random}','selectDeptNeedPlan',"选择需求计划", {mask:true,width : 960,height : 628,resizable:false});
	} */
</script>

<div class="page">
<div id="purchasePlan_container">
	<div id="purchasePlan_layout-center" class="pane ui-layout-center">
	 <div id="purchasePlan_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="purchasePlan_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.dept'/>:
						<input type="hidden" id="purchasePlan_search_dept_id">
					 	<input type="text" id="purchasePlan_search_dept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.store'/>:
						<input type="hidden" id="purchasePlan_search_store_id">
					 	<input type="text" id="purchasePlan_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.makeDate'/>:
						<input type="text"	id="purchasePlan_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'purchasePlan_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="purchasePlan_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'purchasePlan_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.checkDate'/>:
						<input type="text"	id="purchasePlan_search_check_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'purchasePlan_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="purchasePlan_search_check_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'purchasePlan_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='purchasePlan.state'/>:
						<s:select id="purchasePlan_search_state"  list="#{'':'--','0':'新建','1':'已审核','2':'已完成','3':'已中止'}" style="width:80px" ></s:select>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="purchasePlanGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
				</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="purchasePlanGridReload()"><s:text name='button.search'/></button>
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
					<a id="purchasePlan_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_copy_custom" class="savebutton"  href="javaScript:"><span><s:text name="button.copy" /></span></a>
				</li>
			<%-- 	<li>
					<a id="purchasePlan_gridtable_extend" class="deliverbutton" href="javaScript:extendPurchasePlan()" ><span><s:text name="根据需求生成" /></span></a>
				</li> --%>
				<li>
					<a id="purchasePlan_gridtable_createByBase" class="downloadbutton"  href="javaScript:"><span><s:text name="根据基数生成" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_abandon" class="disablebutton"  href="javaScript:"  style="display:none"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_stop" class="confirmbutton"  href="javaScript:"><span><s:text name="中止计划" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('purchasePlan_gridtable','com.huge.ihos.material.purchaseplan.model.PurchasePlan')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div id="purchasePlan_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:purchaseId;width:960;height:628">
			<input type="hidden" id="purchasePlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="purchasePlan_gridtable_addTile">
				<s:text name="purchasePlanNew.title"/>
			</label> 
			<label style="display: none"
				id="purchasePlan_gridtable_editTile">
				<s:text name="purchasePlanEdit.title"/>
			</label>
			<label style="display: none"
				id="purchasePlan_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="purchasePlan_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_purchasePlan_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="purchasePlan_gridtable"></table>
			<div id="purchasePlanPager"></div>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="purchasePlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="purchasePlan_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="purchasePlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>
</div>
</div>