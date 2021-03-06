
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
    //取出当前的工资类别
    var gzItemSetCurGzTypeId = 'XT';
	var gzItemSetLayout;
	var gzItemSetGridIdString="#gzItemSet_gridtable";	
	jQuery(document).ready(function() {
		var gzItemSetChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				gzItemSetLayout.closeSouth();
				return;
			}
    		jQuery("#gzItemSetFormula").load("gzItemSetFormula?itemId="+selectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	gzItemSetLayout = makeLayout({'baseName':'gzItemSet',
    		'tableIds':'gzItemSet_gridtable;gzItemSetFormula_gridtable',
    		'activeGridTable':'gzItemSet_gridtable',
    		'proportion':2,
    		'key':'itemId'},
    		gzItemSetChangeData);
    	var initFlag = 0; 
		var gzItemSetGrid = jQuery(gzItemSetGridIdString);
    	gzItemSetGrid.jqGrid({
    		url : "gzItemGridList?gzTypeId="+gzItemSetCurGzTypeId,
    		editurl:"gzItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'itemId',index:'itemId',align:'center',label : '<s:text name="gzItem.itemId" />',hidden:true,key:true},
{name:'sn',index:'sn',align:'right',width:'50px',label : '<s:text name="gzItem.sn" />',hidden:false,highsearch:true},
{name:'itemName',index:'itemName',align:'left',width:'100px',label : '<s:text name="gzItem.itemName" />',hidden:false,highsearch:true},
{name:'itemShowName',index:'itemShowName',align:'left',width:'100px',label : '<s:text name="gzItem.itemShowName" />',hidden:false,highsearch:true},
{name:'itemCode',index:'itemCode',align:'left',width:'100px',label : '<s:text name="gzItem.itemCode" />',hidden:false,highsearch:true},
// {name:'gzType.gzTypeName',index:'gzType.gzTypeName',align:'center',width:'70px',label : '<s:text name="gzItem.gzTypeId" />',hidden:false},
{name:'gzType.gzTypeId',index:'gzType.gzTypeId',align:'center',width:'70px',label : '<s:text name="gzItem.gzTypeId" />',hidden:true},
{name:'itemType',index:'itemType',align:'center',width:'70px',label : '<s:text name="gzItem.itemType" />',hidden:false,formatter: "select", editoptions:{value:"0:数值型;1:字符型;2:日期型;3:整型"},highsearch:true},
{name:'itemLength',index:'itemLength',align:'right',width:'60px',label : '<s:text name="gzItem.itemLength" />',hidden:false,highsearch:true},
{name:'scale',index:'scale',align:'right',width:'60px',label : '<s:text name="gzItem.scale" />',hidden:false,highsearch:true},
{name:'isInherit',index:'isInherit',align:'center',width:'60px',label : '<s:text name="gzItem.isInherit" />',hidden:false,formatter: "checkbox",highsearch:true},
{name:'isTax',index:'isTax',align:'center',width:'60px',label : '<s:text name="gzItem.isTax" />',hidden:false,formatter: "checkbox",highsearch:true},
{name:'calculateType',index:'calculateType',width:'70px',align:'center',label : '<s:text name="gzItem.calculateType" />',hidden:false,formatter: "select", editoptions:{value:"0:手动;1:计算"},highsearch:true},
{name:'warning',index:'warning',align:'center',width:'60px',label : '<s:text name="gzItem.warning" />',hidden:false,formatter: "checkbox",highsearch:true},
{name:'warningType',index:'warningType',align:'center',width:'60px',label : '<s:text name="gzItem.warningType" />',hidden:true,highsearch:true},
{name:'warningValue',index:'warningValue',align:'right',width:'70px',label : '<s:text name="gzItem.warningValue" />',hidden:false,highsearch:true},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="gzItem.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'statistics',index:'statistics',align:'center',width:'50px',label : '<s:text name="gzItem.statistics" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'gzContentHide',index:'gzContentHide',align:'center',width:'75px',label : '<s:text name="gzItem.gzContentHide" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sysField',index:'sysField',align:'center',width:'50px',label : '<s:text name="gzItem.sysField" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'isUsed',index:'isUsed',align:'center',width:'50px',label : '<s:text name="gzItem.isUsed" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="gzItem.remark" />',hidden:false,highsearch:true}
],
        	jsonReader : {
				root : "gzItems", // (2)
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
				gzItemSetLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		gzItemSetLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
		 		gridContainerResize('gzItemSet','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum <= 0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }else{
	            	var rowIds = jQuery(this).getDataIDs();
	     	        var ret = jQuery(this).jqGrid('getRowData');
	     	        var id = '';
	     	        for(var i = 0;i < rowNum;i++){
	     	        	id = rowIds[i];
	     	        	var snapId = ret[i]["itemId"];
	     	        	var sysField = ret[i]["sysField"];
	     	        	if(sysField == "Yes"){
	     	        		var trTemp = this.rows.namedItem(id);
	     	                if (trTemp) {
	     	                	jQuery(trTemp).addClass("gzItemChangeColor");//系统项改变背景颜色
	     	                }
	     	        	}
	     	        	var calculateType = ret[i]["calculateType"];
	     	        	if(calculateType == '1'){
	     	        		setCellText(this,id,'calculateType','<p style="color:blue">计算</p>');       
	     	        	}
	     		   		setCellText(this,id,'itemName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewGzItemSetList(\''+snapId+'\');">'+ret[i]["itemName"]+'</a>'); 
	     		   		var warning = ret[i]["warning"];
	     		   		if(warning == "Yes"){
	     		   			var warningType = ret[i]["warningType"]+""; 
	     		   			var warningValue = ret[i]["warningValue"]; 
	     		   			if(warningType&&warningValue){
	     		   				switch(warningType){
	     		   					case '1':
	     		   						warningValue = '>' + warningValue;
	     		   						break;
	     		   					case '2':
	     		   						warningValue = '≥' + warningValue;
	     		   						break;
	     		   					case '3':
     		   							warningValue = '<' + warningValue;
     		   							break;
	     		   					case '4':
 		   								warningValue = '≤' + warningValue;
 		   								break;
	     		   				}
	     		   				setCellText(this,id,'warningValue',warningValue);   
	     		   			}else{
	     		   				setCellText(this,id,'warningValue','');   
	     		   			}
	     		   		}else{
	     		   		setCellText(this,id,'warningValue','');   
	     		   		}
	     	        }
	            }
           	var dataTest = {"id":"gzItemSet_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	    initFlag = initColumn('gzItemSet_gridtable','com.huge.ihos.gz.gzItem.model.GzItem',initFlag);
       		} 
    	});
    jQuery(gzItemSetGrid).jqGrid('bindKeys');
    
    /*公式按钮start*/
    /*公式添加*/
    jQuery("#gzItemSetFormula_gridtable_add_custom").bind("click",function(){
    	 var sid = jQuery("#gzItemSet_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条工资项目记录！");
 			return;
 		}else{
 			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#gzItemSet_gridtable").jqGrid('getRowData',rowId);
				if(row['calculateType'] != '1'){
					alertMsg.error("只有计算类型的才能添加公式！");
					return;
				}
				if(row['itemType'] == '1'||row['itemType'] == '2'){
					alertMsg.error("只有数值或者整型的项目才能添加公式！");
					return;
				}
			}
 			var winTitle='<s:text name="gzItemFormulaNew.title"/>';
 	 		var url = "editGzItemFormula?popup=true&itemId="+sid+"&navTabId=gzItemSetFormula_gridtable";
 	 		url = encodeURI(url);
 	 		$.pdialog.open(url,'dialogGzItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
 		}
    });
    /*公式修改*/
    jQuery("#gzItemSetFormula_gridtable_edit_custom").bind("click",function(){
    	var sid = jQuery("#gzItemSetFormula_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录！");
 			return;
 		}
        var winTitle='<s:text name="gzItemFormulaEdit.title"/>';
 		var url = "editGzItemFormula?popup=true&formulaId="+sid+"&navTabId=gzItemSetFormula_gridtable";
 		url = encodeURI(url);
 		$.pdialog.open(url,'dialogGzItemFormula',winTitle, {mask:true,resizable:false,maxable:false,width : 700,height : 550});
    });
    /*公式删除*/
    jQuery("#gzItemSetFormula_gridtable_del_custom").bind("click",function(){
    	var url = "gzItemFormulaGridEdit?oper=del";
    	 var sid = jQuery("#gzItemSetFormula_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null){       	
 			alertMsg.error("请选择记录！");
 			return;
 		}
         url = url+"&id="+sid+"&navTabId=gzItemSetFormula_gridtable";
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
    /*工资项删除*/
    jQuery("#gzItemSet_gridtable_del_custom").bind("click",function(){
    	var url = "${ctx}/gzItemGridEdit?oper=delItemSet"
    		var sid = jQuery("#gzItemSet_gridtable").jqGrid('getGridParam','selarrrow');
    		if (sid == null || sid.length == 0) {
    			alertMsg.error("请选择记录！");
    			return;
    		} else {
    			for(var i = 0;i < sid.length; i++){
    				var rowId = sid[i];
    				var row = jQuery("#gzItemSet_gridtable").jqGrid('getRowData',rowId);
    				if(row['sysField'] !='No'){
    					alertMsg.error("系统工资项不能删除！");
    					return;
    				}
    				if(row['isUsed'] !='No'){
    					alertMsg.error("已使用的工资项不能删除！");
    					return;
    				}
    			}
    			url = url+"&id="+sid+"&navTabId=gzItemSet_gridtable";
    			url = encodeURI(url);
    			jQuery.ajax({
    			    url: "checkDelGzItem?id="+sid,
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
    /*添加工资项*/
	jQuery("#gzItemSet_gridtable_add_custom").bind("click",function(){
		var winTitle = '<s:text name="gzItemNew.title"/>';
		var url = "editGzItem?popup=true&navTabId=gzItemSet_gridtable&gzTypeId="+gzItemSetCurGzTypeId;
		url = encodeURI(url);
		$.pdialog.open(url,'addGzItemGrid',winTitle, {mask:true,width : 695,height : 400,maxable:true,resizable:true});
	});
	/*修改工资项*/
	jQuery("#gzItemSet_gridtable_change_custom").bind("click",function(){
		var sid = jQuery("#gzItemSet_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null||sid.length!=1){       	
			alertMsg.error("请选择一条记录！");
			return;
		}
        var winTitle = '<s:text name="gzItemEdit.title"/>';
    	var url = "editGzItem?itemId="+sid+"&popup=true&navTabId=gzItemSet_gridtable&oper=update";
    	url = encodeURI(url);
    	$.pdialog.open(url,'viewGzItem_'+sid,winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true});
	});
    //重新排序sn
    jQuery("#gzItemSet_gridtable_resort_custom").bind("click",function(){
    	var ret = jQuery("#gzItemSet_gridtable").jqGrid('getRowData');
    	if(!ret||ret.length<=0){
    		alertMsg.error("没有需要生成序号的数据！");
    		return;
    	}
    	var gzTypeId = ret[0]["gzType.gzTypeId"];
    	$.post("resortGzItemSn?gzTypeId="+gzTypeId,null,function(data){
    		if(data.message=="1"){
    			alertMsg.correct("重新生成序号成功！");
    			jQuery(gzItemSetGridIdString).trigger("reloadGrid");
    		}
    	});
    });
    /*排序*/
    jQuery("#gzItemSet_gridtable_sort_custom").bind("click",function(){
    	if(!gzItemSetCurGzTypeId){
			alertMsg.error("请选择工资类别后再进行排序！");
			return
		}
		var winTitle = '<s:text name="gzItemSort.title"/>';
		var url = "sortGzItem?popup=true&navTabId=gzItemSet_gridtable&gzTypeId="+gzItemSetCurGzTypeId;
		url = encodeURI(url);
		$.pdialog.open(url,'sortGzItemGrid',winTitle, {mask:true,width : 650,height : 400,maxable:true,resizable:true});
    });
    //批量修改
    jQuery("#gzItemSet_gridtable_batchEdit_custom").click(function(){
    	 var sid = jQuery("#gzItemSet_gridtable").jqGrid('getGridParam','selarrrow');
    	 if(sid==null){       	
  			alertMsg.error("请选择记录！");
  			return;
  		 }
         var url = "batchEditList?navTabId=gzItemSet_gridtable&tableCode=gz_gzItem&tableKey=itemId";
//          var nisStr = "statisticsFlag";
//          url += "&filterStr=filter_NIS_fieldCode="+nisStr;
         url = encodeURI(url);
         var winTitle = '批量修改';
         $.pdialog.open(url,'batchEdit',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
    });
});
    //查询
    function gzItemSetSearchFormLoad(){
    	propertyFilterSearch('gzItemSet_search_form','gzItemSet_gridtable');
    }
    /*查看工资项*/
    function viewGzItemSetList(id){
    	var winTitle = '<s:text name="gzItemView.title"/>';
    	var url = "editGzItem?itemId="+id+"&popup=true&navTabId=gzItemSet_gridtable&oper=view";
    	url = encodeURI(url);
    	$.pdialog.open(url,'viewGzItem_'+id,'查看工资项信息', {mask:true,width : 650,height : 400,maxable:true,resizable:true});      	
    }
</script>
<style>
.gzItemChangeColor{
	background: #F0F0F0;
}
</style>
<div class="page">
	<div id="gzItemSet_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzItemSet_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='gzItem.itemName'/>:
						<input type="text" name="filter_LIKES_itemName" style="width:100px"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItem.itemShowName'/>:
						<input type="text" name="filter_LIKES_itemShowName" style="width:100px"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItem.itemCode'/>:
						<input type="text" name="filter_LIKES_itemCode" style="width:100px"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItem.isTax'/>:
						<s:select name='filter_EQB_isTax' headerKey=""  style="font-size:12px"
							list="#{'':'--','1':'是','0':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label" >
					   <s:text name='gzItem.calculateType'/>:
				         <s:select name='filter_EQS_calculateType' headerKey=""   style="font-size:12px"
							list="#{'':'--','0':'手动','1':'计算'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label" >
					     <s:text name='gzItem.disabled'/>:
					   <s:select name='filter_EQB_disabled' headerKey=""   style="font-size:12px"
							list="#{'':'--','1':'是','0':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label" >
					     <s:text name='gzItem.statistics'/>:
					   <s:select name='filter_EQB_statistics' headerKey=""   style="font-size:12px"
							list="#{'':'--','1':'是','0':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItem.gzContentHide'/>:
						<s:select name='filter_EQB_gzContentHide' headerKey=""  style="font-size:12px"
							list="#{'':'--','1':'是','0':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItem.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="gzItemSetSearchFormLoad()"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="gzItemSet_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="gzItemSet_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="gzItemSet_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="gzItemSet_gridtable_change_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a id="gzItemSet_gridtable_sort_custom" class="changebutton"  href="javaScript:"><span>排序</span></a>
				</li>
				<li>
					<a id="gzItemSet_gridtable_resort_custom" class="logoutbutton"  href="javaScript:"><span>重新生成序号</span></a>
				</li>
				<li>
                    <a id="gzItemSet_gridtable_batchEdit_custom" class="getdatabutton"><span>批量修改</span></a>
                </li>  
				<li>
                    <a class="settlebutton"  href="javaScript:setColShow('gzItemSet_gridtable','com.huge.ihos.gz.gzItem.model.GzItem')"><span><s:text name="button.setColShow" /></span></a>
                </li> 
			    <li style="float:right">
					<a class="particularbutton" href="javaScript:gzItemSetLayout.optDblclick();"><span>公式列表</span> </a>
				</li>
			</ul>
		</div>
		<div id="gzItemSet_container">
					<div id="gzItemSet_layout-center" class="pane ui-layout-center">		
		<div id="gzItemSet_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzItemSet_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzItemSet_gridtable_addTile">
				<s:text name="gzItemNew.title"/>
			</label> 
			<label style="display: none"
				id="gzItemSet_gridtable_editTile">
				<s:text name="gzItemEdit.title"/>
			</label>
			<div id="load_gzItemSet_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
 			<table id="gzItemSet_gridtable"></table>
		</div>
	<div class="panelBar" id="gzItemSet_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzItemSet_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzItemSet_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="gzItemSet_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>
		</div>
	</div>
	<div id="gzItemSet_layout-south" class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
								<li>
									<a id="gzItemSetFormula_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
								</li>
								<li>
									<a id="gzItemSetFormula_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
								</li>
								<li>
									<a id="gzItemSetFormula_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
								</li>
								<li style="float: right;">
									<a id="gzItemSet_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="gzItemSet_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="gzItemSet_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div id="gzItemSetFormula"></div>
					</div>
				</div>
	</div>
</div>