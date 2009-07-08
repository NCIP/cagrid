    <div id="mainPanel${instance.id}" style="overflow:auto; height:300px; width:900px;">
     <div>
        <a onclick="deleteQueryInstance(${instance.id})">Delete</a>
     </div>
    	<div class="errorMessage">
    	One or more of the remote data sources that you are attempting to query
    	encountered an error. Please contact the Help Desk at ncicb@pop.nci.nih.gov and
    	provide them with the query and error message.
    	<a id="toggleError${instance.id}" onclick="cgp_toggleError(${instance.id})">View Error</a>
    	</div>
        <div id="stackTrace${instance.id}" class="errorStackTrace" style="display:none;">
        	<h3>Error Message</h3>
        	<p>
        	<pre>
            ${instance.error}
            </pre>
            </p>
        </div>
    </div>
