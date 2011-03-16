<%@ include file="include.jsp" %>


<%@ page import="gov.nih.nci.cagrid.portal.portlet.workflow.domain.WorkflowDescription" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>


<div class="content">
	<TABLE BORDER="2">
		<TR bgcolor="grey"><TH> Job ID </TH><TH> Workflow ID </TH><TH> Status </TH><TH> Output </TH></TR>
		<c:forEach var="entry" items="${eprs}">
			<TR>
				<TD>${entry.key}</TD>
				<TD>${entry.value.workflowDesc.id }</TD>
				<TD><b><font size="2" > ${entry.value.status}</font></b></TD>
				<TD>
					<c:if test="${entry.value.status == 'Done'}">
							<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewOutput"/><portlet:param name="uuid" value="${entry.key}"/></portlet:renderURL>">View Output</a>
					</c:if>
				</TD>
			</TR>
			<c:if test="${isEmpty}">
				<tr>
					<td colspan="4">No Active Workflow Instances..</td>
				</tr>
			</c:if>
	</c:forEach>
	</TABLE>
</div>
