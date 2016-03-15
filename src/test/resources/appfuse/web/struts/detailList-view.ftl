<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>

   var ${pojoNameLower}GridIdString="#${pojoNameLower}List";

			${pojoNameLower}grid = jQuery(${pojoNameLower}GridIdString), 
            lastSel = -1, 
            editingRowId = -1;
            ${pojoNameLower}grid.jqGrid({
            	url : "${pojoNameLower}Grid?${masterIdName}=${'$'}{${masterIdName}}",
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
    				root : "pageList", // (2)
    				page : "page",
    				total : "total",
    				records : "records", // (3)
    				repeatitems : false
    			// (4)
    			},
                rowNum:10,
                rowList:[5,10,20,50,100,200],
                pager: '#${pojoNameLower}Pager',
                gridview:true,
                rownumbers:true,
               // sortname: 'demoId',
                viewrecords: true,
               // sortorder: 'desc',
                caption:'<s:text name="${pojoNameLower}List.title" />',
                height:300,
                gridview:true,
                rownumbers:true,
               // loadui: "disable",
                multiselect: true,
				multiboxonly:true,
				shrinkToFit:false,
				autowidth:false,
                onSelectRow: function(rowid) {
					},
				 gridComplete:function(){
	               if(jQuery(this).getDataIDs().length>0){
	                  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);//默认选中第一行
	                }
	           	} 

            });
            jQuery(${pojoNameLower}grid).jqGrid('bindKeys');
            jQuery(${pojoNameLower}grid).jqGrid('navGrid','#${pojoNameLower}Pager',
            		{edit:false,add:false,del:false},
            		{},
            		{},
            		{},
            		{multipleSearch:true, multipleGroup:true}
            		);


