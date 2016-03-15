
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function operateLogGridReload(){
		var urlString = "operateLogGridList";
		var operateLog_userName = jQuery("#operateLog_userName").val();
		var operateLog_operator = jQuery("#operateLog_operator").val();
		var operateLog_operateObject = jQuery("#operateLog_operateObject").val();
		var operateLog_operateTime = jQuery("#operateLog_operateTime").val();
		var operateLog_userMachine = jQuery("#operateLog_userMachine").val();
		
		
		var beginTime = "";
		var endTime = "";
		if(operateLog_operateTime){
			beginTime = operateLog_operateTime +" 00:00:00";
			endTime = operateLog_operateTime +" 23:59:59";
		}
	
		urlString=urlString+"?filter_LIKES_userName="+operateLog_userName+"&filter_LIKES_operator="+operateLog_operator+"&filter_LIKES_operateObject="+operateLog_operateObject+"&filter_GET_operateTime="+beginTime+"&filter_LET_operateTime="+endTime+"&filter_LIKES_userMachine="+operateLog_userMachine;
		urlString=encodeURI(urlString);
		jQuery("#operateLog_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var operateLogLayout;
			  var operateLogGridIdString="#operateLog_gridtable";
	
	jQuery(document).ready(function() { 
		/* operateLogLayout = makeLayout({
			baseName: 'operateLog', 
			tableIds: 'operateLog_gridtable'
		}, null); */
var operateLogGrid = jQuery(operateLogGridIdString);
    operateLogGrid.jqGrid({
    	url : "operateLogGridList",
    	editurl:"operateLogGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'operateLogId',index:'operateLogId',align:'left',label : '<s:text name="operateLog.operateLogId" />',hidden:true,key:true,formatter:'integer',width:100},				
{name:'userName',index:'userName',align:'left',label : '<s:text name="operateLog.userName" />',hidden:false},				
{name:'operator',index:'operator',align:'left',label : '<s:text name="operateLog.operator" />',hidden:false},
{name:'operateObject',index:'operateObject',align:'left',label : '<s:text name="operateLog.operateObject" />',hidden:false,width:200},				
{name:'operateTime',index:'operateTime',align:'center',label : '<s:text name="operateLog.operateTime"/>',hidden:false,formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s',newformat:"Y-m-d H:i:s"},width:200},				
{name:'userMachine',index:'userMachine',align:'left',label : '<s:text name="operateLog.userMachine" />',hidden:false}				
        ],
        jsonReader : {
			root : "operateLogs", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'operateLogId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="operateLogList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: false,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 gridContainerResize('operateLog','div');
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var dataTest = {"id":"operateLog_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   //makepager("operateLog_gridtable");
       	} 

    });
    jQuery(operateLogGrid).jqGrid('bindKeys');
    
	
	
	
	//operateLogLayout.resizeAll();
  	});
	function backupOperateLog(){
		var url = "deleteAndBackupPage?type=b";
		$.pdialog.open(url, 'deleteAndBackup', "备份日志", {
			mask : false,
			width : 400,
			height : 200
		});
	}
	function deleteOperateLog(){
		var url = "deleteAndBackupPage?type=d";
		$.pdialog.open(url, 'deleteAndBackup', "清除日志", {
			mask : false,
			width : 400,
			height : 200
		});
	}
</script>

<div class="page"  id="operateLog_page">
	<div id="operateLog_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="operateLog_search_form" class="queryarea-form">
						<label class="queryarea-label"><s:text name='operateLog.userName'/>:
							<input type="text"	id="operateLog_userName" >
						</label>
						<label class="queryarea-label"><s:text name='operateLog.operator'/>:
						 	<input type="text"	id="operateLog_operator" >
						 </label>
						<label class="queryarea-label"><s:text name='operateLog.operateObject'/>:
						 	<input type="text"		id="operateLog_operateObject" >
						 </label>
						 <label class="queryarea-label"><s:text name='operateLog.operateTime'/>:
						 	<input type="text"		id="operateLog_operateTime" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})">
						 </label>
						  <label class="queryarea-label"><s:text name='operateLog.userMachine'/>:
						 	<input type="text"		id="operateLog_userMachine" >
						 </label>
						 <div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="operateLogGridReload()"><s:text name='button.search'/></button>
								</div>
					</div>
					</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="operateLogGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar"  id="operateLog_buttonBar">
			<ul class="toolBar" id="operateLog_toolbuttonbar">
				<li><a id="" class="backupbutton" href="javaScript:backupOperateLog();" ><span><fmt:message
								key="button.backupOperateLog" />
					</span>
				</a>
				</li>
				<li><a id="" class="delbutton"  href="javaScript:deleteOperateLog()"><span><s:text name="button.deleteOperateLog" /></span>
				</a>
				</li>
			</ul>
</div>
		<div id="operateLog_gridtable_div"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="operateLog_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="operateLog_gridtable_addTile">
				<s:text name="operateLogNew.title"/>
			</label> 
			<label style="display: none"
				id="operateLog_gridtable_editTile">
				<s:text name="operateLogEdit.title"/>
			</label>
			<label style="display: none"
				id="operateLog_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="operateLog_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_operateLog_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="operateLog_gridtable"></table>
		<div id="operateLogPager"></div>
</div>
	</div>
	<div class="panelBar" id="operateLog_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="operateLog_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="operateLog_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="operateLog_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->
