<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="acctcostmapList.title"/></title>
    <meta name="heading" content="<fmt:message key='acctcostmapList.heading'/>"/>
    <meta name="menu" content="AcctcostmapMenu"/>
    
    <script type="text/javascript">
    
    jQuery(document).ready(function() {
    	cancelSel="";
    	editDataId="";
    	editRowCount = 0;
    }); 
    function acctcostmapGridReload(){
		var urlString = "acctcostmapGridList";
		var acctcodeTxt = jQuery("#acctcodeTxt").val();
		var acctnameTxt = jQuery("#acctnameTxt").val();
		var costitemTxt = jQuery("#costitemTxt").val();
		var costitemidTxt = jQuery("#costitemidTxt").val();
		urlString=urlString+"?filter_LIKES_acctcode="+acctcodeTxt+"&filter_LIKES_acctname="+acctnameTxt+"&filter_LIKES_costitem="+costitemTxt+"&filter_LIKES_costitemid="+costitemidTxt;
		urlString=encodeURI(urlString);
		jQuery("#acctcostmap_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
    	function addRecord(){
			var url = "editAcctcostmap?popup=true";
			var winTitle='<fmt:message key="acctcostmapNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#acctcostmap_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editAcctcostmap?popup=true&acctcode=" + sid;
				var winTitle='<fmt:message key="acctcostmapNew.title"/>';
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
		function acctExcelOutPut(){
			var url = "${ctx}/excelOutPut";
			var exportSql="select * from t_acctcostmap";
			var title="科目对照表";
			location.href=url+"?title="+title+"&exportSql="+exportSql; 
		} 
		
	</script>
</head>

<div class="page">
	<!-- <div id="acctcostmap_container">
			<div id="acctcostmap_layout-center"
				class="pane ui-layout-center"> -->
	<div id="acctcostmap_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form onsubmit="return navTabSearch(this);" action="userGridList"
					method="post" class="queryarea-form">
					<label class="queryarea-label"><fmt:message key='acctcostmap.acctcode' />: <input
						type="text" class="input-small" id="acctcodeTxt" size="15">
					</label > 
					<label class="queryarea-label"><fmt:message key='acctcostmap.acctname' />:
						<input type="text" class="input-small" id="acctnameTxt" size="15">
					</label> 
					<label class="queryarea-label"><fmt:message
							key='acctcostmap.costitemid' />: <input type="text"
						class="input-small" id="costitemidTxt" size="15"> </label>
					<label class="queryarea-label"><fmt:message key='acctcostmap.costitem' />: <input
						type="text" class="input-small" id="costitemTxt" size="15">
					</label>
					<%-- 
						<label><fmt:message key='deptmap.targetname'/>:
							<input type="text" class="input-small" id="targetnameTxt" size="15">
						</label> --%>

					<!-- 						<div class="buttonActive" style="float:right"> -->
					<!-- 								<div class="buttonContent"> -->
					<%-- 									<button type="button" onclick="acctcostmapGridReload()"><s:text name='button.search'/></button> --%>
					<!-- 								</div> -->
					<!-- 							</div> -->
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="acctcostmapGridReload()">
								<fmt:message key='button.search' />
							</button>
						</div>
					</div>
					<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->

				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton"
					onclick="gridAddRow(jQuery('#acctcostmap_gridtable'))"><span><fmt:message
								key="button.addRow" /></span></a></li>
				<li><a class="editbutton"
					onclick="gridEditRow(jQuery('#acctcostmap_gridtable'))"><span><fmt:message
								key="button.editRow" /></span></a></li>
				<li><a class="savebutton"
					onclick="gridSaveRow(jQuery('#acctcostmap_gridtable'))"><span><fmt:message
								key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton"
					onclick="gridRestore(jQuery('#acctcostmap_gridtable'))"><span><fmt:message
								key="button.restoreRow" /></span></a></li>
				<li><a id="acctcostmap_gridtable_del" class="delbutton"
					href="javaScript:"><span>删除</span></a></li>
				<li><a class="excelbutton" onclick="acctExcelOutPut()"><span>EXCEL导出</span></a></li>
			</ul>
		</div>
		<sj:dialog id="mybuttondialog"
			buttons="{'OK':function() { okButton(); }}" autoOpen="false"
			modal="true" title="%{getText('messageDialog.title')}" />

		<s:url id="editurl" action="acctcostmapGridEdit" />
		<s:url id="remoteurl" action="acctcostmapGridList" />
		<div id="acctcostmap_gridtable_div" layoutH=95 class="grid-wrapdiv"
			buttonBar="">

			<div id="load_acctcostmap_gridtable"
				class='loading ui-state-default ui-state-active'></div>
			<sjg:grid id="acctcostmap_gridtable" dataType="json"
				gridModel="acctcostmaps" href="%{remoteurl}" editurl="%{editurl}"
				editinline="true" rowList="10,15,20,30,40,50,60,70,80,90,100"
				rowNum="20" rownumbers="true" pager="false" page="1"
				pagerButtons="false" pagerInput="false" pagerPosition="right"
				navigator="false" navigatorAdd="true"
				navigatorAddOptions="{height:200,width:400,reloadAfterSubmit:false}"
				navigatorEditOptions="{height:200,width:400,reloadAfterSubmit:false}"
				navigatorEdit="true" navigatorDelete="false"
				navigatorDeleteOptions="{reloadAfterSubmit:true}"
				navigatorSearch="false"
				navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
				navigatorRefresh="false" multiselect="true" multiboxonly="true"
				navigatorView="true" resizable="true"
				onCompleteTopics="onLoadSelect" autowidth="true" draggable="true"
				onSelectRowTopics="editAcctCostMapData" prmNames="{id:'id'} "
				onEditInlineSuccessTopics="oneditsuccess">
				<sjg:gridColumn name="acctMapId" index="acctMapId" search="true"
					title="%{getText('acctcostmap.acctMapId')}" edittype="text"
					editable="true" formatter="integer" key="true" hidden="true" />
				<sjg:gridColumn name="acctcode" index="acctcode" search="true"
					title="%{getText('acctcostmap.acctcode')}" editable="true"
					edittype="text" />

				<sjg:gridColumn name="acctname" index="acctname"
					title="%{getText('acctcostmap.acctname')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
					editable="true" edittype="text" />

				<sjg:gridColumn id="costitemid" name="costitemid" index="costitemid"
					title="%{getText('acctcostmap.costitemid')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
					editable="true" edittype="text" />

				<sjg:gridColumn name="costitem" index="costitem"
					title="%{getText('acctcostmap.costitem')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
					editable="true" edittype="text" />

			</sjg:grid>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select id="acctcostmap_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="acctcostmap_gridtable_totals"></label>条
			</span>
		</div>

		<div id="acctcostmap_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
