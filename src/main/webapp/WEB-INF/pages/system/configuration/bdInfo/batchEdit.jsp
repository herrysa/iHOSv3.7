<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	var batchEditGridIdString="#batchEdit_gridtable";
	var batchTreeSelectObj = [];
	jQuery(document).ready(function() {
		var url = "batchEditFieldInfoGridList?filter_EQS_bdInfo.tableName=${tableCode}";
		if("${filterStr}"){
			url += "&" + "${filterStr}";
		}
		url = encodeURI(url);
		var batchEditGrid = jQuery(batchEditGridIdString);
			batchEditGrid.jqGrid({
	    	url : url,
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'fieldCode',index:'fieldCode',width : 100,align:'left',label : '字段',hidden:true,key:true},
				{name:'dataType',index:'dataType',width : 100,align:'left',label : '数据类型',hidden:true},
				{name:'dataLength',index:'dataLength',width:50,align:'right',label:'数据长度',hidden:true},
				{name:'userTag',index:'userTag',width:50,align:'right',label:'自定义标签',hidden:true},
				{name:'parameter1',index:'parameter1',width : 100,align:'left',label : '参数',hidden:true},
				{name:'parameter2',index:'parameter2',width : 100,align:'left',label : '参数',hidden:true},
				{name:'sn',index:'sn',width : 100,align:'left',label : '序号',hidden:true},
				{name:'fieldName',index:'fieldName',width : 100,align:'left',label : '名称',hidden:false,sortable:false},
				{name:'editvalue',index:'name',width : 400,align:'left',label : '修改值',hidden:false,formatter:batchEditFormatter,sortable:false}
	        ],
	        jsonReader : {
				root : "fieldInfoes", // (2)
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
				if(batchTreeSelectObj){
					for(var objSn in batchTreeSelectObj){
						var treeTemp = batchTreeSelectObj[objSn];
						var treeId = treeTemp.treeId;
						var treeSql = treeTemp.treeSql;
						 jQuery("#"+treeId).treeselect({
							   dataType:"sql",
							   optType:"single",
							   sql:treeSql,
							   exceptnullparent:false,
							   selectParent:false,
							   lazy:false
							});
					}
				}
	           var dataTest = {"id":"batchEdit_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	       	} 
	    });
	    jQuery(batchEditGrid).jqGrid('bindKeys');
	});
	
	function batchEditFormatter(cellvalue, options, rowObject){
		var dataType = rowObject['dataType'];
		var code = rowObject['fieldCode'];
		var userTag = rowObject['userTag'];
		var parameter = rowObject['parameter1'];
		var parameter2 = rowObject['parameter2'];
		var dataLength = rowObject['dataLength'];
		var editvalue;
		var mydate = new Date();
		var inputId = "batchEdit_"+code+mydate.getTime();
		switch(dataType){
			case '1':
				if(userTag == 'select'){
					editvalue='<select   class="select">';
					editvalue+='<option value =""></option>';
					jQuery.ajax({
 	    		    	url: 'getFiledDataList',
 	    		        data: {code:parameter2},
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
					batchTreeSelectObj.push({treeId:inputId,treeSql:parameter});
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
	
	function saveBatchEdit(){
		var keySid = jQuery("#"+"${navTabId}").jqGrid('getGridParam','selarrrow');
		var sid = jQuery("#batchEdit_gridtable").jqGrid('getGridParam', 'selarrrow');
	    if(sid==null|| sid.length == 0){       	
			alertMsg.error("请选择需要保存的记录。");
			return;
		}
			var code;
			var name;
			var dataType;
			var userTag;
			var curInputObj;
			var jsonObj = [];
			var gridAllfileds="";
			var errorMessage = "";
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#batchEdit_gridtable").jqGrid('getRowData',rowId);
				code = row['fieldCode'];
		        dataType = row['dataType'];
		        userTag = row['userTag'];
		        name = row['fieldName'];
		        switch(dataType){
					case '1':
						if(userTag == 'select'){
							curInputObj = jQuery("#"+sid[i]).find(".select");
						}else if(userTag == 'treeSelect'){
							curInputObj = jQuery("#"+sid[i]).find("input[type=hidden]");
						}else{
							curInputObj = jQuery("#"+sid[i]).find("input[type=text]");
						}
						if(!curInputObj.val()){
							errorMessage += name + "不能为空.";
						}
						jsonObj.push({code:code,value:curInputObj.val()});
						break;
					case '2':
					case '5':
					case '6':
					case '7':
					case '4':
						curInputObj = jQuery("#"+sid[i]).find("input[type=text]");
						if(!curInputObj.val()){
							errorMessage += name + "不能为空.";
						}
						jsonObj.push({code:code,value:curInputObj.val()});
						break;
					case '3':
						curInputObj = jQuery("#"+sid[i]).find("input[name="+code+"]");
						if(curInputObj.attr("checked") == "checked"){
							jsonObj.push({code:code,value:"1"});
						}else{
							jsonObj.push({code:code,value:"0"});
						}
						break;
					default:
					}
				}
			if(errorMessage){
				alertMsg.error(errorMessage);
				return;
			}
		var url = "batchEditSave?tableCode=${tableCode}&tableKey=${tableKey}&navTabId=${navTabId}";
		url = encodeURI(url);
		jQuery("#batchEdit_gridAllDatas").val(JSON.stringify(jsonObj));
		jQuery("#batchEdit_gridAllfileds").val(keySid);
	    jQuery("#batchEdit_search_form").attr("action",url);
		jQuery("#batchEdit_search_form").submit(); 
	}
	function lengthCheckForBatchEdit(obj,maxlength){
		var value=obj.value;
		var id=obj.id;
		if(value&&value.length>maxlength){
			value=value.substr(0,maxlength);
			jQuery("#"+id).val(value);
			alertMsg.error("输入的内容过长,最大长度为"+maxlength);
		}
	}
	function numberCheckForBatchEdit(obj){
		var value=obj.value;
		var id=obj.id;
		if(value&&parseInt(value)!=value){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入整数");
		}
	}
	function digitCheckForBatchEdit(obj){
		var value=obj.value;
		var id=obj.id;
		if(value&&isNaN(value)){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入数字");
		}
	}
</script>
<div class="page">
	<form id="batchEdit_search_form" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
		<s:hidden name="gridAllDatas" id="batchEdit_gridAllDatas"/>
		<s:hidden name="filedKeys" id="batchEdit_gridAllfileds"/>
	</form>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="savebutton" href="javaScript:saveBatchEdit()" ><span><fmt:message key="button.save" /></span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:$.pdialog.closeCurrent();" ><span><fmt:message key="button.close" /></span></a>
				</li>
			</ul>
		</div>
		<div id="batchEdit_gridtable_div" layoutH="40" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
		<div id="load_batchEdit_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="batchEdit_gridtable"></table>
		</div>
	</div>
</div>
