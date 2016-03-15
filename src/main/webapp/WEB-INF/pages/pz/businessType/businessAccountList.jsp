
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	
</script>

<div id="${random}businessAccount_gridtable_div" class="grid-wrapdiv"
	layoutH=120 buttonBar="width:425;height:350;optId:businessId;">
	<input type="hidden" id="${random}businessAccount_gridtable_navTabId"
		value="${sessionScope.navTabId}"> <label style="display: none"
		id="${random}businessAccount_gridtable_addTile"> <s:text
			name="businessAccountNew.title" />
	</label> <label style="display: none"
		id="${random}businessAccount_gridtable_editTile"> <s:text
			name="businessAccountEdit.title" />
	</label>
	<div id="load_${random}businessAccount_gridtable"
		class='loading ui-state-default ui-state-active' style="display: none"></div>
	<table id="${random}businessAccount_gridtable"></table>
</div>
<div class="panelBar" id="${random}businessAccount_pageBar">
	<div class="pages">
		<span><s:text name="pager.perPage" /></span> <select
			id="${random}businessAccount_gridtable_numPerPage">
			<option value="20">20</option>
			<option value="50">50</option>
			<option value="100">100</option>
			<option value="200">200</option>
		</select> <span><s:text name="pager.items" />. <s:text
				name="pager.total" /><label
			id="${random}businessAccount_gridtable_totals"></label> <s:text
				name="pager.items" /></span>
	</div>
	<div id="${random}businessAccount_gridtable_pagination"
		class="pagination" targetType="navTab" totalCount="200"
		numPerPage="20" pageNumShown="10" currentPage="1"></div>
</div>
