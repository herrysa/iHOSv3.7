
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() {
		var bmModelAssistGridIdString="#${random}bmModelAssist_gridtable";
		var bmModelAssistGrid = jQuery(bmModelAssistGridIdString);
    	bmModelAssistGrid.jqGrid({
    		url : "bmModelAssistGridList?assistCode=${assistCode}&modelId=${modelId}&pageType=${pageType}",
    		editurl:"bmModelAssistGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'ID',index:'ID',align:'left',label : '${assistName}ID',width:150,hidden:true,key:true},
			{name:'CODE',index:'CODE',align:'left',label : '${assistName}编码',width:150,hidden:false},
			{name:'NAME',index:'NAME',align:'left',label : '${assistName}名称',width:250,hidden:false}
			],
        	jsonReader : {
				root : "bmAssistItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'CODE',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bmModelAssistList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"${random}bmModelAssist_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    	jQuery("#${random}bmModelAssist_gridtable_add_c").click(function(){
        	var bmAssistItemUrl = "bmModelAssistList?layoutH=100&pageType=add&selectedId=${selectedId}&modelId=${modelId}&assistCode=${assistCode}&parentRandom=${random}";
    		$.pdialog.open(bmAssistItemUrl, 'chooseBmAssistItem', "选择${assistName}", {
    			mask : true,
    			width : 750,
    			height : 450
    		});
    		stopPropagation();
        });
    	jQuery("#${random}bmModelAssist_gridtable_del_c").click(function(){
    		var sids = jQuery("#${random}bmModelAssist_gridtable").jqGrid('getGridParam','selarrrow');
    		if(sids.length==0){
        		alertMsg.error("请选择${assistName}！");
        		return ;
        	}else{
        		jQuery.ajax({
        		    url: "bmModelAssistGridEdit",
        		    type: 'post',
        		    data:{assistCode:"${assistCode}",modelId:"${modelId}",saveIds:sids,oper:"del",},
        		    dataType: 'json',
        		    aysnc:false,
        		    error: function(data){
        		        
        		    },
        		    success: function(data){
        		    	data.navTabId = "${random}bmModelAssist_gridtable";
        		    	formCallBack(data);
        		    }
        		});
        	}
        });
    	jQuery("#${random}bmModelAssist_gridtable_selected").click(function(){
    		var sids = jQuery("#${random}bmModelAssist_gridtable").jqGrid('getGridParam','selarrrow');
    		if(sids.length==0){
        		alertMsg.error("请选择${assistName}！");
        		return ;
        	}else{
        		var names = "";
        		for(var i=0;i<sids.length;i++){
        	    	/* if((sids[i]=='all'||sids[i]=='ALL')&&sids.length>1){
        	    		alertMsg.error("您已选择全部数据，不能同时选择其他数据！");
        	    		return ;
        	    	} */
    				var ret = jQuery("#${random}bmModelAssist_gridtable").jqGrid('getRowData',sids[i]);
    				names+=ret["NAME"]+",";
        		}
        		jQuery.ajax({
        		    url: "saveBmModelAssist",
        		    type: 'post',
        		    data:{assistCode:"${assistCode}",modelId:"${modelId}",saveIds:sids,names:names},
        		    dataType: 'json',
        		    aysnc:false,
        		    error: function(data){
        		        
        		    },
        		    success: function(data){
        		    	$.pdialog.closeCurrent();
        		    	data.navTabId = "${parentRandom}bmModelAssist_gridtable";
        		    	//formCallBack(data);
        		    	jQuery('#'+data.navTabId).trigger("reloadGrid");
        		    }
        		});
        	}
    		
    	});
  	});
</script>

<div class="page">
	<div id="${random}bmModelAssist_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${random}bmModelAssist_search_form" >
					<label style="float:none;white-space:nowrap" >
						${assistName}编码:
						<input type="text" name="filter_LIKES_${bdInfoUtil.codeCol.fieldCode }"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${assistName}名称:
						<input type="text" name="filter_LIKES_${bdInfoUtil.nameCol.fieldCode }"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}bmModelAssist_search_form','${random}bmModelAssist_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<s:if test="pageType=='add'">
					<li><a id="${random}bmModelAssist_gridtable_selected" class="addbutton" href="javaScript:" ><span>确定</span>
					</a>
					</li>
				</s:if>
				<s:else>
					<s:if test="modelId!=null&&modelId!=''">
					<li><a id="${random}bmModelAssist_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a></li>
					</s:if>
					<s:else>
					<li id="addBmModelAssistLi"><a id="addBmModelAssistA" style='color:#808080;' class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a></li>
					<script>
						jQuery("#addBmModelAssistLi").unbind("hover");
						jQuery("#addBmModelAssistA").hover(function(e){
				    		e.stopPropagation();
				    	});
					</script>
					</s:else>
					<li><a id="${random}bmModelAssist_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
					</a>
					</li>
				</s:else>
			</ul>
		</div>
		<div id="${random}bmModelAssist_gridtable_div" layoutH="${layoutH}" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}bmModelAssist_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}bmModelAssist_gridtable_addTile">
				<s:text name="bmModelAssistNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}bmModelAssist_gridtable_editTile">
				<s:text name="bmModelAssistEdit.title"/>
			</label>
			<div id="load_${random}bmModelAssist_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}bmModelAssist_gridtable"></table>
			<!--<div id="bmModelAssistPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}bmModelAssist_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}bmModelAssist_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}bmModelAssist_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>