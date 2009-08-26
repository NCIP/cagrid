<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="browse-search-includes.jspf" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<div style="text-align:right;">
	<a href="/web/guest/help/-/wiki/Help/Browsing+the+Catalog">Help</a>
</div>

<%@ include file="catalogCreateDialog.jspf" %>

<script language="JavaScript">

    function ${ns}viewDetails(id) {
        $("${ns}catalogDetailsForm").entryId.value = id;
        $("${ns}catalogDetailsForm").action = '<portlet:renderURL/>';
        $("${ns}catalogDetailsForm").submit();
    }

    function ${ns}pageCallback(type, args) {

        var resultList = args[0];


        $("featuredDiv").setStyle({display: 'none'});
        $("jsFillerFeaturedResults").innerHTML="";
        $("regularResults").setStyle({display: 'none'});
        $("jsFillerRegularResults").innerHTML="";

         if(resultList!=null){
          YAHOO.log("Received results of size:" + resultList.length);

        for (var i = 0, len = resultList.length; i < len; ++i) {
            $("regularResults").setStyle({display: 'block'});
            var result = resultList[i];

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
            icon.setAttribute('src', '<c:url value="/images/catalog_icons/"/>' + result.catalog_type + '.png');
            icon.setAttribute('alt', '');
            iconLnk.appendChild(icon);
            resultDiv.appendChild(iconLnk);

            var nameDiv = document.createElement('div');
            nameDiv.appendChild(detailsLnk);
            resultDiv.appendChild(nameDiv);


            var descDiv = document.createElement('div');
            if (result.description == null || result.description == undefined || result.description == "") {
                descDiv.className = "oneResultNoDescription";
                descDiv.appendChild(document.createTextNode("No information available"));
            } else {
                descDiv.className = "oneResultDescription";
                descDiv.appendChild(document.createTextNode((result.description).truncate(80, "...")));
            }
            resultDiv.appendChild(descDiv);

            if (result.featured) {
                $("featuredDiv").setStyle({display: 'block'});
                $("jsFillerFeaturedResults").appendChild(resultDiv);
                continue;
            }
            $("jsFillerRegularResults").appendChild(resultDiv);

        }
        }
    }


</script>

<form:form id="${ns}catalogDetailsForm" name="catalogDetailsForm">
    <input type="hidden" name="entryId" value=""><input type="hidden" name="operation" value="viewDetails">

    <div>
        <tags:searchBox searchCallback="checkEnter" id="${ns}keyword"/>

        <div>
            <div id="${ns}tree" class="tree-container"></div>
            <div class="searchResults">
                <%@ include file="/WEB-INF/jsp/browse/sort.jspf" %>
                <div id="${ns}searchBar" class="searchBar">
                </div>

                <div id="catalogResult">
                  <div id="${ns}catalogs">
                    <div id="featuredDiv" style="display:none;">
                        <h3>Featured Results</h3>

                        <div id="jsFillerFeaturedResults"><!-- Featured search results will go here --></div>
                    </div>
                    <div id="regularResults" style="display:none;">
                        <h3>Search Results</h3>

                        <div id="jsFillerRegularResults"><!-- Not featured search results will go here --></div>
                    </div>
                </div>

                </div>
                
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
    if (wildcard != "*:*") {
        $("${ns}keyword").value = wildcard;
    }

    function checkEnter(e, keyword) { //e is event object passed from function invocation
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
            if (keyword.length > 0)
                search(keyword);

            return false;
        }
        else {
            return true;
        }
    }

    search(wildcard);

    function search(keyword) {
        if (keyword.length < 1) {
            keyword = wildcard;
        }
        new Catalogs({
            keyword: keyword,
            catalogType: "${catalogType}",
            paginatorDiv: "${ns}paginatorDiv",
            searchBar:"${ns}searchBar",
            treeDiv: "${ns}tree",
            rowsPerPage: 10,
            sortField:$("${ns}sortList").value

        });
        resultEvent.subscribe(${ns}pageCallback);
    }
</script>




