
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainTeacherGridReload(){
		var urlString = "trainTeacherGridList";
		var code = jQuery("#search_trainTeacher_code").val();
		var name = jQuery("#search_trainTeacher_name").val();
		var sex = jQuery("#search_trainTeacher_sex").val();
		var category = jQuery("#search_trainTeacher_category").val();
		var educationLevel = jQuery("#search_trainTeacher_educationLevel").val();
		var workUnit = jQuery("#search_trainTeacher_workUnit").val();
		var remark = jQuery("#search_trainTeacher_remark").val();
		var disabled=jQuery("#search_trainTeacher_disabled").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_EQS_sex="+sex+"&filter_EQS_category="+category;
		urlString=urlString+"&filter_EQS_educationLevel="+educationLevel+"&filter_LIKES_workUnit="+workUnit;
		urlString=urlString+"&filter_LIKES_remark="+remark+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#trainTeacher_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
			  var trainTeacherGridIdString="#trainTeacher_gridtable";
	
	jQuery(document).ready(function() { 
var trainTeacherGrid = jQuery(trainTeacherGridIdString);
    trainTeacherGrid.jqGrid({
    	url : "trainTeacherGridList",
    	editurl:"trainTeacherGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainTeacher.id" />',hidden:true,key:true},		
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainTeacher.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="trainTeacher.name" />',hidden:false,highsearch:true},	
{name:'sex',index:'sex',width : 40,align:'center',label : '<s:text name="trainTeacher.sex" />',hidden:false,highsearch:true},
{name:'birthDay',index:'birthDay',width : 70,align:'center',label : '<s:text name="trainTeacher.birthDay" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'phone',index:'phone',width : 100,align:'left',label : '<s:text name="trainTeacher.phone" />',hidden:false,highsearch:true},
{name:'email',index:'email',width : 100,align:'left',label : '<s:text name="trainTeacher.email" />',hidden:false,highsearch:true},
{name:'category',index:'category',width : 60,align:'left',label : '<s:text name="trainTeacher.category" />',hidden:false,highsearch:true},	
{name:'courses',index:'courses',width : 100,align:'left',label : '<s:text name="trainTeacher.courses" />',hidden:false,highsearch:true},
{name:'educationLevel',index:'educationLevel',width : 60,align:'left',label : '<s:text name="trainTeacher.educationLevel" />',hidden:false,highsearch:true},
{name:'profession',index:'profession',width : 60,align:'left',label : '<s:text name="trainTeacher.profession" />',hidden:false,highsearch:true},
{name:'school',index:'school',width : 100,align:'left',label : '<s:text name="trainTeacher.school" />',hidden:false,highsearch:true},	
{name:'workUnit',index:'workUnit',width : 100,align:'left',label : '<s:text name="trainTeacher.workUnit" />',hidden:false,highsearch:true},	
{name : 'disabled',index : 'disabled',align : 'center',width:40,label : '<s:text name="trainTeacher.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainTeacher.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}				

        ],
        jsonReader : {
			root : "trainTeachers", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainTeacherList.title" />',
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
			 gridContainerResize('trainTeacher','div');
			 var rowNum = jQuery(this).getDataIDs().length;
         	if(rowNum>0){
             	var rowIds = jQuery(this).getDataIDs();
             	var ret = jQuery(this).jqGrid('getRowData');
             	var id='';
             	for(var i=0;i<rowNum;i++){
           	  		id=rowIds[i];
           	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainTeacher(\''+id+'\');">'+ret[i]["code"]+'</a>');
           		}
         	}else{
				var tw = jQuery(this).outerWidth();
				jQuery(this).parent().width(tw);
				jQuery(this).parent().height(1);
			}
           var dataTest = {"id":"trainTeacher_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(trainTeacherGrid).jqGrid('bindKeys');
    
	
    jQuery("#trainTeacher_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainTeacher?popup=true&navTabId=trainTeacher_gridtable";
		var winTitle='<s:text name="trainTeacherNew.title"/>';
		$.pdialog.open(url,'addTrainTeacher',winTitle, {mask:true,width : 700,height : 370});
	}); 
     jQuery("#trainTeacher_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainTeacher_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainTeacherEdit.title"/>';
		var url = "editTrainTeacher?popup=true&id="+sid+"&navTabId=trainTeacher_gridtable";
		$.pdialog.open(url,'editTrainTeacher',winTitle, {mask:true,width : 700,height : 370});
	}); 
	
	//trainTeacherLayout.resizeAll();
  	});
	function viewTrainTeacher(id){
		var url = "editTrainTeacher?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainTeacher','查看培训教师信息', {mask:true,width : 700,height : 370});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainTeacher_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainTeacher_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainTeacher.code'/>:
						 	<input type="text"  id="search_trainTeacher_code" style="width:80px"/>
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainTeacher.name'/>:
						 	<input type="text"  id="search_trainTeacher_name" style="width:80px"/>
						</label>	
						<label style="float:none;white-space:nowrap" >
      						 <s:text name='trainTeacher.sex'/>:
      					 <s:select
          					   key="trainTeacher.sex" id="search_trainTeacher_sex"
           					 	 maxlength="50" list="sexList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
            					 listValue="content" emptyOption="false" theme="simple"></s:select>
     							 </label>
						<label style="float:none;white-space:nowrap" >
      						 <s:text name='trainTeacher.category'/>:
      					 <s:select
          					   key="trainTeacher.category" id="search_trainTeacher_category"
           					 	 maxlength="50" list="teacherCategoryList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
            					 listValue="content" emptyOption="false" theme="simple"></s:select>
     							 </label>
						<label style="float:none;white-space:nowrap" >
      						 <s:text name='trainTeacher.educationLevel'/>:
      					 <s:select
          					   key="trainTeacher.educationLevel" id="search_trainTeacher_educationLevel"
           					 	 maxlength="50" list="educationList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
            					 listValue="content" emptyOption="false" theme="simple"></s:select>
     							 </label>
     					
     					    <label style="float:none;white-space:nowrap" >
      							 <s:text name='trainTeacher.workUnit'/>:
     						   <input type="text" id="search_trainTeacher_workUnit" style="width:70px"/>
     							 </label>
    						  <label style="float:none;white-space:nowrap" >
     						  <s:text name='trainTeacher.remark'/>:
     						   <input type="text" id="search_trainTeacher_remark" style="width:80px"/>
     						 </label>
     						 <label style="float:none;white-space:nowrap" >
						<s:text name='trainTeacher.disabled'/>:
					 	<s:select id="search_trainTeacher_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>
     							 
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainTeacherGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainTeacherGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainTeacher_buttonBar">
			<ul class="toolBar">
				<li><a id="trainTeacher_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainTeacher_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainTeacher_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('trainTeacher_gridtable','com.huge.ihos.hr.trainTeacher.model.TrainTeacher')"><span><s:text name="button.setColShow" /></span></a>
   				</li> --%>
			</ul>
		</div>
		<div id="trainTeacher_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainTeacher_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainTeacher_gridtable_addTile">
				<s:text name="trainTeacherNew.title"/>
			</label> 
			<label style="display: none"
				id="trainTeacher_gridtable_editTile">
				<s:text name="trainTeacherEdit.title"/>
			</label>
			<label style="display: none"
				id="trainTeacher_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainTeacher_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainTeacher_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainTeacher_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="trainTeacher_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainTeacher_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainTeacher_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainTeacher_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>