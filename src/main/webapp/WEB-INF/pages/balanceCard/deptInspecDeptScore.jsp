
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
	var deptInspectDeptScoreLayout;
	var deptInspectDeptScoreGridIdString="#deptInspectDeptScore_gridtable";
	var lastsel;
	var kpiscore = "${inspectBSCSCore}";
	jQuery(document).ready(function() { 
    	//deptInspectDeptScoreLayout = makeLayout({'baseName':'deptInspectDeptScore','tableIds':'deptInspectDeptScore_gridtable','proportion':2,'key':'inspectContentId'},deptInspectScoreChangeData);
	var inspectContentId = "${requestScope.inspectContentId}";
    	var deptInspectDeptScoreGrid = jQuery(deptInspectDeptScoreGridIdString);
    deptInspectDeptScoreGrid.jqGrid({
    	url : "deptInspectGridList?scoreType=new",
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:50},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:30},
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'department.departmentId',index:'department.departmentId',align:'left',label : '<s:text name="deptInspect.department" />',hidden:true,width:80},
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'inspectBSC.weidusW',index:'inspectBSC.weidusW',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,cellattr: function(rowId, tv, rawObject, cm, rdata) {return 'name=\'inspectBSC_weidus\'';},width:60}, 
{name:'inspectBSC.fenleiW',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,cellattr: function(rowId, tv, rawObject, cm, rdata) {return 'name=\'inspectBSC_fenlei\'';},width:60},
{name:'inspectBSC.xiangmuW',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:100},
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,width:120,formatter:htmlStringFormatter},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
{name:'inspectBSC.type',index:'inspectBSC.type',align:'center',label : '<s:text name="bsc.type" />',hidden:false,width:30},
{name:'inspectBSC.score',index:'inspectBSC.score',align:'left',label : '<s:text name="bsc.score" />',hidden:true,editable:true,width:100},
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
			groupColumnShow : [false],
			groupText : ['<input name="deptConfirmCheckBox" type="checkbox" value="{0}"> <span name="deptInspectDeptScore_groupDiv" style="margin-top:5px;"><input type="hidden" id="deptInspectDeptScore_numInput" value="{1}" /><b>{0}</b> (指标:{1} 总分:<span id="deptInspectDeptScore_tottleScoreSpan"></span>  实际得分:<span id="deptInspectDeptScore_getScoreSpan"></span>)</span> '],
			groupDataSorted :[false]
	   	},
		gridComplete:function(){
			gridContainerResize("deptInspectDeptScore","div");
			jQuery("#gview_deptInspectDeptScore_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			}); 
           fullGridEdit(deptInspectDeptScoreGrid);
           //gridResize(null,"deptInspectScore_layout-south","deptInspectDeptScore_gridtable");
           var dataTest = {"id":"deptInspectDeptScore_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   <c:if test="${ editComputeKpi=='no'}">
   		   $(deptInspectDeptScoreGridIdString).find("td[aria-describedby='deptInspectDeptScore_gridtable_inspectBSC.type']").each(function(){
   			   if($(this).text()=='计算'){
   			  		$(this).parent().find("input[name='score']").attr('disabled','true');
   			  		$(this).parent().find("input[name='dscore']").attr('disabled','true');
   			   }
   		   });
   		  </c:if>
   		addDetailButtonForGrid("deptInspectDeptScore_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
   		deptInspectDeptScore_flush();
   		
       	} 

    });
    
   
    
    jQuery(deptInspectDeptScoreGrid).jqGrid('bindKeys');
    
    jQuery(deptInspectDeptScoreGrid).unbind("keydown").bind("keydown",function(e){
		chargeKeyPress(deptInspectDeptScoreGrid,e);
	});
	//inspectBSCLayout.resizeAll();
	
    /*===================================按钮权限begin============================================*/
	//实例化toolButtonBar
	var deptInspectDeptScore_menuButtonArrJson = "${menuButtonArrJson}";
	var deptInspectDeptScore_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectDeptScore_menuButtonArrJson,false)));
	var deptInspectDeptScore_toolButtonBar = new ToolButtonBar({el:$('#deptInspectDeptScore_buttonBar'),collection:deptInspectDeptScore_toolButtonCollection,attributes:{
		tableId : 'deptInspectDeptScore_gridtable',
		baseName : 'deptInspectDeptScore',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="deptInspectDeptScoreNew.title"/>',
		editTitle : null
		}}).render(); 
	 //保存
	deptInspectDeptScore_toolButtonBar.addCallBody('0601050111',function() {
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
		        deptInspectDeptScore_flush();
		    }
		});
  		
	},{});
	//确认
	deptInspectDeptScore_toolButtonBar.addCallBody('0601050112',function() {
		var checkItem = "";
  		jQuery("input[name=deptConfirmCheckBox]:checked").each(function(){
  			checkItem += $(this).val()+"__";
  		});
  		var url='submitDeptScoreInspect?navTabId=deptInspectDeptScore_gridtable&scoreType=new&deptConfirmCheckBox='+checkItem;
  		url=encodeURI(url);
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
	},{});
	//收缩
	var deptInspectDeptScore_unfoldButton = {id:'0601050113',buttonLabel:'收缩/展开',className:"unfoldbutton",show:true,enable:true,
			callBody : function() {
				if(jQuery("#openDeptInspectStatus").val()=='0'){
					jQuery("#deptInspectDeptScore_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:true}}).trigger("reloadGrid");
					jQuery("#openDeptInspectStatus").val(1);
					//$('#openDeptInspectSpan').html('展开');
				}else{
					jQuery("#deptInspectDeptScore_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:false}}).trigger("reloadGrid");
					jQuery("#openDeptInspectStatus").val(0);
					//$('#openDeptInspectSpan').html('收缩');
				}
			}};
	deptInspectDeptScore_toolButtonBar.addButton(deptInspectDeptScore_unfoldButton);
	var deptInspectDeptScore_submitButton = {id:'0601050114',buttonLabel:'按项目打分',className:"submitbutton",show:true,enable:true,
		callBody : function() {
			var url = "getScoreInspectBSC?redirectType=item&menuId=${menuId}";
			//window.location = url;
			//jQuery.post(url, {}, DWZ.ajaxDone, "json");
			navTab.reload(url, {
				title : "New Tab",
				fresh : false,
				data : {}
			});
		}};
	var scoreType = "${scoreType}";
	if(scoreType == "all") {
		deptInspectDeptScore_toolButtonBar.addButton(deptInspectDeptScore_submitButton);
	}
	/*===================================按钮权限end============================================*/
	var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
		sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
	var herpType = "${fns:getHerpType()}";
	if(herpType == "M") {
		sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
	}
		sql += " order by orderCol";
  	jQuery("#deptNameTreeSelect").treeselect({
			dataType:"sql",
			optType:"single",
			sql:sql,
			minWidth:"200px",
			exceptnullparent:false,
			lazy:false
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
  	function computeDscore(obj){
  		//var grid.jqGrid('getRowData',id);
  		if(!numberValidate(obj)){
  			return;
  		}
  		var objValue = jQuery(obj).val();
  		jQuery(obj).val(toDecimal(objValue,jjScoreDecimalPlaces));
  		
  		var fullScore = jQuery(obj).parent().parent().find("input[name='inspectBSC.score']").eq(0).val();
  		fullScore = parseFloat(fullScore).toFixed(jjScoreDecimalPlaces);
  		var dscore = objValue/fullScore;
  		dscore = toDecimal(dscore*100);
  		jQuery(obj).parent().parent().find("input[name=dscore]").eq(0).val(dscore);
  		//var rowId = jQuery(obj).attr("id").split("_")[0];
  		//var row = jQuery("#deptInspectDeptScore_gridtable").jqGrid('getRowData',rowId);
  		//var score = row["score"];
  		//alert(score);
  	}
  	function computeScore(obj){
  		if(!numberValidate(obj)){
  			return;
  		}
  		var objValue = jQuery(obj).val();
  		jQuery(obj).val(toDecimal(objValue));
  		var fullScore = jQuery(obj).parent().parent().find("input[name='inspectBSC.score']").eq(0).val();
  		fullScore = parseFloat(fullScore).toFixed(jjScoreDecimalPlaces);
  		var score = objValue*fullScore;
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
  	 function deptInspectDeptScore_flush(){
     	$('span[name=deptInspectDeptScore_groupDiv]').each(function(){
       		var selectRows = $(this).find('#deptInspectDeptScore_numInput').val();
 	    	var startRow = $(this).parent().parent().next();
 	    	var tottleScore = 0.00;
 	    	var getScore = 0.00;
 	    	while(selectRows >0){
 	    		tottleScore+=parseFloat(startRow.find("input[name='inspectBSC.score']").eq(0).val());
 	    		getScore+=parseFloat(startRow.find("input[name=score]").eq(0).val());
 		    	startRow = startRow.next();
 		    	selectRows--;
 	    	}
 			
 			$(this).find('#deptInspectDeptScore_tottleScoreSpan').html(tottleScore.toFixed(jjScoreDecimalPlaces));
 			$(this).find('#deptInspectDeptScore_getScoreSpan').html(getScore.toFixed(jjScoreDecimalPlaces));
       	 });
     	
     }
  	function saveDeptScoreInspect(){
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
		        deptInspectDeptScore_flush();
		    }
		});
  		
  	}
  	
  	jQuery("#kpiDeptNameScore").treeselect({
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
		jQuery("#kpiDeptNameScore").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
  	//addTreeSelect("tree","sync","deptNameTreeSelect","deptNameTreeSelect_id","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
  	
  	function submitDeptScoreInspect(){
  		var checkItem = "";
  		jQuery("input[name=deptConfirmCheckBox]:checked").each(function(){
  			checkItem += $(this).val()+"__";
  		});
  		var url='submitDeptScoreInspect?navTabId=deptInspectDeptScore_gridtable&scoreType=new&deptConfirmCheckBox='+checkItem;
  		url=encodeURI(url);
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
  	
  	
  	/* jQuery("#openAllDeptInspect").unbind("click").bind("click",function(){
		if(jQuery("#openDeptInspectStatus").val()=='0'){
			jQuery("#deptInspectDeptScore_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatus").val(1);
			$('#openDeptInspectSpan').html('展开');
		}else{
			jQuery("#deptInspectDeptScore_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatus").val(0);
			$('#openDeptInspectSpan').html('收缩');
		}
	}); */
  	
  	function deptInspecScoreItem() {
		var url = "getScoreInspectBSC?redirectType=item";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
  
</script>
<div class="page" id="deptInspectDeptScore_page">
	<div class="pageHeader" id="deptInspectDeptScore_pageHeader">
			<div class="searchBar">
			<form id="deptInspectDeptScore_form" class="queryarea-form">
			
			<label class="queryarea-label" >
					<s:text name='deptInspect.department'/>:
					<input type="hidden" name="filter_LIKES_department.departmentId" id="deptNameTreeSelect_id">
					<input type="text" id="deptNameTreeSelect" />
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
					<input type="text" id="kpiDeptNameScore" />
					<input type="hidden" name="filter_EQL_kpiItem.id" id="kpiDeptNameScore_id" >
			</label>
			<label class="queryarea-label" >
					<s:text name='bsc.type'/>:
						 <select name="filter_EQS_inspectBSC.type">
		        						<option value=''>--</option>
		        						<option value='手工'>手工</option>
		        						<option value='计算'>计算</option>
		        			</select>
			</label>
			<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('deptInspectDeptScore_form','deptInspectDeptScore_gridtable')"><s:text name='button.search'/></button>
								</div>
						</div>
			</form>
			</div>
	</div>
	<div class="pageContent">
	<div class="panelBar" id="deptInspectDeptScore_buttonBar">
			<%--  <ul class="toolBar">
				<!-- <li><a class="editbutton" href="javaScript:" ><span>编辑
					</span>
				</a>
				</li> -->
				<li><a class="savebutton" href="javaScript:saveDeptScoreInspect()" ><span>保存
					</span>
				</a>
				</li>
				<li><a class="submitbutton" href="javaScript:submitDeptScoreInspect()" ><span>确认
					</span>
				</a>
				</li>
				<li><a class="unfoldbutton" id="openAllDeptInspect" href="#" ><span id="openDeptInspectSpan">收缩</span></a>
				</li>
				<c:if test="${scoreType =='all' }">
					<li><a class="submitbutton" href="javascript:deptInspecScoreItem()" ><span>按项目打分</span></a></li>
				</c:if>
				
			</ul>  --%>
		</div>
				<input type='hidden' id="openDeptInspectStatus" value='0' />
		<div id="deptInspectDeptScore_gridtable_div" 
			class="grid-wrapdiv">
		<div id="load_deptInspectDeptScore_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="deptInspectDeptScore_gridtable"></table>
		<div id="deptInspectScorePager"></div>
		</div>
	</div>
	<div class="panelBar" id="deptInspectDeptScore_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectDeptScore_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectDeptScore_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectDeptScore_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
	</div>
</div>
