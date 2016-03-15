<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/links.jsp"%>
<%@ include file="/common/miniNavBar.jsp"%> --%>
<head>
    <title><fmt:message key="interLoggerList.title"/></title>
    <meta name="heading" content="<fmt:message key='interLoggerList.heading'/>"/>
    <meta name="menu" content="InterLoggerMenu"/>
<%--     <script type="text/javascript"
	src="${ctx}/jquerytheme/js/jquery-ui-1.8.19.custom.min.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="${ctx}/scripts/jqgrid/css/ui.jqgrid.css" />
<script type="text/javascript"	src="/scripts/jqgrid/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript"
	src="${ctx}/scripts/jqgrid/js/i18n/grid.locale-cn.js"></script> --%>
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#interLogger_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#interLogger_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editInterLogger?popup=true";
			var winTitle='<fmt:message key="interLoggerNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#interLogger_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#mybuttondialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#mybuttondialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editInterLogger?popup=true&logId=" + sid;
				var winTitle='<fmt:message key="interLoggerNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		jQuery(document).ready(function() { 
			//alert("interLoggerGridList?dataCollectionTaskId=${dataCollectionTaskId}");
			var interLoggerGrid = jQuery("#interLogger_gridtable");
			interLoggerGrid.jqGrid({
				url : "interLoggerGridList?dataCollectionTaskId=${dataCollectionTaskId}",
				editurl:"interLoggerGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel:[
				{name:'logId',index:'logId',align:'left',label : '<s:text name="interLogger.logId" />',hidden:true,key:true,formatter:"integer"},
				{name:'periodCode',index:'periodCode',align:'left',width:35,label : '<s:text name="interLogger.periodCode" />',hidden:false,sortable:true},
				{name:'taskInterId',index:'taskInterId',align:'left',width:50,label : '<s:text name="interLogger.taskInterId" />',hidden:true,sortable:true},
				{name:'logDateTime',index:'logDateTime',align:'center',width:60,label : '<s:text name="interLogger.logDateTime" />',hidden:false,sortable:true,formatter:"date",formatoptions:{srcformat:'Y-m-d H:i:s',newformat : 'Y-m-d H:i:s'}},
				{name:'logFrom',index:'logFrom',align:'left',width:50,label : '<s:text name="interLogger.logFrom" />',hidden:false,sortable:true},
				{name:'logMsg',index:'logMsg',align:'left',label : '<s:text name="interLogger.logMsg" />',hidden:false,sortable:true}
				],jsonReader : {
					root : "interLoggers",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false
				},
				sortname: '',
				viewrecords: true,
				sortorder: 'asc',
				height:700,
				gridview:true,
				rowNum : 100,
				rownumbers:true,
				loadui: "disable",
				multiselect: true,
				multiboxonly:true,
				shrinkToFit:true,
				autowidth:true,
				onSelectRow: function(rowid) {
				},
				gridComplete:function(){
					//if(jQuery(this).getDataIDs().length>0){
					//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
						// }
					var dataTest = {"id":"interLogger_gridtable"};
					jQuery.publish("onLoadSelect",dataTest,null);
					makepager("interLogger_gridtable");
				}
			});
			jQuery("#interLogger_gridtable").jqGrid('bindKeys');
		});
	</script>
</head>


 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>
<script type="text/javascript">
<!--
//alert("%{dataCollectionTaskId}")
//-->
</script>
<s:url id="editurl" action="interLoggerGridEdit"/> 
<s:url id="remoteurl" action="interLoggerGridList">
<s:param name="dataCollectionTaskId" value="%{dataCollectionTaskId}"></s:param>
</s:url> 

<%-- <div align="left">
	<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
	<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div> --%>
<div class="page">
	<div class="pageContent">
		<div id="interLogger_gridtable_div" class="grid-wrapdiv" layoutH="38">
		<div id="load_interLogger_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		<table id="interLogger_gridtable"></table>
<%-- 		<sjg:grid 
    	id="interLogger_gridtable" 
    	dataType="json" 
    	gridModel="interLoggers"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="50,100,200,300,400"
    	rowNum="100"
    	rownumbers="true"
    	pager="true" 
    	page="1"
    	pagerButtons="true"
    	pagerInput="true"
    	pagerPosition="right"
		navigator="true"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="true"
		navigatorDeleteOptions="{reloadAfterSubmit:true}"   
        navigatorSearch="true"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
 		navigatorRefresh="true"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		autowidth="true"
	onCompleteTopics="onLoadSelect"
  		draggable="true"
  		height="700"
    >
    <sjg:gridColumn 
    	    name="logId" 
    	    search="false" 
    	    index="logId" 
    	    title="%{getText('interLogger.logId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
       <sjg:gridColumn 
 	    name="periodCode" 
 	    index="periodCode" 
 	    title="%{getText('interLogger.periodCode')}" 
 	     hidden="false"
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="35"
 	    />    	    
       <sjg:gridColumn 
 	    name="taskInterId" 
 	    index="taskInterId" 
 	    title="%{getText('interLogger.taskInterId')}" 
 	     hidden="true"
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="50"
 	    />
    <sjg:gridColumn 
    	name="logDateTime" 
	    index="logDateTime" 
	    title="%{getText('interLogger.logDateTime')}" 
	    sortable="true"
      formatter="date"
      formatoptions="{srcformat:'Y-m-d H:i:s',newformat : 'Y-m-d H:i:s'}"
      align="center"
width="60"
      /> 
   <sjg:gridColumn 
 	    name="logFrom" 
 	    index="logFrom" 
 	    title="%{getText('interLogger.logFrom')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="50"
 	    />
   <sjg:gridColumn 
 	    name="logMsg" 
 	    index="logMsg" 
 	    title="%{getText('interLogger.logMsg')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />

  </sjg:grid> --%>
		</div>
</div>
</div>