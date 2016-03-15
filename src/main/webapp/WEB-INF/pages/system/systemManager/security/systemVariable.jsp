<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">

	var pagefrom = "${pagefrom}";
	jQuery(document).ready(function() {
			
		
		jQuery("#sys_code_orgCode").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"select orgCode id,orgname name ,parentOrgCode parent from t_org where orgCode in ${fns:u_writeDPSql('org_dp')}",
			exceptnullparent:false,
			lazy:false,
			callback : {}
		});
			
			/* jQuery("#sys_code_copyCode").unbind("click").bind("click",function(){
				var orgCode = jQuery("#sys_code_orgCode_id").val();
				jQuery("#sys_code_copyCode").treeselect({
					dataType:"sql",
					optType:"single",
					sql:"select copycode id,copyname name ,0 parent from t_copy where orgCode='"+orgCode+"'",
					exceptnullparent:false,
					lazy:false,
					showOpt:'immediate',
					callback : {}
				});
			}); */
		jQuery("#systemVariable_currentRootMenu").change(function(){
			getOrgModel();
		});
		getOrgModel();
	});
	
	function getOrgModel(){
		var currentRootMenu = jQuery("#systemVariable_currentRootMenu").val();
		var businessDate = jQuery("#systemVariable_businessDate").val();
		var year = businessDate.split("-")[0];
		$.ajax({
		    url: 'findOrgModel?businessYear='+year+"&currentRootMenu="+currentRootMenu,
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		    	if(data!=null&&data.orgModel=='2'){
		    		jQuery("#systemVariable_org").show();
		    	}else{
		    		jQuery("#systemVariable_org").hide();
		    	}
		    }
		});
	}
	
	function cancelSetSV(){
		$.pdialog.closeCurrent();
		if(pagefrom=="login"){
			window.location.href = "${ctx }/j_security_logout";
		}
	}
	function setSV(){
		/* if(!jQuery("#sys_code_orgCode_id").val()){
			jQuery("#sys_code_orgCode_id").val("-1");
		}
		if(!jQuery("#sys_code_copyCode_id").val()){
			jQuery("#sys_code_copyCode_id").val("-1");
		} */
		/* if(!jQuery("#systemVariable_period").val()&&(jQuery("#systemVariable_currentRootMenu").val()!='50')){
			alertMsg.error("期间不能为空！");
			return ;
		} */
		$.ajax({
		    url: 'getPeriodByBusinessDate?businessDate='+$("#systemVariable_businessDate").val(),
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        //if(data.currentPeriod){
			        jQuery("#systemVariable_period").val(data.currentPeriod);
			        var formData =$('#sbForm').serializeObject();
					$.ajax({
					    url: 'setSystemVariable',
					    type: 'post',
					    dataType: 'json',
					    data:formData,
					    async:false,
					    error: function(data){
					    },
					    success: function(data){
					        if(data.message=='1'){
					        	jQuery("#dataBaseName").text(data.systemVariable.dataBaseName);
					        	//jQuery("#orgName").text(data.systemVariable.orgName);
					        	//jQuery("#copyName").text(data.systemVariable.copyName);
					        	jQuery("#businessDate").text(data.systemVariable.businessDate);
					        	jQuery("#currentPeriod").text(data.systemVariable.period);
					        	bulidMenuTree();
					        	var nanTabNum = jQuery(".navTab-tab").find("li").length;
					        	var tempNum = nanTabNum;
					        	for(var ntni=1;ntni<nanTabNum;ntni++){
					        		navTab._closeTab(tempNum-1);
					        		tempNum = jQuery(".navTab-tab").find("li").length;
					        	}
					        	/* systemVariableReay = 1;
					        	if((jQuery("select[name='systemVariable.orgCode']").val()!="-1")&&(jQuery("select[name='systemVariable.copyCode']").val()!="-1")){
					        		systemVariableLose = 0;
					        	} */
					        	jQuery("#ihosLogo").removeClass();
					        	var subsys = jQuery("#systemVariable_currentRootMenu").val();
					        	var logoName = "";
					        	if(subsys=='10'){
					        		logoName = "hr";
					        	}else if(subsys=='01'){
					        		logoName = "cb";
					        	}else if(subsys=='06'){
					        		logoName = "jx";
					        	}else if(subsys=='11'){
					        		logoName = "gz";
					        	}
					        	jQuery("#ihosLogo").addClass("logo"+logoName);
					        	var url = data.systemVariable.subSysMainPage;
					        	url = url.substring(1);
					        	if(url&&url!='empty.jsp'){
						        	jQuery("#mainPage").loadUrl(url);
					        	}else{
					        		jQuery("#mainPage").loadUrl("mainPage");
					        	}
					        	if(jQuery("#systemVariable_org").is(":visible")){
					        		var orgName = jQuery("#sys_code_orgCode").val();
					        		jQuery("#optOrgLi").text("操作单位："+orgName);
					        	}else{
					        		jQuery("#optOrgLi").text("单位名称：${fns:getVariable('%HASP_NAME%')}");
					        	}
					        	
					        	$.pdialog.closeCurrent();
					        }else{
					        	alertMsg.error(data.message);
					        }
					    }
					});
		        //}
		        /* else{
		        	//alertMsg.error("请选择已定义的期间内日期！");
		        	alertMsg.error("此日期没有对应期间！");
		        	jQuery("#systemVariable_period").val("");
		        } */
		    }
		});
		
	}
	function checkPeriod(obj){
		$.ajax({
		    url: 'getPeriodByBusinessDate?businessDate='+obj.value,
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data.currentPeriod){
			        jQuery("#systemVariable_period").val(data.currentPeriod);
		        }else{
		        	//alertMsg.error("请选择已定义的期间内日期！");
		        	//alertMsg.error("此日期没有对应期间！");
		        	jQuery("#systemVariable_period").val("未定义");
		        }
		    }
		});
	}
</script>

	<div class="page">
		<div class="pageContent">
			<form id="sbForm" method="post" action=""
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">
						<s:select style="font-size:12px;margin:0px" list="menus" listKey="menuId" listValue="menuName" headerKey="-1" headerValue="" key="systemVariable.currentRootMenu" id="systemVariable_currentRootMenu" cssClass="required"></s:select>
					</div>
					<div class="unit">
						<s:textfield   id="systemVariable_businessDate" key="systemVariable.businessDate" cssClass="required" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'${maxData}'})" readonly="true" onchange="checkPeriod(this)"/>
					</div>
					<div class="unit">
						<s:textfield   key="systemVariable.period" id="systemVariable_period" cssClass="required" readonly="true" disabled="true"/>
					</div>
					<div id="systemVariable_org" class="unit" style="display:none">
						<s:textfield id="sys_code_orgCode" key="systemVariable.orgCode" name="systemVariable.orgName" cssClass="" style="width:230px"/>
						<s:hidden id="sys_code_orgCode_id" name="systemVariable.orgCode"/>
					</div>
					<%-- <div class="unit">
						<s:select list="copyList"  id="systemVariable_copyCode" key="systemVariable.copyCode" listKey="copycode" listValue="copyname" headerKey="-1" headerValue="" cssClass="required" />
					</div> --%>
					<%-- <div class="unit" style="margin-top: 20px;margin-bottom: 20px;">
						<s:textfield id="sys_code_orgCode" key="systemVariable.orgCode" name="systemVariable_orgCode" cssClass="" style="width:230px"/>
						<s:hidden id="sys_code_orgCode_id" name="systemVariable.orgCode"/>
					</div>
					<div class="unit">
						<s:textfield id="sys_code_copyCode" key="systemVariable.copyCode" name="systemVariable_copyCode" cssClass="" style="width:230px"/>
						<s:hidden id="sys_code_copyCode_id" name="systemVariable.copyCode"/>
					</div> --%>
					 
				</div>
				<div class="formBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button id="setSVButton" type="button" onclick="setSV()">确定</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="cancelSetSV()">取消</button>
								</div>
							</div></li>
					</ul>
				</div>
			</form>
		</div>
	</div>
