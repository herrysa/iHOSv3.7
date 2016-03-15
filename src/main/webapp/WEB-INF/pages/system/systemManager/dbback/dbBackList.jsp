<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="periodList.title"/></title>
    <meta name="heading" content="<fmt:message key='periodList.heading'/>"/>
    <meta name="menu" content="PeriodMenu"/>
    
    <script type="text/javascript">
    function downloadBackupFile(){
	    	 var sid = jQuery("#dbbackup_gridtable").jqGrid('getGridParam','selarrrow');
		     if(sid==null || sid.length ==0){
	        	alertMsg.error(selectNone);
				return;
				}
		     if(sid.length>1){
		        	alertMsg.error(selectMore);
					return;
			 }else{
				 var dbbackId = jQuery("#dbbackup_gridtable").jqGrid('getGridParam','selrow');
				 
					var url = "downLoadBackUp?popup=true&dbBackupId=" + dbbackId;
					
					alertMsg.confirm("确认下载此数据库备份文件？", {
						okCall: function(){
							//$("#background,#progressBar").text("download now , plase wait!");
							location.href=url;
							
						}
					});
			}
    }
		jQuery(document).ready(function() { 
			
    	});
	</script>
</head>


<div class="page">
	<div class="pageContent" style="overflow:hidden">
<s:url id="editurl" action="dbBackupGridEdit"/> 
<s:url id="remoteurl" action="dbBackupGridList"/> 
	<s:url id="searchBooleanSelectList" action="searchSelectBooleanList?dicCode=radioOpenClose"	namespace="/system">
		
	</s:url>


<div class="panelBar">
			<ul class="toolBar">
				<li><a id="dbbackup_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="dbbackup_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a class="downloadbutton"  href="javaScript:downloadBackupFile();"><span>下载</span>
				</a>
				</li>
		
			</ul>
		</div>
		<div id="dbbackup_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			
			buttonBar="base_URL:editDbBackup;optId:dbBackupId;width:650;height:400"
			>
			<input type="hidden" id="dbbackup_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="dbbackup_gridtable_addTile">
				<fmt:message key="dbBackupNew.title"/>
			</label> 
		<%-- 	<label style="display: none"
				id="dbbackup_gridtable_editTile">
				<fmt:message key="costUseNew.title"/>
			</label> --%>
		
			

<sjg:grid 
    	id="dbbackup_gridtable" 
    	dataType="json" 
    	gridModel="dbBackups"

    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
   	
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="right"
    	
		navigator="false"
		navigatorAdd="false"
	    navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		draggable="true"
	autowidth="true"
	onCompleteTopics="onLoadSelect"
	    >
    <sjg:gridColumn 
    	    name="bkid" 
    	    search="false" 
    	    index="bkid" 
    	    title="%{getText('dbBackup.bkid')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"
    	    editable="true" 
    	    key="true"
    	    />
    	     <sjg:gridColumn 
 	    name="backupDataTime" 
 	    index="backupDataTime" 
 	    title="%{getText('dbBackup.backupDataTime')}"  
 	    width="100"
  	    />   
       <sjg:gridColumn 
 	    name="dbBackupFileName" 
 	    index="dbBackupFileName" 
 	    title="%{getText('dbBackup.dbBackupFileName')}"
 	    width="100"
  	    />
 	   
  	      <sjg:gridColumn 
 	    name="dbBackupFileSize" 
 	    index="dbBackupFileSize" 
 	    title="%{getText('dbBackup.dbBackupFileSize')}"
 	    width="100"
  	    /> 
 	    <sjg:gridColumn 
 	    name="operatorName" 
 	    index="operatorName" 
 	    title="%{getText('dbBackup.operatorName')}"
 	    width="100"
  	    />   
 	    <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('dbBackup.remark')}"
 	    width="100"
  	    />    
  </sjg:grid>
</div>
	
	</div>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="dbbackup_gridtable_totals"></label>条</span>
		</div>
		
		<div id="dbbackup_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>