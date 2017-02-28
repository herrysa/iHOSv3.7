
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var formDesignerLayout;
	var formDesignerGridIdString="#formDesigner_gridtable";
	
	jQuery(document).ready(function() { 
		var formDesignerGrid = jQuery(formDesignerGridIdString);
    	formDesignerGrid.jqGrid({
    		url : "formDesignerGridList",
    		editurl:"formDesignerGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'formId',index:'formId',align:'left',label : '<s:text name="formDesigner.formId" />',hidden:false,key:true},
			{name:'formName',index:'formName',align:'left',label : '<s:text name="formDesigner.formName" />',hidden:false},
			{name:'bdInfoId',index:'bdInfoId',align:'left',label : '<s:text name="formDesigner.bdInfoId" />',hidden:false},
			{name:'detailBdinfoId',index:'detailBdinfoId',align:'left',label : '<s:text name="formDesigner.detailBdinfoId" />',hidden:false},
			],
        	jsonReader : {
				root : "formDesigners", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'formId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="formDesignerList.title" />',
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
           	var dataTest = {"id":"formDesigner_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	jQuery("#formDesigner_gridtable_design").click(function(){
    		var sid = jQuery("#formDesigner_gridtable").jqGrid('getGridParam','selarrrow');
        	if(sid.length==0){
        		alertMsg.error("请选择一条记录！");
        		return;
        	}
        	if(sid.length>1){
        		alertMsg.error("只能选择一条记录！");
        		return;
        	}
        	var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "designForm?navTabId=design&formId="+sid;
    		var winTitle='设计表单模板';
    		$.pdialog.open(url,'designForm',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    	});
  	});
</script>

<div class="page">
	<div id="formDesigner_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="formDesigner_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='formDesigner.formId'/>:
						<input type="text" name="filter_EQS_formId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='formDesigner.bdInfoId'/>:
						<input type="text" name="filter_EQS_bdInfoId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='formDesigner.formName'/>:
						<input type="text" name="filter_EQS_formName"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('formDesigner_search_form','formDesigner_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="formDesigner_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="formDesigner_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="formDesigner_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a id="formDesigner_gridtable_design" class="changebutton"  href="javaScript:"
					><span>设计模板
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="formDesigner_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="optId:formId;width:500;height:300">
			<input type="hidden" id="formDesigner_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="formDesigner_gridtable_addTile">
				<s:text name="formDesignerNew.title"/>
			</label> 
			<label style="display: none"
				id="formDesigner_gridtable_editTile">
				<s:text name="formDesignerEdit.title"/>
			</label>
			<div id="load_formDesigner_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="formDesigner_gridtable"></table>
			<!--<div id="formDesignerPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="formDesigner_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="formDesigner_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="formDesigner_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>