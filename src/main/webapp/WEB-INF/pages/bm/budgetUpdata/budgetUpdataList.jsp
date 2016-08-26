
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetUpdataGridIdString${random}="#${random}budgetUpdata_gridtable";
	jQuery(document).ready(function() { 
		var budgetUpdataGrid${random} = jQuery(budgetUpdataGridIdString${random});
		var upType = "${upType}";
		var url = "budgetUpdataGridList?1=1";
		var modelType = 1;
		if("${modelType}"){
			modelType = "${modelType}";
		}
		if(upType=="1"){
			url += "&upType="+upType+"&stepCode=${stepCode}&filter_EQS_modelXfId.modelId.modelType="+modelType;
		}else if(upType=="2"){
			url += "&upType="+upType+"&filter_EQS_modelXfId.modelId.modelType="+modelType;
		}else{
			url += "&xfId=${xfId}&state=${state}";
		}
		if("${bmCheckProcessCode}"){
			url += "&bmCheckProcessCode=${bmCheckProcessCode}"
		}
		budgetUpdataGrid${random}.jqGrid({
    		url : url,
    		editurl:"budgetUpdataGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'updataId',index:'updataId',align:'center',label : '<s:text name="budgetUpdata.updataId" />',hidden:true,key:true},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetUpdata.periodYear" />',hidden:false,width:70},
			{name:'modelXfId.modelId.modelCode',index:'modelXfId.modelId.modelCode',align:'left',label : '<s:text name="budgetUpdata.modelCode" />',hidden:false,width:100},
			{name:'modelXfId.modelId.modelName',index:'modelXfId.modelId.modelName',align:'left',label : '<s:text name="budgetUpdata.model" />',hidden:false,width:250},
			{name:'modelXfId.modelId.modelTypeTxt',index:'modelXfId.modelId.modelTypeTxt',align:'left',label : '<s:text name="budgetUpdata.budgetType" />',hidden:false,width:70},
			{name:'modelXfId.modelId.periodType',index:'modelXfId.modelId.periodType',align:'left',label : '<s:text name="budgetUpdata.periodType" />',hidden:false,width:70},
			{name:'department.name',index:'department.name',align:'left',label : '<s:text name="budgetUpdata.department" />',hidden:false},
			<c:forEach items="${processColumns}" var="pc">
			{
				name : "checkMap.${pc.code }",
				index : "checkMap.${pc.code }",
				label : "${pc.name }",
				width : 150,
				sortable:false,
				/* <c:if test="${dc.columnType=='varchar'}">
					formatter:stringFormatter,
				</c:if> */
				<c:if test="${pc.dataType=='date'}">
					align:"center",
					formatter:'date',formatoptions:{newformat : 'Y-m-d'}
				</c:if>
			},
			</c:forEach>
			],
        	jsonReader : {
				root : "budgetUpdatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'department.internalCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	grouping:true,
    	   	groupingView : {
    	   		groupField : ['modelXfId.modelId.modelName'],
    	   		//groupSummary : [true],
    			groupColumnShow : [true],
    			groupText : ['<b>{0}</b> (记录数:{1}) '],
    			groupDataSorted :[false]
    	   	},
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
           	var extraHeight = 35;
           	if("${upType}"==2){
           		extraHeight = 1;
           	}
           	//gridContainerResize('${random}budgetUpdata','div',0,extraHeight);
           	var dataTest = {"id":"${random}budgetUpdata_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
		jQuery("#${random}budgetUpdata_gridtable_ok").click(function(){
			var state = "${businessProcessStep.state }";
			var sid = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getGridParam','selarrrow');
	    	if(sid.length==0){
	    		alertMsg.error("请选择要审核的预算记录！");
	    		return ;
	    	}
	    	$.get("changeUpdataState", {
				"_" : $.now(),updataId:sid,state:state,navTabId:'${random}budgetUpdata_gridtable'
			}, function(data) {
				formCallBack(data);
			});
		});
		jQuery("#${random}stepInfoDiv_confirm").click(function(){
			var bmProcessId = jQuery("#${random}stepInfoDiv").data("bmProcessId");
			var opt = jQuery("#${random}stepInfoDiv").data("opt");
			var info = jQuery("textarea[name='stepInfoDiv_info']",".dialogContent").val();
			dealBmProcessFunction(bmProcessId,opt,info);
			$.pdialog.closeCurrentDiv('${random}stepInfoDiv');
		});
		jQuery("#${random}budgetUpdata_gridtable_groupByDept").click(function(){
	    	var txt = jQuery(this).find("span").eq(0).text();
	    	var optType = "审批";
	    	if("${upType}"!=1){
	    		optType = "查询";
	    	}
	    	if(txt.indexOf("部门")!=-1){
	    		jQuery('#${random}budgetUpdata_gridtable').jqGrid('setGridParam', {
	    			groupingView : {
	        	   		groupField : ['department.name']}
	    		}).trigger("reloadGrid");
	        	jQuery(this).find("span").eq(0).text("按预算模板"+optType);
	    	}else{
	    		jQuery('#${random}budgetUpdata_gridtable').jqGrid('setGridParam', {
	    			groupingView : {
	        	   		groupField : ['modelXfId.modelId.modelName']}
	    		}).trigger("reloadGrid");
	        	jQuery(this).find("span").eq(0).text("按预算部门"+optType);
	    	}
	    	
	    });
	    jQuery("#${random}budgetUpdata_gridtable_log").click(function(){
			var sid = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
	        var url = "bmModelProcessLogList?navTabId=${random}budgetUpdata_gridtable&updataId="+sid;
			var winTitle='预算审批日志';
			$.pdialog.open(url,'bmCheckLog',winTitle, {mask:true,width : 730,height : 450});
	    });
	    
	    jQuery("#${random}budgetUpdata_gridtable_showReport").click(function(){
			var sid = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var fullHeight = jQuery("#container").innerHeight();
	    	var fullWidth = jQuery("#container").innerWidth();
			$.pdialog.open('openBmReport?reportType=show&updataId='+sid,'bmReport',"预算上报", {mask:true,width : fullWidth,height : fullHeight});
	    });
  	});
	function bmProcessFuction(opt){
		var stepCode = "${businessProcessStep.stepCode }";
		var sid = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择要审核的预算记录！");
    		return ;
    	}
    	var modelCode = null;
    	for(var i in sid){
    		var id = sid[i];
    		var rowData = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getRowData',id);
    		var newModelCode = rowData['modelXfId.modelId.modelCode'];
    		if(!modelCode){
    			modelCode = newModelCode;
    		}else{
    			if(modelCode!= newModelCode){
    				alertMsg.error("一次只能审核一种预算模板的记录！");
    				return ;
    			}
    		}
    	}
    	
    	$.get("findBmModelProcess", {
			"_" : $.now(),modelCode:modelCode,stepCode:stepCode
		}, function(data) {
			//formCallBack(data);
			if(data.maprs!=null){
				var stepInfo = data.maprs.stepInfo;
				var bmProcessId = data.maprs.bmProcessId;
				/* if(opt=='ok'){
					bmProcessId = data.bmModelProcess.okStep.bmProcessId;
				}else{
					bmProcessId = data.bmModelProcess.noStep.bmProcessId;
				} */
				if(stepInfo==true){
					var url = "#DIA_inline?inlineId=${random}stepInfoDiv";
					jQuery("#${random}stepInfoDiv").data("bmProcessId",bmProcessId);
					jQuery("#${random}stepInfoDiv").data("opt",opt);
					$.pdialog.open(url, 'importSearch', "审核原因", {
						mask : false,
						width : 400,
						height : 200
					});
				}else{
					dealBmProcessFunction(bmProcessId,opt);
				}
			}
		});
	}
	
	function dealBmProcessFunction(bmProcessId,opt,info){
		if(!info){
			info = "";
		}
		alertMsg.confirm("确认操作？", {
			okCall: function(){
				var sid = jQuery("#${random}budgetUpdata_gridtable").jqGrid('getGridParam','selarrrow');
				$.get("optUpdataState", {
					"_" : $.now(),bmProcessId:bmProcessId,opt:opt,updataId:sid,message:info,navTabId:'${random}budgetUpdata_gridtable'
				}, function(data) {
					formCallBack(data);
				});
			}
		});
		
	}
</script>

<div class="page">
	<div id="${random}budgetUpdata_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}budgetUpdata_search_form" >
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.checkDate'/>:
						<input type="text" name="filter_EQS_checkDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.checker'/>:
						<input type="text" name="filter_EQS_checker"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						预算模板编码:
						<s:if test="budgetModel!=null">
							<input type="text" name="filter_LIKES_modelXfId.modelId.modelCode" readonly="true" value="${budgetModel.modelCode}"/>
						</s:if>
						<s:else>
							<input type="text" name="filter_LIKES_modelXfId.modelId.modelCode"/>
						</s:else>
					</label>
					<label style="float:none;white-space:nowrap" >
						预算模板名称:
						<s:if test="budgetModel!=null">
							<input type="text" name="filter_LIKES_modelXfId.modelId.modelName" readonly="true" value="${budgetModel.modelName}"/>
						</s:if>
						<s:else>
							<input type="text" name="filter_LIKES_modelXfId.modelId.modelName"/>
						</s:else>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.department'/>:
						<input type="text" name="filter_LIKES_department.name"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.operator'/>:
						<input type="text" name="filter_EQS_operator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.optDate'/>:
						<input type="text" name="filter_EQS_optDate"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.submitDate'/>:
						<input type="text" name="filter_EQS_submitDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='${random}budgetUpdata.submitter'/>:
						<input type="text" name="filter_EQS_submitter"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}budgetUpdata_search_form','${random}budgetUpdata_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="${random}budgetUpdata_buttonBar" class="panelBar">
			<ul class="toolBar">
				<%-- <li><a id="${random}budgetUpdata_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li> --%>
				<%-- <s:if test="upType==1">
				<li><a id="${random}budgetUpdata_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				</s:if> --%>
				<s:if test="upType==1">
					<li><a id="${random}budgetUpdata_gridtable_ok" class="checkbutton"  href="javaScript:bmProcessFuction('ok')"><span>${businessProcessStep.okName }</span>
					</a>
					</li>
					<li><a id="${random}budgetUpdata_gridtable_no" class="delbutton"  href="javaScript:bmProcessFuction('no')"><span>${businessProcessStep.noName }</span>
					</a>
					</li>
					<li><a id="${random}budgetUpdata_gridtable_showReport" class="reportbutton"  href="javaScript:"><span>查看报表</span>
					</a>
					</li>
				</s:if>
				<s:elseif test="upType==2">
					<li><a id="${random}budgetUpdata_gridtable_log" class="openbutton"  href="javaScript:bmProcessFuction('no')"><span>审批日志</span>
					</a>
					</li>
					<li><a id="${random}budgetUpdata_gridtable_showReport" class="reportbutton"  href="javaScript:"><span>查看报表</span>
					</a>
					</li>
				</s:elseif>
				<s:elseif test="upType==1||upType==2">
				<li><a id="${random}budgetUpdata_gridtable_groupByDept" class="settingbutton"  href="javaScript:"><span>按预算部门<s:if test="upType==1">审批</s:if><s:else>查询</s:else></span>
				</a>
				</li>
				</s:elseif>
				<%-- <li><a id="${random}budgetUpdata_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="${random}budgetUpdata_gridtable_div" layoutH=90 class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}budgetUpdata_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}budgetUpdata_gridtable_addTile">
				<s:text name="budgetUpdataNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}budgetUpdata_gridtable_editTile">
				<s:text name="budgetUpdataEdit.title"/>
			</label>
			<div id="load_${random}budgetUpdata_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}budgetUpdata_gridtable"></table>
			<!--<div id="${random}budgetUpdataPager"></div>-->
		</div>
	</div>
	<div id="${random}budgetUpdata_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}budgetUpdata_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}budgetUpdata_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}budgetUpdata_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	<div id="${random}stepInfoDiv" style="display: none">
		<form>
			<div class="pageContent">
				<form id="${random}stepInfoDiv_form" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="50">
				<s:textarea name="stepInfoDiv_info" style="height:90%;width:90%"></s:textarea>
				</div>
				</form>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button id="${random}stepInfoDiv_confirm" type="button">确定</button>
							</div>
						</div>
					</li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>