<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script>

</script>

<div class="page">
	<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
				<s:iterator value="voucherStatus">
					<li><a href="voucherList?state=<s:property value='state'/>" class="j-ajax"><span><s:property value="stateName"/></span></a></li>
				</s:iterator>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:1000px;padding:0">
			<s:iterator value="voucherStatus">
			<div></div>
			</s:iterator>
		</div>
		
	</div>
	</div>
</div>