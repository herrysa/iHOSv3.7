
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportPlanItemLayout;
	var reportPlanItemGridIdString="#reportPlanItem_gridtable";
	
	jQuery(document).ready(function() { 
		var reportPlanItemGrid = jQuery(reportPlanItemGridIdString);
    	reportPlanItemGrid.jqGrid({
    		url : "reportPlanItemGridList",
    		editurl:"reportPlanItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'colId',index:'colId',align:'center',label : '<s:text name="reportPlanItem.colId" />',hidden:false,key:true},{name:'colName',index:'colName',align:'center',label : '<s:text name="reportPlanItem.colName" />',hidden:false},{name:'colSn',index:'colSn',align:'center',label : '<s:text name="reportPlanItem.colSn" />',hidden:false,formatter:'integer'},{name:'colWidth',index:'colWidth',align:'center',label : '<s:text name="reportPlanItem.colWidth" />',hidden:false,formatter:'integer'},{name:'fontIndex',index:'fontIndex',align:'center',label : '<s:text name="reportPlanItem.fontIndex" />',hidden:false},{name:'headerFontIndex',index:'headerFontIndex',align:'center',label : '<s:text name="reportPlanItem.headerFontIndex" />',hidden:false},{name:'headerTextColor',index:'headerTextColor',align:'center',label : '<s:text name="reportPlanItem.headerTextColor" />',hidden:false},{name:'isThousandSeparat',index:'isThousandSeparat',align:'center',label : '<s:text name="reportPlanItem.isThousandSeparat" />',hidden:false},{name:'itemCode',index:'itemCode',align:'center',label : '<s:text name="reportPlanItem.itemCode" />',hidden:false},{name:'planId',index:'planId',align:'center',label : '<s:text name="reportPlanItem.planId" />',hidden:false}        	],
        	jsonReader : {
				root : "reportPlanItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'colId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportPlanItemList.title" />',
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
           	var dataTest = {"id":"reportPlanItem_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("reportPlanItem_gridtable");
       		} 

    	});
    jQuery(reportPlanItemGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportPlanItem_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportPlanItem_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.colId'/>:
						<input type="text" name="filter_EQS_colId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.colName'/>:
						<input type="text" name="filter_EQS_colName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.colSn'/>:
						<input type="text" name="filter_EQS_colSn"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.colWidth'/>:
						<input type="text" name="filter_EQS_colWidth"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.fontIndex'/>:
						<input type="text" name="filter_EQS_fontIndex"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.headerFontIndex'/>:
						<input type="text" name="filter_EQS_headerFontIndex"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.headerTextColor'/>:
						<input type="text" name="filter_EQS_headerTextColor"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.isThousandSeparat'/>:
						<input type="text" name="filter_EQS_isThousandSeparat"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.itemCode'/>:
						<input type="text" name="filter_EQS_itemCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanItem.planId'/>:
						<input type="text" name="filter_EQS_planId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(reportPlanItem_search_form,reportPlanItem_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(reportPlanItem_search_form,reportPlanItem_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="reportPlanItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportPlanItem_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportPlanItem_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportPlanItem_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="reportPlanItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportPlanItem_gridtable_addTile">
				<s:text name="reportPlanItemNew.title"/>
			</label> 
			<label style="display: none"
				id="reportPlanItem_gridtable_editTile">
				<s:text name="reportPlanItemEdit.title"/>
			</label>
			<div id="load_reportPlanItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportPlanItem_gridtable"></table>
			<!--<div id="reportPlanItemPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportPlanItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportPlanItem_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportPlanItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>