
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() {
		var budgetModelHzGridIdString="#${random}_budgetModelHz_gridtable";
		var budgetModelHzGrid = jQuery(budgetModelHzGridIdString);
    	budgetModelHzGrid.jqGrid({
    		url : "budgetUpdataGridList?1=1&upType=3&modelType=2&filter_EQI_modelXfId.state=${state}",
    		editurl:"budgetModelXfGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'modelXfId.xfId',index:'modelXfId.xfId',align:'center',label : '<s:text name="budgetModelHz.xfId" />',hidden:true,key:true},
			{name:'updataId',index:'updataId',align:'center',label : '<s:text name="budgetModelHz.xfId" />',hidden:true},
			{name:'modelXfId.modelId.modelId',index:'modelXfId.modelId.modelId',align:'left',label : '<s:text name="budgetModelHz.modelId" />',hidden:true,width:100},
			{name:'modelXfId.modelId.modelCode',index:'modelXfId.modelId.modelCode',align:'left',label : '<s:text name="budgetModelHz.modelCode" />',hidden:false,width:100},
			{name:'modelXfId.modelId.modelName',index:'modelXfId.modelId.modelName',align:'left',label : '<s:text name="budgetModelHz.model" />',hidden:false,width:250},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetModelHz.periodYear" />',hidden:false,width:70},
			{name:'modelXfId.modelId.modelType',index:'modelId.modelTypeTxt',align:'left',label : '<s:text name="budgetModelHz.budgetType" />',hidden:false,width:120,formatter : 'select',	edittype : 'select',editoptions : {value : '1:科室填报(自下而上);2:预算汇总(自下而上);3:职能代编(自上而下)'}},
			{name:'department.name',index:'department.name',align:'left',label : '汇总部门',hidden:false,width:200},
			{name:'modelXfId.state',index:'modelXfId.state',align:'center',label : '<s:text name="budgetModelHz.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未汇总;1:汇总中;2:已汇总;3:已过期'},width:70},
			{name:'modelXfId.xfDate',index:'modelXfId.xfDate',align:'center',label : '<s:text name="budgetModelHz.HzDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'modelXfId.bmXf.state',index:'modelXfId.bmXf.state',align:'center',label : '被汇总预算状态',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未上报;1:上报中;2:已上报;3:已过期'},width:120},
			{name:'modelXfId.bmXf.xfDate',index:'modelXfId.bmXf.xfDate',align:'center',label : '被汇总预算下发时间',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:120},
			],
        	jsonReader : {
				root : "budgetUpdatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'modelXfId.xfDate',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="budgetModelHzList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				//budgetModelHzLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        	//	budgetModelHzLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	gridContainerResize('${random}_budgetModelHz','div',0,30);
           	var dataTest = {"id":"${random}_budgetModelHz_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
     		 var rowNum = jQuery(this).getDataIDs().length;
     		 var ret = jQuery(this).jqGrid('getRowData');
     	     if(rowNum > 0){
     	    	 var rowIds = jQuery(this).getDataIDs();
     	    	 var i=0;
     	    	 for (i=0;i<rowNum;i++){
     	    		var id = rowIds[i];
     	    	 	var hrefUrl = "budgetUpdataList?xfId="+id;
			    	var state = ret[i]["modelXfId.state"];
			    	var xfstate = ret[i]["modelXfId.bmXf.state"];
			    	if(state=="1"){
              		  	setCellText(this,id,'modelXfId.state','<span style="color:red" >汇总中</span>');
              	  	}else if(state=="2"){
              		  	setCellText(this,id,'modelXfId.state','<span style="color:blue" >已汇总</span>');
              	  	}else if(state=="3"){
              		  	setCellText(this,id,'modelXfId.state','<span style="color:gray" >已过期</span>');
              	  	}
			    	if(xfstate=="1"){
              		  	setCellText(this,id,'modelXfId.bmXf.state','<span style="color:red" >上报中</span>');
              	  	}else if(xfstate=="2"){
              		  	setCellText(this,id,'modelXfId.bmXf.state','<span style="color:blue" >已上报</span>');
              	  	}else if(xfstate=="3"){
              		  	setCellText(this,id,'modelXfId.bmXf.state','<span style="color:gray" >已过期</span>');
              	  	}
			    	<c:forEach items="${bsStepList}" var="pc">
			    	var cellText = ret[i]["stepMap.${pc.stepCode }"];
			    	setCellText(jQuery(this)[0],id,'stepMap.${pc.stepCode }','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=${pc.state }\')" target="navTab">'+cellText+"</a>")
					</c:forEach>
			    	var modelId = ret[i]["modelXfId.modelId.modelId"];
			    	var hrefUrlModelName = "editBudgetModelReport?reportType=1&modelId="+modelId;
			    	var cellTextModelName = ret[i]["modelXfId.modelId.modelName"];
			    	setCellText(jQuery(this)[0],id,'modelXfId.modelId.modelName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="modelReportZhuanqu(\''+hrefUrlModelName+'\')" target="navTab">'+cellTextModelName+"</a>");
     	    	 }
     	     }
       		} 

    	});
    jQuery("#${random}_budgetModelHz_gridtable_refresh").click(function(){
    	$.get("budgetModelXfRefresh", {
			"_" : $.now(),modelType:2,navTabId:'${random}_budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#${random}_budgetModelHz_gridtable_xf").click(function(){
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	$.get("budgetModel_Xf", {
			"_" : $.now(),xfId:sid,navTabId:'${random}_budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    jQuery("#${random}_budgetModelHz_gridtable_reXf").click(function(){
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认重新汇总？重新汇总后,之前汇总的数据将作废！", {
			okCall: function(){
				jQuery.post("budgetModel_Xf", {
					"_" : $.now(),xfId:sid,xfType:'2',navTabId:'${random}_budgetModelHz_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    
    jQuery("#${random}_budgetModelHz_gridtable_hzReport").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0||sid.length>1){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	var rowData = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getRowData',sid);
    	var updataId = rowData['updataId'];
    	$.pdialog.open('openBmReport?reportType=1&updataId='+updataId+'&modelType=2','bmReport',"预算汇总", {mask:true,width : fullWidth,height : fullHeight});
      	});
    
    
    jQuery("#${random}_budgetModelHz_gridtable_comfirm").click(function(){
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	var rowData = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getRowData',sid);
    	var updataId = rowData['updataId'];
    	$.get("confirmBmUpdata", {
			"_" : $.now(),updataId:updataId,navTabId:'${random}_budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#${random}_budgetModelHz_gridtable_del_c").click(function(){
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	for(var i in sid){
    		var id = sid[i];
	    	var rowData = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getRowData',id);
	    	var state = rowData["state"];
	    	if(state==1||state==2){
	    		alertMsg.error("只能删除未汇总或已过期的汇总数据！");
	    		return ;
	    	}
    	}
    	alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post("budgetModelXfDel", {
					"_" : $.now(),id:sid,xfType:2,navTabId:'${random}_budgetModelHz_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    
    jQuery("#${random}_budgetModelHz_gridtable_gq").click(function(){
    	var sid = jQuery("#${random}_budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认作废？", {
			okCall: function(){
				jQuery.post("budgetModel_gq", {
					"_" : $.now(),xfId:sid,navTabId:'${random}_budgetModelHz_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    
    });
    
	function setCellText(grid,rowid,colName,cellTxt){
		 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
        for (; iCol<cCol; iCol++) {
            if (cm[iCol].name === colName) {
                tr = grid.rows.namedItem(rowid);
                if (tr) {
                   jQuery(tr.cells[iCol]).html(cellTxt);
                }
                break;
            }
        }
		
	}
	function bmUpdataZhuanqu(url){
		$.pdialog.open(url, "bmupdataList", "上报科室明细",{width:800,height:600});
	}
	function modelReportZhuanqu(url){
		var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
		url = encodeURI(url);
    	$.pdialog.open(url, 'showBudgetReport', "查看预算模板", {
    		mask : true,
    		maxable : true,
    		resizable : true,
    		width : fullWidth,
    		height : fullHeight
    	});
	}
</script>

<div class="page">
	<div id="${random}_budgetModelHz_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}_budgetModelHz_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.modelCode'/>:
						<input type="text" name="filter_LIKES_modelXfId.modelId.modelCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						模板名称:
						<input type="text" name="filter_LIKES_modelXfId.modelId.modelName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.HzDate'/>:从
						<input type="text" name="filter_GED_modelXfId.xfDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
						到&nbsp;<input type="text" name="filter_LED_modelXfId.xfDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.state'/>:
						<s:select list="#{'0':'未汇总','1':'汇总中','2':'已汇总','3':'已过期' }" name="filter_EQI_state" headerKey="" headerValue="--"></s:select>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}_budgetModelHz_search_form','${random}_budgetModelHz_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="${random}_budgetModelHz_buttonBar" class="panelBar">
			<ul class="toolBar">
				<s:if test="state==0">
				<li><a id="${random}_budgetModelHz_gridtable_refresh" class="initbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="${random}_budgetModelHz_gridtable_xf" class="settlebutton"  href="javaScript:"><span>汇总</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==1||state==2">
				<li><a id="${random}_budgetModelHz_gridtable_reXf" class="resettlebutton"  href="javaScript:"
					><span>重新汇总
					</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==1||state==2">
				<li><a id="${random}_budgetModelHz_gridtable_gq" class="closebutton"  href="javaScript:"
					><span>作废
					</span>
				</a>
				</li>
				</s:if>
				<li><a id="${random}_budgetModelHz_gridtable_hzReport" class="previewbutton"  href="javaScript:"
					><span>查看汇总表
					</span>
				</a>
				</li>
				<s:if test="needBmHzCheckProcess==1&&state==1">
					<li><a id="${random}_budgetModelHz_gridtable_comfirm" class="submitbutton" href="javaScript:" ><span>提交审批</span></a></li>
				</s:if>
				<s:if test="state==0||state==3">
				<li><a id="${random}_budgetModelHz_gridtable_del_c" class="delbutton"  href="javaScript:"
					><span>删除
					</span>
				</a>
				</li>
				</s:if>
			</ul>
		</div>
		<div id="${random}_budgetModelHz_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_budgetModelHz_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_budgetModelHz_gridtable_addTile">
				<s:text name="budgetModelHzNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_budgetModelHz_gridtable_editTile">
				<s:text name="budgetModelHzEdit.title"/>
			</label>
			<div id="load_${random}_budgetModelHz_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_budgetModelHz_gridtable"></table>
			<!--<div id="budgetModelHzPager"></div>-->
		</div>
	</div>
	<div id="${random}_budgetModelHz_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_budgetModelHz_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_budgetModelHz_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_budgetModelHz_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>