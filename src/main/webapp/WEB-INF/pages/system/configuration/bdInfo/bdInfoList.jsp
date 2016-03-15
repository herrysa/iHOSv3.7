
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
		var bdInfoChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				bdInfoLayout.closeSouth();
				return;
			}
    		jQuery("#fieldInfo").load("fieldInfoList?bdInfoId="+selectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	bdInfoLayout = makeLayout({'baseName':'bdInfo',
    		'tableIds':'bdInfo_gridtable;fieldInfo_gridtable',
    		'activeGridTable':'bdInfo_gridtable',
    		'proportion':2,
    		'key':'bdInfoId'},
    		bdInfoChangeData);
		
    
		var bdInfoGrid = jQuery("#bdInfo_gridtable");
    	bdInfoGrid.jqGrid({
    		url : "bdInfoGridList",
    		editurl:"bdInfoGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'bdInfoId',index:'bdInfoId',align:'left',width:'100px',label : '<s:text name="bdInfo.bdInfoId" />',hidden:false,key:true},        	          
{name:'tableName',index:'tableName',align:'left',width:'100px',label : '<s:text name="bdInfo.tableName" />',hidden:false},
{name:'bdInfoName',index:'bdInfoName',align:'left',width:'100px',label : '<s:text name="bdInfo.bdInfoName" />',hidden:false},
{name:'entityName',index:'entityName',align:'left',width:'100px',label : '<s:text name="bdInfo.entityName" />',hidden:false},
{name:'tableAlias',index:'tableAlias',align:'left',width:'100px',label : '<s:text name="bdInfo.tableAlias" />',hidden:false},
{name:'sn',index:'sn',align:'right',width:'50px',label : '<s:text name="bdInfo.sn" />',hidden:false},     
{name:'subSystem',index:'subSystem',align:'left',width:'100px',label : '<s:text name="bdInfo.subSystem" />',hidden:false},
{name:'treeStructure',index:'treeStructure',align:'center',width:'50px',label : '<s:text name="bdInfo.treeStructure" />',hidden:false,formatter:'checkbox'},
{name:'parentTable',index:'parentTable',align:'left',width:'100px',label : '<s:text name="bdInfo.parentTable" />',hidden:false},
{name:'typeTable',index:'typeTable',align:'left',width:'100px',label : '<s:text name="bdInfo.typeTable" />',hidden:false},
{name:'changer.name',index:'changer.name',align:'left',width : 60,label : '<s:text name="bdInfo.changer" />',hidden:false,highsearch:true},
{name:'changeDate',index:'changeDate',align:'center',width : 70,label : '<s:text name="bdInfo.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="bdInfo.disabled" />',hidden:false,formatter:'checkbox'},
{name:'sysField',index:'sysField',align:'center',width:'50px',label : '<s:text name="bdInfo.sysField" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="bdInfo.remark" />',hidden:false}
],
        	jsonReader : {
				root : "bdInfoes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sn',
        	viewrecords: true,
        	sortorder: '',
        	//rowNum:'100',
        	//caption:'<s:text name="bdInfoList.title" />',
        	height:'100%',
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				bdInfoLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		bdInfoLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
		 		gridContainerResize('bdInfo','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }
           	var dataTest = {"id":"bdInfo_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("bdInfo_gridtable");
       		} 
    	});
    jQuery(bdInfoGrid).jqGrid('bindKeys');
    
    /*字段按钮start*/
    /*字段添加*/
    jQuery("#fieldInfo_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	var sid = jQuery("#bdInfo_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条表描述记录！");
 			return;
 		}else{
 			var winTitle='<s:text name="fieldInfoNew.title"/>';
 	 		var url = "editFieldInfo?popup=true&bdInfoId="+sid+"&navTabId=fieldInfo_gridtable";
 	 		$.pdialog.open(url,'editFieldInfo',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
 		}
    });
    /*字段修改*/
    jQuery("#fieldInfo_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
    	var sid = jQuery("#fieldInfo_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录！");
 			return;
 		}
        var winTitle='<s:text name="fieldInfoEdit.title"/>';
 		var url = "editFieldInfo?popup=true&id="+sid+"&navTabId=fieldInfo_gridtable";
 		$.pdialog.open(url,'editFieldInfo',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
    });
    /*字段按钮end*/ 
    //表添加
     jQuery("#bdInfo_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
 		var winTitle = '<s:text name="bdInfoNew.title"/>';
 		var url = "editBdInfo?popup=true&navTabId=bdInfo_gridtable";
 		$.pdialog.open(url,'addBdInfo',winTitle, {mask:true,width : 695,height : 400,maxable:true,resizable:true});
     });
   //表修改
 	jQuery("#bdInfo_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
 		var sid = jQuery("#bdInfo_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null||sid.length!=1){       	
			alertMsg.error("请选择一条记录！");
			return;
		}
        var winTitle = '<s:text name="bdInfoEdit.title"/>';
    	var url = "editBdInfo?bdInfoId="+sid+"&popup=true&navTabId=bdInfo_gridtable";
    	$.pdialog.open(url,'editBdInfo',winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true});
     });
    /*表删除*/
//     jQuery("#bdInfo_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
//     	});
    //查询
    jQuery("#bdInfo_searchButton").unbind( 'click' ).bind("click",function(){
       	propertyFilterSearch('bdInfo_search_form','bdInfo_gridtable');
    });
  	});
    /*查看表*/
//     function viewbdInfoList(id){
//     	var winTitle = '<s:text name="bdInfoView.title"/>';
//     	var url = "editbdInfo?itemId="+id+"&popup=true&oper=view";
//     	$.pdialog.open(url,'viewbdInfo_'+id,'查看表信息', {mask:true,width : 650,height : 400,maxable:true,resizable:true});      	
//     }
    	//上下移动
	function bdInfoColMove(dir) {
		var sid = jQuery("#bdInfo_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length != 1) {
 			return;
		}
		var rowId = sid[0];
		var rowData = jQuery("#bdInfo_gridtable").getRowData(rowId);
		var sn = rowData["sn"];
    	var rowIds = jQuery("#bdInfo_gridtable").jqGrid("getDataIDs");
    	var rowSn = jQuery.inArray(rowId, rowIds);
    	var rowIdsLength = rowIds.length;
    	if(dir == "down") {
		    var nextRowSn = rowSn+1;
	    	if(nextRowSn == rowIdsLength){
	    		return ;
	    	}
	    	var nextRowId = rowIds[nextRowSn];
	    	var nextRowData = jQuery("#bdInfo_gridtable").getRowData(nextRowId);
	    	jQuery("#bdInfo_gridtable").jqGrid("setRowData",rowId,{sn:nextRowData["sn"]},{});
	    	jQuery("#bdInfo_gridtable").jqGrid("setRowData",nextRowId,{sn:sn},{});
			rowData = jQuery("#bdInfo_gridtable").getRowData(rowId);
	    	nextRowData = jQuery("#bdInfo_gridtable").getRowData(nextRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var nextRowDataClone = cloneObj(nextRowData);
	    	jQuery("#bdInfo_gridtable").delRowData(rowId);
	    	jQuery("#bdInfo_gridtable").delRowData(nextRowId);
	    	if(rowSn == 0){
	    		jQuery("#bdInfo_gridtable").addRowData(nextRowId, nextRowDataClone, "first");
	        	jQuery("#bdInfo_gridtable").addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn-1];
	    		jQuery("#bdInfo_gridtable").addRowData(nextRowId, nextRowDataClone, "after",rowIdTemp);
	    		jQuery("#bdInfo_gridtable").addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}
			jQuery("#bdInfo_gridtable").jqGrid("setSelection",rowId);
    	} else if(dir == "up") {
    		if(rowSn == 0) {
    			return;
    		}
    		var preRowSn = rowSn - 1;
    		var preRowId = rowIds[preRowSn];
	    	var preRowData = jQuery("#bdInfo_gridtable").getRowData(preRowId);
	    	jQuery("#bdInfo_gridtable").jqGrid("setRowData",rowId,{sn:preRowData["sn"]},{});
	    	jQuery("#bdInfo_gridtable").jqGrid("setRowData",preRowId,{sn:sn},{});
	    	rowData = jQuery("#bdInfo_gridtable").getRowData(rowId);
	    	preRowData = jQuery("#bdInfo_gridtable").getRowData(preRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var preRowDataClone = cloneObj(preRowData);
	    	jQuery("#bdInfo_gridtable").delRowData(rowId);
	    	jQuery("#bdInfo_gridtable").delRowData(preRowId);
	    	if(rowSn == rowIdsLength - 1){
	    		jQuery("#bdInfo_gridtable").addRowData(preRowId, preRowDataClone, "last");
	        	jQuery("#bdInfo_gridtable").addRowData(rowId, rowDataClone, "before",preRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn+1];
	    		jQuery("#bdInfo_gridtable").addRowData(preRowId, preRowDataClone, "before",rowIdTemp);
	    		jQuery("#bdInfo_gridtable").addRowData(rowId, rowDataClone, "before",preRowId);
	    	}
			jQuery("#bdInfo_gridtable").jqGrid("setSelection",rowId);
    	}
	}
    function bdInfoColMoveSave(){
    	var rowDatas = jQuery("#bdInfo_gridtable").jqGrid("getRowData");
		var dataStr = "";
		for(var i = 0;i < rowDatas.length; i++) {
			var row = rowDatas[i];
			var bdInfoId = row["bdInfoId"];
	    	var rowSn = row["sn"];
	    	dataStr += bdInfoId + ":" + rowSn + ",";
		}
		dataStr = dataStr.substr(0,dataStr.length-1);
		var page = jQuery("#bdInfo_gridtable").jqGrid("getGridParam","page");
		jQuery.ajax({
			url:"saveBdInfoSn",
			data:{dataStr:dataStr},
			dataType:"json",
			type:"post",
			success:function(data) {
				if(data.statusCode == 200) {
					alertMsg.info("保存成功！");
					jQuery("#bdInfo_gridtable").jqGrid("setGridParam",{page:page}).trigger("reloadGrid");
				}
			}
		});
    }
</script>
<div class="page">
	<div id="bdInfo_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bdInfo_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='bdInfo.bdInfoName'/>:
						<input type="text" name="filter_LIKES_bdInfoName" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bdInfo.tableName'/>:
						<input type="text" name="filter_LIKES_tableName" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
					     <s:text name='bdInfo.disabled'/>:
					   <s:select name='filter_EQB_disabled' headerKey=""   style="font-size:12px"
							list="#{'':'--','false':'否','true':'是'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bdInfo.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" id="bdInfo_searchButton"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="bdInfo_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="bdInfo_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="bdInfo_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="bdInfo_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li><a class="unfoldbutton" href="javaScript:bdInfoColMove('up')"><span><s:text name="上移" /> </span> </a></li>
				<li><a class="foldbutton" href="javaScript:bdInfoColMove('down')"><span><s:text name="下移" /> </span> </a></li>
				<li><a class="savebutton" href="javaScript:bdInfoColMoveSave();"><span><s:text name="保存" /> </span> </a></li>
			     <li style="float:right">
					<a class="particularbutton" href="javaScript:bdInfoLayout.optDblclick();"><span>字段列表</span> </a>
				</li>
			</ul>
		</div>
		<div id="bdInfo_container">
					<div id="bdInfo_layout-center" class="pane ui-layout-center">		
		<div id="bdInfo_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bdInfo_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_bdInfo_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
 			<table id="bdInfo_gridtable"></table>
		</div>
	<div class="panelBar" id="bdInfo_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bdInfo_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bdInfo_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bdInfo_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
		</div>
	</div>
	<div id="bdInfo_layout-south" class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
								<li>
									<a id="fieldInfo_gridtable_add_custom" class="addbutton" href="javaScript:"><span><s:text name="button.add" /></span></a>
								</li>
								<li>
									<a id="fieldInfo_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
								</li>
								<li>
									<a id="fieldInfo_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
								</li>
								<li style="float: right;">
									<a id="bdInfo_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="bdInfo_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="bdInfo_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div id="fieldInfo"></div>
					</div>
					<!-- </div> -->
				</div>
	</div>
</div>