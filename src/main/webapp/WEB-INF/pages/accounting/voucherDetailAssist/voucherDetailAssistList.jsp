
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var voucherDetailAssistLayout;
	var voucherDetailAssistGridIdString="#voucherDetailAssist_gridtable";
	
	jQuery(document).ready(function() { 
		var voucherDetailAssistGrid = jQuery(voucherDetailAssistGridIdString);
    	voucherDetailAssistGrid.jqGrid({
    		url : "voucherDetailAssistGridList",
    		editurl:"voucherDetailAssistGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'voucherDetailAssistId',index:'voucherDetailAssistId',align:'center',label : '<s:text name="voucherDetailAssist.voucherDetailAssistId" />',hidden:false,key:true},{name:'abstractStr',index:'abstractStr',align:'center',label : '<s:text name="voucherDetailAssist.abstractStr" />',hidden:false},{name:'assist',index:'assist',align:'center',label : '<s:text name="voucherDetailAssist.assist" />',hidden:false},{name:'assistValue',index:'assistValue',align:'center',label : '<s:text name="voucherDetailAssist.assistValue" />',hidden:false},{name:'money',index:'money',align:'center',label : '<s:text name="voucherDetailAssist.money" />',hidden:false,formatter:'number'},{name:'num',index:'num',align:'center',label : '<s:text name="voucherDetailAssist.num" />',hidden:false,formatter:'integer'},        	],
        	jsonReader : {
				root : "voucherDetailAssists", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'voucherDetailAssistId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="voucherDetailAssistList.title" />',
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
           	var dataTest = {"id":"voucherDetailAssist_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("voucherDetailAssist_gridtable");
       		} 

    	});
    jQuery(voucherDetailAssistGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="voucherDetailAssist_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="voucherDetailAssist_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.voucherDetailAssistId'/>:
						<input type="text" name="filter_EQS_voucherDetailAssistId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.abstractStr'/>:
						<input type="text" name="filter_EQS_abstractStr"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.assist'/>:
						<input type="text" name="filter_EQS_assist"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.assistType'/>:
						<input type="text" name="filter_EQS_assistType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.assistValue'/>:
						<input type="text" name="filter_EQS_assistValue"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.money'/>:
						<input type="text" name="filter_EQS_money"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.num'/>:
						<input type="text" name="filter_EQS_num"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetailAssist.voucherDetail'/>:
						<input type="text" name="filter_EQS_voucherDetail"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(voucherDetailAssist_search_form,voucherDetailAssist_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(voucherDetailAssist_search_form,voucherDetailAssist_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="voucherDetailAssist_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="voucherDetailAssist_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="voucherDetailAssist_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="voucherDetailAssist_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="voucherDetailAssist_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="voucherDetailAssist_gridtable_addTile">
				<s:text name="voucherDetailAssistNew.title"/>
			</label> 
			<label style="display: none"
				id="voucherDetailAssist_gridtable_editTile">
				<s:text name="voucherDetailAssistEdit.title"/>
			</label>
			<div id="load_voucherDetailAssist_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="voucherDetailAssist_gridtable"></table>
			<!--<div id="voucherDetailAssistPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="voucherDetailAssist_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="voucherDetailAssist_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="voucherDetailAssist_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>