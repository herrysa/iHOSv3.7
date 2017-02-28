
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var projNatureGridIdString="#projNature_gridtable";
	
	jQuery(document).ready(function() { 
var projNatureGrid = jQuery(projNatureGridIdString);
    projNatureGrid.jqGrid({
    	url : "projNatureGridList",
    	editurl:"projNatureGridEdit",
		datatype : "json",
		editinline:true,
		mtype : "GET",
        colModel:[
{name:'natureId',index:'natureId',align:'left',label : '<s:text name="projNature.natureId" />',hidden:true,key:true,editable:true,edittype:'text'},				
{name:'natureName',index:'natureName',align:'left',label : '<s:text name="projNature.natureName" />',hidden:false,editable:true,edittype:'text'},			
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="projNature.disabled" />',hidden:false,formatter:'checkbox',editable:true,edittype:'checkbox'},				
{name:'isSys',index:'isSys',align:'center',label : '<s:text name="projNature.isSys" />',hidden:false,formatter:'checkbox',editable:true,edittype:'checkbox'},				
        ],
        jsonReader : {
			root : "projNatures", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'natureId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="projNatureList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"projNature_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("projNature_gridtable");
       	} 

    });
    jQuery(projNatureGrid).jqGrid('bindKeys');
    
	
	
	
	//projNatureLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="projNature_container">
			<div id="projNature_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<form id='projNatureForm'>
				<table class="searchContent">
					<tr>
						<td><s:text name='projNature.natureName'/>:
							<input type="text"	name="filter_LIKES_natureName" >
						</td>
						 <td>
						<s:text name='projNature.disabled'/>:
						<s:select list="#{'':'全部','false':'使用','true':'停用'}" name="filter_EQB_disabled" style="width:60px"></s:select>
						 </td>
						  <td>
						<s:text name='projNature.isSys' />:
						<s:select list="#{'':'全部','true':'是','false':'否'}" name="filter_EQB_isSys" style="width:60px"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('projNatureForm','projNature_gridtable')"><s:text name='button.search'/></button>
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
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#projNature_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#projNature_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#projNature_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#projNature_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a class="delbutton" id="projNature_gridtable_del"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
			</ul>
		</div>
		<div id="projNature_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:natureId;width:500;height:300">
			<input type="hidden" id="projNature_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="projNature_gridtable_addTile">
				<s:text name="projNatureNew.title"/>
			</label> 
			<label style="display: none"
				id="projNature_gridtable_editTile">
				<s:text name="projNatureEdit.title"/>
			</label>
			<label style="display: none"
				id="projNature_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="projNature_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
 <table id="projNature_gridtable"></table>
		<div id="projNaturePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="projNature_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="projNature_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="projNature_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>