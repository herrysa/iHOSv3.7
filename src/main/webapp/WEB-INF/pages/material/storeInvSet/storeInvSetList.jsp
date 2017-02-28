
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var storeInvSetGridIdString="#storeInvSet_gridtable";
	
	jQuery(document).ready(function() { 
		var storeInvSetFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#storeInvSet_container").css("height",storeInvSetFullSize);
		$('#storeInvSet_container').layout({ 
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
					gridResize(null,null,"storeInvSet_gridtable","single");
				}
			}
		});
		initStoreLeftTreeInSet();
var storeInvSetGrid = jQuery(storeInvSetGridIdString);
    storeInvSetGrid.jqGrid({
    	url : "storeInvSetGridList?filter_INS_store.id=-1",
    	editurl:"storeInvSetGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'storeInvSetId',index:'storeInvSetId',align:'center',label : '<s:text name="storeInvSet.storeInvSetId" />',hidden:true,key:true},
/* {name:'inventoryDict.invId',index:'inventoryDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true},	
 */{name:'inventoryDict.invCode',index:'inventoryDict.invCode',width:80,align:'left',label : '<s:text name="inventoryDict.invCode" />',hidden:false},				
{name:'inventoryDict.invName',index:'inventoryDict.invName',align:'left',label : '<s:text name="inventoryDict.invName" />',hidden:false},				
{name:'inventoryDict.inventoryType.invTypeName',index:'inventoryDict.inventoryType.invTypeName',width:100,align:'left',sortable:false,label : '<s:text name="inventoryType.invTypeName" />',hidden:false},				
{name:'inventoryDict.invModel',index:'inventoryDict.invModel',width:150,align:'left',label : '<s:text name="inventoryDict.invModel" />',hidden:false,key:true},				
{name:'inventoryDict.vendor.vendorName',index:'inventoryDict.vendor.vendorName',width:150,align:'left',sortable:false,label : '<s:text name="inventoryDict.vendor" />',hidden:false},				
{name:'store.storeName',index:'store.storeName',align:'center',label : '<s:text name="所在仓库" />',hidden:true}
/*{name:'highLimit',index:'highLimit',align:'center',label : '<s:text name="storeInvSet.highLimit" />',hidden:false,formatter:'number'},				
{name:'lowLimit',index:'lowLimit',align:'center',label : '<s:text name="storeInvSet.lowLimit" />',hidden:false,formatter:'number'},				
{name:'safestock',index:'safestock',align:'center',label : '<s:text name="storeInvSet.safestock" />',hidden:false,formatter:'number'},				
 */
        ],
        jsonReader : {
			root : "storeInvSets", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'inventoryDict.invCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="storeInvSetList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"storeInvSet_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("storeInvSet_gridtable");
       	} 

    });
    	jQuery(storeInvSetGrid).jqGrid('bindKeys');
    	treeSelect_invTypeInStoreInvSet(jQuery("#storeInvSet_search_inventoryType"));
    	combogrid_vendorInStoreInvSet("storeInvSet_search_vendor");
	 	
  	});
	function initStoreLeftTreeInSet() {
		$.get("makeStoreTree?disabled=disabled", {
			"_" : $.now()
		}, function(data) {
			var storeTreeData = data.storeTree;
			var storeTree = $.fn.zTree.init($("#storeTreeLeftInSet"),
					ztreesetting_storeLeftInSet, storeTreeData);
			storeTree.expandAll(true);
		});
	}
	function treeSelect_invTypeInStoreInvSet(obj){
		var orgForStoreInvSet = "${fns:userContextParam('orgCode')}";
		var copyForStoreInvSet = "${fns:userContextParam('copyCode')}";
	 	obj.treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled =0 and orgCode='"+orgForStoreInvSet+"' and copyCode='"+copyForStoreInvSet+"'",
			exceptnullparent:false,
			lazy:false
		});
	}
	
	function combogrid_vendorInStoreInvSet(str){
		jQuery("#"+str).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams:{
				sql : "select VENDORID,VENDORCODE,VENDORNAME from sy_vendor where disabled=0 and isMate = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
				cloumns : 'VENDORCODE,VENDORNAME,CNCODE'
			},
			sidx:"VENDORCODE",
			colModel : [
			{
				'columnName' : 'VENDORCODE',
				'width' : '30',
				'align' : 'left',
				'label' : '编码'
			},
			{
				'columnName' : 'VENDORNAME',
				'width' : '60',
				'align' : 'left',
				'label' : '名称'
			}],
			select : function(event, ui) {
				$(this).val(ui.item.VENDORNAME);
				jQuery("#"+str+"_id").val(ui.item.VENDORID);
				return false;
			}
		});
	}
	
	var ztreesetting_storeLeftInSet = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick:reloadStoreInvSetGrid
			},
			data : {
				key : {
					name : "storeName"
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
	var selectStoreInvSetTreeId="",selectStoreInvSetIds="";
	function reloadStoreInvSetGrid(e, treeId, treeNode){
		/* if(treeNode.isParent){
			return;
		} */
		var nodeId = treeNode.id;
		selectStoreInvSetTreeId = nodeId;
		var reloadUrlString = "storeInvSetGridList";
		reloadUrlString += "?filter_INS_store.id="+nodeId;
		reloadUrlString=encodeURI(reloadUrlString);
		//alert(reloadUrlString);
		jQuery("#storeInvSet_gridtable").jqGrid('setGridParam',{url:reloadUrlString,page:1}).trigger("reloadGrid");
	}
	function getStoreInvSetGridSearchFilter(){
		var invTypeId = jQuery("#storeInvSet_search_inventoryType_id").val();
		var invName = jQuery("#storeInvSet_search_invName").val();
		var vendId = jQuery("#storeInvSet_search_vendor_id").val();
		var filterUrl = "&filter_EQS_inventoryDict.inventoryType.id="+invTypeId;
		filterUrl += "&filter_LIKES_inventoryDict.invName="+invName;
		filterUrl += "&filter_EQS_inventoryDict.vendor.vendorId="+vendId;
		return filterUrl;
	}
	function reloadStoreInvSetGridBySearchFilter(){
		
		var reloadUrlString = "storeInvSetGridList?filter_EQS_store.id="+selectStoreInvSetTreeId;
		reloadUrlString += getStoreInvSetGridSearchFilter();
		reloadUrlString=encodeURI(reloadUrlString);
		//alert(reloadUrlString);
		jQuery("#storeInvSet_gridtable").jqGrid('setGridParam',{url:reloadUrlString,page:1}).trigger("reloadGrid");
		
	}
	function checkStoreTree(){
		var storeTreeObj = $.fn.zTree.getZTreeObj("storeTreeLeftInSet");
		var nodes = storeTreeObj.getSelectedNodes();
		if(nodes.length==0){
			alertMsg.error("请选择仓库");
			return false;
		}else if(nodes[0].isParent){
			alertMsg.error("请选择末级仓库");
			return false;
		}
		return true;
	}
	
	function addStoreInvSet(){
		if(!checkStoreTree()){
			return;
		};
		
		var edit_URL = "editStoreInvSet" , tableId = "storeInvSet_gridtable" , width=998 , height = 628;
		var url = edit_URL+"?popup=true";
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'addStoreInvSet', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	} 
	
	function delStoreInvSetAll(){
		if(!checkStoreTree()){
			return;
		};
		var allIDs = jQuery("#storeInvSet_gridtable").getDataIDs();
		if(allIDs.length==0){
			alertMsg.info("没有可删除的记录!");
			return;
		}
		alertMsg.confirm("确认删除全部记录？", {
			okCall: function(){
				var delAllUrl = "deleteStoreInvSetAll?filter_EQS_store.id="+selectStoreInvSetTreeId;
				var filterUrl = getStoreInvSetGridSearchFilter();
				delAllUrl += filterUrl;
				delAllUrl = encodeURI(delAllUrl);
				$.ajax({
					type:'post',
					url:delAllUrl,
					async:false,
					success:function(){
						var delReUrlString = "storeInvSetGridList?filter_EQS_store.id="+selectStoreInvSetTreeId;
						delReUrlString += filterUrl;
						delReUrlString = encodeURI(delReUrlString);
						jQuery("#storeInvSet_gridtable").jqGrid('setGridParam',{url:delReUrlString,page:1}).trigger("reloadGrid");
						alertMsg.correct("当前仓库材料删除成功!");
					}
				});
			}
		});
	}
</script>

<div class="page">
	
	<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="storeInvSet_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.inventoryType'/>:
							<input id="storeInvSet_search_inventoryType" type="text"/>
							<input id="storeInvSet_search_inventoryType_id" type="hidden" name="filter_EQS_inventoryDict.inventoryType.id"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.invName'/>:
							<input id="storeInvSet_search_invName" type="text" name="filter_LIKES_inventoryDict.invName"/>
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.vendor'/>:
							<input id="storeInvSet_search_vendor" type="text"  value="拼音/汉字/编码" size="14"
							class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#storeInvSet_search_vendor_id'))" 
							onfocus="clearInput(this,jQuery('#storeInvSet_search_vendor_id'))" onkeyDown="setTextColor(this)"/>
							<s:hidden id="storeInvSet_search_vendor_id" name="filter_EQS_inventoryDict.vendor.vendorId"/>
						</label>&nbsp;&nbsp;
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<%--  <button type="button" onclick="propertyFilterSearch('storeInvSet_search_form','storeInvSet_gridtable')"><s:text name='button.search'/></button>
									 --%> <button type="button" onclick="reloadStoreInvSetGridBySearchFilter()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="storeInvSet_gridtable_addLocal" class="addbutton" onclick="addStoreInvSet()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="storeInvSet_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				<li>
					<a id="storeInvSet_gridtable_delAll" class="delallbutton"  onclick="delStoreInvSetAll()"><span><s:text name="全部删除" /></span></a>
				</li>
				
			
			</ul>
		</div>
		<div id="storeInvSet_container" >
			<div id="storeInvSet_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div id="storeTreeLeftInSet" class="ztree"></div>
			</div>
			<div id="storeInvSet_layout-center" class="pane ui-layout-center" >
				<div id="storeInvSet_gridtable_div" layoutH="124" class="grid-wrapdiv" class="unitBox"  
					buttonBar="optId:storeInvSetId;width:500;height:300">
					<input type="hidden" id="storeInvSet_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="storeInvSet_gridtable_addTile">
						<s:text name="storeInvSetNew.title"/>
					</label> 
					<label style="display: none"
						id="storeInvSet_gridtable_editTile">
						<s:text name="storeInvSetEdit.title"/>
					</label>
					<label style="display: none"
						id="storeInvSet_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="storeInvSet_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_storeInvSet_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					 <table id="storeInvSet_gridtable"></table>
					<div id="storeInvSetPager"></div>
				</div>
				<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="storeInvSet_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="storeInvSet_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
		
				<div id="storeInvSet_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>
		
			</div>
			</div>
			
		</div>
	</div>
</div>