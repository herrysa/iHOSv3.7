<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var gzItemSortGridDefine = {
		key:"gzItemSort_gridtable",
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
supcanGridMap.put("gzItemSort_gridtable",gzItemSortGridDefine);
	jQuery(document).ready(function() {
		var gzItemSortGrid = cloneObj(supCanTreeListGrid);
		gzItemSortGrid.Cols = [{name:'itemId',align:'center',text : '<s:text name="gzItem.itemId" />',width:80,isHide:"absHide",editable:false,dataType:'string'},
					           {name:'itemName',align:'left',text : '<s:text name="gzItem.itemName" />',width:100,isHide:false,editable:false,dataType:'string'},
					           {name:'itemShowName',align:'left',text : '<s:text name="gzItem.itemShowName" />',width:100,isHide:false,editable:false,dataType:'string'},
					           {name:'itemCode',align:'left',text : '<s:text name="gzItem.itemCode" />',width:100,isHide:false,editable:false,dataType:'string'},
					           {name:'sn',align:'right',width:50,text : '<s:text name="gzItem.sn" />',isHide:"absHide",dataType:'int'},
	 				           {name:'gzTypeName',align:'center',text : '<s:text name="gzItem.gzTypeId" />',width:70,isHide:false,editable:false,dataType:'string'},
	 				           {name:'itemType',align:'center',text : '<s:text name="gzItem.itemType" />',width:70,isHide:false,editable:false,dataType:'string'},
	 				           {name:'calculateType',align:'center',width:80,text : '<s:text name="gzItem.calculateType" />',isHide:false,editable:false,dataType:'string'},
	 				           {name:'gzContentHide',align:'center',width:80,text : '<s:text name="gzItem.gzContentHide" />',isHide:false,editable:false,dataType:'bool',editType:'Check'},
	 				           {name:'disabled',align:'center',width:80,text : '<s:text name="gzItem.disabled" />',isHide:false,editable:false,dataType:'bool',editType:'Check'},
	 				           {name:'sysField',align:'center',width:80,text : '<s:text name="gzItem.sysField" />',isHide:false,editable:false,dataType:'bool',editType:'Check'},
	 				           {name:'remark',align:'left',width:250,text : '<s:text name="gzItem.remark" />',isHide:false,editable:false,dataType:'string'}
	 				           ];
		gzItemSortGrid.Properties.Title = "工资项排序";
		gzItemSortGrid.Properties.sort = "sn";
		gzItemSortGridDefine.main.Build = JSON.stringify(gzItemSortGrid);
// 		var gzType = jQuery("#gz_type").val();
		jQuery.ajax({
			url: 'gzItemGridList?filter_EQS_gzType.gzTypeId=${gzTypeId}',
			data: {oper:"all"},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
			},
			success: function(data){
				var gzItems = data.gzItems;
				var gzItemsGridData = {};
				var gzItemObj = [];
				if(gzItems){
					jQuery.each(gzItems,function(index,gzItem){
						var itemType = gzItem["itemType"]+"";
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
						var calculateType = gzItem["calculateType"]+"";
						calculateType = calculateType == '0'?'手动':'计算';
						gzItemObj[index] = {
								itemId:gzItem["itemId"],
								itemName:gzItem["itemName"],
								itemShowName:gzItem["itemShowName"],
								itemCode:gzItem["itemCode"],
								sn:gzItem["sn"],
								gzTypeName:gzItem["gzType"]["gzTypeName"],
								itemType:itemType,
								calculateType:calculateType,
								gzContentHide:gzItem["gzContentHide"],
								disabled:gzItem["disabled"],
								sysField:gzItem["sysField"],
								remark:gzItem["remark"]
						};
					});
				}
				gzItemsGridData.Record = gzItemObj;
				gzItemSortGridDefine.main.Load = JSON.stringify(gzItemsGridData);
				insertTreeListToDiv("gzItemSort_gridtable_div","gzItemSort_gridtable","","100%");
			}
		});
		/*上移*/
		jQuery("#gzItemSort_moveUp").bind("click",function(){
			var curRows = gzItemSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				gzItemSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var nextRow = +curRows - 1;
			if(nextRow < 0){
				return;
			}
			gzItemSort_gridtable.func("SelectRow","-1");
			gzItemSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  true");
			gzItemSort_gridtable.func("SelectRow",nextRow);
		});
		/*下移*/
		jQuery("#gzItemSort_moveDown").bind("click",function(){
			var curRows = gzItemSort_gridtable.func("GetCurrentRow","");
			if(!curRows||curRows.indexOf(",") >=0){
				gzItemSort_gridtable.func("MessageBoxFloat", "请选择一条记录！" + "\r\n Warning \r\n Information");
				return;
			}
			var rows = gzItemSort_gridtable.func("getRows", "");
			var nextRow = +curRows + 1;
			if(nextRow >= rows){
				return;
			}
			gzItemSort_gridtable.func("SelectRow","-1");
			gzItemSort_gridtable.func("MoveRow", curRows +" \r\n " + nextRow +"\r\n  false");
			gzItemSort_gridtable.func("SelectRow",nextRow);
		});
		/*保存*/
		jQuery("#gzItemSort_save").bind("click",function(){
			var rows = gzItemSort_gridtable.func("getRows", "");
			var itemIdStr = "";
			for(var i =0; i<rows;i++){
				var itemId = gzItemSort_gridtable.func("GetCellData",i +"\r\n itemId");
				itemIdStr += itemId + ",";
			}
			if(itemIdStr){
				itemIdStr = itemIdStr.substring(0,itemIdStr.length-1);
			}
			var url = "gzItemSort?navTabId=${navTabId}";
			$.post(url,{itemIdStr:itemIdStr},function(data) {
				formCallBack(data);
			});
		});
		/*关闭*/
		jQuery("#gzItemSort_close").bind("click",function(){
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
					<a class="insertrowbutton" href="javaScript:" id="gzItemSort_moveUp"><span>上移</span></a>
				</li>
				<li>
					<a class="insertrowbutton" href="javaScript:" id="gzItemSort_moveDown"><span>下移</span></a>
				</li>
				<li>
					<a class="savebutton" href="javaScript:" id="gzItemSort_save"><span>确定</span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:" id="gzItemSort_close"><span>关闭</span></a>
				</li>
			</ul>
	</div>
	<div id="gzItemSort_gridtable_div" layoutH="30" class="grid-wrapdiv" buttonBar="width:500;height:300">
<!-- 			<div id="load_gzItemSort_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div> -->
	</div>
	</div>
</div>




