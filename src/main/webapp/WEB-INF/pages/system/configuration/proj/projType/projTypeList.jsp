
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var projTypeGridIdString="#projType_gridtable";
	
	jQuery(document).ready(function() { 
	var projTypeGrid = jQuery(projTypeGridIdString);
    projTypeGrid.jqGrid({
    	url : "projTypeGridList",
    	editurl:"projTypeGridEdit",
		datatype : "json",
		mtype : "GET",
		editinline:true,
        colModel:[
{name:'typeId',index:'typeId',align:'left',label : '<s:text name="projType.typeId" />',hidden:true,key:true,editable:true,edittype:'text'},				
{name:'typeName',index:'typeName',align:'left',label : '<s:text name="projType.typeName" />',hidden:false,editable:true,edittype:'text'},				
{name:'projDetailKind',index:'projDetailKind',align:'left',label : '<s:text name="projType.projDetailKind" />',hidden:false,editable:true,edittype:'text'},				
{name:'cnCode',index:'cnCode',align:'left',label : '<s:text name="projType.cnCode" />',hidden:false},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="projType.disabled" />',hidden:false,formatter:'checkbox',editable:true,edittype:'checkbox'},				
{name:'isSys',index:'isSys',align:'center',label : '<s:text name="projType.isSys" />',hidden:false,formatter:'checkbox',editable:true,edittype:'checkbox'}			
        ],
        jsonReader : {
			root : "projTypes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'typeId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="projTypeList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"projType_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("projType_gridtable");
       	} 

    });
    jQuery(projTypeGrid).jqGrid('bindKeys');
    
	
	
	
	//projTypeLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="projType_container">
			<div id="projType_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<form id="projTypeForm">
				<table class="searchContent">
					<tr>
						<td><s:text name='projType.typeName'/>:
							<input type="text"	name="filter_LIKES_typeName" >
						</td>
						<td><s:text name='projType.cnCode'/>:
						 	<input type="text"	name="filter_LIKES_cnCode" >
						 </td>
						 <td>
						<s:text name='projType.disabled'/>:
						<s:select list="#{'':'全部','false':'使用','true':'停用'}" name="filter_EQB_disabled" style="width:60px"></s:select>
						 </td>
						  <td>
						<s:text name='projType.isSys' />:
						<s:select list="#{'':'全部','true':'是','false':'否'}" name="filter_EQB_isSys" style="width:60px"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('projTypeForm','projType_gridtable')"><s:text name='button.search'/></button>
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
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#projType_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#projType_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#projType_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#projType_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a class="delbutton" id="projType_gridtable_del"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
			</ul>
		</div>
		<div id="projType_gridtable_div" layoutH="153"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:typeId;width:500;height:300">
			<input type="hidden" id="projType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="projType_gridtable_addTile">
				<s:text name="projTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="projType_gridtable_editTile">
				<s:text name="projTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="projType_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="projType_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
 <table id="projType_gridtable"></table>
		<div id="projTypePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="projType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="projType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="projType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>