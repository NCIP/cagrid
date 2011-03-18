<%@ include file="include.jsp" %>

<div class="content">
	<H3>There has been an error.  Our Apologies.</H3>
	<p>${exception }</p>
	<a href="<portlet:renderURL  windowState="normal"  portletMode="view"/>">View Workflow Definitions</a>
</div>