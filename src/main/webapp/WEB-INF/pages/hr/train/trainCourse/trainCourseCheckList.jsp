
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var trainCourseCheckGridIdString="#trainCourseCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var trainCourseCheckGrid = jQuery(trainCourseCheckGridIdString);
	    trainCourseCheckGrid.jqGrid({
	    	url : "trainCourseGridList?"+"filter_EQS_trainNeed.id="+"${needId}"+"&filter_NIS_id="+"${id}",
	    	editurl:"trainCourseGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainCourse.id" />',hidden:true,key:true},				
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainCourse.code" />',hidden:false,highsearch:true},	
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainCourse.name" />',hidden:false,highsearch:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'startDate',index:'startDate',width : 70,align:'center',label : '<s:text name="trainCourse.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'endDate',index:'endDate',width : 70,align:'center',label : '<s:text name="trainCourse.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'expense',index:'expense',width : 60,align:'right',label : '<s:text name="trainCourse.expense" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'hour',index:'hour',width : 60,align:'right',label : '<s:text name="trainCourse.hour" />',hidden:false,formatter:'number',highsearch:true},				
				{name:'trainNeed.name',index:'trainNeed.name',width : 100,align:'left',label : '<s:text name="trainCourse.trainNeed" />',hidden:false,highsearch:true},
				{name:'trainTeacher.name',index:'trainTeacher.name',width : 80,align:'left',label : '<s:text name="trainCourse.trainTeacher" />',hidden:false,highsearch:true},
				{name:'trainInformationNames',index:'trainInformationNames',width : 200,align:'left',label : '<s:text name="trainCourse.trainInformationNames" />',hidden:false,highsearch:true},
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainCourse.remark" />',hidden:false,highsearch:true}				
			
	        ],
	        jsonReader : {
				root : "trainCourses", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'id',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainCourseList.title" />',
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
				 gridContainerResize('trainCourseCheck','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
            	if(rowNum>0){
                	var rowIds = jQuery(this).getDataIDs();
                	var ret = jQuery(this).jqGrid('getRowData');
                	var id='';
                	for(var i=0;i<rowNum;i++){
              	  		id=rowIds[i];
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainCourseRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              		}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"trainCourseCheck_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	jQuery(trainCourseCheckGrid).jqGrid('bindKeys');
     	jQuery("#search_trainCourseCheck_trainTeacher").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_teacher where disabled = 0 ORDER BY code",
		   exceptnullparent:false,
		   minWidth:"150px",
		   lazy:false
		});
     	jQuery("#trainCourseCheck_gridtable_import_custom").unbind( 'click' ).bind("click",function(){
     		var sid = jQuery("#trainCourseCheck_gridtable").jqGrid('getGridParam','selarrrow');
            if(sid==null || sid.length ==0){
            	alertMsg.error("请选择记录。");
    			return;
    			}else {
    				alertMsg.confirm("确认引入课程？", {
    					okCall : function() {
    						for(var i = 0;i < sid.length; i++){
    							var rowId = sid[i];
    							var row = jQuery("#trainCourseCheck_gridtable").jqGrid('getRowData',rowId);
    							 var mydate = new Date();
    							 var trainDate=mydate.format("yyyy-MM-dd");
    							 jQuery("#"+"${navTabId}").addRowData("recordDetail"+mydate.getTime()+i, {
    		 						 "id":"recordDetail"+mydate.getTime()+i,
    		 						 "trainCourse.id":row['id'],
    								 "trainCourse.name":row['name'],
    								 "trainDate": trainDate,
    								 "trainHour":row['hour'],
    								 "personIds":"${trainNeed.personIds}",
    								 "personNames":"${trainNeed.personNames}"
    								 }, "last");  
    						}
    						jQuery.pdialog.close("addtrainCourse");
    					}
    				});
    			}
    	});
  	});
	function viewTrainCourseRecord(id){
		var url = "editTrainCourse?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainCourse','查看培训课程信息', {mask:true,width : 700,height : 380});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainCourseCheck_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainCourseCheck_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.trainTeacher'/>:
					 	<input type="hidden" id="search_trainCourseCheck_trainTeacher_id" name="filter_EQS_trainTeacher.id">
				 		<input type="text" id="search_trainCourseCheck_trainTeacher" style="width:100px">
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--       				<input type="text"  name="filter_EQS_yearMonth" style="width:50px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCourse.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainCourseCheck_search_form','trainCourseCheck_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainCourseCheck_search_form','trainCourseCheck_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainCourseCheck_buttonBar">
			<ul class="toolBar">
				<li><a id="trainCourseCheck_gridtable_import_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="引入" />
					</span>
				</a>
				</li>
				<%-- <li>
    				 <a class="settlebutton"  href="javaScript:setColShow('trainCourseCheck_gridtable','com.huge.ihos.hr.trainCourse.model.TrainCourse')"><span><s:text name="button.setColShow" /></span></a>
    			</li> --%>
			</ul>
		</div>
		<div id="trainCourseCheck_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="trainCourseCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainCourseCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainCourseCheck_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainCourseCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainCourseCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainCourseCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainCourseCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>