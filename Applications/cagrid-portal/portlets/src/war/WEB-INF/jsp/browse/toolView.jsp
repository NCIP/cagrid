<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<%@ include file="/WEB-INF/jsp/browse/backLink.jspf" %>
<p/>

<c:set var="catalogEntry" value="${catalogEntryViewBean.catalogEntry}"/>
<c:out value="${catalogEntry.name}"/>
<p/>
<c:out value="${catalogEntry.description}"/>
<p/>


<%@ include file="/WEB-INF/jsp/browse/pocs.jspf" %>


<%@ include file="/WEB-INF/jsp/browse/relatedItemsAndOtherInfo.jspf" %>