<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var kqDayDataDetailLayout;
var kqDayDataDetailGridIdString="#kqDayDataDetail_gridtable";
var kqDayDataDetaillastrow = "";
var kqDayDataDetaillastcell = "";
var currentCells = kqDayData_gridtable.func("GetCurrentCell", "");
var currentCellArr = currentCells.split(",");
var startRow = +currentCellArr[0];
var startCol = +currentCellArr[1];
var endRow = +currentCellArr[2];
var endCol = +currentCellArr[3];
var kqItemsDDJson = {};
jQuery(document).ready(function() {
	if(kqItemsDD){
		for(var index in kqItemsDD){
			var nameTemp = kqItemsDD[index]["shortName"];
			kqItemsDDJson[nameTemp] = kqItemsDD[index];
		}
	}
	var gridJsonData = [];
	if(startRow == endRow && startCol == endCol && kqDayDataDetail){
		var kqIdTemp =  kqDayData_gridtable.func("GetCellData",startRow+"\r\n kqId");
		var colName = kqDayData_gridtable.func("GetColProp",startCol+"\r\n name");
		var jsonKey = kqIdTemp + "_" + colName;
		if(kqDayDataDetail[jsonKey]){
			for(var index in kqDayDataDetail[jsonKey]){
				var kqDayDataDetailTemp = kqDayDataDetail[jsonKey][index];
				var detailId = kqDayDataDetailTemp["detailId"];
				var item = kqDayDataDetailTemp["item"];
				var value = kqDayDataDetailTemp["value"];
				var kqItemsDDJsonTemp = kqItemsDDJson[item];
				var kqItemName = "";
				var frequency = "";
				if(kqItemsDDJsonTemp){
					kqItemName = kqItemsDDJsonTemp.kqItemName;
					frequency = kqItemsDDJsonTemp.frequency + "";
					switch(frequency){
						case "0":
							frequency = "天";
							break;
						case "1":
							frequency = "次";
							break;
						case "2":
							frequency = "小时";
							break;
					}
				}
				gridJsonData.push({
					"detailId":detailId,
					"kqItemName":kqItemName,
					"frequency":frequency,
					"item":item,
					"value":value});
			}
		}else{
			var mydate = new Date();
			var cellData = kqDayData_gridtable.func("GetCellData",startRow+"\r\n"+colName);
			var kqItemsDDJsonTemp = kqItemsDDJson[cellData];
			var kqItemName = "";
			var frequency = "";
			if(kqItemsDDJsonTemp){
				kqItemName = kqItemsDDJsonTemp.kqItemName;
				frequency = kqItemsDDJsonTemp.frequency + "";
				switch(frequency){
					case "0":
						frequency = "天";
						break;
					case "1":
						frequency = "次";
						break;
					case "2":
						frequency = "小时";
						break;
				}
			}
			if(cellData){
				gridJsonData.push({
					"detailId":"detailId"+mydate.getTime(),
					"kqItemName":kqItemName,
					"frequency":frequency,
					"item":cellData,
					"value":"1"});
			}
		}
	}
	var kqDayDataDetailGrid = jQuery(kqDayDataDetailGridIdString);
	kqDayDataDetailGrid.jqGrid({
		//url : "kqDayDataDetailGridList?1=2",
		url:"",
		editurl:"",
		datatype : "local",
		mtype : "GET",
		data:gridJsonData,
    	colModel:[
{name:'detailId',index:'detailId',align:'center',label : '<s:text name="kqDayDataDetail.detailId" />',hidden:true,key:true},
{name:'kqItemName',index:'kqItemName',align:'center',width:'80',label : '项目名称',hidden:false},
{name:'item',index:'item',align:'center',width:'80',label : '简称',hidden:false},
{name:'value',index:'value',width:'120',align:'center',label : '<s:text name="kqDayDataDetail.kqValue" />',hidden:false,editable:true,edittype:'custom',editoptions:{custom_element:jqGridNaNElem,custom_value:jqGridElemValue}},
{name:'frequency',index:'frequency',align:'center',width:'80',label : '单位',hidden:false}
],
    	jsonReader : {
			root : "kqDayDataDetails", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
			// (4)
		},
		rowNum:'1000',
    	sortname: 'colId',
    	viewrecords: true,
    	sortorder: 'asc',
    	//caption:'<s:text name="kqDayDataDetailList.title" />',
    	height:300,
    	gridview:true,
    	rownumbers:true,
    	loadui: "disable",
    	cellsubmit:'clientArray',
    	cellEdit:true,
    	multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
    	onSelectRow: function(rowid) {
   		},
   		beforeEditCell:function(rowid,cellname,v,iRow,iCol){
   			kqDayDataDetaillastrow = iRow;
   			kqDayDataDetaillastcell = iCol;
   		},
	 	gridComplete:function(){
       		//if(jQuery(this).getDataIDs().length>0){
       		//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
       		// }
       		var dataTest = {"id":"kqDayDataDetail_gridtable"};
  	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   	jQuery("#gview_kqDayDataDetail_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
      			jQuery(this).find("th").each(function(){
      				jQuery(this).find("div").eq(0).css("line-height","18px");
      			});
      		}); 
  			gridResize(null,null,"kqDayDataDetail_gridtable","single");
   		} 
	});
jQuery(kqDayDataDetailGrid).jqGrid('bindKeys');
	if("${oper}" == "one"){
		jQuery("#kqDayDataDetail_gridtable_items_div").hide();
		jQuery("#kqDayDataDetail_gridtable_del_li").hide();
		jQuery("#kqDayDataDetail_gridtable_save_li").hide();
		jQuery("#kqDayDataDetail_gridtable_add_li").hide();
		
	}	
});


/*新增考勤项*/
function kqDayDataDetailTreeSelectInit(){
	var itemCodes = jQuery("#kqDayDataDetail_gridtable").jqGrid('getCol', "item");
	var zNodes = [];
	if(kqItemsDD){
		for(var index in kqItemsDD){
			var nameTemp = kqItemsDD[index]["shortName"];
			if(jQuery.inArray(nameTemp, itemCodes) == -1){
				zNodes.push({id:nameTemp,pId:"",name:nameTemp,isParent:false});
			}
		}
	}
	jQuery("#kqDayDataDetail_items").treeselect({
		optType : "multi",
		dataType : 'nodes',
		exceptnullparent : true,
		lazy : false,
		zNodes : zNodes,
//			minWidth : '120px',
		selectParent : true
	});    	
}
/*添加*/
function addKqDayDataDetail(){
  var itemCodes = jQuery("#kqDayDataDetail_items_id").val();
  var itemNames = jQuery("#kqDayDataDetail_items").val();
  if(!itemCodes){
	  alertMsg.error("请选择需要添加的考勤项！");
	  return;
  }
  var itemCodeArr = itemCodes.split(",");
  var itemNameArr = itemNames.split(",");
  var itemCodeLength = itemCodeArr.length;
  for(var indexTemp = 0;indexTemp<itemCodeLength;indexTemp++){
	  var itemCode = itemCodeArr[indexTemp];
	  var kqItemsDDJsonTemp = kqItemsDDJson[itemCode];
		var kqItemName = "";
		var frequency = "";
		if(kqItemsDDJsonTemp){
			kqItemName = kqItemsDDJsonTemp.kqItemName;
			frequency = kqItemsDDJsonTemp.frequency + "";
			switch(frequency){
				case "0":
					frequency = "天";
					break;
				case "1":
					frequency = "次";
					break;
				case "2":
					frequency = "小时";
					break;
			}
		}
		
	  var mydate = new Date();
		 jQuery("#kqDayDataDetail_gridtable").addRowData("detailId"+mydate.getTime()+indexTemp, {
			 "detailId":"detailId"+mydate.getTime()+indexTemp,
			 "item":itemCode,
			 "kqItemName":kqItemName,
			 "frequency":frequency,
			 "value":"1"
			 }, "last");
  }
  jQuery("#kqDayDataDetail_items_id").val("");
  jQuery("#kqDayDataDetail_items").val("");
}
/*删除*/
function delKqDayDataDetail(){
  var sid = jQuery("#kqDayDataDetail_gridtable").jqGrid('getGridParam','selarrrow');
  if(sid==null || sid.length ==0){
  	alertMsg.error("请选择记录。");
		return;
		}else {
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					var errorMessage = "";
				for(var i = sid.length;i > 0; i--){
						var rowId = sid[i-1];
						var row = jQuery("#kqDayDataDetail_gridtable").jqGrid('getRowData',rowId);
						jQuery("#kqDayDataDetail_gridtable").delRowData(rowId);
					}
				}
			});
		}
}
/*保存*/
function saveKqDayDataDetail(){
  if(kqDayDataDetaillastrow||kqDayDataDetaillastcell){
	  $("#kqDayDataDetail_gridtable").jqGrid("saveCell",kqDayDataDetaillastrow,kqDayDataDetaillastcell);
  }
  var detailArr = [];
  var detailStr = "";
  var frequencyCount = 0;
  jQuery("#kqDayDataDetail_gridtable>tbody>tr").each(function (){
	 var rowData = jQuery("#kqDayDataDetail_gridtable").getRowData(this.id);
		 var detailId = rowData["detailId"];
		 if(detailId){
			 var item = rowData["item"];
			 var value = rowData["value"];
			 var frequency = rowData["frequency"];
			 if(frequency == "天"){
				 frequencyCount += +value;
			 }
			 detailStr += item + ",";
			 detailArr.push({detailId:detailId,kqId:"",column:"",item:item,value:value});
		 }
  });
//   if(frequencyCount !== 1){
// 	  alertMsg.error("天合计数应该为1。");
// 	  return;
//   }
  if(detailStr){
	  detailStr = detailStr.substring(0,detailStr.length-1);
  }
  kqDayData_gridtable.func("GrayWindow",'1 \r\n 255');//遮罩/还原的动作
	for(var rowIndex = startRow;rowIndex <= endRow;rowIndex++){
		var kqIdTemp =  kqDayData_gridtable.func("GetCellData",rowIndex+"\r\n kqId");
		var rowIndexTemp = kqIdDDObj[kqIdTemp];
		if(rowIndexTemp){
			kqDayDatas[rowIndexTemp]["isEdit"] = '1';
		}
		for(var colIndex = startCol;colIndex <= endCol;colIndex++){
			kqDayData_gridtable.func("SetCellData",rowIndex+"\r\n"+colIndex+"\r\n"+detailStr);
			if(rowIndexTemp){
				var colName = kqDayData_gridtable.func("GetColProp",colIndex+"\r\n name");
				var detailArrTemp = cloneObj(detailArr);
				if(detailArrTemp){
					for(var arrIndex in detailArrTemp){
						detailArrTemp[arrIndex]["kqId"] = kqIdTemp;
						detailArrTemp[arrIndex]["column"] = colName;
					}
				}
				kqDayDataDetail[kqIdTemp+"_"+colName] = detailArrTemp;
				kqDayDatas[rowIndexTemp][colName] = detailStr;
			}
		}
		kqDayDataCalDays(rowIndexTemp,rowIndex);
	}
	kqDayData_gridtable.func("GrayWindow","0");//遮罩/还原的动作
  $.pdialog.closeCurrent();
}
//关闭
function closeKqDayDataDetail(){
	$.pdialog.closeCurrent();
}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li id="kqDayDataDetail_gridtable_del_li">
					<a id="kqDayDataDetail_gridtable_del" class="delbutton"  href="javaScript:delKqDayDataDetail()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li id="kqDayDataDetail_gridtable_save_li">
					<a id="kqDayDataDetail_gridtable_save" class="savebutton"  href="javaScript:saveKqDayDataDetail()"><span>确定</span></a>
				</li>
				<li>
					<a id="kqDayDataDetail_gridtable_close" class="closebutton"  href="javaScript:closeKqDayDataDetail()"><span>关闭</span></a>
				</li>
				<li id="kqDayDataDetail_gridtable_add_li" style="float: right;">
					<a  class="addbutton"  href="javaScript:addKqDayDataDetail()"><span><s:text name="button.add" /></span></a>
				</li>
			</ul>
		</div>
		<div id="kqDayDataDetail_gridtable_items_div" style="margin-top:-25px;margin-right:55px;float:right;">
			<span>考勤项:</span>
			<input type="text" id="kqDayDataDetail_items" onfocus="kqDayDataDetailTreeSelectInit()">
			<input type="hidden" id="kqDayDataDetail_items_id">
		</div>
		<div style="clear: both;">
		<div id="kqDayDataDetail_gridtable_div" layoutH="25" class="grid-wrapdiv" extraHeight='17'>
 			<table id="kqDayDataDetail_gridtable"></table>
		</div>
	</div>
</div>
</div>





