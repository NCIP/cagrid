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
			console.log('responseText : ' + responseText);
			console.log('texyStatus : ' + textStatus);
			console.log('XMLHttpRequest : ' + XMLHttpRequest);
			if(doPoll) {
				setTimeout('ajaxPollingUpdate('+url+','+divId+","+doPoll+","+period+')', period*1000);
			}
		});	
};