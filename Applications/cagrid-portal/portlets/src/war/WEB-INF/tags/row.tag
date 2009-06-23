<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="rowValue" required="true"%>
<%@attribute name="rowLabel" required="true"%>

<div class="row">
	<label for="<c:out value="${rowValue}"/>"><c:out value="${rowLabel}"/></label>
	<div class="value"><c:out value="${rowValue}"/></div>
</div>