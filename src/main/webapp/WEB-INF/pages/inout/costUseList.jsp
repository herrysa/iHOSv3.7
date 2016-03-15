<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="costUseList.title"/></title>
    <meta name="heading" content="<fmt:message key='costUseList.heading'/>"/>
    <meta name="menu" content="CostUseMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#costUse_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#costUse_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editCostUse?popup=true";
			var winTitle='<fmt:message key="costUseNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#costUse_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editCostUse?popup=true&costUseId=" + sid;
				var winTitle='<fmt:message key="costUseNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
		
		function checkGridDeleteOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		    //alert(msg);
		   //jQuery("#gridinfo").html(msg);
		   jQuery('div.#mybuttondialog').html(msg);
			  jQuery('#mybuttondialog').dialog('open');
	        return [true,""];   
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		/* var costUseLayout;
		jQuery(document).ready(function() { 
			costUseLayout = makeLayout({
				baseName: 'costUse', 
				tableIds: 'costUse_gridtable'
			}, null);
			costUseLayout.resizeAll();
    	}); */
		jQuery(document).ready(function() {
			
			var initCostUseFlag = 0;
			var costUseGrid = jQuery("#costUse_gridtable");
			costUseGrid.jqGrid({
				url : "costUseGridList",
				editurl:"costUseGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel:[
					{name:'costUseId',index:'costUseId',align:'left',width:"360",label : '<s:text name="costUse.costUseId" />',hidden:false,highsearch:false,key:true},				
					{name:'costUseName',index:'costUseName',align:'left',width:"1085",label : '<s:text name="costUse.costUseName" />',sortable:true,hidden:false,highsearch:true},				
					{name:'disabled',index:'disabled',align:'center',width:"180",label : '<s:text name="costUse.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"}				
					
				],
				jsonReader : {
					root : "costUses", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname: 'costUseId',
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
				width:500,
		        onSelectRow: function(rowid) {
		       
		       	},
				 gridComplete:function(){
					 
					 gridContainerResize("costUse","div");
		           var dataTest = {"id":"costUse_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("costUse_gridtable");
		      	 initCostUseFlag = initColumn('costUse_gridtable','com.huge.ihos.inout.model.CostUse',initCostUseFlag);
		       	} 
		    });
		    jQuery(costUseGrid).jqGrid('bindKeys');
		});
	</script>
</head>


<div class="page">
	<!-- <div class="pageContent" style="overflow:hidden"> -->

<!-- <div id="costUse_container">
			<div id="costUse_layout-center"
				class="pane ui-layout-center"> -->

<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="costUseGridEdit"/> 
<s:url id="remoteurl" action="costUseGridList"/>  --%>
<div class="panelBar" id="costUse_buttonBar">
			<ul class="toolBar">
				<li><a id="costUse_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="costUse_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="costUse_gridtable_edit" class="changebutton"  href="javaScript:"
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
		<div id="costUse_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="base_URL:editCostUse;optId:costUseId;width:400;height:270">
			<input type="hidden" id="costUse_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="costUse_gridtable_addTile">
				<fmt:message key="costUseNew.title"/>
			</label> 
			<label style="display: none"
				id="costUse_gridtable_editTile">
				<fmt:message key="costUseEdit.title"/>
			</label>
			<label style="display: none"
				id="costUse_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="costUse_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_costUse_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
<table id="costUse_gridtable"></table>
 <%-- <sjg:grid 
    	id="costUse_gridtable" 
    	dataType="json" 
    	gridModel="costUses"
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
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		onCompleteTopics="onLoadSelect"
  		autowidth="true"
  		draggable="true"
  		width="500"
    >
    <sjg:gridColumn 
    	    name="costUseId" 
    	    search="false" 
    	    index="costUseId" 
    	    title="%{getText('costUse.costUseId')}" 
    	    hidden="false"
    	    key="true"
    	    width="100"
    	    />
    
   <sjg:gridColumn 
 	    name="costUseName" 
 	    index="costUseName" 
 	    title="%{getText('costUse.costUseName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="300"
 	    />
    <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('costUse.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="50"/> 
  </sjg:grid> --%>
</div>
	
	<div class="panelBar" id="costUse_pageBar">
		<div class="pages">
			<span>显示</span>
			<select id="costUse_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="costUse_gridtable_totals"></label>条</span>
		</div>
		
		<div id="costUse_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div>
</div> -->