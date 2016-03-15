
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
function strategyGridReload(){
		var urlString = "strategyGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#strategy_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var strategyLayout;
			  var strategyGridIdString="#strategy_gridtable";
	
	jQuery(document).ready(function() { 
		/* strategyLayout = makeLayout({
			baseName: 'strategy', 
			tableIds: 'strategy_gridtable'
		}, null); */
var strategyGrid = jQuery(strategyGridIdString);
    strategyGrid.jqGrid({
    	url : "strategyGridList",
    	editurl:"strategyGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
			{name:'strategyId',index:'strategyId',align:'center',label : '<s:text name="strategy.strategyId" />',hidden:true,key:true,formatter:'integer'},				
			{name:'strategyTitle',index:'strategyTitle',align:'center',label : '<s:text name="strategy.strategyTitle" />',hidden:false},				
			{name:'periodBegin',index:'periodBegin',align:'center',label : '<s:text name="strategy.periodBegin" />',hidden:false},				
			{name:'periodEnd',index:'periodEnd',align:'center',label : '<s:text name="strategy.periodEnd" />',hidden:false},				
			{name:'attachment',index:'attachment',align:'center',label : '<s:text name="strategy.attachment" />',hidden:false},				
			{name:'attachmentUrl',index:'attachmentUrl',align:'center',label : '<s:text name="strategy.attachmentUrl" />',hidden:true},				
			{name:'remark',index:'remark',align:'center',label : '<s:text name="strategy.remark" />',hidden:false},				
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="strategy.disabled" />',hidden:false,formatter:'checkbox'}				
        ],
        jsonReader : {
			root : "strategies", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'strategyId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="strategyList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        /* gridComplete: function() {
        	downLoadAttachment(this);
        }, */
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 downLoadAttachment(this);
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"strategy_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("strategy_gridtable");
       	} 

    });
    jQuery(strategyGrid).jqGrid('bindKeys');
  	});
	 function downLoadAttachment(grid){
		  var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData'); 
		if(rowNum>0){
			 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		var id = rowIds[i]; 
	    		var data=ret[i]["attachment"];
		 		var physicalPath=ret[i]["attachmentUrl"];
		 		var physicalPath=physicalPath+"\\"+data;
		 		physicalPath=encodeURI(physicalPath);
		 		setCellText(grid,id,'attachment','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showAttachment(\'downStragey?popup=true&strategyId='+id+'\',\''+physicalPath+'\')"  target="navTab">'+data+"</a>")
	    	 }
		 } 
	} 
	  function showAttachment(url,physicalPath){
		  alertMsg.confirm("确认下载此附件？", {
				okCall: function(){
					 jQuery.ajax({
						dataType : 'json',
						url:'existFile?physicalPath='+physicalPath,
					    type: 'POST',
					    error: function(data){
					    	alertMsg.confirm("此文件不存在！！！");
					    },success: function(data){
					    	location.href=url;
					    }
					});  
				}
			});
	} 
	  function setCellText(grid,rowid,colName,cellTxt){
			 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
	       for (; iCol<cCol; iCol++) {
	           if (cm[iCol].name === colName) {
	               tr = grid.rows.namedItem(rowid);
	               if (tr) {
	                  jQuery(tr.cells[iCol]).html(cellTxt);
	               }
	               break;
	           }
	       }
			
		}
</script>

<div class="page">
<div id="strategy_container">
			<div id="strategy_layout-center"
				class="pane ui-layout-center">
<%-- 	<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='strategy.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='strategy.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='strategy.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='strategy.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="strategyGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a id="strategy_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span>
				</a>
				</li>
				<li><a id="strategy_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="strategy_gridtable_edit" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span>
				</a>
				</li>
				<!-- <li><a class="downloadbutton"  href="javaScript:downloadBackupFile();"><span>下载附件</span>
				</a>
				</li> -->
			
			</ul>
		</div>
		<div id="strategy_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="optId:strategyId;width:500;height:300">
			<input type="hidden" id="strategy_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="strategy_gridtable_addTile">
				<s:text name="strategyNew.title"/>
			</label> 
			<label style="display: none"
				id="strategy_gridtable_editTile">
				<s:text name="strategyEdit.title"/>
			</label>
			<label style="display: none"
				id="strategy_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="strategy_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_strategy_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="strategy_gridtable"></table>
		<div id="strategyPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="strategy_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="strategy_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="strategy_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>