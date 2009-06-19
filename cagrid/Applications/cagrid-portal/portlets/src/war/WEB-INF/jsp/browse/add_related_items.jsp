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
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.CommunityCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.CommunityCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.CommunityCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry" checked="true"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.DataSetCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.DesktopToolCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.DesktopToolCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.DesktopToolCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.InformationModelCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.InformationModelCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.InformationModelCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.InstitutionCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceEndPointCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceInterfaceCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceInterfaceCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.GridServiceInterfaceCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.KnowledgeCenterCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.KnowledgeCenterCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.KnowledgeCenterCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.PersonCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.PortletCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.PortletCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.PortletCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.ProjectCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ProjectCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ProjectCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.SharedQueryCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.TerminologyCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.TerminologyCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.TerminologyCatalogEntry.description"/>
	</li>	
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.ToolCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ToolCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ToolCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.ToolDeploymentCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ToolDeploymentCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.ToolDeploymentCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.WorkflowCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.WorkflowCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.WorkflowCatalogEntry.description"/>
	</li>
	<li>
		<input type="radio" name="entryType" value="gov.nih.nci.cagrid.portal.domain.catalog.WorkspaceCatalogEntry"/>
		<b><spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.WorkspaceCatalogEntry"/></b><br/>
		<spring:message code="gov.nih.nci.cagrid.portal.domain.catalog.WorkspaceCatalogEntry.description"/>
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

