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
	src="<c:url value="/dwr/interface/TerminologyService.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/engine.js"/>"></script>
<script type="text/javascript" src="<c:url value="/dwr/util.js"/>"></script>

<div align="left" style="overflow:auto; height:500px" class="yui-skin-sam">

<h3>
Areas of Focus
</h3>
<p>
Select all terms that apply.
</p>
<div id="${ns}addTermsButtonContainer"></div>
<form name="${ns}addTermsForm">

</form>

</div>

<script type="text/javascript">

var ${ns}addTermsButton = null;

function ${ns}renderAddTermsFormInputs(terminologyUri){
	TerminologyService.renderAddTermsFormInputs(terminologyUri, "${ns}",
	{
		callback:function(html){
			jQuery("${ns}addTermsForm").html(html);	
		},
		errorHandler:function(errorString, exception){
			alert("Error rendering terms inputs: " + errorString);
		}
	});
}

jQuery(document).ready(function() {

	${ns}renderAddTermsFormInputs("http://cagrid.org/terms/area_of_focus_1_0.owl#");

	${ns}addTermsButton = new YAHOO.widget.Button({
		label: "Add",
		id: "${ns}addTermsButton",
		container: "${ns}addTermsButtonContainer"
	});
	${ns}addTermsButton.set("disabled", true);

	${ns}addTermsButton.on("click", function(evt){
		${ns}addTerms(jQuery("form[name='${ns}addTermsForm']  :input[name='term']").val());
	});
	
	
});
</script>