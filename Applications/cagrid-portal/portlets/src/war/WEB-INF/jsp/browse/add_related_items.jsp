<%@ include file="/WEB-INF/jsp/include/servlet_includes.jsp" %>

<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.7.0/build/button/assets/skins/sam/button.css" />
<link rel="stylesheet" type="text/css" href="<c:url value="/js/yui/container/assets/skins/sam/container.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/js/yui/autocomplete/assets/skins/sam/autocomplete.css"/>">

<script type="text/javascript" src="<c:url value="/js/yui/yahoo-dom-event/yahoo-dom-event.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/datasource/datasource-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/element/element-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/button/button-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/logger/logger-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/autocomplete/autocomplete-min.js"/>"></script>

<c:set var="ns" value="${param.ns}"/>

<script type="text/javascript"
	src="<c:url value="/dwr/interface/CatalogEntryManagerFacade.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/engine.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/util.js"/>"></script>

<div align="left" style="overflow:auto; height:500px" class="yui-skin-sam">

<h3>
Adding a Related Item
</h3>
<p>
First, choose the kind of catalog entry for which you would like to describe a relationship.
Then, select the kind relationship that you would like to describe.
</p>
<div id="${ns}addRelationshipButtonContainer"></div>
<form name="${ns}addRelatedItemsForm">

<b>Kinds of Catalog Entries</b><br/>
<ul>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry" checked="true"/>
		<b>Dataset</b><br/>
		Description of dataset catlog entries.
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.ToolCatalogEntry"/>
		<b>Tool</b><br/>
		Description of tool catlog entries.
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry"/>
		<b>Person</b><br/>
		Description of person catlog entries.
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.CommunityCatalogEntry"/>
		<b>Community</b><br/>
		Description of community catlog entries.
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry"/>
		<b>Institution</b><br/>
		Description of institution catlog entries.
	</li>
</ul>	
<br/>

<p>
<b>Kinds of Relationships</b>
<div id="${ns}roleTypeDiv">

</div>
</p>

</form>

</div>

<script type="text/javascript">


function ${ns}populateLists(entryType){

	CatalogEntryManagerFacade.renderRoleTypesForType(entryType, "${ns}",
	{
		callback:function(html){
			jQuery("#${ns}roleTypeDiv").html(html);
			if(jQuery("form[name='${ns}addRelatedItemsForm']  :input[name='roleType']").get().length > 0){
				${ns}addRelationshipButton.set("disabled", false);			
			}else{
				${ns}addRelationshipButton.set("disabled", true);
			}
  		},
  		errorHandler:function(errorString, exception){
  			alert("Error rendering role types: " + errorString);
  		}
	});
}

var ${ns}addRelationshipButton = null;

jQuery(document).ready(function() {


	${ns}populateLists('gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry');
	
	jQuery("form[name='${ns}addRelatedItemsForm']  :input[name='entryType']").bind('change', function(evt){
		${ns}populateLists(evt.target.value);
	});

	${ns}addRelationshipButton = new YAHOO.widget.Button({
		label: "Add",
		id: "${ns}addRelationshipButton",
		container: "${ns}addRelationshipButtonContainer"
	});
	${ns}addRelationshipButton.set("disabled", true);

	${ns}addRelationshipButton.on("click", function(evt){
		${ns}addRelationship(jQuery("form[name='${ns}addRelatedItemsForm']  :input[name='roleType']").val());
	});
	
	
});
</script>

