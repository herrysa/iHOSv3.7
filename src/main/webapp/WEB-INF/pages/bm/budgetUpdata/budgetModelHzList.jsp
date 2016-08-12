
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetModelHzLayout;
	var budgetModelHzGridIdString="#budgetModelHz_gridtable";
	
	var budgetModelHzLayout;
	jQuery(document).ready(function() {
		var budgetModelHzGrid = jQuery(budgetModelHzGridIdString);
    	budgetModelHzGrid.jqGrid({
    		url : "budgetModelXfGridList?filter_EQB_modelId.isHz=true",
    		editurl:"budgetModelXfGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'xfId',index:'xfId',align:'center',label : '<s:text name="budgetModelHz.xfId" />',hidden:true,key:true},
			{name:'updataId',index:'updataId',align:'center',label : '<s:text name="budgetModelHz.xfId" />',hidden:true},
			{name:'modelId.modelCode',index:'modelId.modelCode',align:'left',label : '<s:text name="budgetModelHz.modelCode" />',hidden:false,width:100},
			{name:'modelId.modelName',index:'modelId.modelName',align:'left',label : '<s:text name="budgetModelHz.model" />',hidden:false,width:250},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetModelHz.periodYear" />',hidden:false,width:70},
			{name:'modelId.modelTypeTxt',index:'modelId.modelTypeTxt',align:'left',label : '<s:text name="budgetModelHz.budgetType" />',hidden:false,width:100},
			{name:'deptName',index:'deptName',align:'left',label : '汇总部门',hidden:false,width:200},
			{name:'state',index:'state',align:'center',label : '<s:text name="budgetModelHz.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未汇总;1:汇总中;2:已汇总;3:已过期'},width:70},
			{name:'xfDate',index:'xfDate',align:'center',label : '<s:text name="budgetModelHz.HzDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'bmXf.state',index:'bmXf.state',align:'center',label : '被汇总预算状态',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未上报;1:上报中;2:已上报;3:已过期'},width:120},
			{name:'bmXf.xfDate',index:'bmXf.xfDate',align:'center',label : '被汇总预算下发时间',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:120},
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
           	gridContainerResize('budgetModelHz','div');
           	var dataTest = {"id":"budgetModelHz_gridtable"};
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
			    	var xfstate = ret[i]["bmXf.state"];
			    	if(state=="1"){
              		  	setCellText(this,id,'state','<span style="color:red" >汇总中</span>');
              	  	}else if(state=="2"){
              		  	setCellText(this,id,'state','<span style="color:blue" >已汇总</span>');
              	  	}else if(state=="3"){
              		  	setCellText(this,id,'state','<span style="color:gray" >已过期</span>');
              	  	}
			    	if(xfstate=="1"){
              		  	setCellText(this,id,'bmXf.state','<span style="color:red" >上报中</span>');
              	  	}else if(xfstate=="2"){
              		  	setCellText(this,id,'bmXf.state','<span style="color:blue" >已上报</span>');
              	  	}else if(xfstate=="3"){
              		  	setCellText(this,id,'bmXf.state','<span style="color:gray" >已过期</span>');
              	  	}
			    	<c:forEach items="${bsStepList}" var="pc">
			    	var cellText = ret[i]["stepMap.${pc.stepCode }"];
			    	setCellText(jQuery(this)[0],id,'stepMap.${pc.stepCode }','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=${pc.state }\')" target="navTab">'+cellText+"</a>")
					</c:forEach>
     	    	 }
     	     }
       		} 

    	});
    jQuery(budgetModelHzGrid).jqGrid('bindKeys');
    
    jQuery("#budgetModelHz_gridtable_refresh").click(function(){
    	$.get("budgetModelXfRefresh", {
			"_" : $.now(),xfType:2,navTabId:'budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#budgetModelHz_gridtable_xf").click(function(){
    	var sid = jQuery("#budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	$.get("budgetModel_Xf", {
			"_" : $.now(),xfId:sid,navTabId:'budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    jQuery("#budgetModelHz_gridtable_reXf").click(function(){
    	var sid = jQuery("#budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认重新汇总？重新汇总后,之前汇总的数据将作废！", {
			okCall: function(){
				jQuery.post("budgetModel_Xf", {
					"_" : $.now(),xfId:sid,xfType:'4',navTabId:'budgetModelHz_gridtable'
				}, function(data) {
					formCallBack(data);
				},"json");
				
			}
		});
    });
    
    jQuery("#budgetModelHz_gridtable_hzReport").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var sid = jQuery("#budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0||sid.length>1){
    		alertMsg.error("请选择一条记录！");
    		return;
    	}
    	var rowData = jQuery("#budgetModelHz_gridtable").jqGrid('getRowData',sid);
    	var updataId = rowData['updataId'];
    	$.pdialog.open('openBmReport?reportType=1&updataId='+updataId,'bmReport',"预算汇总", {mask:true,width : fullWidth,height : fullHeight});
      	});
    
    
    jQuery("#budgetModelHz_gridtable_comfirm").click(function(){
    	var sid = jQuery("#budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	var rowData = jQuery("#budgetModelHz_gridtable").jqGrid('getRowData',sid);
    	var updataId = rowData['updataId'];
    	$.get("confirmBmUpdata", {
			"_" : $.now(),updataId:updataId,navTabId:'budgetModelHz_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#budgetModelHz_gridtable_del_c").click(function(){
    	var sid = jQuery("#budgetModelHz_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	for(var i in sid){
    		var id = sid[i];
	    	var rowData = jQuery("#budgetModelHz_gridtable").jqGrid('getRowData',id);
	    	var state = rowData["state"];
	    	if(state==1||state==2){
	    		alertMsg.error("只能删除未汇总或已过期的汇总数据！");
	    		return ;
	    	}
    	}
    	alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post("budgetModelXfDel", {
					"_" : $.now(),id:sid,xfType:2,navTabId:'budgetModelHz_gridtable'
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
</script>

<div class="page">
<div id="budgetModelHz_container">
	<div id="budgetModelHz_layout-center" class="pane ui-layout-center">
	<div id="budgetModelHz_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetModelHz_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.modelCode'/>:
						<input type="text" name="filter_LIKES_modelId.modelCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						模板名称:
						<input type="text" name="filter_LIKES_modelId.modelName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.HzDate'/>:从
						<input type="text" name="filter_GED_xfDate" name="filter_GED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
						到&nbsp;<input type="text" name="filter_LED_xfDate" name="filter_GED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelHz.state'/>:
						<s:select list="#{'0':'未汇总','1':'汇总中','2':'已汇总','3':'已过期' }" name="filter_EQI_state" headerKey="" headerValue="--"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('budgetModelHz_search_form','budgetModelHz_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="budgetModelHz_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetModelHz_gridtable_refresh" class="initbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="budgetModelHz_gridtable_xf" class="settlebutton"  href="javaScript:"><span>汇总</span>
				</a>
				</li>
				<li><a id="budgetModelHz_gridtable_reXf" class="resettlebutton"  href="javaScript:"
					><span>重新汇总
					</span>
				</a>
				</li>
				<li><a id="budgetModelHz_gridtable_hzReport" class="previewbutton"  href="javaScript:"
					><span>查看汇总表
					</span>
				</a>
				</li>
				<s:if test="needBmHzCheckProcess!='1'">
				<li><a id="budgetModelHz_gridtable_comfirm" class="submitbutton" href="javaScript:" ><span>提交审批</span></a></li>
				</s:if>
				<li><a id="budgetModelHz_gridtable_del_c" class="delbutton"  href="javaScript:"
					><span>删除
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="budgetModelHz_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetModelHz_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetModelHz_gridtable_addTile">
				<s:text name="budgetModelHzNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetModelHz_gridtable_editTile">
				<s:text name="budgetModelHzEdit.title"/>
			</label>
			<div id="load_budgetModelHz_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetModelHz_gridtable"></table>
			<!--<div id="budgetModelHzPager"></div>-->
		</div>
	</div>
	<div id="budgetModelHz_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetModelHz_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetModelHz_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetModelHz_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>

<div id="budgetModelHz_layout-south" class="pane ui-layout-south" style="padding: 2px">
		<div class="panelBar">
						<ul class="toolBar">
							<li style="float: right;"><a id="budgetModelHz_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="budgetModelHz_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="budgetModelHz_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="budgetUpdataListDiv"></div>
</div>

</div>
</div>