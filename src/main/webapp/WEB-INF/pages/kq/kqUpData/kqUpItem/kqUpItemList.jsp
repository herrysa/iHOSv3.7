
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
		var curKqType = jQuery("#${random}_kqUpItem_kqType").val();
		jQuery("#${random}_curkKqType").val(curKqType);
		var kqUpItemChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				kqUpItemLayout.closeSouth();
				return;
			}
    		jQuery("#${random}_kqUpItemFormula").load("kqUpItemFormulaList?itemId="+selectedSearchId+"&random=${random}");
    		$("#background,#progressBar").hide();
    	};
    	kqUpItemLayout = makeLayout({'baseName':'${random}_kqUpItem',
    		'tableIds':'${random}_kqUpItem_gridtable;${random}_kqUpItemFormula_gridtable',
    		'activeGridTable':'${random}_kqUpItem_gridtable',
    		'proportion':2,
    		'key':'itemId'},
    		kqUpItemChangeData);
		
    	var initFlag = 0; 
		var kqUpItemGrid = jQuery("#${random}_kqUpItem_gridtable");
    	kqUpItemGrid.jqGrid({
    		url : "kqUpItemGridList?kqTypeId="+curKqType,
    		editurl:"kqUpItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'itemId',index:'itemId',align:'center',label : '<s:text name="kqUpItem.itemId" />',hidden:true,key:true},
{name:'sn',index:'sn',align:'right',width:'50px',label : '<s:text name="kqUpItem.sn" />',hidden:false,highsearch:true},
{name:'itemName',index:'itemName',align:'left',width:'100px',label : '<s:text name="kqUpItem.itemName" />',hidden:false,highsearch:true},
{name:'itemCode',index:'itemCode',align:'left',width:'100px',label : '<s:text name="kqUpItem.itemCode" />',hidden:false,highsearch:true},
{name:'kqType.kqTypeName',index:'kqType.kqTypeName',align:'center',width:'70px',label : '<s:text name="kqUpItem.kqTypeId" />',hidden:false,highsearch:true},
{name:'kqType.kqTypeId',index:'kqType.kqTypeId',align:'center',width:'70px',label : '<s:text name="kqUpItem.kqTypeId" />',hidden:true},
{name:'itemType',index:'itemType',align:'center',width:'70px',label : '<s:text name="kqUpItem.itemType" />',hidden:false,formatter: "select", editoptions:{value:"0:数值型;1:字符型;2:日期型;3:整型"},highsearch:true},
{name:'itemLength',index:'itemLength',align:'right',width:'60px',label : '<s:text name="kqUpItem.itemLength" />',hidden:false,highsearch:true},
{name:'scale',index:'scale',align:'right',width:'60px',label : '<s:text name="kqUpItem.scale" />',hidden:false,highsearch:true},
{name:'isInherit',index:'isInherit',align:'center',width:'60px',label : '<s:text name="kqUpItem.isInherit" />',hidden:true,formatter: "checkbox"},
{name:'calculateType',index:'calculateType',width:'70px',align:'center',label : '<s:text name="kqUpItem.calculateType" />',hidden:false,formatter: "select", editoptions:{value:"0:手动;1:计算"},highsearch:true},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="kqUpItem.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'kqUpDataHide',index:'kqUpDataHide',align:'center',width:'75px',label : '<s:text name="kqUpItem.kqUpDataHide" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sysField',index:'sysField',align:'center',width:'50px',label : '<s:text name="kqUpItem.sysField" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'showType',index:'showType',align:'left',width:'100px',label : '<s:text name="kqUpItem.showType" />',hidden:true},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="kqUpItem.remark" />',hidden:false,highsearch:true}
],
        	jsonReader : {
				root : "kqUpItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sn',
        	viewrecords: true,
        	sortorder: 'asc',
        	rowNum:'100',
        	//caption:'<s:text name="kqUpItemList.title" />',
        	height:'100%',
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				kqUpItemLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		kqUpItemLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
		 		/*2015.08.27 form search change*/
		 		gridContainerResize('${random}_kqUpItem','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }else{
	            	var rowIds = jQuery(this).getDataIDs();
	     	        var ret = jQuery(this).jqGrid('getRowData');
	     	        var id='';
	     	        for(var i=0;i<rowNum;i++){
	     	        	id=rowIds[i];
	     	        	var itemId = ret[i]["itemId"];
	     		   		setCellText(this,id,'itemName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewKqUpItemList(\''+itemId+'\');">'+ret[i]["itemName"]+'</a>'); 
	     		   		var sysField = ret[i]["sysField"];
    	        		if(sysField == "Yes"){
    	        			var trTemp = this.rows.namedItem(id);
    	                	if (trTemp) {
    	                		jQuery(trTemp).addClass("kqItemChangeColor");//系统项改变背景颜色
    	               	 	}
    	        		}
	     	        }
	            }
           	var dataTest = {"id":"${random}_kqUpItem_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("kqUpItem_gridtable");
      	  	initFlag = initColumn('${random}_kqUpItem_gridtable','com.huge.ihos.kq.kqUpData.model.KqUpItem',initFlag);
       		} 
    	});
    jQuery(kqUpItemGrid).jqGrid('bindKeys');
    
    /*公式按钮start*/
    /*公式添加*/
    jQuery("#${random}_kqUpItemFormula_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	var curkKqType = jQuery("#${random}_curkKqType").val();
    	var sid = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条考勤项目记录！");
 			return;
 		}else{
 			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getRowData',rowId);
				if(row['calculateType'] != '1'){
					alertMsg.error("只有计算类型的才能添加公式！");
					return;
				}
				if(row['itemType'] == '1'||row['itemType'] == '2'){
					alertMsg.error("只有数值或者整型的项目才能添加公式！");
					return;
				}
			}
 			var winTitle='<s:text name="kqUpItemFormulaNew.title"/>';
 	 		var url = "editKqUpItemFormula?popup=true&itemId="+sid+"&navTabId=${random}_kqUpItemFormula_gridtable&kqTypeId="+curkKqType;
 	 		$.pdialog.open(url,'editKqUpItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
 		}
    });
    /*公式修改*/
    jQuery("#${random}_kqUpItemFormula_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
    	var curkKqType = jQuery("#${random}_curkKqType").val();
    	var sid = jQuery("#${random}_kqUpItemFormula_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录！");
 			return;
 		}
        var winTitle='<s:text name="kqUpItemFormulaEdit.title"/>';
 		var url = "editKqUpItemFormula?popup=true&formulaId="+sid+"&navTabId=${random}_kqUpItemFormula_gridtable&kqTypeId="+curkKqType;
 		$.pdialog.open(url,'editKqUpItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
    });
    /*公式删除*/
    jQuery("#${random}_kqUpItemFormula_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
    	var url = "kqUpItemFormulaGridEdit?oper=del";
    	 var sid = jQuery("#${random}_kqUpItemFormula_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null){       	
 			alertMsg.error("请选择记录！");
 			return;
 		}else{
 			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#${random}_kqUpItemFormula_gridtable").jqGrid('getRowData',rowId);
				if(row['inUsed'] !='No'){
					alertMsg.error("已使用的公式不能删除！");
					return;
				}
			}
 		}
         url = url+"&id="+sid+"&navTabId=${random}_kqUpItemFormula_gridtable";
         alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
         });
    });
    /*公式按钮end*/ 
    //考勤项添加
     jQuery("#${random}_kqUpItem_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	var curkKqType = jQuery("#${random}_curkKqType").val();
    	 if(!curkKqType){
 			alertMsg.error("请选择考勤类别后再添加考勤项！");
 			return
 		}
 		var winTitle = '<s:text name="kqUpItemNew.title"/>';
 		var url = "editKqUpItem?popup=true&navTabId=${random}_kqUpItem_gridtable&kqTypeId="+curkKqType;
 		$.pdialog.open(url,'addKqUpItemGrid',winTitle, {mask:true,width : 695,height : 300,maxable:true,resizable:true});
     });
   //考勤项修改
 	jQuery("#${random}_kqUpItem_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
 		var sid = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null||sid.length!=1){       	
			alertMsg.error("请选择一条记录！");
			return;
		}
        var winTitle = '<s:text name="kqUpItemEdit.title"/>';
    	var url = "editKqUpItem?itemId="+sid+"&popup=true&navTabId=${random}_kqUpItem_gridtable&oper=update";
    	$.pdialog.open(url,'viewkqUpItem_'+sid,winTitle, {mask:true,width : 650,height : 350,maxable:true,resizable:true});
     });
    /*考勤项删除*/
    jQuery("#${random}_kqUpItem_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
    	var curkKqType = jQuery("#${random}_curkKqType").val();
    	var url = "${ctx}/kqUpItemGridEdit?oper=del";
    		var sid = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getGridParam','selarrrow');
    		if (sid == null || sid.length == 0) {
    			alertMsg.error("请选择记录！");
    			return;
    		} else {
    			for(var i = 0;i < sid.length; i++){
    				var rowId = sid[i];
    				var row = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getRowData',rowId);
    				if(row['sysField'] !='No'){
    					alertMsg.error("系统考勤项不能删除！");
    					return;
    				}
    				if(row['showType'] =='kqItem'){
    					alertMsg.error("考勤基础项不能删除！");
    					return;
    				}
    			}
    			url = url+"&id="+sid+"&navTabId=${random}_kqUpItem_gridtable";
    			jQuery.ajax({
    			    url: "checkDelKqUpItem?id="+sid+"&kqTypeId="+curkKqType,
    			    type: 'post',
    			    dataType: 'json',
    			    aysnc:false,
    			    error: function(data){
    			    },
    			    success: function(data){
    			        if(data){
    			        	alertMsg.error(data.message);
    						return;
    			        }else{
    			        	alertMsg.confirm("确认删除？", {
    							okCall : function() {
    								$.post(url,function(data) {
    									formCallBack(data);
    								});
    							}
    						});
    					}
    			    }
    			});
    		}
    	});
    /*考勤项排序*/
    jQuery("#${random}_kqUpItem_gridtable_sort_custom").unbind( 'click' ).bind("click",function(){
    	var curkKqType = jQuery("#${random}_curkKqType").val();
    	if(!curkKqType){
			alertMsg.error("请选择考勤类别后再进行排序！");
			return
		}
		var winTitle = '<s:text name="kqUpItemSort.title"/>';
		var url = "sortKqUpItem?popup=true&navTabId=${random}_kqUpItem_gridtable&random=${random}";
		$.pdialog.open(url,'sortKqUpItemGrid',winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true});
    });
    /*考勤项重新生成序号*/
    jQuery("#${random}_kqUpItem_gridtable_resort_custom").unbind( 'click' ).bind("click",function(){
    	var ret = jQuery("#${random}_kqUpItem_gridtable").jqGrid('getRowData');
    	if(!ret||ret.length<=0){
    		alertMsg.error("没有需要生成序号的数据！");
    		return;
    	}
    	var kqTypeId = ret[0]["kqType.kqTypeId"];
    	$.post("resortKqUpItemSn?kqTypeId="+kqTypeId,null,function(data){
    		if(data.message=="1"){
    			alertMsg.correct("重新生成序号成功！");
    			jQuery("#${random}_kqUpItem_gridtable").trigger("reloadGrid");
    		}
    	});
    });
    //查询
    jQuery("#${random}_kqUpItem_searchButton").unbind( 'click' ).bind("click",function(){
       	propertyFilterSearch('${random}_kqUpItem_search_form','${random}_kqUpItem_gridtable');
       	var curKqType = jQuery("#${random}_kqUpItem_kqType").val();
		jQuery("#${random}_curkKqType").val(curKqType);
    });
  	});
    /*查看考勤项*/
    function viewKqUpItemList(id){
    	var winTitle = '<s:text name="kqUpItemView.title"/>';
    	var url = "editKqUpItem?itemId="+id+"&popup=true&oper=view";
    	$.pdialog.open(url,'viewKqUpItem_'+id,'查看考勤项信息', {mask:true,width : 650,height : 350,maxable:true,resizable:true});      	
    }
</script>
<style>
.kqItemChangeColor{
	background: #F0F0F0;
}
</style>
<div class="page">
	<div id="${random}_kqUpItem_pageHeader" class="pageHeader">
	<input id="${random}_curkKqType" type="hidden">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}_kqUpItem_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='kqUpItem.itemName'/>:
						<input type="text" name="filter_LIKES_itemName" style="width:100px"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqUpItem.itemCode'/>:
						<input type="text" name="filter_LIKES_itemCode" style="width:100px"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqUpItem.kqTypeId'/>:
						<select id="${random}_kqUpItem_kqType" name="filter_EQS_kqType.kqTypeId">
							<c:forEach var="kqType" items="${kqTypes}">
								<option value="${kqType.kqTypeId}">${kqType.kqTypeName}</option>
								</c:forEach>
							</select>
				    </label>
					<label class="queryarea-label">
					   <s:text name='kqUpItem.calculateType'/>:
				         <s:select name='filter_EQS_calculateType' headerKey=""   style="font-size:12px"
							list="#{'':'--','0':'手动','1':'计算'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
					     <s:text name='kqUpItem.kqUpDataHide'/>:
					   <s:select name='filter_EQB_kqUpDataHide' headerKey=""   style="font-size:12px"
							list="#{'':'--','true':'是','false':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
					     <s:text name='kqUpItem.disabled'/>:
					   <s:select name='filter_EQB_disabled' headerKey=""   style="font-size:12px"
							list="#{'':'--','true':'是','false':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='kqUpItem.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<s:hidden id="changeFlag" value="0"/>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" id="${random}_kqUpItem_searchButton"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}_kqUpItem_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="${random}_kqUpItem_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="${random}_kqUpItem_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="${random}_kqUpItem_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a id="${random}_kqUpItem_gridtable_sort_custom" class="changebutton"  href="javaScript:"><span>排序</span></a>
				</li>
				<li>
					<a id="${random}_kqUpItem_gridtable_resort_custom" class="logoutbutton"  href="javaScript:"><span>重新生成序号</span></a>
				</li>
				<li>
                    <a class="settlebutton"  href="javaScript:setColShow('${random}_kqUpItem_gridtable','com.huge.ihos.kq.kqUpData.model.KqUpItem')"><span><s:text name="button.setColShow" /></span></a>
                </li> 
			     <li style="float:right">
					<a class="particularbutton" href="javaScript:kqUpItemLayout.optDblclick();"><span>公式列表</span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_kqUpItem_container">
					<div id="${random}_kqUpItem_layout-center" class="pane ui-layout-center">		
		<div id="${random}_kqUpItem_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqUpItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="${random}_load_kqUpItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
 			<table id="${random}_kqUpItem_gridtable"></table>
		</div>
	<div class="panelBar" id="${random}_kqUpItem_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_kqUpItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_kqUpItem_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_kqUpItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
		</div>
	</div>
	<div id="${random}_kqUpItem_layout-south" class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
<!-- 								<li> -->
<%-- 									<a class="settlebutton"  href="javaScript:setColShow('pactAccountP_gridtable','com.huge.ihos.hr.pact.model.PactAccount')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 								</li> -->
								<li>
									<a id="${random}_kqUpItemFormula_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
								</li>
								<li>
									<a id="${random}_kqUpItemFormula_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
								</li>
								<li>
									<a id="${random}_kqUpItemFormula_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
								</li>
								<li style="float: right;">
									<a id="${random}_kqUpItem_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="${random}_kqUpItem_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="${random}_kqUpItem_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div id="${random}_kqUpItemFormula"></div>
					</div>
					<!-- </div> -->
				</div>
	</div>
</div>