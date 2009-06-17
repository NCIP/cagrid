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

        <%--/* this is where results are rendered. Needs to be styled */--%>
            var resultDiv = document.createElement('div');
			resultDiv.className = "oneResultDiv";
            var detailsLnk = document.createElement('a');
            detailsLnk.setAttribute('href', 'javascript:${ns}viewDetails(' + result.id + ')');
            detailsLnk.setAttribute('name', 'Details');
            detailsLnk.innerHTML = result.name;

			var iconDiv = document.createElement('div');
			iconDiv.className = "oneResultIcon";
			var icon = document.createElement('img');
			icon.setAttribute('src', '<c:url value="/images/icon-unavailable_50px.png"/>');
			icon.setAttribute('alt', '');
			iconDiv.appendChild(icon);    
			resultDiv.appendChild(iconDiv);
			
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
        <div style="width:100%;height:20px;">
            <div style="float:right;">
                Search Keyword:<input id="${ns}keyword" type="text" size="20"/><input type="button" value="Search" onclick="search($('${ns}keyword').value);"/>
            </div>
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




