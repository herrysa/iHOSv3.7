<#assign pojoNameLower = pojo.shortName.substring(0,1).toLowerCase()+pojo.shortName.substring(1)>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
    "http://struts.apache.org/dtds/struts-2.0.dtd">

<struts>


	<package name="${pojoNameLower}" extends="default"	namespace="/">

		<action name="${pojoNameLower}List" class="${basepackage}.webapp.action.${pojo.shortName}PagedAction">
			<result name="input">/WEB-INF/pages/${pojoNameLower}/${pojoNameLower}List.jsp</result>
			<result name="success">/WEB-INF/pages/${pojoNameLower}/${pojoNameLower}List.jsp</result>
		</action>
		<action name="${pojoNameLower}GridList" class="${basepackage}.webapp.action.${pojo.shortName}PagedAction"		method="${pojoNameLower}GridList">
			<result type="json">
				<param name="includeProperties">${util.getPluralForWord(pojoNameLower)}.*,records,total,page</param>
				<param name="noCache">true</param>
				<param name="ignoreHierarchy">false</param>
			</result>
		</action>
		<action name="${pojoNameLower}GridEdit" class="${basepackage}.webapp.action.${pojo.shortName}PagedAction"			method="${pojoNameLower}GridEdit">
			<result name="success" type="json">
                <param name="includeProperties">message,statusCode,callbackType,navTabId</param>
                   <param name="noCache">true</param>
				 <param name="ignoreHierarchy">false</param>
            </result>
		</action>
		<!-- called when a record is clicked on the grid -->
		<action name="edit${pojo.shortName}" class="${basepackage}.webapp.action.${pojo.shortName}PagedAction"			method="edit">
			<result name="success">/WEB-INF/pages/${pojoNameLower}/${pojoNameLower}Form.jsp</result>
			<result name="input">/WEB-INF/pages/${pojoNameLower}/${pojoNameLower}Form.jsp</result>
		</action>

		<!-- called when clicking save on the edit form -->
		<action name="save${pojo.shortName}" class="${basepackage}.webapp.action.${pojo.shortName}PagedAction"			method="save">
			<result name="success" type="json">
                <param name="includeProperties">message,statusCode,callbackType,navTabId</param>
                   <param name="noCache">true</param>
				 <param name="ignoreHierarchy">false</param>
            </result>
		</action>

	</package>

</struts>
