
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);

	var deptInspectScoreDetailReadLayout;
	var deptInspectScoreDetailReadGridIdString="#deptInspectScoreDetailRead_gridtable";
	var lastsel;
	var kpiscore = "${inspectBSCSCore}";
	jQuery(document).ready(function() { 
    	//deptInspectScoreDetailReadLayout = makeLayout({'baseName':'deptInspectScoreDetailRead','tableIds':'deptInspectScoreDetailRead_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
	//var inspectContentId = "${requestScope.inspectContentId}";
	var inspectDept = "${inspectDept}";
    	var deptInspectScoreDetailReadGrid = jQuery(deptInspectScoreDetailReadGridIdString);
    deptInspectScoreDetailReadGrid.jqGrid({
    	url : "deptInspectGridList?inspectDept="+inspectDept,
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:80},
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:120},
{name:'kpiItemName',index:'kpiItemName',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:100},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:80},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},
{name:'money2',index:'money3',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,width:110,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},	
{name:'operatorInfo',index:'operatorInfo',align:'center',label : '<s:text name="deptInspect.operator" />',hidden:false},
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,formatter:stringFormatter},
{name:'operator1Info',index:'operator1Info',align:'center',label : '<s:text name="deptInspect.operator1" />',hidden:false},
{name:'remark2',index:'remark2',align:'left',label : '<s:text name="deptInspect.remark2" />',hidden:false,formatter:stringFormatter},
{name:'state',index:'state',align:'center',label : '<s:text name="deptInspect.state" />',hidden:false,width:80,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}					
/* {name:'remark3',index:'remark3',align:'center',label : '<s:text name="deptInspect.remark3" />',hidden:false,edittype:"textarea",editable:true} */
			

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
        rowNum : 20,
        sortname: 'deptinspectId',
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
		onSelectRow: function(id) {
			/* if(id && id!==lastsel){
				jQuery(this).jqGrid('restoreRow',lastsel);
				jQuery(this).jqGrid('editRow',id,true);
				lastsel=id;
			} */
       	},
		gridComplete:function(){
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
          
       	   /* if(jQuery(this).getDataIDs().length>0){
       		   var row = jQuery(this).jqGrid('getRowData',jQuery(this).getDataIDs()[0]);
       		   kpiscore = row["score"];
           } */
           reFormatInspectDeptColumnData(this);
           fullGridEdit(deptInspectScoreDetailReadGrid);
           var dataTest = {"id":"deptInspectScoreDetailRead_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScoreDetailRead_gridtable");
      	 //var gridName = "deptInspectScoreDetailRead_gridtable";
       	} 

    });
    
    jQuery(deptInspectScoreDetailReadGrid).jqGrid('bindKeys');
    
	/* jQuery("#deptInspectScoreDetailRead_gridtable").keydown(function(event){
		var beforRow,afterRow;
		var selId = jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('getGridParam','selarrrow');
		var gridIds = jQuery("#deptInspectScoreDetailRead_gridtable").getDataIDs();
		if(gridIds.length>0){
			for(var gi=0;gi<gridIds.length;gi++){
				if(gridIds[gi]==selId){
					if(gi>0){
						beforRow = gridIds[gi-1];
					}else{
						beforRow = gridIds[0];
					}
					if(gi<gridIds.length-1){
						afterRow = gridIds[gi+1];
					}else{
						afterRow = gridIds[gridIds.length-1];
					}
					break;
				}
				
			}
        } 
		if(event.which==40){
			jQuery("#deptInspectScoreDetailRead_gridtable").resetSelection();
			jQuery("#deptInspectScoreDetailRead_gridtable").setSelection(afterRow,true);
			/* jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('editRow',afterRow,true);
			jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('restoreRow',lastsel);
			selId = jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('getGridParam','selarrrow');
			lastsel = selId; 
			
		}else if(event.which==38){
			jQuery("#deptInspectScoreDetailRead_gridtable").resetSelection();
			jQuery("#deptInspectScoreDetailRead_gridtable").setSelection(beforRow,true);
			//jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('editRow',beforRow,true);
			//jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('restoreRow',lastsel);
			//selId = jQuery("#deptInspectScoreDetailRead_gridtable").jqGrid('getGridParam','selarrrow');
			//lastsel = selId;
		}
	}); */
	
	
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
  	function saveScore(){
  		
  	}
  	
  	function saveAuditRemrk(){
  		var sids = "";
  		var remark2 = "";
  		jQuery("input[name=deptinspectId]").each(function(){
  			sids += jQuery(this).val()+",";
  		});
  		jQuery("textarea[name=remark3]").each(function(){
  			remark2 += jQuery(this).val()+",";
  		});
  		sids += ",end";
  		remark2 += "end";
  		var url = 'saveAuditRemrk?deptinspectId='+sids+'&remark3='+remark2;
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
  	
  	
  	function reFormatInspectDeptColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var data = ret[i]["state"];
	    		 var color = "black";
	    		 var checkperiod = ret[i]["checkperiod"];
	    		 if(data==0){
	    			 data = "新建";
	    		 }else if(data==1){
	    			 data = "已使用";
	    			 color = "green";
	    		 }else if(data==2){
	    			 data = "已确认";
	    			 color = "blue";
	    		 }else if(data==3){
	    			 data = "已初审";
	    			 color = "orange";
	    		 }else if(data==4){
	    			 data = "已审核";
	    			 color = "red";
	    		 }
	    		 setCellText(grid,id,'state','<span style="color:'+color+'" >'+data+"</span>")
	    	 }
	    }
	}
	function setCellText(grid,rowid,colName,cellTxt){
		 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
     for (; iCol<cCol; iCol++) {
         if (cm[iCol].name === colName) {
             tr = grid.rows.namedItem(rowid);
             if (tr) {
                jQuery(tr.cells[iCol]).html(cellTxt);
             }
             break;
         }
     }
		
	}
</script>
<div class="page">
<div id="deptInspectScoreDetailRead_container">
			<div id="deptInspectScoreDetailRead_layout-center"
				class="pane ui-layout-center">
	<%--<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='deptInspectScore.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspectScore.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspectScore.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspectScore.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptInspectScoreGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>--%>
	<div class="pageContent">





<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" href="javaScript:saveAuditRemrk()" ><span><s:text name="button.save"></s:text>
					</span>
				</a>
				</li>
			</ul>
		</div> --%>
		<div id="deptInspectScoreDetailRead_gridtable_div" layoutH="30"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScoreDetailRead_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScoreDetailRead_gridtable_addTile">
				<s:text name="deptInspectScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScoreDetailRead_gridtable_editTile">
				<s:text name="deptInspectScoreEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScoreDetailRead_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScoreDetailRead_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScoreDetailRead_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScoreDetailRead_gridtable"></table>
		<div id="deptInspectScorePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreDetailRead_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreDetailRead_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreDetailRead_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>