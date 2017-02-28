
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
function invMainInitBookStore() {
	var url = "${ctx}/invMainBookConfirm"
	var sid = jQuery("#book_store_gridtable").jqGrid('getGridParam',
			'selarrrow');
	//console.log(sid);
	if (sid == null || sid.length == 0) {
		alertMsg.error("请选择仓库。");
		return;
	} else {
		for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#book_store_gridtable").jqGrid('getRowData',rowId);
			if(row['isBook']=='Yes'){
				alertMsg.error("该仓库["+row['storeName']+"]已记账.不能重复记账!");
				return;
			}
		}
		url = url+"?id="+sid+"&navTabId=book_store_gridtable";
		alertMsg.confirm("确认记账？", {
			okCall : function() {
				jQuery.post(url, function(data) {
					formCallBack(data);
				});

			}
		});
	}
}
function invMainInitUnBookStore() {
	var url = "${ctx}/invMainUnBookConfirm"
	var sid = jQuery("#book_store_gridtable").jqGrid('getGridParam',
			'selarrrow');
	//console.log(sid);
	if (sid == null || sid.length == 0) {
		alertMsg.error("请选择仓库。");
		return;
	} else {
		for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#book_store_gridtable").jqGrid('getRowData',rowId);
			if(row['isBook']=='No'){
				alertMsg.error("该仓库["+row['storeName']+"]尚未记账.不能反记账!");
				return;
			}
		}
		url = url+"?id="+sid+"&navTabId=book_store_gridtable";
		alertMsg.confirm("确认反记账？", {
			okCall : function() {
				jQuery.post(url, function(data) {
					formCallBack(data);
				});

			}
		});
	}
}

function invMainBatchEndBook(){
	var url = "${ctx}/invMainBatchEndBook"
		alertMsg.confirm("确认结账？", {
			okCall : function() {
				jQuery.post(url, function(data) {
					formCallBack(data);
				});

			}
		});
}
	function bookStoreGridReload(){
		var urlString = "bookStoreGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#book_store_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	//var bookStoreLayout;
			  var bookStoreGridIdString="#book_store_gridtable";
	
	jQuery(document).ready(function() { 
		/* bookStoreLayout = makeLayout({
			baseName: 'store', 
			tableIds: 'book_store_gridtable'
		}, null); */
var bookStoreGrid = jQuery(bookStoreGridIdString);
    bookStoreGrid.jqGrid({
    	url : "bookStoreGridList?filter_EQS_orgCode="+"${fns:userContextParam('orgCode')}"+"&filter_EQB_leaf=true&filter_EQB_disabled=false",
    	editurl:"invMainBookConfirm",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="store.id" />',hidden:true,key:true},				
{name:'storeName',index:'storeName',align:'left',label : '<s:text name="store.storeName" />',hidden:false},				
{name:'isBook',index:'isBook',align:'center',label : '是否已记账',hidden:false,formatter:'checkbox'},				
/* {name:'level',index:'level',align:'center',label : '<s:text name="store.level" />',hidden:false,formatter:'integer'},				
{name:'lft',index:'lft',align:'center',label : '<s:text name="store.lft" />',hidden:false,formatter:'integer'},				
{name:'rgt',index:'rgt',align:'center',label : '<s:text name="store.rgt" />',hidden:false,formatter:'integer'},				
{name:'address',index:'address',align:'center',label : '<s:text name="store.address" />',hidden:false},				
{name:'cnCode',index:'cnCode',align:'center',label : '<s:text name="store.cnCode" />',hidden:false}, */				
{name:'startDate',index:'startDate',align:'center',label : '<s:text name="store.startDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="store.disabled" />',hidden:true,formatter:'checkbox'}
/* ,				
{name:'isLock',index:'isLock',align:'center',label : '<s:text name="store.isLock" />',hidden:false,formatter:'checkbox'},				
{name:'isPos',index:'isPos',align:'center',label : '<s:text name="store.isPos" />',hidden:false,formatter:'checkbox'},				
{name:'leaf',index:'leaf',align:'center',label : '<s:text name="store.leaf" />',hidden:false,formatter:'checkbox'},				
{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="store.orgCode" />',hidden:false},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="store.remark" />',hidden:false},				
{name:'storeCode',index:'storeCode',align:'center',label : '<s:text name="store.storeCode" />',hidden:false},				
{name:'storeType',index:'storeType',align:'center',label : '<s:text name="store.storeType" />',hidden:false},				
{name:'telephone',index:'telephone',align:'center',label : '<s:text name="store.telephone" />',hidden:false} */				

        ],
        jsonReader : {
			root : "stores", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="storeList.title" />',
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
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"book_store_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("book_store_gridtable");
       	} 

    });
    jQuery(bookStoreGrid).jqGrid('bindKeys');
    
	
	
	
	//bookStoreLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="book_store_container">
			<div id="book_store_layout-center"
				class="pane ui-layout-center">

	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<%-- <li><a id="book_store_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li> --%>
				<li><a id="book_store_gridtable_cus" class="confirmbutton"  href="javaScript:invMainInitBookStore();"><span>期初记账</span>
				</a>
				</li>
				<li><a id="book_store_gridtable_cus_unbook" class="delallbutton"  href="javaScript:invMainInitUnBookStore();"><span>期初反记账</span>
				</a>
				</li>
				<li><a id="book_store_gridtable_cus_batchEnd" class="delbutton"  href="javaScript:invMainBatchEndBook();"><span>期末结账</span>
				</a>
				</li>
			<%-- 	<li><a id="book_store_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="book_store_gridtable_div" layoutH="84"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="book_store_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="book_store_gridtable_addTile">
				<s:text name="storeNew.title"/>
			</label> 
			<label style="display: none"
				id="book_store_gridtable_editTile">
				<s:text name="storeEdit.title"/>
			</label>
			<label style="display: none"
				id="book_store_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="book_store_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_book_store_gridtable" class='loading ui-state-default ui-state-active' style="display: none;"></div>
 <table id="book_store_gridtable"></table>
		<div id="book_storePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="book_store_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="book_store_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="book_store_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>