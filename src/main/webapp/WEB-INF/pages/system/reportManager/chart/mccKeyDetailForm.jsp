<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="mccKeyDetailDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='mccKeyDetailDetail.heading'/>"/>
    <script>
	jQuery(document).ready(function() {
		
		jQuery('#${random}savelink').click(function() {
			jQuery("#mccKeyDetailForm").attr("action","saveMccKeyDetail?popup=true&navTabId="+'${navTabId}');
			jQuery("#mccKeyDetailForm").submit();
			//alert(jQuery("#mccKeyDetailForm").attr("action"));
		});
		jQuery("#mkdC").colorpicker({
		    fillcolor:true
		});
		//ShowColorControl("mkdC");
	});
</script>
</head>
<body >
<div class="page">
		<div class="pageContent">

<s:form id="mccKeyDetailForm" action="saveMccKeyDetail?popup=true"  method="post" validate="false" cssClass="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
	<div class="pageFormContent" layoutH="56">
		<div class="unit">
	 		<s:textfield key="mccKeyDetail.mccKey.mccKeyId" required="false" name="mccKeyDetail.mccKeyId" value="%{mccKeyDetail.mccKey.mccKeyId}" maxlength="255"   rows="5"  cols="50"  cssClass="text medium" readonly="true" />
	 		<s:if test="%{mccKeyDetail.mccKeyDetailId!=null}">
				<s:textfield key="mccKeyDetail.mccKeyDetailId" required="true"  cssClass="text medium" readonly="true" value="%{mccKeyDetail.mccKeyDetailId}"/>
			</s:if>
			<s:else>
				<s:textfield key="mccKeyDetail.mccKeyDetailId" required="true" cssClass="text medium"  onblur="checkId(this,'MccKeyDetail','mccKeyDetailId')"/>
			</s:else>
	   	 	
	 	</div>
	 	<div class="unit">
	 		<s:textfield key="mccKeyDetail.minValue" required="false"	maxlength="255"   rows="5"  cols="50"  cssClass="text medium" />
	 		<s:textfield key="mccKeyDetail.maxValue" required="false"	maxlength="255"   rows="5"  cols="50"  cssClass="text medium" />
	    </div>
	 	<div class="unit">
	 		<s:textfield key="mccKeyDetail.state" required="false"	maxlength="255"   rows="5"  cols="50"  cssClass="text medium" />
	 		<s:textfield key="mccKeyDetail.color" id="mkdC" required="false"	maxlength="255"   rows="5"  cols="50" cssClass="text medium" readonly="true"/>
	 	</div>
	 	<div class="unit">
	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 		<s:checkbox id="warning" key="mccKeyDetail.warning" theme="simple"></s:checkbox><fmt:message key="mccKeyDetail.warning"/>
	 	</div>
 	</div>
 		
 		<div class="formBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}savelink">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div></li>
					</ul>
				</div>
</s:form>
</div>
</div>
</body>
