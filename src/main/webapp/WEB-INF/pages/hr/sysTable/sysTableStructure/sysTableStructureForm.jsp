<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#${random}_sysTableStructure_tableContent").treeselect({
			   dataType:"sql",
			   optType:"single",
			   sql:"SELECT contentId id,name FROM sys_hr_table_content ORDER BY sn",
			   exceptnullparent:false,
			   lazy:false
			  });
		jQuery('#sysTableStructureSavelink').click(function() {
			if(!jQuery("#sysTableStructure_dataType").val()){
				alertMsg.error("请选择数据类型。");
				return;
			}
			jQuery("#sysTableStructureForm").attr("action","saveSysTableStructure?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#sysTableStructureForm").submit();
		});	
		if('${entityIsNew}'=="true"){
		}else{
			sysTableStrDataTypeSelected("${sysTableStructure.fieldInfo.dataType}");
			if("${sysTableStructure.fieldInfo.sysField}"=="true"){
				jQuery(".sysTableStructureFormInput").attr('readOnly',"true").attr("onfocus","").attr("onclick","");
				jQuery('#sysTableStructure_read').click(function() {
					return false;
				});
				jQuery('#sysTableStructure_notNull').click(function() {
					return false;
				});
				if("${sysTableStructure.tableContent.isView}" == "true"||"${sysTableStructure.isView}" == "true"){
	 				jQuery('#sysTableStructure_batchEdit').click(function() {
						return false;
					});
	 				jQuery('#sysTableStructure_statistics').click(function() {
						return false;
					});
				}
			}
		}
		sysTableStrUserTagSelected('${sysTableStructure.fieldInfo.userTag}');
	});
	function sysTableStrDataTypeSelected(value){
		 switch(value)
		 {
		 case '1'://字符型
			 jQuery("#fieldLength").show();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").show();
			 jQuery("#fieldDefault").show();			 
		   break;
		 case '2'://浮点型
			 jQuery("#fieldLength").show();
			 jQuery("#fieldDecimal").show();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").show();
		   break;
		 case '3'://布尔型
			 jQuery("#fieldLength").hide();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").show();
		   break;
		 case '4'://日期型
			 jQuery("#fieldLength").hide();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").hide();
		   break;
		 case '5'://整数型
			 jQuery("#fieldLength").hide();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").show();
		   break;
		 case '6'://货币型
			 jQuery("#fieldLength").hide();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").show();
		   break;
		 case '7'://图片型
			 jQuery("#fieldLength").hide();
			 jQuery("#fieldDecimal").hide();
			 jQuery("#fieldCode").hide();
			 jQuery("#fieldDefault").hide();
		   break;
		 default:
			 jQuery("#fieldLength").hide();
		 	 jQuery("#fieldDecimal").hide();
		 	 jQuery("#fieldCode").hide();
		 	 jQuery("#fieldDefault").hide();
		 }
		}
	function sysTableStrUserTagSelected(value){
		 var oldValue = '${sysTableStructure.fieldInfo.userTag}';
		 if(oldValue != value){
			 jQuery("#sysTableStructure_parameter").val("");
			 jQuery("#sysTableStructure_parameter_id").val("");
		 }
		 switch(value)
		 {
		 case 'input'://文本框
			 jQuery("#sysTableStructure_parameter").hide();
		 	 jQuery("#sysTableStructure_parameter_name").hide();
		   break;
		 case 'select'://下拉框
			 jQuery("#sysTableStructure_parameter_name").show();
			 jQuery("#sysTableStructure_parameter").show().treeselect({
					   dataType:"sql",
					   optType:"single",
					   sql:"SELECT code id,name FROM t_dictionary  ORDER BY dictionaryId",
					   exceptnullparent:false,
					   lazy:false
					  });
		   break;
		 case 'treeSelect'://TreeSelect
			 jQuery("#sysTableStructure_parameter_name").show();
			 jQuery("#sysTableStructure_parameter").show().unbind("click").unbind("focus");
		   break;
		 default:
			 jQuery("#sysTableStructure_parameter").hide();
		 	 jQuery("#sysTableStructure_parameter_name").hide();
		 }
	}
	function checkSysTableStructureCode(obj){
		var value = obj.value;
		var filedCode = "${sysTableStructure.fieldInfo.fieldCode}";
		var bdinfoId = "${sysTableStructure.tableContent.bdinfo.bdInfoId}";
		if(filedCode == value){
			return;
		}
		 jQuery.ajax({
	            url: 'fieldInfoGridList?filter_EQS_bdInfo.bdInfoId='+bdinfoId+'&filter_EQS_fieldCode='+value,
	            data: {},
	            type: 'post',
	            dataType: 'json',
	            async:false,
	            error: function(data){
	            },
	            success: function(data){
	             if(data.fieldInfoes&&data.fieldInfoes.length>0){
	              alertMsg.error("此字段名已存在！");
	              obj.value="";
	             }
	            }
	        });
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sysTableStructureForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<label><s:text name='sysTableStructure.sysTableContent' />:</label>
				<input type="text" id="${random}_sysTableStructure_tableContentReadOnly" name="sysTableStructure.tableContent.name" value="${sysTableStructure.tableContent.name}" readonly="readonly" class="required"/>
				<input type="hidden" id="${random}_sysTableStructure_tableContentReadOnly_id" name="sysTableStructure.tableContent.id" value="${sysTableStructure.tableContent.id}"/>
				</s:if>
				<s:else>				
				<label><s:text name='sysTableStructure.sysTableContent' />:</label>
				<input type="text" id="${random}_sysTableStructure_tableContent"  name="sysTableStructure.tableContent.name" value="${sysTableStructure.tableContent.name}" class="required"/>
				<input type="hidden" id="${random}_sysTableStructure_tableContent_id" name="sysTableStructure.tableContent.id" value="${sysTableStructure.tableContent.id}"/>
				</s:else>
				</div>
				<div class="unit">
				<s:hidden key="sysTableStructure.id"></s:hidden>
				<s:hidden key="sysTableStructure.sumFlag"></s:hidden>
				<s:hidden key="sysTableStructure.changeUser.personId"></s:hidden>
				<s:hidden key="sysTableStructure.changeDate"></s:hidden>
				<s:hidden key="sysTableStructure.orderBySn"></s:hidden>
				<s:hidden key="sysTableStructure.ascDesc"></s:hidden>
				<s:hidden key="sysTableStructure.groupSn"></s:hidden>
				<s:hidden key="sysTableStructure.fieldInfo.fieldId"/>
				<s:hidden key="sysTableStructure.isView"/>
				<s:if test="%{!entityIsNew}">
				<s:textfield key="sysTableStructure.fieldInfo.fieldCode"   readonly="true" cssClass="required"/>
				<s:textfield id="sysTableStructure_name" key="sysTableStructure.name" name="sysTableStructure.name" cssClass="required sysTableStructureFormInput" oldValue="'${sysTableStructure.name}'" notrepeat='中文名称已存在' validatorParam="from SysTableStructure structure where structure.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield key="sysTableStructure.fieldInfo.fieldCode"  maxlength="50" cssClass="required" onblur="checkSysTableStructureCode(this)"/>
				<s:textfield id="sysTableStructure_name" key="sysTableStructure.name" name="sysTableStructure.name" cssClass="required sysTableStructureFormInput" notrepeat='中文名称已存在' validatorParam="from SysTableStructure structure where structure.name=%value%"/>
				</s:else>							
				</div>
				<div class="unit">
				<s:textfield id="sysTableStructure_sn" key="sysTableStructure.sn" name="sysTableStructure.sn" cssClass="digits sysTableStructureFormInput"/>	
				<s:if test="%{entityIsNew}">	
				<s:select id="sysTableStructure_dataType" key="sysTableStructure.fieldInfo.dataType" onchange="sysTableStrDataTypeSelected(this.value)" name="sysTableStructure.fieldInfo.dataType" list="#{'':'--','1':'字符型','2':'浮点型','3':'布尔型','4':'日期型','5':'整数型','6':'货币型','7':'图片型'}" style="width:140px" ></s:select>
				</s:if>
				<s:else>
				<label><s:text name='sysTableStructure.dataType'/>:</label>
				<input type="hidden" id="sysTableStructure_dataType" name="sysTableStructure.fieldInfo.dataType" value="${sysTableStructure.fieldInfo.dataType}" />
									<c:choose>  
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='1'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="字符型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='2'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="浮点型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='3'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="布尔型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='4'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="日期型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='5'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="整数型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='6'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="货币型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										<c:when test="${sysTableStructure.fieldInfo.dataType=='7'}">
       										<input type="text" id="${random}_sysTableStructure_dataType" value="图片型" name="sysTableStructuredataType" readonly="readonly"/>
       										</c:when>
       										 <c:otherwise>  
       										 <input type="text" id="${random}_sysTableStructure_dataType" value="--" name="sysTableStructuredataType" readonly="readonly"/>
       										 </c:otherwise>
       									</c:choose>   
				</s:else>
				</div>				
				<div class="unit" id="fieldLength" style="display:none">
				<s:if test="%{entityIsNew}">
					 <s:textfield id="sysTableStructure_dataLength"   key="sysTableStructure.fieldInfo.dataLength" name="sysTableStructure.fieldInfo.dataLength" cssClass="digits"/>
				</s:if>
				<s:else>
					 <s:textfield id="sysTableStructure_dataLength"   key="sysTableStructure.fieldInfo.dataLength" name="sysTableStructure.fieldInfo.dataLength" cssClass="digits" readonly="true"/>
				</s:else>
				</div>
				<div class="unit" id="fieldDecimal" style="display:none">
				<s:if test="%{entityIsNew}">
				<s:textfield id="sysTableStructure_dataDecimal"  key="sysTableStructure.fieldInfo.dataDecimal" name="sysTableStructure.fieldInfo.dataDecimal" cssClass="digits"/>
				</s:if>
				<s:else>
				<s:textfield id="sysTableStructure_dataDecimal"  key="sysTableStructure.fieldInfo.dataDecimal" name="sysTableStructure.fieldInfo.dataDecimal" cssClass="digits" readonly="true"/>
				</s:else>
				
				</div>
				<div class="unit" id="fieldDefault" style="display:none">
				<s:if test="%{entityIsNew}">
				<s:textfield id="sysTableStructure_fieldDefault"  maxlength="50" key="sysTableStructure.fieldInfo.fieldDefault" name="sysTableStructure.fieldInfo.fieldDefault" />								
				</s:if>
				<s:else>
				<s:textfield id="sysTableStructure_fieldDefault"  maxlength="50" key="sysTableStructure.fieldInfo.fieldDefault" name="sysTableStructure.fieldInfo.fieldDefault" readonly="true"/>								
				</s:else>
				</div>
				<div class="unit" style="display:none" id="fieldCode">
				<s:select id="sysTableStructure_userTag" key="sysTableStructure.userTag" onchange="sysTableStrUserTagSelected(this.value)" name="sysTableStructure.fieldInfo.userTag" list="#{'input':'文本框','select':'下拉框','treeSelect':'treeSelect'}" style="width:140px" ></s:select>
				<label id="sysTableStructure_parameter_name"><s:text name='sysTableStructure.parameter'/>:</label>
				<input type="text" id="sysTableStructure_parameter" name="sysTableStructure.fieldInfo.parameter1" value="${sysTableStructure.fieldInfo.parameter1}"/>
				<input type="hidden" id="sysTableStructure_parameter_id" name="sysTableStructure.fieldInfo.parameter2" value="${sysTableStructure.fieldInfo.parameter2}"/>
				</div>							
				<div class="unit">
				<label><s:text name='sysTableStructure.batchEdit' />:</label> <span
							style="float: left; width: 140px"> 
				<s:checkbox id="sysTableStructure_batchEdit" theme="simple" key="sysTableStructure.batchEdit" name="sysTableStructure.batchEdit" />
				</span>
				<label><s:text name='sysTableStructure.disabled' />:</label> <span
							style="float: left; width: 140px"> 
				<s:checkbox id="sysTableStructure_disabled" theme="simple" key="sysTableStructure.disabled" name="sysTableStructure.disabled" />
				</span>
				</div>	
				<div class="unit">
				 <s:textfield id="sysTableStructure_gridShowLength"   key="sysTableStructure.gridShowLength" name="sysTableStructure.gridShowLength" cssClass="digits"/>
				<label><s:text name='sysTableStructure.statistics' />:</label> <span
							style="float: left; width: 140px"> 
				<s:checkbox id="sysTableStructure_statistics" theme="simple" key="sysTableStructure.fieldInfo.statistics" name="sysTableStructure.fieldInfo.statistics" />
				</span>
				</div>
				<div class="unit">				
				      <s:textarea key="sysTableStructure.remark" required="false" maxlength="200"
							rows="4" cols="50" cssClass="input-xlarge" />
				</div>		
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="sysTableStructureSavelink"><s:text name="button.save" /></button>
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





