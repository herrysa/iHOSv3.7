<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="allotSetList.title"/></title>
    <meta name="heading" content="<fmt:message key='allotSetList.heading'/>"/>
    <meta name="menu" content="AllotSetMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#allotSet_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#allotSet_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editAllotSet?popup=true";
			var winTitle='<fmt:message key="allotSetNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#allotSet_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editAllotSet?popup=true&allotSetId=" + sid;
				var winTitle='<fmt:message key="allotSetNew.title"/>';
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
		/* var allotSetLayout;
		jQuery(document).ready(function() { 
			allotSetLayout = makeLayout({
				baseName: 'allotSet', 
				tableIds: 'allotSet_gridtable'
			}, null);
			allotSetLayout.resizeAll();
    	}); */
		jQuery(document).ready(function() { 
				//var initAllotSetFlag = 0;
				var allotSetGrid = jQuery("#allotSet_gridtable");
				allotSetGrid.jqGrid({
					url : "allotSetGridList",
					editurl:"allotSetGridEdit",
					datatype : "json",
					mtype : "GET",
					colModel:[
						{name:'allotSetId',index:'allotSetId',align:'left',width:"270",label : '<s:text name="allotSet.allotSetId" />',sortable:true,hidden:true,highsearch:false,key:true,formatter:"integer"},				
						{name:'allotSetName',index:'allotSetName',align:'left',width:"450",label : '<s:text name="allotSet.allotSetName" />',sortable:true,hidden:false,highsearch:true},				
						{name:'disabled',index:'disabled',align:'center',width:"60",label : '<s:text name="allotSet.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"},
						{name:'remark',index:'remark',align:'left',width:"450",label : '<s:text name="allotSet.remark" />',sortable:true,hidden:false,highsearch:true}
					],
					jsonReader : {
						root : "allotSets", // (2)
						page : "page",
						total : "total",
						records : "records", // (3)
						repeatitems : false
					},
					sortname: 'allotSetId',
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
						 gridContainerResize("allotSet","div");
			           var dataTest = {"id":"allotSet_gridtable"};
			      	   jQuery.publish("onLoadSelect",dataTest,null);
			      	   makepager("allotSet_gridtable");
			      	// initAllotSetFlag = initColumn('allotSet_gridtable','com.huge.ihos.inout.model.AllotSet',initAllotSetFlag);
			       	} 
			    });
			    jQuery(allotSetGrid).jqGrid('bindKeys');
    	});
		
	</script>
</head>


<div class="page">
	<!-- <div class="pageContent">
	<div id="allotSet_container">
			<div id="allotSet_layout-center"
				class="pane ui-layout-center"> -->

<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="allotSetGridEdit"/> 
<s:url id="remoteurl" action="allotSetGridList"/>  --%>
<div class="panelBar" id="allotSet_buttonBar">
			<ul class="toolBar">
				<li><a id="allotSet_gridtable_add" class="addbutton"
					external="true" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="allotSet_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="allotSet_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="allotSet_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="edit_URL:editAllotSet?popup=true&allotSetId=;width:550;height:400">
			<input type="hidden" id="allotSet_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="allotSet_gridtable_addTile">
				<fmt:message key="allotSetNew.title"/>
			</label> 
			<label style="display: none"
				id="allotSet_gridtable_editTile">
				<fmt:message key="allotSetEdit.title"/>
			</label>
			<label style="display: none"
				id="allotSet_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="allotSet_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_allotSet_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
			<table id="allotSet_gridtable"></table>
 <%-- <sjg:grid 
    	id="allotSet_gridtable" 
    	dataType="json" 
    	gridModel="allotSets"
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
    	    name="allotSetId" 
    	    search="false" 
    	    index="allotSetId" 
    	    title="%{getText('allotSet.allotSetId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="allotSetName" 
 	    index="allotSetName" 
 	    title="%{getText('allotSet.allotSetName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
    <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('allotSet.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
   <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('allotSet.remark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid> --%>
</div>

	<div class="panelBar" id="allotSet_pageBar">
		<div class="pages">
			<span>显示</span> <select id="allotSet_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="allotSet_gridtable_totals"></label>条</span>
		</div>

		<div id="allotSet_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div>
</div> -->