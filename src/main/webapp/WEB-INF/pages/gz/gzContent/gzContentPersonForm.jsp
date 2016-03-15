
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzContentPersonGridIdString="#gzContentPerson_gridtable";
	jQuery(document).ready(function() {
		var initGzContentPersonFlag = 0;
		jQuery("#gzContentPerson_period").text(lastPeriod);
		var gridObject = jQuery(gzContentPersonGridIdString);
		var url = "gzContentPersonGridList?lastPeriod="+lastPeriod+"&gzTypeId="+gzTypeId+"&curPeriod="+curPeriod+"&curIssueNumber="+curIssueNumber;
		url = encodeURI(url);
		gridObject.jqGrid({
			url : url,
			editurl : "",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,sortable:true,highsearch:true},
				{name : 'name',index : 'name',align : 'left',width:100,label : '<s:text name="person.name" />',hidden : false, sortable:true,highsearch:true},
				{name : 'personCode',index : 'personCode',align : 'left',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:true,highsearch:true},
				{name : 'department.departmentId',index : 'department.departmentId',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : true},
				{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false, sortable:true,highsearch:true},
				<c:if test="${herpType=='S2'}">
				{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:70,label : '<s:text name="hisOrg.branchName" />',hidden : false, sortable:true,highsearch:true},
				</c:if>
				{name : 'gzType',index:'gzType',align:'center',width:'70px',label : '<s:text name="person.gzType" />',hidden:true},
				{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:true,highsearch:true},
				{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:true,highsearch:true},
				{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:true,highsearch:true},
				{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:true,highsearch:true},
				{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:true,formatter:'currency',
						formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
				{name : 'idNumber',index : 'idNumber',align : 'left',width:130,label : '<s:text name="person.idNumber" />',hidden : false, sortable:true,highsearch:true},
				{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true}
			],
			jsonReader : {
				root : "monthPersons", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
			rowNum:'10000',
			sortname : 'personCode',
			viewrecords : true,
			sortorder : 'asc',
			height : 300,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			onSelectRow : function(rowid) {

			},
			gridComplete : function() {
				 var rowNum = jQuery(this).getDataIDs().length;
		           if(rowNum<=0){
						var tw = jQuery(this).outerWidth();
			        	jQuery(this).parent().width(tw);
			        	jQuery(this).parent().height(1);
					}
				var dataTest = {"id":"gzContentPerson_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				initGzContentPersonFlag = initColumn('gzContentPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.MonthPerson',initGzContentPersonFlag);
	       	} 
			});
		jQuery(gridObject).jqGrid('bindKeys'); 
		
		/*部门树*/
		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		var herpType = "${fns:getHerpType()}";
		if(herpType == "S2") {
			var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
			if(branchPriv) {
				sql += " and branchCode in " + branchPriv;
			} else {
				sql += " and 1=2";
			}
		}
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#gzContentPerson_depts").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		/*人员类别树*/
		jQuery("#gzContentPerson_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px'
		});
		/*按钮事件*/
		jQuery("#gzContentPerson_gridtable_add_custom").bind("click",function(){
			var sid = jQuery("#gzContentPerson_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid == null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return;
			}else{
				jQuery.ajax({
					url: 'refreshGzContent?columns=',
					data: {curPeriod:curPeriod,curIssueNumber:curIssueNumber,
						lastPeriod:lastPeriod,gzTypeId:gzTypeId,personId:sid},
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
						alertMsg.error("系统错误！");
					},
					success: function(data){
						if(data.statusCode != 200){
							alertMsg.error(data.message);
						}else{
							var nodeIds = "";
							for(var i = 0;i < sid.length; i++) {
								var rowData = jQuery("#gzContentPerson_gridtable").jqGrid('getRowData',sid[i]);
								var deptId = rowData["department.departmentId"];
								nodeIds += deptId + ",";
							}
							gzContentLeftTreeReload(nodeIds,"add");
							gzContentGridTableReLoadData();
							$.pdialog.closeCurrent();
						}
					}
				});
				
			}
		});
  	});
	/*查询*/
	function gzContentPersonGridReload(){
		propertyFilterSearch('gzContentPerson_search_form','gzContentPerson_gridtable');
	}
</script>

<div class="page">
	<div class="pageHeader" id="gzContentPerson_pageHeader">
			<div class="searchBar">	
			<div class="searchContent">
			<form id="gzContentPerson_search_form" class="queryarea-form" >
				<label >
							<s:text name='gzContent.period'/>:
						<font id="gzContentPerson_period" style="color: red;"></font>
				</label>
				<label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}"><fmt:message key="hisOrg.branchName" />：
					<s:select list="branchs" name="filter_EQS_branchCode" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" theme="simple"></s:select>
				</label>
				<label class="queryarea-label"><fmt:message key='person.personId'/>：
					<input type="text" name="filter_LIKES_personId" style="width: 100px;"/>
				</label>
				<label  class="queryarea-label"><fmt:message key='person.name'/>：
					<input type="text" name="filter_LIKES_name" style="width: 100px;"/>
				</label>
				<label class="queryarea-label"><fmt:message key='person.personCode'/>：
					<input type="text" name="filter_LIKES_personCode" style="width: 100px;"/>
				</label>
				<label class="queryarea-label"><fmt:message key="person.departmentName"/>：
					<input type="text" id="gzContentPerson_depts" style="width: 100px;">
					<input type="hidden" id="gzContentPerson_depts_id" name="filter_INS_department.departmentId">
				</label>
				<label class="queryarea-label"><fmt:message key='person.status'/>：
					<input id="gzContentPerson_empTypes" type="text" name="filter_INS_status" style="width: 100px;">
					<input id="gzContentPerson_empTypes_id" type="hidden">
				</label>
				<label class="queryarea-label"><fmt:message key='person.disable'/>：
					<s:select list="#{'':'--','1':'是','0':'否'}"  name="filter_EQB_disable"></s:select>
				</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="gzContentPersonGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>		
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="gzContentPersonGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	
	<div class="pageContent">
<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="gzContentPerson_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="settlebutton" href="javaScript:setColShow('gzContentPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.MonthPerson');"><span><s:text name="设置表格列" /></span></a>
				</li>
			</ul>
		</div>
		<div id="gzContentPerson_gridtable_div" layoutH="98" class="pageFormContent grid-wrapdiv" style="padding:1px">
			<table id="gzContentPerson_gridtable"></table>
			</div>
		</div>
</div>



