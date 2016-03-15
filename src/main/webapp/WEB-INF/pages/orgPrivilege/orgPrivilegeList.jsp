
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var orgPrivilegeLayout;
	var orgPrivilegeGridIdString="#orgPrivilege_gridtable";
	
	jQuery(document).ready(function() { 
		var orgPrivilegeGrid = jQuery(orgPrivilegeGridIdString);
    	orgPrivilegeGrid.jqGrid({
    		url : "orgPrivilegeGridList",
    		editurl:"orgPrivilegeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'privilegeId',index:'privilegeId',align:'center',label : '<s:text name="orgPrivilege.privilegeId" />',hidden:true,key:true},
			{name:'personId.name',index:'personId.name',align:'left',label : '<s:text name="orgPrivilege.person" />',hidden:false,width:100},
			{name:'personId.orgCode',index:'personId.orgCode',align:'left',label : '<s:text name="orgPrivilege.org" />',hidden:false,width:150},
			{name:'personId.department.name',index:'personId.department.name',align:'left',label : '<s:text name="orgPrivilege.dept" />',hidden:false,width:150},
			{name:'orgIds',index:'orgIds',align:'center',label : '<s:text name="orgPrivilege.orgIds" />',hidden:true},
			{name:'orgNames',index:'orgNames',align:'left',label : '<s:text name="orgPrivilege.orgNames" />',hidden:false,width:300},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="orgPrivilege.remark" />',hidden:false,width:200}  
			],
        	jsonReader : {
				root : "orgPrivileges", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'privilegeId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="orgPrivilegeList.title" />',
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
		 		gridContainerResize('orgPrivilege','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"orgPrivilege_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("orgPrivilege_gridtable");
       		} 

    	});
    jQuery(orgPrivilegeGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page" id="orgPrivilege">
	<div id="orgPrivilege_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="orgPrivilege_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='orgPrivilege.privilegeId'/>:
						<input type="text" name="filter_EQS_privilegeId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='orgPrivilege.orgIds'/>:
						<input type="text" name="filter_EQS_orgIds"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='orgPrivilege.orgNames'/>:
						<input type="text" name="filter_EQS_orgNames"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='orgPrivilege.personId'/>:
						<input type="text" name="filter_EQS_personId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='orgPrivilege.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(orgPrivilege_search_form,orgPrivilege_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch(orgPrivilege_search_form,orgPrivilege_gridtable)"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="orgPrivilege_buttonBar">
   <ul class="toolBar" id="orgPrivilege_toolbuttonbar">
				<li><a id="orgPrivilege_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="orgPrivilege_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="orgPrivilege_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="orgPrivilege_gridtable_div" class="grid-wrapdiv" buttonBar="optId:privilegeId;width:500;height:300">
			<input type="hidden" id="orgPrivilege_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="orgPrivilege_gridtable_addTile">
				<s:text name="orgPrivilegeNew.title"/>
			</label> 
			<label style="display: none"
				id="orgPrivilege_gridtable_editTile">
				<s:text name="orgPrivilegeEdit.title"/>
			</label>
			<div id="load_orgPrivilege_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="orgPrivilege_gridtable"></table>
			<!--<div id="orgPrivilegePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="orgPrivilege_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="orgPrivilege_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="orgPrivilege_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="orgPrivilege_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>