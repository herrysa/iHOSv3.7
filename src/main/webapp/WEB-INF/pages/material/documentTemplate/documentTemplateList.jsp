
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var documentTemplateGridIdString="#documentTemplate_gridtable";
	jQuery(document).ready(function() { 
		var documentTemplateGrid = jQuery(documentTemplateGridIdString);
   		documentTemplateGrid.jqGrid({
	    	url : "documentTemplateGridList",
	    	editurl:"documentTemplateGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="documentTemplate.id" />',hidden:true,key:true},				
				{name:'templateType',index:'templateType',align:'left',width:70,label : '<s:text name="documentTemplate.templateType" />',hidden:false},				
				{name:'templateName',index:'templateName',align:'left',width:80,label : '<s:text name="documentTemplate.templateName" />',hidden:false},				
				{name:'title',index:'title',align:'left',width:100,label : '<s:text name="documentTemplate.title" />',hidden:false},				
				{name:'useDate',index:'useDate',align:'center',width:60,label : '<s:text name="documentTemplate.useDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				/* {name:'inputArea',index:'inputArea',align:'left',label : '<s:text name="documentTemplate.inputArea" />',hidden:false,sortable:false},				
				{name:'listArea',index:'listArea',align:'left',label : '<s:text name="documentTemplate.listArea" />',hidden:false,sortable:false},			
				{name:'footArea',index:'footArea',align:'left',label : '<s:text name="documentTemplate.footArea" />',hidden:false,sortable:false},				
				 */{name:'beUsed',index:'beUsed',align:'center',width:50,label : '<s:text name="documentTemplate.beUsed" />',hidden:false,formatter:'checkbox'}				

	        ],
	        jsonReader : {
				root : "documentTemplates", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'useDate',
	        viewrecords: true,
	        sortorder: 'desc',
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
	           var dataTest = {"id":"documentTemplate_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("documentTemplate_gridtable");
	       	} 
	    });
    	jQuery(documentTemplateGrid).jqGrid('bindKeys');
  	});
	function docTemFormEdit(type){
   		var sid = jQuery("#documentTemplate_gridtable").jqGrid('getGridParam','selarrrow');
   		if (sid == null || sid.length == 0) {
   			alertMsg.error("请选择记录。");
   			return;
   		} else if(sid.length > 1){
   			alertMsg.error("请只选择一条记录");
			return;
   		}else {
   			if(type=="edit"){
   				var row = jQuery("#documentTemplate_gridtable").jqGrid('getRowData',sid[0]);
   				if(row['beUsed']=='Yes'){
					alertMsg.error("您选择的模板["+row['templateName']+"]正在使用,不能修改!");
					return;
				}
   				var url = "${ctx}/editDocumentTemplate?id="+sid+"&templateType="+row['templateType']+"&navTabId=documentTemplate_gridtable";
   	   			$.pdialog.open(url,'editDocumentTemplate',"修改单据模板", {mask:true,width : 880,height : 588,resizable:false,maxable:false});
   			}else if(type=="preview"){
   				var row = jQuery("#documentTemplate_gridtable").jqGrid('getRowData',sid[0]);
   				var dialogTitle = ""+row['templateName']+"预览";
   				$.pdialog.open('${ctx}/previewDocumentTemplate?id='+sid,'previewDocumentTemplate',dialogTitle, {mask:true,width : 972,height : 628,resizable:false,maxable:true});
   			}
   			
   		}
	}
	function docTemListEdit(type){
		var url = "${ctx}/documentTemplateGridEdit?oper="+type;
    	var sid = jQuery("#documentTemplate_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}else{
			for(var i = 0;i < sid.length; i++){
   				var rowId = sid[i];
   				var row = jQuery("#documentTemplate_gridtable").jqGrid('getRowData',rowId);
   				if(type=="disable"){
   					if(row['beUsed']=='No'){
   						alertMsg.error("您选择的模板["+row['templateName']+"]处于停用状态,无需停用!");
   						return;
   					}
   				}else if(type=="enable"){
   					if(row['beUsed']=='Yes'){
   						alertMsg.error("您选择的模板["+row['templateName']+"]正在使用,不能重复启用!");
   						return;
   					}
   				}else if(type=="del"){
   					if(row['beUsed']=='Yes'){
   						alertMsg.error("您选择的模板["+row['templateName']+"]正在使用,不能删除!");
   						return;
   					}
   				}
   			}
			url = url+"&id="+sid+"&navTabId=documentTemplate_gridtable";
			if(type=="del"){
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						jQuery.post(url, function(data) {
							formCallBack(data);
						});
					}
				});
			}else{
				jQuery.post(url, function(data) {
					formCallBack(data);
				});
			}
		}
	}
</script>

<div class="page">
	<div id="documentTemplate_container">
		<div id="documentTemplate_layout-center" class="pane ui-layout-center">
			<div id="documentTemplate_pageHeader" class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form id="documentTemplate_search_form" >
							<label style="float:none;white-space:nowrap" >
								<s:text name='documentTemplate.templateType'/>:
								<s:select list="templateTypes" name="filter_LIKES_templateType" emptyOption="true" style="width:125px"></s:select>
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >
								<s:text name='documentTemplate.templateName'/>:
								<input type="text" name="filter_LIKES_templateName">
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >
								<s:text name='documentTemplate.title'/>:
								<input type="text" name="filter_LIKES_title">
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >
								<s:text name='documentTemplate.beUsed'/>:
							 	<select  name="filter_EQB_beUsed">
							 		<option value="">--</option>
							 		<option value="1">是</option>
							 		<option value="0">否</option>
							 	</select>
							</label>&nbsp;&nbsp;
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('documentTemplate_search_form','documentTemplate_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</form>
					</div>
					<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="propertyFilterSearch('documentTemplate_search_form','documentTemplate_gridtable')"><s:text name='button.search'/></button>
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
						<li>
							<a id="documentTemplate_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
						</li>
						<li>
							<a class="delbutton"  href="javaScript:docTemListEdit('del')"><span><s:text name="button.delete" /></span></a>
						</li>
						<li>
							<a class="changebutton"  href="javaScript:docTemFormEdit('edit')"><span><s:text name="button.edit" /></span></a>
						</li>
						<li>
							<a class="previewbutton"  href="javaScript:docTemFormEdit('preview')"><span><s:text name="预览" /></span></a>
						</li>
						<li>
							<a class="enablebutton"  href="javaScript:docTemListEdit('enable')"><span><s:text name="button.enable" /></span></a>
						</li>
						<li>
							<a class="disablebutton"  href="javaScript:docTemListEdit('disable')"><span><s:text name="button.disable" /></span></a>
						</li>
					</ul>
				</div>
				<div id="documentTemplate_gridtable_div" layoutH="120" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
					buttonBar="optId:id;width:880;height:588">
					<input type="hidden" id="documentTemplate_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="documentTemplate_gridtable_addTile">
						<s:text name="documentTemplateNew.title"/>
					</label> 
					<label style="display: none" id="documentTemplate_gridtable_editTile">
						<s:text name="documentTemplateEdit.title"/>
					</label>
					<label style="display: none" id="documentTemplate_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none" id="documentTemplate_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_documentTemplate_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
		 			<table id="documentTemplate_gridtable"></table>
				</div>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="documentTemplate_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="documentTemplate_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
				<div id="documentTemplate_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>
			</div>
		</div>
	</div>
</div>