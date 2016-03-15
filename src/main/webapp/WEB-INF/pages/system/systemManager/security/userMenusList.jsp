<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<head>
    <title><fmt:message key="searchOptionList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchOptionList.heading'/>"/>
    <meta name="menu" content="SearchOptionMenu"/>
    
    <script type="text/javascript">
    	function editUserMenu(){
    		var userid = "<s:property value='#parameters.id'/>";
			var url = "getPrivileges?type=allForTab&roleName="
			+ "&userId="
			+ userid;
			$.pdialog.open(url, "extraRole", "设置用户模块", {mask:true,height:600});　
    	}
	</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
				<ul class="toolBar">
					<li><a class="settingbutton"
						href="javaScript:editUserMenu();"><span>设置模块</span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
<div id="userMenus_gridtable_div" extraHeight=83 extraWidth=10 tablecontainer="userTabsContent"
				class="grid-wrapdiv"
				buttonBar="">

<s:url id="editurl" action=""/> 
<s:url id="remoteurl" action="userMenuGridList">
<s:param name="id" value="#parameters.id"></s:param>
</s:url> 

<div id="load_userMenus_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="userMenus_gridtable" 
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
    	    name="menuId" 
    	    search="false" 
    	    index="menuId" 
    	    title="%{getText('menu.menuId')}" 
    	    hidden="true"
    	   
    	    key="true"
    	    width="120"
    	    />
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
		<div id="userMenus_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="userMenus_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="userMenus_gridtable_totals"></label>条</span>
				</div>

				<div id="userMenus_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>