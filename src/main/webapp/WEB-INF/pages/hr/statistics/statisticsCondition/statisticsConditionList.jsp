
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
var statisticsConditionGridIdString="#statisticsCondition_gridtable";
	
	jQuery(document).ready(function() { 
		initStatisticsConditionFlag=0;
var statisticsConditionGrid = jQuery(statisticsConditionGridIdString);
    statisticsConditionGrid.jqGrid({
    	url : "statisticsConditionGridList?filter_EQS_parentItem.id=${itemId}",
    	editurl:"statisticsConditionGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="statisticsCondition.id" />',hidden:true,key:true},
{name:'code',index:'code',align:'left',width:'100',label : '<s:text name="statisticsCondition.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',align:'left',width:'100',label : '<s:text name="statisticsCondition.name" />',hidden:false,highsearch:true},	
{name:'expression',index:'expression',align:'left',width:'150',label : '<s:text name="statisticsCondition.expression" />',hidden:false,highsearch:true},
{name:'parentItem.name',index:'parentItem.name',align:'left',width:'100',label : '<s:text name="statisticsCondition.parentItem" />',hidden:false,highsearch:true},
{name:'sn',index:'sn',align:'right',width:'60',label : '<s:text name="statisticsCondition.sn" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'sysFiled',index:'sysFiled',align:'center',width:'60',label : '<s:text name="statisticsCondition.sysFiled" />',hidden:true,formatter:'checkbox',highsearch:false},			
{name:'disabled',index:'disabled',align:'center',width:'60',label : '<s:text name="statisticsCondition.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
// {name:'changeUser.name',index:'changeUser.left',align:'left',width : 100,label : '<s:text name="statisticsCondition.changeUser" />',hidden:false,highsearch:true,highsearch:true},	
// {name:'changeDate',index:'changeDate',align:'center',width:'80',label : '<s:text name="statisticsCondition.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
{name:'remark',index:'remark',align:'left',width:'250',label : '<s:text name="statisticsCondition.remark" />',hidden:false,highsearch:true}			


        ],
        jsonReader : {
			root : "statisticsConditions", // (2)
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
			 //gridContainerResize('statisticsCondition','div');
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var dataTest = {"id":"statisticsCondition_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	  // makepager("statisticsCondition_gridtable");
      	 initStatisticsConditionFlag = initColumn('statisticsCondition_gridtable','com.huge.ihos.hr.statistics.model.StatisticsCondition',initStatisticsConditionFlag);
       	} 

    });
    jQuery(statisticsConditionGrid).jqGrid('bindKeys');
    
	
	
	
	//statisticsConditionLayout.resizeAll();
  	});
</script>

<div class="page">
	<div class="pageContent" >
		<div id="statisticsCondition_gridtable_div" tablecontainer="statisticsCondition_layout-south" extraHeight=82 
			class="grid-wrapdiv">
			<input type="hidden" id="statisticsCondition_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="statisticsCondition_gridtable_addTile">
				<s:text name="statisticsConditionNew.title"/>
			</label> 
			<label style="display: none"
				id="statisticsCondition_gridtable_editTile">
				<s:text name="statisticsConditionEdit.title"/>
			</label>
			<label style="display: none"
				id="statisticsCondition_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="statisticsCondition_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_statisticsCondition_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="statisticsCondition_gridtable"></table>
		<div id="statisticsConditionPager"></div>
</div>
	</div>
	<div class="panelBar" id="statisticsCondition_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="statisticsCondition_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="statisticsCondition_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="statisticsCondition_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
