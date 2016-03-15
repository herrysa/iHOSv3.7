
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	
	var deptInspectScoreAuditLayout;
	var deptInspectScoreAuditGridIdString="#deptInspectScoreAudit_gridtable";
	var lastsel;
	jQuery(document).ready(function() { 
    	//deptInspectScoreAuditLayout = makeLayout({'baseName':'deptInspectScoreAudit','tableIds':'deptInspectScoreAudit_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
    	var deptInspectScoreAuditGrid = jQuery(deptInspectScoreAuditGridIdString);
    deptInspectScoreAuditGrid.jqGrid({
    	url : "getInspectDeptScoreStateList",
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:60},	
{name:'scoreStaeId',index:'scoreStaeId',align:'center',label : '<s:text name="deptInspect.department" />',hidden:true,key:true},
{name:'inspectOrg.shortName',index:'inspectOrg.shortName',align:'center',label : '<fmt:message key="hisOrg.orgName" />',hidden:false},
{name:'inspectdept.name',index:'inspectdept.name',align:'center',label : '<s:text name="deptInspect.department" />',hidden:false},
{name:'state',index:'state',align:'center',label : '<s:text name="deptInspect.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}					
        ],
        jsonReader : {
			root : "inspectDeptScoreState", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'checkperiod',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="inspectBSCList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
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
           reFormatColumnData(this);
           var dataTest = {"id":"deptInspectScoreAudit_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("deptInspectScoreAudit_gridtable");
      	 //var gridName = "deptInspectScoreAudit_gridtable";
       	} 

    });
    
    jQuery(deptInspectScoreAuditGrid).jqGrid('bindKeys');
    
	/* jQuery("#deptInspectScoreAudit_gridtable").keydown(function(event){
		var beforRow,afterRow;
		var selId = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
		var gridIds = jQuery("#deptInspectScoreAudit_gridtable").getDataIDs();
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
			jQuery("#deptInspectScoreAudit_gridtable").resetSelection();
			jQuery("#deptInspectScoreAudit_gridtable").setSelection(afterRow,true);
			/* jQuery("#deptInspectScoreAudit_gridtable").jqGrid('editRow',afterRow,true);
			jQuery("#deptInspectScoreAudit_gridtable").jqGrid('restoreRow',lastsel);
			selId = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
			lastsel = selId; 
			
		}else if(event.which==38){
			jQuery("#deptInspectScoreAudit_gridtable").resetSelection();
			jQuery("#deptInspectScoreAudit_gridtable").setSelection(beforRow,true);
			//jQuery("#deptInspectScoreAudit_gridtable").jqGrid('editRow',beforRow,true);
			//jQuery("#deptInspectScoreAudit_gridtable").jqGrid('restoreRow',lastsel);
			//selId = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
			//lastsel = selId;
		}
	}); */
	
	
	//inspectBSCLayout.resizeAll();
	
	/*===================================按钮权限begin============================================*/
    //实例化ToolButtonBar
     var deptInspectScoreAudit_menuButtonArrJson = "${menuButtonArrJson}";
     var deptInspectScoreAudit_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectScoreAudit_menuButtonArrJson,false)));
     var deptInspectScoreAudit_toolButtonBar = new ToolButtonBar({el:$('#deptInspectScoreAudit_buttonBar'),collection:deptInspectScoreAudit_toolButtonCollection,attributes:{
      tableId : 'deptInspectScoreAudit_gridtable',
      baseName : 'deptInspectScoreAudit',
      width : 600,
      height : 600,
      base_URL : null,
      optId : null,
      fatherGrid : null,
      extraParam : null,
      selectNone : "请选择记录。",
      selectMore : "只能选择一条记录。",
      addTitle : '<s:text name="deptInspectScoreAuditNew.title"/>',
      editTitle : null
     }}).render();
     deptInspectScoreAudit_toolButtonBar.addCallBody('0601060101',function(e,$this,param){
    	 var sids = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
 		if(sids.length == 0){
 			alertMsg.error("请选择否决行！");
 			return ;
 		}
 		var checkPeriod = "";
 		var inspectTemplId = "";
 			
 		var row = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getRowData',sids[0]);
 		checkPeriod = row["checkperiod"]; 
 		inspectTemplId = row["inspectTemplId"]; 
 		/* for(var si=0;si<sids.length;si++){
 			var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[si]);
 			inspectDept+=row[""];
 		} */
 		
 		//var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
 		$.ajax({
 		    url: 'denyDeptInspect?navTabId=deptInspectScoreAudit_gridtable&scoreType=audit_check&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
 		    type: 'post',
 		    dataType: 'json',
 		    aysnc:false,
 		    error: function(data){
 		        jQuery('#name').attr("value",data.responseText);
 		    },
 		    success: function(data){
 		        // do something with xml
 		    	formCallBack(data);
 		    }
 		});
     },{});
     /*===================================按钮权限end============================================*/
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

  	function saveScoreEdit(){
  		var sids = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
  		for(var si=0;si<sids.length;si++){
  			jQuery("#deptInspectScoreAudit_gridtable").jqGrid('saveRow',sids[si]);
  		}
  		alertMsg.correct("保存成功！");
  	}
  	
  	function auditDenyDeptInspect(){
		var sids = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids.length == 0){
			alertMsg.error("请选择否决行！");
			return ;
		}
		var checkPeriod = "";
		var inspectTemplId = "";
			
		var row = jQuery("#deptInspectScoreAudit_gridtable").jqGrid('getRowData',sids[0]);
		checkPeriod = row["checkperiod"]; 
		inspectTemplId = row["inspectTemplId"]; 
		/* for(var si=0;si<sids.length;si++){
			var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[si]);
			inspectDept+=row[""];
		} */
		
		//var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
		$.ajax({
		    url: 'denyDeptInspect?navTabId=deptInspectScoreAudit_gridtable&scoreType=audit_check&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		    	formCallBack(data);
		    }
		});
	}
  	
  	function reFormatColumnData(grid){
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
	    		 setCellText(grid,id,'state','<a style="color:'+color+';text-decoration:underline;cursor:pointer;"  onclick="showInspectDeptDetail(\'deptInspectScoreReadDetailList?checkperiod='+checkperiod+'&inspectDept='+id+'\',\''+data+'\')" target="dialog" width="880" height="600">'+data+"</a>")
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
	function showInspectDeptDetail(url,title){
		$.pdialog.open(url, 'showInspectDeptDetail', "职能科室打分状态详细列表", {mask:false,width:1000,height:650});　
		//navTab.openTab("showInspectDeptDetail", url, { title:"职能科室打分状态详细列表", fresh:false, data:{} });
	}
</script>
<div class="page">
<div id="deptInspectScoreAudit_container">
			<div id="deptInspectScoreAudit_layout-center"
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





<div class="panelBar" id = "deptInspectScoreAudit_buttonBar">
			<!-- <ul class="toolBar">
				<li><a class="addbutton" href="javaScript:auditDenyDeptInspect()" ><span>否决
					</span>
				</a>
				</li>
			</ul> -->
		</div>
		<div id="deptInspectScoreAudit_gridtable_div" layoutH="56"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="deptInspectScoreAudit_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="deptInspectScoreAudit_gridtable_addTile">
				<s:text name="deptInspectScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="deptInspectScoreAudit_gridtable_editTile">
				<s:text name="deptInspectScoreEdit.title"/>
			</label>
			<label style="display: none"
				id="deptInspectScoreAudit_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="deptInspectScoreAudit_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_deptInspectScoreAudit_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="deptInspectScoreAudit_gridtable"></table>
		<div id="deptInspectScorePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectScoreAudit_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectScoreAudit_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectScoreAudit_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>