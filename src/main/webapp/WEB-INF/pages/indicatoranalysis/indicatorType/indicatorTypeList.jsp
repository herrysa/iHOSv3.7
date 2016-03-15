
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var indicatorTypeGridIdString="#indicatorType_gridtable";
	
	jQuery(document).ready(function() { 
		var indicatorTypeGrid = jQuery(indicatorTypeGridIdString);
    	indicatorTypeGrid.jqGrid({
    		url : "indicatorTypeGridList",
    		editurl:"indicatorTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'code',index:'code',align:'left',width:'150',label : '<s:text name="indicatorType.code" />',hidden:false,key:true},
				{name:'name',index:'name',align:'left',width:'200',label : '<s:text name="indicatorType.name" />',hidden:false}        	
			],
        	jsonReader : {
				root : "indicatorTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'code',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="indicatorTypeList.title" />',
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
	           	var dataTest = {"id":"indicatorType_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	   	makepager("indicatorType_gridtable");
       		} 

	    	});
	    	jQuery(indicatorTypeGrid).jqGrid('bindKeys');
	  	});
</script>

<div class="page">
	<div id="indicatorType_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="indicatorType_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='indicatorType.code'/>:
						<input type="text" name="filter_LIKES_code"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='indicatorType.name'/>:
						<input type="text" name="filter_LIKES_name"/>
					</label>
				</form>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="propertyFilterSearch('indicatorType_search_form','indicatorType_gridtable')"><s:text name='button.search'/></button>
					</div>
				</div>
			</div>
			<%-- <div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('indicatorType_search_form','indicatorType_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div> --%>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="indicatorType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="indicatorType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="indicatorType_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="indicatorType_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:360;height:200">
			<input type="hidden" id="indicatorType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="indicatorType_gridtable_addTile">
				<s:text name="indicatorTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="indicatorType_gridtable_editTile">
				<s:text name="indicatorTypeEdit.title"/>
			</label>
			<div id="load_indicatorType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="indicatorType_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="indicatorType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="indicatorType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="indicatorType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>