
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
var kqUpItemFormulaGrid = jQuery("#${random}_kqUpItemFormula_gridtable");
kqUpItemFormulaGrid.jqGrid({
    	url : "kqUpItemFormulaGridList?itemId=${itemId}",
    	editurl:"kqUpItemFormulaGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'formulaId',index:'formulaId',align:'center',label : '<s:text name="kqUpItemFormula.formulaId" />',hidden:true,key:true},
{name:'name',index:'name',width:'120px',align:'left',label : '<s:text name="kqUpItemFormula.name" />',hidden:false},
{name:'kqUpItem.itemName',index:'kqUpItem.itemName',width:'80px',align:'left',label : '<s:text name="kqUpItemFormula.kqUpItem" />',hidden:false},
{name:'sn',index:'sn',width : '40',align:'right',label : '<s:text name="kqUpItemFormula.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'expContent',index:'expContent',width:'400px',align:'left',label : '<s:text name="kqUpItemFormula.expContent" />',hidden:false},
{name:'otherExped',index:'otherExped',width:'60px',align:'center',label : '<s:text name="kqUpItemFormula.otherExped" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'otherExpType',index:'otherExpType',width:'60px',align:'center',label : '<s:text name="kqUpItemFormula.otherExpType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:增加;2:减少;3:乘系数'},highsearch:true},
{name:'otherExpValue',index:'otherExpValue',width:'60px',align:'right',label : '<s:text name="kqUpItemFormula.otherExpValue" />',hidden:false,formatter:'number',highsearch:true},
{name:'inUsed',index:'inUsed',width:'50px',align:'center',label : '<s:text name="kqUpItemFormula.inUsed" />',hidden:true,formatter:'checkbox'}
        ],
        jsonReader : {
			root : "kqUpItemFormulas", // (2)
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
           var dataTest = {"id":"${random}_kqUpItemFormula_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(kqUpItemFormulaGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div class="pageContent" >
		<div id="${random}_kqUpItemFormula_gridtable_div" tablecontainer="${random}_kqUpItem_layout-south" extraHeight="147"
			class="grid-wrapdiv">
			<input type="hidden" id="${random}_kqUpItemFormula_gridtable_navTabId" value="${sessionScope.navTabId}">
<div id="${random}_load_kqUpItemFormula_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="${random}_kqUpItemFormula_gridtable"></table>
		<div id="${random}_kqUpItemFormulaPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_kqUpItemFormula_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_kqUpItemFormula_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="${random}_kqUpItemFormula_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
