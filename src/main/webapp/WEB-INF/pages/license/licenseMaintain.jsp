<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
    jQuery(document).ready(function() {
	/* jQuery('#savelink').click(
		console.log("save click.");
			function() {
				//alert("save");
				jQuery("#v2cUpLoadForm").attr("action","updateLicenseV2C");
				jQuery("#v2cUpLoadForm").submit();
			}); */

	try {
	    // tableToGrid(".table-to-grid", {});
	} catch (e) {
	    alert(e.message)
	}
    });
    function uploadV2CFile() {
	//alert("save");
	//jQuery("#v2cUpLoadForm").attr("action","updateLicenseV2C");
	jQuery("#v2cUpLoadForm").submit();
    }

    function exportC2VFile() {
	window.location = "${ctx}/exportC2V";
    }
    function onV2CChangeFile() {
	jQuery("#v2c_fileName").text("");
    }
</script>
</head>
<div class="page">
	<div class="pageContent">

			<div class="pageFormContent nowrap">

				<fieldset>
					<legend>基本信息</legend>
					<dl>
						<dt>
							<s:text name="customerInfo.customerName" />
							:
						</dt>
						<dd>
							<s:property value="license.customer.customerName" />
						</dd>
					</dl>
					<dl>
						<dt>
							<s:text name="venderInfo.productVersion" />
							:
						</dt>
						<dd>
							<s:property value="license.vender.productVersion" />
						</dd>
					</dl>
					<dl>
						<dt>
							<s:text name="venderInfo.copyRightInfo" />
							:
						</dt>
						<dd>
							<s:property value="license.vender.copyRightInfo" />
						</dd>
					</dl>
					<dl>
						<dt>有效期:</dt>
						<dd>
							<%-- <input type="text" name="date1" class="date" readonly="true" value="${license.expirationDate}"/>
				<a class="inputDateButton" href="javascript:;">选择</a> --%>
							<s:property value="license.expirationDate" />
						</dd>
					</dl>
					<%-- <dl>
						 <dt>
							OEM
							:
						</dt>
						<dd>
							<s:property value="license.customer.oemFlag" />
							<s:checkbox label="OEM:" 
								name="license.customer.oemFlag" cssClass="" disabled="true" theme="simple"></s:checkbox>
						</dd>
					</dl> --%>


				</fieldset>
				<fieldset>
					<legend>产品授权模块</legend>

					<table class="table" width="100%" layoutH="350">
						<thead>
							<tr>
								<th width="25" orderField="productId">序号</th>
								<th width="120" orderField="productId"><s:text
										name="productInfo.productId" /></th>
								<th width="120" orderField="productName"><s:text
										name="productInfo.productName" /></th>
								<th width="120" orderField="allowedUsers">用户数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${license.products}" varStatus="status">
								<tr>
									<td>${status.index+1}</td>
									<td>${p.productId}</td>
									<td>${p.productName}</td>
									<td>${p.allowedUsers}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</fieldset>
			</div>
		
		<form id="v2cUpLoadForm" method="post" action="updateLicenseV2C"
			class="pageForm " enctype="multipart/form-data"
			onsubmit="return uploadCallback(this);">
			<div class="pageFormContent nowrap">

				<fieldset>
					<legend>License更新</legend>

					<dl>
						<dt style="width:150px">选择新的License文件(V2C):</dt>
						<dd style="width:250px">
							<s:file id="v2cFileId" name="v2cFile"
								onchange="onV2CChangeFile();" style="width: 200px;"></s:file>
							<span id="v2c_fileName" style="margin-left: 5px"></span>

						</dd>
						<dd>
							<div>
								<div class="buttonContent">
									<button type="button" onclick="javascript:uploadV2CFile();">
										<s:text name="button.upload" />
									</button>
								</div>
							</div>
						</dd>
					</dl>
					

				</fieldset>
				<fieldset>
					<legend>License导出</legend>
					<div class="" style="float: left;">
						<div class="buttonContent">
							<button type="button" onclick="javascript:exportC2VFile();">
								导出当前授权信息C2V</button>
						</div>
					</div>

				</fieldset>


			</div>
		</form>

	</div>
</div>