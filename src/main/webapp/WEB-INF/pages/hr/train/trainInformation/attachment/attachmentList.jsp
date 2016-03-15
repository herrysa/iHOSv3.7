
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function attachmentGridReload(){
		var urlString = "attachmentGridList";
		var code = jQuery("#search_attachment_code").val();
		var name = jQuery("#search_attachment_name").val();
		var remark = jQuery("#search_attachment_remark").val();
		
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_remark="+remark;
	    if("${type}"){
	    	urlString+="&filter_EQS_type="+"${type}";
	    	urlString+="&filter_EQS_foreignKey="+"${foreignKey}";
		}
		urlString=encodeURI(urlString);
		jQuery("#attachment_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
			  var attachmentGridIdString="#attachment_gridtable";
	
	jQuery(document).ready(function() { 
	var url="attachmentGridList";
	if("${type}"){
		url+="?filter_EQS_type="+"${type}";
		url+="&filter_EQS_foreignKey="+"${foreignKey}";
	}
var attachmentGrid = jQuery(attachmentGridIdString);
    attachmentGrid.jqGrid({
    	url :url,
    	editurl:"attachmentGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="attachment.id" />',hidden:true,key:true},				
{name:'code',index:'code',width : 80,align:'left',label : '<s:text name="attachment.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 200,align:'left',label : '<s:text name="attachment.name" />',hidden:false,highsearch:true},
{name:'foreignKey',width : 100,index:'foreignKey',align:'left',label : '<s:text name="attachment.foreignKey" />',hidden:true,highsearch:true},				
{name:'makeDate',width : 70,index:'makeDate',align:'center',label : '<s:text name="attachment.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
{name:'maker.name',index:'maker.name',width : 60,align:'left',label : '<s:text name="attachment.maker" />',hidden:false,highsearch:true},
{name:'path',index:'path',width : 100,align:'left',label : '<s:text name="attachment.path" />',hidden:true,highsearch:true},				
{name:'type',index:'type',width : 100,align:'left',label : '<s:text name="attachment.type" />',hidden:true,highsearch:true},				
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="attachment.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}	
        ],
        jsonReader : {
			root : "attachments", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="attachmentList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 /*2015.08.27 form search change*/
			 gridContainerResize('attachment','div');
			 var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];	  
	              	  var filePath="";
	              	  if(ret[i]["path"]){
	              		filePath=ret[i]["path"];
	              	    setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:downLoadAttachment(\''+filePath+'\',\''+ret[i]["name"]+'\');">'+ret[i]["name"]+'</a>');
	              	  }
	   	        	
	   	        	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewAttachmentRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              }
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
			 
           var dataTest = {"id":"attachment_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(attachmentGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#attachment_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editAttachment?popup=true&navTabId=attachment_gridtable";
		if("${type}"){
			url+="&type="+"${type}";
			url+="&foreignKey="+"${foreignKey}";
		}
		var winTitle='<s:text name="attachmentNew.title"/>';
		$.pdialog.open(url,'addAttachment',winTitle, {mask:true,width : 700,height : 270});
	}); 
     jQuery("#attachment_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#attachment_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="attachmentEdit.title"/>';
		var url = "editAttachment?popup=true&id="+sid+"&navTabId=attachment_gridtable";
		$.pdialog.open(url,'editAttachment',winTitle, {mask:true,width : 700,height : 270});
	}); 
	//attachmentLayout.resizeAll();
  	});
	function viewAttachmentRecord(id){
		var url = "editAttachment?oper=view&id="+id;
		$.pdialog.open(url,'viewAttachment','查看信息', {mask:true,width : 700,height : 270,maxable:false});
	}
	function downLoadAttachment(filePath,fileName){
//	 		url=encodeURI(url);
		var filePathArr = filePath.split('.');
		if(filePathArr.length > 0){
			fileName += "." + filePathArr[filePathArr.length-1];
		}
		var fileFullPath=jQuery("#attachment_fileFullPath").val();
		fileFullPath+=filePath;
		 jQuery.ajax({
             url: 'attachmentFileExist',
             data: {filePath:fileFullPath},
             type: 'post',
             dataType: 'json',
             async:false,
             error: function(data){
             },
             success: function(data){
             	if(data.type){
             		alertMsg.error(data.type);
        			return;
             	}else{
             		var url = "${ctx}/downLoadFile?filePath="+fileFullPath+"&fileName="+fileName+"&delete=0";
              		location.href=url; 
             	}
             }
         });
	}
</script>

<div class="page">
	<div class="pageHeader" id="attachment_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="attachment_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='attachment.code'/>:
						 	<input type="text"  id="search_attachment_code" style="width:100px"/>
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='attachment.name'/>:
						 	<input type="text"  id="search_attachment_name" style="width:100px"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='attachment.remark'/>:
						 	<input type="text"  id="search_attachment_remark" />
						</label>	
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="attachmentGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="attachmentGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="attachment_buttonBar">
			<ul class="toolBar">
				<li><a id="attachment_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="attachment_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="attachment_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('attachment_gridtable','com.huge.ihos.hr.attachment.model.Attachment')"><span><s:text name="button.setColShow" /></span></a>
   				</li> --%>			
			</ul>
		</div>
		<div id="attachment_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="attachment_fileFullPath" value='<s:property value="fileFullPath" escapeHtml="false" />'/>
			<input type="hidden" id="attachment_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="attachment_gridtable_addTile">
				<s:text name="attachmentNew.title"/>
			</label> 
			<label style="display: none"
				id="attachment_gridtable_editTile">
				<s:text name="attachmentEdit.title"/>
			</label>
			<label style="display: none"
				id="attachment_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="attachment_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_attachment_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="attachment_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="attachment_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="attachment_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="attachment_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="attachment_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>