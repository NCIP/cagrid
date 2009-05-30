<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<h1>Browsing: <c:out value="${browseType}"/></h1>

<p>
<c:choose>
	<c:when test="${empty entries}">
		No entries of this type.
	</c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="entry" items="${entries}">
			<li>
				<portlet:renderURL var="viewDetailsUrl">
					<portlet:param name="operation" value="viewDetails"/>
					<portlet:param name="entryId" value="${entry.id}"/>
				</portlet:renderURL>
				<a href="<c:out value="${viewDetailsUrl}"/>">
					<c:out value="${entry.name}"/>
				</a>				
			</li>
			</c:forEach>
		</ul>	
	</c:otherwise>
</c:choose>
</p>