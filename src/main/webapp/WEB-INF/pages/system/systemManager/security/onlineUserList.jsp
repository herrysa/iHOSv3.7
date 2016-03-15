<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
function userGridReload(){
	var urlString = "getOnlineUserList";
	var onlineUserName = jQuery("#onlineUserName").val();
	var onlineUserPersonname = jQuery("#onlineUserPersonname").val();
	var onlineUserdeptId = jQuery("#onlineUserdeptId").val();

	urlString=urlString+"?filter_LIKES_username="+onlineUserName+"&filter_LIKES_person.name="+onlineUserPersonname+"&filter_LIKES_person.department.departmentId="+onlineUserdeptId;
	urlString=encodeURI(urlString);
	jQuery("#onlineuser_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
}
function logoutUser(){
	var sid = jQuery("#onlineuser_gridtable").jqGrid('getGridParam', 'selarrrow');
	if (sid == null || sid.length == 0) {
		alertMsg.error("<fmt:message key='list.selectNone'/>");
		return;
	}
	if (sid.length > 1) {
		alertMsg.error("<fmt:message key='list.selectNone'/>");
		return;
	} else {
		alertMsg.confirm("确认将该用户退出系统？", {
			okCall: function(){
				jQuery.ajax({
				    url: 'tichuUserById?id='+sid,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				    	 jQuery('#error').text(data.responseText);
				    },
				    success: function(data){
				    	var currentPage = jQuery("#onlineuser_gridtable").jqGrid('getGridParam', 'page');
						
				    	jQuery("#onlineuser_gridtable").trigger('reloadGrid',
							[ {
									page : currentPage
								} ]);
				        alert(data.message);
				    }
				});
				
			}
		});
		
	}
}
</script>
<label id="error"></label> 
<div class="page">
<!-- <div id="onlineuser_container">
			<div id="onlineuser_layout-center"
				class="pane ui-layout-center"> -->
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
		<form onsubmit="return navTabSearch(this);" action="userGridList"
			method="post" class="queryarea-form">
					<label class="queryarea-label"><fmt:message key='user.username' />：<input
								type="text" id="onlineUserName" class="input-small">
					</label>
					<label class="queryarea-label"><fmt:message key='person.name' />：<input
								type="text" id="onlineUserPersonname" class="input-small">
					</label>
					<label class="queryarea-label"><fmt:message key='department.name' />：
							<s:select id="onlineUserdeptId" list="departList" listKey="departmentId" listValue="name" headerKey="" headerValue="--"></s:select>
					</label>
					<div class="buttonActive" style="float: right;">
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


<s:url id="editurl" action="onlineuserGridEdit"/> 
<s:url id="remoteurl" action="getOnlineUserList"/> 
<div class="panelBar">
			<ul class="toolBar">
				<li><a class="logoutbutton" external="true" href="javaScript:logoutUser();" ><span><fmt:message
								key="button.tichuuser" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="onlineuser_gridtable_div" layoutH="95"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="onlineuser_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="onlineuser_gridtable_addTile">
				<fmt:message key="onlineuserNew.title"/>
			</label> 
			<label style="display: none"
				id="onlineuser_gridtable_editTile">
				<fmt:message key="onlineuserEdit.title"/>
			</label>
			<label style="display: none"
				id="onlineuser_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="onlineuser_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_onlineuser_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="onlineuser_gridtable" 
    	dataType="json" 
    	gridModel="users"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	pager="false" 
    	page="1"
    	multiselect="true"
  		multiboxonly="true"
  		
  		shrinkToFit="true"
  		autowidth="true"
		onCompleteTopics="onLoadSelect"
  		draggable="true"
  		prmNames="{id:'selIds'} "
    >
    <sjg:gridColumn 
    	    name="id" 
    	    search="false" 
    	    index="id" 
    	    title="%{getText('user.id')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
       <sjg:gridColumn 
 	    name="username" 
 	    index="username" 
 	    title="%{getText('user.username')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
 	           <sjg:gridColumn 
 	    name="personName" 
 	    index="personName" 
 	    title="%{getText('person.name')}" 
 	    sortable="false"
 	    
 	    />

    <sjg:gridColumn 
    	name="deptName" 
	    index="deptName" 
	    title="%{getText('department.name')}" 
	    sortable="true"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      /> 
      <sjg:gridColumn 
    	name="loginTime" 
	    index="loginTime" 
	    title="%{getText('user.loginTime')}" 
	    sortable="true"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      formatter='date'
	  formatoptions="{srcformat: 'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}"
	  align="center"
      />
      <sjg:gridColumn 
    	name="loginIp" 
	    index="loginIp" 
	    title="%{getText('user.loginIp')}" 
	    sortable="true"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      />

  </sjg:grid>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="onlineuser_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="onlineuser_gridtable_totals"></label>条</span>
		</div>

		<div id="onlineuser_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->