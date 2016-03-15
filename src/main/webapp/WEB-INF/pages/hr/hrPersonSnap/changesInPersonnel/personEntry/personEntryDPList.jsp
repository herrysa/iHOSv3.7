
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var showIds = null;
	var personEntryDPGridIdString="#personEntryDP_gridtable";
	jQuery(document).ready(function() { 
		jQuery("#personEntryDP_pageHeader").find("select").css("font-size","12px");
		var initFlag = 0;
		var personEntryDPGrid = jQuery(personEntryDPGridIdString);
   		personEntryDPGrid.jqGrid({
	    	url : "hrPersonCurrentGridList?filter_EQB_disable=true",
	    	editurl:"hrPersonCurrentGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'personId',index:'personId',align:'center',label : '<s:text name="hrPersonSnap.personId" />',hidden:true,key:true,highsearch:false},				
				{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrPersonSnap.snapCode" />',hidden:true,highsearch:false},				
				{name:'personCode',index:'personCode',align:'left',width:70,label : '<s:text name="hrPersonSnap.personCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:80,label : '<s:text name="hrPersonSnap.name" />',hidden:false,highsearch:true},
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:false,highsearch:true},		
				{name:'hrOrg.orgCode',index:'hrOrg.orgCode',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:true},		
				{name:'department.departmentId',index:'department.departmentId',width:120,align:'left',label : '<s:text name="hrPersonSnap.departmentId" />',hidden:true},				
				{name:'department.name',index:'department.name',width:120,align:'left',label : '<s:text name="hrPersonSnap.hrDept" />',hidden:false,highsearch:true},				
				{name:'sex',index:'sex',align:'center',width:40,label : '<s:text name="hrPersonSnap.sex" />',hidden:false,highsearch:true},				
				{name:'status.name',index:'status.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.empType" />',hidden:false,highsearch:true},	
				{name:'postType',index:'postType',align:'left',width:60,label : '<s:text name="hrPersonSnap.postType" />',hidden:false,highsearch:true},				
				{name:'duty.name',index:'duty.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.duty" />',hidden:false,highsearch:true},
				{name:'jobTitle',index:'jobTitle',align:'left',width:60,label : '<s:text name="hrPersonSnap.jobTitle" />',hidden:false,highsearch:true},	
				{name:'age',index:'age',width : '40',align:'right',label : '<s:text name="hrPersonSnap.age" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'birthday',index:'birthday',align:'center',width:70,label : '<s:text name="hrPersonSnap.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'idNumber',index:'idNumber',align:'left',width:100,label : '<s:text name="hrPersonSnap.idNumber" />',hidden:false,highsearch:true},
				{name:'nation',index:'nation',align:'left',width:60,label : '<s:text name="hrPersonSnap.nation" />',hidden:false,highsearch:true},	
				{name:'politicalCode',index:'politicalCode',align:'left',width:60,label : '<s:text name="hrPersonSnap.politicalCode" />',hidden:false,highsearch:true},
				{name:'workPhone',index:'workPhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.workPhone" />',hidden:false,highsearch:true},
				{name:'mobilePhone',index:'mobilePhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.mobilePhone" />',hidden:false,highsearch:true},	
				{name:'email',index:'email',align:'left',width:100,label : '<s:text name="hrPersonSnap.email" />',hidden:false,highsearch:true},				
				{name:'entryDate',index:'entryDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.entryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'educationalLevel',index:'educationalLevel',align:'left',width:60,label : '<s:text name="hrPersonSnap.educationalLevel" />',hidden:false,highsearch:true},
				{name:'degree',index:'degree',align:'left',width:60,label : '<s:text name="hrPersonSnap.degree" />',hidden:false,highsearch:true},	
				{name:'school',index:'school',align:'left',width:100,label : '<s:text name="hrPersonSnap.school" />',hidden:false,highsearch:true},	
				{name:'profession',index:'profession',align:'left',width:80,label : '<s:text name="hrPersonSnap.profession" />',hidden:false,highsearch:true},	
				{name:'graduateDate',index:'graduateDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.graduateDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'ratio',index:'ratio',align:'right',width:60,label : '<s:text name="hrPersonSnap.ratio" />',hidden:false,highsearch:true,formatter:'currency',
					formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.0000'}},				
				{name:'disable',index:'disable',align:'center',width:40,label : '<s:text name="hrPersonSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'jjmark',index:'jjmark',align:'center',width:40,label : '<s:text name="hrPersonSnap.jjmark" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'imagePath',index:'imagePath',align:'center',label : '<s:text name="hrPerson.imagePath" />',hidden:true}
	        ],
	        jsonReader : {
				root : "hrPersonCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'personCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="hrPersonCurrentList.title" />',
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
				gridContainerResize("personEntryDP","div",0,327);
	            var dataTest = {"id":"personEntryDP_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('personEntryDP_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonSnap',initFlag);
	       	} 
	       	
    	});
   	 	
// 	   	jQuery("#personEntryDP_queryCommon").treeselect({
// 			dataType : "sql",
// 			optType : "single",
// 			sql : "SELECT id, name FROM sy_query_common WHERE disabled = 0 order by sn asc",
// 			exceptnullparent : false,
// 			lazy : false
// 		});
	   	
    	jQuery("#personEntryDP_empType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			lazy:false
		});
    	
// 		jQuery("#personEntryDP_postType").treeselect({
// 			dataType:"sql",
// 			optType:"multi",
// 			sql:"SELECT value id ,displayContent name , '1' parent FROM t_dictionary_items WHERE dictionary_id = ( SELECT dictionaryId FROM t_dictionary WHERE code='postType' )",
// 			exceptnullparent:false,
// 			selectParent:false,
// 			lazy:false,
// 			minWidth :'130px'
// 		});
		jQuery("#personEntryDP_hrDept").treeselect({
    			dataType:"url",
    			optType:"multi",
    			url:'getHrDeptHisNode',
    			exceptnullparent:false,
    			selectParent:false,
    			minWidth:'230px',
    			lazy:false
    		});
  	});
	function personEntryDPSearchFormReaload(){
		propertyFilterSearch('personEntryDP_search_form','personEntryDP_gridtable',true);
		jQuery("#personEntryDP_gridtable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
	}
	function personEntryDPAdd(){
		var sid = jQuery("#personEntryDP_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null || sid.length !=1){
         	alertMsg.error("请选择一条记录。");
  			return;
  			}
		var row = jQuery("#personEntryDP_gridtable").jqGrid('getRowData',sid[0]);
		sid += '_'+row['snapCode'];
		alertMsg.confirm("确认添加？", {
				okCall : function() {
					$.ajax({
					    url: 'getHrPersonSnapBySnapId?snapId='+sid,
					    type: 'post',
					    data:{},
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					       if(data&&data.hrPersonSnap){
					    	   var hrPersonSnap = data.hrPersonSnap;
					    	   var notAdd = true;
					    	   if(!hrPersonSnap.personCode){
					    		    alertMsg.error("人员编码为空的不能添加。");
					     			return;
					    	   }
					    	   if(jQuery("#personEntry_hrDept_id").val()!= hrPersonSnap.hrDept.departmentId){
					    		   alertMsg.confirm("该人员不在所选部门中，确认添加？", {
					   				okCall : function() {
					   					personEntryAddDp(hrPersonSnap);
					   					$.pdialog.close('personEntryDPList');
					   				}
					    	     });
					    	   }else{
					    		   personEntryAddDp(hrPersonSnap);
					    		   $.pdialog.close('personEntryDPList');
					    	   }
					       }
					    }
					});
				}
			});
	}
	function personEntryAddDp(hrPersonSnap){
		   jQuery("#personEntry_personFrom").val('hpsDisabled');
		   jQuery("#personEntry_personCode").attr('readOnly',"true").removeAttr("onclick").removeAttr("onfocus");
		   if(hrPersonSnap.personId){
    		   jQuery("#personEntry_personId").val(hrPersonSnap.personId);
    	   }
		   if(hrPersonSnap.name){
    		   jQuery("#personEntry_name").val(hrPersonSnap.name);
    	   }
    	   if(hrPersonSnap.personCode){
    		   jQuery("#personEntry_personCode").val(hrPersonSnap.personCode);
    	   }
    	   if(hrPersonSnap.postType){
    		   jQuery("#personEntry_postType").val(hrPersonSnap.postType);
    	   }
    	   if(hrPersonSnap.idNumber){
    		   jQuery("#personEntry_idNumber").val(hrPersonSnap.idNumber);
    	   }
    	   if(hrPersonSnap.jobTitle){
    		   jQuery("#personEntry_jobTitle").val(hrPersonSnap.jobTitle);
    	   }
    	   if(hrPersonSnap.sex){
    		   jQuery("#personEntry_sex").val(hrPersonSnap.sex);
    	   }
    	   if(hrPersonSnap.birthday){
    		   jQuery("#personEntry_birthday").val(new Date(hrPersonSnap.birthday).format('yyyy-MM-dd'));
    	   }
    	   if(hrPersonSnap.age){
    		   jQuery("#personEntry_age").val(hrPersonSnap.age);
    	   }
    	   if(hrPersonSnap.email){
    		   jQuery("#personEntry_email").val(hrPersonSnap.email);
    	   }
    	   if(hrPersonSnap.mobilePhone){
    		   jQuery("#personEntry_mobilePhone").val(hrPersonSnap.mobilePhone);
    	   }
    	   if(hrPersonSnap.politicalCode){
    		   jQuery("#personEntry_personPolCode").val(hrPersonSnap.politicalCode);
    	   }
    	   if(hrPersonSnap.nation){
    		   jQuery("#personEntry_people").val(hrPersonSnap.nation);
    	   }
    	   if(hrPersonSnap.nativePlace){
    		   jQuery("#personEntry_nativePlace").val(hrPersonSnap.nativePlace);
    	   }
    	   if(hrPersonSnap.homeTelephone){
    		   jQuery("#personEntry_homeTelephone").val(hrPersonSnap.homeTelephone);
    	   }
    	   if(hrPersonSnap.domicilePlace){
    		   jQuery("#personEntry_domicilePlace").val(hrPersonSnap.domicilePlace);
    	   }
    	   if(hrPersonSnap.houseAddress){
    		   jQuery("#personEntry_houseAddress").val(hrPersonSnap.houseAddress);
    	   }
    	   if(hrPersonSnap.educationalLevel){
    		   jQuery("#personEntry_educationalLevel").val(hrPersonSnap.educationalLevel);
    	   }
    	   if(hrPersonSnap.degree){
    		   jQuery("#personEntry_degree").val(hrPersonSnap.degree);
    	   }
    	   if(hrPersonSnap.school){
    		   jQuery("#personEntry_school").val(hrPersonSnap.school);
    	   }
    	   if(hrPersonSnap.profession){
    		   jQuery("#personEntry_professional").val(hrPersonSnap.profession);
    	   }
    	   if(hrPersonSnap.maritalStatus){
    		   jQuery("#personEntry_maritalStatus").val(hrPersonSnap.maritalStatus);
    	   }
    	   if(hrPersonSnap.graduateDate){
    		   jQuery("#personEntry_graduateDay").val(new Date(hrPersonSnap.graduateDate).format('yyyy-MM-dd'));
    	   }
	}
</script>
<div class="page" id="personEntryDP_page">
	<div id="personEntryDP_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="personEntryDP_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='hrPersonSnap.personCode'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_personCode" />
					</label>
					<label class="queryarea-label" >
						<s:text name='hrPersonSnap.name'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_name" />
					</label>
					<label class="queryarea-label" >
						<s:text name='hrPersonSnap.hrDept'/>:
						<input type="text" id="personEntryDP_hrDept"/>
					 	<input type="hidden" id="personEntryDP_hrDept_id" name="filter_INS_department.departmentId" />
					</label>
					<label class="queryarea-label" >
       					<s:text name='hrPersonSnap.empType'/>:
      					<input type="text" id="personEntryDP_empType" style="width:100px"/>
					 	<input type="hidden" id="personEntryDP_empType_id" name="filter_SQS_{empType}" />
					 </label>
<!-- 					 <label style="float:none;white-space:nowrap" > -->
<%--        					<s:text name='hrPersonSnap.postType'/>: --%>
<!--       					<input type="text" id="personEntryDP_postType" style="width:100px"/> -->
<!-- 					 	<input type="hidden" id="personEntryDP_postType_id" name="filter_EQS_postType"/> -->
<!-- 					 </label> -->
<!-- 					 <label style="float:none;white-space:nowrap" > -->
<%--        					<s:text name='hrPersonSnap.duty'/>: --%>
<!--       					<input type="text" id="personEntryDP_duty" style="width:100px" name="filter_LIKES_duty.name"/> -->
<!-- 					 </label> -->
					<label class="queryarea-label" >
       					<s:text name='hrPersonSnap.sex'/>:
      					 <s:select
           				  key="hrPersonSnap.sex" name="filter_EQS_sex" 
            			  maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
<!--      				 <label style="float:none;white-space:nowrap" > -->
<%--        					<s:text name='hrPersonSnap.educationalLevel'/>: --%>
<%--       					 <s:select --%>
<%--            				  key="hrPersonSnap.educationalLevel" name="filter_EQS_educationalLevel"  --%>
<%--             			  maxlength="50" list="educationalLevelList" listKey="value" headerKey="" headerValue="--"  --%>
<%--             			  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				 </label> -->
<!--      				 <label style="float:none;white-space:nowrap" > -->
<%--        					<s:text name='hrPersonSnap.nation'/>: --%>
<%--       					 <s:select --%>
<%--            				  key="hrPersonSnap.nation" name="filter_EQS_nation"  --%>
<%--             			  maxlength="50" list="peopleList" listKey="value" headerKey="" headerValue="--"  --%>
<%--             			  listValue="content" emptyOption="false" theme="simple"></s:select> --%>
<!--      				</label> -->
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='hrPersonSnap.queryCommon'/>: --%>
<!-- 					 	<input type="text" id="personEntryDP_queryCommon" style="width:120px"/> -->
<!-- 					 	<input type="hidden" id="personEntryDP_queryCommon_id" name="queryCommonId"/> -->
<!-- 					</label> -->
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="personEntryDPSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
			<%-- <div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="personEntryDPSearchFormReaload()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div> --%>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="personEntryDP_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="personEntryDP_gridtable_add_custom" class="addbutton" href="javaScript:personEntryDPAdd()" ><span><s:text name='确定'/></span></a>
				</li>
			</ul>
		</div>
				<div id="personEntryDP_gridtable_div"  class="grid-wrapdiv" class="unitBox" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="personEntryDP_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_personEntryDP_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="personEntryDP_gridtable"></table>
				</div>
				<div class="panelBar" id="personEntryDP_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="personEntryDP_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="personEntryDP_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="personEntryDP_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
	</div>
</div>
