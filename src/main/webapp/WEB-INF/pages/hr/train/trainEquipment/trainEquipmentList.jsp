
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainEquipmentGridReload(){
		var urlString = "trainEquipmentGridList";
		var code = jQuery("#search_trainEquipment_code").val();
		var name = jQuery("#search_trainEquipment_name").val();
		var category = jQuery("#search_trainEquipment_category").val();
		var state = jQuery("#search_trainEquipment_state").val();
		var acquisitionDatefrom = jQuery("#trainEquipment_search_acquisitionDate_from").val();
		var acquisitionDateto = jQuery("#trainEquipment_search_acquisitionDate_to").val();
		var remark = jQuery("#search_trainEquipment_remark").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_EQS_category="+category+"&filter_EQS_state="+state;
		urlString=urlString+"&filter_GED_acquisitionDate="+acquisitionDatefrom+"&filter_LED_acquisitionDate="+acquisitionDateto;
		urlString=urlString+"&filter_LIKES_remark="+remark;
		urlString=encodeURI(urlString);
		jQuery("#trainEquipment_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
			  var trainEquipmentGridIdString="#trainEquipment_gridtable";
	
	jQuery(document).ready(function() { 
var trainEquipmentGrid = jQuery(trainEquipmentGridIdString);
    trainEquipmentGrid.jqGrid({
    	url : "trainEquipmentGridList",
    	editurl:"trainEquipmentGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainEquipment.id" />',hidden:true,key:true},	
{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="trainEquipment.code" />',hidden:false,highsearch:true},				
{name:'name',index:'name',align:'left',width : 150,label : '<s:text name="trainEquipment.name" />',hidden:false,highsearch:true},	
{name:'acquisitionAmount',index:'acquisitionAmount',width : 60,align:'left',label : '<s:text name="trainEquipment.acquisitionAmount" />',hidden:false,highsearch:true},				
{name:'acquisitionDate',index:'acquisitionDate',width : 70,align:'center',label : '<s:text name="trainEquipment.acquisitionDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
{name:'acquisitonExpenses',index:'acquisitonExpenses',width : 60,align:'right',label : '<s:text name="trainEquipment.acquisitonExpenses" />',hidden:false,formatter:'number',highsearch:true},				
{name:'category',index:'category',width : 70,align:'left',label : '<s:text name="trainEquipment.category" />',hidden:false,highsearch:true},				
{name:'state',index:'state',width : 60,align:'center',label : '<s:text name="trainEquipment.state" />',hidden:false,highsearch:true},	
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainEquipment.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}			
			

        ],
        jsonReader : {
			root : "trainEquipments", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainEquipmentList.title" />',
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
			 gridContainerResize('trainEquipment','div');
			 var rowNum = jQuery(this).getDataIDs().length;
         	if(rowNum>0){
             	var rowIds = jQuery(this).getDataIDs();
             	var ret = jQuery(this).jqGrid('getRowData');
             	var id='';
             	for(var i=0;i<rowNum;i++){
           	  		id=rowIds[i];
           	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainEquipmentRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
           		}
         	}else{
	        	var tw = jQuery(this).outerWidth();
	        	jQuery(this).parent().width(tw);
	        	jQuery(this).parent().height(1);
	        }
           var dataTest = {"id":"trainEquipment_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(trainEquipmentGrid).jqGrid('bindKeys');
    
	
    jQuery("#trainEquipment_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainEquipment?popup=true&navTabId=trainEquipment_gridtable";
		var winTitle='<s:text name="trainEquipmentNew.title"/>';
		$.pdialog.open(url,'addTrainEquipment',winTitle, {mask:true,width : 700,height : 300});
	}); 
     jQuery("#trainEquipment_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainEquipment_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainEquipmentEdit.title"/>';
		var url = "editTrainEquipment?popup=true&id="+sid+"&navTabId=trainEquipment_gridtable";
		$.pdialog.open(url,'editTrainEquipment',winTitle, {mask:true,width : 700,height : 300});
	}); 
	
	//trainEquipmentLayout.resizeAll();
  	});
	function viewTrainEquipmentRecord(id){
		var url = "editTrainEquipment?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainEquipment','查看培训设施信息', {mask:true,width : 700,height : 300});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainEquipment_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainEquipment_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainEquipment.code'/>:
						 	<input type="text"  id="search_trainEquipment_code" style="width:80px"/>
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainEquipment.name'/>:
						 	<input type="text"  id="search_trainEquipment_name" style="width:80px"/>
						</label>
						<label style="float:none;white-space:nowrap" >
      						 <s:text name='trainEquipment.category'/>:
       						<s:select
           					  key="trainEquipment.category" id="search_trainEquipment_category"  style="font-size:9pt;"
            				 maxlength="50" list="categoryList" listKey="value" headerKey="" headerValue="--" 
            				 listValue="content" emptyOption="false" theme="simple"></s:select>
     					 </label>
     					 <label style="float:none;white-space:nowrap" >
      						 <s:text name='trainEquipment.state'/>:
       						<s:select
           					  key="trainEquipment.state" id="search_trainEquipment_state"  style="font-size:9pt;"
            				 maxlength="50" list="stateList" listKey="value" headerKey="" headerValue="--" 
            				 listValue="content" emptyOption="false" theme="simple"></s:select>
     					 </label>
						<label style="float:none;white-space:nowrap" >
     						 <s:text name='trainEquipment.acquisitionDate'/>:
     						 <input type="text"	id="trainEquipment_search_acquisitionDate_from" style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainEquipment_search_acquisitionDate_to\')}'})">
    						  <s:text name='至'/>
    						   <input type="text"	id="trainEquipment_search_acquisitionDate_to" style="width:70px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainEquipment_search_acquisitionDate_from\')}'})">
    						 </label>	
    					<label style="float:none;white-space:nowrap" >
							<s:text name='trainEquipment.remark'/>:
						 	<input type="text"  id="search_trainEquipment_remark" style="width:90px;"/>
						</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainEquipmentGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainEquipmentGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="trainEquipment_buttonBar">
			<ul class="toolBar">
				<li><a id="trainEquipment_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainEquipment_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainEquipment_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('trainEquipment_gridtable','com.huge.ihos.hr.trainEquipment.model.TrainEquipment')"><span><s:text name="button.setColShow" /></span></a>
   				</li> --%>			
			</ul>
		</div>
		<div id="trainEquipment_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainEquipment_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainEquipment_gridtable_addTile">
				<s:text name="trainEquipmentNew.title"/>
			</label> 
			<label style="display: none"
				id="trainEquipment_gridtable_editTile">
				<s:text name="trainEquipmentEdit.title"/>
			</label>
			<label style="display: none"
				id="trainEquipment_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainEquipment_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainEquipment_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainEquipment_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="trainEquipment_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainEquipment_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainEquipment_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainEquipment_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>