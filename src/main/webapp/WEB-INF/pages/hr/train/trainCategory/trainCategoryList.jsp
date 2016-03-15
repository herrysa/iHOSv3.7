
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainCategoryGridReload(){
		var urlString = "trainCategoryGridList";
		var code = jQuery("#search_trainCategory_code").val();
		var name = jQuery("#search_trainCategory_name").val();
		var goal=jQuery("#search_trainCategory_goal").val();
		var target=jQuery("#search_trainCategory_target").val();
		var content=jQuery("#search_trainCategory_content").val();
		var remark=jQuery("#search_trainCategory_remark").val();
		var disabled=jQuery("#search_trainCategory_disabled").val();
		
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_goal="+goal+"&filter_LIKES_target="+target;
		urlString=urlString+"&filter_LIKES_content="+content;
		urlString=urlString+"&filter_LIKES_remark="+remark+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#trainCategory_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var trainCategoryGridIdString="#trainCategory_gridtable";
	
	jQuery(document).ready(function() { 
		var trainCategoryGrid = jQuery(trainCategoryGridIdString);
	    trainCategoryGrid.jqGrid({
	    	url : "trainCategoryGridList",
	    	editurl:"trainCategoryGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainCategory.id" />',hidden:true,key:true},				
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainCategory.code" />',hidden:false,highsearch:true},	
				{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainCategory.name" />',hidden:false,highsearch:true},
				{name:'goal',index:'goal',width : 150,align:'left',label : '<s:text name="trainCategory.goal" />',hidden:false,highsearch:true},				
				{name:'target',index:'target',width : 80,align:'left',label : '<s:text name="trainCategory.target" />',hidden:false,highsearch:true},
				{name:'content',index:'content',width : 200,align:'left',label : '<s:text name="trainCategory.content" />',hidden:false,highsearch:true,formatter:stringFormatter},	
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainCategory.remark" />',hidden:false,highsearch:true,formatter:stringFormatter},
				{name : 'disabled',index : 'disabled',align : 'center',width:40,label : '<s:text name="trainCategory.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true}
	        ],
	        jsonReader : {
				root : "trainCategories", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainCategoryList.title" />',
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
				 gridContainerResize('trainCategory','div');
				var rowNum = jQuery(this).getDataIDs().length;
            	if(rowNum>0){
                	var rowIds = jQuery(this).getDataIDs();
                	var ret = jQuery(this).jqGrid('getRowData');
                	var id='';
                	for(var i=0;i<rowNum;i++){
              	  		id=rowIds[i];
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainCategoryRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              		}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	           var dataTest = {"id":"trainCategory_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	jQuery(trainCategoryGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#trainCategory_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainCategory?popup=true&navTabId=trainCategory_gridtable";
		var winTitle='<s:text name="trainCategoryNew.title"/>';
		$.pdialog.open(url,'addTrainCategory',winTitle, {mask:true,width : 700,height : 350});
	}); 
     jQuery("#trainCategory_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainCategory_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainCategoryEdit.title"/>';
		var url = "editTrainCategory?popup=true&id="+sid+"&navTabId=trainCategory_gridtable";
		$.pdialog.open(url,'editTrainCategory',winTitle, {mask:true,width : 700,height : 350});
	}); 
	//trainCategoryLayout.resizeAll();
  	});
	function viewTrainCategoryRecord(id){
		var url = "editTrainCategory?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainCategory','查看培训项目信息', {mask:true,width : 700,height : 350});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainCategory_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainCategory_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.code'/>:
					 	<input type="text"  id="search_trainCategory_code" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.name'/>:
					 	<input type="text"  id="search_trainCategory_name" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.goal'/>:
					 	<input type="text"  id="search_trainCategory_goal" style="width:80px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.disabled'/>:
					 	<s:select id="search_trainCategory_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.target'/>:
						<s:select key="trainCategory.target" id="search_trainCategory_target" headerKey="" headerValue="--" 
    					   maxlength="50" list="trainTargetList" listKey="value" style="font-size:9pt;"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.content'/>:
					 	<input type="text"  id="search_trainCategory_content" style="width:80px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainCategory.remark'/>:
					 	<input type="text"  id="search_trainCategory_remark" />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="trainCategoryGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="trainCategoryGridReload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainCategory_buttonBar">
			<ul class="toolBar">
				<li><a id="trainCategory_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainCategory_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainCategory_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="trainCategory_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainCategory_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainCategory_gridtable_addTile">
				<s:text name="trainCategoryNew.title"/>
			</label> 
			<label style="display: none"
				id="trainCategory_gridtable_editTile">
				<s:text name="trainCategoryEdit.title"/>
			</label>
			<label style="display: none"
				id="trainCategory_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainCategory_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainCategory_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainCategory_gridtable"></table>
</div>
	</div>
	<div class="panelBar" id="trainCategory_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainCategory_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainCategory_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainCategory_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>