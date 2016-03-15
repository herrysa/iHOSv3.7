<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var codeRule = "2-2-2-2-2-2-2-2-2";
	jQuery(document).ready(function() {
		jQuery("#indicator_codeRule").html(codeRule);
		switchSetFormulaButton(${indicator.leaf});
			
	});
	function switchSetFormulaButton(leaf){
		if(leaf){
			jQuery("#setParentFormulaId").attr("disabled","disabled");
			jQuery("#setLeafFormulaId").removeAttr("disabled");
		}else{
			jQuery("#setLeafFormulaId").attr("disabled","disabled");
			jQuery("#setParentFormulaId").removeAttr("disabled");
		}
	}
	function refreshIndicatorTree(data){
		var indicatorNode = data.indicator;
		var parentId = data.parentId;
		var indicatorTreeObj = $.fn.zTree.getZTreeObj("indicatorTree");
		var pNode = indicatorTreeObj.getNodeByParam("id", parentId, null);
		if('${entityIsNew}'=="true"){
			var addNode = {id:indicatorNode.id,pId:parentId,name:indicatorNode.nameWithCode,isParent:!indicatorNode.leaf}
			indicatorTreeObj.addNodes(pNode, addNode,false);//参数最后一个为true时不自动展开父节点
			var url = "${ctx}/editIndicator?parentId="+parentId+"&id="+indicatorNode.id; 
			reloadIndicatorForm(url);
		}else{
			var oldIndicatorName = "${indicator.name}";
			var oldIndicatorLeaf = "${indicator.leaf}";
			var newIndicatorName = indicatorNode.name;
			var newIndicatorLeaf = ""+indicatorNode.leaf;
			if(oldIndicatorName != newIndicatorName || oldIndicatorLeaf != newIndicatorLeaf){
				var indicatorId = "${indicator.id}";
				var updateNode = indicatorTreeObj.getNodeByParam("id", indicatorId, null);
				updateNode.name = indicatorNode.nameWithCode;
				updateNode.isParent = !indicatorNode.leaf;
				indicatorTreeObj.updateNode(updateNode);
			}
		}
		formCallBack(data);
	}
	
	function checkIndiactorName(obj){
		/*同一个父亲的孩子名字不能重复*/
		if(!obj.value){
			return;
		}
		$.ajax({
		    url: 'checkIndiactorName',
		    type: 'post',
		    data:{name:obj.value,indicatorTypeId:"${indicator.indicatorType.code}",parentId:"${parentId}"},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data!=null){
				     obj.value="";
		        	 alertMsg.error(data.message);
		        }
		    }
		});
	}
	
	function checkIndiactorCode(obj){
		if(!obj.value){
			return;
		}
		var parentCode = "${indicator.code}";
		if(isMatch(obj,codeRule)){
			var url = 'checkIndiactorCode';
			var indiactorTypeId = "${indicator.indicatorType.code}";
			url = encodeURI(url);
			$.ajax({
			    url: url,
			    type: 'post',
			    data:{code:obj.value,indicatorTypeId:indiactorTypeId,returnMessage:"此编码已存在"},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(data!=null){
					     obj.value=parentCode;
			        	 alertMsg.error(data.message);
			        }
			    }
			});
		}else{
			alertMsg.error("请填写符合规则的编码");
			obj.value=parentCode;
		}
	}
	function isMatch(obj,codeRule){
		if(obj instanceof Object){
			obj = obj.value;
		}
		var rules = codeRule.split('-');
		var num = 0;
		for(index in rules){
			num += Number(rules[index]);
			if(obj.length<num){
				break;
			}
			if(obj.length==num){
				return true;
			}
		}
		return false;
	}
	
	function editIndicatorLeaf(obj){
		var indicatorTreeObj = $.fn.zTree.getZTreeObj("indicatorTree");
		var editNode = indicatorTreeObj.getNodeByParam("id", "${indicator.id}", null);
		if(editNode.children){
			alertMsg.error("当前指标有下级指标，不能修改末级标志");
		    return false;
		}else{
			switchSetFormulaButton(obj.checked);
			return true;
		}
	}
	function editIndicatorToPercent(obj){
		if(obj.checked){
			jQuery("#indicator_unit").val("%");
		}else{
			jQuery("#indicator_unit").val("");
		}
		return true;
	}
	function setParentFormula(){
		$.pdialog.open('${ctx}/setIndicatorFormula?id=${indicator.id}','setIndicatorFormula','设置公式', {mask:true,width : 600,height : 480});
	}
	function setLeafFormula(){
		$.pdialog.open('${ctx}/setIndicatorLeafFormula?realFormula='+jQuery("#indicator_realFormula").val(),'setIndicatorLeafFormula','选取指标', {mask:true,width : 600,height : 480});
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="indicatorForm" method="post" action="saveIndicator?popup=true&entityIsNew=${entityIsNew}" class="pageForm required-validate" onsubmit="return validateCallback(this,refreshIndicatorTree);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="indicator.id"/>
					<s:hidden key="indicator.level"/>
					<s:hidden key="indicator.leftOper"/>
					<s:hidden key="indicator.rightOper"/>
					<s:hidden key="indicator.indicatorType.code"/>
					<s:hidden key="indicator.parent.id"/>
					<s:hidden key="indicator.realFormula" id="indicator_realFormula"/>
				</div>
				<div class="unit">
					<s:textfield key="indicator.parent" value="%{parentName}" readonly="true" disabled="true" size="50" cssClass="textInput readonly"/>
					<s:hidden name="parentId" value="%{parentId}"/>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="indicator.code" name="indicator.code" cssClass="required" onblur="checkIndiactorCode(this)" size="50"/>
						编码规则：<span id="indicator_codeRule"></span>
					</s:if>
					<s:else>
						<s:textfield key="indicator.code" name="indicator.code" cssClass="required textInput readonly" readonly="true" size="50"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="indicator.name" name="indicator.name" cssClass="required" size="50" onblur="checkIndiactorName(this)"/>
				</div>
				<div class="unit">
					<%-- <label><s:text name="indicator.unit"></s:text>:</label>
					<s:select list="#{'':'',,'元':'元','万元':'万元','百分比':'百分比'}" theme="simple"
						key="indicator.unit" listKey="key" listValue="value" style="width:70px"/> --%>
					<s:textfield key="indicator.unit" name="indicator.unit" cssClass="textInput" size="50" id="indicator_unit"/>
				</div>
				<div class="unit">
					<s:textfield key="indicator.precision" name="indicator.precision" cssClass="digits textInput" size="10"/>
					<span style="float: left; width: 150px">
						<label><s:text name="indicator.toPercent"></s:text>:</label>
						<s:checkbox key="indicator.toPercent" name="indicator.toPercent" theme="simple" onclick="return editIndicatorToPercent(this)"/>
					</span>
				</div>
				<div class="unit">
					<span style="float: left; width: 214px">
						<label><s:text name="indicator.leaf"></s:text>:</label>
						<s:checkbox key="indicator.leaf" name="indicator.leaf" theme="simple" onclick="return editIndicatorLeaf(this)"/>
					</span>
					<span style="float: left; width: 150px">
						<label><s:text name="indicator.needSeparator"></s:text>:</label>
						<s:checkbox key="indicator.needSeparator" name="indicator.needSeparator" theme="simple"/>
					</span>
				</div>
				<div class="unit">
					<s:textarea id="indicator_formula" key="indicator.formula" name="indicator.formula" cssClass="textInput" 
							cols="43" rows="8" style="overflow-x:hidden;resize:none"/>
					<br/><br/><br/><br/>
					<input id="setParentFormulaId" type="button" value="设置" onclick="setParentFormula()"/><br/><br/>
					<input id="setLeafFormulaId" type="button" value="选取指标" onclick="setLeafFormula()"/>
				</div>
				<div class="unit">
					<s:textarea key="indicator.remark" name="indicator.remark" cssClass="textInput" cols="43" rows="3" style="overflow-x:hidden"/>
				</div>
			</div>
		</form>
	</div>
</div>
