
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function sysTableKindGridReload(){
		var urlString = "sysTableKindGridList";
		var name = jQuery("#sysTableKind_search_name").val();
		var code = jQuery("#sysTableKind_search_code").val();
		var sysFiled = jQuery("#sysTableKind_search_sysFiled").val();
		var disabled = jQuery("#sysTableKind_search_disabled").val();
		
		
		urlString=urlString+"?filter_LIKES_name="+name+"&filter_LIKES_code="+code;
		urlString=urlString+"&filter_EQB_sysFiled="+sysFiled+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#sysTableKind_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var sysTableKindLayout;
			  var sysTableKindGridIdString="#sysTableKind_gridtable";
	
	jQuery(document).ready(function() { 
var sysTableKindGrid = jQuery(sysTableKindGridIdString);
    sysTableKindGrid.jqGrid({
    	url : "sysTableKindGridList",
    	editurl:"sysTableKindGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="sysTableKind.id" />',hidden:true,key:true},
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="sysTableKind.code" />',hidden:false,highsearch:true},				
{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="sysTableKind.name" />',hidden:false,highsearch:true},				
{name:'sn',index:'sn',width : 60,align:'right',label : '<s:text name="sysTableKind.sn" />',hidden:false,formatter:'integer',highsearch:true},								
{name:'mainTable',index:'mainTable',width : 100,align:'left',label : '<s:text name="sysTableKind.mainTable" />',hidden:false,highsearch:true},
{name:'mainTablePK',index:'mainTablePK',width : 60,align:'left',label : '<s:text name="sysTableKind.mainTablePK" />',hidden:false,highsearch:true},
{name:'mainTablePKLength',index:'mainTablePKLength',width : 80,align:'right',label : '<s:text name="sysTableKind.mainTablePKLength" />',hidden:false,formatter:'integer',highsearch:true},
{name:'sysFiled',index:'sysFiled',width : 60,align:'center',label : '<s:text name="sysTableKind.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'disabled',index:'disabled',width : 60,align:'center',label : '<s:text name="sysTableKind.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="sysTableKind.remark" />',hidden:false,highsearch:true}

        ],
        jsonReader : {
			root : "sysTableKinds", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="sysTableKindList.title" />',
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
			 gridContainerResize('sysTableKind','div');
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"sysTableKind_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(sysTableKindGrid).jqGrid('bindKeys');
    
    jQuery("#sysTableKind_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		var url = "editSysTableKind?popup=true&navTabId=sysTableKind_gridtable";
		var winTitle='<s:text name="sysTableKindNew.title"/>';
		$.pdialog.open(url,'addSysTableKind',winTitle, {mask:true,width : 700,height : 300});
	}); 
     jQuery("#sysTableKind_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#sysTableKind_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="sysTableKindEdit.title"/>';
		var url = "editSysTableKind?popup=true&id="+sid+"&navTabId=sysTableKind_gridtable";
		$.pdialog.open(url,'editSysTableKind',winTitle, {mask:true,width : 700,height : 300});
	}); 
     jQuery("#sysTableKind_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/sysTableKindGridEdit?oper=del"
			var sid = jQuery("#sysTableKind_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#sysTableKind_gridtable").jqGrid('getRowData',rowId);
					
					if(row['sysFiled']=='Yes'){
						alertMsg.error("系统记录不能删除!");
						return;
					}
				}
				
				jQuery.ajax({
		             url: 'sysTableContentGridList?filter_INS_tableKind.id='+sid,
		             data: {},
		             type: 'post',
		             dataType: 'json',
		             async:false,
		             error: function(data){
		             },
		             success: function(data){
		             	if(data.sysTableContents&&data.sysTableContents.length>0){
		             		alertMsg.error("包含数据表的数据表类型不能删除!");
							return;
		             	}
		             	url = url+"&id="+sid+"&navTabId=sysTableKind_gridtable";
						alertMsg.confirm("确认删除？", {
							okCall : function() {
								$.post(url,function(data) {
									formCallBack(data);
								});
							}
						});
		             }
		         });
			}
		});
	
	//sysTableKindLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="sysTableKind_container">
			<div id="sysTableKind_layout-center"
				class="pane ui-layout-center">
			<div class="pageHeader" id="sysTableKind_pageHeader">
				<div class="searchBar">
				<div class="searchContent">
					<form id="sysTableKind_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableKind.code'/>:
      						 <input type="text" id="sysTableKind_search_code"/>
						</label>
				<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableKind.name'/>:
      						 <input type="text" id="sysTableKind_search_name"/>
						</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableKind.sysFiled'/>:
					 	<s:select id="sysTableKind_search_sysFiled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableKind.disabled'/>:
					 	<s:select id="sysTableKind_search_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="sysTableKindGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="sysTableKindGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="sysTableKind_buttonBar">
			<ul class="toolBar">
				<li><a id="sysTableKind_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="sysTableKind_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="sysTableKind_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<%-- <li>
     				<a class="settlebutton"  href="javaScript:setColShow('sysTableKind_gridtable','com.huge.ihos.hr.sysTables.model.SysTableKind')"><span><s:text name="button.setColShow" /></span></a>
   				 </li> --%>
			</ul>
		</div>
		<div id="sysTableKind_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:960;height:628">
			<input type="hidden" id="sysTableKind_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="sysTableKind_gridtable_addTile">
				<s:text name="sysTableKindNew.title"/>
			</label> 
			<label style="display: none"
				id="sysTableKind_gridtable_editTile">
				<s:text name="sysTableKindEdit.title"/>
			</label>
			<label style="display: none"
				id="sysTableKind_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="sysTableKind_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_sysTableKind_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="sysTableKind_gridtable"></table>
		<div id="sysTableKindPager"></div>
</div>
	</div>
	<div class="panelBar" id="sysTableKind_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="sysTableKind_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysTableKind_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="sysTableKind_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>