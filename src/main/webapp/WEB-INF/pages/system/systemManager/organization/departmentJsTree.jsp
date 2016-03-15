<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/jstree.jsp"%> --%>
<head>
<title><fmt:message key="departmentDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='departmentDetail.heading'/>" />
	<script type="text/javascript" src="${ctx}/scripts/jstree/jquery.jstree.js"></script>
<script type="text/javascript">

	
		jQuery(document).ready(function() { 
/* 			jQuery.subscribe('treeClicked', function(event, data) {
				var item = event.originalEvent.data.rslt.obj;
				alert('Clicked ID : ' + item.attr("id") + ' - Text ' + item.text());
			}); */

			treeModel = [{"data":"Confirm Application","attr":{"id":"10"},"children":null},
			             {"data":"Things","attr":{"id":"20"},"children":[{"data":"Thing 1","attr":{"id":"21"},"children":null},{"data":"Thing 2","attr":{"id":"22"},"children":null},{"data":"Thing 3","attr":{"id":"23"},"children":null},{"data":"Thing 4","attr":{"id":"24"},"children":[{"data":"Thing 4.1","attr":{"id":"241"},"children":null},{"data":"Thing 4.2","attr":{"id":"242"},"children":null},{"data":"Thing 4.3","attr":{"id":"243"},"children":null}]}]},{"data":"Colors","attr":{"id":"40"},"children":[{"data":"Red","attr":{"id":"41"},"children":null},{"data":"Green","attr":{"id":"42"},"children":null},{"data":"Blue","attr":{"id":"43"},"children":null},{"data":"Yellow","attr":{"id":"44"},"children":null}]}];
			
			
			jQuery.getJSON("/displayJsDepartmentTreeJson",function(data) {

				//alert(treeModel);
 				treeModel=data.departTreeList;
 				//alert(treeModel);
 				//alert(data);
 	
 				})
			

	 			jQuery("#demoTree").jstree({
	 				"themes" : {
	 					
	 					            "theme" : "apple",
	 					
	 					            "dots" : true,
	 					
	 					            "icons" : true
	 					      },

				      json_data : {
					         data : function(){
					 				return treeModel;
					 				}
					      },
					      plugins : ["themes", "json_data", "ui", "checkbox"]
					 }); 
	 			
		});
		
		
		
</script>
</head>


<%-- <sjt:tree	id="treeDynamicAjax" 
    		jstreetheme="apple"
    		rootNode="departTreeList"
    		childCollectionProperty="children"
    		nodeTitleProperty="title"
    		nodeIdProperty="id"
    	/>  --%>
    	 <strong>Result Div :</strong>
    	 
   <div id="demoTree"></div> 	 
    	 

    	