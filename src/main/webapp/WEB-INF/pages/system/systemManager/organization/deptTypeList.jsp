<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var deptTypeLayout;
var deptTypeGridIdString="#deptType_gridtable";
jQuery(document).ready(function(){
	var deptTypeGrid = jQuery(deptTypeGridIdString);
	deptTypeGrid.jqGrid({
		url : "deptTypeGridList",
		editurl:"deptTypeGridEdit",
		datatype : "json",
		mtype : "GET",
    	colModel:[
{name:'deptTypeId',index:'deptTypeId',align:'left',width:'120px',label : '<s:text name="deptType.deptTypeId" />',hidden:false,key:true,highsearch:true},
{name:'deptTypeName',index:'deptTypeName',align:'left',width:'150px',label : '<s:text name="deptType.deptTypeName" />',hidden:false,highsearch:true},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="deptType.disabled" />',hidden:false,formatter:"checkbox",highsearch:true}
],
    	jsonReader : {
			root : "deptTypes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
			// (4)
		},
    	sortname: 'deptTypeId',
    	viewrecords: true,
    	sortorder: 'asc',
    	//caption:'<s:text name="gzTypeList.title" />',
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
	 		var rowNum = jQuery(this).getDataIDs().length;
            if(rowNum<=0){
            	var tw = jQuery(this).outerWidth();
				jQuery(this).parent().width(tw);
				jQuery(this).parent().height(1);
            }
       	var dataTest = {"id":"deptType_gridtable"};
  	   	jQuery.publish("onLoadSelect",dataTest,null);
   	} 
	});
jQuery(deptTypeGrid).jqGrid('bindKeys');

}); 
	function addRecord() {
		var url = "editDeptType?popup=true";
		var winTitle = '<fmt:message key="deptTypeNew.title"/>';
		popUpWindow(url, winTitle, "width=500");
	}
	function editRecord() {
		var sid = jQuery("#deptType_gridtable").jqGrid('getGridParam',
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
			var url = "editDeptType?popup=true&deptTypeId=" + sid;
			var winTitle = '<fmt:message key="deptTypeNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
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

	function hideLoading() {
		alert("before");
		jQuery("#deptType_gridtable").setGridParam({
			loadui : "disable"
		});
		alert("after");
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar" id="deptType_buttonBar">
			<ul class="toolBar" id="deptType_toolbuttonbar">
				<li><a id="deptType_gridtable_add" class="addbutton"
					href="javaScript:"><span><fmt:message key="button.add" />
					</span> </a></li>
				<li><a id="deptType_gridtable_del" class="delbutton"
					href="javaScript:"><span>删除</span></a></li>
				<li><a id="deptType_gridtable_edit" class="changebutton"
					href="javaScript:"><span><fmt:message key="button.edit" />
					</span> </a></li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="deptType_gridtable_div" layoutH="57" class="grid-wrapdiv"
			buttonBar="width:500;height:300">
			<input type="hidden" id="deptType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptType_gridtable_addTile">
				<fmt:message key="deptTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="deptType_gridtable_editTile">
				<fmt:message key="deptTypeNew.title"/>
			</label>
			<label style="display: none"
				id="deptType_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptType_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_deptType_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="deptType_gridtable"></table>
			<!--<div id="gzTypePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="deptType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
