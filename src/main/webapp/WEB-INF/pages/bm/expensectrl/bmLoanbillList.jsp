
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var bmLoanbillGridIdString="#bmLoanbill_gridtable";
		var bmLoanbillGrid = jQuery(bmLoanbillGridIdString);
    	bmLoanbillGrid.jqGrid({
    		url : "bmLoanbillGridList",
    		editurl:"bmLoanbillGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'BILLID',index:'BILLID',align:'center',label : '${chNameMap["BILLID"]}',hidden:true,key:true},
			{name:'BILLCODE',index:'BILLCODE',align:'left',label : '${chNameMap["BILLCODE"]}',hidden:false},
			{name:'MAKEDATE',index:'MAKEDATE',align:'left',label : '${chNameMap["MAKEDATE"]}',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'PERSONNAME',index:'PERSONNAME',align:'left',label : '${chNameMap["PERSONNAME"]}',hidden:false,width:100},
			{name:'EXPENDDEPTNAME',index:'EXPENDDEPTNAME',align:'left',label : '${chNameMap["CENTRALDEPTID"]}',hidden:false,width:150},
			{name:'CENTRALDEPTNAME',index:'CENTRALDEPTNAME',align:'left',label : '${chNameMap["CENTRALDEPTID"]}',hidden:false,width:150},
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
        	sortname: 'BILLCODE',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bmLoanbillList.title" />',
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
      		gridContainerResize('bmLoanbill','div');
           	var dataTest = {"id":"bmLoanbill_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      		var rowNum = jQuery(this).getDataIDs().length;
  			var ret = jQuery(this).jqGrid('getRowData');
  			if(rowNum > 0){
    	    	 var rowIds = jQuery(this).getDataIDs();
    	    	 var i=0;
    	    	 for (i=0;i<rowNum;i++){
    	    		var id = rowIds[i];
    	    	 	var hrefUrl = "editBmLoanbill?editable=0&suId="+id;
			    	var state = ret[i]["STATE"];
			    	if(state==1){
             		  	setCellText(this,id,'STATE','<span style="color:green" >已审核</span>');
             	  	}else if(state==2){
             		  	setCellText(this,id,'STATE','<span style="color:blue" >已记账</span>');
             	  	}
			    	var billCode = ret[i]["BILLCODE"];
			    	setCellText(jQuery(this)[0],id,'BILLCODE','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmLoanbillFormZhuanqu(\''+hrefUrl+'\')" target="navTab">'+billCode+"</a>");
    	    	 }
    	     }
       		} 

    	});
    jQuery(bmLoanbillGrid).jqGrid('bindKeys');
    jQuery("#bmLoanbill_gridtable_add_c").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmLoanbill?navTabId=bmLoanbill_gridtabl";
		var winTitle='添加借款申请单';
		$.pdialog.open(url,'addbmLoanbill',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    });
    jQuery("#bmLoanbill_gridtable_edit_c").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
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
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能修改新建的单据。");
				return;
			}
		}
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmLoanbill?navTabId=bmLoanbill_gridtabl&suId="+sid;
		var winTitle='修改借款申请单';
		$.pdialog.open(url,'editBmLoanbill',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    });
    
    jQuery("#bmLoanbill_gridtable_del_c").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0||state==3)){
				alertMsg.error("只能删除新建和作废的单据。");
				return;
			}
		}
    	$.post("delBmLoanbill", {
    		"_" : $.now(),suId:sid,navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmLoanbill_gridtable_zf").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能作废新建单据。");
				return;
			}
		}
    	$.post("delBmLoanbill", {
    		"_" : $.now(),suId:sid,navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    
    jQuery("#bmLoanbill_gridtable_check").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==0)){
				alertMsg.error("只能审核新建的单据。");
				return;
			}
		}
    	$.post("changeBmLoanbillState", {
    		"_" : $.now(),suId:sid,state:1,navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmLoanbill_gridtable_uncheck").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==1)){
				alertMsg.error("只能销审已审核的单据。");
				return;
			}
		}
    	$.post("changeBmLoanbillState", {
    		"_" : $.now(),suId:sid,state:0,navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#bmLoanbill_gridtable_account").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmLoanbill_gridtable").jqGrid('getRowData',id);
			var state = rowData["STATE"];
			if(!(state==1)){
				alertMsg.error("只能记账已审核的单据。");
				return;
			}
		}
    	$.post("changeBmLoanbillState", {
    		"_" : $.now(),suId:sid,state:2,navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
  	});
	function bmLoanbillFormZhuanqu(hrefUrl){
		var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
		var winTitle='查看借款申请单';
		$.pdialog.open(hrefUrl,'editBmLoanbill',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
	}
</script>

<div class="page">
	<div id="bmLoanbill_pageHeader" class="pageHeader">
			<div id="bmLoanbill_searchBar" class="searchBar">
				<div class="searchContent">
				<form id="bmLoanbill_search_form" >
					<label style="float:none;white-space:nowrap" >
						${chNameMap["BILLCODE"]}:
						<input type="text" name="filter_LIKES_BILLCODE"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["MAKEDATE"]}:
						<input type="text" name="filter_EQS_makeDate"  class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["CENTRALDEPTID"]}:
						<input type="text" name="filter_EQS_CENTRALDEPTID"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["EXPENDDEPTID"]}:
						<input type="text" name="filter_EQS_EXPENDDEPTID"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${chNameMap["PERSONNAME"]}:
						<input type="text" name="filter_LIKES_PERSONNAME"/>
					</label>
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
							<button type="button" onclick="propertyFilterSearch('bmLoanbill_search_form','bmLoanbill_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="bmLoanbill_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="bmLoanbill_gridtable_add_c" class="addbutton" href="javaScript:" ><span>申请</span></a></li>
				<li><a id="bmLoanbill_gridtable_edit_c" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a></li>
				<!-- <li><a id="bmLoanbill_gridtable_zf" class="closebutton"  href="javaScript:"><span>作废</span></a></li> -->
				<li><a id="bmLoanbill_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="bmLoanbill_gridtable_check" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				<li><a id="bmLoanbill_gridtable_uncheck" class="canceleditbutton"  href="javaScript:"><span>销审</span></a></li>
				<li><a id="bmLoanbill_gridtable_account" class="confirmbutton"  href="javaScript:"><span>记账</span></a></li>
			
			</ul>
		</div>
		<div id="bmLoanbill_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<div id="load_bmLoanbill_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmLoanbill_gridtable"></table>
			<!--<div id="bmLoanbillPager"></div>-->
		</div>
	</div>
	<div id="bmLoanbill_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmLoanbill_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmLoanbill_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmLoanbill_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>