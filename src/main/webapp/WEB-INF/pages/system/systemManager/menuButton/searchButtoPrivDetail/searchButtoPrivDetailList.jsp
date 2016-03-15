
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var searchButtoPrivDetailLayout;
	var searchButtoPrivDetailGridIdString="#searchButtoPrivDetail_gridtable";
	
	jQuery(document).ready(function() { 
		var searchButtoPrivDetailGrid = jQuery(searchButtoPrivDetailGridIdString);
    	searchButtoPrivDetailGrid.jqGrid({
    		url : "searchButtoPrivDetailGridList",
    		editurl:"searchButtoPrivDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'sbpDetailId',index:'sbpDetailId',align:'center',label : '<s:text name="searchButtoPrivDetail.sbpDetailId" />',hidden:false,key:true},{name:'order',index:'order',align:'center',label : '<s:text name="searchButtoPrivDetail.order" />',hidden:false,formatter:'integer'},{name:'right',index:'right',align:'center',label : '<s:text name="searchButtoPrivDetail.right" />',hidden:false,formatter:'checkbox'},{name:'searchURL',index:'searchURL',align:'center',label : '<s:text name="searchButtoPrivDetail.searchURL" />',hidden:false},{name:'searchURLId',index:'searchURLId',align:'center',label : '<s:text name="searchButtoPrivDetail.searchURLId" />',hidden:false},{name:'searchURLTitle',index:'searchURLTitle',align:'center',label : '<s:text name="searchButtoPrivDetail.searchURLTitle" />',hidden:false}        	],
        	jsonReader : {
				root : "searchButtoPrivDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sbpDetailId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="searchButtoPrivDetailList.title" />',
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
           	var dataTest = {"id":"searchButtoPrivDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("searchButtoPrivDetail_gridtable");
       		} 

    	});
    jQuery(searchButtoPrivDetailGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="searchButtoPrivDetail_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="searchButtoPrivDetail_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.sbpDetailId'/>:
						<input type="text" name="filter_EQS_sbpDetailId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.order'/>:
						<input type="text" name="filter_EQS_order"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.right'/>:
						<input type="text" name="filter_EQS_right"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.searchButtonPriv'/>:
						<input type="text" name="filter_EQS_searchButtonPriv"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.searchURL'/>:
						<input type="text" name="filter_EQS_searchURL"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.searchURLId'/>:
						<input type="text" name="filter_EQS_searchURLId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='searchButtoPrivDetail.searchURLTitle'/>:
						<input type="text" name="filter_EQS_searchURLTitle"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(searchButtoPrivDetail_search_form,searchButtoPrivDetail_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(searchButtoPrivDetail_search_form,searchButtoPrivDetail_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="searchButtoPrivDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="searchButtoPrivDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="searchButtoPrivDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="searchButtoPrivDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="searchButtoPrivDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="searchButtoPrivDetail_gridtable_addTile">
				<s:text name="searchButtoPrivDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="searchButtoPrivDetail_gridtable_editTile">
				<s:text name="searchButtoPrivDetailEdit.title"/>
			</label>
			<div id="load_searchButtoPrivDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="searchButtoPrivDetail_gridtable"></table>
			<!--<div id="searchButtoPrivDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="searchButtoPrivDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="searchButtoPrivDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="searchButtoPrivDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>