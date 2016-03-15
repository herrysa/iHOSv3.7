
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainSiteGridReload(){
		var urlString = "trainSiteGridList";
		var code = jQuery("#search_trainSite_code").val();
		var name = jQuery("#search_trainSite_name").val();
		var address = jQuery("#search_trainSite_address").val();
		var evaluationGrade = jQuery("#search_trainSite_evaluationGrade").val();
		var contacts = jQuery("#search_trainSite_contacts").val();
		var remark=jQuery("#search_trainSite_remark").val();
		  var disabled=jQuery("#search_trainSite_disabled").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_address="+address+"&filter_EQS_evaluationGrade="+evaluationGrade;
		urlString=urlString+"&filter_LIKES_contacts="+contacts;
		urlString=urlString+"&filter_LIKES_remark="+remark+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#trainSite_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
			  var trainSiteGridIdString="#trainSite_gridtable";
	
	jQuery(document).ready(function() { 
var trainSiteGrid = jQuery(trainSiteGridIdString);
    trainSiteGrid.jqGrid({
    	url : "trainSiteGridList",
    	editurl:"trainSiteGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainSite.id" />',hidden:true,key:true},
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainSite.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 150,align:'left',label : '<s:text name="trainSite.name" />',hidden:false,highsearch:true},
{name:'contacts',index:'contacts',width : 80,align:'left',label : '<s:text name="trainSite.contacts" />',hidden:false,highsearch:true},
{name:'contactNumber',index:'contactNumber',width : 100,align:'left',label : '<s:text name="trainSite.contactNumber" />',hidden:false,highsearch:true},
{name:'address',index:'address',width : 150,align:'left',label : '<s:text name="trainSite.address" />',hidden:false,highsearch:true},				
{name:'equipment',index:'equipment',width : 100,align:'left',label : '<s:text name="trainSite.equipment" />',hidden:false,highsearch:true},				
{name:'evaluationGrade',index:'evaluationGrade',width : 80,align:'left',label : '<s:text name="trainSite.evaluationGrade" />',hidden:false,highsearch:true},				
{name:'rentCharge',index:'rentCharge',width : 60,align:'right',label : '<s:text name="trainSite.rentCharge" />',hidden:false,formatter:'number',highsearch:true},				
{name:'scale',index:'scale',width : 60,align:'left',label : '<s:text name="trainSite.scale" />',hidden:false,highsearch:true},		
{name : 'disabled',index : 'disabled',align : 'center',width:40,label : '<s:text name="trainSite.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainSite.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}		
        ],
        jsonReader : {
			root : "trainSites", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainSiteList.title" />',
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
			 gridContainerResize('trainSite','div');
			 var rowNum = jQuery(this).getDataIDs().length;
         	if(rowNum>0){
             	var rowIds = jQuery(this).getDataIDs();
             	var ret = jQuery(this).jqGrid('getRowData');
             	var id='';
             	for(var i=0;i<rowNum;i++){
           	  		id=rowIds[i];
           	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainSiteRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
           		}
         	}else{
				var tw = jQuery(this).outerWidth();
				jQuery(this).parent().width(tw);
				jQuery(this).parent().height(1);
			}
           var dataTest = {"id":"trainSite_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(trainSiteGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#trainSite_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainSite?popup=true&navTabId=trainSite_gridtable";
		var winTitle='<s:text name="trainSiteNew.title"/>';
		$.pdialog.open(url,'addTrainSite',winTitle, {mask:true,width : 700,height : 310});
	}); 
     jQuery("#trainSite_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainSite_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainSiteEdit.title"/>';
		var url = "editTrainSite?popup=true&id="+sid+"&navTabId=trainSite_gridtable";
		$.pdialog.open(url,'editTrainSite',winTitle, {mask:true,width : 700,height : 310});
	}); 
	//trainSiteLayout.resizeAll();
  	});
	function viewTrainSiteRecord(id){
		var url = "editTrainSite?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainSite','查看培训场所信息', {mask:true,width : 700,height : 310});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainSite_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainSite_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainSite.code'/>:
						 	<input type="text"  id="search_trainSite_code" style="width:90px"/>
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainSite.name'/>:
						 	<input type="text"  id="search_trainSite_name" style="width:90px"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainSite.address'/>:
						 	<input type="text"  id="search_trainSite_address" style="width:90px"/>
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='trainSite.contacts'/>:
						 	<input type="text"  id="search_trainSite_contacts" style="width:90px"/>
						</label>	
						
						<label style="float:none;white-space:nowrap" >
       					<s:text name='trainSite.evaluationGrade'/>:
      					 <s:select
          					   key="trainSite.evaluationGrade" id="search_trainSite_evaluationGrade"  style="font-size:9pt;"
           						  maxlength="50" list="evaluationGradeList" listKey="value" headerKey="" headerValue="--" 
           						  listValue="content" emptyOption="false" theme="simple"></s:select>
    					  </label>
    					  <label style="float:none;white-space:nowrap" >
     					  <s:text name='trainSite.remark'/>:
      					  <input type="text" id="search_trainSite_remark" style="width:75px"/>
      						</label>
      						<label style="float:none;white-space:nowrap" >
						<s:text name='trainSite.disabled'/>:
					 	<s:select id="search_trainSite_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>
     				
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainSiteGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainSiteGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="trainSite_buttonBar">
			<ul class="toolBar">
				<li><a id="trainSite_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainSite_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainSite_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('trainSite_gridtable','com.huge.ihos.hr.trainSite.model.TrainSite')"><span><s:text name="button.setColShow" /></span></a>
   				</li> --%>
			</ul>
		</div>
		<div id="trainSite_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainSite_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainSite_gridtable_addTile">
				<s:text name="trainSiteNew.title"/>
			</label> 
			<label style="display: none"
				id="trainSite_gridtable_editTile">
				<s:text name="trainSiteEdit.title"/>
			</label>
			<label style="display: none"
				id="trainSite_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainSite_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainSite_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainSite_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="trainSite_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainSite_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainSite_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainSite_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>