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
        <!--
            Keyword:
            <input name="keyword" value="${cmd.keyword}" size="30"/><br>
		-->	

		Workflow Name 22: <c:out value="${cmd.theWorkflow.name}"/><c:out value=" ${cmd.keyword}"/>

	<TABLE BORDER=2>
	<TR><TD>
		<form action="<portlet:actionURL/>" method="post">
	</TD></TR>
	<c:forEach var="i" begin="1" end = "${cmd.theWorkflow.inputPorts}" step="1" varStatus ="status">
		<TR> <TD><TEXTAREA name="inputValues" rows="5" cols="80"></TEXTAREA></TD></TR>
	</c:forEach>
	<TR><TD>
		<input type="hidden" name="workflowId" value="${cmd.theWorkflow.workflowId }">
		<input type="hidden" name="formState" value="2">
		<input type="submit" value="Submit">
		</form>
	</TD></TR>
	</TABLE>


</div>