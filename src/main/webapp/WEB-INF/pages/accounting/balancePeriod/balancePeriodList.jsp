
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function balancePeriodGridReload(){
		var urlString = "balancePeriodGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#balancePeriod_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var balancePeriodLayout;
			  var balancePeriodGridIdString="#balancePeriod_gridtable";
	
	jQuery(document).ready(function() { 
		balancePeriodLayout = makeLayout({
			baseName: 'balancePeriod', 
			tableIds: 'balancePeriod_gridtable'
		}, null);
var balancePeriodGrid = jQuery(balancePeriodGridIdString);
    balancePeriodGrid.jqGrid({
    	url : "balancePeriodGridList",
    	editurl:"balancePeriodGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'balancePeriodId',index:'balancePeriodId',align:'center',label : '<s:text name="balancePeriod.balancePeriodId" />',hidden:false,key:true},				
{name:'lend',index:'lend',align:'center',label : '<s:text name="balancePeriod.lend" />',hidden:false,formatter:'number'},				
{name:'loan',index:'loan',align:'center',label : '<s:text name="balancePeriod.loan" />',hidden:false,formatter:'number'},				
{name:'periodMonth',index:'periodMonth',align:'center',label : '<s:text name="balancePeriod.periodMonth" />',hidden:false}				

        ],
        jsonReader : {
			root : "balancePeriods", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'balancePeriodId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="balancePeriodList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"balancePeriod_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("balancePeriod_gridtable");
       	} 

    });
    jQuery(balancePeriodGrid).jqGrid('bindKeys');
    
	
	
	
	//balancePeriodLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="balancePeriod_container">
			<div id="balancePeriod_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='balancePeriod.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='balancePeriod.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='balancePeriod.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='balancePeriod.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="balancePeriodGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a id="balancePeriod_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="balancePeriod_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="balancePeriod_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="balancePeriod_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:balancePeriodId;width:500;height:300">
			<input type="hidden" id="balancePeriod_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="balancePeriod_gridtable_addTile">
				<s:text name="balancePeriodNew.title"/>
			</label> 
			<label style="display: none"
				id="balancePeriod_gridtable_editTile">
				<s:text name="balancePeriodEdit.title"/>
			</label>
			<label style="display: none"
				id="balancePeriod_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="balancePeriod_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_balancePeriod_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="balancePeriod_gridtable"></table>
		<div id="balancePeriodPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="balancePeriod_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="balancePeriod_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="balancePeriod_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>