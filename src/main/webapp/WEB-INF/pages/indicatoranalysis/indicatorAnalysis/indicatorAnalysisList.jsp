
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var indicatorAnalysisGridIdString="#indicatorAnalysis_gridtable";
	jQuery(document).ready(function() { 
		var indicatorAnalysisGrid = jQuery(indicatorAnalysisGridIdString);
    	indicatorAnalysisGrid.jqGrid({
    		url : "indicatorAnalysisGridList?filter_EQS_checkPeriod=${checkPeriod}&filter_EQS_indicator.indicatorType.code=${indicatorTypeCode}&filter_EQS_indicator.id=${indicatorId}&filter_LEI_indicator.level=${indicatorLevel}",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'indicator.id',index:'indicator.id',align:'center',label : '<s:text name="indicatorAnalysis.id" />',hidden:true,key:true},
				{name:'indicator.name',index:'indicator.name',align:'left',width:'200',label : '<s:text name="indicatorAnalysis.indicator" />',hidden:false},
				{name:'checkPeriod',index:'checkPeriod',align:'center',width:'80',label : '<s:text name="indicatorAnalysis.checkPeriod" />',hidden:false},
				//{name:'value',index:'value',align:'right',width:'150',label : '<s:text name="indicatorAnalysis.value" />',hidden:false} ,       
				{name:'value',index:'value',align:'right',width:'150',label : '<s:text name="indicatorAnalysis.value" />',hidden:false,formatter : formatIndicatorValue} ,       
				{name:'indicator.unit',index:'indicator.unit',align:'left',width:'60',label : '<s:text name="indicator.unit" />',hidden:false} ,       
				{name:'indicator.precision',index:'indicator.precision',align:'right',width:'20',label : '<s:text name="indicator.precision" />',hidden:true} ,       
				{name:'indicator.needSeparator',index:'indicator.needSeparator',align:'right',width:'20',label : '<s:text name="indicator.needSeparator" />',hidden:true} ,       
				{name:'indicator.toPercent',index:'indicator.toPercent',align:'right',width:'20',label : '<s:text name="indicator.toPercent" />',hidden:true} ,       
				{name:'remark',index:'remark',align:'center',width:'250',label : '<s:text name="indicatorAnalysis.remark" />',hidden:false}
			],
        	jsonReader : {
				root : "indicatorAnalysiss", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'indicator.code',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="indicatorAnalysisList.title" />',
        	height:300,
        	//gridview:true,
        	//rownumbers:true,
        	loadui: "disable",
        	//multiselect: true,
			//multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			treeGrid : true,
			treeGridModel : "adjacency",
			ExpandColumn : "indicator.name",
			ExpandColClick : true,
			treeIcons : {
				leaf : 'ui-icon-document-b'
			},
		 	treeReader : {  
		    	level_field: "indicator.level",  
		    	parent_id_field: "indicator.parent.id",   
		    	leaf_field: "isLeaf",
		    	expanded_field:"indicator.expanded"
		    },  
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
	           	var dataTest = {"id":"indicatorAnalysis_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	   	makepager("indicatorAnalysis_gridtable");
	      	    jQuery("#indicatorAnalysis_gridtable").find(".treeclick").trigger('click');
       		} 
	    });
	    jQuery(indicatorAnalysisGrid).jqGrid('bindKeys');
	    
	   jQuery("#indicatorAnalysis_search_indicator").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  id id,name name,parentId parent FROM t_indicator where indicatorTypeId = '${indicatorTypeCode}'",
			selectParent:true,
			exceptnullparent : false,
			lazy : false
		});
	   initGridSearchFormValue();
	});
	function formatIndicatorUnit(cellvalue, options, rowObject){
		var toPercent = rowObject['indicator'].toPercent;
		if(toPercent){
			cellvalue = "%";
		}
		return cellvalue
	}
	function formatIndicatorValue(cellvalue, options, rowObject){
		var indicator = rowObject['indicator'];
		var toPercent = indicator.toPercent;
		var precision = indicator.precision;
		var needSeparator = indicator.needSeparator;
		if(!precision){
			precision = 2;
		}
		if(toPercent){
			cellvalue = (cellvalue*100);
		}
		if(needSeparator){
			cellvalue = fmoney(cellvalue, precision);
		}else{
			cellvalue = cellvalue.toFixed(precision);
		}
		return cellvalue;
	}
	function fmoney(s, n){   
	   n = n > 0 && n <= 20 ? n : 2;   
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = s.split(".")[0].split("").reverse(),   
	   r = s.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ ){   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
	} 
	
	function initGridSearchFormValue(){
		var indicatorId = "${indicatorId}";
		var checkPeriod = "${checkPeriod}";
		var indicatorLevel = "${indicatorLevel}";
		if(checkPeriod){
			jQuery("#indicatorAnalysis_search_checkPeriod").val(checkPeriod);
		}
		if(indicatorId){
			jQuery("#indicatorAnalysis_search_indicator_radio").trigger("click");
			jQuery("#indicatorAnalysis_search_indicator").val("${indicatorName}");
			jQuery("#indicatorAnalysis_search_indicator_id").val(indicatorId);
		}
		if(indicatorLevel){
			jQuery("#indicatorAnalysis_search_indicator_level_radio").trigger("click");
			jQuery("#indicatorAnalysis_search_indicator_level").val(indicatorLevel);
		}
	}
	function initIndicatorValue(){
		var checkPeriod = jQuery("#indicatorAnalysis_search_checkPeriod").val();
		if(!checkPeriod){
			alertMsg.error("请选择期间!");
			return;
		}
		$.ajax({
		    url: 'initIndicatorValue',
		    type: 'post',
		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data.statusCode==200){
		        	alertMsg.correct(data.message);
		        	jQuery("#indicatorAnalysis_gridtable").trigger("reloadGrid");
		        }else{
		        	alertMsg.error(data.message);
		        }
		    }
		});
	}
	
	function calculateIndicatorValue(){
		var checkPeriod = jQuery("#indicatorAnalysis_search_checkPeriod").val();
		if(!checkPeriod){
			alertMsg.error("请选择期间!");
			return;
		}
		$.ajax({
		    url: 'executeIndicatorSp',
		    type: 'post',
		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod,spName:"sp_GL_keyCal"},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data.statusCode==200){
		        	$.ajax({
		    		    url: 'calculateIndicatorValue',
		    		    type: 'post',
		    		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod},
		    		    dataType: 'json',
		    		    aysnc:false,
		    		    error: function(data){
		    		    },
		    		    success: function(data){
		    		        if(data.statusCode==200){
		    		        	alertMsg.correct(data.message);
		    		        	jQuery("#indicatorAnalysis_gridtable").trigger("reloadGrid");
		    		        }else{
		    		        	alertMsg.error(data.message);
		    		        }
		    		    }
		    		});
		        }else{
		        	alertMsg.error(data.message);
		        }
		    }
		});
	}
	
	function singleSelectCon(obj){
		var val = obj.value;
		if("id"===val){
			jQuery("#indicatorAnalysis_search_indicator").removeAttr("disabled").removeAttr("readonly").removeClass("readonly");
			jQuery("#indicatorAnalysis_search_indicator_id").removeAttr("disabled");
			jQuery("#indicatorAnalysis_search_indicator_level").attr("disabled","disabled").attr("readonly","readonly").addClass("readonly").val("");
		}else{
			jQuery("#indicatorAnalysis_search_indicator_id").attr("disabled","disabled");
			jQuery("#indicatorAnalysis_search_indicator").attr("disabled","disabled").attr("readonly","readonly").addClass("readonly");
			jQuery("#indicatorAnalysis_search_indicator_level").removeAttr("disabled").removeAttr("readonly").removeClass("readonly");
		}
	}
	
	function checkInputLevel(obj){
		if(obj.value){
			var reg = /^\d+$/ ;
			if(!reg.test(obj.value)){
				alertMsg.error("层级必须为正整数!");
				obj.value="";
				return;
			}
		}
	}
	
	function switchGraphAndGrid(redirectType){
		var indicatorTypeCode = "${indicatorTypeCode}";
		var checkPeriod = jQuery("#indicatorAnalysis_search_checkPeriod").val();
		var indicatorId = jQuery("#indicatorAnalysis_search_indicator_id").val();
		var level = jQuery("#indicatorAnalysis_search_indicator_level").val();
		var url = "switchGraphAndGrid?redirectType="+redirectType;
		var condition = jQuery("input[name=indicatorAnalysisCondition]:checked").val();
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {checkPeriod:checkPeriod,indicatorId:indicatorId,indicatorLevel:level,condition:condition,indicatorTypeCode:indicatorTypeCode}
		});
	}
	
	function reloadIndicatorAnalysisGrid(){
		var checkPeriod = jQuery("#indicatorAnalysis_search_checkPeriod").val();
		var indicatorId = jQuery("#indicatorAnalysis_search_indicator_id").val();
		var level = jQuery("#indicatorAnalysis_search_indicator_level").val();
		var loadUrl = "indicatorAnalysisGridList?indicatorTypeCode=${indicatorTypeCode}"
				+"&filter_EQS_checkPeriod="+checkPeriod;
		var indicatorIdRadio = jQuery("#indicatorAnalysis_search_indicator_radio").attr("checked");
		if(indicatorIdRadio){
			loadUrl += "&filter_EQS_indicator.id="+indicatorId;
		}else{
			loadUrl += "&filter_LEI_indicator.level="+level;
		}
		loadUrl = encodeURI(loadUrl);
		jQuery("#indicatorAnalysis_gridtable").jqGrid('setGridParam', {url : loadUrl,page : 1}).trigger("reloadGrid");
	}
	
</script>

<div class="page">
	<div id="indicatorAnalysis_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<s:hidden id="indicatorTypeCode" name="indicatorTypeCode"></s:hidden>
				<form id="indicatorAnalysis_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='indicatorAnalysis.checkPeriod'/>:
						<input id="indicatorAnalysis_search_checkPeriod" type="text" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" size="10" value="${checkPeriod}"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<input type="radio" name="indicatorAnalysisCondition" onclick="singleSelectCon(this)" value="id" checked="checked" id="indicatorAnalysis_search_indicator_radio"/>
						<s:text name='indicatorAnalysis.indicator'/>:
						<input type="text" id="indicatorAnalysis_search_indicator" size="40"/>
						<input type="hidden" id="indicatorAnalysis_search_indicator_id" />
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<input type="radio" name="indicatorAnalysisCondition" onclick="singleSelectCon(this)" value="level" id="indicatorAnalysis_search_indicator_level_radio"/>
						<s:text name='层级'/>:
						<input type="text" disabled="disabled" size="10" id="indicatorAnalysis_search_indicator_level" onblur="checkInputLevel(this)" class="readonly"/>
					</label>&nbsp;&nbsp;
				</form>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="reloadIndicatorAnalysisGrid()"><s:text name='button.search'/></button>
					</div>
				</div>
			</div>
			<%-- <div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="reloadIndicatorAnalysisGrid()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div> --%>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="zbcomputebutton" href="javaScript:initIndicatorValue()" ><span><s:text name="初始化" /></span></a>
				</li>
				<li>
					<a class="zbcomputebutton" href="javaScript:calculateIndicatorValue()" ><span><s:text name="计算" /></span></a>
				</li>
				<li>
					<a class="getdatabutton"  href="javaScript:switchGraphAndGrid('graph')"><span><s:text name="图形显示" /></span></a>
				</li>
			</ul>
		</div>
		<div id="indicatorAnalysis_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="indicatorAnalysis_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="indicatorAnalysis_gridtable_addTile">
				<s:text name="indicatorAnalysisNew.title"/>
			</label> 
			<label style="display: none"
				id="indicatorAnalysis_gridtable_editTile">
				<s:text name="indicatorAnalysisEdit.title"/>
			</label>
			<div id="load_indicatorAnalysis_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="indicatorAnalysis_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="indicatorAnalysis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="indicatorAnalysis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="indicatorAnalysis_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>