
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
	jjScoreDecimalPlaces = 2;
}
jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	
	
	var deptInspectDeptScoreCheckLayout;
	var deptInspectDeptScoreCheckGridIdString="#deptInspectDeptScoreCheck_gridtable";
	jQuery(document).ready(function() { 
    var deptInspectDeptScoreCheckGrid = jQuery(deptInspectDeptScoreCheckGridIdString);
    deptInspectDeptScoreCheckGrid.jqGrid({
    	url : "deptInspectGridList?scoreType=check",
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true,edittype:"text",editable:true},	
{name:'department.departmentId',index:'department.departmentId',align:'left',label : '<s:text name="deptInspect.department" />',hidden:true,width:80},
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false,width:40},
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false,width:30},	
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false,width:80},
{name:'department.internalCode',index:'department.internalCode',align:'left',label : '<s:text name="department.internalCode" />',hidden:true,width:80},
{name:'inspectBSC.weidusW',index:'inspectBSC.weidusW',align:'left',label : '<s:text name="bsc.weidu" />',hidden:false,cellattr: function(rowId, tv, rawObject, cm, rdata) {return 'name=\'inspectBSC_weidus\'';},width:80}, 
{name:'inspectBSC.fenleiW',index:'inspectBSC.fenlei',align:'left',label : '<s:text name="bsc.fenlei" />',hidden:false,cellattr: function(rowId, tv, rawObject, cm, rdata) {return 'name=\'inspectBSC_fenlei\'';},width:60},
{name:'inspectBSC.xiangmuW',index:'inspectBSC.xiangmu',align:'left',label : '<s:text name="bsc.xiangmu" />',hidden:false,width:100},
{name:'inspectBSC.rules',index:'inspectBSC.rules',align:'left',label : '<s:text name="bsc.rules" />',hidden:false,width:120,formatter:htmlStringFormatter},	
{name:'inspectBSC.rulesHtml',index:'inspectBSC.rulesHtml',align:'left',label : '<s:text name="bsc.rules" />',hidden:true,formatter:htmlFormatter},	
{name:'inspectBSC.score',index:'inspectBSC.score',align:'right',label : '<s:text name="bsc.score" />',hidden:false,width:50,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'weight',index:'weight',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:true,width:50},
{name:'score',index:'score',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,width:50,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},
{name:'money1',index:'money1',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,width:60},
{name:'money2',index:'money2',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,width:60},
{name:'dscore',index:'dscore',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,width:60},
{name:'operatorInfo',index:'operatorInfo',align:'center',label : '<s:text name="deptInspect.operator" />',hidden:false,width:90},
{name:'remark',index:'remark',align:'left',label : '<s:text name="deptInspect.remark" />',hidden:false,width:100,formatter:stringFormatter},
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
			groupText : ['<input name="deptConfirmCheckBox" type="checkbox" value="{0}"> <span name="deptInspectDeptScoreCheck_groupDiv" style="margin-top:5px;"><input type="hidden" id="deptInspectDeptScoreCheck_hiddenNumInput" value="{1}" /><b>{0}</b> (指标:{1} 总分:<span id="deptInspectDeptScoreCheck_tottleScoreSpan"></span>  实际得分:<span id="deptInspectDeptScoreCheck_getScoreSpan"></span>)</span>  '],
			groupDataSorted :[false]
	   	},
		gridComplete:function(){
			gridContainerResize("deptInspectDeptScoreCheck","div");
			jQuery("#gview_deptInspectDeptScoreCheck_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
				jQuery(this).find("th").each(function(){
					
					jQuery(this).find("div").eq(0).css("line-height","18px");
				});
			}); 
           fullGridEdit(deptInspectDeptScoreCheckGrid);
           //gridResize(null,"deptInspectScore_layout-south","deptInspectDeptScore_gridtable");
           var dataTest = {"id":"deptInspectDeptScoreCheck_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	 addDetailButtonForGrid("deptInspectDeptScoreCheck_gridtable","inspectBSC.rules","inspectBSC.rulesHtml","aria-describedby");
      	$('span[name=deptInspectDeptScoreCheck_groupDiv]').each(function(){
      		var selectRows = $(this).find('#deptInspectDeptScoreCheck_hiddenNumInput').val();
	    	var index = $(this).parent().parent().next();
	    	var tottleScore = 0.00;
	    	var getScore = 0.00;
			while(selectRows>0){
				tottleScore += parseFloat($('#deptInspectDeptScoreCheck_gridtable').jqGrid('getRowData',index.attr('id'))['inspectBSC.score']);
				getScore+= parseFloat($('#deptInspectDeptScoreCheck_gridtable').jqGrid('getRowData',index.attr('id'))['score']);
	    		index = index.next();
	    		selectRows--;
	    	}
			$(this).find('#deptInspectDeptScoreCheck_tottleScoreSpan').html(tottleScore.toFixed(jjScoreDecimalPlaces));
			$(this).find('#deptInspectDeptScoreCheck_getScoreSpan').html(getScore.toFixed(jjScoreDecimalPlaces));
      	 });
       	} 

    });
    
    jQuery(deptInspectDeptScoreCheckGrid).jqGrid('bindKeys');
    
    jQuery(deptInspectDeptScoreCheckGrid).unbind("keydown").bind("keydown",function(e){
		chargeKeyPress(deptInspectDeptScoreCheckGrid,e);
	});
	//inspectBSCLayout.resizeAll();
	
    /*===================================按钮权限begin============================================*/
	//实例化toolButtonBar
	var deptInspectDeptScoreCheck_menuButtonArrJson = "${menuButtonArrJson}";
	var deptInspectDeptScoreCheck_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectDeptScoreCheck_menuButtonArrJson,false)));
	var deptInspectDeptScoreCheck_toolButtonBar = new ToolButtonBar({el:$('#deptInspectDeptScoreCheck_buttonBar'),collection:deptInspectDeptScoreCheck_toolButtonCollection,attributes:{
		tableId : 'deptInspectDeptScoreCheck_gridtable',
		baseName : 'deptInspectDeptScoreCheck',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="deptInspectDeptScoreCheckNew.title"/>',
		editTitle : null
		}}).render(); 

	//保存
	deptInspectDeptScoreCheck_toolButtonBar.addCallBody('0601050211',function() {
		saveCheckRemrak();
	},{});
	//审核
	deptInspectDeptScoreCheck_toolButtonBar.addCallBody('0601050212',function() {
		checkDeptInspect();
	},{});
	//否决
	deptInspectDeptScoreCheck_toolButtonBar.addCallBody('0601050213',function() {
		denyDeptInspect();
	},{});
	//展开收缩
	var deptInspectDeptScoreCheck_unfoldButton = {id:'0601050214',buttonLabel:'展开/收缩',className:"unfoldbutton",show:true,enable:true,
			callBody : function() {
				if(jQuery("#openDeptInspectStatusCheck").val()=='0'){
					jQuery("#deptInspectDeptScoreCheck_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:true}}).trigger("reloadGrid");
					jQuery("#openDeptInspectStatusCheck").val(1);
					//$('#openDeptInspectSpanCheck').html('展开');
					//jQuery("#openAllDeptInspectCheck").removeClass('unfoldbutton').addClass('foldbutton');
				}else{
					jQuery("#deptInspectDeptScoreCheck_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:false}}).trigger("reloadGrid");
					jQuery("#openDeptInspectStatusCheck").val(0);
					//jQuery("#openAllDeptInspectCheck").removeClass('foldbutton').addClass('unfoldbutton');
					//$('#openDeptInspectSpanCheck').html('收缩');
				}
			}};
	deptInspectDeptScoreCheck_toolButtonBar.addButton(deptInspectDeptScoreCheck_unfoldButton);
	//按项目审核
	var deptInspectDeptScoreCheck_submitButton = {id:'0601050215',buttonLabel:'按项目审核',className:"submitbutton",show:true,enable:true,
			callBody : function() {
				deptInspecScoreCheckItem();
			}};
	var scoreType = "${scoreType}";
	if(scoreType == "all") {
		deptInspectDeptScoreCheck_toolButtonBar.addButton(deptInspectDeptScoreCheck_submitButton);
	}
	/*===================================按钮权限end============================================*/
	var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
		sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
			var herpType = "${fns:getHerpType()}";
		if(herpType == "M") {
			sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
		}
		sql += " order by orderCol ";
	jQuery("#deptNameTreeSelectCheck").treeselect({
		dataType:"sql",
		optType:"single",
		sql:sql,
		minWidth:"200px",
		exceptnullparent:false,
		lazy:false
	});
  	});
	
	
	jQuery("#kpiDeptName_check").treeselect({
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
		jQuery("#kpiDeptName_check").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,keyName name,parent_id parent FROM KH_KEYDEPOT where "+filter+" and disabled=0 ",
			exceptnullparent:false,
			lazy:false
		});

		/*addTreeSelect("tree","sync","kpiName_his","kpiId_his","single",{tableName:"KH_KEYDEPOT",treeId:"id",treeName:"keyName",parentId:"parent_id",filter:filter+" and disabled=0"});*/
	}
	function saveCheckRemrak(){
		//alert("审核前保存");
  		var sids = "";
  		var remark2 = "";
  		jQuery("input[name=deptinspectId]",deptInspectDeptScoreCheckGridIdString).each(function(){
  			sids += jQuery(this).val()+",";
  		});
  		jQuery("textarea[name=remark2]",deptInspectDeptScoreCheckGridIdString).each(function(){
  			remark2 += jQuery(this).val()+",";
  		});
  		sids += ",end";
  		remark2 += "end";
  		var  url =  'saveCheckRemrk?deptinspectId='+sids+'&remark2='+remark2;
  		url=encodeURI(url);
  		//alert(url);
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
  
  	function numberValidate(obj){
  		var objValue = jQuery(obj).val();
  		if(!isFloatOrNull(objValue)){
  			jQuery(obj).val("")
  			alertMsg.error("请输入数字！");
  			return false;
  		}else{
  			jQuery(obj).val(toDecimal(objValue));
  			return true;
  		}
  	}
  	
  	

  	function checkOrDeny(scoreType){
  		var checkItem = "";
  		jQuery("input[name=deptConfirmCheckBox]:checked").each(function(){
  			checkItem += $(this).val()+"__";
  		});
  		var url='submitDeptScoreInspect?navTabId=deptInspectDeptScoreCheck_gridtable&scoreType='+scoreType+'&deptConfirmCheckBox='+checkItem;
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
  	
  	function checkDeptInspect(scoreType){
  		checkOrDeny("check");
	}
  	
  	function denyDeptInspect(){
  		checkOrDeny("denytouse");
  	}
	
	jQuery("#openAllDeptInspectCheck").unbind("click").bind("click",function(){
		if(jQuery("#openDeptInspectStatusCheck").val()=='0'){
			jQuery("#deptInspectDeptScoreCheck_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatusCheck").val(1);
			$('#openDeptInspectSpanCheck').html('展开');
			//jQuery("#openAllDeptInspectCheck").removeClass('unfoldbutton').addClass('foldbutton');
		}else{
			jQuery("#deptInspectDeptScoreCheck_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['department.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#openDeptInspectStatusCheck").val(0);
			//jQuery("#openAllDeptInspectCheck").removeClass('foldbutton').addClass('unfoldbutton');
			$('#openDeptInspectSpanCheck').html('收缩');
		}
	});
	
	function deptInspecScoreCheckItem() {
		var url = "getScoreInspectBSCCheck?redirectType=item&menuId=${menuId}";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
	//addTreeSelect("tree","sync","deptNameTreeSelectCheck","deptNameTreeSelectCheck_id","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
</script>
<div class="page" id="deptInspectDeptScoreCheck_page">
	<div class="pageHeader" id="deptInspectDeptScoreCheck_pageHeader">
			<div class="searchBar">
			<form id="deptInspectDeptScoreCheck_form" class="queryarea-form">
			<label style="float:none;white-space:nowrap" >
					<s:text name='deptInspect.department'/>:
					<input type="hidden" name="filter_LIKES_department.departmentId" id="deptNameTreeSelectCheck_id">
					<input type="text" id="deptNameTreeSelectCheck" />
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
							<input type="hidden"	id="kpiDeptName_check_id" name="filter_EQL_kpiItem.id">
						 <input type="text"	id=kpiDeptName_check />
			</label>
				<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('deptInspectDeptScoreCheck_form','deptInspectDeptScoreCheck_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
				</form>
			</div>
	</div>
	<div class="pageContent">
	<div class="panelBar" id="deptInspectDeptScoreCheck_buttonBar">
			<%-- <ul class="toolBar">
				<li><a class="savebutton" href="javaScript:saveCheckRemrak()" ><span><s:text name="button.save"></s:text>
								</span>
								</a>
							</li>
				<li><a class="addbutton" href="javaScript:checkDeptInspect()" ><span>审核
					</span>
				</a>
				</li>
				<li><a  class="delbutton"  href="javaScript:denyDeptInspect()"><span>否决</span>
				</a>
				</li>
				<li><a class="unfoldbutton" id="openAllDeptInspectCheck" href="#" ><span id="openDeptInspectSpanCheck">收缩</span></a>
				</li>
				<c:if test="${scoreType =='all' }">
					<li><a class="submitbutton" href="javascript:deptInspecScoreCheckItem()" ><span>按项目审核</span></a></li>
				</c:if>
				<!-- <li><a class="particularbutton"
								external="true" href="javaScript:deptInspectScoreCheckLayout.optDblclick();"><span>明细</span> </a></li> -->
			</ul> --%>
		</div>
				<input type='hidden' id="openDeptInspectStatusCheck" value='0' />
		<div id="deptInspectDeptScoreCheck_gridtable_div" 
			class="grid-wrapdiv">
		<div id="load_deptInspectDeptScoreCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="deptInspectDeptScoreCheck_gridtable"></table>
		<div id="deptInspectScoreCheckPager"></div>
		</div>
	</div>
	<div class="panelBar" id="deptInspectDeptScoreCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectDeptScoreCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectDeptScoreCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectDeptScoreCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
