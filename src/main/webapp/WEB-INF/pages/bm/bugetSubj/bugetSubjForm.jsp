<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bugetSubjForm").attr("action","saveBugetSubj?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bugetSubjForm").submit();
		});*/
	});
	function bugetSubjFormCallBack(data){
		if('${entityIsNew}'=='true'){
			var node = {};
			var bugetSubj = data.bugetSubj;
			node.id = bugetSubj.bugetSubjId;
			node.name = bugetSubj.bugetSubjName;
			if(bugetSubj.parentId){
				node.pId = bugetSubj.parentId.bugetSubjId;
			}else{
				node.pId = "-1";
			}
			//alert(data.workScore);
			var zTree = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft");
			var prentNode = zTree.getNodeByParam("id",node.pId);
			zTree.addNodes(prentNode,node);
			bugetSubjReload();
		}else{
			var zTree = $.fn.zTree.getZTreeObj("bugetSubjTreeLeft");
			var bugetSubj = data.bugetSubj;
			var node = zTree.getNodeByParam("id",bugetSubj.bugetSubjId);
			node.name="("+bugetSubj.bugetSubjCode+") "+bugetSubj.bugetSubjName;
			zTree.updateNode(node);
		}
		formCallBack(data);
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bugetSubjForm" method="post"	action="saveBugetSubj?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,bugetSubjFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="bugetSubj.bugetSubjId"/>
				<s:hidden key="bugetSubj.orgCode"/>
				<s:hidden key="bugetSubj.copyCode"/>
				<s:hidden key="bugetSubj.periodYear"/>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield id="bugetSubj_bugetSubjCode" key="bugetSubj.bugetSubjCode" name="bugetSubj.bugetSubjCode" cssClass="required				
				
				       " notrepeat='预算科目编码已存在' validatorParam="from BugetSubj b where b.bugetSubjCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="bugetSubj.bugetSubjCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield id="bugetSubj_bugetSubjName" key="bugetSubj.bugetSubjName" name="bugetSubj.bugetSubjName" cssClass="required				
				
				       " notrepeat='预算科目名称已存在' validatorParam="from BugetSubj b where b.bugetSubjName=%value%"/>
					</s:if>
					<s:else>
						<s:textfield id="bugetSubj_bugetSubjName" key="bugetSubj.bugetSubjName" name="bugetSubj.bugetSubjName" cssClass="required				
				
				       " notrepeat='预算科目名称已存在' oldValue="${bugetSubj.bugetSubjName }" validatorParam="from BugetSubj b where b.bugetSubjName=%value%"/>
					</s:else>
				
				</div>
				<div class="unit">
				<s:textfield id="bugetSubj_cnCode" key="bugetSubj.cnCode" name="bugetSubj.cnCode" readOnly="true" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:hidden id="bugetSubj_parentId_bugetSubjId" key="bugetSubj.parentId.bugetSubjId" name="bugetSubj.parentId.bugetSubjId" cssClass="				
				
				       "/>
				<label><s:text name="bugetSubj.parentId"/>:</label>
				<s:textfield id="bugetSubj_parentId_bugetSubjName" key="bugetSubj.parentId.bugetSubjName" name="bugetSubj.parentId.bugetSubjName"  readonly="true" cssClass="				
				
				       " theme="simple"/>
				</div>
				<div class="unit">
				<s:hidden id="bugetSubj_centralDeptId_id" name="bugetSubj.centralDeptId.departmentId"/>
				<s:textfield id="bugetSubj_centralDeptId" key="bugetSubj.centralDeptId" name="bugetSubj.centralDeptId.name" />
					<script>
					jQuery("#bugetSubj_centralDeptId").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "select deptId id,name,parentDept_id parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_bm_department where ysleaf=1 and disabled=0 and deptId <> 'XT'",
						exceptnullparent : true,
						initSelect : "${bugetSubj.centralDeptId.departmentId}",
						lazy : false,
						minWidth : '280px',
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<div class="unit">
				<s:hidden id="bugetSubj_subjTypeCode_id" name="bugetSubj.subjTypeCode"/>
				<s:textfield id="bugetSubj_subjTypeCode" key="bugetSubj.subjTypeCode" name="bugetSubj_subjTypeCode"/>
					<script>
					jQuery("#bugetSubj_subjTypeCode").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "SELECT AccttypeId id,Accttype name,'1' parent FROM GL_accountType ORDER BY Accttypecode asc",
						exceptnullparent : true,
						initSelect : "${bugetSubj.subjTypeCode}",
						lazy : false,
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<div class="unit">
				<s:textfield id="bugetSubj_slevel" key="bugetSubj.slevel" name="bugetSubj.slevel" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<label><s:text name="bugetSubj.ctrlPeriod"/>:</label>
				<s:select list="#{'年度':'年度','季度':'季度','月度':'月度'}" key="bugetSubj.ctrlPeriod" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
					<label><s:text name="bugetSubj.isCarry"/>:</label>
					<s:checkbox id="bugetSubj_isCarry" key="bugetSubj.isCarry" name="bugetSubj.isCarry" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="bugetSubj.isprocessctrl"/>:</label>
					<s:checkbox id="bugetSubj_isprocessctrl" key="bugetSubj.isprocessctrl" name="bugetSubj.isprocessctrl" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="bugetSubj.leaf"/>:</label>
					<s:checkbox id="bugetSubj_leaf" key="bugetSubj.leaf" name="bugetSubj.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="bugetSubj.disabled"/>:</label>
					<s:checkbox id="bugetSubj_disabled" key="bugetSubj.disabled" name="bugetSubj.disabled" theme="simple"/>
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





