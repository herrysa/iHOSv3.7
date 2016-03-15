<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="menuDetail.title" /></title>
<meta name="heading" content="<fmt:message key='menuDetail.heading'/>" />
<script>
	/* function firstSel(){
	 alert("fdc");
	 } */
	jQuery(document).ready(function() {

		jQuery.subscribe('firstSel', function() {
			var chargeid = document.getElementById("sel_rootId").value;
			jQuery("#menusubSystemmenuId").attr("value", chargeid);

			/* 			 var url = jQuery("#selectWithReloadTopic");
			 alert(url.data);  */
		});
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#menuForm").attr("action","saveMenu?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#menuForm").submit();
			//menuGridReload(); 
		});
		jQuery('input:text:first').focus();
		
		setTimeout(function(){
			var parentKey = jQuery("#parentKey").val();
			var parentValue = jQuery("#parentValue").val();
			$.ajax({
			    url: 'menuDoubleSelect?rootId='+jQuery("#menusubSystemmenuId").val(),
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			        var selectObj = jQuery("#selectWithReloadTopic");
			        var selected = jQuery("#selectWithReloadTopic").val();
			        if(data.subMenus!=null){
			        jQuery.each(data.subMenus,function(i,item){
			        	if(item.menuId!=parentKey){
			        		selectObj.append("<option value='"+item.menuId+"'>"+item.menuName+"</option>");
			        	}else{
			        		selectObj.find("option").eq(0).remove(); 
			        		//jQuery("#selectWithReloadTopic").remove("<option value='"+item.menuId+"'>"+item.menuName+"</option>");
			        		selectObj.append("<option selected value='"+item.menuId+"'>"+item.menuName+"</option>");
			        	}
			        });
			        }
			    }
			});
		},200);
	});
</script>
</head>
<s:url id="saveMenu" action="saveMenu" />
<div class="page">
	<div class="pageContent">
		<s:form id="menuForm" method="post" action="saveMenu?popup=true"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
			<s:hidden key="menu.systemType"/>
				<div class="unit">

					<s:hidden name="editType" value="%{editType}" label="editType" />

					<s:if test="%{editType==1}">
						<s:textfield key="menu.menuId" cssClass="required" maxlength="30"
							readonly="true" />
						<%-- <s:textfield key="person.personId" required="true" /> --%>
					</s:if>
					<s:else>
						<s:textfield key="menu.menuId" cssClass="required" maxlength="30" onblur="checkId(this,'Menu','menuId')"/>
					</s:else>
					<s:textfield key="menu.menuName" cssClass="required" maxlength="50" />
				</div>
				<div class="unit">
					<s:textfield key="menu.menuLevel" cssClass="required" />
					<s:textfield key="menu.displayOrder" required="false" />
				</div>
				<div class="unit">
					<s:textarea key="menu.menuAction" cssClass="required" maxlength="5000"
						rows="5" cols="55" />
				</div>
				<div class="unit">
					<label><fmt:message key='menu.leaf' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="menu.leaf"  theme="simple"></s:checkbox>
					</span> 
					<s:if test="%{editType==1}">
					<label><fmt:message key='menu.disabled' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="menu.disabled" theme="simple"></s:checkbox>
					</span>
					</s:if>
					<s:else>
						<s:hidden key="menu.disabled" />
					</s:else>
				</div>
				<div class="unit">
					<s:hidden id="menusubSystemmenuId" name="menu.subSystem.menuId"
						readonly="true" />
					<s:hidden id="parentKey" value="%{menu.parent.menuId}"/>
					<s:hidden id="parentValue" value="%{menu.parent.menuName}"/>
					<s:if test="%{editType==0 || (editType==1 && menu.parent!=null)}">
						<s:url id="remoteurl1" action="menuDoubleSelect">
							<%-- <s:param name="rootIdFirst" value="menu.subSystem.menuId"></s:param> --%>
						</s:url>
						<sj:select href="%{remoteurl1}"
							label="%{getText('menu.subSystem')}"
							value="%{menu.subSystem.menuId}" id="sel_rootId" name="rootId"
							list="subSystems" listKey="menuId" listValue="menuName"
							emptyOption="true" cssClass="required"
							onChangeTopics="firstSel,reloadsecondlist" />

						<sj:select href="%{remoteurl1}" key="menu.parent"
							id="selectWithReloadTopic" formIds="menuForm"
							reloadTopics="reloadsecondlist" name="menu.parent.menuId"
							value="%{menu.parent.menuId}" list="subMenus" listKey="menuId"
							listValue="menuName" emptyOption="false" cssClass="required" />
					</s:if>

					<s:else>
						<s:textfield label="%{getText('menu.subSystem')}"
							value="%{menu.subSystem.menuName}" disabled="true" />
						<s:textfield label="%{getText('menu.parent')}"
							value="%{menu.parent.menuName}" disabled="true" />
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="menu.entityName" required="false" maxlength="50" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</s:form>
	</div>
</div>
