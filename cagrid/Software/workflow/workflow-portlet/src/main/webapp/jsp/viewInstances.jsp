<%@ include file="include.jsp" %>

<%@ page import="gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>

<div class="content">
	<script type="text/javascript">
		jQuery(function($) {
			ajaxPollingUpdate('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>', '#<portlet:namespace/>ajaxDiv' , false, 0);
		});
	</script>
	
	<div id="<portlet:namespace/>ajaxDiv" >
	</div>
</div>
