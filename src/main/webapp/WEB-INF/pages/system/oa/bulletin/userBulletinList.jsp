
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var userBulletinLayout;
			  var userBulletinGridIdString="#userBulletin_gridtable";
	
	jQuery(document).ready(function() { 
		userBulletinLayout = makeLayout({
			baseName: 'userBulletin', 
			tableIds: 'userBulletin_gridtable'
		}, null);
		var gridUrl = "bulletinGridList?group_on=OR&filter_EQS_secretLevel=全院&filter_EQS_department=${currentUser.person.department.name}";
		gridUrl = encodeURI(gridUrl);
var userBulletinGrid = jQuery(userBulletinGridIdString);
    userBulletinGrid.jqGrid({
    	url : gridUrl,
    	editurl:"bulletinGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'bulletinId',index:'bulletinId',align:'left',label : '<s:text name="bulletin.bulletinId" />',hidden:true,key:true,formatter:'integer'},				
{name:'subject',index:'subject',align:'left',label : '<s:text name="bulletin.subject" />',hidden:false,width:300},				
{name:'secretLevel',index:'secretLevel',align:'left',label : '<s:text name="bulletin.secretLevel" />',hidden:false,width:80},
{name:'department',index:'department',align:'left',label : '<s:text name="bulletin.department" />',hidden:false,width:80},
{name:'createTime',index:'createTime',align:'center',label : '<s:text name="bulletin.createTime" />',hidden:false,formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s',newformat:"Y-m-d H:i:s"},width:150},				
{name:'creator',index:'creator',align:'left',label : '<s:text name="bulletin.creator" />',hidden:false,width:80}				
        ],
        jsonReader : {
			root : "bulletins", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'bulletinId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="userBulletinList.title" />',
        height:300,
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           reFormatColumnData(this);
           var dataTest = {"id":"userBulletin_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("userBulletin_gridtable");
       	} 

    });
    jQuery(userBulletinGrid).jqGrid('bindKeys');
    
	
	
	
	//userBulletinLayout.resizeAll();
  	});
	function preViewuserBulletin(){
		var sid = jQuery("#userBulletin_gridtable").jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
			alertMsg.error("");
			return;
		}
	    if(sid.length>1){
			alertMsg.error("");
			return;
		}else{
			var url = "showuserBulletin?bulletinId="+ sid;
			var winTitle="公告预览";
			//alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'preViewuserBulletin', winTitle, {mask:false,width:850,height:600});　
		}
	}
	function reFormatColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var data = ret[i]["subject"];
	    		 setCellText(grid,id,'subject','<a style="color:blue;text-decoration:underline;cursor:pointer;"  onclick="showUserBulletin(\'showBulletin?bulletinId='+id+'\',\''+data+'\')" target="dialog" width="880" height="600">'+data+"</a>")
	    	 }
	    }
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
	function showUserBulletin(url,title){
		$.pdialog.open(url, 'preViewUserBulletin', "公告", {mask:false,width:880,height:600});　
	}
</script>

<div class="page">
<div id="userBulletin_container">
			<div id="userBulletin_layout-center"
				class="pane ui-layout-center">
	<div class="pageContent">
<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="userBulletin_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="userBulletin_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="userBulletin_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:preViewuserBulletin()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			</ul>
		</div> --%>
		<div id="userBulletin_gridtable_div" layoutH="32"
			class="grid-wrapdiv"
			buttonBar="optId:userBulletinId;width:850;height:600">
			<input type="hidden" id="userBulletin_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="userBulletin_gridtable_addTile">
				<s:text name="userBulletinNew.title"/>
			</label> 
			<label style="display: none"
				id="userBulletin_gridtable_editTile">
				<s:text name="userBulletinEdit.title"/>
			</label>
			<label style="display: none"
				id="userBulletin_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="userBulletin_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_userBulletin_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="userBulletin_gridtable"></table>
		<div id="userBulletinPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="userBulletin_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="userBulletin_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="userBulletin_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>