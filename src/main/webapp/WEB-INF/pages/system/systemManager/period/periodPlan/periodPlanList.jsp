
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function periodPlanGridReload(){
		var urlString = "periodPlanGridList?1=1";
		propertyFilterSearch("periodPlan_search_form","periodPlan_gridtable",true);
		urlString=encodeURI(urlString);
		jQuery("#periodPlan_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	function changeDefaultPeriodPlan(){
		//alertMsg.info("切换当前类别！");
		var sid = jQuery("#periodPlan_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid.length != 1) {
			alertMsg.info("请选择一条记录！");
			return;
		}
		var rowId = sid[0];
		var row = jQuery("#periodPlan_gridtable").jqGrid('getRowData',rowId);
		if(row["isDefault"] == "Yes"){
			alertMsg.error("该方案已经是当前方案！");
			return;
		}
		var url = "periodPlanGridEdit?planId="+rowId+"&popup=true&navTabId=periodPlan_gridtable&oper=changeDef";  
		alertMsg.confirm("确认切换当前方案？",{
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					if(data.statusCode == 200){
						setTimeout(function(){
							//关闭除了main和当前页的其他页面
							var navTabLis= jQuery("ul.navTab-tab li");
							var curTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
							jQuery.each(navTabLis, function(){
								var tabid = jQuery(this).attr("tabid");
								if(tabid != curTabid&&tabid != "main"){
									navTab.closeTab(tabid);
								}
							});
						},100);
					}
				});
			}
		});
		
	}
	
	function delPeriodPlanItem(){
		//alertMsg.info("删除！");
		var sid = jQuery("#periodPlan_gridtable").jqGrid("getGridParam","selarrrow");
		var editUrl = jQuery("#periodPlan_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId=periodPlan_gridtable&oper=del";
		if(sid==null || sid.length ==0){
			alertMsg.error("请选择记录！");
			return;
		}
		for(var i = 0;i < sid.length; i++) {
			var row = jQuery("#periodPlan_gridtable").jqGrid("getRowData",sid[i]);
			if(row["isDefault"] == "Yes") {
				alertMsg.error("默认方案不能删除！");
				return;
			}
		}
		alertMsg.confirm("确认删除？",{
			okCall:function() {
				jQuery.post(editUrl, {}, formCallBack, "json");
			}
		});
	}
	
	var periodPlanLayout;
	var periodPlanGridIdString="#periodPlan_gridtable";
	
	jQuery(document).ready(function() { 
	var periodPlanGrid = jQuery(periodPlanGridIdString);
	periodPlanGrid.jqGrid({
		url : "periodPlanGridList",
		editurl:"periodPlanGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'planId',index:'planId',align:'left',label : '<s:text name="periodPlan.planId" />',width:100,hidden:false,key:true,editable:true,edittype:"text"},				
{name:'planName',index:'planName',align:'left',label : '<s:text name="periodPlan.planName" />',width:200,hidden:false,editable:true,edittype:"text"},			
{name:'remark',index:'remark',align:'left',label : '<s:text name="periodPlan.remark" />',width:300,hidden:false,editable:true,edittype:"text"},				
{name:'isDefault',index:'isDefault',align:'center',label : '<s:text name="periodPlan.isDefault" />',width:100,hidden:false,editable:false,formatter:"checkbox"}			
		],
		jsonReader : {
			root : "periodPlans", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
		sortname: 'planId',
		viewrecords: true,
		sortorder: 'asc',
		//caption:'<s:text name="periodPlanList.title" />',
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
			gridContainerResize('periodPlan','div');
			if(jQuery(this).getDataIDs().length>0){
			//jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
			}
			var dataTest = {"id":"periodPlan_gridtable"};
			jQuery.publish("onLoadSelect",dataTest,null);
			makepager("periodPlan_gridtable");
		} 

		});
	jQuery(periodPlanGrid).jqGrid('bindKeys');
	});
</script>

<div class="page">
	<div id="periodPlan_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="periodPlan_search_form" class="queryarea-form">
					<label class="queryarea-label">
					<s:text name='periodPlan.planId' />
					<input type="text" name="filter_LIKES_planId" />
					</label>
					<label class="queryarea-label">
					<s:text name='periodPlan.planName' />: 
					<input type="text" name="filter_LIKES_planName" />
					</label>
					<%-- 					<label class="queryarea-label">
						<s:text name='periodPlan.isDefault'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_isDefault"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label> --%>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button" onclick="periodPlanGridReload();">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="periodPlan_buttonBar">
			<ul class="toolBar">
				<li><a class="addbutton"
					onclick="gridAddRow(jQuery('#periodPlan_gridtable'))"><span><fmt:message
								key="button.addRow" /></span></a></li>
				<li><a class="editbutton"
					onclick="gridEditRow(jQuery('#periodPlan_gridtable'))"><span><fmt:message
								key="button.editRow" /></span></a></li>
				<li><a class="savebutton"
					onclick="gridSaveRow(jQuery('#periodPlan_gridtable'))"><span><fmt:message
								key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton"
					onclick="gridRestore(jQuery('#periodPlan_gridtable'))"><span><fmt:message
								key="button.restoreRow" /></span></a></li>
				<li><a class="delbutton" href="javaScript:delPeriodPlanItem();"><span>删除</span></a></li>
				<li><a class="changebutton" href="javaScript:changeDefaultPeriodPlan();"><span>切换方案</span></a></li>
			</ul>
		</div>
		<div id="periodPlan_gridtable_div"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:planId;width:500;height:300">
			<input type="hidden" id="periodPlan_gridtable_navTabId"
				value="${sessionScope.navTabId}"> <label
				style="display: none" id="periodPlan_gridtable_addTile"> <s:text
					name="periodPlanNew.title" />
			</label> <label style="display: none" id="periodPlan_gridtable_editTile">
				<s:text name="periodPlanEdit.title" />
			</label> <label style="display: none" id="periodPlan_gridtable_selectNone">
				<s:text name='list.selectNone' />
			</label> <label style="display: none" id="periodPlan_gridtable_selectMore">
				<s:text name='list.selectMore' />
			</label>
			<table id="periodPlan_gridtable"></table>
			<div id="periodPlanPager"></div>
		</div>
	</div>
	<div class="panelBar" id="periodPlan_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select
				id="periodPlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text
					name="pager.total" /><label id="periodPlan_gridtable_totals"></label>
				<s:text name="pager.items" /></span>
		</div>

		<div id="periodPlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
