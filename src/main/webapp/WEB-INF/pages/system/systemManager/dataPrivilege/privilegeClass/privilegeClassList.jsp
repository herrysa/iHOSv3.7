
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">

	function dealPrivilegeClassLoad(treeId,node,opt) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodeId = node.id;
		var oldNode = treeObj.getNodeByParam("id", nodeId, null);
		if(node) {
			switch(opt) {
				case "add" :
				var parentNode = treeObj.getNodeByParam("id",node.pId,null);
				treeObj.addNodes(parentNode,node);
				/* setTimeout(function() {
					var newNode = treeObj.getNodeByParam("id",nodeId,null);
					treeObj.selectNode(newNode);
					//jQuery("#privilegeClass_gridtable").setSelection(nodeId,false);
				},100); */
				break;
				case "edit" :
					if(oldNode) {
						oldNode.name=node.name;
						oldNode.icon=node.icon;
						treeObj.updateNode(oldNode);
					}
					treeObj.selectNode(oldNode);
				break;
				case "del" :
				if(oldNode) {
					treeObj.removeNode(oldNode);
				}
				break;
			}
		}
	}
	function privilegeClassGridReload(){
		var urlString = "privilegeClassGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#privilegeClass_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var ztreesetting_privilegeClassTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "privilegeClassGridList?1=1";
				    var nodeId = treeNode.id;
				    var parentId = treeNode.pId;
				    if(nodeId != "-1") {
				    	urlString += "&nodeId=" + nodeId;
				    	/* if(parentId == "-1") {
				    		if(treeNode.children) {
					    		urlString += "&parentId=" + nodeId; 
				    		} else {
				    			urlString += "&nodeId=" + nodeId;
				    		}
				    	} else {
				    		urlString += "&nodeId=" + nodeId;
				    	} */
				    }
					urlString=encodeURI(urlString);
					jQuery("#privilegeClass_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
				}
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
	function loadPrivilegeClassTree() {
		$.get("makeDataPrivilegeClassTree", function(data) {
			var privilegeClassTreeData = data.dataPrivilegeClassNodes;
			var privilegeClassTree = $.fn.zTree.init($("#privilegeClassTree"),ztreesetting_privilegeClassTree, privilegeClassTreeData);
			var nodes = privilegeClassTree.getNodes();
			privilegeClassTree.expandNode(nodes[0], true, false, true);
			privilegeClassTree.selectNode(nodes[0]);
		});
		jQuery("#privilegeClass_expandTree").text("展开");
	}
	/*
 	* 展开\折叠树
 	*/
	 function toggleExpandPrivilegeClassTree(obj,treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId); 
		var obj = jQuery(obj);
		var text = obj.html();
			if(text=='展开'){
			obj.html("折叠");
			zTree.expandAll(true);
		}else{
			obj.html("展开");
			//zTree.expandAll(false);
			var allNodes = zTree.transformToArray(zTree.getNodes());
			for(var nodeIndex in allNodes){
				var node = allNodes[nodeIndex];
				if(node.pId == "-1") {
					zTree.expandNode(node,false,true,true);
				} 
				//if(node.subSysTem=='PITEM'){
				//}
			}
		}
	} 
	var privilegeClassLayout;
			  var privilegeClassGridIdString="#privilegeClass_gridtable";
	
	jQuery(document).ready(function() { 
		//layout
		var privilegeClassFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('privilegeClass_container','privilegeClass_gridtable',privilegeClassFullSize);
		//leftTree
		loadPrivilegeClassTree();
		//jqGrid
		privilegeClassLayout = makeLayout({
			baseName: 'privilegeClass', 
			tableIds: 'privilegeClass_gridtable'
		}, null);
	var privilegeClassGrid = jQuery(privilegeClassGridIdString);
    privilegeClassGrid.jqGrid({
    	url : "privilegeClassGridList",
    	editurl:"privilegeClassGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
		{name:'classCode',index:'classCode',align:'left',label : '<s:text name="privilegeClass.classCode" />',hidden:false,key:true},				
		{name:'className',index:'className',align:'left',label : '<s:text name="privilegeClass.className" />',hidden:false},				
		/* {name:'searchName',index:'searchName',align:'left',label : '<s:text name="privilegeClass.searchName" />',hidden:false}, */				
		{name:'sn',index:'sn',align:'left',label : '<s:text name="privilegeClass.sn" />',hidden:false},				
		{name:'bdInfo.bdInfoName',index:'bdInfo.bdInfoName',align:'left',label : '<s:text name="privilegeClass.bdInfo" />',hidden:false},				
		{name:'bdInfo.tableName',index:'bdInfo.tableName',align:'left',label : '<s:text name="privilegeClass.table" />',hidden:false},				
		{name:'disabled',index:'disabled',align:'center',label : '<s:text name="privilegeClass.disabled" />',hidden:false,formatter:'checkbox'}				

        ],
        jsonReader : {
			root : "privilegeClasses", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="privilegeClassList.title" />',
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
			//layout
		 		gridContainerResize('privilegeClass','layout');
        		//when select tree node
        		var rowNum = jQuery(this).getDataIDs().length;
        		if(rowNum > 0) {
					var treeObj = $.fn.zTree.getZTreeObj("privilegeClassTree");
					if(treeObj) {
						var selectedNode = treeObj.getSelectedNodes();
						if(selectedNode && selectedNode.length == 1) {
							var sid = selectedNode[0].id;
							if(sid != "-1") { // && selectedNode[0].pId != "-1
								jQuery(this).jqGrid("setSelection",sid);
							}
						}
					}
        		} else {
        			var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
        		}
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var dataTest = {"id":"privilegeClass_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("privilegeClass_gridtable");
       	} 

    });
    jQuery(privilegeClassGrid).jqGrid('bindKeys');
    
	
	
	
	//privilegeClassLayout.resizeAll();
	jQuery("#privilegeClass_gridtable_add_custom").unbind("click").bind("click",function() {
		var winTitle = jQuery("#privilegeClass_gridtable_addTile").text();
		var treeObj = $.fn.zTree.getZTreeObj("privilegeClassTree");
		var sNode = treeObj.getSelectedNodes();
		var sid = "";
		if(sNode && sNode.length == 1) {
			sid = sNode[0].id;
		}
		var urlString = "editPrivilegeClass?popup=true&navTabId=privilegeClass_gridtable&parentClass="+sid;
		urlStirng = encodeURI(urlString);
		$.pdialog.open(urlString, 'editPrivilegeClass', winTitle, {mask:true,width:500,height:300});
	});
	jQuery("#privilegeClass_gridtable_del_custom").unbind("click").bind("click",function(){
		var sid = jQuery("#privilegeClass_gridtable").jqGrid("getGridParam","selarrrow");
		var editUrl = jQuery("#privilegeClass_gridtable").jqGrid("getGridParam","editurl");
		editUrl+="?id=" + sid+"&navTabId=privilegeClass_gridtable&oper=del";
		editUrl = encodeURI(editUrl);
		if(sid==null || sid.length ==0){
			alertMsg.error("请选择记录。");
			return;
		}else{
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl,function(data) {
						formCallBack(data);
						if(data.statusCode != 200) {
							return;
						}
						//privilegeClassGridReload();
						for(var i = 0;i < sid.length; i++) {
							dealPrivilegeClassLoad("privilegeClassTree",{id:sid[i]},"del");
						}
					});
				}
			});
		}
	});
  	});
</script>

<div class="page">
	<div class="pageContent">
	<div class="panelBar" id="privilegeClass_buttonBar">
			<ul class="toolBar">
				<li><a id="privilegeClass_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="privilegeClass_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="privilegeClass_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
<div id="privilegeClass_container">
			<div id="privilegeClass_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="float:right"> 
 					<span id="privilegeClass_expandTree" onclick="toggleExpandPrivilegeClassTree(this,'privilegeClassTree')" style="vertical-align:middle;text-decoration:underline;cursor:pointer;">展开</span>
				</div>
				<div id="privilegeClassTree" class="ztree"></div>
			</div>
			<div id="privilegeClass_layout-center"
				class="pane ui-layout-center">
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='privilegeClass.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='privilegeClass.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='privilegeClass.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='privilegeClass.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="privilegeClassGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>

		
		<div id="privilegeClass_gridtable_div" layoutH="58"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:classCode;width:500;height:300">
			<input type="hidden" id="privilegeClass_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="privilegeClass_gridtable_addTile">
				<s:text name="privilegeClassNew.title"/>
			</label> 
			<label style="display: none"
				id="privilegeClass_gridtable_editTile">
				<s:text name="privilegeClassEdit.title"/>
			</label>
			<label style="display: none"
				id="privilegeClass_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="privilegeClass_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_privilegeClass_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="privilegeClass_gridtable"></table>
		<div id="privilegeClassPager"></div>
</div>
	<div class="panelBar" id="privilegeClass_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="privilegeClass_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="privilegeClass_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="privilegeClass_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
</div>
</div>