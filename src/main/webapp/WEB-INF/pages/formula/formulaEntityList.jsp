<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="formulaEntityList.title"/></title>
    <meta name="heading" content="<fmt:message key='formulaEntityList.heading'/>"/>
    <meta name="menu" content="FormulaEntityMenu"/>
    
    <script type="text/javascript">
	    function refreshGridCurrentPage(){
	    	var jq = jQuery('#formulaEntity_gridtable');
	        var currentPage = jq.jqGrid('getGridParam', 'page');  
	    	jQuery('#formulaEntity_gridtable').trigger('reloadGrid', [{page: currentPage }]);
	    }    
    	function addRecord(){
			var url = "editFormulaEntity?popup=true";
			var winTitle='<fmt:message key="formulaEntityNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#formulaEntity_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#mybuttondialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#mybuttondialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editFormulaEntity?popup=true&formulaEntityId=" + sid;
				var winTitle='<fmt:message key="formulaEntityNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		var formulaEntityLayout;
		jQuery(document).ready(function() { 
			formulaEntityLayout = makeLayout({
				baseName: 'formulaEntity', 
				tableIds: 'formulaEntity_gridtable'
			}, null);
			formulaEntityLayout.resizeAll();
				
				var initFormulaEntityFlag = 0;
				var formulaEntityGrid = jQuery("#formulaEntity_gridtable");
				formulaEntityGrid.jqGrid({
					url : "formulaEntityGridList",
					editurl:"formulaEntityGridEdit",
					datatype : "json",
					mtype : "GET",
					colModel:[
						{name:'formulaEntityId',index:'formulaEntityId',align:'left',width:"270",label : '<s:text name="formulaEntity.formulaEntityId" />',sortable:true,hidden:false,highsearch:false,key:true},				
						{name:'entityName',index:'entityName',align:'left',width:"450",label : '<s:text name="formulaEntity.entityName" />',sortable:true,hidden:false,highsearch:true},				
						{name:'tableName',index:'tableName',align:'left',width:"450",label : '<s:text name="formulaEntity.tableName" />',sortable:true,hidden:false,highsearch:true},				
						{name:'entityDesc',index:'entityDesc',align:'left',width:"450",label : '<s:text name="formulaEntity.entityDesc" />',sortable:true,hidden:false,highsearch:true}
					],
					jsonReader : {
						root : "formulaEntities", // (2)
						page : "page",
						total : "total",
						records : "records", // (3)
						repeatitems : false
					},
					sortname: 'formulaEntityId',
					viewrecords: true,
					sortorder: 'asc',
			        height:300,
			        gridview:true,
			        rownumbers:true,
			        loadui: "disable",
			        multiselect: true,
					multiboxonly:true,
					shrinkToFit:true,
					autowidth:true,
			        onSelectRow: function(rowid) {
			       
			       	},
					 gridComplete:function(){
						 gridContainerResize("formulaEntity","div");
			           var dataTest = {"id":"formulaEntity_gridtable"};
			      	   jQuery.publish("onLoadSelect",dataTest,null);
			      	   makepager("formulaEntity_gridtable");
			      	 initFormulaEntityFlag = initColumn('formulaEntity_gridtable','com.huge.ihos.inout.model.FormulaEntity',initFormulaEntityFlag);
			       	} 
			    });
			    jQuery(formulaEntityGrid).jqGrid('bindKeys');
    	});
		
	</script>
</head>

<div class="page">
	<div class="pageContent">
<div id="formulaEntity_container">
			<div id="formulaEntity_layout-center"
				class="pane ui-layout-center">

<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="formulaEntityGridEdit"/> 
<s:url id="remoteurl" action="formulaEntityGridList"/>  --%>

<div class="panelBar" id="formulaEntity_buttonBar">
			<ul class="toolBar">
				<li><a id="formulaEntity_gridtable_add" class="addbutton"
					external="true" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="formulaEntity_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="formulaEntity_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="formulaEntity_gridtable_div" layoutH="57"  class="grid-wrapdiv"
			style="margin-left: 2px; margin-top: 2px;"
			buttonBar="width:550;height:300">
			<input type="hidden" id="formulaEntity_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="formulaEntity_gridtable_addTile">
				<fmt:message key="formulaEntityNew.title"/>
			</label> 
			<label style="display: none"
				id="formulaEntity_gridtable_editTile">
				<fmt:message key="formulaEntityEdit.title"/>
			</label>
			<label style="display: none"
				id="formulaEntity_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="formulaEntity_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_formulaEntity_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
<table id="formulaEntity_gridtable"></table>
 <%-- <sjg:grid 
    	id="formulaEntity_gridtable" 
    	dataType="json" 
    	gridModel="formulaEntities"
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
		navigatorDeleteOptions="{reloadAfterSubmit:true}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		draggable="true"
  		autowidth="true"
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="formulaEntityId" 
    	    search="false" 
    	    index="formulaEntityId" 
    	    title="%{getText('formulaEntity.formulaEntityId')}" 
    	    hidden="false"
    	    key="true"
    	    width="100"
    	    />
    

   <sjg:gridColumn 
 	    name="entityName" 
 	    index="entityName" 
 	    title="%{getText('formulaEntity.entityName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="200"
 	    />
   <sjg:gridColumn 
 	    name="tableName" 
 	    index="tableName" 
 	    title="%{getText('formulaEntity.tableName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="200"
 	    />
   <sjg:gridColumn 
 	    name="entityDesc" 
 	    index="entityDesc" 
 	    title="%{getText('formulaEntity.entityDesc')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="200"
 	    /> 	    
  </sjg:grid> --%>

	</div>
	<div class="panelBar" id="formulaEntity_pageBar">
		<div class="pages">
			<span>显示</span> <select id="formulaEntity_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="formulaEntity_gridtable_totals"></label>条</span>
		</div>

		<div id="formulaEntity_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>