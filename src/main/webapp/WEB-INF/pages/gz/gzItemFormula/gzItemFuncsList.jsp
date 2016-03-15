<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var funcArr = new Array();
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#gzContentForm").attr("action","saveGzContent?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#gzContentForm").submit();
		});*/
		var funcStr = gzFuncs;
		if(funcStr){
			funcArr = funcStr.split(";");
			jQuery.each(funcArr, function (key, value) {
				 jQuery("#gzItemFuncsList_funcs").get(0).options.add(new Option(value,value));
	            });
			var index = jQuery.inArray(funcArr[0], funcArr)
			if(index != -1){
				var htmlText = funcsNote[index];
				jQuery("#gzItemFuncsList_funcsNote").html(htmlText);
			}
		}
		jQuery("#gzItemFuncsList_funcs").bind('change',function(){
			var value = this.value;
			var index = jQuery.inArray(value, funcArr)
			if(index != -1){
				var htmlText = funcsNote[index];
				jQuery("#gzItemFuncsList_funcsNote").html(htmlText);
			}
		});
	});
	function closeGzItemFuncsListDialog(){
		$.pdialog.close('gzItemFuncsList');
	}
	function gzItemFuncsAdd(){
		var value = jQuery("#gzItemFuncsList_funcs").val();
		jQuery("#"+"${navTabId}").insertAtCousor(value);
// 		var oldValue = jQuery("#"+"${navTabId}").val();
// 		oldValue = oldValue + value;
// 		jQuery("#"+"${navTabId}").val(oldValue);
		closeGzItemFuncsListDialog();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzContentForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div style="width:250px;margin: auto">
					<div>
						函数<select id="gzItemFuncsList_funcs" style="float:none"></select>
					</div>
					<div id="gzItemFuncsList_funcsNote" style="border:1px solid #5bc0de;width:250px;height:280px;margin-top: 3px;overflow:auto;">
					
					</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzItemFuncsAdd()">确定</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closeGzItemFuncsListDialog();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





