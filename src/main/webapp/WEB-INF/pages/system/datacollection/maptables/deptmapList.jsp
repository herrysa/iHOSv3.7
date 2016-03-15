<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="deptmapList.title"/></title>
    <meta name="heading" content="<fmt:message key='deptmapList.heading'/>"/>
    <meta name="menu" content="DeptmapMenu"/>
    <script type="text/javascript">
    jQuery(document).ready(function() {
    	cancelSel="";
    	editDataId="";
    	editRowCount = 0;
    	
    	jQuery("#marktypeTxt").treeselect({
    		optType:"single",
    		dataType:'sql',
			sql:'SELECT marktype id , marktype name ,1 parent FROM t_deptmap GROUP BY marktype'
    	});
    });
    function deptmapGridReload(){
		var urlString = "deptmapGridList";
		var marktypeTxt = jQuery("#marktypeTxt").val();
		var sourcecodeTxt = jQuery("#sourcecodeTxt").val();
		var sourcenameTxt = jQuery("#sourcenameTxt").val();
		var targetcodeTxt = jQuery("#targetcodeTxt").val();
		var targetnameTxt = jQuery("#targetnameTxt").val();
		urlString=urlString+"?filter_LIKES_marktype="+marktypeTxt+"&filter_LIKES_sourcecode="+sourcecodeTxt+"&filter_LIKES_sourcename="+sourcenameTxt+"&filter_LIKES_targetcode="+targetcodeTxt+"&filter_LIKES_targetname="+targetnameTxt;
		urlString=encodeURI(urlString);
		jQuery("#deptmap_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");

	}

    	function addRecord(){
			var url = "editDeptmap?popup=true";
			var winTitle='<fmt:message key="deptmapNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#deptmap_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editDeptmap?popup=true&id=" + sid;
				var winTitle='<fmt:message key="deptmapNew.title"/>';
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
		function deptMapExcelOutPut(){
			var url = "${ctx}/excelOutPut";
			var exportSql="select * from t_deptmap";
			var title="科室对照表";
			location.href=url+"?title="+title+"&exportSql="+exportSql; 
		} 
	</script>
	<style type="text/css">

	</style>
</head>

<div class="page" id="deptmap_page">
	<!-- <div id="deptmap_container">
			<div id="deptmap_layout-center"
				class="pane ui-layout-center"> -->
	<div id="deptmap_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form onsubmit="return navTabSearch(this);" action="userGridList"
					method="post" class="queryarea-form">
					<label class="queryarea-label"> <input type="hidden"
						class="input-small" id="marktypeTxt_id" size="15"> <fmt:message
							key='deptmap.marktype' />: <input type="text"
						class="input-small" id="marktypeTxt" size="15">
					</label> <label class="queryarea-label"> <fmt:message
							key='deptmap.sourcecode' />: <input type="text"
						class="input-small" id="sourcecodeTxt" size="15">
					</label> <label class="queryarea-label"> <fmt:message
							key='deptmap.sourcename' />: <input type="text"
						class="input-small" id="sourcenameTxt" size="15">
					</label> <label class="queryarea-label"> <fmt:message
							key='deptmap.targetcode' />: <input type="text"
						class="input-small" id="targetcodeTxt" size="15">
					</label> <label class="queryarea-label"> <fmt:message
							key='deptmap.targetname' />: <input type="text"
						class="input-small" id="targetnameTxt" size="15">
					</label>

					<!-- 						<div class="buttonActive" style="float:right"> -->
					<!-- 								<div class="buttonContent"> -->
					<%-- 									<button type="button" onclick="deptmapGridReload()"><s:text name='button.search'/></button> --%>
					<!-- 								</div> -->
					<!-- 							</div> -->
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="deptmapGridReload()">
								<fmt:message key='button.search' />
							</button>
						</div>

						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="deptmap_buttonBar">
			<ul class="toolBar">
				<li><a class="addbutton"
					onclick="gridAddRow(jQuery('#deptmap_gridtable'))"><span><fmt:message
								key="button.addRow" /></span></a></li>
				<li><a class="editbutton"
					onclick="gridEditRow(jQuery('#deptmap_gridtable'))"><span><fmt:message
								key="button.editRow" /></span></a></li>
				<li><a class="savebutton"
					onclick="gridSaveRow(jQuery('#deptmap_gridtable'))"><span><fmt:message
								key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton"
					onclick="gridRestore(jQuery('#deptmap_gridtable'))"><span><fmt:message
								key="button.restoreRow" /></span></a></li>
				<li><a id="deptmap_gridtable_del" class="delbutton"
					href="javaScript:"><span>删除</span></a></li>
				<li><a class="excelbutton" onclick="deptMapExcelOutPut()"><span>EXCEL导出</span></a></li>
			</ul>
		</div>
		<sj:dialog id="mybuttondialog"
			buttons="{'OK':function() { okButton(); }}" autoOpen="false"
			modal="true" title="%{getText('messageDialog.title')}" />

		<s:url id="editurl" action="deptmapGridEdit" />
		<s:url id="remoteurl" action="deptmapGridList" />
		<div id="deptmap_gridtable_div" layoutH=95 class="grid-wrapdiv"
			buttonBar="">

			<div id="load_deptmap_gridtable"
				class='loading ui-state-default ui-state-active'></div>
			<sjg:grid id="deptmap_gridtable" dataType="json" gridModel="deptmaps"
				href="%{remoteurl}" editurl="%{editurl}" editinline="true"
				rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
				rownumbers="true" pager="false" page="1" pagerButtons="false"
				pagerInput="false" pagerPosition="right" navigator="false"
				navigatorAdd="false"
				navigatorAddOptions="{height:230,width:400,reloadAfterSubmit:false}"
				navigatorEditOptions="{height:200,width:400,reloadAfterSubmit:false}"
				navigatorEdit="false" navigatorDelete="false"
				navigatorDeleteOptions="{reloadAfterSubmit:true}"
				navigatorSearch="false"
				navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
				navigatorRefresh="false" multiselect="true" navigatorView="true"
				multiboxonly="true" resizable="true" onCompleteTopics="onLoadSelect"
				autowidth="true" draggable="true" prmNames="{id:'id'} ">
				<sjg:gridColumn name="deptMapId" index="deptMapId" search="true"
					title="%{getText('deptmap.deptMapId')}" formatter="integer"
					key="true" editable="true" hidden="true" />

				<sjg:gridColumn name="marktype" index="marktype"
					title="%{getText('deptmap.marktype')}" sortable="true"
					search="true" editable="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
				<sjg:gridColumn name="sourcecode" index="sourcecode"
					title="%{getText('deptmap.sourcecode')}" sortable="true"
					search="true" editable="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
				<sjg:gridColumn name="sourcename" index="sourcename"
					title="%{getText('deptmap.sourcename')}" sortable="true"
					search="true" editable="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
				<sjg:gridColumn name="targetcode" index="targetcode"
					title="%{getText('deptmap.targetcode')}" sortable="true"
					search="true" editable="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
				<sjg:gridColumn name="targetname" index="targetname"
					title="%{getText('deptmap.targetname')}" sortable="true"
					search="true" editable="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" />
			</sjg:grid>
		</div>
	</div>
	<div class="panelBar" id="deptmap_pageBar">
		<div class="pages">
			<span>显示</span> <select id="deptmap_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="deptmap_gridtable_totals"></label>条
			</span>
		</div>

		<div id="deptmap_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->