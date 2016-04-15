<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/scripts/date.js"></script>
<script>
	var hrPersonTabpanel;
	function showHrPersonSnapSubSet(obj,subTableCode,subTableName){
		if(obj.style.color == 'black'){
			return;
		}
		var url = "showSubSet?subTableCode="+subTableCode+"&subEntityId=${hrPersonSnap.personId}&tablecontainer=hrPersonSnapForm_pageFormContent&oper=${oper}";
		url+="&snapCode="+"${hrPersonSnap.snapCode}";
		var exist = false;
		var hrPersonTabs = hrPersonTabpanel.getTabs();
		for(var t=0;t<hrPersonTabs.length;t++){
			var subTableCodeTemp = hrPersonTabs[t].id;
			if(subTableCode==subTableCodeTemp){
				exist = true;
				break;
			}
		}
		hrPersonTabpanel.addTab({id:subTableCode,
		    title:subTableName ,
		    html:'<div id="'+subTableCode+'_div" name="subSetDiv"></div>',
		    closable: true,
		    disabled:false
		});
		jQuery("#hrPersonSnapForm_Menues_List").find("a").css("color","blue");
		obj.style.color = 'black';
		if(!exist){
			jQuery("#"+subTableCode+"_div").loadUrl(url);
		}
	}
	
	jQuery(document).ready(function() {
		jQuery("#hrPersonSnapForm").find("select").css("font-size","12px");
		// init tabPanel and baseInfo
		var baseInfo = jQuery("#hrPersonSnapForm_Tabs_List").html();
		jQuery("#hrPersonSnapForm_Tabs_List").html("");
		hrPersonTabpanel = new TabPanel({  
	        renderTo:'hrPersonSnapForm_Tabs_List',  
	        width:637,  
	        height:518,  
	        autoResizable:false,
	        heightResizable:false,
	        widthResizable:false,
	        active : 0,
	        items : [
	            {id:'baseInfo',title:'基本信息',html:baseInfo,closable: false} 
	        ],
	        afterShow:function(id){
	        	jQuery("#hrPersonSnapForm_Menues_List").find("a").css("color","blue");
	        	//jQuery("#"+id+"_menu").css("color","black");
	        }
	    });
		jQuery("#hrPersonSnapForm_Menues_List").find("a:first").css("color","black");
		// set layout
		jQuery("#hrPersonSnapForm_container").css("height",520);
		$('#hrPersonSnapForm_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 146,
			spacing_open:0,//边框的间隙  
			spacing_closed:0,//关闭时边框的间隙 
			resizable :false,
			resizerClass :"ui-layout-resizer-blue",
			slidable :false,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小"//鼠标移到边框时，提示语
			
		});
		jQuery('#saveHrPersonlink').click(function() {
			//附加信息保存
			var gridAllDatas=[];
			var gridAllDatasIndex=0;
			var hrPersonTabs = hrPersonTabpanel.getTabs();
			for(var i=0;i<hrPersonTabs.length;i++){
				 var tableCode = hrPersonTabs[i].id;
				 var editFlag = jQuery("#"+tableCode+"_gridtable_editFlag").val();
				 var gridRowNum=0;
          		 if(jQuery("#"+tableCode+"_gridtable").attr("id")){
          			jQuery("#"+tableCode+"_gridtable>tbody>tr").each(function (){
          				var rowId=this.id;
          				if(editFlag=="1"){
          					jQuery("#"+tableCode+"_gridtable").jqGrid('saveRow',rowId,  {  
    						    "keys" : true,  
    						    "oneditfunc" : null,  
    						    "successfunc" : null,  
    						    "url" : "clientArray",  
    						     "extraparam" : {},  
    						    "aftersavefunc" : null,  
    						    "errorfunc": null,  
    						    "afterrestorefunc" : null,  
    						    "restoreAfterError" : true,  
    						    "mtype" : "POST"  
    					});  
          				}
          				if(gridRowNum==0){
          				gridAllDatas[gridAllDatasIndex]={"hrSubSetName":tableCode};
          				}else{
          				gridAllDatas[gridAllDatasIndex] = jQuery("#"+tableCode+"_gridtable").getRowData(rowId); 
          				}
          				gridAllDatasIndex++;
          				gridRowNum++;
                      }); 
          		 }
			}   
			var jsonArray={"edit":gridAllDatas};
			jQuery("#hrPersonSnapForm_gridAllDatas").val(JSON.stringify(jsonArray));
			jQuery("#hrPersonSnapForm").attr("action","saveHrPersonSnap?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}"+"&imagePath="+"${hrPersonSnap.imagePath}");
			jQuery("#hrPersonSnapForm").submit();
		});

		//init left subSetMenuList
		var mainTable = "v_hr_person_current";
		jQuery.ajax({
           	url: 'getTableContentList',
           	data: {mainTable:mainTable},
           	type: 'post',
           	dataType: 'json',
           	async:false,
           	error: function(data){
           	},
           	success: function(data){
        		var menuListHtml = jQuery("#hrPersonSnapForm_Menues_List")[0].innerHTML;
          	 	for(var num = 0;num< data.sysTableContents.length;num++){
          		 	var tableCode = data.sysTableContents[num].bdinfo.tableName;
          			var subTableCode = "'"+tableCode+"'";
          			var subTableName = ""+data.sysTableContents[num].name+"";
          			menuListHtml +='<div class="unit"><a id="'+tableCode+'_menu" style="font-size: 12px;color:blue;text-decoration:underline;cursor:pointer;" onclick="showHrPersonSnapSubSet(this,'+subTableCode+',\''+subTableName+'\');" >';
          			menuListHtml += subTableName + '</a></div>';
          	 	} 
          	 	jQuery("#hrPersonSnapForm_Menues_List")[0].innerHTML = menuListHtml;
           	}
       	});
		   
		   //image upload
		jQuery('#testhrPersonSnapFileInput').uploadify({
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
		    queueID:'fileQueuehrPersonSnap','onInit': function () { 
				jQuery('#fileQueuehrPersonSnap').hide();
			},
			onUploadSuccess: function(file, data, response) {
            	var retdata = eval('(' + data + ')');
                if(retdata.message==='sizeNotSuitable'){
                	alertMsg.error('图片大小应不超过两寸照片413*591。');
                }else{
                    jQuery('#hrPersonSnap_fileName').attr('value',retdata.message);
//                     jQuery('#hrPersonSnapimageShow').attr('src','${ctx}/home/hrPerson/'+retdata.message); 
//                     jQuery('#hrPersonSnapimageShow').show();
                    jQuery('#hrPersonSnapimageShowBase').attr('src','${ctx}/home/temporary/'+retdata.message); 
                    jQuery('#hrPersonSnapimageShowBase').show();
                }
			 }
		}); 	
		hrPersonChangeImg(jQuery("#hrPersonSnap_sex").val());
		//readonly
	   	if("${oper}"=='view'){
		   readOnlyForm("hrPersonSnapForm");
		   jQuery("#testhrPersonSnapFileInput").hide();
		   jQuery("#hrPersonSnap_imageDefaultSize").hide();
	   	}else{
	   	   jQuery("#hrPersonSnap_name").addClass("required");
	   	   jQuery("#hrPersonSnap_empType").addClass("required");
		   jQuery("#hrPersonSnap_empType").treeselect({
			   dataType:"sql",
			   optType:"single",
			   sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			   exceptnullparent:false,
			   selectParent:false,
			   lazy:false
			});
		   	var departmentId =jQuery("#hrPersonSnap_hrDept_id").val();
			jQuery("#hrPersonSnap_duty").treeselect({
				dataType:"url",
				optType:"single",
				url:'getPostForRecruitNeed?deptId='+departmentId,
				exceptnullparent:false,
				lazy:false
			});
			jQuery("#hrPersonSnap_bank1").treeselect({
				optType : "single",
				dataType : 'sql',
				sql : "SELECT bankName id,bankName name FROM sy_bank WHERE disabled = '0'",
				exceptnullparent : true,
				lazy : false,
				minWidth : '180px',
				selectParent : false
			});
			jQuery("#hrPersonSnap_bank2").treeselect({
				optType : "single",
				dataType : 'sql',
				sql : "SELECT bankName id,bankName name FROM sy_bank WHERE disabled = '0'",
				exceptnullparent : true,
				lazy : false,
				minWidth : '180px',
				selectParent : false
			});
	   	}
	   	adjustFormInput("hrPersonSnapForm");
	});
	
	//检查人员编码是否重复
	function checkHrPersonCode(obj){
		var value = obj.value;
		if(!value){
			return;
		}
		var orgCode = jQuery("#hrPersonSnap_orgCode_id").val();
		var personId = orgCode+'_'+obj.value;
		var url = 'checkId';
		url = encodeURI(url);
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{entityName:'HrPersonCurrent',searchItem:'personId',searchValue:personId,returnMessage:'人员编码已存在！'},
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
	// 保存人员后的回调函数,主要功能是添加人员后更新左侧树的人员数量
	function saveHrPersonSnapCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var personNode = data.personNode;
		if("${entityIsNew}"=="true"){
			dealHrTreeNode("hrPersonCurrentLeftTree",personNode,"add","person");
		}else{
			dealHrTreeNode("hrPersonCurrentLeftTree",personNode,"change","person");
		}
		/* var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
		var showPersonCount = jQuery("#hrPersonCurrent_showPersonCount").attr("checked");
// 		var showDisabledDept = jQuery("#hrPersonCurrent_showDisabled").attr("checked");
		if('${entityIsNew}'=="true"){//  add 获取父级节点，绑定至父级节点上
			   var deptId = data.deptId;
			   var updateNode = hrDeptTreeObj.getNodeByParam("id", deptId, null);
			   updateHrDeptPersonCountAddOne("hrPersonCurrentLeftTree",updateNode,showPersonCount);
		} */
	}
	//人员数加一
	function updateHrDeptPersonCountAddOne(treeObjId,updateNode,showPersonCount){
		var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
		var personCount = parseInt(updateNode.personCount)+1;
		updateNode.personCount=personCount;
		if(showPersonCount){
			updateNode.name = updateNode.nameWithoutPerson+"("+personCount+")";
		}
		treeObj.updateNode(updateNode);
		var parentNode = updateNode.getParentNode();
		if(parentNode&&parentNode.subSysTem!="ALL"){
			updateHrDeptPersonCountAddOne(treeObjId,parentNode,showPersonCount);
		}
	}
	
	function changeHrPersonSnapInfo(id) {
		var content=jQuery("#"+id);
		if(content.css("display")=="none"){
			content.css("display","block");
		}else{
			content.css("display","none");
		}
	}
	function calPersonAge(obj){
		var name = obj.name;
		var birthday = obj.value;
		/* if(name=='hrPersonSnap.birthday'){
			var idNumber = jQuery("input[name='hrPersonSnap.idNumber']").val();
			var idBirthday = idNumber.substring(6,10)+"-"+idNumber.substring(10,12)+"-"+idNumber.substring(12,14);
			if(birthday!=idBirthday){
				alertMsg.error(idBirthday);
			}
		} */
		if(birthday){
			var birthdate = new Date(birthday);
			//var age = birthdate.DateDiff('y',new Date());
			var age=getHrPersonAge(birthdate);
			jQuery("#hrPersonSnap_age").val(age);
		}
	}
	function hrPersonChangeImg(value){
		if(jQuery("#hrPersonSnap_fileName").val()){
			return;
		}
		if(value=="女"){
			jQuery("#hrPersonSnapimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/femaleDefalut.jpg"); 
		}else{
			jQuery("#hrPersonSnapimageShowBase").attr('src',"${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg"); 
		}
	}
	//计算年龄(年月日都包含)
	function getHrPersonAge(myDate){
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
	
	function closeHrPersonSnapFormDialog(){
		if("${oper}"!='view'){
			$.pdialog.closeCurrent();
		}else{
			$.pdialog.close('viewHrPersonSnap_${hrPersonSnap.snapId}');
		}
	}
</script>
<div id="hrPersonSnapPage" class="page">
	<div id="hrPersonSnapForm_pageContent" class="pageContent">
		<form id="hrPersonSnapForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveHrPersonSnapCallback);">
			<div id="hrPersonSnapForm_pageFormContent" class="pageFormContent" layoutH="39" style="padding: 0px;">
				<div id="hrPersonSnapForm_container">
					<!-- 左边 头像和子集菜单列表 -->
					<div id="hrPersonSnapnForm_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto; background:#eeeeee;width:146px;height:100%;">
						<div style="margin-top:12px;margin-left:10px;position:relative;">
		         			<div>
								<input type="hidden" id="hrPersonSnap_fileName" name="hrPersonSnap.imagePath" value="${hrPersonSnap.imagePath}"/>	
		      					<div id="fileQueuehrPersonSnap" class="fileQueue"></div>         
		       				</div>
     			    		<div >
     				  			<table>
      					 			<tbody>
      					 				<tr id="hrPersonSnap_imageDefaultSize">
      					 					<td>
      					 					图片不超过413*591
      					 					</td>
      					 				</tr>
      									<tr>
      										<td colspan="2">
			       							 <s:if test="%{hrPersonSnap.imagePath!=null&&hrPersonSnap.imagePath!=''}">
			         							<img id="hrPersonSnapimageShowBase" src="${ctx}/home/hrPerson/${hrPersonSnap.orgCode}/${hrPersonSnap.imagePath}" height="148" width="106" />
			       							 </s:if>
			       							 <s:else>
			        							 <img id="hrPersonSnapimageShowBase" src="${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg" height="148" width="106" />
			       							 </s:else>
     						     			</td>
     						     		</tr>
     						     		<tr>
     						     			<td><input id="testhrPersonSnapFileInput" type="file" /></td><!-- <td> <input id="imageHRPDel" type="button" onclick="javascript:deleteHrPersonSnapImg()" value="删除" /></td> -->
     						     		</tr>
      					 			</tbody>      
       							</table>
                  			</div>
						</div>
				 		<div id="hrPersonSnapForm_Menues_List" style="margin-top:20px;margin-left:10px;position:relative;">
					 		<div class="unit">
								<a id="baseInfo_menu" style="font-size: 12px;color:blue;text-decoration:underline;cursor:pointer;" onclick="showHrPersonSnapSubSet(this,'baseInfo');" >基本信息</a>
					 		</div>	
				 		</div>
					</div>	
					<!-- 右侧 基本信息 和附属子集信息 -->
					<div id="hrPersonSnapForm_layout-center" class="pane ui-layout-center">	
						<div id='hrPersonSnapForm_Tabs_List'>
							<div>
							<div>
								<s:hidden name="gridAllDatas" id="hrPersonSnapForm_gridAllDatas"/>
								<s:hidden key="hrPersonSnap.snapId"/>
								<s:hidden key="hrPersonSnap.snapCode"/>
								<s:hidden key="hrPersonSnap.personId"/>
								<s:hidden key="hrPersonSnap.deptSnapCode"/>
								<s:hidden key="hrPersonSnap.disabled"/>
							</div>
							<!-- 基本信息 -->
							<div class="unit">
								<a href="javascript:changeHrPersonSnapInfo('contentHrPersonSnapBase');" style="font-size: 12px">基本信息</a>
							</div>
							<div id="contentHrPersonSnapBase">
								<div class="unit">
									<label><s:text name='hrPersonSnap.orgCode' />:</label>
									<input type="text" id="hrPersonSnap_orgCode" value="${hrPersonSnap.hrOrg.orgname}" readonly="readonly"/>
									<input type="hidden" id="hrPersonSnap_orgCode_id" name="hrPersonSnap.orgCode" value="${hrPersonSnap.orgCode}"/>						
									<s:hidden key="hrPersonSnap.orgSnapCode"/>
									</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.hrDept' />:</label>
									<input type="text" id="hrPersonSnap_hrDept" name="hrPersonSnap.hrDept.name" value="${hrPersonSnap.hrDept.name}" readonly="readonly"/>
									<input type="hidden" id="hrPersonSnap_hrDept_id" name="hrPersonSnap.hrDept.departmentId" value="${hrPersonSnap.hrDept.departmentId}"/>	
									<label><fmt:message key="hisOrg.branchName" />:</label>
									<s:textfield theme="simple" readonly="true" value="%{hrPersonSnap.hrDept.branch.branchName}" id="hrPersonSnap_branchName" />
									<s:hidden id="hrPersonSnap_branchCode" value="%{hrPersonSnap.hrDept.branch.branchCode}" name="hrPersonSnap.branchCode" />
								</div>
								<div class="unit">
									<s:if test="%{entityIsNew}">
										<s:textfield id="hrPersonSnap_personCode" key="hrPersonSnap.personCode" name="hrPersonSnap.personCode" cssClass="required" maxlength="20"
										notrepeat='人员编码已存在' validatorParam="from HrPersonCurrent person where person.personCode=%value% and person.orgCode='${hrPersonSnap.orgCode}'"/>
									</s:if>
									<s:else>
										<s:textfield id="hrPersonSnap_personCode" key="hrPersonSnap.personCode" name="hrPersonSnap.personCode" readonly="true"/>
									</s:else>
									<s:textfield id="hrPersonSnap_name" key="hrPersonSnap.name" name="hrPersonSnap.name"  maxlength="10"/>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.empType' />:</label> 
									<input type="text" id="hrPersonSnap_empType" name="hrPersonSnap.empType.name" value="${hrPersonSnap.empType.name}"/>
									<input type="hidden" id="hrPersonSnap_empType_id" name="hrPersonSnap.empType.id" value="${hrPersonSnap.empType.id}"/>
									<label><s:text name='hrPersonSnap.postType' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
								    	<s:select key="hrPersonSnap.postType" cssClass="input-small" maxlength="20"
				        						 list="dutyList" listKey="value" listValue="content"
				        						 emptyOption="false" theme="simple">
				        				</s:select>
				        			</span>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.duty' />:</label> 
								    <input type="text" id="hrPersonSnap_duty" name="hrPersonSnap.duty.name" value="${hrPersonSnap.duty.name}" class=""/>
									<input type="hidden" id="hrPersonSnap_duty_id" name="hrPersonSnap.duty.id" value="${hrPersonSnap.duty.id}"/>
									<label><s:text name='hrPersonSnap.jobTitle' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.jobTitle" maxlength="20" 
												 list="jobTitleList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.sex' />:</label>
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.sex" maxlength="10" id="hrPersonSnap_sex" onchange="hrPersonChangeImg(this.value)"
												 list="sexList" listKey="value" listValue="content"
												 emptyOption="false" theme="simple">
										</s:select>
									</span>
									<s:textfield key="hrPersonSnap.cnCode" name="hrPersonSnap.cnCode" cssClass="" readonly="true"/>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.birthday' />:</label>
									<input name="hrPersonSnap.birthday" type="text" class="Wdate"  style="height:15px"
				              			 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
				              			 value="<fmt:formatDate value="${hrPersonSnap.birthday}" pattern="yyyy-MM-dd"/>"
				              			 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,onpicked:calPersonAge(this)})" 
				              			 /> 
									<s:textfield id="hrPersonSnap_age" key="hrPersonSnap.age" name="hrPersonSnap.age" cssClass="digits" readonly="true"/>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.nation' />:</label>
									<span class="formspan" style="float: left; width: 140px">
									  	<s:select  key="hrPersonSnap.nation" cssClass="input-small" maxlength="20"  
													list="nationList" listKey="value" listValue="content"
													emptyOption="true" theme="simple">
										</s:select>	
									 </span>	
									 <label><s:text name='hrPersonSnap.politicalCode' />:</label>
									 <span class="formspan" style="float: left; width: 140px">
									  	<s:select  key="hrPersonSnap.politicalCode" cssClass="input-small" maxlength="50" 
													list="politicalCodeList" listKey="value" listValue="content"
													emptyOption="true" theme="simple">
										</s:select>	
									 </span>	
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.idNumber" name="hrPersonSnap.idNumber" cssClass="idcard" maxlength="20" />
									<label><s:text name='hrPersonSnap.educationalLevel' />:</label>
									<span class="formspan" style="float: left; width: 140px">
									  	<s:select  key="hrPersonSnap.educationalLevel" cssClass="input-small" maxlength="20"  
													list="educationalLevelList" listKey="value" listValue="content"
													emptyOption="true" theme="simple">
										</s:select>	
									</span>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.mobilePhone" name="hrPersonSnap.mobilePhone" cssClass="phone" maxlength="15"/>
									<s:textfield key="hrPersonSnap.email" name="hrPersonSnap.email" cssClass="email" maxlength="50"/>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.nativePlace" name="hrPersonSnap.nativePlace" cssClass="" maxlength="30"/>
									<label><s:text name='hrPersonSnap.maritalStatus' />:</label>
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.maritalStatus" maxlength="10"
												 list="maritalStatusList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.homeTelephone" name="hrPersonSnap.homeTelephone" cssClass="telephone" maxlength="20"/>
								</div>
								<div class="unit">
								<s:textfield key="hrPersonSnap.domicilePlace" name="hrPersonSnap.domicilePlace" style="width:401px"  cssClass="" maxlength="100"/>
								</div>
								<div class="unit">
								<s:textfield key="hrPersonSnap.houseAddress" name="hrPersonSnap.houseAddress"  style="width:401px"  cssClass="" maxlength="100"/>
								</div>
							</div>
							<!-- 教育信息 -->
							<div class="unit">
								<a href="javascript:changeHrPersonSnapInfo('contentHrPersonSnapEdu');" style="font-size: 12px">教育信息</a>
							</div>
							<div id="contentHrPersonSnapEdu" style="display:none">
								<div class="unit">
									<s:textfield key="hrPersonSnap.school" name="hrPersonSnap.school" cssClass="" maxlength="50"/>
									<label><s:text name='hrPersonSnap.profession' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.profession" maxlength="20" 
												 list="professionList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.degree' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.degree" maxlength="20"
												 list="degreeList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
									<label><s:text name='hrPersonSnap.graduateDate' />:</label>
									<input name="hrPersonSnap.graduateDate" type="text"  class="Wdate" style="height:15px"
				              			 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
				              			 value="<fmt:formatDate value="${hrPersonSnap.graduateDate}" pattern="yyyy-MM-dd"/>"
				               			onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
				               	</div>
				               	<div class="unit">
				               		<s:textfield key="hrPersonSnap.zcGraduateSchool" name="hrPersonSnap.zcGraduateSchool" cssClass="" maxlength="50"/>
				               		<label><s:text name='hrPersonSnap.zcEduLevel' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.zcEduLevel" maxlength="20" 
												 list="educationalLevelList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
				               	</div>
				               	<div class="unit">
				               		<label><s:text name='hrPersonSnap.zcGraduateDate' />:</label>
				               		<input name="hrPersonSnap.zcGraduateDate" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.zcGraduateDate}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
				               	</div>
							</div>
							<!-- 工作信息 -->
							<div class="unit">
								<a href="javascript:changeHrPersonSnapInfo('contentHrPersonSnapWork');" style="font-size: 12px">工作信息</a>
							</div>
							<div id="contentHrPersonSnapWork" style="display:none">
								<div class="unit">
								 	<label><s:text name='hrPersonSnap.workBegin' />:</label>
								 	<input name="hrPersonSnap.workBegin" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.workBegin}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
				               		 <label><s:text name='hrPersonSnap.entryDate' />:</label>
				               		 <input name="hrPersonSnap.entryDate" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.entryDate}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.nowTitleCode' />:</label> 
									<span class="formspan" style="float: left; width: 140px"> 
										<s:select key="hrPersonSnap.nowTitleCode" maxlength="20" 
												 list="jobTitleList" listKey="value" listValue="content"
												 emptyOption="true" theme="simple">
										</s:select>
									</span>
								    <label><s:text name='hrPersonSnap.nowTitleTime' />:</label>
								    <input name="hrPersonSnap.nowTitleTime" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.nowTitleTime}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
								</div>
								<div class="unit">
									 <label><s:text name='hrPersonSnap.titleDate' />:</label>
									 <input name="hrPersonSnap.titleDate" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.titleDate}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
				               		 <label><s:text name='hrPersonSnap.dutyDate' />:</label>
				               		 <input name="hrPersonSnap.dutyDate" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.dutyDate}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.natureCode" name="hrPersonSnap.natureCode" cssClass="" maxlength="20"/>
									<label><s:text name='hrPersonSnap.teacherNurse' />:</label>
			    					 <span class="formspan" style="float:left;width:140px">
			     						 <s:checkbox key="hrPersonSnap.teacherNurse" name="hrPersonSnap.teacherNurse" theme="simple"/>
			     					 </span>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.jobLevel" name="hrPersonSnap.jobLevel" cssClass="" maxlength="20"/>
									<s:textfield key="hrPersonSnap.attenceCode" name="hrPersonSnap.attenceCode" cssClass="" maxlength="20"/>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.nurseAge" name="hrPersonSnap.nurseAge" cssClass="digits"/>
									<s:textfield key="hrPersonSnap.workAge" name="hrPersonSnap.workAge" cssClass="digits"/>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.softWorkAge" name="hrPersonSnap.softWorkAge" cssClass="digits"/>
									<s:textfield key="hrPersonSnap.workPhone" name="hrPersonSnap.workPhone" cssClass="" maxlength="15"/>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.formal' />:</label>
			    					<span class="formspan" style="float:left;width:140px">
			     						<s:checkbox key="hrPersonSnap.formal" name="hrPersonSnap.formal" theme="simple"/>
			     					</span>
				     				<label><s:text name='hrPersonSnap.purchaser' />:</label>
			    					<span class="formspan" style="float:left;width:140px">
			     						<s:checkbox key="hrPersonSnap.purchaser" name="hrPersonSnap.purchaser" theme="simple"/>
			     					</span>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.jobSalary" name="hrPersonSnap.jobSalary" cssClass="number"/>
				     			   	<label><s:text name='hrPersonSnap.salaryWay' />:</label>
				     				<span class="formspan" style="float: left; width: 140px">
				       					<s:select  key="hrPerson.salaryWay" cssClass="input-small" maxlength="20" 
				        				 list="payWayList" listKey="value" listValue="content"
				        				 emptyOption="true" theme="simple"></s:select>	
				     				 </span> 
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.salary" name="hrPersonSnap.salary" cssClass="number"/>
									<label><s:text name='hrPersonSnap.dealDate' />:</label>
									<input name="hrPersonSnap.dealDate" type="text" class="Wdate" style="height:15px"
					              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					               		 value="<fmt:formatDate value="${hrPersonSnap.dealDate}" pattern="yyyy-MM-dd"/>"
					               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.salaryLevel" name="hrPersonSnap.salaryLevel" cssClass="" maxlength="10"/>
									<label><s:text name='hrPersonSnap.salaryBDate' />:</label>
									<input name="hrPersonSnap.salaryBDate" type="text" class="Wdate" style="height:15px"
				              		 onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
				               		 value="<fmt:formatDate value="${hrPersonSnap.salaryBDate}" pattern="yyyy-MM-dd"/>"
				               		 onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.gzType'/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
				    				<s:select key="hrPersonSnap.gzType" headerKey=""   
										list="#request.gztypes" listKey="gzTypeId" listValue="gzTypeName"
						   	 			emptyOption="false"  maxlength="50" width="50px" theme="simple">
				       				</s:select>
				       				</span>
				       				<s:textfield key="hrPersonSnap.taxType" maxlength="20" cssClass=""/>
								</div>
								<div class="unit">
									<label><s:text name="hrPersonSnap.bank1"/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
										<input id="hrPersonSnap_bank1" type="text" name="hrPersonSnap.bank1" maxlength="10" value="${hrPersonSnap.bank1}">
										<input id="hrPersonSnap_bank1_id" type="hidden" value="${hrPersonSnap.bank1}">
									</span>
									<s:textfield key="hrPersonSnap.salaryNumber" maxlength="20" cssClass=""/>
								</div>
								<div class="unit">
									<label><s:text name="hrPersonSnap.bank2"/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
										<input id="hrPersonSnap_bank2" type="text" name="hrPersonSnap.bank2" maxlength="10" value="${hrPersonSnap.bank2}">
										<input id="hrPersonSnap_bank2_id" type="hidden" value="${hrPersonSnap.bank2}">
									</span>
									<s:textfield key="hrPersonSnap.salaryNumber2" maxlength="20" cssClass=""/>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.stopSalary'/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
										<s:checkbox key="hrPersonSnap.stopSalary" theme="simple"/>
									</span>
								</div>
								<div class="unit">
									<s:textarea key="hrPersonSnap.stopReason" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.kqType'/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
				    				<s:select key="hrPersonSnap.kqType" headerKey=""   
										list="#request.kqtypes" listKey="kqTypeId" listValue="kqTypeName"
						   	 			emptyOption="false"  maxlength="50" width="50px" theme="simple">
				       				</s:select>
				       				</span>
				       				<label><s:text name='hrPersonSnap.stopKq'/>:</label>
									<span  class="formspan" style="float: left; width: 140px">
										<s:checkbox key="hrPersonSnap.stopKq" theme="simple"/>
									</span>
								</div>
								<div class="unit">
									<s:textarea key="hrPersonSnap.stopKqReason" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
								</div>
							</div>
							<!-- 其他信息 -->
							<div class="unit">
								<a href="javascript:changeHrPersonSnapInfo('contentHrPersonSnapOther');" style="font-size: 12px">其他信息</a>
							</div>
							<div id="contentHrPersonSnapOther" style="display:none">
								<div class="unit">
									<s:textfield key="hrPersonSnap.station" name="hrPersonSnap.station" cssClass="" maxlength="20"/>
									<s:textfield key="hrPersonSnap.comeFrom" name="hrPersonSnap.comeFrom" cssClass="" maxlength="20"/>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.insuCode" name="hrPersonSnap.insuCode" cssClass="" maxlength="20"/>
									<s:textfield key="hrPersonSnap.passWord" name="hrPersonSnap.passWord" cssClass="" maxlength="50"/>
								</div>
								<div class="unit">
									<s:textfield key="hrPersonSnap.ratio" name="hrPersonSnap.ratio" cssClass="number"/>
									<label><s:text name='hrPersonSnap.foreigner' />:</label>
									<span class="formspan" style="float:left;width:140px">
										<s:checkbox key="hrPersonSnap.foreigner" name="hrPersonSnap.foreigner" theme="simple"/>
									</span>
								</div>
								<div class="unit">
									<label><s:text name='hrPersonSnap.jjmark' />:</label>
									<span class="formspan" style="float:left;width:140px">
										<s:checkbox key="hrPersonSnap.jjmark" name="hrPersonSnap.jjmark" theme="simple"/>
									</span>
									<%-- <label><s:text name='hrPersonSnap.disabled' />:</label>
									<span style="float:left;width:140px">
										<s:checkbox key="hrPersonSnap.disabled" name="hrPersonSnap.disabled" theme="simple"/>
									</span> --%>
								</div>
								<div class="unit">				
								     <s:textarea key="hrPersonSnap.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
								</div>	
							</div>
						</div><!--  -->
					</div><!-- center end -->
				</div><!-- container end-->
				</div>
				<s:if test="hrPersonSnap.disabled==true">
					<div style="position:absolute;left:720px;top:60px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
						<span style='color:red;font-size:12px'>已停用</span>
					</div>
				</s:if>
			</div><!-- pageFromContent end -->
			<div class="formBar">
				<ul>
					<li id="hrPersonSnapFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveHrPersonlink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closeHrPersonSnapFormDialog()"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





