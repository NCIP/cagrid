<%@tag%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@attribute name="input_name" required="true" %>
<%@attribute name="entry_type" required="true" %>
<%@attribute name="id_prefix" required="false" %>

<c:set var="rtsId">${id_prefix}${entry_type}</c:set>
<input type="radio" name="${input_name}" value="${entry_type}"/>

<a id="${rtsId}-infoPopup-control"
   class="infoPopupLink"
   onmouseover="$('${rtsId}-infoPopup-content').style.display='inline'"
   onmouseout="$('${rtsId}-infoPopup-content').style.display='none'">
   <spring:message code="${entry_type}"/>
</a>&nbsp;

        <span id="${rtsId}-infoPopup-content" class="infoPopup">
        <spring:message code="${entry_type}.description"/>


<span class="infoPopup-pointer">&nbsp;</span></span>

<div id="${rtsId}_roleTypesContainer"></div>