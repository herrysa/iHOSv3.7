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
		{name:'bmProcessId',index:'bmProcessId',align:'left',label : '<s:text name="modelProcess.bmProcessId" />',hidden:true,key:true},
		{name:'stepCode',index:'stepCode',align:'left',label : '<s:text name="modelProcess.stepCode" />',hidden:true,width:100},
		{name:'stepName',index:'stepName',align:'left',label : '<s:text name="modelProcess.stepName" />',hidden:false,width:100},
		{name:'roleId',index:'roleId',align:'left',label : '<s:text name="modelProcess.roleId" />',hidden:true,width:100},
		{name:'okName',index:'okName',align:'left',label : '<s:text name="modelProcess.okName" />',hidden:false,width:100},
		{name:'noName',index:'noName',align:'left',label : '<s:text name="modelProcess.noName" />',hidden:false,width:100},
		{name:'okStep.stepName',index:'okStep.stepName',align:'left',label : '<s:text name="modelProcess.okStep" />',hidden:false,width:100},
		{name:'noStep.stepName',index:'noStep.stepName',align:'left',label : '<s:text name="modelProcess.noStep" />',hidden:false,width:100},
		{name:'roleName',index:'roleName',align:'left',label : '<s:text name="modelProcess.roleName" />',hidden:false,width:100},
		{name:'checkDeptName',index:'checkDeptName',align:'left',label : '<s:text name="modelProcess.checkDept" />',hidden:false,width:100},
		{name:'checkDeptName',index:'checkDeptName',align:'left',label : '<s:text name="modelProcess.checkDept" />',hidden:false,width:100},
		{name:'checkPersonName',index:'checkPersonName',align:'left',label : '<s:text name="modelProcess.checkPerson" />',hidden:false,width:100},
		{name:'state',index:'state',align:'right',label : '<s:text name="modelProcess.state" />',hidden:false,formatter:'integer',width:60},
		{name:'isEnd',index:'isEnd',align:'center',label : '<s:text name="modelProcess.isEnd" />',hidden:false,formatter:'checkbox',width:70},
		{name:'condition',index:'condition',align:'center',label : '<s:text name="modelProcess.condition" />',hidden:false,width:100},
		{name:'bmDept',index:'bmDept',align:'center',label : '<s:text name="modelProcess.bmDept" />',hidden:false,formatter:'checkbox',width:70},
		{name:'unionCheck',index:'unionCheck',align:'center',label : '<s:text name="modelProcess.unionCheck" />',hidden:false,formatter:'checkbox',width:70},
		{name:'stepInfo',index:'stepInfo',align:'center',label : '<s:text name="modelProcess.stepInfo" />',hidden:false,formatter:'checkbox',width:70},
		{name:'remark',index:'remark',align:'left',label : '<s:text name="modelProcess.remark" />',hidden:false,width:200}
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
		shrinkToFit:false,
		autowidth:false,
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
		if("${budgetModel.disabled}"=="false"){
			alertMsg.error("模板在启用中，不能修改流程！");
			return ;
		}
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
		if("${budgetModel.disabled}"=="false"){
			alertMsg.error("模板在启用中，不能修改流程！");
			return ;
		}
		var ids = jQuery(modelProcessGridIdString).jqGrid('getDataIDs');
		var stepCode ="";
		for(var i in ids){
			var id = ids[i];
			var rowData = jQuery(modelProcessGridIdString).jqGrid('getRowData',id);
			stepCode += rowData['stepCode']+",";
		}
		var url = "selectBmModelProcess?navTabId=modelProcess_gridtable&modelId=${modelId}&stepCode="+stepCode;
		var winTitle='选择预算审批流程';
		$.pdialog.open(url,'addBmModelProcess',winTitle, {mask:true,width : 600,height : 450});
		stopPropagation();
	});
	jQuery("#modelProcess_gridtable_del_c").click(function(){
		if("${budgetModel.disabled}"=="false"){
			alertMsg.error("模板在启用中，不能修改流程！");
			return ;
		}
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
		if("${budgetModel.disabled}"=="false"){
			alertMsg.error("模板在启用中，不能修改流程！");
			return ;
		}
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
		if("${budgetModel.disabled}"=="false"){
			alertMsg.error("模板在启用中，不能修改流程！");
			return ;
		}
		var sid = jQuery("#modelProcess_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid.length==0){
			alertMsg.error("请选择一条记录！");
			return;
		}
		var url = "editBmModelProcess?navTabId=modelProcess_gridtable&bmProcessId="+sid;
		var winTitle='修改预算审批流程';
		$.pdialog.open(url,'editBmModelProcess',winTitle, {mask:true,width : 600,height : 500});
		stopPropagation();
	});
	jQuery("#modelProcess_gridtable_process_check").click(function(){
		var url = "checkBmModelProcess?navTabId=modelProcess_gridtable&modelId=${modelId}";
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
});
</script>

<div class="page" id="modelProcess_page">
	<div class="pageContent">
		<div id="modelProcess_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="modelProcess_gridtable_refresh" class="initbutton"  href="javaScript:"><span>初始化</span>
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
				<li><a id="modelProcess_gridtable_delAll_c" class="delbutton"  href="javaScript:"><span>全部删除</span>
				</a>
				</li>
				<!-- <li><a id="modelProcess_gridtable_process_check" class="delbutton"  href="javaScript:"><span>流程检查</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="modelProcess_gridtable_div" layoutH="63" class="grid-wrapdiv">
 			<table id="modelProcess_gridtable"></table>
		</div>
	</div>
</div>