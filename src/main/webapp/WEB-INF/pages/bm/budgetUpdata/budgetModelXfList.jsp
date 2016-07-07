
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetModelXfLayout;
	var budgetModelXfGridIdString="#budgetModelXf_gridtable";
	
	var budgetModelXfLayout;
	jQuery(document).ready(function() { 
		var budgetModelXfGrid = jQuery(budgetModelXfGridIdString);
		
		var budgetModelXfChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				budgetModelXfLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
			jQuery("#budgetUpdataListDiv").load("budgetUpdataList?xfId="+newSelectedSearchId);
			$("#background,#progressBar").hide();
		};
		budgetModelXfLayout = makeLayout({'baseName':'budgetModelXf','tableIds':'budgetModelXf_gridtable;budgetUpdata_gridtable','proportion':2,'key':'xfId'},budgetModelXfChangeData);
		
    	budgetModelXfGrid.jqGrid({
    		url : "budgetModelXfGridList",
    		editurl:"budgetModelXfGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'xfId',index:'xfId',align:'center',label : '<s:text name="budgetModelXf.xfId" />',hidden:true,key:true},
			{name:'modelId.modelName',index:'modelId.modelName',align:'left',label : '<s:text name="budgetModelXf.model" />',hidden:false},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetModelXf.periodYear" />',hidden:false},
			{name:'modelId.modelTypeTxt',index:'modelId.modelTypeTxt',align:'left',label : '<s:text name="budgetModelXf.budgetType" />',hidden:false},
			{name:'state',index:'state',align:'center',label : '<s:text name="budgetModelXf.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:未上报;1:上报中;2:已上报;3:已过期'}},
			{name:'xfDate',index:'xfDate',align:'center',label : '<s:text name="budgetModelXf.xfDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
			{name:'xfNum',index:'xfNum',align:'right',label : '<s:text name="budgetModelXf.xfNum" />',hidden:false},
			{name:'updataingNum',index:'updataingNum',align:'right',label : '<s:text name="budgetModelXf.updatating" />',hidden:false},
			{name:'confirmNum',index:'confirmNum',align:'right',label : '<s:text name="budgetModelXf.confirmed" />',hidden:false},
			{name:'checkedNum',index:'checkedNum',align:'right',label : '<s:text name="budgetModelXf.checked" />',hidden:false},
			{name:'submitedNum',index:'submitedNum',align:'right',label : '<s:text name="budgetModelXf.updatated" />',hidden:false}
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
			shrinkToFit:true,
			autowidth:true,
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
           	gridContainerResize('budgetModelXf','fullLayout');
           	var dataTest = {"id":"budgetModelXf_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
     		 var rowNum = jQuery(this).getDataIDs().length;
     		 var ret = jQuery(this).jqGrid('getRowData');
     	     if(rowNum > 0){
     	    	 var rowIds = jQuery(this).getDataIDs();
     	    	 var i=0;
     	    	 for (i=0;i<rowNum;i++){
     	    		var id = rowIds[i];
     	    	 	var hrefUrl = "budgetUpdataList?xfId="+id;
			    	var updataingNum = ret[i]["updataingNum"];
			    	var confirmNum = ret[i]["confirmNum"];
			    	var checkedNum = ret[i]["checkedNum"];
			    	var submitedNum = ret[i]["submitedNum"];
			    	setCellText(jQuery(this)[0],id,'updataingNum','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=0\')" target="navTab">'+updataingNum+"</a>")
			    	setCellText(jQuery(this)[0],id,'confirmNum','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=1\')" target="navTab">'+confirmNum+"</a>")
			    	setCellText(jQuery(this)[0],id,'checkedNum','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=2\')" target="navTab">'+checkedNum+"</a>")
			    	setCellText(jQuery(this)[0],id,'submitedNum','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="bmUpdataZhuanqu(\''+hrefUrl+'&state=3\')" target="navTab">'+submitedNum+"</a>")
     	    	 }
     	     }
       		} 

    	});
    jQuery(budgetModelXfGrid).jqGrid('bindKeys');
    
    jQuery("#budgetModelXf_gridtable_refresh").click(function(){
    	$.get("budgetModelXfRefresh", {
			"_" : $.now(),navTabId:'budgetModelXf_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    
    jQuery("#budgetModelXf_gridtable_xf").click(function(){
    	var sid = jQuery("#budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	$.get("budgetModel_Xf", {
			"_" : $.now(),xfId:sid,navTabId:'budgetModelXf_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
    jQuery("#budgetModelXf_gridtable_reXf").click(function(){
    	var sid = jQuery("#budgetModelXf_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return ;
    	}
    	alertMsg.confirm("确认重新下发？重新下发后,之前填报的数据将作废！", {
			okCall: function(){
				jQuery.post("budgetModel_Xf", {
					"_" : $.now(),xfId:sid,xfType:'1',navTabId:'budgetModelXf_gridtable'
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
<div id="budgetModelXf_container">
	<div id="budgetModelXf_layout-center" class="pane ui-layout-center">
	<div id="budgetModelXf_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetModelXf_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.model'/>:
						<input type="text" name="filter_EQS_model"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.xfDate'/>:
						<input type="text" name="filter_EQD_xfDate" class="Wdate"  style="height:15px"
				              			 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
				              			 value="<fmt:formatDate value="${hrPersonSnap.birthday}" pattern="yyyy-MM-dd"/>"
				              			 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,onpicked:calPersonAge(this)})" />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.state'/>:
						<s:select list="#{'0':'未上报','1':'上报中','2':'已上报','3':'已过期' }" name="filter_EQS_state" headerKey="" headerValue="--"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('budgetModelXf_search_form','budgetModelXf_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="budgetModelXf_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetModelXf_gridtable_refresh" class="addbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="budgetModelXf_gridtable_xf" class="delbutton"  href="javaScript:"><span>下发</span>
				</a>
				</li>
				<li><a id="budgetModelXf_gridtable_reXf" class="changebutton"  href="javaScript:"
					><span>重新下发
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="budgetModelXf_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetModelXf_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetModelXf_gridtable_addTile">
				<s:text name="budgetModelXfNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetModelXf_gridtable_editTile">
				<s:text name="budgetModelXfEdit.title"/>
			</label>
			<div id="load_budgetModelXf_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetModelXf_gridtable"></table>
			<!--<div id="budgetModelXfPager"></div>-->
		</div>
	</div>
	<div id="budgetModelXf_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetModelXf_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetModelXf_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetModelXf_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>

<div id="budgetModelXf_layout-south" class="pane ui-layout-south" style="padding: 2px">
		<div class="panelBar">
						<ul class="toolBar">
							<li style="float: right;"><a id="budgetModelXf_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="budgetModelXf_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="budgetModelXf_unfold" class="unfoldbutton"
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