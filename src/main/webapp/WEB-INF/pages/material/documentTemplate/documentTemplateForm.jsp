<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>

<script>
	jQuery(document).ready(function() {
		if(${entityIsNew}){
			jQuery("#documentTemplate_templateType").change(function(){
				$.get("${ctx}/getDocTemDatas?templateType="+this.value,function(data){
					var output = "";
					$.each(data.inputs,function(i,val){
						 output += "<div style='float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap'>"
						 		+ "<label style='text-align:center;'>"+val.name+"</label>"
						 		+ "<input type='checkbox' name='documentTemplate.inputArea' "+(val.checked==true?'checked':'')+" value='"+val.name+"'/>"
						 		+ "<input type='hidden' value='"+val.necessary+"'/>"
						 		+ "<input type='hidden' name='documentTemplate.inputAreaValue' value='"+val.value+"'/>"
						 		+"</div>";
			        });
					jQuery("#template_inputArea").empty().append(output);
					output = "";
					$.each(data.lists,function(i,val){
						 output += "<div style='float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap'>"
						 		+ "<label style='text-align:center;'>"+val.name+"</label>"
						 		+ "<input type='checkbox' name='documentTemplate.listArea' "+(val.checked==true?'checked':'')+" value='"+val.name+"'/>"
						 		+ "<input type='hidden' value='"+val.necessary+"'/>"
						 		+ "<input type='hidden' name='documentTemplate.listAreaValue' value='"+val.value+"'/>"
						 		+"</div>";
			        });
					jQuery("#template_listArea").empty().append(output);
					output = "";
					$.each(data.foots,function(i,val){
						 output += "<div style='float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap'>"
						 		+ "<label style='text-align:center;'>"+val.name+"</label>"
						 		+ "<input type='checkbox' name='documentTemplate.footArea' "+(val.checked==true?'checked':'')+" value='"+val.name+"'/>"
						 		+ "<input type='hidden' value='"+val.necessary+"'/>"
						 		+ "<input type='hidden' name='documentTemplate.footAreaValue' value='"+val.value+"'/>"
						 		+"</div>";
			        });
					jQuery("#template_footArea").empty().append(output);
					addClickFunctionToCheckBox();
				});
			});
		}else{
			jQuery("#documentTemplate_templateType").attr("disabled","disabled");
		}
		jQuery('#savelink').click(function() {
			jQuery("#documentTemplateForm").find("input[type='checkbox']").each(function(){
				var checked = jQuery(this).attr("checked");
				if(!!checked){
					
				}else{
					jQuery(this).next().next().attr("disabled","true");
				}
			});
			jQuery("#documentTemplateForm").attr("action","saveDocumentTemplate?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#documentTemplateForm").submit();
		});
		addClickFunctionToCheckBox();
	});
	function addClickFunctionToCheckBox(){
		jQuery("input[type='checkbox']").each(function(){
			$(this).click(function(){
				var obj = $(this).next().val();
				if(obj=="true"){
					alertMsg.error("该项为必选项！");
					return false;
				}
				return true;
			});
			var obj = $(this).prev();
			obj.unbind( 'mouseover' ).bind("mouseover",function(){
				obj.css({"color":"red" }); 
			});
			obj.unbind( 'mouseout' ).bind("mouseout",function(){
				obj.css({"color":"black" }); 
			});
			obj.unbind( 'dblclick' ).bind("dblclick",function(){
				var text = obj.text();
				obj.text("");
				obj.append("<input id='insertlll' type='text' style='margin:-3px' value="+text+" size='16' onblur='checkInput(this)'></input>")
				obj.children().focus();
			}); 
		});
	}
	function checkInput(obj){
		var p = jQuery(obj).parent();
		p.text(obj.value);
		p.next().val(obj.value);
	}
	//校验模版名称是否重复
	function validateTemplateName(obj){
		if("${documentTemplate.templateName}"==obj.value){
			return;
		} 
		$.get("${ctx}/checkTemplateName?templateType="+jQuery("#documentTemplate_templateType").val()+"&templateName="+obj.value,function(data){
			if(data.exists){
				alertMsg.error("该类型下模板名称已存在，请修改！");
	   			obj.value="";
			}
		});
	}
	
</script>

<style>
legend {
    color:#06c;
    padding:5px 10px;
    font-weight:500; 
    border:0
}
</style>
</head>

<div class="page">
	<div class="pageContent">
		<form id="documentTemplateForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="documentTemplate.id"/>
					<s:hidden key="documentTemplate.orgCode"/>
					<s:hidden key="documentTemplate.copyCode"/>
					<s:hidden key="documentTemplate.beUsed"/>
					<s:if test="%{!entityIsNew}">
						<input type="hidden" name="documentTemplate.templateType" value="${documentTemplate.templateType}"/> 
					</s:if>
					<s:select id="documentTemplate_templateType" key="documentTemplate.templateType" name="documentTemplate.templateType" 
					list="templateTypes"/>
					<s:textfield key="documentTemplate.templateName" name="documentTemplate.templateName" cssClass="required" onblur="validateTemplateName(this)"/>
					<s:textfield key="documentTemplate.title" name="documentTemplate.title" cssClass="required"/>
				</div>
				<br/><br/>
				<!-- *****************************inputArea************************************ -->
				<div>
					<fieldset style="height:140px;">
						<legend><s:text name="documentTemplate.inputArea"></s:text></legend>
						<div id="template_inputArea">
							<s:iterator value="inputs" id="input">
								<div style="float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap">
									<label style="text-align:center;"><s:property value="name"/></label>
									<input type="checkbox" name="documentTemplate.inputArea" ${input.checked==true?'checked':''} value="<s:property value="name"/>"/>
									<input type="hidden" value="<s:property value="necessary"/>"/>
									<input type="hidden" name="documentTemplate.inputAreaValue" value="<s:property value="value"/>"/>
								</div>
							</s:iterator>
						</div>
					</fieldset>
				</div><br/>
				<!-- *****************************listArea************************************ -->
				<div>
					<fieldset style="height:180px;">
						<legend><s:text name="documentTemplate.listArea"></s:text></legend>
						<div id="template_listArea">
							<s:iterator value="lists" id="list">
								<div style="float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap">
									<label style="text-align:center;"><s:property value="name"/></label>
									<input type="checkbox" name="documentTemplate.listArea" ${list.checked==true?'checked':''} value="<s:property value="name"/>"/>
									<input type="hidden" value="<s:property value="necessary"/>"/>
									<input type="hidden" name="documentTemplate.listAreaValue" value="<s:property value="value"/>"/>
								</div>
							</s:iterator>
						</div>
					</fieldset>
				</div><br/>
				<!-- *****************************footArea************************************ -->
				<div>
					<fieldset style="height:70px;">
						<legend><s:text name="documentTemplate.footArea"></s:text></legend>
						<div id="template_footArea">
							<s:iterator value="foots" id="foot">
								<div style="float:left;border:1px solid #B8D0D6;padding:2px;margin:5px;white-space:nowrap">
									<label style="text-align:center;"><s:property value="name"/></label>
									<input type="checkbox" name="documentTemplate.footArea" ${foot.checked==true?'checked':''} value="<s:property value="name"/>"/>
									<input type="hidden" value="<s:property value="necessary"/>"/>
									<input type="hidden" name="documentTemplate.footAreaValue" value="<s:property value="value"/>"/>
								</div>
							</s:iterator>
						</div>
					</fieldset>	
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
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





