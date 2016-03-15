
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function statisticsItemGridReload() {
		var urlString = "statisticsItemGridList";
		var code = jQuery("#search_statisticsItem_code").val();
		var name = jQuery("#search_statisticsItem_name").val();
		var remark = jQuery("#search_statisticsItem_remark").val();
		var disabled = jQuery("#search_statisticsItem_disabled").val();

		urlString = urlString + "?filter_LIKES_code=" + code
				+ "&filter_LIKES_name=" + name;
		urlString = urlString + "&filter_LIKES_remark=" + remark;
		urlString = urlString + "&filter_EQB_disabled=" + disabled;
		urlString = urlString + "&statisticsCode=" + "${statisticsCode}";
		urlString = encodeURI(urlString);
		jQuery("#statisticsItem_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var statisticsItemLayout;
	var statisticsItemGridIdString = "#statisticsItem_gridtable";

	jQuery(document)
			.ready(
					function() {
						var hrDepartmentCurrentFullSize = jQuery("#container")
								.innerHeight() - 118;
						jQuery("#statisticsItem_container").css("height",
								hrDepartmentCurrentFullSize);
						$('#statisticsItem_container').layout(
								{
									applyDefaultStyles : false,
									west__size : 250,
									spacing_open : 5,//边框的间隙  
									spacing_closed : 5,//关闭时边框的间隙 
									resizable : true,
									resizerClass : "ui-layout-resizer-blue",
									slidable : true,
									resizerDragOpacity : 1,
									resizerTip : "可调整大小",
									onresize_end : function(paneName, element,
											state, options, layoutName) {
										if ("center" == paneName) {
											gridResize(null, null,
													"statisticsItem_gridtable",
													"single");
										}
									}
								});
						var statisticsItemChangeData = function(
								selectedSearchId) {
							if (selectedSearchId.length == 0) {
								statisticsItemLayout.closeSouth();
								return;
							}
							jQuery("#statisticsCondition").load(
									"statisticsConditionList?itemId="
											+ selectedSearchId);
							$("#background,#progressBar").hide();
						};
						statisticsItemLayout = makeLayout(
								{
									'baseName' : 'statisticsCondition',
									'tableIds' : 'statisticsItem_gridtable;statisticsCondition_gridtable',
									'activeGridTable' : 'statisticsItem_gridtable',
									'fullSize' : 96,
									'proportion' : 2,
									'key' : 'id'
								}, statisticsItemChangeData);
						var initFlag = 0;
						var statisticsItemGrid = jQuery(statisticsItemGridIdString);
						statisticsItemGrid
								.jqGrid({
									url : "statisticsItemGridList?statisticsCode="
											+ "${statisticsCode}",
									editurl : "statisticsItemGridEdit",
									datatype : "json",
									mtype : "GET",
									colModel : [
											{
												name : 'id',
												index : 'id',
												align : 'center',
												label : '<s:text name="statisticsItem.id" />',
												hidden : true,
												key : true
											},
											{
												name : 'code',
												index : 'code',
												align : 'left',
												width : 100,
												label : '<s:text name="statisticsItem.code" />',
												hidden : false,
												highsearch : true
											},
											{
												name : 'name',
												index : 'name',
												align : 'left',
												width : 100,
												label : '<s:text name="statisticsItem.name" />',
												hidden : false,
												highsearch : true
											},
											{
												name : 'parentType.name',
												index : 'parentType.name',
												align : 'left',
												width : 100,
												label : '<s:text name="statisticsItem.parentType" />',
												hidden : false,
												highsearch : true
											},
											// {name:'sn',index:'sn',align:'right',width : 60,label : '<s:text name="statisticsItem.sn" />',hidden:false,formatter:'integer',highsearch:true},	
											{
												name : 'mccKeyId',
												index : 'mccKeyId',
												align : 'left',
												width : 100,
												label : '<s:text name="statisticsItem.mccKeyId" />',
												hidden : false,
												highsearch : true
											},
											{
												name : 'statisticsBdInfo.bdInfo.tableName',
												index : 'statisticsBdInfo.bdInfo.tableName',
												align : 'left',
												width : 150,
												label : '<s:text name="statisticsItem.statisticsBdInfo" />',
												hidden : false,
												highsearch : true
											},
											// {name:'statisticsValue',index:'statisticsValue',align:'center',width : 60,label : '<s:text name="statisticsItem.statisticsValue" />',hidden:false,formatter:'checkbox',highsearch:true},
											{
												name : 'statisticsFieldInfo',
												index : 'statisticsFieldInfo',
												align : 'left',
												width : 100,
												label : '<s:text name="statisticsItem.statisticsFieldInfo" />',
												hidden : false,
												highsearch : true
											},
											{
												name : 'dynamicStatistics',
												index : 'dynamicStatistics',
												align : 'center',
												width : 70,
												label : '<s:text name="statisticsItem.dynamicStatistics" />',
												hidden : false,
												formatter : 'checkbox',
												highsearch : true
											},
											// {name:'dynamicTable',index:'dynamicTable',align:'left',width : 100,label : '<s:text name="statisticsItem.dynamicTable" />',hidden:false,highsearch:true},
											// {name:'dynamicCode',index:'dynamicCode',align:'left',width : 100,label : '<s:text name="statisticsItem.dynamicCode" />',hidden:false,highsearch:true},
											// {name:'dynamicTablePk',index:'dynamicTablePk',align:'left',width : 100,label : '<s:text name="statisticsItem.dynamicTablePk" />',hidden:false,highsearch:true},
											// {name:'dynamicTableForeignKey',index:'dynamicTableForeignKey',align:'left',width : 100,label : '<s:text name="statisticsItem.dynamicTableForeignKey" />',hidden:false,highsearch:true},
											// {name:'dynamicFilterSql',index:'dynamicFilterSql',align:'left',width : 200,label : '<s:text name="statisticsItem.dynamicFilterSql" />',hidden:false,highsearch:true},
											{
												name : 'sysFiled',
												index : 'sysFiled',
												align : 'center',
												width : 60,
												label : '<s:text name="statisticsItem.sysFiled" />',
												hidden : true,
												formatter : 'checkbox',
												highsearch : false
											},
											{
												name : 'disabled',
												index : 'disabled',
												align : 'center',
												width : 60,
												label : '<s:text name="statisticsItem.disabled" />',
												hidden : false,
												formatter : 'checkbox',
												highsearch : true
											},
											{
												name : 'remark',
												index : 'remark',
												align : 'left',
												width : 250,
												label : '<s:text name="statisticsItem.remark" />',
												hidden : false,
												highsearch : true
											}
									// {name:'changeUser.name',index:'changeUser.name',align:'left',width : 100,label : '<s:text name="statisticsItem.changeUser" />',hidden:false,highsearch:true},	
									// {name:'changeDate',index:'changeDate',align:'center',width : 80,label : '<s:text name="statisticsItem.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true}	

									],
									jsonReader : {
										root : "statisticsItems", // (2)
										page : "page",
										total : "total",
										records : "records", // (3)
										repeatitems : false
									// (4)
									},
									sortname : 'id',
									viewrecords : true,
									sortorder : 'desc',
									//caption:'<s:text name="statisticsItemList.title" />',
									height : '100%',
									gridview : true,
									rownumbers : true,
									loadui : "disable",
									multiselect : true,
									multiboxonly : true,
									shrinkToFit : false,
									autowidth : false,
									ondblClickRow : function() {
										statisticsItemLayout.optDblclick();
									},
									onSelectRow : function(rowid) {
										setTimeout(function() {
											statisticsItemLayout.optClick();
										}, 100);
									},
									gridComplete : function() {
										gridContainerResize('statisticsItem',
												'layout');
										//            if(jQuery(this).getDataIDs().length>0){
										//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
										//             }
										var dataTest = {
											"id" : "statisticsItem_gridtable"
										};
										jQuery.publish("onLoadSelect",
												dataTest, null);
										initFlag = initColumn(
												'statisticsItem_gridtable',
												'com.huge.ihos.hr.statistics.model.StatisticsItem',
												initFlag);
										//       	   makepager("statisticsItem_gridtable");
									}

								});
						jQuery(statisticsItemGrid).jqGrid('bindKeys');

						jQuery("#statisticsItem_gridtable_add_custom")
								.unbind('click')
								.bind(
										"click",
										function() {

											var zTree = $.fn.zTree
													.getZTreeObj("statisticsItemTreeLeft");
											checkCount = zTree
													.getSelectedNodes();
											var classpurview = "";
											if (checkCount.length == 1) {
												for (var i = 0; i < checkCount.length; i++) {
													if (checkCount[i].subSysTem == "leaf") {
														classpurview = checkCount[i].id;
													} else if (checkCount[i].subSysTem == "item") {
														classpurview = checkCount[i].pId;
													} else {
														alertMsg
																.error("父级统计类别不能添加统计项。");
														return;
													}
												}
											} else {
												alertMsg.error("请选择统计类别。");
												return;
											}
											var url = "editStatisticsItem?navTabId=statisticsItem_gridtable";
											url = url + "&statisticsCode="
													+ "${statisticsCode}";
											if (classpurview) {
												url += "&parentTypeId="
														+ classpurview;
											}
											var winTitle = '<s:text name="statisticsItemNew.title"/>';
											$.pdialog.open(url,
													'addStatisticsItem',
													winTitle, {
														mask : true,
														width : 700,
														height : 400
													});
										});
						jQuery("#statisticsItem_gridtable_del_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var sid = jQuery(
													"#statisticsItem_gridtable")
													.jqGrid('getGridParam',
															'selarrrow');
											if (sid == null || sid.length == 0) {
												alertMsg.error("请选择记录。");
												return;
											} else {
												var url = "${ctx}/statisticsItemGridEdit?oper=del";
												url = url
														+ "&id="
														+ sid
														+ "&navTabId=statisticsItem_gridtable";
												alertMsg
														.confirm(
																"确认删除？",
																{
																	okCall : function() {
																		$
																				.post(
																						url,
																						function(
																								data) {
																							formCallBack(data);
																							statisticsItemLeftTree();
																						});
																	}
																});
											}
										});
						jQuery("#statisticsItem_gridtable_edit_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var sid = jQuery(
													"#statisticsItem_gridtable")
													.jqGrid('getGridParam',
															'selarrrow');
											if (sid == null || sid.length != 1) {
												alertMsg.error("请选择一条记录。");
												return;
											}
											var winTitle = '<s:text name="statisticsItemEdit.title"/>';
											var url = "editStatisticsItem?navTabId=statisticsItem_gridtable&id="
													+ sid;
											url = url + "&statisticsCode="
													+ "${statisticsCode}";
											$.pdialog.open(url,
													'editStatisticsItem',
													winTitle, {
														mask : true,
														width : 700,
														height : 400
													});
										});
						//statisticsItemLayout.resizeAll();
						//condition toobar load
						jQuery("#statisticsCondition_gridtable_add_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var url = "editStatisticsCondition?popup=true&navTabId=statisticsCondition_gridtable";
											var sid = jQuery(
													"#statisticsItem_gridtable")
													.jqGrid('getGridParam',
															'selarrrow');
											if (sid == null || sid.length != 1) {
												alertMsg.error("请选择一条统计项记录。");
												return;
											}
											url += "&itemId=" + sid;
											url = url + "&statisticsCode="
													+ "${statisticsCode}";
											var winTitle = '<s:text name="statisticsConditionNew.title"/>';
											$.pdialog.open(url,
													'editStatisticsCondition',
													winTitle, {
														mask : true,
														resizable : false,
														maxable : false,
														width : 700,
														height : 600
													});
										});
						jQuery("#statisticsCondition_gridtable_edit_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var sid = jQuery(
													"#statisticsCondition_gridtable")
													.jqGrid('getGridParam',
															'selarrrow');
											if (sid == null || sid.length != 1) {
												alertMsg.error("请选择一条统计条件记录。");
												return;
											}
											var winTitle = '<s:text name="statisticsConditionEdit.title"/>';
											var url = "editStatisticsCondition?popup=true&id="
													+ sid
													+ "&navTabId=statisticsCondition_gridtable";
											url = url + "&statisticsCode="
													+ "${statisticsCode}";
											$.pdialog.open(url,
													'editStatisticsCondition',
													winTitle, {
														mask : true,
														resizable : false,
														maxable : false,
														width : 700,
														height : 600
													});
										});
						statisticsItemLeftTree();
					});
	function statisticsItemLeftTree() {
		$.get("makeStatisticsItemTree?statisticsCode=" + "${statisticsCode}", {
			"_" : $.now()
		}, function(data) {
			var statisticsItemTreeData = data.statisticsItemTreeNodes;
			var statisticsItemTree = $.fn.zTree.init(
					$("#statisticsItemTreeLeft"),
					ztreesetting_statisticsItemLeft, statisticsItemTreeData);
			var nodes = statisticsItemTree.getNodes();
			statisticsItemTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_statisticsItemLeft = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag : zTreeBeforeDrag,
			onClick : onModuleClick
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId"
			}
		}
	};
	function zTreeBeforeDrag(treeId, treeNodes) {
		return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) {
		var urlString = "statisticsItemGridList";
		switch (treeNode.subSysTem) {
		case 'item':
			urlString = urlString + "?filter_EQS_id=" + treeNode.id;
			break;
		case 'parent':
			//urlString=urlString+"?filter_EQS_parentType.id="+treeNode.id;
			break;
		case 'leaf':
			urlString = urlString + "?filter_EQS_parentType.id=" + treeNode.id;
			break;
		default:
			urlString = urlString + "?filter_EQS_id=" + treeNode.id;
		}
		urlString = urlString + "&statisticsCode=" + "${statisticsCode}";
		urlString = encodeURI(urlString);
		jQuery("#statisticsItem_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
</script>

<div class="page" id="statisticsItem_page">
	<div class="pageHeader" id="statisticsItem_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="statisticsItem_search_form" class="queryarea-form">
					<label class="queryarea-label"> <s:text
							name='statisticsItem.code' />: <input type="text"
						id="search_statisticsItem_code" />
					</label> <label class="queryarea-label"> <s:text
							name='statisticsItem.name' />: <input type="text"
						id="search_statisticsItem_name" />
					</label> <label class="queryarea-label"> <s:text
							name='statisticsItem.disabled' />: <s:select
							id="search_statisticsItem_disabled" headerKey="" headerValue="--"
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false" maxlength="10" theme="simple"
							style="font-size:9pt;">
						</s:select>
					</label> <label class="queryarea-label"> <s:text
							name='statisticsItem.remark' />: <input type="text"
						id="search_statisticsItem_remark" />
					</label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button" onclick="statisticsItemGridReload()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
			<!-- 				<div class="subBar"> -->
			<!-- 					<ul> -->
			<!-- 						<li><div class="buttonActive"> -->
			<!-- 								<div class="buttonContent"> -->
			<%-- 									<button type="button" onclick="statisticsItemGridReload()"><s:text name='button.search'/></button> --%>
			<!-- 								</div> -->
			<!-- 							</div></li> -->

			<!-- 					</ul> -->
			<!-- 				</div> -->
		</div>
	</div>
	<div class="pageContent">





		<div class="panelBar" id="statisticsItem_buttonBar">

			<ul class="toolBar">
				<li><a id="statisticsItem_gridtable_add_custom"
					class="addbutton" href="javaScript:"><span><fmt:message
								key="button.add" /></span></a></li>
				<li><a id="statisticsItem_gridtable_del_custom"
					class="delbutton" href="javaScript:"><span><s:text
								name="button.delete" /></span></a></li>
				<li><a id="statisticsItem_gridtable_edit_custom"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /></span></a></li>
				<li><a class="settlebutton"
					href="javaScript:setColShow('statisticsItem_gridtable','com.huge.ihos.hr.statistics.model.StatisticsItem')"><span><s:text
								name="button.setColShow" /></span></a></li>
				<li style="float: right"><a class="particularbutton"
					href="javaScript:statisticsItemLayout.optDblclick();"><span>统计条件</span>
				</a></li>
			</ul>
		</div>
		<div id="statisticsItem_container">
			<div id="statisticsItem_layout-west" class="pane ui-layout-west"
				style="float: left; display: block; overflow: auto;">
				<div style="margin-left: 20px; margin-bottom: 2px;">
					<span
						style="vertical-align: middle; text-decoration: underline; cursor: pointer; float: right;"
						onclick="toggleExpandTreeWithShowFlag(this,'statisticsItemTreeLeft',true)">展开</span>
				</div>
				<div id="statisticsItemTreeLeft" class="ztree"></div>
			</div>
			<div id="statisticsItem_layout-center" class="pane ui-layout-center">
				<div id="statisticsCondition_container">
					<div id="statisticsCondition_layout-center"
						class="pane ui-layout-center">

						<div id="statisticsItem_gridtable_div" class="grid-wrapdiv"
							style="margin-left: 2px; margin-top: 2px; overflow: hidden"
							buttonBar="optId:id;width:900;height:550">
							<input type="hidden" id="statisticsItem_gridtable_navTabId"
								value="${sessionScope.navTabId}"> <label
								style="display: none" id="statisticsItem_gridtable_addTile">
								<s:text name="statisticsItemNew.title" />
							</label> <label style="display: none"
								id="statisticsItem_gridtable_editTile"> <s:text
									name="statisticsItemEdit.title" />
							</label> <label style="display: none"
								id="statisticsItem_gridtable_selectNone"> <s:text
									name='list.selectNone' />
							</label> <label style="display: none"
								id="statisticsItem_gridtable_selectMore"> <s:text
									name='list.selectMore' />
							</label>
							<div id="load_statisticsItem_gridtable"
								class='loading ui-state-default ui-state-active'
								style="display: none"></div>
							<table id="statisticsItem_gridtable"></table>
							<div id="statisticsItemPager"></div>
						</div>
						<div class="panelBar" id="statisticsItem_pageBar">
							<div class="pages">
								<span><s:text name="pager.perPage" /></span> <select
									id="statisticsItem_gridtable_numPerPage">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <span><s:text name="pager.items" />. <s:text
										name="pager.total" /><label
									id="statisticsItem_gridtable_totals"></label>
								<s:text name="pager.items" /></span>
							</div>

							<div id="statisticsItem_gridtable_pagination" class="pagination"
								targetType="navTab" totalCount="200" numPerPage="20"
								pageNumShown="10" currentPage="1"></div>

						</div>
					</div>
					<div id="statisticsCondition_layout-south"
						class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
								<li><a id="statisticsCondition_gridtable_add_custom"
									class="addbutton" href="javaScript:"><span><fmt:message
												key="button.add" /></span></a></li>
								<li><a id="statisticsCondition_gridtable_del"
									class="delbutton" href="javaScript:"><span><s:text
												name="button.delete" /></span></a></li>
								<li><a id="statisticsCondition_gridtable_edit_custom"
									class="changebutton" href="javaScript:"><span><s:text
												name="button.edit" /></span></a></li>
								<li><a class="settlebutton"
									href="javaScript:setColShow('statisticsCondition_gridtable','com.huge.ihos.hr.statistics.model.StatisticsCondition')"><span><s:text
												name="button.setColShow" /></span></a></li>

								<li style="float: right;"><a id="statisticsCondition_close"
									class="closebutton" href="javaScript:"><span><fmt:message
												key="button.close" /></span></a></li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;"><a id="statisticsCondition_fold"
									class="foldbutton" href="javaScript:"><span><fmt:message
												key="button.fold" /></span></a></li>
								<li class="line" style="float: right">line</li>
								<li style="float: right"><a id="statisticsCondition_unfold"
									class="unfoldbutton" href="javaScript:"><span><fmt:message
												key="button.unfold" /></span></a></li>
							</ul>
						</div>
						<div id="statisticsCondition"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>