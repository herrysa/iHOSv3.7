
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var workScoreLayout;
	var workScoreGridIdString="#workScore_gridtable";
	
	jQuery(document).ready(function() {
		var workScoreFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('workScore_container','workScore_gridtable',workScoreFullSize);
		var ztreesetting_workScore = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					onClick : onWorkScoreClick
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
		function onWorkScoreClick(event, treeId, treeNode, clickFlag) { 
			var nodeId = treeNode.id;
			var urlString = "workScoreGridList";
			if(treeNode.id!="-1"){
				if(treeNode.children){
			    	var ids=treeNode.id;
			    	ids = findChildrenNode(ids,treeNode);
			    	urlString=urlString+"?filter_INS_id="+ids;	
				}else{
					urlString=urlString+"?filter_EQS_id="+treeNode.id;	
				}
			}
		    urlString = encodeURI(urlString);
			jQuery(workScoreGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
		$.get("getWorkScoreTree", {
			"_" : $.now()
		}, function(data) {
			var workScoreTreeData = data.workScoreTreeNodes;
			var workScoreTree = $.fn.zTree.init($("#workScoreTreeLeft"),
					ztreesetting_workScore, workScoreTreeData);
			var nodes = workScoreTree.getNodes();
			workScoreTree.expandNode(nodes[0], true, false, true);
			workScoreTree.selectNode(nodes[0]);
			
			
		});
		jQuery("#gzPerson_expandTree").text("展开");
		var workScoreGrid = jQuery(workScoreGridIdString);
    	workScoreGrid.jqGrid({
    		url : "workScoreGridList",
    		editurl:"workScoreGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'id',index:'id',align:'left',label : '<s:text name="workScore.id" />',hidden:false,width:100,key:true},
			{name:'workName',index:'workName',align:'left',label : '<s:text name="workScore.workName" />',width:150,hidden:false},
			{name:'parentid.workName',index:'parentid.workName',align:'left',label : '<s:text name="workScore.parentid" />',hidden:false},
			{name:'clevel',index:'clevel',align:'right',label : '<s:text name="workScore.clevel" />',width:50,hidden:false,formatter:'integer'},
			{name:'workScore',index:'workScore',align:'right',label : '<s:text name="workScore.workScore" />',width:70,hidden:false,formatter:'integer'},
			{name:'workunit',index:'workunit',align:'center',label : '<s:text name="workScore.workunit" />',width:70,hidden:false},
			{name:'leaf',leaf:'disabled',align:'center',label : '<s:text name="workScore.leaf" />',width:50,hidden:false,formatter:'checkbox'},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="workScore.disabled" />',width:50,hidden:false,formatter:'checkbox'},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="workScore.remark" />',width:200,hidden:false}
			],
        	jsonReader : {
				root : "workScores", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'id',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="workScoreList.title" />',
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
           	  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	 }
           	gridContainerResize('workScore','layout');
           	var dataTest = {"id":"workScore_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(workScoreGrid).jqGrid('bindKeys');
    
    jQuery("#workScore_gridtable_add_c").click(function(){
    	var zTree = $.fn.zTree.getZTreeObj("workScoreTreeLeft"); 
        var nodes = zTree.getSelectedNodes();
        if(nodes.length!=1){
      		alertMsg.error("请选择上级工作量积分。");
        	return;
     	}
        var rowData = jQuery("#workScore_gridtable").jqGrid('getRowData',nodes[0].id);
        if(rowData.leaf=='Yes'){
        	alertMsg.error("末级工作量积分不能添加下级工作量积分。");
        	return;
        }
        var url = "editWorkScore?navTabId=workScore_gridtable&parentid="+nodes[0].id;
		var winTitle='<s:text name="workScoreNew.title"/>';
		$.pdialog.open(url,'addWorkScore',winTitle, {mask:true,width : 500,height : 400});
    });
    jQuery("#workScore_gridtable_edit_c").click(function(){
    	var sid = jQuery("#workScore_gridtable").jqGrid('getGridParam','selarrrow');
    	 if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 			}
 		var winTitle='<s:text name="workScoreEdit.title"/>';
 		var url = "editWorkScore?popup=true&id="+sid+"&navTabId=workScore_gridtable";
 		$.pdialog.open(url,'editWorkScore',winTitle, {mask:true,width : 500,height : 400});
    });
    jQuery("#workScore_gridtable_del_c").click(function(){
    	var sid = jQuery("#workScore_gridtable").jqGrid('getGridParam','selarrrow');
    	var url = "${ctx}/workScoreGridEdit?oper=del";
    	if(sid==null||sid.length==0){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 		}else{
 			url = url+"&id="+sid+"&navTabId=workScore_gridtable";
 			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						// delete 
						if(data.statusCode!=200){
						   return;
						  }
						 var workScoreTreeObj = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
						 for(var i=0;i<sid.length;i++){
						       var delId = sid[i];
						       var node = workScoreTreeObj.getNodeByParam("id", delId, null);
						       workScoreTreeObj.removeNode(node);
						  }
					});
				}
			});
 		}
    });
  	});
	
	function workScoreReload(){
		var zTree = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
		var treeNodes = zTree.getSelectedNodes();
		if(treeNodes.length==0){
			return ;
		}
		var treeNode = treeNodes[0];
		var nodeId = treeNode.id;
		var urlString = "workScoreGridList";
		if(treeNode.children){
	    	var ids=treeNode.id;
	    	$.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	});
	    	urlString=urlString+"?filter_INS_id="+ids;	
		}else{
			urlString=urlString+"?filter_EQS_id="+treeNode.id;	
		}
	    urlString = encodeURI(urlString);
		jQuery(workScoreGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="workScore_page">
	<div id="workScore_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="workScore_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.id'/>:
						<input type="text" name="filter_EQS_id"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.workName'/>:
						<input type="text" name="filter_EQS_workName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.clevel'/>:
						<input type="text" name="filter_EQI_clevel" size="5"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='workScore.parentid'/>:
						<input type="text" name="filter_EQS_parentid"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.disabled'/>:
						<select name="filter_EQB_disabled">
							<option value=""></option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.leaf'/>:
						<select name="filter_EQB_leaf">
							<option value=""></option>
							<option value="true">是</option>
							<option value="false">否</option>
						</select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='workScore.workScore'/>:
						<input type="text" name="filter_EQS_workScore"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='workScore.workunit'/>:
						<input type="text" name="filter_EQS_workunit"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('workScore_search_form','workScore_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
					
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="workScore_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="workScore_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="workScore_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="workScore_gridtable_edit_c" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="workScore_container">
		<div id="workScore_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			<a style="position: relative; float: right;top:5px" id="workScore_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#workScore_expandTree").click(function(){
					var thisText = jQuery(this).text();
					if(thisText=="展开"){
						var workScoreTee = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
						workScoreTee.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						var workScoreTee = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
						workScoreTee.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="workScoreTreeLeft" class="ztree"></div>
		</div>
		<div id="gzPerson_layout-center" class="pane ui-layout-center">
		<div id="workScore_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:400">
			<input type="hidden" id="workScore_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="workScore_gridtable_addTile">
				<s:text name="workScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="workScore_gridtable_editTile">
				<s:text name="workScoreEdit.title"/>
			</label>
			<div id="load_workScore_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="workScore_gridtable"></table>
			<!--<div id="workScorePager"></div>-->
		</div>
		<div id="workScore_pageBar" class="panelBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="workScore_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="workScore_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="workScore_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
		
		</div>
		</div>
	</div>
</div>