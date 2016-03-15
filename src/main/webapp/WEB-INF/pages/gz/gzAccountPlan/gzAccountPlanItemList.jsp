
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzAccountPlanItemLayout;
	var gzAccountPlanItemGridIdString="#gzAccountPlanItem_gridtable";
	var gzAccountPlanItemlastrow = "";
	var gzAccountPlanItemlastcell = "";
	jQuery(document).ready(function() {
		var gridJsonData = [];
		if(gzAccountItems){
    		for(var itemCode in gzAccountItems){
    			var name = gzAccountItems[itemCode].name;
    			var oldName = gzAccountItems[itemCode].oldName;
    			var isThousandSeparat = gzAccountItems[itemCode].isThousandSeparat;
    			var headerFontIndex = gzAccountItems[itemCode].headerFontIndex;
    			var fontIndex = gzAccountItems[itemCode].fontIndex;
    			var headerTextColor = gzAccountItems[itemCode].headerTextColor;
    			var mydate = new Date();
    			gridJsonData.push({"colId":"planItem"+mydate.getTime()+itemCode,
   				 "itemName":oldName,
				 "colName":name,
				 "itemCode":itemCode,
				 "isThousandSeparat":isThousandSeparat,
				 "headerFontIndex":headerFontIndex,
				 "fontIndex":fontIndex,
				 "headerTextColor":headerTextColor});
//     			 jQuery("#gzAccountPlanItem_gridtable").addRowData("planItem"+mydate.getTime()+itemCode, {
//     				 }, "last");
    		}
    		
    	 //jQuery("#gzAccountPlanItem_gridtable")[0].addJSONData(gridJsonData);
    	}
		if(configurable != "true"){
			jQuery("#gzAccountPlanItem_gridtable_del_li").hide();
			jQuery("#gzAccountPlanItem_gridtable_add_li").hide();
			jQuery("#gzAccountPlanItem_gridtable_items_div").hide();
		}
		var supCanFontSelect = "0:微软雅黑;1:微软雅黑(加粗);2:微软雅黑(下划线);3:微软雅黑(加粗&下划线);";
		supCanFontSelect += "4:宋体;5:宋体(加粗);6:宋体(下划线);7:宋体(加粗&下划线);";
		supCanFontSelect += "8:方正舒体;9:方正姚体;10:仿宋体;11:黑体;";
		supCanFontSelect += "12:华文彩云;13:华文仿宋;14:华文琥珀;15:华文楷体;";
		supCanFontSelect += "16:华文隶书;17:华文宋体;18:华文细黑;19:华文新魏;";
		supCanFontSelect += "20:华文行楷;21:华文中宋;22:楷体;23:隶书;24:幼圆";
		var gzAccountPlanItemGrid = jQuery(gzAccountPlanItemGridIdString);
    	gzAccountPlanItemGrid.jqGrid({
    		//url : "gzAccountPlanItemGridList?1=2",
    		url:"",
    		editurl:"",
			datatype : "local",
			mtype : "GET",
			data:gridJsonData,
        	colModel:[
{name:'colId',index:'colId',align:'center',label : '<s:text name="gzAccountPlanItem.colId" />',hidden:true,key:true},
{name:'itemName',index:'itemName',width:'150',align:'center',label : '<s:text name="gzAccountPlanItem.itemName" />',hidden:false},
{name:'colName',index:'colName',width:'150',align:'center',label : '<s:text name="gzAccountPlanItem.colName" />',hidden:false,editable:true,edittype:'text'},
{name:'itemCode',index:'itemCode',align:'center',label : '<s:text name="gzAccountPlanItem.itemCode" />',hidden:true},
{name:'isThousandSeparat',index:'isThousandSeparat',width:'60',align:'center',label : '<s:text name="gzAccountPlanItem.isThousandSeparat" />',hidden:false,editable:true,formatter:'select',edittype:'select', editoptions:{value:"0:否;1:是"}},
{name:'headerFontIndex',index:'headerFontIndex',width:'120',align:'center',label : '<s:text name="gzAccountPlanItem.headerFontIndex" />',hidden:false,editable:true,formatter:'select',edittype:'select', editoptions:{value:supCanFontSelect}},
{name:'fontIndex',index:'fontIndex',width:'120',align:'center',label : '<s:text name="gzAccountPlanItem.fontIndex" />',hidden:false,editable:true,formatter:'select',edittype:'select',editoptions:{value:supCanFontSelect}},
{name:'headerTextColor',index:'headerTextColor',width:'90',align:'center',label : '<s:text name="gzAccountPlanItem.headerTextColor" />',hidden:false,editable:true,edittype:'custom',editoptions:{custom_element:colorPickerElem,custom_value:hrSubSetElemValue}
,cellattr: function(rowId, val, rawObject, cm, rdata){ if(val){return "style='color:"+val+"'";}}}
],
        	jsonReader : {
				root : "gzAccountPlanItems", // (2)
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
        	//caption:'<s:text name="gzAccountPlanItemList.title" />',
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
       			gzAccountPlanItemlastrow = iRow;
       			gzAccountPlanItemlastcell = iCol;
       		},
		 	gridComplete:function(){
           		//if(jQuery(this).getDataIDs().length>0){
           		//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           		// }
           		var dataTest = {"id":"gzAccountPlanItem_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
	      	   	jQuery("#gview_gzAccountPlanItem_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
	      			jQuery(this).find("th").each(function(){
	      				jQuery(this).find("div").eq(0).css("line-height","18px");
	      			});
	      		}); 
      			gridResize(null,null,"gzAccountPlanItem_gridtable","single");
       		} 
    	});
    jQuery(gzAccountPlanItemGrid).jqGrid('bindKeys');
  	});
	
	//创建一个input输入框
    function hrSubSetIntElem (value, options) {
      var el = document.createElement("input");
      el.type="text";
      el.value = value;
      el.onblur = function(){
    	var valueTest=el.value;
		if(valueTest&&parseInt(valueTest)!=valueTest){
				el.value=0;
				alertMsg.error("请输入整数！");
			}
		}
      return el;
    }
  //创建一个颜色选择器
    function colorPickerElem (value, options) {
		  var el = document.createElement("input");
		  el.type="text";
		  el.value = value;
		  jQuery(el).colorpicker({
			    fillcolor:true,
			    ifr:true
			});
		  el.readOnly = true;
		  return el;
	}
	
	//获取值
    function hrSubSetElemValue(elem) {
      return $(elem).val();
    }
	
  /*新增工资项*/
    function gzAccountPlanItemTreeSelectInit(){
    	var itemCodes = jQuery("#gzAccountPlanItem_gridtable").jqGrid('getCol', "itemCode");
    	var itemCodeStr = "";
    	if(itemCodes){
    		var itemCodeLength = itemCodes.length;
    		for(var indexTemp = 0;indexTemp<itemCodeLength;indexTemp++){
    			itemCodeStr += "'" + itemCodes[indexTemp] + "',";
    		}
    		itemCodeStr = itemCodeStr.substring(0,itemCodeStr.length-1);
    	}
    	var gzTypeId = "${gzTypeId}";
    	var sql = "SELECT itemCode AS id,itemName AS name FROM gz_gzItem where gzTypeId = '"+gzTypeId+"'";
	  	if(itemCodeStr){
	  		sql += " AND itemCode NOT IN (" + itemCodeStr + ") ";	
	  	}
	  	sql += " order by sn ";
    	jQuery("#gzAccountPlanItem_items").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
// 			minWidth : '120px',
			selectParent : true
		});    	
    }
  /*添加*/
  function addGzAccountPlanItem(){
	  var itemCodes = jQuery("#gzAccountPlanItem_items_id").val();
	  var itemNames = jQuery("#gzAccountPlanItem_items").val();
	  if(!itemCodes){
		  alertMsg.error("请选择需要添加的工资项！");
		  return;
	  }
	  var itemCodeArr = itemCodes.split(",");
	  var itemNameArr = itemNames.split(",");
	  var itemCodeLength = itemCodeArr.length;
	  for(var indexTemp = 0;indexTemp<itemCodeLength;indexTemp++){
		  var mydate = new Date();
			 jQuery("#gzAccountPlanItem_gridtable").addRowData("planItem"+mydate.getTime()+indexTemp, {
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
	  jQuery("#gzAccountPlanItem_items_id").val("");
	  jQuery("#gzAccountPlanItem_items").val("");
  }
  /*删除*/
  function delGzAccountPlanItem(){
	  var sid = jQuery("#gzAccountPlanItem_gridtable").jqGrid('getGridParam','selarrrow');
      if(sid==null || sid.length ==0){
      	alertMsg.error("请选择记录。");
			return;
			}else {
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						var errorMessage = "";
					for(var i = sid.length;i > 0; i--){
							var rowId = sid[i-1];
							var row = jQuery("#gzAccountPlanItem_gridtable").jqGrid('getRowData',rowId);
							if(jQuery.inArray(row['itemCode'], requiredItems) == -1){
								jQuery("#gzAccountPlanItem_gridtable").delRowData(rowId);
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
  function saveGzAccountPlanItem(){
	  if(gzAccountPlanItemlastrow||gzAccountPlanItemlastcell){
		  $("#gzAccountPlanItem_gridtable").jqGrid("saveCell",gzAccountPlanItemlastrow,gzAccountPlanItemlastcell);
	  }
	  gzAccountItems = {};
	  jQuery("#${random}gz_items2").empty();
	  jQuery("#${random}gz_items1").empty();
	  if (allGzItems) {
			for (var i = 0; i < allGzItems.length; i++) {
				jQuery("#${random}gz_items1").append(
						"<option value='" + allGzItems[i].itemCode
								+ "'ondblclick='gzItemMoveToRight(this)'>"
								+ allGzItems[i].itemName + "</option>");
			}
	  }
	  var gzAccountItemIndex = 0;
	  jQuery("#gzAccountPlanItem_gridtable>tbody>tr").each(function (){
		 var rowData = jQuery("#gzAccountPlanItem_gridtable").getRowData(this.id);
			 var itemCode = rowData["itemCode"];
			 if(itemCode){
				 var name = rowData["colName"];
				 var oldName = rowData["itemName"];
				 var isThousandSeparat = rowData["isThousandSeparat"];
				 var headerFontIndex = rowData["headerFontIndex"];
				 var fontIndex = rowData["fontIndex"];
				 var headerTextColor = rowData["headerTextColor"];
				 gzAccountItems[itemCode] = {"name":name,"oldName":oldName,isThousandSeparat:isThousandSeparat,headerFontIndex:headerFontIndex,
						 fontIndex:fontIndex,headerTextColor:headerTextColor,sn:gzAccountItemIndex};
				 gzAccountItemIndex ++;
				 jQuery("#${random}gz_items1 option[value='"+ itemCode + "']").remove();
					jQuery("#${random}gz_items2").append("<option value='" + itemCode
									+ "'ondblclick='gzItemMoveToLeft(this)'>"
									+ oldName + "</option>");
			 }
	  });
	  $.pdialog.closeCurrent();
  }
</script>
<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li id="gzAccountPlanItem_gridtable_del_li">
					<a id="gzAccountPlanItem_gridtable_del" class="delbutton"  href="javaScript:delGzAccountPlanItem()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="gzAccountPlanItem_gridtable_save" class="savebutton"  href="javaScript:saveGzAccountPlanItem()"><span><s:text name="确定" /></span></a>
				</li>
				<li id="gzAccountPlanItem_gridtable_add_li" style="float: right;">
					<a  class="addbutton"  href="javaScript:addGzAccountPlanItem()"><span><s:text name="button.add" /></span></a>
				</li>
			</ul>
		</div>
		<div id="gzAccountPlanItem_gridtable_items_div" style="margin-top:-25px;margin-right:55px;float:right;">
			<span>工资项:</span>
			<input type="text" id="gzAccountPlanItem_items" onfocus="gzAccountPlanItemTreeSelectInit()">
			<input type="hidden" id="gzAccountPlanItem_items_id">
		</div>
		<div style="clear: both;">
		<div id="gzAccountPlanItem_gridtable_div" layoutH="25" class="grid-wrapdiv" extraHeight='17'>
 			<table id="gzAccountPlanItem_gridtable"></table>
		</div>
	</div>
</div>
</div>