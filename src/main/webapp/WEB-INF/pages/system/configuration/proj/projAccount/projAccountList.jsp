
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var projAccountLayout;
			  var projAccountGridIdString="#projAccount_gridtable";
	jQuery(document).ready(function() { 
var projAccountGrid = jQuery(projAccountGridIdString);
    projAccountGrid.jqGrid({
    	url : "projAccountGridList",
    	editurl:"projAccountGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'projAcctId',index:'projAcctId',align:'center',label : '<s:text name="projAccount.projAcctId" />',hidden:false,key:true},				

        ],
        jsonReader : {
			root : "projAccounts", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'projAcctId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="projAccountList.title" />',
        height:400,
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"projAccount_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("projAccount_gridtable");
       	} 

    });
    jQuery(projAccountGrid).jqGrid('bindKeys');
    
	
	
	
	//projAccountLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="projAccount_container">
			<div id="projAccount_layout-center"
				class="pane ui-layout-center">
	<div class="pageContent">
<div class="panelBar">
			<ul class="toolBar">
				<li><a id="projAccount_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="projAccount_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="projAccount_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="projAccount_gridtable_div" layoutH="50" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"; height:500px;
			buttonBar="optId:projAcctId;width:500;height:500">
			<input type="hidden" id="projAccount_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="projAccount_gridtable_addTile">
				<s:text name="projAccountNew.title"/>
			</label> 
			<label style="display: none"
				id="projAccount_gridtable_editTile">
				<s:text name="projAccountEdit.title"/>
			</label>
			<label style="display: none"
				id="projAccount_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="projAccount_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
 <table id="projAccount_gridtable"></table>
		<div id="projAccountPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="projAccount_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="projAccount_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="projAccount_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>