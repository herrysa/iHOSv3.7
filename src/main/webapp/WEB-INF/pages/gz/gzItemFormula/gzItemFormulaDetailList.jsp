
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
var gzItemFormulaDetailGridIdString="#gzItemFormulaDetail_gridtable";
	jQuery(document).ready(function() { 
		initGzItemFormulaDetailFlag=0;
var gzItemFormulaDetailGrid = jQuery(gzItemFormulaDetailGridIdString);
gzItemFormulaDetailGrid.jqGrid({
    	url : "gzItemFormulaGridList?itemId=${itemId}",
    	editurl:"gzItemFormulaGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'formulaId',index:'formulaId',align:'center',label : '<s:text name="gzItemFormula.formulaId" />',hidden:true,key:true},
{name:'name',index:'name',width:'120px',align:'left',label : '<s:text name="gzItemFormula.name" />',hidden:false},
{name:'gzItem.itemName',index:'gzItem.itemName',width:'80px',align:'left',label : '<s:text name="gzItemFormula.gzItem" />',hidden:false},
{name:'sn',index:'sn',width : '40',align:'right',label : '<s:text name="gzItemFormula.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'expContent',index:'expContent',width:'400px',align:'left',label : '<s:text name="gzItemFormula.expContent" />',hidden:false},
{name:'otherExped',index:'otherExped',width:'60px',align:'center',label : '<s:text name="gzItemFormula.otherExped" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'otherExpType',index:'otherExpType',width:'60px',align:'center',label : '<s:text name="gzItemFormula.otherExpType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:增加;2:减少;3:乘系数'},highsearch:true},
{name:'otherExpValue',index:'otherExpValue',width:'60px',align:'right',label : '<s:text name="gzItemFormula.otherExpValue" />',hidden:false,formatter:'number',highsearch:true},
{name:'inUsed',index:'inUsed',width:'50px',align:'center',label : '<s:text name="gzItemFormula.inUsed" />',hidden:false,formatter:'checkbox'}
        ],
        jsonReader : {
			root : "gzItemFormulas", // (2)
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
           var dataTest = {"id":"gzItemFormulaDetail_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	 initGzItemFormulaDetailFlag = initColumn('gzItemFormulaDetail_gridtable','com.huge.ihos.gz.gzItemFormula.model.GzItemFormula',initGzItemFormulaDetailFlag);
       	} 

    });
    jQuery(gzItemFormulaDetailGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div class="pageContent" >

		<div id="gzItemFormulaDetail_gridtable_div" tablecontainer="gzItem_layout-south" extraHeight=147 


			class="grid-wrapdiv">
			<input type="hidden" id="gzItemFormulaDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzItemFormulaDetail_gridtable_addTile">
				<s:text name="gzItemFormulaDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="gzItemFormulaDetail_gridtable_editTile">
				<s:text name="gzItemFormulaDetailEdit.title"/>
			</label>
			<label style="display: none"
				id="gzItemFormulaDetail_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="gzItemFormulaDetail_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_gzItemFormulaDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="gzItemFormulaDetail_gridtable"></table>
		<div id="gzItemFormulaDetailPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzItemFormulaDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzItemFormulaDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="gzItemFormulaDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
