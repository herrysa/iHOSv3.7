<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="roleList.title" />
</title>
<script type="text/javascript">
	function refreshGridCurrentPage() {
		var jq = jQuery('#role_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#role_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function addRecord() {
		var url = "editRole?popup=true";
		var winTitle = '<fmt:message key="personNew.title"/>';
		//popUpWindow(url, winTitle, "width=500");
		//navTab.openTab("addRole", url, { title:winTitle, flesh:true, data:{} });
		$.pdialog.open(url, 'addUser', winTitle, {mask:false,width:550,height:700});　
	}
	function editRecord() {
		var sid = jQuery("#role_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			alertMsg.error("<fmt:message key='list.selectMore'/>");
			return;
		} else {
			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "editRole?popup=true&roleId=" + sid;
			var winTitle = '<fmt:message key="roleNew.title"/>';
			//popUpWindow(url, winTitle, "width=500");
			navTab.openTab("editRole", url, { title:winTitle, external:true, flesh:true, data:{} });
		}
	}
	function delRoleRecord() {
		var sid = jQuery("#role_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}else{
			var row = jQuery("#role_gridtable").jqGrid('getRowData',sid);
			var roleType = row['roleType'];
			if(roleType=='1'){
				alertMsg.error('系统角色不能删除！');
				return ;
			}
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post("roleGridEdit?id="+sid+"&navTabId=role_gridtable&oper=del", {
					}, formCallBack, "json");
				}
			});
		}
	}
	function checkGridDeleteOperation(response, postdata) {
		var gridresponse = gridresponse || {};
		gridresponse = jQuery.parseJSON(response.responseText);
		var msg = gridresponse["gridOperationMessage"];
		// alert(msg);
		//jQuery("#gridinfo").html(msg);
		jQuery('div.#mybuttondialog').html(msg);
		jQuery('#mybuttondialog').dialog('open');
		return [ true, "" ];
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
	//setTimeout("getGridParam()","500");
	function reloadRoleTab(selectedSearchId) {
		var url = null;
		var selected;
		var i = 0;
		$("#roleTab").find("li").each(
				function() {
					if (i == 6) {
						url = "roleBaseInfo"
					} else if (i == 0) {
						url = "roleMenusList?popup=true&roleId="
								+ selectedSearchId;
					} 
					else if (i == 1) {
						url = "dataPrivilege?container=roleTabsContent&dataPrivilegeType=1&selectedId="
								+ selectedSearchId;
					}
					else if (i == 2) {
						url = "roleSearchEntityClusterList?popup=true&roleId="
							+ selectedSearchId;
					} else if (i == 3) {
						url = "buttonPrivList?popup=true&buttonType=1&roleId="
								+ selectedSearchId;
					}else if (i == 4) {
						url = "buttonPrivList?popup=true&buttonType=2&roleId="
							+ selectedSearchId;
					}else if( i == 5){
						url = "roleUsersList?popup=true&roleId="
							+ selectedSearchId;
					}
					
					$(this).find("a").eq(0).attr("href", url);
					if(jQuery(this).attr("class")=="selected"){
						selected = i;
						$(this).find("a").eq(0).trigger('click');
					}
					i++;
		});
		
		$("#background,#progressBar").hide();
	}
	
	var roleLayout;
		jQuery(document).ready(function() { 
			/* roleLayout = makeLayout({
				baseName: 'role', 
				tableIds: 'role_gridtable'
			}, null); */
			var roleChangeData = function(selectedSearchId) {
				reloadRoleTab(selectedSearchId);
			}
			roleLayout = makeLayout({
				baseName:'role',
				tableIds:'role_gridtable;roleTab',
				proportion:2,
				key:'id'
			},roleChangeData);
			//roleLayout.resizeAll();
			jQuery("#roleTabsContent").css('padding',0);
			/* jQuery.subscribe('rowselect', function(event, data) {
				//alert("row select!")
				var ret = jQuery("#role_gridtable").jqGrid('getRowData',event.originalEvent.id);
						var dicId = ret["id"];
						var urlString = "roleDataPrivilegeList";
						urlString = urlString + "?roleId=" + dicId;
						//alert("url String is: " +urlString);
						jQuery("#roleSearchEntityCluster_gridtable").jqGrid('setGridParam', {
							url : urlString,
							page : 1
						}).trigger("reloadGrid");

		   	}); */
			
    	});

		jQuery(document).ready(function() {
			var roleGrid = jQuery("#role_gridtable");
			roleGrid.jqGrid({
				url : "roleGridList",
				editurl : "roleGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel : [
					{name : 'id',index : 'id',align : 'left',width:60,label : '<s:text name="role.id" />',hidden : false,key : true},
					{name : 'chName',index : 'chName',align : 'left',width:200,label : '<s:text name="role.chName" />',hidden : false, sortable:true},
					{name : 'name',index : 'name',align : 'left',width:200,label : '<s:text name="role.name" />',hidden : false, sortable:true},
					{name : 'roleType',index : 'roleType',align : 'left',width:60,label : '<s:text name="role.type" />',hidden : false, sortable:true, formatter:"select",edittype:"select", editoptions:{value:"1:系统;0:自定义"}},
					{name : 'description',index : 'description',align : 'left',width:300,label : '<s:text name="role.description" />',hidden : false,sortable:true}
				],
				jsonReader : {
					root : "roles", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname : '',
				viewrecords : true,
				sortorder : 'asc',
				height : 300,
				gridview : true,
				rownumbers : true,
				loadui : "disable",
				multiselect : true,
				multiboxonly : true,
				shrinkToFit : true,
				autowidth : true,
				onSelectRow : function(rowid) {
					setTimeout(function() {
						roleLayout.optClick();
					},100);
				},
				ondblClickRow:function() {
					roleLayout.optDblclick();
				},
				gridComplete : function() {
			 		//gridContainerResize('role','div');
					var dataTest = {
						"id" : "role_gridtable"
					};
					jQuery.publish("onLoadSelect",dataTest, null);
				}
			});
			jQuery(roleGrid).jqGrid('bindKeys');
		});
		function roleFormCallBack(data){
			roleLayout.toggleSouth();
			formCallBack(data);
		}
</script>
</head>

<div class="page">
	<div class="pageContent" style="overflow:hidden">
	<div id="role_container">
			<div id="role_layout-center"
				class="pane ui-layout-center">
	<%-- 	<sj:dialog id="mybuttondialog"
			buttons="{'OK':function() { okButton(); }}" autoOpen="false"
			modal="true" title="%{getText('messageDialog.title')}" />

		<s:url id="editurl" action="roleGridEdit" />
		<s:url id="remoteurl" action="roleGridList" /> --%>

		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="role_gridtable_add" class="addbutton" href="javaScript:"><span><fmt:message key="button.add"/></span></a></li>
				<li><a class="delbutton" href="javaScript:delRoleRecord()" title="确定要删除吗?"><span>删除</span></a></li>
				<li><a id="role_gridtable_edit" class="changebutton" href="javaScript:"><span><fmt:message key="button.edit"/></span></a></li>
				<li><a class="settingbutton"
								 href="javaScript:roleLayout.optDblclick();"><span>赋权</span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span></a></li> -->
			</ul>
		</div>
		<%-- <sj:submit id="add_button" value="%{getText('button.add')}"
			onClickTopics="addRowRecord" button="true" onclick="addRecord();" />
		<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"
			button="true" onclick="editRecord();" /> --%>
		<div id="role_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="edit_URL:editRole?popup=true&roleId=;width:600;height:200">
			<input type="hidden" id="role_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="role_gridtable_addTile">
				<fmt:message key="roleNew.title"/>
			</label> 
			<label style="display: none"
				id="role_gridtable_editTile">
				<fmt:message key="roleEdit.title"/>
			</label>
			<label style="display: none"
				id="role_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="role_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
		<div id="load_role_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		<table id="role_gridtable"></table>
		<%-- <sjg:grid 
    	id="role_gridtable" 
    	dataType="json" 
    	gridModel="roles"
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
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridDeleteOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{showQuery:true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		shrinkToFit="false"
		autowidth="false"
		onCompleteTopics="onLoadSelect"
  		draggable="true"
  		hidegrid="false"
  		onclick="roleLayout.optClick();"
  		ondblclick="roleLayout.optDblclick();"
    >

			<sjg:gridColumn name="id" search="false" index="id"
				title="%{getText('role.id')}" hidden="false" key="true" width="60"/>
			<sjg:gridColumn name="chName" index="chName"
				title="%{getText('role.chName')}" sortable="true" search="true" width="200"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />

			<sjg:gridColumn name="name" index="name"
				title="%{getText('role.name')}" sortable="true" search="true" width="200"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />
			<sjg:gridColumn name="roleType" index="roleType"
				title="%{getText('role.type')}" sortable="true" search="true" width="60"
				searchoptions="{sopt:['eq','ne','cn','bw']}" 
				editable="true" formatter="select"
				edittype="select" editoptions="{value:'1:系统;0:自定义'}"/>
			<sjg:gridColumn name="description" index="description"
				title="%{getText('role.description')}" sortable="true" search="true" width="300"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />

		</sjg:grid> --%>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<!-- <select class="combox" id="role_gridtable_numPerPage"> -->
			<select id="role_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="role_gridtable_totals"></label>条</span>
		</div>
		
		<div id="role_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<div id="role_layout-south"	class="pane ui-layout-south"> 
<div id="" class="panelBar">
						<ul class="toolBar">

							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
							<li  style="float: right;"><a id="role_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li  style="float: right;"><a  class="foldbutton" id="role_fold"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li  style="float: right"><a  class="unfoldbutton" id="role_unfold"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
</div>
<div class="tabs" currentIndex="0" eventType="click" id="roleTab" tabcontainer="role_layout-south" extraHeight=28>
						<div class="tabsHeader">
							<div id="" class="tabsHeaderContent">
								<ul>
									<!-- <li><a href="roleBaseInfo" class="j-ajax"><span>基本信息
										</span>
									</a>
									</li> -->
									<li><a href="roleMenusList?popup=true" class="j-ajax"><span>角色模块
										</span>
									</a>
									</li>
									<li><a href="dataPrivilege?container=roleTabsContent&dataPrivilegeType=1" class="j-ajax"><span>数据权限
										</span>
									</a>
									</li>
									<li><a href="roleSearchEntityClusterList?popup=true" class="j-ajax"><span>实体权限
										</span>
									</a>
									</li>
									<li><a href="buttonPrivList?popup=true&buttonType=1" class="j-ajax"><span>search禁用按钮
										</span>
									</a>
									</li>
									<li><a href="buttonPrivList?popup=true&buttonType=2" class="j-ajax"><span>模块禁用按钮
										</span>
									</a>
									</li>
									<li><a href="roleUsersList?popup=true" class="j-ajax"><span>角色用户
										</span>
									</a>
									</li>
								</ul>
							</div>
						</div>
						<div id="roleTabsContent" class="tabsContent"
							style="height: 250px;">
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
							<div></div>
						</div>
						<div class="tabsFooter">
							<div class="tabsFooterContent"></div>
						</div>
					</div>
<%-- <div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" id="roleSearchEntityCluster_gridtable_add"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a class="delete" id="roleSearchEntityCluster_gridtable_del"
						href="javaScript:" title="确定要删除吗?"><span>删除</span> </a></li>
					<li><a class="edit" id="roleSearchEntityCluster_gridtable_edit"
						href="javaScript:"><span><fmt:message
									key="button.edit" /> </span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
		<div id="roleSearchEntityCluster_gridtable_div" layoutH="57"
				class="grid-wrapdiv"
				buttonBar="base_URL:editRoleDataPrivilege;optId:entityClusterId;fatherGrid:role_gridtable;extraParam:roleId;width:400;height:300">
				<input type="hidden" id="roleSearchEntityCluster_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="roleSearchEntityCluster_gridtable_addTile">
					<fmt:message key="searchEntityClustersList.title" />
				</label> <label style="display: none"
					id="roleSearchEntityCluster_gridtable_editTile"> <fmt:message
						key="searchEntityClustersList.title" />
				</label>
					<s:url id="detailRemoteUrl" action="roleDataPrivilegeList" />
			<s:url id="detailEditUrl" action="roleDataPrivilegeGridEdit" />
			<div id="load_roleSearchEntityCluster_gridtable" class='loading ui-state-default ui-state-active'></div>
				<sjg:grid id="roleSearchEntityCluster_gridtable" href="%{detailRemoteUrl}"
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
					navigatorRefresh="false" multiselect="true" multiselectWidth="20"
					multiboxonly="true" resizable="true" autowidth="true"
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
					<span>显示</span> <select id="roleSearchEntityCluster_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="roleSearchEntityCluster_gridtable_totals"></label>条
					</span>
				</div>

				<div id="roleSearchEntityCluster_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>

--%>
</div> 
</div>
</div>
</div>