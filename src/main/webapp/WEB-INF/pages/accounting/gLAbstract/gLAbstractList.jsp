
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gLAbstractLayout;
	var gLAbstractGridIdString="#gLAbstract_gridtable";
	
	jQuery(document).ready(function() { 
		gLAbstractLayout = makeLayout({
			baseName: 'gLAbstract', 
			tableIds: 'gLAbstract_gridtable'
		}, null);
		var gLAbstractGrid = jQuery(gLAbstractGridIdString);
		    gLAbstractGrid.jqGrid({
		    	url : "gLAbstractGridList",
		    	editurl:"gLAbstractGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
			{name:'abstractId',index:'abstractId',align:'center',label : '<s:text name="gLAbstract.abstractId" />',hidden:true,key:true},				
			{name:'acctcode',index:'acctcode',align:'center',label : '<s:text name="gLAbstract.acctcode" />',hidden:true,editable:true},				
			{name:'cnCode',index:'cnCode',align:'left',label : '<s:text name="gLAbstract.cnCode" />',hidden:false,width:50,edittype:'text',editable:false},				
			{name:'copycode',index:'copycode',align:'center',label : '<s:text name="gLAbstract.copycode" />',hidden:true,editable:true},				
			{name:'kjYear',index:'kjYear',align:'center',label : '<s:text name="gLAbstract.kjYear" />',hidden:true,editable:true},				
			{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="gLAbstract.orgCode" />',hidden:true,editable:true},				
			{name:'voucher_abstract',index:'voucher_abstract',align:'left',label : '<s:text name="gLAbstract.voucher_abstract" />',hidden:false,edittype:'text',editable:true},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="gLAbstract.disabled" />',hidden:false,edittype:'checkbox',editable:true,formatter:'checkbox',width:30}			
		        ],
		        jsonReader : {
					root : "gLAbstracts", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				// (4)
				},
		        sortname: 'abstractId',
		        viewrecords: true,
		        sortorder: 'desc',
		        //caption:'<s:text name="gLAbstractList.title" />',
		        height:300,
		        gridview:true,
		        rownumbers:true,
		        loadui: "disable",
		        multiselect: true,
				multiboxonly:true,
				shrinkToFit:true,
				autowidth:false,
		        onSelectRow: function(rowid) {
		       
		       	},
				 gridComplete:function(){
		           if(jQuery(this).getDataIDs().length>0){
		              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
		            }
		           var dataTest = {"id":"gLAbstract_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("gLAbstract_gridtable");
		       	} 
		
		    });
    	jQuery(gLAbstractGrid).jqGrid('bindKeys');
    
	
	
	
	//gLAbstractLayout.resizeAll();
  	});
</script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
    <link  rel="stylesheet"  href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<div class="page">
<div id="gLAbstract_container">
			<div id="gLAbstract_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<form id="searchAbstractForm">
			<table class="searchContent">
					<tr>
						<td><s:text name='gLAbstract.cnCode'/>:
							<input type="text" name="filter_LIKES_cnCode"	id="glabstract_value" >
						 </td>
						<td><s:text name='gLAbstract.voucher_abstract'/>:
						 	<input type="text" name="filter_LIKES_voucher_abstract" >
						</td>
						<td><s:text name='gLAbstract.disabled'/>:
						 	<s:select name="filter_EQB_disabled"  maxlength="20" list="#{'':'全部','false':'使用','true':'停用'}" theme="simple"></s:select>
						 </td>
			<%--	
							<s:text name='gLAbstract.disabled'/>: 
						 	<input type="text"		id="descriptionTxt" >
						 <td><s:text name='gLAbstract.subSystemId'/>:
						 	
						 </td>
				--%>
					</tr>
				</table>
			</form>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('searchAbstractForm','gLAbstract_gridtable' )"><s:text name='button.search'/></button>
								</div>
							</div></li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

		<div class="panelBar">
		<ul class="toolBar">
				<li><a class="addbutton" onclick="gridAddRow(jQuery('#gLAbstract_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="gridEditRow(jQuery('#gLAbstract_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#gLAbstract_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#gLAbstract_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="gLAbstract_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			</ul>
			<!-- 
			<ul class="toolBar">
				<li><a id="gLAbstract_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="gLAbstract_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="gLAbstract_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
			 -->
		</div>
		<div id="gLAbstract_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:abstractId;width:500;height:300">
			<input type="hidden" id="gLAbstract_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gLAbstract_gridtable_addTile">
				<s:text name="gLAbstractNew.title"/>
			</label> 
			<label style="display: none"
				id="gLAbstract_gridtable_editTile">
				<s:text name="gLAbstractEdit.title"/>
			</label>
			<label style="display: none"
				id="gLAbstract_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="gLAbstract_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_gLAbstract_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="gLAbstract_gridtable"></table>
		<div id="gLAbstractPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gLAbstract_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gLAbstract_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="gLAbstract_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>