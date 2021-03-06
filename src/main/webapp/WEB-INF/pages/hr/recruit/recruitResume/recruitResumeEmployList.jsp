
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
  var recruitResumeGridIdString="#recruitResumeEmploy_gridtable";
	
	jQuery(document).ready(function() {
		var initFlag = 0;
		var recruitResumeGrid = jQuery(recruitResumeGridIdString);
	    recruitResumeGrid.jqGrid({
	    	url : "recruitResumeGridList?filter_EQS_state=2",
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
				{name:'birthday',index:'birthday',width : 70,align:'center',label : '<s:text name="recruitResume.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'workStartDate',index:'workStartDate',width : 70,align:'center',label : '<s:text name="recruitResume.workStartDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'contactWay',index:'contactWay',width : 100,align:'left',label : '<s:text name="recruitResume.contactWay" />',hidden:false,highsearch:true},				
				{name:'email',index:'email',width : 100,align:'left',label : '<s:text name="recruitResume.email" />',hidden:false,highsearch:true},		
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="recruitResume.state" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:审查合格;2:拟录用;3:已录用;4:面试不合格'},highsearch:true},
				{name:'examineEvaluate',index:'examineEvaluate',width : 200,align:'left',label : '<s:text name="recruitResume.examineEvaluate" />',hidden:true,highsearch:true},				
				{name:'examineGrade',index:'examineGrade',width : 100,align:'right',label : '<s:text name="recruitResume.examineGrade" />',hidden:true,formatter:'integer',highsearch:true},								
				{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',width : 130,align:'left',label : '<s:text name="recruitResume.orgCode" />',hidden:false,highsearch:true},
				{name:'hrDept.name',index:'hrDept.name',width : 100,align:'left',label : '<s:text name="recruitResume.dept" />',hidden:false,highsearch:true},
				{name:'post.name',index:'post.name',width : 60,align:'left',label : '<s:text name="recruitResume.post" />',hidden:false,highsearch:true},
				{name:'reportDate',index:'reportDate',width : 70,align:'center',label : '<s:text name="recruitResume.reportDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'reportContacts',index:'reportContacts',width : 100,align:'left',label : '<s:text name="recruitResume.reportContacts" />',hidden:false,highsearch:true},				
				{name:'reportContactWay',index:'reportContactWay',width : 100,align:'left',label : '<s:text name="recruitResume.reportContactWay" />',hidden:false,highsearch:true},				
				{name:'interviewState',index:'interviewState',width : 70,align:'center',label : '<s:text name="recruitResume.interviewState" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:待通知;1:已通知;2:联系不上;3:已有工作;4:个人放弃'},highsearch:true},
				{name:'interviewSpace',index:'interviewSpace',width : 100,align:'left',label : '<s:text name="recruitResume.interviewSpace" />',hidden:true,highsearch:true},
				{name:'professionalExaminer',index:'professionalExaminer',width : 80,align:'left',label : '<s:text name="recruitResume.professionalExaminer" />',hidden:true,highsearch:true},
				{name:'foreignLanguageExaminer',index:'foreignLanguageExaminer',width : 80,align:'left',label : '<s:text name="recruitResume.foreignLanguageExaminer" />',hidden:true,highsearch:true},
				{name:'examinerDate',index:'examinerDate',align:'center',width : 70,label : '<s:text name="recruitResume.examinerDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'interviewContacts',index:'interviewContacts',width : 100,align:'left',label : '<s:text name="recruitResume.interviewContacts" />',hidden:true,highsearch:true},	
				{name:'interviewContactWay',index:'interviewContactWay',width : 100,align:'left',label : '<s:text name="recruitResume.interviewContactWay" />',hidden:true,highsearch:true},
				{name:'viewState',index:'viewState',width : '50',align:'left',label : '<s:text name="recruitResume.favoriteState" />',hidden:true},
			 	{name:'photo',index:'photo',width : '50',align:'left',label : '<s:text name="recruitResume.photo" />',hidden:true},
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
				 gridContainerResize('recruitResumeEmploy','div');
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
		            	var gridName="recruitResumeEmploy_gridtable";
		            	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitResumeRecord(\''+id+'\',\''+gridName+'\',\''+viewState+'\');">'+ret[i]["code"]+'</a>');
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
		              		setCellText(this,id,'state','<span style="color:red" >已录用</span>');
		              	}else if(ret[i]['state']=="4"){
		              		setCellText(this,id,'state','<span style="color:gray" >面试不合格</span>');
		              	}
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
           		var dataTest = {"id":"recruitResumeEmploy_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('recruitResumeEmploy_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume',initFlag);
       		} 
    	});
    	jQuery(recruitResumeGrid).jqGrid('bindKeys');
    	
    	//实例化ToolButtonBar
        var recruitResumeEmploy_menuButtonArrJson = "${menuButtonArrJson}";
        var recruitResumeEmploy_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(recruitResumeEmploy_menuButtonArrJson,false)));
        var recruitResumeEmploy_toolButtonBar = new ToolButtonBar({el:$('#recruitResumeEmploy_buttonBar'),collection:recruitResumeEmploy_toolButtonCollection,attributes:{
         tableId : 'recruitResumeEmploy_gridtable',
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
        var recruitResumeEmploy_function = new scriptFunction();
        recruitResumeEmploy_function.optBeforeCall = function(e,$this,param){
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
    //报到信息
     recruitResumeEmploy_toolButtonBar.addCallBody('1004050101',function(e,$this,param){
    	 var sid = jQuery("#recruitResumeEmploy_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var winTitle='<s:text name="recruitResumeEmploy.title"/>';
			var url = "recruitResumeEmployForm?popup=true&id="+sid+"&navTabId=recruitResumeEmploy_gridtable";
			$.pdialog.open(url,'recruitResumeEmploy',winTitle, {mask:true,width : 700,height : 350,maxable:false});
   },{});
     recruitResumeEmploy_toolButtonBar.addBeforeCall('1004050101',function(e,$this,param){
			return recruitResumeEmploy_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
     //录用
     recruitResumeEmploy_toolButtonBar.addCallBody('1004050102',function(e,$this,param){
    	 var url = "${ctx}/recruitResumeGridEdit?oper=employ";
      	var sid = jQuery("#recruitResumeEmploy_gridtable").jqGrid('getGridParam','selarrrow');
      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#recruitResumeEmploy_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='2'){
						alertMsg.error("只能对处于拟录用状态的记录进行录用!");
						return;
					}
					if(!row['post.name']){
						alertMsg.error("请先设置录用报到信息!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=recruitResumeEmploy_gridtable";
				alertMsg.confirm("确认录用？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
   },{});
     recruitResumeEmploy_toolButtonBar.addBeforeCall('1004050102',function(e,$this,param){
			return recruitResumeEmploy_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var recruitResumeEmploy_setColShowButton = {id:'1004050103',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('recruitResumeEmploy_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume');
          }};
        recruitResumeEmploy_toolButtonBar.addButton(recruitResumeEmploy_setColShowButton);//实例化ToolButtonBar
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
	<div class="pageHeader" id="recruitResumeEmploy_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitResumeEmploy_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:80px;"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      					 <s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth" style="width:50px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.recruitDate'/>:
						<input type="text"	id="recruitResumeEmploy_search_recruit_date_from" name="filter_GED_recruitDate"  style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitResumeEmploy_search_recruit_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitResumeEmploy_search_recruit_date_to"  name="filter_LED_recruitDate" style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitResumeEmploy_search_recruit_date_from\')}'})">
					</label>
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
						<s:text name='recruitResume.remark'/>:
					 	<input type="text"  name="filter_LIKES_remark" style="width:80px;"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitResumeEmploy_search_form','recruitResumeEmploy_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitResumeEmploy_search_form','recruitResumeEmploy_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitResumeEmploy_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResumeEmploy_gridtable_report_custom" class="changebutton"  href="javaScript:"><span>设置报到信息</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResumeEmploy_gridtable_employ_custom" class="checkbutton"  href="javaScript:"><span>录用</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('recruitResumeEmploy_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResumeEmploy')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="recruitResumeEmploy_gridtable_div" class="grid-wrapdiv" class="unitBox"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="recruitResumeEmploy_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_recruitResumeEmploy_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="recruitResumeEmploy_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="recruitResumeEmploy_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="recruitResumeEmploy_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitResumeEmploy_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="recruitResumeEmploy_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>