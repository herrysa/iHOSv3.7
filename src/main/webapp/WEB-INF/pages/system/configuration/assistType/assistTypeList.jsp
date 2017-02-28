
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var assistTypeLayout;
	var assistTypeGridIdString="#assistType_gridtable";
	
	jQuery(document).ready(function() { 
		var assistTypeGrid = jQuery(assistTypeGridIdString);
    	assistTypeGrid.jqGrid({
    		url : "assistTypeGridList",
    		editurl:"assistTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[

{name:'typeCode',index:'typeCode',align:'left',label : '<s:text name="assistType.typeCode" />',hidden:false,key:true,editable:true},
{name:'typeName',index:'typeName',align:'left',label : '<s:text name="assistType.typeName" />',hidden:false,edittype:"text",editable:true},
{name:'bdInfo.bdInfoName',index:'bdInfo.bdInfoName',align:'left',label : '<s:text name="assistType.bdInfo.bdInfoName" />',hidden:false,editable:true},
{name:'bdInfo.bdInfoId',index:'bdInfo.bdInfoId',align:'left',label : '<s:text name="assistType.bdInfo.bdInfoId" />',hidden:true,editable:true},
{name:'filter',index:'filter',align:'left',label : '<s:text name="assistType.filter" />',hidden:false,edittype:"text",editable:true},
{name:'memo',index:'memo',align:'left',label : '<s:text name="assistType.memo" />',hidden:false,edittype:"text",editable:true},
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="assistType.disabled" />',hidden:false,edittype:'checkbox',editable:true,formatter:'checkbox',width:30}        	],

        	jsonReader : {
				root : "assistTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'typeCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="assistTypeList.title" />',
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
           	var dataTest = {"id":"assistType_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("assistType_gridtable");
       		} 

    	});
    jQuery(assistTypeGrid).jqGrid('bindKeys');
  	});
	
	function editAssistTypeInGrid(){
		gridEditRow(jQuery('#assistType_gridtable'));
		assistTypeBdInfoGridEdit();
  	}
  	function addAssistTypeInGrid(){
  		gridAddRow(jQuery('#assistType_gridtable'));
  		assistTypeBdInfoGridEdit();
  	}
	
	function assistTypeBdInfoGridEdit(){
		$("input[name='bdInfo.bdInfoName']:visible").autocomplete("autocompleteBySql",{
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
	  						data : data[i].bdinfoId+','+data[i].bdinfoName,
	  						value : data[i].bdinfoId,
	  						result : data[i].bdinfoName
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "bdinfoId,bdinfoName",
	  			sql:"SELECT ltrim(bdinfoId) as bdinfoId, bdinfoName from t_bdinfo where 1=1 ",
	  		},
	  		formatItem : function(row) {
	  			return row;
	  		},
	  		formatResult : function(row) {
	  			return row;
	  		}
	  	});
	  	jQuery("input[name='bdInfo.bdInfoName']:visible").result(function(event, row, formatted) {
	  		if (row == "没有结果") {
	  			return;
	  		}
	  		jQuery("input[name='bdInfo.bdInfoId']").attr("value", (row.split(','))[0]); 
	  	});
  	}
</script>

<div class="page">
	<div id="assistType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				 <form id="assistType_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='assistType.typeName'/>:
						<input type="text" name="filter_LIKES_typeName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='assistType.typeCode'/>:
						<input type="text" name="filter_EQS_typeCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='assistType.bdInfo.bdInfoName'/>:
						<input type="text" name="filter_LIKES_bdInfo.bdInfoName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='assistType.disabled'/>:
						<s:select name="filter_EQB_disabled"  maxlength="20" list="#{'':'全部','false':'使用','true':'停用'}" theme="simple"></s:select>
					</label>
				</form> 
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('assistType_search_form','assistType_gridtable')"><s:text name='button.search'/></button>
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
			<!-- form edit
				<li><a id="assistType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a></li>
				<li><a id="assistType_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a></li>
				 <li><a id="assistType_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li> 
			 -->
			<!-- grid edit -->
			
				 <li><a class="addbutton" onclick="addAssistTypeInGrid()" ><span><fmt:message key="button.addRow" /></span></a></li>
				
				 
				<li><a class="editbutton" onclick="editAssistTypeInGrid()" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#assistType_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#assistType_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
					
				<li><a id="assistType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
			</ul>
		</div>
		<div id="assistType_gridtable_div" layoutH="120" style="margin-left: 2px; margin-top: 2px; overflow: hidden" class="grid-wrapdiv" buttonBar="optId:typeCode;width:350;height:300">
			<input type="hidden" id="assistType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="assistType_gridtable_addTile">
				<s:text name="assistTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="assistType_gridtable_editTile">
				<s:text name="assistTypeEdit.title"/>
			</label>
			<div id="load_assistType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="assistType_gridtable"></table>
			<!--<div id="assistTypePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="assistType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="assistType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="assistType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>