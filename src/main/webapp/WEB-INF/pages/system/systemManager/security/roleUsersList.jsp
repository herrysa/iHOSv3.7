<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<head>
    <title><fmt:message key="searchOptionList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchOptionList.heading'/>"/>
    <meta name="menu" content="SearchOptionMenu"/>
    
    <script type="text/javascript">
		/* jQuery(document).ready(function() { 
			var searchTabsContentW = jQuery("#roleTabsContent").width();
			var searchTabsContentH = jQuery("#roleTabsContent").height();
			setTimeout(function(){
				jQuery("#roleUsers_gridtable").jqGrid('setGridHeight',searchTabsContentH-53);
			},100);
			setTimeout(function(){
				jQuery("#roleUsers_gridtable").jqGrid('setGridWidth',searchTabsContentW);
			},100);
    	}); */
	</script>
</head>

<div class="page">
	<div class="pageContent">
<div id="roleUsers_gridtable_div"  extraHeight=55 extraWidth=10 tablecontainer="roleTabsContent"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchOption;optId:searchOptionId;fatherGrid:search_gridtable;extraParam:searchId;width:650;height:680">
 				<input type="hidden" id="ssearchOption_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none"
						id="searchOption_gridtable_addTile"> <fmt:message key="searchOptionNew.title"/> </label> 
						<label style="display: none" id="searchOption_gridtable_editTile"> 
							<fmt:message key="searchOptionNew.title"/>
						</label>

<s:url id="roleUserGridListUrl" action="roleUserGridList">
<s:param name="roleId" value="#parameters.roleId"></s:param>
</s:url> 
<div id="load_roleUsers_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="roleUsers_gridtable" 
    	dataType="json" 
    	gridModel="users"
    	href="%{roleUserGridListUrl}"    	
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="10"
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
		navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="false"
  		multiboxonly="false"
  		resizable="true"
  		draggable="true"
  		autowidth="true" 
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="username" 
    	    search="false" 
    	    index="username" 
    	    title="%{getText('user.username')}" 
    	    hidden="false"
    	   
    	    key="true"
    	    width="120"
    	    />
     <sjg:gridColumn 
 	    name="personName" 
 	    index="personName" 
 	    title="%{getText('person.name')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    />
 	    
 	    <sjg:gridColumn 
 	    name="deptName" 
 	    index="deptName" 
 	    title="%{getText('department.name')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    />
    

  </sjg:grid>
</div>
		<div id="roleUsers_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="roleUsers_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="roleUsers_gridtable_totals"></label>条</span>
				</div>

				<div id="roleUsers_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>