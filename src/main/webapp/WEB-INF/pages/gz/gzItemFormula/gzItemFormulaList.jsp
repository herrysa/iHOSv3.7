
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzItemFormulaGridIdString="#gzItemFormula_gridtable";
	var gzItemFormulaLayout;
	jQuery(document).ready(function() { 
		var gzItemFormulaFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#gzItemFormula_container").css("height",gzItemFormulaFullSize);
		var gzItemFormulaChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				gzItemFormulaLayout.closeSouth();
				return;
			}
    		jQuery("#gzItemFormulaDetail").load("gzItemFormulaDetailList?itemId="+selectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	gzItemFormulaLayout = makeLayout({'baseName':'gzItemFormulaDetail',
    		'tableIds':'gzItemFormula_gridtable;gzItemFormulaDetail_gridtable',
    		'activeGridTable':'gzItemFormula_gridtable',
    		'fullSize':120,
    		'proportion':2,
    		'key':'itemId'},
    		gzItemFormulaChangeData);
		var gzItemGrid = jQuery(gzItemFormulaGridIdString);
    	gzItemGrid.jqGrid({
    		url : "gzItemForFormulaGridList",
    		editurl:"gzItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'itemId',index:'itemId',align:'center',label : '<s:text name="gzItem.itemId" />',hidden:true,key:true},
{name:'sn',index:'sn',align:'right',width:'50px',label : '<s:text name="gzItem.sn" />',hidden:true},
{name:'itemName',index:'itemName',align:'left',width:'100px',label : '<s:text name="gzItem.itemName" />',hidden:false},
{name:'gzTypeId',index:'gzTypeId',align:'center',width:'80px',label : '<s:text name="gzItem.gzTypeId" />',hidden:false,formatter:function (cellvalue, options, rowObject){if(cellvalue=='0'){return "普通员工"} return '高级员工'}},
{name:'itemType',index:'itemType',align:'left',width:'80px',label : '<s:text name="gzItem.itemType" />',hidden:false,formatter: "select", editoptions:{value:"0:数值型;1:字符型;2:日期型"}},
{name:'itemLength',index:'itemLength',align:'right',width:'50px',label : '<s:text name="gzItem.itemLength" />',hidden:false},
{name:'fullTaxCost',index:'fullTaxCost',align:'right',width:'50px',label : '<s:text name="gzItem.fullTaxCost" />',hidden:false},
{name:'isInherit',index:'isInherit',align:'left',width:'80px',label : '<s:text name="gzItem.isInherit" />',hidden:false,formatter: "select", editoptions:{value:"0:继承项;1:非继承项"}},
{name:'isTax',index:'isTax',align:'left',width:'80px',label : '<s:text name="gzItem.isTax" />',hidden:false,formatter: "select", editoptions:{value:"0:扣税项;1:非扣税项"}},
{name:'calculateType',index:'calculateType',width:'80px',align:'center',label : '<s:text name="gzItem.calculateType" />',hidden:false,formatter: "select", editoptions:{value:"0:手动;1:计算"}},
{name:'warning',index:'warning',align:'center',width:'50px',label : '<s:text name="gzItem.warning" />',hidden:false,formatter: "select", editoptions:{value:"0:是;1:否"}},
{name:'warningValue',index:'warningValue',align:'right',width:'80px',label : '<s:text name="gzItem.warningValue" />',hidden:false},
{name:'remark',index:'remark',align:'left',label : '<s:text name="gzItem.remark" />',hidden:false},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="gzItem.disabled" />',hidden:false,formatter:'checkbox'} ],
        	jsonReader : {
				root : "gzItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'itemId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="gzItemList.title" />',
        	height:'100%',
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				gzItemFormulaLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		gzItemFormulaLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
		 	var rowNum = jQuery(this).getDataIDs().length;
	        var rowIds = jQuery(this).getDataIDs();
	        var ret = jQuery(this).jqGrid('getRowData');
	        var id='';
	        for(var i=0;i<rowNum;i++){
	        	id=rowIds[i];
		   		setCellText(this,id,'itemName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewGzItemList(\''+ret[i]["itemId"]+'\');">'+ret[i]["itemName"]+'</a>');
	          }
           	var dataTest = {"id":"gzItemFormula_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("gzItemFormula_gridtable");
       		} 

    	});
    jQuery(gzItemGrid).jqGrid('bindKeys');
    
    /*公式按钮start*/
    jQuery("#gzItemFormulaDetail_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	 var sid = jQuery("#gzItemFormula_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 			}
        var winTitle='<s:text name="gzItemFormulaNew.title"/>';
 		var url = "editGzItemFormula?popup=true&itemId="+sid+"&navTabId=gzItemFormulaDetail_gridtable";
 		url = encodeURI(url);
 		$.pdialog.open(url,'editGzItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
    });
    jQuery("#gzItemFormulaDetail_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
    	 var sid = jQuery("#gzItemFormulaDetail_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 			}
        var winTitle='<s:text name="gzItemFormulaEdit.title"/>';
 		var url = "editGzItemFormula?popup=true&formulaId="+sid+"&navTabId=gzItemFormulaDetail_gridtable";
 		url = encodeURI(url);
 		$.pdialog.open(url,'editGzItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
    });
    jQuery("#gzItemFormulaDetail_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
    	var url = "gzItemFormulaGridEdit?oper=del";
    	 var sid = jQuery("#gzItemFormulaDetail_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null){       	
 			alertMsg.error("请选择记录。");
 			return;
 		}
         url = url+"&id="+sid+"&navTabId=gzItemFormulaDetail_gridtable";
         url = encodeURI(url);
         alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
         });
    });
    /*公式按钮end*/  
  	});
</script>

<div class="page">
	<div id="gzItemFormula_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzItemFormula_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='gzItem.itemName'/>:
						<input type="text" name="filter_LIKE_itemName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						 <s:text name='gzItem.gzTypeId'/>:
						 <s:select name='filter_EQS_gzTypeId' headerKey=""   
							list="#{'0':'普通员工','1':'高级员工'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				     </s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='gzItem.isTax'/>:
						<s:select name='filter_EQS_isTax' headerKey=""   
							list="#{'':'--','0':'扣税项','1':'非扣税项'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
					     <s:text name='gzItem.disabled'/>:
					   <s:select name='filter_EQB_disabled' headerKey=""   
							list="#{'':'--','false':'启用','true':'停用'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('gzItemFormula_search_form','gzItemForFormula_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('gzItemFormula_search_form','gzItemForFormula_gridtable')"><s:text name='button.search'/></button>
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
			<li style="float:right">
					<a class="particularbutton" href="javaScript:gzItemFormulaLayout.optDblclick();"><span>公式列表</span> </a>
			</li>
			</ul>
		</div>
		<div id="gzItemFormula_container">
		<div id="gzItemFormulaDetail_container">
					<div id="gzItemFormulaDetail_layout-center" class="pane ui-layout-center">
		<div id="gzItemFormula_gridtable_div" layoutH="124" class="unitBox"  class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzItemFormula_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzItemFormula_gridtable_addTile">
				<s:text name="gzItemNew.title"/>
			</label> 
			<label style="display: none"
				id="gzItemFormula_gridtable_editTile">
				<s:text name="gzItemEdit.title"/>
			</label>
			<div id="load_gzItemFormula_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzItemFormula_gridtable"></table>
			<!--<div id="gzItemPager"></div>-->
		</div>
		<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzItemFormula_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzItemFormula_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="gzItemFormula_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
		</div>
	</div>
		<div id="gzItemFormulaDetail_layout-south" class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
<!-- 								<li> -->
<%-- 									<a class="settlebutton"  href="javaScript:setColShow('pactAccountPDetail_gridtable','com.huge.ihos.hr.pact.model.PactAccountDetail')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 								</li> -->
								<li>
									<a id="gzItemFormulaDetail_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
								</li>
								<li>
									<a id="gzItemFormulaDetail_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
								</li>
								<li>
									<a id="gzItemFormulaDetail_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
								</li>
								<li style="float: right;">
									<a id="gzItemFormulaDetail_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="gzItemFormulaDetail_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="gzItemFormulaDetail_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div id="gzItemFormulaDetail"></div>
					</div>
					</div>
				</div>
	</div>
</div>