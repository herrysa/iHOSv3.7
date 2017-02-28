<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		//loadStorePositionGrid();
		jQuery("#store_department_departmentId").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	});
	
	/* function dynamicAddPerson(personType){
		var addpersonSql = "select p.personId as PERSONID, p.name as PNAME,d.name as DNAME from t_person p,t_department d where p.dept_id=d.deptId and p.disabled = 0 ";
		var deptId = jQuery("#store_department_departmentId_id").val();
		if(""!=deptId.trim()){
			addpersonSql += " and p.dept_id = '"+deptId+"'";
		}
		jQuery(personType).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams:{
				sql : addpersonSql,
				cloumns : 'p.name'
			},
			sidx:"DNAME",
			sord:"asc",
			rows:10,
			colModel : [
			{
				'columnName' : 'PNAME',
				'width' : '50',
				'label' : '姓名'
			},
			{
				'columnName' : 'DNAME',
				'width' : '40',
				'label' : '部门'
			}],
			select : function(event, ui) {
				$(this).val(ui.item.PNAME);
				jQuery(personType+"_id").val(ui.item.PERSONID);
				return false;
			}
		});
	}
	 */
	function setStoreParentCode(obj){
		var currentParentCode = jQuery("#store_parentNode_storeCode").val();
		jQuery("#store_storeCode").val(currentParentCode=="-1"?"":currentParentCode);
	}
	function validateStoreCode(obj){
		if(${entityIsNew}==false){
			return;
		}
		var currentParentCode = jQuery("#store_parentNode_storeCode").val();
		currentParentCode=(currentParentCode=="-1"?"":currentParentCode);
		var storeCodeRule = jQuery("#storeCodeRule_id").val();
		//alert("编码方案"+storeCodeRule);
		if(storeCodeRule){
			var storeCode = obj.value;
			var validateFatherCode = getFather(storeCode,storeCodeRule);
			if(storeCode!=""){
				if(!isMatch(storeCode,storeCodeRule)){
					alertMsg.error("仓库编码格式不正确，请重新输入！");
					obj.value=currentParentCode;
					return;
				}else if(currentParentCode && currentParentCode!=validateFatherCode){
					alertMsg.error("上级类别编码不正确");
		    		obj.value=currentParentCode;
		    		return;
				}
			}
		}
		var storeIdTemp = "${fns:userContextParam('orgCode')}_"+obj.value;
		
   		var url = 'checkId';
   		url = encodeURI(url);
   		$.ajax({
   		    url: url,
   		    type: 'post',
   		    data:{entityName:'Store',searchItem:'storeId',searchValue:storeIdTemp,returnMessage:'仓库编码已存在'},
   		    dataType: 'json',
   		    aysnc:false,
   		    error: function(data){
   		        
   		    },
   		    success: function(data){
   		        if(data!=null){
   		        	 alertMsg.error(data.message);
   				     obj.value=currentParentCode;
   		        }
   		    }
   		});
		
	}
</script>
</head>
		<div class="pageContent">
			<form id="storeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,addOrUpdateStore);">
			<div class="pageFormContent" layoutH="150">
				<div class="unit">
					<s:hidden  id = "currentStoreEntityState" value="%{entityIsNew}" />
					<s:hidden  id = "storeCodeRule_id" value="%{codeRule}" />
					<s:hidden  id = "store_id" key="store.id" required="true" cssClass="validate[required]"/>
					<s:textfield value="%{store.parentNode.storeNameWithCode}" id="store_parentNode_name"  label="父级"  readonly="true" cssClass="textInput readonly"  size="50"/>
					<s:hidden value="%{store.parentNode.id}" id="store_parentNode_id" />
					<s:hidden value="%{store.parentNode.storeCode}" id="store_parentNode_storeCode" />
				</div>
				<s:if test="%{entityIsNew}">
					<div class="unit">
						<s:textfield id="store_storeCode" key="store.storeCode" name="store.storeCode" cssClass="required" size="50" onfocus="setStoreParentCode()"onblur="validateStoreCode(this)"/>
						<s:if test="%{codeRule!='' && codeRule!=null}">
							<span>编码规则:${codeRule}</span>
						</s:if>
					</div>
				</s:if>
				<s:else>
					<div class="unit">
						<s:textfield id="store_storeCode" key="store.storeCode" name="store.storeCode" readonly="true" size="50" cssClass="textInput readonly" />
					</div>
				</s:else>
				<div class="unit">
					<s:textfield id="store_storeName" key="store.storeName" name="store.storeName" cssClass="required" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="store_cnCode" key="store.cnCode" name="store.cnCode" readonly="true" cssClass="textInput readonly" size="50"/>
				</div>
				<div class="unit">
					<%-- <s:textfield id="store_storeType" key="store.storeType" name="store.storeType" cssClass="required digits" size="50"/>
					 --%><s:select id="store_storeType" key="store.storeType" list="#{'0':'一般库房','1':'科室库房','2':'其它库房','9':'虚拟库房'}" style="width:285px"></s:select>
				</div>
				<div class="unit">
					<s:textfield id="store_address" key="store.address" name="store.address"  cssClass="textInput" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="store_telephone" key="store.telephone" name="store.telephone"  cssClass="textInput" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="store_department_departmentId" key="主管部门" name="store.department.name"  cssClass="textInput" size="50"/>
					<s:hidden id="store_department_departmentId_id" name="store.department.departmentId"/>
				</div>
				<div class="unit">
					<s:textfield id="store_keeper" key="store.keeper" name="store.keeper" cssClass="textInput" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="store_purchaser" key="store.purchaser" name="store.purchaser" cssClass="textInput" size="50"/>
				</div>
				<div class="unit">
					<s:textfield id="store_accountor" key="store.accountor" name="store.accountor" cssClass="textInput" size="50"/>
				</div>
				<div class="unit">
					<s:textarea id="store_remark" key="store.remark" name="store.remark"  cssClass="textInput" cols="43"/>
				</div>
				<div class="unit">
					<label class="checkbox">货位管理:</label>
				<s:checkbox id="store_isPos" key="store.isPos" name="store.isPos" theme="simple"/>
				</div>
				<div class="unit">
					<label class="checkbox">末级:</label>
					<s:checkbox id="store_leaf" key="store.leaf" name="store.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label class="checkbox">停用 :</label>
					<s:checkbox id="store_disabled" key="store.disabled" name="store.disabled" theme="simple" />
				</div>
				
			</div>
			</form>
		</div>





