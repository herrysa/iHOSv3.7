
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmDepartmentLayout;
	var bmDepartmentGridIdString="#bmDepartment_gridtable";
	
	jQuery(document).ready(function() { 
		var bmDepartmentGrid = jQuery(bmDepartmentGridIdString);
    	bmDepartmentGrid.jqGrid({
    		url : "bmDepartmentGridList?filter_NIS_departmentId=${departmentId}",
    		editurl:"bmDepartmentGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'departmentId',index:'departmentId',align:'left',label : '<s:text name="bmDepartment.departmentId" />',hidden:true,width:150,key:true},
			{name:'deptCode',index:'deptCode',align:'left',label : '<s:text name="bmDepartment.deptCode" />',hidden:false,width:150},
			{name:'name',index:'name',align:'left',label : '<s:text name="bmDepartment.name" />',hidden:false,width:200},
			{name:'cnCode',index:'cnCode',align:'leftr',label : '<s:text name="bmDepartment.cnCode" />',hidden:false,width:100},
			{name:'internalCode',index:'internalCode',align:'left',label : '<s:text name="bmDepartment.internalCode" />',hidden:false,width:150},
			{name:'deptClass',index:'deptClass',align:'left',label : '<s:text name="bmDepartment.deptClass" />',hidden:false,width:100},
			{name:'outin',index:'outin',align:'left',label : '<s:text name="bmDepartment.outin" />',hidden:false,width:100},
			{name:'manager',index:'manager',align:'left',label : '<s:text name="bmDepartment.manager" />',hidden:false,width:150},
			{name:'phone',index:'phone',align:'left',label : '<s:text name="bmDepartment.phone" />',hidden:false,width:200},
			{name:'clevel',index:'clevel',align:'right',label : '<s:text name="bmDepartment.clevel" />',hidden:false,formatter:'integer',width:70},
			{name:'leaf',index:'leaf',align:'center',label : '<s:text name="bmDepartment.leaf" />',hidden:false,formatter:'checkbox',width:70},
			{name:'ysLeaf',index:'ysLeaf',align:'center',label : '<s:text name="bmDepartment.ysLeaf" />',hidden:false,formatter:'checkbox',width:70},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="bmDepartment.disabled" />',hidden:false,formatter:'checkbox',width:70},
			{name:'note',index:'note',align:'left',label : '<s:text name="bmDepartment.note" />',hidden:false,width:200},
			],
        	jsonReader : {
				root : "bmDepartments", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'deptCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bmDepartmentList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"bmDepartment_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	
    	jQuery("#bmDepartment_gridtable_add_c").click(function(){
    		var sid = jQuery("#bmDepartment_gridtable").jqGrid('getGridParam','selarrrow');
    		if(sid==null|| sid.length == 0){       	
    			alertMsg.error("请选择预算责任中心。");
    			return;
    		}
    		$.post("saveBmDepartment", {
    			"_" : $.now(),deptId:sid,modelId:"${modelId}",navTabId:'${navTabId}'
    		}, function(data) {
    			formCallBack(data);
    			
    		});
    	});
  	});
</script>

<div class="page">
	<div id="bmDepartment_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmDepartment_search_form" >
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.deptClass'/>:
						<input type="text" name="filter_EQS_deptClass"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.deptCode'/>:
						<input type="text" name="filter_LIKES_deptCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.name'/>:
						<input type="text" name="filter_LIKES_name"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.outin'/>:
						<input type="text" name="filter_EQS_outin"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.leaf'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_leaf"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.ysLeaf'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_ysLeaf"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmDepartment.note'/>:
						<input type="text" name="filter_EQS_note"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bmDepartment_search_form','bmDepartment_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
					
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bmDepartment_gridtable_add_c" class="addbutton" href="javaScript:" ><span>确定
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="bmDepartment_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmDepartment_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmDepartment_gridtable_addTile">
				<s:text name="bmDepartmentNew.title"/>
			</label> 
			<label style="display: none"
				id="bmDepartment_gridtable_editTile">
				<s:text name="bmDepartmentEdit.title"/>
			</label>
			<div id="load_bmDepartment_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmDepartment_gridtable"></table>
			<!--<div id="bmDepartmentPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmDepartment_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmDepartment_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmDepartment_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>