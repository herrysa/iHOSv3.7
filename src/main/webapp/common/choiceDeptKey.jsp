<%@ page language="java" pageEncoding="UTF-8"%>

	<!-- -----------------选择指标(弹出框START)----------------- -->
	<div id="${random}_choiceDeptKeyDiv" style="display: none">
	<div class="pageContent" align="right">
		<form id="${random}_choiceDeptKeyForm" method="post" enctype="multipart/form-data" class="pageForm required-validate" >
			<div class="pageFormContent" align="center" layoutH="56">
		<table>
		<tr>
		<td id="${random}_deptNSelect">
		
		</td>
		<td valign="middle" align="center" width="50">
		<input id="${random}_addKeyBut" type="button"  style="width: 37px" class="btn"  value=">" onclick="addKey()"/><br>
		<input id="${random}_delKeyBut" type="button"  style="width: 37px" value="&lt;" onclick="delKey()"/><br>
		<input id="${random}_allAddKeyBut" type="button"  style="width: 37px" value=">>" onclick="addKeyAll()"/><br>
		<input id="${random}_allDelKeyBut" type="button" style="width: 37px" value="&lt;&lt;" onclick="delKeyAll()"/>
		</td>
		<td>
		已选指标：<br>
		<select id="${random}_deptKeyAld" ondblclick="delKey()" multiple="true" style="width:200px;" name="selectedall" size="14">
			<s:property value="alDeptKey" escapeHtml="false"/>
		</select>
		</td>
		</tr>
		</table>
		</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="okDeptKeyButton()">确定</button>
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
	<script type="text/javascript">
	/* 选择指标*/
	var deptKeyList;
	var deptKeyListClone;
	function choiceDeptKey(cId){
		choiceId=cId;
		var url = "#DIA_inline?inlineId=${random}_choiceDeptKeyDiv";
		
		var deptN=jQuery("#${random}_deptName").val();
		if(deptN==null||deptN==""){
			jQuery("#${random}_deptNSelect").html('<input type="text" id="${random}_quickKeySelect"  size="16"/><input type="button" onclick="quickKeySelect()" value="搜"/><br>选择指标：<br><select id="${random}_deptKeyAl" ondblclick="addKey()" name="selectableall" multiple="true" style="width: 200px; height: auto" size="14"><c:forEach items="${orgKeys}" var="allKey"><c:if test="${allKey.fieldTitle!=null }"><option value="${allKey.fieldName}">${allKey.formulaFieldId}&nbsp;&nbsp;${allKey.fieldTitle}</option></c:if></c:forEach></select>');
			//alert(jQuery("#deptNSelect").html());
			//meaSure=true;
		}else{
			jQuery("#${random}_deptNSelect").html('<input type="text" id="${random}_quickKeySelect"  size="16"/><input type="button" onclick="quickKeySelect()" value="搜"/><br>选择指标：<br><select id="${random}_deptKeyAl" ondblclick="addKey()" name="selectableall" multiple="true" style="width: 200px; height: auto" size="14"><c:forEach items="${deptKeys}" var="allKey"><c:if test="${allKey.fieldTitle!=null }"><option value="${allKey.fieldName}">${allKey.formulaFieldId}&nbsp;&nbsp;${allKey.fieldTitle}</option></c:if></c:forEach></select>');
			//meaSure=false;
		}
		
	  
	 $.pdialog.open(url, '${random}_choiceDeptKey', "选择指标", {
		mask : true,
		width : 500,
		height : 400
	});
	 var deptKeys = jQuery("#${random}_deptKey").val().split(",");
		deptKeyList=jQuery("#${random}_deptKeyAl");
		deptKeyListClone=deptKeyList.clone(true);
		deptKeyListClone.find("option").each(function(){
			for ( var i = 0; i < deptKeys.length; i++) {
				    if (jQuery(this).attr("value") == deptKeys[i]) {
						jQuery(this).remove();  
					}  }
		 });

		
	} 
	
	
	function okDeptKeyButton(){
		//alert();
		 	var alreadyValue=new Array();
			var alreadyText =new Array();
			jQuery("#${random}_deptKeyAld").find("option").each(function(){
				alreadyValue.push(jQuery(this).attr("value"));
				alreadyText.push(jQuery(this).text());
			});
		if(choiceId=='2'){
			var deptNameArr=jQuery("#${random}_deptName").attr("value").split(",");
			var deptIdArr=jQuery("#${random}_deptId").attr("value").split(",");
			if((alreadyValue.length>1)&&(deptIdArr.length>1)){
				alert("科室和指标不能同时选择多个,指标选择多个时,科室只能选一个,反之相同(请重新选择科室)!");
			}else{
				jQuery("#${random}_deptKeyName").attr("value",alreadyText);
				jQuery("#${random}_deptKey").attr("value",alreadyValue); 
				$.pdialog.closeCurrentDiv('${random}_choiceDeptKeyDiv');
			}
		}else{
			jQuery("#${random}_deptKeyName").attr("value",alreadyText);
			jQuery("#${random}_deptKey").attr("value",alreadyValue); 
			$.pdialog.closeCurrentDiv('${random}_choiceDeptKeyDiv'); 
		}
	};
	//指定添加
	function addKey() {
		var leftValue = new Array();
		var leftText = new Array();
		deptKeyList.find("option").each(function(){
			if(jQuery(this).attr("selected")){
				leftValue.push(jQuery(this).attr("value"));
				leftText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < leftValue.length; i++) {
			var item = "<option value="+leftValue[i]+">"+ leftText[i]+ "</option>";
			jQuery("#${random}_deptKeyAld").append(item);
			deptKeyListClone.find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
			deptKeyList.find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
		}
	}
		
		
	//指定删除
	function delKey() {
		var rightValue = new Array();
		var rightText = new Array();
		jQuery("#${random}_deptKeyAld").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				rightValue.push(jQuery(this).attr("value"));
				rightText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < rightValue.length; i++) {
			deptKeyList.append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			deptKeyListClone.append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			jQuery("#${random}_deptKeyAld").find("option").each(function() {
					if (jQuery(this).attr("value") == rightValue[i]) {
							jQuery(this).remove();
					}
				});
				}
	}
	
	
	//全部添加
	function addKeyAll() {
				deptKeyList.find("option").each(function() {
					jQuery("#${random}_deptKeyAld").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					jQuery(this).remove();
				});
				deptKeyListClone.find("option").each(function() {
					jQuery("#${random}_deptKeyAld").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					jQuery(this).remove();
				});
		}
	
	
	//全部删除
	function delKeyAll() {
				jQuery("#${random}_deptKeyAld").find("option").each(function() {
					deptKeyList.append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					deptKeyListClone.append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					jQuery(this).remove();
				});
		}
	</script>
	<!-- -----------------选择指标(弹出框END)----------------- -->
	<script type="text/javascript">
	
	function quickKeySelect(){
		var inputKey=jQuery("#${random}_quickKeySelect").attr("value");
		 deptKeyList.empty();
		 if(inputKey==null||inputKey==""){
			 deptKeyListClone.find("option").each(function(){
				deptKeyList.append(jQuery(this).clone(true));
			 });
		 }
		 //alert(deptKeyListClone.find("option").length);
		 deptKeyListClone.find("option").each(function(){
			if(jQuery(this).text().indexOf(inputKey)>=0){
				deptKeyList.append(jQuery(this).clone(true));
			} 
		 }); 
		/* jQuery.ajax({
			dataType : 'json',
			url:'quickKeySelect?quickKeyStr='+inputKey+'&meaSure='+meaSure,
		    type: 'POST',
		    error: function(data){
		    	alert(":error");
		    },success: function(data){
		    	var url = data["deptKeyStr"];
		    	jQuery('#${random}_deptKeyAl').html(url);
		    }
		}); */
	}
	</script>
	