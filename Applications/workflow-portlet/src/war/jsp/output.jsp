<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>



<div class="content">

	<UL><font color="blue" size="4">The Output of the Workflow</font></UL><BR>
	<B>UUID of the Submitted Workflow : </B>${cmd.selectedUUID}<BR>
	<B>Output Port :</B><BR>
	<c:forEach var="out" items= "${cmd.outputs}">
		<c:out value="${out}"/>	
	</c:forEach>
</div>