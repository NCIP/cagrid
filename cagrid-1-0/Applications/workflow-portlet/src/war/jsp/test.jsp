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

	<c:if test="${cmd.formState == 0}">
	    <table border=0>
	      <c:forEach var="workflow" items="${cmd.allWorkflows}">
	
	        <tr>
	          <td> <b><font size="4" > ${workflow.name} -- Author2: ${workflow.author} </font></b> <br><br></td>
	        </tr>
			<tr> 
				<td><b><font size="2" > Description2: </font></b>${workflow.description} </td>
			</tr>
			<tr>
	          <td><b><font size="2" > Scufl Path2: </font></b>${workflow.scuflLocation} </td>
	        </tr>
			<tr>
	          <td><b><font size="2" > Current Working Dir2: </font></b> ${cmd.keyword}</td>
	        </tr>
			<tr><td><br>
			<form action="<portlet:actionURL/>" method="post">
	        <span style="color:red"><form:errors path="*"/></span>
			<!--<input type="hidden" name="Form" value="1">
			<input type="hidden" name="name" value="${workflow.name}">
			<input type="hidden" name="description" value="${workflow.description }">
			<input type="hidden" name="author" value="${workflow.author}">
			<input type="hidden" name="path" value= "${workflow.scuflLocation }">
			-->
			<input type="hidden" name="keyword" value="${workflow.name }">
			<input type="hidden" name="workflowId" value="${workflow.workflowId }">
			<input type="hidden" name="formState" value="1">
			<input type="submit" value="Select Workflow">
			
	
			</form>
			<br>
			<hr>
			</td></tr>
	
	      </c:forEach>
	    </table>
 	</c:if>
	<c:if test="${cmd.formState == 1}">
		Workflow Name : <c:out value="${cmd.theWorkflow.name}"/>

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



	</c:if>


</div>