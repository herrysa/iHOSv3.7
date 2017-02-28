
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var voucherTypeGridIdString="#voucherType_gridtable";
	
	jQuery(document).ready(function() { 
		//var initFlag = 0;
		var voucherTypeGrid = jQuery(voucherTypeGridIdString);
	    voucherTypeGrid.jqGrid({
	    	url : "voucherTypeGridList",
	    	editurl:"voucherTypeGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'voucherTypeId',index:'voucherTypeId',align:'center',label : '<s:text name="voucherType.voucherTypeId" />',hidden:true,key:true,editable:true},				
				{name:'voucherTypeCode',index:'voucherTypeCode',align:'center',width:80,label : '<s:text name="voucherType.voucherTypeCode" />',hidden:false,edittype:"text",editable:true},				
				{name:'vouchertype',index:'vouchertype',align:'center',width:120,label : '<s:text name="voucherType.vouchertype" />',hidden:false,edittype:"text",editable:true},			
				{name:'isUsed',index:'isUsed',align:'center',width:50,label : '<s:text name="voucherType.isUsed" />',hidden:false,edittype:"checkbox",editable:true,formatter:'checkbox'},				
				{name:'voucherTypeShort',index:'voucherTypeShort',align:'center',label : '<s:text name="voucherType.voucherTypeShort" />',hidden:false,edittype:"text",editable:true}				
        		],
	        jsonReader : {
				root : "voucherTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'voucherTypeCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="voucherTypeList.title" />',
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
	           /* if(jQuery(this).getDataIDs().length>0){
	              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            } */
	          //  initFlag = initColumn("voucherType_gridtable","com.huge.ihos.accounting.voucherType.model.VouvherType",initFlag);
	           var dataTest = {"id":"voucherType_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("voucherType_gridtable");
	       	} 
	
	    });
    jQuery(voucherTypeGrid).jqGrid('bindKeys');
	
  	});
	
	function voucherTypeEdit(obj){
		obj.jqGrid("setColProp","voucherTypeCode",{editable:false});
		gridEditRow(obj);
	}
	function voucherTypeAdd(obj){
		obj.jqGrid("setColProp","voucherTypeCode",{editable:true});
		gridAddRow(obj);
	}
</script>

<div class="page">
	<div id="voucherType_pageHeader" class="pageHeader" >
			<div class="searchBar">
				<div class="searchContent">
					<form id="voucherType_search_form" >
						<%-- <label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.orgId'/>:
							<input type="text" name="filter_EQS_orgId"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.copyCode'/>:
							<input type="text" name="filter_EQS_copyCode"/>
						</label> --%>
						<label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.voucherTypeCode'/>:
							<input type="text" name="filter_EQS_voucherTypeCode"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.vouchertype'/>:
							<input type="text" name="filter_EQS_vouchertype"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.voucherTypeShort'/>:
							<input type="text" name="filter_EQS_voucherTypeShort"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='voucherType.isUsed'/>:
							<s:select list="#{'':'全部','true':'已使用','false':'未使用'}" name="filter_EQB_isUsed" style="width:60px"></s:select>
						</label>&nbsp;&nbsp;
					</form>
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('voucherType_search_form','voucherType_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('voucherType_search_form','voucherType_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
						<%-- <li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('voucherType_gridtable','com.huge.ihos.accounting.voucherType.model.VoucherType')"><s:text name='button.higher'/></button>
								</div>
							</div>
						</li> --%>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

	<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" onclick="voucherTypeAdd(jQuery('#voucherType_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="voucherTypeEdit(jQuery('#voucherType_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#voucherType_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#voucherType_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="voucherType_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			
			</ul>
		</div>
		<div id="voucherType_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:voucherTypeId;width:500;height:300">
			<input type="hidden" id="voucherType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="voucherType_gridtable_addTile">
				<s:text name="voucherTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="voucherType_gridtable_editTile">
				<s:text name="voucherTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="voucherType_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="voucherType_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
	<div id="load_voucherType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="voucherType_gridtable"></table>
		<div id="voucherTypePager"></div>
	</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="voucherType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="voucherType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="voucherType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>