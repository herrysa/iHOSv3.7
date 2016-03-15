<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savePersonEntrylink').click(function() {
 			jQuery("#personEntryForm").attr("action","savePersonEntry?popup=true&navTabId=${navTabId}&oper=${oper}&entityIsNew=${entityIsNew}"+"&imagePath="+"${personEntry.imagePath}");
			jQuery("#personEntryForm").submit();
		});
		
		
		   //image upload
		jQuery('#testpersonEntryFileInput').uploadify({
			debug:true,
			swf:'${ctx}/dwz/uploadify/scripts/uploadify.swf',
			uploader:'${ctx}/uploadHrPersonImageFile',
			formData:{PHPSESSID:'xxx', ajax:1},
		    buttonText:'请上传照片',
		    fileObjName: 'imageFile', 
		    fileSizeLimit:'200KB',
		    fileTypeDesc:'*.jpg;*.jpeg;*.png;',
		    fileTypeExts:'*.jpg;*.jpeg;*.png;',
		    queueSizeLimit : 1,
		    height: 20,
            width: 80,
		    auto:true,
		    multi:false,
		    wmode: 'transparent' , 
		    rollover: false,
		    queueID:'fileQueuePersonEntry','onInit': function () { 
				jQuery('#fileQueuePersonEntry').hide();
			},
			onUploadSuccess: function(file, data, response) {
            	var retdata = eval('(' + data + ')');
                if(retdata.message==='sizeNotSuitable'){
                	alertMsg.error('图片大小应不超过两寸照片413*591。');
                }else{
                    jQuery('#personEntry_fileName').attr('value',retdata.message);
                    jQuery('#personEntryimageShowBase').attr('src','${ctx}/home/temporary/'+retdata.message); 
                    jQuery('#personEntryimageShowBase').show();
                }
			 }
		}); 	
		personEntryChangeImg(jQuery("#personEntry_sex").val());
		if("${oper}"=='view'){
			readOnlyForm("personEntryForm");
			jQuery('#personEntryFormDPImpButton').hide();
			jQuery("#testpersonEntryFileInput").hide();
			jQuery("#personEntry_imageDefaultSize").hide();
		} else{
			jQuery("#personEntry_name").addClass("required");
			jQuery("#personEntry_orgCode").addClass("required");
			jQuery("#personEntry_empType").addClass("required");
			jQuery("#personEntry_personCode").addClass("required");
			var departmentId = jQuery("#personEntry_hrDept_id").val();
			jQuery("#personEntry_duty").treeselect({
				dataType:"url",
				optType:"single",
				url:'getPostForRecruitNeed?deptId='+departmentId,
				exceptnullparent:false,
				lazy:false
			});
			 jQuery("#personEntry_empType").treeselect({
				   dataType:"sql",
				   optType:"single",
				   sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
				   exceptnullparent:false,
				   selectParent:false,
				   lazy:false
				});
			 if(jQuery('#personEntry_personFrom').val()){
				 jQuery("#personEntry_personCode").attr('readOnly',"true").removeAttr("onclick").removeAttr("onfocus");
			 }
		}
		adjustFormInput("personEntryForm");
	});
	function changePersonEntryInfo(id) {
		var content=jQuery("#"+id);
		if(content.css("display")=="none"){
			content.css("display","block");
		}else{
			content.css("display","none");
		}
	}
	
	function personEntryChangeImg(value){
		if(jQuery("#personEntry_fileName").val()){
			return;
		}
		if(value=="女"){
			jQuery("#personEntryimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/femaleDefalut.jpg"); 
		}else{
			jQuery("#personEntryimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg"); 
		}
	}
	
	//检查人员编码是否重复
	function checkHrPersonCode(obj){
		if(jQuery('#personEntry_personFrom').val()){
			return;
		}
		var value = obj.value;
		if(!value){
			return;
		}
		var orgCode = jQuery("#personEntry_orgCode_id").val();
		var personId = orgCode+'_'+obj.value;
		var url = 'checkId';
		url = encodeURI(url);
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{entityName:'HrPersonSnap',searchItem:'personId',searchValue:personId,returnMessage:'人员编码已存在！'},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
				     obj.value="";
		        }
		    }
		});
	}
	function savePersonEntryCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		if("${oper}"=="done"){
			var personNode = data.personNode;
			if(personNode){
				if(data.navTabId=='hrPersonCurrent_gridtable'){
					dealHrTreeNode("hrPersonCurrentLeftTree",personNode,"add","person");
				}else{
					dealHrTreeNode("hrDepartmentPersonEntryTreeLeft",personNode,"editPC","person");
				}
			}
			if(data.pactCreate){
				var pactUrl="${ctx}/personEntryGridEdit?oper=donePact";
				pactUrl = pactUrl+"&id="+data.id+"&navTabId=${navTabId}";
				alertMsg.confirm("确认生成合同？", {
					okCall : function() {
						$.post(pactUrl,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		}
	}
	function calPersonEntryAge(obj){
		var birthday = obj.value;
		if(birthday){
			var birthdate = new Date(birthday);
			//var age = birthdate.DateDiff('y',new Date());
			var age=getPersonEntryAge(birthdate);
			jQuery("#personEntry_age").val(age);
		}
	}
	//计算年龄(年月日都包含)
	function getPersonEntryAge(myDate){
		var age=0;
		var nowDate=new Date();
		var yearNow=nowDate.getFullYear();
		var monthNow=nowDate.getMonth()+1;
		var dayOfMonthNow=nowDate.getDate();
		
		var yearBirth = myDate.getFullYear();
		var monthBirth = myDate.getMonth()+1;
		var dayOfMonthBirth = myDate.getDate();
		
		if(yearNow<=yearBirth){
			return age;
		}
		age=yearNow-yearBirth;
		 if (monthNow <= monthBirth) { 
             if (monthNow == monthBirth) { 
                 //monthNow==monthBirth 
                 if (dayOfMonthNow < dayOfMonthBirth) { 
                     age--; 
                 } 
             } else { 
                 //monthNow>monthBirth 
                 age--; 
             } 
         } 
         return age; 
	}
	function personEntryDPImp(){
		var url = "personEntryDPList";
		var winTitle='<s:text name="personEntryDPList.title"/>';
		$.pdialog.open(url,'personEntryDPList',winTitle, {mask:true,width : 700,height : 600});
		stopPropagation();
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="personEntryForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savePersonEntryCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="personEntry.id"/>
					<s:hidden key="personEntry.maker.personId"/>
					<s:hidden key="personEntry.makeDate"/>
					<s:hidden key="personEntry.checker.personId"/>
					<s:hidden key="personEntry.checkDate"/>
					<s:hidden key="personEntry.state"/>
					<s:hidden key="personEntry.doneDate"/>
					<s:hidden key="personEntry.doner.personId"/>
					<s:hidden key="personEntry.deptSnapCode" />
					<s:hidden key="personEntry.code"/>
					<s:hidden key="personEntry.yearMonth"/>
					<s:hidden key="personEntry.personFrom" id="personEntry_personFrom"/>
					<s:hidden key="personEntry.personId" id="personEntry_personId"/>
				</div>
				<!-- 基本信息 -->
				<div class="unit">
					<a href="javascript:changePersonEntryInfo('contentPersonEntryBase');" style="font-size: 12px">基本信息</a>
				</div>
				<div id="contentPersonEntryBase">
				
				<div style="margin-left:0px;position:relative;">
		         			<div>
								<input type="hidden" id="personEntry_fileName" name="personEntry.imagePath" value="${personEntry.imagePath}"/>	
		      					<div id="fileQueuePersonEntry" class="fileQueue" style="display:none"></div>         
		       				</div>
     			    		<div>
     				  			<table>
      					 			<tbody>
      									<tr>
      										<td style="width:400px;"><s:textfield id="personEntry_name" key="personEntry.name"  maxlength="20"/></td>
      										<td colspan="2" rowspan="6">
      										  <span id="personEntry_imageDefaultSize">图片不超过413*591</span><br>
			       							  <s:if test="%{personEntry.imagePath!=null&&personEntry.imagePath!=''}">
			         							<img style="border:1px solid gray" id="personEntryimageShowBase" src="${ctx}/home/personEntry/${personEntry.imagePath}" height="148" width="106" />
			       							 </s:if>
			       							 <s:else>
			        							 <img style="border:1px solid gray" id="personEntryimageShowBase" src="${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg" height="148" width="106" />
			       							 </s:else><br>
			       							 <input id="testpersonEntryFileInput" type="file" />
     						     			</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<s:if test="%{entityIsNew}">
     						     		<s:textfield id="personEntry_personCode" key="personEntry.personCode" maxlength="20"
      									notrepeat='人员编码已存在' validatorType="sql"  validatorParam="SELECT v.personId AS checkId FROM v_hr_person_current v WHERE v.personCode = %value% AND v.orgCode = '${orgCode}' UNION SELECT hpe.id AS checkId FROM hr_person_entry hpe WHERE hpe.personCode = %value% AND hpe.deptId IN (SELECT v.deptId FROM v_hr_department_current v WHERE v.orgCode = '${orgCode}')"/>
     						     		</s:if>
     						     		<s:else>
     						     		<s:textfield id="personEntry_personCode" key="personEntry.personCode" maxlength="20" oldValue="'${personEntry.personCode}'"
      									notrepeat='人员编码已存在' validatorType="sql"  validatorParam="SELECT v.personId AS checkId FROM v_hr_person_current v WHERE v.personCode = %value% AND v.orgCode = '${orgCode}' UNION SELECT hpe.id AS checkId FROM hr_person_entry hpe WHERE hpe.personCode = %value% AND hpe.deptId IN (SELECT v.deptId FROM v_hr_department_current v WHERE v.orgCode = '${orgCode}')"/>
     						     		</s:else>
      									</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<label><s:text name='personEntry.orgCode' />:</label>
						<input type="text" id="personEntry_orgCode" value="${orgName}"  readonly="readonly"/>
						<input type="hidden" id="personEntry_orgCode_id" name="orgCode" value="${orgCode}"/>	
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
					<label><s:text name='personEntry.hrDept' />:</label>
						<input type="text" id="personEntry_hrDept" name="personEntry.hrDept.name" value="${personEntry.hrDept.name}" readonly="readonly"/>
						<input type="hidden" id="personEntry_hrDept_id" name="personEntry.hrDept.departmentId" value="${personEntry.hrDept.departmentId}"/>	
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     	<label><s:text name='personEntry.postType' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
								    	<s:select id="personEntry_postType" key="personEntry.postType" cssClass="input-small" maxlength="20"
				        						 list="postTypeList" listKey="value" listValue="content"
				        						 emptyOption="false" theme="simple">
				        				</s:select>
				        			</span>
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<label><s:text name='personEntry.empType' />:</label> 
						<input type="text" id="personEntry_empType" name="personEntry.empType.name" value="${personEntry.empType.name}" />
						<input type="hidden" id="personEntry_empType_id" name="personEntry.empType.id" value="${personEntry.empType.id}"/>
     						     		</td>
     						     		</tr>
      					 			</tbody>      
       							</table>
                  			</div>
						</div>
						<div class="unit">
							<label><s:text name='personEntry.duty' />:</label> 
						<input type="text" id="personEntry_duty" name="personEntry.duty.name" value="${personEntry.duty.name}" class=""/>
						<input type="hidden" id="personEntry_duty_id" name="personEntry.duty.id" value="${personEntry.duty.id}"/>
						<s:textfield id="personEntry_idNumber" key="personEntry.idNumber" required="false" maxlength="20" cssClass="input-small idcard" />
						</div>
					<div class="unit">
						<label><s:text name='personEntry.jobTitle' />:</label> 
							<span class="formspan" style="float: left; width: 140px"> 
								<s:select id="personEntry_jobTitle" key="personEntry.jobTitle" maxlength="20" 
										list="jobTitleList" listKey="value" listValue="content"
										emptyOption="true" theme="simple">
								</s:select>
						</span>
						<label><s:text name='personEntry.sex' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select onchange="personEntryChangeImg(this.value)"
							id="personEntry_sex" key="personEntry.sex" required="false" maxlength="20" list="sexList"
							listKey="value"  listValue="content" 
							emptyOption="false" theme="simple"></s:select>
						</span>
					</div>
					<div class="unit">
					<label ><s:text name='personEntry.birthday' />:</label>
						<input id="personEntry_birthday" name="personEntry.birthday" type="text"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
							value="<fmt:formatDate value="${personEntry.birthday}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,onpicked:calPersonEntryAge(this)})" /> 
					<s:textfield id="personEntry_age" key="personEntry.age" name="personEntry.age" cssClass="digits" readonly="true"/>
					</div>
					<div class="unit">
						<s:textfield id="personEntry_email" key="personEntry.email" required="false" maxlength="20" cssClass="input-small email" />
					    <s:textfield id="personEntry_mobilePhone" key="personEntry.mobilePhone" required="false" maxlength="20" cssClass="input-small phone" />
				  	</div>
				 	<div class="unit">
						<label><s:text name='personEntry.personPolCode' />:</label>
					 	<span class="formspan" style="float: left; width: 140px">
					 		<s:select id="personEntry_personPolCode" key="personEntry.personPolCode" 
									cssClass="input-small" maxlength="20" 
									list="personPolList" listKey="value" listValue="content"
									emptyOption="true" theme="simple"></s:select>
						</span>
						<label><s:text name='personEntry.people' />:</label>
					 	<span class="formspan" style="float: left; width: 140px">
					 		<s:select id="personEntry_people"  key="personEntry.people" 
									cssClass="input-small" maxlength="20" 
									list="peopleList" listKey="value" listValue="content"
									emptyOption="true" theme="simple"></s:select>
						</span>
					</div>
					<div class="unit">
						<s:textfield id="personEntry_nativePlace" key="personEntry.nativePlace" name="personEntry.nativePlace" cssClass="" maxlength="30"/>
						<s:textfield id="personEntry_homeTelephone" key="personEntry.homeTelephone" name="personEntry.homeTelephone" cssClass="telephone" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield id="personEntry_domicilePlace" key="personEntry.domicilePlace" name="personEntry.domicilePlace" style="width:401px"  cssClass="" maxlength="100"/>
						</div>
					<div class="unit">
						<s:textfield id="personEntry_houseAddress" key="personEntry.houseAddress" name="personEntry.houseAddress"  style="width:401px"  cssClass="" maxlength="100"/>
						</div>
					<div class="unit">
						<label><s:text name='personEntry.educationalLevel' />:</label> 
						<span class="formspan" style="float: left; width: 140px"> <s:select
							key="personEntry.educationalLevel" id="personEntry_educationalLevel"
							cssClass="input-small" maxlength="20" 
							list="educationalLevelList" listKey="value" listValue="content"
							emptyOption="true" theme="simple"></s:select>
						</span>
						<label><s:text name='personEntry.degree' />:</label>
						<span class="formspan" style="float: left; width: 140px">
					  		<s:select  key="personEntry.degree" id="personEntry_degree"
							cssClass="input-small" maxlength="20" 
							list="degreeList" listKey="value" listValue="content"
							emptyOption="true" theme="simple"></s:select>
						</span>	
					</div>
					<div class="unit">
						<s:textfield id="personEntry_school" key="personEntry.school" required="false" maxlength="100" cssClass="input-small " />
						<label><s:text name='personEntry.professional' />:</label>
						<span class="formspan" style="float: left; width: 140px">
					  		<s:select key="personEntry.professional" id="personEntry_professional"
								cssClass="input-small" maxlength="20" 
								list="professionalList" listKey="value" listValue="content"
								emptyOption="true" theme="simple"></s:select>	
					 	</span>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.maritalStatus' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.maritalStatus" id="personEntry_maritalStatus"
       							 maxlength="20" list="maritalStatusList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
    			    	<label style="float:left;white-space:nowrap"><s:text name='personEntry.graduateDay' />:</label>
						<input name="personEntry.graduateDay" type="text" id="personEntry_graduateDay"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
							value="<fmt:formatDate value="${personEntry.graduateDay}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<label><s:text name='personEntry.entryDate' />:</label>
						<input name="personEntry.entryDate" type="text"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
							value="<fmt:formatDate value="${personEntry.entryDate}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
				</div>
				<!-- 工作经历-->
				<div class="unit">
					<a href="javascript:changePersonEntryInfo('contentPersonEntryWork');" style="font-size: 12px">工作经历</a>
				</div>
				<div id="contentPersonEntryWork" style="display:none">
					<div class="unit">
						<a style="font-size: 12px">一:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.workStartDateFirst' />:</label>
						<input name="personEntry.workStartDateFirst" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workStartDateFirst}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.workEndDateFirst' />:</label>
						<input name="personEntry.workEndDateFirst" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workEndDateFirst}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.workUnitFirst" name="personEntry.workUnitFirst" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.workPostFirst" name="personEntry.workPostFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.payLevelFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.payLevelFirst" 
       							 maxlength="20" list="salaryLevelResumeList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
    			    	<s:textfield key="personEntry.leavingReasonFirst" name="personEntry.leavingReasonFirst" cssClass="" maxlength="50"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.certifierFirst" name="personEntry.certifierFirst" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.certifierPhoneFirst" name="personEntry.certifierPhoneFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<a style="font-size: 12px">二:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.workStartDateSecond' />:</label>
						<input name="personEntry.workStartDateSecond" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workStartDateSecond}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.workEndDateSecond' />:</label>
						<input name="personEntry.workEndDateSecond" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workEndDateSecond}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.workUnitSecond" name="personEntry.workUnitSecond" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.workPostSecond" name="personEntry.workPostSecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.payLevelSecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.payLevelSecond" 
       							 maxlength="20" list="salaryLevelResumeList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			   		</span>
    			    	<s:textfield key="personEntry.leavingReasonSecond" name="personEntry.leavingReasonSecond" cssClass="" maxlength="50"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.certifierSecond" name="personEntry.certifierSecond" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.certifierPhoneSecond" name="personEntry.certifierPhoneSecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<a style="font-size: 12px">三:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.workStartDateThird' />:</label>
						<input name="personEntry.workStartDateThird" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workStartDateThird}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.workEndDateThird' />:</label>
						<input name="personEntry.workEndDateThird" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.workEndDateThird}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.workUnitThird" name="personEntry.workUnitThird" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.workPostThird" name="personEntry.workPostThird" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.payLevelThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.payLevelThird" 
       							 maxlength="20" list="salaryLevelResumeList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
    			    	<s:textfield key="personEntry.leavingReasonThird" name="personEntry.leavingReasonThird" cssClass="" maxlength="50"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.certifierThird" name="personEntry.certifierThird" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.certifierPhoneThird" name="personEntry.certifierPhoneThird" cssClass="" maxlength="20"/>
					</div>
				</div>
				<!-- 教育经历 -->
				<div class="unit">
					<a href="javascript:changePersonEntryInfo('contentPersonEntryEdu');" style="font-size: 12px">教育经历</a>
				</div>
				<div id="contentPersonEntryEdu" style="display:none">
					<div class="unit">
						<a style="font-size: 12px">一:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduStartDateFirst' />:</label>
						<input name="personEntry.eduStartDateFirst" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.eduStartDateFirst}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.eduEndDateFirst' />:</label>
						<input name="personEntry.eduEndDateFirst" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.eduEndDateFirst}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.schoolFirst" name="personEntry.schoolFirst" cssClass="" maxlength="30"/>
						<label><s:text name='personEntry.professionFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.professionFirst" 
       							 maxlength="20" list="professionalList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduLevelFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.eduLevelFirst" 
       							 maxlength="20" list="educationalLevelList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<a style="font-size: 12px">二:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduStartDateSecond' />:</label>
						<input name="personEntry.eduStartDateSecond" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.eduStartDateSecond}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.eduEndDateSecond' />:</label>
						<input name="personEntry.eduEndDateSecond" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.eduEndDateSecond}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.schoolSecond" name="personEntry.schoolSecond" cssClass="" maxlength="30"/>
						<label><s:text name='personEntry.professionSecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.professionSecond" 
       							 maxlength="20" list="professionalList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduLevelSecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.eduLevelSecond" 
       							 maxlength="20" list="educationalLevelList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<a style="font-size: 12px">三:</a>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduStartDateThird' />:</label>
						<input name="personEntry.eduStartDateThird" type="text"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="height:15px"
								value="<fmt:formatDate value="${personEntry.eduStartDateThird}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
						<label><s:text name='personEntry.eduEndDateThird' />:</label>
						<input name="personEntry.eduEndDateThird" type="text" class="Wdate" style="height:15px"
								onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value="${personEntry.eduEndDateThird}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="personEntry.schoolThird" name="personEntry.schoolThird" cssClass="" maxlength="30"/>
						<label><s:text name='personEntry.professionThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.professionThird" 
       							 maxlength="20" list="professionalList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='personEntry.eduLevelThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select
       							 key="personEntry.eduLevelThird"
       							 maxlength="20" list="educationalLevelList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
				</div>
				<div class="unit">
					<a href="javascript:changePersonEntryInfo('contentPersonEntryFamily');" style="font-size: 12px">家庭成员</a>
				</div>
				<div id="contentPersonEntryFamily" style="display:none">
					<div class="unit">
						<a style="font-size: 12px">一:</a>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyTiesFirst" name="personEntry.familyTiesFirst" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.familyNameFirst" name="personEntry.familyNameFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyWorkUnitFirst" name="personEntry.familyWorkUnitFirst" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.familyPhoneFirst" name="personEntry.familyPhoneFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<a style="font-size: 12px">二:</a>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyTiesSecond" name="personEntry.familyTiesSecond" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.familyNameSecond" name="personEntry.familyNameSecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyWorkUnitSecond" name="personEntry.familyWorkUnitSecond" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.familyPhoneSecond" name="personEntry.familyPhoneSecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<a style="font-size: 12px">三:</a>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyTiesThird" name="personEntry.familyTiesThird" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.familyNameThird" name="personEntry.familyNameThird" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.familyWorkUnitThird" name="personEntry.familyWorkUnitThird" cssClass="" maxlength="30"/>
						<s:textfield key="personEntry.familyPhoneThird" name="personEntry.familyPhoneThird" cssClass="" maxlength="20"/>
					</div>
				</div>
				<div class="unit">
					<a href="javascript:changePersonEntryInfo('contentPersonEntryOther');" style="font-size: 12px">其他信息</a>
				</div>
				<div id="contentPersonEntryOther" style="display:none">
					<div class="unit">
						<s:textfield key="personEntry.emergencyContact" name="personEntry.emergencyContact" cssClass="" maxlength="20"/>
						<s:textfield key="personEntry.emergencyTies" name="personEntry.emergencyTies" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="personEntry.emergencyPhone" name="personEntry.emergencyPhone" cssClass="" maxlength="20"/>
					</div>
 					<div class="unit">
						<s:textarea key="personEntry.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
					</div>
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${personNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="personEntry.maker"></s:text>:</label>
								<input type="text" value="${personEntry.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="personEntry.checker"></s:text>:</label>
								<input type="text" value="${personEntry.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="personEntry.doner"></s:text>:</label>
								<input type="text" value="${personEntry.doner.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="personEntryFormDPImpButton">
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="personEntryDPImp()"><s:text name="停用人员引入" /></button>
							</div>
						</div>
					</li>
					<li id="personEntryFormSaveButton">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savePersonEntrylink"><s:text name="button.save" /></button>
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





