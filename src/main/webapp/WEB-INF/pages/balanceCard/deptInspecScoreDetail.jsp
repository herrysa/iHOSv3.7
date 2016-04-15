
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
	var deptInspectScoreDetailLayout;
	var deptInspectScoreDetailGridIdString="#deptInspectScoreDetail_gridtable";
	var lastsel;
	var kpiscore = "${inspectBSCSCore}";
	jQuery(document).ready(function() { 
    	//deptInspectScoreDetailLayout = makeLayout({'baseName':'deptInspectScoreDetail','tableIds':'deptInspectScoreDetail_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
	var inspectContentId = "${requestScope.inspectContentId}";
    	var deptInspectScoreDetailGrid = jQuery(deptInspectScoreDetailGridIdString);
    deptInspectScoreDetailGrid.jqGrid({
    	url : "deptInspectGridList?inspectContentId="+inspectContentId+"&scoreType=new",
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false,width:30},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:80},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,width:80,
				edittype:"text",editrules:{number:true},editable:true,editoptions:{dataEvents:[{type:'blur',fn: function(e) { computeDscore(this); }}]}},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,width:80,edittype:"text",editable:true,editoptions:{dataEvents:[{type:'blur',fn: function(e) { numberValidate(this); }}]}},
{name:'money2',index:'money2',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,width:80,edittype:"text",editable:true,editoptions:{dataEvents:[{type:'blur',fn: function(e) { numberValidate(this); }}]}},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,width:80,edittype:"text",editable:true,editoptions:{dataEvents:[{type:'blur',fn: function(e) { computeScore(this); }}]}},	
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,edittype:"textarea",editable:true,formatter:stringFormatter}
			

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
			jQuery("#gview_deptInspectScoreDetail_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			}); 
           fullGridEdit(deptInspectScoreDetailGrid);
           gridResize(null,"deptInspectScore_layout-south","deptInspectScoreDetail_gridtable");
           if($('#editComputeKpi_hidden').val()=='no'){
	  		   $(deptInspectScoreDetailGridIdString).find("td[aria-describedby='deptInspectScoreDetail_gridtable_inspectBSC.type']").each(function(){
	  			   if($(this).text()=='计算'){
	  			  		$(this).parent().find("input[name='score']").attr('disabled','true');
	  			  		$(this).parent().find("input[name='dscore']").attr('disabled','true');
	  			   }
	  			 //$(this).parent().find("input[name='dscore']").attr('readOnly','true');
	  		   });
           }
       	} 

    });
    
    jQuery(deptInspectScoreDetailGrid).jqGrid('bindKeys');
    
    jQuery(deptInspectScoreDetailGrid).unbind("keydown").bind("keydown",function(e){
		chargeKeyPress(deptInspectScoreDetailGrid,e);
	});
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
  	
  	function computeDscore(obj){
  		//var grid.jqGrid('getRowData',id);
  		if(!numberValidate(obj)){
  			return;
  		}
  		var objValue = jQuery(obj).val();
  		jQuery(obj).val(toDecimal(objValue,jjScoreDecimalPlaces));
  		var dscore = objValue/kpiscore;
  		dscore = toDecimal(dscore*100);
  		jQuery(obj).parent().parent().find("input[name=dscore]").eq(0).val(dscore);
  		//var rowId = jQuery(obj).attr("id").split("_")[0];
  		//var row = jQuery("#deptInspectScoreDetail_gridtable").jqGrid('getRowData',rowId);
  		//var score = row["score"];
  		//alert(score);
  	}
  	function computeScore(obj){
  		if(!numberValidate(obj)){
  			return;
  		}
  		var objValue = jQuery(obj).val();
  		jQuery(obj).val(toDecimal(objValue,2));
  		var score = objValue*kpiscore;
  		score = toDecimal(score/100,jjScoreDecimalPlaces);
  		jQuery(obj).parent().parent().find("input[name=score]").eq(0).val(score);
  	}
  	function numberValidate(obj){
  		var objValue = jQuery(obj).val();
  		if(!isFloatOrNull(objValue)){
  			jQuery(obj).val("")
  			alertMsg.error("请输入数字！");
  			return false;
  		}else{
  			//jQuery(obj).val(toDecimal(objValue));
  			return true;
  		}
  	}
  	function saveScoreEdit(){
  		var deptinspectId = "",score="",money1="",money2="",dscore="",remark="";
  		jQuery("input[name=deptinspectId]").each(function(){
  			deptinspectId += jQuery(this).val()+",";
  		});
  		jQuery("input[name=score]").each(function(){
  			score += jQuery(this).val()+",";
  		});
  		jQuery("input[name=money1]").each(function(){
  			money1 += jQuery(this).val()+",";
  		});
  		jQuery("input[name=money2]").each(function(){
  			money2 += jQuery(this).val()+",";
  		});
  		jQuery("input[name=dscore]").each(function(){
  			dscore += jQuery(this).val()+",";
  		});
  		jQuery("textarea[name=remark]").each(function(){
  			remark += jQuery(this).val()+",";
  		});
  		deptinspectId += "end";
  		score += "end";
  		money1 += "end";
  		money2 += "end";
  		dscore += "end";
  		remark += "end";
  		var url='saveDeptInspect?deptinspectId='+deptinspectId+'&score='+score+'&money1='+money1+'&money2='+money2+'&dscore='+dscore+'&remark='+remark;
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
<div class="page">
	<div class="pageContent">
		<div id="deptInspectScoreDetail_gridtable_div"  extraHeight=92 tablecontainer="deptInspectScore_layout-south"
			class="grid-wrapdiv">
		<div id="load_deptInspectScoreDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="deptInspectScoreDetail_gridtable"></table>
		<div id="deptInspectScorePager"></div>
		</div>
	</div>
</div>
