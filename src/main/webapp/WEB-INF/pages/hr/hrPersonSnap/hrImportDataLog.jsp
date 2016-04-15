<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<head>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			var taskInterId = "${param.taskInterId}";
			jQuery("#${random}importDataDefine_gridtable_log").jqGrid({
				url : "importDataLogGridList?taskInterId=" + taskInterId,
				editurl : "",
				datatype : "json",
				mtype : "GET",
				colModel : [
	{name:'logId',index:'logId',label:'<s:text name="interLogger.logId"/>',formatter:'integer',hidden:true,key:true},
	{name:'periodCode',index:'periodCode',aligh:'center',width:'80px',label:'<s:text name="interLogger.periodCode"/>',hidden:false},
	{name:'taskInterId',index:'taskInterId',aligh:'left',width:'130px',label:'<s:text name="interLogger.taskInterId"/>',hidden:true},
	{name:'logDateTime',index:'logDateTime',aligh:'left',width:'130px',label:'<s:text name="interLogger.logDateTime"/>',formatter:"date",formatoptions:{srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i:s'},hidden:false},
	{name:'logFrom',index:'logFrom',aligh:'center',width:'80px',label:'<s:text name="interLogger.logFrom"/>',hidden:false},
	{name:'logMsg',index:'logMsg',aligh:'left',width:'300px',label:'<s:text name="interLogger.logMsg"/>',hidden:false}
				],jsonReader : {
					root : "interLoggers",
					page : "page",
					total : "total",
					records : "records",
					repeatitems : false
				},
				sortname : 'logDateTime',
				viewrecords : true,
				sortorder : 'desc',
				height : 300,
				gridview : true,
				rowNum:'100',
				rownumbers : true,
				loadui : "disable",
				multiselect : true,
				multiboxonly : true,
				shrinkToFit : false,
				autowidth : false,
				onSelectRow : function(rowid) {
				},
				gridComplete : function() {
					var dataTest = {
						"id" : "${random}importDataDefine_gridtable_log"
					};
					jQuery.publish("onLoadSelect", dataTest, null);
				}
			});
			jQuery("#${random}importDataDefine_gridtable_log").jqGrid('bindKeys');
			
			if(taskInterId) {
				jQuery.ajax({
					url:"findImportDataDefineById?interfaceId="+taskInterId,
					dataType:"json",
					type:"get",
					success:function(data) {
						jQuery("#${random}interfaceName_log").text(data.interfaceName);
					}
				});
			}
			
		});
	</script>
</head>
<div class="page" id="${random}dvShowImportDataLog">

	<div class="pageContent" id="${random}detailPageContent">
		<div class="panelBar" style="margin-top: 1px;">
			<ul class="toolBar">
				<li><a class="canceleditbutton" onclick="$.pdialog.closeCurrent();" href="javaScript:void(0);"><span><s:text
								name="button.close" /></span></a></li>
			</ul>
		</div>
		<div id="${random}importDataDefine_gridtable_log_div" class="grid-wrapdiv"
			buttonBar="width:500;height:300;" layoutH="58">
			<div id="${random}load_importDataDefine_gridtable_log" class='loading ui-state-default ui-state-active' style="display: none"></div>
			<table id="${random}importDataDefine_gridtable_log" style="width: 450px;"></table>
		</div>
	</div>
	<div class="panelBar" id="${random}importDataDefine_gridtable_log_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}importDataDefine_gridtable_log_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}importDataDefine_gridtable_log_totals"></label><s:text name="pager.items" /></span>
		</div>
		<%-- <div id="${random}importDataDefine_gridtable_log_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div> --%>
	</div>
</div>