<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="matetypeList.title"/></title>
    <meta name="heading" content="<fmt:message key='matetypeList.heading'/>"/>
    <meta name="menu" content="MatetypeMenu"/>
    <script type="text/javascript">
    jQuery(document).ready(function() {
    	cancelSel="";
    	editDataId="";
    	editRowCount = 0;
    });
     
	function matetypeGridReload(){
		var urlString = "matetypeGridList";
		var mateTypeIdTxt = jQuery("#mateTypeIdTxt").val();
		var costItemIdTxt = jQuery("#costItemIdTxt").val();
		var costitemTxt = jQuery("#costitemTxt").val();
		var mateTypeTxt = jQuery("#mateTypeTxt").val();
		urlString=urlString+"?filter_LIKES_mateTypeId="+mateTypeIdTxt+"&filter_LIKES_costItemId="+costItemIdTxt+"&filter_LIKES_costitem="+costitemTxt+"&filter_LIKES_mateType="+mateTypeTxt;
		urlString=encodeURI(urlString);
		jQuery("#matetype_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
    	function addRecord(){
			var url = "editMatetype?popup=true";
			var winTitle='<fmt:message key="matetypeNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#matetype_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editMatetype?popup=true&mateTypeId=" + sid;
				var winTitle='<fmt:message key="matetypeNew.title"/>';
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
		
		function mateExcelOutPut(){
			var url = "${ctx}/excelOutPut";
			var exportSql="select * from t_matetype";
			var title="物资对照表";
			location.href=url+"?title="+title+"&exportSql="+exportSql; 
		} 
	</script>
	
	
	
	<!-- autocomplete plugin scripts start  -->
    <script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
    <link  rel="stylesheet"  href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
    <!-- autocomplete plugin scripts end  -->
    
	</head>
<div class="page">
<!-- <div id="matetype_container">
			<div id="matetype_layout-center"
				class="pane ui-layout-center"> -->
	<div id="matetype_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
		<form onsubmit="return navTabSearch(this);" action="userGridList"
			method="post" class="queryarea-form">
						<label class="queryarea-label"><fmt:message key='matetype.mateTypeId' />： <input
							type="text" class="input-small" id="mateTypeIdTxt" size="10">
						</label>
						<label class="queryarea-label"><fmt:message key='matetype.mateType' />： <input
							type="text" class="input-small" id="mateTypeTxt" size="15">
						</label>
						<label class="queryarea-label"><fmt:message key='matetype.costItemId' />： <input
							type="text" class="input-small" id="costItemIdTxt" size="15">
						</label>
						<label class="queryarea-label"><fmt:message key='matetype.costitem' />： <input
							type="text" class="input-small" id="costitemTxt" size="10">
						</label>
						<%-- <label><fmt:message key='deptmap.targetname' />: <input
							type="text" class="input-small" id="mateTypeTxt" size="15">
						</label> --%>
<!-- 						<div class="buttonActive" style="float:right"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="matetypeGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
						<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="matetypeGridReload()"><fmt:message key='button.search'/></button>
								</div>
							</div>
		</form>
			</div>
				</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#matetype_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#matetype_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#matetype_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#matetype_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="matetype_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
				<li><a class="excelbutton" onclick="mateExcelOutPut()" ><span>EXCEL导出</span></a></li>
			</ul>
		</div>
		<sj:dialog id="mybuttondialog"
			buttons="{'OK':function() { okButton(); }}" autoOpen="false"
			modal="true" title="%{getText('messageDialog.title')}" />
		<s:url id="editurl" action="matetypeGridEdit" />
		<s:url id="remoteurl" action="matetypeGridList" />
		<div id="matetype_gridtable_div" layoutH=95
			class="grid-wrapdiv"
			buttonBar="">
		<div id="load_matetype_gridtable" class='loading ui-state-default ui-state-active'></div>
			<sjg:grid id="matetype_gridtable" dataType="json"
				cssClass="jqgridStyle" gridModel="matetypes" href="%{remoteurl}"
				editurl="%{editurl}" altClass="redClass" altRows="true"
				editinline="true" rowList="10,15,20,30,40,50,60,70,80,90,100"
				rowNum="20" rownumbers="true" pager="false" page="1"
				pagerButtons="false" pagerInput="false" pagerPosition="right"
				navigator="false" navigatorAdd="false" navigatorEdit="false"
				navigatorDelete="false" navigatorSearch="false"
				navigatorSearchOptions="{multipleSearch:true,showQuery: true}"
				navigatorRefresh="false" navigatorView="false" multiselect="true"
				multiboxonly="true" resizable="true"
				onEditInlineSuccessTopics="oneditsuccess" prmNames="{id:'ids'} "
				onCompleteTopics="onLoadSelect" autowidth="true">
				<sjg:gridColumn name="mateMapId" index="mateMapId" search="true"
					title="%{getText('matetype.mateMapId')}" formatter="integer"
					key="true" editable="true" hidden="true" />
				<sjg:gridColumn name="mateTypeId" index="mateTypeId" search="true"
					title="%{getText('matetype.mateTypeId')}" editable="true" />
				<sjg:gridColumn name="mateType" index="mateType" search="true"
					title="%{getText('matetype.mateType')}" editable="true"
					edittype="text" />
				<sjg:gridColumn name="costItemId" index="costItemId"
					title="%{getText('matetype.costItemId')}" editable="true"
					edittype="text" sortable="true" search="false" />

				<sjg:gridColumn name="costitem" index="costitem" search="true"
					title="%{getText('matetype.costitem')}" editable="true"
					edittype="text" />
				
<%-- 				<sjg:gridColumn name="mateType" index="mateType" search="true"
					title="%{getText('matetype.mateType')}" editable="true"
					edittype="select" editoptions="{value:'%{selectOption}'}" onchange="changeSelectValue"/> --%>
			</sjg:grid>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="matetype_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="matetype_gridtable_totals"></label>条</span>
		</div>

		<div id="matetype_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	<!-- </div>
</div> -->
