<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var reportPlanSortGridDefine = {
		key:"reportPlanSort_gridtable",
		main:{
			Build : '',
			Load :'',
		},
		event:{
		},callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				grid.func("EnableMenu","print,copy,addSort,selectCol,export,separate,showRuler,enter \r\n false");//打印预览,复制,加入多重排序,自定义显示隐藏列,转换输出,分屏冻结,显示/隐藏左标尺,回车键行为	
				var handle = grid.func("GetHandle", ""); 
				grid.func("EnableDrag", handle); //拖曳操作

			}
		}
	};
supcanGridMap.put("reportPlanSort_gridtable",reportPlanSortGridDefine);
	jQuery(document).ready(function() {
		var reportPlanSortGrid = cloneObj(supCanTreeListGrid);
		reportPlanSortGrid.Cols = [{name:'itemCode',align:'left',text : '项目编码',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'name',align:'left',text : '项目名称',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'oldName',align:'left',text : '显示名称',width:100,isHide:false,editable:false,dataType:'string'}
//		 				           {name:'sn',align:'right',width:50,text : '<s:text name="reportPlan.sn" />',isHide:"absHide",dataType:'int'}
		 				           ];
		reportPlanSortGrid.Properties.Title = "${subSystemPrefix}项排序";
		reportPlanSortGridDefine.main.Build = JSON.stringify(reportPlanSortGrid);
		var reportPlanObj = [];
		if(reportPlanItems){
			for(var itemCode in reportPlanItems){
				var reportPlanItem = reportPlanItems[itemCode];
				var name = reportPlanItem.name;
				var oldName = reportPlanItem.oldName; 
				reportPlanObj.push({itemCode:itemCode,name:name,oldName:oldName});
			}
		}
		var reportPlansGridData = {};
		reportPlansGridData.Record = reportPlanObj;
		reportPlanSortGridDefine.main.Load = JSON.stringify(reportPlansGridData);
		insertTreeListToDiv("reportPlanSort_gridtable_div","reportPlanSort_gridtable","","100%");
		/*上移*/
		jQuery("#reportPlanSort_moveUp").bind("click",function(){
			var curRows = reportPlanSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				reportPlanSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var nextRow = +curRows - 1;
			if(nextRow < 0){
				return;
			}
			reportPlanSort_gridtable.func("SelectRow","-1");
			reportPlanSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  true");
			reportPlanSort_gridtable.func("SelectRow",nextRow);
		});
		/*下移*/
		jQuery("#reportPlanSort_moveDown").bind("click",function(){
			var curRows = reportPlanSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				reportPlanSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var rows = reportPlanSort_gridtable.func("getRows", "");
			var nextRow = +curRows + 1;
			if(nextRow >= rows){
				return;
			}
			reportPlanSort_gridtable.func("SelectRow","-1");
			reportPlanSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  false");
			reportPlanSort_gridtable.func("SelectRow",nextRow);
		});
		/*保存*/
		jQuery("#reportPlanSort_save").bind("click",function(){
			var rows = reportPlanSort_gridtable.func("getRows", "");
			var reportPlanItemsTemp = {};
			for(var i =0; i<rows;i++){
				var itemCode = reportPlanSort_gridtable.func("GetCellData",i +"\r\n itemCode");
				if(reportPlanItems[itemCode]){
					reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
					delete reportPlanItems[itemCode];
				}
			}
			if(reportPlanItems){
				for(var itemCode in reportPlanItems){
					if(reportPlanItems[itemCode]){
						reportPlanItemsTemp[itemCode] = reportPlanItems[itemCode];
					}
				}
			}
			reportPlanItems = cloneObj(reportPlanItemsTemp);
			//reportPlanItems排序
			if(reportPlanItems){
				var reportItemHtml = "";
				var reportPlanItemIndex = 0;
				for(var itemCode in reportPlanItems){
					var reportPlanItem = reportPlanItems[itemCode];
					reportPlanItem.sn = reportPlanItemIndex;
					var colName = reportPlanItem.name;
					reportPlanItemIndex ++;
					reportItemHtml += "<option value='" + itemCode
								+ "'>" + colName + "</option>";
				}
				jQuery("#reportPlanForm_items2").html(reportItemHtml);
			}
			$.pdialog.close("sortReportPlanItems");
		});
		/*关闭*/
		jQuery("#reportPlanSort_close").bind("click",function(){
			$.pdialog.close("sortReportPlanItems");
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="insertrowbutton" href="javaScript:" id="reportPlanSort_moveUp"><span>上移</span></a>
				</li>
				<li>
					<a class="insertrowbutton" href="javaScript:" id="reportPlanSort_moveDown"><span>下移</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:" id="reportPlanSort_save"><span>确定</span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:" id="reportPlanSort_close"><span>关闭</span></a>
				</li>
			</ul>
	</div>
	<div id="reportPlanSort_gridtable_div" layoutH="34" class="grid-wrapdiv" buttonBar="width:500;height:300">
	</div>
	</div>
</div>




