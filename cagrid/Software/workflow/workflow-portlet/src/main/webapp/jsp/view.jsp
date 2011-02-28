<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>

<div class="content">
<script type="text/javascript">
function ajaxUpdate() {
	console.log("AJAX Updating...")
	jQuery('#<portlet:namespace/>ajaxDiv').load('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>', 
		function() {
			console.log("AJAX Updated.")
		});	
}

jQuery(function($){
	$(document).ready(function() {
		ajaxUpdate();   
	});
});
</script>
	<table border=0>
		<c:forEach var="workflow" items="${cmd.allWorkflows}">
			<tr>
				<td><b><font size="4"> ${workflow.name} </font></b> <br>
				<br>
				</td>
			</tr>
			<tr>
				<td><b><font size="2"> Description: </font></b>${workflow.description}
				</td>
			</tr>
			<tr>
				<td><b><font size="2"> Author: </font></b> ${workflow.author}
			</tr>
			<TR>
				<TD><img src="<c:url value="${workflow.imageFile}"/>" width="100%" /></TD>
			</TR>
			<tr>
				<td><br>
				<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${workflow.workflowId}"/></portlet:renderURL>">Select Workflow</a>
				<br>
				<hr>
				</td>
			</tr>
		</c:forEach>
	</table>
	<div id="<portlet:namespace/>ajaxDiv">
	
	</div>
	
</div>
