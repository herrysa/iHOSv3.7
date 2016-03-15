
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
/* 	var linkInfoLayout;
	var linkInfoGridIdString="#linkInfo_gridtable";
	
	jQuery(document).ready(function() { 
		var linkInfoGrid = jQuery(linkInfoGridIdString);
    	linkInfoGrid.jqGrid({
    		url : "linkInfoGridList",
    		editurl:"linkInfoGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'infoId',index:'infoId',align:'center',label : '<s:text name="linkInfo.infoId" />',hidden:false,key:true},{name:'type',index:'type',align:'center',label : '<s:text name="linkInfo.type" />',hidden:false}        	],
        	jsonReader : {
				root : "linkInfoes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'infoId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="linkInfoList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"linkInfo_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("linkInfo_gridtable");
       		} 

    	});
    jQuery(linkInfoGrid).jqGrid('bindKeys');
  	}); */
  	function saveLinkInfoMessage() {
  		var middleLink = jQuery("#linkInfo_middleLink").val();
  		var cwLink = jQuery("#linkInfo_cwLink").val();
  		if(!middleLink && !cwLink) {
  			return;
  		}
  		jQuery("#linkInfo_form").submit();
  	}
  	
  	function resetLinkInfoFormInput() {
  		jQuery("#linkInfo_form").find("select").each(function() {
  			jQuery(this).val('');
  		});
  	}
  	function linkInfoFormCallBack(data) {
  		formCallBack(data);
  	}
  	jQuery(function() {
  		var middleDbLinkId = "${middleDbLinkId}";
  		var cwDbLinkId = "${cwDbLinkId}";
  		jQuery("#linkInfo_middleLink").val(middleDbLinkId);
  		jQuery("#linkInfo_cwLink").val(cwDbLinkId);
  	});
</script>

<div class="page">
	<div class="pageContent">
	<form action="saveLinkInfoMessage" method="post" id="linkInfo_form" 
		class="pageForm required-validate" onsubmit="return validateCallback(this,linkInfoFormCallBack);">
		<div class="pageFormContent" layoutH="56">
			<div style="margin: 175px;">
				<div class="unit">
					<label><s:text name="linkInfo.middleLink"></s:text>:</label>
					<s:select id="linkInfo_middleLink" list="dataSources" listKey="dataSourceDefineId" listValue="dataSourceName" emptyOption="true" name="middleLink" class="required"/>
				</div>
				<div class="unit">
					<label><s:text name="linkInfo.cwLink"></s:text>:</label>
					<s:select id="linkInfo_cwLink" list="dataSources" listKey="dataSourceDefineId" listValue="dataSourceName" emptyOption="true" name="cwLink" class="required"/>
				</div>
			</div>
		</div>
		<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="saveLinkInfoMessage()"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="resetLinkInfoFormInput()"><s:text name="button.clear" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</form>
	</div>
	<%-- <div id="linkInfo_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="linkInfo_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='linkInfo.infoId'/>:
						<input type="text" name="filter_EQS_infoId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='linkInfo.dataSource'/>:
						<input type="text" name="filter_EQS_dataSource"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='linkInfo.type'/>:
						<input type="text" name="filter_EQS_type"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(linkInfo_search_form,linkInfo_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(linkInfo_search_form,linkInfo_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="linkInfo_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="linkInfo_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="linkInfo_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="linkInfo_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="linkInfo_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="linkInfo_gridtable_addTile">
				<s:text name="linkInfoNew.title"/>
			</label> 
			<label style="display: none"
				id="linkInfo_gridtable_editTile">
				<s:text name="linkInfoEdit.title"/>
			</label>
			<div id="load_linkInfo_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="linkInfo_gridtable"></table>
			<!--<div id="linkInfoPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="linkInfo_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="linkInfo_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="linkInfo_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div> --%>
</div>