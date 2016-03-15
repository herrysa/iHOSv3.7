
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var trainRecordLayout;
	var trainRecordGridIdString="#trainRecord_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var trainRecordGrid = jQuery(trainRecordGridIdString);
    	trainRecordGrid.jqGrid({
    		url : "trainRecordGridList",
    		editurl:"trainRecordGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainRecord.id" />',hidden:true,key:true},
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainRecord.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainRecord.name" />',hidden:false,highsearch:true},
{name:'trainNeed.name',index:'trainNeed.name',width : 100,align:'left',label : '<s:text name="trainRecord.className" />',hidden:false,highsearch:true},
{name:'goal',index:'goal',width : 150,align:'left',label : '<s:text name="trainRecord.goal" />',hidden:false,highsearch:true},
{name:'content',index:'content',width : 200,align:'left',label : '<s:text name="trainRecord.content" />',hidden:false,highsearch:true,formatter:stringFormatter},
// {name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
{name:'makeDate',index:'makeDate',align:'center',width : 70,label : '<s:text name="trainRecord.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'maker.name',index:'maker.name',width : 60,align:'left',label : '<s:text name="trainRecord.maker" />',hidden:false,highsearch:true},
{name:'checkDate',index:'checkDate',width : 60,align:'center',label : '<s:text name="trainRecord.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'checker.name',index:'checker.name',width : 70,align:'left',label : '<s:text name="trainRecord.checker" />',hidden:false,highsearch:true},
{name:'doneDate',index:'doneDate',width : 70,align:'center',label : '<s:text name="trainRecord.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'doner.name',index:'doner.name',width : 60,align:'left',label : '<s:text name="trainRecord.doner" />',hidden:false,highsearch:true},
{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="trainRecord.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已归档'},highsearch:true},        
],
        	jsonReader : {
				root : "trainRecords", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'id',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="trainRecordList.title" />',
        	height:300,
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
				 gridContainerResize('trainRecord','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
		 		var rowNum = jQuery(this).getDataIDs().length;
            	if(rowNum>0){
                	var rowIds = jQuery(this).getDataIDs();
                	var ret = jQuery(this).jqGrid('getRowData');
                	var id='';
                	for(var i=0;i<rowNum;i++){
              	  		id=rowIds[i];
              	  		if(ret[i]['state']=="1"){
              		  		setCellText(this,id,'state','<span >新建</span>');
              	  		}else if(ret[i]['state']=="2"){
              		  		setCellText(this,id,'state','<span style="color:blue" >已审核</span>');
              	  		}else if(ret[i]['state']=="3"){
              		  		setCellText(this,id,'state','<span style="color:red" >已归档</span>');
              	  		}
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              		}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           	var dataTest = {"id":"trainRecord_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	  initFlag = initColumn('trainRecord_gridtable','com.huge.ihos.hr.trainRecord.model.TrainRecord',initFlag);
//       	   	makepager("trainRecord_gridtable");
       		} 

    	});
    jQuery(trainRecordGrid).jqGrid('bindKeys');
  //实例化ToolButtonBar
    var trainRecord_menuButtonArrJson = "${menuButtonArrJson}";
    var trainRecord_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainRecord_menuButtonArrJson,false)));
    var trainRecord_toolButtonBar = new ToolButtonBar({el:$('#trainRecord_buttonBar'),collection:trainRecord_toolButtonCollection,attributes:{
     tableId : 'trainRecord_gridtable',
     baseName : 'trainRecord',
     width : 600,
     height : 600,
     base_URL : null,
     optId : null,
     fatherGrid : null,
     extraParam : null,
     selectNone : "请选择记录。",
     selectMore : "只能选择一条记录。",
     addTitle : '<s:text name="trainRecordNew.title"/>',
     editTitle : null
    }}).render();
    //实例化结束
    var trainRecord_function = new scriptFunction();
    trainRecord_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.checkPeriod){
			if('${yearStarted}'!='true'){
				alertMsg.error("本年度人力资源系统未启用。");
    			return pass;
			}
		}
        return true;
	};
//为button添加方法 (普通点击按钮)
//添加
 trainRecord_toolButtonBar.addCallBody('1005040601',function(e,$this,param){
	 var url = "editTrainRecord?popup=true&navTabId=trainRecord_gridtable";
		var winTitle='<s:text name="trainRecordNew.title"/>';
		$.pdialog.open(url,'addTrainRecord',winTitle, {mask:true,width : 650,height : 480,maxable:false});
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040601',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//删除
 trainRecord_toolButtonBar.addCallBody('1005040602',function(e,$this,param){
		var url = "${ctx}/trainRecordGridEdit?oper=del"
	     	var sid = jQuery("#trainRecord_gridtable").jqGrid('getGridParam','selarrrow');
	     	if(sid==null|| sid.length == 0){       	
					alertMsg.error("请选择记录。");
					return;
				} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainRecord_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='1'){
						alertMsg.error("只有删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainRecord_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040602',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//修改
 trainRecord_toolButtonBar.addCallBody('1005040603',function(e,$this,param){
	 var sid = jQuery("#trainRecord_gridtable").jqGrid('getGridParam','selarrrow');
     if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
		}
     var row = jQuery("#trainRecord_gridtable").jqGrid('getRowData',sid[0]);
		if(row['state']!='1'){
			alertMsg.error("只能修改处于新建状态的记录!");
			return;
		}
		var winTitle='<s:text name="trainRecordEdit.title"/>';
		var url = "editTrainRecord?popup=true&id="+sid+"&navTabId=trainRecord_gridtable";
		$.pdialog.open(url,'editTrainRecord',winTitle, {mask:true,width : 650,height : 480,maxable:false});
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040603',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//审核
 trainRecord_toolButtonBar.addCallBody('1005040604',function(e,$this,param){
	 var url = "${ctx}/trainRecordGridEdit?oper=check";
  	var sid = jQuery("#trainRecord_gridtable").jqGrid('getGridParam','selarrrow');
  	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#trainRecord_gridtable").jqGrid('getRowData',rowId);
				
				if(row['state']!='1'){
					alertMsg.error("只能审核处于新建状态的记录!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=trainRecord_gridtable";
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040604',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//销审
 trainRecord_toolButtonBar.addCallBody('1005040605',function(e,$this,param){
	 var url = "${ctx}/trainRecordGridEdit?oper=cancelCheck"
	     	var sid = jQuery("#trainRecord_gridtable").jqGrid('getGridParam','selarrrow');
	     	if(sid==null|| sid.length == 0){       	
					alertMsg.error("请选择记录。");
					return;
				} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainRecord_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='2'){
						alertMsg.error("只有已审核的记录才能够被销审!");
						return;
					}
				}
						
				url = url+"&id="+sid+"&navTabId=trainRecord_gridtable";
				alertMsg.confirm("确认销审？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
				}
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040605',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//记入档案
 trainRecord_toolButtonBar.addCallBody('1005040606',function(e,$this,param){
	 var url = "${ctx}/trainRecordGridEdit?oper=done"
	     	var sid = jQuery("#trainRecord_gridtable").jqGrid('getGridParam','selarrrow');
	     	if(sid==null|| sid.length == 0){       	
					alertMsg.error("请选择记录。");
					return;
				} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainRecord_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='2'){
						alertMsg.error("只有记入处于审核状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainRecord_gridtable";
				alertMsg.confirm("确认记入档案？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
},{});
 trainRecord_toolButtonBar.addBeforeCall('1005040606',function(e,$this,param){
		return trainRecord_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
//设置表格列
    var trainRecord_setColShowButton = {id:'1005040607',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
      callBody:function(){
       setColShow('trainRecord_gridtable','com.huge.ihos.hr.trainRecord.model.TrainRecord');
      }};
    trainRecord_toolButtonBar.addButton(trainRecord_setColShowButton);//实例化ToolButtonBar
  	});
	function viewTrainRecord(id){
		var url = "editTrainRecord?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainRecord','查看培训记录信息', {mask:true,width : 650,height : 480,maxable:false});
	}
</script>

<div class="page">
	<div id="trainRecord_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="trainRecord_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecord.code'/>:
						<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecord.name'/>:
						<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecord.className'/>:
						<input type="text" name="filter_LIKES_trainNeed.name" style="width:100px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainRecord.content'/>: --%>
<!-- 						<input type="text" name="filter_LIKES_content" style="width:100px"/> -->
<!-- 					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecord.goal'/>:
						<input type="text" name="filter_LIKES_goal" style="width:100px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--       				<input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     				 </label> -->
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecord.state'/>:
						<s:select name="filter_EQS_state" list="#{'':'--','1':'新建','2':'已审核','3':'已归档'}" style="font-size:9pt;" ></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(trainRecord_search_form,trainRecord_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch(trainRecord_search_form,trainRecord_gridtable)"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainRecord_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="trainRecord_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRecord_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRecord_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRecord_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRecord_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="trainRecord_gridtable_wirteRecord" class="changebutton"  href="javaScript:"><span>记入档案</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainRecord_gridtable','com.huge.ihos.hr.trainRecord.model.TrainRecord')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="trainRecord_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="trainRecord_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainRecord_gridtable_addTile">
				<s:text name="trainRecordNew.title"/>
			</label> 
			<label style="display: none"
				id="trainRecord_gridtable_editTile">
				<s:text name="trainRecordEdit.title"/>
			</label>
			<div id="load_trainRecord_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainRecord_gridtable"></table>
			<!--<div id="trainRecordPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="trainRecord_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainRecord_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRecord_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="trainRecord_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>