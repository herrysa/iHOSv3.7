<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="dataSourceDefineList.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataSourceDefineList.heading'/>" />
<meta name="menu" content="DataSourceDefineMenu" />

<script type="text/javascript">
    	$("#background,#progressBar").hide();
	    function setDataSourceDefineModel(postdata, formid){
	  	  //alert("beforesubmit");
	  	   postdata['model.dataSourceDefineId'] = postdata['dataSourceDefineId'];
	  	   postdata['model.dataSourceName'] = postdata['dataSourceName'];
	  	   postdata['model.dataSourceType'] = postdata['dataSourceType'];
	  	   postdata['model.note'] = postdata['note'];
	      return [true,""];
	    }
		
		function checkGridOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		   // alert(msg);
		   jQuery("#gridinfo").html(msg);
	        return [true,""];   
		}
    
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		
    	
	</script>
	<style>
		.grid-wrapdiv{
			margin-left: 2px; 
			margin-top: 2px; 
			overflow: hidden !important
		}
	</style>
</head>


<div class="page">
	<div class="pageContent">
	<!-- <div id="dataSourceDefine_container">
			<div id="dataSourceDefine_layout-center"
				class="pane ui-layout-center">
 -->
		<s:url id="editurl" action="dataSourceDefineGridEdit" />
		<s:url id="remoteurl" action="dataSourceDefineGridList" />


		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="dataSourceDefine_gridtable_add" class="addbutton"
					 href="javaScript:">
					<span>
					<fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="dataSourceDefine_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="dataSourceDefine_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="dataSourceDefine_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="base_URL:editDataSourceDefine||deleteDataSourceDefine;optId:dataSourceDefineId;width:650;height:350">
			<input type="hidden" id="dataSourceDefine_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="dataSourceDefine_gridtable_addTile">
				<fmt:message key="dataSourceDefineNew.title" />
			</label> 
			<label style="display: none"
				id="dataSourceDefine_gridtable_editTile">
				<fmt:message key="dataSourceDefineEdit.title" />
			</label>
			<label style="display: none"
				id="dataSourceDefine_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="dataSourceDefine_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_dataSourceDefine_gridtable" class='loading ui-state-default ui-state-active'></div>
			<sjg:grid id="dataSourceDefine_gridtable" dataType="json"
				gridModel="dataSourceDefines" 
								href="%{remoteurl}"  
				editurl="%{editurl}" rowList="10,15,20,30,40,50,60,70,80,90,100"
				rowNum="20" rownumbers="true" pager="false" page="1"
				pagerButtons="false" pagerInput="false" pagerPosition="right"
				navigator="false" navigatorAdd="false" navigatorEdit="false"
				navigatorDelete="false"
				navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
				navigatorSearch="false"
				navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
				navigatorRefresh="false" multiselect="true" multiboxonly="true"
				resizable="true" onclick="dataSourceDefineLayout.optClick();" draggable="true"
				shrinkToFit="true" autowidth="true" onCompleteTopics="onLoadSelect"
				gridview="true" cssStyle="" 
				>
				<sjg:gridColumn name="dataSourceDefineId" search="false"
					index="dataSourceDefineId"
					title="%{getText('dataSourceDefine.dataSourceDefineId')}"
					hidden="true" formatter="integer" sortable="false" key="true" width="100"/>

				<sjg:gridColumn name="dataSourceName" index="dataSourceName"
					title="%{getText('dataSourceDefine.dataSourceName')}"
					sortable="true" search="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" width="100"/>
				<sjg:gridColumn name="dataSourceType.dataSourceTypeName"
					index="dataSourceType.dataSourceTypeName"
					title="%{getText('dataSourceType.dataSourceTypeName')}"
					sortable="true" search="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
				<sjg:gridColumn name="url" index="url"
					title="%{getText('dataSourceDefine.url')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="300"/>
				<sjg:gridColumn name="userName" index="userName"
					title="%{getText('dataSourceDefine.userName')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="60"/>
				<%-- <sjg:gridColumn name="password" index="password"
					title="%{getText('dataSourceDefine.password')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="80"
					/> --%>


				<sjg:gridColumn name="dataSourceType.isNeedFile"
					index="dataSourceType.isNeedFile"
					title="%{getText('dataSourceType.isNeedFile')}" sortable="true"
					edittype="checkbox" formatter="checkbox" search="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="60"/>
				<sjg:gridColumn name="dataSourceType.fileType"
					index="dataSourceType.fileType"
					title="%{getText('dataSourceType.fileType')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="60"/>
				<sjg:gridColumn name="note" index="note"
					title="%{getText('dataSourceDefine.note')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
			</sjg:grid>
		</div>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="dataSourceDefine_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="dataSourceDefine_gridtable_totals"></label>条</span>
		</div>

		<div id="dataSourceDefine_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	</div>
<!-- </div>
</div> -->