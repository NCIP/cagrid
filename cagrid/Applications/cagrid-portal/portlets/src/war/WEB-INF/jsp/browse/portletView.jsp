<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="/WEB-INF/jsp/browse/common/top.jspf" %>
<%@ include file="/WEB-INF/jsp/browse/backLink.jspf" %>

<c:choose>
	<c:when test="${viewMode eq 'edit'}">
<%@ include file="/WEB-INF/jsp/browse/portlet/edit.jspf" %>
	</c:when>
	<c:otherwise>
<%@ include file="/WEB-INF/jsp/browse/portlet/view.jspf" %>	
	</c:otherwise>
</c:choose>