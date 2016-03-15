
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var allSystemPeriodStatusLayout;
	var allSystemPeriodStatusGridIdString="#allSystemPeriodStatus_gridtable";
	
	jQuery(document).ready(function() { 
		var gridColModelStr = jQuery("#allSystemPeriodStatus_gridColModelStr").val();
		var groupHeaderStr = jQuery("#allSystemPeriodStatus_groupHeaderStr").val();
		var colModel = [];
		if(gridColModelStr){
			colModel = JSON.parse(gridColModelStr);
		}
		var groupHeaders = [];
		if(groupHeaderStr){
			groupHeaders = JSON.parse(groupHeaderStr);
		}
		var gridSql = jQuery("#allSystemPeriodStatus_gridSql").val();
		var allSystemPeriodStatusGrid = jQuery(allSystemPeriodStatusGridIdString);
    	allSystemPeriodStatusGrid.jqGrid({
    		url : "allSystemPeriodStatusGridList?gridSql="+gridSql,
    		editurl:"allSystemPeriodStatusGridList",
			datatype : "json",
			mtype : "GET",
        	colModel:colModel,
        	jsonReader : {
				root : "allSystemPeriodStatuss", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'period',
        	viewrecords: true,
        	sortorder: 'desc',
        	rowNum:'10000',
        	//caption:'<s:text name="allSystemPeriodStatusList.title" />',
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
// 				gridContainerResize('allSystemPeriodStatus','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }
           	var dataTest = {"id":"allSystemPeriodStatus_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 
    	});
    jQuery(allSystemPeriodStatusGrid).jqGrid('bindKeys');
    if(groupHeaders.length > 0){
    	jQuery(allSystemPeriodStatusGrid).jqGrid('setGroupHeaders', {
    	    useColSpanStyle: true, 
    	    groupHeaders:groupHeaders
    	  });
    }
    jQuery("#gview_allSystemPeriodStatus_gridtable th[colspan]").css("text-align","center");
  	});
</script>

<div class="page" id="allSystemPeriodStatus_page">
<input type="hidden" id="allSystemPeriodStatus_gridSql" value='<s:property value="gridSql" escapeHtml="false"/>'>
<input type="hidden" id="allSystemPeriodStatus_gridColModelStr" value='<s:property value="gridColModelStr" escapeHtml="false"/>'>
<input type="hidden" id="allSystemPeriodStatus_groupHeaderStr" value='<s:property value="groupHeaderStr" escapeHtml="false"/>'>
<!-- 	<div id="allSystemPeriodStatus_pageHeader" class="pageHeader"> -->
<%-- 	<input type="hidden" id="allSystemPeriodStatus_gridColModelStr" value='<s:property value="gridColModelStr" escapeHtml="false"/>'> --%>
<!-- 			<div class="searchBar"> -->
<!-- 				<div class="searchContent"> -->
<!-- 				<form id="allSystemPeriodStatus_search_form" class="queryarea-form"> -->
<!-- 					<label class="queryarea-label"> -->
<!-- 						期间: -->
<!-- 						<input type="text" style="width:80px"/> -->
<!-- 					</label> -->
<!-- 					<div class="buttonActive" style="float:right"> -->
<!-- 						<div class="buttonContent"> -->
<%-- 							<button type="button" onclick="propertyFilterSearch('allSystemPeriodStatus_search_form','allSystemPeriodStatus_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</form> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 	</div> -->
	<div class="pageContent">
<!-- 		<div class="panelBar" id="allSystemPeriodStatus_buttonBar"> -->
<!-- 			<ul class="toolBar" id="allSystemPeriodStatus_toolbuttonbar"> -->
<!--  				<li> -->
<%-- 					<a id="allSystemPeriodStatus_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!--  				</li>  -->
<!-- 			</ul> -->
<!-- 		</div> -->
		<div id="allSystemPeriodStatus_gridtable_div" layoutH="10" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="allSystemPeriodStatus_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_allSystemPeriodStatus_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="allSystemPeriodStatus_gridtable"></table>
		</div>
	</div>
</div>