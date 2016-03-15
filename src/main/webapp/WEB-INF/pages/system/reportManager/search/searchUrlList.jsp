<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<%-- <%@ include file="/common/miniNavBar.jsp"%> --%>
<head>
    <title><fmt:message key="searchUrlList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchUrlList.heading'/>"/>
    <meta name="menu" content="SearchUrlMenu"/>
    
    <script type="text/javascript">
    function stringFormatter (cellvalue, options, rowObject)	{
    	return cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
    }
    function refreshGridCurrentPage(){
    	var jq = jQuery('#searchUrl_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#searchUrl_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addUrlRecord(){
			var url = "editSearchUrl?popup=true&searchId=<s:property value='#parameters.searchId'/>";
			var winTitle='<fmt:message key="searchUrlNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editUrlRecord(){
	        var sid = jQuery("#searchUrl_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editSearchUrl?popup=true&searchUrlId=" + sid;
				var winTitle='<fmt:message key="searchUrlNew.title"/>';
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
				jQuery("#searchUrl_gridtable").jqGrid('setGridHeight',searchTabsContentH-73);
			},100);
			setTimeout(function(){
				jQuery("#searchUrl_gridtable").jqGrid('setGridWidth',searchTabsContentW);
			},100);*/
    	});
	</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" id="searchUrl_gridtable_add" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton" id="searchUrl_gridtable_del" href="javaScript:"
					 title="确定要删除吗?"><span>删除</span>
				</a>
				</li>
				<li><a class="changebutton" id="searchUrl_gridtable_edit" href="javaScript:"
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
<div id="searchUrl_gridtable_div" extraHeight=83 extraWidth=10 tablecontainer="searchTabsContent"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchUrl;optId:searchUrlId;fatherGrid:search_gridtable;extraParam:searchId;width:600;height:500">
 				<input type="hidden" id="searchUrl_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none"
						id="searchUrl_gridtable_addTile"> <fmt:message key="searchUrlNew.title"/> </label> <label style="display: none"
						id="searchUrl_gridtable_editTile"> <fmt:message key="searchUrlNew.title"/> </label> 
 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="searchUrlGridEdit"/> 
<s:url id="remoteurl" action="searchUrlGridList">
<s:param name="searchId" value="#parameters.searchId"></s:param>
</s:url>  
<div id="load_searchUrl_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="searchUrl_gridtable" 
    	dataType="json" 
    	gridModel="searchUrls"
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
  		onCompleteTopics="onLoadSelect"
    > 
    <sjg:gridColumn 
    	    name="searchUrlId" 
    	    search="false" 
    	    index="searchUrlId" 
    	    title="%{getText('searchUrl.searchUrlId')}" 
    	    hidden="false"
    	  
    	    key="true"
    	    width="60"
    	    />
    
   <sjg:gridColumn 
 	    name="title" 
 	    index="title" 
 	    title="%{getText('searchUrl.title')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="60"
 	    />
   <sjg:gridColumn 
 	    name="url" 
 	    index="url" 
 	    title="%{getText('searchUrl.url')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		formatter="stringFormatter"
 		width="300"
 	    />
 	 <sjg:gridColumn 
 	    name="icon" 
 	    index="icon" 
 	    title="%{getText('searchUrl.icon')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		formatter="stringFormatter"
 		width="60"
 	    /> 	 	    
    <sjg:gridColumn 
    	name="nullFlag" 
	    index="nullFlag" 
	    align="center"
	    title="%{getText('searchUrl.nullFlag')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="60"/> 
    <sjg:gridColumn 
    	name="visible" 
	    index="visible" 
	    align="center"
	    title="%{getText('searchUrl.visible')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="60"/> 
   <sjg:gridColumn 
 	    name="oorder" 
 	    index="oorder" 
 	    title="%{getText('searchUrl.oorder')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="60"
 	    />



  </sjg:grid>
</div>
		<div id="searchUrl_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="searchUrl_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="searchUrl_gridtable_totals"></label>条</span>
				</div>

				<div id="searchUrl_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>