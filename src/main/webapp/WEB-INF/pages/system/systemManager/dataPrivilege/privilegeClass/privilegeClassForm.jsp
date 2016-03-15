<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#privilegeClassForm").attr("action","savePrivilegeClass?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#privilegeClassForm").submit();
		});
		jQuery("#privilegeClass_bdInfo").combogrid({
			url : '${ctx}/ComboGrid/comboGridSqlList',
			queryParams:{
				sql : "SELECT * FROM t_bdinfo",
				cloumns : 'BDINFONAME,TABLENAME'
			},
			colModel : [{
				'columnName' : 'BDINFOID',
				'width' : '0',
				'label' : 'ID',
				'hidden': true
			},
			{
				'columnName' : 'ORGCODECOLNAME',
				'width' : '0',
				'label' : '单位',
				'hidden': true
			},
			{
				'columnName' : 'COPYCODECOLNAME',
				'width' : '0',
				'label' : '帐套',
				'hidden': true
			},
			{
				'columnName' : 'KJYEARCOLNAME',
				'width' : '0',
				'label' : '期间',
				'hidden': true
			},
			{
				'columnName' : 'BDINFONAME',
				'width' : '60',
				'label' : '资源',
				'align' : 'left'
			},
			{
				'columnName' : 'TABLENAME',
				'width' : '30',
				'label' : '表名',
				'align' : 'left'
			}],
			select : function(event, ui) {
				//$("#inventoryDict_invType").val(ui.item.ID);
				$(this).val(ui.item.BDINFONAME);
				$(this).next().val(ui.item.BDINFOID);
				return false;
			}
		});
		var parentClass="${parentClass}";
		var sql = "SELECT classCode id,className name ,ISNULL(parentId, '-1') parent,sn from t_privilegeClass where disabled = '0' ORDER BY sn";
		jQuery("#privilegeClass_parentClass").treeselect({
			initSelect:parentClass,
			optType:'single',
			dataType:'sql',
			sql:sql,
			exceptnullparent:false,
			selectParent:true,
			lazy:false
		});
		jQuery().val();
	});
	function privilegeClassFormCallBack(data) {
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var itemNode = data.privilegeClassTreeNode;
		if(!itemNode){
			return ;
		}
		if("${entityIsNew}"=="true") {
			dealPrivilegeClassLoad("privilegeClassTree",itemNode,"add");
		} else {
			dealPrivilegeClassLoad("privilegeClassTree",itemNode,"edit");
		}
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="privilegeClassForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,privilegeClassFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:textfield key="privilegeClass.classCode"  cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="privilegeClass_className" key="privilegeClass.className" name="privilegeClass.className" cssClass="required"/>
				</div>
				<%-- <div class="unit">
					<s:textfield id="privilegeClass_searchName" key="privilegeClass.searchName" name="privilegeClass.searchName" cssClass="required"/>
				</div> --%>
				<div class="unit">
					<s:textfield id="privilegeClass_sn" key="privilegeClass.sn" name="privilegeClass.sn" cssClass="required"/>
				</div>
				<div class="unit">
					<s:textfield id="privilegeClass_bdInfo" key="privilegeClass.bdInfo" name="privilegeClass.bdInfo.bdInfoName" cssClass="required"/>
					<s:hidden key="privilegeClass.bdInfo.bdInfoId"/>
				</div>
				<div class="unit">

					<label><s:text name="privilegeClass.parentClass" />:</label>
					<input type="text" id="privilegeClass_parentClass" value="${privilegeClass.parentClass.className}"/>
					<input type="hidden" id="privilegeClass_parentClass_id" name="privilegeClass.parentClass.classCode" value="${privilegeClass.parentClass.classCode}" />
				</div>
				<div class="unit">

					<s:if test="%{entityIsNew}">
						<s:hidden key="privilegeClass.disabled"/>  
					</s:if>
					<s:else>
						<label><s:text name="privilegeClass.disabled"/></label>
						<s:checkbox id="privilegeClass_disabled" key="privilegeClass.disabled" name="privilegeClass.disabled" theme="simple"/>
					</s:else>
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





