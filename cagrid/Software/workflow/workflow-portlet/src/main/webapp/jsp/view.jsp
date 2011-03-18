<%@ include file="include.jsp" %>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<div class="content">
	<script type="text/javascript">
		jQuery(function($) {
			console.log("Document Ready");
			jQuery('#<portlet:namespace/>accordion').accordion( { header : 'h3', autoHeight : false } );
			ajaxPollingUpdate('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstances"/></portlet:renderURL> #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , false, 0);
		});
	</script>
	
	<h3>Workflow Portlet</h3>
	
	<div id="<portlet:namespace/>scrollPanel"  class="standard">
		<div id="<portlet:namespace/>accordion" >
			<c:forEach var="workflow" items="${workflows}">
				<h3><a href="#">ID# ${workflow.id} - ${workflow.name}</a></h3>
				<div>
				<table border=0>
					<tr><td>${workflow.description}</td></tr>
					<tr><td>Author: ${workflow.author}</td></tr>
					<tr><td><img src="<c:url value="${workflow.thumbnailURI}"/>" /></td></tr>
					<tr><td><a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${workflow.id}"/></portlet:renderURL>">Select Workflow</a></td></tr>
				</table>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<div id="<portlet:namespace/>ajaxDiv">
	</div>
	
	<div id="<portlet:namespace/>progress" ></div>
</div>
