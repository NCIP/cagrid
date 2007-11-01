<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/query/tabs.jspf" %>
<%@ taglib prefix="m" tagdir="/WEB-INF/tags"%>

<script type='text/javascript'
	src='<c:url value="/dwr/interface/UMLClassTreeFacade.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
<script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>

<portlet:actionURL var="editCriterionUrl">
	<portlet:param name="operation" value="selectCriterion"/>
	<portlet:param name="path" value="ATT_PATH"/>
</portlet:actionURL>

<script type="text/javascript">
	//<![CDATA[
<c:set var="treeFacadeName" value="UMLClassTreeFacade"/>
    function <portlet:namespace/>toggleDiv(id){
    
    	var prefix = "<portlet:namespace/>";
    	var nodeId = prefix + id + "Node";
    	var divId = prefix + id + "Div";
    	
    	var node = document.getElementById(nodeId);
    	var div = document.getElementById(divId);

    	if(div.style.display == "none"){
    		var wasEmpty = jQuery.trim(DWRUtil.getValue(divId)).length == 0;
			<c:out value="${treeFacadeName}"/>.openNode(id, 
			{ 
				prefix: prefix, 
			 	path: id,
			 	editCriterionUrl: '<c:out value="${editCriterionUrl}" escapeXml="false"/>'
			},
			{
				callback:function(html){
					div.style.display = "";
					if(wasEmpty){
						DWRUtil.setValue(divId, html, {escapeHtml:false});
					}
					node.className = "coll_node";
				},
				errorHandler:function(errorString, exception){
					alert("Error opening node: " + errorString);
				}
			});

    	}else{
    		<c:out value="${treeFacadeName}"/>.closeNode(id, { prefix: prefix },
			{
				errorHandler:function(errorString, exception){
					alert("Error closing node: " + errorString);
				}
			});
			div.style.display = "none";
			node.className = "exp_node";
    	}
    }
    // ]]>
</script>

<style type="text/css">
<!--
<%@ include file="/WEB-INF/jsp/tree/tree_node_styles_frag.jsp"%>
-->
</style>
<p />
<portlet:actionURL var="action"/>

<c:choose>
	<c:when test="${!empty rootNode}">

<c:set var="prefix"><portlet:namespace/></c:set>
<c:set var="node" value="${rootNode}"/>
<%@ include file="/WEB-INF/jsp/query/builder/umlClass.jspf"%>
	
	</c:when>
	<c:otherwise>
No UMLClass is currently selected.
	</c:otherwise>
</c:choose>

