<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="/WEB-INF/jsp/include/liferay-includes.jspf" %>
<c:set var="ns"><portlet:namespace/></c:set>

<%--<c:set var="searchLink"><portlet:actionURL/></c:set>--%>

<liferay-portlet:actionURL var="searchLink" portletName="BrowsePortlet_WAR_cagridportlets"
                           portletMode="view">
    <liferay-portlet:param name="operation" value="view"/>
</liferay-portlet:actionURL>


<script type="text/javascript">
    function ${ns}search(e) { //e is event object passed from function invocation
        var characterCode //literal character code will be stored in this variable
        if (e && e.which) { //if which property of event object is supported (NN4)
            e = e;
            characterCode = e.which;//character code is contained in NN4's which property
        } else {
            e = event
            characterCode = e.keyCode; //character code is contained in IE's keyCode property
        }
        if (characterCode == 13) { //if generated character code is equal to ascii 13 (if enter key)
            var searchLink = "${searchLink}";
            searchLink = searchLink.replace("/guest/home", "/guest/catalog/all");
            $("${ns}searchKeyword").value = $('${ns}keyword').value;
            $("${ns}searchForm").action = searchLink;
            $("${ns}searchForm").submit();
            return false;
        } else {
            return true
        }
    }

</script>


<form:form id="${ns}searchForm" method="post">
    <div class="searchBox">
        <input type="hidden" name="searchKeyword" id="${ns}searchKeyword"/>

        <div class="L-endcap"></div>
        <input id="${ns}keyword" class="search" type="text" size="20"
               value="Search" style="color:#afafaf;" onkeypress="return ${ns}search(event);"/>

        <div class="R-endcap"></div>

    </div>
</form:form>
