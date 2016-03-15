<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>

<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var ${pojoNameLower}Layout;
	var ${pojoNameLower}GridIdString="#${pojoNameLower}_gridtable";
	
	jQuery(document).ready(function() { 
		var ${pojoNameLower}Grid = jQuery(${pojoNameLower}GridIdString);
    	${pojoNameLower}Grid.jqGrid({
    		url : "${pojoNameLower}GridList",
    		editurl:"${pojoNameLower}GridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			<#list pojo.getAllPropertiesIterator() as field>
				<#if !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
				{name:'${field.name}',index:'${field.name}',align:'center',label : '<s:text name="${pojoNameLower}.${field.name}" />',hidden:false<#rt/><#if field.equals(pojo.identifierProperty)><#t/>,key:true</#if><#if field.value.typeName == "java.util.Date"><#t/>,formatter:'date'</#if><#if field.value.typeName == "boolean" || field.value.typeName == "java.lang.Boolean"><#t/>,formatter:'checkbox'</#if><#if  field.value.typeName == "float" || field.value.typeName == "double" ||  field.value.typeName == "java.lang.Float" || field.value.typeName == "java.lang.Double" || field.value.typeName == "java.math.BigDecimal"><#t/>,formatter:'number'</#if><#if  field.value.typeName == "int" || field.value.typeName == "long" ||  field.value.typeName == "short" || field.value.typeName == "java.lang.Integer" || field.value.typeName == "java.lang.Long" || field.value.typeName == "java.lang.Short"><#t/>,formatter:'integer'</#if><#t/>}<#if field_has_next>,</#if>
				</#if>
			</#list>    
        	],
        	jsonReader : {
				root : "${util.getPluralForWord(pojoNameLower)}", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: '${pojo.identifierProperty.name}',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="${pojoNameLower}List.title" />',
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
           	var dataTest = {"id":"${pojoNameLower}_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("${pojoNameLower}_gridtable");
       		} 

    	});
    jQuery(${pojoNameLower}Grid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="${pojoNameLower}_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="${pojoNameLower}_search_form" >
					<#list pojo.getAllPropertiesIterator() as field>
					<label style="float:none;white-space:nowrap" >
						<s:text name='${pojoNameLower}.${field.name}'/>:
						<input type="text" name="filter_EQS_${field.name}"/>
					</label>
					</#list>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(${pojoNameLower}_search_form,${pojoNameLower}_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(${pojoNameLower}_search_form,${pojoNameLower}_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="${pojoNameLower}_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="${pojoNameLower}_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="${pojoNameLower}_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="${pojoNameLower}_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${pojoNameLower}_gridtable_navTabId" value="${'$'}{sessionScope.navTabId}">
			<label style="display: none" id="${pojoNameLower}_gridtable_addTile">
				<s:text name="${pojoNameLower}New.title"/>
			</label> 
			<label style="display: none"
				id="${pojoNameLower}_gridtable_editTile">
				<s:text name="${pojoNameLower}Edit.title"/>
			</label>
			<div id="load_${pojoNameLower}_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${pojoNameLower}_gridtable"></table>
			<!--<div id="${pojoNameLower}Pager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${pojoNameLower}_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${pojoNameLower}_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${pojoNameLower}_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>