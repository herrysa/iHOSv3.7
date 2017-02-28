<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#balanceAssistForm").attr("action","saveBalanceAssist?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#balanceAssistForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="balanceAssistForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="balanceAssist.balanceAssistId"/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="balanceAssist.assistType.id" list="assistTypeList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_assistValue" key="balanceAssist.assistValue" name="balanceAssist.assistValue" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="balanceAssist.balance.id" list="balanceList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_beginDai" key="balanceAssist.beginDai" name="balanceAssist.beginDai" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_beginJie" key="balanceAssist.beginJie" name="balanceAssist.beginJie" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_initBalance" key="balanceAssist.initBalance" name="balanceAssist.initBalance" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_num" key="balanceAssist.num" name="balanceAssist.num" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balanceAssist_yearBalance" key="balanceAssist.yearBalance" name="balanceAssist.yearBalance" cssClass="				number
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





