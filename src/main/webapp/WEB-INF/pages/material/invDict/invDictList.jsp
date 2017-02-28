<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	function inventoryDictGridReload(){
		var urlString = "inventoryDictGridList";
		
		var paramKeyTxt = jQuery("#search_inventoryDict_invType_id").val();
		//console.log(paramKeyTxt);
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var invCodeTxt = jQuery("#search_inventoryDict_invCode").val();
		var invNameTxt = jQuery("#search_inventoryDict_invName").val();
		
		var agentTxt = jQuery("#search_inventoryDict_agent").val();
		var factoryTxt = jQuery("#search_inventoryDict_factory").val();
		var isHighValueTxt = jQuery("#search_inventoryDict_isHighValue").val();
		var invUseTxt = jQuery("#search_inventoryDict_invUse").val();
	
		urlString=urlString+"?filter_EQS_inventoryType.id="+paramKeyTxt+"&filter_LIKES_invCode="+invCodeTxt+"&filter_LIKES_invName="+invNameTxt+"&filter_LIKES_agent="+agentTxt
				+"&filter_LIKES_factory="+factoryTxt+"&filter_EQB_isHighValue="+isHighValueTxt+"&filter_EQS_invUse.invUseId="+invUseTxt;
		urlString=encodeURI(urlString);
		jQuery("#inventoryDict_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var inventoryDictGridIdString="#inventoryDict_gridtable";
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var invDictFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#inventoryDict_container").css("height",invDictFullSize);
		$('#inventoryDict_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",//鼠标移到边框时，提示语
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				if("center" == paneName){
					gridResize(null,null,"inventoryDict_gridtable","single");
				}
			}
		});
		initInventoryTypeLeftTree();
		var inventoryDictGrid = jQuery(inventoryDictGridIdString);
		inventoryDictGrid.jqGrid({
			url : "inventoryDictGridList",
			editurl : "inventoryDictGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
					{name : 'invId',index : 'invId',align : 'center',label : '<s:text name="inventoryDict.invId" />',hidden : true,key : true},
					{name : 'invCode',index : 'invCode',align : 'left',label : '<s:text name="inventoryDict.invCode" />',hidden : false,width : 80,highsearch:true},
					{name : 'invName',index : 'invName',align : 'left',label : '<s:text name="inventoryDict.invName" />',hidden : false,width : 150,highsearch:true},
					{name : 'inventoryType.invTypeName',index : 'inventoryType_id',align : 'left',label : '<s:text name="inventoryDict.inventoryType" />',hidden : false,width : 100,highsearch:true},
					{name : 'invModel',index : 'invModel',align : 'left',label : '<s:text name="inventoryDict.invModel" />',hidden : false,width : 150,highsearch:true},
					{name : 'cnCode',index : 'cnCode',align : 'left',label : '<s:text name="inventoryDict.cnCode" />',hidden : false,width : 130,highsearch:true},
					{name : 'planPrice',index : 'planPrice',align : 'right',label : '<s:text name="inventoryDict.planPrice" />',hidden : false,formatter : 'number',width : 80,highsearch:true},
					{name : 'vendor.vendorName',index : 'vendor',align : 'left',label : '<s:text name="inventoryDict.vendor" />',hidden : false,width : 200,highsearch:true},
					{name : 'refCost',index : 'refCost',align : 'right',label : '<s:text name="inventoryDict.refCost" />',hidden : false,formatter : 'number',width : 80,highsearch:true},
					{name : 'firstUnit',index : 'firstUnit',align : 'center',label : '<s:text name="inventoryDict.firstUnit" />',hidden : false,width : 80,highsearch:true},
					{name : 'safeStock',index : 'safeStock',align : 'right',label : '<s:text name="inventoryDict.safeStock" />',hidden : false,formatter : 'integer',width : 80,highsearch:true},
					{name : 'lowLimit',index : 'lowLimit',align : 'right',label : '<s:text name="inventoryDict.lowLimit" />',hidden : false,formatter : 'integer',width : 80,highsearch:true},
					{name : 'highLimit',index : 'highLimit',align : 'right',label : '<s:text name="inventoryDict.highLimit" />',hidden : false,formatter : 'integer',width : 80,highsearch:true},
					{name : 'isCharge',index : 'isCharge',align : 'center',label : '<s:text name="inventoryDict.isCharge" />',hidden : false,formatter : 'checkbox',width : 80,highsearch:true},
					{name : 'isHighValue',index : 'isHighValue',align : 'center',label : '<s:text name="inventoryDict.isHighValue" />',hidden : false,formatter : 'checkbox',width : 80,highsearch:true},
					{name : 'disabled',index : 'disabled',align : 'center',label : '<s:text name="inventoryDict.disabled" />',hidden : false,formatter : 'checkbox',width : 80,highsearch:true},

					],
			jsonReader : {
				root : "inventoryDicts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			sortname : 'invCode',
			viewrecords : true,
			sortorder : 'asc',

			//caption:'<s:text name="inventoryDictList.title" />',
			height : 300,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			onSelectRow : function(rowid) {

			},
			gridComplete : function() {
				var dataTest = {
					"id" : "inventoryDict_gridtable"
				};
				jQuery.publish("onLoadSelect",
						dataTest, null);
				initFlag = initColumn('inventoryDict_gridtable','com.huge.ihos.material.model.InventoryDict',initFlag);
				
				//makepager("inventoryDict_gridtable");
			}

		});
		jQuery(inventoryDictGrid).jqGrid('bindKeys');
		
		jQuery("#search_inventoryDict_invType").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
		
		jQuery("#inventoryDict_gridtable_batchEdit").unbind('click').bind("click",function() {
			var sid = jQuery(
					"#inventoryDict_gridtable")
					.jqGrid('getGridParam',
							'selarrrow');
			selectNone = "请选择记录。";
			selectMore = "只能选择一条记录。";
			var editTitle = "材料字典批量修改";
			if (sid == null || sid.length == 0) {
				alertMsg.error(selectNone);
				return;
			}
			else {
				var url = "inventoryDictBatchEdit"
						+ "?popup=true&"
						+ "id"
						+ "="
						+ sid
						+ "&navTabId="
						+ "inventoryDict_gridtable";

				var winTitle = editTitle;
				url = encodeURI(url);
				$.pdialog.open(url,
						'batchEditInventoryDict',
						winTitle, {
							mask : false,
							width : 520,
							height : 600,
							resizable:false,
							maxable:false
						});

			}
		});
		
		jQuery("#inventoryDict_gridtable_copy").unbind('click').bind("click",function() {
			var sid = jQuery(
					"#inventoryDict_gridtable")
					.jqGrid('getGridParam',
							'selarrrow');
			selectNone = "请选择记录。";
			selectMore = "只能选择一条记录。";
			var editTitle = jQuery(
					"#inventoryDict_gridtable_addTile")
					.text();
			if (sid == null || sid.length == 0) {
				alertMsg.error(selectNone);
				return;
			}
			if (sid.length > 1) {
				alertMsg.error(selectMore);
				return;
			} else {
				var url = "copyInventoryDict"
						+ "?popup=true&"
						+ "invId"
						+ "="
						+ sid
						+ "&navTabId="
						+ "inventoryDict_gridtable";

				var winTitle = editTitle;
				url = encodeURI(url);
				$.pdialog.open(url,
						'copyInventoryDict',
						winTitle, {
							mask : false,
							width : 900,
							height : 550
						});
			}
		});
		
		jQuery("#inventoryDict_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
			var invTypeId="";
			var invTypeTreeObj = $.fn.zTree.getZTreeObj("inventoryTypeTreeLeft");
			var nodes = invTypeTreeObj.getSelectedNodes();
			if (nodes &&  nodes.length > 0) {
				node = nodes[0];
				invTypeId = node.id;
				if (node.leaf == false) {
					invTypeId = "";
				}
			}
			
			var url = "editInventoryDict"
					+ "?popup=true&navTabId="
					+ "inventoryDict_gridtable"
					+ "&invTypeId=" + invTypeId;
		
			var winTitle = "新建物资字典";
			url = encodeURI(url);
			$.pdialog.open(url,
					'addInventoryDict',
					winTitle, {
						mask : false,
						width : 900,
						height : 550
					});
		});

	});
	function initInventoryTypeLeftTree() {
		$.get("makeInventoryTypeTree?disabled=disabled", {
			"_" : $.now()
		}, function(data) {
			var invTypeTreeData = data.inventoryTypeTreeList;
			var invTypeTree = $.fn.zTree.init($("#inventoryTypeTreeLeft"),
					ztreesetting_inventoryTypeLeft, invTypeTreeData);
			var nodes = invTypeTree.getNodes();
			invTypeTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_inventoryTypeLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onModuleClick
			},
			data : {
				key : {
					name : "invTypeNameWithCode"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parent"
				}
			}
	};
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) {
	 	var gridId = "inventoryDict_gridtable";
		var url = "${ctx}/inventoryDictGridList";
		var parentId = treeNode.id;
		if(parentId)
		url += "?filter_INS_inventoryType.id=" + parentId;
		url = encodeURI(url);
		jQuery("#" + gridId).jqGrid('setGridParam', {
			url : url,
			page : 1
		}).trigger("reloadGrid"); 
	}
</script>
<div class="page">
	<div id="inventoryDict_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="inventoryDict_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.inventoryType'/>:
							<input type="hidden" id="search_inventoryDict_invType_id" />
							<input type="text" id="search_inventoryDict_invType"  />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invCode'/>:
						 	<input type="text"  id="search_inventoryDict_invCode" />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invName'/>:
						 	<input type="text"  id="search_inventoryDict_invName" />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.agent'/>:
						 	<input type="text"  id="search_inventoryDict_agent" />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.factory'/>:
						 	<input type="text"  id="search_inventoryDict_factory" />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.isHighValue'/>:
						 	<select id="search_inventoryDict_isHighValue" >
						 	<option></option>
						 	<option value="1">是</option>
						 	<option value="0">不是</option>
						 	</select>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invUse'/>:
						 	<s:select theme="simple" list="invUsedList"  listValue="invUseName" listKey="invUseId"   
						 		id="search_inventoryDict_invUse" key="inventoryDict.invUse" 
						 		name="inventoryDict.invUse.invUseId" emptyOption="true"></s:select>
						</label>&nbsp;&nbsp;
						<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="inventoryDictGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
					</form>
					
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="inventoryDictGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('inventoryDict_gridtable','com.huge.ihos.material.model.InventoryDict')"><s:text name='button.higher'/></button>
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
				<li>
					<a id="inventoryDict_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="inventoryDict_gridtable_copy" class="addbutton"  href="javaScript:"><span><s:text name="button.copy" /></span></a>
				</li>
				<li>
					<a id="inventoryDict_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a id="inventoryDict_gridtable_batchEdit" class="changebutton"  href="javaScript:"><span><s:text name="button.batchEdit" /></span></a>
				</li>
				<li>
					<a id="inventoryDict_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a  class="delbutton"  href="javaScript:setColShow('inventoryDict_gridtable','com.huge.ihos.material.model.InventoryDict')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div id="inventoryDict_container">
			<div id="inventoryDict_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div id="inventoryTypeTreeLeft" class="ztree"></div>
			</div>
			<div id="inventoryDict_layout-center" class="pane ui-layout-center">
				<div id="inventoryDict_gridtable_div" layoutH="120" class="grid-wrapdiv" class="unitBox"
					style="margin-left: 1px; margin-top: 1px; overflow: hidden"
					buttonBar="optId:invId;width:900;height:550">
					<input type="hidden" id="inventoryDict_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="inventoryDict_gridtable_addTile">
						<s:text name="inventoryDictNew.title"/>
					</label> 
					<label style="display: none"
						id="inventoryDict_gridtable_editTile">
						<s:text name="inventoryDictEdit.title"/>
					</label>
					<label style="display: none"
						id="inventoryDict_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="inventoryDict_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_inventoryDict_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					 <table id="inventoryDict_gridtable"></table>
					<div id="inventoryDictPager"></div>
				</div>
				<div class="panelBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="inventoryDict_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="inventoryDict_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="inventoryDict_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>
			
				</div>
			</div>
		</div>
	</div>
</div>