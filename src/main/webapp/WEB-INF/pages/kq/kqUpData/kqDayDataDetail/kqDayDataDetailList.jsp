
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqDayDataDetailLayout;
	var kqDayDataDetailGridIdString="#kqDayDataDetail_gridtable";
	
	jQuery(document).ready(function() { 
		var kqDayDataDetailGrid = jQuery(kqDayDataDetailGridIdString);
    	kqDayDataDetailGrid.jqGrid({
    		url : "kqDayDataDetailGridList",
    		editurl:"kqDayDataDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'detailId',index:'detailId',align:'center',label : '<s:text name="kqDayDataDetail.detailId" />',hidden:false,key:true},{name:'kqColumn',index:'kqColumn',align:'center',label : '<s:text name="kqDayDataDetail.kqColumn" />',hidden:false},{name:'kqId',index:'kqId',align:'center',label : '<s:text name="kqDayDataDetail.kqId" />',hidden:false},{name:'kqItem',index:'kqItem',align:'center',label : '<s:text name="kqDayDataDetail.kqItem" />',hidden:false},{name:'kqValue',index:'kqValue',align:'center',label : '<s:text name="kqDayDataDetail.kqValue" />',hidden:false,formatter:'number'}        	],
        	jsonReader : {
				root : "kqDayDataDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'detailId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="kqDayDataDetailList.title" />',
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
           	var dataTest = {"id":"kqDayDataDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("kqDayDataDetail_gridtable");
       		} 

    	});
    jQuery(kqDayDataDetailGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="kqDayDataDetail_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqDayDataDetail_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='kqDayDataDetail.detailId'/>:
						<input type="text" name="filter_EQS_detailId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='kqDayDataDetail.kqColumn'/>:
						<input type="text" name="filter_EQS_kqColumn"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='kqDayDataDetail.kqId'/>:
						<input type="text" name="filter_EQS_kqId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='kqDayDataDetail.kqItem'/>:
						<input type="text" name="filter_EQS_kqItem"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='kqDayDataDetail.kqValue'/>:
						<input type="text" name="filter_EQS_kqValue"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(kqDayDataDetail_search_form,kqDayDataDetail_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(kqDayDataDetail_search_form,kqDayDataDetail_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="kqDayDataDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="kqDayDataDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="kqDayDataDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="kqDayDataDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqDayDataDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kqDayDataDetail_gridtable_addTile">
				<s:text name="kqDayDataDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="kqDayDataDetail_gridtable_editTile">
				<s:text name="kqDayDataDetailEdit.title"/>
			</label>
			<div id="load_kqDayDataDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqDayDataDetail_gridtable"></table>
			<!--<div id="kqDayDataDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="kqDayDataDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqDayDataDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="kqDayDataDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>