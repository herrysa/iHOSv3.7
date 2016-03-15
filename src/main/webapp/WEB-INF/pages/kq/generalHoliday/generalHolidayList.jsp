
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var generalHolidayLayout;
	var generalHolidayGridIdString="#generalHoliday_gridtable";
	
	jQuery(document).ready(function() { 
		var generalHolidayGrid = jQuery(generalHolidayGridIdString);
    	generalHolidayGrid.jqGrid({
    		url : "generalHolidayGridList",
    		editurl:"generalHolidayGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'holidayId',index:'holidayId',align:'center',width:'100px',label : '<s:text name="generalHoliday.holidayId" />',hidden:true,key:true},
{name:'dateCode',index:'dateCode',align:'center',width:'300px',label : '<s:text name="generalHoliday.dateCode" />',hidden:false},
{name:'number',index:'number',align:'center',width:'300px',label : '<s:text name="generalHoliday.number" />',hidden:false,formatter:'number',formatoptions:{decimalPlaces: 1}}
			],jsonReader : {
				root : "generalHolidays", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'holidayId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="generalHolidayList.title" />',
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
		 		gridContainerResize('generalHoliday','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           		var dataTest = {"id":"generalHoliday_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		makepager("generalHoliday_gridtable");
       		} 

    	});
   	 	jQuery(generalHolidayGrid).jqGrid('bindKeys');
   		 //添加
   	 	jQuery("#generalHoliday_gridtable_add_custom").unbind("click").bind("click",function() {
    		var urlString = "editGeneralHoliday?popup=true$navTabId=generalHoliday_gridtable";
    		urlString = encodeURI(urlString);
    		var winTitle = '<s:text name="generalHolidayNew.title"/>';
    		$.pdialog.open(urlString,"addGeneralHoliday",winTitle,{mask:true,width:375,height:175,maxable:false,resizable:false});
    	});
   		 //修改
    	jQuery("#generalHoliday_gridtable_edit_custom").unbind("click").bind("click",function() {
    		var sid = jQuery(generalHolidayGridIdString).jqGrid("getGridParam","selarrrow");
    		if(sid == null || sid.length == 0) {
    			alertMsg.error("请选择一条记录！");
    			return;
    		}
    		if(sid.length > 1) {
    			alertMsg.error("只能选择一条记录！");
    			return;
    		}
    		
    		var urlString = "editGeneralHoliday?holidayId="+sid+"&popup=true$navTabId=generalHoliday_gridtable&oper=update";
    		urlString = encodeURI(urlString);
    		var winTitle = '<s:text name="generalHolidayEdit.title"/>';
    		$.pdialog.open(urlString,"editGeneralHoliday",winTitle,{mask:true,width:375,height:175,maxable:false,resizable:false});
    	});
  	});
	function reloadGeneralHolidayGrid() {
		jQuery("#generalHoliday_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div id="generalHoliday_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="generalHoliday_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='generalHoliday.dateCode'/>:
						<input type="text" name="filter_EQS_dateCode"/>
					</label>
					<!--<label style="float:none;white-space:nowrap" >
						<s:text name='generalHoliday.number'/>:
						<input type="text" name="filter_EQN_number"/>
					</label>-->
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('generalHoliday_search_form','generalHoliday_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="generalHoliday_buttonBar">
			<ul class="toolBar">
				<li><a id="generalHoliday_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="generalHoliday_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="generalHoliday_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="generalHoliday_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="generalHoliday_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="generalHoliday_gridtable_addTile">
				<s:text name="generalHolidayNew.title"/>
			</label> 
			<label style="display: none"
				id="generalHoliday_gridtable_editTile">
				<s:text name="generalHolidayEdit.title"/>
			</label>
			<div id="load_generalHoliday_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="generalHoliday_gridtable"></table>
			<!--<div id="generalHolidayPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="generalHoliday_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="generalHoliday_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="generalHoliday_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="generalHoliday_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>