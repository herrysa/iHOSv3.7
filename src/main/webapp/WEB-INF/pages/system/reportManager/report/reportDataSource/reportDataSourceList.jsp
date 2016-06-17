
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportDataSourceLayout;
	var reportDataSourceGridIdString="#reportDataSource_gridtable";
	
	jQuery(document).ready(function() { 
		var reportDataSourceGrid = jQuery(reportDataSourceGridIdString);
    	reportDataSourceGrid.jqGrid({
    		url : "reportDataSourceGridList",
    		editurl:"reportDataSourceGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'code',index:'code',align:'center',label : '<s:text name="reportDataSource.code" />',hidden:false,key:true},{name:'dataSource',index:'dataSource',align:'center',label : '<s:text name="reportDataSource.dataSource" />',hidden:false},{name:'dsDesc',index:'dsDesc',align:'center',label : '<s:text name="reportDataSource.dsDesc" />',hidden:false},{name:'name',index:'name',align:'center',label : '<s:text name="reportDataSource.name" />',hidden:false},{name:'remark',index:'remark',align:'center',label : '<s:text name="reportDataSource.remark" />',hidden:false},{name:'reportId',index:'reportId',align:'center',label : '<s:text name="reportDataSource.reportId" />',hidden:false}        	],
        	jsonReader : {
				root : "reportDataSources", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'code',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportDataSourceList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"reportDataSource_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("reportDataSource_gridtable");
       		} 

    	});
    jQuery(reportDataSourceGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportDataSource_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportDataSource_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.code'/>:
						<input type="text" name="filter_EQS_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.dataSource'/>:
						<input type="text" name="filter_EQS_dataSource"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.dsDesc'/>:
						<input type="text" name="filter_EQS_dsDesc"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.name'/>:
						<input type="text" name="filter_EQS_name"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDataSource.reportId'/>:
						<input type="text" name="filter_EQS_reportId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(reportDataSource_search_form,reportDataSource_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(reportDataSource_search_form,reportDataSource_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="reportDataSource_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportDataSource_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportDataSource_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportDataSource_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="reportDataSource_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportDataSource_gridtable_addTile">
				<s:text name="reportDataSourceNew.title"/>
			</label> 
			<label style="display: none"
				id="reportDataSource_gridtable_editTile">
				<s:text name="reportDataSourceEdit.title"/>
			</label>
			<div id="load_reportDataSource_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportDataSource_gridtable"></table>
			<!--<div id="reportDataSourcePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportDataSource_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportDataSource_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportDataSource_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>