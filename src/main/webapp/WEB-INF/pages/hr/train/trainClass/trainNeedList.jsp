
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var trainNeedGridIdString="#trainNeed_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var trainNeedGrid = jQuery(trainNeedGridIdString);
	    trainNeedGrid.jqGrid({
	    	url : "trainNeedGridList",
	    	editurl:"trainNeedGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainNeed.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainNeed.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainNeed.name" />',hidden:false,highsearch:true},
				{name:'expense',index:'expense',width : 60,align:'right',label : '<s:text name="trainNeed.expense" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'peopleNumber',index:'peopleNumber',width : 50,align:'right',label : '<s:text name="trainNeed.peopleNumber" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'personNames',index:'personNames',width : 200,align:'left',label : '<s:text name="trainNeed.personNames" />',hidden:false,highsearch:true},
				{name:'startDate',index:'startDate',width : 70,align:'center',label : '<s:text name="trainNeed.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'endDate',index:'endDate',width : 70,align:'center',label : '<s:text name="trainNeed.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'goal',index:'goal',width : 100,align:'left',label : '<s:text name="trainNeed.goal" />',hidden:true,highsearch:true},
				{name:'content',index:'content',width : 150,align:'left',label : '<s:text name="trainNeed.content" />',hidden:true,highsearch:true,formatter:stringFormatter},				
// 				{name:'courseArrangement',index:'courseArrangement',width : 100,align:'left',label : '<s:text name="trainNeed.courseArrangement" />',hidden:false,highsearch:true},		
				{name:'applyStartDate',index:'applyStartDate',width : 70,align:'center',label : '<s:text name="trainNeed.applyStartDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'applyEndDate',index:'applyEndDate',width : 70,align:'center',label : '<s:text name="trainNeed.applyEndDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'type',index:'type',width : 60,align:'center',label : '<s:text name="trainNeed.type" />',hidden:false,highsearch:true},
				{name:'year',index:'year',width : 60,align:'center',label : '<s:text name="trainNeed.year" />',hidden:false,highsearch:true},
				{name:'quarter',index:'quarter',width : 60,align:'center',label : '<s:text name="trainNeed.quarter" />',hidden:false,highsearch:true},	
				{name:'month',index:'month',width : 60,align:'center',label : '<s:text name="trainNeed.month" />',hidden:false,highsearch:true},							
				{name:'principal',index:'principal',width : 100,align:'left',label : '<s:text name="trainNeed.principal" />',hidden:false,highsearch:true},							
				{name:'sponsor',index:'sponsor',width : 100,align:'left',label : '<s:text name="trainNeed.sponsor" />',hidden:false,highsearch:true},							
				{name:'state',index:'state',width : 80,align:'center',label : '<s:text name="trainNeed.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核'},highsearch:true},				
				{name:'studyPeriod',index:'studyPeriod',width : 100,align:'left',label : '<s:text name="trainNeed.studyPeriod" />',hidden:true,highsearch:true},				
// 				{name:'syllabus',index:'syllabus',width : 200,align:'left',label : '<s:text name="trainNeed.syllabus" />',hidden:false,highsearch:true},				
				{name:'target',index:'target',width : 100,align:'left',label : '<s:text name="trainNeed.target" />',hidden:true,highsearch:true},				
				{name:'trainMethod',index:'trainMethod',width : 100,align:'left',label : '<s:text name="trainNeed.trainMethod" />',hidden:true,highsearch:true},				
				{name:'trainType',index:'trainType',width : 100,align:'left',label : '<s:text name="trainNeed.trainType" />',hidden:true,highsearch:true},	
				{name:'trainPlan.name',index:'trainPlan.name',width : 100,align:'left',label : '<s:text name="trainNeed.trainPlan" />',hidden:true,highsearch:true},
				{name:'trainEquipmentNames',index:'trainEquipmentNames',width : 100,align:'left',label : '<s:text name="trainNeed.trainEquipmentNames" />',hidden:true,highsearch:true},
				{name:'trainInstitution.name',index:'trainInstitution.name',width : 100,align:'left',label : '<s:text name="trainNeed.trainInstitution" />',hidden:true,highsearch:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : 70,align:'center',label : '<s:text name="trainNeed.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'approvalOpinion',index:'approvalOpinion',width : 100,align:'center',label : '<s:text name="trainNeed.approvalOpinion" />',hidden:true},	
				{name:'maker.name',index:'maker.name',width : 60,align:'left',label : '<s:text name="trainNeed.maker" />',hidden:false,highsearch:true},	
				{name:'checkDate',index:'checkDate',width : 70,align:'center',label : '<s:text name="trainNeed.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'checker.name',index:'checker.name',width : 60,align:'left',label : '<s:text name="trainNeed.checker" />',hidden:false,highsearch:true},	
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainNeed.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}				

	        ],
	        jsonReader : {
				root : "trainNeeds", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainNeedList.title" />',
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
				 gridContainerResize('trainNeed','div');
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
	              		  	setCellText(this,id,'state','<span style="color:red" >已审核</span>');
	              	  	}
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainNeedRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              	}
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	           	var dataTest = {"id":"trainNeed_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('trainNeed_gridtable','com.huge.ihos.hr.trainNeed.model.TrainNeed',initFlag);
       		} 
    	});
    	jQuery(trainNeedGrid).jqGrid('bindKeys');
    	
    	jQuery("#search_trainNeed_trainPlan").treeselect({
 		   dataType:"sql",
 		   optType:"single",
 		   sql:"SELECT id,name FROM hr_train_plan where state in(2,3) ORDER BY code",
 		   exceptnullparent:false,
 		   minWidth:"150px",
 		   lazy:false
 		});
    	
    	//实例化ToolButtonBar
        var trainNeed_menuButtonArrJson = "${menuButtonArrJson}";
        var trainNeed_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainNeed_menuButtonArrJson,false)));
        var trainNeed_toolButtonBar = new ToolButtonBar({el:$('#trainNeed_buttonBar'),collection:trainNeed_toolButtonCollection,attributes:{
         tableId : 'trainNeed_gridtable',
         baseName : 'trainNeed',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainNeedNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var trainNeed_function = new scriptFunction();
        trainNeed_function.optBeforeCall = function(e,$this,param){
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
     trainNeed_toolButtonBar.addCallBody('1005040201',function(e,$this,param){
    		var url = "editTrainNeed?popup=true&navTabId=trainNeed_gridtable";
			var winTitle='<s:text name="trainNeedNew.title"/>';
			$.pdialog.open(url,'addTrainNeed',winTitle, {mask:true,width : 700,height : 500,maxable:false});
   },{});
     trainNeed_toolButtonBar.addBeforeCall('1005040201',function(e,$this,param){
		return trainNeed_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //删除
     trainNeed_toolButtonBar.addCallBody('1005040202',function(e,$this,param){
    	 var url = "${ctx}/trainNeedGridEdit?oper=del";
      	var sid = jQuery("#trainNeed_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainNeed_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='1'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainNeed_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}	
   },{});
     trainNeed_toolButtonBar.addBeforeCall('1005040202',function(e,$this,param){
 		return trainNeed_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //修改
     trainNeed_toolButtonBar.addCallBody('1005040203',function(e,$this,param){
    		var sid = jQuery("#trainNeed_gridtable").jqGrid('getGridParam','selarrrow');
	       	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
	       	var row = jQuery("#trainNeed_gridtable").jqGrid('getRowData',sid[0]);
			if(row['state']!='1'){
				alertMsg.error("只能修改处于新建状态的记录!");
				return;
			}
			var winTitle='<s:text name="trainNeedEdit.title"/>';
			var url = "editTrainNeed?popup=true&id="+sid+"&navTabId=trainNeed_gridtable";
			$.pdialog.open(url,'editTrainNeed',winTitle, {mask:true,width : 700,height : 500,maxable:false});
   },{});
     trainNeed_toolButtonBar.addBeforeCall('1005040203',function(e,$this,param){
 		return trainNeed_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //审核
     trainNeed_toolButtonBar.addCallBody('1005040204',function(e,$this,param){
    	 var url = "${ctx}/trainNeedGridEdit?oper=check";
      	var sid = jQuery("#trainNeed_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainNeed_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='1'){
						alertMsg.error("只能审核处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainNeed_gridtable";
				alertMsg.confirm("确认审核？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
   },{});
     trainNeed_toolButtonBar.addBeforeCall('1005040204',function(e,$this,param){
 		return trainNeed_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //销审
     trainNeed_toolButtonBar.addCallBody('1005040205',function(e,$this,param){
    	 var url = "${ctx}/trainNeedGridEdit?oper=cancelCheck"
          	var sid = jQuery("#trainNeed_gridtable").jqGrid('getGridParam','selarrrow');
          	if(sid==null|| sid.length == 0){       	
  				alertMsg.error("请选择记录。");
  				return;
  			} else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#trainNeed_gridtable").jqGrid('getRowData',rowId);
 					if(row['state']!='2'){
 						alertMsg.error("只有已审核的记录才能够被销审!");
 						return;
 					}
 				}
 				url = url+"&id="+sid+"&navTabId=trainNeed_gridtable";
 				alertMsg.confirm("确认销审？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
  			}
   },{});
     trainNeed_toolButtonBar.addBeforeCall('1005040205',function(e,$this,param){
 		return trainNeed_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var trainNeed_setColShowButton = {id:'1005040206',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('trainNeed_gridtable','com.huge.ihos.hr.trainNeed.model.TrainNeed');
          }};
        trainNeed_toolButtonBar.addButton(trainNeed_setColShowButton);//实例化ToolButtonBar
  	});
	function viewTrainNeedRecord(id){
		var url = "editTrainNeed?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainNeed','查看培训班信息', {mask:true,width : 700,height : 500,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainNeed_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainNeed_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainNeed.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainNeed.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainNeed.trainPlan'/>:
					 	<input type="hidden" id="search_trainNeed_trainPlan_id" name="filter_EQS_trainPlan.id">
				 		<input type="text" id="search_trainNeed_trainPlan" style="width:100px">
					</label>
					<label style="float:none;white-space:nowrap" >
   					    <s:text name='trainNeed.type'/>:
     					<s:select key="trainNeed.type" name="filter_EQS_type"
          					   maxlength="50" list="typeList" listKey="value" headerKey="" headerValue="--" 
           					  listValue="content" emptyOption="false" theme="simple"></s:select>
     				</label>
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--      					 <s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainNeed.checkDate'/>: --%>
<!-- 						<input type="text"	id="trainNeed_search_checkDate_from" name="filter_GED_checkDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainNeed_search_checkDate_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="trainNeed_search_checkDate_to" name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainNeed_search_checkDate_from\')}'})"> -->
<!-- 					</label> -->
<!--    					<label style="float:none;white-space:nowrap" > -->
<%--    						<s:text name='trainNeed.year'/>: --%>
<!--      					<input type="text" name="filter_EQS_year" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy'})"/> -->
<!--    					</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--    					    <s:text name='trainNeed.quarter'/>: --%>
<%--      					<s:select key="trainNeed.quarter" name="filter_EQS_quarter" --%>
<%--           					   maxlength="50" list="quarterList" listKey="value" headerKey="" headerValue="--"  --%>
<%--            					  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--    					    <s:text name='trainNeed.month'/>: --%>
<%--      					<s:select key="trainNeed.month" name="filter_EQS_month" --%>
<%--           					   maxlength="50" list="monthList" listKey="value" headerKey="" headerValue="--"  --%>
<%--            					  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainNeed.state'/>:
						<s:select name="filter_EQS_state" list="#{'':'--','1':'新建','2':'已审核'}" style="font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
	   					 <s:text name='trainNeed.remark'/>:
	     				 <input type="text" name="filter_LIKES_remark" style="width:80px"/>
	   				</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainNeed_search_form','trainNeed_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainNeed_search_form','trainNeed_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainNeed_buttonBar">
<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="trainNeed_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="trainNeed_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainNeed_gridtable_edit_custom" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainNeed_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainNeed_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainNeed_gridtable','com.huge.ihos.hr.trainNeed.model.TrainNeed')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		
		<div id="trainNeed_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="trainNeed_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainNeed_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainNeed_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainNeed_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainNeed_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainNeed_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainNeed_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
