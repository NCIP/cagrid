<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="/WEB-INF/jsp/browse/backLink.jspf" %>
<c:set var="catalogEntry" value="${catalogEntryViewBean.catalogEntry}"/>
<div class="photoRow">
	<div class="photo">
		<img src="../../../images/photo-unavailable.png" alt="Photo of <c:out value="${catalogEntry.name}"/>" />
	</div>
	<div class="photoRightContent">
		<h1 class="entryName"><c:out value="${catalogEntry.name}"/></h1>
		<p class="entryDescription">
		<c:out value="${catalogEntry.description}"/>
		</p>
	</div>
	<!-- The following line is for formatting purposes -->
	<hr style="clear:both; visibility:hidden;"/>
</div>
<div class="contactInfoRow">
	<c:if test="${catalogEntry.phoneNumberPublic}">
		<c:set var="rowLabel" value="Phone"/>
		<c:set var="rowValue" value="${catalogEntry.phoneNumber}"/>
	    <%@ include file="/WEB-INF/jsp/browse/row.jspf" %>
	</c:if>
	<c:if test="${catalogEntry.emailAddressPublic}">
	    <c:set var="rowLabel" value="e-mail"/>
		<c:set var="rowValue" value="${catalogEntry.emailAddress}"/>
	    <%@ include file="/WEB-INF/jsp/browse/row.jspf" %>
	</c:if>
	<c:if test="${catalogEntry.addressPublic}">
		<%@ include file="/WEB-INF/jsp/browse/address.jspf" %>
	</c:if>
	<c:if test="${catalogEntry.webSite}">
		<c:set var="rowLabel" value="Website"/>
		<c:set var="rowValue" value="${catalogEntry.webSite}"/>
	    <%@ include file="/WEB-INF/jsp/browse/row.jspf" %>
	</c:if>
</div>
<%@ include file="/WEB-INF/jsp/browse/relatedItemsAndOtherInfo.jspf" %>
