
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	function privilegeClassGridReload(){
		var urlString = "privilegeClassGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#privilegeClass_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var privilegeClassLayout;
			  var privilegeClassGridIdString="#privilegeClass_gridtable";
	
	jQuery(document).ready(function() { 
		privilegeClassLayout = makeLayout({
			baseName: 'privilegeClass', 
			tableIds: 'privilegeClass_gridtable'
		}, null);
var privilegeClassGrid = jQuery(privilegeClassGridIdString);
    privilegeClassGrid.jqGrid({
    	url : "privilegeClassGridList",
    	editurl:"privilegeClassGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'classCode',index:'classCode',align:'left',label : '<s:text name="privilegeClass.classCode" />',hidden:false,key:true,width:80},				
{name:'className',index:'className',align:'left',label : '<s:text name="privilegeClass.className" />',hidden:false,width:80},				
{name:'field',index:'field',align:'center',label : '<s:text name="privilegeClass.field" />',hidden:false,editable:true}				

        ],
        jsonReader : {
			root : "privilegeClasses", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'classCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="privilegeClassList.title" />',
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
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           fullGridEdit("#privilegeClass_gridtable");
           var dataTest = {"id":"privilegeClass_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("privilegeClass_gridtable");
      	 	setDataPriValue();
      	 	setTimeout(function(){
      	 		addFiledComplete();
      	 	},200);
       	} 

    });
    jQuery(privilegeClassGrid).jqGrid('bindKeys');
    
	
	jQuery("#selectDataPrivilege").click(function(){
		var dataprivalue = "";
		var allIds = jQuery("#privilegeClass_gridtable").jqGrid('getDataIDs');
		var sids = jQuery("#privilegeClass_gridtable").jqGrid('getGridParam','selarrrow');
		var colModel = jQuery("#privilegeClass_gridtable").jqGrid("getGridParam",'colModel');  
	    /* for(var i=0;i<sids.length;i++){
			var ret = jQuery("#privilegeClass_gridtable").jqGrid('getRowData',sids[i]);
			alert(ret['className']);
			dataprivalue += sids[i]+":"+ret['field']+",";
		} */
		var i=0;
		for(var i=0;i<allIds.length;i++){
			for(var s=0;s<sids.length;s++){
				if(allIds[i]==sids[s]){
					var fieldValue = jQuery("input[name=field]").eq(i).val();
			    	if(fieldValue!=""){
			    		dataprivalue += sids[s]+":"+fieldValue+",";
			    	}
				}
			}
		}
	    /* jQuery("input[name=field]").each(function(){
	    	var fieldValue = jQuery(this).val();
	    	if(fieldValue!=""){
	    		dataprivalue += sids[i]+":"+fieldValue+",";
	    	}
    		i++;
	    }); */
	    jQuery("#menu_dataPrivileg").val(dataprivalue);
	    $.pdialog.closeCurrent();
	});
	
	//privilegeClassLayout.resizeAll();
  	});
	function setDataPriValue(){
		var menu_dataPrivileg = jQuery("#menu_dataPrivileg").val();
		/* jQuery("#privilegeClass_gridtable").find("tr").each(function(){
			
		}); */
		if(menu_dataPrivileg!=null&&menu_dataPrivileg!=""){
			var menu_dataPrivilegArr = menu_dataPrivileg.split(",");
			for(var i=0;i<menu_dataPrivilegArr.length;i++){
				var mdp = menu_dataPrivilegArr[i];
				var mdpArr = mdp.split(":");
				jQuery("#privilegeClass_gridtable").jqGrid('setSelection',mdpArr[0]);
				var ind = jQuery("#privilegeClass_gridtable").jqGrid('getInd',mdpArr[0]);
				jQuery("input[name=field]").eq(ind-1).val(mdpArr[1]);
			}
		}
	}
	
	function addFiledComplete(){
		jQuery("input[name='field']","#privilegeClass_gridtable").each(function(){
			var id = jQuery(this).attr("id");
			jQuery("#"+id).autocomplete("autocompleteBySql",{
				width : 270,
				multiple : true,
				multipleSeparator: ";", 
				autoFill : false,
				matchContains : true,
				matchCase : true,
				dataType : 'json',
				parse : function(json) {
					var data = json.autocompleteResult;
					if (data == null || data == "") {
						var rows = [];
						rows[0] = {
							data : "没有结果",
							value : "",
							result : ""
						};
						return rows;
					} else {
						var rows = [];
						for ( var i = 0; i < data.length; i++) {
								
							rows[rows.length] = {
								data : data[i].tableName + ","
										+ data[i].colName ,
								value : data[i].tableName+"."+ data[i].colName,
								result : data[i].tableName+"."+ data[i].colName
							};
						}
						return rows;
					}
				},
				extraParams : {
					cloumns : "a.name,b.name",
					sql:"select a.name as tableName, b.name as colName from [sysobjects] a,[syscolumns] b where a.id = b.id and a.type='U'"
				},
				formatItem : function(row) {
					//return dropId(row);
					return row;
				},
				formatResult : function(row) {
					//return dropId(row);
					return row;
				}
			});
		});
	}
</script>

<div class="page">
<div id="privilegeClass_container">
			<div id="privilegeClass_layout-center"
				class="pane ui-layout-center">
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='privilegeClass.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='privilegeClass.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='privilegeClass.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='privilegeClass.subSystemId'/>:
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
									<button type="button" onclick="privilegeClassGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a id="selectDataPrivilege" class="addbutton" href="javaScript:" ><span>确定
					</span>
				</a>
				</li>
				<%-- <li><a id="privilegeClass_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="privilegeClass_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="privilegeClass_gridtable_div" layoutH="57"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:classCode;width:500;height:300">
			<input type="hidden" id="privilegeClass_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="privilegeClass_gridtable_addTile">
				<s:text name="privilegeClassNew.title"/>
			</label> 
			<label style="display: none"
				id="privilegeClass_gridtable_editTile">
				<s:text name="privilegeClassEdit.title"/>
			</label>
			<label style="display: none"
				id="privilegeClass_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="privilegeClass_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_privilegeClass_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="privilegeClass_gridtable"></table>
		<div id="privilegeClassPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="privilegeClass_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="privilegeClass_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="privilegeClass_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>