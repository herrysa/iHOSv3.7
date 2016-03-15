<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<title><fmt:message key="userDetail.title" /></title>
<meta name="heading" content="<fmt:message key='userDetail.heading'/>" />
<script type="text/javascript">
	

	function getHtmlCon(tarID, tarName, gridID, gridName,isSingle) {
		var url = "query?searchName=sel_person&actionName=querySelectSingle&popup=true&tarID="
					+ tarID + "&tarName=" + tarName + "&gridID=" + gridID
					+ "&gridName=" + gridName+ "&isSingle="+isSingle;
					//alert(result);
					$.pdialog.open(url, "selectPerson", "选择人员", {mask:true,height:650,width:700});
					//jQuery("#htmlcon").html(result);
					//jQuery("#htmlcon").append(result);

					//jQuery("#htmlcon").attr(result);
	}
	function extraRole() {
		var userRolesSelected = "";
		jQuery("#userRolesSelected").find("option").each(
		function() {
			userRolesSelected += jQuery(this).attr("value")+ ",";
			});
			var url = "getPrivileges?type=all&roleName="
			+ userRolesSelected
			+ "&userId="
			+ "${user.id}";
			$.pdialog.open(url, "extraRole", "添加额外权限", {mask:true,height:600});　
	}
	
	function submitCallBack(json){
		alertMsg.info(json);
		navTab.closeCurrentTab();
	}
	jQuery(document).ready(
		function() {
			jQuery("#${random}savelink").bind("click",function() {
				var userRolesSelected = "";
				jQuery("#userRolesSelected").find("option").each(function() {
					userRolesSelected += jQuery(this).attr("value")
						+ ",";
					});
				jQuery("form[name=userForm]").attr("action","isaveUser?popup=true&userRolesSelected="
					+ userRolesSelected+"&navTabId="+'${navTabId}');
				jQuery("form[name=userForm]").submit();
				});
				jQuery("#extraAuthor").click(function() {
					var selectedRole = jQuery("#userRoles").val();
					var url = "getPrivilegesByRole?roleName="+ selectedRole;
					var winTitle = '<fmt:message key="userNew.title"/>';
					popUpWindow(url, winTitle,"width=500");
				});
				

						jQuery("#toRight").click(
							function() {
								var leftValue = new Array();
								var leftText = new Array();
								jQuery("#userRoles").find("option").each(function(){
									if(jQuery(this).attr("selected")){
										leftValue.push(jQuery(this).attr("value"));
										leftText.push(jQuery(this).text());
									}
								});
								for ( var i = 0; i < leftValue.length; i++) {
									var item = "<option value="+leftValue[i]+">"
												+ leftText[i]
											 	+ "</option>";
									jQuery("#userRolesSelected").append(item);
									jQuery("#userRoles").find("option").each(
										function() {
											if (jQuery(this).attr("value") == leftValue[i]) {
												jQuery(this).remove();
											}
									});
								}
						});
						jQuery("#toLeft").click(
							function() {
								//var leftValue = jQuery("#userRolesSelected").val();
								var rightValue = new Array();
								var rightText = new Array();
								jQuery("#userRolesSelected").find("option").each(function(){
									if(jQuery(this).attr("selected")){
										rightValue.push(jQuery(this).attr("value"));
										rightText.push(jQuery(this).text());
									}
								});
									for ( var i = 0; i < rightValue.length; i++) {
										jQuery("#userRoles").append(
											"<option value="+rightValue[i]+">"
															+ rightText[i]
															+ "</option>");
										jQuery("#userRolesSelected").find("option").each(
											function() {
												if (jQuery(this).attr("value") == rightValue[i]) {
														jQuery(this).remove();
												}
											});
											}
										});
						jQuery("#allToLeft").click(
							function() {
								jQuery("#userRolesSelected").find("option").each(
									function() {
										jQuery("#userRoles").append("<option value="
																	+ jQuery(this).attr("value")
																	+ ">"
																	+ jQuery(this).text()
																	+ "</option>");
										jQuery(this).remove();
								});
						});
						jQuery("#allToRight").click(
							function() {
								jQuery("#userRoles").find("option").each(
									function() {
										jQuery("#userRolesSelected").append(
											"<option value="
											+ jQuery(this).attr("value")
											+ ">"
											+ jQuery(this).text()
											+ "</option>");
										jQuery(this).remove();
								});
						});
						jQuery("#userRoles").find("option").each(function(){
							var role = jQuery(this);
							jQuery("#userRolesSelected").find("option").each(function(){
								if(role.attr("value")==jQuery(this).attr("value")){
									role.remove();
								}
							});
						});
					});
	function checkRepeatName(){
		var un = jQuery("#un").val();
		$.ajax({
		    url: 'checkRepeatName?userName='+un,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        alertMsg.error(data.message);
		        jQuery("#un").val("");
		    }
		});
	}
</script>
</head>
<body>

	<div class="page">
		<div class="pageContent">
			<form name="userForm" method="post" action=""
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
				<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:hidden key="user.id" />
					<s:hidden key="user.subSystem" />
					<div class="unit">
					<s:if test="%{editType==0}">
					<s:textfield id="un" key="user.username" cssClass="required" 
						notrepeat='用户名已存在' validatorParam="from User user where user.username=%value%"/>
					</s:if>
					<s:else>
					<s:textfield id="un" key="user.username" cssClass="required" oldValue="'${user.username}'"
						notrepeat='用户名已存在' validatorParam="from User user where user.username=%value%"/>
					</s:else>
					</div>
					
					<s:if test="%{editType==0}">
						<div class="unit">
							<s:password id="password" key="user.password" showPassword="true"
								cssClass="required" />
						</div>
						<div class="unit">
							<s:password key="user.confirmPassword" cssClass="required" equalto="#password" 
								showPassword="true"  />
						</div>
					</s:if>
					<s:else>
						<s:hidden key="user.password" showPassword="true" required="true"
							/>

						<s:hidden key="user.confirmPassword" value="%{user.password}"
							required="true" showPassword="true"
							/>
					</s:else>
					
					
					<s:if test="%{user.id!=null}">
					<div class="unit">
						
						<label><fmt:message key="user.enabled" />:
						</label>
						<s:checkbox key="user.enabled" id="user.enabled"
								theme="simple" fieldValue="true" /> 
								</div>
						</s:if>
						
						
							<%-- <fmt:message
									key="user.enabled" /><s:checkbox
									key="user.accountExpired" id="user.accountExpired"
									theme="simple" fieldValue="true" /> <fmt:message
									key="user.accountExpired" />
								<s:checkbox key="user.accountLocked" id="user.accountLocked"
									theme="simple" fieldValue="true" /> <fmt:message
									key="user.accountLocked" />
								<s:checkbox key="user.credentialsExpired"
									id="user.credentialsExpired" theme="simple" fieldValue="true" />
								<fmt:message key="user.credentialsExpired" />  --%>
					
					<div class="unit">
						<label for="userRoles"><fmt:message
								key="userProfile.assignRoles" />: </label>
						<table>
							<tr>
								<td><select id="userRoles" name="userRoles" multiple="true"
									style="width: 150px; height: auto" size="8">
										<c:forEach items="${roles}" var="role">
											<option value="${role.value}">${role.label}</option>
										</c:forEach>
								</select></td>
								<td width="50px"  align="center">
								<input id="toRight" class="btn" style="width: 37px" type="button" value=">">
						<br /> <input id="allToRight" class="btn" style="width: 37px" type="button" value=">>">
						<br /> <input id="toLeft" class="btn" style="width: 37px" type="button" value="&lt;">
						<br /> <input id="allToLeft" class="btn" style="width: 37px" type="button" value="&lt;&lt;">
								</td>
								<td>
									<select id="userRolesSelected" name="userRolesSelected"	multiple="true" style="width: 150px;height: auto" size="8">
											<c:forEach items="${roles}" var="role">
												<c:forEach items="${user.roles}" var="userRole">
													<c:if test="${userRole.name==role.value }">
														<option value="${role.value}">${role.label}</option>
													</c:if>
												</c:forEach>
											</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</div>
					<div class="unit" style="display:none">
						<label>额外权限:</label>
							<a id="addExtraPri" href="javaScript:extraRole()">添加额外权限</a>
							<input type="hidden" id="content" name="content"/>
					</div>

					<!-- <div id="inline2">
    <a href="javascript:;" onclick="jQuery.fancybox.close();">Close</a>
    <input type="button" class="btn btn-primary" value="Get selarrrow(多选ids)" onclick="alert(jQuery('#gridTable').jqGrid('getGridParam','selarrrow'));"/>
    <input type="button" class="btn btn-primary" value="获取选择行数据" onclick="getSelectRowData('idField','nameField','id','name');"/>
    <table id="gridTable"></table>
    <div id="gridPager"></div>
    
   </div> -->




					<div class="unit">
						<s:hidden id="hiddenPersonId" key="user.person.personId"
							required="true" />
						<table>
							<tr>
								<td><s:textfield id="hiddenPersonName"
										key="user.person.name" cssClass="required"/>&nbsp;&nbsp;
								</td>
								<td>
									<div style="margin-top: 0px;">
										<a id="person" href="javaScript:getHtmlCon('hiddenPersonId','hiddenPersonName','PERSONID','PNAME','1');" style="margin-top: 0px;"
											>选择人员</a>
									</div></td>
							</tr>
						</table>
					</div>

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
</body>
</html>