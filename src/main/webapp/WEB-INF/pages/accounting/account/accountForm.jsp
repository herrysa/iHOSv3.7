<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#accountForm").attr("action","saveAccount?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#accountForm").submit();
		});*/
		var diaTitle = $.pdialog.getCurrent().find(".dialogHeader").find("h1").html().trim();
		if(diaTitle=="增加科目"){
			jQuery("#accountNew_arrow").css("display","none");
			jQuery("#account_acctCode").blur(acctCodeValidate);
			jQuery("#account_acctCode").removeAttr("readonly");
		}
		if(diaTitle=="修改科目"){
			jQuery("#account_type_show").attr("disabled","disabled");
		}
		
	});
	var records = jQuery('#account_gridtable').jqGrid('getGridParam','records');
	var currentId = jQuery('#account_gridtable').jqGrid('getGridParam','selarrrow');
	var curpagenum = $("#account_gridtable").jqGrid('getGridParam', 'page');
	var rowListNum = $("#account_gridtable").jqGrid('getGridParam', 'rowNum');
	var currentIndex = jQuery('#account_gridtable').jqGrid('getInd',currentId);
	currentIndex += (curpagenum-1)*rowListNum;
	var neededIndex;
	
	function findAccount(type){
		if(type=='first'){
			neededIndex = currentIndex =1;
		}else if(type=='next'){
			if(currentIndex==records){
				alertMsg.info("当前已是最后一行");
				return;
			}
			neededIndex = ++currentIndex;
		}else if(type=='back'){
			if(currentIndex==1){
				alertMsg.info("当前已是第一行");
				return;
			}
			neededIndex = --currentIndex;
		}else{
			neededIndex = currentIndex=records;
		}
		if(selectId=='-1' || selectId==''){
			selectId='';
		}
		jQuery.ajax({
		    url: 'getAccountByNumber',
		    data: {number:neededIndex,selectId:selectId},
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		       // alert("failed");
		    },
		    success: function(data){
		    	changeAccountForm(data);
		    }
		});
		
		function changeAccountForm(data){
			var acct = data.acct;
	        jQuery('#account_acctCode').val(acct['acctCode']);
	        jQuery('#account_acctname').val(acct['acctname']);
	        jQuery('#account_cnCodeF').val(acct['cnCode']);
	        jQuery('#account_type option:first').text(acct.accttype['accounttype']);
	        jQuery('#account_type_show option:first').text(acct.accttype['accounttype']);
	        jQuery('#account_acctNature').val(acct['acctNature']);
	        jQuery('#account_direction').val(acct['direction']);
	        jQuery('#account_lossDirection').val(acct['lossDirection']);
	        jQuery('#account_disabled').prop('checked',acct['disabled']);
	        jQuery('#usedStamp').css("visibility",acct['isUsed']?"visible":"hidden");
	       
	        var arr = document.getElementsByName("typeName");
	        for(var i=0;i<arr.length;i++){
	        	arr[i].checked=false;
	        }
	        var assistTypes = acct['assistTypes'];
	        if(assistTypes==null){
	        	return;
	        }
	        assistTypes = acct['assistTypes'].split(",");
	        
	        for(var i=1;i<assistTypes.length-1;i++){
	        	for(var j=0;j<arr.length;j++){
		        	if(assistTypes[i].trim()==arr[j].value.trim()){
			        	arr[j].checked=true;
		        	}
		        }
	        }
		}
	}
	
	function acctCodeValidate(){
		var acctCodeText = jQuery("#account_acctCode").val();
		var accountCodeRule = jQuery("#account_codeRule").text();
		if(acctCodeText!=""){
			if(!isMatch(acctCodeText,accountCodeRule) || !/^[1-5]$/.test(acctCodeText.charAt(0))){
				alertMsg.error("科目代码格式不正确，请重新输入！");
				jQuery("#account_acctCode").val("");
			}else {
				jQuery.ajax({
				    url: 'checkAcctcode',
				    data:{acctcode:acctCodeText},
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    	//alertMsg.error("请求发送失败");
				    },
				    success: function(data){
				    	if(!data.hasFather){
				    		alertMsg.error("不存在上级科目:"+getFather(acctCodeText,accountCodeRule));
				    		jQuery("#account_acctCode").val("");
				    	}else{
				    		//检查科目代码是否重复需要通过id检查,重写checkId()
				    		var newAccountId = '001001_001_2012_'+acctCodeText;
				    		var url = 'checkId';
				    		url = encodeURI(url);
				    		$.ajax({
				    		    url: url,
				    		    type: 'post',
				    		    data:{entityName:'Account',searchItem:'acctId',searchValue:newAccountId,returnMessage:'该科目代码已存在，请更换！'},
				    		    dataType: 'json',
				    		    aysnc:false,
				    		    error: function(data){
				    		        
				    		    },
				    		    success: function(data){
				    		        if(data!=null){
				    		        	 alertMsg.error(data.message);
				    		        	 jQuery("#account_acctCode").val("");
				    		        }else{
				    		        	setTypeAndDirection(acctCodeText);
				    		        }
				    		    }
				    		});
				    	}
				    }
				});
			}
		} 
	}
	function setTypeAndDirection(acctCodeText){
		if(acctCodeText!=""){
			//根据填入的代码自动填充科目类型和余额方向
			var typeCode = acctCodeText.charAt(0);
			var index = parseInt(typeCode)-1;
			jQuery("#account_type").get(0).selectedIndex=index;
			jQuery("#account_type_show").get(0).selectedIndex=index;
			jQuery("#account_type_show").attr("disabled","disabled");
			if(typeCode=="2" || typeCode=="3" || typeCode=="4"){
				jQuery("#account_direction").get(0).selectedIndex=1;
			}else{
				jQuery("#account_direction").get(0).selectedIndex=0;
			}
		}
	}
	
</script>
<style>
#accountForm .pageFormContent label{
	width:80px;
}
#accountForm .pageFormContent .unit{
	height:21px;
	margin-left:30px;
}
.test {
    padding:10px;
    margin:10px;
    width:270px;
    color:#333; 
    border:rgb(49, 135, 221) solid 2px;
}
legend {
    color:#06c;
    padding:5px 10px;
    font-weight:500; 
    border:0
    /*background:white;*/
} 
#accountNew_arrow a{
	font-size:18px;
	text-decoration:none;
	color:#06c;
}

</style>
</head>

<div class="page">
	<div class="pageContent">
		<form id="accountForm" method="post"	action="saveAccount?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,refreshTree);">
			<div class="pageFormContent" layoutH="56">
				<legend>内容</legend>
				<div style="height:8px;margin-top:-24px">
					<span style="margin-left:120px">规则：</span><span id="account_codeRule"  >${codeRule}</sapn> 
				</div>
				<%-- <div class="unit">
					<label><s:text name="account.acctCode"/></label>
					<input type="text" id="account_acctCode" name="account.acctCode" value="${account.acctCode }" ${entityIsNew==false?'readonly=true':''}" class="required" style="margin-right:40px"/>
					<s:textfield id="account_acctname" key="account.acctname" name="account.acctname" cssClass="required" />
				</div> --%>
				<div class="unit">
					<s:textfield id="account_acctCode" key="account.acctCode"  name="account.acctCode" cssClass="required" readonly="true" style="margin-right:40px"/>
					<s:textfield id="account_acctname" key="account.acctname" name="account.acctname" cssClass="required" />
				</div> 
				<div class="unit">
					<s:select id="account_type_show" key="account.accttype.accttypeId" list="accountTypes" listKey="accttypeId" listValue="accounttype" style="margin-right:93px"></s:select>
					<s:textfield id="account_cnCodeF" key="account.cnCode" name="account.cnCode" readonly="true"/>
				</div>
				<div style="display:none;">
					<s:select id="account_type" key="account.accttype.accttypeId" list="accountTypes" listKey="accttypeId" listValue="accounttype"></s:select>
				</div>
				<div class="unit">
					<s:textfield id="account_acctNature" key="account.acctNature" name="account.acctNature" cssClass="required" style="margin-right:40px"/>
					<s:select id="account_direction" key="account.direction" list="#{'借':'借','贷':'贷'}" style="width:50px"></s:select>
				</div>
				<hr/>
				<legend>辅助</legend>
				<%-- <s:checkboxlist theme="template.css_xhtml"  name="assistType" list="assistTypes" listKey="orgCopyAsstIdPk.assistTypeId" listValue="assistType" template="checkboxlist.ftl"></s:checkboxlist> --%>
				<s:iterator var="asst" value="assistTypes" status="it">
					<s:if test="#it.index%4==0">
						<div class="unit">
					</s:if>
					
						<label><s:property value="typeName"/>   </label>
						<span style="width:30px;float:left;">
						<input id="assist_Type" type="checkbox" name="typeName" ${checked==true?'checked':''} value="<s:property value='typeCode' escapeHtml='false' /> "/>
						</span>
					<s:if test="#it.index%4==3 || #it.last">
						</div>
					</s:if>
				</s:iterator>
				<hr/>
				<div style="position:absolute;left:300px;top:240px">
					<img id="usedStamp" src="${ctx}/images/isUsed.png" style="width:150px;height:120px;visibility:${account.isUsed?'visible':'hidden'}"/>
				</div>
				<legend>其它</legend>
				<div class="unit">
				<s:select id="account_lossDirection" list="#{0:'自动转出',1:'借方转出',2:'贷方转出',3:'余额方向',4:'不转出'}" key="account.lossDirection" listKey="key" listValue="value"></s:select>
				</div>
				<div class="unit">
					<input style="margin-left:50px;margin-top:0px" type="checkbox" name="lossDirectionAsync" ${lossDirectionAsync=='true'?'checked':''} value="true"/>
					<label style="float:none">同步修改下科级结转方向</label>
				</div>
				<div class="unit">
					<input style="margin-left:50px;margin-top:0px" id="account_disabled" type="checkbox" name="account.disabled" ${account.disabled=='true'?'checked':''} value="true"/>
					<label style="float:none">冻结</label>
				</div>
			
			</div>
			<div class="formBar">
				<ul id="accountNew_arrow" style="float:left;margin-top:3px;margin-left:230px" class="nextbefor">
					<%-- <li><a href="javaScript:findAccount('first');"><img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/Button_First.png"></a>
					</li>
					<li><a href="javaScript:findAccount('next');"><img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/Button_Next.png" style="height:90%"></a>
					</li>
					<li><a href="javaScript:findAccount('back');"><img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/Button_Back.png" style="height:90%"></a>
					</li>
					<li><a href="javaScript:findAccount('last');"><img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/Button_Last.png" style="height:90%"></a>
					</li> --%>
					<li><a href="javaScript:findAccount('first');">&nbsp;&lt;&lt;</a>
					</li>
					<li><a href="javaScript:findAccount('next');">&nbsp;&gt;</a>
					</li>
					<li><a href="javaScript:findAccount('back');">&nbsp;&lt;</a>
					</li>
					<li><a href="javaScript:findAccount('last');">&nbsp;&gt;&gt;</a>
					</li>
					
				</ul>
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
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





