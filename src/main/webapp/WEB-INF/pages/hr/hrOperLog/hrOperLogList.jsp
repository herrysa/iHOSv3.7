
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrOperLogGridIdString="#hrOperLog_gridtable";
	
	jQuery(document).ready(function() { 
	var hrOperLogGrid = jQuery(hrOperLogGridIdString);
    hrOperLogGrid.jqGrid({
    	url : "hrOperLogGridList",
    	editurl:"hrOperLogGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
		{name:'logId',index:'logId',align:'center',label : '<s:text name="hrOperLog.logId" />',hidden:true,key:true},				
		{name:'operTable',index:'operTable',align:'left',width:'150',label : '<s:text name="hrOperLog.operTable" />',hidden:false},				
		{name:'recordCode',index:'recordCode',align:'left',width:'200',label : '<s:text name="hrOperLog.recordCode" />',hidden:false},			
		{name:'operType',index:'operType',align:'center',width:'60',label : '<s:text name="hrOperLog.operType" />',hidden:false},				
		{name:'columnName',index:'columnName',align:'left',width:'120',label : '<s:text name="hrOperLog.columnName" />',hidden:false},				
		{name:'oldValue',index:'oldValue',align:'left',width:'220',label : '<s:text name="hrOperLog.oldValue" />',hidden:false},				
		{name:'newValue',index:'newValue',align:'left',width:'220',label : '<s:text name="hrOperLog.newValue" />',hidden:false},				
		{name:'operPerson.name',index:'operPerson.name',align:'left',width:'80',label : '<s:text name="hrOperLog.operPerson" />',hidden:false},				
		{name:'operTime',index:'operTime',align:'center',width:'140',label : '<s:text name="hrOperLog.operTime" />',hidden:false,formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s',newformat:"Y-m-d H:i:s"}},				
		{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="hrOperLog.orgCode" />',hidden:true}	

        ],
        jsonReader : {
			root : "hrOperLogs", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'operTime',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="hrOperLogList.title" />',
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
			 gridContainerResize("hrOperLog","div");
           var dataTest = {"id":"hrOperLog_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("hrOperLog_gridtable");
       	} 

    });
    jQuery(hrOperLogGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page" id="hrOperLog_page">
	<div id="hrOperLog_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrOperLog_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='hrOperLog.operTable'/>:
					 	<input type="hidden" id="operTable_id" />
					 	<input type="text" id="operTable" name="filter_LIKES_operTable"/>
					 	<script>
					 	jQuery("#operTable").treeselect({
							dataType : "sql",
							optType : "single",
							sql : "SELECT operTable id, operTable name , '1' parent FROM hr_operlog GROUP BY operTable",
							exceptnullparent : false,
							lazy : false,
							minWidth:'200px'
						});
					 	</script>
					</label>
					<label class="queryarea-label" >
						<s:text name='hrOperLog.recordCode'/>:
					 	<input type="text" name="filter_LIKES_recordCode"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='hrOperLog.operType'/>:
					 	<select name="filter_EQS_operType" style="font-size:12px">
					 		<option value="">--</option>
					 		<option value="添加">添加</option>
					 		<option value="修改">修改</option>
					 		<option value="删除">删除</option>
					 	</select>
					</label>
					<label class="queryarea-label" >
						<s:text name='hrOperLog.columnName'/>:
					 	<input type="text" name="filter_LIKES_columnName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='hrOperLog.operPerson'/>:
					 	<input type="text" name="filter_LIKES_operPerson.name"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='hrOperLog.operTime'/>:
					 	<input type="text" name="operTime" class="Wdate" style="height:15px;width:80px" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrOperLog_search_form','hrOperLog_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<!-- 								<button type="button" -->
<!-- 									onclick="propertyFilterSearch('hrOperLog_search_form','hrOperLog_gridtable');"> -->
<%-- 									<s:text name='button.search' /> --%>
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</div></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="hrOperLog_buttonBar">
			<ul class="toolBar" id="hrOperLog_buttonBar">
				<li>
					<a id="hrOperLog_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
			</ul>
		</div>
		<div id="hrOperLog_gridtable_div" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:logId;width:500;height:300">
			<input type="hidden" id="hrOperLog_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="hrOperLog_gridtable_addTile">
				<s:text name="hrOperLogNew.title"/>
			</label> 
			<label style="display: none"
				id="hrOperLog_gridtable_editTile">
				<s:text name="hrOperLogEdit.title"/>
			</label>
			<label style="display: none"
				id="hrOperLog_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="hrOperLog_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_hrOperLog_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 			<table id="hrOperLog_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="hrOperLog_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="hrOperLog_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrOperLog_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="hrOperLog_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
