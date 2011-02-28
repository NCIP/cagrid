function ajaxUpdate() {
	console.log("AJAX Updating...")
	jQuery('#<portlet:namespace/>ajaxDiv').load('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>', 
		function() {
			console.log("AJAX Updated.")
		});	
}