<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<%@ include file="browse-search-includes.jspf" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<portlet:defineObjects />
<liferay-theme:defineObjects />

<%--
TODO: Need to get local button CSS working.
--%>
<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.7.0/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/yui/container/assets/skins/sam/container.css"/>" />


<script type="text/javascript" src="<c:url value="/js/yui/yahoo-dom-event/yahoo-dom-event.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/connection/connection-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/element/element-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/button/button-min.js"/>"></script>
<%--
TODO: Need to download dragdrop.
--%>
<script type="text/javascript" src="http://yui.yahooapis.com/2.7.0/build/dragdrop/dragdrop-min.js"></script>
<script type="text/javascript" src="<c:url value="/js/yui/container/container-min.js"/>"></script>

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

<% 
String actionId = "ADD_CATALOG_ENTRY";
%>
<c:if test="<%= permissionChecker.hasPermission(scopeGroupId, portletDisplay.getRootPortletId(), portletDisplay.getResourcePK(), actionId) %>">
<portlet:actionURL var="addCatalogEntryUrl">
	<portlet:param name="operation" value="createCatalogEntry"/>
</portlet:actionURL>
<div class="yui-skin-sam">
	<div id="${ns}addButtonContainer"></div>
	<div id="${ns}createDialog" style="display: none">
		<div class="hd">Please select the type of entry to create.</div>
		<div class="bd">
			<form name="${ns}addCatalogEntryForm" method="POST" action="<c:out value="${addCatalogEntryUrl}"/>">
				<label for="entryType" value="Type"/>
				<select name="entryType" size="5">
					<option value="DATASET">Dataset</option>
					<option value="PERSON">Person</option>
					<option value="TOOL">Tool</option>
					<option value="COMMUNITY">Community</option>
					<option value="INSTITUTION">Institution</option>
				</select>
			</form>
		</div>
	</div>
</div>
</c:if>

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
<script type="text/javascript">

var ${ns}createDialog = null;
var ${ns}addButton = null;
 
function ${ns}createCatalogEntry(){
	if(${ns}createDialog.validate()){
		${ns}createDialog.form.submit();
	}
}

function ${ns}cancelCreateCatalogEntry(){
	${ns}createDialog.cancel();
}

jQuery(document).ready(function() {
	
	${ns}createDialog = 
		new YAHOO.widget.Dialog("${ns}createDialog",  
            { width : "300px", 
              fixedcenter : true, 
	          visible : false,  
	          constraintoviewport : true,
	          modal : true, 
	          buttons : [ 
	          	{ text:"Submit", handler:${ns}createCatalogEntry, isDefault:true }, 
	            { text:"Cancel", handler:${ns}cancelCreateCatalogEntry } 
	          ]
	        });
	        
	${ns}createDialog.validate = function(){
		var data = this.getData();
		if(data.entryType == ""){
			alert("Please select an entry type to create.");
			return false;
		}else{
			return true;
		}
	};
	
	${ns}createDialog.render();
	document.getElementById('${ns}createDialog').style.display='block';
	
	${ns}addButton = new YAHOO.widget.Button({
			label: "Add New Catalog Entry",
			id: "${ns}addButton",
			container: "${ns}addButtonContainer",
		});
	
	${ns}addButton.on("click", function(evt){
		${ns}createDialog.show();
	});
	

});
</script>


<p>

</p>