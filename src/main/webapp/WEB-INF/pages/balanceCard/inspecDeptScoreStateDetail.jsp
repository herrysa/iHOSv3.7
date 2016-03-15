
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function deptInspectScoreReadGridReload(){
		var urlString = "getScoreTempl?scoreType=new&inspectDept="+inspectDept+"&checkperiod="+checkperiod;
		var inspectTemplName = jQuery("#inspectTemplName_state").val();
		var kpiId = jQuery("#kpiName_state_id").val();
		//var descriptionTxt = jQuery("#descriptionTxt").val();
		//var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"&inspectTemplName="+inspectTemplName+"&kpiId="+kpiId;
		urlString=encodeURI(urlString);
		jQuery("#deptInspectScoreRead_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectScoreReadLayout;
	var deptInspectScoreReadGridIdString="#deptInspectScoreRead_gridtable";
	var inspectDept = "${requestScope.inspectDept}";
	var checkperiod = "${requestScope.checkperiod}";
	jQuery(document).ready(function() { 
		var deptInspectScoreReadChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				deptInspectScoreReadLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
    		jQuery("#deptInspectScoreReadDetail").load("deptInspectScoreReadDetailList?inspectContentId="+newSelectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	deptInspectScoreReadLayout = makeLayout({'baseName':'deptInspectScoreRead','tableIds':'deptInspectScoreRead_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreReadChangeData);
var deptInspectScoreReadGrid = jQuery(deptInspectScoreReadGridIdString);
    deptInspectScoreReadGrid.jqGrid({
    	url : "getScoreTempl?scoreType=new&inspectDept="+inspectDept+"&checkperiod="+checkperiod,
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'inspectBSC.inspectContentId',index:'inspectBSC.inspectContentId',align:'center',label : '<s:text name="inspectBSC.inspectContentId" />',hidden:true,key:true},	
{name:'inspectBSC.inspectTemplName',index:'inspectBSC.inspectTemplName',align:'center',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:80},
{name:'inspectBSC.weidus',index:'inspectBSC.weidus',align:'center',label : '<s:text name="bsc.weidu" />',hidden:false},
{name:'inspectBSC.fenlei',index:'inspectBSC.fenlei',align:'center',label : '<s:text name="bsc.fenlei" />',hidden:false},
{name:'inspectBSC.xiangmu',index:'inspectBSC.xiangmu',align:'center',label : '<s:text name="bsc.xiangmu" />',hidden:false},
{name:'inspectBSC.weight',index:'inspectBSC.weight',align:'center',label : '<s:text name="bsc.weight" />',hidden:false,width:60},	
{name:'inspectBSC.score',index:'inspectBSC.score',align:'center',label : '<s:text name="bsc.score" />',hidden:false,width:60},	
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false},				
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'center',label : '<s:text name="bsc.rules" />',hidden:false},	
{name:'inspectBSC.requirement',index:'inspectBSC.requirement',align:'center',label : '<s:text name="bsc.requirement" />',hidden:false},	
{name:'inspectBSC.pattern',index:'inspectBSC.pattern',align:'center',label : '<s:text name="bsc.pattern" />',hidden:false},				
{name:'inspectBSC.remark',index:'inspectBSC.remark',align:'center',label : '<s:text name="bsc.remark" />',hidden:false,formatter:stringFormatter}				
			

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
			deptInspectScoreReadLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		deptInspectScoreReadLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"deptInspectScoreRead_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScoreRead_gridtable");
      	   
      	 var gridName = "deptInspectScoreRead_gridtable";
       	} 

    });
    jQuery(deptInspectScoreReadGrid).jqGrid('bindKeys');
    
	
	jQuery("#kpiName_state").treeselect({
		dataType:"sql",
		optType:"single",
		sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where lft>=0 and lft<=10000 and id<>-1 and disabled=0 ",
		exceptnullparent:false,
		lazy:false
	});
    /*addTreeSelect("tree","sync","kpiName_state","kpiId_state","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:" lft>=0 and lft<=10000 and id<>-1 and disabled=0"});
	//inspectBSCLayout.resizeAll();
  	});*/
	
	function submitDeptInspect(){
		var sids = jQuery("#deptInspectScoreRead_gridtable").jqGrid('getGridParam','selarrrow');
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
</script>
<div class="page">
<div id="deptInspectScoreRead_container">
			<div id="deptInspectScoreRead_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<label style="float:none;white-space:nowrap" >
					<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	id="inspectTemplName_state" >
			</label>
			<label style="float:none;white-space:nowrap" >
					<s:text name='bsc.kpi'/>:
							<input type="hidden"	id="kpiName_state_id" >
						 <input type="text"	id="kpiName_state" />
			</label>
			<%-- <label style="float:none;white-space:nowrap" >
					<s:text name='bsc.type'/>:
						 	<input type="text"	id="" >
			</label> --%>
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='deptInspectScoreRead.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspectScoreRead.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspectScoreRead.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspectScoreRead.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptInspectScoreReadGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" href="javaScript:submitDeptInspect()" ><span>确认
					</span>
				</a>
				</li>
				<li><a  class="delbutton"  href="javaScript:"><span>自动得分</span>
				</a>
				</li>
				<li><a class="particularbutton"
								external="true" href="javaScript:deptInspectScoreReadLayout.optDblclick();"><span>明细</span> </a></li>
			</ul>
		</div>
		<div id="deptInspectScoreRead_gridtable_div" layoutH="120"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScoreRead_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScoreRead_gridtable_addTile">
				<s:text name="deptInspectScoreReadNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScoreRead_gridtable_editTile">
				<s:text name="deptInspectScoreReadEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScoreRead_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScoreRead_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScoreRead_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScoreRead_gridtable"></table>
		<div id="deptInspectScoreReadPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreRead_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreRead_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreRead_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>

<div id="deptInspectScoreRead_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					
		<div class="panelBar">
						<ul class="toolBar">
							
							
							
							<li style="float: right;"><a id="deptInspectScoreRead_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="deptInspectScoreRead_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="deptInspectScoreRead_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="deptInspectScoreReadDetail"></div>
</div>


</div>
</div>