
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var trainRequirementGridIdString="#trainRequirement_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var trainRequirementGrid = jQuery(trainRequirementGridIdString);
    	trainRequirementGrid.jqGrid({
	    	url : "trainRequirementGridList?1=1",
	    	editurl:"trainRequirementGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainRequirement.id" />',hidden:true,key:true},
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainRequirement.code" />',hidden:false,highsearch:true},	
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainRequirement.name" />',hidden:false,highsearch:true},
				{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',align:'left',width : 130,label : '<s:text name="trainRequirement.orgCode" />',hidden:false,highsearch:true},
				{name:'hrDept.name',index:'hrDept.name',width : 80,align:'left',label : '<s:text name="trainRequirement.dept" />',hidden:false,highsearch:true},
				{name:'trainCategory.name',index:'trainCategory.name',width : 100,align:'left',label : '<s:text name="trainRequirement.trainCategory" />',hidden:false,highsearch:true},
				{name:'goal',index:'goal',width : 100,align:'left',label : '<s:text name="trainRequirement.goal" />',hidden:false,highsearch:true},				
				{name:'content',index:'content',width : 100,align:'left',label : '<s:text name="trainRequirement.content" />',hidden:false,highsearch:true,formatter:stringFormatter},	
				{name:'orgCode',index:'orgCode',width : 100,align:'center',label : '<s:text name="trainRequirement.orgCode" />',hidden:true},
				{name:'peopleNumber',index:'peopleNumber',width : 60,align:'right',label : '<s:text name="trainRequirement.peopleNumber" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'personNames',index:'personNames',width : 250,align:'left',label : '<s:text name="trainRequirement.personNames" />',hidden:false,highsearch:true},
				{name:'trainStaffList',index:'trainStaffList',width : 100,align:'center',label : '培训人员管理',hidden:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : 70,align:'center',label : '<s:text name="trainRequirement.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'maker.name',index:'maker.name',width : 60,align:'left',label : '<s:text name="trainRequirement.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : 70,align:'center',label : '<s:text name="trainRequirement.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'checker.name',index:'checker.name',width : 60,align:'left',label : '<s:text name="trainRequirement.checker" />',hidden:false,highsearch:true},
				{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="trainRequirement.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核'},highsearch:true},	
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainRequirement.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}

	        ],
	        jsonReader : {
				root : "trainRequirements", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainRequirementList.title" />',
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
				 gridContainerResize('trainRequirement','div');
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
	              	  	editUrl = "'${ctx}/trainStaffList?requirementId="+ret[i]["id"]+"'";
	   	        	  	setCellText(this,id,'trainStaffList','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="viewTrainStaff('+editUrl+');">参加培训人员</a>');
	   	        	 	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainRequirementRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	                }
				}else{
					var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
				}
           		var dataTest = {"id":"trainRequirement_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('trainRequirement_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirement',initFlag);
       		} 

    	});
    	jQuery(trainRequirementGrid).jqGrid('bindKeys');
    
    	jQuery("#search_trainRequirement_trainCategory").treeselect({
		   	dataType:"sql",
		   	optType:"single",
		   	minWidth:"150px",
		   	sql:"SELECT id,name FROM hr_train_category where disabled = 0 ORDER BY code",
		   	exceptnullparent:false,
		   	lazy:false
		});
	
    	//实例化ToolButtonBar
        var trainRequirement_menuButtonArrJson = "${menuButtonArrJson}";
        var trainRequirement_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainRequirement_menuButtonArrJson,false)));
        var trainRequirement_toolButtonBar = new ToolButtonBar({el:$('#trainRequirement_buttonBar'),collection:trainRequirement_toolButtonCollection,attributes:{
         tableId : 'trainRequirement_gridtable',
         baseName : 'trainRequirement',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainRequirementNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var trainRequirement_function = new scriptFunction();
        trainRequirement_function.optBeforeCall = function(e,$this,param){
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
     trainRequirement_toolButtonBar.addCallBody('1005020101',function(e,$this,param){
    	var url = "editTrainRequirement?popup=true&navTabId=trainRequirement_gridtable";
		var winTitle='<s:text name="trainRequirementNew.title"/>';
		$.pdialog.open(url,'addTrainRequirement',winTitle, {mask:true,width : 700,height : 400,maxable:false});
   },{});
     trainRequirement_toolButtonBar.addBeforeCall('1005020101',function(e,$this,param){
			return trainRequirement_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
     //删除
     trainRequirement_toolButtonBar.addCallBody('1005020102',function(e,$this,param){
    	 var url = "${ctx}/trainRequirementGridEdit?oper=del";
      	var sid = jQuery("#trainRequirement_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainRequirement_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='1'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainRequirement_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
   },{});
     trainRequirement_toolButtonBar.addBeforeCall('1005020102',function(e,$this,param){
			return trainRequirement_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
     //修改
     trainRequirement_toolButtonBar.addCallBody('1005020103',function(e,$this,param){
    	  var sid = jQuery("#trainRequirement_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
	        var row = jQuery("#trainRequirement_gridtable").jqGrid('getRowData',sid[0]);
			if(row['state']!='1'){
				alertMsg.error("只能修改处于新建状态的记录!");
				return;
			}
			var winTitle='<s:text name="trainRequirementEdit.title"/>';
			var url = "editTrainRequirement?popup=true&id="+sid+"&navTabId=trainRequirement_gridtable";
			$.pdialog.open(url,'editTrainRequirement',winTitle, {mask:true,width : 700,height : 400,maxable:false});
   },{});
     trainRequirement_toolButtonBar.addBeforeCall('1005020103',function(e,$this,param){
			return trainRequirement_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
     //审核
     trainRequirement_toolButtonBar.addCallBody('1005020104',function(e,$this,param){
    		var url = "${ctx}/trainRequirementGridEdit?oper=check";
         	var sid = jQuery("#trainRequirement_gridtable").jqGrid('getGridParam','selarrrow');
         	if(sid==null|| sid.length == 0){       	
 				alertMsg.error("请选择记录。");
 				return;
 			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#trainRequirement_gridtable").jqGrid('getRowData',rowId);
					
					if(row['state']!='1'){
						alertMsg.error("只能审核处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=trainRequirement_gridtable";
				alertMsg.confirm("确认审核？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
   },{});
     trainRequirement_toolButtonBar.addBeforeCall('1005020104',function(e,$this,param){
			return trainRequirement_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
     //销审
     trainRequirement_toolButtonBar.addCallBody('1005020105',function(e,$this,param){
    	 var url = "${ctx}/trainRequirementGridEdit?oper=cancelCheck"
          	var sid = jQuery("#trainRequirement_gridtable").jqGrid('getGridParam','selarrrow');
          	if(sid==null|| sid.length == 0){       	
  				alertMsg.error("请选择记录。");
  				return;
  			} else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#trainRequirement_gridtable").jqGrid('getRowData',rowId);
 					
 					if(row['state']!='2'){
 						alertMsg.error("只有已审核的记录才能够被销审!");
 						return;
 					}
 				}
 				url = url+"&id="+sid+"&navTabId=trainRequirement_gridtable";
 				alertMsg.confirm("确认销审？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
  			}
   },{});
     trainRequirement_toolButtonBar.addBeforeCall('1005020105',function(e,$this,param){
			return trainRequirement_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var trainRequirement_setColShowButton = {id:'1005020106',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('trainRequirement_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirement');
          }};
        trainRequirement_toolButtonBar.addButton(trainRequirement_setColShowButton);//实例化ToolButtonBar	 	
  	});
	function viewTrainRequirementRecord(id){
		var url = "editTrainRequirement?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainRequirement','查看培训需求信息', {mask:true,width : 700,height : 400,maxable:false});
	}		
	function viewTrainStaff(url){
		$.pdialog.open(url,'viewTrainStaff','参加培训人员列表', {mask:true,width : 700,height : 500});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainRequirement_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainRequirement_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:80px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.trainCategory'/>:
					 	<input type="hidden" id="search_trainRequirement_trainCategory_id" name="filter_EQS_trainCategory.id" >
				 		<input type="text" id="search_trainRequirement_trainCategory" style="width:80px">
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.goal'/>:
					 	<input type="text" name="filter_LIKES_goal" style="width:80px"/>
					</label>	
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainRequirement.content'/>: --%>
<!-- 					 	<input type="text" name="filter_LIKES_content" style="width:80px"/> -->
<!-- 					</label> -->
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       				<s:text name="期间"/>: --%>
<!--      				 <input type="text"  name="filter_EQS_yearMonth" style="width:60px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.state'/>:
						<s:select name="filter_EQS_state"  list="#{'':'--','1':'新建','2':'已审核'}" style="font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:80px"/>
					</label>	
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainRequirement_search_form','trainRequirement_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainRequirement_search_form','trainRequirement_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainRequirement_buttonBar">
<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="trainRequirement_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="trainRequirement_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainRequirement_gridtable_edit_custom" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRequirement_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="trainRequirement_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainRequirement_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirement')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="trainRequirement_container">
				<div id="trainRequirement_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="trainRequirement_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_trainRequirement_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="trainRequirement_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="trainRequirement_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="trainRequirement_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRequirement_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="trainRequirement_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
		
	</div>
</div>