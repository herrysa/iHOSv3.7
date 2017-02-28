
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var accountTypeLayout;
	var accountTypeGridIdString="#accountType_gridtable";
	
	jQuery(document).ready(function() {
		var accountTypeGrid = jQuery(accountTypeGridIdString);
    	accountTypeGrid.jqGrid({
    		url : "accountTypeGridList",
    		editurl:"accountTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'accttypeId',index:'accttypeId',align:'center',label : '<s:text name="accountType.accttypeId" />',hidden:true,key:true},        	
				{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="accountType.orgCode" />',hidden:false,edittype:"text",editable:true},        	
				{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="accountType.copyCode" />',hidden:false,edittype:"text",editable:true},
				{name:'accounttype',index:'accounttype',align:'center',label : '<s:text name="accountType.accounttype" />',hidden:false,edittype:"text",editable:true},
				{name:'accouttypecode',index:'accouttypecode',align:'center',label : '<s:text name="accountType.accouttypecode" />',hidden:false,edittype:"text",editable:true}
				],
        	jsonReader : {
				root : "accountTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'accouttypecode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="accountTypeList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"accountType_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("accountType_gridtable");
       		} 

    	});
    	jQuery(accountTypeGrid).jqGrid('bindKeys');
  	});	
</script>

<div class="page">
	<div id="accountType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="accountType_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='accountType.orgCopyPk'/>:
						<input type="text" name="filter_EQS_orgCopyPk"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='accountType.accounttype'/>:
						<input type="text" name="filter_EQS_accounttype"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='accountType.accouttypecode'/>:
						<input type="text" name="filter_EQS_accouttypecode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='accountType.copyCode'/>:
						<input type="text" name="filter_EQS_copyCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='accountType.orgId'/>:
						<input type="text" name="filter_EQS_orgId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('accountType_search_form','accountType_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('accountType_search_form','accountType_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#accountType_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#accountType_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#accountType_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#accountType_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="accountType_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			</ul>
		</div>
		
		<div id="accountType_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="accountType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="accountType_gridtable_addTile">
				<s:text name="accountTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="accountType_gridtable_editTile">
				<s:text name="accountTypeEdit.title"/>
			</label>
			<div id="load_accountType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="accountType_gridtable"></table>
			<!--<div id="accountTypePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="accountType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="accountType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="accountType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>