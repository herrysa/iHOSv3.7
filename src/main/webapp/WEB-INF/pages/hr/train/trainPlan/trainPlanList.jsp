
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	
	var trainPlanGridIdString="#trainPlan_gridtable";
	
	jQuery(document).ready(function() {
		var initFlag = 0;
		var trainPlanGrid = jQuery(trainPlanGridIdString);
	    trainPlanGrid.jqGrid({
	    	url : "trainPlanGridList",
	    	editurl:"trainPlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainPlan.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainPlan.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainPlan.name" />',hidden:false,highsearch:true},
				{name:'approvalOpinion',index:'approvalOpinion',width : 100,align:'center',label : '<s:text name="trainPlan.approvalOpinion" />',hidden:true},				
				{name:'budgetAmount',index:'budgetAmount',width : 60,align:'right',label : '<s:text name="trainPlan.budgetAmount" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'type',index:'type',width : 60,align:'left',label : '<s:text name="trainPlan.type" />',hidden:false,highsearch:true},
				{name:'year',index:'year',width : 60,align:'center',label : '<s:text name="trainPlan.year" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y'},highsearch:true},
				{name:'quarter',index:'quarter',width : 60,align:'center',label : '<s:text name="trainPlan.quarter" />',hidden:false,highsearch:true},	
				{name:'month',index:'month',width : 60,align:'center',label : '<s:text name="trainPlan.month" />',hidden:false,highsearch:true},				
				{name:'trainCategory.name',index:'trainCategory.name',width : 100,align:'left',label : '<s:text name="trainPlan.trainCategory" />',hidden:false,highsearch:true},
				{name:'target',index:'target',width : 60,align:'center',label : '<s:text name="trainPlan.target" />',hidden:false,highsearch:true},
				{name:'goal',index:'goal',width : 100,align:'left',label : '<s:text name="trainPlan.goal" />',hidden:true,highsearch:true},	
				{name:'content',index:'content',width : 200,align:'left',label : '<s:text name="trainPlan.content" />',hidden:true,highsearch:true,formatter:stringFormatter},	
				{name:'peopleNumber',index:'peopleNumber',width : 50,align:'right',label : '<s:text name="trainPlan.peopleNumber" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'personNames',index:'personNames',width : 200,align:'left',label : '<s:text name="trainPlan.personNames" />',hidden:false,highsearch:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : 100,align:'center',label : '<s:text name="trainPlan.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'maker.name',index:'maker.name',width : 60,align:'left',label : '<s:text name="trainPlan.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : 70,align:'center',label : '<s:text name="trainPlan.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',width : 60,align:'left',label : '<s:text name="trainPlan.checker" />',hidden:false,highsearch:true},
				{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="trainPlan.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已处理'},highsearch:true},				
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainPlan.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}
	        ],
	        jsonReader : {
				root : "trainPlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainPlanList.title" />',
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
				 gridContainerResize('trainPlan','div');
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
              		  		setCellText(this,id,'state','<span style="color:red" >已处理</span>');
              	  		}
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainPlanRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              		}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"trainPlan_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('trainPlan_gridtable','com.huge.ihos.hr.trainPlan.model.TrainPlan',initFlag);
       		} 

    	});
    	jQuery(trainPlanGrid).jqGrid('bindKeys');
    	jQuery("#search_trainPlan_trainCategory").treeselect({
 		   dataType:"sql",
 		   optType:"single",
 		   sql:"SELECT id,name FROM hr_train_category where disabled = 0 ORDER BY code",
 		   exceptnullparent:false,
 		  minWidth:"150px",
 		   lazy:false
 		});
    	//实例化ToolButtonBar
        var trainPlan_menuButtonArrJson = "${menuButtonArrJson}";
        var trainPlan_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainPlan_menuButtonArrJson,false)));
        var trainPlan_toolButtonBar = new ToolButtonBar({el:$('#trainPlan_buttonBar'),collection:trainPlan_toolButtonCollection,attributes:{
         tableId : 'trainPlan_gridtable',
         baseName : 'trainPlan',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainPlanNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var trainPlan_function = new scriptFunction();
        trainPlan_function.optBeforeCall = function(e,$this,param){
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
     trainPlan_toolButtonBar.addCallBody('1005030201',function(e,$this,param){
    	var url = "editTrainPlan?popup=true&navTabId=trainPlan_gridtable";
		var winTitle='<s:text name="trainPlanNew.title"/>';
		$.pdialog.open(url,'addTrainPlan',winTitle, {mask:true,width : 650,height : 480,maxable:false});
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030201',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //需求分析
     trainPlan_toolButtonBar.addCallBody('1005030202',function(e,$this,param){
    	 var url = "analysisTrainRequirement";
		 var winTitle='培训需求统计分析';
		 $.pdialog.open(url,'analysisTrainPlanList',winTitle, {mask:true,width : 700,height : 500});
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030202',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //删除
     trainPlan_toolButtonBar.addCallBody('1005030203',function(e,$this,param){
    	 var url = "${ctx}/trainPlanGridEdit?oper=del"
          	var sid = jQuery("#trainPlan_gridtable").jqGrid('getGridParam','selarrrow');
          	if(sid==null|| sid.length == 0){       	
  				alertMsg.error("请选择记录。");
  				return;
  			} else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#trainPlan_gridtable").jqGrid('getRowData',rowId);
 					if(row['state']!='1'){
 						alertMsg.error("只有删除处于新建状态的记录!");
 						return;
 					}
 				}
 				url = url+"&id="+sid+"&navTabId=trainPlan_gridtable";
 				alertMsg.confirm("确认删除？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030203',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //修改
     trainPlan_toolButtonBar.addCallBody('1005030204',function(e,$this,param){
    	   var sid = jQuery("#trainPlan_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
	        var row = jQuery("#trainPlan_gridtable").jqGrid('getRowData',sid[0]);
			if(row['state']!='1'){
				alertMsg.error("只能修改处于新建状态的记录!");
				return;
			}
			var winTitle='<s:text name="trainPlanEdit.title"/>';
			var url = "editTrainPlan?popup=true&id="+sid+"&navTabId=trainPlan_gridtable";
			$.pdialog.open(url,'editTrainPlan',winTitle, {mask:true,width : 650,height : 480,maxable:false});
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030204',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //审核
     trainPlan_toolButtonBar.addCallBody('1005030205',function(e,$this,param){
    	 var url = "${ctx}/trainPlanGridEdit?oper=check";
      	var sid = jQuery("#trainPlan_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainPlan_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='1'){
						alertMsg.error("只能审核处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainPlan_gridtable";
				alertMsg.confirm("确认审核？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030205',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //销审
     trainPlan_toolButtonBar.addCallBody('1005030206',function(e,$this,param){
    	 var url = "${ctx}/trainPlanGridEdit?oper=cancelCheck"
          	var sid = jQuery("#trainPlan_gridtable").jqGrid('getGridParam','selarrrow');
          	if(sid==null|| sid.length == 0){       	
  				alertMsg.error("请选择记录。");
  				return;
  			} else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#trainPlan_gridtable").jqGrid('getRowData',rowId);
 					
 					if(row['state']!='2'){
 						alertMsg.error("只有已审核的记录才能够被销审!");
 						return;
 					}
//  					var trainNeedsId=null;
//  					jQuery.ajax({
//  		               url: 'trainNeedGridList?filter_EQS_trainPlan.id='+rowId,
//  		               data: {},
//  		               type: 'post',
//  		               dataType: 'json',
//  		               async:false,
//  		               error: function(data){
//  		               },
//  		               success: function(data){
//  		                if(data.trainNeeds[0]){
//  		                	trainNeedsId=data.trainNeeds[0]["id"];
//  		                }
//  		              }
//  		           	});
//  					if(trainNeedsId){
//  						alertMsg.error("已生成培训班的记录不能被销审!");
//  						return;
//  					}
 				}
  					
 				url = url+"&id="+sid+"&navTabId=trainPlan_gridtable";
 				alertMsg.confirm("确认销审？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
  			}
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030206',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //生成培训班
     trainPlan_toolButtonBar.addCallBody('1005030207',function(e,$this,param){
    	 var sid = jQuery("#trainPlan_gridtable").jqGrid('getGridParam','selarrrow');
     	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainPlan_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='2'){
						alertMsg.error("只有处于已审核状态的记录才能生成培训班!");
						return;
					}
				}
				var url = "editTrainNeed?popup=true&navTabId=trainNeed_gridtable"+"&trainPlanId="+sid;
				var winTitle='新建培训班';
				$.pdialog.open(url,'addTrainNeed',winTitle, {mask:true,width : 700,height : 600});
			}
   },{});
     trainPlan_toolButtonBar.addBeforeCall('1005030207',function(e,$this,param){
			return trainPlan_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var trainPlan_setColShowButton = {id:'1005030208',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('trainPlan_gridtable','com.huge.ihos.hr.trainPlan.model.TrainPlan');
          }};
        trainPlan_toolButtonBar.addButton(trainPlan_setColShowButton);//实例化ToolButtonBar
  	});
	function viewTrainPlanRecord(id){
		var url = "editTrainPlan?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainPlan','查看培训计划信息', {mask:true,width : 650,height : 480,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainPlan_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainPlan_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainPlan.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainPlan.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
   					    <s:text name='trainPlan.type'/>:
     					<s:select key="trainPlan.type" name="filter_EQS_type" style="font-size:9pt;"
       					   maxlength="50" list="typeList" listKey="value" headerKey="" headerValue="--" 
        					  listValue="content" emptyOption="false" theme="simple"></s:select>
     				</label>
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainPlan.trainCategory'/>:
					 	<input type="hidden" id="search_trainPlan_trainCategory_id" name="filter_EQS_trainCategory.id">
				 		<input type="text" id="search_trainPlan_trainCategory" style="width:80px">
					</label>
					
<!--      				<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainPlan.checkDate'/>: --%>
<!-- 						<input type="text"	id="trainPlan_search_checkDate_from" name="filter_GED_checkDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainPlan_search_checkDate_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="trainPlan_search_checkDate_to" name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainPlan_search_checkDate_from\')}'})"> -->
<!-- 					</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--      					<s:text name='trainPlan.year'/>: --%>
<!--        					<input type="text" name="filter_EQS_year" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy'})"/> -->
<!--      				</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--    					    <s:text name='trainPlan.quarter'/>: --%>
<%--      					<s:select key="trainPlan.quarter" name="filter_EQS_quarter" style="font-size:9pt;" --%>
<%--           					   maxlength="50" list="quarterList" listKey="value" headerKey="" headerValue="--"  --%>
<%--            					  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				</label> -->
<!--      				<label style="float:none;white-space:nowrap" > -->
<%--    					    <s:text name='trainPlan.month'/>: --%>
<%--      					<s:select key="trainPlan.month" name="filter_EQS_month"  style="font-size:9pt;" --%>
<%--           					   maxlength="50" list="monthList" listKey="value" headerKey="" headerValue="--"  --%>
<%--            					  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainPlan.state'/>:
						<s:select name="filter_EQS_state" list="#{'':'--','1':'新建','2':'已审核','3':'已处理'}" style="font-size:9pt;" ></s:select>
					</label>	
					<label style="float:none;white-space:nowrap" >
     					<s:text name='trainPlan.remark'/>:
       					<input type="text" name="filter_LIKES_remark" style="width:100px"/>
     				</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainPlan_search_form','trainPlan_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainPlan_search_form','trainPlan_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainPlan_buttonBar">
<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="trainPlan_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainPlan_gridtable_analysis_custom" class="downloadbutton" href="javaScript:" ><span>需求统计分析</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="trainPlan_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainPlan_gridtable_edit_custom" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainPlan_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainPlan_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li><a id="trainPlan_gridtable_addTrainNeed_custom" class="changebutton"  href="javaScript:" -->
<!-- 					><span>生成培训班 -->
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainPlan_gridtable','com.huge.ihos.hr.trainPlan.model.TrainPlan')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		
		<div id="trainPlan_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="trainPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainPlan_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainPlan_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainPlan_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainPlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainPlan_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainPlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>