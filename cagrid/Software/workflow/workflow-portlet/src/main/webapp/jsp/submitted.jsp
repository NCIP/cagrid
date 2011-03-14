<%@ include file="include.jsp" %>

<div class="content">
     <div class="result">
		<span style="font-weight:bold; font-color:blue"><font color="blue" size="2">${cmd.result}</font><BR><hr/></span>
		<a href="<portlet:renderURL windowState="normal" ><portlet:param name="action" value="viewInstances"/></portlet:renderURL>">View Submitted Workflows</a><br/>
		<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>">View Workflow Definitions</a>
	</div>
</div>
