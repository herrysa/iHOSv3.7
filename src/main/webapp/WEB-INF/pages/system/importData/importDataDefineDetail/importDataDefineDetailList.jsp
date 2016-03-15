
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var importDataDefineDetailLayout;
	var importDataDefineDetailGridIdString = "#importDataDefineDetail_gridtable";

	jQuery(document)
			.ready(
					function() {
						var importDataDefineDetailGrid = jQuery(importDataDefineDetailGridIdString);
						importDataDefineDetailGrid
								.jqGrid({
									url : "importDataDefineDetailGridList",
									editurl : "importDataDefineDetailGridEdit",
									datatype : "json",
									mtype : "GET",
									colModel : [
											{
												name : 'detailId',
												index : 'detailId',
												align : 'center',
												label : '<s:text name="importDataDefineDetail.detailId" />',
												hidden : false,
												key : true
											},
											{
												name : 'detailName',
												index : 'detailName',
												align : 'center',
												label : '<s:text name="importDataDefineDetail.detailName" />',
												hidden : false
											},
											{
												name : 'detailType',
												index : 'detailType',
												align : 'center',
												label : '<s:text name="importDataDefineDetail.detailType" />',
												hidden : false,
												formatter : 'integer'
											},
											{
												name : 'entityName',
												index : 'entityName',
												align : 'center',
												label : '<s:text name="importDataDefineDetail.entityName" />',
												hidden : false
											},
											{
												name : 'isConstraint',
												index : 'isConstraint',
												align : 'center',
												label : '<s:text name="importDataDefineDetail.isConstraint" />',
												hidden : false,
												formatter : 'checkbox'
											} ],
									jsonReader : {
										root : "importDataDefineDetails", // (2)
										page : "page",
										total : "total",
										records : "records", // (3)
										repeatitems : false
									// (4)
									},
									sortname : 'detailId',
									viewrecords : true,
									sortorder : 'desc',
									//caption:'<s:text name="importDataDefineDetailList.title" />',
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
											"id" : "importDataDefineDetail_gridtable"
										};
										jQuery.publish("onLoadSelect",
												dataTest, null);
										makepager("importDataDefineDetail_gridtable");
									}

								});
						jQuery(importDataDefineDetailGrid).jqGrid('bindKeys');
					});
</script>

<div class="page">
	<div id="importDataDefineDetail_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="importDataDefineDetail_search_form">
					<label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.detailId' />: <input type="text"
						name="filter_EQS_detailId" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.detailName' />: <input type="text"
						name="filter_EQS_detailName" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.detailType' />: <input type="text"
						name="filter_EQS_detailType" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.entityName' />: <input type="text"
						name="filter_EQS_entityName" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.importDataDefine' />: <input
						type="text" name="filter_EQS_importDataDefine" />
					</label> <label style="float: none; white-space: nowrap"> <s:text
							name='importDataDefineDetail.isConstraint' />: <input type="text"
						name="filter_EQS_isConstraint" />
					</label>
				</form>
				<div class="buttonActive" style="float: right">
					<div class="buttonContent">
						<button type="button"
							onclick="propertyFilterSearch(importDataDefineDetail_search_form,importDataDefineDetail_gridtable)">
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
									onclick="propertyFilterSearch(importDataDefineDetail_search_form,importDataDefineDetail_gridtable)">
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
				<li><a id="importDataDefineDetail_gridtable_add"
					class="addbutton" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="importDataDefineDetail_gridtable_del"
					class="delbutton" href="javaScript:"><span><s:text
								name="button.delete" /></span> </a></li>
				<li><a id="importDataDefineDetail_gridtable_edit"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>

			</ul>
		</div>
		<div id="importDataDefineDetail_gridtable_div" layoutH="120"
			class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="importDataDefineDetail_gridtable_navTabId"
				value="${sessionScope.navTabId}"> <label
				style="display: none" id="importDataDefineDetail_gridtable_addTile">
				<s:text name="importDataDefineDetailNew.title" />
			</label> <label style="display: none"
				id="importDataDefineDetail_gridtable_editTile"> <s:text
					name="importDataDefineDetailEdit.title" />
			</label>
			<div id="load_importDataDefineDetail_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="importDataDefineDetail_gridtable"></table>
			<!--<div id="importDataDefineDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select
				id="importDataDefineDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text
					name="pager.total" /><label
				id="importDataDefineDetail_gridtable_totals"></label>
			<s:text name="pager.items" /></span>
		</div>
		<div id="importDataDefineDetail_gridtable_pagination"
			class="pagination" targetType="navTab" totalCount="200"
			numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>