<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:choose>
    <c:when test="${empty page}">
        <c:set var="page" value="0"/>
    </c:when>
    <c:otherwise>
        <c:set var="page" value="${page}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${page == 1}">
        <c:set var="nextPage" value="${null}"/>
    </c:when>
    <c:otherwise>
        <c:set var="nextPage" value="${page + 1}"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${page == 0}">
        <c:set var="prevPage" value="${null}"/>
    </c:when>
    <c:otherwise>
        <c:set var="prevPage" value="${page - 1}"/>
    </c:otherwise>
</c:choose>

<h1>Add New Participant</h1>

<portlet:actionURL var="formAction">
    <portlet:param name="action" value="addParticipant"/>
    <portlet:param name="_page"><jsp:attribute name="value"><c:out value="${page}"/></jsp:attribute></portlet:param>
</portlet:actionURL>

<form:form commandName="participant" method="post" action="${formAction}">

    <form:errors path="*" cssStyle="color:red"/>
	
	<table border="0" cellpadding="4">
		<c:choose> 
			<c:when test="${page == 0}" >
				<tr>
				    <th>First Name</th>
				    <td><form:input path="firstName" size="30" maxlength="80"/></td>
				</tr>
				<tr>
				    <th>Last Name</th>
				    <td><form:input path="lastName" size="30" maxlength="80"/></td>
				</tr>
			</c:when> 
			<c:when test="${page == 1}" >
				<tr>
				    <th>Phone Number</th>
				    <td><form:input path="phoneNumber" size="30" maxlength="80"/></td>
				</tr>
				<tr>
				    <th>Email Address</th>
				    <td><form:input path="emailAddress" size="30" maxlength="80"/></td>
				</tr>
			</c:when>			
		</c:choose>  
		<tr>
			<th colspan="2">
				<c:choose>
				    <c:when test="${empty prevPage}">
				        <input type="submit" value="Previous" disabled/>
				    </c:when>
				    <c:otherwise>
				        <input type="submit" name="_target<c:out value="${prevPage}"/>" value="Previous">
				    </c:otherwise>
				</c:choose>
				<c:choose>
				    <c:when test="${empty nextPage}">
				        <input type="submit" value="Next" disabled/>
				    </c:when>
				    <c:otherwise>
				        <input type="submit" name="_target<c:out value="${nextPage}"/>" value="Next">
				    </c:otherwise>
				</c:choose>
				<input type="submit" name="_finish" value="Finish"/>
				<input type="submit" name="_cancel" value="Cancel"/>
			</th>
		</tr>
	</table>
</form:form>

<p style="text-align:center;"><a href="<portlet:renderURL portletMode="view" windowState="normal"/>">- <spring:message code="button.home"/> -</a></p>
