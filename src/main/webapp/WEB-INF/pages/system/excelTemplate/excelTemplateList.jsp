
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var excelTemplateLayout;
	var excelTemplateGridIdString="#excelTemplate_gridtable";
	
	jQuery(document).ready(function() { 
		var excelTemplateGrid = jQuery(excelTemplateGridIdString);
    	excelTemplateGrid.jqGrid({
    		url : "excelTemplateGridList",
    		editurl:"excelTemplateGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'templateId',index:'templateId',align:'center',label : '<s:text name="excelTemplate.templateId" />',hidden:true,key:true},
{name:'templateCode',index:'templateCode',width:'80',align:'left',label : '<s:text name="excelTemplate.templateCode" />',hidden:false},
{name:'templateName',index:'templateName',width:'150',align:'left',label : '<s:text name="excelTemplate.templateName" />',hidden:false},
{name:'type',index:'type',width:'80',align:'left',label : '<s:text name="excelTemplate.type" />',hidden:false},
{name:'path',index:'path',width:'250',align:'left',label : '<s:text name="excelTemplate.path" />',hidden:false},
{name:'maker.name',index:'maker.name',width:'80',align:'left',label : '<s:text name="excelTemplate.maker" />',hidden:false},
{name:'makeDate',index:'makeDate',width:'100',align:'center',label : '<s:text name="excelTemplate.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
// {name:'disabled',index:'disabled',width:'50',align:'center',label : '<s:text name="excelTemplate.disabled" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',width:'200',align:'left',label : '<s:text name="excelTemplate.remark" />',hidden:false}
        	],
        	jsonReader : {
				root : "excelTemplates", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'templateId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="excelTemplateList.title" />',
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
		 		gridContainerResize('excelTemplate','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
				if (rowNum > 0) {
					var rowIds = jQuery(this).getDataIDs();
					var ret = jQuery(this).jqGrid('getRowData');
					var id = '';
					for ( var i = 0; i < rowNum; i++) {
						id = rowIds[i];
						setCellText(this,id,'path','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:downLoadExcelTemplate(\''+ret[i]["path"]+'\',\''+ret[i]["path"]+'\');">'+ret[i]["path"]+'</a>');
					}
				}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }

           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"excelTemplate_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("excelTemplate_gridtable");
       		} 

    	});
    jQuery(excelTemplateGrid).jqGrid('bindKeys');
    jQuery("#excelTemplate_gridtable_add_custom").unbind( 'click' ).bind("click",function(){   
		var winTitle='<s:text name="excelTemplateNew.title"/>';
		var url = "editExcelTemplate?popup=true&navTabId=excelTemplate_gridtable";
		$.pdialog.open(url,'editExcelTemplate',winTitle, {mask:true,width : 700,height : 300});
	}); 
    jQuery("#excelTemplate_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#excelTemplate_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="excelTemplateEdit.title"/>';
		var url = "editExcelTemplate?popup=true&templateId="+sid+"&navTabId=excelTemplate_gridtable";
		$.pdialog.open(url,'editExcelTemplate',winTitle, {mask:true,width : 700,height : 300});
	}); 
    jQuery("#excelTemplate_type").treeselect({
		dataType : "sql",
		optType : "multi",
		sql : "SELECT type id,type name FROM t_ExcelTemplate group by type",
		exceptnullparent : false,
		lazy : false
	});
  	});
	function downLoadExcelTemplate(filePath,fileName){
// 		url=encodeURI(url);
	var fileFullPath=jQuery("#excelTemplate_fileFullPath").val();
	fileFullPath+=filePath;
// 	fileName+=".xls";
	 jQuery.ajax({
         url: 'excelTemplateExist',
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

<div class="page"  id="excelTemplate_page">
	<div id="excelTemplate_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="excelTemplate_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='excelTemplate.templateCode'/>:
						<input type="text" name="filter_LIKES_templateCode" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='excelTemplate.templateName'/>:
						<input type="text" name="filter_LIKES_templateName" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='excelTemplate.type'/>:
						<input type="text" id="excelTemplate_type" style="width:120px"/>
						<input type="hidden" id="excelTemplate_type_id" name="filter_INS_type" style="width:100px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='excelTemplate.disabled'/>: --%>
<%-- 					 	<s:select name="filter_EQB_disabled" headerKey="" headerValue="--"  --%>
<%-- 							list="#{'1':'是','0':'否' }" listKey="key" listValue="value" --%>
<%-- 							emptyOption="false"  maxlength="10" theme="simple" > --%>
<%-- 						</s:select> --%>
<!-- 					</label>&nbsp;&nbsp; -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='excelTemplate.remark'/>:
						<input type="text" name="filter_LIKES_remark" style="width:150px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('excelTemplate_search_form','excelTemplate_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('excelTemplate_search_form','excelTemplate_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="excelTemplate_buttonBar">
			<ul class="toolBar" id="excelTemplate_toolbuttonbar">
				<li><a id="excelTemplate_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="excelTemplate_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="excelTemplate_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="excelTemplate_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="excelTemplate_fileFullPath" value='<s:property value="fileFullPath" escapeHtml="false" />'/>
			<input type="hidden" id="excelTemplate_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="excelTemplate_gridtable_addTile">
				<s:text name="excelTemplateNew.title"/>
			</label> 
			<label style="display: none"
				id="excelTemplate_gridtable_editTile">
				<s:text name="excelTemplateEdit.title"/>
			</label>
			<div id="load_excelTemplate_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="excelTemplate_gridtable"></table>
			<!--<div id="excelTemplatePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="excelTemplate_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="excelTemplate_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="excelTemplate_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="excelTemplate_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>