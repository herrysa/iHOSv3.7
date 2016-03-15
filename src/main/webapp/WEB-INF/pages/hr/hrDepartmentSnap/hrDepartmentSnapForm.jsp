<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	function loadHrDeptSubSet(){
		var baseInfo = jQuery("#${random}_hrDepartmentSnapForm_Tabs_List").html();
		jQuery("#${random}_hrDepartmentSnapForm_Tabs_List").html("");
		var hrDeptTabPanel = new TabPanel({  
	        renderTo:'${random}_hrDepartmentSnapForm_Tabs_List',  
	        width:666,  
	        height:436,  
	        autoResizable:false,
	        heightResizable:false,
	        widthResizable:false,
	        active : 0,
	        items : [
				{id:'hrDeptSnapForm_baseInfo',title:'基本信息',html:baseInfo,closable: false}
	        ]
	    });
		var mainTable = "v_hr_department_current";
		jQuery.ajax({
	        url: 'getTableContentList',
	        data: {mainTable:mainTable},
	        type: 'post',
	        dataType: 'json',
	        async:false,
	        error: function(data){
	        },
	        success: function(data){
	       		for(var num = 0;num< data.sysTableContents.length;num++){
	       			var tableCode = data.sysTableContents[num].bdinfo.tableName;
	       			var subSetName = data.sysTableContents[num].name;
	       			var subSetHtml = '<div id="'+tableCode+'_div"></div>'
	       			var url = "showSubSet?subTableCode="+tableCode+"&subEntityId=${hrDepartmentSnap.deptId}&tablecontainer=${random}_hrDepartmentSnapForm_pageFormContent&oper=${oper}&snapCode=${hrDepartmentSnap.snapCode}";
	       			hrDeptTabPanel.addTab({id:tableCode,title:subSetName,html:subSetHtml,closable: false});
	       			jQuery("#"+tableCode+"_div").loadUrl(url);
	       		} 
	        }
	    });
		hrDeptTabPanel.show(0,false);
		return hrDeptTabPanel;
	}
	jQuery(document).ready(function() {
		
		jQuery("#${random}_hrDepartmentSnapForm").find("select").css("font-size","12px");
		var hrDeptTabPanel = loadHrDeptSubSet();
		jQuery('#${random}_saveHrDeptSnaplink').click(function() {
			 //附加信息保存
			var gridAllDatas=[];
			var hrDeptTabs = hrDeptTabPanel.getTabs();
			for(var i=0;i<hrDeptTabs.length;i++){
				 var tableCode = hrDeptTabs[i].id;
				 var gridRowNum=0;
				 var editFlag = jQuery("#"+tableCode+"_gridtable_editFlag").val();
          		 if(jQuery("#"+tableCode+"_gridtable").attr("id")){
          			jQuery("#"+tableCode+"_gridtable>tbody>tr").each(function (){ 
          				if(gridRowNum==0){
          					gridAllDatas.push({"hrSubSetName":tableCode});
          				}else{
          					if(editFlag=="1"){
          					jQuery("#"+tableCode+"_gridtable").jqGrid('saveRow',this.id,  {  
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
          					gridAllDatas.push(jQuery("#"+tableCode+"_gridtable").getRowData(this.id)); 
          				}
          				gridRowNum++;
                    }); 
          		 }
			}   
			   
			var jsonArray={"edit":gridAllDatas};
			jQuery("#${random}_hrDepartmentSnapForm_gridAllDatas").val(JSON.stringify(jsonArray)); 
			jQuery("#${random}_hrDepartmentSnapForm").attr("action","saveHrDepartmentSnap?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#${random}_hrDepartmentSnapForm").submit();
		});
		
		if("${oper}"!='view'){
			jQuery("#${random}_hrDepartmentSnap_name").addClass("required");
			initDeptTreeSelect("${random}_hrDepartmentSnap_jjDept","jjLeaf");
		 	initDeptTreeSelect("${random}_hrDepartmentSnap_ysDept","ysLeaf");
		}else{
			readOnlyForm("${random}_hrDepartmentSnapForm");
		}
		adjustFormInput("${random}_hrDepartmentSnapForm");
		
		jQuery("#${random}_hrDepartmentSnap_name").blur(function() {
			var val = jQuery(this).val();
			if(val) {
				jQuery("#${random}_hrDepartmentSnap_shortName").val(val);
			}
		});
		if("${entityIsNew}" == "true") {
			jQuery("#${random}_hrDepartmentSnap_deptCode").blur(function() {
				var val = jQuery(this).val();
				if(val) {
					jQuery("#${random}_hrDepartmentSnap_internalCode").val(val);
				}
			});
		}
	});
	
	/*奖金部门、预算部门的treeSelect*/
	function initDeptTreeSelect(id,column){
		var orgCode = "${hrDepartmentSnap.orgCode}";
		jQuery("#"+id).treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  deptId id,name,parentDept_id parent FROM v_hr_department_current where "+column+"=1 and disabled =0 and orgCode='"+orgCode+"' ORDER BY deptCode",
			exceptnullparent : false,
			lazy : true,
			selectParent:true
		});
	}
	/*保存后的回调函数*/
	function saveHrDepartmentSnapCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var deptNode = data.deptNode;
		if(!deptNode){
			return ;
		}
		if("${entityIsNew}"=="true"){
			dealHrTreeNode("hrDepartmentCurrentLeftTree",deptNode,"add");
		}else{
			dealHrTreeNode("hrDepartmentCurrentLeftTree",deptNode,"change");
		}
	}
	/*检查部门编码和名称*/
	function checkDuplicateField(obj,fieldName){
		var fieldValue = obj.value;
		if(!fieldValue){
			return;
		}
		var returnMessage = "该部门";
		if("deptCode"===fieldName){
			returnMessage += "编码";
		}else if("name"===fieldName){
			var orignalDeptName = "${hrDepartmentSnap.name}";
			if(orignalDeptName===fieldValue){
				return;
			}
			returnMessage += "名称";
		}
		returnMessage += "已存在。";
		var orgCode = "${hrDepartmentSnap.orgCode}";
		$.ajax({
		    url: "checkHrDeptSnapDuplicateField",
		    type: 'post',
		    data:{fieldName:fieldName,fieldValue:fieldValue,orgCode:orgCode,returnMessage:returnMessage},
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
	
	function setDeptOutIn(obj){
		if(obj.value=='门诊' || obj.value=='住院'){
			jQuery('#${random}_hrDepartmentSnap_outin').val(obj.value);
		}else{
			jQuery('#${random}_hrDepartmentSnap_outin').val("");
		}
	}
	
	function checkDeptPhone(obj){
		var value = obj.value;
		if(value){
			var reg=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/ 
			if(reg.test(value)==false){
				alertMsg.error("请填写合法的电话号。");
				obj.value=""
				return;
			}
		}
	}
	
	function closeHrDeptSnapFormDialog(){
		if("${oper}"!='view'){
			$.pdialog.closeCurrent();
		}else{
			$.pdialog.close('viewHrDepartmentSnap_${hrDepartmentSnap.snapId}');
		}
	}
	function changeHrDepartmentSnapInfo(id) {
		var content=jQuery("#"+id);
		if(content.css("display")=="none"){
			content.css("display","block");
		}else{
			content.css("display","none");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_hrDepartmentSnapForm" method="post"	action="" class="pageForm required-validate" onsubmit="return validateCallback(this,saveHrDepartmentSnapCallback);">
			<div id="${random}_hrDepartmentSnapForm_pageFormContent" class="pageFormContent" layoutH="45" style="padding:1px">
				<div id="${random}_hrDepartmentSnapForm_Tabs_List" style="margin:-1px">
					<div>
						<input type="hidden" name="gridAllDatas" id="${random}_hrDepartmentSnapForm_gridAllDatas"/>
						<s:hidden key="hrDepartmentSnap.snapId"/>
						<s:hidden key="hrDepartmentSnap.snapCode"/>
						<s:hidden key="hrDepartmentSnap.deptId"/>
						<s:hidden key="hrDepartmentSnap.deleted"/>
						<s:hidden key="hrDepartmentSnap.state"/>
						<s:hidden key="hrDepartmentSnap.hisPDSnapCode"/>
						<s:hidden key="hrDepartmentSnap.hisJjDSnapCode"/>
						<s:hidden key="hrDepartmentSnap.hisYsDSnapCode"/>
						<s:hidden key="hrDepartmentSnap.disabled"/>
						<s:hidden key="hrDepartmentSnap.leaf"/>
						<s:hidden key="hrDepartmentSnap.outer"/>
						<s:hidden key="hrDepartmentSnap.budget"/>
						<s:hidden key="hrDepartmentSnap.invalidDate"/>
						<s:hidden key="hrDepartmentSnap.purchaseDept"/>
						<s:hidden key="hrDepartmentSnap.funcDept"/>
						<input type="hidden" name="hrDepartmentSnap.outin" value="${hrDepartmentSnap.outin}" id="${random}_hrDepartmentSnap_outin"/>
					</div>
					<div class="unit">
						<s:textfield key="hrDepartmentSnap.orgName" readonly="true" value="%{hrDepartmentSnap.hrOrg.orgname}"/>
							<s:hidden key="hrDepartmentSnap.orgCode"/>
							<s:hidden key="hrDepartmentSnap.orgSnapCode"/>
						<s:if test="%{entityIsNew}">
							<label><s:text name='hrDepartmentSnap.deptCode' />:</label>
							<input type="text" id="${random}_hrDepartmentSnap_deptCode" name="hrDepartmentSnap.deptCode" value="${hrDepartmentSnap.deptCode}" maxLength="20" class="required" 
							notrepeat='部门编码已存在' validatorParam="from HrDepartmentCurrent dept where dept.deptCode=%value% and dept.orgCode='${hrDepartmentSnap.orgCode}'"/>
						</s:if>
						<s:else>
							<s:textfield key="hrDepartmentSnap.deptCode" name="hrDepartmentSnap.deptCode" readonly="true"/>
						</s:else>
					</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.name' />:</label>
						<s:if test="%{entityIsNew}">
						<input type="text" id="${random}_hrDepartmentSnap_name" name="hrDepartmentSnap.name" value="${hrDepartmentSnap.name}" maxLength="50" 
						notrepeat='部门名称已存在' validatorParam="from HrDepartmentCurrent dept where dept.name=%value% and dept.orgCode='${hrDepartmentSnap.orgCode}'"/>
						</s:if>
						<s:else>
						<input type="text" id="${random}_hrDepartmentSnap_name" name="hrDepartmentSnap.name" value="${hrDepartmentSnap.name}" maxLength="50" oldValue="${hrDepartmentSnap.name}"
						notrepeat='部门名称已存在' validatorParam="from HrDepartmentCurrent dept where dept.name=%value% and dept.orgCode='${hrDepartmentSnap.orgCode}'"/>
						</s:else>
						<s:textfield id="%{random}_hrDepartmentSnap_shortName" key="hrDepartmentSnap.shortName" name="hrDepartmentSnap.shortName" cssClass="" maxLength="30"/>
					</div>
					<div class="unit">
						<s:textfield id="%{random}_hrDepartmentSnap_internalCode" key="hrDepartmentSnap.internalCode" name="hrDepartmentSnap.internalCode" maxLength="20"/>
						<s:textfield key="hrDepartmentSnap.cnCode" name="hrDepartmentSnap.cnCode" readonly="true"/>
					</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.parentDept' />:</label>
						<input type="text" id="${random}_hrDepartmentSnap_parentDept" value="${hrDepartmentSnap.hisParentDept.name}" readonly="readonly"/>
						<input type="hidden" id="${random}_hrDepartmentSnap_parentDept_id" name="hrDepartmentSnap.parentDeptId" value="${hrDepartmentSnap.parentDeptId}"/>						
						<s:if test="%{entityIsNew}">
						<label><s:text name='hrDepartmentSnap.deptType'/>:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="hrDepartmentSnap.deptType" cssClass="required"
								list="deptTypeList" listKey="deptTypeName" listValue="deptTypeName"
								maxlength="20" emptyOption="false" theme="simple">
							</s:select>
						</span>
						</s:if>
						<s:else>
						<s:textfield key="hrDepartmentSnap.deptType" name="hrDepartmentSnap.deptType" cssClass="" readonly="true"/>
						</s:else>
					</div>
					<div class="unit">
						<label><s:text name="hrDepartmentSnap.branch" />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select list="branches" listKey="branchCode" listValue="branchName" emptyOption="true" name="hrDepartmentSnap.branchCode" value="hrDepartmentSnap.branchCode"></s:select>
						</span>
						<label><s:text name="hrDepartmentSnap.dgroup" />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select 
								name="hrDepartmentSnap.dgroup" value="hrDepartmentSnap.dgroup" required="false" maxlength="20"
								list="dgroupList" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select></span>
					</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.attrCode' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select
								key="hrDepartmentSnap.attrCode" onchange="setDeptOutIn(this)"
								list="deptAttrList" listKey="value" listValue="content"
								 emptyOption="true" theme="simple" maxlength="20">
							</s:select>
						</span>
						<label><s:text name='hrDepartmentSnap.subClass' />:</label> 
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select key="hrDepartmentSnap.subClass"  
							list="deptSubClassList"  listKey="value" listValue="content" 
							emptyOption="true" theme="simple" maxlength="20">
						</s:select> 
						</span>
					</div>
						<%-- <label><s:text name='hrDepartmentSnap.outin' />:</label> 
							<span style="float: left; width: 152px">
							 	<s:select key="hrDepartmentSnap.outin" 
							 		list="deptOutinList" listKey="value" listValue="content" 
							 		emptyOption="true" theme="simple" maxlength="10" >
							 	</s:select>
							</span>  --%>
					<div class="unit">
						<s:textfield key="hrDepartmentSnap.manager" name="hrDepartmentSnap.manager" maxLength="20" cssClass=""/>
						<s:textfield key="hrDepartmentSnap.phone" name="hrDepartmentSnap.phone" maxLength="20" cssClass="" onblur="checkDeptPhone(this)"/>
					</div>
					<div class="unit">
						<s:textfield key="hrDepartmentSnap.contact" name="hrDepartmentSnap.contact" maxLength="20" cssClass=""/>
						<s:textfield key="hrDepartmentSnap.contactPhone" name="hrDepartmentSnap.contactPhone" maxLength="20" onblur="checkDeptPhone(this)" cssClass=""/>
					</div>
					<div class="unit">
						<s:textfield key="hrDepartmentSnap.planCount" name="hrDepartmentSnap.planCount" cssClass="digits" readonly="true"/>
						<s:textfield id="hrDepartmentSnap_realCount" key="hrDepartmentSnap.realCount" name="hrDepartmentSnap.realCount"  cssClass="digits" readonly="true"/>
					</div>
					<div class="unit">
						<s:textfield id="hrDepartmentSnap_realZbCount" key="hrDepartmentSnap.realZbCount" name="hrDepartmentSnap.realZbCount" cssClass="digits" readonly="true"/>
						<s:textfield id="hrDepartmentSnap_diffCount" key="hrDepartmentSnap.diffCount" name="hrDepartmentSnap.diffCount" cssClass="digits" readonly="true"/>
						<%-- <s:textfield key="hrDepartmentSnap.invalidDate" name="hrDepartmentSnap.invalidDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/> --%>
					</div>
					<div class="unit">
						<s:textfield key="hrDepartmentSnap.clevel" name="hrDepartmentSnap.clevel" readonly="true"/>
					</div>
					<div class="unit">
						<s:textarea key="hrDepartmentSnap.remark"  maxlength="200" rows="2" cols="53" />
					</div>
					<div class="unit">
						<a href="javascript:changeHrDepartmentSnapInfo('contentHrDepartmentSnapOther');" style="font-size: 12px">其他信息</a>
						</div>
					<div id="contentHrDepartmentSnapOther" style="display:none">
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.kindCode' />:</label>
							<span class="formspan" style="float: left; width: 140px"> 
								<s:select key="hrDepartmentSnap.kindCode" 
									list="deptKindList" listKey="value" listValue="content" 
									emptyOption="true" theme="simple"  maxlength="20">
								</s:select>
							</span>
						<label><s:text name='hrDepartmentSnap.jjDeptType' />:</label> 
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select key="hrDepartmentSnap.jjDeptType" name="hrDepartmentSnap.jjDeptType.khDeptTypeId"
							list="jjDeptTypeList" listKey="khDeptTypeId" listValue="khDeptTypeName"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
						</span>
					</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.ysDept' />:</label>
						<input type="text" id="${random}_hrDepartmentSnap_ysDept" value="${hrDepartmentSnap.hisYsDept.name}"/>
						<input type="hidden" id="${random}_hrDepartmentSnap_ysDept_id" name="hrDepartmentSnap.ysDeptId" value="${hrDepartmentSnap.ysDeptId}"/>
						<label><s:text name='hrDepartmentSnap.jjDept' />:</label>
						<input type="text" id="${random}_hrDepartmentSnap_jjDept" value="${hrDepartmentSnap.hisJjDept.name}"/>
						<input type="hidden" id="${random}_hrDepartmentSnap_jjDept_id" name="hrDepartmentSnap.jjDeptId" value="${hrDepartmentSnap.jjDeptId}"/>
					</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.ysLeaf' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:checkbox key="hrDepartmentSnap.ysLeaf"  theme="simple"></s:checkbox>
						</span>
						<label><s:text name='hrDepartmentSnap.jjLeaf' />:</label> 
						<span class="formspan" style="float: left; width: 140px"> 
							<s:checkbox key="hrDepartmentSnap.jjLeaf"  theme="simple"></s:checkbox>
						</span>
					</div>
					</div>
					<%-- <div class="unit">
						<label><s:text name='hrDepartmentSnap.purchaseDept' />:</label> 
						<span style="float: left; width: 152px"> 
							<s:checkbox key="hrDepartmentSnap.purchaseDept" required="false" theme="simple"/>
						</span>
						<label><s:text name='hrDepartmentSnap.funcDept' />:</label> 
						<span style="float: left; width: 140px"> 
							<s:checkbox key="hrDepartmentSnap.funcDept" required="false" theme="simple"></s:checkbox>
						</span>
					</div> --%>
					<%-- <div class="unit">
						<label><s:text name='hrDepartmentSnap.outer' />:</label> 
						<span style="float: left; width: 152px"> 
							<s:checkbox	key="hrDepartmentSnap.outer" required="false" theme="simple" onclick=""></s:checkbox>
						</span>						
						<label><s:text name='hrDepartmentSnap.budget' />:</label> 
						<span style="float: left; width: 140px"> 
							<s:checkbox key="hrDepartmentSnap.budget" required="false" theme="simple"></s:checkbox>
						</span>
					</div> --%>
					<%-- <div class="unit">
						<label><s:text name='hrDepartmentSnap.leaf' />:</label> 
						<span style="float: left; width: 152px"> 
							<s:checkbox	key="hrDepartmentSnap.leaf" required="false" theme="simple" onclick=""></s:checkbox>
						</span>
						<label><s:text name='hrDepartmentSnap.disabled' />:</label> 
						<span style="float: left; width: 140px"> 
							<s:checkbox key="hrDepartmentSnap.disabled" required="false" theme="simple" />
						</span>
					</div> --%>
				</div>
				<s:if test="hrDepartmentSnap.state==4">
					<div style="position:absolute;left:580px;top:40px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
						<span style='color:red;font-size:12px'>已撤销</span>
					</div>
				</s:if>
				<s:elseif test="hrDepartmentSnap.disabled==true">
					<div style="position:absolute;left:580px;top:40px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
						<span style='color:red;font-size:12px'>已停用</span>
					</div>
				</s:elseif>
				
			</div>
		<div class="formBar">
			<ul>
				<li id="${random}_hrDepartmentSnapFormSaveButton"><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" id="${random}_saveHrDeptSnaplink"><s:text name="button.save" /></button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="Button" onclick="closeHrDeptSnapFormDialog()"><s:text name="button.cancel" /></button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		</form>
	</div>
</div>





