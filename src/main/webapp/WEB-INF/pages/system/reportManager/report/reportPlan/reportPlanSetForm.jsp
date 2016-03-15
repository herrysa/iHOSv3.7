
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportPlanItemLayout;
	var reportPlanItemGridIdString="#reportPlanItem_gridtable";
	var reportPlanItemlastrow = "";
	var reportPlanItemlastcell = "";
	jQuery(document).ready(function() {
		var gridJsonData = [];
		if(reportPlanItems){
    		for(var itemCode in reportPlanItems){
    			var name = reportPlanItems[itemCode].name;
    			var oldName = reportPlanItems[itemCode].oldName;
    			var isThousandSeparat = reportPlanItems[itemCode].isThousandSeparat;
    			var headerFontIndex = reportPlanItems[itemCode].headerFontIndex;
    			var fontIndex = reportPlanItems[itemCode].fontIndex;
    			var headerTextColor = reportPlanItems[itemCode].headerTextColor;
    			var mydate = new Date();
    			gridJsonData.push({"colId":"planItem"+mydate.getTime()+itemCode,
   				 "itemName":oldName,
				 "colName":name,
				 "itemCode":itemCode,
				 "isThousandSeparat":isThousandSeparat,
				 "headerFontIndex":headerFontIndex,
				 "fontIndex":fontIndex,
				 "headerTextColor":headerTextColor});
    		}
    	}
		if(configurable != "true"){
			jQuery("#reportPlanItem_gridtable_del_li").hide();
			jQuery("#reportPlanItem_gridtable_add_li").hide();
			jQuery("#reportPlanItem_gridtable_items_div").hide();
		}
		var supCanFontSelect = "0:微软雅黑;1:微软雅黑(加粗);2:微软雅黑(下划线);3:微软雅黑(加粗&下划线);";
		supCanFontSelect += "4:宋体;5:宋体(加粗);6:宋体(下划线);7:宋体(加粗&下划线);";
		supCanFontSelect += "8:方正舒体;9:方正姚体;10:仿宋体;11:黑体;";
		supCanFontSelect += "12:华文彩云;13:华文仿宋;14:华文琥珀;15:华文楷体;";
		supCanFontSelect += "16:华文隶书;17:华文宋体;18:华文细黑;19:华文新魏;";
		supCanFontSelect += "20:华文行楷;21:华文中宋;22:楷体;23:隶书;24:幼圆";
		var reportPlanItemGrid = jQuery(reportPlanItemGridIdString);
    	reportPlanItemGrid.jqGrid({
    		//url : "reportPlanItemGridList?1=2",
    		url:"",
    		editurl:"",
			datatype : "local",
			mtype : "GET",
			data:gridJsonData,
        	colModel:[
{name:'colId',index:'colId',align:'center',label : '<s:text name="reportPlanItem.colId" />',hidden:true,key:true},
{name:'itemName',index:'itemName',width:'150',align:'center',label : '<s:text name="reportPlanItem.itemName" />',hidden:false},
{name:'colName',index:'colName',width:'150',align:'center',label : '<s:text name="reportPlanItem.colName" />',hidden:false,editable:true,edittype:'text'},
{name:'itemCode',index:'itemCode',align:'center',label : '<s:text name="reportPlanItem.itemCode" />',hidden:true},
{name:'isThousandSeparat',index:'isThousandSeparat',width:'60',align:'center',label : '<s:text name="reportPlanItem.isThousandSeparat" />',hidden:false,editable:true,formatter:'select',edittype:'select', editoptions:{value:"0:否;1:是"}},
{name:'headerFontIndex',index:'headerFontIndex',width:'120',align:'center',label : '<s:text name="reportPlanItem.headerFontIndex" />',hidden:false,editable:true,formatter:'select',edittype:'select', editoptions:{value:supCanFontSelect}},
{name:'fontIndex',index:'fontIndex',width:'120',align:'center',label : '<s:text name="reportPlanItem.fontIndex" />',hidden:false,editable:true,formatter:'select',edittype:'select',editoptions:{value:supCanFontSelect}},
{name:'headerTextColor',index:'headerTextColor',width:'90',align:'center',label : '<s:text name="reportPlanItem.headerTextColor" />',hidden:false,editable:true,edittype:'custom',editoptions:{custom_element:jqGridColorPickerElem,custom_value:jqGridElemValue}
,cellattr: function(rowId, val, rawObject, cm, rdata){ if(val){return "style='color:"+val+"'";}}}
],
        	jsonReader : {
				root : "reportPlanItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
			rowNum:'1000',
        	sortname: '',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="reportPlanItemList.title" />',
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
       			reportPlanItemlastrow = iRow;
       			reportPlanItemlastcell = iCol;
       		},
		 	gridComplete:function(){
           		//if(jQuery(this).getDataIDs().length>0){
           		//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           		// }
           		var dataTest = {"id":"reportPlanItem_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
	      	   	jQuery("#gview_reportPlanItem_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
	      			jQuery(this).find("th").each(function(){
	      				jQuery(this).find("div").eq(0).css("line-height","18px");
	      			});
	      		}); 
      			gridResize(null,null,"reportPlanItem_gridtable","single");
       		} 
    	});
    jQuery(reportPlanItemGrid).jqGrid('bindKeys');
  	});
  /*新增报表项*/
    function reportPlanItemTreeSelectInit(){
    	var itemCodes = jQuery("#reportPlanItem_gridtable").jqGrid('getCol', "itemCode");
    	var treeNodes = [];
    	if(allReportItems){
    		for(var index in allReportItems){
    			var reportItem = allReportItems[index];
    			var itemCode = reportItem.itemCode;
    			var itemName = reportItem.itemName;
    			if(!itemCodes || jQuery.inArray(itemCode, itemCodes)==-1){
    				treeNodes.push({id:itemCode,name:itemName});
    			}
    		}
    	}
    	var itemCodeStr = "";
    	if(itemCodes){
    		var itemCodeLength = itemCodes.length;
    		for(var indexTemp = 0;indexTemp<itemCodeLength;indexTemp++){
    			itemCodeStr += "'" + itemCodes[indexTemp] + "',";
    		}
    		itemCodeStr = itemCodeStr.substring(0,itemCodeStr.length-1);
    	}
    	jQuery("#reportPlanItem_items").treeselect({
			optType : "multi",
			dataType : 'nodes',
			zNodes : treeNodes,
			exceptnullparent : true,
			lazy : false,
// 			minWidth : '120px',
			selectParent : true
		});    	
    }
  /*添加*/
  function addreportPlanItem(){
	  var itemCodes = jQuery("#reportPlanItem_items_id").val();
	  var itemNames = jQuery("#reportPlanItem_items").val();
	  if(!itemCodes){
		  alertMsg.error("请选择需要添加的${subSystemPrefix}项！");
		  return;
	  }
	  var itemCodeArr = itemCodes.split(",");
	  var itemNameArr = itemNames.split(",");
	  var itemCodeLength = itemCodeArr.length;
	  for(var indexTemp = 0;indexTemp<itemCodeLength;indexTemp++){
		  var mydate = new Date();
			 jQuery("#reportPlanItem_gridtable").addRowData("planItem"+mydate.getTime()+indexTemp, {
				 "colId":"planItem"+mydate.getTime()+indexTemp,
				 "itemName":itemNameArr[indexTemp],
				 "colName":itemNameArr[indexTemp],
				 "itemCode":itemCodeArr[indexTemp],
				 "isThousandSeparat":"1",
				 "headerFontIndex":'1',
				 "fontIndex":'0',
				 "headerTextColor":'#000000'
				 }, "last");
	  }
	  jQuery("#reportPlanItem_items_id").val("");
	  jQuery("#reportPlanItem_items").val("");
  }
  /*删除*/
  function delreportPlanItem(){
	  var sid = jQuery("#reportPlanItem_gridtable").jqGrid('getGridParam','selarrrow');
      if(sid==null || sid.length ==0){
      	alertMsg.error("请选择记录。");
			return;
			}else {
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						var errorMessage = "";
					for(var i = sid.length;i > 0; i--){
							var rowId = sid[i-1];
							var row = jQuery("#reportPlanItem_gridtable").jqGrid('getRowData',rowId);
							if(jQuery.inArray(row['itemCode'], requiredItems) == -1){
								jQuery("#reportPlanItem_gridtable").delRowData(rowId);
							}else{
								errorMessage += row['itemName'] + ",";
							}
						}
					if(errorMessage){
						errorMessage = errorMessage.substring(0,errorMessage.length-1);
									alertMsg.error(errorMessage+"为系统项.");
									return;
								}
					}
				});
			}
  }
  /*保存*/
  function savereportPlanItem(){
	  if(reportPlanItemlastrow||reportPlanItemlastcell){
		  $("#reportPlanItem_gridtable").jqGrid("saveCell",reportPlanItemlastrow,reportPlanItemlastcell);
	  }
	  reportPlanItems = {};
	  jQuery("#reportPlanForm_items2").empty();
	  jQuery("#reportPlanForm_items1").empty();
	  if (allReportItems) {
			for (var i = 0; i < allReportItems.length; i++) {
				jQuery("#reportPlanForm_items1").append(
						"<option value='" + allReportItems[i].itemCode
								+ "'ondblclick='reportPlanItemMoveToRight(this)'>"
								+ allReportItems[i].itemName + "</option>");
			}
	  }
	  var reportItemIndex = 0;
	  jQuery("#reportPlanItem_gridtable>tbody>tr").each(function (){
		 var rowData = jQuery("#reportPlanItem_gridtable").getRowData(this.id);
			 var itemCode = rowData["itemCode"];
			 if(itemCode){
				 var name = rowData["colName"];
				 var oldName = rowData["itemName"];
				 var isThousandSeparat = rowData["isThousandSeparat"];
				 var headerFontIndex = rowData["headerFontIndex"];
				 var fontIndex = rowData["fontIndex"];
				 var headerTextColor = rowData["headerTextColor"];
				 reportPlanItems[itemCode] = {"name":name,"oldName":oldName,isThousandSeparat:isThousandSeparat,headerFontIndex:headerFontIndex,
						 fontIndex:fontIndex,headerTextColor:headerTextColor,sn:reportItemIndex};
				 reportItemIndex ++;
				 jQuery("#reportPlanForm_items1 option[value='"+ itemCode + "']").remove();
					jQuery("#reportPlanForm_items2").append("<option value='" + itemCode
									+ "'ondblclick='reportPlanItemMoveToLeft(this)'>"
									+ oldName + "</option>");
			 }
	  });
	  $.pdialog.close('reportPlanSet');
  }
</script>
<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li id="reportPlanItem_gridtable_del_li">
					<a id="reportPlanItem_gridtable_del" class="delbutton"  href="javaScript:delreportPlanItem()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="reportPlanItem_gridtable_save" class="savebutton"  href="javaScript:savereportPlanItem()"><span><s:text name="确定" /></span></a>
				</li>
				<li id="reportPlanItem_gridtable_add_li" style="float: right;">
					<a  class="addbutton"  href="javaScript:addreportPlanItem()"><span><s:text name="button.add" /></span></a>
				</li>
			</ul>
		</div>
		<div id="reportPlanItem_gridtable_items_div" style="margin-top:-25px;margin-right:55px;float:right;">
			<span>${subSystemPrefix}项:</span>
			<input type="text" id="reportPlanItem_items" onfocus="reportPlanItemTreeSelectInit()">
			<input type="hidden" id="reportPlanItem_items_id">
		</div>
		<div style="clear: both;">
		<div id="reportPlanItem_gridtable_div" layoutH="25" class="grid-wrapdiv" extraHeight='17'>
 			<table id="reportPlanItem_gridtable"></table>
		</div>
	</div>
</div>
</div>