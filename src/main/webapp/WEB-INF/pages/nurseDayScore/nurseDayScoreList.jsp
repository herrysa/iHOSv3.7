
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function nurseDayScoreGridReload(){
		propertyFilterSearch('nurseDayScore_search_form','nurseDayScore_gridtable');
	}
	var nurseDayScoreLayout;
	var nurseDayScoreGridIdString="#nurseDayScore_gridtable";
	
	jQuery(document).ready(function() { 
		var nurseDayScoreChangeData = function(selectedSearchId){
    		jQuery("#nurseDayScoreDetail").load("nurseDayScoreDetailList?dayScoreID="+selectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	nurseDayScoreLayout = makeLayout({
    		'baseName':'nurseDayScore',
    		'tableIds':'nurseDayScore_gridtable;nurseDayScoreDetail_gridtable',
    		'proportion':2,
    		'key':'dayScoreID'
    		},nurseDayScoreChangeData);
		
		
var nurseDayScoreGrid = jQuery(nurseDayScoreGridIdString);
    nurseDayScoreGrid.jqGrid({
    	url : "nurseDayScoreGridList",
    	editurl:"nurseDayScoreGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'dayScoreID',index:'dayScoreID',align:'center',label : '<s:text name="nurseDayScore.dayScoreID" />',hidden:true,key:true,formatter:'integer'},				
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="nurseDayScore.checkperiod" />',hidden:false,width:80},				
{name:'scoredate',index:'scoredate',align:'center',label : '<s:text name="nurseDayScore.scoredate" />',hidden:false,formatter:'date',formatoptions:{ newformat: 'Y-m-d'},width:80},				
{name:'groupname',index:'groupname',align:'left',label : '<s:text name="nurseDayScore.groupname" />',hidden:false,width:80},				
{name:'operatorname',index:'operatorname',align:'center',label : '<s:text name="nurseDayScore.operatorname" />',hidden:false,width:80},				
{name:'operatedate',index:'operatedate',align:'center',label : '<s:text name="nurseDayScore.operatedate" />',hidden:false,formatter:'date',formatoptions:{ newformat: 'Y-m-d'},width:80},				
{name:'auditname',index:'auditname',align:'center',label : '<s:text name="nurseDayScore.auditname" />',hidden:false,width:80},				
{name:'auditdate',index:'auditdate',align:'center',label : '<s:text name="nurseDayScore.auditdate" />',hidden:false,formatter:'date',formatoptions:{ newformat: 'Y-m-d'},width:80},				
{name:'days',index:'days',align:'center',label : '<s:text name="nurseDayScore.days" />',hidden:false,formatter:'number',width:80,formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 1, defaultValue: '0.0'},},				
{name:'dayscore',index:'dayscore',align:'center',label : '<s:text name="nurseDayScore.dayscore" />',hidden:false,formatter:'number',width:80},				
{name:'state',index:'state',align:'center',label : '<s:text name="nurseDayScore.state" />',hidden:false,formatter:'select',edittype:"select",editoptions:{value:"0:新建;1:已审核"},width:80},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="nurseDayScore.remark" />',hidden:false,width:80}				

        ],
        jsonReader : {
			root : "nurseDayScores", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'scoredate',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="nurseDayScoreList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			nurseDayScoreLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		nurseDayScoreLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"nurseDayScore_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("nurseDayScore_gridtable");
       	} 

    });
    jQuery(nurseDayScoreGrid).jqGrid('bindKeys');
    
	
	
	
	//nurseDayScoreLayout.resizeAll();
  	});
	
	
function executeSpTemp(taskName){
		
		var taskName =taskName;
		var proArgsStr ="201207";
		var url = 'executeSp';
		alertMsg.confirm("确认初始化？", {
			okCall: function(){
				$.ajax({
				    url: url,
				    type: 'post',
				    data:{taskName:taskName,proArgsStr:proArgsStr},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	alertMsg.info(data.message);
				    }
				});
			}
		});
	}
	
	function auditNurseDayScore(){
		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var sid = jQuery("#nurseDayScore_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null||sid.length==0){
			alertMsg.error("请选择一行记录！");
			return;
		}else{
			for(var i=0;i<sid.length;i++){
				var row = jQuery("#nurseDayScore_gridtable").jqGrid('getRowData',sid[i]);
				var state = row['state'];
				var scoredate = row['scoredate'];
				var dept = row['groupname'];
				if(state==1){
					alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
					return;
				}
			}
		}
		jQuery.post("auditNurseDayScore",{id:sid,navTabId:'nurseDayScore_gridtable'},function(json){
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok){
				if (json.navTabId){
					//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
					jQuery('#'+json.navTabId.split(",")[0]).trigger("reloadGrid");
				} else if (json.rel) {
					navTabPageBreak({}, json.rel);
				}
				if ("closeCurrent" == json.callbackType) {
					$.pdialog.closeCurrent();
				}
			}
		});
	}
	var isHaveRight = "${isHaveRight}";
 	function addNurseDayScore(){
		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var url = "editNurseDayScore?popup=true&navTabId=nurseDayScore_gridtable";
		var winTitle="添加日考勤记录";
		//alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'editNurseDayScore', winTitle, {mask:false,width:500,height:350});
	}
 	function delNurseDayScore(){
 		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		
		var sid = jQuery("#nurseDayScore_gridtable").jqGrid('getGridParam','selarrrow');
		for(var i=0;i<sid.length;i++){
			var row = jQuery("#nurseDayScore_gridtable").jqGrid('getRowData',sid[i]);
			var state = row['state'];
			var scoredate = row['scoredate'];
			var dept = row['groupname'];
			if(state==1){
				alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
				return;
			}
		}
		var editUrl = jQuery("#nurseDayScore_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId=nurseDayScore_gridtable&oper=del";
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error("请选择记录。");
				return;
		}else{
			//jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, delNurseDayScoreCallback, "json");
					
				}
			});
		}
 	}
 	function delNurseDayScoreCallback(data){
 		formCallBack(data);
 		jQuery('#nurseDayScoreDetail_gridtable').trigger("reloadGrid");
 	}
 	
 	function computDateScore(){
 		if(isHaveRight=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
 		var sid = jQuery("#nurseDayScore_gridtable").jqGrid('getGridParam','selarrrow');
 		if(sid==null || sid.length ==0){
			alertMsg.error("请选择记录。");
			return;
		}else if(sid.length>1){
			alertMsg.error("只能选择一条记录。");
			return;
		}
 		for(var i=0;i<sid.length;i++){
			var row = jQuery("#nurseDayScore_gridtable").jqGrid('getRowData',sid[i]);
			var state = row['state'];
			var scoredate = row['scoredate'];
			var dept = row['groupname'];
			if(state==1){
				alertMsg.error(dept+" "+scoredate+" 的考勤记录已审核！");
				return;
			}
		}
 		//executeSp('sp_CalCurrentDayScore','nurseDayScoreDetail_gridtable');
 		var taskName ='sp_CalCurrentDayScore';
 		var proArgsStr ="${checkPeriod},";
 		proArgsStr+=sid;
 		var url = 'executeSp';
 		alertMsg.confirm("确认执行？", {
 			okCall: function(){
 				$.ajax({
 				    url: url,
 				    type: 'post',
 				    data:{taskName:taskName,proArgsStr:proArgsStr},
 				    dataType: 'json',
 				    aysnc:false,
 				    error: function(data){
 				        
 				    },
 				    success: function(data){
 				    	data.navTabId = "nurseDayScore_gridtable";
 				    	formCallBack(data);
 				    }
 				});
 			}
 		});
 	}
	function fillScoredate(obj){
		var objValue = obj.value;
		jQuery("#scoredate_from").val(objValue);
		jQuery("#scoredate_to").val(objValue);
	}
</script>

<div class="page">
<div id="nurseDayScore_container">
			<div id="nurseDayScore_layout-center"
				class="pane ui-layout-center">
		<div id="nurseDayScore_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="nurseDayScore_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='nurseDayScore.checkperiod'/>:
						<input type="text"	id="nurseDayScore_checkperiod" name="filter_EQS_checkperiod" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" >
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='nurseDayScore.groupname'/>:
						<input type="hidden"	id="nurseDayScore_groupId" name="filter_EQS_groupid.departmentId" >
						<input type="text"	id="nurseDayScore_groupname" name="filter_exclude_groupid.departmentId" >
						<script>
						addTreeSelect("tree","sync","nurseDayScore_groupname","nurseDayScore_groupId","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:" jjleaf='1'",initSelect:"",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
						</script>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='nurseDayScore.scoredate'/>:
						<s:hidden id="scoredate_from" name="filter_GED_scoredate"></s:hidden>
						<s:hidden id="scoredate_to" name="filter_LED_scoredate"></s:hidden>
						<input type="text"		id="nurseDayScore_scoredate" name="filter_exclude_scoredate" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onchange="fillScoredate(this)">
					</label>
					</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="nurseDayScoreGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="nurseDayScoreGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" href="javaScript:addNurseDayScore()" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a  class="delbutton"  href="javaScript:delNurseDayScore()"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:auditNurseDayScore()"
					><span>审核
					</span>
				</a>
				</li>
			<li><a class="particularbutton"
								external="true" href="javaScript:nurseDayScoreLayout.optDblclick();"><span>明细</span> </a></li>
				
			</ul>
		</div>
		<div id="nurseDayScore_gridtable_div" layoutH=120
			class="grid-wrapdiv"
			buttonBar="optId:dayScoreID;width:500;height:350">
			<input type="hidden" id="nurseDayScore_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="nurseDayScore_gridtable_addTile">
				<s:text name="nurseDayScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="nurseDayScore_gridtable_editTile">
				<s:text name="nurseDayScoreEdit.title"/>
			</label>
			<label style="display: none"
				id="nurseDayScore_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="nurseDayScore_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_nurseDayScore_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="nurseDayScore_gridtable"></table>
		<div id="nurseDayScorePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="nurseDayScore_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="nurseDayScore_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="nurseDayScore_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<div id="nurseDayScore_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					
		<div class="panelBar">
						<ul class="toolBar">
							<li><a class="addbutton" href="javaScript:addNurseDayScoreDetail()" ><span><fmt:message
								key="button.add" />
								</span>
								</a>
							</li>
							<li><a class="delbutton"  href="javaScript:delNurseDayScoreDetail()"><span><s:text name="button.delete" /></span>
								</a>
							</li>
							<%-- <li><a id="nurseDayScoreDetail_gridtable_edit" class="changebutton"  href="javaScript:"
								><span><s:text name="button.edit" />
								</span>
							</a>
							</li> --%>
							<li><a class="addbutton" href="javaScript:saveNurseDayScore()" ><span><s:text name="button.save"></s:text>
								</span>
								</a>
							</li>
							<li><a class="addbutton" href="javaScript:computDateScore()" ><span>计算当日积分
								</span>
								</a>
							</li>
							<li style="float: right;"><a id="nurseDayScore_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="nurseDayScore_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="nurseDayScore_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
		<div id="nurseDayScoreDetail"></div>
</div>
</div>
</div>