
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var trainCourseGridIdString="#trainCourse_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var trainCourseGrid = jQuery(trainCourseGridIdString);
	    trainCourseGrid.jqGrid({
	    	url : "trainCourseGridList",
	    	editurl:"trainCourseGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainCourse.id" />',hidden:true,key:true},				
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainCourse.code" />',hidden:false,highsearch:true},	
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainCourse.name" />',hidden:false,highsearch:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'startDate',index:'startDate',width : 70,align:'center',label : '<s:text name="trainCourse.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'endDate',index:'endDate',width : 70,align:'center',label : '<s:text name="trainCourse.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'expense',index:'expense',width : 60,align:'right',label : '<s:text name="trainCourse.expense" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'hour',index:'hour',width : 60,align:'right',label : '<s:text name="trainCourse.hour" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'trainNeed.name',index:'trainNeed.name',width : 100,align:'left',label : '<s:text name="trainCourse.trainNeed" />',hidden:false,highsearch:true},
				{name:'trainTeacher.name',index:'trainTeacher.name',width : 80,align:'left',label : '<s:text name="trainCourse.trainTeacher" />',hidden:false,highsearch:true},
				{name:'trainInformationNames',index:'trainInformationNames',width : 200,align:'left',label : '<s:text name="trainCourse.trainInformationNames" />',hidden:false,highsearch:true},
				{name:'trainInformationIds',index:'trainInformationIds',width : 200,align:'left',label : '<s:text name="trainCourse.trainInformationIds" />',hidden:true},
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainCourse.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}				
			
	        ],
	        jsonReader : {
				root : "trainCourses", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'id',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainCourseList.title" />',
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
				 gridContainerResize('trainCourse','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
            	if(rowNum>0){
                	var rowIds = jQuery(this).getDataIDs();
                	var ret = jQuery(this).jqGrid('getRowData');
                	var id='';
                	for(var i=0;i<rowNum;i++){
              	  		id=rowIds[i];
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainCourseRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              	  	var trainInformationIds=ret[i]["trainInformationIds"];
              	  	var trainInformationNames=ret[i]["trainInformationNames"];
              	  	var strIds= new Array(); //定义一数组 
              	  	var strNames= new Array(); //定义一数组 
              		strIds=trainInformationIds.split(","); //字符分割 
              		strNames=trainInformationNames.split(","); //字符分割 
              		var nameStr="";
              		for (var idIndex=0;idIndex<strIds.length ;idIndex++ ) 
              		{ 
              			if(idIndex>0){
              				nameStr+=",";
              			}
              			nameStr+='<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainInformationRecord(\''+strIds[idIndex]+'\');">'+strNames[idIndex]+'</a>'
              		} 
              		setCellText(this,id,'trainInformationNames',nameStr);
                	}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"trainCourse_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   	initFlag = initColumn('trainCourse_gridtable','com.huge.ihos.hr.trainCourse.model.TrainCourse',initFlag);
       		} 

    	});
    	jQuery(trainCourseGrid).jqGrid('bindKeys');
	
    	//实例化ToolButtonBar
        var trainCourse_menuButtonArrJson = "${menuButtonArrJson}";
        var trainCourse_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainCourse_menuButtonArrJson,false)));
        var trainCourse_toolButtonBar = new ToolButtonBar({el:$('#trainCourse_buttonBar'),collection:trainCourse_toolButtonCollection,attributes:{
         tableId : 'trainCourse_gridtable',
         baseName : 'trainCourse',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainCourseNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var trainCourse_function = new scriptFunction();
        trainCourse_function.optBeforeCall = function(e,$this,param){
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
     trainCourse_toolButtonBar.addCallBody('1005040301',function(e,$this,param){
    	 var url = "editTrainCourse?popup=true&navTabId=trainCourse_gridtable";
		var winTitle='<s:text name="trainCourseNew.title"/>';
		$.pdialog.open(url,'addTrainCourse',winTitle, {mask:true,width : 700,height : 380,maxable:false});
   },{});
     trainCourse_toolButtonBar.addBeforeCall('1005040301',function(e,$this,param){
			return trainCourse_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //删除
     trainCourse_toolButtonBar.addCallBody('1005040302',function(e,$this,param){
    	 var url = "${ctx}/trainCourseGridEdit?oper=del";
       	var sid = jQuery("#trainCourse_gridtable").jqGrid('getGridParam','selarrrow');
       	if(sid==null|| sid.length == 0){       	
 				alertMsg.error("请选择记录。");
 				return;
 			}else {
 				url = url+"&id="+sid+"&navTabId=trainCourse_gridtable";
 				alertMsg.confirm("确认删除？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}	
   },{});
     trainCourse_toolButtonBar.addBeforeCall('1005040302',function(e,$this,param){
			return trainCourse_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //修改
     trainCourse_toolButtonBar.addCallBody('1005040303',function(e,$this,param){
    	 var sid = jQuery("#trainCourse_gridtable").jqGrid('getGridParam','selarrrow');
     	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var winTitle='<s:text name="trainCourseEdit.title"/>';
			var url = "editTrainCourse?popup=true&id="+sid+"&navTabId=trainCourse_gridtable";
			$.pdialog.open(url,'edittTrainCourse',winTitle, {mask:true,width : 700,height : 380,maxable:false});
   },{});
     trainCourse_toolButtonBar.addBeforeCall('1005040303',function(e,$this,param){
			return trainCourse_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var trainCourse_setColShowButton = {id:'1005040304',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('trainCourse_gridtable','com.huge.ihos.hr.trainCourse.model.TrainCourse');
          }};
        trainCourse_toolButtonBar.addButton(trainCourse_setColShowButton);//实例化ToolButtonBar
        
     	jQuery("#search_trainCourse_trainNeed").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_class where state='2' ORDER BY code",
		   exceptnullparent:false,
		   minWidth:"150px",
		   lazy:false
		});
     	jQuery("#search_trainCourse_trainTeacher").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_teacher where disabled = 0 ORDER BY code",
		   exceptnullparent:false,
		   minWidth:"150px",
		   lazy:false
		});
  	});
	function viewTrainCourseRecord(id){
		var url = "editTrainCourse?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainCourse','查看培训课程信息', {mask:true,width : 700,height : 380,maxable:false});
	}
	function viewTrainInformationRecord(id){
		var url = "editTrainInformation?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainInformation','查看培训资料信息', {mask:true,width : 700,height : 300,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainCourse_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainCourse_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.trainNeed'/>:
					 	<input type="hidden" id="search_trainCourse_trainNeed_id" name="filter_EQS_trainNeed.id">
				 		<input type="text" id="search_trainCourse_trainNeed" style="width:100px">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.trainTeacher'/>:
					 	<input type="hidden" id="search_trainCourse_trainTeacher_id" name="filter_EQS_trainTeacher.id">
				 		<input type="text" id="search_trainCourse_trainTeacher" style="width:100px">
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--      					 <input type="text"  name="filter_EQS_yearMonth" style="width:80px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.remark'/>:
					 	<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainCourse_search_form','trainCourse_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainCourse_search_form','trainCourse_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainCourse_buttonBar">
<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="trainCourse_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="trainCourse_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainCourse_gridtable_edit_custom" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li>
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainCourse_gridtable','com.huge.ihos.hr.trainCourse.model.TrainCourse')"><span><s:text name="button.setColShow" /></span></a> --%>
<%--     			</li> --%>
<!-- 			</ul> -->
		</div>
		<div id="trainCourse_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="trainCourse_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainCourse_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainCourse_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainCourse_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainCourse_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainCourse_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainCourse_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>