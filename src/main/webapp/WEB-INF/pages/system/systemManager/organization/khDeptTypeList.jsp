<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="khDeptTypeList.title"/></title>
    <meta name="heading" content="<fmt:message key='khDeptTypeList.heading'/>"/>
    <meta name="menu" content="KhDeptTypeMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editKhDeptType?popup=true";
			var winTitle='<fmt:message key="khDeptTypeNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#khDeptType_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editKhDeptType?popup=true&khDeptTypeId=" + sid;
				var winTitle='<fmt:message key="khDeptTypeNew.title"/>';
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
		/* var deptTypeLayout;
		jQuery(document).ready(function() { 
			deptTypeLayout = makeLayout({
				baseName: 'deptType', 
				tableIds: 'deptType_gridtable'
			}, null);
			//deptTypeLayout.resizeAll();
    	}); */
		
		
		function hideLoading(){
			alert("before");
			jQuery("#khDeptType_gridtable").setGridParam({loadui: "disable"});
			alert("after");
		}
	</script>
</head>


<div class="page">
	<!-- <div class="pageContent">
<div id="deptType_container">
			<div id="deptType_layout-center"
				class="pane ui-layout-center"> -->
 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="khDeptTypeGridEdit"/> 
<s:url id="remoteurl" action="khDeptTypeGridList"/> 

<div class="panelBar">
			<ul class="toolBar">
				<li><a id="khDeptType_gridtable_add" class="addbutton"
					 href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="khDeptType_gridtable_del" class="delbutton" href="javaScript:" title="确定要删除吗?"><span>删除</span>
				</a></li>
				<li><a id="khDeptType_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message
								key="button.edit" /> </span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="khDeptType_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="width:400;height:250">
			<input type="hidden" id="khDeptType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="khDeptType_gridtable_addTile">
				<fmt:message key="khDeptTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="khDeptType_gridtable_editTile">
				<fmt:message key="khDeptTypeNew.title"/>
			</label>
			<label style="display: none"
				id="khDeptType_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="khDeptType_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_khDeptType_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="khDeptType_gridtable" 
    	dataType="json" 
    	gridModel="khDeptTypes"
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
  		autowidth="true"
		onCompleteTopics="onLoadSelect"
		onBeforeTopics="hideLoading"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="khDeptTypeId" 
    	    search="false" 
    	    index="khDdeptTypeId" 
    	    title="%{getText('khDeptType.khDeptTypeId')}" 
    	    hidden="false"
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="khDeptTypeName" 
 	    index="khDeptTypeName" 
 	    title="%{getText('khDeptType.khDeptTypeName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
    <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('khDeptType.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
  </sjg:grid>
</div>

	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox" name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="khDeptType_gridtable_totals"></label>条</span>
		</div>

		<div id="khDeptType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->