
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetAssistTypeLayout;
	var budgetAssistTypeGridIdString="#budgetAssistType_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetAssistTypeGrid = jQuery(budgetAssistTypeGridIdString);
    	budgetAssistTypeGrid.jqGrid({
    		url : "budgetAssistTypeGridList",
    		editurl:"budgetAssistTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'colCode',index:'colCode',align:'left',label : '<s:text name="budgetAssistType.colCode" />',hidden:false,editable:true,key:true,width:150},
			{name:'colName',index:'colName',align:'left',label : '<s:text name="budgetAssistType.colName" />',hidden:false,editable:true,width:200},
			{name:'assistType.typeCode',index:'assistType.typeCode',align:'left',label : '<s:text name="budgetAssistType.assistType" />',hidden:true,editable:true,width:200},
			{name:'assistType.typeName',index:'assistType.typeName',align:'left',label : '<s:text name="budgetAssistType.assistType" />',hidden:false,editable:true,width:200}
			],
        	jsonReader : {
				root : "budgetAssistTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'colCode',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="budgetAssistTypeList.title" />',
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
           	var dataTest = {"id":"budgetAssistType_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("budgetAssistType_gridtable");
       		} 

    	});
    jQuery(budgetAssistTypeGrid).jqGrid('bindKeys');
    
    jQuery("#budgetAssistType_gridtable_add_custom").click(function(){
    	gridAddRow(jQuery('#budgetAssistType_gridtable'));
    	budgetAssistTypeGridEdit();
    });
  	});
	function budgetAssistTypeGridEdit(){
		$("input[name='assistType.typeName']:visible").autocomplete("autocompleteBySql",{
	  		width : 270,
	  		multiple : false,
	  		multipleSeparator: "", 
	  		autoFill : false,
	  		matchContains : true,
	  		matchCase : true,
	  		dataType : 'json',
	  		parse : function(json) {
	  			var data = json.result;
	  			if (data == null || data == "") {
	  				var rows = [];
	  				rows[0] = {
	  					data : "没有结果",
	  					value : "",
	  					result : ""
	  				};
	  				return rows;
	  			} else {
	  				var rows = [];
	  				for ( var i = 0; i < data.length; i++) {
	  					rows[rows.length] = {
	  						data : {
								showValue : data[i].showValue ,
								id : data[i].id,
								name : data[i].name
							},
	  						value : data[i].id,
	  						result : data[i].name
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "assistTypeCode,assistTypeName",
	  			sql:"SELECT assistTypeCode id,assistTypeName name from sy_assistType where assistTypeCode like '%q%' or assistTypeName like '%q%'",
	  		},
	  		formatItem : function(row) {
	  			if(typeof(row)=='string'){
					return row
				}else{
					return row.showValue;
				}
	  		},
	  		formatResult : function(row) {
	  			if(typeof(row)=='string'){
					return row
				}else{
					return row.showValue;
				}
	  		}
	  	});
	  	jQuery("input[name='assistType.typeName']:visible").result(function(event, row, formatted) {
	  		if (row == "没有结果") {
	  			return;
	  		}
	  		jQuery("input[name='assistType.typeCode']").attr("value", row.id); 
	  	});
  	}
</script>

<div class="page">
	<div id="budgetAssistType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetAssistType_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetAssistType.colCode'/>:
						<input type="text" name="filter_EQS_colCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetAssistType.assistType'/>:
						<input type="text" name="filter_EQS_assistType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetAssistType.colName'/>:
						<input type="text" name="filter_EQS_colName"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetAssistType_search_form,budgetAssistType_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(budgetAssistType_search_form,budgetAssistType_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div> --%>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetAssistType_gridtable_add_custom" class="addbutton" href="javaScript:" ><span>添加行</span>
				</a>
				</li>
				<li><a class="editbutton" onclick="editAssistTypeInGrid()" ><span>编辑行</span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#budgetAssistType_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#budgetAssistType_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="budgetAssistType_gridtable_del" class="delbutton"  href="javaScript:"><span>删除行</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="budgetAssistType_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="optId:colCode;width:500;height:300">
			<input type="hidden" id="budgetAssistType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetAssistType_gridtable_addTile">
				<s:text name="budgetAssistTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetAssistType_gridtable_editTile">
				<s:text name="budgetAssistTypeEdit.title"/>
			</label>
			<div id="load_budgetAssistType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetAssistType_gridtable"></table>
			<!--<div id="budgetAssistTypePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetAssistType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetAssistType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetAssistType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>