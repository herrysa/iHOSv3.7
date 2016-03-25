
<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#${random}savelink').click(function() {
			jQuery("#funcDefineForm").attr("action","saveFuncDefine?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#funcDefineForm").submit();
		});*/

	});
	/*检查名称是否有重复*/
	function checkFuncSame(funcDefine_data) {
		var checkValue = funcDefine_data.value;
		var checkId = "#" + funcDefine_data.id + "_check";
		var checkType = funcDefine_data.id.split("_")[1];
		//console.info(checkType);
		//var msg = jQuery(checkId).value;
		jQuery.ajax({
			url: "checkFuncSame",
		    data:{checkValue:checkValue,checkType:checkType},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data){
		        	alertMsg.error("名称已存在!");
		        	jQuery(checkId).val("名称已存在!");
					return;
		        }else{
		        	jQuery(checkId).val("");
		        }
		    }
		});
				
	}
	/*检查参数*/
	function checkParam(checkedParam) {
		//输入框如果为空，显示默认提示信息
		if(!checkedParam.value){
			checkedParam.style.color="#999";
			checkedParam.value=checkedParam.defaultValue;
		} else {
			var regex = new RegExp("^(\{[a-zA-Z]{1,10}\}|\{[a-zA-Z]{1,10}\},){0,10}$");
			if(!regex.test(checkedParam.value)) {
				alertMsg.error("参数输入格式有误，请参照：{demo},{demo}(大括号内英文字符个数不得多余10个,参数的个数不得多余10个)！");
				checkedParam.value = "";
				return;
			}
		}
		
			
	}
	/*检查函数体*/
	function checkBody(checkedBody) {
		var checkedParam = jQuery("#funcDefine_funcParam").val();
		var paramArray = checkedParam.split(",");
		var regex = new RegExp("\{[a-zA-Z]{1,10}\}");
		/*var bodyString = regex.exec(checkedBody.value);*/
		if (checkedBody.value) {
			if(checkedParam) {
				for (var i = 0; i <= paramArray.length - 1; i++) {
					if (paramArray[i]) {
						var flag = checkedBody.value.indexOf(paramArray[i]);
						if (flag == -1) {
							alertMsg.error("请检查函数体，确认定义的参数已全部使用,且没有使用未定义的参数！");
						}
					}
				}
			}else{
				if(regex.test(checkedBody.value)) {
					alertMsg.error("请检查函数体，确认定义的参数已全部使用,且没有使用未定义的参数！");
				}
			}
		}

	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="funcDefineForm" method="post"
			action="saveFuncDefine?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="funcDefine.funcId" />
				</div>
				<div class="unit">
					<input id="funcDefine_funcCode_check" type="hidden">
					<s:textfield id="funcDefine_funcCode" key="funcDefine.funcCode"
						name="funcDefine.funcCode" cssClass="required"
						onblur="checkFuncSame(this);" />
				</div>
				<div class="unit">
					<s:textfield id="funcDefine_funcName" key="funcDefine.funcName"
						name="funcDefine.funcName" cssClass="required"
						onblur="checkFuncSame(this);" />
				</div>
				<%-- <div class="unit">
					<s:select list="#{'0':'表内函数','1':'表间函数' }"
						key="funcDefine.funcType" id="funcDefine_funcType" listKey="key"
						listValue="value" emptyOption="false" maxlength="50" width="50px"></s:select>
				</div> --%>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id="funcDefine_funcParam" key="funcDefine.funcParam"
							name="funcDefine.funcParam" cssClass=""></s:textfield>
					</s:if>
					<s:else>
						<s:textfield id="funcDefine_funcParam" key="funcDefine.funcParam"
							name="funcDefine.funcParam" cssClass="" value="参数为英文字符，参数之间用逗号分割"
							onFocus="if(value==defaultValue){value='';this.style.color='#000';}"
							onBlur="checkParam(this);" style="color:#999;width:300px;" />
					</s:else>
				</div>
				<div class="unit">
					<s:textarea id="funcDefine_funcBody" key="funcDefine.funcBody"
						name="funcDefine.funcBody" onblur="checkBody(this);"
						cssClass="required" style="width: 300px;height: 100px;" />
				</div>
				<div class="unit">
					<s:textarea id="funcDefine_funcDesc" key="funcDefine.funcDesc"
						name="funcDefine.funcDesc" cssClass="required"
						style="width: 300px;height: 60px;" />
				</div>
				<div class="unit">
					<s:textarea id="funcDefine_remark" key="funcDefine.remark"
						name="funcDefine.remark" cssClass=""
						style="width: 300px;height: 40px;" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<s:text name="button.save" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





