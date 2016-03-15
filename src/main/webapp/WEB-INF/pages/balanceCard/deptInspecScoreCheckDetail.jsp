
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
	
	var deptInspectScoreCheckDetailGridIdString="#deptInspectScoreCheckDetail_gridtable";
	jQuery(document).ready(function() { 
	var inspectContentId = "${requestScope.inspectContentId}";
    	var deptInspectScoreCheckDetailGrid = jQuery(deptInspectScoreCheckDetailGridIdString);
    deptInspectScoreCheckDetailGrid.jqGrid({
    	url : "deptInspectGridList?inspectContentId="+inspectContentId+"&scoreType=check",
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:80},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'},hidden:false,width:80},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'},hidden:false,width:80},
{name:'money2',index:'money3',align:'right',label : '<s:text name="deptInspect.money2" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'},hidden:false,width:80},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'},hidden:false,width:80},	
{name:'operatorInfo',index:'operatorInfo',align:'center',label : '<s:text name="deptInspect.operator" />',hidden:false},
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,formatter:stringFormatter},
{name:'remark2',index:'remark2',align:'left',label : '<s:text name="deptInspect.remark2" />',hidden:false,edittype:"textarea",editable:true,formatter:stringFormatter}

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
			jQuery("#gview_deptInspectScoreCheckDetail_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			});
           fullGridEdit(deptInspectScoreCheckDetailGrid);
           gridResize(null,"deptInspectScoreCheck_layout-south","deptInspectScoreCheckDetail_gridtable");
       	} 
    });
    
    jQuery(deptInspectScoreCheckDetailGrid).jqGrid('bindKeys');
    
	//inspectBSCLayout.resizeAll();
  	});
  	function fullGridEdit(gridId){
		try{
		var ids = jQuery(gridId).jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		var cl = ids[i];
		jQuery(gridId).editRow(cl);
	}	
		}catch(err){
			alert(err);
		}
}
  	function saveCheckRemrk(){
  		var sids = "";
  		var remark2 = "";
  		jQuery("input[name=deptinspectId]",deptInspectScoreCheckDetailGridIdString).each(function(){
  			sids += jQuery(this).val()+",";
  		});
  		jQuery("textarea[name=remark2]",deptInspectScoreCheckDetailGridIdString).each(function(){
  			remark2 += jQuery(this).val()+",";
  		});
  		sids += ",end";
  		remark2 += "end";
  		var  url =  'saveCheckRemrk?deptinspectId='+sids+'&remark2='+remark2
  		url=encodeURI(url);
  		$.ajax({
		    url: url,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        alert(data.message);
		    }
		});
  	}
</script>

		<div id="deptInspectScoreCheckDetail_gridtable_div" extraHeight=92 tablecontainer="deptInspectScoreCheck_layout-south"
			class="grid-wrapdiv">
		<div id="load_deptInspectScoreCheckDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="deptInspectScoreCheckDetail_gridtable"></table>
		<div id="deptInspectScorePager"></div>
		</div>