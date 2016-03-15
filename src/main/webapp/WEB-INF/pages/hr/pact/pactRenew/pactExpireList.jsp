
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactExpireGridIdString="#pactExpire_gridtable";
	jQuery(document).ready(function() {
		jQuery("#pactExpire_pageHeader").find("select").css("font-size","12px");
		var initFlag = 0;
		var pactExpireGrid = jQuery(pactExpireGridIdString);
   	 	pactExpireGrid.jqGrid({
	    	url : "pactExpireGridList?filter_EQI_signState=3&filter_EQI_lockType=0",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pact.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '120',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},	
				{name:'hrPerson.name',index:'hrPerson.name',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},	
				{name:'hrPerson.personId',index:'hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personSnapCode',index:'personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'dept.hrOrg.orgname',index:'dept.hrOrg.orgname',width : '150',align:'left',label : '<s:text name="pact.orgCode" />',hidden:false,highsearch:true},	
				{name:'dept.name',index:'dept.name',width : '100',align:'left',label : '<s:text name="pact.dept" />',hidden:false,highsearch:true},	
				{name:'post.name',index:'post.name',width : '100',align:'left',label : '<s:text name="pact.post" />',hidden:false,highsearch:true},	
			 	{name:'workContent',index:'workContent',width : '150',align:'left',label : '<s:text name="pact.workContent" />',hidden:false,highsearch:true},		
				{name:'signTimes',index:'signTimes',width : '60',align:'right',label : '<s:text name="pact.signTimes" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'signYear',index:'signYear',width : '60',align:'right',label : '<s:text name="pact.signYear" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'beginDate',index:'beginDate',width : '100',align:'center',label : '<s:text name="pact.beginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'endDate',index:'endDate',width : '100',align:'center',label : '<s:text name="pact.endDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'path',index:'path',align:'center',label : '<s:text name="pact.path" />',hidden:true},				
				{name:'remark',index:'remark',width : '150',align:'left',label : '<s:text name="pact.remark" />',hidden:false,highsearch:true},	
				{name:'pactState',index:'pactState',width : '80',align:'center',label : '<s:text name="pact.pactState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:初签;2:续签'}},				
				{name:'signState',index:'signState',width : '80',align:'center',label : '<s:text name="pact.signState" />',hidden:true,highsearch:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:有效;2:终止;3:解除'}}				
	        ],
	        jsonReader : {
				root : "pacts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'endDate',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="pactList.title" />',
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
			 	gridContainerResize('pactExpire','div',0,30);
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  	id=rowIds[i];
		   	          	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
			   	       	var snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personSnapCode'];
		   	          	setCellText(this,id,'hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPerson.name"]+'</a>');
	             	}
	            }else{
	            	var tw = jQuery("#pactExpire_gridtable").outerWidth();
					jQuery("#pactExpire_gridtable").parent().width(tw);
					jQuery("#pactExpire_gridtable").parent().height(1);
	            }
           		var dataTest = {"id":"pactExpire_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('pactExpire_gridtable','com.huge.ihos.hr.pact.model.PactExpire',initFlag);
       		} 
    	});
    	jQuery(pactExpireGrid).jqGrid('bindKeys');
    	jQuery("#pactExpire_search_form_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
			exceptnullparent : false,
			lazy : false,
			minWidth:'200px',
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery("#pactExpire_search_form_dept").val("");
					jQuery("#pactExpire_search_form_dept_id").val("");
					jQuery("#pactExpire_search_form_post").val("");
					jQuery("#pactExpire_search_form_post_id").val("");
					jQuery("#pactExpire_search_form_dept").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#pactExpire_search_form_orgCode_id").val()+"' order by deptCode",
						exceptnullparent : false,
						selectParent:true,
						lazy : false,
						minWidth:'200px',
						callback:{
							afterClick:function(){
								jQuery("#pactExpire_search_form_post").val("");
								jQuery("#pactExpire_search_form_post_id").val("");
								jQuery("#pactExpire_search_form_post").treeselect({
									dataType : "sql",
									optType : "single",
									sql : "SELECT  id,name FROM hr_post where disabled = 0 and deptId='"+jQuery("#pactExpire_search_form_dept_id").val()+"'",
									lazy : false
								});
							}
						}
					});
				}
			}
		});
    	
    	var pactExpire_menuButtonArrJson = "${menuButtonArrJson}";
    	var pactExpire_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pactExpire_menuButtonArrJson,false)));
    	var pactExpire_toolButtonBar = new ToolButtonBar({el:$('#pactExpire_buttonBar'),collection:pactExpire_toolButtonCollection,attributes:{
    		tableId : 'pactExpire_gridtable',
    		baseName : 'pactExpire',
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
    	
    	var pactExpire_function = new scriptFunction();
    	pactExpire_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
	        return true;
		};
    	pactExpire_toolButtonBar.addCallBody('1003020201',function(e,$this,param){
    		var sid = jQuery("#pactExpire_gridtable").jqGrid('getGridParam','selarrrow');
            if(sid==null || sid.length ==0 ){
            	alertMsg.error("请选择记录。");
    			return;
    		}
    		var winTitle='<s:text name="pactRenew.title"/>';
    		var url = "editPactRenew?navTabId=pactExpire_gridtable&random=${random}&pactIds="+sid;
    		$.pdialog.open(url,'addPactRenew',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		},{});
    	pactExpire_toolButtonBar.addBeforeCall('1003020201',function(e,$this,param){
			return pactExpire_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	var pactExpire_setColShowButton = {id:'1003020299',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
       			callBody:function(){
       				setColShow('pactExpire_gridtable','com.huge.ihos.hr.pact.model.PactExpire');
       		}};
    	pactExpire_toolButtonBar.addButton(pactExpire_setColShowButton);
  	});
</script>

<div class="page" id="pactExpire_page">
	<div class="pageHeader" id="pactExpire_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactExpire_search_form"  style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="filter_LIKES_hrPerson.name"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.orgCode'/>:
      					<input type="hidden" name="filter_EQS_dept.orgCode" id="pactExpire_search_form_orgCode_id"/>
      					<input type="text" id="pactExpire_search_form_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.dept'/>:
      					<input type="hidden" name="filter_EQS_dept.departmentId" id="pactExpire_search_form_dept_id"/>
      					<input type="text" id="pactExpire_search_form_dept"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.post'/>:
      					<input type="hidden" name="filter_EQS_post.id" id="pactExpire_search_form_post_id"/>
      					<input type="text" id="pactExpire_search_form_post"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.pactState'/>:
						<s:select name="filter_EQI_pactState" list="#{'':'--','1':'初签','2':'续签'}" style="width:70px" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='到期天数'/>:
						<input name="pactExpireDayS" type="text" style="width:70px" value="${pactExpireDayS}"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactExpire_search_form','pactExpire_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>		
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactExpire_search_form','pactExpire_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pactExpire_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:renewPact()" ><span><s:text name="续签"/></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('pactExpire_gridtable','com.huge.ihos.hr.pact.model.PactExpire')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="pactExpire_gridtable_div" class="grid-wrapdiv" class="unitBox"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="pactExpire_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_pactExpire_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="pactExpire_gridtable"></table>
		</div>
		<div class="panelBar" id="pactExpire_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="pactExpire_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />.<s:text name="pager.total" /><label id="pactExpire_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="pactExpire_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>