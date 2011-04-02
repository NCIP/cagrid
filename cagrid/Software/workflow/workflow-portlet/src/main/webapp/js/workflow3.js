/**
 * Replace the contents of a div by doing an AJAX request.
 * @param url Portlet <i>action</i> request parameter. (routed via Spring)
 * @param divId ID of div to replace with response contents
 * @param doPoll whether to start polling
 * @param period time interval between ajax calls. (seconds)
 */
function ajaxPollingUpdate(url, divId, doPoll, period) {
	jQuery('#'+divId).load( url, function(responseText, textStatus, XMLHttpRequest ) {
			if(doPoll) {
				var call = 'ajaxPollingUpdate(\"'+url+'\",\"'+divId+'\",true,'+period+')';
				setTimeout(call, period*1000);
			}
		});	
};

function WorkflowUtil(jsonURL, statusURL) {
	this.jsonURL = jsonURL;
	this.statusURL = statusURL;
	this.namespace = "_workflow_WAR_workflowportlet_";
	this.show = function() {
		console.log('json: ' + jsonURL);
	};
	/**
	 * @param windowState  Liferay windowstate.  i.e. <b>normal</b> or <b>exclusive</b> <i>(for ajax)</i>
	 * @param action	portlet request action
	 * @param parameters	<code>Map</code> of request parameters
	 * @returns	the URL
	 */
	this.createRenderURL= function( windowState, action, parameters) {
		return "http://localhost:8080/user/portaladmin/home?p_p_id=workflow_WAR_workflowportlet&p_p_lifecycle=0&p_p_state="+windowState+"&p_p_mode=view&_workflow_WAR_workflowportlet_action="+action;
	};
	
	/**
	 * @param windowState  Liferay windowstate.  i.e. <b>normal</b> or <b>exclusive</b> <i>(for ajax)</i>
	 * @param action	portlet request action
	 * @param parameters	<code>Map</code> of request parameters
	 * @returns	the URL
	 */
	this.createActionURL = function( windowState, action, parameters) {
		return "http://localhost:8080/user/portaladmin/home?p_p_id=workflow_WAR_workflowportlet&p_p_lifecycle=1&p_p_state="+windowState+"&p_p_mode=view&_workflow_WAR_workflowportlet_action="+action;
	};
	
	/** 
	 * @returns Portlet Namespace 
	 */
	this.ns = function() {
		return "_workflow_WAR_workflowportlet_";
	};
};