
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var defineReportLayout;
	var defineReportGridIdString="#defineReport_gridtable";
	
	jQuery(document).ready(function() { 
		var defineReportGrid = jQuery(defineReportGridIdString);
    	defineReportGrid.jqGrid({
    		url : "defineReportGridList",
    		editurl:"defineReportGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'code',index:'code',align:'center',label : '<s:text name="defineReport.code" />',hidden:false,key:true},
{name:'name',index:'name',align:'center',label : '<s:text name="defineReport.name" />',hidden:false},
{name:'type',index:'type',align:'center',label : '<s:text name="defineReport.type" />',hidden:false}, 
{name:'isSys',index:'isSys',align:'center',label : '<s:text name="defineReport.isSys" />',hidden:false}, 
{name:'remark',index:'remark',align:'center',label : '<s:text name="defineReport.remark" />',hidden:false}
],
        	jsonReader : {
				root : "defineReports", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'code',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="defineReportList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"defineReport_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("defineReport_gridtable");
       		} 

    	});
    jQuery(defineReportGrid).jqGrid('bindKeys');
    
    jQuery("#defineReport_gridtable_report").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var sid = jQuery("#defineReport_gridtable").jqGrid('getGridParam','selarrrow');
    	var url = "editUserDefineReport?code="+sid;
    	url = encodeURI(url);
    	$.pdialog.open(url, 'editReport', "编辑报表", {
    		mask : true,
    		maxable : true,
    		resizable : true,
    		width : fullWidth,
    		height : fullHeight
    	});
    });
    
  	});
</script>

<div class="page">
	<div id="defineReport_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="defineReport_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='defineReport.code'/>:
						<input type="text" name="filter_EQS_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='defineReport.name'/>:
						<input type="text" name="filter_EQS_name"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='defineReport.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='defineReport.type'/>:
						<input type="text" name="filter_EQS_type"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(defineReport_search_form,defineReport_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(defineReport_search_form,defineReport_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="defineReport_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="defineReport_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="defineReport_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a id="defineReport_gridtable_report" class="changebutton"  href="javaScript:"
					><span>编辑报表
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="defineReport_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="defineReport_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="defineReport_gridtable_addTile">
				<s:text name="defineReportNew.title"/>
			</label> 
			<label style="display: none"
				id="defineReport_gridtable_editTile">
				<s:text name="defineReportEdit.title"/>
			</label>
			<div id="load_defineReport_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="defineReport_gridtable"></table>
			<!--<div id="defineReportPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="defineReport_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="defineReport_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="defineReport_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>