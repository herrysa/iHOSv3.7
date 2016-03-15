
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessTypeParamLayout;
	var businessTypeParamGridIdString="#businessTypeParam_gridtable";
	
	jQuery(document).ready(function() { 
		var businessTypeParamGrid = jQuery(businessTypeParamGridIdString);
		var businessId = jQuery("#businessTypeParam_gridtable_businessId").val();
    	businessTypeParamGrid.jqGrid({
    		url : "businessTypeParamGridList?businessId="+businessId,
    		editurl:"businessTypeParamGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'paramId',index:'paramId',align:'center',label : '<s:text name="businessTypeParam.paramId" />',hidden:true,key:true},
{name:'paramCode',index:'paramCode',align:'left',width:100,label : '<s:text name="businessTypeParam.paramCode" />',hidden:false},
{name:'paramValue',index:'paramValue',align:'left',width:200,label : '<s:text name="businessTypeParam.paramValue" />',hidden:false},
{name:'sn',index:'sn',align:'right',width:50,label : '<s:text name="businessTypeParam.sn" />',hidden:false,formatter:'integer'},
{name:'disabled',index:'disabled',align:'center',width:75,label : '<s:text name="businessTypeParam.disabled" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'left',width:400,label : '<s:text name="businessTypeParam.remark" />',hidden:false}
],
			jsonReader : {
				root : "businessTypeParams", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'paramId',
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
           	var dataTest = {"id":"businessTypeParam_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("businessTypeParam_gridtable");
       		} 

    	});
    jQuery(businessTypeParamGrid).jqGrid('bindKeys');
    //添加
    jQuery("#businessTypeParam_gridtable_add_custom").click(function() {
    	var businessId = jQuery("#businessTypeParam_gridtable_businessId").val();
    	var url = "editBusinessTypeParam?popup=true&navTabId=businessTypeParam_gridtable"+"&businessId="+businessId;
    	url = encodeURI(url);
    	var winTitle = '<s:text name="businessTypeParamNew.title"/>';
		$.pdialog.open(url,"addBusinessTypeParam",winTitle,{mask:true,width:425,height:275,maxable:false,resizable:false});
    });
    //修改
    jQuery("#businessTypeParam_gridtable_edit_custom").click(function() {
    	var sid = jQuery("#businessTypeParam_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}
		if(sid.length != 1) {
			alertMsg.error("只能选择一条记录。");
			return;
		}
		var url = "editBusinessTypeParam?popup=true&navTabId=businessTypeParam_gridtable&paramId="+sid[0];
		url = encodeURI(url);
		var winTitle = '<s:text name="businessTypeParamEdit.title"/>';
		$.pdialog.open(url,"editBusinessTypeParam",winTitle,{mask:true,width:425,height:300,maxable:false,resizable:false});

    });
  	});
</script>

<div class="page">
	<div id="businessTypeParam_pageHeader" class="pageHeader">
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
							<button type="button" onclick="propertyFilterSearch(businessTypeParam_search_form,businessTypeParam_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="businessTypeParam_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessTypeParam_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="businessTypeParam_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="businessTypeParam_gridtable_div" tablecontainer="businessTypeTabsContent" extraHeight="118" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="businessTypeParam_gridtable_navTabId" value="${sessionScope.navTabId}">
			<input type="hidden" id="businessTypeParam_gridtable_businessId" value="${businessId}" />
			<label style="display: none" id="businessTypeParam_gridtable_addTile">
				<s:text name="businessTypeParamNew.title"/>
			</label> 
			<label style="display: none"
				id="businessTypeParam_gridtable_editTile">
				<s:text name="businessTypeParamEdit.title"/>
			</label>
			<div id="load_businessTypeParam_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="businessTypeParam_gridtable"></table>
			<!--<div id="businessTypeParamPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="businessTypeParam_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessTypeParam_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="businessTypeParam_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>