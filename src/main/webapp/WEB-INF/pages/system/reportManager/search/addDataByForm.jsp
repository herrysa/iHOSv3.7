<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveQueryData').click(function() {
			//jQuery("#addQueryDataForm").attr("action","saveQueryData?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			//jQuery("#addQueryDataForm").submit();
			var formData = jQuery("#addQueryDataForm").serializeObject();
			formData = json2str(formData);
			$.ajax({
			    url: 'saveQueryData',
			    type: 'post',
			    data:{queryFormData:formData,searchName:"${searchName}",entityIsNew:"${entityIsNew}",id:"${id}",navTabId:"${navTabId}"},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    },
			    success: function(data){
			    	formCallBack(data);
			    }
			});
		});
	});
</script>

<div class="page">
	<div class="pageContent">
		<s:hidden key="searchName"></s:hidden>
		<s:form id="addQueryDataForm" method="post" action="saveReport?popup=true"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:iterator value="searchOptions" status="it">
				<s:if test="#it.index%colNum==0||alone==true">
				<div class="unit">
				</s:if>
					<s:if  test="userTag!='checkboxGroupH'&&userTag!='radioGroupH'&&userTag!='checkboxGroupV'&&userTag!='radioGroupV'">
						<label><s:property value="formTitle"/>:</label>
					</s:if>	
					<span id="${random}_${searchName}_${it.index}" style="width:140px;float: left;">
					<input type="hidden" id="${random}_${searchName}_${it.index}_${item.htmlField}_param1" value="${param1}"/>
					<script>
					var param1 = jQuery("#${random}_${searchName}_${it.index}_${item.htmlField}_param1").val();
					makeUserTag('${random}_${searchName}_${it.index}','${userTag}','${tableField}','${tableFieldValue}','${title}','true','${readOnly}','${required}','${rule}','${length}','addQueryDataForm','${random}','${searchName}',param1,'${param2}','form','${tableFieldNameValue}');
					</script>
					</span>
					<label style="float:none;line-height:15px">${suffix}</label>
				<s:if test="#it.index!=0&&(#it.index+1)%colNum==0||alone==true">
				</div>
				</s:if>
				<s:if test="#it.last==true">
					</div>
				</s:if>
				</s:iterator>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveQueryData">保存</button>
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
