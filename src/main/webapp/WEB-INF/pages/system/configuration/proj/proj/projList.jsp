
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	jQuery(document).ready(function() { 
		var projAccountGridIdString="#proj_gridtable";
		var projAccountGrid = jQuery(projAccountGridIdString);
	    projAccountGrid.jqGrid({
	    	url : "projGridList",
	    	editurl:"projGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'projId',index:'projId',align:'center',label : '<s:text name="proj.projId" />',hidden:false,key:true},				
				{name:'projCode',index:'projCode',align:'center',label : '<s:text name="proj.projCode" />',hidden:false},				
				{name:'projName',index:'projName',align:'center',label : '<s:text name="proj.projName" />',hidden:false},				
				{name:'projNature.natureName',index:'projNature.natureName',align:'center',label : '<s:text name="projNature.natureName" />',hidden:false},				
				{name:'projUse.useName',index:'projUse.useName',align:'center',label : '<s:text name="projUse.useName" />',hidden:false},				
				{name:'projType.typeName',index:'projType.typeName',align:'center',label : '<s:text name="projType.typeName" />',hidden:false},				
				{name:'principal',index:'principal',align:'center',label : '<s:text name="proj.principal" />',hidden:false},				
				{name:'beginDate',index:'beginDate',align:'center',label : '<s:text name="proj.beginDate" />',hidden:false,formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},				
				{name:'endDate',index:'endDate',align:'center',label : '<s:text name="proj.endDate" />',hidden:false,formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},				
				{name:'cnCode',index:'cnCode',align:'center',label : '<s:text name="proj.cnCode" />',hidden:false},				
				{name:'disabled',index:'disabled',align:'center',label : '<s:text name="proj.disabled" />',hidden:false,formatter:"checkbox"},				
				{name:'leaf',index:'leaf',align:'center',label : '<s:text name="proj.leaf" />',hidden:false,formatter:"checkbox"},				
				{name:'memo',index:'memo',align:'center',label : '<s:text name="proj.memo" />',hidden:false,formatter:"textarea"}			
	        ],
	        jsonReader : {
				root : "projs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'projId',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="projAccountList.title" />',
	        height:400,
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
	           if(jQuery(this).getDataIDs().length>0){
	              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"proj_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	       	} 
	    });
    
	
	
	
	//projAccountLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="proj_container">
			<div id="proj_layout-center"
				class="pane ui-layout-center">
				<div class="pageHeader">
			<div class="searchBar">
			<form id='projSearchForm'>
				<table class="searchContent">
					<tr>
						<td><s:text name='proj.projName'/>:
							<input type="text"	name="filter_LIKES_projName" >
						</td>
						<td><s:text name='proj.projCode'/>:
							<input type="text"	name="filter_LIKES_projCode" >
						</td>
						 <td>
						<s:text name='proj.disabled'/>:
						<s:select list="#{'':'全部','false':'使用','true':'停用'}" name="filter_EQB_disabled" style="width:60px"></s:select>
						 </td>
						  <td>
						<s:text name='proj.leaf' />:
						<s:select list="#{'':'全部','true':'是','false':'否'}" name="filter_EQB_leaf" style="width:60px"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('projSearchForm','proj_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</form>
			</div>
	</div>
	<div class="pageContent">
<div class="panelBar">
			<ul class="toolBar">
				<li><a id="proj_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="proj_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="proj_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="proj_gridtable_div" layoutH="153" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"; height:300px;
			buttonBar="optId:projId;width:600;height:350">
			<input type="hidden" id="proj_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="proj_gridtable_addTile">
				<s:text name="projNew.title"/>
			</label> 
			<label style="display: none"
				id="proj_gridtable_editTile">
				<s:text name="projEdit.title"/>
			</label>
			<label style="display: none"
				id="proj_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="proj_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
 <table id="proj_gridtable"></table>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="proj_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="proj_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="proj_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>