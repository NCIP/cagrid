<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<c:set var="browseURL"><portlet:actionURL/></c:set>


<script type="text/javascript">
    function search() {
        $("searchKeyword").value = $('keyword').value;
        $("searchForm").action = "${browseURL}";
        $("searchForm").submit();
    }
</script>


<form:form id="searchForm" method="post">
    <div style="position:absolute;width:95%;height:30px;padding-bottom:10px;margin-top:-40px;z-index:21;">
        <div style="float:right;z-index:31;">
            <input type="hidden" name="searchKeyword" id="searchKeyword"/>
            <input type="hidden" name="operation" id="operation" value="catalogSearch">

            Search: <input id="keyword" type="text" size="20"/>
            <input type="button" value="Search" onclick="search();"/>
        </div>
    </div>
</form:form>
