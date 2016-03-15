
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var trainRequirementCheckGridIdString="#trainRequirementCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var trainRequirementCheckGrid = jQuery(trainRequirementCheckGridIdString);
    	trainRequirementCheckGrid.jqGrid({
	    	url : "trainRequirementGridList?filter_EQS_state=2",
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
				 gridContainerResize('trainRequirementCheck','div');
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
           		var dataTest = {"id":"trainRequirementCheck_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('trainRequirementCheck_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirement',initFlag);
       		} 

    	});
    	jQuery(trainRequirementCheckGrid).jqGrid('bindKeys');
    
    	jQuery("#search_trainRequirementCheck_trainCategory").treeselect({
		   	dataType:"sql",
		   	optType:"single",
		   	sql:"SELECT id,name FROM hr_train_category where disabled = 0 ORDER BY code",
		   	exceptnullparent:false,
		   	minWidth:"150px",
		   	lazy:false
		});
	
  	});
	function viewTrainRequirementRecord(id){
		var url = "editTrainRequirement?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainRequirement','查看培训需求信息', {mask:true,width : 700,height : 400});
	}		
	function viewTrainStaff(url){
		$.pdialog.open(url,'viewTrainStaff','参加培训人员列表', {mask:true,width : 700,height : 500});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainRequirementCheck_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainRequirementCheck_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.trainCategory'/>:
					 	<input type="hidden" id="search_trainRequirementCheck_trainCategory_id" name="filter_EQS_trainCategory.id">
				 		<input type="text" id="search_trainRequirementCheck_trainCategory" style="width:100px">
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.goal'/>:
					 	<input type="text" name="filter_LIKES_goal" style="width:100px"/>
					</label>	
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainRequirement.content'/>: --%>
<!-- 					 	<input type="text" name="filter_LIKES_content" style="width:100px"/> -->
<!-- 					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirement.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>	
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       					<s:text name="期间"/>: --%>
<!--      					 <input type="text"  name="filter_EQS_yearMonth" style="width:80px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      					</label> -->
					
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainRequirementCheck_search_form','trainRequirementCheck_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainRequirementCheck_search_form','trainRequirementCheck_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainRequirementCheck_buttonBar">
			<ul class="toolBar">
				<li>
    				 <a class="settlebutton"  href="javaScript:setColShow('trainRequirementCheck_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirement')"><span><s:text name="button.setColShow" /></span></a>
    			</li>
			</ul>
		</div>
		<div id="trainRequirementCheck_container">
				<div id="trainRequirementCheck_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="trainRequirementCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_trainRequirementCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="trainRequirementCheck_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="trainRequirementCheck_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="trainRequirementCheck_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRequirementCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="trainRequirementCheck_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
		
	</div>
</div>