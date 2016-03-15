
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitNeedGridIdString="#recruitNeed_gridtable";
	jQuery(document).ready(function() {
// 		var recruitNeedFullSize = jQuery("#container").innerHeight()-118;
// 		setLeftTreeLayout('recruitNeed_container','recruitNeed_gridtable',recruitNeedFullSize);
		var initFlag = 0;
		var recruitNeedGrid = jQuery(recruitNeedGridIdString);
    	recruitNeedGrid.jqGrid({
	    	url : "recruitNeedGridList?1=1",
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
				{name:'sex',index:'sex',width : '50',align:'left',label : '<s:text name="recruitNeed.sex" />',hidden:false,highsearch:true},	
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.maritalStatus" />',hidden:false,highsearch:true},
				{name:'educationLevel',index:'educationLevel',width : '60',align:'left',label : '<s:text name="recruitNeed.educationLevel" />',hidden:false,highsearch:true},
				{name:'profession',index:'profession',width : '60',align:'left',label : '<s:text name="recruitNeed.profession" />',hidden:false,highsearch:true},
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.politicsStatus" />',hidden:false,highsearch:true},
				{name:'startDate',width : '60',index:'startDate',align:'center',label : '<s:text name="recruitNeed.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'endDate',index:'endDate',width : '60',align:'center',label : '<s:text name="recruitNeed.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'postResponsibility',index:'postResponsibility',width : '150',align:'left',label : '<s:text name="recruitNeed.postResponsibility" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'jobRequirements',index:'jobRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.jobRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'otherRequirements',index:'otherRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.otherRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},
				{name:'workExperience',index:'workExperience',width : '100',align:'left',label : '<s:text name="recruitNeed.workExperience" />',hidden:true,highsearch:true},				
				{name:'workplace',index:'workplace',width : '100',align:'left',label : '<s:text name="recruitNeed.workplace" />',hidden:true,highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="recruitNeed.yearMonth" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : '60',align:'center',label : '<s:text name="recruitNeed.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'maker.name',index:'maker.name',width : '60',align:'left',label : '<s:text name="recruitNeed.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : '60',align:'center',label : '<s:text name="recruitNeed.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',width : '60',align:'left',label : '<s:text name="recruitNeed.checker" />',hidden:false,highsearch:true},
				{name:'doneDate',index:'doneDate',width : '60',align:'center',label : '<s:text name="recruitNeed.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'doner.name',index:'doner.name',width : '60',align:'left',label : '<s:text name="recruitNeed.doner" />',hidden:false,highsearch:true},
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
				 gridContainerResize('recruitNeed','div');
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
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitNeedRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
		             }
		        }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"recruitNeed_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('recruitNeed_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed',initFlag);
       		} 
    	});
    	jQuery(recruitNeedGrid).jqGrid('bindKeys');
    	//实例化ToolButtonBar
        var recruitNeed_menuButtonArrJson = "${menuButtonArrJson}";
        var recruitNeed_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(recruitNeed_menuButtonArrJson,false)));
        var recruitNeed_toolButtonBar = new ToolButtonBar({el:$('#recruitNeed_buttonBar'),collection:recruitNeed_toolButtonCollection,attributes:{
         tableId : 'recruitNeed_gridtable',
         baseName : 'recruitNeed',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="recruitNeedNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var recruitNeed_function = new scriptFunction();
        recruitNeed_function.optBeforeCall = function(e,$this,param){
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
  		recruitNeed_toolButtonBar.addCallBody('1004010101',function(e,$this,param){
  			var url = "editRecruitNeed?popup=true&navTabId=recruitNeed_gridtable";
			var winTitle='<s:text name="recruitNeedNew.title"/>';
			$.pdialog.open(url,'addRecruitNeed',winTitle, {mask:true,width : 700,height : 500,maxable:false});
  		},{});
  		recruitNeed_toolButtonBar.addBeforeCall('1004010101',function(e,$this,param){
			return recruitNeed_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//删除
  		recruitNeed_toolButtonBar.addCallBody('1004010102',function(e,$this,param){
  			 var url = "${ctx}/recruitNeedGridEdit?oper=del";
	         var sid = jQuery("#recruitNeed_gridtable").jqGrid('getGridParam','selarrrow');
	         if(sid==null|| sid.length == 0){       	
	 			alertMsg.error("请选择记录。");
	 			return;
	 			}else {
					for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#recruitNeed_gridtable").jqGrid('getRowData',rowId);
						
						if(row['state']!='1'){
							alertMsg.error("只能删除处于新建状态的记录!");
							return;
						}
					}
					url = url+"&id="+sid+"&navTabId=recruitNeed_gridtable";
					alertMsg.confirm("确认删除？", {
						okCall : function() {
							$.post(url,function(data) {
								formCallBack(data);
							});
						}
					});
				}
  		},{});
  		recruitNeed_toolButtonBar.addBeforeCall('1004010102',function(e,$this,param){
			return recruitNeed_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//修改
  		recruitNeed_toolButtonBar.addCallBody('1004010103',function(e,$this,param){
  			 var sid = jQuery("#recruitNeed_gridtable").jqGrid('getGridParam','selarrrow');
 	        if(sid==null|| sid.length != 1){       	
 				alertMsg.error("请选择一条记录。");
 				return;
 			}
 	        var row = jQuery("#recruitNeed_gridtable").jqGrid('getRowData',sid[0]);
 	        if(row['state']!='1'){
 				alertMsg.error("只能修改处于新建状态的记录!");
 				return;
 			}
 			var winTitle='<s:text name="recruitNeedEdit.title"/>';
 			var url = "editRecruitNeed?popup=true&id="+sid+"&navTabId=recruitNeed_gridtable";
 			$.pdialog.open(url,'editRecruitNeed',winTitle, {mask:true,width : 700,height : 500,maxable:false});
  		},{});
  		recruitNeed_toolButtonBar.addBeforeCall('1004010103',function(e,$this,param){
			return recruitNeed_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//审核
  		recruitNeed_toolButtonBar.addCallBody('1004010104',function(e,$this,param){
  			 var url = "${ctx}/recruitNeedGridEdit?oper=check";
	         var sid = jQuery("#recruitNeed_gridtable").jqGrid('getGridParam','selarrrow');
	         if(sid==null|| sid.length == 0){       	
	 			alertMsg.error("请选择记录。");
	 			return;
	 			}else {
					for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#recruitNeed_gridtable").jqGrid('getRowData',rowId);
						
						if(row['state']!='1'){
							alertMsg.error("只能审核处于新建状态的记录!");
							return;
						}
					}
					url = url+"&id="+sid+"&navTabId=recruitNeed_gridtable";
					alertMsg.confirm("确认审核？", {
						okCall : function() {
							$.post(url,function(data) {
								formCallBack(data);
							});
						}
					});
				}
  		},{});
  		recruitNeed_toolButtonBar.addBeforeCall('1004010104',function(e,$this,param){
			return recruitNeed_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//销审
  		recruitNeed_toolButtonBar.addCallBody('1004010105',function(e,$this,param){
  			 var url = "${ctx}/recruitNeedGridEdit?oper=cancelCheck"
  		         var sid = jQuery("#recruitNeed_gridtable").jqGrid('getGridParam','selarrrow');
  		         if(sid==null|| sid.length == 0){       	
  		 			alertMsg.error("请选择记录。");
  		 			return;
  		 			} else {
  		 					for(var i = 0;i < sid.length; i++){
  		 						var rowId = sid[i];
  		 						var row = jQuery("#recruitNeed_gridtable").jqGrid('getRowData',rowId);
  		 						
  		 						if(row['state']!='2'){
  		 							alertMsg.error("只有已审核的记录才能够被销审!");
  		 							return;
  		 						}
  		 					}
  		 					url = url+"&id="+sid+"&navTabId=recruitNeed_gridtable";
  		 					alertMsg.confirm("确认销审？", {
  		 						okCall : function() {
  		 							$.post(url,function(data) {
  		 								formCallBack(data);
  		 							});
  		 						}
  		 					});
  		 				}
  		},{});
  		recruitNeed_toolButtonBar.addBeforeCall('1004010105',function(e,$this,param){
			return recruitNeed_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//设置表格列
  	     var recruitNeed_setColShowButton = {id:'1004010106',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
  	       callBody:function(){
  	        setColShow('recruitNeed_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed');
  	       }};
  	   recruitNeed_toolButtonBar.addButton(recruitNeed_setColShowButton);//实例化ToolButtonBar
     
      
  	});
	function viewRecruitNeedRecord(id){
		var url = "editRecruitNeed?popup=true&id="+id+"&oper=view";
		$.pdialog.open(url,'viewRecruitNeed_'+id,'查看招聘需求信息', {mask:true,width : 700,height : 500});
	}
</script>

<div class="page">
	<div class="pageHeader" id="recruitNeed_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="recruitNeed_search_form" style="white-space: break-all;word-wrap:break-word;">
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
<!-- 						<input type="text"	id="recruitNeed_search_check_date_from" name="filter_GED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitNeed_search_check_date_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="recruitNeed_search_check_date_to" name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitNeed_search_check_date_from\')}'})"> -->
<!-- 					</label> -->
<!-- 						<label style="float:none;white-space:nowrap" > -->
<%--      					 <s:text name="recruitNeed.yearMonth"/>: --%>
<!--      					 <input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--     					 </label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.doneDate'/>:
						<input type="text"	id="recruitNeed_search_done_date_from" name="filter_GED_doneDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitNeed_search_done_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="recruitNeed_search_done_date_to"  name="filter_LED_doneDate"  style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitNeed_search_done_date_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.state'/>:
						<s:select name="filter_EQS_state"  list="#{'':'--','1':'新建','2':'已审核','3':'已处理'}" style="font-size:9pt;" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('recruitNeed_search_form','recruitNeed_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('recruitNeed_search_form','recruitNeed_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="recruitNeed_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="recruitNeed_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitNeed_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitNeed_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitNeed_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="recruitNeed_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('recruitNeed_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--    				 </li> -->
<!-- 			</ul> -->
		</div>
		<div id="recruitNeed_container">
				<div id="recruitNeed_gridtable_div" class="grid-wrapdiv">
					<input type="hidden" id="recruitNeed_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_recruitNeed_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="recruitNeed_gridtable"></table>
				</div>
				</div>
				<div class="panelBar" id="recruitNeed_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="recruitNeed_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="recruitNeed_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="recruitNeed_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
		</div>
</div>