<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<portlet:defineObjects />
<liferay-theme:defineObjects />
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.7.0/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/yui/container/assets/skins/sam/container.css"/>" />
<script type="text/javascript" src="<c:url value="/js/yui/yahoo-dom-event/yahoo-dom-event.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/element/element-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/button/button-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/logger/logger-min.js"/>"></script>

<c:set var="catalogEntry" value="${catalogEntryViewBean.catalogEntry}"/>
<c:set var="ns"><portlet:namespace/></c:set>

<portlet:renderURL var="viewUrl">
	<portlet:param name="operation" value="view"/>
</portlet:renderURL>
	<portlet:renderURL var="viewDetailsUrl" portletMode="edit">
		<portlet:param name="operation" value="viewRelationshipType"/>
		<portlet:param name="id" value="CPREPLACE"/>
	</portlet:renderURL>
	
<a href="<c:out value="${viewUrl}"/>">Back to search results</a>

<%
Object[] roleTypes = new Object[]{
		new String[]{"gov.nih.nci.cagrid.portal.portlet.domain.catalog.DataSetCatalogEntry", "Dataset"},		
		new String[]{"gov.nih.nci.cagrid.portal.portlet.domain.catalog.PersonCatalogEntry", "Person"},
		new String[]{"gov.nih.nci.cagrid.portal.portlet.domain.catalog.ToolCatalogEntry", "Tool"},
		new String[]{"gov.nih.nci.cagrid.portal.portlet.domain.catalog.CommunityCatalogEntry", "Community"},
		new String[]{"gov.nih.nci.cagrid.portal.portlet.domain.catalog.InstitutionCatalogEntry", "Institution"},		
};
pageContext.setAttribute("roleTypes", roleTypes);
%>

<c:choose>
	<c:when test="${viewMode eq 'edit'}">
<%@ include file="/WEB-INF/jsp/browse/relationshipTypeView_edit.jspf" %>
<br/>
	</c:when>
	<c:otherwise>
<%@ include file="/WEB-INF/jsp/browse/relationshipTypeView_view.jspf" %>	
	</c:otherwise>
</c:choose>