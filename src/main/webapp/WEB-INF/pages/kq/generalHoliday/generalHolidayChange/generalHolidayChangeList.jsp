
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var generalHolidayChangeLayout;
	var generalHolidayChangeGridIdString="#generalHolidayChange_gridtable";
	
	jQuery(document).ready(function() { 
		var generalHolidayChangeGrid = jQuery(generalHolidayChangeGridIdString);
    	generalHolidayChangeGrid.jqGrid({
    		url : "generalHolidayChangeGridList",
    		editurl:"generalHolidayChangeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'changeId',index:'changeId',align:'center',label : '<s:text name="generalHolidayChange.changeId" />',hidden:true,key:true},
{name:'oldDate',index:'oldDate',align:'left',width:'150px',label : '<s:text name="generalHolidayChange.oldDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
{name:'newDate',index:'newDate',align:'left',width:'150px',label : '<s:text name="generalHolidayChange.newDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
{name:'changeDesc',index:'changeDesc',align:'left',width:'300px',label : '<s:text name="generalHolidayChange.changeDesc" />',hidden:false}
			],jsonReader : {
				root : "generalHolidayChanges", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'changeId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="generalHolidayChangeList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
		 		gridContainerResize('generalHolidayChange','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"generalHolidayChange_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("generalHolidayChange_gridtable");
       		} 

    	});
    jQuery(generalHolidayChangeGrid).jqGrid('bindKeys');
    /*添加*/
	jQuery("#generalHolidayChange_gridtable_add_custom").unbind("click").bind("click",function() {
		var urlString = "editGeneralHolidayChange?popup=true$navTabId=generalHolidayChange_gridtable";
		urlString = encodeURI(urlString);
		var winTitle = '<s:text name="generalHolidayChangeNew.title"/>';
		$.pdialog.open(urlString,"addGeneralHolidayChange",winTitle,{mask:true,width:400,height:225,maxable:false,resizable:false});
	});
	/*编辑*/
	jQuery("#generalHolidayChange_gridtable_edit_custom").unbind("click").bind("click",function() {
		var sid = jQuery(generalHolidayChangeGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length == 0) {
			alertMsg.error("请选择一条记录！");
			return;
		}
		if(sid.length > 1) {
			alertMsg.error("只能选择一条记录！");
			return;
		}
		
		var urlString = "editGeneralHolidayChange?popup=true&navTabId=generalHolidayChange_gridtable&oper=update&changeId="+sid[0];
		urlString = encodeURI(urlString);
		var winTitle = '<s:text name="generalHolidayChangeEdit.title"/>';
		$.pdialog.open(urlString,"editGeneralHolidayChange",winTitle,{mask:true,width:400,height:225,maxable:false,resizable:false});
	});
  });
	function reloadGeneralHolidayChangeGrid() {
		jQuery("#generalHolidayChange_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div id="generalHolidayChange_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="generalHolidayChange_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='generalHolidayChange.oldDate'/>:
						<input type="text" id="generalHolidayChange_oldDate_search_from" name="filter_GED_oldDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'generalHolidayChange_oldDate_search_to\')}'})"/>
						<s:text name='至'/>
						<input type="text" id="generalHolidayChange_oldDate_search_to" name="filter_LED_oldDate" class="Wdate" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'generalHolidayChange_oldDate_search_from\')}'})"/>
					</label>
					<label class="queryarea-label">
						<s:text name='generalHolidayChange.newDate'/>:
						<input type="text" id="generalHolidayChange_newDate_search_from" name="filter_GED_newDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'generalHolidayChange_newDate_search_to\')}'})"/>
						<s:text name='至'/>
						<input type="text" id="generalHolidayChange_newDate_search_to" name="filter_LED_newDate" class="Wdate" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'generalHolidayChange_newDate_search_from\')}'})"/>
					</label>
					<label class="queryarea-label">
						<s:text name='generalHolidayChange.changeDesc'/>:
						<input type="text" name="filter_LIKES_changeDesc"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('generalHolidayChange_search_form','generalHolidayChange_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="generalHolidayChange_buttonBar">
			<ul class="toolBar">
				<li><a id="generalHolidayChange_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="generalHolidayChange_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="generalHolidayChange_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="generalHolidayChange_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="generalHolidayChange_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="generalHolidayChange_gridtable_addTile">
				<s:text name="generalHolidayChangeNew.title"/>
			</label> 
			<label style="display: none"
				id="generalHolidayChange_gridtable_editTile">
				<s:text name="generalHolidayChangeEdit.title"/>
			</label>
			<div id="load_generalHolidayChange_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="generalHolidayChange_gridtable"></table>
			<!--<div id="generalHolidayChangePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="generalHolidayChange_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="generalHolidayChange_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="generalHolidayChange_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="generalHolidayChange_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>