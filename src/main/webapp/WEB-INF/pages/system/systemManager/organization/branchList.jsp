
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var branchLayout;
	var branchGridIdString="#branch_gridtable";
	
	jQuery(document).ready(function() {
		var branchFullSize = jQuery("#container").innerHeight()-116;
		jQuery("#branch_container").css("height",branchFullSize);
		$('#branch_container').layout({ 
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
					gridResize(null,null,"branch_gridtable","single");
				}
			}
			
		});
		loadBranchTree();
		var branchGrid = jQuery(branchGridIdString);
    	branchGrid.jqGrid({
    		url : "branchGridList",
    		editurl:"branchGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'branchCode',index:'branchCode',align:'left',width:20,label : '<s:text name="branch.branchCode" />',hidden:false,key:true},
{name:'branchName',index:'branchName',align:'left',width:30,label : '<s:text name="branch.branchName" />',hidden:false},
{name:'org.orgname',index:'org.orgname',align:'left',width:44,label : '<fmt:message key="hisOrg.orgName" />',hidden:false},      	
{name:'disabled',index:'disabled',align:'center',width:6,label : '<s:text name="branch.disabled" />',hidden:false,formatter:'checkbox'}
			],
        	jsonReader : {
				root : "branches", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'branchCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="branchList.title" />',
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
		 		gridContainerResize("branch","layout");
		 		var rowNum = jQuery(this).getDataIDs().length;
		 		if(rowNum > 0) {
		 			var ztree = $.fn.zTree.getZTreeObj("branchTree");
	                if(ztree){
	                	var selectNode = ztree.getSelectedNodes();
						if(selectNode && selectNode.length==1){
							var selectid = selectNode[0].id;
	 						jQuery(this).jqGrid('setSelection',selectid);
						}
	                }
		 		}/*  else {
					var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
				} */
			var dataTest = {"id":"branch_gridtable"};
			jQuery.publish("onLoadSelect",dataTest,null);
			} 

		});
		jQuery(branchGrid).jqGrid('bindKeys');
		jQuery("#branch_gridtable_add_custom").click(function() {
			var ztree = $.fn.zTree.getZTreeObj("branchTree");
			var sid;
			if(ztree){
				var selectNode = ztree.getSelectedNodes();
				if(selectNode && selectNode.length==1){
					sid = selectNode[0].id;
				}
				if(sid !=="-1" && selectNode[0].subSysTem === 'ORG'){
					jQuery.ajax({
						url:'checkBranchCanBeAdded',
						type:'post',
						//data:'',
						dataType: 'json',
						aysnc:false,
						error: function(data){
						},
						success: function(data) {
							if(!data.canBeAdded){
								 alertMsg.error("当前版本可添加的院区数已经是最大。");
							} else {
								var url = "editBranch?popup=true&nabTabId=branch_gridtable&orgCode="+sid;
								var winTitle = jQuery("#branch_gridtable_addTile").text();
								url = encodeURI(url);
								$.pdialog.open(url,"addBranch",winTitle,{mask:true,width:400,height:250});
							}
						}
					});
				} else {
					alertMsg.error("请选择一个单位。");
					return;
				}
			}
		});
		jQuery("#branch_gridtable_del_custom").click(function() {
			var sid = jQuery("#branch_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				var editUrl = jQuery("#branch_gridtable").jqGrid('getGridParam', 'editurl');
				editUrl+="?id=" + sid+"&navTabId=branch_gridtable&oper=del";
				alertMsg.confirm("确认删除？", {
					okCall: function(){
						jQuery.post(editUrl, {
						}, branchFormCallBack, "json");
						
					}
				});
			}
		});
});
	var ztreesetting_branch = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var treeId = treeNode.id;
					var subSysTem = treeNode.subSysTem;
					var urlString = "branchGridList";
					if(treeId !==""){
						if(subSysTem ==="BRANCH") {
							urlString += "?filter_EQS_branchCode="+treeId;
						} else if(subSysTem ==="ORG") {
							urlString += "?filter_EQS_orgCode="+treeId;
						}
					}
					urlString=encodeURI(urlString);
					jQuery("#branch_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	function branchFormCallBack(data) {
		reloadBranchGrid();
		formCallBack(data);
	}
	function reloadBranchGrid() {
		jQuery("#branch_gridtable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
		loadBranchTree();
	}
	
	function loadBranchTree() {
		var treeUrl = "makeBranchTree";
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var branchTreeData = data.ztreeList;
			var branchTree = $.fn.zTree.init($("#branchTree"),ztreesetting_branch, branchTreeData);
			var nodes = branchTree.getNodes();
			branchTree.expandNode(nodes[0], true, false, true);
			branchTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'branchTree',
				showCode:jQuery('#branch_showCode')[0],
				disabledDept:false,
				disabledPerson: false,
				showCount:false });
		});
		//jQuery("#branch_expandTree").text("展开");
	}
</script>

<div class="page">
	<div id="branch_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="branch_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='branch.branchCode'/>:
						<input type="text" name="filter_LIKES_branchCode"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='branch.branchName'/>:
						<input type="text" name="filter_LIKES_branchName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='branch.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled"></s:select>
					</label>
					<%-- <label class="queryarea-label" >
						<s:text name='branch.orgCode'/>:
						<input type="text" name="filter_EQS_orgCode"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('branch_search_form','branch_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="branch_buttonBar">
			<ul class="toolBar">
				<li><a id="branch_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="branch_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="branch_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="branch_container">
			<div id="branch_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
						<span>
							显示机构编码
							<input id="branch_showCode" type="checkbox" checked="checked" onclick="toggleDisabledOrCount({treeId:'branchTree',showCode:this,disabledDept:false,disabledPerson:false,showCount:false })"/>
						</span>
				</div>
				<!-- <div style="margin-left:20px;margin-bottom:2px;float:right">
					<span id="branch_expandTree" style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:10px" onclick="toggleExpandTree(this,'branchTree')">展开</span>
				</div> -->
				<div id="branchTree" class="ztree"></div>
			</div>
			<div id="branch_layout-center" class="pane ui-layout-center">
				<div id="branch_gridtable_div" class="grid-wrapdiv" buttonBar="width:400;height:250;optId:branchCode">
					<input type="hidden" id="branch_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="branch_gridtable_addTile">
						<s:text name="branchNew.title"/>
					</label> 
					<label style="display: none" id="branch_gridtable_editTile">
						<s:text name="branchEdit.title"/>
					</label>
					<div id="load_branch_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="branch_gridtable"></table>
				</div>
				<div class="panelBar" id="branch_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="branch_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="branch_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="branch_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>