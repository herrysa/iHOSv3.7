
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var width = 960,height = 628;
	function deptMRSummaryGridReload(){
		var urlString = "deptMRSummaryGridList";
		var storeId = "${storeId}";
		var makeDate_from = jQuery("#deptMRSummary_select_make_date_from").val();
		var makeDate_to = jQuery("#deptMRSummary_select_make_date_to").val();
		var planType = jQuery("#deptMRSummary_select_planType").val();
		
		urlString = urlString
			+ "?oper=nopurchaseId&filter_GED_makeDate=" + makeDate_from + "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_EQS_store.id=" + storeId + "&filter_EQS_planType=" + planType;
		urlString=encodeURI(urlString);
		jQuery("#deptMRSummary_select_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var deptMRSummaryLayout;
			  var deptMRSummaryGridIdString="#deptMRSummary_select_gridtable";
	
	jQuery(document).ready(function() { 
		var storeIdGet ="${storeId}";
		var displayurl = "deptMRSummaryGridList?oper=nopurchaseId";
		if(storeIdGet){
			displayurl = displayurl+"&storeId="+storeIdGet;
		}
var deptMRSummaryGrid = jQuery(deptMRSummaryGridIdString);
    deptMRSummaryGrid.jqGrid({
    	url : displayurl,
    	editurl:"deptMRSummaryGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'mrId',index:'mrId',align:'center',label : '<s:text name="deptMRSummary.mrId" />',hidden:true,key:true},
{name:'mrNo',index:'mrNo',align:'left',width : 130,label : '<s:text name="deptMRSummary.mrNo" />',hidden:false},
{name:'planType',index:'planType',align:'left',width : 100,label : '<s:text name="deptMRSummary.planType" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:月计划;2:追加计划'}},
{name:'store.storeName',index:'store.storeName',align:'left',width : 100,label : '<s:text name="deptMRSummary.store" />',hidden:false,highsearch:true},	
{name:'makePerson.name',index:'makePerson.name',align:'left',width : 60,label : '<s:text name="deptMRSummary.makePerson" />',hidden:false,highsearch:true},				
{name:'makeDate',index:'makeDate',align:'center',width : 100,label : '<s:text name="deptMRSummary.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},															
/*{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="deptMRSummary.orgCode" />',hidden:false},
{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="deptMRSummary.copyCode" />',hidden:false},	
{name:'periodMonth',index:'periodMonth',align:'center',label : '<s:text name="deptMRSummary.periodMonth" />',hidden:false},
{name:'docTemId',index:'docTemId',align:'center',label : '<s:text name="deptMRSummary.docTemId" />',hidden:false},*/								
{name:'remark',index:'remark',align:'left',width : 120,label : '<s:text name="deptMRSummary.remark" />',hidden:false},				

        ],
        jsonReader : {
			root : "deptMRSummaries", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'mrId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="deptMRSummaryList.title" />',
        height:440,
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
           if(jQuery(this).getDataIDs().length>0){
             // jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]); 
              var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
             	 editUrl = "'${ctx}/editDeptMRSummary?loadtype=select&mrId="+ret[i]["mrId"]+"'";
             	 setCellText(this,id,'mrNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showDeptMRSummary('+editUrl+');">'+ret[i]["mrNo"]+'</a>');
             	 }
	            }
            }          
           var dataTest = {"id":"deptMRSummary_select_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptMRSummary_select_gridtable");
       	} 

    });
    jQuery(deptMRSummaryGrid).jqGrid('bindKeys');
//     jQuery("#deptMRSummary_select_store").treeselect({
// 		dataType : "sql",
// 		optType : "single",
// 		sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+jQuery("#orgCode").html()+"'",
// 		exceptnullparent : false,
// 		lazy : false
// 	}); 
    jQuery("#deptMRSummary_select_gridtable_ok_custom").unbind( 'click' ).bind("click",function(){
    	var urlString = "${ctx}/purchasePlanDetailGridList"
			var sid = jQuery("#deptMRSummary_select_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} 
		$('#getmrId').val(sid);
		urlString = urlString+"?mrId="+sid+"&navTabId=deptMRSummary_gridtable";
		urlString=encodeURI(urlString);
		jQuery("#${random}_purchasePlanTable").setGridParam({url:urlString}).reload();
		/* jQuery("${random}_purchasePlan_${store}_id").val() = jQuery("#deptMRSummary_search_store_id").val();
		jQuery("${random}_purchasePlan_${store}_id").val() = jQuery("#deptMRSummary_search_store").val(); */
		$.pdialog.closeCurrent();
		/* var url = "editDeptMRSummary";
		$.pdialog.open(url,'addDeptMRSummary','科室需求计划汇总', {mask:true,width : width,height : height}); */
	});
	//deptMRSummaryLayout.resizeAll();
  	});
	function showDeptMRSummary(url){
		$.pdialog.open(url,'editDeptMRSummary','科室需求计划汇总明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
<div id="deptMRSummarySelect_container">
	<div id="deptMRSummarySelect_layout-center" class="pane ui-layout-center">
		<div id="deptMRSummarySelect_pageHeader" class="pageHeader">
			<div class="searchBar">
			  <div class="searchContent">
				<form id="deptMRSummary_select_form" >
					<label style="float:none;white-space:nowrap;display:none" >
						<s:text  name='deptMRSummary.store'/>:
						<input type="hidden" id="deptMRSummary_select_store_id">
					 	<input type="text" id="deptMRSummary_select_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.makeDate'/>:
						<input type="text"	id="deptMRSummary_select_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptMRSummary_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="deptMRSummary_select_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptMRSummary_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.planType'/>:
						<s:select id="deptMRSummary_select_planType"  list="#{'':'--','1':'月计划','2':'追加计划'}" style="width:80px" ></s:select>
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right;display:none">
						<div class="buttonContent">
							<button type="button" onclick="deptMRSummaryGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptMRSummaryGridReload()"><s:text name='button.search'/></button>
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
				<li><a id="deptMRSummary_select_gridtable_ok_custom" class="addbutton" href="javaScript:" ><span><s:text name='deptMRSummary.ok'/>
					</span>
				</a>
				</li>			
			</ul>
		</div>
		<div id="deptMRSummarySelect_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:mrId;width:960;height:628">
			<input type="hidden" id="deptMRSummarySelect_gridtable_navTabId" value="${sessionScope.navTabId}">
            <div id="load_deptMRSummary_select_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
                    <table id="deptMRSummary_select_gridtable"></table>
             </div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptMRSummarySelect_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptMRSummary_select_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptMRSummarySelect_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>
</div>
</div>