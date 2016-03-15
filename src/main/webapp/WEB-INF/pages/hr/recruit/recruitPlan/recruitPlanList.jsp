
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitPlanGridIdString="#recruitPlan_gridtable";
	jQuery(document).ready(function() { 
// 		var recruitPlanFullSize = jQuery("#container").innerHeight()-118;
// 		setLeftTreeLayout('recruitPlan_container','recruitPlan_gridtable',recruitPlanFullSize);
		var initFlag = 0;
		var recruitPlanGrid = jQuery(recruitPlanGridIdString);
	    recruitPlanGrid.jqGrid({
	    	url : "recruitPlanGridList?1=1",
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
				{name:'state',index:'state',width : '40',align:'center',label : '<s:text name="recruitPlan.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已发布;4:已终止'},highsearch:true},	
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
				{name:'makeDate',index:'makeDate',width : '70',align:'center',label : '<s:text name="recruitPlan.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'maker.name',index:'maker.name',width : '70',align:'left',label : '<s:text name="recruitPlan.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : '70',align:'center',label : '<s:text name="recruitPlan.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',width : '70',align:'left',label : '<s:text name="recruitPlan.checker" />',hidden:false,highsearch:true},
				{name:'releasedDate',index:'releasedDate',width : '70',align:'center',label : '<s:text name="recruitPlan.releasedDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'releaseder.name',index:'releaseder.name',width : '70',align:'left',label : '<s:text name="recruitPlan.releaseder" />',hidden:false,highsearch:true},
				{name:'breakDate',index:'breakDate',width : '70',align:'center',label : '<s:text name="recruitPlan.breakDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'breaker.name',index:'breaker.name',width : '70',align:'left',label : '<s:text name="recruitPlan.breaker" />',hidden:false,highsearch:true},
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
	        sortname: 'code',
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
				 gridContainerResize('recruitPlan','div');
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
			            	setCellText(this,id,'state','<span style="color:green">已审核</span>');
			            }else if(ret[i]['state']=="3"){
			            	setCellText(this,id,'state','<span style="color:blue">已发布</span>');
			            }else if(ret[i]['state']=="4"){
			            	setCellText(this,id,'state','<span style="color:red">已终止</span>');
			            }
			            
			            setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitPlanRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
			        }
		        }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	           var dataTest = {"id":"recruitPlan_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   initFlag = initColumn('recruitPlan_gridtable','com.huge.ihos.hr.recruitPlan.model.RecruitPlan',initFlag);
	       	} 
	    });
	    jQuery(recruitPlanGrid).jqGrid('bindKeys');
	    
	  //实例化ToolButtonBar
	     var recruitPlan_menuButtonArrJson = "${menuButtonArrJson}";
	     var recruitPlan_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(recruitPlan_menuButtonArrJson,false)));
	     var recruitPlan_toolButtonBar = new ToolButtonBar({el:$('#recruitPlan_buttonBar'),collection:recruitPlan_toolButtonCollection,attributes:{
	      tableId : 'recruitPlan_gridtable',
	      baseName : 'recruitPlan',
	      width : 600,
	      height : 600,
	      base_URL : null,
	      optId : null,
	      fatherGrid : null,
	      extraParam : null,
	      selectNone : "请选择记录。",
	      selectMore : "只能选择一条记录。",
	      addTitle : '<s:text name="recruitPlanNew.title"/>',
	      editTitle : null
	     }}).render();
	     //实例化结束
	     var recruitPlan_function = new scriptFunction();
	     recruitPlan_function.optBeforeCall = function(e,$this,param){
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
  		recruitPlan_toolButtonBar.addCallBody('1004020201',function(e,$this,param){
  			var url = "editRecruitPlan?popup=true&navTabId=recruitPlan_gridtable";
			var winTitle='<s:text name="recruitPlanNew.title"/>';
			$.pdialog.open(url,'addRecruitPlan',winTitle, {mask:true,width : 700,height : 500,maxable:false});
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020201',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //删除
  		recruitPlan_toolButtonBar.addCallBody('1004020202',function(e,$this,param){
  			var url = "${ctx}/recruitPlanGridEdit?oper=del";
	    	var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
	      	if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='1'){
						alertMsg.error("只能删除处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020202',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //修改
  		recruitPlan_toolButtonBar.addCallBody('1004020203',function(e,$this,param){
  			 var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
 	        if(sid==null|| sid.length != 1){       	
 				alertMsg.error("请选择一条记录。");
 				return;
 			}
 	        var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',sid[0]);
 	        if(row['state']!='1'){
 				alertMsg.error("只能修改处于新建状态的记录!");
 				return;
 			}
 			var winTitle='<s:text name="recruitPlanEdit.title"/>';
 			var url = "editRecruitPlan?popup=true&id="+sid+"&navTabId=recruitPlan_gridtable";
 			$.pdialog.open(url,'editrecruitPlan',winTitle, {mask:true,width : 700,height : 500,maxable:false});
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020203',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //审核
  		recruitPlan_toolButtonBar.addCallBody('1004020204',function(e,$this,param){
  			 var url = "${ctx}/recruitPlanGridEdit?oper=check";
	         var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
	         if(sid==null|| sid.length == 0){       	
	 			alertMsg.error("请选择记录。");
	 			return;
	 		}else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='1'){
						alertMsg.error("只能审核处于新建状态的记录!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
				alertMsg.confirm("确认审核？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020204',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //销审
  		recruitPlan_toolButtonBar.addCallBody('1004020205',function(e,$this,param){
  			 var url = "${ctx}/recruitPlanGridEdit?oper=cancelCheck"
  		         var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
  		         if(sid==null|| sid.length == 0){       	
  		 			alertMsg.error("请选择记录。");
  		 			return;
  		 		} else {
  					for(var i = 0;i < sid.length; i++){
  						var rowId = sid[i];
  						var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
  						if(row['state']!='2'){
  							alertMsg.error("只有已审核的记录才能够被销审!");
  							return;
  						}
  					}
  					url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
  					alertMsg.confirm("确认销审？", {
  						okCall : function() {
  							$.post(url,function(data) {
  								formCallBack(data);
  							});
  						}
  					});
  				}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020205',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //发布
  		recruitPlan_toolButtonBar.addCallBody('1004020206',function(e,$this,param){
  			var url = "${ctx}/recruitPlanGridEdit?oper=confirm"
  		        var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
  		        if(sid==null|| sid.length == 0){       	
  		  			alertMsg.error("请选择记录。");
  		  			return;
  		  		} else {
  					for(var i = 0;i < sid.length; i++){
  						var rowId = sid[i];
  						var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
  						if(row['state']=='2'||row['state']=='4'){
  						}else{
  							alertMsg.error("只有已审核或已终止的记录才能够发布!");
  							return;
  						}
  					}
  					   jQuery.ajax({
  				             url: 'recruitPlanPublishCheck',
  				             data: {id:sid},
  				             type: 'post',
  				             dataType: 'json',
  				             async:false,
  				             error: function(data){
  				             },
  				             success: function(data){
  				             	if(data.checkResult){
  				             		alertMsg.error(data.checkResult);
  									return;
  				             	}
  				             	url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
  								alertMsg.confirm("确认发布？", {
  									okCall : function() {
  										$.post(url,function(data) {
  											formCallBack(data);
  										});
  									}
  								});
  				             }
  				         });
  				}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020206',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //再发布
  		recruitPlan_toolButtonBar.addCallBody('1004020207',function(e,$this,param){
  			var url = "${ctx}/recruitPlanGridEdit?oper=confirmAgain"
  		        var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
  		        if(sid==null|| sid.length == 0){       	
  		  			alertMsg.error("请选择记录。");
  		  			return;
  		  		} else {
  					for(var i = 0;i < sid.length; i++){
  						var rowId = sid[i];
  						var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
  						if(row['state']!='3'){
  							alertMsg.error("只有已发布的记录才能够再发布!");
  							return;
  						}
  					}
  					url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
  					alertMsg.confirm("确认发布？", {
  						okCall : function() {
  							$.post(url,function(data) {
  								formCallBack(data);
  							});
  						}
  					});
  				}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020207',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		 //终止
  		recruitPlan_toolButtonBar.addCallBody('1004020208',function(e,$this,param){
  			var url = "${ctx}/recruitPlanGridEdit?oper=break";
	        var sid = jQuery("#recruitPlan_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length == 0){       	
	  			alertMsg.error("请选择记录。");
	  			return;
	  		} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#recruitPlan_gridtable").jqGrid('getRowData',rowId);
					if(row['state']!='3'){
						alertMsg.error("只有已发布的记录才能够被终止!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=recruitPlan_gridtable";
				alertMsg.confirm("确认终止？", {
					okCall : function() {
						$.post(url,function(data) {
							formCallBack(data);
						});
					}
				});
			}
 		 },{});
  		recruitPlan_toolButtonBar.addBeforeCall('1004020208',function(e,$this,param){
			return recruitPlan_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//设置表格列
  	     var recruitPlan_setColShowButton = {id:'1004020209',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
  	       callBody:function(){
  	        setColShow('recruitPlan_gridtable','com.huge.ihos.hr.recruitPlan.model.RecruitPlan');
  	       }};
  	   recruitPlan_toolButtonBar.addButton(recruitPlan_setColShowButton);//实例化ToolButtonBar
  	     
		//招聘需求
// 		jQuery("#recruitPlan_gridtable_addFromNeed_custom").unbind( 'click' ).bind("click",function(){
// 			var url = "viewRecruitNeed?popup=true&navTabId=recruitPlan_gridtable";
// 			var winTitle='选择招聘需求';
// 			$.pdialog.open(url,'selectRecruitNeed',winTitle, {mask:true,width : 800,height : 600,resizable:false});
// 		}); 
  	});
	
	function viewRecruitPlanRecord(id){
		var url = "editRecruitPlan?popup=true&id="+id+"&oper=view";
		$.pdialog.open(url,'viewRecruitPlan','查看招聘计划信息', {mask:true,width : 700,height : 500,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="recruitPlan_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitPlan_search_form" style="white-space: break-all;word-wrap:break-word;">
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
<%-- 						<s:text name='recruitPlan.checkDate'/>: --%>
<!-- 						<input type="text"	id="recruitPlan_search_check_date_from" name="filter_GED_checkDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitPlan_search_check_date_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="recruitPlan_search_check_date_to"  name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitPlan_search_check_date_from\')}'})"> -->
<!-- 					</label> -->

<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--       					<s:text name="期间"/>: --%>
<!--       					<input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     				 </label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.releasedDate'/>:
						<input type="text"	id="recruitPlan_search_releasedDate_from" name="filter_GED_releasedDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitPlan_search_releasedDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitPlan_search_releasedDate_to" name="filter_LED_releasedDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitPlan_search_releasedDate_from\')}'})">
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='recruitPlan.breakDate'/>: --%>
<!-- 						<input type="text"	id="recruitPlan_search_break_date_from"  name="filter_GED_breakDate" style="width:80px;height:15px" class="Wdate"   onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitPlan_search_break_date_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="recruitPlan_search_break_date_to" name="filter_LED_breakDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitPlan_search_break_date_from\')}'})"> -->
<!-- 					</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.state'/>:
						<s:select id="recruitPlan_search_state"  name="filter_EQS_state"  list="#{'':'--','1':'新建','2':'已审核','3':'已发布','4':'已终止'}" style="font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitPlan.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitPlan_search_form','recruitPlan_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitPlan_search_form','recruitPlan_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitPlan_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_confirm_custom" class="confirmbutton"  href="javaScript:"><span><s:text name="发布" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitPlan_gridtable_confirmAgain_custom" class="submitbutton"  href="javaScript:"><span><s:text name="再发布" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="recruitPlan_gridtable_break_custom" class="disablebutton" href="javaScript:" ><span>终止</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('recruitPlan_gridtable','com.huge.ihos.hr.recruitPlan.model.RecruitPlan')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--   				</li> -->
<!-- 			</ul> -->
		</div>
		<div id="recruitPlan_container">
				<div id="recruitPlan_gridtable_div" class="grid-wrapdiv" class="unitBox" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden"
					buttonBar="optId:id;width:500;height:300">
					<input type="hidden" id="recruitPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_recruitPlan_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
	 				<table id="recruitPlan_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="recruitPlan_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="recruitPlan_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitPlan_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="recruitPlan_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
	</div>
</div>