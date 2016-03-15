<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="mccKeyList.title"/></title>
    <meta name="heading" content="<fmt:message key='mccKeyList.heading'/>"/>
    <meta name="menu" content="MccKeyMenu"/>
    
    <script type="text/javascript">
    function gridReload() {
		var urlString = "mccKeyGridList";
		var chartType = jQuery("#chartType").val();
		var ckey = jQuery("#ckey").val();
		var caption = jQuery("#caption").val();
		var subCaption = jQuery("#subCaption").val();
		urlString = urlString + "?filter_LIKES_chartType="+chartType+"&filter_LIKES_ckey="+ckey+"&filter_LIKES_caption="+caption+"&filter_LIKES_subCaption="+subCaption;
		urlString = encodeURI(urlString);
		jQuery("#mccKey_gridtable").jqGrid('setGridParam', {url : urlString,page : 1}).trigger("reloadGrid");
	}
    function stringFmatter (cellvalue, options, rowObject)
	{
	   // do something here
	   //return cellvalue.substring(0,20)+"..."
		return cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
	}
	/* var defineLayout;
	var southSize = Math.floor(screen.availHeight / 2);
	var layoutSettings_taskdefine = {
			applyDefaultStyles : true // basic styling for testing & demo purposes
			//  	,	south__size:				500
			//,	south__minheight:			300
			,
			south__resizable : false // OVERRIDE the pane-default of 'resizable=true'
			,
			south__spacing_open : 0 // no resizer-bar when open (zero height)
			,
			south__spacing_closed : 0 // big resizer-bar when open (zero height)
			,
			south__maxHeight : southSize // 1/2 screen height
			,
			south__closable : true,
			south__size : southSize
			//south__initHidden : true
	}; */
    /* 	function addRecord(){
			var url = "editMccKey?popup=true";
			var winTitle='<fmt:message key="mccKeyNew.title"/>';
			//$.pdialog.open(url, 'addMccKey', winTitle, {mask:false,width:1000,height:300});
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#mccKey_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editMccKey?popup=true&mccKeyId=" + sid;
				var winTitle='<fmt:message key="mccKeyNew.title"/>';
				openWindow(url, winTitle, " width=900");
			}
		} */
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		var mccKeyLayout;
	     jQuery(document).ready(function() { 
	    	
	    	 var mccKeyChangeData = function(selectedSearchId){
					var url = "mccKeyDetailGridJsonList?popup=true&mccKeyId="+selectedSearchId;
					jQuery("#mccKeyDetail_gridtable").jqGrid('setGridParam',{url : url}).trigger("reloadGrid");
					
					$("#background,#progressBar").hide();
			 }
    	 mccKeyLayout = makeLayout({'baseName':'mccKey','tableIds':'mccKey_gridtable;mccKeyDetail_gridtable','proportion':2,'key':'mccKeyId'},mccKeyChangeData);
	    });
     jQuery.subscribe('reFormatColumnData', function(event, data) {
    	 var rowNum = jQuery(data).getDataIDs().length;
		 var ret = jQuery(data).jqGrid('getRowData');
		 if(rowNum > 0){
	    	 var rowIds = jQuery(data).getDataIDs();
	    	 for (var i=0;i<rowNum;i++){
	    		var id = rowIds[i];
			    var colorData = ret[i]["color"];
	    		jQuery(data).setCell(id,'color','',{background:colorData});
	    		//jQuery(data).setCell(id,'warning','','pzCell');
	    	 } 
	    	}
		});

	</script>
	<style>
	.pzCell{
		border-right-color:red !important
	}
	</style>
</head>
<div id="mccKey_page" class="page">
	<div class="pageContent">
		<div id="mccKey_container">
			<!-- MCCKEY START -->

			<div id="mccKey_layout-center" class="pane ui-layout-center"
				style="padding: 2px">
				<div class="pageHeader">
					<div class="searchBar">
						<div class="searchContent">
							<form onsubmit="return navTabSearch(this);" action="userGridList"
								method="post" class="queryarea-form">

								<label class="queryarea-label"> <s:select id="chartType"
										key="mccKey.chartType"
										list='#{"":"---请选择---","饼图":"饼图","柱形图":"柱形图","折线图":"折线图","表盘":"表盘"}' />
								</label> <label class="queryarea-label"> <fmt:message
										key='mccKey.ckey' />： <input type="text" class="input-big"
									id="ckey" size="15">
								</label> <label class="queryarea-label"> <fmt:message
										key='mccKey.caption' />： <input type="text" class="input-big"
									id="caption" size="20">
								</label> <label class="queryarea-label"> <fmt:message
										key='mccKey.subCaption' />： <input type="text"
									class="input-big" id="subCaption" size="20">
								</label>


								<div class="buttonActive" style="float: right;">
									<div class="buttonContent">
										<button type="button" onclick="gridReload()">
											&nbsp;&nbsp;
											<fmt:message key='button.search' />
											&nbsp;&nbsp;
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
				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />
				<s:url id="editurl" action="mccKeyGridEdit" />
				<s:url id="remoteurl" action="mccKeyGridList" />


				<div class="panelBar">
					<ul class="toolBar">
						<li><a id="mccKey_gridtable_add" class="add"
							href="javaScript:"><span><fmt:message
										key="button.add" /></span></a></li>
						<li><a id="mccKey_gridtable_del" class="delete"
							href="javaScript:"><span>删除</span></a></li>
						<li><a id="mccKey_gridtable_edit" class="edit"
							href="javaScript:"><span><fmt:message
										key="button.edit" /></span></a></li>
						<li><a class="particularbutton" external="true"
							href="javaScript:mccKeyLayout.optDblclick();"><span>明细</span>
						</a></li>
					</ul>
				</div>

				<div id="mccKey_gridtable_div" layoutH="95" class="grid-wrapdiv"
					buttonBar="base_URL:editMccKey;optId:mccKeyId;width:850;height:600">
					<input type="hidden" id="mccKey_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="mccKey_gridtable_addTile"><fmt:message
							key="mccKeyNew.title" /></label> <label style="display: none"
						id="mccKey_gridtable_editTile"><fmt:message
							key="mccKeyEdit.title" /></label> <label style="display: none"
						id="mccKey_gridtable_selectNone"><fmt:message
							key='list.selectNone' /></label> <label style="display: none"
						id="mccKey_gridtable_selectMore"><fmt:message
							key='list.selectMore' /></label>
					<div id="load_mccKey_gridtable"
						class='loading ui-state-default ui-state-active'></div>
					<sjg:grid id="mccKey_gridtable" dataType="json" gridModel="mccKeys"
						href="%{remoteurl}" editurl="%{editurl}"
						rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
						onCompleteTopics="onLoadSelect" rownumbers="true" pager="false"
						page="1" pagerButtons="false" onSelectRowTopics="rowselect"
						pagerInput="false" pagerPosition="right" navigator="false"
						sortname="ckey" navigatorAdd="false" navigatorEdit="false"
						navigatorDelete="false"
						navigatorDeleteOptions="{reloadAfterSubmit:true}"
						navigatorSearch="false"
						navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
						navigatorRefresh="false" multiselect="true" multiboxonly="true"
						resizable="true" autowidth="true"
						onclick="mccKeyLayout.optClick()"
						ondblclick="mccKeyLayout.optDblclick();">
						<sjg:gridColumn name="mccKeyId" search="false" index="mccKeyId"
							title="%{getText('mccKey.mccKeyId')}" hidden="true" key="true" />
						<sjg:gridColumn name="ckey" index="ckey"
							title="%{getText('mccKey.ckey')}" sortable="true" search="true"
							width="50" searchoptions="{sopt:['eq','ne','cn','bw']}" />
						<sjg:gridColumn name="caption" index="caption" width="100"
							title="%{getText('mccKey.caption')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
						<sjg:gridColumn name="subCaption" index="subCaption" width="100"
							title="%{getText('mccKey.subCaption')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
						<sjg:gridColumn name="chartType" index="chartType"
							title="%{getText('mccKey.chartType')}" sortable="true"
							search="true" width="50"
							searchoptions="{sopt:['eq','ne','cn','bw']}" />
						<sjg:gridColumn name="keyField" index="keyField"
							title="%{getText('mccKey.keyField')}" sortable="true" width="50"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
						<sjg:gridColumn name="mysql" index="mysql"
							title="%{getText('mccKey.mysql')}" sortable="true" search="true"
							width="200" searchoptions="{sopt:['eq','ne','cn','bw']}"
							formatter="stringFmatter" />
					</sjg:grid>
				</DIV>
				<div id="mccKey_gridtable_footBar" class="panelBar">
					<div class="pages">
						<span>显示</span> <select id="mccKey_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label id="mccKey_gridtable_totals"></label>条
						</span>
					</div>
					<div id="mccKey_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>

				</div>
			</div>

			<!-- MCCKEY END -->


			<!-- MCCKEYDETAIL START -->

			<DIV id="mccKey_layout-south" class="pane ui-layout-south"
				style="padding: 2px">
				<div class="panelBar">
					<ul class="toolBar">
						<li><a id="mccKeyDetail_gridtable_add" class="add"
							href="javaScript:"><span><fmt:message
										key="button.add" /></span></a></li>
						<li><a id="mccKeyDetail_gridtable_del" class="delete"
							href="javaScript:"><span>删除</span></a></li>
						<li><a id="mccKeyDetail_gridtable_edit" class="edit"
							href="javaScript:"><span><fmt:message
										key="button.edit" /></span></a></li>
						<li style="float: right;"><a id="mccKey_close"
							href="javaScript:" class="closebutton"><span><fmt:message
										key="button.close" /></span></a></li>
						<li class="line" style="float: right">line</li>
						<li style="float: right;"><a id="mccKey_fold"
							href="javaScript:" class="foldbutton"><span><fmt:message
										key="button.fold" /></span></a></li>
						<li class="line" style="float: right">line</li>
						<li style="float: right"><a id="mccKey_unfold"
							href="javaScript:" class="unfoldbutton"><span><fmt:message
										key="button.unfold" /></span></a></li>
					</ul>
				</div>
				<div id="mccKeyDetail_gridtable_div" class="grid-wrapdiv"
					tablecontainer="mccKey_layout-south" extraHeight=82
					buttonBar="fatherGrid:mccKey_gridtable;extraParam:mccKeyId;width:600;height:220">
					<input type="hidden" id="mccKeyDetail_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="mccKeyDetail_gridtable_addTile"><fmt:message
							key="mccKeyDetailNew.title" /> </label> <label style="display: none"
						id="mccKeyDetail_gridtable_editTile"> <fmt:message
							key="mccKeyDetailEdit.title" />
					</label> <label style="display: none"
						id="mccKeyDetail_gridtable_selectNone"> <fmt:message
							key='list.selectNone' />
					</label> <label style="display: none"
						id="mccKeyDetail_gridtable_selectMore"> <fmt:message
							key='list.selectMore' />
					</label>
					<sj:dialog id="mybuttondialog"
						buttons="{'OK':function() { okButton(); }}" autoOpen="false"
						modal="true" title="%{getText('messageDialog.title')}" />
					<s:url id="editurl2" action="mccKeyDetailGridEdit" />
					<s:url id="remoteurl2" action="mccKeyDetailGridList">
						<s:param name="allotItemId" value="#parameters.allotItemId"></s:param>
					</s:url>

					<sjg:grid id="mccKeyDetail_gridtable" dataType="json"
						sortname="minValue" sortorder="asc" gridModel="mccKeyDetails"
						href="%{remoteurl2}" editurl="%{editurl2}"
						onGridCompleteTopics="reFormatColumnData"
						onCompleteTopics="onLoadSelect" rownumbers="true" pager="fasle"
						page="1" pagerButtons="fasle" pagerInput="fasle"
						pagerPosition="fasle" multiselect="true" multiboxonly="true"
						resizable="true" autowidth="true" draggable="true">
						<sjg:gridColumn name="mccKeyId" index="mccKeyId" hidden="true"
							title="%{getText('mccKeyDetail.mccKeyId')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="10" />
						<sjg:gridColumn name="mccKeyDetailId" search="false"
							index="mccKeyDetailId"
							title="%{getText('mccKeyDetail.mccKeyDetailId')}" hidden="false"
							key="true" width="10" />
						<sjg:gridColumn name="state" index="state"
							title="%{getText('mccKeyDetail.state')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="10" />
						<sjg:gridColumn name="minValue" index="minValue"
							title="%{getText('mccKeyDetail.minValue')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="20" />
						<sjg:gridColumn name="maxValue" index="maxValue"
							title="%{getText('mccKeyDetail.maxValue')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="20" />
						<sjg:gridColumn name="warning" index="manual"
							title="%{getText('mccKeyDetail.warning')}" sortable="true"
							edittype="checkbox" formatter="checkbox" search="true"
							searchoptions="{sopt:['eq','ne']}" editrules="{required: true}"
							width="10" />
						<sjg:gridColumn name="color" index="color" align="right"
							title="%{getText('mccKeyDetail.color')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="7" />
					</sjg:grid>
				</div>
				<div id="mccKeyDetail_gridtable_footBar" class="panelBar">
					<div class="pages">
						<span>显示</span> <select id="mccKeyDetail_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label id="mccKeyDetail_gridtable_totals"></label>条
						</span>
					</div>
					<div id="mccKeyDetail_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>
				</div>
			</div>
			<!-- MCCKEYDETAIL END -->


		</div>
	</div>
</div>