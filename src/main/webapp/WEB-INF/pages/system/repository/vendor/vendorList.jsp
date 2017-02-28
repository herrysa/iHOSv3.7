
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var vendorGridIdString="#vendor_gridtable";
	var orgForVendorType = "${fns:userContextParam('orgCode')}";
	if(!orgForVendorType){
		orgForVendorType = "${fns:userContextParam('loginOrgId')}";
	}
	var vendorTypeTreeLeftObj;
	jQuery(document).ready(function() {
		var initFlag = 0;
		var vendorFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#vendor_container").css("height",vendorFullSize);
		$('#vendor_container').layout({ 
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
						gridResize(null,null,"vendor_gridtable","single");
					}
				}
		});
		$.get("makeVendorTypeTree?disabled=disabled",{ "_": $.now() },function(data) {
			var vendorTypeTreeData = data.vendorTypeTreeList;
			vendorTypeTreeLeftObj = $.fn.zTree.init($("#vendorTypeTreeLeft"), ztreesetting_vendorTypeLeft,vendorTypeTreeData);
			var nodes = vendorTypeTreeLeftObj.getNodes();
			vendorTypeTreeLeftObj.expandNode(nodes[0], true, false, true);
		});
		jQuery("#vendor_vendorType_search").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT  vendorTypeId id,vendorTypeName name,parent_id parent FROM sy_vendorType where parent_id is not null and disabled = 0 and orgCode='"+orgForVendorType+"'",
			exceptnullparent:false,
			lazy:false
		});
		
		var vendorGrid = jQuery(vendorGridIdString);
	    vendorGrid.jqGrid({
	    	url : "vendorGridList",
	    	editurl:"vendorGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
	{name:'vendorId',index:'vendorId',align:'center',label : '<s:text name="vendor.vendorId" />',hidden:true,key:true},	
	{name:'vendorCode',index:'vendorCode',align:'left',width:100,label : '<s:text name="vendor.vendorCode" />',hidden:false,highsearch:true},				
	{name:'vendorName',index:'vendorName',align:'left',width:240,label : '<s:text name="vendor.vendorName" />',hidden:false,highsearch:true},
	{name:'cnCode',index:'cnCode',align:'left',label : '<s:text name="vendor.cnCode" />',hidden:false,highsearch:true},				
	{name:'province',index:'province',align:'left',label : '<s:text name="vendor.province" />',hidden:false,highsearch:true},				
	{name:'city',index:'city',align:'left',label : '<s:text name="vendor.city" />',hidden:false,highsearch:true},	
	{name:'venPerson',index:'venPerson',align:'left',label : '<s:text name="vendor.venPerson" />',hidden:false,highsearch:true},				
	{name:'venPhone',index:'venPhone',align:'left',label : '<s:text name="vendor.venPhone" />',hidden:false,highsearch:true},				
	{name:'venMobile',index:'venMobile',align:'left',label : '<s:text name="vendor.venMobile" />',hidden:false,highsearch:true},				
	{name:'disabled',index:'disabled',align:'center',label : '<s:text name="vendor.disabled" />',hidden:false,highsearch:true,formatter:'checkbox'},				
	{name:'isDrug',index:'isDrug',align:'center',label : '<s:text name="vendor.isDrug" />',hidden:false,highsearch:true,formatter:'checkbox'},				
	{name:'isEquip',index:'isEquip',align:'center',label : '<s:text name="vendor.isEquip" />',hidden:false,highsearch:true,formatter:'checkbox'},				
	{name:'isImma',index:'isImma',align:'center',label : '<s:text name="vendor.isImma" />',hidden:false,highsearch:true,formatter:'checkbox'},				
	{name:'isMate',index:'isMate',align:'center',label : '<s:text name="vendor.isMate" />',hidden:false,highsearch:true,formatter:'checkbox'}
	
	/* {name:'accPayMoney',index:'accPayMoney',align:'center',label : '<s:text name="vendor.accPayMoney" />',hidden:false,formatter:'number'},				
	{name:'businessCharter',index:'businessCharter',align:'center',label : '<s:text name="vendor.businessCharter" />',hidden:false},				
	{name:'bvenTax',index:'bvenTax',align:'center',label : '<s:text name="vendor.bvenTax" />',hidden:false,formatter:'checkbox'},				
	{name:'distCode',index:'distCode',align:'center',label : '<s:text name="vendor.distCode" />',hidden:false},				
	{name:'endDate',index:'endDate',align:'center',label : '<s:text name="vendor.endDate" />',hidden:false,formatter:'date'},				
	{name:'frequency',index:'frequency',align:'center',label : '<s:text name="vendor.frequency" />',hidden:false,formatter:'integer'},				
	{name:'lastDate',index:'lastDate',align:'center',label : '<s:text name="vendor.lastDate" />',hidden:false,formatter:'date'},				
	{name:'lastMoney',index:'lastMoney',align:'center',label : '<s:text name="vendor.lastMoney" />',hidden:false,formatter:'number'},				
	{name:'lastPayDate',index:'lastPayDate',align:'center',label : '<s:text name="vendor.lastPayDate" />',hidden:false,formatter:'date'},				
	{name:'lastPayMoney',index:'lastPayMoney',align:'center',label : '<s:text name="vendor.lastPayMoney" />',hidden:false,formatter:'number'},				
	{name:'managedeptId',index:'managedeptId',align:'center',label : '<s:text name="vendor.managedeptId" />',hidden:false},				
	{name:'payCode',index:'payCode',align:'center',label : '<s:text name="vendor.payCode" />',hidden:false,formatter:'integer'},				
	{name:'products',index:'products',align:'center',label : '<s:text name="vendor.products" />',hidden:false},				
	{name:'shortname',index:'shortname',align:'center',label : '<s:text name="vendor.shortname" />',hidden:false},				
	{name:'trafCode',index:'trafCode',align:'center',label : '<s:text name="vendor.trafCode" />',hidden:false,formatter:'integer'},				
	{name:'venAdress',index:'venAdress',align:'center',label : '<s:text name="vendor.venAdress" />',hidden:false},				
	{name:'venBank',index:'venBank',align:'center',label : '<s:text name="vendor.venBank" />',hidden:false},				
	{name:'venBankAccount',index:'venBankAccount',align:'center',label : '<s:text name="vendor.venBankAccount" />',hidden:false},				
	{name:'venCreDate',index:'venCreDate',align:'center',label : '<s:text name="vendor.venCreDate" />',hidden:false,formatter:'integer'},				
	{name:'venCreGrade',index:'venCreGrade',align:'center',label : '<s:text name="vendor.venCreGrade" />',hidden:false},				
	{name:'venCreLine',index:'venCreLine',align:'center',label : '<s:text name="vendor.venCreLine" />',hidden:false,formatter:'integer'},				
	{name:'venDevDate',index:'venDevDate',align:'center',label : '<s:text name="vendor.venDevDate" />',hidden:false,formatter:'date'},				
	{name:'venDirAddress',index:'venDirAddress',align:'center',label : '<s:text name="vendor.venDirAddress" />',hidden:false},				
	{name:'venDirCode',index:'venDirCode',align:'center',label : '<s:text name="vendor.venDirCode" />',hidden:false,formatter:'integer'},				
	{name:'venDisRate',index:'venDisRate',align:'center',label : '<s:text name="vendor.venDisRate" />',hidden:false,formatter:'integer'},				
	{name:'venEmail',index:'venEmail',align:'center',label : '<s:text name="vendor.venEmail" />',hidden:false},				
	{name:'venFax',index:'venFax',align:'center',label : '<s:text name="vendor.venFax" />',hidden:false},				
	{name:'venLperson',index:'venLperson',align:'center',label : '<s:text name="vendor.venLperson" />',hidden:false},				
	{name:'venPayCon',index:'venPayCon',align:'center',label : '<s:text name="vendor.venPayCon" />',hidden:false},				
	{name:'venPostcode',index:'venPostcode',align:'center',label : '<s:text name="vendor.venPostcode" />',hidden:false},				
	{name:'venPperson',index:'venPperson',align:'center',label : '<s:text name="vendor.venPperson" />',hidden:false},				
	{name:'venRegCode',index:'venRegCode',align:'center',label : '<s:text name="vendor.venRegCode" />',hidden:false},				
	{name:'venTrade',index:'venTrade',align:'center',label : '<s:text name="vendor.venTrade" />',hidden:false},				
	{name:'vendorType',index:'vendorType',align:'center',label : '<s:text name="vendor.vendorType" />',hidden:false} */
	        ],
	        jsonReader : {
				root : "vendors", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'vendorCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="vendorList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			 gridComplete:function(){
	           if(jQuery(this).getDataIDs().length>0){
	              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"vendor_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("vendor_gridtable");
	      	   initFlag = initColumn('vendor_gridtable','com.huge.ihos.vendor.model.Vendor',initFlag);
	       	} 

	    });
	    jQuery(vendorGrid).jqGrid('bindKeys');
	});
	var ztreesetting_vendorTypeLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick:reloadVendorGrid
			},
			data : {
				key : {
					name : "vendorTypeName"
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
	function reloadVendorGrid(e, treeId, treeNode){
		var gridId = "vendor_gridtable";
		var url = "${ctx}/vendorGridList";
		var parentId = treeNode.id;
		
		if(parentId){
			if(parentId!="-1"){
				url += "?filter_INS_vendorType.id=" + parentId;
			}
		}
		url = encodeURI(url);
		jQuery("#" + gridId).jqGrid('setGridParam', {
			url : url,
			page : 1
		}).trigger("reloadGrid"); 
	}
	function addVendor(){
		var vendorTypeId="";
		var nodes = vendorTypeTreeLeftObj.getSelectedNodes();
		if (nodes &&  nodes.length > 0) {
			node = nodes[0];
			vendorTypeId = node.id;
			if (node.leaf == false) {
				//alertMsg.error("当前节点非末级，不能为其添加供应商字典！");
				//return;
				vendorTypeId = "";
			}
		}
		var url = "editVendor?popup=true&navTabId=vendor_gridtable&vendorTypeId="+vendorTypeId;
		var winTitle = "添加供应商";
		url = encodeURI(url);
		$.pdialog.open(url,'addVendor',winTitle, {mask : false,width : 620,height : 520});
	}
	
</script>

<div class="page">
	<div id="vendor_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="vendor_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='供应商类别'/>:
						<input id="vendor_vendorType_search" type="text" name=""/>
						<input id="vendor_vendorType_search_id" type="hidden" name="filter_EQS_vendorType.id"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='vendor.vendorCode'/>:
						<input id="vendorCode_search" type="text" name="filter_EQS_vendorCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='vendor.vendorName'/>:
						<input id="vendorName_search" type="text" name="filter_LIKES_vendorName"/>
					</label>&nbsp;&nbsp;
				</form>
				</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('vendor_search_form','vendor_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div></li>
				
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="vendor_gridtable_add_custom" class="addbutton" onclick="addVendor()"><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="vendor_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="vendor_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" />
					</span>
				</a>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('vendor_gridtable','com.huge.ihos.vendor.model.Vendor')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				</li>
			
			</ul>
		</div>
		<div id="vendor_container">
			<div id="vendor_layout-center" class="pane ui-layout-center">
				<div id="vendor_gridtable_div" layoutH="120" class="grid-wrapdiv" class="unitBox"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden"
					buttonBar="optId:vendorId;width:730;height:520">
					<input type="hidden" id="vendor_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="vendor_gridtable_addTile">
						<s:text name="vendorNew.title"/>
					</label> 
					<label style="display: none"
						id="vendor_gridtable_editTile">
						<s:text name="vendorEdit.title"/>
					</label>
					<label style="display: none"
						id="vendor_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="vendor_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_vendor_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					 <table id="vendor_gridtable"></table>
						<div id="vendorPager"></div>
				</div>
				<div class="panelBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="vendor_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="vendor_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="vendor_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>
				</div>
			</div>
			<div id="vendor_layout-west" class="pane ui-layout-west">
				<div id="vendorTypeTreeLeft" class="ztree"></div>
			</div>
		</div>
	</div>
	
</div>