
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bankLayout;
	var bankGridIdString="#bank_gridtable";
	
	jQuery(document).ready(function() { 
		var bankGrid = jQuery(bankGridIdString);
    	bankGrid.jqGrid({
    		url : "bankGridList",
    		editurl:"bankGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'bankId',index:'bankId',align:'center',width:'100px',label : '<s:text name="bank.bankId" />',hidden:false,key:true},
{name:'bankName',index:'bankName',align:'center',width:'100px',label : '<s:text name="bank.bankName" />',hidden:false},
{name:'disabled',index:'disabled',align:'center',width:'60px',label : '<s:text name="bank.disabled" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'center',width:'200px',label : '<s:text name="bank.remark" />',hidden:false}   
],
        	jsonReader : {
				root : "banks", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'bankId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bankList.title" />',
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
		 		gridContainerResize('bank','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"bank_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
//       	   	makepager("bank_gridtable");
       		} 

    	});
    jQuery(bankGrid).jqGrid('bindKeys');
    
    	jQuery("#bank_gridtable_add_custom").bind("click",function(){
    		var url = "editBank?popup=true&navTabId=bank_gridtable";
    		var winTitle = '<s:text name="bankNew.title"/>';
        	$.pdialog.open(url,'editBank',winTitle, {mask:true,width : 650,height : 300,maxable:true,resizable:true});  
    	});
    	jQuery("#bank_gridtable_edit_custom").bind("click",function(){
    		var sid = jQuery("#bank_gridtable").jqGrid('getGridParam','selarrrow');
        	 if(sid==null || sid.length !=1){
 	        	alertMsg.error("请选择一条记录！");
 				return pass;
 			}
 	       var url = "editBank?bankId="+sid+"&popup=true&navTabId=bank_gridtable";
        	var winTitle = '<s:text name="bankEdit.title"/>';
        	$.pdialog.open(url,'editBank',winTitle, {mask:true,width : 650,height : 300,maxable:true,resizable:true});  
    	});
  	});
</script>

<div class="page" id="bank_page">
	<div id="bank_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bank_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='bank.bankId'/>:
						<input type="text" name="filter_LIKES_bankId"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='bank.bankName'/>:
						<input type="text" name="filter_LIKES_bankName"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='bank.disabled'/>: --%>
<!-- 						<input type="text" name="filter_EQS_disabled"/> -->
<!-- 					</label> -->
					<label class="queryarea-label" >
						<s:text name='bank.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bank_search_form','bank_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch(bank_search_form,bank_gridtable)"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="bank_buttonBar">
			<ul class="toolBar">
				<li><a id="bank_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="bank_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bank_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="bank_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bank_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bank_gridtable_addTile">
				<s:text name="bankNew.title"/>
			</label> 
			<label style="display: none"
				id="bank_gridtable_editTile">
				<s:text name="bankEdit.title"/>
			</label>
			<div id="load_bank_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bank_gridtable"></table>
			<!--<div id="bankPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="bank_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bank_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bank_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bank_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>