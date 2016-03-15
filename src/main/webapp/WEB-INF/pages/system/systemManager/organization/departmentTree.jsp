<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/jstree.jsp"%> --%>
<head>
<title><fmt:message key="departmentDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='departmentDetail.heading'/>" />

</head>


 <sjt:tree	id="deptTree" 
    		jstreetheme="apple"
    		rootNode="departTreeList"
    		childCollectionProperty="children"
    		nodeTitleProperty="title"
    		nodeIdProperty="id"
    	/>
    	 
    	 

    	