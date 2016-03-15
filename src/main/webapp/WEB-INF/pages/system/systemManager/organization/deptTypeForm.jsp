<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="deptTypeDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='deptTypeDetail.heading'/>" />
<script>
jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			jQuery("#deptTypeForm").attr("action","saveDeptType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#deptTypeForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="deptTypeForm" method="post" action="deptTypeForm?popup=true" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
		<div class="pageFormContent" layoutH="56">
				<div class="unit">
		<s:if test="%{deptType.deptTypeId==null}">
		<s:textfield key="deptType.deptTypeId" cssClass="required" notrepeat='科室类型ID已存在' validatorParam="from DeptType dt where dt.deptTypeId=%value%"/>
		</s:if>
		<s:else>
		<s:textfield key="deptType.deptTypeId" cssClass="required" readonly="true"/>
		</s:else>
	</div>
	<div class="unit">
	<s:if test="%{entityIsNew}">
	<s:textfield key="deptType.deptTypeName" cssClass="required" maxlength="20" notrepeat='科室类型名称已存在' validatorParam="from DeptType dt where dt.deptTypeName=%value%"/>
	</s:if>
	<s:else>
	<s:textfield key="deptType.deptTypeName" cssClass="required" maxlength="20" oldValue="'${deptType.deptTypeName}'" notrepeat='科室类型名称已存在' validatorParam="from DeptType dt where dt.deptTypeName=%value%"/>
	</s:else>
		</div>
	<%-- <s:url id="dicSelectList" action="dictionaryItemSelectList"	namespace="/system">
	<s:param name="dicCode">radioyesno</s:param>
</s:url>

<sj:radio key="deptType.disabled" required="true"  href="%{dicSelectList}"	list="dictionaryItemsSelectList" listKey="value" listValue="content" buttonset="false"/> --%>
	<s:if test="%{deptType.deptTypeId!=null}">
	<div class="unit">
	<label><fmt:message key='deptType.disabled' />:</label>
	<s:checkbox key="deptType.disabled" theme="simple"></s:checkbox>
	</div>
	</s:if>
	</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
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
		</form>
	</div>
</div>