
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	function deptInspectScoreGridReload(){
		var urlString = "getScoreTempl?scoreType=new";
		var inspectTemplName = jQuery("#inspectTemplName").val();
		var kpiId = jQuery("#kpiNameScore_id").val();
		var periodType = jQuery("#periodType_score").val();
		//var descriptionTxt = jQuery("#descriptionTxt").val();
		//var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"&inspectTemplName="+inspectTemplName+"&kpiId="+kpiId+"&periodType="+periodType;
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#deptInspectScore_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectScoreLayout;
	var deptInspectScoreGridIdString="#deptInspectScore_gridtable";
	
	jQuery(document).ready(function() { 
		var deptInspectScoreChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				deptInspectScoreLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
    		jQuery("#deptInspectScoreDetail").load("deptInspectScoreDetailList?inspectContentId="+newSelectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	deptInspectScoreLayout = makeLayout({'baseName':'deptInspectScore','tableIds':'deptInspectScore_gridtable;deptInspectScoreDetail_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
var deptInspectScoreGrid = jQuery(deptInspectScoreGridIdString);
    deptInspectScoreGrid.jqGrid({
    	url : "getScoreTempl?scoreType=new",
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'inspectBSC.inspectContentId',index:'inspectBSC.inspectContentId',align:'center',label : '<s:text name="inspectBSC.inspectContentId" />',hidden:true,key:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:50},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:30},
{name:'inspectBSC.inspectTemplName',index:'inspectBSC.inspectTemplName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:60},
{name:'inspectBSC.weidus',index:'inspectBSC.weidus',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,width:60},
{name:'inspectBSC.fenlei',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,width:60},
{name:'inspectBSC.xiangmu',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:120},
{name:'inspectBSC.weight',index:'inspectBSC.weight',align:'right',label : '<s:text name="bsc.weight" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'},hidden:false,width:40},	
{name:'inspectBSC.score',index:'inspectBSC.score',align:'right',label : '<s:text name="bsc.score" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'},hidden:false,width:40},	
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false,width:65},				
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,formatter:htmlStringFormatter,width:60},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
/* {name:'inspectBSC.requirement',index:'inspectBSC.requirement',align:'center',label : '<s:text name="bsc.requirement" />',hidden:false},	
{name:'inspectBSC.pattern',index:'inspectBSC.pattern',align:'center',label : '<s:text name="bsc.pattern" />',hidden:false,width:50},	 */			
{name:'state',index:'state',align:'center',label : '<s:text name="deptInspect.state" />',width:50,hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}					

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
        sortorder: 'asc',
        //caption:'<s:text name="inspectBSCList.title" />',
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		height:"auto",
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			deptInspectScoreLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		deptInspectScoreLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
			 gridContainerResize("deptInspectScore","div");
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"deptInspectScore_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScore_gridtable");
      	   
      	 var gridName = "deptInspectScore_gridtable";
        
      	addDetailButtonForGrid("deptInspectScore_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
       	} 

    });
    jQuery(deptInspectScoreGrid).jqGrid('bindKeys');
	
	
   /* addTreeSelect("tree","sync","kpiName","kpiId","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:" lft>=0 and lft<=10000 and id<>-1 and disabled=0"});*/
	//inspectBSCLayout.resizeAll();
   
	/*===================================按钮权限begin============================================*/
	//实例化toolButtonBar
	var deptInspectScore_menuButtonArrJson = "${menuButtonArrJson}";
	var deptInspectScore_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectScore_menuButtonArrJson,false)));
	var deptInspectScore_toolButtonBar = new ToolButtonBar({el:$('#deptInspectScore_buttonBar'),collection:deptInspectScore_toolButtonCollection,attributes:{
		tableId : 'deptInspectScore_gridtable',
		baseName : 'deptInspectScore',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="deptInspectScoreNew.title"/>',
		editTitle : null
		}}).render();
	//确认
	deptInspectScore_toolButtonBar.addCallBody('0601050101',function() {
		var sids = jQuery("#deptInspectScore_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择确认行");
			return ;
		}
		$.ajax({
		    url: 'submitDeptInspect?navTabId=deptInspectScore_gridtable&scoreType=new&inspectContentId='+sids,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        formCallBack(data);
		    }
		});
	},{});
	
	var deptInspectScore_particularButton = {id:'0601050102',buttonLabel:'明细',className:"particularbutton",show:true,enable:true,
				callBody : function() {
					deptInspectScoreLayout.optDblclick();
				}};
	deptInspectScore_toolButtonBar.addButton(deptInspectScore_particularButton);
	var deptInspectScore_submitButton = {id:'0601050103',buttonLabel:'按科室打分',className:"submitbutton",show:true,enable:true,
			callBody : function() {
				var url = "getScoreInspectBSC?redirectType=dept&menuId=${menuId}";
				//window.location = url;
				//jQuery.post(url, {}, DWZ.ajaxDone, "json");
				navTab.reload(url, {
					title : "New Tab",
					fresh : false,
					data : {}
				});
			}};
	deptInspectScore_toolButtonBar.addButton(deptInspectScore_submitButton);
	//deptInspectScore_toolButtonBar.disabledAll();
	
	/*===================================按钮权限end============================================*/
   
  	});
	jQuery("#kpiNameScore").treeselect({
		dataType:"sql",
		optType:"single",
		sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where lft>=0 and lft<=40000 and disabled=0 ",
		exceptnullparent:false,
		lazy:false
	});
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
		jQuery("#kpiNameScore").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
  	
	function submitDeptInspect(){
		var sids = jQuery("#deptInspectScore_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择确认行");
			return ;
		}
		$.ajax({
		    url: 'submitDeptInspect?navTabId=deptInspectScore_gridtable&scoreType=new&inspectContentId='+sids,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        formCallBack(data);
		    }
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
	function htmlStringFormatter(cellvalue, options, rowObject){
		try
		{
			cellvalue = cellvalue.replace(/<[^>]+>/g,"");
			cellvalue = cellvalue.replace(/&nbsp;/g,"");
			cellvalue = cellvalue.replace(/\r\n/g, "").replace(/\n/g, "").replace(/\t/g, "").replace(/(^\s*)|(\s*$)/g, "");
		}
		catch(err)
		{
			return cellvalue;
		}
	return cellvalue;
	}
	function htmlFormatter(cellvalue, options, rowObject){
		try{
			cellvalue = cellvalue.replace(/&quot;/g,"\"");
		}catch(err)
		{
			return cellvalue;
		}
	return cellvalue;
	}
	function deptInspecScoreDept() {
		var url = "getScoreInspectBSC?redirectType=dept";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
</script>
<div class="page" id="deptInspectScore_page">
<div id="deptInspectScore_container">
			<div id="deptInspectScore_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="deptInspectScore_pageHeader">
			<div class="searchBar">
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	id="inspectTemplName" >
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.periodType'/>:
						 <select id="periodType_score" onchange="initkpiSelect(this)">
		        						<option value=''>--</option>
		        						<option value='月'>月</option>
		        						<option value='季'>季</option>
		        						<option value='半年'>半年</option>
		        						<option value='年'>年</option>
		        			</select>
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='bsc.kpi'/>:
							<input type="hidden"	id="kpiNameScore_id" >
						 <input type="text"	id="kpiNameScore" />
			</label>
			<%-- <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<select name='type'>
		        						<option value='计算'>计算</option>
		        						<option value='手工'>手工</option>
		        			</select>
			</label> --%>
			<%-- <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<input type="text"	id="" >
			</label> --%>
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='deptInspectScore.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspectScore.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspectScore.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspectScore.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="buttonActive" style="float:right;">
								<div class="buttonContent">
									<button type="button" onclick="deptInspectScoreGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
			</div>
	</div>
	<div class="pageContent">

<input type="hidden" id="editComputeKpi_hidden" value="${editComputeKpi }"/>



<div class="panelBar" id="deptInspectScore_buttonBar">
			<%-- <ul class="toolBar">
				<li><a class="addbutton" href="javaScript:submitDeptInspect()" ><span>确认
					</span>
				</a>
				</li>
				<!-- <li><a  class="delbutton"  href="javaScript:"><span>自动得分</span>
				</a>
				</li> -->
				<li><a class="particularbutton"
								external="true" href="javaScript:deptInspectScoreLayout.optDblclick();"><span>明细</span> </a></li>
				<c:if test="${scoreType =='all' }">
				<li><a class="submitbutton" href="javascript:deptInspecScoreDept()" ><span>按科室打分</span></a>
				</li>				
				</c:if>
			</ul> --%>
		</div>
		<div id="deptInspectScore_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScore_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScore_gridtable_addTile">
				<s:text name="deptInspectScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScore_gridtable_editTile">
				<s:text name="deptInspectScoreEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScore_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScore_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScore_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScore_gridtable"></table>
		<div id="deptInspectScorePager"></div>
</div>
	</div>
	<div class="panelBar" id="deptInspectScore_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScore_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScore_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScore_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>

<div id="deptInspectScore_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					
		<div class="panelBar">
						<ul class="toolBar">
							
							<li><a class="addbutton" href="javaScript:saveScoreEdit()" ><span><s:text name="button.save"></s:text>
								</span>
								</a>
							</li>
							
							<li style="float: right;"><a id="deptInspectScore_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="deptInspectScore_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="deptInspectScore_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="deptInspectScoreDetail"></div>
</div>

</div>
</div>