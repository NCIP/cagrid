function WorkflowUtil(jsonURL, statusURL, ns) {
	this.namespace = ns;
	this.jsonURL = jsonURL;
	this.statusURL = statusURL;
	
	/**
	 * Replace the contents of a div by doing an AJAX request.
	 * @param action Portlet <i>action</i> request parameter. (routed via Spring)
	 * @param divId ID of div to replace with response contents
	 * @param doPoll whether to start polling
	 * @param period time interval between ajax calls. (seconds)
	 */
	function ajaxPollingUpdate(url, divId, doPoll, period) {
		jQuery('#'+divId).load( url, function(responseText, textStatus, XMLHttpRequest ) {
				if(doPoll) {
					var call = 'ajaxPollingUpdate(\"'+url+'\",\"'+divId+'\",true,'+period+')';
					//get sessionEndpointReferences from server & check if any instance is in
					//an active state
					var eprs = this.getEprs();
					console.log("# of eprs: " + eprs.length);
					
					setTimeout(call, period*1000);
				}
			});	
	};
	
	/**
	 * Get SessionEndpointReferences from server via AJAX
	 */
	function getEprs() {
		jQuery.ajax( {
				url: this.jsonURL,
				dataType: "json", 
				success: function(data, textStatus, XMLHttpRequest) {
					console.log("Done");
					console.log("Response: " + data);
				}
			} ); 
		var jsonString = "{ 'eprs' : \
			[{ 'workflowDesc' : {'id' : 1, 'name' : 'workflow1'}, 'status' : 'Active'}, \
			 { 'workflowDesc' : {'id' : 2, 'name' : 'workflow2'}, 'status' : 'Failed'} \
			]}";
		var eprs = eval(jsonString);
		return eprs;
	}
};