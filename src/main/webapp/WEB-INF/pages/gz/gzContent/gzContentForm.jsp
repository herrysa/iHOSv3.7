<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#gzContentForm_rowId").val("${id}");
		if(gzContent_gridtable){
			var rowId = "${id}";
			var cols = gzContent_gridtable.func("GetCols","");//获取列数
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			var unitFlag = true;
			var htmlStr = "";
			if(cols > 0&&rows>0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var isAbsHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
					var isHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isHide");//隐藏
					if(isAbsHide == 1||isHide == 1){
						continue;
					}
					var colName = gzContent_gridtable.func("GetColProp",colIndex+" \r\n name");//name属性
					var colText = gzContent_gridtable.func("GetColProp",colIndex+" \r\n text");//列名
					var editable = gzContent_gridtable.func("GetColProp",colIndex+" \r\n editable");//是否可修改
					var dataType = gzContent_gridtable.func("GetColProp",colIndex+" \r\n dataType");//数据类型
					var value = gzContent_gridtable.func("GetCellData",rowId+" \r\n "+colIndex);//列的值
	 				if(unitFlag){
						htmlStr += '<div class="unit">';
						htmlStr += '<label>'+colText+'</label>';
						htmlStr += '<span  class="formspan" style="float: left; width: 140px">';
						switch(dataType){
							case "string":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
							case　"double":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" class="number" readonly="readonly">';
								break;
							case "int":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" class="digits" readonly="readonly">';
								break;
							case "date":
								if(value){
									value = new Date(value).format('yyyy-MM-dd');
								}
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
							default:
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
						}
						htmlStr += '</span>';
						unitFlag = false;
					}else{
						htmlStr += '<label>'+colText+'</label>';
						htmlStr += '<span  class="formspan" style="float: left; width: 140px">';
						switch(dataType){
							case "string":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
							case　"double":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" class="number" readonly="readonly">';
								break;
							case "int":
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" class="digits" readonly="readonly">';
								break;
							case "date":
								if(value){
									value = new Date(value).format('yyyy-MM-dd');
								}
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
							default:
								htmlStr += '<input type="text" name="'+colName+'" value="'+value+'" readonly="readonly">';
								break;
						}
						htmlStr += '</span>';
						htmlStr += '</div>';
						unitFlag = true;
					}  
				}
			}
			if(htmlStr){
				var endStr = htmlStr.substr(htmlStr.length - 4);
				if(endStr != "div>"){
					htmlStr += '</div>';
				}
			}
			jQuery("#gzContentForm_pageFormContent").html(htmlStr);
		}
		adjustFormInput("gzContentForm");
		gzContentFormEditRow();
	});
	/*首条*/
	function gzContentFormFirstRow(){
		if(gzContent_gridtable){
			var rowId = jQuery("#gzContentForm_rowId").val();
			//var rows = gzContent_gridtable.func("getRows","");//获取行数
			gzContentFormSaveRow();
			rowId = 0;
			gzContentFormDataReload(rowId);
		}
	}
	/*上条*/
	function gzContentFormPreviewRow(){
		if(gzContent_gridtable){
			var rowId = jQuery("#gzContentForm_rowId").val();
			gzContentFormSaveRow();
			//var rows = gzContent_gridtable.func("getRows","");//获取行数
			if(rowId == 0){
				rowId = 0;
				gzContentFormDataReload(rowId);
			}else{
				rowId = +rowId - 1;
				gzContentFormDataReload(rowId);
			}
		}
	}
	/*下条*/
	function gzContentFormNextRow(){
		if(gzContent_gridtable){
			var rowId = jQuery("#gzContentForm_rowId").val();
			gzContentFormSaveRow();
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			if(rowId == (+rows-2)){
				rowId = +rows-2;
				gzContentFormDataReload(rowId);
			}else{
				rowId = +rowId + 1;
				gzContentFormDataReload(rowId);
			}
		}
	}
	/*末条*/
	function gzContentFormLastRow(){
		if(gzContent_gridtable){
			var rowId = jQuery("#gzContentForm_rowId").val();
			gzContentFormSaveRow();
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			rowId = +rows-2;
			gzContentFormDataReload(rowId);
		}
	}
	/*修改*/
	function gzContentFormEditRow(){
		if(gzContent_gridtable){
			var cols = gzContent_gridtable.func("GetCols","");//获取列数
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			if(cols > 0&&rows>0){
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var isAbsHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
					var isHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isHide");//隐藏
					if(isAbsHide == 1||isHide == 1){
						continue;
					}
					var colName = gzContent_gridtable.func("GetColProp",colIndex+" \r\n name");//name属性
					var editable = gzContent_gridtable.func("GetColProp",colIndex+" \r\n editable");//是否可修改
					var dataType = gzContent_gridtable.func("GetColProp",colIndex+" \r\n dataType");//数据类型
					if(editable == 1){
						jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').removeAttr("readonly").removeClass("readonly");
						if(dataType == "date"){
							jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').bind("focus",function(){
								WdatePicker({skin:"ext",isShowClear:true,readOnly:true});
							});
						} 
					}
				}
			}
		}
	}
	/*确定*/
	function gzContentFormSaveRow(){
		if(curPeriodStatus||curCheckStatus){
			return;
		}
		if(gzContent_gridtable){
			var cols = gzContent_gridtable.func("GetCols","");//获取列数
			var rows = gzContent_gridtable.func("getRows","");//获取行数
			var rowId = jQuery("#gzContentForm_rowId").val();
			if(cols > 0&&rows>0){
				var gzIdTemp =  gzContent_gridtable.func("GetCellData",rowId+"\r\n gzId");
				var rowIndex = jQuery.inArray(gzIdTemp, gzIds);
				for(var colIndex = 0;colIndex < cols;colIndex++){
					var isAbsHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
					var isHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isHide");//隐藏
					if(isAbsHide == 1||isHide == 1){
						continue;
					}
					var colName = gzContent_gridtable.func("GetColProp",colIndex+" \r\n name");//name属性
					var editable = gzContent_gridtable.func("GetColProp",colIndex+" \r\n editable");//是否可修改
					var dataType = gzContent_gridtable.func("GetColProp",colIndex+" \r\n dataType");//数据类型
					if(editable == 1){
// 						jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').attr("readonly","readonly").addClass("readonly");
// 						if(dataType == "date"){
// 							jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').unbind("focus");
// 						}
						var value = jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').val();
						if(rowIndex > -1){
							gzContents[rowIndex]["isEdit"] = '1';
							gzContents[rowIndex][colName] = value;
						}
						gzContent_gridtable.func("SetCellData", rowId +" \r\n "+colName+" \r\n"+value);
					}
				}
				if(rowIndex > -1){
					gzContentCalculate(rowIndex,rowId);
				}
				gzContentFormDataReload(rowId);
			}
		}
	}
	/*取消*/
	function gzContentFormCancelRow(){
		if(gzContent_gridtable){
			var rowId = jQuery("#gzContentForm_rowId").val();
			gzContentFormDataReload(rowId);
		}
	}
	/*关闭*/
	function gzContentFormCloseRow(){
		$.pdialog.closeCurrent();
	}
	
	/*工资数据填充*/
	function gzContentFormDataReload(rowId){
		gzContent_gridtable.func("SelectRow",rowId);
		jQuery("#gzContentForm_rowId").val(rowId);
		var cols = gzContent_gridtable.func("GetCols","");//获取列数
		var rows = gzContent_gridtable.func("getRows","");//获取行数
		if(cols > 0&&rows>0){
			for(var colIndex = 0;colIndex < cols;colIndex++){
				var isAbsHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isAbsHide");//绝对隐藏
				var isHide = gzContent_gridtable.func("GetColProp",colIndex+" \r\n isHide");//隐藏
				if(isAbsHide == 1||isHide == 1){
					continue;
				}
				var colName = gzContent_gridtable.func("GetColProp",colIndex+" \r\n name");//name属性
				var dataType = gzContent_gridtable.func("GetColProp",colIndex+" \r\n dataType");//数据类型
				var value = gzContent_gridtable.func("GetCellData",rowId+" \r\n "+colIndex);//列的值
				if(value&&dataType=="date"){
					value = new Date(value).format('yyyy-MM-dd');
				}
				jQuery('#gzContentForm_pageFormContent input[name="'+colName+'"]').val(value);
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:gzContentFormFirstRow()" ><span>首条</span></a>
				</li>
				<li>
					<a class="addbutton" href="javaScript:gzContentFormPreviewRow()" ><span>上条</span></a>
				</li>
				<li>
					<a class="addbutton" href="javaScript:gzContentFormNextRow()" ><span>下条</span></a>
				</li>
				<li>
					<a class="addbutton" href="javaScript:gzContentFormLastRow()" ><span>末条</span></a>
				</li>
<!-- 				<li>
					<a class="addbutton" href="javaScript:gzContentFormEditRow()" ><span>修改</span></a>
				</li>
 -->				<li>
					<a class="savebutton" href="javaScript:gzContentFormSaveRow()" ><span>确定</span></a>
				</li>
				<li>
					<a class="canceleditbutton" href="javaScript:gzContentFormCancelRow()" ><span>取消</span></a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:gzContentFormCloseRow()" ><span>关闭</span></a>
				</li>
			</ul>
	</div>
		<form id="gzContentForm" method="post"	action="saveGzContent?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div><input id="gzContentForm_rowId" style="display: none"></div>
			<div class="pageFormContent" layoutH="56" id="gzContentForm_pageFormContent">
			</div>
		</form>
	</div>
</div>





