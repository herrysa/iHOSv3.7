
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var colSearchLayout;
	var colSearchGridIdString="#colSearch_gridtable";
	//alert("${colSelectStr}");
	var searchGrid = "${grid}";
	var entityName = "${entityName}";
	var gridCols = "${cols}";
	jQuery(document).ready(function() { 
		var colSearchGrid = jQuery(colSearchGridIdString);
		var gridUrl = "colSearchGridList?entityName=${entityName}&cols=${cols}&titles=${titles}"
		gridUrl = encodeURI(gridUrl);
    	colSearchGrid.jqGrid({
    		url : gridUrl,
    		urlback:"",
    		editurl:"colSearchGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'id',index:'id',align:'center',label : '<s:text name="colSearch.id" />',hidden:true,key:true},
			{name:'col',index:'col',align:'center',label : '<s:text name="colSearch.label" />',hidden:false,width:150,editable: true,edittype:"select",editoptions:{value:"${colSelectStr}"}},
			{name:'label',index:'label',align:'left',label : '<s:text name="colSearch.label" />',hidden:true},
			{name:'filedType',index:'filedType',align:'left',label : '<s:text name="colSearch.label" />',hidden:true,editable:true,edittype:"select",editoptions:{value:"${colTypeSelectStr}"}},
			{name:'operator',index:'operator',align:'center',label : '<s:text name="colSearch.operator" />',hidden:false,editable: true,edittype:"select",editoptions:{value:"EQ:=;NE:<>;GT:>;LT:<;GE:>=;LE:<=;LIKE:like;IN:in;NI:not in"}},
			{name:'templetName',index:'templetName',align:'center',label : '<s:text name="colSearch.templetName" />',hidden:true},
			{name:'editTime',index:'editTime',align:'center',label : '<s:text name="colSearch.editTime" />',hidden:true},
			{name:'value',index:'value',align:'center',label : '<s:text name="colSearch.value" />',hidden:false,editable: true}        	
			],
        	jsonReader : {
				root : "colSearches", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'editTime',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="colSearchList.title" />',
        	height:250,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: false,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"colSearch_gridtable"};
      	   //	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("colSearch_gridtable");
      	  	fullGridEdit("#colSearch_gridtable");
	      	  jQuery("select[name='col']").change(function(){
	          	jQuery(this).parent().parent().find("select[name='filedType']").val(jQuery(this).val());
	          });
       		} 

    	});
    jQuery(colSearchGrid).jqGrid('bindKeys');
    jQuery("#highSearchButton").click(function(){
    	
    	var searchChecked = jQuery("#colSearchCheckbox").attr('checked');
    	var searchName = jQuery("#colSearchName").val();
    	if(searchChecked=='checked'){
    		if(!searchName){
    			alert('sn');
    			return ;
    		}
    	}
    	
    	var trIndex = 0;
    	jQuery("input[name='value']","#colSearch_gridtable").each(function(){
    		var thisValue = jQuery(this).val();
    		var thisCol,thisOpt;
    		if(thisValue){
    			thisCol = jQuery("select[name='col']","#colSearch_gridtable").eq(trIndex).val();
    			thisOpt = jQuery("select[name='operator']","#colSearch_gridtable").eq(trIndex).val();
    		trIndex++;
    		}
    	});
    	var searchUrl = jQuery("#"+searchGrid).jqGrid('getGridParam', 'url');
    	var urlBack = jQuery("#"+searchGrid).jqGrid('getGridParam', 'urlback');
    	if(urlBack!=null){
    		searchUrl = urlBack;
    	}else{
    		jQuery("#"+searchGrid).jqGrid('setGridParam',{urlback:searchUrl});
    	}
    	if(searchUrl.indexOf("?")==-1){
    		searchUrl += "?a=1"
    	}
    	
    	var cols="",operators="",values="",colIsShows="",endStr="";
    	jQuery("input[name='value']").each(function(){
    		var v = jQuery(this).val();
    		if(v){
    			var col = jQuery(this).parent().parent().find("select[name='col']").eq(0).val();
    			var operator = jQuery(this).parent().parent().find("select[name='operator']").eq(0).val();
    			var filedType = jQuery(this).parent().parent().find("select[name='filedType']").eq(0).find("option:selected").text();
    			var filter = "&filter_"+operator+filedType+"_"+col+"="+v;
    			searchUrl += filter;
    			values += v+",";
    			cols += col+",";
    			operators += operator+",";
    		}
    	});
    	if(searchChecked=='checked'){
    		$.ajax({
			    url: 'saveColSearchTempl',
			    type: 'post',
			    data:{templName:searchName,entityName:entityName,cols:cols,operators:operators,values:values},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    },
			    success: function(data){
			        // do something with xml
			        alert(data);
			    }
			});
    	}
    	
    	jQuery("#"+searchGrid).jqGrid('setGridParam',{url:searchUrl,page:1}).trigger("reloadGrid");
    	$.pdialog.closeCurrent();
    	
    });
    
    jQuery("select[name='searchTemplt']").change(function(){
    	var templtName = jQuery(this).val();
    	var url = "colSearchGridList?entityName=${entityName}&templateName="+templtName+"&cols=${cols}&titles=${titles}";
    	jQuery("#colSearch_gridtable").jqGrid('setGridParam',{url:url,page:1}).trigger("reloadGrid");
    });
    
  	});
	
	
	
</script>

<style>
.test {
    padding:10px;
    margin:10px;
    height:90%;
    color:#333; 
    border:rgb(49, 135, 221) solid 1px;
}
.test legend {
    color:#06c;
    padding:5px 10px;
    font-weight:normal !important; 
    border:0
    /*background:white;*/
} 
</style>

<div class="page">
	<%-- <div id="colSearch_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="colSearch_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.id'/>:
						<input type="text" name="filter_EQS_id"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.col'/>:
						<input type="text" name="filter_EQS_col"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.label'/>:
						<input type="text" name="filter_EQS_label"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.operator'/>:
						<input type="text" name="filter_EQS_operator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.templetName'/>:
						<input type="text" name="filter_EQS_templetName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='colSearch.value'/>:
						<input type="text" name="filter_EQS_value"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(colSearch_search_form,colSearch_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(colSearch_search_form,colSearch_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="colSearch_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="colSearch_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="colSearch_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div> --%>
		<!-- <div style="width:49%;float:left"> -->
		<form>
		<div layoutH="36">
		<fieldset class="test" style="float:left;width:300px;">
		<legend>查询条件</legend>
		查询模板：<s:select list="templtList" listKey="templetName" listValue="templetName" name="searchTemplt"></s:select>
		<div id="colSearch_gridtable_div" >
			<input type="hidden" id="colSearch_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="colSearch_gridtable_addTile">
				<s:text name="colSearchNew.title"/>
			</label> 
			<label style="display: none"
				id="colSearch_gridtable_editTile">
				<s:text name="colSearchEdit.title"/>
			</label>
			<div id="load_colSearch_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="colSearch_gridtable"></table>
			<!--<div id="colSearchPager"></div>-->
			<div style="margin:5px">查询名称：<input id="colSearchName" size=10/> 保存模板<input id="colSearchCheckbox" type="checkbox"/></div>
		</div>
		</fieldset>
		<%-- <fieldset class="test" style="float:right;width:250px;overflow:auto;" >
		<legend>显示项</legend>
					显示模板：<s:select list="templtShowList" listKey="templetName" listValue="templetName" name="showTemplt"></s:select>
					<div class="sortDrag" >
						<s:iterator value="colList" status="it">
							
						<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">
						<input id="${col}" type="checkbox" name="columnCheckBox" <s:if test="hidden=='false'">checked</s:if> />
						<label><s:property value="title"/></label>
						 <span style="margin-right:10px;float:right">列宽：<input style="float:none" name="columnWidth" type="text" size="3" value="${value.width }"/></span>
						</div>
						</s:iterator>
				</div>
				显示名称：<input id="colShowName" size=10/> 保存模板<input id="colShowCheckbox" type="checkbox"/>
		</fieldset> --%>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button id="highSearchButton" type="Button"><s:text name="button.search" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			</form>
	</div>
	<%-- <div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="colSearch_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="colSearch_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="colSearch_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div> --%>
</div>