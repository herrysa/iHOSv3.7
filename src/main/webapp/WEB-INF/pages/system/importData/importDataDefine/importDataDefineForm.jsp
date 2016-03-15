<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>

<script type="text/javascript">
	var importDataDefineDetailLayout;
	var importDataDefineDetailGridIdString = "#importDataDefineDetail_gridtable";

	jQuery(document)
			.ready(
					function() {
						var importDataDefineDetailGrid = jQuery(importDataDefineDetailGridIdString);
						importDataDefineDetailGrid
								.jqGrid({
									url:"",
						    		editurl:"",
									datatype : "json",
									mtype : "GET",
									colModel : [
{name:'detailId',index:'detailId',align:'left',label : '<s:text name="importDataDefineDetail.detailId" />',hidden:true,key:true},
{name : 'detailName',index : 'detailName',align : 'left',label : '<s:text name="importDataDefineDetail.detailName" />',hidden : false},
{name : 'detailType',index : 'detailType',align : 'left',label : '<s:text name="importDataDefineDetail.detailType" />',hidden : false},
{name : 'entityName',index : 'entityName',align : 'left',label : '<s:text name="importDataDefineDetail.entityName" />',hidden : false},
{name : 'isConstraint',index : 'isConstraint',align : 'left',label : '<s:text name="importDataDefineDetail.isConstraint" />',hidden : false}
									    ],
									jsonReader : {
										root : "importDataDefineDetails", // (2)
										page : "page",
										total : "total",
										records : "records", // (3)
										repeatitems : false
									// (4)
									},
									sortname : 'detailId',
									viewrecords : true,
									sortorder : 'desc',
									//caption:'<s:text name="importDataDefineDetailList.title" />',
									height : 300,
									gridview : true,
									rownumbers : true,
									loadui : "disable",
									multiselect : true,
									multiboxonly : true,
									shrinkToFit : true,
									autowidth : true,
									onSelectRow : function(rowid) {
									},
									gridComplete : function() {
										//if(jQuery(this).getDataIDs().length>0){
										//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
										// }
										var dataTest = {
											"id" : "importDataDefineDetail_gridtable"
										};
										jQuery.publish("onLoadSelect",
												dataTest, null);
										makepager("importDataDefineDetail_gridtable");
									}

								});
						jQuery(importDataDefineDetailGrid).jqGrid('bindKeys');
					});
	/*保存*/
	function saveDataDefineDetail() {
		jQuery("#importDataDefineForm").submit();
	}
	/*添加*/
	function addDataDefineDetail() {

	}
</script>
</head>

<div class="page">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="savebutton" href="javaScript:saveDataDefineDetail();"><span><s:text
							name="button.save" /></span></a></li>
			<li><a class="canceleditbutton" href="javaScript:void(0);" onclick="$.pdialog.closeCurrent();"><span><s:text
							name="button.cancel" /></span></a></li>
			<li><a class="addbutton" href="javaScript:addDataDefineDetail();"><span><s:text
							name="button.add" /></span></a></li>
			<li><a class="delbutton" href="javaScript:void(0);" id="importDataDefineDetail_gridtable_del"><span><s:text
							name="button.delete" /></span></a></li>
		</ul>
	</div>
	<div class="pageContent" id="detailPageContent">
		<form id="importDataDefineForm" method="post" style="height:45px;width:auto;"
			action="saveImportDataDefine?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);" layouth="410px">
			<div class="pageFormContent" style="padding: 0">
				<div class="unit">
					<s:hidden key="importDataDefine.interfaceId" />
				</div>
				<div class="unit" style="color: #15428B;">
					<s:textfield id="importDataDefine_interfaceName"
						key="importDataDefine.interfaceName"
						name="importDataDefine.interfaceName" cssClass="required" />
				</div>
			</div>
		</form>
		<div id="importDataDefineDetail_gridtable_div" layoutH="120"
			class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="importDataDefineDetail_gridtable_navTabId"
				value="${sessionScope.navTabId}"> <label
				style="display: none" id="importDataDefineDetail_gridtable_addTile">
				<s:text name="importDataDefineDetailNew.title" />
			</label> <label style="display: none"
				id="importDataDefineDetail_gridtable_editTile"> <s:text
					name="importDataDefineDetailEdit.title" />
			</label>
			<div id="load_importDataDefineDetail_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="importDataDefineDetail_gridtable"></table>
			<!--<div id="importDataDefineDetailPager"></div>-->
		</div>
	</div>

</div>

<!-- <div class="page">
	<div class="pageContent">
		<form id="importDataDefineForm" method="post"	action="saveImportDataDefine?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="importDataDefine.interfaceId"/>
				</div>
				<div class="unit">
				<s:textfield id="importDataDefine_interfaceName" key="importDataDefine.interfaceName" name="importDataDefine.interfaceName" cssClass="required"/>
				</div>			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div> -->





