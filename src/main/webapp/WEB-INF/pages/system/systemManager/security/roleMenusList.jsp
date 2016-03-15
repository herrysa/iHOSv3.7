<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<head>
    <title><fmt:message key="searchOptionList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchOptionList.heading'/>"/>
    <meta name="menu" content="SearchOptionMenu"/>
    
    <script type="text/javascript">
    	function editRoleMenu(){
    		var sid = "<s:property value='#parameters.roleId'/>";
    		var url = "editRole?popup=true&roleId="+sid+"&editType=2&navTabId=roleMenus_gridtable";
    		$.pdialog.open(url, 'editRoleMenu', "设置角色模块", {mask:false,width:600,height:500});　
    	}
		/* jQuery(document).ready(function() { 
			var roleTabsContentW = jQuery("#roleTabsContent").width();
			var roleTabsContentH = jQuery("#roleTabsContent").height();
			setTimeout(function(){
				jQuery("#roleMenus_gridtable").jqGrid('setGridHeight',roleTabsContentH-73);
			},100);
			setTimeout(function(){
				jQuery("#roleMenus_gridtable").jqGrid('setGridWidth',roleTabsContentW);
			},100);
    	}); */
	</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
				<ul class="toolBar">
					<li><a class="settingbutton"
						href="javaScript:editRoleMenu();"><span>模块设置</span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
<div id="roleMenus_gridtable_div"  extraHeight=83 extraWidth=10 tablecontainer="roleTabsContent"
				class="grid-wrapdiv"
				buttonBar="">

<s:url id="editurl" action=""/> 
<s:url id="remoteurl" action="roleMenuGridList">
<s:param name="roleId" value="#parameters.roleId"></s:param>
</s:url> 

<div id="load_roleMenus_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="roleMenus_gridtable" 
    	dataType="json" 
    	gridModel="menus"
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
		navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="flase"
  		multiboxonly="false"
  		resizable="true"
  		draggable="true"
  		autowidth="true" 
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="subSystemName" 
    	    search="false" 
    	    index="subSystemName" 
    	    title="%{getText('menu.subSystem')}" 
    	    hidden="false"
    	    width="120"
    	    />
    <sjg:gridColumn 
    	    name="menuName" 
    	    search="false" 
    	    index="menuName" 
    	    title="%{getText('menu.menuName')}" 
    	    hidden="false"
    	   
    	    key="true"
    	    width="120"
    	    />
     <sjg:gridColumn 
 	    name="parentName" 
 	    index="parentName" 
 	    title="%{getText('menu.parent')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    />
    

  </sjg:grid>
</div>
		<div id="roleMenus_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="roleMenus_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="roleMenus_gridtable_totals"></label>条</span>
				</div>

				<div id="roleMenus_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>