
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var invBatchGridIdString="#invBatch_gridtable";
	
	jQuery(document).ready(function() { 
var invBatchGrid = jQuery(invBatchGridIdString);
    invBatchGrid.jqGrid({
    	url : "invBatchGridList",
    	editurl:"invBatchGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="invBatch.id" />',hidden:true,key:true},				
{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true},				
{name:'batchNo',index:'batchNo',align:'left',label : '<s:text name="invBatch.batchNo" />',hidden:false},				
{name:'invDict.invName',index:'invDict.invName',align:'left',label : '<s:text name="inventoryDict.invName" />',hidden:false},				
{name:'invDict.invCode',index:'invDict.invCode',align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:false},				
{name:'invDict.vendor.vendorName',index:'invDict.vendor.vendorName',align:'left',label : '<s:text name="供应商" />',hidden:false,sortable:false},				
{name:'store.storeName',index:'store.storeName',align:'left',label : '<s:text name="仓库" />',hidden:false},				
/* {name:'invDict.isBatch',index:'invDict.isBatch',align:'center',label : '<s:text name="inventoryDict.isBatch" />',hidden:false,
	formatter : 'checkbox',
	editable : true,
	edittype : "checkbox",
	editoptions : {
		value : "true:false"
	}
	}, */				
/* {name:'batchSerial',index:'batchSerial',align:'left',label : '<s:text name="invBatch.batchSerial" />',hidden:false,formatter:'integer'},				
 */{name:'productionDate',index:'productionDate',align:'center',label : '<s:text name="invBatch.productionDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},				
{name:'validityDate',index:'validityDate',align:'center',label : '<s:text name="invBatch.validityDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}}				

        ],
        jsonReader : {
			root : "invBatches", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'invDict.invCode,productionDate',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="invBatchList.title" />',
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
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"invBatch_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("invBatch_gridtable");
       	} 

    });
    jQuery(invBatchGrid).jqGrid('bindKeys');
    jQuery("#invBatch_search_form_vendor").combogrid(
			{
				url : '${ctx}/comboGridSqlList',
				queryParams : {
					sql : "SELECT v.vendorId vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where disabled = 0 and isMate = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
					cloumns : 'vendorName,vendorCode,cncode'
				},
				rows:10,
				sidx:"VID",
				showOn:false,
				colModel : [ {
					'columnName' : 'VID',
					'width' : '10',
					'label' : 'VID',
					hidden : true,
				}, {
					'columnName' : 'VCODE',
					'width' : '30',
					'align' : 'left',
					'label' : '供应商编码'
				},{
					'columnName' : 'VNAME',
					'width' : '60',
					'align' : 'left',
					'label' : '供应商名称'
				
				}

				],
				
				select : function(event, ui) {
						 $(event.target).val(ui.item.VNAME);
						$("#invBatch_search_form_vendor_id").val(ui.item.VID);
					return false; 
				}
			});
    jQuery("#invBatch_search_form_store").treeselect({
		dataType : "sql",
		optType : "single",
		sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where parent_id is not null and disabled = 0 and orgCode='"+ "${fns:userContextParam('orgCode')}" + "'",
		exceptnullparent : false,
		lazy : false
				
	});
  });
</script>

<div class="page">
<div id="invBatch_container">
			<div id="invBatch_layout-center"
				class="pane ui-layout-center">
	<div id="invBatch_pageHeader" class="pageHeader">
		<div class="searchBar">
				<div class="searchContent">
					<form id="invBatch_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='invBatch.batchNo'/>:
							<input type="text" id="invBatch_search_form_batchNo" name="filter_LIKES_batchNo"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='材料名称'/>:
							<input type="text" id="invBatch_search_form_invName" name="filter_LIKES_invDict.invName"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='供应商'/>:
							<input type="hidden" id="invBatch_search_form_vendor_id" name="filter_EQS_invDict.vendor.vendorId"/>
							<input type="text" id="invBatch_search_form_vendor" value="拼音/汉字/编码" 
							class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#invBatch_search_form_vendor_id'))" 
							onfocus="clearInput(this,jQuery('#invBatch_search_form_vendor_id'))" onkeyDown="setTextColor(this)"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='仓库'/>:
							<input type="hidden" id="invBatch_search_form_store_id" name="filter_EQS_store.id"/>
							<input type="text" id="invBatch_search_form_store"/>
						</label>&nbsp;&nbsp;
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('invBatch_search_form','invBatch_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
					
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('invBatch_search_form','invBatch_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

		<div id="invBatch_gridtable_div" layoutH="90" 
			style="margin-left: 1px; margin-top: 1px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="invBatch_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invBatch_gridtable_addTile">
				<s:text name="invBatchNew.title"/>
			</label> 
			<label style="display: none"
				id="invBatch_gridtable_editTile">
				<s:text name="invBatchEdit.title"/>
			</label>
			<label style="display: none"
				id="invBatch_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invBatch_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_invBatch_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="invBatch_gridtable"></table>
		<div id="invBatchPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invBatch_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invBatch_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invBatch_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>