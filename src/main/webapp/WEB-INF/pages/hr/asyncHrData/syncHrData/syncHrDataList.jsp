
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var syncHrDataLayout;
	var syncHrDataGridIdString=${random}+"syncHrData_gridtable";
	var syncType = "${syncType}";
	jQuery(document).ready(function() { 
		
	    //绑定jqGrid
	    
		var syncHrDataGrid = jQuery("#"+syncHrDataGridIdString);
    	syncHrDataGrid.jqGrid({
    		url : "syncHrDataGridList?1=1&syncType="+syncType,
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'syncHrId',index:'syncHrId',align:'center',label : '<s:text name="syncHrData.syncHrId" />',hidden:true,key:true},
{name:'syncHrName',index:'syncHrName',align:'left',width:'150px',label : '<s:text name="syncHrData.syncHrName" />',hidden:false},
<c:if test="${syncType=='2'}"> 
{name:'isUseHR',index:'isUseHR',align:'left',width:'130px',label : '<s:text name="syncHrData.isUseHR" />',hidden:false},
{name:'orgCode',index:'orgCode',align:'left',width:'50px',label : '<s:text name="syncHrData.orgCode" />',hidden:true},
{name:'orgName',index:'orgName',align:'left',width:'150px',label : '<s:text name="syncHrData.orgName" />',hidden:false},
{name:'deptIds',index:'deptIds',align:'left',width:'80px',label : '<s:text name="syncHrData.deptIds" />',hidden:true},
{name:'deptNames',index:'deptNames',align:'left',width:'400px',label : '<s:text name="syncHrData.deptNames" />',hidden:false},
</c:if>
<c:if test="${syncType=='1'}">
{name:'hr_snap_time',index:'hr_snap_time',align:'center',width:'130px',label : '<s:text name="syncHrData.hr_snap_time" />',hidden:false,formatter:"date",formatoptions:{srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
{name:'syncToHrType',index:'syncToHrType',align:'left',width:'150px',label : '<s:text name="syncHrData.syncToHrType" />',hidden:false},
</c:if>
{name:'syncOperator',index:'syncOperator',align:'left',width:'100px',label : '<s:text name="syncHrData.syncOperator" />',hidden:false},
{name:'syncTime',index:'syncTime',align:'center',width:'130px',label : '<s:text name="syncHrData.syncBeginTime" />',hidden:false,formatter:"date",formatoptions:{srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
{name:'remarks',index:'remarks',align:'left',width:'200px',label : '<s:text name="syncHrData.remarks" />',hidden:false},
],
        	jsonReader : {
				root : "syncHrDatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'syncHrId',       
        	viewrecords: true,
        	sortorder: 'desc',
        	height:600,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
			  /*2015.08.27 form search change*/
			  gridContainerResize('${random}syncHrData','div',0,26);
		 	  var rowNum = jQuery(this).getDataIDs().length;
 	               var rowIds = jQuery(this).getDataIDs();
 	               var ret = jQuery(this).jqGrid('getRowData');
 	               var id='';
 	               for(var i=0;i<rowNum;i++){
 	               id=rowIds[i];
 	               var snapId=ret[i]["syncHrId"];
 	               var hr_snap_time = ret[i]["hr_snap_time"];
 		   	        	setCellText(this,id,'syncHrName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewSyncHrData(\''+snapId+'\',\''+syncType+'\',\''+hr_snap_time+'\');">'+ret[i]["syncHrName"]+'</a>');
 	                }
           	var dataTest = {"id":syncHrDataGridIdString};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager(syncHrDataGridIdString);
    		
      	   	var sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 "+ 
    		"and orgCode in (select orgCode from hr_syncData_log)";
    		jQuery("#syncHrData_orgNamed").treeselect({
    			optType:"single",
    			dataType:'sql',
    			sql:sql,
    			exceptnullparent:true,
    			lazy:false,
    			selectParent:true
    		});
       	} 
    });
    jQuery(syncHrDataGrid).jqGrid('bindKeys');
  	});
	

	
	//为页面提交按钮提供事件响应
    function addSyncData (){
		var winTitle='<s:text name="syncFromHrDataNew.title"/>';
		var url = "editSyncHrData?popup=true&navTabId="+syncHrDataGridIdString+"&syncType="+syncType;
		if(syncType=='1'){
			$.pdialog.open(url,'addsyncHrDataGrid',winTitle, {mask:true,width : 510,height : 280,maxable:true,resizable:false});
		}else{
			winTitle='<s:text name="syncToHrDataNew.title"/>';
			$.pdialog.open(url,'addsyncHrDataGrid',winTitle, {mask:true,width : 510,height : 350,maxable:true,resizable:false});
		}
	}
	//param为两个隐藏框的id
    function changeSysTimeType(id1,id2){
    	//首先获得下拉框的时间 
            jQuery("#"+id1).attr("name","filter_GET_syncTime");
    		jQuery("#"+id2).attr("name","filter_LET_syncTime");
    		WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'});
    }

</script>
<style type="text/css">
	.asyncDataTable{
		border:solid 0px black;
		position:relative;
		left:30px;
		margin-top:5px
	}
	.asyncDataTable td{
		padding:0px
	}
		.asyncDataTable{
		border:solid 0px black;
		position:relative;
		left:0px;
		margin-top:5px
	}
	.asyncDataTable td{
		padding:0px
	}
</style>
<div class="page" id="${random}syncHrData_page">
	<div id="${random}syncHrData_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
			<form id="${random}syncHrData_search_form"  style="white-space: break-all;word-wrap:break-word;" >	
			 <c:if test="${syncType=='1'}">
			 <label style="float:none;white-space:nowrap" >
						<s:text name='syncHrData.syncHrName'/>:
						<input type="text" name="filter_LIKES_syncHrName" style="width:90px"/>
			  </label>
			<label style="float:none;white-space:nowrap"></label>			
				<s:select name="filter_EQS_syncToHrType" headerKey=""   
							list="#{'':'--','通过菜单手动同步':'通过菜单手动同步','HR系统结账时同步':'HR系统结账时同步'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				</s:select>
				<label style="float:none;white-space:nowrap"></label>
						<s:text name='syncHrData.syncBeginTime'/>:
						<s:text name=" 从"/>:
						<input type="text" id="syncHrDataBeginsyncTime1"  name="hrPerson.beginDate" onclick="changeSysTimeType('syncHrData_beginSyncDate1','syncHrData_EndDate1')" onblur="checkQueryDate('syncHrDataBeginsyncTime1','syncHrDataEndsyncTime1',0,'syncHrData_beginSyncDate1','syncHrData_EndDate1')" style="width:65px"/>
						<s:text name="至"/>:
						<input type="text" id="syncHrDataEndsyncTime1" name="hrPerson.endDate" onclick="changeSysTimeType('syncHrData_beginSyncDate1','syncHrData_EndDate1')"  onblur="checkQueryDate('syncHrDataBeginsyncTime1','syncHrDataEndsyncTime1',1,'syncHrData_beginSyncDate1','syncHrData_EndDate1')" style="width:65px"/>
						<input type="hidden" id="syncHrData_beginSyncDate1" name="hrPerson.endDate"  />
						<input type="hidden" id="syncHrData_EndDate1" name="hrPerson.endDate"  />
			 </c:if>	
			 <c:if test="${syncType=='2'}">	
			 <label style="float:none;white-space:nowrap" >
						<s:text name='syncHrData.syncHrName'/>:
						<input type="text" name="filter_LIKES_syncHrName" style="width:90px"/>
			  </label>
			  <label style="float:none;white-space:nowrap" >
						<s:text name='syncHrData.orgName'/>:
						<input type="hidden" name="filter_EQS_orgCode" style="width:90px" id="syncHrData_orgNamed_id"/>
						<input type="text" id="syncHrData_orgNamed"/>
			  </label>
			   <label style="float:none;white-space:nowrap"></label>
						<s:text name='syncHrData.syncBeginTime'/>:
						<s:text name=" 从"/>:
						<input type="text" id="syncHrDataBeginsyncTime2"  name="hrPerson.beginDate" onclick="changeSysTimeType('syncHrData_beginSyncDate2','syncHrData_EndDate2')" onblur="checkQueryDate('syncHrDataBeginsyncTime2','syncHrDataEndsyncTime2',0,'syncHrData_beginSyncDate2','syncHrData_EndDate2')" style="width:65px"/>
						<s:text name="至"/>:
						<input type="text" id="syncHrDataEndsyncTime2" name="hrPerson.endDate" onclick="changeSysTimeType('syncHrData_beginSyncDate2','syncHrData_EndDate2')"  onblur="checkQueryDate('syncHrDataBeginsyncTime2','syncHrDataEndsyncTime2',1,'syncHrData_beginSyncDate2','syncHrData_EndDate2')" style="width:65px"/>
						<input type="hidden" id="syncHrData_beginSyncDate2" name="hrPerson.endDate"  />
						<input type="hidden" id="syncHrData_EndDate2" name="hrPerson.endDate"  />			
			</c:if>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}syncHrData_search_form',syncHrDataGridIdString)"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}syncHrData_buttonBar">
			<ul class="toolBar" id="${random}syncHrData_toolbuttonbar">
				<li><a id="syncHrData_gridtable_add" class="addbutton" href="javaScript:addSyncData()" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="${random}syncHrData_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="syncHrDataGridIdStringsyncHrData_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="syncHrData_gridtable_addTile">
				<s:text name="syncHrDataNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}syncHrData_gridtable_editTile">
				<s:text name="syncHrDataEdit.title"/>
			</label>
			<div id="${random}load_syncHrData_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}syncHrData_gridtable" ></table>
			<!--<div id="syncHrDataPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="${random}syncHrData_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}syncHrData_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}syncHrData_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}syncHrData_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>