<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#jjAllotForm").attr("action","saveJjAllot?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			//alert(jQuery("#jjAllotForm").attr("action"));
			var amount = $("#jjAllot_amount").val();
			if(amount<=0){
				alertMsg.error("请输入一个大于0的金额！");
				$("#jjAllot_amount").attr("value","");
				return;
			}
			jQuery("#jjAllotForm").submit();
		});
	});
	function compareAmount(){
		var deptId=jQuery("#jjAllot_allotDept_Id").val();
		var amount=jQuery("#jjAllot_amount").val();
		jQuery.ajax({
			dataType : 'json',
			url:'compareAmount?deptId='+deptId+"&amount="+amount,
		    type: 'POST',
		    error: function(data){
		    	jQuery("#jjAllot_amount").attr("value","");
		    	alertMsg.error("没有找到奖金科室本月下发金额数据！");
		    },success: function(data){
		    	 var value = data["compareValue"];
		    	if(value<0){
		    		jQuery("#jjAllot_amount").attr("value","");
		    		alertMsg.warn("所填金额必须小于支出奖金科室下发总金额！");
		    	} 
		    }
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="jjAllotForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden id="" name="jjAllot.id" value="%{jjAllot.id}"/>
					<s:textfield id="jjAllot_checkPeriod" key="jjAllot.checkPeriod" name="jjAllot.checkPeriod"  size="12" value="%{currentPeriod}"  readonly="true"/>
					<s:textfield id="jjAllot_itemName" key="jjAllot.itemName" name="jjAllot.itemName" size="13" value="%{(itemName==null||itemName=='')?jjAllot.itemName:itemName}" readonly="true"/>
				</div>
				<div class="unit">
				
				<s:select id="jjAllot_allotDept_Id" key="jjAllot.allotDeptId" name="jjAllot.allotDeptId.departmentId" style="width:133px;" list='deptAllotList' cssClass="required" listKey="departmentId" listValue="name" />
				
				</div>
				
				
				<div class="unit">
				<s:hidden id="jjAllot_jjDept_id" name="jjAllot.jjDeptId.departmentId" value="%{jjAllot.jjDeptId.departmentId}"/>
				<label><s:text name="jjAllot.jjDeptId"></s:text>:</label>
				<span style="float:left;width:140px">
						<s:textfield size="25" id="jjAllot_allotDept_name" name="jjAllot_allotDept_name" cssClass="required" value="%{jjAllot.jjDeptId.name}"	 ></s:textfield>
				</span>
				<script>
												addTreeSelect("tree","sync","jjAllot_allotDept_name","jjAllot_jjDept_id","single",{tableName:"v_jj_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:" jjleaf=1",initSelect:"${jjDeptMap.deptIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});


				</script>
				</div>
				
				<div class="unit">
				<s:textfield id="jjAllot_amount" key="jjAllot.amount" name="jjAllot.amount"  size="12" cssClass="required" onblur="compareAmount()"/>
				</div>
				
				
				<div class="unit">
				<s:textarea id="jjAllot_remark" key="jjAllot.remark" name="jjAllot.remark" rows="2" cols="43" required="false"  />
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





