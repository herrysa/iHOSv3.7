
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var deptInspectScoreDetailHisLayout;
	var deptInspectScoreDetailHisGridIdString="#deptInspectScoreDetailHis_gridtable";
	jQuery(document).ready(function() { 
	var inspectContentId = "${requestScope.inspectContentId}";
    	var deptInspectScoreDetailHisGrid = jQuery(deptInspectScoreDetailHisGridIdString);
    deptInspectScoreDetailHisGrid.jqGrid({
    	url : "deptInspectGridList?inspectContentId="+inspectContentId+"&state="+${state}+"&checkPeriod="+${checkPeriod},
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:80},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},
{name:'money2',index:'money3',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},	
{name:'operatorInfo',index:'operatorInfo',align:'center',label : '<s:text name="deptInspect.operator" />',hidden:false},
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,formatter:stringFormatter},
{name:'operator1Info',index:'operator1Info',align:'center',label : '<s:text name="deptInspect.operator1" />',hidden:false},
{name:'remark2',index:'remark2',align:'left',label : '<s:text name="deptInspect.remark2" />',hidden:false,formatter:stringFormatter},
{name:'operator2Info',index:'operator2Info',align:'center',label : '<s:text name="deptInspect.operator2" />',hidden:false},
{name:'remark3',index:'remark3',align:'left',label : '<s:text name="deptInspect.remark3" />',hidden:false,formatter:stringFormatter}
			

        ],
        jsonReader : {
			root : "deptInspects", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rowNum : 1000,
        sortname: 'department.internalCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="inspectBSCList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: false,
		multiboxonly:false,
		shrinkToFit:true,
		autowidth:true,
		gridComplete:function(){
			jQuery("#gview_deptInspectScoreDetailHis_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			});
       	} 

    });
    
    jQuery(deptInspectScoreDetailHisGrid).jqGrid('bindKeys');
    
	
	//inspectBSCLayout.resizeAll();
  	});
</script>
<div class="page">
	<div class="pageContent">
		<div id="deptInspectScoreDetailHis_gridtable_div" extraHeight=92 tablecontainer="deptInspectScoreHis_layout-south"
			class="grid-wrapdiv">
			<div id="load_deptInspectScoreDetailHis_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="deptInspectScoreDetailHis_gridtable"></table>
			<div id="deptInspectScorePager"></div>
		</div>
	</div>
	<%-- <div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreDetailHis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreDetailHis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreDetailHis_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div> --%>
</div>