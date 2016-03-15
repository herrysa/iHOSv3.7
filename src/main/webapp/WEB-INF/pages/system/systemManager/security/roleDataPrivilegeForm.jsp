<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
function getHtmlCon1(tarID, tarName, gridID, gridName,isSingle) {
	var url = "query?searchName=sel_role_data&actionName=querySelectSingle&popup=true&tarID="
				+ tarID + "&tarName=" + tarName + "&gridID=" + gridID
				+ "&gridName=" + gridName + "&isSingle="+isSingle;
				//alert(url);
				$.pdialog.open(url, "selectDataPrivilege", "选择数据权限", {mask:true,height:650,width:900});
				//jQuery("#htmlcon").html(result);
				//jQuery("#htmlcon").append(result);

				//jQuery("#htmlcon").attr(result);
}
jQuery(document).ready(function(){


	jQuery("#savelink").bind("click",function(){
		//alert("save it");
		jQuery("#dataPrivilegeForm").attr("action","saveDataPrivilegeGridEdit?popup=true&navTabId="+'${navTabId}');
		jQuery("#dataPrivilegeForm").submit();
	});

});

</script>
</head>
<body>
	<div class="page">
		<div class="pageContent">
			<form id="dataPrivilegeForm" name="dataPrivilegeForm" method="post" action=""
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">
					
					<s:hidden name="roleId" ></s:hidden>
						<s:hidden id="hiddenClusterIds" name="clusters"	required="true" />
						<table>
							<tr>
								<td><s:textfield id="hiddenClusterNames"
										name="clusterNames" cssClass="required" />&nbsp;&nbsp;</td>
								<td>
									<div style="margin-top: 0px;">
										<a id="person"
											href="javaScript:getHtmlCon1('hiddenClusterIds','hiddenClusterNames','ENTITYCLUSTERID','ENTITYCLUSTERID','2');"
											style="margin-top: 0px;">选择数据权限</a>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="savelink">保存</button>
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