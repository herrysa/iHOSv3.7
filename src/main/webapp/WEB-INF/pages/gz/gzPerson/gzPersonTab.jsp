<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<script>
jQuery(document).ready(function() {
	var gridString = "#gzTypeTab${gzTypeId}_gridtable";
	var gridObject = jQuery(gridString);
	gridObject.jqGrid({
		url : "gzPersonGridList?filter_EQS_gzType=${gzTypeId}",
		editurl : "personGridEdit",
		datatype : "json",
		mtype : "GET",
		colModel : [
			{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,highsearch:true},
			{name : 'name',index : 'name',align : 'left',width:100,label : '<s:text name="person.name" />',hidden : false, sortable:true,highsearch:true},
			{name : 'personCode',index : 'personCode',align : 'center',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false, sortable:true,highsearch:true},
			{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:true,highsearch:true},
			{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:true,highsearch:true},
			{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:true,highsearch:true},
			{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:true,highsearch:true},
			{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:true,formatter:'currency',
					formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
			{name : 'idNumber',index : 'idNumber',align : 'left',width:130,label : '<s:text name="person.idNumber" />',hidden : false, sortable:true,highsearch:true},
			{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'stopSalary',index : 'stopSalary',align : 'center',width:50,label : '<s:text name="person.stopSalary" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true}
			
			/*{name : 'salaryNumber',index : 'salaryNumber',align : 'center',width:70,label : '<s:text name="person.salaryNumber" />',hidden : false, sortable:true},
			{name : 'workBegin',index : 'workBegin',align : 'center',width:70,label : '<s:text name="person.workBegin" />',hidden : false, formatter:'date',formatoptions:{newformat : '%{datePattern}'},sortable:true},
			{name : 'birthday',index : 'birthday',align : 'center',width:70,label : '<s:text name="person.birthday" />',hidden : false, formatter:'date',formatoptions:{newformat : '%{datePattern}'},sortable:true},
			{name : 'duty',index : 'duty',align : 'center',width:70,label : '<s:text name="person.duty" />',hidden : false, sortable:true,edittype:'checkbox',formatter:'checkbox'},
			{name : 'educationalLevel',index : 'educationalLevel',align : 'center',width:70,label : '<s:text name="person.educationalLevel" />',hidden : false, sortable:true,edittype:'checkbox',formatter:'checkbox'},
			{name : 'note',index : 'note',align : 'center',width:70,label : '<s:text name="person.note" />',hidden : false, sortable:true}, */
		],
		jsonReader : {
			root : "persons", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		},
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
	        var activeTab = gzPersonTabPanel.getActiveTab();
	        if(activeTab&&activeTab.id){
	        	var activeId = activeTab.id;
	        	var gzTypeIdTemp = activeId.substring(11);
	        	if("${gzTypeId}"==gzTypeIdTemp){
	        		gzPersonListCountLoad(gzTypeIdTemp);
	        	}
	        }
			var dataTest = {"id":"gzTypeTab${gzTypeId}_gridtable"};
			jQuery.publish("onLoadSelect",dataTest,null);
			makepager("gzTypeTab${gzTypeId}_gridtable");
       	} 
		});
		jQuery(gridObject).jqGrid('bindKeys'); 
});
</script>
</head>
<div id="gzTypeTab${gzTypeId}_page" class="page">
	<div class="pageContent">
		<div id="gzTypeTab${gzTypeId}_gridtable_div" layoutH="10" style="margin-left: 2px; margin-top: 2px; overflow: hidden" tablecontainer="${tablecontainer}" extraWidth="160" extraHeight="78">
			<table id="gzTypeTab${gzTypeId}_gridtable"></table>
			<input id="gzTypeTab${gzTypeId}_gridtable_editFlag" type="hidden"/>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> 
			<select id="gzTypeTab${gzTypeId}_gridtable_numPerPage">
			</select> 
			<span>
				<s:text name="pager.items" />. <s:text name="pager.total" />
				<label id="gzTypeTab${gzTypeId}_gridtable_totals" style="width:10px;float:none;"></label><s:text name="pager.items" />
			</span>
		</div>
		<div id="gzTypeTab${gzTypeId}_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>