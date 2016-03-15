
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainInstitutionGridReload(){
		var urlString = "trainInstitutionGridList";
		var code = jQuery("#search_trainInstitution_code").val();
		var name = jQuery("#search_trainInstitution_name").val();
		var contacts = jQuery("#search_trainInstitution_contacts").val();
		var contactAddress = jQuery("#search_trainInstitution_contactAddress").val();
		var evaluationGrade = jQuery("#search_trainInstitution_evaluationGrade").val();
		var remark = jQuery("#search_trainInstitution_remark").val();
		var disabled=jQuery("#search_trainInstitution_disabled").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_contacts="+contacts+"&filter_LIKES_contactAddress="+contactAddress;
		urlString=urlString+"&filter_EQS_evaluationGrade="+evaluationGrade+"&filter_LIKES_remark="+remark;
		urlString=urlString+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#trainInstitution_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var trainInstitutionGridIdString="#trainInstitution_gridtable";
	
	jQuery(document).ready(function() { 
var trainInstitutionGrid = jQuery(trainInstitutionGridIdString);
    trainInstitutionGrid.jqGrid({
    	url : "trainInstitutionGridList",
    	editurl:"trainInstitutionGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainInstitution.id" />',hidden:true,key:true},				
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainInstitution.code" />',hidden:false,highsearch:true},	
{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="trainInstitution.name" />',hidden:false,highsearch:true},	
{name:'contacts',index:'contacts',width : 80,align:'left',label : '<s:text name="trainInstitution.contacts" />',hidden:false,highsearch:true},	
{name:'contactNumber',index:'contactNumber',width : 100,align:'left',label : '<s:text name="trainInstitution.contactNumber" />',hidden:false,highsearch:true},
{name:'contactAddress',width : 200,index:'contactAddress',align:'left',label : '<s:text name="trainInstitution.contactAddress" />',hidden:false,highsearch:true},	
{name:'email',index:'email',width : 100,align:'left',label : '<s:text name="trainInstitution.email" />',hidden:false,highsearch:true},				
{name:'postCode',index:'postCode',width : 80,align:'left',label : '<s:text name="trainInstitution.postCode" />',hidden:false,highsearch:true},				
{name:'webSite',index:'webSite',width : 150,align:'left',label : '<s:text name="trainInstitution.webSite" />',hidden:false,highsearch:true},	
{name:'evaluationGrade',index:'evaluationGrade',width : 60,align:'left',label : '<s:text name="trainInstitution.evaluationGrade" />',hidden:false,highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainInstitution.remark" />',hidden:false,highsearch:true,formatter:stringFormatter},
{name : 'disabled',index : 'disabled',align : 'center',width:40,label : '<s:text name="trainInstitution.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true}
        ],
        jsonReader : {
			root : "trainInstitutions", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainInstitutionList.title" />',
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
			 gridContainerResize('trainInstitution','div');
			 var rowNum = jQuery(this).getDataIDs().length;
         	if(rowNum>0){
             	var rowIds = jQuery(this).getDataIDs();
             	var ret = jQuery(this).jqGrid('getRowData');
             	var id='';
             	for(var i=0;i<rowNum;i++){
           	  		id=rowIds[i];
           	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainInstitution(\''+id+'\');">'+ret[i]["code"]+'</a>');
           		}
         	}else{
	        	var tw = jQuery(this).outerWidth();
	        	jQuery(this).parent().width(tw);
	        	jQuery(this).parent().height(1);
	        }
           var dataTest = {"id":"trainInstitution_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(trainInstitutionGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#trainInstitution_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainInstitution?popup=true&navTabId=trainInstitution_gridtable";
		var winTitle='<s:text name="trainInstitutionNew.title"/>';
		$.pdialog.open(url,'addTrainInstitution',winTitle, {mask:true,width : 700,height : 350});
	}); 
     jQuery("#trainInstitution_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainInstitution_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainInstitutionEdit.title"/>';
		var url = "editTrainInstitution?popup=true&id="+sid+"&navTabId=trainInstitution_gridtable";
		$.pdialog.open(url,'editTrainInstitution',winTitle, {mask:true,width : 700,height : 350});
	}); 
	//trainInstitutionLayout.resizeAll();
  	});
	function viewTrainInstitution(id){
		var url = "editTrainInstitution?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainInstitution','查看培训机构信息', {mask:true,width : 700,height : 350});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainInstitution_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainInstitution_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.code'/>:
						 	<input type="text"  id="search_trainInstitution_code" style="width:80px"/>
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.name'/>:
						 	<input type="text"  id="search_trainInstitution_name" style="width:80px"/>
						</label>
						<label style="float:none;white-space:nowrap" >
						<s:text name='trainInstitution.disabled'/>:
					 	<s:select id="search_trainInstitution_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.contacts'/>:
						 	<input type="text"  id="search_trainInstitution_contacts" style="width:80px"/>
						</label>	
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.contactAddress'/>:
						 	<input type="text"  id="search_trainInstitution_contactAddress" style="width:80px"/>
						</label>	
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.evaluationGrade'/>:
							<s:select
     					   key="trainInstitution.evaluationGrade" id="search_trainInstitution_evaluationGrade"
     					   maxlength="50" list="evaluationGradeList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
     					   listValue="content" emptyOption="false" theme="simple"></s:select>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainInstitution.remark'/>:
						 	<input type="text"  id="search_trainInstitution_remark" />
						</label>
								
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainInstitutionGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainInstitutionGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainInstitution_buttonBar">
			<ul class="toolBar">
				<li><a id="trainInstitution_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainInstitution_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainInstitution_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('trainInstitution_gridtable','com.huge.ihos.hr.trainInstitution.model.TrainInstitution')"><span><s:text name="button.setColShow" /></span></a>
   				</li> --%>
			</ul>
		</div>
		<div id="trainInstitution_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainInstitution_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainInstitution_gridtable_addTile">
				<s:text name="trainInstitutionNew.title"/>
			</label> 
			<label style="display: none"
				id="trainInstitution_gridtable_editTile">
				<s:text name="trainInstitutionEdit.title"/>
			</label>
			<label style="display: none"
				id="trainInstitution_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainInstitution_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainInstitution_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainInstitution_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="trainInstitution_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainInstitution_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainInstitution_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainInstitution_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>