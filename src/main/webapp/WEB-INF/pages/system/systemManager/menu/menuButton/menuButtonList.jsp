
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var menuButtonGridIdString="#menuButton_gridtable";
	
	jQuery(document).ready(function() {
		var menuButtonFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#menuButton_container").css("height",menuButtonFullSize);
		$('#menuButton_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 290,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",
			onresize_end : function(paneName,element,state,options,layoutName){
				if("center" == paneName){
					gridResize(null,null,'menuButton_gridtable',"single");
				}
			}
		});
		loadMenuTreeInMenuButton();
		var menuButtonGrid = jQuery(menuButtonGridIdString);
	    menuButtonGrid.jqGrid({
	    	url : "menuButtonGridList",
	    	editurl:"menuButtonGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'menuId',index:'menuId',align:'left',label : '<s:text name="menuButton.menuId" />',hidden:false},				
				{name:'id',index:'id',align:'left',label : '<s:text name="menuButton.id" />',hidden:false,key:true},				
				{name:'buttonLabel',index:'buttonLabel',align:'left',label : '<s:text name="menuButton.buttonLabel" />',hidden:false},				
				{name:'oorder',index:'oorder',align:'center',label : '<s:text name="menuButton.order" />',hidden:false,formatter:'integer'},				
				{name:'className',index:'className',align:'left',label : '<s:text name="menuButton.className" />',hidden:false},				
				{name:'buttonUrl',index:'buttonUrl',align:'left',label : '<s:text name="menuButton.buttonUrl" />',hidden:false}	

	        ],
	        jsonReader : {
				root : "menuButtons", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'menuId,oorder',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="menuButtonList.title" />',
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
		 		gridContainerResize("menuButton","layout");
           		var dataTest = {"id":"menuButton_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 
    	});
    	jQuery(menuButtonGrid).jqGrid('bindKeys');
  	});
	function loadMenuTreeInMenuButton() {
		var treeUrl = "makeMenuTreeForMenuButton";
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var menuTreeData = data.menuNodes;
			var menuTree = $.fn.zTree.init($("#menuTreeInMenuButton"),ztreesetting_menuTreeInMenuButton, menuTreeData);
			var nodes = menuTree.getNodes();
			// menuTree.expandNode(nodes[0], true, false, true);
		});
	}
	
	var ztreesetting_menuTreeInMenuButton = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "menuButtonGridList";
				var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	urlString += "?menuId="+nodeId;
			    }
				urlString=encodeURI(urlString);
				jQuery("#menuButton_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	function addMenuButton(){
		var zTree = $.fn.zTree.getZTreeObj("menuTreeInMenuButton");  
	    var nodes = zTree.getSelectedNodes(); 
	    if(nodes.length!=1 || nodes[0].isParent){
	    	alertMsg.error("请选择末级菜单。");
      		return;
	    }
	    var url = "editMenuButton?navTabId=menuButton_gridtable&menuId="+nodes[0].id;
		var winTitle='<s:text name="添加按钮"/>';
		$.pdialog.open(url,'addMenuButton',winTitle, {mask:true,width : 400,height : 300,maxable:false,resizable:false});
	}
</script>

<div class="page" id="menuButton_page">
	<div class="pageHeader" id="menuButton_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="menuButton_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='menuButton.menuId'/>:
      					<input type="text" name="filter_LIKES_menuId"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='menuButton.id'/>:
      					<input type="text" name="filter_LIKES_id"/>
					</label>
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button"
								onclick="propertyFilterSearch('menuButton_search_form','menuButton_gridtable')">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="menuButton_buttonBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addMenuButton()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="menuButton_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="menuButton_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
			</ul>
		</div>
		<div id="menuButton_container">
			<div id="menuButton_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div id="menuTreeInMenuButton" class="ztree"></div>
			</div>
			<div id="menuButton_layout-center" class="pane ui-layout-center">
				<div id="menuButton_gridtable_div" class="grid-wrapdiv"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden"
					buttonBar="optId:id;width:400;height:300">
					<input type="hidden" id="menuButton_gridtable_navTabId" value="${sessionScope.navTabId}" >
					<label style="display: none" id="menuButton_gridtable_addTile">
						<s:text name="menuButtonNew.title"/>
					</label> 
					<label style="display: none"
						id="menuButton_gridtable_editTile">
						<s:text name="menuButtonEdit.title"/>
					</label>
					<label style="display: none"
						id="menuButton_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="menuButton_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_menuButton_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="menuButton_gridtable"></table>
				</div>
				<div class="panelBar" id="menuButton_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="menuButton_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="menuButton_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="menuButton_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>