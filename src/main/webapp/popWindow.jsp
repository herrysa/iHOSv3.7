<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/common/links.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script type="text/javascript">
function popWindow(){
var w = eval("window");
//w.open(pageURL, pageName, "channelmode=0,directories=0,fullscreen=0,menubar=0,toolbar=0,directories=0,scrollbars=1,resizable=1,location=1,status=1,titlebar=1,left=0,top=0,width="+ (screen.availWidth - 10) +",height="+ (screen.availHeight-50));
var win = w.open("${ctx}/mainMenu", "_blank", "toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,left=0 ,top=0,width=" + (screen.availWidth) + ",height=" + (screen.availHeight -35));
if(win){
window.opener=null;
window.open('','_self','');
window.close(); 
win.focus();
}
}
  
</script>
</head>
<body onload="popWindow();">

</body>
</html>