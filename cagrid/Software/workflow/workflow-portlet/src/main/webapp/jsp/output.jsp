<%@ include file="include.jsp" %>

<div class="content">
	<UL><font color="blue" size="4">The Output of the Workflow</font></UL><BR>
	<B>UUID of the Submitted Workflow : </B>${uuid}<BR>
	<B>Workflow Output :</B><BR><BR>
	<c:forEach var="out" items= "${outputs}">
		<c:out value="${out}"/>	
	</c:forEach>
	<a href="<portlet:renderURL portletMode="view"/>">View Workflow Definitions</a>
</div>
