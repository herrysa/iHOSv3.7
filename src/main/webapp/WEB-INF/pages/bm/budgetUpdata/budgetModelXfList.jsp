
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var budgetModelXfGridIdString="#${random}_budgetModelXf_gridtable";
		var budgetModelXfGrid = jQuery(budgetModelXfGridIdString);
		
    	budgetModelXfGrid.jqGrid({
    		url : "budgetModelXfGridList?filter_EQS_modelId.modelType=1&filter_EQI_state=${state}",
    		editurl:"budgetModelXfGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'xfId',index:'xfId',align:'center',label : '<s:text name="budgetModelXf.xfId" />',hidden:true,key:true},
			{name:'modelId.modelId',index:'modelId.modelId',align:'left',label : '<s:text name="budgetModelXf.modelId" />',hidden:true,width:100},
			{name:'modelId.modelCode',index:'modelId.modelCode',align:'left',label : '<s:text name="budgetModelXf.modelCode" />',hidden:false,width:100},
			{name:'modelId.modelName',index:'modelId.modelName',align:'left',label : '<s:text name="budgetModelXf.model" />',hidden:false,width:200},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetModelXf.periodYear" />',hidden:false,width:70},
			{name:'modelId.modelType',index:'modelId.modelType',align:'left',label : '<s:text name="budgetModelXf.budgetType" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '1:科室填报(自下而上);2:预算汇总(自下而上);3:职能代编(自上而下)'},width:150},
			{name:'state',index:'state',align:'center',label : '<s:text name="budgetModelXf.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未上报;1:上报中;2:已上报;3:已过期'},width:70},
			{name:'xfDate',index:'xfDate',align:'center',label : '<s:text name="budgetModelXf.xfDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:70},
			{name:'xfNum',index:'xfNum',align:'right',label : '<s:text name="budgetModelXf.xfNum" />',hidden:false,width:60},
			<c:forEach items="${bsStepList}" var="pc">
			{
				name : "stepMap.${pc.stepCode }",
				index : "stepMap.${pc.stepCode }",
				label : "${pc.stepName }",
				width : 120,
				sortable:false,
				align:"right"
			},
			</c:forEach>
			],
        	jsonReader : {
				root : "budgetModelXfs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'xfDate',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="budgetModelXfList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				//budgetModelXfLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        	//	budgetModelXfLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	gridContainerResize('${random}_budgetModelXf','div',0,30);
           	var dataTest = {"id":"${random}_budgetModelXf_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
     		 var rowNum = jQuery(this).getDataIDs().length;
     		 var ret = jQuery(this).jqGrid('getRowData');
     	     if(rowNum > 0){
     	    	 var rowIds = jQuery(this).getDataIDs();
     	    	 var i=0;
     	    	 for (i=0;i<rowNum;i++){
     	    		var id = rowIds[i];
     	    	 	var hrefUrl = "budgetUpdataList?xfId="+id;
			    	var state = ret[i]["state"];
			    	if(state=="1"){
              		  	setCellText(this,id,'state','<span style="color:red" >上报中</span>');
              	  	}else if(state=="2"){
              		  	setCellText(this,id,'state','<span style="color:blue" >已上报</span>');
              	  	}else if(state=="3"){
              		  	setCellText(this,id,'state','<span style="color:gray" >已过期</span>');
              	  	}
			    	<c:forEach items="${bsStepList}" var="pc">
			    	var cellText = ret[i]["stepMap.${pc.stepCode }"];
			    	setCellText(jQuery(this)[0],id,'stepMap.${pc.stepCode }','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=${pc.state }\')" target="navTab">'+cellText+"</a>")
					</c:forEach>
			    	var modelId = ret[i]["modelId.modelId"];
			    	var hrefUrlModelName = "editBudgetModelReport?reportType=1&modelId="+modelId;
			    	var cellTextModelName = ret[i]["modelId.modelName"];
			    	setCellText(jQuery(this)[0],id,'modelId.modelName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="modelReportZhuanqu(\''+hrefUrlModelName+'\')" target="navTab">'+cellTextModelName+"</a>");
     	    	 }
     	     }
       		} 

    	});
    
    jQuery("#${random}_budgetModelXf_gridtable_refresh").click(function(){
    	$.get("budgetModelXfRefresh", {
			"_" : $.now(),modelType:'1',navTabId:'${random}_budgetModelXf_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#${random}_budgetModelXf_gridtable_xf").click(function(){
    	var sid = jQuery("#${random}_budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	$.get("budgetModel_Xf", {
			"_" : $.now(),xfId:sid,navTabId:'${random}_budgetModelXf_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    jQuery("#${random}_budgetModelXf_gridtable_reXf").click(function(){
    	var sid = jQuery("#${random}_budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认重新下发？重新下发后,之前填报的数据将作废！", {
			okCall: function(){
				jQuery.post("budgetModel_Xf", {
					"_" : $.now(),xfId:sid,xfType:'2',navTabId:'${random}_budgetModelXf_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    
    jQuery("#${random}_budgetModelXf_gridtable_gq").click(function(){
    	var sid = jQuery("#${random}_budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认作废？", {
			okCall: function(){
				jQuery.post("budgetModel_gq", {
					"_" : $.now(),xfId:sid,navTabId:'${random}_budgetModelXf_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    jQuery("#${random}_budgetModelXf_gridtable_del_c").click(function(){
    	var sid = jQuery("#${random}_budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	for(var i in sid){
    		var id = sid[i];
	    	var rowData = jQuery("#${random}_budgetModelXf_gridtable").jqGrid('getRowData',id);
	    	var state = rowData["state"];
	    	if(state==1||state==2){
	    		alertMsg.error("只能删除未上报或已过期的下发数据！");
	    		return ;
	    	}
    	}
    	alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post("budgetModelXfDel?oper=del", {
					"_" : $.now(),id:sid,navTabId:'${random}_budgetModelXf_gridtable'
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
		$.pdialog.open(url, "bmupdataList", "上报状况明细",{width:800,height:600});
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
	<div id="${random}_budgetModelXf_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}_budgetModelXf_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.modelCode'/>:
						<input type="text" name="filter_LIKES_modelId.modelCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						模板名称:
						<input type="text" name="filter_LIKES_modelId.modelName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.xfDate'/>:从
						<input type="text" name="filter_GED_xfDate" name="filter_GED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
						到&nbsp;<input type="text" name="filter_LED_xfDate" name="filter_GED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.state'/>:
						<s:select list="#{'0':'未上报','1':'上报中','2':'已上报','3':'已过期' }" name="filter_EQI_state" headerKey="" headerValue="--"></s:select>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						年度:
						<s:select list="yearList" headerKey="" headerValue="--" name="filter_EQS_periodYear" listKey="periodYear" listValue="periodYear"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}_budgetModelXf_search_form','${random}_budgetModelXf_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="${random}_budgetModelXf_buttonBar" class="panelBar">
			<ul class="toolBar">
				<s:if test="state==0">
				<li><a id="${random}_budgetModelXf_gridtable_refresh" class="initbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="${random}_budgetModelXf_gridtable_xf" class="settlebutton"  href="javaScript:"><span>下发</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==1||state==2">
				<li><a id="${random}_budgetModelXf_gridtable_reXf" class="resettlebutton"  href="javaScript:"
					><span>重新下发
					</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==1||state==2">
				<li><a id="${random}_budgetModelXf_gridtable_gq" class="closebutton"  href="javaScript:"
					><span>作废
					</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==0||state==3">
				<li><a id="${random}_budgetModelXf_gridtable_del_c" class="delbutton"  href="javaScript:"
					><span>删除
					</span>
				</a>
				</li>
				</s:if>
			</ul>
		</div>
		<div id="${random}_budgetModelXf_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_budgetModelXf_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_budgetModelXf_gridtable_addTile">
				<s:text name="budgetModelXfNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_budgetModelXf_gridtable_editTile">
				<s:text name="budgetModelXfEdit.title"/>
			</label>
			<div id="load_${random}_budgetModelXf_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_budgetModelXf_gridtable"></table>
			<!--<div id="budgetModelXfPager"></div>-->
		</div>
	</div>
	<div id="${random}_budgetModelXf_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_budgetModelXf_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_budgetModelXf_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_budgetModelXf_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
</div>
</div>