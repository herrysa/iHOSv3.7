
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqMonthDataPersonGridIdString="#kqMonthDataPerson_gridtable";
	jQuery(document).ready(function() {
		jQuery("#kqMonthDataPerson_period").text(lastPeriod);
		var gridObject = jQuery(kqMonthDataPersonGridIdString);
		gridObject.jqGrid({
			url : "kqMonthDataPersonGridList?lastPeriod="+lastPeriod+"&kqTypeId="+kqTypeIdMD+"&curPeriod="+curPeriod+"&curDeptId="+curDeptId,
			editurl : "",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,sortable:false,highsearch:true},
				{name : 'name',index : 'name',align : 'left',width:100,label : '<s:text name="person.name" />',hidden : false, sortable:false,highsearch:true},
				{name : 'personCode',index : 'personCode',align : 'center',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:false,highsearch:true},
				{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false, sortable:false,highsearch:true},
				{name : 'kqType',index:'kqType',align:'center',width:'70px',label : '<s:text name="person.kqType" />',hidden:true},
				{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:false,highsearch:true},
				{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:false,highsearch:true},
				{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:false,highsearch:true},
				{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:false,highsearch:true},
				{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:false,formatter:'currency',
						formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
				{name : 'idNumber',index : 'idNumber',align : 'left',width:130,label : '<s:text name="person.idNumber" />',hidden : false, sortable:false,highsearch:true},
				{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:false,formatter:'checkbox',highsearch:true},
				{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:false,formatter:'checkbox',highsearch:true}
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
				var dataTest = {"id":"kqMonthDataPerson_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
	       	} 
			});
		jQuery(gridObject).jqGrid('bindKeys'); 
		
		/*部门树*/
// 		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
// 		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
// 		sql += " ORDER BY orderCol ";
// 		jQuery("#kqMonthDataPerson_depts").treeselect({
// 			optType : "multi",
// 			dataType : 'sql',
// 			sql : sql,
// 			exceptnullparent : true,
// 			lazy : false,
// 			minWidth : '180px',
// 			selectParent : false
// 		});
		/*人员类别树*/
		jQuery("#kqMonthDataPerson_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px'
		});
		/*按钮事件*/
		jQuery("#kqMonthDataPerson_gridtable_add_custom").bind("click",function(){
			var sid = jQuery("#kqMonthDataPerson_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid == null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return;
			}else{
				jQuery.ajax({
					url: 'refreshKqMonthData',
					data: {curPeriod:curPeriod,lastPeriod:lastPeriod,kqTypeId:kqTypeIdMD,curDeptId:curDeptId,personId:sid},
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
							kqMonthDataGridTableReLoadData();
							$.pdialog.closeCurrent();
						}
					}
				});
			}
		});
  	});
	/*查询*/
	function kqMonthDataPersonGridReload(){
		propertyFilterSearch('kqMonthDataPerson_search_form','kqMonthDataPerson_gridtable');
	}
</script>

<div class="page">
	<div class="pageHeader" id="kqMonthDataPerson_pageHeader">
			<div class="searchBar">	
			<div class="searchContent">
			<form id="kqMonthDataPerson_search_form" >
				<label style="float:none;white-space:nowrap" >
       					<s:text name='kqMonthData.period'/>:
       				<font id="kqMonthDataPerson_period" style="color: red;"></font>
				</label>&nbsp;&nbsp;
				<label><fmt:message key='person.personId'/>：
					<input type="text" name="filter_LIKES_personId" style="width: 100px;"/>
				</label>&nbsp;&nbsp;
				<label><fmt:message key='person.name'/>：
					<input type="text" name="filter_LIKES_name" style="width: 100px;"/>
				</label>&nbsp;&nbsp;
				<label><fmt:message key='person.personCode'/>：
					<input type="text" name="filter_LIKES_personCode" style="width: 100px;"/>
				</label>&nbsp;&nbsp;
<%-- 				<label><fmt:message key="person.departmentName"/>： --%>
<!-- 					<input type="text" id="kqMonthDataPerson_depts" style="width: 100px;"> -->
<!-- 					<input type="hidden" id="kqMonthDataPerson_depts_id" name="filter_INS_department.departmentId"> -->
<!-- 				</label>&nbsp;&nbsp; -->
				<label><fmt:message key='person.status'/>：
					<input id="kqMonthDataPerson_empTypes" type="text" name="filter_INS_status" style="width: 100px;">
					<input id="kqMonthDataPerson_empTypes_id" type="hidden">
				</label>&nbsp;&nbsp;
				<label><fmt:message key='person.disable'/>：
					<s:select list="#{'':'--','1':'是','0':'否'}"  name="filter_EQB_disable"></s:select>
				</label>&nbsp;&nbsp;
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="kqMonthDataPersonGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>		
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="kqMonthDataPersonGridReload()"><s:text name='button.search'/></button> --%>
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
					<a id="kqMonthDataPerson_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
			</ul>
		</div>
		<div id="kqMonthDataPerson_gridtable_div" layoutH="98" class="pageFormContent grid-wrapdiv" style="padding:1px">
			<table id="kqMonthDataPerson_gridtable"></table>
			</div>
		</div>
</div>



