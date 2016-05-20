<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#workScoreForm").attr("action","saveWorkScore?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#workScoreForm").submit();
		});*/
	});
	function workScoreFormCallBack(data){
		if('${entityIsNew}'=='true'){
			var node = {};
			var workScore = data.workScore;
			node.id = workScore.id;
			node.name = workScore.workName;
			if(workScore.parentid){
				node.pId = workScore.parentid.id;
			}else{
				node.pId = "-1";
			}
			//alert(data.workScore);
			var zTree = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
			var prentNode = zTree.getNodeByParam("id",node.pId);
			zTree.addNodes(prentNode,node);
			workScoreReload();
		}else{
			var zTree = $.fn.zTree.getZTreeObj("workScoreTreeLeft");
			var workScore = data.workScore;
			var node = zTree.getNodeByParam("id",workScore.id);
			node.name=workScore.workName;
			zTree.updateNode(node);
		}
		formCallBack(data);
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="workScoreForm" method="post"	action="saveWorkScore?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,workScoreFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="workScore.id" cssClass="required" notrepeat='工作量积分编码已存在' validatorParam="from WorkScore ws where ws.id=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="workScore.id" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="workScore_workName" key="workScore.workName" name="workScore.workName" cssClass="required				
				
				       "/>
				</div>
				<div class="unit">
				<s:hidden key="workScore.parentid.id"></s:hidden>
				<s:textfield id="workScore_parentid" key="workScore.parentid" readonly="true" name="workScore.parentid.workName" cssClass="				
				
				       "/>
				
				</div>
				<div class="unit">
				<s:textfield id="workScore_clevel" key="workScore.clevel" name="workScore.clevel" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="workScore_workScore" key="workScore.workScore" name="workScore.workScore" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="workScore_workunit" key="workScore.workunit" name="workScore.workunit" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name='workScore.leaf'/>:</label>
					<s:checkbox id="workScore_leaf" key="workScore.leaf" name="workScore.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name='workScore.disabled'/>:</label>
					<s:checkbox id="workScore_disabled" key="workScore.disabled" name="workScore.disabled" theme="simple"/>
				</div>
				<div class="unit">
				<s:textarea id="workScore_remark" key="workScore.remark" style="width:250px;height:30px" name="workScore.remark" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
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





