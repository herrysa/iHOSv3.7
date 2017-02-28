
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var bugetSubjGridIdString="#bugetSubj_gridtable";
		var bugetSubjGrid = jQuery(bugetSubjGridIdString);
		var bugetSubjFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('bugetSubj_container','bugetSubj_gridtable',bugetSubjFullSize);
		var ztreesetting_bugetSubj = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onBugetSubjClick
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
		function onBugetSubjClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "bugetSubjGridList";
			if(treeNode.id!="-1"){
				if(treeNode.children){
			    	var ids=treeNode.id;
			    	ids = findChildrenNode(ids,treeNode);
			    	urlString=urlString+"?filter_INS_bugetSubjId="+ids;	
				}else{
					urlString=urlString+"?filter_EQS_bugetSubjId="+treeNode.id;	
				}
			}
			//alert(urlString);
		    urlString = encodeURI(urlString);
			jQuery(bugetSubjGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
		$.get("getBudgetSubjTree", {
			"_" : $.now()
		}, function(data) {
			var bugetSubjTreeData = data.bugetSubjTreeNodes;
			var bugetSubjTree = $.fn.zTree.init($("#bugetSubjTreeLeft"),
					ztreesetting_bugetSubj, bugetSubjTreeData);
			var nodes = bugetSubjTree.getNodes();
			bugetSubjTree.expandNode(nodes[0], true, false, true);
			bugetSubjTree.selectNode(nodes[0]);
			
		});
		jQuery("#bugetSubj_expandTree").text("展开");
    	bugetSubjGrid.jqGrid({
    		url : "bugetSubjGridList",
    		editurl:"bugetSubjGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'bugetSubjId',index:'bugetSubjId',align:'left',label : '<s:text name="bugetSubj.bugetSubjId" />',hidden:true,key:true},
			{name:'bugetSubjCode',index:'bugetSubjCode',align:'left',label : '<s:text name="bugetSubj.bugetSubjCode" />',hidden:false,width:100},
			{name:'bugetSubjName',index:'bugetSubjName',align:'left',label : '<s:text name="bugetSubj.bugetSubjName" />',hidden:false,width:150},
			{name:'cnCode',index:'cnCode',align:'left',label : '<s:text name="bugetSubj.cnCode" />',hidden:false,width:100},
			{name:'subjTypeCode',index:'subjTypeCode',align:'left',label : '<s:text name="bugetSubj.subjTypeCode" />',hidden:true,width:100},
			{name:'subjTypeName',index:'subjTypeName',align:'left',label : '<s:text name="bugetSubj.subjTypeCode" />',hidden:false,width:100},
			{name:'centralDeptId.name',index:'centralDeptId.name',align:'left',label : '<s:text name="bugetSubj.centralDeptId" />',hidden:false,width:150},
			{name:'ctrlPeriod',index:'ctrlPeriod',align:'left',label : '<s:text name="bugetSubj.ctrlPeriod" />',hidden:false,width:80},
			{name:'slevel',index:'slevel',align:'right',label : '<s:text name="bugetSubj.slevel" />',hidden:false,formatter:'integer',width:60},
			{name:'isCarry',index:'isCarry',align:'center',label : '<s:text name="bugetSubj.isCarry" />',hidden:false,formatter:'checkbox',width:80},
			{name:'isprocessctrl',index:'isprocessctrl',align:'center',label : '<s:text name="bugetSubj.isprocessctrl" />',hidden:false,formatter:'checkbox',width:80},
			{name:'leaf',index:'leaf',align:'center',label : '<s:text name="bugetSubj.leaf" />',hidden:false,formatter:'checkbox',width:80},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="bugetSubj.disabled" />',hidden:false,formatter:'checkbox',width:80}
			],
        	jsonReader : {
				root : "bugetSubjs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'bugetSubjCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bugetSubjList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"bugetSubj_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("bugetSubj_gridtable");
       		} 

    	});
    jQuery(bugetSubjGrid).jqGrid('bindKeys');
    jQuery("#bugetSubj_gridtable_add_c").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft"); 
        var nodes = zTree.getSelectedNodes();
        if(nodes.length!=1){
      		alertMsg.error("请选择上级预算科目。");
        	return;
     	}
        var rowData = jQuery("#bugetSubj_gridtable").jqGrid('getRowData',nodes[0].id);
        if(rowData.leaf=='Yes'){
        	alertMsg.error("末级预算科目不能添加下级预算科目。");
        	return;
        }
        var url = "editBugetSubj?navTabId=bugetSubj_gridtable&parentId="+nodes[0].id;
		var winTitle='<s:text name="bugetSubjNew.title"/>';
		$.pdialog.open(url,'addbugetSubj',winTitle, {mask:true,width : 500,height : 450});
    });
    
    jQuery("#bugetSubj_gridtable_edit_c").click(function(){
    	var sid = jQuery("#bugetSubj_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	if(sid.length>1){
    		alertMsg.error("只能选择一条记录！");
    		return;
    	}
        var url = "editBugetSubj?navTabId=bugetSubj_gridtable&bugetSubjId="+sid;
		var winTitle='<s:text name="bugetSubjEdit.title"/>';
		$.pdialog.open(url,'editbugetSubj',winTitle, {mask:true,width : 500,height : 450});
    });
    
    jQuery("#bugetSubj_gridtable_del_c").click(function(){
    	var sid = jQuery("#bugetSubj_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bugetSubj_gridtable").jqGrid('getRowData',id);
			var disabled = rowData["disabled"];
			if(disabled!='Yes'){
				alertMsg.error("只能删除停用的科目。");
				return;
			}
		}
    	$.post("bugetSubjGridEdit", {
    		"_" : $.now(),id:sid,oper:'del',navTabId:'bugetSubj_gridtable'
		}, function(data) {
			formCallBack(data);
			var zTree = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft"); 
			for(var i in sid){
				var oldNode = zTree.getNodeByParam("id", sid[i], null);
				zTree.removeNode(oldNode);
			}
		});
    });
  	});
	function bugetSubjReload(){
		var zTree = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft");
		var treeNodes = zTree.getSelectedNodes();
		if(treeNodes.length==0){
			return ;
		}
		var treeNode = treeNodes[0];
		var nodeId = treeNode.id;
		var urlString = "bugetSubjGridList";
		if(treeNode.children){
	    	var ids=treeNode.id;
	    	$.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	});
	    	urlString=urlString+"?filter_INS_bugetSubjId="+ids;	
		}else{
			urlString=urlString+"?filter_INS_bugetSubjId="+treeNode.id;	
		}
	    urlString = encodeURI(urlString);
		jQuery("#bugetSubj_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="bugetSubj_page">
	<div id="bugetSubj_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bugetSubj_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.bugetSubjCode'/>:
						<input type="text" name="filter_LIKES_bugetSubjCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.bugetSubjName'/>:
						<input type="text" name="filter_LIKES_bugetSubjName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.subjTypeCode'/>:
						<s:hidden id="bugetSubj_subjTypeCode_id" name="filter_EQS_subjTypeCode"/>
						<s:textfield id="bugetSubj_subjTypeCode" name="_exclude_bugetSubj_subjTypeCode"/>
						<script>
						jQuery("#bugetSubj_subjTypeCode").treeselect({
							optType : "single",
							dataType : 'sql',
							sql : "SELECT AccttypeId id,Accttype name,'1' parent FROM GL_accountType ORDER BY Accttypecode asc",
							exceptnullparent : true,
							lazy : false,
							selectParent : false,
							callback : {
							}
						});
						</script>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.centralDeptId'/>:
						<s:hidden id="bugetSubj_centralDeptId_id" name="filter_EQS_centralDeptId.departmentId"/>
						<s:textfield id="bugetSubj_centralDeptId" name="_exclude_centralDeptId" />
							<script>
							jQuery("#bugetSubj_centralDeptId").treeselect({
								optType : "single",
								dataType : 'sql',
								sql : "select deptId id,name,parentDept_id parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_bm_department where ysleaf=1 and disabled=0 and deptId <> 'XT'",
								exceptnullparent : true,
								lazy : false,
								minWidth : '280px',
								selectParent : false,
								callback : {
								}
							});
							</script>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.orgCode'/>:
						<input type="text" name="filter_EQS_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.copyCode'/>:
						<input type="text" name="filter_EQS_copyCode"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.ctrlPeriod'/>:
						<s:select list="#{'年度':'年度','季度':'季度','月度':'月度'}" name="filter_EQS_ctrlPeriod" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.isCarry'/>:
						<input type="text" name="filter_EQS_isCarry"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.isprocessctrl'/>:
						<input type="text" name="filter_EQS_isprocessctrl"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.slevel'/>:
						<input type="text" name="filter_EQI_slevel" size="8"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.leaf'/>:
						<s:select list="#{'1':'是','0':'否'}" name="filter_EQB_leaf" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.disabled'/>:
						<s:select list="#{'1':'是','0':'否'}" name="filter_EQB_disabled" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bugetSubj_search_form','bugetSubj_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="bugetSubj_buttonBar">
			<ul class="toolBar">
				<li><a id="bugetSubj_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="bugetSubj_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bugetSubj_gridtable_edit_c" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="bugetSubj_container">
		<div id="bugetSubj_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
		<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('bugetSubjTreeLeft',this)"/>
					</span>
				</div>
			<a style="position: relative; float: right;top:5px" id="bugetSubj_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#bugetSubj_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var bugetSubjTee = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft");
					if(thisText=="展开"){
						bugetSubjTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						bugetSubjTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="bugetSubjTreeLeft" class="ztree"></div>
		</div>
		<div id="bugetSubj_layout-center" class="pane ui-layout-center">
		<div id="bugetSubj_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:600;height:400">
			<input type="hidden" id="bugetSubj_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bugetSubj_gridtable_addTile">
				<s:text name="bugetSubjNew.title"/>
			</label> 
			<label style="display: none"
				id="bugetSubj_gridtable_editTile">
				<s:text name="bugetSubjEdit.title"/>
			</label>
			<div id="load_bugetSubj_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bugetSubj_gridtable"></table>
			<!--<div id="bugetSubjPager"></div>-->
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bugetSubj_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bugetSubj_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bugetSubj_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	</div>
	</div>
</div>