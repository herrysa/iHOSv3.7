<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
function globalparamGridReload(){
	var urlString = "globalparamGridList";
	var paramKeyTxt = jQuery("#paramKeyTxt").val();
	var paramValueTxt = jQuery("#paramValueTxt").val();
	var descriptionTxt = jQuery("#descriptionTxt").val();
	var subSystemTxt = jQuery("#subSystemTxt").val();

	urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
	urlString=encodeURI(urlString);
	jQuery("#globalparam_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
}
/* var globalparamLayout;
jQuery(document).ready(function() { 
	globalparamLayout = makeLayout({
		baseName: 'globalparam', 
		tableIds: 'globalparam_gridtable'
	}, null);
	//globalparamLayout.resizeAll();
  	}); */
</script>
 
<div class="page">
<!-- <div id="globalparam_container">
			<div id="globalparam_layout-center"
				class="pane ui-layout-center"> -->
	<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td style="padding-right:10px"><fmt:message key='globalParam.paramKey'/>：
							<input type="text"	id="paramKeyTxt" size="15">
						</td>
						<td style="padding-right:10px"><fmt:message key='globalParam.paramValue'/>：
						 	<input type="text"	id="paramValueTxt" size="15">
						 </td>
						<td style="padding-right:10px"><fmt:message key='globalParam.description'/>：
						 	<input type="text"		id="descriptionTxt" size="18">
						 </td>
						 <td style="padding-right:10px"><fmt:message key='globalParam.subSystemId'/>：
						 	<s:select name="subSystemC" id="subSystemTxt"  
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="globalparamGridReload()"><fmt:message key='button.search'/></button>
								</div>
							</div></li>
						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">



 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="globalparamGridEdit"/> 
<s:url id="remoteurl" action="globalparamGridList"/> 

<div class="panelBar">
			<ul class="toolBar">
				<li><a id="globalparam_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="globalparam_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="globalparam_gridtable_edit" class="changebutton"  href="javaScript:"
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
		<div id="globalparam_gridtable_div" layoutH="120"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="globalparam_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="globalparam_gridtable_addTile">
				<fmt:message key="globalParamNew.title"/>
			</label> 
			<label style="display: none"
				id="globalparam_gridtable_editTile">
				<fmt:message key="globalParamEdit.title"/>
			</label>
			<label style="display: none"
				id="globalparam_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="globalparam_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_globalparam_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="globalparam_gridtable" 
    	dataType="json" 
    	gridModel="globalParams"
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
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		onCompleteTopics="onLoadSelect"
  		shrinkToFit="true"
  		autowidth="true"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="paramId" 
    	    search="false" 
    	    index="paramId" 
    	    title="%{getText('globalParam.paramId')}" 
    	    hidden="true"
    	    key="true"
    	    /> 
   <sjg:gridColumn 
    	    name="paramKey" 
    	    search="false" 
    	    index="paramKey" 
    	    title="%{getText('globalParam.paramKey')}" 
    	    hidden="false"
    	    key="false"
    	    /> 
    
   <sjg:gridColumn 
 	    name="paramValue" 
 	    index="paramValue" 
 	    title="%{getText('globalParam.paramValue')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		
 	    />
 	       <sjg:gridColumn 
 	    name="description" 
 	    index="description" 
 	    title="%{getText('globalParam.description')}" 
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="300"
 	    /> 	 	    
   <sjg:gridColumn 
 	    name="subSystemId" 
 	    index="subSystemId" 
 	    title="%{getText('globalParam.subSystemId')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="300"
 	    />
 	    
  </sjg:grid>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="globalparam_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="globalparam_gridtable_totals"></label>条</span>
		</div>

		<div id="globalparam_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->