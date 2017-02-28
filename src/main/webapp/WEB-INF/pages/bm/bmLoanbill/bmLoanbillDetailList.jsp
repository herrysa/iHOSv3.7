
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmLoanbillDetailLayout;
	var bmLoanbillDetailGridIdString="#bmLoanbillDetail_gridtable";
	
	jQuery(document).ready(function() { 
		var bmLoanbillDetailGrid = jQuery(bmLoanbillDetailGridIdString);
    	bmLoanbillDetailGrid.jqGrid({
    		url : "bmLoanbillDetailGridList",
    		editurl:"bmLoanbillDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'billdetailId',index:'billdetailId',align:'center',label : '<s:text name="bmLoanbillDetail.billdetailId" />',hidden:false,key:true},{name:'apply',index:'apply',align:'center',label : '<s:text name="bmLoanbillDetail.apply" />',hidden:false,formatter:'number'},{name:'balance',index:'balance',align:'center',label : '<s:text name="bmLoanbillDetail.balance" />',hidden:false,formatter:'number'},{name:'billId',index:'billId',align:'center',label : '<s:text name="bmLoanbillDetail.billId" />',hidden:false},{name:'billNum',index:'billNum',align:'center',label : '<s:text name="bmLoanbillDetail.billNum" />',hidden:false},{name:'bmSubjId',index:'bmSubjId',align:'center',label : '<s:text name="bmLoanbillDetail.bmSubjId" />',hidden:false},{name:'payModel',index:'payModel',align:'center',label : '<s:text name="bmLoanbillDetail.payModel" />',hidden:false},{name:'purpose',index:'purpose',align:'center',label : '<s:text name="bmLoanbillDetail.purpose" />',hidden:false},{name:'sn',index:'sn',align:'center',label : '<s:text name="bmLoanbillDetail.sn" />',hidden:false,formatter:'integer'},{name:'total',index:'total',align:'center',label : '<s:text name="bmLoanbillDetail.total" />',hidden:false,formatter:'number'},{name:'usable',index:'usable',align:'center',label : '<s:text name="bmLoanbillDetail.usable" />',hidden:false,formatter:'number'}        	],
        	jsonReader : {
				root : "bmLoanbillDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'billdetailId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bmLoanbillDetailList.title" />',
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
           	var dataTest = {"id":"bmLoanbillDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("bmLoanbillDetail_gridtable");
       		} 

    	});
    jQuery(bmLoanbillDetailGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="bmLoanbillDetail_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmLoanbillDetail_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.billdetailId'/>:
						<input type="text" name="filter_EQS_billdetailId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.apply'/>:
						<input type="text" name="filter_EQS_apply"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.balance'/>:
						<input type="text" name="filter_EQS_balance"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.billId'/>:
						<input type="text" name="filter_EQS_billId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.billNum'/>:
						<input type="text" name="filter_EQS_billNum"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.bmSubjId'/>:
						<input type="text" name="filter_EQS_bmSubjId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.payModel'/>:
						<input type="text" name="filter_EQS_payModel"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.purpose'/>:
						<input type="text" name="filter_EQS_purpose"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.sn'/>:
						<input type="text" name="filter_EQS_sn"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.total'/>:
						<input type="text" name="filter_EQS_total"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmLoanbillDetail.usable'/>:
						<input type="text" name="filter_EQS_usable"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(bmLoanbillDetail_search_form,bmLoanbillDetail_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(bmLoanbillDetail_search_form,bmLoanbillDetail_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="bmLoanbillDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="bmLoanbillDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bmLoanbillDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="bmLoanbillDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmLoanbillDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmLoanbillDetail_gridtable_addTile">
				<s:text name="bmLoanbillDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="bmLoanbillDetail_gridtable_editTile">
				<s:text name="bmLoanbillDetailEdit.title"/>
			</label>
			<div id="load_bmLoanbillDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmLoanbillDetail_gridtable"></table>
			<!--<div id="bmLoanbillDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmLoanbillDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmLoanbillDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmLoanbillDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>