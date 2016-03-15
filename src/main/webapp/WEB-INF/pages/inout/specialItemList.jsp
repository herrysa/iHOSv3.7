
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function specialItemGridReload(){
		var urlString = "specialItemGridList";
		var itemIdTxt = jQuery("#itemIdTxt").val();
		var itemNameTxt = jQuery("#itemNameTxt").val();
		var specialItem_itemType = jQuery("#specialItem_itemType").val();
	
		urlString=urlString+"?filter_LIKES_itemId="+itemIdTxt+"&filter_LIKES_itemName="+itemNameTxt+"&filter_EQS_itemType="+specialItem_itemType;
		urlString=encodeURI(urlString);
		jQuery("#specialItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var specialItemLayout;
			  var specialItemGridIdString="#specialItem_gridtable";
	
	jQuery(document).ready(function() { 
		specialItemLayout = makeLayout({
			baseName: 'specialItem', 
			tableIds: 'specialItem_gridtable'
		}, null);
var specialItemGrid = jQuery(specialItemGridIdString);
    specialItemGrid.jqGrid({
    	url : "specialItemGridList",
    	editurl:"specialItemGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'itemId',index:'itemId',align:'left',label : '<s:text name="specialItem.itemId" />',hidden:false,key:true,width:60},				
{name:'itemName',index:'itemName',align:'left',label : '<s:text name="specialItem.itemName" />',hidden:false,width:80},				
{name:'itemType',index:'itemType',align:'center',label : '<s:text name="specialItem.itemType" />',hidden:false,width:40},				
{name:'remark',index:'remark',align:'left',label : '<s:text name="specialItem.remark" />',hidden:false},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="specialItem.disabled" />',hidden:false,formatter:'checkbox',width:40}				

        ],
        jsonReader : {
			root : "specialItems", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'itemId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="specialItemList.title" />',
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
			 gridContainerResize("specialItem","div");
			 
           var dataTest = {"id":"specialItem_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("specialItem_gridtable");
       	} 

    });
    jQuery(specialItemGrid).jqGrid('bindKeys');
    
	
	
	
	//specialItemLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="specialItem_container">
			<div id="specialItem_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="specialItem_pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<label class="queryarea-label"><s:text name='specialItem.itemId'/>:
							<input type="text"	id="itemIdTxt" >
					</label>
					<label class="queryarea-label"><s:text name='specialItem.itemName'/>:
						 	<input type="text"	id="itemNameTxt" >
					</label>
					<label class="queryarea-label"><s:text name='specialItem.itemType'/>:
						 <s:select id="specialItem_itemType"  style="width:133px;" 	list="dicSpecialItemList" listKey="value" listValue="content"
					emptyOption="true"></s:select>
					</label>
					<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="specialItemGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="specialItem_buttonBar">
			<ul class="toolBar">
				<li><a id="specialItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="specialItem_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="specialItem_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="specialItem_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:itemId;width:500;height:300">
			<input type="hidden" id="specialItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="specialItem_gridtable_addTile">
				<s:text name="specialItemNew.title"/>
			</label> 
			<label style="display: none"
				id="specialItem_gridtable_editTile">
				<s:text name="specialItemEdit.title"/>
			</label>
			<label style="display: none"
				id="specialItem_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="specialItem_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_specialItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="specialItem_gridtable"></table>
		<div id="specialItemPager"></div>
</div>
	</div>
	<div class="panelBar" id="specialItem_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="specialItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="specialItem_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="specialItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>