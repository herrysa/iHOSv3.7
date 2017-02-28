
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var userReportDefineLayout;
	var userReportDefineGridIdString="#userReportDefine_gridtable";
	
	jQuery(document).ready(function() { 
		var userReportDefineGrid = jQuery(userReportDefineGridIdString);
    	userReportDefineGrid.jqGrid({
    		url : "userReportDefineGridList",
    		editurl:"userReportDefineGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'defineId',index:'defineId',align:'center',label : '<s:text name="userReportDefine.defineId" />',hidden:false,key:true},{name:'defineClass',index:'defineClass',align:'center',label : '<s:text name="userReportDefine.defineClass" />',hidden:false},{name:'reportXml',index:'reportXml',align:'center',label : '<s:text name="userReportDefine.reportXml" />',hidden:false},{name:'userId',index:'userId',align:'center',label : '<s:text name="userReportDefine.userId" />',hidden:false}        	],
        	jsonReader : {
				root : "userReportDefines", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'defineId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="userReportDefineList.title" />',
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
           	var dataTest = {"id":"userReportDefine_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("userReportDefine_gridtable");
       		} 

    	});
    jQuery(userReportDefineGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="userReportDefine_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="userReportDefine_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='userReportDefine.defineId'/>:
						<input type="text" name="filter_EQS_defineId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='userReportDefine.defineClass'/>:
						<input type="text" name="filter_EQS_defineClass"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='userReportDefine.reportXml'/>:
						<input type="text" name="filter_EQS_reportXml"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='userReportDefine.userId'/>:
						<input type="text" name="filter_EQS_userId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(userReportDefine_search_form,userReportDefine_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(userReportDefine_search_form,userReportDefine_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="userReportDefine_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="userReportDefine_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="userReportDefine_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="userReportDefine_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="userReportDefine_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="userReportDefine_gridtable_addTile">
				<s:text name="userReportDefineNew.title"/>
			</label> 
			<label style="display: none"
				id="userReportDefine_gridtable_editTile">
				<s:text name="userReportDefineEdit.title"/>
			</label>
			<div id="load_userReportDefine_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="userReportDefine_gridtable"></table>
			<!--<div id="userReportDefinePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="userReportDefine_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="userReportDefine_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="userReportDefine_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>