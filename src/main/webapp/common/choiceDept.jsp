<%@ page language="java" pageEncoding="UTF-8"%>
	<!-- -----------------------------------弹出框START----------------------------------- -->

	<!-- -----------------选择科室(弹出框START)----------------- -->

<div id="${random}_choiceDeptDiv" style="display: none">
	<div class="pageContent" align="right">
		<form id="${random}_choiceDeptForm" method="post" enctype="multipart/form-data" class="pageForm required-validate" >
			<div class="pageFormContent" align="center" layoutH="56">
			<input type="hidden" id="${random}_deptJsonStr" value='<s:property value="deptJsonStr" escapeHtml="false"/>'>
				<table class="searchContent">
					<tr>
					<c:if test="${herpType == 'M' }">
						<td colspan="3">
							&nbsp;<fmt:message key='hisOrg.orgName' />：<input type="text" style="float: none;" id="${random}_choiceOrg" onfocus="quickOrgTreeSelect(this)"/>
					<input type="hidden" id="${random}_choiceOrg_id">
					<input type="button" onclick="quickOrgSelect()" value="搜"/><br><br>
						</td>
					</c:if>
					</tr>
					<tr >
						<td id="leftSelect">
							&nbsp;<input type="text" id="${random}_quickDeptSelect"  size="16"/><input type="button" onclick="quickDeptNameSelect()" value="搜"/><br><br>
							&nbsp;选择科室：<br>
							&nbsp;<select id="${random}_dept" ondblclick="addDept()" name="selectAll" multiple="true" style="width: 200px; height: auto" size="14">
									<c:forEach items="${departments}" var="dept">
										<c:if test="${dept.name!=null }"><option value="${dept.departmentId}">${dept.name}</option></c:if>
									</c:forEach>
								</select>
						</td>
						<td valign="middle" align="center" width="50">
							<input id="${random}_addBut" type="button"  class="btn"  style="width: 37px"  value=">" onclick="addDept()"/><br>
							<input id="${random}_delBut" type="button" value="&lt;"  style="width: 37px" onclick="delDept()"/><br>
							<input id="${random}_allAddBut" type="button" value=">>" style="width: 37px" onclick="addAll()"/><br>
							<input id="${random}_allDelBut" type="button" value="&lt;&lt;" style="width: 37px" onclick="delAll()"/>
						</td>
						<td><br><br><br><br>
							已选科室：<br>
							<select id="${random}_deptd" ondblclick="delDept()" multiple="true" style="width:200px;" name="selectedAll" size="14">
							</select>
							<s:property value="alDeptName" escapeHtml="false"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="okDeptButton()">确定</button>
							</div>
						</div>
					</li>
					<li><div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>

</div>

	
	<!-- -----------------选择科室(弹出框END)----------------- -->




<script type="text/javascript">
var deptJsonStr = jQuery("#${random}_deptJsonStr").val();
var deptJson = {};
if(deptJsonStr){
	deptJson = JSON.parse(deptJsonStr);
}
//部门树点击
function quickOrgTreeSelect(obj){
	jQuery(obj).treeselect({
		dataType:"sql",
		optType:"multi",
		sql:"SELECT orgCode id,ISNULL(shortName, orgname) name,parentOrgCode parent FROM T_Org where orgCode <> 'XT'",
		exceptnullparent:true,
		selectParent:true,
		lazy:true
	});
}
//单位按钮点击
function quickOrgSelect(){
	var orgCodeStr = jQuery("#${random}_choiceOrg_id").val();
	if(orgCodeStr){
		var orgCodeArr = orgCodeStr.split(",");
		var deptIdDArr = [];
		jQuery("#${random}_deptd").find("option").each(function(){
			var deptId = jQuery(this).attr("value");
			if(!deptJson[deptId]||jQuery.inArray(deptJson[deptId].orgCode, orgCodeArr) == -1){
				jQuery(this).remove();
			}else{
				deptIdDArr.push(deptId);
			}
		});
		var deptHtml = "";
		for(var index in deptJson){
			var deptId = deptJson[index].deptId;
			var deptName = deptJson[index].deptName;
			var orgCode = deptJson[index].orgCode;
			if(jQuery.inArray(orgCode, orgCodeArr)>-1&&jQuery.inArray(deptId, deptIdDArr)==-1){
				deptHtml += '<option value="'+deptId+'">'+deptName+'</option>';
			}
		}
		jQuery("#${random}_dept").html(deptHtml);
	}else{
		var deptIdDArr = [];
		jQuery("#${random}_deptd").find("option").each(function(){
			var deptId = jQuery(this).attr("value");
			deptIdDArr.push(deptId);
		});
		var deptHtml = "";
		for(var index in deptJson){
			var deptId = deptJson[index].deptId;
			var deptName = deptJson[index].deptName;
			if(jQuery.inArray(deptId, deptIdDArr)==-1){
				deptHtml += '<option value="'+deptId+'">'+deptName+'</option>';
			}
		}
		jQuery("#${random}_dept").html(deptHtml);
	}
}
var deptList = jQuery("#${random}_dept");
var deptListClone = jQuery("#${random}_dept").clone(true);
	/* 选择科室 */
function choiceDept(){
	var url = "#DIA_inline?inlineId=${random}_choiceDeptDiv";
	$.pdialog.open(url, '${random}_choiceDept', "选择科室", {
		mask : true,
		width : 500,
		height : 440
	});
	var deptIds = jQuery("#${random}_deptId").val().split(",");
	deptListClone.find("option").each(function(){
			for ( var i = 0; i < deptIds.length; i++) {
				    if (jQuery(this).attr("value") == deptIds[i]) {
						jQuery(this).remove();  
					}  }
		 });
} 
function okDeptButton(){
	
 	var alreadyValue=new Array();
	var alreadyText =new Array();
	jQuery("#${random}_deptd").find("option").each(function(){
		alreadyValue.push(jQuery(this).attr("value"));
		alreadyText.push(jQuery(this).text());
	});
	
	if(choiceId=='2'){
		var deptNameArr=jQuery("#${random}_deptKeyName").attr("value").split(",");
		var deptIdArr=jQuery("#${random}_deptKey").attr("value").split(",");
		if((alreadyValue.length>1)&&(deptIdArr.length>1)){
			alert("科室和指标不能同时选择多个,科室选择多个时,指标只能选一个,反之相同(请重新选择指标)!");
		}
		else{
			jQuery("#${random}_deptName").attr("value",alreadyText);
			jQuery("#${random}_deptId").attr("value",alreadyValue);
			$.pdialog.closeCurrentDiv('${random}_choiceDeptDiv');
		} 
	}else{
		jQuery("#${random}_deptName").attr("value",alreadyText);
		jQuery("#${random}_deptId").attr("value",alreadyValue);
		$.pdialog.closeCurrentDiv('${random}_choiceDeptDiv');
	}
};
		//指定添加
function addDept()  {
		var leftValue = new Array();
		var leftText = new Array();
		deptList.find("option").each(function(){
			if(jQuery(this).attr("selected")){
				leftValue.push(jQuery(this).attr("value"));
				leftText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < leftValue.length; i++) {
			var item = "<option value="+leftValue[i]+">"+ leftText[i]+ "</option>";
			jQuery("#${random}_deptd").append(item);
			deptList.find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
			deptListClone.find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
		}
}
			
			
		//指定删除
function delDept() {
		var rightValue = new Array();
		var rightText = new Array();
		jQuery("#${random}_deptd").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				rightValue.push(jQuery(this).attr("value"));
				rightText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < rightValue.length; i++) {
			deptList.append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			deptListClone.append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			jQuery("#${random}_deptd").find("option").each(function() {
					if (jQuery(this).attr("value") == rightValue[i]) {
							jQuery(this).remove();
					}
				});
				}
}
		
		
		//全部添加
function addAll() {
// 	deptListClone.find("option").each(function() {
// 			jQuery("#${random}_deptd").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
// 			jQuery(this).remove();
// 		});
	
	jQuery("#${random}_dept").find("option").each(function() {
			jQuery("#${random}_deptd").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
			jQuery(this).remove();
		});
	}
		
		
		//全部删除
function delAll() {
		jQuery("#${random}_deptd").find("option").each(function() {
			deptList.append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
			deptListClone.append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
			jQuery(this).remove();
		});
	}
		
</script>


<!-- -----------------快速搜索科室(START)----------------- -->
	<script type="text/javascript">
		function quickDeptNameSelect(){
			 var inputDept=jQuery("#${random}_quickDeptSelect").attr("value");
			 //alert(inputDept);
			 //var quickDeptList=deptList;
			 deptList.empty();
			 if(inputDept==null||inputDept==""){
				 deptListClone.find("option").each(function(){
					deptList.append(jQuery(this).clone(true));
				 });
			 }
			 deptListClone.find("option").each(function(){
				 if(jQuery(this).text().indexOf(inputDept)>=0){
					 deptList.append(jQuery(this).clone(true));
					}
			 });
			/* jQuery.ajax({
				dataType : 'json',
				url:'quickDeptSelect?quickDeptStr='+inputDept,
			    type: 'POST',
			    error: function(data){
			    	alert(":error");
			    },success: function(data){
			    	var url = data["deptString"];
			    	//alert(url);
					$('#${random}_dept').html(url);
			    }
			});  */
			//alert("quickDeptSelect");
		}
	</script>
	<!-- -----------------快速搜索科室(END)----------------- -->
	