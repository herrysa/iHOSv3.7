
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
 
 
	function deptInspectScoreHisGridReload(){
		var urlString = "getScoreTempl?scoreType=his";
		var inspectTemplName = jQuery("#inspectTemplName_his").val();
		var kpiId = jQuery("#kpiName_his_id").val();
		var checkPeriodFrom = jQuery("#checkPeriodFrom_his").val();
		var checkPeriod = jQuery("#checkPeriod_his").val();
		var periodType = jQuery("#periodType_his").val();
		var state = jQuery("#state_his").val();
		//var descriptionTxt = jQuery("#descriptionTxt").val();
		//var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"&inspectTemplName="+inspectTemplName+"&kpiId="+kpiId+"&periodType="+periodType+"&checkPeriodFrom="+checkPeriodFrom+"&checkPeriod="+checkPeriod+"&state="+state;
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#deptInspectScoreHis_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectScoreHisLayout;
	var deptInspectScoreHisGridIdString="#deptInspectScoreHis_gridtable";
	jQuery(document).ready(function() { 
		var deptInspectScoreHisChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				deptInspectScoreHisLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
    		jQuery("#deptInspectScoreHisDetail").load("deptInspectHisDetailList?comId="+newSelectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	deptInspectScoreHisLayout = makeLayout({'baseName':'deptInspectScoreHis','tableIds':'deptInspectScoreHis_gridtable;deptInspectScoreDetailHis_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreHisChangeData);
var deptInspectScoreHisGrid = jQuery(deptInspectScoreHisGridIdString);
    deptInspectScoreHisGrid.jqGrid({
    	url : "getScoreTempl?scoreType=his",
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'comId',index:'comId',align:'center',label : '<s:text name="inspectBSC.inspectContentId" />',hidden:true,key:true},	
{name:'inspectBSC.inspectContentId',index:'inspectBSC.inspectContentId',align:'center',label : '<s:text name="inspectBSC.inspectContentId" />',hidden:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:50},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:30},
{name:'inspectBSC.inspectTemplName',index:'inspectBSC.inspectTemplName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:60},
{name:'inspectBSC.weidus',index:'inspectBSC.weidus',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,width:60},
{name:'inspectBSC.fenlei',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,width:60},
{name:'inspectBSC.xiangmu',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:100},
{name:'inspectBSC.weight',index:'inspectBSC.weight',align:'right',label : '<s:text name="bsc.weight" />',hidden:false,width:50,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},	
{name:'inspectBSC.score',index:'inspectBSC.score',align:'right',label : '<s:text name="bsc.score" />',hidden:false,width:50,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},	
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false,width:50},				
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,formatter:htmlStringFormatter,width:60},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
/* {name:'inspectBSC.requirement',index:'inspectBSC.requirement',align:'center',label : '<s:text name="bsc.requirement" />',hidden:false},	
{name:'inspectBSC.pattern',index:'inspectBSC.pattern',align:'center',label : '<s:text name="bsc.pattern" />',hidden:false},		 */		
{name:'state',index:'state',align:'center',width:60,label : '<s:text name="deptInspect.state" />',width:70,hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}								

        ],
        jsonReader : {
			root : "deptinspectScores", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'inspectBSC.inspectContentId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="inspectBSCList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			deptInspectScoreHisLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		deptInspectScoreHisLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
			 gridContainerResize("deptInspectScoreHis","div");
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"deptInspectScoreHis_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScoreHis_gridtable");
      	   
      	 var gridName = "deptInspectScoreHis_gridtable";
      	reFormatInspectDeptScoreHisColumnData(this);
        
      	addDetailButtonForGrid("deptInspectScoreHis_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
       	} 

    });
    jQuery(deptInspectScoreHisGrid).jqGrid('bindKeys');
    
	jQuery("#kpiName_his").treeselect({
		dataType:"sql",
		optType:"single",
		sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where lft>=0 and lft<=40000 and disabled=0 ",
		exceptnullparent:false,
		lazy:false
	});
	
    /*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:" lft>=0 and lft<=40000 and disabled=0"});*/
	//inspectBSCLayout.resizeAll();
  	});

	function submitDeptInspect(){
		var sids = jQuery("#deptInspectScoreHis_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择确认行");
			return ;
		}
		$.ajax({
		    url: 'submitDeptInspect?scoreType=new&inspectContentId='+sids,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        alert(data.message);
		    }
		});
	}
	
	function initkpiSelect(obj){
		var objValue = obj.value;
		
		var filter = "";
		if(objValue=="月"){
			filter = "lft>=0 and lft<=10000 and id<>-1";
		}else if(objValue=="季"){
			filter = "lft>=10000 and lft<=20000 and id<>-2";
		}else if(objValue=="半年"){
			filter = "lft>=20000 and lft<=30000 and id<>-3";
		}else if(objValue=="一年"){
			filter = "lft>=30000 and lft<=40000 and id<>-4";
		}else{
			filter = "lft>=0 and lft<=40000";
		}
		jQuery("#kpiName_his").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
	
	function inspectDeptScoreHisItem() {
		var url = "showInspectDeptHis?redirectType=dept";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
	
	
	function stringFormatter (cellvalue, options, rowObject)	{
		try
			{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
			}
			catch(err)
			{
				return cellvalue;
			}
		return cellvalue;
	}
	
	function reFormatInspectDeptScoreHisColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var data = ret[i]["state"];
	    		 var color = "black";
	    		 var checkperiod = ret[i]["checkperiod"];
	    		 if(data==0){
	    			 data = "新建";
	    		 }else if(data==1){
	    			 data = "已使用";
	    			 color = "green";
	    		 }else if(data==2){
	    			 data = "已确认";
	    			 color = "blue";
	    		 }else if(data==3){
	    			 data = "已初审";
	    			 color = "orange";
	    		 }else if(data==4){
	    			 data = "已审核";
	    			 color = "red";
	    		 }
	    		 setCellText(grid,id,'state','<span style="color:'+color+'" >'+data+"</span>")
	    	 }
	    }
	}
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
</script>
<div class="page" id="deptInspectScoreHis_page">
<div id="deptInspectScoreHis_container">
			<div id="deptInspectScoreHis_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="deptInspectScoreHis_pageHeader">
			<div class="searchBar">
			<label><s:text name='deptInspect.checkperiod' />：从 <input
									class="input-mini" type="text" id="checkPeriodFrom_his"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${checkPeriod}" size="10" />到<input
									class="input-mini" type="text" id="checkPeriod_his"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${checkPeriod}" size="10" /> 
								</label>
								&nbsp;&nbsp;
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	id="inspectTemplName_his" >
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.periodType'/>:
						 <select id="periodType_his" onchange="initkpiSelect(this)">
		        						<option value=''>--</option>
		        						<option value='月'>月</option>
		        						<option value='季'>季</option>
		        						<option value='半年'>半年</option>
		        						<option value='年'>年</option>
		        			</select>
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='bsc.kpi'/>:
							<input type="hidden"	id="kpiName_his_id" >
						 <input type="text"	id="kpiName_his" />
			</label>
			
			<label style="float:none;white-space:nowrap" >
					<s:text name='deptInspect.state'/>:
						 <select id="state_his">
		        						<option value=''>--</option>
		        						<option value='0'>新建</option>
		        						<option value='1'>已使用</option>
		        						<option value='2'>已确认</option>
		        						<option value='3'>已初审</option>
		        						<option value='4'>已审核</option>
		        			</select>
			</label>
			<%--
			 <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<select id='type_his'>
						 				<option value=''>--</option>
		        						<option value='计算'>计算</option>
		        						<option value='手工'>手工</option>
		        			</select>
			</label> 
			 <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<input type="text"	id="" >
			</label> --%>
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='deptInspectScoreHis.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspectScoreHis.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspectScoreHis.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspectScoreHis.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="deptInspectScoreHisGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="deptInspectScoreHis_buttonBar">
			<ul class="toolBar">
				<li><a class="particularbutton"
								external="true" href="javaScript:deptInspectScoreHisLayout.optDblclick();"><span>明细</span> </a></li>
								<c:if test="${scoreType =='all' }">
					<li><a class="submitbutton" href="javascript:inspectDeptScoreHisItem()" ><span>按科室查看</span></a>
				</c:if>
			</ul>
		</div>
		<div id="deptInspectScoreHis_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScoreHis_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScoreHis_gridtable_addTile">
				<s:text name="deptInspectScoreHisNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScoreHis_gridtable_editTile">
				<s:text name="deptInspectScoreHisEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScoreHis_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScoreHis_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScoreHis_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScoreHis_gridtable"></table>
		<div id="deptInspectScoreHisPager"></div>
</div>
	</div>
	<div class="panelBar" id="deptInspectScoreHis_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreHis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreHis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreHis_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>

<div id="deptInspectScoreHis_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					
		<div class="panelBar">
						<ul class="toolBar">
							
							
							
							<li style="float: right;"><a id="deptInspectScoreHis_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="deptInspectScoreHis_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="deptInspectScoreHis_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="deptInspectScoreHisDetail"></div>
</div>

</div>
</div>