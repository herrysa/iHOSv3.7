
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzItemCheckLayout;
	var gzItemCheckGridIdString="#gzItemCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var gzItemCheckGrid = jQuery(gzItemCheckGridIdString);
		var urlString = "gzItemGridList?filter_EQS_gzType.gzTypeId=XT&filter_EQB_sysField=0";
		urlString += "&filter_SQS_itemCode=itemCode NOT IN (SELECT itemCode FROM gz_gzItem  WHERE gzTypeId = '${gzTypeId}')";
    	gzItemCheckGrid.jqGrid({
    		url : urlString,
    		editurl:"gzItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'itemId',index:'itemId',align:'center',label : '<s:text name="gzItem.itemId" />',hidden:true,key:true},
{name:'sn',index:'sn',align:'right',width:'50px',label : '<s:text name="gzItem.sn" />',hidden:false},
{name:'itemName',index:'itemName',align:'left',width:'100px',label : '<s:text name="gzItem.itemName" />',hidden:false},
{name:'itemShowName',index:'itemShowName',align:'left',width:'100px',label : '<s:text name="gzItem.itemShowName" />',hidden:false},
{name:'itemCode',index:'itemCode',align:'left',width:'100px',label : '<s:text name="gzItem.itemCode" />',hidden:false},
{name:'gzType.gzTypeId',index:'gzType.gzTypeId',align:'center',width:'70px',label : '<s:text name="gzItem.gzTypeId" />',hidden:true},
{name:'itemType',index:'itemType',align:'center',width:'70px',label : '<s:text name="gzItem.itemType" />',hidden:false,formatter: "select", editoptions:{value:"0:数值型;1:字符型;2:日期型;3:整型"}},
{name:'itemLength',index:'itemLength',align:'right',width:'60px',label : '<s:text name="gzItem.itemLength" />',hidden:false},
{name:'scale',index:'scale',align:'right',width:'60px',label : '<s:text name="gzItem.scale" />',hidden:false},
{name:'isInherit',index:'isInherit',align:'center',width:'60px',label : '<s:text name="gzItem.isInherit" />',hidden:false,formatter: "checkbox"},
{name:'isTax',index:'isTax',align:'center',width:'60px',label : '<s:text name="gzItem.isTax" />',hidden:false,formatter: "checkbox"},
{name:'calculateType',index:'calculateType',width:'70px',align:'center',label : '<s:text name="gzItem.calculateType" />',hidden:false,formatter: "select", editoptions:{value:"0:手动;1:计算"}},
{name:'warning',index:'warning',align:'center',width:'60px',label : '<s:text name="gzItem.warning" />',hidden:false,formatter: "checkbox"},
{name:'warningType',index:'warningType',align:'center',width:'60px',label : '<s:text name="gzItem.warningType" />',hidden:true},
{name:'warningValue',index:'warningValue',align:'right',width:'70px',label : '<s:text name="gzItem.warningValue" />',hidden:false},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="gzItem.disabled" />',hidden:false,formatter:'checkbox'},
{name:'statistics',index:'statistics',align:'center',width:'50px',label : '<s:text name="gzItem.statistics" />',hidden:false,formatter:'checkbox'},
{name:'gzContentHide',index:'gzContentHide',align:'center',width:'75px',label : '<s:text name="gzItem.gzContentHide" />',hidden:false,formatter:'checkbox'},
// {name:'sysField',index:'sysField',align:'center',width:'50px',label : '<s:text name="gzItem.sysField" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="gzItem.remark" />',hidden:false}
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
        	sortorder: '',
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
		 	gridComplete:function(){
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
	     	        	var calculateType = ret[i]["calculateType"];
	     	        	if(calculateType == '1'){
	     	        		setCellText(this,id,'calculateType','<p style="color:blue">计算</p>');       
	     	        	}
// 	     		   		setCellText(this,id,'itemName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewGzItemList(\''+snapId+'\');">'+ret[i]["itemName"]+'</a>'); 
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
           	var dataTest = {"id":"gzItemCheck_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 
    	});
    jQuery(gzItemCheckGrid).jqGrid('bindKeys');
    /*确定*/
	jQuery("#gzItemCheck_gridtable_add_custom").bind("click",function(){
		var sid = jQuery("#gzItemCheck_gridtable").jqGrid('getGridParam','selarrrow');
		var confirmMessage = "";
		if("${entityIsNew}" == "true"){
			if (sid == null || sid.length == 0) {
				sid = "";
			}
			confirmMessage = "首次添加时，系统工资项自动添加到该工资类别中,";
		}else{
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录");
	    		 return;
			}
		}
		
		jQuery.ajax({
		    url: "checkAddGzItem?id="+sid+"&gzTypeId=${gzTypeId}",
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		    	if(data.statusCode != 200){
		    		 alertMsg.error(data.message);
		    		 return;
		    	}else{
		    		alertMsg.confirm(confirmMessage + "确认添加?", {
		    			okCall : function() {
		    				jQuery.ajax({
		    				    url: "addGzItem?id="+sid+"&gzTypeId=${gzTypeId}&navTabId=${navTabId}",
		    				    type: 'post',
		    				    dataType: 'json',
		    				    aysnc:false,
		    				    error: function(data){
		    				    },
		    				    success: function(data){
		    				        	formCallBack(data);
		    				        	if(data.statusCode == 200){
		    				    			var itemId = data.itemId;
		    				    			if(itemId){
		    				    				setTimeout(function(){
		    				    					jQuery("#${navTabId}").jqGrid('setSelection',itemId);
		    				    					}, 500);
		    				    			}
		    				    		}
		    				        	$.pdialog.closeCurrent();
		    				    }
		    				});
		    			}
		    		});
		    	}
		    }
		});
	});
    /*关闭*/
	jQuery("#gzItemCheck_gridtable_cancel_custom").bind("click",function(){
		$.pdialog.closeCurrent();
	});
  	});
</script>

<div class="page" id="gzItemCheck_page">
	<div id="gzItemCheck_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzItemCheck_search_form" class="queryarea-form">
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
							<button type="button" onclick="propertyFilterSearch('gzItemCheck_search_form','gzItemCheck_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="gzItemCheck_buttonBar">
			<ul class="toolBar" id="gzItemCheck_toolbuttonbar">
				<li>
					<a id="gzItemCheck_gridtable_add_custom" class="addbutton" href="javaScript:" ><span>确定</span></a>
				</li>
				<li>
					<a id="gzItemCheck_gridtable_cancel_custom" class="closebutton"  href="javaScript:"><span><s:text name="button.close" /></span></a>
				</li>
			</ul>
		</div>
		<div id="gzItemCheck_gridtable_div" layoutH="108" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzItemCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzItemCheck_gridtable_addTile">
				<s:text name="gzItemCheckNew.title"/>
			</label> 
			<label style="display: none"
				id="gzItemCheck_gridtable_editTile">
				<s:text name="gzItemCheckEdit.title"/>
			</label>
			<div id="load_gzItemCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzItemCheck_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="gzItemCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzItemCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzItemCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="gzItemCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>