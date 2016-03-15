<!--${pojo.shortName}-START-->
<table name="${clazz.table.name}">
<#foreach field in pojo.getAllPropertiesIterator()>
<#if !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
    <#foreach column in field.getColumnIterator()>
        <column>${column.name}</column>
    </#foreach>
<#elseif c2h.isManyToOne(field)>
    <#foreach column in field.getColumnIterator()>
        <column>${column.name}</column>    
    </#foreach>
</#if>
</#foreach>
<#assign rows = 1 .. 10>
<#foreach num in rows>
    <row>
<#foreach field in pojo.getAllPropertiesIterator()>
<#if !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
    <#foreach column in field.getColumnIterator()>
        <value description="${column.name}"><#rt/>
        <#if field.equals(pojo.identifierProperty)>
            <#lt/>-${num}<#rt/>
        <#elseif field.equals(pojo.versionProperty)>
            <#if field.value.typeName.equals("timestamp")>
                2000-01-01 00:00:00<#t/>
            <#else>
                1<#rt/>
            </#if>
        <#else>
	            <#lt/>
	            <#if field.value.typeName.equals("java.util.Date")>
	            <![CDATA[${data.getTestValueForDbUnit(column)}]]><#t/>
	            <#else><#if field.value.typeName.equals("java.lang.String")><![CDATA[${data.getTestValueForDbUnit(column)}]]><#t/>
	           		 <#else><#if field.value.typeName.equals("java.math.BigDecimal")>0.000000<#t/>
	            	<#else>${data.getTestValueForDbUnit(column)}<#t/>
	            		</#if>
	            		</#if>
	            </#if>
	            <#rt/>
        </#if>
        <#lt/></value>
    </#foreach>
<#elseif c2h.isManyToOne(field)>
    <#foreach column in field.getColumnIterator()>
        <value description="${column.name}">-${num}</value>
    </#foreach>
</#if>
</#foreach>
    </row>
</#foreach>
    </table>
    <!--${pojo.shortName}-END-->
</dataset>
