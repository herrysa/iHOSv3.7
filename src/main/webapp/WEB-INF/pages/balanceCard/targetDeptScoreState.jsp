
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);

	var targetDeptScoreStateLayout;
	var targetDeptScoreStateGridIdString="#targetDeptScoreState_gridtable";
	var lastsel;
	jQuery(document).ready(function() { 
    	//targetDeptScoreStateLayout = makeLayout({'baseName':'targetDeptScoreState','tableIds':'targetDeptScoreState_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
    	var targetDeptScoreStateGrid = jQuery(targetDeptScoreStateGridIdString);
    targetDeptScoreStateGrid.jqGrid({
    	url : "getTargetDeptScoreStateList",
    	editurl:"",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'inspectTemplId',index:'inspectTemplId',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:130},	
{name:'inspectTemplName',index:'inspectTemplName',align:'center',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="inspectTempl.periodType" />',hidden:false,width:80},
{name:'inspectOrg.shortName',index:'inspectOrg.shortName',align:'center',label : '<s:text name="hisOrg.orgName" />',hidden:false,width:100,sortable:false},
{name:'inspectdept.name',index:'inspectdept.name',align:'center',label : '<s:text name="deptInspect.department" />',hidden:false,width:200,sortable:false},
{name:'totalcore',index:'totalcore',align:'right',label : '<s:text name="deptInspect.totalscore" />',hidden:false,classes:"blueFont",formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},	
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,classes:"redFont"},	
{name:'money2',index:'money2',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,classes:"greenFont"},	
{name:'scoreStaeId',index:'scoreStaeId',align:'center',label : '<s:text name="deptInspect.department" />',hidden:true,key:true},
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
			gridContainerResize("targetDeptScoreState","div");
			reFormatTargetColumnData(this);
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
          
       	   /* if(jQuery(this).getDataIDs().length>0){
       		   var row = jQuery(this).jqGrid('getRowData',jQuery(this).getDataIDs()[0]);
       		   kpiscore = row["score"];
           } */
           var dataTest = {"id":"targetDeptScoreState_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("targetDeptScoreState_gridtable");
      	 //var gridName = "targetDeptScoreState_gridtable";
       	} 

    });
    
    jQuery(targetDeptScoreStateGrid).jqGrid('bindKeys');
    
	/* jQuery("#targetDeptScoreState_gridtable").keydown(function(event){
		var beforRow,afterRow;
		var selId = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
		var gridIds = jQuery("#targetDeptScoreState_gridtable").getDataIDs();
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
			jQuery("#targetDeptScoreState_gridtable").resetSelection();
			jQuery("#targetDeptScoreState_gridtable").setSelection(afterRow,true);
			/* jQuery("#targetDeptScoreState_gridtable").jqGrid('editRow',afterRow,true);
			jQuery("#targetDeptScoreState_gridtable").jqGrid('restoreRow',lastsel);
			selId = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
			lastsel = selId; 
			
		}else if(event.which==38){
			jQuery("#targetDeptScoreState_gridtable").resetSelection();
			jQuery("#targetDeptScoreState_gridtable").setSelection(beforRow,true);
			//jQuery("#targetDeptScoreState_gridtable").jqGrid('editRow',beforRow,true);
			//jQuery("#targetDeptScoreState_gridtable").jqGrid('restoreRow',lastsel);
			//selId = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
			//lastsel = selId;
		}
	}); */
	
	
	//inspectBSCLayout.resizeAll();
	
    /*===================================按钮权限begin============================================*/
    //实例化ToolButtonBar
     var targetDeptScoreState_menuButtonArrJson = "${menuButtonArrJson}";
     var targetDeptScoreState_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(targetDeptScoreState_menuButtonArrJson,false)));
     var targetDeptScoreState_toolButtonBar = new ToolButtonBar({el:$('#targetDeptScoreState_buttonBar'),collection:targetDeptScoreState_toolButtonCollection,attributes:{
      tableId : 'targetDeptScoreState_gridtable',
      baseName : 'targetDeptScoreState',
      width : 600,
      height : 600,
      base_URL : null,
      optId : null,
      fatherGrid : null,
      extraParam : null,
      selectNone : "请选择记录。",
      selectMore : "只能选择一条记录。",
      addTitle : '<s:text name="targetDeptScoreStateNew.title"/>',
      editTitle : null
     }}).render();
     //终审
     targetDeptScoreState_toolButtonBar.addCallBody('0601060201',function(e,$this,param){
    	 var checkPeriod = "";
 		var inspectTemplId = "";
 		var state = "";
 		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
 		if(sids==null||sids.length==0){
 			alertMsg.error("请选择一条记录！");
 			return;
 		}
 		for(var si=0;si<sids.length;si++){
 			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
 			checkPeriod += row["checkperiod"]+",";
 			inspectTemplId += row["inspectTemplId"]+",";
 			state = row["state"];
 			//alert(state);
 			if(parseInt(state)!=3){
 				alertMsg.error("非已初审状态的计分卡不能终审!");
 				return;
 			}
 		}
 		$.ajax({
 		    url: 'submitDeptInspect?navTabId=targetDeptScoreState_gridtable&scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
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
     //撤销终审
     targetDeptScoreState_toolButtonBar.addCallBody('0601060202',function(e,$this,param){
    	 var checkPeriod = "";
 		var inspectTemplId = "";
 		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
 		var state = "";
 		if(sids==null||sids.length==0){
 			alertMsg.error("请选择一条记录！");
 			return;
 		}
 		for(var si=0;si<sids.length;si++){
 			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
 			checkPeriod = row["checkperiod"];
 			inspectTemplId += row["inspectTemplId"]+",";
 			state = row["state"];
 			//alert(state);
 			if(parseInt(state)!=4){
 				alertMsg.error("只有已审核状态，才能撤销！");
 				return;
 			}
 		}
 		/* for(var si=0;si<sids.length;si++){
 			var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[si]);
 			inspectDept+=row[""];
 		} */
 		
 		//var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
 		$.ajax({
 		    url: 'denyDeptInspect?navTabId=targetDeptScoreState_gridtable&scoreType=audit_audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
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
     //查看计分卡
     targetDeptScoreState_toolButtonBar.addCallBody('0601060203',function(e,$this,param){
    	 var checkPeriod = "";
 		var inspectTemplId = "";
 		var deptName = "";
 		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
 		
 		if(sids==null || sids.length ==0){
 			alertMsg.error("请选择一条记录！");
 			return ;
 		}
 	    if(sids.length>1){
 			alertMsg.error("只能选择一条记录！");
 			return ;
 		}
 		
 		
 		for(var si=0;si<sids.length;si++){
 			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
 			checkPeriod += row["checkperiod"];
 			inspectTemplId += row["inspectTemplId"];
 			deptName += row["inspectdept.name"];
 		}
 		var url = 'showInspectBSC?scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId+'&targetDept='+deptName;
 		url=encodeURI(url);
 		$.pdialog.open(url, 'showInspectBSC', "平衡计分卡", {mask:true,width:900,height:650});　
 		/* $.ajax({
 		    url: 'showInspectBSC?scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
 		    type: 'post',
 		    dataType: 'json',
 		    aysnc:false,
 		    error: function(data){
 		        jQuery('#name').attr("value",data.responseText);
 		    },
 		    success: function(data){
 		        // do something with xml
 		        alert(data.message);
 		    }
 		}); */
     },{});
     //生成得分汇总表
     targetDeptScoreState_toolButtonBar.addCallBody('0601060204',function(e,$this,param){
    	 var taskName ="sp_kh_genReport";
 		var proArgsStr ="${currentPeriod}";
 		var url = 'backUpPerson?taskName='+taskName+'&proArgsStr='+proArgsStr;
 		alertMsg.confirm("确认生成？", {
 			okCall: function(){
 				$.ajax({
 				    url: url,
 				    type: 'post',
 				    dataType: 'json',
 				    aysnc:false,
 				    error: function(data){
 				        
 				    },
 				    success: function(data){
 				    	formCallBack(data);
 				    }
 				});
 			}
 		});
     },{});
     /*===================================按钮权限end============================================*/
     //treeselect
     var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
			sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
			var herpType = "${fns:getHerpType()}";
			if(herpType == "M") {
				sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
			}
			sql += " order by orderCol";
	 	jQuery("#deptInspectDeptScoreTreeSelect").treeselect({
			dataType:"sql",
			optType:"single",
			sql:sql,
			exceptnullparent:false,
			lazy:false,
			minWidth:"220px",
			callback : {}
		});
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
  		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
  		for(var si=0;si<sids.length;si++){
  			jQuery("#targetDeptScoreState_gridtable").jqGrid('saveRow',sids[si]);
  		}
  		alertMsg.correct("保存成功！");
  	}
  	
  	function auditCancelDeptInspect(){
  		var checkPeriod = "";
		var inspectTemplId = "";
		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
		var state = "";
		if(sids==null||sids.length==0){
			alertMsg.error("请选择一条记录！");
			return;
		}
		for(var si=0;si<sids.length;si++){
			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
			checkPeriod = row["checkperiod"];
			inspectTemplId += row["inspectTemplId"]+",";
			state = row["state"];
			//alert(state);
			if(parseInt(state)!=4){
				alertMsg.error("只有已审核状态，才能撤销！");
				return;
			}
		}
		/* for(var si=0;si<sids.length;si++){
			var row = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getRowData',sids[si]);
			inspectDept+=row[""];
		} */
		
		//var sids = jQuery("#deptInspectScoreCheck_gridtable").jqGrid('getGridParam','selarrrow');
		$.ajax({
		    url: 'denyDeptInspect?navTabId=targetDeptScoreState_gridtable&scoreType=audit_audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
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
  	
  	function auditDeptInspect(){
  		var checkPeriod = "";
		var inspectTemplId = "";
		var state = "";
		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
		if(sids==null||sids.length==0){
			alertMsg.error("请选择一条记录！");
			return;
		}
		for(var si=0;si<sids.length;si++){
			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
			checkPeriod += row["checkperiod"]+",";
			inspectTemplId += row["inspectTemplId"]+",";
			state = row["state"];
			//alert(state);
			if(parseInt(state)!=3){
				alertMsg.error("非已初审状态的计分卡不能终审!");
				return;
			}
		}
		$.ajax({
		    url: 'submitDeptInspect?navTabId=targetDeptScoreState_gridtable&scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
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
  	
  	function showInspectBSC(){
  		var checkPeriod = "";
		var inspectTemplId = "";
		var deptName = "";
		var sids = jQuery("#targetDeptScoreState_gridtable").jqGrid('getGridParam','selarrrow');
		
		if(sids==null || sids.length ==0){
			alertMsg.error("请选择一条记录！");
			return ;
		}
	    if(sids.length>1){
			alertMsg.error("只能选择一条记录！");
			return ;
		}
		
		
		for(var si=0;si<sids.length;si++){
			var row = jQuery("#targetDeptScoreState_gridtable").jqGrid('getRowData',sids[si]);
			checkPeriod += row["checkperiod"];
			inspectTemplId += row["inspectTemplId"];
			deptName += row["inspectdept.name"];
		}
		var url = 'showInspectBSC?scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId+'&targetDept='+deptName;
		url=encodeURI(url);
		$.pdialog.open(url, 'showInspectBSC', "平衡计分卡", {mask:true,width:900,height:650});　
		/* $.ajax({
		    url: 'showInspectBSC?scoreType=audit&inspectDept='+sids+'&checkPeriod='+checkPeriod+'&inspectTemplId='+inspectTemplId,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        alert(data.message);
		    }
		}); */
  	}
  	function creatScoreTable(){
		var taskName ="sp_kh_genReport";
		var proArgsStr ="${currentPeriod}";
		var url = 'backUpPerson?taskName='+taskName+'&proArgsStr='+proArgsStr;
		alertMsg.confirm("确认生成？", {
			okCall: function(){
				$.ajax({
				    url: url,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	formCallBack(data);
				    }
				});
			}
		});
	}
  	
  	
  	function reFormatTargetColumnData(grid){
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
  	//addTreeSelect("tree","sync","deptInspectDeptScoreTreeSelect","deptInspectDeptScoreTreeSelect_id","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
</script>
<style>
	.blueFont {
		color: blue;
	}
	.redFont {
		color: red;
	}
	.greenFont {
		color: green;
	}
</style>
<div class="page" id="targetDeptScoreState_page">
<div id="targetDeptScoreState_container">
			<div id="targetDeptScoreState_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="targetDeptScoreState_pageHeader">
			<div class="searchBar">
			<form id="targetDeptScoreState_gridtable_form" class="queryarea-form">
			<label class="queryarea-label" >
					<s:text name='inspectTempl.inspectModelName'/>:
						 	<input type="text"	name="templateName" >
			</label>
				<label class="queryarea-label" >
					<s:text name='deptInspect.department'/>:
					<input type="hidden" name="deptId" id="deptInspectDeptScoreTreeSelect_id">
					<input type="text" id="deptInspectDeptScoreTreeSelect" />
			</label>
			<label class="queryarea-label" >
					<s:text name='inspectTempl.periodType'/>:
						 <select name="periodType" onchange="initkpiSelect(this)">
		        						<option value=''>--</option>
		        						<option value='月'>月</option>
		        						<option value='季'>季</option>
		        						<option value='半年'>半年</option>
		        						<option value='年'>年</option>
		        			</select>
			</label>
			<label class="queryarea-label" >
					<s:text name="deptInspect.state" />:
						<select name="state">
		        						<option value=''>--</option>
		        						<option value='0'>新建</option>
		        						<option value='1'>已使用</option>
		        						<option value='2'>已确认</option>
		        						<option value='3'>已初审</option>
		        						<option value='4'>已审核</option>
		        			</select>
			</label>
				<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('targetDeptScoreState_gridtable_form','targetDeptScoreState_gridtable')"><s:text name='button.search'/></button>
								</div>
						</div>
			</form>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="targetDeptScoreState_buttonBar">
			<!-- <ul class="toolBar">
				<li><a class="confirmbutton" href="javaScript:auditDeptInspect()" ><span>终审
					</span>
				</a>
				</li>
				<li><a class="canceleditbutton" href="javaScript:auditCancelDeptInspect()" ><span>撤销终审
					</span>
				</a>
				</li>
				<li><a class="searchbutton" href="javaScript:showInspectBSC()" ><span>查看计分卡
					</span>
				</a>
				</li>
				<li><a class="reportbutton" href="javaScript:creatScoreTable()" ><span>生成得分汇总表
					</span>
				</a>
				</li>
			</ul> -->
		</div>
		<div id="targetDeptScoreState_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="targetDeptScoreState_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="targetDeptScoreState_gridtable_addTile">
				<s:text name="deptInspectScoreNew.title"/>
			</label> 
			<label style="display: none"
				id="targetDeptScoreState_gridtable_editTile">
				<s:text name="deptInspectScoreEdit.title"/>
			</label>
			<label style="display: none"
				id="targetDeptScoreState_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="targetDeptScoreState_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_targetDeptScoreState_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="targetDeptScoreState_gridtable"></table>
		<div id="deptInspectScorePager"></div>
</div>
	</div>
	<div class="panelBar" id="targetDeptScoreState_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="targetDeptScoreState_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="targetDeptScoreState_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="targetDeptScoreState_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>