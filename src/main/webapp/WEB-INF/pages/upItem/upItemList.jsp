
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
	function upItemGridReload() {
		var urlString = "upItemGridList";
		var itemNameTxt = jQuery("#itemNameTxt").val();
		var upItemItemClassCX = jQuery("#upItem_ItemClass_CX").val();
		var costItemIdTxt = jQuery("#costItemIdTxt").val();
		var sbDeptName = jQuery("#upItem_sbDept").val();
		var auditDeptName = jQuery("#upItem_auditDept").val();
		urlString = urlString + "?filter_LIKES_itemName=" + itemNameTxt
				+ "&filter_EQS_itemClass=" + upItemItemClassCX
				+ "&filter_EQS_costItemId.costItemId=" + costItemIdTxt
				+ "&filter_LIKES_sbdeptId.departmentId="+sbDeptName
				+ "&filter_LIKES_auditDeptId.departmentId="+auditDeptName;
		//alert(urlString);
		urlString = encodeURI(urlString);
		jQuery("#upItem_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var upItemLayout;
	var upItemGridIdString = "#upItem_gridtable";

	jQuery(document).ready(function() {
		/* upItemLayout = makeLayout({
			baseName: 'upItem', 
			tableIds: 'upItem_gridtable'
		}, null); */
		var upItemGrid = jQuery(upItemGridIdString);
		upItemGrid.jqGrid({
			url : "upItemGridList",
			editurl : "upItemGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [ {
				name : 'id',
				index : 'id',
				align : 'center',
				label : '<s:text name="upItem.id" />',
				hidden : true,
				key : true,
				formatter : 'integer'
			}, {
				name : 'itemName',
				index : 'itemName',
				align : 'left',
				label : '<s:text name="upItem.itemName" />',
				hidden : false,
				width : 100
			}, {
				name : 'itemClass',
				index : 'itemClass',
				align : 'center',
				label : '<s:text name="upItem.itemClass" />',
				hidden : false,
				width : 70
			}, {
				name : 'costItemId.costItemId',
				index : 'costItemId',
				align : 'left',
				label : '成本项目编码',
				hidden : false,
				width : 100
			}, {
				name : 'costItemId.costItemName',
				index : 'costItemId',
				align : 'left',
				label : '<s:text name="upItem.costItemId" />',
				hidden : false
			}, {
				name : 'sbdeptId.name',
				index : 'sbdeptId',
				align : 'left',
				label : '<s:text name="upItem.sbdeptId" />',
				hidden : false,
				width : 100
			}, {
				name : 'auditDeptId.name',
				index : 'auditDeptId',
				align : 'left',
				label : '<s:text name="upItem.auditDeptId" />',
				hidden : false,
				width : 100
			}, {
				name : 'sbPersonId.name',
				index : 'sbPersonId.name',
				align : 'left',
				label : '<s:text name="upItem.sbPersonId" />',
				hidden : false,
				width : 50
			},
			{
				name : 'remark',
				index : 'remark',
				align : 'left',
				label : '<s:text name="upItem.remark" />',
				hidden : false
			}, {
				name : 'isUpOtherDept',
				index : 'isUpOtherDept',
				align : 'center',
				label : '<s:text name="upItem.isUpOtherDept" />',
				hidden : false,
				formatter : 'checkbox',
				width : 80
			}, {
				name : 'disabled',
				index : 'disabled',
				align : 'center',
				label : '<s:text name="upItem.disabled" />',
				hidden : false,
				formatter : 'checkbox',
				width : 60
			}

			],
			jsonReader : {
				root : "upItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			gridview : true,
			rownumbers : true,
			sortname : 'id',
			viewrecords : true,
			sortorder : 'desc',
			//caption:'<s:text name="upItemList.title" />',
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
				gridContainerResize("upItem", "div");
				/* if(jQuery(this).getDataIDs().length>0){
				   jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
				 } */
				var dataTest = {
					"id" : "upItem_gridtable"
				};
				jQuery.publish("onLoadSelect", dataTest, null);
				makepager("upItem_gridtable");
			}

		});
		jQuery(upItemGrid).jqGrid('bindKeys');
	});
	jQuery(
			function() {
				jQuery("#costItemIdTxtCX").autocomplete(
						"autocomplete",
						{
							width : 300,
							multiple : false,
							autoFill : false,
							matchContains : true,
							matchCase : true,
							dataType : 'json',
							parse : function(test) {
								var data = test.autocompleteList;
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
									for (var i = 0; i < data.length; i++) {
										rows[rows.length] = {
											data : data[i].costItemId + ","
													+ data[i].costItemId + ","
													+ data[i].cnCode + ","
													+ data[i].costItemName
													+ ":" + data[i].costItemId,
											value : data[i].costItemName,
											result : data[i].costItemName
										};
									}
									return rows;
								}
							},
							extraParams : {
								flag : 2,
								entity : "CostItem",
								cloumnsStr : "costItemId,costItemName,cnCode",
								extra1 : " leaf=true and (",
								extra2 : ")"
							},
							formatItem : function(row) {
								return dropId(row);
							},
							formatResult : function(row) {
								return dropId(row);
							}
						});
				jQuery("#costItemIdTxtCX").result(
						function(event, row, formatted) {
							if (row == "没有结果") {
								return;
							}
							jQuery("#costItemIdTxt").attr("value", getId(row));
						});
			})
</script>

<div class="page">
	<div id="upItem_container">

		<div class="pageHeader" id="upItem_pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<s:hidden id="costItemIdTxt" />
					<label class="queryarea-label"><s:text
							name='upItem.itemName' />: <input type="text" id="itemNameTxt">
					</label> <label class="queryarea-label"> <s:select
							id="upItem_ItemClass_CX" key="upItem.itemClass"
							style="width:133px;" list='#{"全院":"全院","本科室":"本科室"}'
							emptyOption="true" />
					</label> <label class="queryarea-label"><s:text
							name='upItem.costItemId' />: <input type="text"
						id="costItemIdTxtCX" value="拼音/汉字/编码"
						class="input-medium defaultValueStyle"
						onfocus="clearInput(this,jQuery('#costItemIdTxt'))"
						onblur="setDefaultValue(this)" onkeydown="setTextColor(this)" /> </label>
					<label class="queryarea-label">
					<s:text name="upItem.sbdeptId"></s:text>
						<s:select list="sbDeptMap" headerKey="" headerValue="--" listKey="departmentId" listValue="name" id="upItem_sbDept"></s:select>
					</label>
					<label class="queryarea-label">
					<s:text name="upItem.auditDeptId"></s:text>
						<s:select list="auditDeptMap" headerKey="" headerValue="--" listKey="departmentId" listValue="name" id="upItem_auditDept"></s:select>
					</label>
					<%-- <td><s:text name='upItem.sbdeptId'/>:
						 	<input type="text"		id="sbdeptIdTxt" />
						 </td>
						<td><s:text name='upItem.auditDeptId'/>:
						 	<input type="text"		id="auditDeptIdTxt" /> --%>

					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="upItemGridReload()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="pageContent">





			<div class="panelBar" id="upItem_buttonBar">
				<ul class="toolBar">
					<li><a id="upItem_gridtable_add" class="addbutton"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a id="upItem_gridtable_del" class="delbutton"
						href="javaScript:"><span><s:text name="button.delete" /></span>
					</a></li>
					<li><a id="upItem_gridtable_edit" class="changebutton"
						href="javaScript:"><span><s:text
									name="button.edit" /> </span> </a></li>

				</ul>
			</div>
			<div id="upItem_gridtable_div" class="grid-wrapdiv"
				buttonBar="optId:id;width:600;height:350">
				<input type="hidden" id="upItem_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="upItem_gridtable_addTile"> <s:text
						name="upItemNew.title" />
				</label> <label style="display: none" id="upItem_gridtable_editTile">
					<s:text name="upItemEdit.title" />
				</label> <label style="display: none" id="upItem_gridtable_selectNone">
					<s:text name='list.selectNone' />
				</label> <label style="display: none" id="upItem_gridtable_selectMore">
					<s:text name='list.selectMore' />
				</label>
				<div id="load_upItem_gridtable"
					class='loading ui-state-default ui-state-active'
					style="display: none"></div>
				<table id="upItem_gridtable"></table>
				<div id="upItemPager"></div>
			</div>
		</div>
		<div class="panelBar" id="upItem_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select
					id="upItem_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text
						name="pager.total" /><label id="upItem_gridtable_totals"></label>
				<s:text name="pager.items" /></span>
			</div>

			<div id="upItem_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1"></div>

		</div>
	</div>
</div>
</div>