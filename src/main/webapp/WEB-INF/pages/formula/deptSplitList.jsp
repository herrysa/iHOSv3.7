<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="deptSplitList.title"/></title>
    <meta name="heading" content="<fmt:message key='deptSplitList.heading'/>"/>
    <meta name="menu" content="DeptSplitMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editDeptSplit?popup=true";
			var winTitle='<fmt:message key="deptSplitNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#deptSplit_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editDeptSplit?popup=true&id=" + sid;
				var winTitle='<fmt:message key="deptSplitNew.title"/>';
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
		/* var deptSplitLayout;
		jQuery(document).ready(function() { 
			deptSplitLayout = makeLayout({
				baseName: 'deptSplit', 
				tableIds: 'deptSplit_gridtable'
			}, null);
			deptSplitLayout.resizeAll();
    	}); */
	</script>
</head>


<div class="page">
	<!-- <div class="pageContent">
<div id="deptSplit_container">
			<div id="deptSplit_layout-center"
				class="pane ui-layout-center"> -->
 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="deptSplitGridEdit"/> 
<s:url id="remoteurl" action="deptSplitGridList"/> 
<div class="panelBar">
			<ul class="toolBar">
				<li><a id="deptSplit_gridtable_add" class="addbutton"
					external="true" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="deptSplit_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="deptSplit_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="deptSplit_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="edit_URL:editDeptSplit?popup=true&id=;width:550;height:400">
			<input type="hidden" id="deptSplit_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptSplit_gridtable_addTile">
				<fmt:message key="deptSplitNew.title"/>
			</label> 
			<label style="display: none"
				id="deptSplit_gridtable_editTile">
				<fmt:message key="deptSplitEdit.title"/>
			</label>
			<label style="display: none"
				id="deptSplit_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptSplit_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_deptSplit_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="deptSplit_gridtable" 
    	dataType="json" 
    	gridModel="deptSplits"
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
    	    name="id" 
    	    search="false" 
    	    index="id" 
    	    title="%{getText('deptSplit.id')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="costratio" 
 	    index="costratio" 
 	    title="%{getText('deptSplit.costratio')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="deptid" 
 	    index="deptid" 
 	    title="%{getText('deptSplit.deptid')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="deptname" 
 	    index="deptname" 
 	    title="%{getText('deptSplit.deptname')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
    <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('deptSplit.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
   <sjg:gridColumn 
 	    name="payinrattio" 
 	    index="payinrattio" 
 	    title="%{getText('deptSplit.payinrattio')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="pubdeptid" 
 	    index="pubdeptid" 
 	    title="%{getText('deptSplit.pubdeptid')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="pubdeptname" 
 	    index="pubdeptname" 
 	    title="%{getText('deptSplit.pubdeptname')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('deptSplit.remark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid>
</div>

	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="deptSplit_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="deptSplit_gridtable_totals"></label>条</span>
		</div>

		<div id="deptSplit_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
<!-- </div>
</div>
</div> -->