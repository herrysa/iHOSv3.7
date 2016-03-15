<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#sysTableContentSavelink').click(function() {
			jQuery("#sysTableContentForm").attr("action","saveSysTableContent?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#sysTableContentForm").submit();
		});
		if("${sysTableContent.bdinfo.sysField}"=="true"){
			jQuery(".sysTableContentFormInput").attr('readOnly',"true").attr("onfocus","").attr("onclick","").attr("onblur","");
			jQuery('#sysTableContent_sumFlag').click(function() {
				return false;
			});
// 			jQuery('#sysTableContent_disabled').click(function() {
// 				return false;
// 			});
			if('${sysTableContent.isView}' == 'true'){
	 			jQuery('#sysTableContent_statistics').click(function() {
				return false;
			});
			}

			jQuery('#sysTableContent_isView').click(function() {
				return false;
			});
		}
		if('${entityIsNew}'!="true"){
			jQuery('#sysTableContent_isView').click(function() {
				return false;
			});
		}
	});
	function sysTableContentFormSubmit(data){
		formCallBack(data);
		sysTableContentLeftTree();
		//selectNodeClick("${sysTableContent.id}");
	}
	function selectNodeClick(id){
		var urlString = "sysTableContentGridList";
		urlString = urlString+"?filter_EQS_id="+id;	
		urlString = encodeURI(urlString);
		jQuery("#sysTableContent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
	function checkIdForsysTableContent(obj){
		var isView = jQuery("#sysTableContent_isView").attr("checked");
		if(isView){
			checkId(obj,'BdInfo','tableName','此表名已存在!');
			return;
		}
 		var value = obj.value;
 		var tableCode = "${sysTableContent.tableKind.code}"+"_";
		if(value.indexOf(tableCode)==0){
		}else{
			alertMsg.error("表名称应符合 "+tableCode +"表名 这样的规则");
			obj.value = "${sysTableContent.tableKind.code}"+"_";
		}
		checkId(obj,'BdInfo','tableName','此表名已存在!');
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sysTableContentForm" method="post"	action="saveSysTableContent?popup=true" class="pageForm required-validate"	onsubmit="return validateCallback(this,sysTableContentFormSubmit);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="sysTableContent.id"></s:hidden>
				<s:hidden key="sysTableContent.bdinfo.bdInfoId"/>
				<s:hidden key="sysTableContent.changeDate"/>
				<s:hidden key="sysTableContent.changeUser.personId"/>
				<s:hidden key="sysTableContent.sumFlag"/>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield key="sysTableContent.bdinfo.tableName" id="sysTableContent_code"  maxlength="30" cssClass="required" readonly="true"/>
				<s:textfield id="sysTableContent_name" maxlength="50" key="sysTableContent.name" name="sysTableContent.name" cssClass="required sysTableContentFormInput" oldValue="'${sysTableContent.name}'" notrepeat='中文名称已存在' validatorParam="from SysTableContent content where content.name=%value%"/>
				</s:if>
				<s:else>				
				<s:textfield key="sysTableContent.bdinfo.tableName" id="sysTableContent_code"  maxlength="30" cssClass="required sysTableContentFormInput" onblur="checkIdForsysTableContent(this)"/>
				<s:textfield id="sysTableContent_name" maxlength="50" key="sysTableContent.name" name="sysTableContent.name" cssClass="required sysTableContentFormInput" notrepeat='中文名称已存在' validatorParam="from SysTableContent content where content.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="sysTableContent_sn" key="sysTableContent.sn" name="sysTableContent.sn" cssClass="digits"/>
				<label><s:text name='sysTableContent.tablekind' />:</label>
				<input type="text" id="${random}_sysTableContent_tableKindReadOnly" name="sysTableContent.tableKind.name" value="${sysTableContent.tableKind.name}" readonly="readonly"/>
				<input type="hidden" id="${random}_sysTableContent_tableKindReadOnly_id" name="sysTableContent.tableKind.id" value="${sysTableContent.tableKind.id}"/>
				</div>
				<div class="unit">
				<s:textfield id="sysTableContent_orderByField" maxlength="50" key="sysTableContent.bdinfo.orderByField" name="sysTableContent.bdinfo.orderByField"/>
				<label><s:text name='sysTableContent.orderByFieldAsc' />:</label>
				 <span	style="float: left; width: 138px"> 
				<s:checkbox id="sysTableContent_orderByFieldAsc" key="sysTableContent.bdinfo.orderByFieldAsc" required="false" theme="simple"/>
				</span>
				</div>
				<div class="unit">
				<label><s:text name='sysTableContent.statistics' />:</label>
				 <span	style="float: left; width: 138px"> 
				<s:checkbox id="sysTableContent_statistics" key="sysTableContent.bdinfo.statistics" required="false" theme="simple"/>
				</span>
				<label><s:text name="sysTableContent.disabled"/>:</label>
				 <span	style="float: left; width: 140px"> 
				<s:checkbox id="sysTableContent_disabled" key="sysTableContent.disabled" required="false" theme="simple"/>
				</span>
				</div>
				<div class="unit">
				<label><s:text name="sysTableContent.isView"/>:</label>
				 <span	style="float: left; width: 140px"> 
				<s:checkbox id="sysTableContent_isView" key="sysTableContent.isView" required="false" theme="simple"/>
				</span>
				</div>
				<div class="unit">				
				      <s:textarea key="sysTableContent.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="sysTableContentSavelink"><s:text name="button.save" /></button>
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





