<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="portletext" uri="http://liferay.com/tld/portlet" %>

<portlet:renderURL   var="ajaxStatusURL"  portletMode="view" windowState="exclusive"><portlet:param name="action" value="viewInstances"/></portlet:renderURL>
<portlet:renderURL   var="jsonURL"  portletMode="view" windowState="exclusive"><portlet:param name="action" value="json"/></portlet:renderURL>

<portlet:renderURL var="viewDefinitionsURL"  windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>
<portlet:renderURL var="viewInstancesURL"  windowState="normal"><portlet:param name="action" value="viewInstances"/></portlet:renderURL>