<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchUrlDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='searchUrlDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#searchUrlForm").attr("action","saveSearchUrl?popup=true&navTabId="+'${navTabId}');
			jQuery("#searchUrlForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchUrlForm" method="post"
			action="saveSearchUrl?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:url id="saveSearchUrl" action="saveSearchUrl" />
					<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:if test="%{editType==1}">
						<s:textfield key="searchUrl.searchUrlId" cssClass="required"
							maxlength="30" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield key="searchUrl.searchUrlId" cssClass="required"
							maxlength="30" />
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="searchUrl.title" cssClass="required"
						maxlength="50" />
				</div>
				<div class="unit">
					<s:textfield key="searchUrl.oorder" cssClass="required digits" />

				</div>
				<div class="unit">
					<s:textfield key="search.searchId" name="searchUrl.search.searchId"
						value="%{searchUrl.search.searchId}" required="false"
						maxlength="30" readonly="true" />
				</div>
				<div class="unit">
					<label><fmt:message key='searchUrl.nullFlag' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="searchUrl.nullFlag" theme="simple" /> </span>
					<s:if test="%{searchUrl.title==null}">
					<label><fmt:message key='searchUrl.visible' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="searchUrl.visible" theme="simple"  value="true"/> </span>
					</s:if>
					<s:else>
					<label><fmt:message key='searchUrl.visible' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="searchUrl.visible" theme="simple"  value="%{searchUrl.visible}"/> </span>
					</s:else>
				</div>
				<div class="unit">
					<s:textarea key="searchUrl.url" cssClass="required" maxlength="1000"
						rows="10" cols="54" />
				</div>
				<div class="unit">
					<s:textfield key="searchUrl.icon" cssClass="required" maxlength="50"/>
				</div>
				<div class="unit">
					<label>结账检查:</label>
					<input  type="text" id="searchUrl_jzSystem" size="50" maxlength="50"/>
					<input  type="hidden" id="searchUrl_jzSystem_id" name="searchUrl.jzSystem" />
					<script>
					jQuery("#searchUrl_jzSystem").treeselect({
						optType:"multi",
						dataType:"sql",
						sql:"SELECT m.code id,me.menuName name FROM sy_model m,t_menu me WHERE m.menuId=me.menuId  ORDER BY jzSn",
						initSelect:"${searchUrl.jzSystem }",
						exceptnullparent:true,
						lazy:false,
						minWidth:'200px'
					});
					</script>
				</div>
				<div class="unit">
					<s:select list="#{'S1':'S1','S2':'S2','M1':'M1','M2':'M2','G':'G' }" key="searchUrl.herpType" listKey="key"
						listValue="value" emptyOption="true" maxlength="50" width="50px"></s:select>
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
		</form>
	</div>
</div>
