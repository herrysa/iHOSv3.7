
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var width = 960,height = 628;
	function deptMRSummaryGridReload(){
		var urlString = "deptMRSummaryGridList";
		var storeId = jQuery("#deptMRSummary_search_store_id").val();
		var makeDate_from = jQuery("#deptMRSummary_search_make_date_from").val();
		var makeDate_to = jQuery("#deptMRSummary_search_make_date_to").val();
		var planType = jQuery("#deptMRSummary_search_planType").val();
		
		urlString = urlString
			+ "?filter_GED_makeDate=" + makeDate_from + "&filter_LED_makeDate=" + makeDate_to
			+ "&filter_EQS_store.id=" + storeId + "&filter_EQS_planType=" + planType;
		urlString=encodeURI(urlString);
		jQuery("#deptMRSummary_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var deptMRSummaryLayout;
			  var deptMRSummaryGridIdString="#deptMRSummary_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
var deptMRSummaryGrid = jQuery(deptMRSummaryGridIdString);
    deptMRSummaryGrid.jqGrid({
    	url : "deptMRSummaryGridList",
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
{name:'purchaseId',index:'purchaseId',align:'center',width : 120,label : '<s:text name="deptMRSummary.purchaseId" />',hidden:false}, 
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
           if(jQuery(this).getDataIDs().length>0){
             // jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]); 
              var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
             	 editUrl = "'${ctx}/editDeptMRSummary?mrId="+ret[i]["mrId"]+"'";
             	 setCellText(this,id,'mrNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showDeptMRSummary('+editUrl+');">'+ret[i]["mrNo"]+'</a>');
             	 if(ret[i]["purchaseId"]==null || ret[i]["purchaseId"]==""){
             			setCellText(this,id,'purchaseId','<p>未生成</p>');
             		 }else{
             			setCellText(this,id,'purchaseId','<p style="color:blue">已生成</p>');
             		 }
             	 }
	            }
            }          
           var dataTest = {"id":"deptMRSummary_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptMRSummary_gridtable");
      	 initFlag = initColumn('deptMRSummary_gridtable','com.huge.ihos.material.deptplan.model.DeptMRSummary',initFlag);
       	} 

    });
    jQuery(deptMRSummaryGrid).jqGrid('bindKeys');
    jQuery("#deptMRSummary_search_store").treeselect({
		dataType : "sql",
		optType : "single",
		sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
		exceptnullparent : false,
		lazy : false
	}); 
    jQuery("#deptMRSummary_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editDeptMRSummary";
		$.pdialog.open(url,'addDeptMRSummary','科室需求计划汇总', {mask:true,width : width,height : height});
	});
	//deptMRSummaryLayout.resizeAll();
  	});
	function showDeptMRSummary(url){
		$.pdialog.open(url,'editDeptMRSummary','科室需求计划汇总明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
<div id="deptMRSummary_container">
			<div id="deptMRSummary_layout-center"
				class="pane ui-layout-center">
		<div id="deptMRSummary_pageHeader" class="pageHeader">
			<div class="searchBar">
			<div class="searchContent">
				<form id="deptMRSummary_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.store'/>:
						<input type="hidden" id="deptMRSummary_search_store_id">
					 	<input type="text" id="deptMRSummary_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.makeDate'/>:
						<input type="text"	id="deptMRSummary_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptMRSummary_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="deptMRSummary_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptMRSummary_search_make_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.planType'/>:
						<s:select id="deptMRSummary_search_planType"  list="#{'':'--','1':'月计划','2':'追加计划'}" style="width:80px" ></s:select>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="deptMRSummaryGridReload()"><s:text name='button.search'/></button>
						</div>
					</div> --%>
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
			</div>
	</div>
    <div class="pageContent">
    <div class="panelBar">
			<ul class="toolBar">
				<li><a id="deptMRSummary_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><s:text name='deptMRSummary.summary'/>
					</span>
				</a>
				</li>
				<!--<li><a id="deptMRSummary_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="deptMRSummary_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>		  -->	
				<li>
    			 <a class="settlebutton" href="javaScript:setColShow('deptMRSummary_gridtable','com.huge.ihos.material.deptplan.model.DeptMRSummary')"><span><s:text name="button.setColShow" /></span></a>
    			</li>				
			</ul>
		</div>
		<div id="deptMRSummary_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:mrId;width:500;height:300">
			<input type="hidden" id="deptMRSummary_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptMRSummary_gridtable_addTile">
				<s:text name="deptMRSummaryNew.title"/>
			</label> 
			<label style="display: none"
				id="deptMRSummary_gridtable_editTile">
				<s:text name="deptMRSummaryEdit.title"/>
			</label>
			<label style="display: none"
				id="deptMRSummary_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptMRSummary_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptMRSummary_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="deptMRSummary_gridtable"></table>
		<div id="deptMRSummaryPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptMRSummary_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptMRSummary_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptMRSummary_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>