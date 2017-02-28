
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery('#createPurchasePlanByBaselink').click(function() {
			//校验
			var hasNull = false;
			var errorMsg = "";
			jQuery("#createPurchasePlanByBaseForm .required").each(function(){
				if(!$(this).val()){
					errorMsg += $(this).prev().text();
					hasNull = true;
				}
			});
			errorMsg=errorMsg.replaceAll(":",",");
			if(hasNull){
				alertMsg.error(errorMsg+"不能为空！");
				return;
			} 
			
			var deptId = jQuery("#purchasePlan_createByBase_dept_id").val();
			var purchaserId = jQuery("#purchasePlan_createByBase_purchaser_id").val();
			var storeId = jQuery("#purchasePlan_createByBase_store_id").val();
			var invTypeId = jQuery("#purchasePlan_createByBase_invType_id").val();
			var makeDate = jQuery("#purchasePlan_createByBase_make_date").val();
			var arrivalDate = jQuery("#purchasePlan_createByBase_arrival_date").val();
			jQuery.ajax({
			    url: 'createPurchasePlanByBase?navTabId=purchasePlan_gridtable',
			    data: {deptId:deptId,purchaserId:purchaserId,storeId:storeId,invTypeId:invTypeId,makeDate:makeDate,arrivalDate:arrivalDate},
			    type: 'post',
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    	
			    },
			    success: function(data){
			    	if(data.purchaseId){
			    		$.pdialog.closeCurrent();
			    		$.pdialog.open('${ctx}/editPurchasePlan?popup=true&purchaseId='+data.purchaseId,'editPurchasePlan',"采购计划单明细", {mask:true,width : 960,height : 628,resizable:false});	
			    	}					
					formCallBack(data);
			    }
			});
		});
		jQuery("#purchasePlan_createByBase_dept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false,
			callback : {
				afterClick : function() {
					$("#purchasePlan_createByBase_purchaser").val("");
					$("#purchasePlan_createByBase_purchaser_id").val("");
				}
			}
		});
		jQuery("#purchasePlan_createByBase_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
		jQuery("#purchasePlan_createByBase_invType").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
	});
	function initPurchaser(obj){
		var deptId = jQuery("#purchasePlan_createByBase_dept_id").val();
		if(deptId){
			jQuery("#purchasePlan_createByBase_purchaser").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT p.personId id ,p.name name,1 parent FROM t_person p where p.disabled = 0 and p.dept_id='"+deptId+"'",
				exceptnullparent:false,
				lazy:false
			}); 
		}else{
			alertMsg.error("请先选择科室。");
			return;
		}
	}
</script>

<div class="page">
	<div class="pageContent">
		<form id="createPurchasePlanByBaseForm" method="post" class="pageForm required-validate">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<label><s:text name='purchasePlan.dept'/>:</label>
					<input type="text" id="purchasePlan_createByBase_dept" class="required" />
					<input type="hidden" id="purchasePlan_createByBase_dept_id" name="deptId"/>
				</div>
				<div class="unit">
					<label><s:text name='purchasePlanDetail.purchaser'/>:</label>
					<input type="text" id="purchasePlan_createByBase_purchaser" class="required" onfocus="initPurchaser()"/>
					<input type="hidden" id="purchasePlan_createByBase_purchaser_id" name="purchaserId"/>
				</div>
				<div class="unit">
					<label><s:text name='purchasePlan.store'/>:</label>
					<input type="text" id="purchasePlan_createByBase_store" class="required"/>
					<input type="hidden" id="purchasePlan_createByBase_store_id" name="storeId"/>
				</div>
				<div class="unit">
					<label><s:text name='物资类别'/>:</label>
					<input type="text" id="purchasePlan_createByBase_invType" class=""/>
					<input type="hidden" id="purchasePlan_createByBase_invType_id" name="invTypeId"/>
				</div>
				<div class="unit">
					<label><s:text name='purchasePlan.makeDate'/>:</label>
					<input type="text"	id="purchasePlan_createByBase_make_date" name="makeDate" style="width:80px;height:15px" class="required Wdate" value="${fns:userContextParam('businessDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'${fns:userContextParam('periodBeginDateStr')}',maxDate:'${fns:userContextParam('periodEndDateStr')}'})" />
				</div>
				<div class="unit">
					<label><s:text name='purchasePlanDetail.arrivalDate'/>:</label>
					<input type="text"	id="purchasePlan_createByBase_arrival_date" name="arrivalDate" style="width:80px;height:15px" class="required Wdate" value="${fns:userContextParam('businessDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'purchasePlan_createByBase_make_date\')}'})" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="createPurchasePlanByBaselink"><s:text name="生成" /></button>
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