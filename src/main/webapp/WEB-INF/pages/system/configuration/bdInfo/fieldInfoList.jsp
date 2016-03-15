
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
var fieldInfoGrid = jQuery("#fieldInfo_gridtable");
fieldInfoGrid.jqGrid({
    	url : "fieldInfoGridList?bdInfoId=${bdInfoId}",
    	editurl:"fieldInfoGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'fieldId',index:'fieldId',width:'100px',align:'left',label : '<s:text name="fieldInfo.fieldId" />',hidden:false,key:true},
{name:'fieldCode',index:'fieldCode',width:'100px',align:'left',label : '<s:text name="fieldInfo.fieldCode" />',hidden:false},
{name:'fieldName',index:'fieldName',width:'100px',align:'left',label : '<s:text name="fieldInfo.fieldName" />',hidden:false},
{name:'sn',index:'sn',width : '40',align:'right',label : '<s:text name="fieldInfo.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'entityFieldName',index:'entityFieldName',width:'100px',align:'left',label : '<s:text name="fieldInfo.entityFieldName" />',hidden:false},
{name:'isPkCol',index:'isPkCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isPkCol" />',hidden:false,formatter:'checkbox'},
{name:'isNameCol',index:'isNameCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isNameCol" />',hidden:false,formatter:'checkbox'},
{name:'isCnCodeCol',index:'isCnCodeCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isCnCodeCol" />',hidden:false,formatter:'checkbox'},
{name:'isOrgCol',index:'isOrgCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isOrgCol" />',hidden:false,formatter:'checkbox'},
{name:'isCopyCol',index:'isCopyCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isCopyCol" />',hidden:false,formatter:'checkbox'},
{name:'isPeriodYearCol',index:'isPeriodYearCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isPeriodYearCol" />',hidden:false,formatter:'checkbox'},
{name:'isPeriodMonthCol',index:'isPeriodMonthCol',width:'50px',align:'center',label : '<s:text name="fieldInfo.isPeriodMonthCol" />',hidden:false,formatter:'checkbox'},
{name:'isParentPk',index:'isParentPk',width:'50px',align:'center',label : '<s:text name="fieldInfo.isParentPk" />',hidden:false,formatter:'checkbox'},
{name:'isTypePk',index:'isTypePk',width:'50px',align:'center',label : '<s:text name="fieldInfo.isTypePk" />',hidden:false,formatter:'checkbox'},
{name:'isFk',index:'isFk',width:'50px',align:'center',label : '<s:text name="fieldInfo.isFk" />',hidden:false,formatter:'checkbox'},
{name:'fkTable',index:'fkTable',width:'100px',align:'left',label : '<s:text name="fieldInfo.fkTable" />',hidden:false},
{name:'dataType',index:'dataType',width : 100,align:'center',label : '<s:text name="fieldInfo.dataType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:字符型;2:浮点型;3:布尔型;4:日期型;5:整数型;6:货币型;7:图片型'},highsearch:true},
{name:'dataLength',index:'dataLength',width : 80,align:'right',label : '<s:text name="fieldInfo.dataLength" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'dataDecimal',index:'dataDecimal',width : 80,align:'right',label : '<s:text name="fieldInfo.dataDecimal" />',hidden:false,formatter:'integer',highsearch:true},
{name:'fieldDefault',index:'fieldDefault',width : 100,align:'left',label : '<s:text name="fieldInfo.fieldDefault" />',hidden:false,highsearch:true},
{name:'userTag',index:'userTag',align:'left',width : 80,label : '<s:text name="fieldInfo.userTag" />',hidden:false},				
{name:'parameter1',index:'parameter1',width : 150,align:'left',label : '<s:text name="fieldInfo.parameter1" />',hidden:false,highsearch:true},					
{name:'parameter2',index:'parameter2',width : 150,align:'left',label : '<s:text name="fieldInfo.parameter2" />',hidden:false,highsearch:true},	
{name:'readable',index:'readable',width : 60,align:'center',label : '<s:text name="fieldInfo.readable" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'notNull',index:'notNull',width : 60,align:'center',label : '<s:text name="fieldInfo.notNull" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'batchEdit',index:'batchEdit',width : 60,align:'center',label : '<s:text name="fieldInfo.batchEdit" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'changer.name',index:'changer.name',align:'left',width : 60,label : '<s:text name="fieldInfo.changer" />',hidden:false,highsearch:true},
{name:'changeDate',index:'changeDate',align:'center',width : 70,label : '<s:text name="fieldInfo.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'disabled',index:'disabled',width:'60px',align:'center',label : '<s:text name="fieldInfo.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sysField',index:'sysField',width:'50px',align:'center',label : '<s:text name="fieldInfo.sysField" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="fieldInfo.remark" />',hidden:false}
        ],
        jsonReader : {
			root : "fieldInfoes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="statisticsConditionList.title" />',
        height:'100%',
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
           var dataTest = {"id":"fieldInfo_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(fieldInfoGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div class="pageContent" >
		<div id="fieldInfo_gridtable_div" tablecontainer="bdInfo_layout-south" extraHeight="145"
			class="grid-wrapdiv">
			<input type="hidden" id="fieldInfo_gridtable_navTabId" value="${sessionScope.navTabId}">
<div id="load_fieldInfo_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="fieldInfo_gridtable"></table>
		<div id="fieldInfoPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="fieldInfo_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="fieldInfo_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="fieldInfo_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
