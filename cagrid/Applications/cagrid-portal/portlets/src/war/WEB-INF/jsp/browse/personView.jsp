<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="/WEB-INF/jsp/browse/common_top.jspf" %>
<%@ include file="/WEB-INF/jsp/browse/backLink.jspf" %>

<c:choose>
	<c:when test="${viewMode eq 'edit'}">
<%@ include file="/WEB-INF/jsp/browse/personView_edit.jspf" %>
	</c:when>
	<c:otherwise>
<%@ include file="/WEB-INF/jsp/browse/personView_view.jspf" %>
	</c:otherwise>
</c:choose>