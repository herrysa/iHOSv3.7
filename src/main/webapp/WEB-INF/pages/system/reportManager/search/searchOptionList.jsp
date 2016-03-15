<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<head>
    <title><fmt:message key="searchOptionList.title"/></title>
    <meta name="heading" content="<fmt:message key='searchOptionList.heading'/>"/>
    <meta name="menu" content="SearchOptionMenu"/>
    
    <script type="text/javascript">
    	function refreshGridCurrentPage(){
    		var jq = jQuery('#searchOption_gridtable');
            var currentPage = jq.jqGrid('getGridParam', 'page');  
			jQuery('#searchOption_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    	}
    
    	function addOptionRecord(){
			var url = "editSearchOption?popup=true&searchId=<s:property value='#parameters.searchId'/>";
			var winTitle='<fmt:message key="searchOptionNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editOptionRecord(){
	        var sid = jQuery("#searchOption_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editSearchOption?popup=true&searchOptionId=" + sid;
				var winTitle='<fmt:message key="searchOptionNew.title"/>';
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
				jQuery("#searchOption_gridtable").jqGrid('setGridHeight',searchTabsContentH-73);
			},100);
			setTimeout(function(){
				jQuery("#searchOption_gridtable").jqGrid('setGridWidth',searchTabsContentW);
			},100);*/
    	});
	</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" id="searchOption_gridtable_add" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton" id="searchOption_gridtable_del" href="javaScript:"
					 title="确定要删除吗?"><span>删除</span>
				</a>
				</li>
				<li><a class="changebutton" id="searchOption_gridtable_edit" href="javaScript:"
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
<div id="searchOption_gridtable_div"  extraHeight=83 extraWidth=10 tablecontainer="searchTabsContent"
				class="grid-wrapdiv"
				buttonBar="base_URL:editSearchOption;optId:searchOptionId;fatherGrid:search_gridtable;extraParam:searchId;width:650;height:620">
 				<input type="hidden" id="ssearchOption_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none"
						id="searchOption_gridtable_addTile"> <fmt:message key="searchOptionNew.title"/> </label> 
						<label style="display: none" id="searchOption_gridtable_editTile"> 
							<fmt:message key="searchOptionNew.title"/>
						</label>

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="searchOptionGridEdit"/> 
<s:url id="remoteurl" action="searchOptionGridList">
<s:param name="searchId" value="#parameters.searchId"></s:param>
</s:url> 
<div id="load_searchOption_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="searchOption_gridtable" 
    	dataType="json" 
    	gridModel="searchOptions"
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
  		draggable="true"
  		autowidth="true" 
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="searchOptionId" 
    	    search="false" 
    	    index="searchOptionId" 
    	    title="%{getText('searchOption.searchOptionId')}" 
    	    hidden="false"
    	   
    	    key="true"
    	    width="120"
    	    />
     <sjg:gridColumn 
 	    name="fieldName" 
 	    index="fieldName" 
 	    title="%{getText('searchOption.fieldName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    />
    <sjg:gridColumn 
 	    name="title" 
 	    index="title" 
 	    title="%{getText('searchOption.title')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="150"
 	    />	    
    <sjg:gridColumn 
    	name="visible" 
	    index="visible" 
	    title="%{getText('searchOption.visible')}" 
	    sortable="true"
      edittype="checkbox"
      align="center"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="50"/>  	    
    <sjg:gridColumn 
    	name="frozen" 
	    index="frozen" 
	    title="冻结" 
	    sortable="true"
      edittype="checkbox"
      align="center"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="50"/>  	    
   <sjg:gridColumn 
 	    name="oorder" 
 	    index="oorder" 
 	    title="%{getText('searchOption.oorder')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    /> 	
    <sjg:gridColumn 
 	    name="displayFormat" 
 	    index="displayFormat" 
 	    title="%{getText('searchOption.displayFormat')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    /> 	    
    <sjg:gridColumn 
 	    name="fieldType" 
 	    index="fieldType" 
 	    title="%{getText('searchOption.fieldType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    /> 	
   <sjg:gridColumn 
 	    name="displayWidth" 
 	    index="displayWidth" 
 	    title="%{getText('searchOption.displayWidth')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    /> 	    
   <sjg:gridColumn 
 	    name="alignType" 
 	    index="alignType" 
 	    title="%{getText('searchOption.alignType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    /> 	 	    
    <sjg:gridColumn 
    	name="printable" 
	    index="printable" 
	    title="%{getText('searchOption.printable')}" 
	    sortable="true"
	    align="center"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      width="50"/>  	    
   <sjg:gridColumn 
 	    name="editType" 
 	    index="editType" 
 	    title="%{getText('searchOption.editType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    />
    <sjg:gridColumn 
 	    name="param1" 
 	    index="param1" 
 	    title="%{getText('searchOption.param1')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    />
   <sjg:gridColumn 
 	    name="param2" 
 	    index="param2" 
 	    title="%{getText('searchOption.param2')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    />	    
 	    	     	      
   <sjg:gridColumn 
 	    name="calcType" 
 	    index="calcType" 
 	    title="%{getText('searchOption.calcType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="70"
 	    />

	    

    
<%--     
   <sjg:gridColumn 
 	    name="linkField" 
 	    index="linkField" 
 	    title="%{getText('searchOption.linkField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />



   <sjg:gridColumn 
 	    name="url" 
 	    index="url" 
 	    title="%{getText('searchOption.url')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    /> --%>

  </sjg:grid>
</div>
		<div id="searchOption_gridtable_footBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="searchOption_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="searchOption_gridtable_totals"></label>条</span>
				</div>

				<div id="searchOption_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>
</div>