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
			var searchTabsContentW = jQuery("#userTabsContent").width();
			var searchTabsContentH = jQuery("#userTabsContent").height();
			setTimeout(function(){
				jQuery("#userSearchEntityCluster_gridtable").jqGrid('setGridHeight',searchTabsContentH-53);
			},100);
			setTimeout(function(){
				jQuery("#userSearchEntityCluster_gridtable").jqGrid('setGridWidth',searchTabsContentW);
			},100);
    	}); */
	</script>
</head>

<div class="page">
	<div class="pageContent">
<%-- <div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" id="userSearchEntityCluster_gridtable_add"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a class="delete" id="userSearchEntityCluster_gridtable_del"
						href="javaScript:" title="确定要删除吗?"><span>删除</span> </a></li>
					<li><a class="edit" id="userSearchEntityCluster_gridtable_edit"
						href="javaScript:"><span><fmt:message
									key="button.edit" /> </span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div> --%>
		<div id="userSearchEntityCluster_gridtable_div" extraHeight=55 extraWidth=10 tablecontainer="userTabsContent"
				class="grid-wrapdiv"
				buttonBar="base_URL:editRoleDataPrivilege;optId:entityClusterId;fatherGrid:role_gridtable;extraParam:roleId;width:400;height:300">
				<input type="hidden" id="userSearchEntityCluster_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="userSearchEntityCluster_gridtable_addTile">
					<fmt:message key="searchEntityClustersList.title" />
				</label> <label style="display: none"
					id="userSearchEntityCluster_gridtable_editTile"> <fmt:message
						key="searchEntityClustersList.title" />
				</label>
					<s:url id="detailRemoteUrl" action="userRoleDataPrivilegeList">
					<s:param name="userId" value="#parameters.userId"></s:param>
					</s:url>
			<s:url id="detailEditUrl" action="roleDataPrivilegeGridEdit" />
			<div id="load_userSearchEntityCluster_gridtable" class='loading ui-state-default ui-state-active'></div>
				<sjg:grid id="userSearchEntityCluster_gridtable" href="%{detailRemoteUrl}"
					gridModel="searchEntityClusters" dataType="json"
					editurl="%{detailEditUrl}" 
					
					pager="false" page="1"
					pagerButtons="false" pagerInput="false" pagerPosition="center"
					rowList="5,10,15,20,25,30,35,40,45,50" rowNum="20"
					rownumbers="true" navigator="false" navigatorAdd="false"
					navigatorAddOptions="{reloadAfterSubmit:true,beforeSubmit:setSearchEntityClusterModel,afterSubmit:detailReload}"
					navigatorEdit="false"
					navigatorEditOptions="{reloadAfterSubmit:true,beforeSubmit:setSearchEntityClusterModel,afterSubmit:detailReload}"
					navigatorDelete="false"
					navigatorDeleteOptions="{reloadAfterSubmit:true}"
					navigatorSearch="false"
					navigatorSearchOptions="{sopt:['cn','bw','eq'],multipleSearch:true,  showQuery: true}"
					navigatorView="false" navigatorViewOptions=""
					navigatorRefresh="false" multiselect="false" multiselectWidth="20"
					multiboxonly="false" resizable="true" autowidth="true"
					onCompleteTopics="onLoadSelect"
					>



					<sjg:gridColumn name="entityClusterId" search="false"
						index="searchEntityClusterId"
						title="%{getText('searchEntityClusters.searchEntityClustersId')}"
						hidden="true" formatter="integer" sortable="false" width="50"
						key="true" />
					<sjg:gridColumn name="searchEntity.entityId" search="false"
						index="dictionary.dictionaryId"
						title="%{getText('dictionary.dictionaryId')}" hidden="true"
						formatter="integer" sortable="false" width="50" />
						<sjg:gridColumn name="searchEntity.entityName" search="false"
						index="dictionary.dictionaryId"
						title="%{getText('entity.entityName')}" hidden="false"
						 sortable="false" width="50" />
					<sjg:gridColumn name="clusterLevel" index="clusterLevel"
						title="%{getText('searchEntityCluster.clusterLevel')}" sortable="true"
						width="100" editrules="{required: true}" />
					<sjg:gridColumn name="expression" index="expression"
						title="%{getText('searchEntityCluster.expression')}" sortable="true"
						width="100" editrules="{required: true}" />
					<sjg:gridColumn name="priority" index="priority"
						title="%{getText('searchEntityCluster.priority')}" sortable="true" hidden="true"
						width="100" editrules="{required: true}" />
				</sjg:grid>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="userSearchEntityCluster_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="userSearchEntityCluster_gridtable_totals"></label>条
					</span>
				</div>

				<div id="userSearchEntityCluster_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>