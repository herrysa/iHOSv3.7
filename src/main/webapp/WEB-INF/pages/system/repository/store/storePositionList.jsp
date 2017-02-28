
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	 var storePositionGridIdString="#storePosition_gridtable";
	jQuery(document).ready(function() { 
	var storePositionGrid = jQuery(storePositionGridIdString);
    storePositionGrid.jqGrid({
    	url : "storePositionGridList?filter_INS_store.id=-1",
    	editurl:"storePositionGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'posId',index:'posId',align:'center',label : '<s:text name="storePosition.posId" />',hidden:true,key:true,editable:true},				
{name:'store.id',index:'storeId',align:'center',label : '<s:text name="store.id" />',hidden:true,editable:true},				
/* {name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="storePosition.orgCode" />',hidden:false},	 */			
{name:'posCode',index:'posCode',align:'left',label : '<s:text name="storePosition.posCode" />',hidden:false,edittype:"text",editable:true,editrules:{required:true},editoptions:{dataEvents:[{type:'blur',fn: function(e) { checkPosCode(this); }}]}},				
{name:'posName',index:'posName',align:'left',label : '<s:text name="storePosition.posName" />',hidden:false,edittype:"text",editable:true,editrules:{required:true}},				
{name:'remark',index:'remark',align:'left',label : '<s:text name="storePosition.remark" />',hidden:false,edittype:"text",editable:true},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="storePosition.disabled" />',hidden:false,edittype:"checkbox",editable:true,formatter:'checkbox'}				

        ],
        jsonReader : {
			root : "storePositions", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'posCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="storePositionList.title" />',
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
           var dataTest = {"id":"storePosition_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("storePosition_gridtable");
       	}

    });
    jQuery(storePositionGrid).jqGrid('bindKeys');
    refreshStorePosTree();
    var storePosFullSize = jQuery("#container").innerHeight()-145;
	jQuery("#storePosition_container").css("height",storePosFullSize);
	$('#storePosition_container').layout({ 
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
				gridResize(null,null,"storePosition_gridtable","single");
			}
		}
		
	});
  	});
	function refreshStorePosTree(){
		jQuery.ajax({
		    url: 'makeStoreTreeIsPos',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		        alert(data);
		    },
		    success: function(data){
		        //alert(data.ztreeList);
		        setTimeout(function(){
		        	storePosTree = jQuery.fn.zTree.init(jQuery("#storePosTree"), ztreesetting_storePos,data.storeTree);
		        	var rootnode = storePosTree.getNodeByParam("id","-1",null);
		        	storePosTree.selectNode(rootnode);
		        	storePosTree.expandAll(rootnode);
		        },100);
		    }
		});
	}
	var ztreesetting_storePos = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : true
			},
			data : {
				key : {
					name : "storeNameWithCode"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parent"
				}
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick: reloadStorePosGrid
			}
			
		};
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	var selectedStoreNode;
	function reloadStorePosGrid(e, treeId, treeNode){
		var urlString = "storePositionGridList";
		urlString += "?filter_INS_store.id="+treeNode.id;
		urlString=encodeURI(urlString);
		jQuery("#storePosition_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	function checkPosCode(obj){
		if(""==obj.value.trim()){
			alertMsg.error("该项不能为空");
			return;
		}
		//var org = jQuery("#orgCode").html();
		selectedStoreNode = isStoreSelected();
		var posIdTemp = selectedStoreNode.id+"_"+obj.value;
		var url = 'checkId';
		url = encodeURI(url);
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{entityName:'StorePosition',searchItem:'posId',searchValue:posIdTemp,returnMessage:'货位编码已存在'},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
				     obj.value="";
		        }
		    }
		});
	}
	function isStoreSelected(){
		var nodes = storePosTree.getSelectedNodes();
		if(nodes.length==0){
			alertMsg.error("请选择仓库");
			return;
		}
		return nodes[0];
	}
	function editStorePosition(obj){
		obj.jqGrid("setColProp","posCode",{editable:false});
		gridEditRow(obj);
	}
	function addStorePosition(obj){
		selectedStoreNode = isStoreSelected();
		if(selectedStoreNode.isParent){
			alertMsg.error("请选择末级仓库");
			return;
		}
		obj.jqGrid("setColProp","posCode",{editable:true});
		gridAddRow(obj);
		var storeId = selectedStoreNode.id;
		//var storeId = "QH0001_0101";
		var new_storeId = document.getElementById("new_row_store.id");
		new_storeId.value=storeId;
	}
	function saveStorePosition(obj){
		gridSaveRow(obj);
	}
	
	
	
</script>
<div id="storePos_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="storePos_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='storePosition.posCode'/>:
						<input type="text" name="filter_LIKES_posCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='storePosition.posName'/>:
						<input type="text" name="filter_LIKES_posName"/>
					</label>&nbsp;&nbsp;
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='storePosition.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled" style="width:60px"></s:select>
					</label>&nbsp;&nbsp;
					
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearchInAccountList('account_search_form','account_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('storePos_search_form','storePosition_gridtable')"><s:text name='button.search'/></button>
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
			<li><a class="addbutton" onclick="addStorePosition(jQuery('#storePosition_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
			<li><a class="editbutton" onclick="editStorePosition(jQuery('#storePosition_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
			<li><a class="savebutton" onclick="saveStorePosition(jQuery('#storePosition_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
			<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#storePosition_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
			<li><a id="storePosition_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			
		</ul>
	</div>
	<div id="storePosition_container">
		<div id="storePosition_layout-west" class="pane ui-layout-west" 
			style="float: left; display: block; overflow: auto;">
			<DIV id="storePosTree" class="ztree"></DIV>
		</div>
		<div id="storePosition_layout-center" class="pane ui-layout-center">
			<div id="storePosition_gridtable_div" layoutH="148" class="grid-wrapdiv" class="unitBox"
				buttonBar="width:500;height:300">
				<input type="hidden" id="storePosition_gridtable_navTabId" value="${sessionScope.navTabId}">
				<label style="display: none" id="storePosition_gridtable_addTile">
					<s:text name="storePositionNew.title"/>
				</label> 
				<label style="display: none"
					id="storePosition_gridtable_editTile">
					<s:text name="storePositionEdit.title"/>
				</label>
				<label style="display: none"
					id="storePosition_gridtable_selectNone">
					<s:text name='list.selectNone'/>
				</label>
				<label style="display: none"
					id="storePosition_gridtable_selectMore">
					<s:text name='list.selectMore'/>
				</label>
				<div id="load_storePosition_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					<table id="storePosition_gridtable"></table>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="storePosition_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="storePosition_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
				<div id="storePosition_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1">
				</div>
			</div>
		</div>
	</div>
</div>