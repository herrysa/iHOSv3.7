
# -- ${pojo.shortName}-START
<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<#foreach field in pojo.getAllPropertiesIterator()>
<#if !c2h.isCollection(field) && !c2h.isManyToOne(field) && !c2j.isComponent(field)>
    <#lt/>${pojoNameLower}.${field.name}=${data.getFieldDescription(field.name)}
</#if>
</#foreach>


${pojoNameLower}New.title =New ${pojo.shortName} Information
${pojoNameLower}Edit.title =Edit ${pojo.shortName} Information
${pojoNameLower}List.title=${pojo.shortName} List



${pojoNameLower}.added=${pojo.shortName} has been added successfully.
${pojoNameLower}.updated=${pojo.shortName} has been updated successfully.
${pojoNameLower}.deleted=${pojo.shortName} has been deleted successfully.
# -- ${pojo.shortName}-END