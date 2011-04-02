<%@ include file="include.jsp" %>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<div class="content">
	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					jQuery('#<portlet:namespace/>accordion').accordion( { 
							header : 'h3', autoHeight : false, event: 'click',
							change: function(event, ui) {
								var id = ui.newHeader[0].id;
								var url = ui.newContent[0].title + " #<portlet:namespace/>inputs"+id;
								jQuery('#<portletnamespace/>inputs'+id).load(url);
							}
				 	} );
			ajaxPollingUpdate('${ajaxStatusURL} #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , true, 4);
		});
	</script>
	
	<h3>Workflow Portlet</h3>
	
	<div id="<portlet:namespace/>scrollPanel"  class="standard"    style="overflow: scroll; height:400px;">
		<div id="<portlet:namespace/>accordion" >
			<c:forEach var="workflow" items="${workflows}">
				<h3 id="${workflow.id}"><a href="#">ID# ${workflow.id} - ${workflow.name}</a></h3>
				<div id="<porlet:namespace/>content${workflow.id}"  title="<portlet:renderURL windowState='exclusive' > <portlet:param  name='action' value='newInstance' /><portlet:param  name='id' value='${workflow.id}'/></portlet:renderURL>">
					
					<form id="<portlet:namespace/>fm${workflow.id}" class="uni-form" action="<portlet:actionURL windowState="normal"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${workflow.id}"/></portlet:actionURL>" method="post"  >
						<table border=0>
							<tr><td align="center"  colspan="2"><h3>${workflow.name}</h3></td></tr>
							<tr><td rowspan="2"><img width="200" src="<c:url value="${workflow.previewImageURI}"/>" /></td><td>Author: ${workflow.author}  <a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="newInstance"/><portlet:param  name='id' value='${workflow.id}'/></portlet:renderURL>">Details</a></td></tr>
							<tr><td>${workflow.description}</td></tr>
							
							<div id="<portletnamespace/>inputs${workflow.id}"></div>
							
							<tr><td><input id="<portlet:namespace/>submitButton" type="submit" value="Submit"  title="Launch Workflow Instance"/></td><td><div id="<portlet:namespace/>results${workflow.id}" class="results"></div></td></tr>
						</table>
					</form>
					<script type="text/javascript">
						jQuery(function($) {
							var <portlet:namespace/>fm${workflow.id} =$('#<portlet:namespace/>fm${workflow.id}');
							<portlet:namespace/>fm${workflow.id}.bind("submit",
									function(event){
									   event.preventDefault();
									   var dialog = jQuery('#<portlet:namespace />dialog');
									   dialog.show();
									   dialog.dialog({ modal : true, height : 100, width: 200, show: 'explode', hide: 'explode', resizable:false, draggable:false   });
									   var inputs = $("input, textarea, select", <portlet:namespace/>fm${workflow.id});
									   var url = '<portlet:actionURL windowState="exclusive"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${workflow.id}"/></portlet:actionURL>' + "&" + inputs.serialize();
									   $('#<portlet:namespace/>results${workflow.id}').load(url, 
											   function() {
										   			var dialog = jQuery('#<portlet:namespace />dialog');
										   			dialog.dialog('close');
										   			dialog.hide();
									   			} );
									});
						});
					</script>
				</div>
			</c:forEach>
		</div>
	</div>
	<div id="<portlet:namespace/>ajaxDiv"></div>
	<div id="<portlet:namespace />dialog"  title="Wait Dialog"    style="display: none;cursor:wait;">Submitting Workflow. Please Wait.</div>
	<div id="<portlet:namespace/>progress" ></div>
</div>
