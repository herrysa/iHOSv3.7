<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchList.title" />
</title>
<meta name="heading" content="<fmt:message key='searchList.heading'/>" />
<meta name="menu" content="SearchMenu" />
<script>
	jQuery(document).ready(function() {

		jQuery('#importExcel').click(function() {
			var file = jQuery("#excelfile").val();
			if (file != null && file != "") {
			jQuery("#importForm").attr("action","importExcel");
			//alert(action);
			jQuery("#importForm").submit();
			} else {
				alertMsg.warn('请选择文件！');
			}
		});
		jQuery('#exportExcelData').click(function() {
			var sql = jQuery("#exportSql").val();
			//alertMsg.warn('请输入sql语句！');
			if (sql != null && sql != "") {
				jQuery("#exportForm").attr("action","exportExcel");
				//alert(action);
				jQuery("#exportForm").submit();
				//exportDataCallback();
			} else {
				alertMsg.warn('请输入sql语句！');
			}
			
		});
		jQuery('#exportSqlData').click(function() {
			var sql = jQuery("#exportSql").val();
			if (sql != null && sql != "") {
				jQuery("#exportForm").attr("action","exportSql");
				//alert(action);
				jQuery("#exportForm").submit();
			} else {
				alertMsg.warn('请输入sql语句！');
			}
		});
	});
	function exportDataCallback(){
		alert(<%=request.getSession().getAttribute("importResult")%>);
		<%request.getSession().removeAttribute("importResult");%>;
	}
	function uploadExcelCallback(data){
		var jsonData = data.substring(data.indexOf("{"),data.indexOf("}")+1);
		if(jQuery.browser.msie){
			jsonData = eval("(" +jsonData+ ")");
	    }else{
	    	jsonData = JSON.parse(jsonData);
	    }
		formCallBack(jsonData);
	}
</script>
<c:if test="${importResult!=null && importResult!=''}">
	<script type="text/javascript">
	alert('${importResult}');
	jQuery('#search_gridtable').trigger("reloadGrid");
		/* if("${importResult}"=="1"){
			alertMsg.correct("导入成功！");
		}else{
			alertMsg.error("${importResult}");
		}	 */
	</script>
</c:if>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="importForm" method="post" action="importExcel?popup=true" target="messageFrame" enctype="multipart/form-data" onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent">
				<div class="unit">

					<br />
					<s:file key="importData.message" id="excelfile" name="excelfile"></s:file>
					</div>
					<div class="unit">
					<div class="button" style="margin-left:130px"><div class="buttonContent">
					<button type="button" id="importExcel"><fmt:message key="importData.button" /></button>
					</div></div>
					</div>
			</div>
		</s:form>	
		<s:form id="exportForm" method="post" action="importExcel?popup=true">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<br> <br> <br>
					<hr />
					<br> <br> <br>
					<s:textarea key="exportData.message" style="width:600px;max-width:90%" rows="15"
						id="exportSql" name="exportSql"></s:textarea>
				</div>
				<div class="unit">
				<dl class="nowrap">
					<dd style="width:250px"><div style="margin-left:130px" class="button"><div  class="buttonContent">
					<button  type="button" id="exportExcelData"><fmt:message key="exportExcelData.button" /></button>
					</div></div>
					</dd>
					<dd style="width:100px">
					<div class="button" ><div class="buttonContent">
					<button type="button" id="exportSqlData"><fmt:message key="exportSqlData.button" /></button>
					</div>
					</div>
					</dd>
					</dl>
					
					<%-- <button  type="button" id="exportExcelData"><fmt:message key="exportExcelData.button" /></button>
					<button type="button" id="exportSqlData"><fmt:message key="exportSqlData.button" /></button> --%>
				</div>
			</div>
		</s:form>
	</div>
</div>
<iframe name="messageFrame" style="display:none"></iframe>