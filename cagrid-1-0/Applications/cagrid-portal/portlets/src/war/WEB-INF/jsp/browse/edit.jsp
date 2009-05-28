<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<h1>Select Browse Type</h1>

<c:set var="prefix"><portlet:namespace/></c:set>

<c:set var="formName"><portlet:namespace/>browseForm</c:set>
<portlet:actionURL var="action"/>
<form:form id="${formName}" name="${formName}" action="${action}" commandName="command">

<c:if test="${empty command}">
No command object</br>
</c:if>
<c:if test="${empty command.browseTypes}">
No browse types</br>
</c:if>


<form:select id="${formName}BrowseType" path="browseType">
	<c:forEach var="type" items="${command.browseTypes}">
		<form:option value="${type}"/>
	</c:forEach>
</form:select>

<input type="hidden" name="operation" value="update"/>
<button id="${formName}UpdateBtn" type="submit">Update</button>
	
</form:form>


<br/>
Back to <a href="<portlet:renderURL portletMode="view"/>">View Mode</a>