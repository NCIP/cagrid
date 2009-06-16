<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="browse-search-includes.jspf" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<c:set var="ns"><portlet:namespace/></c:set>

<%@ include file="catalogCreateDialog.jspf"%>

<script language="JavaScript">

    function ${ns}viewDetails(id) {
        $("${ns}catalogDetailsForm").entryId.value = id;
        $("${ns}catalogDetailsForm").action = '<portlet:renderURL/>';
        $("${ns}catalogDetailsForm").submit();
    }

    function pageCallback(type, args) {
        $("${ns}catalogs").innerHTML = '';

        var resultList = args[0];
        for (var i = 0, len = resultList.length; i < len; ++i) {
            var result = resultList[i];

        <%--this is where results are rendered. Needs to be styled--%>
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

            $("${ns}catalogs").appendChild(resultDiv);

        }
    }


</script>

<form:form id="${ns}catalogDetailsForm" name="catalogDetailsForm">
    <input type="hidden" name="entryId" value="">
    <input type="hidden" name="operation" value="viewDetails">

    <div>
        <div style="width:100%;height:20px;">
            <div style="float:right;">
                Search Keyword:<input id="${ns}keyword" type="text" size="20"/>
                <input type="button" value="Search" onclick="search($('${ns}keyword').value);"/>
            </div>
        </div>

        <div>
            <h1>Browsing: <c:out value="${browseType}"/></h1>

            <div class="yui-skin-sam" style="width:100%;float:none;">
                <div id="${ns}paginatorDiv"><!-- pagination controls will go here --></div>
            </div>
            <div id="${ns}tree" style="height:100%;width:30%;float:left;"></div>
            <div id="${ns}catalogs" style="height:100%;width:60%;float:right;">
            </div>
        </div>
    </div>
</form:form>

<script type="text/javascript">

    var wildcard = "${searchKeyword}";
    if (!wildcard) {
        wildcard = "*:*";
    }

    function search(keyword) {
        if ("${catalogType}") {
            $("${ns}tree").style.visibility = "collapse";
            $("${ns}catalogs").style.width = "100%";
        }

        if (keyword.length < 1) {
            keyword = wildcard;
        }
        new Catalogs({keyword:keyword,catalogType:"${catalogType}",paginatorDiv:"${ns}paginatorDiv",treeDiv:"${ns}tree",rowsPerPage:10});
        resultEvent.subscribe(pageCallback);
    }
    search(wildcard);

</script>




