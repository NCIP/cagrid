<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>

<div class="content">
     <div class="result">
		<span style="font-weight:bold; font-color:blue"><font color="blue" size="2">${cmd.result}</font><BR><hr/></span>
		<a href="<portlet:renderURL windowState="normal" ><portlet:param name="action" value="viewInstances"/></portlet:renderURL>">View Submitted Workflows</a><br/>
		<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>">View Workflow Definitions</a>
	</div>
</div>
