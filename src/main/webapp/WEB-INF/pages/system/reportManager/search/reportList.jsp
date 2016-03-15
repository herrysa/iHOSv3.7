<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="reportList.title"/></title>
    <meta name="heading" content="<fmt:message key='reportList.heading'/>"/>
    <meta name="menu" content="ReportMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#report_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#report_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editReport?popup=true";
			var winTitle='<fmt:message key="reportNew.title"/>';
			//popUpWindow(url, winTitle, "width=500");
			$.pdialog.open(url, 'addChargeType', winTitle, {mask:false,width:650,height:400});
		}
		function editRecord(){
	        var sid = jQuery("#report_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editReport?popup=true&reportId=" + sid;
				var winTitle='<fmt:message key="reportNew.title"/>';
				//popUpWindow(url, winTitle, "width=500");
				$.pdialog.open(url, '', winTitle, {mask:false,width:650,height:400});
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
    	});
	</script>
</head>

<div class="page">
	<div id="report_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
			<form id="report_search_form" >
				<label style="float:none;white-space:nowrap" >
					<s:text name='report.reportName'/>:
					<input type="text" name="filter_LIKES_reportName"/>
				</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<label style="float:none;white-space:nowrap" >
					<s:text name='report.groupName'/>:
					<input type="text" name="filter_LIKES_groupName"/>
				</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<label style="float:none;white-space:nowrap" >
					<s:text name='report.reportType'/>:
					<!-- <input type="text" name="filter_EQS_reportType" /> -->
					<s:select name="filter_EQS_reportType" list="reportTypeList" listKey="value" listValue="content" emptyOption="true"></s:select>
				</label>
					<div class="buttonActive" style="float:right;">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('report_search_form','report_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div>
					
			</form>
<!-- 				<div class="buttonActive" style="float:right"> -->
<!-- 					<div class="buttonContent"> -->
<%-- 						<button type="button" onclick="propertyFilterSearch('report_search_form','report_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 					</div> -->
<!-- 				</div> -->
			</div>
		</div>
	</div>
	<div class="pageContent" >
	<div class="panelBar">
			<ul class="toolBar">
				<li><a id="report_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="report_gridtable_del" class="delbutton" href="javaScript:"
					 title="确定要删除吗?"><span>删除</span>
				</a>
				</li>
				<li><a id="report_gridtable_edit" class="changebutton" href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
<div id="report_gridtable_div" layoutH="95"
				class="grid-wrapdiv"
				buttonBar="width:650;height:400">
		<input type="hidden" id="report_gridtable_navTabId"
							value="${sessionScope.navTabId}"/>
							<label style="display: none" id="report_gridtable_addTile">
								<fmt:message key="reportNew.title"/>
							</label> 
							<label style="display: none"
								id="report_gridtable_editTile">
								<fmt:message key="reportEdit.title"/>
							</label>
 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="reportGridEdit"/> 
<s:url id="remoteurl" action="reportGridList"/> 
<div id="load_report_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="report_gridtable" 
    	dataType="json" 
    	gridModel="reports"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="right"
		navigator="false"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		height="200"
  		draggable="true"
  			autowidth="true"
	onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="reportId" 
    	    search="false" 
    	    index="reportId" 
    	    title="%{getText('report.reportId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="groupName" 
 	    index="groupName" 
 	    title="%{getText('report.groupName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
 	       <sjg:gridColumn 
 	    name="reportName" 
 	    index="reportName" 
 	    title="%{getText('report.reportName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="300"
 	    />


   <sjg:gridColumn 
 	    name="reportType" 
 	    index="reportType" 
 	    title="%{getText('report.reportType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="50"
 	    />
   <sjg:gridColumn 
 	    name="reqNo" 
 	    index="reqNo" 
 	    title="%{getText('report.reqNo')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="50"
 	    />
   <sjg:gridColumn 
 	    name="url" 
 	    index="url" 
 	    title="%{getText('report.url')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="300"
 	    />
 	       <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('report.remark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid>
  </div>
  <div id="report_gridtable_footBar" class="panelBar">
						<div class="pages">
							<span>显示</span> <select id="report_gridtable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label id="report_gridtable_totals"></label>条</span>
						</div>

						<div id="report_gridtable_pagination" class="pagination"
							targetType="navTab" totalCount="200" numPerPage="20"
							pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>