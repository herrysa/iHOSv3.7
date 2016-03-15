<%@ include file="/common/taglibs.jsp"%>
<%-- <link rel="stylesheet" type="text/css" media="all"	href="<c:url value='/styles/simplicity/theme.css'/>" /> --%>
<menu:useMenuDisplayer name="Velocity" config="navbarMenu.vm"
	permissions="rolesAdapter">
	<ul class="nav">
		<c:if test="${empty pageContext.request.remoteUser}">
			<li class="active"><a href="<c:url value="/login"/>"
				class="current"><fmt:message key="login.title" /></a></li>
		</c:if>
		<menu:displayMenu name="MainMenu" />
		<menu:displayMenu name="UserMenu" />
		<menu:displayMenu name="AdminMenu" />
		<menu:displayMenu name="PersonDemo" />
		<menu:displayMenu name="DictionaryMaintain" />
		<menu:displayMenu name="Logout" />

		<menu:displayMenu name="TestMenu" />
		<!--Period-START-->
		<menu:displayMenu name="PeriodMenu" />
		<!--Period-END-->

		<!--DataSourceType-START-->
		<menu:displayMenu name="DataSourceTypeMenu" />
		<menu:displayMenu name="DataSourceDefineMenu" />
		<menu:displayMenu name="DataCollectionTaskDefineMenu" />
		<menu:displayMenu name="DataCollectionTaskMenu" />

		<!--DataSourceType-END-->





		<!--Person-START-->
		<menu:displayMenu name="PersonMenu" />
		<!--Person-END-->

		<!--Menu-START-->
		<menu:displayMenu name="MenuMenu" />


		<!--Menu-END-->
		<!--Department-START-->
		<menu:displayMenu name="DepartmentMenu" />
		<!--Department-END-->

		<!--CostUse-START-->
		<menu:displayMenu name="CostUseMenu" />
		<!--CostUse-END-->
		<!--PayinItem-START-->
		<menu:displayMenu name="PayinItemMenu" />
		<!--PayinItem-END-->


		<!--ChargeType-START-->
		<menu:displayMenu name="ChargeTypeMenu" />
		<!--ChargeType-END-->

		<!--CostItem-START-->
		<menu:displayMenu name="CostItemMenu" />
		<!--CostItem-END-->
		<!--ChargeItem-START-->
		<menu:displayMenu name="ChargeItemMenu" />
		<!--ChargeItem-END-->

		<!--DeptType-START-->
		<menu:displayMenu name="DeptTypeMenu" />
		<!--DeptType-END-->

		<!--Sourcepayin-START-->
		<menu:displayMenu name="SourcepayinMenu" />
		<!--Sourcepayin-END-->
		<!--Sourcecost-START-->
		<menu:displayMenu name="SourcecostMenu" />
		<!--Sourcecost-END-->

		<!--Search-START-->
		<menu:displayMenu name="SearchMenu" />
		<!--Search-END-->

		<menu:displayMenu name="QueryMenu" />
		<!--FormulaEntity-START-->
		<menu:displayMenu name="FormulaDefineMenu" />
		<!--FormulaEntity-END-->




		<!--InterLogger-START-->
		<%-- --%>
		<menu:displayMenu name="SecurityMenu" />
		<!--InterLogger-END-->

		<!--Deptmap-START-->
		<menu:displayMenu name="DeptmapMenu" />
		<!--Deptmap-END-->
		<!--Matetype-START-->
		<menu:displayMenu name="MatetypeMenu" />
		<!--Matetype-END-->
		<!--Acctcostmap-START-->
		<menu:displayMenu name="AcctcostmapMenu" />
		<!--Acctcostmap-END-->
	</ul>
</menu:useMenuDisplayer>
