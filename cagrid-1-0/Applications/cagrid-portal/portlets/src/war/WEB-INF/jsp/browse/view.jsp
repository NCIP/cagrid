<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="browse-search-includes.jspf" %>

<portlet:renderURL var="viewDetailsUrl">
    <portlet:param name="operation" value="viewDetails"/>
</portlet:renderURL>
<c:set var="ns"><portlet:namespace/></c:set>  

<script language="JavaScript">

    function ${ns}viewDetails(id) {
        $("${ns}catalogDetailsForm").entryId.value = id;
        $("${ns}catalogDetailsForm").action = '<portlet:renderURL/>';
        $("${ns}catalogDetailsForm").submit();
    }
    
    function pageCallback(result) {
        var resultDiv = document.createElement('div');

        var detailsLnk = document.createElement('a');
        detailsLnk.setAttribute('href', 'javascript:${ns}viewDetails(' + result.id + ')');
        detailsLnk.setAttribute('name', 'Details');
        detailsLnk.innerHTML = result.name;

        var nameDiv = document.createElement('div');
        nameDiv.appendChild(document.createTextNode("Name:"));
        nameDiv.appendChild(detailsLnk);
        resultDiv.appendChild(nameDiv);

        if (null != result.description) {
            var descDiv = document.createElement('div');
            descDiv.appendChild(document.createTextNode(" Description: " + result.description));
            resultDiv.appendChild(descDiv);
        }

        resultDiv.appendChild(document.createElement('br'));

        $("catalogs").appendChild(resultDiv);


    }

</script>

<h1>Browsing: <c:out value="${browseType}"/></h1>
<form:form id="${ns}catalogDetailsForm" name="catalogDetailsForm">
    <input type="hidden" name="entryId" value="">
    <input type="hidden" name="operation" value="viewDetails">

    <div id="catalogs">
    </div>
</form:form>

<script type="text/javascript">
    <c:choose>
    <c:when test="${catalogType!=null}">
    loadCatalogs("*:*", "${catalogType}");
    </c:when>
    <c:otherwise>
    loadCatalogs("*:*");
    </c:otherwise>
    </c:choose>
</script>


<p>

</p>