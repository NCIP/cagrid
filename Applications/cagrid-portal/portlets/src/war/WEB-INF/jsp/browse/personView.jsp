<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<%@ include file="/WEB-INF/jsp/browse/backLink.jspf" %>
<p/>


<c:set var="catalogEntry" value="${catalogEntryViewBean.catalogEntry}"/>
<c:out value="${catalogEntry.name}"/>
<p/>
<c:out value="${catalogEntry.description}"/>
<p/>
Contact Information<br/>

<c:if test="${catalogEntry.phoneNumberPublic}">
Phone: <c:out value="${catalogEntry.phoneNumber}"/><br/>
</c:if>
<c:if test="${catalogEntry.emailAddressPublic}">
Email Address: <c:out value="${catalogEntry.emailAddress}"/><br/>
</c:if>
<c:if test="${catalogEntry.addressPublic}">
Street1: <c:out value="${catalogEntry.street1}"/><br/>
Street2: <c:out value="${catalogEntry.street2}"/><br/>
Locality: <c:out value="${catalogEntry.locality}"/><br/>
State/Province: <c:out value="${catalogEntry.stateProvince}"/><br/>
Country: <c:out value="${catalogEntry.countryCode}"/><br/>
</c:if>
Website: <c:out value="${catalogEntry.webSite}"/>

<%@ include file="/WEB-INF/jsp/browse/relatedItemsAndOtherInfo.jspf" %>

