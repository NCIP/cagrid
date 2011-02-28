<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>

<div id="div" class="content">
<script type="text/javascript">
jQuery(function($){
	var <portlet:namespace/>form = $('#'+'<portlet:namespace />fm');
	<portlet:namespace/>form.bind("submit",function(event){
		   event.preventDefault();
		   var inputs = $("input, textarea, select", <portlet:namespace/>form);
		   var url = '<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.workflowId}"/></portlet:actionURL>' + "&" + inputs.serialize();
		   $('#<portlet:namespace/>results').load(url);
	});
});
</script>
	<TABLE BORDER=0 CELLSPACING=2 CELLPADDING=2>
		<TR><TD>
		<b><font size="4">Workflow Name : ${cmd.theWorkflow.name} -- Author: ${cmd.theWorkflow.author} </font></b><br/>
		</TD></TR>
		<tr><td>
			<b><font size="2"> Description: </font></b>${cmd.theWorkflow.description}<BR>
		</td></tr>
		<TR><TD>
			<form id="<portlet:namespace/>fm" action="<portlet:actionURL><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.workflowId}"/></portlet:actionURL>" method="post">
		</TD></TR>
		<tr><td>
		<c:choose>
			<c:when test="${cmd.theWorkflow.inputPorts>0}">
				<b><font size="2">Please Enter the Workflow	Inputs below:</font></b>
			</c:when>
			<c:otherwise>
				<b><font size="2">This workflow takes no inputs</font></b>
			</c:otherwise>
		</c:choose>
		</td></tr>
		<c:forEach var="item" items="${cmd.theWorkflow.inputs}">
			<tr><td><b><font size="2">Input Port: ${item.name}</font></b></td></tr>
			<TR><TD><TEXTAREA name="inputValues" rows="5" cols="80">${item.name}</TEXTAREA><BR><BR></TD></TR>
		</c:forEach>
		<TR><TD><input type="submit" value="Submit"></TD></TR>
			</form>
	</TABLE>
	<HR>
	<TABLE>
		<TR><TD><font size="4">Workflow Image: </font></TD></TR>
		<TR><TD><img src="<c:url value="${cmd.theWorkflow.imageFile}"/>" width="100%" /></TD></TR>
	</TABLE>
	<div id="<portlet:namespace/>results" class="result">
	</div>
</div>