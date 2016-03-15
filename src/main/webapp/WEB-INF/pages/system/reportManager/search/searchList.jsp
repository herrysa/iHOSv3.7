<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title><fmt:message key="searchList.title" /></title>

<meta name="heading" content="<fmt:message key='searchList.heading'/>" />
<meta name="menu" content="SearchMenu" />
<%-- <script type="text/javascript"	src="${ctx}/jquerytheme/js/jquery-ui-1.8.19.custom.min.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/jqgrid/css/ui.jqgrid.css" />

<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/i18n/grid.locale-cn.js"></script> --%>
<c:if test="${importResult!=null && importResult!=''}">
	<script type="text/javascript">
		//alert("${importResult}");
	</script>
</c:if>
<script type="text/javascript">
	function refreshMasterGridCurrentPage() {
		var jq = jQuery('#search_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#search_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function addSearchRecord() {
		var url = "editSearch?popup=true";
		var winTitle = '<fmt:message key="searchNew.title"/>';
		popUpWindow(url, winTitle, "width=500");
	}
	function editSearchRecord() {
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectNone'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectMore'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
		} else {
			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "editSearch?popup=true&searchId=" + sid;
			var winTitle = '<fmt:message key="searchNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
	}
	function previewSearchDefine() {
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
				'selarrrow');
		//alert(sid);
		//return;
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			/* jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectNone'/>");
			jQuery('#mybuttondialog').dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>")
			return;
		}

		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			/* jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectMore'/>");
			jQuery('#mybuttondialog').dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectMore'/>");
			return;
		} else {

			var ret = jQuery("#search_gridtable").jqGrid('getRowData', sid);

			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "query?searchName=" + ret['searchName'];
			var winTitle = '<fmt:message key="searchPreview.title"/>';
			//popUpWindow(url, winTitle, "width=1000");
			//navTab.reload('preViewQueryList',url,{title:winTitle,fresh:true, data:{} });
			/* navTab.reload(url, {
				title : "New Tab",
				fresh : false,
				data : {}
			}); */
			//jQuery.pdialog.open(url, 'preViewQueryList', winTitle, {mask:false,width:900,height:800});
			menuClick(true);
			navTab.openTab('preViewQueryList', url, {
				title : winTitle,
				fresh : false,
				data : {}
			});
		}
	}

	function importCallback(msg) {
		var msgDialog = jQuery('#msgDialog');
		jQuery('div.#msgDialog').html(
				"<p class='ui-state-error'>" + msg + "</p>");
		msgDialog.dialog('open');
	}

	function importData() {
		//var url = "importSearch?navTabId=search_gridtable";
		var url = "#DIA_inline?inlineId=importDataDiv";
		$.pdialog.open(url, 'importSearch', "导入数据", {
			mask : false,
			width : 400,
			height : 200
		});
		/* var importDialog = jQuery("#importDialog");
		importDialog.dialog('option', 'buttons', {
			"<fmt:message key='dialog.ok'/>" : function() {
				jQuery(this).dialog("close");
				var url = "importExcel";
				importForm.target = "check_file_frame";
				importForm.submit();
				//window.location.href = url;
			},
			"<fmt:message key='dialog.cancel'/>" : function() {
				jQuery(this).dialog("close");
			}
		});
		importDialog.dialog("open"); */
	}

	function exportData() {
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
		'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("<fmt:message key='list.selectNone'/>");
		}else{
			var url = "#DIA_inline?inlineId=exportDataDiv";
			$.pdialog.open(url, 'exportSearch', "导出数据", {
				mask : false,
				width : 250,
				height : 150
			});
		}
		
		
		/* var confirmDialog = jQuery("#confirmDialog");
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectNone'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
		}
		confirmDialog
				.dialog(
						'option',
						'buttons',
						{
							"<fmt:message key='dialog.excel'/>" : function() {
								jQuery(this).dialog("close");
								var url = "exportExcel?searchName=${searchName}&exportSearchId="
										+ sid;
								window.location.href = url;
							},
							"<fmt:message key='dialog.sql'/>" : function() {
								jQuery(this).dialog("close");
								var url = "exportSql?exportSearchName=${searchName}&exportSearchId="
										+ sid;
								window.location.href = url;
							}
						});
		jQuery('div.#confirmDialog').html(
				"<fmt:message key='exportData.type'/>");
		confirmDialog.dialog("open"); */
	}
	
	function exportExcelData(){
		$.pdialog.closeCurrentDiv('exportDataDiv');
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
		'selarrrow');
		var url = "exportExcel?searchName=${searchName}&exportSearchId="
			+ sid;
		window.location.href = url;
	}
	
	function exportSqlData(){
		$.pdialog.closeCurrentDiv('exportDataDiv');
		var sid = jQuery("#search_gridtable").jqGrid('getGridParam',
		'selarrrow');
		var url = "exportSql?exportSearchName=${searchName}&exportSearchId="
			+ sid;
		window.location.href = url;
	}
	
	function getSelectRowData() {
		var grid = jQuery("#search_gridtable");
		var colModel = grid.jqGrid("getGridParam", 'colModel');
		/* var colNames= grid.jqGrid("getGridParam",'colNames'); 
		for (var i = 0; i < colNames.length; i++) {  
		    alert("lable:"+colNames[i]+ " name: " + colModel[i].name);
		}  */

		var id = jQuery("#search_gridtable").jqGrid('getGridParam', 'selrow');
		if (id) {
			var ret = jQuery("#search_gridtable").jqGrid('getRowData', id);
			var msg;
			for ( var i = 0; i < colModel.length; i++) {
				if (colModel[i].name != "cb")
					msg = msg + " " + colModel[i].name + "="
							+ ret[colModel[i].name];
				//alert(ret[colModel[i].name]);
			}
			alert(msg);
		} else {
			alert("Please select row");
		}
	}
	function okButton() {
		jQuery('#mybuttondialog').dialog('close');
	};
	datePick = function(elem) {
		jQuery(elem).datepicker({
			dateFormat : "<fmt:message key='date.format'/>"
		});
		jQuery('#ui-datepicker-div').css("z-index", 2000);
	};

	var selectedChanged = true;
	/* function loadCurrentDetailTab(selectedSearchId) {
		//var tabs = jQuery('#localtabsfdc').tabs('option', 'selected').;

		var tabs = jQuery('#localtabsfdc').tabs();
		var selected = tabs.tabs('option', 'selected');
		
		reloadTab(selected);
	} */

	/* function reloadTab(tabIndex) {
		var url = null;
		//alert(selected);
		if (tabIndex == 0) {
			url = "searchItemList?popup=true&searchId=" + selectedSearchId;
			//alert(url);

		} else if (tabIndex == 1) {
			url = "searchOptionList?popup=true&searchId=" + selectedSearchId;

		} else if (tabIndex == 3) {
			url = "searchLinkList?popup=true&searchId=" + selectedSearchId;

		} else if (tabIndex == 2) {
			url = "searchUrlList?popup=true&searchId=" + selectedSearchId;

		}
		var tabs = jQuery('#localtabsfdc').tabs();
		tabs.tabs('url', tabIndex, url);
		tabs.tabs('load', tabIndex, {
			mask : false
		});

	} */

	//var selectedSearchId = null;
	var selectedChanged = true;
	var dateTime = new Date();

	var clickTime = 0;
	var isSelected = 0;
	var timer;
	/* function loadCurrentDetailTab(selectedSearchId) {
		//var tabs = jQuery('#localtabsfdc').tabs('option', 'selected').;

		//var tabs = jQuery('#localtabsfdc').tabs();
		//var selected = tabs.tabs('option', 'selected');
		var selected = jQuery("#southTabs").attr("currentindex");
		//alert(selected);
		reloadTab(selected);
	} */

	function reloadTab(selectedSearchId) {
		var url = null;
		var selected;
		//alert(selected);
		/* if (tabIndex == 0) {
			url = "searchItemList?popup=true&searchId=" + selectedSearchId;
			//alert(url);

		} else if (tabIndex == 1) {
			url = "searchOptionList?popup=true&searchId=" + selectedSearchId;

		} else if (tabIndex == 3) {
			url = "searchLinkList?popup=true&searchId=" + selectedSearchId;

		} else if (tabIndex == 2) {
			url = "searchUrlList?popup=true&searchId=" + selectedSearchId;

		} */

		//var tabs = jQuery('#localtabsfdc').tabs();
		//tabs.tabs('url', tabIndex, url);
		//tabs.tabs('load', tabIndex);
		var i = 0;
		$("#southTabs").find("li").each(
				function() {
					if (i == 0) {
						url = "searchItemList?popup=true&searchId="
								+ selectedSearchId;
						//alert(url);

					} else if (i == 1) {
						url = "searchOptionList?popup=true&searchId="
								+ selectedSearchId;

					} else if (i == 3) {
						url = "searchLinkList?popup=true&searchId="
								+ selectedSearchId;

					} else if (i == 2) {
						url = "searchUrlList?popup=true&searchId="
								+ selectedSearchId;

					}
					$(this).find("a").eq(0).attr("href", url);
					if (jQuery(this).attr("class") == "selected") {
						selected = i;
						$(this).find("a").eq(0).trigger('click');
					}
					i++;
				});

		$("#background,#progressBar").hide();
	}

	function rowClick() {
		//alert("selectedSearchId is :" + selectedSearchId);
		isSelected = selectedSearchId;
		clickTime = 0;
		loadCurrentDetailTab(selectedSearchId);
	}
	var searchLayout;
	jQuery(document)
			.ready(
					function() {
						/* searchLayout = jQuery('body').layout(
								layoutSettings_taskdefine); */

						/* jQuery.subscribe('rowselect', function(event, data) {
							
							clickTime++;
							if(clickTime==1){
								timer = setTimeout("rowClick()","250"); 
							}else
							if(clickTime==2){
								clearTimeout(timer);
								//togglePane('southPane','searchListDiv');
								clickTime = 0;
							}
							
							var row = jQuery("#search_gridtable").jqGrid('getRowData',
									event.originalEvent.id);
							
							
							selectedSearchId = row['searchId'];
							
							
							//alert("selectedSearchId is :" + selectedSearchId);
							//loadCurrentDetailTab(selectedSearchId);
						});
						jQuery.subscribe('tabchange', function(event, data) {
							//alert("tabchange");
								var selected = event.originalEvent.ui.index;
								reloadTab(selected); 

							});	
						setTimeout("jQuery('#southPane').css('display','none')","50");  */
						/* 			 jQuery.subscribe('focusTopic', function(event,data) { 
						                 alert('focusTopic'); 
						         });  */
						/*    jQuery.subscribe('tabchange', function(event,data) { 
						           alert('tab = ' + event.originalEvent.ui.index); 
						    });  */

						/* 		jQuery('#tab1').click(function() {
									 alert('tab1 click.'); 
								}); */
						var searchChangeData = function(selectedSearchId) {
							reloadTab(selectedSearchId);
						}
						searchLayout = makeLayout(
								{
									'baseName' : 'search',
									'tableIds' : 'search_gridtable;southTabs',
									'key' : 'searchId',
									'proportion' : 2
								}, searchChangeData);
						//searchLayout.resizeAll();
						jQuery("#searchTabsContent").css('padding', 0);
					});
	/* 	function tabChanged(event, data){
	 alert("tabchange");
	 var selected = event.originalEvent.ui.index;
	 reloadTab(selected);
	 } */

	function stringFmatter(cellvalue, options, rowObject) {
		// do something here
		//return cellvalue.substring(0,20)+"..."
		return cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
	}

	function outLinkFomatter(cellvalue, options, rowObject) {

		return "<a href='http://www.baidu.com' target='blank'>" + cellvalue
				+ "</a>";

	}

	var fullHeight;
	var subGridHright = Math.floor(jQuery(document).height() / 2);
	var toggle = 0;
	/*
	 * 重置pane的方法,使用处：展开、折叠
	 */
	function reSizePane(openPane, opt, closePane) {
		if (opt == 0) {
			//defineLayout.sizePane(pane,southSize);
			jQuery("#" + closePane).css("display", "block");
			jQuery("#" + openPane).css("height", subGridHright);
			//jQuery("#"+pan).css("display","block");
			jQuery("#searchTabsContent").css("height", subGridHright - 210);
			jQuery("#searchItem_gridtable_div").css("height",
					subGridHright - 260);
			jQuery("#searchItem_gridtable").jqGrid("setGridHeight",
					subGridHright - 260);
			jQuery("#centerBar").css("display", "block");
		} else {
			//defineLayout.sizePane(pane,size);
			jQuery("#" + closePane).css("display", "none");
			jQuery("#" + openPane).css("height", fullHeight + 30);
			jQuery("#searchTabsContent").css("height", fullHeight - 40);

			jQuery("#searchItem_gridtable_div").css("height", fullHeight - 100);
			jQuery("#searchItem_gridtable").jqGrid("setGridHeight",
					fullHeight - 100);
			jQuery("#centerBar").css("display", "none");
		}

	}
	/*
	 * 关闭pane的方法，使用处：关闭
	 */
	function closeSouth(closePane, openPane) {
		//defineLayout.sizePane("south",southSize);
		//defineLayout.toggle('south');
		jQuery("#" + closePane).css("display", "none");
		jQuery("#" + openPane).css("height", fullHeight + 80);
		jQuery("#search_gridtable_div").css("height", fullHeight - 60);
		jQuery("#search_gridtable").jqGrid("setGridHeight", fullHeight - 60);
		//jQuery("#search_gridtable").jqGrid('setGridHeight', fullHeight-50);
	}

	/* function togglePane(openPane, narrowPane) {
		if (isSelected != selectedSearchId) {
			loadCurrentDetailTab(selectedSearchId);
		}
		//jQuery("#taskRemain").css("display","none");
		//jQuery("#dataCollectionRemainTask_gridtable").jqGrid('setGridParam',{height:500}).trigger("reloadGrid");});
		if (jQuery("#" + openPane).css("display") == "block") {
			jQuery("#" + openPane).css("display", "none");
			jQuery("#" + narrowPane).css("height", fullHeight);
		} else {
			fullHeight = jQuery("#" + narrowPane).height();
			jQuery("#" + narrowPane).css("height", subGridHright);
			jQuery("#search_gridtable_div").css("height", subGridHright - 30);
			//alert(jQuery("#"+narrowPane).css("height"));
			jQuery("#search_gridtable").jqGrid('setGridHeight',
					subGridHright - 60);
			jQuery("#" + openPane).css("height", subGridHright);
			jQuery("#" + openPane).css("display", "block");
		}

	} */
	function searchGridReload() {
		var urlString = "searchGridList";
		var searchId = jQuery("#searchIdTxt").val();
		var searchName = jQuery("#searchNameTxt").val();
		var searchTitle = jQuery("#searchTitleTxt").val();
		var subSystem = jQuery("#subSystemTxt").val();

		urlString = urlString + "?filter_LIKES_searchName=" + searchName
				+ "&filter_LIKES_title=" + searchTitle+"&filter_LIKES_searchId=" + searchId+"&filter_LIKES_subSystem="+subSystem;
		urlString = encodeURI(urlString);
		jQuery("#search_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	function adf() {
		alert(jQuery("#search_layout-south").width());
	}
</script>
</head>

<body>
	<div class="page">
		<div class="pageContent">
			<div id="search_container">
				<div id="search_layout-center" class="pane ui-layout-center"
					style="padding: 2px">
					<div class="pageHeader">
							<div class="searchBar">
								<div class="searchContent">
						<form onsubmit="return navTabSearch(this);" action=""
							method="post" class="queryarea-form">
									<label class="queryarea-label"><fmt:message key='search.searchId' />： <input
										type="text" id="searchIdTxt" class="input-small" size="10">
									</label>
									<label class="queryarea-label"><fmt:message key='search.searchName' />： <input
										type="text" id="searchNameTxt" class="input-small" size="10">
									</label>
									<label class="queryarea-label"><fmt:message key='search.title' />： <input
										type="text" id="searchTitleTxt" class="input-small">
									</label>
									<label class="queryarea-label"><fmt:message key='menu.subSystem' />： 
											<s:select name="search.subSystem" id="subSystemTxt"   maxlength="20"
														list="subSystems"  listKey="menuName"
														listValue="menuName" emptyOption="true" theme="simple">
											</s:select>
									</label>
								<div class="buttonActive" style="float: right;">
												<div class="buttonContent">
													<button type="button" onclick="searchGridReload()">
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
					<div id="centerBar" class="panelBar">
						<ul class="toolBar">
							<li><a class="addbutton" id="search_gridtable_add"
								href="javaScript:"><span><fmt:message
											key="button.add" /> </span> </a>
							</li>
							<li><a class="delbutton" id="search_gridtable_del"
								href="javaScript:" title="确定要删除吗?"><span><fmt:message
											key="button.delete" />
								</span> </a>
							</li>
							<li><a class="changebutton" id="search_gridtable_edit"
								href="javaScript:"><span><fmt:message
											key="button.edit" /> </span> </a>
							</li>
							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
							<li><a class="previewbutton" href="javaScript:previewSearchDefine();"><span><fmt:message
											key="button.testPreview" /> </span> </a>
							</li>
							<li><a class="importbutton" external="true"
								href="javaScript:importData();"><span><fmt:message
											key="button.importData" /> </span> </a>
							</li>
							<li><a class="exportbutton" external="true"
								href="javaScript:exportData();"><span><fmt:message
											key="button.exportData" /> </span> </a>
							</li>
							<li><a class="particularbutton" external="true"
								href="javaScript:searchLayout.optDblclick();"><span><fmt:message
											key="button.detail" /> </span> </a>
							</li>
							<%-- <li><a class="edit" external="true"
								href="javaScript:searchLayout.resizeAll()"><span><fmt:message
											key="button.detail" />
								</span> </a></li>
								<li><a class="edit" external="true"
								href="javaScript:adf()"><span><fmt:message
											key="button.detail" />
								</span> </a></li> --%>
						</ul>
					</div>
					<!-- <DIV id="searchListDiv" class="layoutBox" layoutH="27"> -->
					<sj:dialog id="mybuttondialog"
						buttons="{'OK':function() { okButton(); }}" autoOpen="false"
						modal="true" title="%{getText('messageDialog.title')}" />

					<sj:dialog id="importDialog"
						buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}">
						<s:form id="importForm" action="importExcel" method="post"
							enctype="multipart/form-data">
							<s:file id="excelfile" name="excelfile"></s:file>
						</s:form>
					</sj:dialog>
					<div id="importDataDiv" style="display: none">
						<div class="pageContent">
							<form id="importForm" method="post" action="importExcel"
								enctype="multipart/form-data" class="pageForm required-validate"
								onsubmit="return iframeCallback(this,uploadCallbackForInlineDiv);">
								<input type="hidden" name="dialogId" value="importDataDiv">
								<div class="pageFormContent" layoutH="56">
									<p>
										<label>请选择文件：</label> <input name="excelfile" id="excelfile"
											type="file" /> <input name="navTabId" id="navTabId"
											type="hidden" value="search_gridtable" />
									</p>
								</div>
								<div class="formBar">
									<ul>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button type="submit">提交</button>
												</div>
											</div>
										</li>
										<li><div class="button">
												<div class="buttonContent">
													<button type="button" class="close">取消</button>
												</div>
											</div>
										</li>
									</ul>
								</div>
							</form>
						</div>

					</div>
					<div id="exportDataDiv" style="display:none">
						<div class="pageContent">
							<form  method="post" class="pageForm required-validate">
								<div class="pageFormContent" layoutH="56">
									<p align="center" style="font-size: 16px;color:blue;width:200px;">
										<a href="javaScript:exportExcelData()" style="font-size: 16px;color:blue">EXCEL</a>&nbsp;/&nbsp;
										<a href="javaScript:exportSqlData()()" style="font-size: 16px;color:blue">SQL</a>
										<!-- <div class="button" style="width:100px;margin-left:50px">
												<div class="buttonContent">
													<button type="button" onclick="exportExcelData()"></button>
												</div>
											</div>
											<div class="button" style="width:30px;">
												<div class="buttonContent">
													<button type="button" onclick="exportSqlData()">SQL</button>
												</div>
											</div> -->
									</p>
								</div>
								<div class="formBar">
									<ul>
										<li><div class="button">
												<div class="buttonContent">
													<button type="button" class="close">取消</button>
												</div>
											</div>
										</li>
									</ul>
								</div>
							</form>
						</div>
					</div>
					<sj:dialog id="confirmDialog"
						buttons="{'%{getText('dialog.excel')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.sql')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />

					<!-- <DIV id="search_container" align="left" style="height: 800px;"></DIV> -->

					<s:url id="editurl" action="searchGridEdit" />
					<s:url id="remoteurl" action="searchGridList" />
					<div id="search_gridtable_div" layoutH="95"
						class="grid-wrapdiv"
						buttonBar="base_URL:editSearch;optId:searchId;width:650;height:570">
						<input type="hidden" id="search_gridtable_navTabId"
							value="${sessionScope.navTabId}" /> <input type="hidden"
							id="search_gridtable_isShowSouth" value="0" /> <label
							style="display: none" id="search_gridtable_addTile"> <fmt:message
								key="searchNew.title" /> </label> <label style="display: none"
							id="search_gridtable_editTile"> <fmt:message
								key="searchEdit.title" /> </label>
						<div id="load_search_gridtable"
							class='loading ui-state-default ui-state-active'></div>
						<sjg:grid id="search_gridtable" dataType="json"
							gridModel="searches" href="%{remoteurl}" editurl="%{editurl}"
							rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
							rownumbers="true" pager="false" page="1" pagerButtons="false"
							pagerInput="false" pagerPosition="right" navigator="false"
							navigatorAdd="false" navigatorEdit="false"
							navigatorDelete="false"
							navigatorDeleteOptions="{reloadAfterSubmit:true}"
							navigatorSearch="false"
							navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
							navigatorRefresh="false" multiselect="true" multiboxonly="true"
							resizable="true" height="500" draggable="true"
							onclick="searchLayout.optClick();" shrinkToFit="true"
							onCompleteTopics="onLoadSelect"
							ondblclick="searchLayout.optDblclick();" autowidth="true"
							toppager="false">
							<sjg:gridColumn name="searchId" search="false" index="searchId"
								title="%{getText('search.searchId')}" hidden="false" key="true"
								width="80" />
							<sjg:gridColumn name="searchName" index="searchName"
								title="%{getText('search.searchName')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
							<sjg:gridColumn name="title" index="title"
								title="%{getText('search.title')}" sortable="true" search="true"
								searchoptions="{sopt:['eq','ne','cn','bw']}" />
							<sjg:gridColumn name="multiSelect" index="multiSelect"
								title="%{getText('search.multiSelect')}" sortable="true"
								formatter="checkbox" width="70" />

							<sjg:gridColumn name="mysql" index="mysql"
								title="%{getText('search.mysql')}" sortable="true" search="true"
								searchoptions="{sopt:['eq','ne','cn','bw']}"
								formatter="stringFmatter" width="150" />
							<sjg:gridColumn name="myKey" index="myKey"
								title="%{getText('search.myKey')}" sortable="true" search="true"
								searchoptions="{sopt:['eq','ne','cn','bw']}"
								formatter="outLinkFomatter" width="100" />

							<sjg:gridColumn name="pageSize" index="pageSize"
								title="%{getText('search.pageSize')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								width="70" />
							<sjg:gridColumn name="entityName" index="entityName"
								title="%{getText('search.entityName')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								width="80" />

							<sjg:gridColumn name="ds" index="ds"
								title="%{getText('search.ds')}" sortable="true" search="true"
								searchoptions="{sopt:['eq','ne','cn','bw']}" hidden="true" />

							<%-- 			<sjg:gridColumn name="formAction" index="formAction"
				title="%{getText('search.formAction')}" sortable="true"
				search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
			<sjg:gridColumn name="formName" index="formName"
				title="%{getText('search.formName')}" sortable="true" search="true"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />
			<sjg:gridColumn name="groupBy" index="groupBy"
				title="%{getText('search.groupBy')}" sortable="true" search="true"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />
			
			
			<sjg:gridColumn name="orderBy" index="orderBy"
				title="%{getText('search.orderBy')}" sortable="true" search="true"
				searchoptions="{sopt:['eq','ne','cn','bw']}" />

			<sjg:gridColumn name="remark" index="remark"
				title="%{getText('search.remark')}" sortable="true" search="true"
				searchoptions="{sopt:['eq','ne','cn','bw']}" /> --%>
						</sjg:grid>
					</div>
					<div id="search_gridtable_footBar" class="panelBar">
						<div class="pages">
							<span>显示</span> <select id="search_gridtable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label id="search_gridtable_totals"></label>条</span>
						</div>

						<div id="search_gridtable_pagination" class="pagination"
							targetType="navTab" totalCount="200" numPerPage="20"
							pageNumShown="10" currentPage="1"></div>

					</div>
					<!-- </DIV> -->
				</div>

				<DIV id="search_layout-south" class="pane ui-layout-south"
					 style="padding: 2px;overflow:hidden !important">
					<div id="" class="panelBar">
						<ul class="toolBar">

							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
							<li style="float: right;"><a id="search_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" /> </span> </a>
							</li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a class="foldbutton" id="search_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" /> </span> </a>
							</li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a class="unfoldbutton" id="search_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" /> </span> </a>
							</li>
						</ul>
					</div>

					<div class="tabs" currentIndex="0" eventType="click" id="southTabs" tabcontainer="search_layout-south" extraHeight=22>
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="searchItemList?popup=true" class="j-ajax"><span><fmt:message
													key='searchItemList.title' /> </span> </a></li>
									<li><a href="searchOptionList?popup=true" class="j-ajax"><span><fmt:message
													key='searchOptionList.title' /> </span> </a></li>
									<li><a href="searchLinkList?popup=true" class="j-ajax"><span><fmt:message
													key='searchUrlList.title' /> </span> </a></li>
									<li><a href="searchUrlList?popup=true" class="j-ajax"><span><fmt:message
													key='searchLinkList.title' /> </span> </a></li>
								</ul>
							</div>
						</div>
						<div id="searchTabsContent" class="tabsContent"
							style="height: 250px;">
							<div id="tab1"></div>
							<div id="tab2"></div>
							<div id="tab3"></div>
							<div id="tab4"></div>
						</div>
						<div class="tabsFooter">
							<div class="tabsFooterContent"></div>
						</div>
					</div>

				</DIV>
			</div>
		</div>
	</div>
	<iframe name="check_file_frame" style="display: none"></iframe>
</body>
</html>
