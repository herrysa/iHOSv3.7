<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.huge.ihos.system.reportManager.search.model.SearchOption"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<html>
<head>
<title>querySingleList</title>
<meta name="heading"
	content="<fmt:message key='searchUrlList.heading'/>" />
<meta name="menu" content="SearchUrlMenu" />
<!-- <link rel="stylesheet" type="text/css" media="all"	href="/struts/themes/ui.jqgrid.css" /> -->
<!-- <script type="text/javascript" src="/scripts/jqgrid/js/jquery.jqGrid.min.js"></script> -->
<!-- <script type="text/javascript" src="/struts/js/plugins/jquery.jqGrid.js"></script> -->
<!-- <script type="text/javascript" src="/struts/js/struts2/jquery.grid.struts2-3.2.1.min.js"></script> -->
<!-- <script type="text/javascript" src="/struts/i18n/grid.locale-cn.js"></script> -->

<!-- <script type="text/javascript" src="/struts/js/plugins/grid.formedit.js"></script> -->


<%-- <link rel="stylesheet" type="text/css" media="screen"
	href="${ctx}/jquerytheme/css/redmond/jquery-ui-1.8.19.custom.css" />
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/jquery-1.7.2.min.js"></script>
<script>
	jQuery.noConflict();
</script>
<script type="text/javascript"	src="${ctx}/jquerytheme/js/jquery-ui-1.8.19.custom.min.js"></script> --%>
<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/jqgrid/css/ui.jqgrid.css" />

<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/i18n/grid.locale-cn.js"></script>
  <%-- <script type="text/javascript" src="${ctx}/scripts/fancyBox/source/jquery.fancybox.js?v=2.0.6"></script> --%>
<script type="text/javascript">



	jQuery(document).ready(function() {
		jQuery("#${random}_${searchName}_gridTable").jqGrid({
			url : "${ctx}/queryListJson?searchName=${searchName}",
			editurl:  "${ctx}/pub?searchName=${searchName}",
			datatype : "json",
			mtype : "GET",
			height : "100%",
			autowidth : true,
			shrinkToFit :false,
			colModel : [
			<c:forEach items="${searchOptions}" var="op">
			{
				name : "${op.fieldNameUpperCase }",
				index : "${op.fieldName }",
				label : "${op.title}",
				width : "${op.displayWidth*10}",
				<c:if test="${op.fieldName==viewDef.key}">
					key:true,
					frozen : false,
				</c:if>
				
				//formater
			<c:if test="${op.fieldType=='Boolean'}">
					formatter :"checkbox",
				</c:if>

					
				align:'${op.alignType}'
			},
			
			</c:forEach>
			            
			     ],
			viewrecords : true,
			rowNum : ${viewDef.pageSize},
			rowList : [${viewDef.pageSize},${viewDef.pageSize}*2,${viewDef.pageSize}*3,${viewDef.pageSize}*4,${viewDef.pageSize}*5,${viewDef.pageSize}*6,${viewDef.pageSize}*7,${viewDef.pageSize}*8,${viewDef.pageSize}*9,${viewDef.pageSize}*10],
			prmNames : {
				search : "search"
			}, //(1)
			jsonReader : {
				root : "queryList", // (2)
				records : "records", // (3)
				repeatitems : false	// (4)
			},
			//pager : "#gridPager",
			hidegrid : false,
			<c:if test="${viewDef.multiSelect}">
			multiselect: true,
			multiboxonly:true,
			</c:if>
			altRows: false,
			rownumbers: true,
			loadui: "disable",
			gridComplete:function(){
				 makepager("${random}_${searchName}_gridTable");
				 jQuery("#gview_${random}_${searchName}_gridTable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							//jQuery(this).css("vertical-align","middle");
							//jQuery(this).find("div").eq(0).css("margin-top","5px");
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
				});
			}
		});
		jQuery("#${random}_${searchName}_gridTable").jqGrid('navGrid', '#gridPager', {edit:false,add:false,del:false,search:false},
				{},
				{},
				{},
				{multipleSearch:false, multipleGroup:false, showQuery: true}
				);
		
		
		
		
		jQuery("#${random}_${searchName}_gridTable").jqGrid('setFrozenColumns');
	});
	
	function gridReload(){
		var urlString = "${ctx}/queryListJson?searchName=${searchName}";
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			<c:forEach items="${searchItems}" var="item">
				var ${item.htmlField} = jQuery("#${item.htmlField}").val();
			</c:forEach>
		</c:if>
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		urlString=encodeURI(urlString);
		jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function getSelectRowData(sId,dId,varId,disId,isSingle){
		//alert(isSingle==='1');
		//alert(isSingle);
		var grid = jQuery("#${random}_${searchName}_gridTable");
		var colModel = grid.jqGrid("getGridParam",'colModel');  
		if(isSingle==='1'){
			var ids = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
			if(ids.length>1){
				alert("Please select only one row");
			}else{
				
			var id = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selrow');
			if (id)	{
				var ret = jQuery("#${random}_${searchName}_gridTable").jqGrid('getRowData',id);
				var value = ret[varId];
				var display = ret[disId];
				jQuery('#'+sId).attr("value",value);
				jQuery('#'+dId).attr("value",display);
				$.pdialog.closeCurrent();
			} else { alert("Please select row");}}
		}else{
			var id = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
			if(id.length>0){
				var values=new Array();
				var displays=new Array();
				
				for (i=0;i<id.length;i++)
				{
					var ret = jQuery("#${random}_${searchName}_gridTable").jqGrid('getRowData',id[i]);
					values[i] = ret[varId];
					displays[i] = ret[disId];
					//alert(id[i]);
				}
				//alert(values);
				//alert(displays);
				jQuery('#'+sId).attr("value",values);
				jQuery('#'+dId).attr("value",displays);
				$.pdialog.closeCurrent();
			}else{
				alert("Please select row");
			}
		
		}
	}

</script>
</head>
<%-- <sj:dialog id="msgDialog"
	buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<sj:dialog id="confirmDialog"
	buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<sj:dialog id="testDialog"
	buttons="{'OK':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" /> --%>
<div class="page">
	<div class="pageContent">
<div class="pageHeader">
<div class="searchBar">
		<div class="searchContent">
	<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
		<c:forEach items="${searchItems}" var="item">
			<label style="float: none">${item.caption}：
			<c:choose>
				<c:when test="${item.userTag=='yearMonth'}">
					<appfuse:yearMonth htmlField="${item.htmlField}"></appfuse:yearMonth>
				</c:when>
				<c:when test="${item.userTag=='checkbox'}">
					<input type="checkbox" id="${item.htmlField}" />
				</c:when>
				<c:when test="${item.userTag=='stringSelect'}">
					<appfuse:stringSelect htmlFieldName="${item.htmlField}"
						paraString="${item.param1}"></appfuse:stringSelect>
				</c:when>
				<c:when test="${item.userTag=='dicSelect'}">
					<appfuse:dictionary code="${item.param1}" name="${item.htmlField}" required="false" />	
				</c:when>

				<c:otherwise>
					<input type="text" id="${item.htmlField}" />
				</c:otherwise>
			</c:choose>
</label>





			<%-- 			<c:if test="${item.userTag=='yearMonth'}">
				<appfuse:yearMonth htmlField="${item.htmlField}"></appfuse:yearMonth>
			</c:if>
			<input type="text" id="${item.htmlField}" /> --%>
		</c:forEach>
		<!-- <button onclick="gridReload()" id="submitButton"
			style="margin-left: 30px;">Search</button> -->
			</c:if>
			</div>
			<div class="subBar">
								<ul>
									<li><div class="buttonActive">
											<div class="buttonContent">
												<button type="button" onclick="gridReload()"><fmt:message key='button.search'/></button>
											</div>
										</div></li>
									<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
								</ul>
							</div>
	</div>
	
</div>
<div class="panelBar">
						<ul class="toolBar">
							<li><a class="add" id="search_gridtable_add"
								href="javaScript:" onclick="getSelectRowData('${tarID}','${tarName}','${gridID}','${gridName}','${isSingle}');"><span>获取选择行数据</span> </a></li>
							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
						</ul>
					</div>
<!-- <h2>jqGrid Test 01</h2>
<a href="" id="pub_delete">Get Selected id's</a> 
<button id="pub_delete" style="margin-left: 0px;" onclick="confirmDelete();"><fmt:message key='button.delete'/></button>
<button id="pub_process" style="margin-left: 0px;" onclick="selectConfirmProcess('test_proc','&ARGS=CurrentPeriod&ARGS=SelIDS','process messages.');"><fmt:message key='button.delete'/></button>
-->
<%-- <c:forEach items="${searchUrls}" var="op">
	<button id="${op.searchUrlId}" style="margin-left: 0px;"
		onclick="${op.url}">${op.title}</button>
</c:forEach> --%>

 <%-- <input type="button" class="btn btn-primary" value="获取选择行数据" onclick="getSelectRowData('${tarID}','${tarName}','${gridID}','${gridName}');"/> --%>


<div layoutH="118">
	<table id="${random}_${searchName}_gridTable"></table>
	<div id="gridPager"></div>
	
</div>
<div class="panelBar">
						<div class="pages">
							<span>显示</span> <select id="${random}_${searchName}_gridTable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label
								id="${random}_${searchName}_gridTable_totals"></label>条</span>
						</div>

						<div id="${random}_${searchName}_gridTable_pagination"
							class="pagination" targetType="navTab" totalCount="200"
							numPerPage="20" pageNumShown="10" currentPage="1"></div>

</div>
</div>
</div>