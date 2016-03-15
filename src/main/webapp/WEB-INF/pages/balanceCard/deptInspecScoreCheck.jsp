
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
	function deptInspectScoreCheckGridReload(){
		var urlString = "getScoreTempl?scoreType=check";
		var inspectTemplName = jQuery("#inspectTemplName_check").val();
		var kpiId = jQuery("#kpiName_check_id").val();
		var periodType = jQuery("#periodType_check").val();
		//var descriptionTxt = jQuery("#descriptionTxt").val();
		//var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"&inspectTemplName="+inspectTemplName+"&kpiId="+kpiId+"&periodType="+periodType;
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#deptInspectScoreCheck_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectScoreCheckLayout;
	var deptInspectScoreCheckGridIdString="#deptInspectScoreCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var deptInspectScoreCheckChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				deptInspectScoreCheckLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
    		jQuery("#deptInspectScoreCheckDetail").load("deptInspectScoreCheckDetailList?inspectContentId="+newSelectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	deptInspectScoreCheckLayout = makeLayout({'baseName':'deptInspectScoreCheck','tableIds':'deptInspectScoreCheck_gridtable;deptInspectScoreCheckDetail_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreCheckChangeData);
var deptInspectScoreCheckGrid = jQuery(deptInspectScoreCheckGridIdString);
    deptInspectScoreCheckGrid.jqGrid({
    	url : "getScoreTempl?scoreType=check",
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'inspectBSC.inspectContentId',index:'inspectBSC.inspectContentId',align:'center',label : '<s:text name="inspectBSC.inspectContentId" />',hidden:true,key:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:40},				
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:30},				
{name:'inspectBSC.inspectTemplName',index:'inspectBSC.inspectTemplName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:60},
{name:'inspectBSC.inspectTempl.jjdepttype.khDeptTypeName',index:'inspectBSC.inspectTempl.jjdepttype.khDeptTypeName',align:'center',label : '<s:text name="inspectTempl.jjdepttype" />',hidden:false,width:50},
{name:'inspectBSC.weidus',index:'inspectBSC.weidus',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,width:60},
{name:'inspectBSC.fenlei',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,width:60},
{name:'inspectBSC.xiangmu',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:120},
{name:'inspectBSC.weight',index:'inspectBSC.weight',align:'right',label : '<s:text name="bsc.weight" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'},hidden:false,width:40},	
{name:'inspectBSC.score',index:'inspectBSC.score',align:'right',label : '<s:text name="bsc.score" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'},hidden:false,width:40},	
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,width:120,formatter:htmlStringFormatter,width:60},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
/* {name:'inspectBSC.requirement',index:'inspectBSC.requirement',align:'center',label : '<s:text name="bsc.requirement" />',hidden:false},	
{name:'inspectBSC.pattern',index:'inspectBSC.pattern',align:'center',label : '<s:text name="bsc.pattern" />',hidden:false}		 */	
			

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
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			deptInspectScoreCheckLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		deptInspectScoreCheckLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
			 gridContainerResize("deptInspectScoreCheck","div");
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"deptInspectScoreCheck_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScoreCheck_gridtable");
      	   
      	 var gridName = "deptInspectScoreCheck_gridtable";
      	addDetailButtonForGrid("deptInspectScoreCheck_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
       	} 

    });
    jQuery(deptInspectScoreCheckGrid).jqGrid('bindKeys');
	
	
  	
    /*addTreeSelect("tree","sync","kpiName_check","kpiId_check","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:" lft>=0 and lft<=10000 and id<>-1 and disabled=0"});*/
	
	//inspectBSCLayout.resizeAll();
    /*===================================按钮权限begin============================================*/
	//实例化toolButtonBar
	var deptInspectScoreCheck_menuButtonArrJson = "${menuButtonArrJson}";
	var deptInspectScoreCheck_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectScoreCheck_menuButtonArrJson,false)));
	var deptInspectScoreCheck_toolButtonBar = new ToolButtonBar({el:$('#deptInspectScoreCheck_buttonBar'),collection:deptInspectScoreCheck_toolButtonCollection,attributes:{
		tableId : 'deptInspectScoreCheck_gridtable',
		baseName : 'deptInspectScoreCheck',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="deptInspectScoreCheckNew.title"/>',
		editTitle : null
		}}).render(); 
    //审核
    deptInspectScoreCheck_toolButtonBar.addCallBody('0601050201',function() {
    	checkDeptInspect();
    },{});
    //否决
	deptInspectScoreCheck_toolButtonBar.addCallBody('0601050202',function() {
		checkDenyDeptInspect();
    },{});
    //明细
    var deptInspectScoreCheck_particularButton = {id:'0601050103',buttonLabel:'明细',className:"particularbutton",show:true,enable:true,
			callBody : function() {
				deptInspectScoreCheckLayout.optDblclick();
			}};
    deptInspectScoreCheck_toolButtonBar.addButton(deptInspectScoreCheck_particularButton);
    //按科室审核
    var deptInspectScoreCheck_submitButton = {id:'0601050104',buttonLabel:'按科室审核',className:"submitbutton",show:true,enable:true,
			callBody : function() {
				deptInspecScoreCheckDept();
			}};
	deptInspectScoreCheck_toolButtonBar.addButton(deptInspectScoreCheck_submitButton);
	/*===================================按钮权限end============================================*/
  	});
	jQuery("#kpiName_check").treeselect({
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
		jQuery("#kpiName_check").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
	
	function checkDeptInspect(){
		var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择确认行");
			return ;
		}
		$.ajax({
		    url: 'submitDeptInspect?navTabId=deptInspectScoreCheck_gridtable&scoreType=check&inspectContentId='+sids,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		    	formCallBack(data);
		    }
		});
	}
	
	function checkDenyDeptInspect(){
		var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
		var checkPeriod = "";
			
		var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[0]);
		checkPeriod = row["checkperiod"];
		/* for(var si=0;si<sids.length;si++){
			var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[si]);
			inspectDept+=row[""];
		} */
		
		//var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择确认行");
			return ;
		}
		$.ajax({
		    url: 'denyDeptInspect?navTabId=deptInspectScoreCheck_gridtable&scoreType=check&inspectContentId='+sids+'&checkPeriod='+checkPeriod,
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
	function deptInspecScoreCheckDept() {
		var url = "getScoreInspectBSCCheck?redirectType=dept&menuId=${menuId}";
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
</script>
<div class="page" id="deptInspectScoreCheck_page">
<div id="deptInspectScoreCheck_container">
			<div id="deptInspectScoreCheck_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="deptInspectScoreCheck_pageHeader">
			<div class="searchBar">
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	id="inspectTemplName_check" >
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.periodType'/>:
						 <select id="periodType_check" onchange="initkpiSelect(this)">
		        						<option value=''>--</option>
		        						<option value='月'>月</option>
		        						<option value='季'>季</option>
		        						<option value='半年'>半年</option>
		        						<option value='年'>年</option>
		        			</select>
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='bsc.kpi'/>:
							<input type="hidden"	id="kpiName_check_id" >
						 <input type="text"	id="kpiName_check" />
			</label>
			<%-- <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<select name='type'>
		        						<option value='计算'>计算</option>
		        						<option value='手工'>手工</option>
		        			</select>
			</label> --%>
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='deptInspectScoreCheck.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspectScoreCheck.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspectScoreCheck.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspectScoreCheck.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="deptInspectScoreCheckGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="deptInspectScoreCheck_buttonBar">
			<%-- <ul class="toolBar">
				<li><a class="addbutton" href="javaScript:checkDeptInspect()" ><span>审核
					</span>
				</a>
				</li>
				<li><a  class="delbutton"  href="javaScript:checkDenyDeptInspect()"><span>否决</span>
				</a>
				</li>
				<li><a class="particularbutton"
								external="true" href="javaScript:deptInspectScoreCheckLayout.optDblclick();"><span>明细</span> </a></li>
				<c:if test="${scoreType =='all' }">
				<li><a class="submitbutton" href="javascript:deptInspecScoreCheckDept()" ><span>按科室审核</span></a>
				</li>				
				</c:if>
			</ul> --%>
			
		</div>
		<div id="deptInspectScoreCheck_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScoreCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScoreCheck_gridtable_addTile">
				<s:text name="deptInspectScoreCheckNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScoreCheck_gridtable_editTile">
				<s:text name="deptInspectScoreCheckEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScoreCheck_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScoreCheck_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScoreCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScoreCheck_gridtable"></table>
		<div id="deptInspectScoreCheckPager"></div>
</div>
	</div>
	<div class="panelBar" id="deptInspectScoreCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>

<div id="deptInspectScoreCheck_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					
		<div class="panelBar">
						<ul class="toolBar">
							
							<li><a class="addbutton" href="javaScript:saveCheckRemrk()" ><span><s:text name="button.save"></s:text>
								</span>
								</a>
							</li>
							
							<li style="float: right;"><a id="deptInspectScoreCheck_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="deptInspectScoreCheck_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="deptInspectScoreCheck_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="deptInspectScoreCheckDetail">
		</div>
</div>


</div>
</div>