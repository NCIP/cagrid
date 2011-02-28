<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>

<div class="content">
	<TABLE BORDER=0 CELLSPACING=2 CELLPADDING=2>
		<TR>
			<TD><b><font size="4">Workflow Name :
			${cmd.theWorkflow.name} -- Author: ${cmd.theWorkflow.author} </font></b> <br>
			<br>
			</TD>
		</TR>
		<tr>
			<td><b><font size="2"> Description: </font></b>${cmd.theWorkflow.description}<BR>
			<BR>
			</td>
		</tr>

		<TR>
			<TD>
			<form action="<portlet:actionURL><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.workflowId}"/></portlet:actionURL>" method="post">
			</TD>
		</TR>
		<tr>
			<td><b><font size="2">Please Enter the Workflow
			Inputs below:<BR>
			<BR>
			</font></b></td>
		</tr>

		<c:forEach var="item" items="${cmd.theWorkflow.inputs}">
			<tr>
				<td><b><font size="2"> Input Port: ${item.name}</font></b></td>
			</tr>
			<TR>
				<TD><TEXTAREA name="inputValues" rows="5" cols="80">${item.name}</TEXTAREA><BR>
				<BR>
				</TD>
			</TR>
		</c:forEach>
		<TR>
			<TD><input type="hidden" name="workflow" value="${cmd.theWorkflow}">
				<input type="hidden" name="formState" value="2">
				<input type="submit" value="Submit">
			</form>
			</TD>
		</TR>
	</TABLE>
	<HR>
	<TABLE>
		<TR>
			<TD><font size="4">Workflow Image: </font></TD>
		</TR>
		<TR>
			<TD><img src="<c:url value="${cmd.theWorkflow.imageFile}"/>" width="100%" /></TD>
		</TR>
	</TABLE>
</div>