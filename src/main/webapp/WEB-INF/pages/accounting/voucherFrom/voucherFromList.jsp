
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var voucherFromLayout;
	var voucherFromGridIdString="#voucherFrom_gridtable";
	
	jQuery(document).ready(function() { 
		var voucherFromGrid = jQuery(voucherFromGridIdString);
    	voucherFromGrid.jqGrid({
    		url : "voucherFromGridList",
    		editurl:"voucherFromGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'voucherFromCode',index:'voucherFromCode',align:'center',label : '<s:text name="voucherFrom.voucherFromCode" />',hidden:false,key:true},{name:'voucherFromName',index:'voucherFromName',align:'center',label : '<s:text name="voucherFrom.voucherFromName" />',hidden:false},{name:'voucherFromShort',index:'voucherFromShort',align:'center',label : '<s:text name="voucherFrom.voucherFromShort" />',hidden:false}        	],
        	jsonReader : {
				root : "voucherFroms", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'voucherFromCode',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="voucherFromList.title" />',
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
           	var dataTest = {"id":"voucherFrom_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("voucherFrom_gridtable");
       		} 

    	});
    jQuery(voucherFromGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="voucherFrom_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="voucherFrom_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherFrom.voucherFromCode'/>:
						<input type="text" name="filter_EQS_voucherFromCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherFrom.voucherFromName'/>:
						<input type="text" name="filter_EQS_voucherFromName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherFrom.voucherFromShort'/>:
						<input type="text" name="filter_EQS_voucherFromShort"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(voucherFrom_search_form,voucherFrom_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(voucherFrom_search_form,voucherFrom_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="voucherFrom_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="voucherFrom_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="voucherFrom_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="voucherFrom_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="voucherFrom_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="voucherFrom_gridtable_addTile">
				<s:text name="voucherFromNew.title"/>
			</label> 
			<label style="display: none"
				id="voucherFrom_gridtable_editTile">
				<s:text name="voucherFromEdit.title"/>
			</label>
			<div id="load_voucherFrom_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="voucherFrom_gridtable"></table>
			<!--<div id="voucherFromPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="voucherFrom_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="voucherFrom_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="voucherFrom_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>