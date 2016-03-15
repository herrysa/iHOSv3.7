
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitResumeGridIdString="#recruitResumeView_gridtable";
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var recruitResumeGrid = jQuery(recruitResumeGridIdString);
	    recruitResumeGrid.jqGrid({
	    	url : "recruitResumeGridList?filter_EQS_recruitPlan.id="+"${planId}",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitResume.id" />',hidden:true,key:true},
				{name:'viewPhoto',index:'viewPhoto',align:'left',width : '20',label : '<s:text name=" " />',hidden:false},
				{name:'code',index:'code',align:'left',width : '100',label : '<s:text name="recruitResume.code" />',hidden:false,highsearch:true},	
				{name:'recruitDate',index:'recruitDate',width : '70',align:'center',label : '<s:text name="recruitResume.recruitDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'name',index:'name',width : '80',align:'left',label : '<s:text name="recruitResume.name" />',hidden:false,highsearch:true},
				{name:'recruitPlan.hrOrg.orgname',index:'recruitPlan.hrOrg.orgname',width : 130,align:'left',label : '<s:text name="recruitResume.planOrg" />',hidden:false,highsearch:true},
				{name:'recruitPlan.post',index:'recruitPlan.post',width : '60',align:'left',label : '<s:text name="recruitResume.recruitPlan" />',hidden:false,highsearch:true},
				{name:'sex',index:'sex',width : '50',align:'center',label : '<s:text name="recruitResume.sex" />',hidden:false,highsearch:true},
				{name:'age',index:'age',width : '50',align:'right',label : '<s:text name="recruitResume.age" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'viewState',index:'viewState',width : '50',align:'left',label : '<s:text name="recruitResume.favoriteState" />',hidden:true},
				{name:'photo',index:'photo',width : '50',align:'left',label : '<s:text name="recruitResume.photo" />',hidden:true},
				{name:'birthday',index:'birthday',width : '70',align:'center',label : '<s:text name="recruitResume.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'workStartDate',index:'workStartDate',width : '70',align:'center',label : '<s:text name="recruitResume.workStartDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'domicilePlace',index:'domicilePlace',width : '100',align:'left',label : '<s:text name="recruitResume.domicilePlace" />',hidden:true,highsearch:true},				
				{name:'presentResidence',index:'presentResidence',width : '100',align:'left',label : '<s:text name="recruitResume.presentResidence" />',hidden:true,highsearch:true},				
				{name:'contactWay',index:'contactWay',width : '100',align:'left',label : '<s:text name="recruitResume.contactWay" />',hidden:false,highsearch:true},				
				{name:'email',index:'email',width : '100',align:'left',label : '<s:text name="recruitResume.email" />',hidden:false,highsearch:true},				
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitResume.maritalStatus" />',hidden:false,highsearch:true},				
				{name:'typeOfId',index:'typeOfId',width : '80',align:'left',label : '<s:text name="recruitResume.typeOfId" />',hidden:true,highsearch:true},				
				{name:'idNumber',index:'idNumber',width : '100',align:'left',label : '<s:text name="recruitResume.idNumber" />',hidden:true,highsearch:true},				
				{name:'overseaAssignment',index:'overseaAssignment',width : '80',align:'center',label : '<s:text name="recruitResume.overseaAssignment" />',hidden:true,formatter:'checkbox',highsearch:true},				
				{name:'nation',index:'nation',width : '60',align:'left',label : '<s:text name="recruitResume.nation" />',hidden:true,highsearch:true},				
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitResume.politicsStatus" />',hidden:true,highsearch:true},				
				{name:'foreignLanguage',index:'foreignLanguage',width : '80',align:'left',label : '<s:text name="recruitResume.foreignLanguage" />',hidden:true,highsearch:true},				
				{name:'foreignLanguageLevel',index:'foreignLanguageLevel',width : '100',align:'left',label : '<s:text name="recruitResume.foreignLanguageLevel" />',hidden:true,highsearch:true},				
				{name:'interests',index:'interests',width : '200',align:'left',label : '<s:text name="recruitResume.interests" />',hidden:true,highsearch:true},				
				{name:'selfAssessment',index:'selfAssessment',width : '200',align:'left',label : '<s:text name="recruitResume.selfAssessment" />',hidden:true,highsearch:true},				
				{name:'expectJobCategory',index:'expectJobCategory',width : '100',align:'left',label : '<s:text name="recruitResume.expectJobCategory" />',hidden:true,highsearch:true},				
				{name:'expectWorkplace',index:'expectWorkplace',width : '100',align:'left',label : '<s:text name="recruitResume.expectWorkplace" />',hidden:true,highsearch:true},				
				{name:'expectOccupation',index:'expectOccupation',width : '100',align:'left',label : '<s:text name="recruitResume.expectOccupation" />',hidden:true,highsearch:true},				
				{name:'expectIndustry',index:'expectIndustry',width : '100',align:'left',label : '<s:text name="recruitResume.expectIndustry" />',hidden:true,highsearch:true},				
				{name:'expectMonthlyPay',index:'expectMonthlyPay',width : '100',align:'left',label : '<s:text name="recruitResume.expectMonthlyPay" />',hidden:false,highsearch:true},				
				{name:'workingCondition',index:'workingCondition',width : '100',align:'left',label : '<s:text name="recruitResume.workingCondition" />',hidden:true,highsearch:true},				
				{name:'projectExperience',index:'projectExperience',width : '200',align:'left',label : '<s:text name="recruitResume.projectExperience" />',hidden:true,highsearch:true},				
				{name:'professionalSkill',index:'professionalSkill',width : '200',align:'left',label : '<s:text name="recruitResume.professionalSkill" />',hidden:true,highsearch:true},				
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'state',index:'state',width : '60',align:'center',label : '<s:text name="recruitResume.state" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:审查合格;2:拟录用;3:已录用;4:面试不合格'},highsearch:true},			
				{name:'interviewState',index:'interviewState',width : '70',align:'center',label : '<s:text name="recruitResume.interviewState" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:待通知;1:已通知;2:联系不上;3:已有工作;4:个人放弃'},highsearch:true},		
				{name:'remark',index:'remark',width : '250',align:'left',label : '<s:text name="recruitResume.remark" />',hidden:false,highsearch:true}				
	        ],
	        jsonReader : {
				root : "recruitResumes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'recruitDate',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="recruitResumeList.title" />',
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
				 gridContainerResize('recruitResumeView','div',0,430);
			 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
		              	  id=rowIds[i];
		              	  if(ret[i]['state']=="0"){
		              		  setCellText(this,id,'state','<span style="color:black">新建</span>');
		              	  }else if(ret[i]['state']=="1"){
		              		  if(ret[i]['interviewState']=="1"||ret[i]['interviewState']=="0"){
		              			setCellText(this,id,'state','<span style="color:green" >审查合格</span>');
		              		  }else{
		              			setCellText(this,id,'state','<span style="color:gray" >审查合格</span>');
		              		  }
		              	  }else if(ret[i]['state']=="2"){
		              		  setCellText(this,id,'state','<span style="color:blue" >拟录用</span>');
		              	  }else if(ret[i]['state']=="3"){
		              		  setCellText(this,id,'state','<span style="color:green" >已录用</span>');
		              	  }else if(ret[i]['state']=="4"){
		              		  setCellText(this,id,'state','<span style="color:gray" >面试不合格</span>');
		              	  }
		              	var defaultRecordViewPhoto="";
		            	var viewState=ret[i]['viewState'];
		            	if(viewState=="1"){
		            		defaultRecordViewPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/envelopeopen.png";
		            	}else{
		            		defaultRecordViewPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/envelope.png";
		            	}
		            	setCellText(this,id,'viewPhoto','<img src='+defaultRecordViewPhoto+'>');
		            	var gridName="recruitResumeView_gridtable";
		            	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitResumeRecord(\''+id+'\',\''+gridName+'\',\''+viewState+'\');">'+ret[i]["code"]+'</a>');
						
		            	//图片设置
		              	var sex =ret[i]['sex'];
		              	var defaultSexPhoto="";
		              	if(sex=="男"){
		              		defaultSexPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/male.png";
		              	}else{
		              		defaultSexPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/female.png";
		              	}	
		              	var photo=ret[i]['photo'];
		              	if(photo){
			              	 setCellText(this,id,'name',ret[i]['name']+'<img src='+defaultSexPhoto+'>');
		              	}
		              }
				}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	           var dataTest = {"id":"recruitResumeView_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   initFlag = initColumn('recruitResumeView_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume',initFlag);
       		} 
    	});
    	jQuery(recruitResumeGrid).jqGrid('bindKeys');
  	});
	function viewRecruitResumeRecord(id,gridName,viewState){
		if(viewState=="0"){
			var defaultRecordViewPhoto="";
			defaultRecordViewPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/envelopeopen.png";
			jQuery("#"+gridName).setCell(id,'viewPhoto','<img src='+defaultRecordViewPhoto+'>');
			jQuery("#"+gridName).setCell(id,'viewState','1');
		}
		viewRecruitResumeRecordAfterSetCell(id);
	}
</script>

<div class="page">
	<div class="pageHeader" id="recruitResumeView_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitResumeView_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='recruitResume.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px;"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='recruitResume.recruitDate'/>:
						<input type="text"	id="recruitResumeView_search_recruit_date_from" name="filter_GED_recruitDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitResumeView_search_recruit_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitResumeView_search_recruit_date_to"  name="filter_LED_recruitDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitResumeView_search_recruit_date_from\')}'})">
					</label>
					<label class="queryarea-label" >
						<s:text name='recruitResume.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px;"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       				<s:text name="期间"/>: --%>
<!--      				 <input type="text"  name="filter_EQS_yearMonth" style="width:100px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label class="queryarea-label" >
						<s:text name='recruitResume.sex'/>:
						<s:select name="filter_EQS_sex" list="#{'':'--','男':'男','女':'女'}" style="font-size:9pt;" ></s:select>
					</label>	
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.state'/>:
						<s:select name="filter_EQS_state" list="#{'':'--','0':'新建','1':'审查合格','2':'拟录用','3':'已录用','4':'面试不合格'}" style="width:80px;font-size:9pt;" ></s:select>
					</label> --%>
					<label class="queryarea-label" >
						<s:text name='recruitResume.remark'/>:
					 	<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitResumeView_search_form','recruitResumeView_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitResumeView_search_form','recruitResumeView_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitResumeView_buttonBar">
			<ul class="toolBar">
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('recruitResumeView_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume')"><span><s:text name="button.setColShow" /></span></a>
    			</li>
			</ul>
		</div>
		<div id="recruitResumeView_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="recruitResumeView_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_recruitResumeView_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="recruitResumeView_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="recruitResumeView_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="recruitResumeView_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitResumeView_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="recruitResumeView_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>