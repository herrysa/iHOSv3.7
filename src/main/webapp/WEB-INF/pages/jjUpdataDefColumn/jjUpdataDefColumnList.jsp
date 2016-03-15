
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function jjUpdataDefColumnGridReload(){
		var urlString = "jjUpdataDefColumnGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#jjUpdataDefColumn_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var jjUpdataDefColumnLayout;
			  var jjUpdataDefColumnGridIdString="#jjUpdataDefColumn_gridtable";
	
	jQuery(document).ready(function() { 
		jjUpdataDefColumnLayout = makeLayout({
			baseName: 'jjUpdataDefColumn', 
			tableIds: 'jjUpdataDefColumn_gridtable'
		}, null);
var jjUpdataDefColumnGrid = jQuery(jjUpdataDefColumnGridIdString);
    jjUpdataDefColumnGrid.jqGrid({
    	url : "jjUpdataDefColumnGridList",
    	editurl:"jjUpdataDefColumnGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'columnId',index:'columnId',align:'center',label : '<s:text name="jjUpdataDefColumn.columnId" />',hidden:true,key:true,formatter:'integer',edittype:"text",editable:true},				
{name:'columnName',index:'columnName',align:'left',label : '<s:text name="jjUpdataDefColumn.columnName" />',hidden:false,sortable:true,width:100},				
{name:'columnType',index:'columnType',align:'center',label : '<s:text name="jjUpdataDefColumn.columnType" />',hidden:false,width:100},				
{name:'title',index:'title',align:'center',label : '<s:text name="jjUpdataDefColumn.title" />',hidden:false,edittype:"text",editable:true},				
{name:'formula',index:'formula',align:'center',label : '<s:text name="jjUpdataDefColumn.formula" />',hidden:false,edittype:"text",editable:true,width:200},				
{name:'type',index:'type',align:'center',label : '<s:text name="jjUpdataDefColumn.type" />',hidden:false,width:80,formatter : 'select',	edittype : 'select',editoptions : {dataEvents:[{type:'change',fn: function(e) { checkByType(this); }}],value : '手工:手工;计算:计算;其他:其他'},editable:true},
{name:'order',index:'order',align:'center',label : '<s:text name="jjUpdataDefColumn.order" />',hidden:false,	editable:true,edittype : 'text',width:80},
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="jjUpdataDefColumn.disable" />',hidden:false,formatter:'checkbox',edittype : 'checkbox',editable:true,width:80}				

        ],
        jsonReader : {
			root : "jjUpdataDefColumns", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'order',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="jjUpdataDefColumnList.title" />',
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
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           
           var dataTest = {"id":"jjUpdataDefColumn_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("jjUpdataDefColumn_gridtable");
      	   fullGridEdit(jjUpdataDefColumnGrid);
       	} 

    });
    jQuery(jjUpdataDefColumnGrid).jqGrid('bindKeys');
    
	
	
	
	//jjUpdataDefColumnLayout.resizeAll();
  	});
	
	function checkByType(obj){
		var type = jQuery(obj).val();
		if(type=="计算"){
			jQuery(obj).parent().parent().find("input[name=formula]").attr("readonly",false);
		}else{
			jQuery(obj).parent().parent().find("input[name=formula]").val("");
			jQuery(obj).parent().parent().find("input[name=formula]").attr("readonly",true);
		}
	}
	
	function initDefColumn(){
		$.ajax({
		    url: 'initDefColumn',
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		    	jQuery("#jjUpdataDefColumn_gridtable").trigger("reloadGrid");
		    }
		});
	}
	function saveDefColumn(){
		var columnId = "",title="",formula="",type="",disorder="",disable="";
  		jQuery("input[name=columnId]").each(function(){
  			columnId += jQuery(this).val()+",";
  		});
  		jQuery("input[name=title]").each(function(){
  			title += jQuery(this).val()+",";
  		});
  		jQuery("input[name=formula]").each(function(){
  			formula += jQuery(this).val()+",";
  		});
  		jQuery("select[name=type]").each(function(){
  			type += jQuery(this).val()+",";
  		});
  		jQuery("input[name=disabled]").each(function(){
  			disable += jQuery(this).attr("checked")+",";
  		});
  		jQuery("input[name=order]").each(function(){
  			disorder += jQuery(this).val()+",";
  		});
  		columnId += "end";
  		title += "end";
  		formula += "end";
  		type += "end";
  		disorder += "end";
  		disable += "end";
  		var url="saveDefColumn";
  		url=encodeURI(url);
  		$.ajax({
		    url: url,
		    type: 'post',
		    data:{columnIdStr:columnId,titleStr:title,formulaStr:formula,typeStr:type,disorderStr:disorder,disableStr:disable,navTabId:'jjUpdataDefColumn_gridtable'},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        formCallBack(data);
		    }
		});
	}
</script>

<div class="page">
<div id="jjUpdataDefColumn_container">
			<div id="jjUpdataDefColumn_layout-center"
				class="pane ui-layout-center">
	<%--<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='jjUpdataDefColumn.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='jjUpdataDefColumn.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='jjUpdataDefColumn.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='jjUpdataDefColumn.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="jjUpdataDefColumnGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>--%>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" href="javaScript:initDefColumn()" ><span>初始化
					</span>
				</a>
				</li>
				
				<li><a id="jjUpdataDefColumn_gridtable_del" class="delbutton"  href="javaScript:"
					><span><s:text name="button.delete" />
					</span>
				</a>
				</li>
				<li><a class="delbutton"  href="javaScript:saveDefColumn()"><span><s:text name="button.save" /></span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="jjUpdataDefColumn_gridtable_div" layoutH="56"
			class="grid-wrapdiv"
			buttonBar="optId:columnId;width:500;height:300">
			<input type="hidden" id="jjUpdataDefColumn_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="jjUpdataDefColumn_gridtable_addTile">
				<s:text name="jjUpdataDefColumnNew.title"/>
			</label> 
			<label style="display: none"
				id="jjUpdataDefColumn_gridtable_editTile">
				<s:text name="jjUpdataDefColumnEdit.title"/>
			</label>
			<label style="display: none"
				id="jjUpdataDefColumn_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="jjUpdataDefColumn_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_jjUpdataDefColumn_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="jjUpdataDefColumn_gridtable"></table>
		<div id="jjUpdataDefColumnPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="jjUpdataDefColumn_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="jjUpdataDefColumn_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="jjUpdataDefColumn_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>