<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var pzMapBatchEditGridIdString="#pzMapBatchEdit_gridtable";
	var sqlArr = new Array();
	var treeIdArr = new Array();
	var paramArr = new Array();
	jQuery(document).ready(function() {
		var pzMapBatchEditGrid = jQuery(pzMapBatchEditGridIdString);
		pzMapBatchEditGrid.jqGrid({
	    	url : "getPzMapBatchEditList?businessId=${businessId}&type=${type}",
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'fieldCode',index:'fieldCode',width : 100,align:'left',label : '字段',hidden:true,key:true},
				{name:'dataType',index:'dataType',width : 100,align:'left',label : '数据类型',hidden:true},
				{name:'dataLength',index:'dataLength',width:50,align:'right',label:'数据长度',hidden:true},
				{name:'userTag',index:'userTag',width:50,align:'right',label:'自定义标签',hidden:true},
				{name:'parameter1',index:'parameter1',width : 100,align:'left',label : '参数',hidden:true},
				{name:'parameter2',index:'parameter2',width : 100,align:'left',label : '参数名称',hidden:true},
				{name:'sn',index:'sn',width : 100,align:'left',label : '序号',hidden:true},
				{name:'name',index:'name',width : 100,align:'left',label : '名称',hidden:false,sortable:false},
				{name:'editvalue',index:'name',width : 400,align:'left',label : '修改值',hidden:false,formatter:batchEditFormatter,sortable:false}
	        ],
	        jsonReader : {
				root : "pzMapBatchEditCols", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'sn',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="sysTableKindList.title" />',
	        height:300,
	        rowNum:10000,
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
			   if(treeIdArr){
				   if(paramArr) {
					   $.each(treeIdArr,function(key,val) {
						   jQuery("#"+val).combogrid({
								url : '${ctx}/comboGridSqlList', 
								queryParams : {
									sql:sqlArr[key],
									cloumns : paramArr[key]
								},
								autoFocus : false,
								showOn : true, 
								rows:10,
								width:600,
								sidx:"id",
								colModel : [ {
									'columnName' : 'ID',
									'width' : '40',
									'label' : 'id',
									'align' : 'left',
								}, {
									'columnName' : 'NAME',
									'width' : '40',
									'align' : 'left',
									'label' : '名称'
								}
								],
								_create: function( event, item ) {
									//alert();
								},
								select : function(event, ui) {
									jQuery("#"+val).val(ui.item.NAME);
									jQuery("#"+val+"_id").val(ui.item.ID);
									/* jQuery(elem).val(ui.item.NAME);
									var rowChangedData = changeData${random}${type}[rowId];
									if(rowChangedData==null){
										rowChangedData = {};
									}
									rowChangedData[elemName] = ui.item.NAME;
									elemName = elemName.replace("_name","");
									rowChangedData[elemName] = ui.item.ID;
									changeData${random}${type}[rowId] = rowChangedData; */
									return false;
								}
							});
					   });
				   } else {
					   $.each(treeIdArr, function(key,val){      
							 jQuery("#"+val).treeselect({
								   dataType:"sql",
								   optType:"single",
								   sql:sqlArr[key],
								   exceptnullparent:false,
								   selectParent:false,
								   lazy:false
								});
						  }); 
				   }
			   }
	           var dataTest = {"id":"pzMapBatchEdit_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	       	} 
	    });
	    jQuery(pzMapBatchEditGrid).jqGrid('bindKeys');
	    
	});
	
	function batchEditFormatter(cellvalue, options, rowObject){
		var dataType = rowObject['dataType'];
		var code = rowObject['fieldCode'];
		var userTag = rowObject['userTag'];
		var parameter = rowObject['parameter1'];
		var parameterName = rowObject['parameter2'];
		var dataLength = rowObject['dataLength'];
		var editvalue;
		var mydate = new Date();
		var inputId="batchEdit"+mydate.getTime()+code;
		switch(dataType){
			case '1':
				if(userTag == 'select'){
					editvalue='<select   class="select">';
					editvalue+='<option value =""></option>';
					jQuery.ajax({
 	    		    	url: 'getFiledDataList',
 	    		        data: {code:parameterName},
 	    		        type: 'post',
 	    		        dataType: 'json',
 	    		        async:false,
 	    		        error: function(data){
 	    		        },
 	    		        success: function(data){ 
 	    		        	for(var optionItem = 0; optionItem<data.filedDataByCode.length;optionItem++){
 	    		        		editvalue+=' <option value ='+data.filedDataByCode[optionItem].value+'>'+data.filedDataByCode[optionItem].value+'</option>';
 	    		        	}
    		        	}
 	    		    });
					editvalue += '</select>';
				}else if(userTag == 'treeSelect'){
					editvalue = '<input type="text"  id="'+inputId+'"></input>';
					treeIdArr.push(inputId);
					sqlArr.push(parameter);
					inputId += "_id";
					editvalue += '<input type="hidden"  id="'+inputId+'"></input>';
				}else if(userTag == 'combogrid'){
					editvalue = '<input type="text"  id="'+inputId+'"></input>';
					treeIdArr.push(inputId);
					sqlArr.push(parameter);
					paramArr.push(parameterName);
					inputId += "_id";
					editvalue += '<input type="hidden"  id="'+inputId+'"></input>';
				}else{
					if(!dataLength){
						dataLength=50;
					}
					editvalue = '<input type="text" maxlength="'+dataLength+'" id="'+inputId+'" onblur="lengthCheckForBatchEdit(this,\''+dataLength+'\')"></input>';
				}
				
				break;
			case '2':
				editvalue = '<input id="'+inputId+'"  type="text"  onblur="numberCheckForBatchEdit(this)"></input>';
				break;
			case '5':
				editvalue = '<input id="'+inputId+'" type="text"  onblur="digitCheckForBatchEdit(this)"></input>';
				break;
			case '6':
				editvalue = '<input id="'+inputId+'" type="text"  onblur="digitCheckForBatchEdit(this)"></input>';
				break;
			case '7':
				editvalue = '<input id="'+inputId+'" maxlength="50" type="text"  onblur="lengthCheckForBatchEdit(this,50)"></input>';
				break;
			case '3':
				editvalue = '<input  type="checkbox" name="'+code+'"></input>';
				break;
			case '4':
				editvalue = '<input  style="height:15px" class="Wdate input-small" onClick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" onFocus="WdatePicker({skin:\'ext\',isShowClear:true,readOnly:true})" type="text" ></input>';
				break;
			default:
				editvalue="";
		}
		return editvalue;
	}
	

	function savePzMapBatchEdit(){
		var keySid = jQuery("#"+"${navTabId}").jqGrid('getGridParam','selarrrow');
		var sid = jQuery("#pzMapBatchEdit_gridtable").jqGrid('getGridParam', 'selarrrow');
		if(sid==null|| sid.length == 0){       	
			alertMsg.error("请选择需要保存的记录。");
			return;
		}
		var pzMapData = {};
		var errorMsg;
		var valueData = {};
		for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#pzMapBatchEdit_gridtable").jqGrid('getRowData',rowId);
			var code = row["fieldCode"];
			var dataType = row['dataType'];
			var userTag = row['userTag'];
			var name = row['fieldName'];
			var curInputObj;
			var curInputObjName;
			switch(dataType) {
				case '1' :
					if(userTag == 'select'){
						curInputObj = jQuery("#"+sid[i]).find(".select");
					}else if(userTag == 'treeSelect'){
						curInputObj = jQuery("#"+sid[i]).find("input[type=hidden]");
					} else if(userTag == 'combogrid') {
						curInputObj = jQuery("#"+sid[i]).find("input[type=hidden]");
						curInputObjName = jQuery("#"+sid[i]).find("input[type=text]");
					}else{
						curInputObj = jQuery("#"+sid[i]).find("input[type=text]");
					}
					if(!curInputObj.val()){
						errorMsg += name + "不能为空.";
					} else {
						valueData[code] = curInputObj.val();
						valueData[code+"_name"] = curInputObjName.val();
					}
					break;
				default:
					break;
					
			}
		}
		for(var j = 0;j< keySid.length;j++) {
			var keyId = keySid[j];
			pzMapData[keyId] = valueData;
		}
		if(errorMsg){
			alertMsg.error(errorMsg);
			return;
		}
		var pzMapDataStr = JSON.stringify(pzMapData);
		jQuery("#pzMapBatchEdit_pzMapData").val(pzMapDataStr);
		jQuery("#pzMapBatchEdit_search_form").submit();
	}
	function lengthCheckForBatchEdit(obj,maxlength){
		var value = obj.value;
		var id = obj.id;
		if(value&&value.length>maxlength){
			value = value.substr(0,maxlength);
			jQuery("#"+id).val(value);
			alertMsg.error("输入的内容过长,最大长度为"+maxlength);
		}
	}
	function numberCheckForBatchEdit(obj){
		var value = obj.value;
		var id = obj.id;
		if(value&&parseInt(value)!=value){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入整数");
		}
	}
	function digitCheckForBatchEdit(obj){
		var value = obj.value;
		var id = obj.id;
		if(value&&isNaN(value)){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入数字");
		}
	}
	function pzMapBatchEditFormCallBack(data) {
		if(data.statusCode == 200) {
			$.pdialog.closeCurrent();
		}
		formCallBack(data);
	}
</script>
<div class="page">
	<form id="pzMapBatchEdit_search_form" method="post"	action="saveBusinessAccountMap" class="pageForm required-validate"	onsubmit="return validateCallback(this,pzMapBatchEditFormCallBack);">
		<input type="hidden" name="businessId" value="${businessId}" id="pzMapBatchEdit_businessId" />
		<input type="hidden" name="type" value="${type}" id="pzMapBatchEdit_type" />
		<input type="hidden" name="navTabId" value="${navTabId}" id="pzMapBatchEdit_navTabId" />
		<input type="hidden" name="businessAccountMapData" id="pzMapBatchEdit_pzMapData" />
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="savebutton" href="javaScript:savePzMapBatchEdit()" ><span><fmt:message key="button.save" /></span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:$.pdialog.closeCurrent();" ><span><fmt:message key="button.close" /></span></a>
				</li>
			</ul>
		</div>
		<div id="pzMapBatchEdit_gridtable_div" layoutH="40" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
		<div id="load_pzMapBatchEdit_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="pzMapBatchEdit_gridtable"></table>
		</div>
	</div>
</div>
