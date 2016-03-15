
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDeptTransferGridIdString="#hrDeptTransfer_gridtable";
	jQuery(document).ready(function() { 
		jQuery("#hrDeptTransfer_pageHeader").find("select").css({"font-size":"12px"});
		var initFlag = 0;
		var hrDeptTransferGrid = jQuery(hrDeptTransferGridIdString);
    	hrDeptTransferGrid.jqGrid({
	    	url : "hrDeptMergeGridList?filter_EQI_type=2",
	    	editurl:"hrDeptMergeGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="hrDeptMerge.id" />',hidden:true,key:true},				
				{name:'mergeNo',index:'mergeNo',align:'left',width:'120',label : '<s:text name="划转编码" />',hidden:false,highsearch:true},				
				// {name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDeptMerge.deptCode" />',hidden:false,highsearch:true},				
				{name:'deptNames',index:'deptNames',align:'left',width:'130',label : '<s:text name="被划转部门" />',hidden:false,highsearch:true},		
				{name:'deptName',index:'deptName',align:'left',width:'130',label : '<s:text name="划转到单位/部门" />',hidden:false,highsearch:true},				
				//{name:'mergeDate',index:'mergeDate',align:'center',width:'80',label : '<s:text name="hrDeptMerge.mergeDate" />',hidden:false,highsearch:true,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},				
				{name:'mergePostAndPerson',index:'mergePostAndPerson',align:'center',width:'100',label : '<s:text name="hrDeptMerge.transferPostAndPerson" />',hidden:false,highsearch:true,formatter:'checkbox'},				
				{name:'mergeReason',index:'mergeReason',align:'left',width:'200',label : '<s:text name="划转原因" />',hidden:false,highsearch:true},				
				{name:'snapCode',index:'snapCode',align:'center',width:'1',label : '<s:text name="hrDeptMerge.snapCode" />',hidden:true},				
				{name:'hrDept.departmentId',index:'hrDept.departmentId',align:'center',width:'1',label : '<s:text name="hrDeptMerge.snapCode" />',hidden:true},				
				<c:if test="${deptNeedCheck=='1'}">
				{name:'makePerson.name',index:'makePerson.name',align:'left',width:'60',label : '<s:text name="hrDeptMerge.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="hrDeptMerge.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptMerge.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="hrDeptMerge.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptMerge.confirmPerson" />',hidden:false,highsearch:true},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="hrDeptMerge.confrimDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				</c:if>
				{name:'state',index:'state',align:'center',width:'60',label : '<s:text name="hrDeptMerge.state" />',hidden:false,highsearch:true,formatter:'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}}				
	        ],
	        jsonReader : {
				root : "hrDeptMerges", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'mergeNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="hrDeptTransferList.title" />',
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
				gridContainerResize('hrDeptTransfer','div');
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
	              		  	setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  	}else if(ret[i]['state']=="3"){
	              		  	setCellText(this,id,'state','<span style="color:blue" >已执行</span>');
	              	  	}
	              	 	//var snapId=ret[i]["hrDept.departmentId"]+'_'+ret[i]["snapCode"];
	              	  	//setCellText(this,id,'deptName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["deptName"]+'</a>');
	              	  setCellText(this,id,'mergeNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptTransfer(\''+id+'\');">'+ret[i]["mergeNo"]+'</a>');
		            }
	            }else{
	 	               var tw = jQuery(this).outerWidth();
	 	               jQuery(this).parent().width(tw);
	 	               jQuery(this).parent().height(1);
	 	            }
	           var dataTest = {"id":"hrDeptTransfer_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	 initFlag = initColumn('hrDeptTransfer_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDeptTransfer',initFlag);
	       	} 
	    });
	    jQuery(hrDeptTransferGrid).jqGrid('bindKeys');
	    /*--------------------------------------tooBar start-------------------------------------------*/
    	var hrDeptTransfer_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrDeptTransfer_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDeptTransfer_menuButtonArrJson,false)));
    	var hrDeptTransfer_toolButtonBar = new ToolButtonBar({el:$('#hrDeptTransfer_buttonBar'),collection:hrDeptTransfer_toolButtonCollection,attributes:{
    		tableId : 'hrDeptTransfer_gridtable',
    		baseName : 'hrDeptTransfer',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : null,
    		editTitle : null
    	}}).render();
    	
    	var hrDeptTransfer_function = new scriptFunction();
    	hrDeptTransfer_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录。");
						return pass;
					}
		        }
		        if(param.opt){
		        	for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#hrDeptTransfer_gridtable").jqGrid('getRowData',rowId);
						if(param.status=='新建'){
							if(row['state']!='1'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}else{
							if(row['state']!='2'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}
			        }
		        }
			}
	        return true;
		};
		//添加
		hrDeptTransfer_toolButtonBar.addCallBody('100102030401',function(e,$this,param){
			var winTitle='<s:text name="新增划转部门"/>';
			var url = "editHrDeptTransfer?popup=true&navTabId=hrDeptTransfer_gridtable&type=2";
			$.pdialog.open(url,'addHrDeptTransfer',winTitle, {mask:true,width : 750,height : 600,maxable:false,resizable:false});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030401',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		hrDeptTransfer_toolButtonBar.addCallBody('100102030402',function(e,$this,param){
			var url = "${ctx}/hrDeptMergeGridEdit?type=2&oper=del"
			var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptTransfer_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030402',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		hrDeptTransfer_toolButtonBar.addCallBody('100102030403',function(e,$this,param){
			var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="修改划转部门"/>';
			var url = "editHrDeptTransfer?popup=true&navTabId=hrDeptTransfer_gridtable&id="+sid;
			$.pdialog.open(url,'editHrDeptTransfer',winTitle, {mask:true,width : 750,height : 600,maxable:false,resizable:false});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030403',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		hrDeptTransfer_toolButtonBar.addCallBody('100102030404',function(e,$this,param){
			var url = "${ctx}/hrDeptMergeGridEdit?type=2&oper=check"
			var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptTransfer_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030404',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		hrDeptTransfer_toolButtonBar.addCallBody('100102030405',function(e,$this,param){
			var url = "${ctx}/hrDeptMergeGridEdit?type=2&oper=cancelCheck"
			var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptTransfer_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030405',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//确认
		hrDeptTransfer_toolButtonBar.addCallBody('100102030406',function(e,$this,param){
			var url = "${ctx}/hrDeptMergeGridEdit?type=2&oper=confirm"
			var sid = jQuery("#hrDeptTransfer_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptTransfer_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认划转？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptTransfer_toolButtonBar.addBeforeCall('100102030406',function(e,$this,param){
			return hrDeptTransfer_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"划转",status:"已审核",checkPeriod:"checkPeriod"});
		var hrDeptTransfer_setColShowButton = {id:'100102030488',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('hrDeptTransfer_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDeptTransfer');
	   			}};
		hrDeptTransfer_toolButtonBar.addButton(hrDeptTransfer_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	function viewHrDeptTransfer(id){
		var winTitle='<s:text name="查看部门划转信息"/>';
		var url = "editHrDeptTransfer?oper=view&id="+id;
		$.pdialog.open(url,'viewHrDeptTransfer',winTitle, {mask:true,width : 680,height : 300,maxable:false,resizable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="hrDeptTransfer_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrDeptTransfer_search_form"  class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='划转到单位/部门'/>:
      					<input type="text" name="filter_LIKES_deptName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='被划转部门'/>:
      					<input type="text" name="filter_LIKES_deptNames"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='hrDeptMerge.mergeDate'/>:
      					<input type="text" id="hrDeptTransfer_search_form_mergeDate_from" name="filter_GED_mergeDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'hrDeptTransfer_search_form_mergeDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="hrDeptTransfer_search_form_mergeDate_to" name="filter_LED_mergeDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'hrDeptTransfer_search_form_mergeDate_from\')}'})">
					</label> --%>
					<label class="queryarea-label">
						<s:text name='划转原因'/>:
      					<input type="text" name="filter_LIKES_mergeReason"/>
					</label>	
					<label class="queryarea-label">
						<s:text name='hrDeptMerge.state'/>:
						<s:select name="filter_EQI_state" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px" ></s:select>
					</label>
					<label class="queryarea-label">
						<s:select id="hrDeptTransfer_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="hrDeptTransferbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('hrDeptTransfer','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('hrDeptTransferbeginDate','hrDeptTransferendDate',0,'hrDeptTransfer_beginDate','hrDeptTransfer_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="hrDeptTransferendDate" name="hrPerson.endDate" onclick="changeSysTimeType('hrDeptTransfer','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('hrDeptTransferbeginDate','hrDeptTransferendDate',1,'hrDeptTransfer_beginDate','hrDeptTransfer_endDate')" style="width:65px"/>
						<input type="hidden" id="hrDeptTransfer_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="hrDeptTransfer_endDate" name="hrPerson.endDate"  />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrDeptTransfer_search_form','hrDeptTransfer_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('hrDeptTransfer_search_form','hrDeptTransfer_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="hrDeptTransfer_buttonBar">

			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addHrDeptTransfer()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:deptTransferListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editHrDeptTransfer()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:deptTransferListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:deptTransferListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:deptTransferListEditOper('confirm')"><span><s:text name="划转" /></span></a>
				</li>-->

		</div>
		<div id="hrDeptTransfer_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="hrDeptTransfer_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_hrDeptTransfer_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="hrDeptTransfer_gridtable"></table>
		</div>
		<div class="panelBar"  id="hrDeptTransfer_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="hrDeptTransfer_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDeptTransfer_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="hrDeptTransfer_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>
