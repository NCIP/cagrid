<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0" %>
<%@ taglib prefix="theme" uri="http://liferay.com/tld/theme" %>
<%@ taglib prefix="util" uri="http://liferay.com/tld/util" %>
<%@ taglib prefix="ui" uri="http://liferay.com/tld/ui" %>

<portlet:renderURL   var="ajaxStatusURL"  portletMode="view" windowState="exclusive"><portlet:param name="action" value="viewInstances"/></portlet:renderURL>
<portlet:renderURL   var="jsonURL"  portletMode="view" windowState="exclusive"><portlet:param name="action" value="json"/></portlet:renderURL>

<portlet:renderURL var="viewDefinitionsURL"  windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>
<portlet:renderURL var="viewInstancesURL"  windowState="normal"><portlet:param name="action" value="viewInstances"/></portlet:renderURL>


<script type="text/javascript">
	var kedzie = new WorkflowUtil( '${jsonURL}', '${ajaxStatusURL}' );
</script>