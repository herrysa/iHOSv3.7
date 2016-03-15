<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="allotPrincipleList.title"/></title>
    <meta name="heading" content="<fmt:message key='allotPrincipleList.heading'/>"/>
    <meta name="menu" content="AllotPrincipleMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editAllotPrinciple?popup=true";
			var winTitle='<fmt:message key="allotPrincipleNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#allotPrinciple_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editAllotPrinciple?popup=true&allotPrincipleId=" + sid;
				var winTitle='<fmt:message key="allotPrincipleNew.title"/>';
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
		/* var allotPrincipleLayout;
		jQuery(document).ready(function() { 
			allotPrincipleLayout = makeLayout({
				baseName: 'allotPrinciple', 
				tableIds: 'allotPrinciple_gridtable'
			}, null);
			allotPrincipleLayout.resizeAll();
    	}); */
    	jQuery(document).ready(function() { 
			//var initAllotSetFlag = 0;
			var allotPrincipleGrid = jQuery("#allotPrinciple_gridtable");
			allotPrincipleGrid.jqGrid({
				url : "allotPrincipleGridList",
				editurl:"allotPrincipleGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel:[
					{name:'allotPrincipleId',index:'allotPrincipleId',align:'left',width:"270",label : '<s:text name="allotPrinciple.allotPrincipleId" />',sortable:true,hidden:false,highsearch:false,key:true},				
					{name:'allotPrincipleName',index:'allotPrincipleName',align:'left',width:"450",label : '<s:text name="allotPrinciple.allotPrincipleName" />',sortable:true,hidden:false,highsearch:true},				
					{name:'paramed',index:'paramed',align:'center',width:"60",label : '<s:text name="allotPrinciple.paramed" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"},
					{name:'param1',index:'param1',align:'left',width:"450",label : '<s:text name="allotPrinciple.param1" />',sortable:true,hidden:false,highsearch:true},
					{name:'disabled',index:'disabled',align:'center',width:"60",label : '<s:text name="allotPrinciple.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"}
				],
				jsonReader : {
					root : "allotPrinciples", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname: 'allotPrincipleId',
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
					 gridContainerResize("allotPrinciple","div");
		           var dataTest = {"id":"allotPrinciple_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("allotPrinciple_gridtable");
		      	// initAllotSetFlag = initColumn('allotSet_gridtable','com.huge.ihos.inout.model.AllotSet',initAllotSetFlag);
		       	} 
		    });
		    jQuery(allotPrincipleGrid).jqGrid('bindKeys');
	});
	
	</script>
</head>

<div class="page">
	<!-- <div class="pageContent">
<div id="allotPrinciple_container">
			<div id="allotPrinciple_layout-center"
				class="pane ui-layout-center"> -->
<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="allotPrincipleGridEdit"/> 
<s:url id="remoteurl" action="allotPrincipleGridList"/>  --%>
<div class="panelBar" id="allotPrinciple_buttonBar">
			<ul class="toolBar">
				<li><a id="allotPrinciple_gridtable_add" class="addbutton"
					external="true" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="allotPrinciple_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="allotPrinciple_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="allotPrinciple_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="width:550;height:300">
			<input type="hidden" id="allotPrinciple_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="allotPrinciple_gridtable_addTile">
				<fmt:message key="allotPrincipleNew.title"/>
			</label> 
			<label style="display: none"
				id="allotPrinciple_gridtable_editTile">
				<fmt:message key="allotPrincipleEdit.title"/>
			</label>
			<label style="display: none"
				id="allotPrinciple_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="allotPrinciple_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_allotPrinciple_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
			<table id="allotPrinciple_gridtable"></table>
 <%-- <sjg:grid 
    	id="allotPrinciple_gridtable" 
    	dataType="json" 
    	gridModel="allotPrinciples"
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
  		autowidth="true"
		onCompleteTopics="onLoadSelect"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="allotPrincipleId" 
    	    search="false" 
    	    index="allotPrincipleId" 
    	    title="%{getText('allotPrinciple.allotPrincipleId')}" 
    	    hidden="false"
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="allotPrincipleName" 
 	    index="allotPrincipleName" 
 	    title="%{getText('allotPrinciple.allotPrincipleName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
    <sjg:gridColumn 
    	name="paramed" 
	    index="paramed" 
	    title="%{getText('allotPrinciple.paramed')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
   <sjg:gridColumn 
 	    name="param1" 
 	    index="param1" 
 	    title="%{getText('allotPrinciple.param1')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />

          <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('allotPrinciple.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
  </sjg:grid> --%>
</div>

	<div class="panelBar" id="allotPrinciple_pageBar">
		<div class="pages">
			<span>显示</span> <select id="allotPrinciple_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="allotPrinciple_gridtable_totals"></label>条</span>
		</div>

		<div id="allotPrinciple_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->