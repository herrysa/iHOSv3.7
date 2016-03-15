<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=='viewResume'){
			readOnlyForm("recruitResumeForm");
			jQuery("#testrecruitResumeFileInput").css("display","none");
			jQuery("#recruitResume_imageDefaultSize").css("display","none");
		}else{
			jQuery("#recruitResume_name").addClass("required");
			jQuery("#recruitResume_recruitPlan").addClass("required");
			jQuery("#recruitResume_recruitPlan").treeselect({
			   dataType:"sql",
			   optType:"single",
			   sql:"SELECT id,post name   FROM hr_recruit_plan  where  state='3' ORDER BY released_date DESC",
			   exceptnullparent:false,
			   lazy:false
			});
			//image upload
			jQuery('#testrecruitResumeFileInput').uploadify({
				debug:true,
				swf:'${ctx}/dwz/uploadify/scripts/uploadify.swf',
				uploader:'${ctx}/uploadHrPersonImageFile',
				formData:{PHPSESSID:'xxx', ajax:1},
			    buttonText:'请选择文件',
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
			    queueID:'fileQueueRecruitResume','onInit': function () { 
					jQuery('#fileQueueRecruitResume').hide();
				},
				onUploadSuccess: function(file, data, response) {
	            	var retdata = eval('(' + data + ')');
	                if(retdata.message==='sizeNotSuitable'){
	                	alertMsg.error('图片大小应不超过两寸照片413*591。');
	                }else{
	                    jQuery('#recruitResume_fileName').attr('value',retdata.message);
	                    jQuery('#recruitResumeimageShowBase').attr('src','${ctx}/home/temporary/'+retdata.message); 
	                    jQuery('#recruitResumeimageShowBase').show();
	                }
				 }
			}); 	
		}
		jQuery('#saveRecruitResumelink').click(function() {
			jQuery("#recruitResumeForm").attr("action","saveRecruitResume?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"+"&imagePath="+"${recruitResume.photo}");
			jQuery("#recruitResumeForm").submit();
		});
		recruitResumeChangeImg(jQuery("#recruitResume_sex").val());
		adjustFormInput("recruitResumeForm");
	});
	
	function changeRecruitResumeInfo(contentId){
		var content=jQuery("#"+contentId);
		if(content.css("display")=="none"){
			content.show();
		}else{
			content.hide();
		}
	}
	function recruitResumeChangeImg(value){
		if(jQuery("#recruitResume_fileName").val()){
			return;
		}
		if(value=="女"){
			jQuery("#recruitResumeimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/femaleDefalut.jpg"); 
		}else{
			jQuery("#recruitResumeimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg"); 
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="recruitResumeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="recruitResume.id"/>
					<s:hidden key="recruitResume.favoriteState"/>
					<s:hidden key="recruitResume.favoriteName.personId"/>
					<s:hidden key="recruitResume.favoriteDate"/>
					<s:hidden key="recruitResume.talentPoolName.personId"/>
					<s:hidden key="recruitResume.talentPoolDate"/>
					<s:hidden key="recruitResume.state"/>
					<s:hidden key="recruitResume.interviewState"/>
					<s:hidden key="recruitResume.interviewSpace"/>
					<s:hidden key="recruitResume.professionalExaminer"/>
					<s:hidden key="recruitResume.foreignLanguageExaminer"/>
					<s:hidden key="recruitResume.examinerDate"/>
					<s:hidden key="recruitResume.interviewContacts"/>
					<s:hidden key="recruitResume.interviewContactWay"/>
					<s:hidden key="recruitResume.examineGrade"/>
					<s:hidden key="recruitResume.examineEvaluate"/>
					<s:hidden key="recruitResume.reportDate"/>
					<s:hidden key="recruitResume.reportContacts"/>
					<s:hidden key="recruitResume.reportContactWay"/>
					<s:hidden key="recruitResume.code"/>
					<s:hidden key="recruitResume.deptSnapCode" />
					<s:hidden key="recruitResume.hrDept.departmentId" />
					<s:hidden key="recruitResume.post.id" />
					<s:hidden key="recruitResume.age"/>
					<s:hidden key="recruitResume.viewState" id="recruitResume_viewState"/>
					<s:hidden key="recruitResume.yearMonth"/>
				</div>
				<div class="unit">
					<a href="javascript:changeRecruitResumeInfo('contentRecruitResumeBase');" style="font-size: 12px">基本信息</a>
				</div>
				<!-- ------------------------基本信息---------------------------------- -->
				<div id="contentRecruitResumeBase">
				
					<div style="margin-left:0px;position:relative;">
		         			<div>
								<input type="hidden" id="recruitResume_fileName" name="recruitResume.photo" value="${recruitResume.photo}"/>	
		      					<div id="fileQueueRecruitResume" class="fileQueue" style="display:none"></div>         
		       				</div>
     			    		<div>
     				  			<table>
      					 			<tbody>
      									<tr>
      										<td style="width:440px;"><s:textfield id="recruitResume_name" key="recruitResume.name" name="recruitResume.name" maxlength="10"/></td>
      										<td colspan="2" rowspan="6">
      										  <span id="recruitResume_imageDefaultSize">图片不超过413*591</span><br>
			       							  <s:if test="%{recruitResume.photo!=null&&recruitResume.photo!=''}">
			         							<img style="border:1px solid gray" id="recruitResumeimageShowBase" src="${ctx}/home/recruitResume/${recruitResume.photo}" height="148" width="106" />
			       							 </s:if>
			       							 <s:else>
			        							 <img style="border:1px solid gray" id="recruitResumeimageShowBase" src="${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg" height="148" width="106" />
			       							 </s:else><br>
			       							 <input id="testrecruitResumeFileInput" type="file" />
     						     			</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
      										<label><s:text name='recruitResume.birthday' />:</label>
						<input name="recruitResume.birthday" type="text" class="Wdate" style="height:15px"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${recruitResume.birthday}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
      										</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<label><s:text name='recruitResume.workStartDate' />:</label>
						<input name="recruitResume.workStartDate" type="text" class="Wdate" style="height:15px"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${recruitResume.workStartDate}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
						<label><s:text name='recruitResume.sex' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.sex" style="font-size:9pt;" id="recruitResume_sex"
    							 maxlength="20" list="sexList" listKey="value" onchange="recruitResumeChangeImg(this.value)"
    							 listValue="content" emptyOption="false" theme="simple"></s:select>
   						</span>
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<s:textfield key="recruitResume.domicilePlace" name="recruitResume.domicilePlace" cssClass="" maxlength="30"/>
     						     		</td>
     						     		</tr>
     						     		<tr>
     						     		<td>
     						     		<s:textfield key="recruitResume.presentResidence" name="recruitResume.presentResidence" cssClass="" maxlength="50"/>
     						     		</td>
     						     		</tr>
      					 			</tbody>      
       							</table>
                  			</div>
						</div>
					<div class="unit">
						<s:textfield key="recruitResume.contactWay" name="recruitResume.contactWay" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.email" name="recruitResume.email" cssClass="email" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.maritalStatus' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.maritalStatus" style="font-size:9pt;"
      							 maxlength="20" list="maritalStatusList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
						<label><s:text name='recruitResume.overseaAssignment' />:</label>
						<span class="formspan" style="float:left;width:140px">
							<s:checkbox key="recruitResume.overseaAssignment" theme="simple"></s:checkbox>
						</span>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.typeOfId' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.typeOfId" style="font-size:9pt;"
      							 maxlength="20" list="typeOfIdList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
						<s:textfield key="recruitResume.idNumber" name="recruitResume.idNumber" cssClass="idcard" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.nation' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.nation" style="font-size:9pt;"
      							 maxlength="20" list="nationList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
	    			    <label><s:text name='recruitResume.politicsStatus' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.politicsStatus" style="font-size:9pt;"
      							 maxlength="20" list="politicsStatusList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.foreignLanguage' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.foreignLanguage" style="font-size:9pt;"
      							 maxlength="20" list="foreignLanguageList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
						<s:textfield key="recruitResume.foreignLanguageLevel" name="recruitResume.foreignLanguageLevel" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.interests" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.selfAssessment" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
					</div>
				</div>
				<!-- ------------------------------------求职意向 ---------------------------------------->
				<div class="unit">
					<a href="javascript:changeRecruitResumeInfo('contentRecruitResumeExpect');" style="font-size: 12px">求职意向</a>
				</div>
				<div id="contentRecruitResumeExpect">
					<div class="unit">
						<label><s:text name='recruitResume.recruitPlan' />:</label> 
						<input type="text" id="recruitResume_recruitPlan" name="recruitResume.recruitPlan.post" value="${recruitResume.recruitPlan.post}" />
						<input type="hidden" id="recruitResume_recruitPlan_id" name="recruitResume.recruitPlan.id" value="${recruitResume.recruitPlan.id}"/>
						<label style="float:left;white-space:nowrap"><s:text name='recruitResume.recruitDate' />:</label>
						<input name="recruitResume.recruitDate" type="text" class="Wdate required" style="height:15px"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" 
							value="<fmt:formatDate value="${recruitResume.recruitDate}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.expectJobCategory' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.expectJobCategory" style="font-size:9pt;"
      							 maxlength="20" list="expectJobCategoryList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
						<s:textfield key="recruitResume.expectWorkplace" name="recruitResume.expectWorkplace" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.expectOccupation" name="recruitResume.expectOccupation" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.expectIndustry" name="recruitResume.expectIndustry" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.expectMonthlyPay' />:</label>
						<span class="formspan" style="float: left; width: 138px"> 
							<s:select key="recruitResume.expectMonthlyPay" style="font-size:9pt;"
      							 maxlength="20" list="salaryLevelList" listKey="value"
      							 listValue="content" emptyOption="false" theme="simple"></s:select>
	    			    </span>
						<s:textfield key="recruitResume.workingCondition" name="recruitResume.workingCondition" cssClass="" maxlength="30"/>
					</div>
				</div>
				<!-- ------------------------------工作经验------------------ -->
				<div class="unit">
					<a href="javascript:changeRecruitResumeInfo('contentRecruitResumeWork');" style="font-size: 12px">工作经验</a>
				</div>
				<div id="contentRecruitResumeWork" style="display:none">
					<div class="unit">
						<a style="font-size: 12px">一:</a>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.companyNameFirst" name="recruitResume.companyNameFirst" cssClass="" maxlength="30"/>
						<s:textfield key="recruitResume.industryCategoryFirst" name="recruitResume.industryCategoryFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.positionCategoryFirst" name="recruitResume.positionCategoryFirst" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.positionNameFirst" name="recruitResume.positionNameFirst" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workStartDateFirst' />:</label>
						<input name="recruitResume.workStartDateFirst" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workStartDateFirst}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.workEndDateFirst' />:</label>
						<input name="recruitResume.workEndDateFirst" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workEndDateFirst}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workMonthlyPayFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select key="recruitResume.workMonthlyPayFirst" style="font-size:9pt;"
       							 maxlength="20" list="salaryLevelResumeList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.workSpecificationFirst" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
					</div>
					<div class="unit">
						<a style="font-size: 12px">二:</a>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.companyNameSecond" name="recruitResume.companyNameSecond" cssClass="" maxlength="30"/>
						<s:textfield key="recruitResume.industryCategorySecond" name="recruitResume.industryCategorySecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.positionCategorySecond" name="recruitResume.positionCategorySecond" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.positionNameSecond" name="recruitResume.positionNameSecond" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workStartDateSecond' />:</label>
						<input name="recruitResume.workStartDateSecond" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workStartDateSecond}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.workEndDateSecond' />:</label>
						<input name="recruitResume.workEndDateSecond" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workEndDateSecond}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workMonthlyPaySecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.workMonthlyPaySecond" style="font-size:9pt;"
   							 maxlength="20" list="salaryLevelResumeList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			   	 	</span>
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.workSpecificationSecond" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
					</div>
					<div class="unit">
						<a style="font-size: 12px">三:</a>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.companyNameThird" name="recruitResume.companyNameThird" cssClass="" maxlength="30"/>
						<s:textfield key="recruitResume.industryCategoryThird" name="recruitResume.industryCategoryThird" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.positionCategoryThird" name="recruitResume.positionCategoryThird" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.positionNameThird" name="recruitResume.positionNameThird" cssClass="" maxlength="20"/>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workStartDateThird' />:</label>
						<input name="recruitResume.workStartDateThird" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workStartDateThird}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.workEndDateThird' />:</label>
						<input name="recruitResume.workEndDateThird" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.workEndDateThird}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.workMonthlyPayThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select  key="recruitResume.workMonthlyPayThird" style="font-size:9pt;"
   							 maxlength="20" list="salaryLevelResumeList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.workSpecificationThird" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
					</div>
				</div>
				<!-- ------------------------------教育背景------------------ -->
				<div class="unit">
					<a href="javascript:changeRecruitResumeInfo('contentRecruitResumeEdu');" style="font-size: 12px">教育背景</a>
				</div>
				<div id="contentRecruitResumeEdu" style="display:none">
					<div class="unit">
						<a style="font-size: 12px">一:</a>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduStartDateFirst' />:</label>
						<input name="recruitResume.eduStartDateFirst" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduStartDateFirst}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.eduEndDateFirst' />:</label>
						<input name="recruitResume.eduEndDateFirst" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduEndDateFirst}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.schoolFirst" name="recruitResume.schoolFirst" cssClass="" maxlength="30"/>
						<label><s:text name='recruitResume.professionFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.professionFirst" style="font-size:9pt;"
       							 maxlength="20" list="professionList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduLevelFirst' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.eduLevelFirst" style="font-size:9pt;"
       							 maxlength="20" list="eduLevelList" listKey="value"
       							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<a style="font-size: 12px">二:</a>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduStartDateSecond' />:</label>
						<input name="recruitResume.eduStartDateSecond" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduStartDateSecond}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.eduEndDateSecond' />:</label>
						<input name="recruitResume.eduEndDateSecond" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduEndDateSecond}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.schoolSecond" name="recruitResume.schoolSecond" cssClass="" maxlength="30"/>
						<label><s:text name='recruitResume.professionSecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.professionSecond" style="font-size:9pt;"
   							 maxlength="20" list="professionList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduLevelSecond' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.eduLevelSecond" style="font-size:9pt;"
   							 maxlength="20" list="eduLevelList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<a style="font-size: 12px">三:</a>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduStartDateThird' />:</label>
						<input name="recruitResume.eduStartDateThird" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduStartDateThird}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
						<label><s:text name='recruitResume.eduEndDateThird' />:</label>
						<input name="recruitResume.eduEndDateThird" type="text"
							onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=""
							value="<fmt:formatDate value="${recruitResume.eduEndDateThird}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />	
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.schoolThird" name="recruitResume.schoolThird" cssClass="" maxlength="30"/>
						<label><s:text name='recruitResume.professionThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.professionThird" style="font-size:9pt;"
   							 maxlength="20" list="professionList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
					<div class="unit">
						<label><s:text name='recruitResume.eduLevelThird' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="recruitResume.eduLevelThird" style="font-size:9pt;"
   							 maxlength="20" list="eduLevelList" listKey="value"
   							 listValue="content" emptyOption="true" theme="simple"></s:select>
    			    	</span>
					</div>
				</div>
				<!-- ------------------------------其他信息------------------ -->
				<div class="unit">
					<a href="javascript:changeRecruitResumeInfo('contentRecruitResumeOther');" style="font-size: 12px">其他信息</a>
				</div>
				<div id="contentRecruitResumeOther" style="display:none">
					<div class="unit">				
				      	<s:textarea key="recruitResume.projectExperience" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.professionalSkill" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
					</div>
					<div class="unit">				
				      	<s:textarea key="recruitResume.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
					</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="recruitResumeFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveRecruitResumelink"><s:text name="button.save" /></button>
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





