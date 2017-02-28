
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function invOutStoreGridReload(){
		var urlString = "invOutStoreGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#invOutStore_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var invOutStoreLayout;
			  var invOutStoreGridIdString="#invOutStore_gridtable";
	
	jQuery(document).ready(function() { 
		invOutStoreLayout = makeLayout({
			baseName: 'invOutStore', 
			tableIds: 'invOutStore_gridtable'
		}, null);
var invOutStoreGrid = jQuery(invOutStoreGridIdString);
    invOutStoreGrid.jqGrid({
    	url : "invOutStoreGridList",
    	editurl:"invOutStoreGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="invOutStore.id" />',hidden:false,key:true},				
{name:'barCode',index:'barCode',align:'center',label : '<s:text name="invOutStore.barCode" />',hidden:false},				
{name:'batchId',index:'batchId',align:'center',label : '<s:text name="invOutStore.batchId" />',hidden:false},				
{name:'batchNo',index:'batchNo',align:'center',label : '<s:text name="invOutStore.batchNo" />',hidden:false},				
{name:'cnCode',index:'cnCode',align:'center',label : '<s:text name="invOutStore.cnCode" />',hidden:false},				
{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="invOutStore.copyCode" />',hidden:false},				
{name:'curAmount',index:'curAmount',align:'center',label : '<s:text name="invOutStore.curAmount" />',hidden:false,formatter:'number'},				
{name:'curMoney',index:'curMoney',align:'center',label : '<s:text name="invOutStore.curMoney" />',hidden:false,formatter:'number'},				
{name:'factory',index:'factory',align:'center',label : '<s:text name="invOutStore.factory" />',hidden:false},				
{name:'firstUnit',index:'firstUnit',align:'center',label : '<s:text name="invOutStore.firstUnit" />',hidden:false},				
{name:'guarantee',index:'guarantee',align:'center',label : '<s:text name="invOutStore.guarantee" />',hidden:false},				
{name:'invCode',index:'invCode',align:'center',label : '<s:text name="invOutStore.invCode" />',hidden:false},				
{name:'invId',index:'invId',align:'center',label : '<s:text name="invOutStore.invId" />',hidden:false},				
{name:'invModel',index:'invModel',align:'center',label : '<s:text name="invOutStore.invModel" />',hidden:false},				
{name:'invName',index:'invName',align:'center',label : '<s:text name="invOutStore.invName" />',hidden:false},				
{name:'invTypeId',index:'invTypeId',align:'center',label : '<s:text name="invOutStore.invTypeId" />',hidden:false},				
{name:'invTypeName',index:'invTypeName',align:'center',label : '<s:text name="invOutStore.invTypeName" />',hidden:false},				
{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="invOutStore.orgCode" />',hidden:false},				
{name:'price',index:'price',align:'center',label : '<s:text name="invOutStore.price" />',hidden:false,formatter:'number'},				
{name:'productionDate',index:'productionDate',align:'center',label : '<s:text name="invOutStore.productionDate" />',hidden:false},				
{name:'storeId',index:'storeId',align:'center',label : '<s:text name="invOutStore.storeId" />',hidden:false},				
{name:'vendorId',index:'vendorId',align:'center',label : '<s:text name="invOutStore.vendorId" />',hidden:false},				
{name:'vendorName',index:'vendorName',align:'center',label : '<s:text name="invOutStore.vendorName" />',hidden:false},				
{name:'yearMonth',index:'yearMonth',align:'center',label : '<s:text name="invOutStore.yearMonth" />',hidden:false}				

        ],
        jsonReader : {
			root : "invOutStores", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="invOutStoreList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"invOutStore_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("invOutStore_gridtable");
       	} 

    });
    jQuery(invOutStoreGrid).jqGrid('bindKeys');
    
	
	
	
	//invOutStoreLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="invOutStore_container">
			<div id="invOutStore_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='invOutStore.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='invOutStore.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='invOutStore.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='invOutStore.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="invOutStoreGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a id="invOutStore_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="invOutStore_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="invOutStore_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="invOutStore_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="invOutStore_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invOutStore_gridtable_addTile">
				<s:text name="invOutStoreNew.title"/>
			</label> 
			<label style="display: none"
				id="invOutStore_gridtable_editTile">
				<s:text name="invOutStoreEdit.title"/>
			</label>
			<label style="display: none"
				id="invOutStore_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invOutStore_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_invOutStore_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="invOutStore_gridtable"></table>
		<div id="invOutStorePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invOutStore_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invOutStore_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invOutStore_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>