
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var recruitResumeGridIdString="#recruitResumeInterview_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var recruitResumeGrid = jQuery(recruitResumeGridIdString);
    	recruitResumeGrid.jqGrid({
	    	url : "recruitResumeGridList?filter_INS_state=1",
	    	editurl:"recruitResumeGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitResume.id" />',hidden:true,key:true},
				{name:'viewPhoto',index:'viewPhoto',align:'left',width : '20',label : '<s:text name=" " />',hidden:false},	
				{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="recruitResume.code" />',hidden:false,highsearch:true},
				{name:'recruitDate',index:'recruitDate',width : 70,align:'center',label : '<s:text name="recruitResume.recruitDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="recruitResume.name" />',hidden:false,highsearch:true},
				{name:'recruitPlan.hrOrg.orgname',index:'recruitPlan.hrOrg.orgname',width : 130,align:'left',label : '<s:text name="recruitResume.planOrg" />',hidden:false,highsearch:true},
				{name:'recruitPlan.post',index:'recruitPlan.post',width : 60,align:'left',label : '<s:text name="recruitResume.recruitPlan" />',hidden:false,highsearch:true},
				{name:'sex',index:'sex',width : 50,align:'center',label : '<s:text name="recruitResume.sex" />',hidden:false,highsearch:true},
				{name:'age',index:'age',width : '50',align:'right',label : '<s:text name="recruitResume.age" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'viewState',index:'viewState',width : '50',align:'left',label : '<s:text name="recruitResume.favoriteState" />',hidden:true},
				{name:'photo',index:'photo',width : '50',align:'left',label : '<s:text name="recruitResume.photo" />',hidden:true},
				{name:'birthday',index:'birthday',width : 70,align:'center',label : '<s:text name="recruitResume.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'workStartDate',index:'workStartDate',width : 70,align:'center',label : '<s:text name="recruitResume.workStartDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'contactWay',index:'contactWay',width : 100,align:'left',label : '<s:text name="recruitResume.contactWay" />',hidden:false,highsearch:true},				
				{name:'email',index:'email',width : 100,align:'left',label : '<s:text name="recruitResume.email" />',hidden:false,highsearch:true},		
				{name:'interviewSpace',index:'interviewSpace',width : 100,align:'left',label : '<s:text name="recruitResume.interviewSpace" />',hidden:false,highsearch:true},
				{name:'examinerDate',index:'examinerDate',align:'center',width : 100,label : '<s:text name="recruitResume.examinerDate" />',hidden:false,formatter:"date",formatoptions:{srcformat: 'Y-m-d H:i',newformat:"Y-m-d H:i"},highsearch:true},
				{name:'interviewContacts',index:'interviewContacts',width : 100,align:'left',label : '<s:text name="recruitResume.interviewContacts" />',hidden:false,highsearch:true},	
				{name:'interviewContactWay',index:'interviewContactWay',width : 100,align:'left',label : '<s:text name="recruitResume.interviewContactWay" />',hidden:false,highsearch:true},
				{name:'professionalExaminer',index:'professionalExaminer',width : 80,align:'left',label : '<s:text name="recruitResume.professionalExaminer" />',hidden:false,highsearch:true},
				{name:'foreignLanguageExaminer',index:'foreignLanguageExaminer',width : 80,align:'left',label : '<s:text name="recruitResume.foreignLanguageExaminer" />',hidden:false,highsearch:true},
				{name:'interviewState',index:'interviewState',width : 70,align:'center',label : '<s:text name="recruitResume.interviewState" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:待通知;1:已通知;2:联系不上;3:已有工作;4:个人放弃'},highsearch:true},
				{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="recruitResume.state" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:审查合格;'},highsearch:true},			
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="recruitResume.remark" />',hidden:false,highsearch:true}				
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
	        gridInterview:true,
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
				 gridContainerResize('recruitResumeInterview','div');
			 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
		            	id=rowIds[i];
		            	var defaultRecordViewPhoto="";
		            	var viewState=ret[i]['viewState'];
		            	if(viewState=="1"){
		            		defaultRecordViewPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/envelopeopen.png";
		            	}else{
		            		defaultRecordViewPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/envelope.png";
		            	}
		            	setCellText(this,id,'viewPhoto','<img src='+defaultRecordViewPhoto+'>');
		            	var gridName="recruitResumeInterview_gridtable";
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
		              	/* if(ret[i]['state']=="1"){
		              		if(ret[i]['interviewState']=="1"||ret[i]['interviewState']=="0"){
		              			setCellText(this,id,'state','<span style="color:green" >审查合格</span>');
		              		}else{
		              			setCellText(this,id,'state','<span style="color:gray" >审查合格</span>');
		              		 }
		              	}else if(ret[i]['state']=="4"){
		              		setCellText(this,id,'state','<span style="color:gray" >面试不合格</span>');
		              	} */
		              	if(ret[i]['interviewState']=='1'){
		              		setCellText(this,id,'interviewState','<span style="color:green" >已通知</span>');
		              	}else if(ret[i]['interviewState']=='2'){
		              		setCellText(this,id,'interviewState','<span style="color:gray" >联系不上</span>');
		              	}else if(ret[i]['interviewState']=='3'){
		              		setCellText(this,id,'interviewState','<span style="color:gray" >已有工作</span>');
		              	}else if(ret[i]['interviewState']=='4'){
		              		setCellText(this,id,'interviewState','<span style="color:gray" >个人放弃</span>');
		              	}
		            }
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"recruitResumeInterview_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('recruitResumeInterview_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume',initFlag);
       		} 
    	});
		jQuery(recruitResumeGrid).jqGrid('bindKeys');
		//实例化ToolButtonBar
	     var recruitResumeInterview_menuButtonArrJson = "${menuButtonArrJson}";
	     var recruitResumeInterview_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(recruitResumeInterview_menuButtonArrJson,false)));
	     var recruitResumeInterview_toolButtonBar = new ToolButtonBar({el:$('#recruitResumeInterview_buttonBar'),collection:recruitResumeInterview_toolButtonCollection,attributes:{
	      tableId : 'recruitResumeInterview_gridtable',
	      baseName : 'recruitResume',
	      width : 600,
	      height : 600,
	      base_URL : null,
	      optId : null,
	      fatherGrid : null,
	      extraParam : null,
	      selectNone : "请选择记录。",
	      selectMore : "只能选择一条记录。",
	      addTitle : '<s:text name="recruitResumeNew.title"/>',
	      editTitle : null
	     }}).render();
	     //实例化结束
	     var recruitResumeInterview_function = new scriptFunction();
	     recruitResumeInterview_function.optBeforeCall = function(e,$this,param){
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
	  recruitResumeInterview_toolButtonBar.addCallBody('1004040101',function(e,$this,param){
		  var sid = jQuery("#recruitResumeInterview_gridtable").jqGrid('getGridParam','selarrrow');
       	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var winTitle='<s:text name="recruitResumeMessage.title"/>';
			var url = "recruitResumeMessage?popup=true&id="+sid+"&navTabId=recruitResumeInterview_gridtable";
			$.pdialog.open(url,'recruitResumeMessage',winTitle, {mask:true,width : 700,height : 400,maxable:false});
	},{});
	  recruitResumeInterview_toolButtonBar.addBeforeCall('1004040101',function(e,$this,param){
			return recruitResumeInterview_function.optBeforeCall(e,$this,param);
  	},{checkPeriod:"checkPeriod"});
	//设置表格列
	     var recruitResumeInterview_setColShowButton = {id:'1004040102',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	       callBody:function(){
	        setColShow('recruitResumeInterview_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume');
	       }};
	     recruitResumeInterview_toolButtonBar.addButton(recruitResumeInterview_setColShowButton);//实例化ToolButtonBar
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
	<div class="pageHeader" id="recruitResumeInterview_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitResumeInterview_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.recruitDate'/>:
						<input type="text"	id="recruitResumeInterview_search_recruit_date_from" name="filter_GED_recruitDate"  style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitResumeInterview_search_recruit_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitResumeInterview_search_recruit_date_to"  name="filter_LED_recruitDate" style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitResumeInterview_search_recruit_date_from\')}'})">
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       					<s:text name="期间"/>: --%>
<!--      				 	<input type="text"  name="filter_EQS_yearMonth" style="width:50px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.recruitPlan'/>:
					 	<input type="text" name="filter_LIKES_recruitPlan.post" style="width:80px;"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:80px;"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.sex'/>:
						<s:select name="filter_EQS_sex" list="#{'':'--','男':'男','女':'女'}" style="font-size:9pt;" ></s:select>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.interviewState'/>:
						<s:select name="filter_EQS_interviewState" list="#{'':'--','1':'已通知','2':'联系不上','3':'已有工作','4':'个人放弃'}" style="font-size:9pt;" ></s:select>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.remark'/>:
					 	<input type="text"  name="filter_LIKES_remark" style="width:100px;"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitResumeInterview_search_form','recruitResumeInterview_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitResumeInterview_search_form','recruitResumeInterview_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitResumeInterview_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResumeInterview_gridtable_message_custom" class="changebutton"  href="javaScript:"><span>面试安排</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('recruitResumeInterview_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResumeInterview')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="recruitResumeInterview_gridtable_div" class="grid-wrapdiv" class="unitBox"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="recruitResumeInterview_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_recruitResumeInterview_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="recruitResumeInterview_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="recruitResumeInterview_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="recruitResumeInterview_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitResumeInterview_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="recruitResumeInterview_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>