<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>

<div class="content">
<script type="text/javascript">
function ajaxUpdate() {
	console.log("AJAX updating...");
	jQuery('#<portlet:namespace/>ajaxDiv').load('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>', 
		function() {
			console.log('Load was performed.');
			setTimeout('ajaxUpdate()',3000);
		});
}

jQuery(function($) { 
$(document).ready(function() {
		console.log("Ready");
		ajaxUpdate();
	});
});
</script>
	<div id="<portlet:namespace/>ajaxDiv" >
	</div>
</div>
