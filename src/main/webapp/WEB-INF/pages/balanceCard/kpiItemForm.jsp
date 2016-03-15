<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
function fieldChange(id,lid) {
	//alert(id);
	var content = document.getElementById(id);
	content.style.display = (content.style.display == "none") ? "block"
			: "none";
	document.getElementById(lid).innerText = (content.style.display == "none") ? "公式定义信息"
			: "隐藏";
}

function editFormulaField(fieldInputFieldId){
	//alert(fieldInputFieldId);
	var fieldId = jQuery("#"+fieldInputFieldId).val();
	//alert(fieldId);
	if( fieldId ==null ||fieldId=="")
		alert("请先选择使用的公式.");
		else{
	var url = "editFormulaField?popup=true&formulaFieldId=" + fieldId;
	var winTitle = '<fmt:message key="formulaFieldEdit.title"/>';
	$.pdialog.open(url, 'editFormulaField', winTitle, {mask:false,width:670,height:650});
}
}

function refreshKpiTree(){
	refreshTreePNode(jQuery("#kpiItem_periodType").val(),jQuery("#kpiItem_parentId").val(),"add");
	//initTree("",jQuery("#kpiItem_periodType").val());
}

	jQuery(document).ready(function() {
		jQuery('#kpiSavelink').click(function() {
			jQuery("#kpiItemForm").attr("action","saveKpiItem?popup=true&entityIsNew=${entityIsNew}");
			jQuery("#kpiItemForm").submit();
				 /* try{
					 var $form = $( "#kpiItemForm" ),
					      url = $form.attr( 'action' );
					// alert(url);
					 if(validateCallback($form,formCallBack)){
						$.post(url, $("#kpiItemForm").serialize(),function( data ) {
							  alert(data.message);
						       initTree(jQuery("#kpiItem_periodType").val());
						      // alert(data.message);
						       
					    });
					 }
				}catch(e){
					alert(e.message);
				} */ 
		});
		var keyCode = "${kpiItem.keyCode}";
		var keyName = "${kpiItem.keyName}";
		var targetId = "${kpiItem.targetField.formulaFieldId}";
		var targetName = "${kpiItem.targetField.fieldTitle}";
		var actualId = "${kpiItem.actualField.formulaFieldId}";
		var actualName = "${kpiItem.actualField.fieldTitle}";
		var scoreId = "${kpiItem.scoreField.formulaFieldId}";
		var scoreName = "${kpiItem.scoreField.fieldTitle}";
		var pattern = "${kpiItem.pattern}";
		var inputType = "${kpiItem.inputType}";
		var executeDeptId = "${kpiItem.executeDept.departmentId}";
		var executeDeptName = "${kpiItem.executeDept.name}";
		var disabled = "${kpiItem.disabled}";
		jQuery("#kpiResetLink").click(function() {
			jQuery("#kpiItem_keyCode").val(keyCode);
			jQuery("#kpiItem_keyName").val(keyName);
			jQuery("#kpiItem_targetField_Namemul_id").val(targetId);
			jQuery("#kpiItem_targetField_Namemul").val(targetName);
			jQuery("#kpiItem_actualField_Namemul_id").val(actualId);
			jQuery("#kpiItem_actualField_Namemul").val(actualName);
			jQuery("#kpiItem_scoreField_Namemul_id").val(scoreId);
			jQuery("#kpiItem_scoreField_Namemul").val(scoreName);
			jQuery("#kpiItem_pattern").val(pattern);
			jQuery("#kpiItem_inputType").val(inputType);
			if(executeDeptId) {
				jQuery("#kpiItem_executeDept_idName_id").val(executeDeptId);
				jQuery("#kpiItem_executeDept_idName").val(executeDeptName);
			}
			if(disabled == "true") {
				jQuery("#kpiItem_disabled").attr("checked","true");
			}
		});
	});

</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kpiItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,refreshKpiTree);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden id="kpiItem_id" name="kpiItem.id"/>
	    			<%-- <s:textfield id="kpiItem_parentNode_id" name="kpiItem.parentNode.keyName" /> --%>
	    			<s:textfield value="%{kpiItem.parentNode.keyName}" id="kpiItem_parentNode_id"  label="父级" readonly="true" disabled="true" cssClass="textInput readonly"  size="50"/>
	    		    <s:hidden id="kpiItem_parentId" name="parentId"/>
					<s:hidden id="kpiItem_lft" key="kpiItem.lft" name="kpiItem.lft" cssClass="digits"/>
					<s:hidden id="kpiItem_rgt" key="kpiItem.rgt" name="kpiItem.rgt" cssClass="digits"/>
					<s:hidden id="kpiItem_level" key="kpiItem.level" name="kpiItem.level" cssClass="digits"/>
				</div>
				<div class="unit">
				<s:textfield id="kpiItem_keyCode" key="kpiItem.keyCode" name="kpiItem.keyCode" cssClass="required" size="50"/>
				</div>
				<div class="unit">
				<s:textfield id="kpiItem_keyName" key="kpiItem.keyName" name="kpiItem.keyName" cssClass="required" size="50"/>
				</div>
				<div class="unit">
				<s:textfield id="kpiItem_periodType" key="kpiItem.periodType" name="kpiItem.periodType" readonly="true" cssClass="readonly" size="50"/>
				</div>
				<s:if test="%{kpiItem.level==3}">
				<div class="unit">
				<%-- <s:textfield id="kpiItem_targetField" key="kpiItem.targetField" name="kpiItem.targetField" cssClass="required"/> --%>
				<s:hidden id="kpiItem_targetField_Namemul_id" name="kpiItem.targetField.formulaFieldId"   />
				<s:textfield id="kpiItem_targetField_Namemul" name="kpiItem_targetField_Namemul" label="%{getText('kpiItem.targetField')}" value="%{kpiItem.targetField.fieldTitle}"  readonly="false" cssClass="textInput " size="50"/>
				<a href="#" onclick="javascript:editFormulaField('kpiItem_targetField_Namemul_id')">编辑公式</a>
				<script>
				jQuery("#kpiItem_targetField_Namemul").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT formulaFieldId id,fieldTitle name,fieldTitle parentId  FROM t_formulaField where formulaEntityId=${formulaEntityFilter}",
						exceptnullparent:false,
						lazy:false
					});
				/*addTreeSelect("list","sync","kpiItem_targetField_Namemul","kpiItem_targetField_formulaFieldId","single",{tableName:"t_formulaField",treeId:"formulaFieldId",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=${formulaEntityFilter}"});*/
				</script>
				</div>
				
				<div class="unit">
				<%-- <s:textfield id="kpiItem_actualField" key="kpiItem.actualField" name="kpiItem.actualField" cssClass="required"/> --%>
				
				<s:hidden id="kpiItem_actualField_Namemul_id" label="kpiItem.actualField" name="kpiItem.actualField.formulaFieldId"   />
				<s:textfield id="kpiItem_actualField_Namemul" name="kpiItem_actualField_Namemul"  label="%{getText('kpiItem.actualField')}" value="%{kpiItem.actualField.fieldTitle}"  readonly="false" cssClass="textInput" size="50"/>
				<a href="#" onclick="javascript:editFormulaField('kpiItem_actualField_Namemul_id')">编辑公式</a>
				<script>
				jQuery("#kpiItem_actualField_Namemul").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT formulaFieldId id,fieldTitle name,fieldTitle parentId  FROM t_formulaField where formulaEntityId=${formulaEntityFilter}",
						exceptnullparent:false,
						lazy:false
					});
				/*addTreeSelect("list","sync","kpiItem_actualField_Namemul","kpiItem_actualField_formulaFieldId","single",{tableName:"t_formulaField",treeId:"formulaFieldId",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=${formulaEntityFilter}"});*/
				</script>
				</div>
							
				<div class="unit">
				<%-- <s:textfield id="kpiItem_scoreField" key="kpiItem.scoreField" name="kpiItem.scoreField" cssClass="required"/> --%>
				<s:hidden id="kpiItem_scoreField_Namemul_id" key="kpiItem.scoreField" name="kpiItem.scoreField.formulaFieldId"   />
				<s:textfield id="kpiItem_scoreField_Namemul" name="kpiItem_scoreField_Namemul" label="%{getText('kpiItem.scoreField')}" value="%{kpiItem.scoreField.fieldTitle}"  readonly="false" cssClass="required" size="50"/>
				<a href="#" onclick="javascript:editFormulaField('kpiItem_scoreField_Namemul_id')">编辑公式</a>
				<%-- <s:property value="formulaEntityFilter"/> --%>
				<script>
				jQuery("#kpiItem_scoreField_Namemul").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT formulaFieldId id,fieldTitle name,fieldTitle parentId  FROM t_formulaField where formulaEntityId=${formulaEntityFilter}",
						exceptnullparent:false,
						lazy:false
					});
				/*addTreeSelect("list","sync","kpiItem_scoreField_Namemul","kpiItem_scoreField_formulaFieldId","single",{tableName:"t_formulaField",treeId:"formulaFieldId",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=${formulaEntityFilter}"});*/
				</script>
				</div>
					
				
				<div class="unit">
				<s:textfield id="kpiItem_pattern" key="kpiItem.pattern" name="kpiItem.pattern" cssClass="textInput " size="50"/>
				</div>
				<div class="unit">
				<s:select id="kpiItem_inputType" key="kpiItem.inputType" name="kpiItem.inputType" list="#{'计算':'计算', '手工':'手工'}" emptyOption="false"/>
				</div>
				
				<div class="unit">
				<s:hidden id="kpiItem_executeDept_idName_id" 
					name="kpiItem.executeDept.departmentId" 
					/>
				 <s:textfield key="kpiItem.executeDept"
					id="kpiItem_executeDept_idName"
					name="kpiItem_executeDept_idName"
					value="%{kpiItem.executeDept.name}"
					readonly="false" cssClass="required" size="50"/>
				<script>
				
					jQuery("#kpiItem_executeDept_idName").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT' union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ORDER BY orderCol",
						exceptnullparent:false,
						selectParent:true,
						lazy:false
					});
					/*addTreeSelect("tree", "sync",
							"kpiItem_executeDept_idName",
							"kpiItem_executeDept_id",
							"single", {
								tableName : "t_department",
								treeId : "deptId",
								treeName : "name",
								parentId : "parentDept_id",
								filter : ""
							});*/
				</script>
				</div>
				<%-- <div class="unit">
				<s:textfield id="kpiItem_displayOrder" key="kpiItem.displayOrder" name="kpiItem.displayOrder" cssClass="digits"/>
				</div> --%>
				
				<div class="unit">
				<s:hidden id="kpiItem_leaf" key="kpiItem.leaf" name="kpiItem.isLeaf"  disabled="true" />
			<%-- 	<s:checkbox id="kpiItem_used" key="kpiItem.used" name="kpiItem.used" /> --%>
			<label class="checkbox">停用 :</label>
				<s:checkbox id="kpiItem_disabled" key="kpiItem.disabled" name="kpiItem.disabled" theme="simple" />
				<%-- <s:textfield key="kpiItem.level"></s:textfield> --%>
<%--   <label class="checkbox">停用 :</label>
<input name="kpiItem.disabled"  id="kpiItem_disabled"  type="checkbox" value="${kpiItem.disabled}"/> --%>

				</div>
				
				
				
				
				<div class="unit">
						<a id="tarlink" href="javascript:fieldChange('targetContent','tarlink');" style="font-size: 12px">公式定义信息</a>
					</div>
					<div id="targetContent"  style="display: none">
				<div   class="unit">
				<s:textarea rows="4" cols="50" value="%{kpiItem.targetField.formula.formula}" label="目标值%{getText('formulaField.formula.formula')}" readonly="true" disabled="true"/>
				</div>
				<div   class="unit">
				<s:textarea  rows="1" cols="50" value="%{kpiItem.targetField.formula.condition}" label="目标值%{getText('formulaField.formula.condition')}" readonly="true" disabled="true"/>
				</div>
				<div   class="unit">
				<s:textarea rows="4" cols="50" value="%{kpiItem.actualField.formula.formula}" label="实际值%{getText('formulaField.formula.formula')}" readonly="true" disabled="true"/>
				</div>
				<div   class="unit">
				<s:textarea  rows="1" cols="50" value="%{kpiItem.actualField.formula.condition}" label="实际值%{getText('formulaField.formula.condition')}" readonly="true" disabled="true"/>
				</div>	
				<div   class="unit">
				<s:textarea rows="4" cols="50" value="%{kpiItem.scoreField.formula.formula}" label="得分%{getText('formulaField.formula.formula')}" readonly="true" disabled="true"/>
				</div>
				<div   class="unit">
				<s:textarea  rows="1" cols="50" value="%{kpiItem.scoreField.formula.condition}" label="得分%{getText('formulaField.formula.condition')}" readonly="true" disabled="true"/>
				</div>
				</div>
				
				</s:if>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="kpiSavelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" id="kpiResetLink"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





