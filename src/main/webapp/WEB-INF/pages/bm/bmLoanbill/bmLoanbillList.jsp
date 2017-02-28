
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmLoanbillLayout;
	var bmLoanbillGridIdString="#bmLoanbill_gridtable";
	
	jQuery(document).ready(function() { 
		var bmLoanbillGrid = jQuery(bmLoanbillGridIdString);
    	bmLoanbillGrid.jqGrid({
    		url : "bmLoanbillGridList",
    		editurl:"bmLoanbillGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'billId',index:'billId',align:'center',label : '<s:text name="bmLoanbill.billId" />',hidden:true,key:true},
			{name:'billCode',index:'billCode',align:'left',label : '<s:text name="bmLoanbill.billCode" />',hidden:false},
			{name:'makeDate',index:'makeDate',align:'left',label : '<s:text name="bmLoanbill.makeDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'personName',index:'personName',align:'left',label : '<s:text name="bmLoanbill.personId" />',hidden:false,width:100},
			{name:'expendDeptName',index:'expendDeptName',align:'left',label : '<s:text name="bmLoanbill.expendDeptId" />',hidden:false,width:150},
			{name:'centralDeptName',index:'centralDeptName',align:'left',label : '<s:text name="bmLoanbill.centralDeptId" />',hidden:false,width:150},
			{name:'money',index:'money',align:'right',label : '<s:text name="bmLoanbill.money" />',hidden:false,formatter:'number',width:70},
			{name:'state',index:'state',align:'center',label : '<s:text name="bmLoanbill.state" />',hidden:false,formatter:'integer',width:100}
			],
        	jsonReader : {
				root : "bmLoanbills", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'billId',
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
           	var dataTest = {"id":"bmLoanbill_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("bmLoanbill_gridtable");
       		} 

    	});
    jQuery(bmLoanbillGrid).jqGrid('bindKeys');
    jQuery("#bmLoanbill_gridtable_add_c").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmLoanbill?navTabId=bmLoanbill_gridtabl";
		var winTitle='<s:text name="bmLoanbillNew.title"/>';
		$.pdialog.open(url,'addBmLoanbill',winTitle, {mask:true,width : fullWidth,height : fullHeight});
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
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var url = "editBmLoanbill?navTabId=bmLoanbill_gridtabl&billId="+sid;
		var winTitle='<s:text name="bmLoanbillEdit.title"/>';
		$.pdialog.open(url,'editBmLoanbill',winTitle, {mask:true,width : fullWidth,height : fullHeight});
    });
    
    jQuery("#bmLoanbill_gridtable_del_c").click(function(){
    	var sid = jQuery("#bmLoanbill_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	$.post("bmLoanbillGridEdit", {
    		"_" : $.now(),id:sid,oper:'del',navTabId:'bmLoanbill_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
  	});
</script>

<div class="page">
	<div id="bmLoanbill_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmLoanbill_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.billCode'/>:
						<input type="text" name="filter_EQS_billCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.centralDeptId'/>:
						<input type="text" name="filter_EQS_centralDeptId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.expendDeptId'/>:
						<input type="text" name="filter_EQS_expendDeptId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.makeDate'/>:
						<input type="text" name="filter_EQS_makeDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.personName'/>:
						<input type="text" name="filter_EQS_personName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbill.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(bmLoanbill_search_form,bmLoanbill_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bmLoanbill_gridtable_add_c" class="addbutton" href="javaScript:" ><span>申请</span></a></li>
				<li><a id="bmLoanbill_gridtable_edit_c" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a></li>
				<li><a class="delbutton"  href="javaScript:"><span>作废</span></a></li>
				<li><a id="bmLoanbill_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="delbutton"  href="javaScript:"><span>审核</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="delbutton"  href="javaScript:"><span>销审</span></a></li>
			
			</ul>
		</div>
		<div id="bmLoanbill_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmLoanbill_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmLoanbill_gridtable_addTile">
				<s:text name="bmLoanbillNew.title"/>
			</label> 
			<label style="display: none"
				id="bmLoanbill_gridtable_editTile">
				<s:text name="bmLoanbillEdit.title"/>
			</label>
			<div id="load_bmLoanbill_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmLoanbill_gridtable"></table>
			<!--<div id="bmLoanbillPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
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