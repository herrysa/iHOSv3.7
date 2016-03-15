
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitPlanGridIdString="#recruitPlanReleased_gridtable";
	jQuery(document).ready(function() { 
// 		var recruitPlanReleasedFullSize = jQuery("#container").innerHeight()-118;
// 		setLeftTreeLayout('recruitPlanReleased_container','recruitPlanReleased_gridtable',recruitPlanReleasedFullSize);
		var initFlag = 0;
		var recruitPlanGrid = jQuery(recruitPlanGridIdString);
	    recruitPlanGrid.jqGrid({
	    	url : "recruitPlanGridList?filter_EQS_state=3",
	    	editurl:"recruitPlanGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitPlan.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '100',align:'left',label : '<s:text name="recruitPlan.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',width : '100',align:'left',label : '<s:text name="recruitPlan.name" />',hidden:false,highsearch:true},
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width : '130',label : '<s:text name="recruitPlan.orgCode" />',hidden:false,highsearch:true},
				{name:'post',index:'post',width : '60',align:'left',label : '<s:text name="recruitPlan.post" />',hidden:false,highsearch:true},
				{name:'recruitNumber',index:'recruitNumber',width : '60',align:'right',label : '<s:text name="recruitPlan.recruitNumber" />',hidden:false,formatter:'integer',highsearch:true},	
				{name:'resumeList',index:'resumeList',width : '60',align:'center',label : '应聘简历',hidden:false,highsearch:true},
				//{name:'state',index:'state',width : '100',align:'center',label : '<s:text name="recruitPlan.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已发布'}},	
				{name:'ageStart',index:'ageStart',width : '60',align:'right',label : '<s:text name="recruitPlan.ageStart" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'ageEnd',index:'ageEnd',width : '60',align:'right',label : '<s:text name="recruitPlan.ageEnd" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'sex',index:'sex',width : '60',align:'left',label : '<s:text name="recruitPlan.sex" />',hidden:false,highsearch:true},	
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitPlan.maritalStatus" />',hidden:false,highsearch:true},
				{name:'educationLevel',index:'educationLevel',width : '60',align:'left',label : '<s:text name="recruitPlan.educationLevel" />',hidden:false,highsearch:true},
				{name:'profession',index:'profession',width : '60',align:'left',label : '<s:text name="recruitPlan.profession" />',hidden:false,highsearch:true},
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitPlan.politicsStatus" />',hidden:false,highsearch:true},
				{name:'channel',index:'channel',width : '100',align:'left',label : '<s:text name="recruitPlan.channel" />',hidden:false,highsearch:true},	
				{name:'startDate',width : '70',index:'startDate',align:'center',label : '<s:text name="recruitPlan.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'endDate',index:'endDate',width : '70',align:'center',label : '<s:text name="recruitPlan.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'hotPost',index:'hotPost',width : '50',align:'center',label : '<s:text name="recruitPlan.hotPost" />',hidden:false,formatter:'checkbox',highsearch:true},		
				{name:'postResponsibility',index:'postResponsibility',width : '150',align:'left',label : '<s:text name="recruitPlan.postResponsibility" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'jobRequirements',index:'jobRequirements',width : '150',align:'left',label : '<s:text name="recruitPlan.jobRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'orgCode',index:'orgCode',width : '100',align:'center',label : '<s:text name="recruitPlan.orgCode" />',hidden:true},				
				{name:'otherRequirements',index:'otherRequirements',width : '150',align:'left',label : '<s:text name="recruitPlan.otherRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'recruitTarget',index:'recruitTarget',width : '100',align:'left',label : '<s:text name="recruitPlan.recruitTarget" />',hidden:true,highsearch:true},				
				{name:'salaryLevel',index:'salaryLevel',width : '120',align:'left',label : '<s:text name="recruitPlan.salaryLevel" />',hidden:true,highsearch:true},				
				{name:'workExperience',index:'workExperience',width : '100',align:'left',label : '<s:text name="recruitPlan.workExperience" />',hidden:true,highsearch:true},				
				{name:'workplace',index:'workplace',width : '100',align:'left',label : '<s:text name="recruitPlan.workplace" />',hidden:true,highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'releasedDate',index:'releasedDate',width : '70',align:'center',label : '<s:text name="recruitPlan.releasedDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'releaseder.name',index:'releaseder.name',width : '70',align:'left',label : '<s:text name="recruitPlan.releaseder" />',hidden:false,highsearch:true},
				{name:'remark',index:'remark',width : '250',align:'left',label : '<s:text name="recruitPlan.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}				

	        ],
	        jsonReader : {
				root : "recruitPlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'releasedDate',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="recruitPlanList.title" />',
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
				 gridContainerResize('recruitPlanReleased','div');
			 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  	id=rowIds[i];
	              	  	editUrl = "'${ctx}/recruitResumeViewList?planId="+ret[i]["id"]+"'";
	   	        	  	setCellText(this,id,'resumeList','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="viewRecruitResume('+editUrl+');">应聘简历</a>');
	   	        		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitPlanReleasedRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	                }
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"recruitPlanReleased_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('recruitPlanReleased_gridtable','com.huge.ihos.hr.recruitPlan.model.RecruitPlanReleased',initFlag);
       		} 
    	});
   	 	jQuery(recruitPlanGrid).jqGrid('bindKeys');
  	});
	
	function viewRecruitPlanReleasedRecord(id){
		var url = "editRecruitPlan?popup=true&id="+id+"&oper=view";
		$.pdialog.open(url,'viewRecruitPlanReleased','查看招聘计划信息', {mask:true,width : 700,height : 500});
	}
	
	function viewRecruitResume(url){
		$.pdialog.open(url,'viewRecruitResumeList','应聘简历列表', {mask:true,width : 700,height : 500});
	}

</script>

<div class="page">
	<div class="pageHeader" id="recruitPlanReleased_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitPlanReleased_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:70px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:70px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.post'/>:
					 	<input type="text" name="filter_LIKES_post" style="width:70px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       					<s:text name="期间"/>: --%>
<!--      					 <input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     				 </label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.releasedDate'/>:
						<input type="text"	id="recruitPlanReleased_search_releasedDate_from" name="filter_GED_releasedDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitPlanReleased_search_releasedDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitPlanReleased_search_releasedDate_to" name="filter_LED_releasedDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitPlanReleased_search_releasedDate_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.remark'/>:
					 	<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitPlanReleased_search_form','recruitPlanReleased_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitPlanReleased_search_form','recruitPlanReleased_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitPlanReleased_buttonBar">
			<ul class="toolBar">
				<li>
    				 <a class="settlebutton"  href="javaScript:setColShow('recruitPlanReleased_gridtable','com.huge.ihos.hr.recruitPlan.model.RecruitPlanReleased')"><span><s:text name="button.setColShow" /></span></a>
  				</li>
			</ul>
		</div>
		<div id="recruitPlanReleased_container">
				<div id="recruitPlanReleased_gridtable_div" class="grid-wrapdiv"  
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="recruitPlanReleased_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_recruitPlanReleased_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
					<table id="recruitPlanReleased_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="recruitPlanReleased_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="recruitPlanReleased_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitPlanReleased_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="recruitPlanReleased_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
		</div>
</div>