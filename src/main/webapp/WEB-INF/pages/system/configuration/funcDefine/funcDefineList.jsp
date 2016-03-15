
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var funcDefineLayout;
	var funcDefineGridIdString = "#funcDefine_gridtable";

	jQuery(document).ready(function() {
		var funcDefineGrid = jQuery(funcDefineGridIdString);
		funcDefineGrid.jqGrid({
			url : "funcDefineGridList",
			editurl : "funcDefineGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [{
				name : 'funcId',
				index : 'funcId',
				align : 'left',
				width : '90px',
				key : true,
				label : '<s:text name="funcDefine.funcId" />',
				hidden : true
			}, {
				name : 'funcCode',
				index : 'funcCode',
				align : 'left',
				width : '90px',
				label : '<s:text name="funcDefine.funcCode" />',
				hidden : false
			}, {
				name : 'funcType',
				index : 'funcType',
				align : 'left',
				width : '90px',
				label : '<s:text name="funcDefine.funcType" />',
				hidden : false,
				formatter : 'select',
				editoptions : {value : "0:表内函数;1:表间函数"}
			}, {
				name : 'funcParam',
				index : 'funcParam',
				align : 'left',
				width : '120px',
				label : '<s:text name="funcDefine.funcParam" />',
				hidden : false
			}, {
				name : 'funcBody',
				index : 'funcBody',
				align : 'left',
				width : '150px',
				label : '<s:text name="funcDefine.funcBody" />',
				hidden : false
			}, {
				name : 'funcDesc',
				index : 'funcDesc',
				align : 'left',
				width : '200px',
				label : '<s:text name="funcDefine.funcDesc" />',
				hidden : false
			}, {
				name : 'remark',
				index : 'remark',
				align : 'left',
				width : '110px',
				label : '<s:text name="funcDefine.remark" />',
				hidden : false
			}, {
				name : 'isSystemFunc',
				index : 'isSystemFunc',
				align : 'left',
				width : '50px',
				label : '<s:text name="funcDefine.isSystemFunc" />',
				hidden : true
			}],
			jsonReader : {
				root : "funcDefines", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			sortname : 'funcId',
			viewrecords : true,
			sortorder : 'desc',
			//caption:'<s:text name="funcDefineList.title" />',
			height : 300,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			onSelectRow : function(rowid) {
				//var sid = jQuery(funcDefineGridIdString).jqGrid("getGridParam","selarrrow");
				//console.info(sid);
			},
			gridComplete : function() {
				gridContainerResize('funcDefine','div');
				//if(jQuery(this).getDataIDs().length>0){
				//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
				// }
				var dataTest = {
					"id" : "funcDefine_gridtable"
				};
				jQuery.publish("onLoadSelect", dataTest, null);
				//makepager("funcDefine_gridtable");
				//initFlag = initColumn('funcDefine_gridtable',' com.huge.ihos.system.configuration.funcDefine.model.FuncDefine',initFlag);
			}

		});
		jQuery(funcDefineGrid).jqGrid('bindKeys');
		jQuery("#funcDefine_gridtable_add_custom").unbind("click").bind("click",function() {
			var url = "editFuncDefine?popup=true&navTabId=funcDefine_gridtable"
			var addTitle=jQuery("#funcDefine_gridtable_addTile").text();
			url = encodeURI(url);
			$.pdialog.open(url,"editFuncDefine",addTitle, {mask:true,resizable:false,maxable:false,width : 435,height : 405});
		});
		jQuery("#funcDefine_gridtable_edit_custom").unbind("click").bind("click",function() {
			var sid =jQuery(funcDefineGridIdString).jqGrid("getGridParam","selarrrow");
			//console.info(sid);
			if(sid == null || sid == "" || sid.length != 1) {
				alertMsg.error("请选择一条记录。");
	 			return;
			}
			var url = "editFuncDefine?funcId=" + sid + "&popup=true&navTabId=funcDefine_gridtable";
			var editTitle=jQuery("#funcDefine_gridtable_editTile").text();
			url = encodeURI(url);
			$.pdialog.open(url,"editFuncDefine",editTitle, {mask:true,resizable:false,maxable:false,width : 435,height : 405});
		});
	});
</script>

<div class="page">
	<div id="funcDefine_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="funcDefine_search_form" class="queryarea-form">
				<!-- <label style="float: none; white-space: nowrap"> <s:text
							name='funcDefine.funcId' />: <input type="text"
						name="filter_EQS_funcId" /> -->
					<label class="queryarea-label"> <s:text
							name='funcDefine.funcCode' />: <input type="text"
						name="filter_LIKES_funcCode" />
					</label> <label class="queryarea-label" > <s:text
							name='funcDefine.funcDesc' />: <input type="text"
						name="filter_LIKES_funcDesc" />
					</label> <label class="queryarea-label" > <s:text
							name='funcDefine.funcType' />: <!-- <input type="text"
						name="filter_EQS_funcType" /> -->
						<s:select name="filter_EQI_funcType" list="#{'':'','0':'表内函数','1':'表间函数'}" style="font-size:12px" 
						listKey="key" listValue="value" emptyOption="false"  maxlength="50" width="50px">
						</s:select>
					</label>
				<div class="buttonActive" style="float: right">
					<div class="buttonContent">
						<button type="button"
							onclick="propertyFilterSearch(funcDefine_search_form,funcDefine_gridtable)">
							<s:text name='button.search' />
						</button>
					</div>
				</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="funcDefine_buttonBar">
			<ul class="toolBar">
				<li><a id="funcDefine_gridtable_add_custom" class="addbutton"
					href="javaScript:"><span><fmt:message key="button.add" />
					</span> </a></li>
				<li><a id="funcDefine_gridtable_del" class="delbutton"
					href="javaScript:"><span><s:text name="button.delete" /></span>
				</a></li>
				<li><a id="funcDefine_gridtable_edit_custom" class="changebutton"
					href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>

			</ul>
		</div>
		<div id="funcDefine_gridtable_div" class="grid-wrapdiv"
			buttonBar="width:500;height:300;">
			<input type="hidden" id="funcDefine_gridtable_navTabId"
				value="${sessionScope.navTabId}"> <label
				style="display: none" id="funcDefine_gridtable_addTile"> <s:text
					name="funcDefineNew.title" />
			</label> <label style="display: none" id="funcDefine_gridtable_editTile">
				<s:text name="funcDefineEdit.title" />
			</label>
			<div id="load_funcDefine_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="funcDefine_gridtable"></table>
			<!--<div id="funcDefinePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="funcDefine_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select
				id="funcDefine_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text
					name="pager.total" /><label id="funcDefine_gridtable_totals"></label>
			<s:text name="pager.items" /></span>
		</div>
		<div id="funcDefine_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>