
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var trainRecordDetailLayout;
	var trainRecordDetailGridIdString="#trainRecordDetail_gridtable";
	
	jQuery(document).ready(function() { 
		var trainRecordDetailGrid = jQuery(trainRecordDetailGridIdString);
    	trainRecordDetailGrid.jqGrid({
    		url : "trainRecordDetailGridList",
    		editurl:"trainRecordDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainRecordDetail.id" />',hidden:false,key:true},
{name:'courseName',index:'courseName',align:'center',label : '<s:text name="trainRecordDetail.courseName" />',hidden:false},
{name:'personIds',index:'personIds',align:'center',label : '<s:text name="trainRecordDetail.personIds" />',hidden:false},
{name:'personNames',index:'personNames',align:'center',label : '<s:text name="trainRecordDetail.personNames" />',hidden:false},
{name:'trainDate',index:'trainDate',align:'center',label : '<s:text name="trainRecordDetail.trainDate" />',hidden:false},
{name:'trainHour',index:'trainHour',align:'center',label : '<s:text name="trainRecordDetail.trainHour" />',hidden:false,formatter:'number'},
],
        	jsonReader : {
				root : "trainRecordDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'id',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="trainRecordDetailList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
				 /*2015.08.27 form search change*/
				 gridContainerResize('trainRecordDetail','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"trainRecordDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("trainRecordDetail_gridtable");
       		} 

    	});
    jQuery(trainRecordDetailGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="trainRecordDetail_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="trainRecordDetail_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.id'/>:
						<input type="text" name="filter_EQS_id"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.courseName'/>:
						<input type="text" name="filter_EQS_courseName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.personIds'/>:
						<input type="text" name="filter_EQS_personIds"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.personNames'/>:
						<input type="text" name="filter_EQS_personNames"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.trainDate'/>:
						<input type="text" name="filter_EQS_trainDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.trainHour'/>:
						<input type="text" name="filter_EQS_trainHour"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainRecordDetail.trainRecord'/>:
						<input type="text" name="filter_EQS_trainRecord"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(trainRecordDetail_search_form,trainRecordDetail_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch(trainRecordDetail_search_form,trainRecordDetail_gridtable)"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainRecordDetail_buttonBar">
			<ul class="toolBar">
				<li><a id="trainRecordDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainRecordDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainRecordDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="trainRecordDetail_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="trainRecordDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainRecordDetail_gridtable_addTile">
				<s:text name="trainRecordDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="trainRecordDetail_gridtable_editTile">
				<s:text name="trainRecordDetailEdit.title"/>
			</label>
			<div id="load_trainRecordDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainRecordDetail_gridtable"></table>
			<!--<div id="trainRecordDetailPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="trainRecordDetail_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainRecordDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRecordDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="trainRecordDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>