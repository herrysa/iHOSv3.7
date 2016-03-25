
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessTypeParamLayout;
	var businessTypeParamGridIdString="#businessTypeCollectTable_gridtable";
	
	jQuery(document).ready(function() { 
		var businessTypeParamGrid = jQuery(businessTypeParamGridIdString);
		var businessId = jQuery("#businessTypeCollectTable_gridtable_businessId").val();
    	businessTypeParamGrid.jqGrid({
    		url : "businessTypeCollectTableGridList?businessId="+businessId,
    		editurl:"editBusinessTypeCollectTable?businessId="+businessId,
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'ColumnsName',index:'ColumnsName',align:'left',label : '列名',hidden:false,editable:true,key:true},
{name:'ColumnType',index:'ColumnType',align:'left',width:100,label : '类型',editable:true,edittype:'select',editoptions: { value: "varchar:varchar;numeric:numeric;int:int" },hidden:false},
{name:'Length',index:'Length',align:'left',width:100,label : '长度',editable:true,hidden:false},
{name:'Description',index:'Description',align:'left',width:200,label : '描述',editable:true,hidden:false}
],
			jsonReader : {
				root : "colList", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'ColumnsName',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="businessTypeParamList.title" />',
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
           	var dataTest = {"id":"businessTypeCollectTable_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("businessTypeCollectTable_gridtable");
       		} 

    	});
    jQuery(businessTypeParamGrid).jqGrid('bindKeys');
    //添加
    jQuery("#businessTypeCollectTable_gridtable_add_custom").click(function() {
    	//var businessId = jQuery("#businessTypeCollectTable_gridtable_businessId").val();
    	//var url = "editBusinessTypeCollectTable?popup=true&navTabId=businessTypeCollectTable_gridtable"+"&businessId="+businessId;
    	gridAddRow(jQuery("#businessTypeCollectTable_gridtable"));
    	//url = encodeURI(url);
    	//var winTitle = '<s:text name="businessTypeParamNew.title"/>';
		//$.pdialog.open(url,"addBusinessTypeParam",winTitle,{mask:true,width:425,height:275,maxable:false,resizable:false});
    });
    //保存
    jQuery("#businessTypeCollectTable_gridtable_save_custom").click(function() {
    	gridSaveRow(jQuery("#businessTypeCollectTable_gridtable"));
    });
    jQuery("#businessTypeCollectTable_gridtable_edit_custom").click(function() {
    	gridEditRow(jQuery("#businessTypeCollectTable_gridtable"));
    });
    //删除
    jQuery("#businessTypeCollectTable_gridtable_del_custom").click(function() {
    	var sid = jQuery("#businessTypeCollectTable_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}
		var businessId = jQuery("#businessTypeCollectTable_gridtable_businessId").val();
		var url = "deleteBusinessTypeCollectTable?popup=true&navTabId=businessTypeCollectTable_gridtable&businessId="+businessId+"&ColumnsName="+sid[0];
		url = encodeURI(url);
		alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post(url, {
				}, formCallBack, "json");
				
			}
		});
    });
  	});
</script>

<div class="page">
	<%-- <div id="businessTypeParam_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="businessTypeParam_search_form" >
					<label class="queryarea-label">
						<s:text name='businessTypeParam.paramCode'/>:
						<input type="text" name="filter_LIKES_paramCode">
					</label>
					<label class="queryarea-label">
						<s:text name='businessTypeParam.paramValue'/>:
						<input type="text" name="filter_LIKES_paramValue">
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeParam.disabled'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_disabled" theme="simple" />
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeParam.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(businessTypeParam_search_form,businessTypeCollectTable_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="businessTypeCollectTable_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessTypeCollectTable_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<%-- <li><a id="businessTypeCollectTable_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
				<li><a id="businessTypeCollectTable_gridtable_save_custom" class="savebutton"  href="javaScript:"
					><span><s:text name="button.save" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="businessTypeCollectTable_gridtable_div" tablecontainer="businessTypeTabsContent" extraHeight="81" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="businessTypeCollectTable_gridtable_navTabId" value="${sessionScope.navTabId}">
			<input type="hidden" id="businessTypeCollectTable_gridtable_businessId" value="${businessId}" />
			<label style="display: none" id="businessTypeCollectTable_gridtable_addTile">
				<s:text name="businessTypeParamNew.title"/>
			</label> 
			<label style="display: none"
				id="businessTypeCollectTable_gridtable_editTile">
				<s:text name="businessTypeParamEdit.title"/>
			</label>
			<div id="load_businessTypeCollectTable_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="businessTypeCollectTable_gridtable"></table>
			<!--<div id="businessTypeParamPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="businessTypeCollectTable_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessTypeCollectTable_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="businessTypeCollectTable_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>