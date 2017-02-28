
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var bugetAcctMapGridIdString="#bugetAcctMap_gridtable";
		var bugetAcctMapGrid = jQuery(bugetAcctMapGridIdString);
		var bugetAcctMapFullSize = jQuery("#container").innerHeight()-56;
		setLeftTreeLayout('bugetAcctMap_container','bugetAcctMap_gridtable',bugetAcctMapFullSize);
		var ztreesetting_bugetAcctMap = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onBugetAcctMapClick
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
		function onBugetAcctMapClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "bugetAcctMapGridList";
			if(treeNode.id!="-1"){
				if(treeNode.children){
			    	var ids=treeNode.id;
			    	ids = findChildrenNode(ids,treeNode);
			    	urlString=urlString+"?filter_INS_bmSubjId.bugetSubjId="+ids;	
				}else{
					urlString=urlString+"?filter_EQS_bmSubjId.bugetSubjId="+treeNode.id;	
				}
			}
		    urlString = encodeURI(urlString);
			jQuery(bugetAcctMapGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		function findChildrenNode(ids,treeNode){
			$.each(treeNode.children,function(n,value) {  
				var n = treeNode.children[n];
	    		 ids+=","+n.id;
	    		 if(n.children&&n.children.length>0){
	    			 ids = findChildrenNode(ids,n);
	    		 }
	    	});
			return ids;
		}
		$.get("getBudgetSubjTree?filter_EQB_disabled=false", {
			"_" : $.now()
		}, function(data) {
			var bugetSubjTreeNodes = data.bugetSubjTreeNodes;
			var bugetAcctMapTree = $.fn.zTree.init($("#bugetAcctMapTreeLeft"),
					ztreesetting_bugetAcctMap, bugetSubjTreeNodes);
			var nodes = bugetAcctMapTree.getNodes();
			bugetAcctMapTree.expandNode(nodes[0], true, false, true);
			bugetAcctMapTree.selectNode(nodes[0]);
			
		});
		jQuery("#bugetAcctMap_expandTree").text("展开");
    	bugetAcctMapGrid.jqGrid({
    		url : "bugetAcctMapGridList",
    		editurl:"bugetAcctMapGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'mapId',index:'mapId',align:'center',label : '<s:text name="bugetAcctMap.mapId" />',hidden:true,key:true},
			{name:'bmSubjId.bugetSubjId',index:'bmSubjId.bugetSubjId',align:'center',label : '<s:text name="bugetAcctMap.bmSubjId" />',hidden:true},
			{name:'bmSubjId.bugetSubjCode',index:'bmSubjId.bugetSubjCode',align:'center',label : '<s:text name="bmSubjId.bugetSubjCode" />',hidden:true},
			{name:'bmSubjId.bugetSubjName',index:'bmSubjId.bugetSubjName',align:'left',label : '<s:text name="bugetAcctMap.bmSubjId" />',hidden:false},
			{name:'bmSubjId.orgCode',index:'bmSubjId.orgCode',align:'left',label : '<s:text name="bugetAcctMap.bmSubjId" />',hidden:true},
			{name:'bmSubjId.copyCode',index:'bmSubjId.copyCode',align:'left',label : '<s:text name="bugetAcctMap.bmSubjId" />',hidden:true},
			{name:'acctId',index:'acctId',align:'center',label : '<s:text name="bugetAcctMap.acctId" />',hidden:true},
			{name:'acctCode',index:'acctCode',align:'left',label : '<s:text name="bugetAcctMap.acctCode" />',hidden:false,width:150},
			{name:'acctName',index:'acctName',align:'left',label : '<s:text name="bugetAcctMap.acctName" />',hidden:false,width:200,editable:true,editoptions : {dataInit : addAcctComboGridToBmSubj},sortable:false},
			{name:'addDirect',index:'addDirect',align:'center',label : '<s:text name="bugetAcctMap.addDirect" />',hidden:false,width:100,editable:true,formatter : 'select',	edittype : 'select',editoptions : {value : '借:借;贷:贷'}},
			{name:'subDirect',index:'subDirect',align:'center',label : '<s:text name="bugetAcctMap.subDirect" />',hidden:false,width:100,editable:true,formatter : 'select',	edittype : 'select',editoptions : {value : '借:借;贷:贷'}},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="bugetAcctMap.remark" />',hidden:false,width:250,editable:true}
			],
        	jsonReader : {
				root : "bugetAcctMaps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'bmSubjId.bugetSubjCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bugetAcctMapList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
			cellEdit : true,
			cellsubmit : 'clientArray',
			beforeEditCell :function(rowid, cellname, value, iRow, iCol){
				//alert(cellname);
			},
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"bugetAcctMap_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(bugetAcctMapGrid).jqGrid('bindKeys');
    jQuery("#bugetAcctMap_gridtable_init").click(function(){
    	$.post("initAcctMap", {
    		"_" : $.now(),navTabId:'bugetAcctMap_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    jQuery("#bugetAcctMap_gridtable_save").click(function(){
    	var grid = jQuery("#bugetAcctMap_gridtable");
    	var rowNum = grid.getDataIDs().length;
		var ret = grid.jqGrid('getRowData');
		/* var 
		if(rowNum > 0){
	    	 var rowIds = grid.getDataIDs();
	    	 var i=0;
	    	 for (i=0;i<rowNum;i++){
	    		var id = rowIds[i];
	    		var acctId = ret[i]["acctId"];
	    		var acctCode = ret[i]["acctCode"];
	    		var acctName = ret[i]["acctName"];
	    		var addDirect = ret[i]["addDirect"];
	    		var subDirect = ret[i]["subDirect"];
	    		var remark = ret[i]["remark"];
	    	 }
		} */
	    		
	    		
    	$.post("saveAcctMap", {
    		"_" : $.now(),navTabId:'bugetAcctMap_gridtable',gridData:JSON.stringify(ret)
		}, function(data) {
			formCallBack(data);
		});
    });
  	});
	function addAcctComboGridToBmSubj(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		
		var sql = "SELECT acctId , acctcode , acctname , cnCode from GL_account";
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql:sql,
				cloumns : 'acctcode,acctname,cnCode'
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:600,
			sidx:"ACCTCODE",
			colModel : [ {
				'columnName' : 'ACCTID',
				'width' : '0',
				'label' : 'ACCTID',
				hidden : true
			}, {
				'columnName' : 'ACCTCODE',
				'width' : '20',
				'align' : 'left',
				'label' : '会计科目编码'
			}, {
				'columnName' : 'ACCTNAME',
				'width' : '20',
				'align' : 'left',
				'label' : '会计科目名称'
			}, {
				'columnName' : 'CNCODE',
				'width' : '10',
				'align' : 'left',
				'label' : '拼音码'
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
				var thisTr = $(elem).parent().parent();
				var rowid = thisTr.attr("id");
				$(elem).attr("value",ui.item.ACCTNAME);
				jQuery("#bugetAcctMap_gridtable").setCell(rowid,"acctId",ui.item.ACCTID);
				jQuery("#bugetAcctMap_gridtable").setCell(rowid,"acctCode",ui.item.ACCTCODE);
			}
		});
	} 
</script>

<div class="page">
	<%-- <div id="bugetAcctMap_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bugetAcctMap_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.mapId'/>:
						<input type="text" name="filter_EQS_mapId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.acctCode'/>:
						<input type="text" name="filter_EQS_acctCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.acctId'/>:
						<input type="text" name="filter_EQS_acctId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.acctName'/>:
						<input type="text" name="filter_EQS_acctName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.addDirect'/>:
						<input type="text" name="filter_EQS_addDirect"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.bmSubjId'/>:
						<input type="text" name="filter_EQS_bmSubjId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetAcctMap.subDirect'/>:
						<input type="text" name="filter_EQS_subDirect"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(bugetAcctMap_search_form,bugetAcctMap_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(bugetAcctMap_search_form,bugetAcctMap_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bugetAcctMap_gridtable_init" class="initbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="bugetAcctMap_gridtable_save" class="savebutton" href="javaScript:" ><span>保存
					</span>
				</a>
				</li>
				<%-- <li><a id="bugetAcctMap_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bugetAcctMap_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="bugetAcctMap_container">
		<div id="bugetAcctMap_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
		<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('bugetAcctMapTreeLeft',this)"/>
					</span>
				</div>
			<a style="position: relative; float: right;top:5px" id="bugetAcctMap_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#bugetAcctMap_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var bugetAcctMapTee = $.fn.zTree.getZTreeObj("bugetAcctMapTreeLeft");
					if(thisText=="展开"){
						bugetAcctMapTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						bugetAcctMapTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="bugetAcctMapTreeLeft" class="ztree"></div>
		</div>
		<div id="bugetAcctMap_layout-center" class="pane ui-layout-center">
		<div id="bugetAcctMap_gridtable_div" layoutH="58" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bugetAcctMap_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bugetAcctMap_gridtable_addTile">
				<s:text name="bugetAcctMapNew.title"/>
			</label> 
			<label style="display: none"
				id="bugetAcctMap_gridtable_editTile">
				<s:text name="bugetAcctMapEdit.title"/>
			</label>
			<div id="load_bugetAcctMap_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bugetAcctMap_gridtable"></table>
			<!--<div id="bugetAcctMapPager"></div>-->
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bugetAcctMap_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bugetAcctMap_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bugetAcctMap_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
	</div>
</div>