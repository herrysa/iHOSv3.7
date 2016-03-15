
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitNeedCheckGridIdString="#recruitNeedCheck_gridtable";
	jQuery(document).ready(function() {
// 		var recruitNeedFullSize = jQuery("#container").innerHeight()-118;
// 		setLeftTreeLayout('recruitNeed_container','recruitNeed_gridtable',recruitNeedFullSize);
		var initFlag = 0;
		var recruitNeedCheckGrid = jQuery(recruitNeedCheckGridIdString);
    	recruitNeedCheckGrid.jqGrid({
	    	url : "recruitNeedGridList?filter_INS_state=2,3",
	    	editurl:"recruitNeedGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitNeed.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '100',align:'left',label : '<s:text name="recruitNeed.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',width : '100',align:'left',label : '<s:text name="recruitNeed.name" />',hidden:false,highsearch:true},
				{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',align:'left',width : '130',label : '<s:text name="recruitNeed.orgCode" />',hidden:false,highsearch:true},
				{name:'hrDept.name',index:'hrDept.name',width : '80',align:'left',label : '<s:text name="recruitNeed.dept" />',hidden:false,highsearch:true},
				{name:'post',index:'post',width : '60',align:'left',label : '<s:text name="recruitNeed.post" />',hidden:false,highsearch:true},
				{name:'recruitNumber',index:'recruitNumber',width : '50',align:'right',label : '<s:text name="recruitNeed.recruitNumber" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'state',index:'state',width : '40',align:'center',label : '<s:text name="recruitNeed.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已处理'},highsearch:true},	
				{name:'ageStart',index:'ageStart',width : '50',align:'right',label : '<s:text name="recruitNeed.ageStart" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'ageEnd',index:'ageEnd',width : '50',align:'right',label : '<s:text name="recruitNeed.ageEnd" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'sex',index:'sex',width : '60',align:'left',label : '<s:text name="recruitNeed.sex" />',hidden:false,highsearch:true},	
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.maritalStatus" />',hidden:false,highsearch:true},
				{name:'educationLevel',index:'educationLevel',width : '60',align:'left',label : '<s:text name="recruitNeed.educationLevel" />',hidden:false,highsearch:true},
				{name:'profession',index:'profession',width : '60',align:'left',label : '<s:text name="recruitNeed.profession" />',hidden:false,highsearch:true},
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.politicsStatus" />',hidden:false,highsearch:true},
				{name:'startDate',width : '70',index:'startDate',align:'center',label : '<s:text name="recruitNeed.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'endDate',index:'endDate',width : '70',align:'center',label : '<s:text name="recruitNeed.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'postResponsibility',index:'postResponsibility',width : '150',align:'left',label : '<s:text name="recruitNeed.postResponsibility" />',hidden:false,highsearch:true,formatter:stringFormatter},				
				{name:'jobRequirements',index:'jobRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.jobRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'otherRequirements',index:'otherRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.otherRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},
				{name:'workExperience',index:'workExperience',width : '100',align:'left',label : '<s:text name="recruitNeed.workExperience" />',hidden:true,highsearch:true},				
				{name:'workplace',index:'workplace',width : '100',align:'left',label : '<s:text name="recruitNeed.workplace" />',hidden:true,highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : '70',align:'center',label : '<s:text name="recruitNeed.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'maker.name',index:'maker.name',width : '70',align:'left',label : '<s:text name="recruitNeed.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : '70',align:'center',label : '<s:text name="recruitNeed.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',width : '70',align:'left',label : '<s:text name="recruitNeed.checker" />',hidden:false,highsearch:true},
				{name:'doneDate',index:'doneDate',width : '70',align:'center',label : '<s:text name="recruitNeed.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'doner.name',index:'doner.name',width : '70',align:'left',label : '<s:text name="recruitNeed.doner" />',hidden:false,highsearch:true},
				{name:'remark',index:'remark',width : '250',align:'left',label : '<s:text name="recruitNeed.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}

	        ],
	        jsonReader : {
				root : "recruitNeeds", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="recruitNeedList.title" />',
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
				 gridContainerResize('recruitNeedCheck','div');
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
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewrecruitNeedCheckRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
		             }
		        }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"recruitNeedCheck_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('recruitNeedCheck_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed',initFlag);
       		} 
    	});
    	jQuery(recruitNeedCheckGrid).jqGrid('bindKeys');     
  	});
	function viewrecruitNeedCheckRecord(id){
		var url = "editRecruitNeed?popup=true&id="+id+"&oper=view";
		$.pdialog.open(url,'viewRecruitNeed_'+id,'查看招聘需求信息', {mask:true,width : 700,height : 500,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="recruitNeedCheck_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitNeedCheck_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:70px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:70px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.post'/>:
					 	<input type="text" name="filter_LIKES_post" style="width:70px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='recruitNeed.checkDate'/>: --%>
<!-- 						<input type="text"	id="recruitNeedCheck_search_check_date_from" name="filter_GED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitNeedCheck_search_check_date_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="recruitNeedCheck_search_check_date_to" name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitNeedCheck_search_check_date_from\')}'})"> -->
<!-- 					</label> -->
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       					<s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth"  style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     				 </label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.doneDate'/>:
						<input type="text"	id="recruitNeedCheck_search_done_date_from" name="filter_GED_doneDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitNeedCheckCheck_search_done_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitNeedCheck_search_done_date_to"  name="filter_LED_doneDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitNeedCheck_search_done_date_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.state'/>:
						<s:select name="filter_EQS_state"  list="#{'':'--','2':'已审核','3':'已处理'}" style="font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitNeedCheck_search_form','recruitNeedCheck_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitNeedCheck_search_form','recruitNeedCheck_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitNeedCheck_buttonBar">
			<ul class="toolBar">
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('recruitNeedCheck_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
			</ul>
		</div>
		<div id="recruitNeedCheck_container">
				<div id="recruitNeedCheck_gridtable_div" class="grid-wrapdiv">
					<input type="hidden" id="recruitNeedCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_recruitNeedCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="recruitNeedCheck_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="recruitNeedCheck_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="recruitNeedCheck_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitNeedCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="recruitNeedCheck_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
		</div>
</div>