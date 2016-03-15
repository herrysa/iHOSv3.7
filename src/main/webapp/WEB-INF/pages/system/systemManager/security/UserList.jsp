<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="userList.title" />
</title>
<meta name="heading" content="<fmt:message key='userList.heading'/>" />
<meta name="menu" content="UserMenu" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
//jQuery("#user_gridtable_pagination").attr("currentPage","2");

	function refreshGridCurrentPage() {
		var jq = jQuery('#user_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#user_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function addRecord() {
		var url = "ieditUser?popup=true&from=list";
		var winTitle = '<fmt:message key="userNew.title"/>';
		//navTab.openTab("addUser", url, { title:winTitle, flesh:true, data:{} });
		$.pdialog.open(url, 'addUser', winTitle, {mask:false,width:550,height:700});　
	}
	function delRecord() {
		var sid = jQuery("#user_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}else{
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post("ajaxDone.html", {
						accountId: accountId
					}, DWZ.ajaxDone, "json");
				}
			});
		}
	}
	function editRecord() {
		var sid = jQuery("#user_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		} else {
			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "ieditUser?from=list&popup=true&id=" + sid;
			var winTitle = '<fmt:message key="userNew.title"/>';
			//popUpWindow(url, winTitle, "width=650");
			//navTab.openTab("editUser", url, { title:winTitle, external:true, flesh:true, data:{} });

			$.pdialog.open(url, "userEdit", winTitle, {height:500,mask:true});　
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
	function userGridReload() {
		var urlString = "userGridList";
		var departmentIdTxt = jQuery("#userNameTxt").val();
		var personnameTxt = jQuery("#personnameTxt").val();
		var userDeptIdTxt = jQuery("#userDeptId").val();
		var disabledTxt = jQuery("#enabledTxt").val();
		//var disabledText = jQuery('input:checkbox:checked').val()
		/* 			var allVals = [];
		 jQuery('#disabledText :checked').each(function() {
		 allVals.push(jQuery(this).val());
		 }); */
		//jQuery('#t').val(allVals)

		urlString = urlString + "?filter_LIKES_username=" + departmentIdTxt
				+ "&filter_LIKES_person.name=" + personnameTxt
				+ "&filter_LIKES_person.department.departmentId=" + userDeptIdTxt
				+ "&filter_EQB_enabled=" + disabledTxt;
		urlString = encodeURI(urlString);
		jQuery("#user_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	
	function reloadUserTab(selectedSearchId) {
		var url = null;
		var selected;
		var i = 0;
		$("#userTab").find("li").each(
				function() {
					if (i == 0) {
						url = "userBaseInfo"
					} else if (i == 1) {
						url = "userMenusList?popup=true&id="
								+ selectedSearchId;
						
					}
					else if(i == 2){
						url = "dataPrivilege?container=userTabsContent&dataPrivilegeType=2&selectedId="
							+ selectedSearchId;
					}
					else if (i == 3) {
						url = "userSearchEntityClusterList?popup=true&userId="
								+ selectedSearchId;
					}else if (i == 4) {
						url = "buttonPrivUserList?popup=true&buttonType=1&userId=" 
							+ selectedSearchId;
					}else if (i == 5){
						url = "buttonPrivUserList?popup=true&buttonType=2&userId=" 
							+ selectedSearchId;
					}else if (i == 6) {
						url = "userRolesList?popup=true&id=" 
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
	
	var userLayout;
	jQuery(document).ready(function() { 
		var userChangeData = function(selectedSearchId) {
			reloadUserTab(selectedSearchId);
		}
		userLayout = makeLayout({
			baseName: 'user', 
			tableIds:'user_gridtable;userTab', 
			proportion:2,
			key:'id'
		}, userChangeData);
		//userLayout.resizeAll();
		jQuery("#userTabsContent").css('padding',0);
		
		jQuery("#userDeptName").autocomplete(
				"autocomplete",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						//alert(test.dicList.length)
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : data[i].departmentId + ","
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
									value : data[i].name,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : " leaf=true and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#userDeptName").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#userDeptId").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		});
		
		
		
	});
	jQuery(document).ready(function() {
		var userGrid = jQuery("#user_gridtable");
		userGrid.jqGrid({
			url : "userGridList",
			editurl : "userGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'id',index : 'id',align : 'left',width:60,label : '<s:text name="user.id" />',hidden : false,key : true,formatter:'integer'},
				{name : 'username',index : 'username',align : 'left',width:100,label : '<s:text name="user.username" />',hidden : false, sortable:true},
				{name : 'personName',index : 'personName',align : 'left',width:150,label : '<s:text name="person.name" />',hidden : false, sortable:false},
				{name : 'deptName',index : 'deptName',align : 'left',width:200,label : '<s:text name="user.dept" />',hidden : false, sortable:false},
				{name : 'enabled',index : 'enabled',align : 'center',width:50,label : '<s:text name="user.enabled" />',hidden : false,sortable:true,formatter:'checkbox',edittype:'checkbox'}
			],
			jsonReader : {
				root : "users", // (2)
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
					userLayout.optClick();
				},100);
			},
			ondblClickRow:function() {
				userLayout.optDblclick();
			},
			gridComplete : function() {
		 		//gridContainerResize('user','div');
				var dataTest = {
					"id" : "user_gridtable"
				};
				jQuery.publish("onLoadSelect",dataTest, null);
			}
		});
		jQuery(userGrid).jqGrid('bindKeys');
	}); 
	function userFormCallBack(data){
		userLayout.closeSouth();
		formCallBack(data);
	}
	function initPassword(){
		var userId = jQuery("#user_gridtable").jqGrid('getGridParam','selarrrow');
		$.ajax({
		    url: 'initPassword',
		    type: 'post',
		    data:{id:userId},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    	alert("error");
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        formCallBack(data);
		    }
		});
	}
</script>
<style>
	.hideoverflow{
		overflow:hidden !important
	}
</style>
</head>

<div class="page">
	<div id="user_container">
		<div id="user_layout-center" class="pane ui-layout-center">
			<div class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form onsubmit="return navTabSearch(this);" action="userGridList"
							method="post" class="queryarea-form">
							<label class="queryarea-label"> <fmt:message
									key='user.username' />：<input type="text" id="userNameTxt"
								class="input-small">
							</label> <label class="queryarea-label"> <fmt:message
									key='person.name' />：<input type="text" id="personnameTxt"
								class="input-small">
							</label> <label class="queryarea-label"> <fmt:message
									key='user.dept' />：<input id="userDeptName" value="拼音/汉字/编码"
								size="14" onfocus="clearInput(this,jQuery('#userDeptId'))"
								class="defaultValueStyle"
								onblur="setDefaultValue(this,jQuery('#userDeptId'))"
								onkeydown="setTextColor(this)" /> <s:hidden id="userDeptId" />
							</label> <label class="queryarea-label"><fmt:message
									key='user.enabled' />：<appfuse:singleSelect
									htmlFieldName="enabledTxt" paraDisString="无效;有效"
									paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
							</label>
							<div class="buttonActive" style="float:right;">
								<div class="buttonContent">
									<button type="button" onclick="userGridReload()">查询</button>
								</div>
							</div>
							<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
						</form>
					</div>
				</div>
			</div>
			<div class="pageContent">
				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />

				<s:url id="editurl" action="userGridEdit" />
				<s:url id="remoteurl" action="userGridList" />


				<div class="panelBar">
					<ul class="toolBar">
						<li><a id="user_gridtable_add" class="addbutton"
							external="true" href="javaScript:"><span><fmt:message
										key="button.add" /> </span> </a></li>
						<li><a id="user_gridtable_del" class="delbutton"
							external="true" href="javaScript:" title="确定要删除吗?"><span>删除</span>
						</a></li>
						<li><a id="user_gridtable_edit" class="changebutton"
							external="true" href="javaScript:"><span><fmt:message
										key="button.edit" /> </span> </a></li>
						<li><a class="settingbutton" external="true"
							href="javaScript:userLayout.optDblclick();"><span>赋权
							</span> </a></li>
						<li><a class="initbutton" external="true"
							href="javaScript:initPassword();"><span>初始化密码 </span> </a>
						</li>
						<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
					</ul>
				</div>
				<div id="user_gridtable_div" layoutH="95" class="grid-wrapdiv"
					buttonBar="base_URL:ieditUser;optId:id;width:550;height:450">
					<input type="hidden" id="user_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="user_gridtable_addTile"> <fmt:message
							key="userNew.title" />
					</label> <label style="display: none" id="user_gridtable_editTile">
						<fmt:message key="userEdit.title" />
					</label> <label style="display: none" id="user_gridtable_selectNone">
						<fmt:message key='list.selectNone' />
					</label> <label style="display: none" id="user_gridtable_selectMore">
						<fmt:message key='list.selectMore' />
					</label>
					<div id="load_user_gridtable"
						class='loading ui-state-default ui-state-active' style="display:none;"></div>
						<table id="user_gridtable"></table>
					<%-- <sjg:grid id="user_gridtable" dataType="json" gridModel="users"
						href="%{remoteurl}" editurl="%{editurl}"
						rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
						rownumbers="true" pager="false" page="1" multiselect="true"
						multiboxonly="true" shrinkToFit="false" autowidth="false"
						onCompleteTopics="onLoadSelect" draggable="true"
						prmNames="{id:'selIds'} " onclick="userLayout.optClick();"
						ondblclick="userLayout.optDblclick();">
						<sjg:gridColumn name="id" search="false" index="id"
							title="%{getText('user.id')}" hidden="false" formatter="integer"
							key="true" width="60" />
						<sjg:gridColumn name="username" index="username"
							title="%{getText('user.username')}" sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="100" />
						<sjg:gridColumn name="personName" index="personName"
							title="%{getText('person.name')}" sortable="false" width="150" />

						<sjg:gridColumn name="deptName" index="deptName"
							title="%{getText('user.dept')}" sortable="false" width="200" />

						<sjg:gridColumn name="enabled" index="enabled"
							title="%{getText('user.enabled')}" sortable="true"
							edittype="checkbox" formatter="checkbox" search="true"
							searchoptions="{sopt:['eq','ne']}" editrules="{required: true}"
							width="50" />

					</sjg:grid> --%>
				</div>
				<div id="user_gridtable_footBar" class="panelBar">
					<div class="pages">
						<span>显示</span> <select id="user_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label id="user_gridtable_totals"></label>条
						</span>
					</div>

					<div id="user_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>

				</div>
			</div>
		</div>
		<div id="user_layout-south" class="hideoverflow pane ui-layout-south">
			<div id="" class="panelBar">
				<ul class="toolBar">

					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
					<li style="float: right;"><a id="user_close"
						class="closebutton" href="javaScript:"><span><fmt:message
									key="button.close" /> </span> </a></li>

					<li class="line" style="float: right">line</li>
					<li style="float: right;"><a id="user_fold" class="foldbutton"
						href="javaScript:"><span><fmt:message key="button.fold" />
						</span> </a></li>
					<li class="line" style="float: right">line</li>
					<li style="float: right"><a id="user_unfold"
						class="unfoldbutton" href="javaScript:"><span><fmt:message
									key="button.unfold" /> </span> </a></li>
				</ul>
			</div>
			<div class="tabs" currentIndex="0" eventType="click" id="userTab"
				tabcontainer="user_layout-south" extraHeight=28 extraWidth=0>
				<div class="tabsHeader">
					<div id="" class="tabsHeaderContent">
						<ul>
							<li><a href="userBaseInfo" class="j-ajax"><span>基本信息
								</span> </a></li>
							<li><a href="userMenusList?popup=true" class="j-ajax"><span>用户模块
								</span> </a></li>
							<li><a
								href="dataPrivilege?container=userTabsContent&dataPrivilegeType=2"
								class="j-ajax"><span>数据权限 </span> </a></li>
							<li><a href="userSearchEntityClusterList?popup=true"
								class="j-ajax"><span>实体权限 </span> </a></li>
							<li><a href="buttonPrivUserList?popup=true&buttonType=1"
								class="j-ajax"><span>search禁用按钮 </span> </a></li>
							<li><a href="buttonPrivUserList?popup=true&buttonType=2"
								class="j-ajax"><span>模块禁用按钮 </span> </a></li>
							<li><a href="userRolesList?popup=true" class="j-ajax"><span>用户角色
								</span> </a></li>
						</ul>
					</div>
				</div>
				<div id="userTabsContent" class="tabsContent" style="height: 500px;">
					<div></div>
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
		</div>
	</div>
</div>