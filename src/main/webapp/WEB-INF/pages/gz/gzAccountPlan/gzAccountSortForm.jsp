<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var gzAccountSortGridDefine = {
		key:"gzAccountSort_gridtable",
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
supcanGridMap.put("gzAccountSort_gridtable",gzAccountSortGridDefine);
	jQuery(document).ready(function() {
		var gzAccountSortGrid = cloneObj(supCanTreeListGrid);
		gzAccountSortGrid.Cols = [{name:'itemCode',align:'left',text : '项目编码',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'name',align:'left',text : '项目名称',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'oldName',align:'left',text : '显示名称',width:100,isHide:false,editable:false,dataType:'string'}
//		 				           {name:'sn',align:'right',width:50,text : '<s:text name="gzAccount.sn" />',isHide:"absHide",dataType:'int'}
		 				           ];
		gzAccountSortGrid.Properties.Title = "工资项排序";
		gzAccountSortGridDefine.main.Build = JSON.stringify(gzAccountSortGrid);
		var gzAccountObj = [];
		if(gzAccountItems){
			for(var itemCode in gzAccountItems){
				var gzAccountItem = gzAccountItems[itemCode];
				var name = gzAccountItem.name;
				var oldName = gzAccountItem.oldName; 
				gzAccountObj.push({itemCode:itemCode,name:name,oldName:oldName});
			}
		}
		var gzAccountsGridData = {};
		gzAccountsGridData.Record = gzAccountObj;
		gzAccountSortGridDefine.main.Load = JSON.stringify(gzAccountsGridData);
		insertTreeListToDiv("gzAccountSort_gridtable_div","gzAccountSort_gridtable","","100%");
		/*上移*/
		jQuery("#gzAccountSort_moveUp").bind("click",function(){
			var curRows = gzAccountSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				gzAccountSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var nextRow = +curRows - 1;
			if(nextRow < 0){
				return;
			}
			gzAccountSort_gridtable.func("SelectRow","-1");
			gzAccountSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  true");
			gzAccountSort_gridtable.func("SelectRow",nextRow);
		});
		/*下移*/
		jQuery("#gzAccountSort_moveDown").bind("click",function(){
			var curRows = gzAccountSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				gzAccountSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var rows = gzAccountSort_gridtable.func("getRows", "");
			var nextRow = +curRows + 1;
			if(nextRow >= rows){
				return;
			}
			gzAccountSort_gridtable.func("SelectRow","-1");
			gzAccountSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  false");
			gzAccountSort_gridtable.func("SelectRow",nextRow);
		});
		/*保存*/
		jQuery("#gzAccountSort_save").bind("click",function(){
			var rows = gzAccountSort_gridtable.func("getRows", "");
			var gzAccountItemsTemp = {};
			for(var i =0; i<rows;i++){
				var itemCode = gzAccountSort_gridtable.func("GetCellData",i +"\r\n itemCode");
				if(gzAccountItems[itemCode]){
					gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
					delete gzAccountItems[itemCode];
				}
			}
			if(gzAccountItems){
				for(var itemCode in gzAccountItems){
					if(gzAccountItems[itemCode]){
						gzAccountItemsTemp[itemCode] = gzAccountItems[itemCode];
					}
				}
			}
			gzAccountItems = cloneObj(gzAccountItemsTemp);
			//gzAccountItems排序
			if(gzAccountItems){
				var gzItemHtml = "";
				var gzAccountItemIndex = 0;
				for(var itemCode in gzAccountItems){
					var gzAccountItem = gzAccountItems[itemCode];
					gzAccountItem.sn = gzAccountItemIndex;
					var colName = gzAccountItem.name;
					gzAccountItemIndex ++;
					gzItemHtml += "<option value='" + itemCode
								+ "'>" + colName + "</option>";
				}
				jQuery("#${random}gz_items2").html(gzItemHtml);
			}
			$.pdialog.close("sortGzAccountPlan");
		});
		/*关闭*/
		jQuery("#gzAccountSort_close").bind("click",function(){
			$.pdialog.close("sortGzAccountPlan");
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="insertrowbutton" href="javaScript:" id="gzAccountSort_moveUp"><span>上移</span></a>
				</li>
				<li>
					<a class="insertrowbutton" href="javaScript:" id="gzAccountSort_moveDown"><span>下移</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:" id="gzAccountSort_save"><span>确定</span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:" id="gzAccountSort_close"><span>关闭</span></a>
				</li>
			</ul>
	</div>
	<div id="gzAccountSort_gridtable_div" layoutH="34" class="grid-wrapdiv" buttonBar="width:500;height:300">
	</div>
	</div>
</div>




