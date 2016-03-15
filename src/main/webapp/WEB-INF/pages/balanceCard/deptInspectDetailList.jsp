
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
    var jjScoreDecimalPlaces = "${jjScoreDecimalPlaces}";
    if(!jjScoreDecimalPlaces||jjScoreDecimalPlaces>4){
    	jjScoreDecimalPlaces = 2;
    }
    jjScoreDecimalPlaces = parseInt(jjScoreDecimalPlaces);
	function deptInspectGridReload(){
		var urlString = "deptInspectGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#deptInspectDetail_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectLayout;
	var inspectTemplId = "${requestScope.inspectTemplId}";
	var deptInspectGridIdString="#deptInspectDetail_gridtable";
	
	jQuery(document).ready(function() { 
		/* deptInspectLayout = makeLayout({
			baseName: 'deptInspect', 
			tableIds: 'deptInspectDetail_gridtable'
		}, null); */
var deptInspectGrid = jQuery(deptInspectGridIdString);
    deptInspectGrid.jqGrid({
    	url : "deptInspectGridList?inspectTemplId="+inspectTemplId,
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:true,key:true},				
{name:'org.orgname',index:'org.orgname',width:'145px',align:'left',label : '<fmt:message key="hisOrg.orgName" />',hidden:false},				
{name:'department.name',index:'department.name',width:'145px',align:'left',label : '<s:text name="deptInspect.department" />',hidden:false},				
{name:'department.internalCode',index:'department.internalCode',width:'145px',align:'left',label : '<s:text name="department.internalCode" />',hidden:true},				
{name:'kpiItemName',index:'kpiItemName',width:'145px',align:'left',label : '<s:text name="kpiItem.keyName" />',hidden:false,sortable:false},	
{name:'tValue',index:'tValue',width:'140px',align:'right',label : '<s:text name="deptInspect.tValue" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},				
{name:'aValue',index:'aValue',width:'140px',align:'right',label : '<s:text name="deptInspect.aValue" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},				
{name:'dscore',index:'dscore',width:'143px',align:'right',label : '<s:text name="deptInspect.dscore" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},				
{name:'weight',index:'weight',width:'145px',align:'right',label : '<s:text name="deptInspect.weight" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},				
{name:'score',index:'score',width:'145px',align:'right',label : '<s:text name="deptInspect.score" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: jjScoreDecimalPlaces, defaultValue: '0.00'}},				
{name:'money1',index:'money1',width:'145px',align:'right',label : '<s:text name="deptInspect.money1" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},				
{name:'money2',index:'money2',width:'145px',align:'right',label : '<s:text name="deptInspect.money2" />',hidden:false,formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'}},				
{name:'inspectdept.name',width:'145px',index:'inspectdept.name',align:'left',label : '<s:text name="deptInspect.inspectdept" />',hidden:false},				
{name:'inspectOrg.orgname',width:'145px',index:'inspectOrg.orgname',align:'left',label : '<s:text name="deptInspect.inspectOrg" />',hidden:false},				
{name:'state',index:'state',width:'145px',align:'center',label : '<s:text name="deptInspect.state" />',hidden:false,formatter : 'select',	edittype : 'select',editoptions : {value : '0:<s:text name="deptInspect.state.new"/>;1:<s:text name="deptInspect.state.used"/>;2:<s:text name="deptInspect.state.confirmed"/>;3:<s:text name="deptInspect.state.checked"/>;4:<s:text name="deptInspect.state.audited"/>'}}			

        ],
        jsonReader : {
			root : "deptInspects", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'department.internalCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="deptInspectList.title" />',
        height:'278',
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
           var dataTest = {"id":"deptInspectDetail_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   /* makepager("deptInspectDetail_gridtable");
      	   gridResize(null,"deptInspectMain_layout-south","deptInspectDetail_gridtable"); */
       	} 

    });
    jQuery(deptInspectGrid).jqGrid('bindKeys');
    
	
	
	
	//deptInspectLayout.resizeAll();
  	});
</script>
<div class="page">
	<div class="pageContent">
		<div id="deptInspectDetail_gridtable_div" extraHeight=147
			tablecontainer="deptInspectMain_layout-south" class="grid-wrapdiv">
			<input type="hidden" id="deptInspectDetail_gridtable_navTabId"
				value="${sessionScope.navTabId}">
			<label
				style="display: none" id="deptInspectDetail_gridtable_addTile">
				<s:text name="deptInspectNew.title" />
			</label> <label style="display: none"
				id="deptInspectDetail_gridtable_editTile"> <s:text
					name="deptInspectEdit.title" />
			</label> <label style="display: none"
				id="deptInspectDetail_gridtable_selectNone"> <s:text
					name='list.selectNone' />
			</label> <label style="display: none"
				id="deptInspectDetail_gridtable_selectMore"> <s:text
					name='list.selectMore' />
			</label>
			<div id="load_deptInspectDetail_gridtable"
				class='loading ui-state-default ui-state-active'
				style="display: none"></div>
			<table id="deptInspectDetail_gridtable"></table>
			<div id="deptInspectPager"></div>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="deptInspectDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptInspectDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="deptInspectDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
		<!-- <div id="deptInspect_container">
			<div id="deptInspect_layout-center"
				class="pane ui-layout-center"> -->
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='deptInspect.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='deptInspect.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='deptInspect.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='deptInspect.subSystemId'/>:
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
									<button type="button" onclick="deptInspectGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>






	<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="deptInspectDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="deptInspectDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="deptInspectDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div> --%>
		


<!-- </div>
</div> -->