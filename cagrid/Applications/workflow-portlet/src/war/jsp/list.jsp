<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="gov.nih.nci.cagrid.portal.portlet.sample.WorkflowDescription" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>



<div class="content">


	<TABLE BORDER="2">
		<TR bgcolor="grey">
			<TH>Job ID</TH><TH>Workflow ID</TH><TH>Status</TH><TH>Output</TH>
		</TR>
	<c:forEach var="entry" items="${cmd.eprsMap}">

		<TR>
		<TD>
			${entry.key}
		</TD>
		<TD>
			${entry.value.workflowDesc.workflowId }
		</TD>
		<TD>
		<b><font size="2" > ${entry.value.status}</font></b>

		</TD>
		<TD>
		<c:choose>
			<c:when test="${entry.value.status == 'Done'}">
						

				

					<form action="<portlet:actionURL/>" method="post">
					<input type="hidden" name="formState" value="4">
					<input type="hidden" name="selectedUUID" value="${entry.key}">
					<input type="submit" name="Output" value = "Output">
					</form>

			</c:when>
			<c:otherwise>
					<button type="button" disabled="disabled">Output</button>
			</c:otherwise>
		</c:choose>
		</TD>

		</TR>
	</c:forEach>
	
	</TABLE>

</div>