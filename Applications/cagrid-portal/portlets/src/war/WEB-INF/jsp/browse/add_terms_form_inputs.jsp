<%@ include file="/WEB-INF/jsp/include/servlet_includes.jsp" %>

<ul>
<c:forEach var="term" items="${terms}">
<li>${term.uri}</li>
</c:forEach>
</ul>