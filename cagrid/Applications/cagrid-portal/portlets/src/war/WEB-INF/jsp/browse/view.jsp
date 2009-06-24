<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="browse-search-includes.jspf" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<c:set var="ns"><portlet:namespace/></c:set>

<%@ include file="catalogCreateDialog.jspf"%>
<%--
<p>
<c:choose>
	<c:when test="${empty entries}">
		No entries of this type.
	</c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="entry" items="${entries}">
			<li>
				<portlet:renderURL var="viewDetailsUrl">
					<portlet:param name="operation" value="viewDetails"/>
					<portlet:param name="entryId" value="${entry.id}"/>
				</portlet:renderURL>
				<a href="<c:out value="${viewDetailsUrl}"/>">
					<c:out value="${entry.name}"/>
				</a>				
			</li>
			</c:forEach>
		</ul>	
	</c:otherwise>
</c:choose>
</p>
--%>

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

        <%--/* this is where results are rendered. Needs to be styled */--%>
            var resultDiv = document.createElement('div');
			resultDiv.className = "oneResultDiv";
            var detailsLnk = document.createElement('a');
            detailsLnk.setAttribute('href', 'javascript:${ns}viewDetails(' + result.id + ')');
            detailsLnk.setAttribute('name', 'Details');
            detailsLnk.innerHTML = result.name;
			
			var iconLnk = document.createElement('a');
			iconLnk.setAttribute('href', 'javascript:${ns}viewDetails(' + result.id + ')');
			iconLnk.setAttribute('name', 'Details');
			iconLnk.className = "oneResultIcon";
			var icon = document.createElement('img');
			icon.setAttribute('src', '<c:url value="/images/person_placeholder_50px.png"/>');
			icon.setAttribute('alt', '');
			iconLnk.appendChild(icon);    
			resultDiv.appendChild(iconLnk);
			
            var nameDiv = document.createElement('div');
			nameDiv.appendChild(detailsLnk);
            resultDiv.appendChild(nameDiv);

            var descDiv = document.createElement('div');
			if (result.description==null || result.description==undefined || result.description=="") {
				descDiv.className = "oneResultNoDescription";
				descDiv.appendChild(document.createTextNode("No information available"));
			} else {
				descDiv.className = "oneResultDescription";
				descDiv.appendChild(document.createTextNode(result.description));
			}
			resultDiv.appendChild(descDiv);

            
            
            $("${ns}catalogs").appendChild(resultDiv);

        }
    }


</script>

<form:form id="${ns}catalogDetailsForm" name="catalogDetailsForm">
    <input type="hidden" name="entryId" value=""><input type="hidden" name="operation" value="viewDetails">
    <div>
        <div class="searchBox">
            <div class="L-endcap"></div>
            <input class="search" id="${ns}keyword" type="text" size="20" value="Search" style="color:#afafaf;" onkeypress="return checkEnter($('${ns}keyword').value, event);"/>
            <div class="R-endcap"></div>
        </div>
        <div>
            <div id="${ns}tree" class="tree-container"></div>
            <div style="height:100%;width:60%;float:right;">
            	<div id="${ns}catalogs"></div>
				<div class="yui-skin-sam">
					<div id="${ns}paginatorDiv" class="pagination">
	                    <!-- pagination controls will go here -->
	                </div>
				</div>
			</div>
			<br style="clear:both;"/>
        </div>
    </div>
</form:form>

<script type="text/javascript">

var wildcard = "${searchKeyword}";
if (!wildcard) {
    wildcard = "*:*";
}

function checkEnter(keyword, e){ //e is event object passed from function invocation
    var characterCode //literal character code will be stored in this variable
    if (e && e.which) { //if which property of event object is supported (NN4)
        e = e;
        characterCode = e.which;//character code is contained in NN4's which property
    }
    else {
        e = event
        characterCode = e.keyCode; //character code is contained in IE's keyCode property
    }
    if (characterCode == 13) { //if generated character code is equal to ascii 13 (if enter key)
        search(keyword);
        return false;
    }
    else {
        return true
    }
}

search(wildcard);

function search(keyword) {
	if ("${catalogType}") {
            $("${ns}tree").style.visibility = "collapse";
            $("${ns}catalogs").style.width = "100%";
        }
        
        if (keyword.length < 1) {
            keyword = wildcard;
        }
        new Catalogs({
            keyword: keyword,
            catalogType: "${catalogType}",
            paginatorDiv: "${ns}paginatorDiv",
            treeDiv: "${ns}tree",
            rowsPerPage: 10
        });
        resultEvent.subscribe(pageCallback);
}
</script>




