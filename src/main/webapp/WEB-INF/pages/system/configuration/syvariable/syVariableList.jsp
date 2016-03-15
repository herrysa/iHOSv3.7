
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var syVariableLayout;
	var syVariableGridIdString="#syVariable_gridtable";
	jQuery(document).ready(function() { 
		var syVariableGrid = jQuery(syVariableGridIdString);
    	syVariableGrid.jqGrid({
    		url : "syVariableGridList",
    		editurl:"syVariableGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'variableName',index:'variableName',align:'left',width:'150px',label : '<s:text name="syVariable.variableName" />',hidden:false,key:true},
{name:'variableDesc',index:'variableDesc',align:'left',width:'200px',label : '<s:text name="syVariable.variableDesc" />',hidden:false},
{name:'value',index:'value',align:'left',width:'80px',label : '<s:text name="syVariable.value" />',hidden:false},
{name:'sn',index:'sn',align:'center',width:'60px',label : '<s:text name="syVariable.sn" />',hidden:false,formatter:'integer'},
{name:'variableType',index:'variableType',align:'left',width:'88px',label : '<s:text name="syVariable.variableType" />',hidden:false},
{name:'variableConstraint',index:'variableConstraint',align:'left',width:'88px',label : '<s:text name="syVariable.variableConstraint" />',hidden:false,formatter:'select',editoptions : {value : "'':'';0:登陆单位;1:登录账套;2:登录期间年"}},
{name:'rightType',index:'rightType',align:'left',width:'60px',label : '<s:text name="syVariable.rightType" />',hidden:false,formatter:'select',editoptions : {value : "'':'';1:读;2:写"}},
{name:'dataFormat',index:'dataFormat',align:'left',width:'88px',label : '<s:text name="syVariable.dataFormat" />',hidden:false,formatter:'select',editoptions : {value : "'':'';1:逗号字符串;2:sql形式"}},
{name:'privaleClass.className',index:'privaleClass.className',align:'left',width:'88px',label : '<s:text name="syVariable.classCode" />',hidden:false},
{name:'sqlExpression',index:'sqlExpression',align:'left',width:'200px',label : '<s:text name="syVariable.sqlExpression" />',hidden:false},
{name:'remark',index:'remark',align:'left',width:'200px',label : '<s:text name="syVariable.remark" />',hidden:false},
{name:'disabled',index:'disabled',align:'center',width:'60px',label : '<s:text name="syVariable.disabled" />',hidden:false,formatter:'checkbox'},
{name:'sys',index:'sys',align:'center',width:'88px',label : '<s:text name="syVariable.sys" />',hidden:false,formatter:'checkbox'}
			],jsonReader : {
				root : "syVariables", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sn',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="syVariableList.title" />',
        	height:300,
        	gridview:true,
        	rowNum:'100',
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
		 		gridContainerResize('syVariable','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var rowNum = jQuery(this).getDataIDs().length;
           		if(rowNum <= 0) {
					 var tw = jQuery("#syVariable_gridtable").outerWidth();
					 jQuery("#syVariable_gridtable").parent().width(tw);
					 jQuery("#syVariable_gridtable").parent().height(1);

				 }
           	var dataTest = {"id":"syVariable_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("syVariable_gridtable");
       		} 

    	});
    jQuery(syVariableGrid).jqGrid('bindKeys');
    /*添加*/
	jQuery("#syVariable_gridtable_add_custom").unbind("click").bind("click",function() {
		var urlString = "editSyVariable?popup=true$navTabId=syVariable_gridtable";
		urlString = encodeURI(urlString);
		var winTitle = '<s:text name="syVariableNew.title"/>';
		$.pdialog.open(urlString,"addSyVariable",winTitle,{mask:true,width:645,height:460,maxable:true,resizable:true});
	});
	/*编辑*/
	jQuery("#syVariable_gridtable_edit_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid.length != 1) {
			alertMsg.error("请选择一条记录！");
			return;
		}
		var urlString = "editSyVariable?popup=true$navTabId=syVariable_gridtable&oper=update&variableName="+sid[0];
		urlString = encodeURI(urlString);
		var winTitle = '<s:text name="syVariableEdit.title"/>';
		$.pdialog.open(urlString,"editSyVariable",winTitle,{mask:true,width:645,height:460,maxable:true,resizable:true});
	});
	/*删除*/
	jQuery("#syVariable_gridtable_del_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid.length == 0) {
			alertMsg.error("请选择一条记录！");
			return;
		}
		for(var i = 0;i < sid.length; i++) {
			var row = jQuery(syVariableGridIdString).jqGrid('getRowData',sid[i]);
			if(row['sys'] == 'Yes') {
				alertMsg.error("系统项不能删除！");
				return;
			}
			var editUrl = "syVariableGridEdit?id=" + sid + "&navTabId=syVariable_gridtable" + "&oper=del";
			editUrl = encodeURI(editUrl);
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {}, formCallBack, "json");
				}
			});
		}
	});
	/*启用*/
	jQuery("#syVariable_gridtable_enable_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid.length != 1) {
			alertMsg.error("请选择一条记录！");
			return;
		}
		var rowId = sid[0];
		var row = jQuery(syVariableGridIdString).jqGrid('getRowData',rowId);
		if(row['disabled'] == 'No'){
			alertMsg.error("该类别已启用！");
			return;
		}
		var urlString = "syVariableGridEdit?popup=true$navTabId=syVariable_gridtable&oper=enable&variableName="+sid[0];
		urlString = encodeURI(urlString);
		alertMsg.confirm("确认启用？", {
			okCall : function() {
				$.post(urlString,function(data) {
					reloadSyVariableGrid();
				});
			}
		});
	});
	/*停用*/
	jQuery("#syVariable_gridtable_disable_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid.length != 1) {
			alertMsg.error("请选择一条记录！");
			return;
		}
		var rowId = sid[0];
		var row = jQuery(syVariableGridIdString).jqGrid('getRowData',rowId);
		if(row['disabled'] == 'Yes'){
			alertMsg.error("该类别已停用！");
			return;
		}
		var urlString = "syVariableGridEdit?popup=true$navTabId=syVariable_gridtable&oper=disable&variableName="+sid[0];
		urlString = encodeURI(urlString);
		alertMsg.confirm("确认停用？", {
			okCall : function() {
				$.post(urlString,function(data) {
					reloadSyVariableGrid();
				});
			}
		});
	});
	/*TreeSelect*/
	jQuery("#syVariable_classCode_list").treeselect({
		dataType:"sql",
		optType:"single",
		sql:"select classCode id,className name,parent=-1 from t_privilegeClass where disabled=0 order by sn",
		exceptnullparent:false,
		selectParent:false,
		lazy:false
	});
	/* //上移
	jQuery("#syVariable_gridtable_upmove_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length != 1) {
 			return;
		}
		var rowId = sid[0];
		var rowIds = jQuery(syVariableGridIdString).jqGrid("getDataIDs");
		var rowSn = jQuery.inArray(rowId, rowIds);
		var rowIdsLength = rowIds.length;
		if(rowSn == 0) {
			return;
		}
		var preRowSn = rowSn - 1;
		var preRowId = rowIds[preRowSn];
		var rowData = jQuery(syVariableGridIdString).getRowData(rowId);
		var preRowData = jQuery(syVariableGridIdString).getRowData(preRowId);
		var rowDataClone = cloneObj(rowData);
		var preRowDataClone = cloneObj(preRowData);
		jQuery(syVariableGridIdString).jqGrid("delRowData",rowId);
		jQuery(syVariableGridIdString).jqGrid("delRowData",preRowId);
		if(rowSn == rowIdsLength - 1){
    		jQuery(syVariableGridIdString).addRowData(preRowId, preRowDataClone, "last");
        	jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
    	}else{
    		var rowIdTemp = rowIds[rowSn+1];
    		jQuery(syVariableGridIdString).addRowData(preRowId, preRowDataClone, "before",rowIdTemp);
    		jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
    	}
		jQuery(syVariableGridIdString).jqGrid("setSelection",rowId);
	});
	//下移
	jQuery("#syVariable_gridtable_downmove_custom").unbind("click").bind("click",function() {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length != 1) {
 			return;
		}
		var rowId = sid[0];
    	var rowIds = jQuery(syVariableGridIdString).jqGrid("getDataIDs");
    	var rowSn = jQuery.inArray(rowId, rowIds);
    	var nextRowSn = rowSn+1;
    	var rowIdsLength = rowIds.length;
    	if(nextRowSn == rowIdsLength){
    		return ;
    	}
    	var nextRowId = rowIds[nextRowSn];
    	var rowData = jQuery(syVariableGridIdString).getRowData(rowId);
    	var nextRowData = jQuery(syVariableGridIdString).getRowData(nextRowId);
    	var rowDataClone = cloneObj(rowData);
    	var nextRowDataClone = cloneObj(nextRowData);
    	jQuery(syVariableGridIdString).delRowData(rowId);
    	jQuery(syVariableGridIdString).delRowData(nextRowId);
    	if(rowSn == 0){
    		jQuery(syVariableGridIdString).addRowData(nextRowId, nextRowDataClone, "first");
        	jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
    	}else{
    		var rowIdTemp = rowIds[rowSn-1];
    		jQuery(syVariableGridIdString).addRowData(nextRowId, nextRowDataClone, "after",rowIdTemp);
    		jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
    	}
		jQuery(syVariableGridIdString).jqGrid("setSelection",rowId);
	});
	 */
	
	
	//保存
	jQuery("#syVariable_gridtable_savemove_custom").unbind("click").bind("click",function() {
		var rowDatas = jQuery(syVariableGridIdString).jqGrid("getRowData");
		var dataStr = "";
		for(var i = 0;i < rowDatas.length; i++) {
			var row = rowDatas[i];
			var variableName = row["variableName"];
	    	var rowSn = row["sn"];
	    	dataStr += variableName + ":" + rowSn + ",";
		}
		dataStr = dataStr.substr(0,dataStr.length-1);
		var page = jQuery(syVariableGridIdString).jqGrid("getGridParam","page");
		jQuery.ajax({
			url:"saveSyVariableSn",
			data:{dataStr:dataStr},
			dataType:"json",
			type:"post",
			success:function(data) {
				if(data.statusCode == 200) {
					alertMsg.info("保存成功！");
					jQuery(syVariableGridIdString).jqGrid("setGridParam",{page:page}).trigger("reloadGrid");
				}
			}
		});
	});
  	});
	//上下移动
	function syVariableColMove(dir) {
		var sid = jQuery(syVariableGridIdString).jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length != 1) {
 			return;
		}
		var rowId = sid[0];
		var rowData = jQuery(syVariableGridIdString).getRowData(rowId);
		var sn = rowData["sn"];
    	var rowIds = jQuery(syVariableGridIdString).jqGrid("getDataIDs");
    	var rowSn = jQuery.inArray(rowId, rowIds);
    	var rowIdsLength = rowIds.length;
    	if(dir == "down") {
		    var nextRowSn = rowSn+1;
	    	if(nextRowSn == rowIdsLength){
	    		return ;
	    	}
	    	var nextRowId = rowIds[nextRowSn];
	    	var nextRowData = jQuery(syVariableGridIdString).getRowData(nextRowId);
	    	jQuery(syVariableGridIdString).jqGrid("setRowData",rowId,{sn:nextRowData["sn"]},{});
	    	jQuery(syVariableGridIdString).jqGrid("setRowData",nextRowId,{sn:sn},{});
			rowData = jQuery(syVariableGridIdString).getRowData(rowId);
	    	nextRowData = jQuery(syVariableGridIdString).getRowData(nextRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var nextRowDataClone = cloneObj(nextRowData);
	    	jQuery(syVariableGridIdString).delRowData(rowId);
	    	jQuery(syVariableGridIdString).delRowData(nextRowId);
	    	if(rowSn == 0){
	    		jQuery(syVariableGridIdString).addRowData(nextRowId, nextRowDataClone, "first");
	        	jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn-1];
	    		jQuery(syVariableGridIdString).addRowData(nextRowId, nextRowDataClone, "after",rowIdTemp);
	    		jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
	    	}
			jQuery(syVariableGridIdString).jqGrid("setSelection",rowId);
    	} else if(dir == "up") {
    		if(rowSn == 0) {
    			return;
    		}
    		var preRowSn = rowSn - 1;
    		var preRowId = rowIds[preRowSn];
	    	var preRowData = jQuery(syVariableGridIdString).getRowData(preRowId);
	    	jQuery(syVariableGridIdString).jqGrid("setRowData",rowId,{sn:preRowData["sn"]},{});
	    	jQuery(syVariableGridIdString).jqGrid("setRowData",preRowId,{sn:sn},{});
	    	rowData = jQuery(syVariableGridIdString).getRowData(rowId);
	    	preRowData = jQuery(syVariableGridIdString).getRowData(preRowId);
		    var rowDataClone = cloneObj(rowData);
	    	var preRowDataClone = cloneObj(preRowData);
	    	jQuery(syVariableGridIdString).delRowData(rowId);
	    	jQuery(syVariableGridIdString).delRowData(preRowId);
	    	if(rowSn == rowIdsLength - 1){
	    		jQuery(syVariableGridIdString).addRowData(preRowId, preRowDataClone, "last");
	        	jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
	    	}else{
	    		var rowIdTemp = rowIds[rowSn+1];
	    		jQuery(syVariableGridIdString).addRowData(preRowId, preRowDataClone, "before",rowIdTemp);
	    		jQuery(syVariableGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
	    	}
			jQuery(syVariableGridIdString).jqGrid("setSelection",rowId);
    	}
    	
	}
	function reloadSyVariableGrid() {
		jQuery("#syVariable_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div id="syVariable_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="syVariable_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='syVariable.variableName'/>:
						<input type="text" name="filter_LIKES_variableName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='syVariable.variableDesc'/>:
						<input type="text" name="filter_LIKES_variableDesc"/>
					</label>
					<label class="queryarea-label" >
						<s:text name="syVariable.classCode"></s:text>:
						<input type="text" id="syVariable_classCode_list" style="width:146px"/>
						<input type="hidden" id="syVariable_classCode_list_id" name="filter_LIKES_privaleClass.classCode"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='syVariable.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='syVariable.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<label class="queryarea-label" >
						<s:text name='syVariable.sys'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_sys"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('syVariable_search_form','syVariable_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar"  id="syVariable_buttonBar">
			<ul class="toolBar">
				<li><a id="syVariable_gridtable_add_custom" class="addbutton"
					href="javaScript:"><span><fmt:message key="button.add" />
					</span> </a></li>
				<li><a id="syVariable_gridtable_del_custom" class="delbutton"
					href="javaScript:"><span><s:text name="button.delete" /></span>
				</a></li>
				<li><a id="syVariable_gridtable_edit_custom"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>
				<li><a id="syVariable_gridtable_enable_custom"
					class="enablebutton" href="javaScript:"><span><s:text
								name="启用" /> </span> </a></li>
				<li><a id="syVariable_gridtable_disable_custom"
					class="disablebutton" href="javaScript:"><span><s:text
								name="停用" /> </span> </a></li>
				<li><a class="unfoldbutton" href="javaScript:syVariableColMove('up')"><span><s:text
								name="上移" /> </span> </a></li>
				<li><a class="foldbutton" href="javaScript:syVariableColMove('down')"><span><s:text
								name="下移" /> </span> </a></li>
				<li><a id="syVariable_gridtable_savemove_custom"
					class="savebutton" href="javaScript:"><span><s:text
								name="保存" /> </span> </a></li>
			</ul>
		</div>
		<div id="syVariable_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="syVariable_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="syVariable_gridtable_addTile">
				<s:text name="syVariableNew.title"/>
			</label> 
			<label style="display: none"
				id="syVariable_gridtable_editTile">
				<s:text name="syVariableEdit.title"/>
			</label>
			<div id="load_syVariable_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="syVariable_gridtable"></table>
			<!--<div id="syVariablePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="syVariable_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="syVariable_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="syVariable_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="syVariable_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>