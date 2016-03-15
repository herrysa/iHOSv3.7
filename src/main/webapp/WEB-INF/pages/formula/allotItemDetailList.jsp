<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="allotItemDetailList.title"/></title>
    <meta name="heading" content="<fmt:message key='allotItemDetailList.heading'/>"/>
    <meta name="menu" content="AllotItemDetailMenu"/>
    
    <script type="text/javascript">
    
    function refreshGridCurrentPage(){
    	var jq = jQuery('#allotItemDetail_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#allotItemDetail_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addDetailRecord(){
			var url = "editAllotItemDetail?popup=true&allotItemId=<s:property value='#parameters.allotItemId'/>";
			var winTitle='<fmt:message key="allotItemDetailNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editDetailRecord(){
	        var sid = jQuery("#allotItemDetail_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#mybuttondialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#mybuttondialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editAllotItemDetail?popup=true&allotItemDetailId=" + sid;
				var winTitle='<fmt:message key="allotItemDetailNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		jQuery(document).ready(function() { 
    	});
	</script>
</head>

<div class="page">
<div class="pageContent">
<div class="panelBar">
			<ul class="toolBar">
				<li><a id="allotItemDetail_gridtable_add" class="addbutton" external="true" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="allotItemDetail_gridtable_del" class="delbutton" external="true" href="javaScript:"
					 title="确定要删除吗?"><span>删除</span>
				</a>
				</li>
				<li><a id="allotItemDetail_gridtable_edit" class="changebutton" external="true" href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				<li style="float:right;"><a id="allotItem_gridtable_close" href="javaScript:reSizePane('south',700);"><span><fmt:message
								key="button.close" /></span></a>
				</li>
				
				<li class="line" style="float:right">line</li>
				<li style="float:right;" ><a id="allotItem_gridtable_fold" href="javaScript:reSizePane('south',0);"><span><fmt:message
								key="button.fold" /></span></a>
				</li>
				<li class="line" style="float:right">line</li>
				<li style="float:right"><a id="allotItem_gridtable_unfold" href="javaScript:closeSouth();"><span><fmt:message
								key="button.unfold" /></span></a>
				</li>
			</ul>
		</div>
<div id="allotItemDetail_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="width:550;height:350">
			<input type="hidden" id="allotItemDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="allotItemDetail_gridtable_addTile">
				<fmt:message key="userNew.title"/>
			</label> 
			<label style="display: none"
				id="allotItemDetail_gridtable_editTile">
				<fmt:message key="userNew.title"/>
			</label>
			<label style="display: none"
				id="allotItemDetail_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="allotItemDetail_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="allotItemDetailGridEdit"/> 
<s:url id="remoteurl" action="allotItemDetailGridList">
<s:param name="allotItemId" value="#parameters.allotItemId"></s:param>

</s:url> 
<%-- <div align="left">
	<sj:submit id="addDetail_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addDetailRecord();"/>
	<sj:submit id="editSelectRowDetail_button" value="%{getText('button.edit')}"  button="true" onclick="editDetailRecord();"/>
</div> --%>
<div id="load_allotItemDetail_gridtable" class='loading ui-state-default ui-state-active'></div>
 <sjg:grid 
    	id="allotItemDetail_gridtable" 
    	dataType="json" 
    	gridModel="allotItemDetails"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="right"
		navigator="false"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		draggable="true"
  		autowidth="true"
  		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="allotItemDetailId" 
    	    search="false" 
    	    index="allotItemDetailId" 
    	    title="%{getText('allotItemDetail.allotItemDetailId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="costRatio" 
 	    index="costRatio" 
 	    title="%{getText('allotItemDetail.costRatiox')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="principle.allotPrincipleName" 
 	    index="principle.allotPrincipleName" 
 	    title="%{getText('allotItemDetail.principle')}" 
 	    /> 	    
   <sjg:gridColumn 
 	    name="bakPrinciple.allotPrincipleName" 
 	    index="bakPrinciple.allotPrincipleName" 
 	    title="%{getText('allotItemDetail.backPrinciple')}" 
 	    /> 	    
    <sjg:gridColumn 
 	    name="realPrinciple.allotPrincipleName" 
 	    index="realPrinciple.allotPrincipleName" 
 	    title="%{getText('allotItemDetail.realPrinciple')}" 
 	    />	    
 	    
   <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('allotItemDetail.remark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid>
</div>
	<div id="allotItem_gridtable_footBar" class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="allotItem_gridtable_totals"></label>条</span>
		</div>
		
		<div id="allotItem_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	 </div>
