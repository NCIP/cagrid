<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<script src="<c:url value="/js/script.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/tablesort.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/pagination.js"/>" type="text/javascript"></script>

<%@ include file="/WEB-INF/jsp/disc/tabs.jspf" %>

<c:set var="prefix"><portlet:namespace/></c:set>
<style type="text/css">

    <!--
    <%@ include file="/css/styleSheet.css" %>
    -->

    .fdTableSortTrigger {
        text-decoration: none;
        font-size: 1.1em;
        color: #FFFFFF; /* constant: black */
        font-weight: bold;
        padding: 0.1cm;
    }

    .pageTxtBtn {
        display: block;
        width: 2em;
        line-height: 2em;
        text-align: center;
        background: #FFFFFF url("<c:url value='/images/scroller/gradient.gif'/>") repeat-x 0 -20px;
    }

    ul.fdtablePaginater li div {
        opacity: 0.5;
    }

    a.currentPage {
        font-weight: bolder;
        border-color: #A84444 !important
    }

    ul li {
        width: auto;
        float: left;
        list-style: none;
        padding-top: 5px;
        padding-bottom: 5px;
        padding-right: 3px;
    }

    ul.fdtablePaginater li a, ul.fdtablePaginater li div {
        border: 1px solid #CCCCCC;
        font-family:georgia,serif;
        font-size:1em;
        display: block;
        outline-color: -moz-use-text-color;
        outline-style: none;
        outline-width: medium;
        padding: 0;
        width: 2em;
    }

    #${prefix}mainTable {
        width: 100%;
        visibility: collapse;
    }

</style>

<portlet:actionURL var="action"/>


<table>
    <tr>
        <td style="padding-right: 5px;">
            <b>Directories:</b>
            <form:form id="${prefix}listDirectories" action="${action}" commandName="listCommand">
                <%@ include file="/WEB-INF/jsp/disc/directory/directoriesSelect.jspf" %>
                <input type="hidden" name="operation" value="selectDirectoryList"/>
            </form:form>
        </td>
        <td style="padding-right: 5px;">
            <b>Search Results:</b>
            <form:form id="${prefix}listResults" action="${action}" commandName="listCommand">
                <%@ include file="/WEB-INF/jsp/disc/directory/searchResultsSelect.jspf" %>
                <input type="hidden" name="operation" value="selectResultsList"/>
            </form:form>
        </td>
    </tr>
</table>

<c:choose>
    <c:when test="${empty listCommand}">
        <p>
            Select an item from either the <i>Directories</i> or <i>Search Resutls</i> drop-down lists, above.
        </p>
    </c:when>
    <c:when test="${fn:length(listCommand.scroller.page) == 0}">
        There are no items to display. Perform new search or select a different directory, above.
    </c:when>
    <c:otherwise>
	
		<span class="scrollerStyle1">
			Found <c:out value="${fn:length(listCommand.scroller.objects)}"/> 
			<c:choose>
                <c:when test="${listCommand.type == 'SERVICE'}">
                    Services
                </c:when>
                <c:when test="${listCommand.type == 'PARTICIPANT'}">
                    Participants
                </c:when>
                <c:otherwise> Points of Contact</c:otherwise>
            </c:choose>
        </span>
        </p>
        <br/>
        <c:set var="scroller" value="${listCommand.scroller}"/>
        <c:set var="scrollOperation" value="scrollDiscoveryList"/>

        <c:choose>
            <c:when test="${listCommand.type == 'SERVICE'}">
                <%@ include file="/WEB-INF/jsp/disc/directory/services.jspf" %>
            </c:when>
            <c:when test="${listCommand.type == 'PARTICIPANT'}">
                <%@ include file="/WEB-INF/jsp/disc/directory/participants.jspf" %>
            </c:when>
            <c:when test="${listCommand.type == 'POC'}">
                <%@ include file="/WEB-INF/jsp/disc/directory/pocs.jspf" %>
            </c:when>
            <c:otherwise>
                <span color="red">UNKNOWN TYPE '<c:out value="${listCommand.type}"/>'</span>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<br/><br/>


<script type="text/javascript">

    function sortCompleteCallback(tableId) {
        $("${prefix}mainTable").style.visibility = 'visible';
    }

</script>