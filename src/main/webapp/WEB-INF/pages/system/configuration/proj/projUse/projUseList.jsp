
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var projUseGridIdString="#projUse_gridtable";
	
	jQuery(document).ready(function() { 
var projUseGrid = jQuery(projUseGridIdString);
    projUseGrid.jqGrid({
    	url : "projUseGridList",
    	editurl:"projUseGridEdit",
		datatype : "json",
		editinline:true,
		mtype : "GET",
        colModel:[
{name:'useCode',index:'useCode',align:'left',label : '<s:text name="projUse.useCode" />',hidden:true,key:true,editable:true,edittype:'text'},				
{name:'useName',index:'useName',align:'left',label : '<s:text name="projUse.useName" />',hidden:false,editable:true,edittype:'text'},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="projUse.disabled" />',hidden:false,formatter:'checkbox',editable:true,edittype:'checkbox'}				
        ],
        jsonReader : {
			root : "projUses", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'useCode',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="projUseList.title" />',
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
           var dataTest = {"id":"projUse_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("projUse_gridtable");
       	} 

    });
    jQuery(projUseGrid).jqGrid('bindKeys');
    
	
	
	
	//projUseLayout.resizeAll();
  	});
</script>


<div class="page">
<div id="projUse_container">
			<div id="projUse_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<form id='projUseForm'>
				<table class="searchContent">
					<tr>
						<td>
							<s:text name='projUse.useName'/>:
							<input type="text"	name="filter_LIKES_useName" />
						</td>
						 <td>
							<s:text name='projUse.disabled'/>:
							<s:select list="#{'':'全部','false':'使用','true':'停用'}" name="filter_EQB_disabled" style="width:60px"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('projUseForm','projUse_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					
					</ul>
				</div>
			</form>
			</div>
	</div>
	<div class="pageContent">
<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#projUse_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#projUse_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#projUse_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#projUse_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a class="delbutton" id="projUse_gridtable_del"  href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
			</ul>
		</div>
		<div id="projUse_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:useCode;width:500;height:300">
			<input type="hidden" id="projUse_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="projUse_gridtable_addTile">
				<s:text name="projUseNew.title"/>
			</label> 
			<label style="display: none"
				id="projUse_gridtable_editTile">
				<s:text name="projUseEdit.title"/>
			</label>
			<label style="display: none"
				id="projUse_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="projUse_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
 <table id="projUse_gridtable"></table>
		<div id="projUsePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="projUse_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="projUse_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="projUse_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>