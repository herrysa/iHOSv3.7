<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet" href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<head>
<title><fmt:message key="searchDetail.title" />
</title>
<meta name="heading" content="<fmt:message key='searchDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		var s1 = jQuery("#search_entity_Name");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#searchForm").attr("action","saveSearch?popup=true&navTabId="+'${navTabId}');
			jQuery("#searchForm").submit();
		});
	});
	jQuery(function(){
		jQuery("#search_entity_Name").autocomplete("autocomplete",{
			width : 300,
			multiple : false,
			autoFill : false,
			matchContains : true,
			matchCase : true,
			dataType : 'json',
			parse : function(test) {
				var data = test.autocompleteList;
				if (data == null || data == "") {
					var rows = [];
					rows[0] = {
						data : "没有结果",
						value : "",
						result : ""
					};
					return rows;
				} else {
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i].entityName + ","
									+ data[i].entityName + ","
									+ data[i].entityDesc+":"
									+ data[i].entityName,
							value : data[i].entityName,
							result : data[i].entityName
						};
					}
					return rows;
				}
			},
			extraParams : {
				flag : 2,
				entity : "SearchEntity",
				cloumnsStr : "entityDesc,entityName"
			},
			formatItem : function(row) {
				return dropId(row);
			},
			formatResult : function(row) {
				return dropId(row);
			}
		});
		jQuery("#search_entity_Name").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#search_entity_name").attr("value",
					getId(row));
			jQuery("#search_entity_Id").attr("value",
					getId(row));
			//alert(jQuery("#search_entity_Id").attr("value"));
		});
		
	})
</script>
<style>
.label {text-align:left;}
</style>
</head>


<div class="page">
	<div class="pageContent">
		<form id="searchForm" method="post" action="saveSearch?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">

				<s:url id="saveSearch" action="saveSearch" />

				<div class="unit">
					<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:if test="%{editType==1}">
						<s:textfield key="search.searchId" cssClass="required" maxlength="30"
							readonly="true" />
					</s:if>
					<s:else>
						<s:textfield key="search.searchId" cssClass="required" maxlength="30" />
					</s:else>


					<s:textfield key="search.searchName" cssClass="required" maxlength="50" />
				</div>
				<div class="unit">
					<s:textfield key="search.title" cssClass="required" style="width:390px"/>

					<%-- <s:textfield key="search.orderBy" required="false" maxlength="45" /> --%>
				</div>
				<div class="unit">
					<label for="search.multiSelect"><fmt:message key='search.multiSelect' />:</label>
					<span style="float:left;width:140px"><s:checkbox key="search.multiSelect" theme="simple" style="float:left;"></s:checkbox></span>
				<s:select key="search.searchBarType" required="false"
						maxlength="20" list="searchBarTypeList" listKey="value"
						cssClass="required" listValue="content" emptyOption="true"></s:select>
					
					<%-- <label for="search.sortable"><fmt:message key='search.sortable' />:</label>
					<span style="float:left;width:140px"><s:checkbox key="search.sortable"  style="float:left;" theme="simple"></s:checkbox>
					</span> --%>
					
				</div>
				<div class="unit">
					<s:textfield key="search.rownumWidth" cssClass="required digits"></s:textfield>
					<s:textfield key="search.pageSize" cssClass="required digits"/>
				</div>
						<div class="unit">
						<s:textfield key="search.formName" required="false" maxlength="50" />
						<s:textfield key="search.myKey" cssClass="required" maxlength="45" />
						</div>
					
				
				<%-- <div class="unit">
					<s:textarea key="search.formAction" required="false"
						maxlength="150" rows="5" cols="54" />

					
				</div> --%>
				<div class="unit">
				<s:hidden id="search_entity_Id" name="search.entityName" value="%{search.entityName}"></s:hidden>
				<label><s:text name="search.entityName"></s:text>:</label>
					<span style="float:left;width:140px">
					<s:textfield id="search_entity_Name"  cssClass="defaultValueStyle" value="%{search.entityName}"  size="25" onfocus="clearInput(this,jQuery('#search_entity_Id'))" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/></span>
					<label for="search.opened"><fmt:message key='search.opened' />:</label>
					<span style="float:left;width:140px"><s:checkbox key="search.opened" theme="simple" style="float:left;"></s:checkbox></span>
					<%-- <s:textfield key="search.orderBy" required="false" maxlength="45" /> --%>
				</div>
				<div class="unit">
					<label><fmt:message key='menu.subSystem' />:</label>
					<s:select name="search.subSystem"   maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
				</div>
				<div class="unit">
					<s:textarea key="search.mysql" cssClass="required" cols="54"
						rows="15"  />

					<!-- <div class="control-group">
            <label class="control-label" for="textarea">Textarea</label>
            <div class="controls">
              <textarea class="input-xlarge" id="textarea" rows="3"></textarea>
            </div>
          </div> -->
					<!-- <textarea cols="1000" rows="10" class="input-xlarge" ></textarea> -->
				</div>
				<div class="unit">
					<s:textarea key="search.remark" required="false" maxlength="200"
						rows="2" cols="54" />

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

