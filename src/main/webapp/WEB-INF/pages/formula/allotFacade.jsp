<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotFacade.title" /></title>
<meta name="heading" content="<fmt:message key='allotFacade.heading'/>" />
<meta name="menu" content="AllotFacadeMenu" />

<script type="text/javascript">
	jQuery(document).ready(function() {
	});
	function runtimeInterLog(){
		var url = "interLoggerList?popup=true";
		var winTitle='<fmt:message key="allotItemDetailNew.title"/>';
		openWindow(url, winTitle, " width=1000");
	}
	function noSelectConfirmProcess(taskName, args, msg) {
		var msgDialog = jQuery('#msgDialog');
		var confirmDialog = jQuery("#confirmDialog");
		confirmDialog.dialog('option', 'buttons', {
			"<fmt:message key='dialog.confirm'/>" : function() {
				jQuery(this).dialog("close");
				var url = "allotProcess?taskName=" + taskName + args;
				var jqxhr = jQuery.post(url, {
					dataType : "json"
				}).success(
						function(data) {

							var status = data['jsonStatus'];
							if (status == "error") {
								jQuery('div.#msgDialog')
										.html(
												"<p class='ui-state-error'>"
														+ data['jsonMessages']
														+ "</p>");
							} else {
								jQuery('div.#msgDialog')
										.html(
												"<p class='ui-state-hover'>"
														+ data['jsonMessages']
														+ "</p>");
							}
							msgDialog.dialog('open');
						});
			},
			"<fmt:message key='dialog.cancel'/>" : function() {
				jQuery(this).dialog("close");
			}
		});
		jQuery('div.#confirmDialog').html(msg);
		confirmDialog.dialog('open');
		/* 	} */
	}
</script>
</head>
<sj:dialog id="msgDialog"
	buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<sj:dialog id="confirmDialog"
	buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />

<input type="button" value="数据校验"></input>
<input type="button" value="预处理"></input>
<input type="button" value="开始核算"></input>
<br/>
<input type="button" value="浏览运行时日志" onclick="runtimeInterLog();"></input>
<button id="pub_process" onclick="noSelectConfirmProcess('test_process','&ARGS=CurrentPeriod','process messages.');">测试按钮</button>



<p>
存储过程参数要求： 第一个是返回码：整形，最后一个为返回消息：字符串 varchar类型 倒数第二个：字符型，用于内部logID
<br/><br/>
内部日志写入语句为：<br/>
insert into t_interlog  (logDateTime,taskInterId,logfrom,logmessage) VALUES(NOW(),intId,'sp test_process','test message');
<br/><br/>
<!-- <blockcode>
<button id="pub_process" onclick="noSelectConfirmProcess('test_process','&ARGS=CurrentPeriod','process messages.');">测试按钮</button>
</blockcode> -->
<xmp>
<button id="pub_process" onclick="noSelectConfirmProcess('test_process','&ARGS=CurrentPeriod','process messages.');">测试按钮</button>
</xmp>
</p>
