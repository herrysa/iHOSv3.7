
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqHolidayLayout;
	var kqHolidayGridIdString="#kqHoliday_gridtable";
	
	jQuery(document).ready(function() { 
		var kqHolidayGrid = jQuery(kqHolidayGridIdString);
    	kqHolidayGrid.jqGrid({
    		url : "kqHolidayGridList",
    		editurl:"kqHolidayGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'holidayId',index:'holidayId',align:'center',label : '<s:text name="kqHoliday.holidayId" />',hidden:true,key:true},
{name:'holidayCode',index:'holidayCode',align:'left',label : '<s:text name="kqHoliday.holidayCode" />',hidden:false},
{name:'holidayName',index:'holidayName',align:'left',label : '<s:text name="kqHoliday.holidayName" />',hidden:false},
{name:'beginDate',index:'beginDate',align:'left',label : '<s:text name="kqHoliday.beginDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
{name:'endDate',index:'endDate',align:'left',label : '<s:text name="kqHoliday.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
{name:'dayNumber',index:'dayNumber',align:'center',label : '<s:text name="kqHoliday.dayNumber" />',hidden:false,formatter:'number',formatoptions:{decimalPlaces: 1}},
{name:'currentYear',index:'currentYear',align:'center',label : '<s:text name="kqHoliday.currentYear" />',hidden:true},
{name:'requirement',index:'requirement',align:'left',label : '<s:text name="kqHoliday.requirement" />',hidden:false},
{name:'holidayDesc',index:'holidayDesc',align:'left',label : '<s:text name="kqHoliday.holidayDesc" />',hidden:false}
        		],jsonReader : {
				root : "kqHolidays", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'holidayId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="kqHolidayList.title" />',
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
		 		gridContainerResize('kqHoliday','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"kqHoliday_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("kqHoliday_gridtable");
       		} 

    	});
    	jQuery(kqHolidayGrid).jqGrid('bindKeys');
    	/*添加*/
    	jQuery("#kqHoliday_gridtable_add_custom").unbind("click").bind("click",function() {
    		var urlString = "editKqHoliday?popup=true$navTabId=kqHoliday_gridtable";
    		urlString = encodeURI(urlString);
			var winTitle = '<s:text name="kqHolidayNew.title"/>';
			$.pdialog.open(urlString,"addKqHoliday",winTitle,{mask:true,width:650,height:325,maxable:false,resizable:false});
    	});
    	/*编辑*/
    	jQuery("#kqHoliday_gridtable_edit_custom").unbind("click").bind("click",function() {
    		var sid = jQuery(kqHolidayGridIdString).jqGrid("getGridParam","selarrrow");
    		if(sid == null || sid.length == 0) {
    			alertMsg.error("请选择一条记录！");
    			return;
    		}
    		if(sid.length > 1) {
    			alertMsg.error("只能选择一条记录！");
    			return;
    		}
    		
    		var urlString = "editKqHoliday?popup=true&navTabId=kqHoliday_gridtable&oper=update&holidayId="+sid[0];
    		urlString = encodeURI(urlString);
			var winTitle = '<s:text name="kqHolidayEdit.title"/>';
			$.pdialog.open(urlString,"editKqHoliday",winTitle,{mask:true,width:650,height:325,maxable:false,resizable:false});
    	});
  	});
  	function reloadKqHolidayGrid() {
		jQuery("#kqHoliday_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div id="kqHoliday_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqHoliday_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='kqHoliday.holidayCode'/>:
						<input type="text" name="filter_LIKES_holidayCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqHoliday.holidayName'/>:
						<input type="text" name="filter_LIKES_holidayName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqHoliday.date'/>:
						<input type="text" id="kqHoliday_date_search_from" name="filter_GED_beginDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'kqHoliday_date_search_to\')}'})"/>
						<s:text name='至'/>
						<input type="text" id="kqHoliday_date_search_to" name="filter_LED_endDate" class="Wdate" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'kqHoliday_date_search_from\')}'})"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqHoliday.dayNumber'/>:
						<input type="text" name="filter_EQN_dayNumber"/>
					</label>
					<!--<label style="float:none;white-space:nowrap" >
						<s:text name='kqHoliday.currentYear'/>:
						<input type="text" name="filter_EQS_currentYear"/>
					</label>-->
					<label class="queryarea-label">
						<s:text name='kqHoliday.holidayDesc'/>:
						<input type="text" name="filter_LIKES_holidayDesc"/>
					</label>
<%-- 					<label style="float:none;white-space:nowrap" >
						<s:text name='kqHoliday.requirement'/>:
						<input type="text" name="filter_EQS_requirement"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('kqHoliday_search_form','kqHoliday_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqHoliday_buttonBar">
			<ul class="toolBar">
				<li><a id="kqHoliday_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="kqHoliday_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="kqHoliday_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="kqHoliday_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqHoliday_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kqHoliday_gridtable_addTile">
				<s:text name="kqHolidayNew.title"/>
			</label> 
			<label style="display: none"
				id="kqHoliday_gridtable_editTile">
				<s:text name="kqHolidayEdit.title"/>
			</label>
			<div id="load_kqHoliday_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqHoliday_gridtable"></table>
			<!--<div id="kqHolidayPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="kqHoliday_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="kqHoliday_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqHoliday_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="kqHoliday_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>