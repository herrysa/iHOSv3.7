
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainRequirementAnalysisGridReload(){
		var urlString = "trainRequirementAnalysisGridList";
		var checkDate_from = jQuery("#trainRequirementAnalysis_search_check_date_from").val();
		var checkDate_to = jQuery("#trainRequirementAnalysis_search_check_date_to").val();
		if(!checkDate_from || !checkDate_to){
			alertMsg.error("请选择要统计的日期范围。");
			return;
		}
		urlString=urlString+"?checkDateFrom="+checkDate_from+"&checkDateTo="+checkDate_to;
		urlString=encodeURI(urlString);
		jQuery("#trainRequirementAnalysis_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var trainRequirementAnalysisGridIdString="#trainRequirementAnalysis_gridtable";
	
	jQuery(document).ready(function() { 
		var trainRequirementAnalysisGrid = jQuery(trainRequirementAnalysisGridIdString);
	    trainRequirementAnalysisGrid.jqGrid({
	    	url : "trainRequirementAnalysisGridList?checkDateFrom=${currentSystemVariable.businessDate}&checkDateTo=${currentSystemVariable.businessDate}",
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'trainCategoryId',index:'trainCategoryId',align:'center',label : '<s:text name="trainRequirementAnalysis.trainCategoryId" />',hidden:true,key:true},
				{name:'trainCategoryName',index:'trainCategoryName',width : 100,align:'left',label : '<s:text name="trainRequirementAnalysis.trainCategoryName" />',hidden:false,highsearch:true},
				{name:'deptName',index:'deptName',width : 150,align:'left',label : '<s:text name="trainRequirementAnalysis.deptName" />',hidden:false,highsearch:true},
				{name:'peopleNumber',index:'peopleNumber',width : 80,align:'right',label : '<s:text name="trainRequirementAnalysis.peopleNumber" />',hidden:false,highsearch:true},
				{name:'personIds',index:'personIds',width : 250,align:'left',label : '<s:text name="trainRequirementAnalysis.personIds" />',hidden:true},
				{name:'personNames',index:'personNames',width : 250,align:'left',label : '<s:text name="trainRequirementAnalysis.personNames" />',hidden:false,highsearch:true}
	        ],
	        jsonReader : {
				root : "trainRequirementAnalysiss", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'trainCategoryId',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainRequirementAnalysisList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
       		},
		 	gridComplete:function(){
				 /*2015.08.27 form search change*/
				 gridContainerResize('trainRequirementAnalysis','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
				if(rowNum<=0){
					var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
				}
           		var dataTest = {"id":"trainRequirementAnalysis_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	jQuery(trainRequirementAnalysisGrid).jqGrid('bindKeys');
    
	    jQuery("#trainRequirementAnalysis_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
	   	  	var sid = jQuery("#trainRequirementAnalysis_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid==null|| sid.length != 1){     	
				alertMsg.error("请选择一条记录。");
				return;
			}else {
				var rowId = sid[0];
				var row = jQuery("#trainRequirementAnalysis_gridtable").jqGrid('getRowData',rowId);
				var peopleNumber=row['peopleNumber'];
				var personIds=row['personIds'];
				var personNames=row["personNames"];
				$.pdialog.closeCurrent();
				var url = "editTrainPlan?popup=true&navTabId=trainPlan_gridtable"+"&peopleNumber="+peopleNumber+"&trainCategoryId="+sid +"&personIds=" +personIds+"&personNames="+personNames;
				var winTitle='生成培训计划';
				$.pdialog.open(url,'addTrainPlanFromAnalysis',winTitle, {mask:true,width : 650,height : 480});
			}
		}); 
  	});
</script>

<div class="page">
	<div class="pageHeader" id="trainRequirementAnalysis_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainRequirementAnalysis_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRequirementAnalysis.checkDate'/>:
						<input type="text"	id="trainRequirementAnalysis_search_check_date_from" value="${currentSystemVariable.businessDate}" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainRequirementAnalysis_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="trainRequirementAnalysis_search_check_date_to" value="${currentSystemVariable.businessDate}" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainRequirementAnalysis_search_check_date_from\')}'})">
					</label>
					<div class="buttonActive" style="float:right"> 
							<div class="buttonContent">
 								<button type="button" onclick="trainRequirementAnalysisGridReload()"><s:text name='button.search'/></button>
 							</div>
 					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="trainRequirementAnalysisGridReload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainRequirementAnalysis_buttonBar">
			<ul class="toolBar">
				<li><a id="trainRequirementAnalysis_gridtable_add_custom" class="addbutton" href="javaScript:" ><span>生成培训计划
					</span>
				</a>
				</li>
				<%-- <li>
    				 <a class="settlebutton"  href="javaScript:setColShow('trainRequirementAnalysis_gridtable','com.huge.ihos.hr.trainRequirement.model.TrainRequirementAnalysis')"><span><s:text name="button.setColShow" /></span></a>
    			</li> --%>
			</ul>
		</div>
		<div id="trainRequirementAnalysis_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="trainRequirementAnalysis_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainRequirementAnalysis_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainRequirementAnalysis_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainRequirementAnalysis_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainRequirementAnalysis_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRequirementAnalysis_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainRequirementAnalysis_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>