<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#statisticsTypeForm").attr("action","saveStatisticsType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#statisticsTypeForm").submit();
		});
	});
	function savestatisticsTypeForm(data){
		formCallBack(data);
		  if(data.statusCode!=200){
			   return;
			  }
		  var statisticsType = data.statisticsType;
		  var parentId = data.parentId;
		  var statisticsTypeTreeObj = $.fn.zTree.getZTreeObj("statisticsTypeTreeLeft");
		  if('${entityIsNew}'=="true"){//  add 获取父级节点，绑定至父级节点上
			  var pNode = statisticsTypeTreeObj.getNodeByParam("id", parentId, null);
			  var nodeName = statisticsType.name;
			  var newNode = {id:statisticsType.id,pId:parentId,name:nodeName,isParent:!statisticsType.leaf}
			  statisticsTypeTreeObj.addNodes(pNode, newNode,false);//参数最后一个为true时不自动展开父节点
		  }else{
			   // 如果修改了名字 ，则需要更新节点
			 var oldTypeName = "${statisticsType.name}";
			 var newTypeName = statisticsType.name;
			 if(oldTypeName != newTypeName){
			 var typeNodeId = "${statisticsType.id}";
			 var updateNode = statisticsTypeTreeObj.getNodeByParam("id", typeNodeId, null);
			 updateNode.name = newTypeName;
			 statisticsTypeTreeObj.updateNode(updateNode);
			 }
		}

	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="statisticsTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savestatisticsTypeForm);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="statisticsType.id"/>
				<s:hidden key="statisticsType.changeDate"/>
				<s:hidden key="statisticsType.changeUser.personId"/>
				<s:hidden key="statisticsType.statisticsCode"/>
				<s:hidden key="statisticsType.leaf"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="statisticsType_code" key="statisticsType.code" name="statisticsType.code" cssClass="required" maxlength="30" notrepeat='统计类型号已存在' validatorParam="from StatisticsType type where type.code=%value%"/>
				<s:textfield id="statisticsType_name" key="statisticsType.name" name="statisticsType.name" cssClass="required" maxlength="50" notrepeat='统计类型名称已存在' validatorParam="from StatisticsType type where type.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="statisticsType_code" key="statisticsType.code" name="statisticsType.code" cssClass="required" maxlength="30" readonly="true"/>
				<s:textfield id="statisticsType_name" key="statisticsType.name" name="statisticsType.name" cssClass="required" maxlength="50" oldValue="'${statisticsType.name}'" notrepeat='统计类型名称已存在' validatorParam="from StatisticsType type where type.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label style="float:left;white-space:nowrap">
						 <s:text name='statisticsType.parentType'/>:</label><span
							style="float: left; width: 138px">
      						<input type="hidden" id="statisticsType_parentType_id" name="statisticsType.parentType.id" value="${statisticsType.parentType.id}"/>
      						 <input type="text" id="statisticsType_parentType" name="statisticsType.parentType.name" value="${statisticsType.parentType.name}" readonly="readonly"/> </span> 
      			</div>
				<div class="unit">
						<s:textarea key="statisticsType.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





