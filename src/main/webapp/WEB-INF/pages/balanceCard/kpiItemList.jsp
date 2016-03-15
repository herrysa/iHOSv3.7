
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
function expandAll(){
	$("#kpiItem_gridtable").find(".treeclick").trigger('click');
}
	function addKpiItem(){
		var url = "editKpiItem?popup=true&navTabId=kpiItem_gridtable";
			var parentId = jQuery("#kpiItem_gridtable").jqGrid('getGridParam','selrow');
				url += "&parentId="+parentId;
	
			if(parentId==null ){
					alertMsg.error(selectNone);
					return;
				}
		var winTitle="add kpi item";
		//alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, '', winTitle, {mask:false,width:550,height:350});　
	}
	
	function editKpiItem(){
		var url = "editKpiItem?popup=true&navTabId=kpiItem_gridtable";
			var parentId = jQuery("#kpiItem_gridtable").jqGrid('getGridParam','selrow');
				url += "&kpiItemId="+parentId;
	
			if(parentId==null ){
					alertMsg.error(selectNone);
					return;
				}
		var winTitle="edit kpi item";
		//alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, '', winTitle, {mask:false,width:550,height:350});　
	}
	function deleteKpiItem(){
		var url = "deleteKpiItem?popup=true&navTabId=kpiItem_gridtable";
			var parentId = jQuery("#kpiItem_gridtable").jqGrid('getGridParam','selrow');
				url += "&kpiItemId="+parentId;
	
			if(parentId==null ){
					alertMsg.error(selectNone);
					return;
				}
		//var winTitle="edit kpi item";
		//alert(url);
		url = encodeURI(url);
		
		var jqxhr = jQuery.post(url, {
			dataType : "json"
		}).success(function(data) {
			var status = data['statusCode'];
			if (status === 300) {
				//alert(data['message']);
				alertMsg.error( data['message']);
			} else {
				//if (isRefreshGrid)
					//refreshGrid(gridID, false);
					alertMsg.info(data['message']);
			}

		});
		jQuery("#kpiItem_gridtable").jqGrid().trigger("reloadGrid");
		//$.pdialog.open(url, '', winTitle, {mask:false,width:550,height:350});　
	}
	function buildKpiItemTree() {
		var url = "rebuildKpiTree";
		try {
			var jqxhr = jQuery.post(url, {
				dataType : "json"
			}).success(function(data) {
				var status = data['statusCode'];
				if (status === 300) {
					//alert(data['message']);
					alertMsg.error( data['message']);
				} else {
					//if (isRefreshGrid)
						//refreshGrid(gridID, false);
						alertMsg.info(data['message']);
						jQuery("#kpiItem_gridtable").jqGrid().trigger("reloadGrid");
				}

			});
		} catch (e) {
			alertMsg.error(e.message);
		}
	}
	function kpiItemGridReload() {
		var urlString = "kpiItemGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();

		urlString = urlString + "?filter_LIKES_paramKey=" + paramKeyTxt
				+ "&filter_LIKES_paramValue=" + paramValueTxt
				+ "&filter_LIKES_description=" + descriptionTxt
				+ "&filter_LIKES_subSystemId=" + subSystemTxt;
		urlString = encodeURI(urlString);
		jQuery("#kpiItem_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var kpiItemLayout;
	var kpiItemGridIdString = "#kpiItem_gridtable";

	jQuery(document).ready(
			function() {
				kpiItemLayout = makeLayout({
					baseName : 'kpiItem',
					tableIds : 'kpiItem_gridtable'
				}, null);
				var kpiItemGrid = jQuery(kpiItemGridIdString);
				kpiItemGrid.jqGrid({
					url : "kpiItemGrid",
					datatype : "json",
					mtype : "GET",
					colModel : [ 
					             
					{
						name : 'keyCode',
						index : 'keyCode',
						align : 'center',
						label : '<s:text name="kpiItem.keyCode" />',
						hidden : false
					}, 
					             {
						name : 'keyName',
						index : 'keyName',
						align : 'center',
						label : '<s:text name="kpiItem.keyName" />',
						hidden : false
					}, 
					 {
						name : 'id',
						index : 'id',
						align : 'center',
						label : '<s:text name="kpiItem.id" />',
						hidden : true,
						key : true,
						formatter : 'integer'
					},
					/*{
						name : 'level',
						index : 'level',
						align : 'center',
						label : '<s:text name="kpiItem.level" />',
						hidden : false,
						formatter : 'integer'
					}, {
						name : 'lft',
						index : 'lft',
						align : 'center',
						label : '<s:text name="kpiItem.lft" />',
						hidden : false,
						formatter : 'integer'
					}, {
						name : 'rgt',
						index : 'rgt',
						align : 'center',
						label : '<s:text name="kpiItem.rgt" />',
						hidden : false,
						formatter : 'integer'
					},  */
					/* {
						name : 'actualField',
						index : 'actualField',
						align : 'center',
						label : '<s:text name="kpiItem.actualField" />',
						hidden : false
					}, 
					{
						name : 'displayOrder',
						index : 'displayOrder',
						align : 'center',
						label : '<s:text name="kpiItem.displayOrder" />',
						hidden : false,
						formatter : 'integer'
					}, */
					/* {
						name : 'leaf',
						index : 'leaf',
						align : 'center',
						label : '<s:text name="kpiItem.leaf" />',
						hidden : false,
						formatter : 'checkbox'
					}, 
					{
						name : 'targetField',
						index : 'targetField',
						align : 'center',
						label : '<s:text name="kpiItem.targetField" />',
						hidden : false
					},
					{
						name : 'scoreField',
						index : 'scoreField',
						align : 'center',
						label : '<s:text name="kpiItem.scoreField" />',
						hidden : false
					},*/
					{
						name : 'used',
						index : 'used',
						align : 'center',
						label : '<s:text name="kpiItem.used" />',
						hidden : false,
						formatter : 'checkbox'
					},
					{
						name : 'disabled',
						index : 'disabled',
						align : 'center',
						label : '<s:text name="kpiItem.disabled" />',
						hidden : false,
						formatter : 'checkbox'
					} 

					],
					jsonReader : {
						root : "kpiItemlist", // (2)
						page : "page",
						total : "total",
						records : "records", // (3)
						repeatitems : false
					// (4)
					},
					//  gridview:true,
					//  rownumbers:true,
					sortname : 'lft',
					//  viewrecords: true,
					sortorder : 'asc',
					//caption:'<s:text name="kpiItemList.title" />',
					height : 300,
					// gridview:true,
					// rownumbers:true,
					loadui : "disable",
					//multiselect: true,
					//multiboxonly:true,
					//shrinkToFit:false,
					//autowidth:false,

					treeGrid : true,
					treeGridModel : "adjacency",
					ExpandColumn : "keyCode",
					treeIcons : {
						leaf : 'ui-icon-document-b'
					},
					ExpandColClick : true,

					onSelectRow : function(rowid) {

					},
					gridComplete : function() {
						if (jQuery(this).getDataIDs().length > 0) {
							jQuery(this).jqGrid('setSelection',
									jQuery(this).getDataIDs()[0]);
						}
						var dataTest = {
							"id" : "kpiItem_gridtable"
						};
						jQuery.publish("onLoadSelect", dataTest, null);
						makepager("kpiItem_gridtable");
						$("#kpiItem_gridtable").find(".treeclick").trigger('click');
					}

				});
				jQuery(kpiItemGrid).jqGrid('bindKeys');
				
				kpiItemLayout.resizeAll();
			});
</script>

<div class="page">
<div id="kpiItem_container">
			<div id="kpiItem_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='kpiItem.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='kpiItem.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='kpiItem.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='kpiItem.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="kpiItemGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a  class="addbutton" href="javaScript:addKpiItem()" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton"  href="javaScript:deleteKpiItem()"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:editKpiItem()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:buildKpiItemTree()"
					><span>rebuild
					</span>
				</a>
				</li>
				<!-- <li><a  class="changebutton"  href="javaScript:expandAll()"
					><span>expandAll
					</span>
				</a>
				</li> -->
				
				
			
			</ul>
		</div>
		
		
		
		<div id="kpiItem_gridtable_div" layoutH="120"
			class="grid-wrapdiv"
			buttonBar="optId:kpiItemId;fatherGrid:kpiItem_gridtable;extraParam:parentId;width:500;height:300">
			<input type="hidden" id="kpiItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kpiItem_gridtable_addTile">
				<s:text name="kpiItemNew.title"/>
			</label> 
			<label style="display: none"
				id="kpiItem_gridtable_editTile">
				<s:text name="kpiItemEdit.title"/>
			</label>
			<label style="display: none"
				id="kpiItem_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="kpiItem_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_kpiItem_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="kpiItem_gridtable"></table>
		<div id="kpiItemPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="kpiItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kpiItem_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="kpiItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>