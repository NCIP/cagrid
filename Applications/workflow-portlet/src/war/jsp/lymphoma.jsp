<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>



<div class="content">

	<UL><font color="blue" size="4">The Output of the Workflow</font></UL><BR>
	<B>UUID of the Submitted Workflow : </B>${cmd.selectedUUID}<BR>
	<B>Workflow Output :</B><BR>
	<font color="green" size="2"><B>NOTE:</B> The highlight rows in the table below show that some of the Predicted lymphoma types are NOT same as the Actual types (TrueClass). The predictions could be wrong sometimes.</font><BR><BR>
	<c:forEach var="out" items= "${cmd.outputs}">
		${out}	
	</c:forEach>
</div>