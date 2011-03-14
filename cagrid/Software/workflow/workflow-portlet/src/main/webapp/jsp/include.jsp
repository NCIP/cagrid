<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ns" value="<portlet:namespace/>"/>
<portlet:renderURL   var="viewInstancesURL" portletMode="view" windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>

