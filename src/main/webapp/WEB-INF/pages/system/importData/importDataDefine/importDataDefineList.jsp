
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var importDataDefineLayout;
	var importDataDefineGridIdString = "#importDataDefine_gridtable";

	jQuery(document).ready(function() {
		var importDataDefineGrid = jQuery(importDataDefineGridIdString);
		importDataDefineGrid.jqGrid({
			url : "importDataDefineGridList",
			editurl : "importDataDefineGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [ {
				name : 'interfaceId',
				index : 'interfaceId',
				align : 'center',
				label : '<s:text name="importDataDefine.interfaceId" />',
				hidden : true,
				key : true
			}, {
				name : 'interfaceName',
				index : 'interfaceName',
				align : 'center',
				label : '<s:text name="importDataDefine.interfaceName" />',
				hidden : false
			}, {
				name : 'remark',
				index : 'remark',
				align : 'center',
				label : '<s:text name="importDataDefine.remark" />',
				hidden : false
			} ],
			jsonReader : {
				root : "importDataDefines", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			sortname : 'interfaceId',
			viewrecords : true,
			sortorder : 'desc',
			//caption:'<s:text name="importDataDefineList.title" />',
			height : 300,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : true,
			autowidth : true,
			onSelectRow : function(rowid) {
			},
			gridComplete : function() {
				//if(jQuery(this).getDataIDs().length>0){
				//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
				// }
				var dataTest = {
					"id" : "importDataDefine_gridtable"
				};
				jQuery.publish("onLoadSelect", dataTest, null);
				makepager("importDataDefine_gridtable");
			}

		});
		jQuery(importDataDefineGrid).jqGrid('bindKeys');
		jQuery("#importDataDefine_gridtable_add_custom").unbind("click").bind("click",function() {
			var url = "editImportDataDefine?popup=true&navTabId=importDataDefine_gridtable"
			var addTitle=jQuery("#importDataDefine_gridtable_addTile").text();
			url = encodeURI(url);
			$.pdialog.open(url,"editImportDataDefine",addTitle, {mask:true,resizable:false,maxable:false,width : 750,height : 500});
		});
		jQuery("#importDataDefine_gridtable_edit_custom").unbind("click").bind("click",function() {
			var sid =jQuery(importDataDefineGridIdString).jqGrid("getGridParam","selarrrow");
			//console.info(sid);
			if(sid == null || sid == "" || sid.length != 1) {
				alertMsg.error("请选择一条记录。");
	 			return;
			}
			var url = "editImportDataDefine?funcId=" + sid + "&popup=true&navTabId=importDataDefine_gridtable";
			var editTitle=jQuery("#importDataDefine_gridtable_editTile").text();
			url = encodeURI(url);
			$.pdialog.open(url,"editImportDataDefine",editTitle, {mask:true,resizable:false,maxable:false,width : 400,height : 450});
		});
	});
</script>

<div class="page">
	<div id="importDataDefine_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="importDataDefine_search_form">
					<label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefine.interfaceName' />: <input type="text"
						name="filter_EQS_interfaceName" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefine.remark' />: <input type="text"
						name="filter_EQS_remark" />
					</label>
				</form>
				<div class="buttonActive" style="float: right">
					<div class="buttonContent">
						<button type="button"
							onclick="propertyFilterSearch(importDataDefine_search_form,importDataDefine_gridtable)">
							<s:text name='button.search' />
						</button>
					</div>
				</div>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button"
									onclick="propertyFilterSearch(importDataDefine_search_form,importDataDefine_gridtable)">
									<s:text name='button.search' />
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="importDataDefine_gridtable_add_custom" class="addbutton"
					href="javaScript:"><span><fmt:message key="button.add" />
					</span> </a></li>
				<li><a id="importDataDefine_gridtable_del" class="delbutton"
					href="javaScript:"><span><s:text name="button.delete" /></span>
				</a></li>
				<li><a id="importDataDefine_gridtable_edit_custom"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>

			</ul>
		</div>
		<div id="importDataDefine_gridtable_div" layoutH="120"
			class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="importDataDefine_gridtable_navTabId"
				value="${sessionScope.navTabId}"> <label
				style="display: none" id="importDataDefine_gridtable_addTile">
				<s:text name="importDataDefineNew.title" />
			</label> <label style="display: none"
				id="importDataDefine_gridtable_editTile"> <s:text
					name="importDataDefineEdit.title" />
			</label>
			<div id="load_importDataDefine_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="importDataDefine_gridtable"></table>
			<!--<div id="importDataDefinePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select
				id="importDataDefine_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text
					name="pager.total" /><label id="importDataDefine_gridtable_totals"></label>
			<s:text name="pager.items" /></span>
		</div>
		<div id="importDataDefine_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>