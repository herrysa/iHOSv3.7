<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/links.jsp"%>
<%@ include file="/common/dwzLinks.jsp"%> --%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="entityList.title" /></title>
<meta name="heading" content="<fmt:message key='entityList.heading'/>" />
<meta name="menu" content="EntityMenu" />

<script type="text/javascript">
    function entityGridReload() {
	var urlString = "searchEntityGridList";
	var entityNameTxt = jQuery("#entityNameTxt").val();
	var entityDescTxt = jQuery("#entityDescTxt").val();
	urlString = urlString + "?filter_LIKES_entityName=" + entityNameTxt
		+ "&filter_LIKES_entityDesc=" + entityDescTxt;
	urlString = encodeURI(urlString);
	jQuery("#searchEntity_gridtable").jqGrid('setGridParam', {
	    url : urlString,
	    page : 1
	}).trigger("reloadGrid");
    }
    function refreshGridCurrentPage() {
	var jq = jQuery('#searchEntity_gridtable');
	var currentPage = jq.jqGrid('getGridParam', 'page');
	jQuery('#searchEntity_gridtable').trigger('reloadGrid', [ {
	    page : currentPage
	} ]);
    }
    function addEntityRecord() {
	alert("add");
	var url = "editSearchEntity?popup=true";
	var winTitle = 'qqq';
	//popUpWindow(url, winTitle, "width=500");
	$.pdialog.open(url, '', winTitle, {
	    mask : false,
	    width : 650,
	    height : 400
	});
    }
    function editEntityRecord() {
	var sid = jQuery("#searchEntity_gridtable").jqGrid('getGridParam',
		'selarrrow');
	if (sid == null || sid.length == 0) {
	    //alert("<fmt:message key='list.selectNone'/>");
	    jQuery('div.#mybuttondialog').html(
		    "<fmt:message key='list.selectNone'/>");
	    jQuery('#mybuttondialog').dialog('open');
	    return;
	}
	if (sid.length > 1) {
	    //alert("<fmt:message key='list.selectMore'/>");
	    jQuery('div.#mybuttondialog').html(
		    "<fmt:message key='list.selectMore'/>");
	    jQuery('#mybuttondialog').dialog('open');
	    return;
	} else {
	    jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
	    var url = "editSearchEntity?popup=true&entityId=" + sid;
	    var winTitle = '<fmt:message key="entityNew.title"/>';
	    //popUpWindow(url, winTitle, "width=500");
	    $.pdialog.open(url, '', winTitle, {
		mask : false,
		width : 650,
		height : 400
	    });
	}
    }
    function okButton() {
	jQuery('#mybuttondialog').dialog('close');
    };
    datePick = function(elem) {
	jQuery(elem).datepicker({
	    dateFormat : "<fmt:message key='date.format'/>"
	});
	jQuery('#ui-datepicker-div').css("z-index", 2000);
    };
    jQuery(document)
	    .ready(
		    function() {
			jQuery
				.subscribe(
					'entityComplete',
					function(event, data) {
					    //alert("entityComplete");
					    if (jQuery(
						    "#searchEntity_gridtable")
						    .getDataIDs().length > 0) {
						jQuery(
							"#searchEntity_gridtable")
							.jqGrid(
								'setSelection',
								jQuery(
									"#searchEntity_gridtable")
									.getDataIDs()[0]);//默认选中第一行
					    }
					});
			jQuery.subscribe('rowselect', function(event, data) {
			    //alert("row select!")
			    var ret = jQuery("#searchEntity_gridtable").jqGrid(
				    'getRowData', event.originalEvent.id);
			    var dicId = ret["entityId"];
			    var urlString = "searchEntityClusterGridList";
			    urlString = urlString + "?entityId=" + dicId;
			    //alert("url String is: " +urlString);
			    jQuery("#searchEntityCluster_gridtable").jqGrid(
				    'setGridParam', {
					url : urlString,
					page : 1
				    }).trigger("reloadGrid");

			});
		    });
</script>
</head>

<div class="page">
<div class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
					<label class="queryarea-label">实体名称: <input type="text"
						id="entityNameTxt" />
					</label>
					<label class="queryarea-label">实体描述: <input type="text" id="entityDescTxt">
					</label>
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="entityGridReload()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
			</div>
		</div>
		</div>
	<div class="pageContent">
		<div id="searchEntityDiv" class="unitBox"
			style="float: left; display: block; overflow: auto; width: 40%;">
			<div class="panelBar">



				<ul class="toolBar">
					<li><a id="searchEntity_gridtable_add" class="addbutton"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a id="searchEntity_gridtable_del" class="delbutton"
						href="javaScript:" title="确定要删除吗?"><span>删除</span> </a></li>
					<li><a id="searchEntity_gridtable_edit" class="changebutton"
						href="javaScript:"><span><fmt:message key="button.edit" />
						</span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
			<div id="searchEntity_gridtable_div" layoutH="95"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchEntity;optId:entityId;width:650;height:400">
				<input type="hidden" id="searchEntity_gridtable_navTabId"
					value="${sessionScope.navTabId}" /> <label style="display: none"
					id="searchEntity_gridtable_addTile"> <fmt:message
						key="entityNew.title" />
				</label> <label style="display: none" id="searchEntity_gridtable_editTile">
					<fmt:message key="entityEdit.title" />
				</label>
				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />

				<s:url id="editurl" action="searchEntityGridEdit" />
				<s:url id="remoteurl" action="searchEntityGridList" />
				<div id="load_searchEntity_gridtable"
					class='loading ui-state-default ui-state-active'></div>
				<sjg:grid id="searchEntity_gridtable" dataType="json"
					gridModel="searchEntities" href="%{remoteurl}" editurl="%{editurl}"
					rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
					rownumbers="true" pager="false" page="1" pagerButtons="false"
					pagerInput="false" pagerPosition="right" navigator="false"
					navigatorAdd="false" navigatorEdit="false" navigatorDelete="false"
					navigatorDeleteOptions="{reloadAfterSubmit:true}"
					navigatorSearch="false"
					navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
					navigatorRefresh="false" multiselect="true" multiboxonly="true"
					resizable="true" height="200" draggable="true" autowidth="true"
					onCompleteTopics="onLoadSelect" onSelectRowTopics="rowselect"
					onGridCompleteTopics="entityComplete">
					<sjg:gridColumn name="entityId" search="false" index="entityId"
						title="%{getText('entity.entityId')}" hidden="false" key="true"
						width="50" />

					<sjg:gridColumn name="entityName" index="entityName"
						title="%{getText('entity.entityName')}" sortable="true"
						search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
						width="150" />
					<sjg:gridColumn name="entityDesc" index="entityDesc"
						title="%{getText('entity.entityDesc')}" sortable="true"
						search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
						width="200" />

				</sjg:grid>
			</div>

			<div id="searchEntity_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="searchEntity_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="searchEntity_gridtable_totals"></label>条
					</span>
				</div>

				<div id="searchEntity_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
		<div id="searchEntityClusterDiv" class="unitBox" style="margin-left: 40%;">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" id="searchEntityCluster_gridtable_add"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a class="delete" id="searchEntityCluster_gridtable_del"
						href="javaScript:" title="确定要删除吗?"><span>删除</span> </a></li>
					<li><a class="edit" id="searchEntityCluster_gridtable_edit"
						href="javaScript:"><span><fmt:message key="button.edit" />
						</span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
			<div id="searchEntityCluster_gridtable_div" layoutH="95"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchEntityCluster;optId:entityClusterId;fatherGrid:searchEntity_gridtable;extraParam:entityId;width:500;height:300">
				<input type="hidden" id="searchEntityCluster_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="searchEntityCluster_gridtable_addTile">
					<fmt:message key="searchEntityClustersNew.title" />
				</label> <label style="display: none"
					id="searchEntityCluster_gridtable_editTile"> <fmt:message
						key="searchEntityClustersEdit.title" />
				</label>

				<s:url id="detailRemoteUrl" action="searchEntityClusterGridList" />
				<s:url id="detailEditUrl" action="searchEntityClusterGridEdit" />
				<div id="load_searchEntityCluster_gridtable"
					class='loading ui-state-default ui-state-active'></div>
				<sjg:grid id="searchEntityCluster_gridtable"
					href="%{detailRemoteUrl}" gridModel="searchEntityClusters"
					dataType="json" editurl="%{detailEditUrl}" pager="false" page="1"
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
					navigatorRefresh="false" multiselect="true" multiselectWidth="20"
					multiboxonly="true" resizable="true" autowidth="true"
					onCompleteTopics="onLoadSelect">



					<sjg:gridColumn name="entityClusterId" search="false"
						index="searchEntityClusterId"
						title="%{getText('searchEntityClusters.searchEntityClustersId')}"
						hidden="true" sortable="false" width="50" key="true" />
					<sjg:gridColumn name="searchEntity.entityId" search="false"
						index="dictionary.dictionaryId"
						title="%{getText('dictionary.dictionaryId')}" hidden="true"
						formatter="integer" sortable="false" width="50" />
					<sjg:gridColumn name="clusterLevel" index="clusterLevel"
						title="%{getText('searchEntityCluster.clusterLevel')}"
						sortable="true" width="100" editrules="{required: true}" />
					<sjg:gridColumn name="expression" index="expression"
						title="%{getText('searchEntityCluster.expression')}"
						sortable="true" width="150" editrules="{required: true}" />
					<sjg:gridColumn name="priority" index="priority"
						title="%{getText('searchEntityCluster.priority')}" sortable="true"
						width="50" editrules="{required: true}" />
				</sjg:grid>
			</div>

			<div class="panelBar">
				<div class="pages">
					<span>显示</span> <select
						id="searchEntityCluster_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="searchEntityCluster_gridtable_totals"></label>条
					</span>
				</div>

				<div id="searchEntityCluster_gridtable_pagination"
					class="pagination" targetType="navTab" totalCount="200"
					numPerPage="20" pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
	</div>
</div>