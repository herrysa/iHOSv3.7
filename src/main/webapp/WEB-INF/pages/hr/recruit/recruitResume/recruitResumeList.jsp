
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitResumeGridIdString="#recruitResume_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var recruitResumeGrid = jQuery(recruitResumeGridIdString);
	    recruitResumeGrid.jqGrid({
	    	url : "recruitResumeGridList",
	    	editurl:"recruitResumeGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitResume.id" />',hidden:true,key:true},
				{name:'viewPhoto',index:'viewPhoto',align:'left',width : '20',label : '<s:text name=" " />',hidden:false},	
				{name:'code',index:'code',align:'left',width : '100',label : '<s:text name="recruitResume.code" />',hidden:false,highsearch:true},	
				{name:'recruitDate',index:'recruitDate',width : '70',align:'center',label : '<s:text name="recruitResume.recruitDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'name',index:'name',width : '80',align:'left',label : '<s:text name="recruitResume.name" />',hidden:false,highsearch:true},
				{name:'recruitPlan.hrOrg.orgname',index:'recruitPlan.hrOrg.orgname',width : '130',align:'left',label : '<s:text name="recruitResume.planOrg" />',hidden:false,highsearch:true},
				{name:'recruitPlan.post',index:'recruitPlan.post',width : '60',align:'left',label : '<s:text name="recruitResume.recruitPlan" />',hidden:false,highsearch:true},
				{name:'sex',index:'sex',width : '50',align:'center',label : '<s:text name="recruitResume.sex" />',hidden:false,highsearch:true},
				{name:'age',index:'age',width : '50',align:'right',label : '<s:text name="recruitResume.age" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'birthday',index:'birthday',width : '70',align:'center',label : '<s:text name="recruitResume.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'contactWay',index:'contactWay',width : '100',align:'left',label : '<s:text name="recruitResume.contactWay" />',hidden:false,highsearch:true},				
				{name:'email',index:'email',width : '100',align:'left',label : '<s:text name="recruitResume.email" />',hidden:false,highsearch:true},				
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'workStartDate',index:'workStartDate',width : '70',align:'center',label : '<s:text name="recruitResume.workStartDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'domicilePlace',index:'domicilePlace',width : '100',align:'left',label : '<s:text name="recruitResume.domicilePlace" />',hidden:true,highsearch:true},				
				{name:'presentResidence',index:'presentResidence',width : '100',align:'left',label : '<s:text name="recruitResume.presentResidence" />',hidden:true,highsearch:true},				
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitResume.maritalStatus" />',hidden:false,highsearch:true},				
				{name:'typeOfId',index:'typeOfId',width : '80',align:'left',label : '<s:text name="recruitResume.typeOfId" />',hidden:true,highsearch:true},				
				{name:'idNumber',index:'idNumber',width : '100',align:'left',label : '<s:text name="recruitResume.idNumber" />',hidden:true,highsearch:true},				
				{name:'overseaAssignment',index:'overseaAssignment',width : '80',align:'center',label : '<s:text name="recruitResume.overseaAssignment" />',hidden:true,formatter:'checkbox',highsearch:true},				
				{name:'nation',index:'nation',width : '60',align:'left',label : '<s:text name="recruitResume.nation" />',hidden:false,highsearch:true},				
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitResume.politicsStatus" />',hidden:true,highsearch:true},				
				{name:'foreignLanguage',index:'foreignLanguage',width : '80',align:'left',label : '<s:text name="recruitResume.foreignLanguage" />',hidden:true,highsearch:true},				
				{name:'foreignLanguageLevel',index:'foreignLanguageLevel',width : '80',align:'left',label : '<s:text name="recruitResume.foreignLanguageLevel" />',hidden:true,highsearch:true},				
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
				{name:'state',index:'state',align:'center',width : '60',label : '<s:text name="recruitResume.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions :  {value : '0:新建;1:审查合格;2:拟录用;3:已录用;4:面试不合格'},highsearch:true},
				{name:'interviewState',index:'interviewState',width : '70',align:'center',label : '<s:text name="recruitResume.interviewState" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:待通知;1:已通知;2:联系不上;3:已有工作;4:个人放弃'},highsearch:true},
				{name:'remark',index:'remark',width : '250',align:'left',label : '<s:text name="recruitResume.remark" />',hidden:false,highsearch:true},
			 	{name:'examineEvaluate',index:'examineEvaluate',width : '200',align:'left',label : '<s:text name="recruitResume.examineEvaluate" />',hidden:true,highsearch:true},				
			 	{name:'examineGrade',index:'examineGrade',width : '80',align:'right',label : '<s:text name="recruitResume.examineGrade" />',hidden:true,formatter:'integer',highsearch:true},				
			 	{name:'examinerDate',index:'examinerDate',width : '80',align:'center',label : '<s:text name="recruitResume.examinerDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
			 	{name:'foreignLanguageExaminer',index:'foreignLanguageExaminer',width : '100',align:'left',label : '<s:text name="recruitResume.foreignLanguageExaminer" />',hidden:true,highsearch:true},				
			 	{name:'interviewContactWay',index:'interviewContactWay',width : '100',align:'left',label : '<s:text name="recruitResume.interviewContactWay" />',hidden:true,highsearch:true},				
			 	{name:'interviewContacts',index:'interviewContacts',width : '100',align:'left',label : '<s:text name="recruitResume.interviewContacts" />',hidden:true,highsearch:true},				
			 	{name:'interviewSpace',index:'interviewSpace',width : '100',align:'left',label : '<s:text name="recruitResume.interviewSpace" />',hidden:true,highsearch:true},				
			 	{name:'professionalExaminer',index:'professionalExaminer',width : '100',align:'left',label : '<s:text name="recruitResume.professionalExaminer" />',hidden:true,highsearch:true},				
			 	{name:'reportContacts',index:'reportContacts',width : '100',align:'left',label : '<s:text name="recruitResume.reportContacts" />',hidden:true,highsearch:true},				
			 	{name:'reportContactWay',index:'reportContactWay',width : '100',align:'left',label : '<s:text name="recruitResume.reportContactWay" />',hidden:true,highsearch:true},				
			 	{name:'favoriteState',index:'favoriteState',width : '50',align:'center',label : '<s:text name="recruitResume.position" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions :  {value : '0:当前;1:收藏夹;2:人才库'}},				
			 	{name:'viewState',index:'viewState',width : '50',align:'left',label : '<s:text name="recruitResume.favoriteState" />',hidden:true},
			 	{name:'photo',index:'photo',width : '50',align:'left',label : '<s:text name="recruitResume.photo" />',hidden:true},
			 	{name:'reportDate',index:'reportDate',width : '80',align:'center',label : '<s:text name="recruitResume.reportDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true}			
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
				 /*2015.08.27 form search change*/
				 gridContainerResize('recruitResume','div');
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
		            	var gridName="recruitResume_gridtable";
		            	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitResumeRecord(\''+id+'\',\''+gridName+'\',\''+viewState+'\');">'+ret[i]["code"]+'</a>');
// 		            	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitResumeRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
		              	var editUrl="";
		              	if(ret[i]['state']=="0"){
		              		  setCellText(this,id,'state','<span style="color:black">新建</span>');
		              	}else if(ret[i]['state']=="1"){
		              		  if(ret[i]['interviewState']=="1"||ret[i]['interviewState']=="0"){
		              			setCellText(this,id,'state','<span style="color:green" >审查合格</span>');
		              		  }else{
		              			setCellText(this,id,'state','<span style="color:gray" >审查合格</span>');
		              		  }
		              	}else if(ret[i]['state']=="2"){
		              		editUrl = "'${ctx}/recruitResumeExamineForm?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'state','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeExamine('+editUrl+');">拟录用</a>');
		              	}else if(ret[i]['state']=="3"){
		              		editUrl = "'${ctx}/recruitResumeEmployForm?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'state','<a style="color:red;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeEmploy('+editUrl+');">已录用</a>');
		              	}else if(ret[i]['state']=="4"){
		              		editUrl = "'${ctx}/recruitResumeExamineForm?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'state','<a style="color:gray;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeExamine('+editUrl+');">面试不合格</a>');
		              	}
		              	if(ret[i]['interviewState']=='1'){
		              		editUrl = "'${ctx}/recruitResumeMessage?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'interviewState','<a style="color:green;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeMessage('+editUrl+');">已通知</a>');
		              	}else if(ret[i]['interviewState']=='2'){
		              		editUrl = "'${ctx}/recruitResumeMessage?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'interviewState','<a style="color:gray;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeMessage('+editUrl+');">联系不上</a>');
		              	}else if(ret[i]['interviewState']=='3'){
		              		editUrl = "'${ctx}/recruitResumeMessage?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'interviewState','<a style="color:gray;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeMessage('+editUrl+');">已有工作</a>');
		              	}else if(ret[i]['interviewState']=='4'){
		              		editUrl = "'${ctx}/recruitResumeMessage?id="+ret[i]["id"]+"'";  
	              			setCellText(this,id,'interviewState','<a style="color:gray;text-decoration:underline;cursor:pointer;" onclick="showRecruitResumeMessage('+editUrl+');">个人放弃</a>');
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
	           	var dataTest = {"id":"recruitResume_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	 	initFlag = initColumn('recruitResume_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume',initFlag);
       		} 
    	});
    	jQuery(recruitResumeGrid).jqGrid('bindKeys');
		/*------------------------------button methods-----------------------------------------------------*/
		//实例化ToolButtonBar
     var recruitResume_menuButtonArrJson = "${menuButtonArrJson}";
     var recruitResume_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(recruitResume_menuButtonArrJson,false)));
     var recruitResume_toolButtonBar = new ToolButtonBar({el:$('#recruitResume_buttonBar'),collection:recruitResume_toolButtonCollection,attributes:{
      tableId : 'recruitResume_gridtable',
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
     var recruitResume_function = new scriptFunction();
     recruitResume_function.optBeforeCall = function(e,$this,param){
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
	//导入
  	recruitResume_toolButtonBar.addCallBody('1004030201',function(e,$this,param){
  		alertMsg.correct('导入成功!');
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030201',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
  	//添加
  	recruitResume_toolButtonBar.addCallBody('1004030202',function(e,$this,param){
  		var url = "editRecruitResume?popup=true&navTabId=recruitResume_gridtable";
		var winTitle='<s:text name="recruitResumeNew.title"/>';
		$.pdialog.open(url,'addRecruitResume',winTitle, {mask:true,width : 700,height : 500,maxable:false});
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030202',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
 	 //删除
  	recruitResume_toolButtonBar.addCallBody('1004030203',function(e,$this,param){
  		var url = "${ctx}/recruitResumeGridEdit?oper=del";
        var sid = jQuery("#recruitResume_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 		}else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#recruitResume_gridtable").jqGrid('getRowData',rowId);
				if(row['state']=='0' || row['state']=='3'|| row['state']=='4' 
					|| row['interviewState']=='2' || row['interviewState']=='3' || row['interviewState']=='4'){
				}else{
					alertMsg.error("只能对[新建]、[面试不合格]、[已录用]和面试联系出现异常的记录进行删除!");
					return;
				}
				
			}
			url = url+"&id="+sid+"&navTabId=recruitResume_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030203',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
  	//修改
  	recruitResume_toolButtonBar.addCallBody('1004030204',function(e,$this,param){
  	  var sid = jQuery("#recruitResume_gridtable").jqGrid('getGridParam','selarrrow');
      var winTitle='<s:text name="recruitResumeEdit.title"/>';
      if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
		}else {
			var rowId = sid[0];
			var row = jQuery("#recruitResume_gridtable").jqGrid('getRowData',rowId);
			if(row['state']!='0'){
				alertMsg.error("只能修改新建记录。");
				return;
			}
			var url = "editRecruitResume?popup=true&id="+sid+"&navTabId=recruitResume_gridtable";
			$.pdialog.open(url,'editRecruitResume',winTitle, {mask:true,width : 700,height : 500,maxable:false});
		}
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030204',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
 	 //审查合格
  	recruitResume_toolButtonBar.addCallBody('1004030205',function(e,$this,param){
  		var url = "${ctx}/recruitResumeGridEdit?oper=qualified";
        var sid = jQuery("#recruitResume_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 		}else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#recruitResume_gridtable").jqGrid('getRowData',rowId);
				if(row['state']!='0' && row['state']!='4'){
					alertMsg.error("只能对新建或者面试不合格的记录进行审查!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=recruitResume_gridtable";
			alertMsg.confirm("确认审查合格？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030205',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
 	 //添加到收藏夹
  	recruitResume_toolButtonBar.addCallBody('1004030206',function(e,$this,param){
  		var url = "${ctx}/recruitResumeGridEdit?oper=favorite";
        var sid = jQuery("#recruitResume_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 		}else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#recruitResume_gridtable").jqGrid('getRowData',rowId);
				if(row['favoriteState']=='1'){
					alertMsg.error("简历已经被收藏!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=recruitResume_gridtable";
			alertMsg.confirm("确认添加到收藏夹？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030206',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
  	//添加到人才库
  	recruitResume_toolButtonBar.addCallBody('1004030207',function(e,$this,param){
  		var url = "${ctx}/recruitResumeGridEdit?oper=talentPool";
     	var sid = jQuery("#recruitResume_gridtable").jqGrid('getGridParam','selarrrow');
     	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#recruitResume_gridtable").jqGrid('getRowData',rowId);
				if(row['favoriteState']=='2'){
					alertMsg.error("简历已在人才库!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=recruitResume_gridtable";
			alertMsg.confirm("确认添加到人才库？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	},{});
  	recruitResume_toolButtonBar.addBeforeCall('1004030207',function(e,$this,param){
		return recruitResume_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
  //设置表格列
    var recruitResume_setColShowButton = {id:'1004030208',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
      callBody:function(){
       setColShow('recruitResume_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume');
      }};
    recruitResume_toolButtonBar.addButton(recruitResume_setColShowButton);//实例化ToolButtonBar
  	});
	
	//状态超链接查看
	//报到方式
	function showRecruitResumeEmploy(url){
		$.pdialog.open(url+"&oper=view",'viewRecruitResumeEmploy','报到信息', {mask:true,width : 700,height : 350});
	}
	//面试结果
	function showRecruitResumeExamine(url){
		$.pdialog.open(url+"&oper=view",'viewRecruitResumeExamine','测评结果', {mask:true,width : 700,height : 350});
	}
	//面试安排
	function showRecruitResumeMessage(url){
		$.pdialog.open(url+"&oper=view",'viewRecruitResumeMessage','面试安排信息', {mask:true,width : 700,height : 400});
	}
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
	<div class="pageHeader" id="recruitResume_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitResume_search_form" style="white-space: break-all;word-wrap:break-word;">
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='recruitResume.code'/>: --%>
<!-- 					 	<input type="text" name="filter_LIKES_code"/> -->
<!-- 					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.recruitDate'/>:
						<input type="text"	id="recruitResume_search_recruit_date_from" name="filter_GED_recruitDate"  style="width:60px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitResume_search_recruit_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitResume_search_recruit_date_to"  name="filter_LED_recruitDate" style="width:60px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitResume_search_recruit_date_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.recruitPlan'/>:
					 	<input type="text" style="width:60px;"  name="filter_LIKES_recruitPlan.post"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth" style="width:50px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     					 </label> -->
    					 <label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.sex'/>:
						<s:select name="filter_EQS_sex" list="#{'':'--','男':'男','女':'女'}" style="width:40px;font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:60px;"/>
					</label>	
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.state'/>:
						<s:select name="filter_EQS_state" list="#{'':'--','0':'新建','1':'审查合格','2':'拟录用','3':'已录用','4':'面试不合格'}" style="width:60px;font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.interviewState'/>:
						<s:select name="filter_EQS_interviewState" list="#{'':'--','0':'待通知','1':'已通知','2':'联系不上','3':'已有工作','4':'个人放弃'}" style="width:60px;font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitResume.position'/>:
						<s:select name="filter_EQS_favoriteState" list="#{'':'--','0':'当前','1':'收藏夹','2':'人才库'}" style="width:40px;font-size:9pt;" ></s:select>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='recruitResume.remark'/>: --%>
<!-- 					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/> -->
<!-- 					</label> -->
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitResume_search_form','recruitResume_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitResume_search_form','recruitResume_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitResume_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResume_gridtable_import_custom"  class="excelbutton" href="javaScript:"><span>导入简历 </span> </a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitResume_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitResume_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitResume_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResume_gridtable_qualified_custom" class="checkbutton"  href="javaScript:"><span>审查合格</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResume_gridtable_favorite_custom" class="savebutton"  href="javaScript:"><span>添加到收藏夹</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="recruitResume_gridtable_talentPool_custom" class="importbutton"  href="javaScript:"><span>添加到人才库</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('recruitResume_gridtable','com.huge.ihos.hr.recruitResume.model.RecruitResume')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="recruitResume_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="recruitResume_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_recruitResume_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="recruitResume_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="recruitResume_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="recruitResume_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitResume_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="recruitResume_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>