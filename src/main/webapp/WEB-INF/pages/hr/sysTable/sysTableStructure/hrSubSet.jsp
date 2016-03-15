<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<script>
	var isView = '';
	var tableKind = '';
	var hrSubSetEditRowId = '';
// 	var treeSqlArr = new Array();
// 	var treeIdArr = new Array();
	var colModelData,subEntityIdString;
	jQuery(document).ready(function() {
		jQuery("#${subTableCode}_gridtable_editFlag").val("0");
		initSubEntityIdString();
		initSubSetGrid();
		var tablecontainer = "${tablecontainer}";
		if(tablecontainer.indexOf("hrDepartmentSnapForm")==-1){
			jQuery("#${subTableCode}_gridtable_div").attr("extraWidth",160);
			if("${oper}"=="view"){
				jQuery("#${subTableCode}_gridtable_div").attr("extraHeight",87);
			}else{
				jQuery("#${subTableCode}_gridtable_div").attr("extraHeight",114);
			}
		}else{
			jQuery("#${subTableCode}_gridtable_div").attr("extraWidth",0);
			if("${oper}"=="view"){
				jQuery("#${subTableCode}_gridtable_div").attr("extraHeight",83);
			}else{
				jQuery("#${subTableCode}_gridtable_div").attr("extraHeight",110);
			}
		}
		if(isView != "isView"){
		jQuery("#${subTableCode}_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		    //gridAddRow(jQuery("#${subTableCode}_gridtable"));
		    if(jQuery("#${subTableCode}_gridtable_editFlag").val()=="1"){
		    	alertMsg.error("请保存编辑行。");
				return;
		    }
		    addRowMulti("${subTableCode}_gridtable");
		    jQuery("#${subTableCode}_gridtable_editFlag").val("1");
		});
		jQuery("#${subTableCode}_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
			if(jQuery("#${subTableCode}_gridtable_editFlag").val()=="1"){
		    	alertMsg.error("请保存编辑行。");
				return;
		    }
			//var sid = jQuery("#${subTableCode}_gridtable").jqGrid('getGridParam','selrow');
			var sid = jQuery("#${subTableCode}_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid==null || sid.length !=1){
	        	alertMsg.error("请选择一条记录。");
				return;
			}else{
				jQuery("#${subTableCode}_gridtable").jqGrid('editRow',sid,true);
				jQuery("#${subTableCode}_gridtable_editFlag").val("1");
				hrSubSetEditRowId = sid[0];
			}
		});
		jQuery("#${subTableCode}_gridtable_cancel_custom").unbind( 'click' ).bind("click",function(){
			var sid = jQuery("#${subTableCode}_gridtable").jqGrid('getGridParam','selarrrow');
			if(sid==null || sid.length !=1){
	        	alertMsg.error("请选择一条记录。");
				return;
			}else{
				if(hrSubSetEditRowId&&hrSubSetEditRowId!=sid[0]){
					alertMsg.error("请选择编辑行记录。");
					return;
				}
				jQuery("#${subTableCode}_gridtable").jqGrid('restoreRow',sid);
				jQuery("#${subTableCode}_gridtable_editFlag").val("0");
				hrSubSetEditRowId='';
			}
		});
		jQuery("#${subTableCode}_gridtable_save_custom").unbind( 'click' ).bind("click",function(){
			var sid = jQuery("#${subTableCode}_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length !=1){
	        	alertMsg.error("请选择一条记录。");
				return;
			}else {
				if(hrSubSetEditRowId&&hrSubSetEditRowId!=sid[0]){
					alertMsg.error("请选择编辑行记录。");
					return;
				}
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					jQuery("#${subTableCode}_gridtable").jqGrid('saveRow',rowId,  {  
						    "keys" : true,  
						    "oneditfunc" : null,  
						    "successfunc" : null,  
						    "url" : "clientArray",  
						     "extraparam" : {},  
						    "aftersavefunc" : null,  
						    "errorfunc": null,  
						    "afterrestorefunc" : null,  
						    "restoreAfterError" : true,  
						    "mtype" : "POST"  
					});  
				}
			}
	        jQuery("#${subTableCode}_gridtable_editFlag").val("0");
	        hrSubSetEditRowId = '';
		});
		
		jQuery("#${subTableCode}_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
			if(jQuery("#${subTableCode}_gridtable_editFlag").val()=="1"){
		    	alertMsg.error("请保存编辑行。");
				return;
		    }
			var sid = jQuery("#${subTableCode}_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录。");
				return;
			}else {
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						for(var i = sid.length;i > 0; i--){
							var rowId = sid[i-1];
							jQuery("#${subTableCode}_gridtable").delRowData(rowId);
						}
					}
				});
			}
	        jQuery("#${subTableCode}_gridtable_editFlag").val("0");
	        hrSubSetEditRowId='';
 		});
		}
		if("${oper}"=='view'){
			jQuery("#${subTableCode}_panelBar").css("display","none");
			gridResize(null,null,"${subTableCode}_gridtable","single");
		}
	});
	function initSubEntityIdString(){
		var subTableCode = "${subTableCode}";
		if(subTableCode.indexOf('hr_person') == 0){
			subEntityIdString = "personId";
		}else if(subTableCode.indexOf('hr_dept') == 0){
			subEntityIdString = "deptId";
		}
	}
	function initSubSetGrid(){
		jQuery.ajax({
	    	url: 'getTableColumnInfo',
	        data: {subTableCode:"${subTableCode}"},
	        type: 'get',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){   
	        	isView = data.isView;
	        	tableKind = data.tableKind;
	        	var colModelDatas = initSubSetColModel(data.sysTableStructures);
	        	initSubSetGridScript(colModelDatas);
	        }
	    });
	}
	function initSubSetColModel(data){
		var colModelDatas = [
			{name:'code',index:'code',align:'left',label : '主键',hidden:true,key:true,width : "30" },
			{name:subEntityIdString,index:subEntityIdString,align:'left',label : '人员ID',hidden:true,width : "120" },
			{name:'snapCode',index:'snapCode',align:'left',label : 'snapCode',hidden:true,width : "30" }
		];  
		for(var index in data){
	 		var row = data[index];
	 		var colModelData = {  
	            name : row.fieldInfo.fieldCode,  
	            index: row.fieldInfo.fieldCode,
	 		    label : row.name,
	 		    align : parseAlign(row.fieldInfo.dataType), 
	 		  	editable:true,
	 		  	sortable:false,
	 		    width : parseWidth(row.gridShowLength)  
	 		}
	 		colModelData = addToFormatter(colModelData,row);
	 		colModelData = addToEditoption(colModelData,row);
	 		colModelDatas.push(colModelData);
		} 
		return colModelDatas;
	}
	function initSubSetGridScript(colModelDatas){
		var gridString = "#${subTableCode}_gridtable";
		var gridObject = jQuery(gridString);
		gridObject.jqGrid({
	    	url : "subSetList?subTableCode=${subTableCode}&subEntityId=${subEntityId}&snapCode=${snapCode}&isView="+isView+"&tableKind="+tableKind,
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:colModelDatas,
	        jsonReader : {
				root : "subSets", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: subEntityIdString,
	        sortorder: 'desc',
	        viewrecords: true,
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
				var rowNum = jQuery("#${subTableCode}_gridtable").getDataIDs().length;
				if(rowNum<=0){
					var tw = jQuery("#${subTableCode}_gridtable").outerWidth();
					jQuery("#${subTableCode}_gridtable").parent().width(tw);
					jQuery("#${subTableCode}_gridtable").parent().height(1);
				}
				var dataTest = {"id":"${subTableCode}_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				makepager("${subTableCode}_gridtable");
	       	} 
   		});
   		jQuery(gridObject).jqGrid('bindKeys'); 
	}
	/*根据数据类型，设定对齐方式*/
	function parseAlign(type){
		var align = "left";
		switch(type){
			case "3"://布尔型
			case "4"://日期型
				align = "center";break;
			case "1"://字符型
			case "7"://图片型
				break;
			case "2"://浮点型
			case "5"://整数型
			case "6"://货币型
				align = "right";break;
			default:
				break;
		}
		return align;
	}
	/*根据数据类型，设定列的宽度*/
	function parseWidth(gridShowLength){
		var width = "80";
		if(gridShowLength&&gridShowLength!=0){
			width = gridShowLength;
		}
		return width;
	}
	/*根据数据类型增加edittype*/
	function addToEditoption(colModelData,row){
		var edittype,editoptions,editrules;
		var userTag = row.fieldInfo.userTag;
		var parameter = row.fieldInfo.parameter1;
		var parameterName = row.fieldInfo.parameter2;
		switch(row.fieldInfo.dataType){
		case '1':
			if(row.fieldInfo.dataLength>0){
				editoptions= { maxlength: row.fieldInfo.dataLength}
			}
			if(userTag == "select"){    
				edittype = "select";
				var editValue=":";
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
		        		   editValue += ";"+data.filedDataByCode[optionItem].value+":"+data.filedDataByCode[optionItem].value;
		        		   }
	        	   }
		           });
				editoptions={value:editValue};
			}else{
				if(row.fieldInfo.dataLength>=100){
					edittype = "textarea";	
				}else{
					edittype = "text";
				}
			}
			break;
		case "3"://布尔型
			edittype = "checkbox";
			editoptions = {value:"Yes:No"}
			break;
		case "4"://日期型
			//edittype = "text";
			//editrules={date:true};
			edittype = 'custom';
			editoptions = {custom_element:jqGridcalendarElem,custom_value:jqGridCalendarValue};
			break;
		case "2"://浮点型
			edittype = 'custom';
			editoptions = {custom_element:jqGridNaNElem,custom_value:jqGridElemValue};
// 			edittype = "text";
// 			editrules={number:true};
			break;
		case "5"://整数型
			edittype = 'custom';
			editoptions = {custom_element:jqGridIntElem,custom_value:jqGridElemValue};
// 			edittype = "text";
// 			editrules={integer:true};
			break;
		case "6"://货币型
			edittype = 'custom';
			editoptions = {custom_element:jqGridNaNElem,custom_value:jqGridElemValue};
// 			edittype = "text";
// 			editrules={number:true};
			break;
		default:
			break;
		}
		if(edittype){
			colModelData.edittype = edittype;
			if(editoptions){
				colModelData.editoptions = editoptions;
			}
			if(editrules){
				colModelData.editrules = editrules;
			}
		}
		return colModelData;
	}
	/*根据数据类型增加formatter*/
	function addToFormatter(colModelData,row){
		var type = row.fieldInfo.dataType;
// 		var userTag = row.fieldInfo.userTag;
// 		var parameter = row.fieldInfo.parameter;
		var formatter,formatoptions;
		switch(type){
			case "3"://布尔型
				formatter = "checkbox";break;
			case "4"://日期型
				formatter = "date";
				formatoptions = {newformat : 'Y-m-d'};
				break;
			case "2"://浮点型
				formatter = "number";break;
			case "5"://整数型
				formatter = "integer";break;
			case "6"://货币型
				formatter = "currency";
				formatoptions = {thousandsSeparator: ',',decimalPlaces: 2};
				break;
			default:
				break;
		}
		if(formatter){
			colModelData.formatter = formatter;
			if(formatoptions){
				colModelData.formatoptions = formatoptions;
			}
		}
		return colModelData;
	}
	
	function addRowMulti(gridId){  
		   //获取表格的初始话model  
		    var colModel = jQuery("#"+gridId).jqGrid().getGridParam("colModel") ;  
		    var cellLenth = colModel.length ;  
		    //设置所有列可编辑  
		    for ( var i = 0; i < cellLenth; i++) {  
		    colModel[i].editable = true ;  
		    }  
		    var newRow = JSON.stringify(colModel); 
		    var mydate = new Date();
		    var newrowid = mydate.getTime();
		   // var ids = jQuery("#"+gridId).jqGrid('getDataIDs');  
		    //如果jqgrid中没有数据 定义行号为1 ，否则取当前最大行号+1  
		   // var rowid = ids.length ==0 ? 1: (ids.length)+1;  
		    //获得新添加行的行号
		   // var newrowid = (0-rowid);  
		    //设置grid单元格不可编辑 （防止在添加时，用户修改其他非添加行的数据）  
		    jQuery("#"+gridId).setGridParam({cellEdit:false});  
		    //将新行追加到表格头部  
		    jQuery("#"+gridId).jqGrid("addRowData", newrowid,newRow , "first"); 
		    hrSubSetEditRowId = newrowid;
		    //设置grid单元格可编辑（防止追加行后，可编辑列无法编辑）  
		    jQuery("#"+gridId).jqGrid('editRow', newrowid, false);  
		    jQuery("#"+gridId).jqGrid('setSelection',newrowid);
	}
</script>
</head>
<div id="${subTableCode}_page" class="page">
	<div class="pageContent">
		<div class="panelBar" id="${subTableCode}_panelBar">
			<ul class="toolBar">
				<li><a id="${subTableCode}_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a id="${subTableCode}_gridtable_edit_custom"  class="editbutton"  href="javaScript:" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a id="${subTableCode}_gridtable_save_custom" class="savebutton"  href="javaScript:" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a id="${subTableCode}_gridtable_cancel_custom" class="canceleditbutton" href="javaScript:"><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="${subTableCode}_gridtable_del_custom" class="delbutton" href="javaScript:"><span><s:text name="button.delete" /></span></a></li>
			</ul>
		</div>
		<div id="${subTableCode}_gridtable_div" layoutH="20" style="margin-left: 2px; margin-top: 2px; overflow: hidden" tablecontainer="${tablecontainer}" extraWidth="160" extraHeight="118">
			<table id="${subTableCode}_gridtable"></table>
			<input id="${subTableCode}_gridtable_editFlag" type="hidden"/>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> 
			<select id="${subTableCode}_gridtable_numPerPage">
			</select> 
			<span>
				<s:text name="pager.items" />. <s:text name="pager.total" />
				<label id="${subTableCode}_gridtable_totals" style="width:10px;float:none;"></label><s:text name="pager.items" />
			</span>
		</div>
		<div id="${subTableCode}_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>