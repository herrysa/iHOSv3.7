
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var bmExpenseClaimGridIdString="#bmExpenseClaim_gridtable";
		var bmExpenseClaimGrid = jQuery(bmExpenseClaimGridIdString);
    	bmExpenseClaimGrid.jqGrid({
    		url : "bmExpenseClaimGridList",
    		editurl:"bmExpenseClaimGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'CLAIMID',index:'claimId',align:'center',label : '${chNameMap["CLAIMID"]}',hidden:true,key:true},
			{name:'CLAIMCODE',index:'claimCode',align:'left',label : '${chNameMap["CLAIMCODE"]}',hidden:false},
			{name:'MAKEDATE',index:'makeDate',align:'left',label : '${chNameMap["MAKEDATE"]}',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'ORGNAME',index:'orgName',align:'left',label : '${chNameMap["ORGNAME"]}',hidden:false,width:100},
			{name:'DEPTNAME',index:'deptName',align:'left',label : '${chNameMap["DEPTNAME"]}',hidden:false,width:100},
			{name:'PERSONNAME',index:'personName',align:'left',label : '${chNameMap["PERSONNAME"]}',hidden:false,width:100},
			{name:'ASSUMEDEPTNAME',index:'assumeDeptName',align:'left',label : '${chNameMap["ASSUMEDEPTNAME"]}',hidden:false,width:100},
			{name:'BMSUBJID.BUGETSUBJNAME',index:'BMSUBJID.BUGETSUBJNAME',align:'left',label : '${chNameMap["BMSUBJID"]}',hidden:false,width:150},
			{name:'MONEY',index:'money',align:'right',label : '${chNameMap["MONEY"]}',hidden:false,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},width:70},
			{name:'STATE',index:'state',align:'center',label : '${chNameMap["STATE"]}',hidden:false,width:100,formatter : 'select',	edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已记账'}},
			{name:'REMARK',index:'remark',align:'left',label : '${chNameMap["REMARK"]}',hidden:false,width:200}
			],
        	jsonReader : {
				root : "sdatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'claimId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bmExpenseClaimList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
      		gridContainerResize('bmExpenseClaim','div');
           	var dataTest = {"id":"bmExpenseClaim_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      		var rowNum = jQuery(this).getDataIDs().length;
  			var ret = jQuery(this).jqGrid('getRowData');
  			if(rowNum > 0){
    	    	 var rowIds = jQuery(this).getDataIDs();
    	    	 var i=0;
    	    	 for (i=0;i<rowNum;i++){
    	    		var id = rowIds[i];
    	    	 	var hrefUrl = "editBmExpenseClaim?editable=0&suId="+id;
			    	var state = ret[i]["STATE"];
			    	if(state==1){
             		  	setCellText(this,id,'STATE','<span style="color:green" >已审核</span>');
             	  	}else if(state==2){
             		  	setCellText(this,id,'STATE','<span style="color:blue" >已记账</span>');
             	  	}
			    	var claimCode = ret[i]["CLAIMCODE"];
			    	setCellText(jQuery(this)[0],id,'CLAIMCODE','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmExpenseFormZhuanqu(\''+hrefUrl+'\')" target="navTab">'+claimCode+"</a>");
    	    	 }
    	     }
       		} 

    	});
    jQuery(bmExpenseClaimGrid).jqGrid('bindKeys');
    jQuery("#bmExpenseClaim_gridtable_add_c").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmExpenseClaim?navTabId=bmExpenseClaim_gridtabl";
		var winTitle='添加报销单';
		$.pdialog.open(url,'addbmExpenseClaim',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    });
    jQuery("#bmExpenseClaim_gridtable_edit_c").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	if(sid.length>1){
    		alertMsg.error("只能选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能修改新建的单据。");
				return;
			}
		}
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmExpenseClaim?navTabId=bmExpenseClaim_gridtabl&suId="+sid;
		var winTitle='修改报销单';
		$.pdialog.open(url,'editbmExpenseClaim',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    });
    
    jQuery("#bmExpenseClaim_gridtable_del_c").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0||state==3)){
				alertMsg.error("只能删除新建和作废的单据。");
				return;
			}
		}
    	$.post("delBmExpenseClaim", {
    		"_" : $.now(),suId:sid,navTabId:'bmExpenseClaim_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmExpenseClaim_gridtable_zf").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能作废新建单据。");
				return;
			}
		}
    	$.post("delBmExpenseClaim", {
    		"_" : $.now(),suId:sid,navTabId:'bmExpenseClaim_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    
    jQuery("#bmExpenseClaim_gridtable_check").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能审核新建的单据。");
				return;
			}
		}
    	$.post("changeBmExpenseClaimState", {
    		"_" : $.now(),suId:sid,state:1,navTabId:'bmExpenseClaim_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmExpenseClaim_gridtable_uncheck").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==1)){
				alertMsg.error("只能销审已审核的单据。");
				return;
			}
		}
    	$.post("changeBmExpenseClaimState", {
    		"_" : $.now(),suId:sid,state:0,navTabId:'bmExpenseClaim_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmExpenseClaim_gridtable_account").click(function(){
    	var sid = jQuery("#bmExpenseClaim_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmExpenseClaim_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==1)){
				alertMsg.error("只能记账已审核的单据。");
				return;
			}
		}
    	$.post("changeBmExpenseClaimState", {
    		"_" : $.now(),suId:sid,state:2,navTabId:'bmExpenseClaim_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
  	});
	function bmExpenseFormZhuanqu(hrefUrl){
		var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
		var winTitle='查看报销单';
		$.pdialog.open(hrefUrl,'editbmExpenseClaim',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
	}
</script>

<div class="page">
	<div id="bmExpenseClaim_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmExpenseClaim_search_form" >
					<label style="float:none;white-space:nowrap" >
						${chNameMap["CLAIMCODE"]}:
						<input type="text" name="filter_LIKES_CLAIMCODE"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["MAKEDATE"]}:
						<input type="text" name="filter_EQS_makeDate"  class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["PERSONNAME"]}:
						<input type="text" name="filter_EQS_PERSONNAME"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						${chNameMap["MAKEDATE"]}:
						<input type="text" name="filter_EQS_expendDeptId"/>
					</label> --%>
					
					<label style="float:none;white-space:nowrap" >
						${chNameMap["STATE"]}:
						<s:select list="#{'0':'新建','1':'已审核','2':'已记账'}" key="filter_EQI_STATE" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["REMARK"]}:
						<input type="text" name="filter_LIKES_REMARK"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bmExpenseClaim_search_form','bmExpenseClaim_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="bmExpenseClaim_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="bmExpenseClaim_gridtable_add_c" class="addbutton" href="javaScript:" ><span>申请</span></a></li>
				<li><a id="bmExpenseClaim_gridtable_edit_c" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a></li>
				<!-- <li><a id="bmExpenseClaim_gridtable_zf" class="closebutton"  href="javaScript:"><span>作废</span></a></li> -->
				<li><a id="bmExpenseClaim_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="bmExpenseClaim_gridtable_check" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				<li><a id="bmExpenseClaim_gridtable_uncheck" class="canceleditbutton"  href="javaScript:"><span>销审</span></a></li>
				<li><a id="bmExpenseClaim_gridtable_account" class="confirmbutton"  href="javaScript:"><span>记账</span></a></li>
			
			</ul>
		</div>
		<div id="bmExpenseClaim_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmExpenseClaim_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmExpenseClaim_gridtable_addTile">
				<s:text name="bmExpenseClaimNew.title"/>
			</label> 
			<label style="display: none"
				id="bmExpenseClaim_gridtable_editTile">
				<s:text name="bmExpenseClaimEdit.title"/>
			</label>
			<div id="load_bmExpenseClaim_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmExpenseClaim_gridtable"></table>
			<!--<div id="bmExpenseClaimPager"></div>-->
		</div>
	</div>
	<div id="bmExpenseClaim_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmExpenseClaim_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmExpenseClaim_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmExpenseClaim_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>