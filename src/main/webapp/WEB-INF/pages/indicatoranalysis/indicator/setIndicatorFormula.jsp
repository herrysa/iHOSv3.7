<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	function moveIndicator(obj){
		var selObj = jQuery(obj).find("option:selected");
		var indicatorName = "["+selObj.text()+"]";
		var indicatorId = selObj.val();
		var oldNameValue = jQuery("#setIndicatorFormula_formula").val();
		jQuery("#setIndicatorFormula_formula").focus().val(oldNameValue+indicatorName);
	}
	
	function validateFormula(type){
		//var reg = new RegExp("^(\[[\u4e00-\u9fa5|\\w]+\][\+|\\-|\*|\/])*(\[[\u4e00-\u9fa5|\\w]+\]){1}$");  
		var exp = "^((([\(](((\[[\u4e00-\u9fa5|\\w]+\])|\\d+)[\+|\\-|\*|\/])*((\[[\u4e00-\u9fa5|\\w]+\])|\\d+){1}[\)])|((\[[\u4e00-\u9fa5|\\w]+\])|\\d+))[\+|\\-|\*|\/])*(([\(](((\[[\u4e00-\u9fa5|\\w]+\])|\\d+)[\+|\\-|\*|\/])*((\[[\u4e00-\u9fa5|\\w]+\])|\\d+){1}[\)])|((\[[\u4e00-\u9fa5|\\w]+\])|\\d+)){1}$";
		var reg = new RegExp(exp);  
		var formula = jQuery("#setIndicatorFormula_formula").val();
		if(reg.test(formula)){
			 if(arguments.length==1 && type=='validate'){
				var idFormula = getIdFormula(formula);
				if(idFormula==false){
					return;
				}
				alertMsg.correct("公式合法！");
			 }
			return true;
		}else{
			alertMsg.error("公式不合法！");
			return false;
		}
	}
	
	function saveIndicatorFormula(){
		if(validateFormula()){
			var formula = jQuery("#setIndicatorFormula_formula").val();
			var idFormula = getIdFormula(formula);
			jQuery("#indicator_formula").val(formula);
			jQuery("#indicator_realFormula").val(idFormula);
			$.pdialog.closeCurrent();
		}
	}
	function getIdFormula(value){
		var idValue = value;
		var prevIndex = value.indexOf('[');
		var endIndex = value.indexOf(']');
		var name = "",rName = "";
		var errorName;
		while(prevIndex>=0 && endIndex>=0){
			name = value.substring(prevIndex, endIndex+1);
			rName = name.substring(1, name.length-1);
			var id = getIdByName(rName);
			if(!id){
				errorName = name;
				break;
			}
			value = value.replace(name, id);
			idValue = idValue.replace(rName, id);
			prevIndex = value.indexOf('[');
			endIndex = value.indexOf(']');
		}
		if(errorName){
			alertMsg.error(errorName+"不存在！");
			return false;
		}
		return idValue;
	}
	function getIdByName(name){
		var id;
		var jsonArray=[];
		<c:forEach var="item" items="${childIndicators}">
		   jsonArray.push({name:"${item.name}",id:"${item.id}"});
		</c:forEach>
		for(var i=0;i<jsonArray.length;i++){
			if(name==jsonArray[i].name){
				id = jsonArray[i].id;
				break;
			}
		}
		return id;
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<s:textarea key="indicator.formula" id="setIndicatorFormula_formula" cols="50" rows="10" style="overflow-x:hidden;resize:none" value="%{indicator.formula}"/>
			</div>
			<div class="unit">
				<label><s:text name="指标"></s:text>:</label>
				<s:select list="childIndicators" listKey="id" listValue="name" ondblclick="moveIndicator(this)"
					emptyOption="false" theme="simple" style="width:322px" size="8"/>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="validateFormula('validate')">检测</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="saveIndicatorFormula()">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</div>
