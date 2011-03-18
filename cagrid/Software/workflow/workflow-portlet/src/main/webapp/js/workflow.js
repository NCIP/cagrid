/**
 * Replace the contents of a div by doing an AJAX request.
 * @param url Portlet <i>action</i> request parameter. (routed via Spring)
 * @param divId ID of div to replace with response contents
 * @param doPoll whether to start polling
 * @param period time interval between ajax calls. (seconds)
 */
function ajaxPollingUpdate(url, divId, doPoll, period) {
	console.log("AJAX Updating [" + divId + "] with URL: [" + url + "]...");
	jQuery('#'+divId).load( url, function(responseText, textStatus, XMLHttpRequest ) {
			console.log("AJAX Updated [" + divId + "] with URL: [" + url + "].");
			if(doPoll) {
				
				jQuery.ajax( { 
					url : '<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="json"/></portlet:renderURL>',  
					error: function() { console.log("Error");  },
					success: function(data, status, xhr) {
						console.log("Data: " + data); 
					},
					type: 'GET'  
				});
				
				setTimeout('ajaxPollingUpdate('+url+','+divId+","+doPoll+","+period+')', period*1000);
			}
		});	
};

function util(jsonURL, statusURL) {
	this.jsonURL = jsonURL;
	this.statusURL = statusURL;
	this.show = function() {
		console.log( "JSON URL: " + jsonURL);
		console.log("Status URL: " + statusURL);
	};
}

