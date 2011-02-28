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
	<TABLE BORDER="2">
		<TR bgcolor="grey"><TH> Job ID </TH><TH> Workflow ID </TH><TH> Status </TH><TH> Output </TH></TR>
	<c:forEach var="entry" items="${cmd.eprsMap}">
		<TR><TD>${entry.key}</TD>
		<TD>${entry.value.workflowDesc.workflowId }</TD>
		<TD><b><font size="2" > ${entry.value.status}</font></b></TD>
		<TD>
		<c:choose>
			<c:when test="${entry.value.status == 'Done'}">
					<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewOutput"/><portlet:param name="uuid" value="${entry.key}"/></portlet:renderURL>">View Output</a>
			</c:when>
			<c:otherwise>
					<button type="button" disabled="disabled">Output</button>
			</c:otherwise>
		</c:choose>
		</TD>

		</TR>
	</c:forEach>
	
	</TABLE>
	<a href="<portlet:renderURL portletMode="view" windowState="normal"/>">View Workflow Definitions</a>
</div>
