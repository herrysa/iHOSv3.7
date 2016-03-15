
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);

	
	var inspecDeptScoreDeptHisGridIdString="#inspecDeptScoreDeptHis_gridtable";
	var lastsel;
	var kpiscore = "${inspectBSCSCore}";
	jQuery(document).ready(function() { 
    	var inspecDeptScoreDeptHisGrid = jQuery(inspecDeptScoreDeptHisGridIdString);
    	inspecDeptScoreDeptHisGrid.jqGrid({
    	url : "deptInspectGridList?scoreType=deptHis",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:80},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:80},	
{name:'department.departmentId',index:'department.departmentId',align:'left',label : '<s:text name="deptInspect.department" />',hidden:true,width:80},
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'inspectBSC.weidusW',index:'inspectBSC.weidusW',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,width:160}, 
{name:'inspectBSC.fenleiW',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,width:100},
{name:'inspectBSC.xiangmuW',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:170},
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,width:120,formatter:htmlStringFormatter},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false,width:60},
{name:'inspectBSC.score',index:'inspectBSC.score',align:'left',label : '<s:text name="bsc.score" />',hidden:true,width:100,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:80},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,width:80,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,width:80},
{name:'money2',index:'money2',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,width:80},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,width:80},	
{name:'operatorInfo',index:'operatorInfo',align:'center',label : '<s:text name="deptInspect.operator" />',hidden:false},
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,formatter:stringFormatter},
{name:'operator1Info',index:'operator1Info',align:'center',label : '<s:text name="deptInspect.operator1" />',hidden:false},
{name:'remark2',index:'remark2',align:'left',label : '<s:text name="deptInspect.remark2" />',hidden:false,formatter:stringFormatter},
{name:'operator2Info',index:'operator2Info',align:'center',label : '<s:text name="deptInspect.operator2" />',hidden:false},
{name:'remark3',index:'remark3',align:'left',label : '<s:text name="deptInspect.remark3" />',hidden:false,formatter:stringFormatter},
{name:'state',index:'state',align:'center',label : '<s:text name="deptInspect.state" />',width:70,hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}
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
        rowNum : 100,
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
		grouping:true,
	   	groupingView : {
	   		groupField : ['department.name'],
	   		//groupSummary : [true],
			groupColumnShow : [false,false],
			groupText : ['<span name="groupDiv" style="margin-top:5px;"><input type="hidden" id="hiddenNumInput" value="{1}" /><b>{0}</b> (指标:{1} 总分:<span id="tottleScoreSpan"></span>  实际得分:<span id="getScoreSpan"></span>)</span> '],
			groupDataSorted :[false,false],
			grouping:false
	   	},
		gridComplete:function(){
	   		gridContainerResize("inspecDeptScoreDeptHis","div");
			jQuery("#gview_inspecDeptScoreDeptHis_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			}); 
           fullGridEdit(inspecDeptScoreDeptHisGrid);
           //gridResize(null,"deptInspectScore_layout-south","inspecDeptScoreDeptHis_gridtable");
           var dataTest = {"id":"inspecDeptScoreDeptHis_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	 reFormatInspectDeptScoreHisDeptColumnData(this);
      	 $('span[name=groupDiv]').each(function(){
      		var selectRows = $(this).find('#hiddenNumInput').val();
	    	var index = $(this).parent().parent().next();
	    	var tottleScore = 0.00;
	    	var getScore = 0.00;
			while(selectRows>0){
				tottleScore += parseFloat($('#inspecDeptScoreDeptHis_gridtable').jqGrid('getRowData',index.attr('id'))['inspectBSC.score']);
				getScore+= parseFloat($('#inspecDeptScoreDeptHis_gridtable').jqGrid('getRowData',index.attr('id'))['score']);
	    		index = index.next();
	    		selectRows--;
	    	}
			$(this).find('#tottleScoreSpan').html(tottleScore.toFixed(jjScoreDecimalPlaces));
			$(this).find('#getScoreSpan').html(getScore.toFixed(jjScoreDecimalPlaces));
      	 });
         addDetailButtonForGrid("inspecDeptScoreDeptHis_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
         
       	} 

    });
    
    jQuery(inspecDeptScoreDeptHisGrid).jqGrid('bindKeys');
    
    jQuery(inspecDeptScoreDeptHisGrid).unbind("keydown").bind("keydown",function(e){
		chargeKeyPress(inspecDeptScoreDeptHisGrid,e);
	});
	//inspectBSCLayout.resizeAll();
	
    var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
		sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
		var herpType = "${fns:getHerpType()}";
		if(herpType == "M") {
			sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
		}
		sql += " order by orderCol";
	jQuery("#deptNameTreeSelectHis").treeselect({
		dataType:"sql",
		optType:"single",
		sql:sql,
		minWidth:"200px",
		exceptnullparent:false,
		lazy:false
	});
  	});

  	
	jQuery("#kpiDeptNameHis").treeselect({
		dataType:"sql",
		optType:"single",
		sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where lft>=0 and lft<=40000 and disabled=0 ",
		exceptnullparent:false,
		lazy:false
	});
  	function initkpiSelect(obj){
		var objValue = obj.value;
		
		var filter = "";
		if(objValue=="月"){
			filter = "lft>=0 and lft<=10000 and id<>-1";
		}else if(objValue=="季"){
			filter = "lft>=10000 and lft<=20000 and id<>-2";
		}else if(objValue=="半年"){
			filter = "lft>=20000 and lft<=30000 and id<>-3";
		}else if(objValue=="一年"){
			filter = "lft>=30000 and lft<=40000 and id<>-4";
		}else{
			filter = "lft>=0 and lft<=40000";
		}
		jQuery("#kpiDeptNameHis").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
  	
  	//addTreeSelect("tree","sync","deptNameTreeSelectHis","deptNameTreeSelectHis_id","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
  	
  	
  	jQuery("#openAllDeptInspectHis").unbind("click").bind("click",function(){
		if(jQuery("#openDeptInspectStatusHis").val()=='0'){
			jQuery("#inspecDeptScoreDeptHis_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatusHis").val(1);
			$('#openDeptInspectHisSpan').html('展开');
		}else{
			jQuery("#inspecDeptScoreDeptHis_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatusHis").val(0);
			$('#openDeptInspectHisSpan').html('收缩');
		}
	});
  	
  	function inspectDeptScoreHisDept() {
		var url = "showInspectDeptHis?redirectType=item";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
  
  	function stringFormatter (cellvalue, options, rowObject)	{
		try
			{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
			}
			catch(err)
			{
				return cellvalue;
			}
		return cellvalue;
	}
  	
  	function reFormatInspectDeptScoreHisDeptColumnData(grid){
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
<div class="page" id="inspecDeptScoreDeptHis_page">
	<div class="pageHeader" id="inspecDeptScoreDeptHis_pageHeader">
			<div class="searchBar">
			<form id="deptInspectDeptScoreHis_form" class="queryarea-form">
			<label class="queryarea-label"><s:text name='deptInspect.checkperiod' />：从 <input
									class="input-mini" type="text" name="checkPeriodFrom"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${checkPeriod}" size="10" />到<input
									class="input-mini" type="text" name="checkPeriod"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${checkPeriod}" size="10" /> 
			</label>
			<label class="queryarea-label" >
					<s:text name='deptInspect.department'/>:
					<input type="hidden" name="filter_LIKES_department.departmentId" id="deptNameTreeSelectHis_id">
					<input type="text" id="deptNameTreeSelectHis" />
			</label>
			<label class="queryarea-label" >
					<s:text name='inspectTempl.periodType'/>:
						 <select name="filter_EQS_periodType" onchange="initkpiSelect(this)">
		        						<option value=''>--</option>
		        						<option value='月'>月</option>
		        						<option value='季'>季</option>
		        						<option value='半年'>半年</option>
		        						<option value='年'>年</option>
		        			</select>
			</label>
			<label class="queryarea-label" >
					<s:text name='bsc.kpi'/>:
					<input type="text" id="kpiDeptNameHis" />
					<input type="hidden" name="filter_EQL_kpiItem.id" id="kpiDeptNameHis_id" >
			</label>
			<label class="queryarea-label" >
					<s:text name='bsc.type'/>:
						 <select name="filter_EQS_inspectBSC.type">
		        						<option value=''>--</option>
		        						<option value='手工'>手工</option>
		        						<option value='计算'>计算</option>
		        			</select>
			</label>
			<label class="queryarea-label" >
					<s:text name='deptInspect.state'/>:
						 <select name="filter_EQI_state">
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
									<button type="button" onclick="propertyFilterSearch('deptInspectDeptScoreHis_form','inspecDeptScoreDeptHis_gridtable')"><s:text name='button.search'/></button>
								</div>
						</div>
			</form>
			</div>
	</div>
	<div class="pageContent">
	<div class="panelBar" id="inspecDeptScoreDeptHis_buttonBar">
			<ul class="toolBar">
				<!-- <li><a class="editbutton" href="javaScript:" ><span>编辑
					</span>
				</a>
				</li> -->
				<li><a class="unfoldbutton" id="openAllDeptInspectHis" href="#" ><span id="openDeptInspectHisSpan">收缩</span></a>
				<input type='hidden' id="openDeptInspectStatusHis" value='0' />
				</li>
				<c:if test="${scoreType =='all' }">
					<li><a class="submitbutton" href="javascript:inspectDeptScoreHisDept()" ><span>按项目查看</span></a>
				</c:if>
				
			</ul>
		</div>
		<div id="inspecDeptScoreDeptHis_gridtable_div" 
			class="grid-wrapdiv">
		<div id="load_inspecDeptScoreDeptHis_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="inspecDeptScoreDeptHis_gridtable"></table>
		<div id="deptInspectScorePager"></div>
		</div>
	</div>
	<div class="panelBar" id="inspecDeptScoreDeptHis_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="inspecDeptScoreDeptHis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="inspecDeptScoreDeptHis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="inspecDeptScoreDeptHis_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
