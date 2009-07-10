<%@tag %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="id" required="false" %>
<%@attribute name="rowValue" required="true" %>
<%@attribute name="rowLabel" required="true" %>


<%--will only render row if the Value is not null--%>
<c:if test="${!empty rowValue}">
    <div class="row"
            <c:if test="${!empty id}">
                id="${id}"
            </c:if>
            >
        <label><c:out value="${rowLabel}"/></label>
        <div class="value"><c:out value="${rowValue}"/></div>
    </div>

</c:if>