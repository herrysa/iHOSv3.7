<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var kqUpItemSortGridDefine = {
		key:"kqUpItemSort_gridtable",
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
supcanGridMap.put("kqUpItemSort_gridtable",kqUpItemSortGridDefine);
	jQuery(document).ready(function() {
		var kqUpItemSortGrid = cloneObj(supCanTreeListGrid);
		kqUpItemSortGrid.Cols = [{name:'itemId',align:'center',text : '<s:text name="kqUpItem.itemId" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
						           {name:'itemName',align:'left',text : '<s:text name="kqUpItem.itemName" />',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'itemCode',align:'left',text : '<s:text name="kqUpItem.itemCode" />',width:100,isHide:false,editable:false,dataType:'string'},
						           {name:'sn',align:'right',width:50,text : '<s:text name="kqUpItem.sn" />',isHide:"absHide",dataType:'int'},
		 				           {name:'kqTypeName',align:'center',text : '<s:text name="kqUpItem.kqTypeId" />',width:70,isHide:false,editable:false,dataType:'string'},
		 				           {name:'itemType',align:'center',text : '<s:text name="kqUpItem.itemType" />',width:70,isHide:false,editable:false,dataType:'string'},
		 				           {name:'calculateType',align:'center',width:80,text : '<s:text name="kqUpItem.calculateType" />',isHide:false,editable:false,dataType:'string'},
		 				           {name:'remark',align:'left',width:250,text : '<s:text name="kqUpItem.remark" />',isHide:false,editable:false,dataType:'string'}
		 				           ];
		kqUpItemSortGrid.Properties.Title = "考勤项排序";
		kqUpItemSortGrid.Properties.sort = "sn";
		kqUpItemSortGridDefine.main.Build = JSON.stringify(kqUpItemSortGrid);
		var curkKqType = jQuery("#${random}_curkKqType").val();
		jQuery.ajax({
			url: 'kqUpItemGridList?filter_EQS_kqType.kqTypeId='+curkKqType,
			data: {oper:"all"},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var kqUpItems = data.kqUpItems;
				var kqUpItemsGridData = {};
				var kqUpItemObj = [];
				if(kqUpItems){
					jQuery.each(kqUpItems,function(index,kqUpItem){
						var itemType = kqUpItem["itemType"]+"";
						switch(itemType){
							case '0':
								itemType = "数值型";
								break;
							case '1':
								itemType = "字符型";
								break;
							case '2':
								itemType = "日期型";
								break;
							case '3':
								itemType = "整型";
								break;
							default:
								itemType = "数值型";
								break;
						}
						var calculateType = kqUpItem["calculateType"]+"";
						calculateType = calculateType == '0'?'手动':'计算';
						kqUpItemObj[index] = {
								itemId:kqUpItem["itemId"],
								itemName:kqUpItem["itemName"],
								itemCode:kqUpItem["itemCode"],
								sn:kqUpItem["sn"],
								kqTypeName:kqUpItem["kqType"]["kqTypeName"],
								itemType:itemType,
								calculateType:calculateType,
								remark:kqUpItem["remark"]
						};
					});
				}
				kqUpItemsGridData.Record = kqUpItemObj;
				kqUpItemSortGridDefine.main.Load = JSON.stringify(kqUpItemsGridData);
				insertTreeListToDiv("kqUpItemSort_gridtable_div","kqUpItemSort_gridtable","","100%");
			}
		});
		/*上移*/
		jQuery("#kqUpItemSort_moveUp").bind("click",function(){
			var curRows = kqUpItemSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				kqUpItemSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var nextRow = +curRows - 1;
			if(nextRow < 0){
				return;
			}
			kqUpItemSort_gridtable.func("SelectRow","-1");
			kqUpItemSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  true");
			kqUpItemSort_gridtable.func("SelectRow",nextRow);
		});
		/*下移*/
		jQuery("#kqUpItemSort_moveDown").bind("click",function(){
			var curRows = kqUpItemSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				kqUpItemSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var rows = kqUpItemSort_gridtable.func("getRows", "");
			var nextRow = +curRows + 1;
			if(nextRow >= rows){
				return;
			}
			kqUpItemSort_gridtable.func("SelectRow","-1");
			kqUpItemSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  false");
			kqUpItemSort_gridtable.func("SelectRow",nextRow);
		});
		/*保存*/
		jQuery("#kqUpItemSort_save").bind("click",function(){
			alertMsg.confirm("排序后将清空所有考勤上报格式，确认排序?", {
				okCall : function() {
					var rows = kqUpItemSort_gridtable.func("getRows", "");
					var itemIdStr = "";
					for(var i =0; i<rows;i++){
						var itemId = kqUpItemSort_gridtable.func("GetCellData",i +"\r\n itemId");
						itemIdStr += itemId + ",";
					}
					if(itemIdStr){
						itemIdStr = itemIdStr.substring(0,itemIdStr.length-1);
					}
					var url = "kqUpItemSort?navTabId=${navTabId}";
					$.post(url,{itemIdStr:itemIdStr},function(data) {
						formCallBack(data);
					});
				}
			});
		});
		/*关闭*/
		jQuery("#kqUpItemSort_close").bind("click",function(){
			$.pdialog.closeCurrent();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="insertrowbutton" href="javaScript:" id="kqUpItemSort_moveUp"><span>上移</span></a>
				</li>
				<li>
					<a class="insertrowbutton" href="javaScript:" id="kqUpItemSort_moveDown"><span>下移</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:" id="kqUpItemSort_save"><span>确定</span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:" id="kqUpItemSort_close"><span>关闭</span></a>
				</li>
			</ul>
	</div>
	<div id="kqUpItemSort_gridtable_div" layoutH="56" class="grid-wrapdiv" buttonBar="width:500;height:300">
<!-- 			<div id="load_kqUpItemSort_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div> -->
	</div>
	</div>
</div>




