
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainRepositoryGridReload(){
		var urlString = "trainRepositoryGridList";
		var code = jQuery("#search_trainRepository_code").val();
		var name = jQuery("#search_trainRepository_name").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=encodeURI(urlString);
		jQuery("#trainRepository_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var trainRepositoryLayout;
			  var trainRepositoryGridIdString="#trainRepository_gridtable";
	
	jQuery(document).ready(function() { 
		trainRepositoryLayout = makeLayout({
			baseName: 'trainRepository', 
			tableIds: 'trainRepository_gridtable'
		}, null);
var trainRepositoryGrid = jQuery(trainRepositoryGridIdString);
    trainRepositoryGrid.jqGrid({
    	url : "trainRepositoryGridList",
    	editurl:"trainRepositoryGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainRepository.id" />',hidden:true,key:true},	
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainRepository.code" />',hidden:false},	
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="trainRepository.name" />',hidden:false},
{name:'classNumber',index:'classNumber',width : 100,align:'left',label : '<s:text name="trainRepository.classNumber" />',hidden:false},				
{name:'contentClass',index:'contentClass',width : 100,align:'left',label : '<s:text name="trainRepository.contentClass" />',hidden:false},				
{name:'creditLine',index:'creditLine',width : 100,align:'left',label : '<s:text name="trainRepository.creditLine" />',hidden:false},				
{name:'expiryDate',index:'expiryDate',width : 100,align:'center',label : '<s:text name="trainRepository.expiryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'implementDate',index:'implementDate',width : 100,align:'center',label : '<s:text name="trainRepository.implementDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'issueDate',index:'issueDate',width : 100,align:'center',label : '<s:text name="trainRepository.issueDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'issueUnit',index:'issueUnit',width : 100,align:'left',label : '<s:text name="trainRepository.issueUnit" />',hidden:false},				
{name:'referenceNumber',index:'referenceNumber',width : 100,align:'left',label : '<s:text name="trainRepository.referenceNumber" />',hidden:false},				
{name:'timeliness',index:'timeliness',width : 100,align:'left',label : '<s:text name="trainRepository.timeliness" />',hidden:false},				
{name:'type',index:'type',width : 100,align:'left',label : '<s:text name="trainRepository.type" />',hidden:false},				
{name:'remark',index:'remark',width : 100,align:'left',label : '<s:text name="trainRepository.remark" />',hidden:false}
        ],
        jsonReader : {
			root : "trainRepositories", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainRepositoryList.title" />',
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
			 gridContainerResize('trainRepository','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"trainRepository_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("trainRepository_gridtable");
       	} 

    });
    jQuery(trainRepositoryGrid).jqGrid('bindKeys');
    
	
    jQuery("#trainRepository_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editTrainRepository?popup=true&navTabId=trainRepository_gridtable";
		var winTitle='<s:text name="trainRepositoryNew.title"/>';
		$.pdialog.open(url,'addTrainRepository',winTitle, {mask:true,width : 700,height : 350});
	}); 
     jQuery("#trainRepository_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#trainRepository_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="trainRepositoryEdit.title"/>';
		var url = "editTrainRepository?popup=true&id="+sid+"&navTabId=trainRepository_gridtable";
		$.pdialog.open(url,'editTrainRepository',winTitle, {mask:true,width : 700,height : 350});
	}); 
	
	//trainRepositoryLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="trainRepository_container">
			<div id="trainRepository_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="trainRepository_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainRepository_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainRepository.code'/>:
						 	<input type="text"  id="search_trainRepository_code" />
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainRepository.name'/>:
						 	<input type="text"  id="search_trainRepository_name" />
						</label>	
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainRepositoryGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainRepositoryGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="trainRepository_buttonBar">
			<ul class="toolBar">
				<li><a id="trainRepository_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="trainRepository_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="trainRepository_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="trainRepository_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainRepository_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainRepository_gridtable_addTile">
				<s:text name="trainRepositoryNew.title"/>
			</label> 
			<label style="display: none"
				id="trainRepository_gridtable_editTile">
				<s:text name="trainRepositoryEdit.title"/>
			</label>
			<label style="display: none"
				id="trainRepository_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainRepository_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainRepository_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainRepository_gridtable"></table>
		<div id="trainRepositoryPager"></div>
</div>
	</div>
	<div class="panelBar" id="trainRepository_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainRepository_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainRepository_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainRepository_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>