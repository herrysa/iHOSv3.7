<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
function json2str(o) {
	var arr = [];
 	var fmt = function(s) {
	if (typeof s == 'object' && s != null) return json2str(s);
 		return /^(string|number)$/.test(typeof s) ? "'" + s + "'" : s;
 	}
 	for (var i in o) arr.push("'" + i + "':" + fmt(o[i]));
 		return '{' + arr.join(',') + '}';
}
	var saveType=0,templtCopyId="";
	jQuery(document).ready(function() {
		if(!jQuery("#inspectTempl_inspecttype").val()){
			jQuery("#inspectTempl_inspecttype").val("科室");
		}
		jQuery('#savelink').click(function() {
			jQuery("#inspectTemplForm").attr("action","saveInspectTempl?popup=true&saveType="+saveType+"&templtCopyId="+templtCopyId+"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#inspectTemplForm").submit();
		});
		jQuery('#inspectTempl_choose').change(function(){
			var inspectTemplId = jQuery(this).val();
			templtCopyId = inspectTemplId;
			$.ajax({
			    url: 'getInspectTemplInfo',
			    data:{inspectModelId:inspectTemplId},
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        //jQuery('#name').attr("value",data.responseText);
			    },
			    success: function(data){
			        // do something with xml
			        //alert(json2str(data));
			        jQuery("#inspectTempl_inspectModelId").val(data.inspectModelId+"-copy");
			        jQuery("#inspectTempl_inspectModelNo").val(data.inspectModelNo+"-copy");
			        jQuery("#inspectTempl_inspectModelName").val(data.inspectModelName+"-copy");
			        jQuery("#inspectTempl_inspecttype").val(data.inspecttype);
			        jQuery("#inspectTempl_periodType").val(data.periodType);
			        jQuery("#inspectTempl_jjdepttype").val(data.jjdepttype.khDeptTypeId);
			        jQuery("#inspectTempl_totalScore").val(data.totalScore);
			        jQuery("#inspectTempl_dept_id").val(data.departmentIds);
			        jQuery("#inspectTempl_dept_name").text(data.departmentNames);
			        jQuery("#inspectTempl_remark").val(data.remark);
			        saveType = 1;
			    }
			});
		});
		var editType = "${editType}";
		if(editType=="1"){
			jQuery("input[type=text]","#inspectTemplForm").attr("readOnly","true");
			jQuery("select","#inspectTemplForm").each(function(){
				jQuery(this).attr("disabled","true");
				var thisValue = jQuery(this).val();
				var thisName = jQuery(this).attr("name");
				jQuery(this).after("<input type='hidden' name='"+thisName+"' value='"+thisValue+"'>");
			});
			//jQuery("textarea","#inspectTemplForm").attr("readOnly","true");
			jQuery("input[type=checkbox]","#inspectTemplForm").attr("disabled","true");
		}
		jQuery("#inspectTempl_inspectModelNo").removeAttr("readOnly");
		jQuery("#inspectTempl_inspectModelName").removeAttr("readOnly");
		jQuery("#inspectTempl_remark").removeAttr("readOnly");
		jQuery("#inspectTempl_disabled").removeAttr("disabled");
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="inspectTemplForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:if test='inspectTempl.inspectModelId==null'>
				<s:select id="inspectTempl_choose" key="inspectTempl.choose" listKey="inspectModelId" listValue="inspectModelName" list="inspectTempls" headerKey="" headerValue="--自定义--"/>
				</s:if>
				<s:else>
				<s:select id="inspectTempl_choose" key="inspectTempl.choose" listKey="inspectModelId" listValue="inspectModelName" list="inspectTempls" headerKey="" headerValue="--自定义--" disabled="true"/>
				</s:else>
				</div>
				<div class="unit">
				<s:if test='inspectTempl.inspectModelId==null'>
					<s:textfield key="inspectTempl.inspectModelId" cssClass="required" onblur="checkId(this,'InspectTempl','inspectModelId')"/>
				</s:if>
				<s:else>
					<s:textfield key="inspectTempl.inspectModelId" readonly="true" cssClass="required"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="inspectTempl_inspectModelNo" key="inspectTempl.inspectModelNo" name="inspectTempl.inspectModelNo" cssClass="required"/>
				</div>
				<div class="unit">
				<s:textfield id="inspectTempl_inspectModelName" key="inspectTempl.inspectModelName" name="inspectTempl.inspectModelName" cssClass="required" style="width:300px"/>
				</div>
				<div class="unit">
					<label><s:text name="inspectTempl.inspecttype"></s:text>:</label>
				    <span style="float:left;width:140px">
				    	<s:textfield id="inspectTempl_inspecttype" name="inspectTempl.inspecttype" readonly="true"/>
					</span>
				</div>
				<div class="unit">
					<label><s:text name="inspectTempl.jjdepttype"></s:text>:</label>
				    <span style="float:left;width:140px">
				    	<s:select id="inspectTempl_jjdepttype" list="jjdepttype" key="inspectTempl.jjdepttype.khDeptTypeId" listKey="khDeptTypeId" listValue="khDeptTypeName" value="%{inspectTempl.jjdepttype.khDeptTypeId}" theme="simple"></s:select>
					</span>
				</div>
				<div class="unit">
					<s:select id="inspectTempl_periodType" key="inspectTempl.periodType" name="inspectTempl.periodType" list="#{'月':'月', '季':'季','半年':'半年','年':'年'}" value="%{inspectTempl.periodType}"/>
				</div>
				<div class="unit">
					<s:textfield id="inspectTempl_totalScore" key="inspectTempl.totalScore" name="inspectTempl.totalScore" cssClass="required digits"/>
				</div>
				<div class="unit">
					<s:hidden id="inspectTempl_dept_id" name="inspectTempl_dept_id" value="%{inspectTempl.departmentIds}"/>
					<label><s:text name="inspectTempl.departmentNames"></s:text>:</label>
				    <span style="float:left;width:140px">
					<s:textarea id="inspectTempl_dept_name" name="inspectTempl_dept_name" rows="2" cols="50" cssClass="required" value="%{inspectTempl.departmentNames}">
					</s:textarea>
					</span>
					<script>
						//addTreeSelect("tree","sync","inspectTempl_dept_name","inspectTempl_dept_id","multi",{tableName:"t_department",treeId:"deptId",treeName:"cnCode+','+name as coderName",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",initSelect:"${inspectTempl.departmentIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
						var editType = "${editType}";
						//if(editType=="0"){
							//addTreeSelect("tree","sync","inspectTempl_dept_name","inspectTempl_dept_id","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",order:"internalCode asc",filter:" jjleaf='1'",initSelect:"${inspectTempl.departmentIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
							var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_kh_department v where v.disabled = '0' and v.jjleaf = '1'" //威海版改成了：v_kh_department这个视图
								sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_kh_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
								var herpType = "${fns:getHerpType()}";
								if(herpType == "M") {
									sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
								}
								sql += " order by orderCol";
							jQuery("#inspectTempl_dept_name").treeselect({
								idId:"inspectTempl_dept_id",
								dataType:"sql",
								optType:"multi",
								sql:sql,
								exceptnullparent:false,
								lazy:false
							});
						//}
					</script>
				</div>
				<div class="unit">
				<s:textarea id="inspectTempl_remark" key="inspectTempl.remark" name="inspectTempl.remark" rows="3" cols="50"/>
				</div>
				<div class="unit">
					<label><s:text name="inspectTempl.disabled"/>:</label>
					<span style="float:left;width:140px">
					<s:checkbox id="inspectTempl_disabled" key="inspectTempl.disabled" name="inspectTempl.disabled" theme="simple"/>
					</span>
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





