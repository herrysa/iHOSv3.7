<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="searchItemList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchItemList.heading'/>"/>
    <meta name="menu" content="SearchItemMenu"/>
    
    <script type="text/javascript">

//	var searchId=${searchId};
 //   alert("searchId is : " + searchId);
     function refreshGridCurrentPage(){
    	var jq = jQuery('#searchItem_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#searchItem_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editSearchItem?popup=true&searchId=<s:property value='#parameters.searchId'/>";
			alert(url);
			var winTitle='<fmt:message key="searchItemNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#searchItem_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editSearchItem?popup=true&searchItemId=" + sid;
				var winTitle='<fmt:message key="searchItemNew.title"/>';
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
		jQuery(document).ready(function() { 
			/*var searchTabsContentW = jQuery("#searchTabsContent").width();
			var searchTabsContentH = jQuery("#searchTabsContent").height();
			setTimeout(function(){
				jQuery("#searchItem_gridtable").jqGrid('setGridHeight',searchTabsContentH-73);
			},100);
			setTimeout(function(){
				jQuery("#searchItem_gridtable").jqGrid('setGridWidth',searchTabsContentW);
			},100);*/
    	});
	</script>
</head>


<div class="page">
	<div class="pageContent">
	<div id="searchItem_container">
			<div id="searchItem_layout-center"
				class="pane ui-layout-center">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" id="searchItem_gridtable_add" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton" id="searchItem_gridtable_del" href="javaScript:"
					 title="确定要删除吗?"><span><fmt:message
								key="button.delete" /></span>
				</a>
				</li>
				<li><a class="changebutton" id="searchItem_gridtable_edit" href="javaScript:"
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



 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="searchItemGridEdit"/> 





<s:url id="remoteurl" action="searchItemGridList" >
<s:param name="searchId" value="#parameters.searchId"></s:param>

</s:url> 

<div id="searchItem_gridtable_div" extraHeight=83 extraWidth=10 tablecontainer="searchTabsContent"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchItem;optId:searchItemId;fatherGrid:search_gridtable;extraParam:searchId;width:650;height:570">
 				<input type="hidden" id="searchItem_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none"
						id="searchItem_gridtable_addTile"> <fmt:message key="searchItemNew.title"/> </label> <label style="display: none"
						id="searchItem_gridtable_editTile"> <fmt:message key="searchItemEdit.title"/> </label> <label style="display: none"
						id="searchItem_gridtable_selectNone"> <fmt:message
							key='list.selectNone' /> </label> <label style="display: none"
						id="searchItem_gridtable_selectMore"> <fmt:message
							key='list.selectMore' /> </label>
 <div id="load_searchItem_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="searchItem_gridtable" 
    	dataType="json" 
    	gridModel="searchItems"
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
  		height="200"
  		draggable="true"
  		autowidth="true"
  		cssClass="ui-jqgrid"
  		cssStyle="position: relative; font-size:11px;"
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="searchItemId" 
    	    search="false" 
    	    index="searchItemId" 
    	    title="%{getText('searchItem.searchItemId')}" 
    	    hidden="false"
    	
    	    key="true"
    	    width="100"
    	    />
    	       <sjg:gridColumn 
 	    name="caption" 
 	    index="caption" 
 	    title="%{getText('searchItem.caption')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    />
 	   <sjg:gridColumn 
 	    name="htmlField" 
 	    index="htmlField" 
 	    title="%{getText('searchItem.htmlField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
       <sjg:gridColumn 
 	    name="field" 
 	    index="field" 
 	    title="%{getText('searchItem.field')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
   <sjg:gridColumn 
 	    name="operator" 
 	    index="operator" 
 	    title="%{getText('searchItem.operator')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="50"
 	    />
 	       <sjg:gridColumn 
 	    name="userTag" 
 	    index="userTag" 
 	    title="%{getText('searchItem.userTag')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
   <sjg:gridColumn 
 	    name="oorder" 
 	    index="oorder" 
 	    title="%{getText('searchItem.oorder')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="60"
 	    /> 	    
    <sjg:gridColumn 
    	name="searchFlag" 
	    index="searchFlag" 
	    title="%{getText('searchItem.searchFlag')}" 
	    sortable="true"
	    align="center"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="80"/> 

   <sjg:gridColumn 
 	    name="clickEvent" 
 	    index="clickEvent" 
 	    title="%{getText('searchItem.clickEvent')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />

   <sjg:gridColumn 
 	    name="param1" 
 	    index="param1" 
 	    title="%{getText('searchItem.param1')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
   <sjg:gridColumn 
 	    name="param2" 
 	    index="param2" 
 	    title="%{getText('searchItem.param2')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
    <sjg:gridColumn 
    	name="mutiSelect" 
	    index="mutiSelect" 
	    title="%{getText('searchItem.mutiSelect')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="80"/> 

  </sjg:grid>
</div>
		<div id="searchItem_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="searchItem_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="searchItem_gridtable_totals"></label>条</span>
				</div>

				<div id="searchItem_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>
</div>
</div>