
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 	var width = 960,height = 628;
	function orderGridReload(){
		var urlString = "orderGridList";
		var deptId = jQuery("#order_search_dept_id").val();
		var makeDate_from = jQuery("#order_search_make_date_from").val();
		var makeDate_to = jQuery("#order_search_make_date_to").val();
		var checkDate_from = jQuery("#order_search_check_date_from").val();
		var checkDate_to = jQuery("#order_search_check_date_to").val();
		var vendorId = jQuery("#order_search_vendor_id").val();
		var state = jQuery("#order_search_state").val();
		
		urlString = urlString
			+ "?filter_GED_makeDate=" + makeDate_from+ "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_GED_checkDate=" + checkDate_from+ "&filter_LED_checkDate=" + checkDate_to
			+ "&filter_EQS_vendor.id=" + vendorId+ "&filter_EQS_dept.departmentId=" + deptId
			+ "&filter_EQS_state=" + state;
		urlString=encodeURI(urlString);
		jQuery("#order_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var orderGridIdString="#order_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var orderGrid = jQuery(orderGridIdString);
   		orderGrid.jqGrid({
	    	url : "orderGridList?filter_GED_makeDate="+jQuery("#order_search_make_date_from").val()+"&filter_LED_makeDate=${fns:userContextParam('businessDateStr')}",
	    	editurl:"orderGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
			{name:'orderId',index:'orderId',align:'center',label : '<s:text name="order.orderId" />',hidden:true,key:true},				
			{name:'orderNo',index:'orderNo',align:'left',width:150,label : '<s:text name="order.orderNo" />',hidden:false,highsearch:true},				
			{name:'makeDate',index:'makeDate',align:'center',width : 110,label : '<s:text name="order.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
			{name:'vendor.vendorName',index:'vendor.vendorName',width : 140,align:'left',label : '<s:text name="order.vendor" />',hidden:false,highsearch:true},				
			{name:'vendor.vendorId',index:'vendor.vendorId',width : 140,align:'left',label : '<s:text name="order.vendorId" />',hidden:true,highsearch:false},				
			{name:'isNotify',index:'isNotify',align:'center',width : 100,label : '<s:text name="order.isNotify" />',hidden:false,highsearch:true,formatter:'checkbox'},				
			{name:'notifyDate',index:'notifyDate',align:'center',width : 100,label : '<s:text name="order.notifyDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
			{name:'dept.name',index:'dept.name',align:'left',width : 100,label : '<s:text name="order.dept" />',hidden:false,highsearch:true},				
			{name:'buyPerson.name',index:'buyPerson.name',width : 80,align:'left',label : '<s:text name="order.buyPerson" />',hidden:false,highsearch:true},				
			{name:'buyPerson.personId',index:'buyPerson.personId',width : 80,align:'left',label : '<s:text name="order.buyPerson" />',hidden:true,highsearch:false},				
			{name:'arrAddr',index:'arrAddr',align:'center',width : 120,label : '<s:text name="order.arrAddr" />',hidden:false,highsearch:true},				
			{name:'procType.typeName',index:'procType.typeName',width : 100,align:'left',label : '<s:text name="order.procType" />',hidden:false,highsearch:true},				
			{name:'checkDate',index:'checkDate',align:'center',width : 100,label : '<s:text name="order.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
			{name:'remark',index:'remark',align:'left',width : 130,label : '<s:text name="order.remark" />',hidden:false,highsearch:true},				
			{name:'state',index:'state',align:'center',width : 100,label : '<s:text name="order.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:部分完成;3:已完成;4:已作废;'}}				
	        ],
	        jsonReader : {
				root : "orders", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'orderNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="orderList.title" />',
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
	              		  setCellText(this,id,'state','<span style="color:#CC00FF" >部分完成</span>');
	              	  }else if(ret[i]['state']=="3"){
	              		  setCellText(this,id,'state','<span style="color:blue" >已完成</span>');
	              	  }else if(ret[i]['state']=="4"){
	              		  setCellText(this,id,'state','<span style="color:#888888" >已作废</span>');
	              	  }
	              	  editUrl = "'${ctx}/editOrder?orderId="+ret[i]["orderId"]+"'";
	   	        	  setCellText(this,id,'orderNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showOrder('+editUrl+');">'+ret[i]["orderNo"]+'</a>');
	              }
	            }
	           var dataTest = {"id":"order_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("order_gridtable");
	      	 initFlag = initColumn('order_gridtable','com.huge.ihos.material.order.model.Order',initFlag);
	       	} 
   		});
    	jQuery(orderGrid).jqGrid('bindKeys');
    	
    	jQuery("#order_search_dept").treeselect({
 			dataType:"sql",
 			optType:"single",
 			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
 			exceptnullparent:false,
 			lazy:false
 		});
    	jQuery("#order_search_vendor").combogrid({
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
					$("#order_search_vendor_id").val(ui.item.VID);
				return false; 
			}
		});
    	
    	
    	 jQuery("#order_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
 			var url = "${ctx}/orderGridEdit?oper=del"
 			var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
 			if (sid == null || sid.length == 0) {
 				alertMsg.error("请选择记录。");
 				return;
 			} else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
 					
 					if(row['state']!='0'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
 				}
 				url = url+"&id="+sid+"&navTabId=order_gridtable";
 				alertMsg.confirm("确认删除？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}
 		});
    	 
    	 jQuery("#order_gridtable_check").unbind( 'click' ).bind("click",function(){
 	    	var url = "${ctx}/orderGridEdit?oper=check"
 				var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
 				if (sid == null || sid.length == 0) {
 					alertMsg.error("请选择记录。");
 					return;
 				} else {
 					for(var i = 0;i < sid.length; i++){
 						var rowId = sid[i];
 						var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
 						
 						if(row['state']!='0'){
							alertMsg.error("只能审核处于新建状态的记录!");
							return;
						}
 					}
 					url = url+"&id="+sid+"&navTabId=order_gridtable";
 					alertMsg.confirm("确认审核？", {
 						okCall : function() {
 							$.post(url,function(data) {
 								formCallBack(data);
 							});
 						}
 					});
 				}
 	    });
    	 jQuery("#order_gridtable_cancelCheck").unbind( 'click' ).bind("click",function(){
  			var url = "${ctx}/orderGridEdit?oper=cancelCheck"
  				var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
  				if (sid == null || sid.length == 0) {
  					alertMsg.error("请选择记录。");
  					return;
  				} else {
  					for(var i = 0;i < sid.length; i++){
  						var rowId = sid[i];
  						var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
  						
  						if(row['state']!='1'){
 							alertMsg.error("只有已审核的记录才能够被销审!");
 							return;
 						}
  					}
  					url = url+"&id="+sid+"&navTabId=order_gridtable";
  					alertMsg.confirm("确认销审？", {
  						okCall : function() {
  							$.post(url,function(data) {
  								formCallBack(data);
  							});
  						}
  					});
  				}
 	    });
    	 jQuery("#order_gridtable_abandon").unbind( 'click' ).bind("click",function(){
  			var url = "${ctx}/orderGridEdit?oper=abandon"
  			var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
  			if (sid == null || sid.length == 0) {
  				alertMsg.error("请选择记录。");
  				return;
  			} else {
  				for(var i = 0;i < sid.length; i++){
  					var rowId = sid[i];
  					var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
  					
  					if(row['state']=='4'){
  						alertMsg.error("您选择的订单已作废!");
  						return;
  					}else if(row['state']!='0'){
  						alertMsg.error("只能作废处于新建状态的记录!");
  						return;
  					}
  				}
  				url = url+"&id="+sid+"&navTabId=order_gridtable";
  				alertMsg.confirm("确认作废订单？", {
  					okCall : function() {
  						$.post(url,function(data) {
  							formCallBack(data);
  						});
  					}
  				});
  			}
  		});
    	jQuery("#order_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
 			var url = "editOrder";
 			$.pdialog.open(url,'addOrder','添加订单', {mask:true,width : width,height : height});
 		});
 		jQuery("#order_gridtable_extend").unbind( 'click' ).bind("click",function(){
 			$.pdialog.open('${ctx}/selectPurchasePlanList','selectPurchasePlan',"选择采购计划", {mask:true,width : width,height : height});
  		});
 		 jQuery("#order_gridtable_copy_custom").unbind( 'click' ).bind("click",function(){
 			var url = "${ctx}/orderGridEdit?oper=copy"
 			var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
 			if (sid == null || sid.length == 0) {
 				alertMsg.error("请选择记录。");
 				return;
 			} else{
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
 					
 					if(row['state']=='1' || row['state']=='3'){						
 					}else{
 						alertMsg.error("只能复制处于已审核或者已完成状态的记录!");
 						return;
 					}
 				}
 				url = url+"&id="+sid+"&navTabId=order_gridtable";
 				alertMsg.confirm("确认复制？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}
 		});
 		jQuery("#order_gridtable_union").unbind( 'click' ).bind("click",function(){
 			var url = "${ctx}/unionOrder"
 			var sid = jQuery("#order_gridtable").jqGrid('getGridParam','selarrrow');
 			if (sid == null || sid.length == 0) {
 				alertMsg.error("请选择记录。");
 				return;
 			} if (sid.length ==1) {
 				alertMsg.error("请至少选择两条记录。");
 				return;
 			} else {
 				var base;
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#order_gridtable").jqGrid('getRowData',rowId);
 					if(row['state']!='0'){
 						alertMsg.error("请只选择选择状态为新建的订单!");
 						return;
 					}
 					var vp = row['buyPerson.personId']+"ш"+row['vendor.vendorId'];
 					if(!base){
 						base = vp;
 					}else{
 						if(base!=vp){
 							alertMsg.error("您选择的记录存在供应商和采购员不一致，不能合并!");
 	 						return;
 						}
 					}
 				}
 				url = url+"?id="+sid+"&navTabId=order_gridtable";
 				alertMsg.confirm("确认合并订单？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							$.pdialog.open("${ctx}/editOrder?popup=true&orderId="+data.orderId,'editOrder','订单明细', {mask:true,width : width,height : height});
 							formCallBack(data);
 						});
 					}
 				});
 			}
  		});
  	});
	function showOrder(url){
		$.pdialog.open(url,'editOrder','订单明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
<div id="order_container">
	<div id="order_layout-center" class="pane ui-layout-center">
	<div id="order_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="order_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.dept'/>:
						<input type="hidden" id="order_search_dept_id">
					 	<input type="text" id="order_search_dept" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.makeDate'/>:
						<input type="text"	id="order_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'order_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="order_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'order_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.checkDate'/>:
						<input type="text"	id="order_search_check_date_from" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'order_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="order_search_check_date_to" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'order_search_check_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >供货商:
						<input type="hidden" id="order_search_vendor_id" >
					 	<input type="text" id="order_search_vendor" value="拼音/汉字/编码"
					 	class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#order_search_vendor_id'))" 
						onfocus="clearInput(this,jQuery('#order_search_vendor_id'))" onkeyDown="setTextColor(this)" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='order.state'/>:
						<s:select id="order_search_state"  list="#{'':'--','0':'新建','1':'已审核','2':'部分完成','3':'已完成','4':'已作废'}" style="width:70px" ></s:select>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="orderGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
				</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="orderGridReload()"><s:text name='button.search'/></button>
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
					<a id="order_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><s:text name="新单" /></span></a>
				</li>
				<li>
					<a id="order_gridtable_copy_custom" class="savebutton"  href="javaScript:"><span><s:text name="button.copy" /></span></a>
				</li>
				<li>
					<a id="order_gridtable_extend" class="deliverbutton"  href="javaScript:"><span><s:text name="根据采购计划生成" /></span></a>
				</li> 
				<li>
					<a id="order_gridtable_union" class="changebutton"  href="javaScript:"><span><s:text name="订单合并" /></span></a>
				</li> 
				<li>
					<a id="order_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<%-- <li>
					<a id="order_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="通知供应商" /></span></a>
				</li> --%>
				<li>
					<a id="order_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="审核" /></span></a>
				</li>
				<li>
					<a id="order_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="销审" /></span></a>
				</li>
				<li>
					<a id="order_gridtable_abandon" class="disablebutton"  href="javaScript:"><span><s:text name="button.abandon" /></span></a>
				</li>
				<%-- <li>
					<a id="order_gridtable_stop" class="confirmbutton"  href="javaScript:" style="display:none"><span><s:text name="中止订单" /></span></a>
				</li> --%>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('order_gridtable','com.huge.ihos.material.order.model.Order')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div id="order_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:orderId;width:500;height:300">
			<input type="hidden" id="order_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="order_gridtable_addTile">
				<s:text name="orderNew.title"/>
			</label> 
			<label style="display: none"
				id="order_gridtable_editTile">
				<s:text name="orderEdit.title"/>
			</label>
			<label style="display: none"
				id="order_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="order_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_order_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			 <table id="order_gridtable"></table>
			<div id="orderPager"></div>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="order_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="order_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="order_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>
</div>
</div>