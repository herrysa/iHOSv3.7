<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
jQuery(document).ready(function() {
	var modelProcessGridIdString = "#modelProcess_gridtable";
	var modelProcessGrid = jQuery(modelProcessGridIdString);
	modelProcessGrid.jqGrid({
		url : "modelProcessGridList?filter_EQS_budgetModel.modelId=${modelId}",
		editurl:"modelProcessGridEdit",
		datatype : "json",
		mtype : "GET",
    	colModel:[
		{name:'bmProcessId',index:'bmProcessId',align:'center',label : '<s:text name="modelProcess.bmProcessId" />',hidden:true,key:true},
		{name:'stepCode',index:'stepCode',align:'center',label : '<s:text name="modelProcess.stepCode" />',hidden:true},
		{name:'stepName',index:'stepName',align:'center',label : '<s:text name="modelProcess.stepName" />',hidden:false},
		{name:'roleId',index:'roleId',align:'center',label : '<s:text name="modelProcess.roleId" />',hidden:true},
		{name:'okName',index:'okName',align:'center',label : '<s:text name="modelProcess.okName" />',hidden:false},
		{name:'noName',index:'noName',align:'center',label : '<s:text name="modelProcess.noName" />',hidden:false},
		{name:'roleName',index:'roleName',align:'center',label : '<s:text name="modelProcess.roleName" />',hidden:false},
		{name:'state',index:'state',align:'center',label : '<s:text name="modelProcess.state" />',hidden:false,formatter:'integer'},
		{name:'stepInfo',index:'stepInfo',align:'center',label : '<s:text name="modelProcess.stepInfo" />',hidden:false,formatter:'checkbox'},
		{name:'unionCheck',index:'unionCheck',align:'center',label : '<s:text name="modelProcess.unionCheck" />',hidden:false,formatter:'checkbox'},
		{name:'condition',index:'condition',align:'center',label : '<s:text name="modelProcess.condition" />',hidden:true},
		{name:'remark',index:'remark',align:'center',label : '<s:text name="modelProcess.remark" />',hidden:false}
		],
    	jsonReader : {
			root : "modelProcesss", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
			// (4)
		},
    	sortname: 'state',
    	viewrecords: true,
    	sortorder: 'asc',
    	//caption:'<s:text name="modelProcessList.title" />',
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
       	//if(jQuery(this).getDataIDs().length>0){
       	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
       	// }
       	//gridContainerResize('modelProcess','div');
       	var dataTest = {"id":"modelProcess_gridtable"};
  	   	jQuery.publish("onLoadSelect",dataTest,null);
   		} 
		
	});
	jQuery("#modelProcess_gridtable_refresh").click(function(){
		$.ajax({
            url: 'refreshModelProcess?navTabId=modelProcess_gridtable&modelId=${modelId}',
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	formCallBack(data);
            }
        });
	});
	jQuery("#modelProcess_gridtable_add_c").click(function(){
		var ids = jQuery(modelProcessGridIdString).jqGrid('getDataIDs');
		var stepCode ="";
		for(var i in ids){
			var id = ids[i];
			var rowData = jQuery(modelProcessGridIdString).jqGrid('getRowData',id);
			alert(json2str(rowData));
			stepCode += rowData['stepCode']+",";
		}
		var url = "selectBmModelProcess?navTabId=modelProcess_gridtable&modelId=${modelId}&stepCode="+stepCode;
		var winTitle='选择预算审批流程';
		$.pdialog.open(url,'addBmModelProcess',winTitle, {mask:true,width : 500,height : 450});
		stopPropagation();
	});
	jQuery("#modelProcess_gridtable_del_c").click(function(){
		var sid = jQuery("#modelProcess_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid.length==0){
			alertMsg.error("请选择一条记录！");
			return;
		}
		var url = "delBmModelProcess?navTabId=modelProcess_gridtable&bmProcessId="+sid;
		$.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	formCallBack(data);
            }
        });
	});
	jQuery("#modelProcess_gridtable_delAll_c").click(function(){
		var url = "delAllBmModelProcess?navTabId=modelProcess_gridtable&modelId=${modelId}";
		$.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	formCallBack(data);
            }
        });
	});
	jQuery("#modelProcess_gridtable_edit_c").click(function(){
		var sid = jQuery("#modelProcess_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid.length==0){
			alertMsg.error("请选择一条记录！");
			return;
		}
		var url = "editBmModelProcess?navTabId=modelProcess_gridtable&bmProcessId="+sid;
		var winTitle='修改预算审批流程';
		$.pdialog.open(url,'editBmModelProcess',winTitle, {mask:true,width : 500,height : 450});
		stopPropagation();
	});
});
</script>

<div class="page" id="modelProcess_page">
	<div class="pageContent">
		<div id="modelProcess_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="modelProcess_gridtable_refresh" class="changebutton"  href="javaScript:"><span>初始化</span>
				</a>
				</li>
				<li><a id="modelProcess_gridtable_add_c" class="addbutton" href="javaScript:" ><span>添加</span>
				</a>
				</li>
				<li><a id="modelProcess_gridtable_edit_c" class="changebutton"  href="javaScript:"><span>修改</span>
				</a>
				</li>
				<li><a id="modelProcess_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="modelProcess_gridtable_delAll_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
			</ul>
		</div>
		<div id="modelProcess_gridtable_div" layoutH="90" class="grid-wrapdiv">
 			<table id="modelProcess_gridtable"></table>
		</div>
	</div>
</div>