<%@ include file="include.jsp" %>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<div id="div" class="content">
	<script type="text/javascript">
		jQuery(function($) {
			var <portlet:namespace/>form = $('#<portlet:namespace />fm');
			<portlet:namespace/>form.bind("submit",
					function(event){
					   event.preventDefault();
					   var dialog = jQuery('#<portlet:namespace />dialog');
					   dialog.toggle();
					   dialog.dialog({ modal : true, height : 100, width: 200, show: 'explode', hide: 'explode', resizable:false, draggable:false   });
					   var inputs = $("input, textarea, select", <portlet:namespace/>form);
					   var url = '<portlet:actionURL windowState="exclusive"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.id}"/></portlet:actionURL>' + "&" + inputs.serialize();
					   $('#<portlet:namespace/>results').load(url, 
							   function() {
						   			console.log('AJAX Submittal returned.');
						   			var dialog = jQuery('#<portlet:namespace />dialog');
						   			dialog.dialog('close');
						   			dialog.toggle();
					   			} );
					});
		});
	</script>
	
	<div id="workflow-portlet-menu">
		<ul>
			<li title="View Submitted Workflows" ><a href="${viewInstancesURL}">View Submitted Workflows</a><br/></li>
			<li title="View Workflow Definitions" ><a href="${viewDefinitionsURL}">View Workflow Definitions</a></li>		
		</ul>
	</div>
			
	<form id="<portlet:namespace/>fm" class="uni-form" action="#" method="post">
		<table border=0 cellspacing=2 cellpadding=2>
			<tr><td>Workflow Name :</td><td> ${cmd.theWorkflow.name}</td></tr>
			<tr><td>Author:</td><td> ${cmd.theWorkflow.author}</td></tr>
			<tr><td>Description:</td> <td>${cmd.theWorkflow.description}</td></tr>
			<tr><td  colspan="2">Workflow inputs:</td></tr>
			<c:forEach var="item" items="${cmd.theWorkflow.inputs}">
					<tr>
						<fieldset class="block-labels">
							<td><label  for="<portlet:namespace/>${item.name}">${item.name}</label></td>
							<td><textarea id="<portlet:namespace/>${item.name}"   name="inputValues" rows="2" cols="40">${item.name}</textarea></td>
						</fieldset>
					</tr>
			</c:forEach>
			<tr><td><input id="<portlet:namespace/>submitButton" type="submit" value="Submit"  title="Launch Workflow Instance"/></td><td><div id="<portlet:namespace/>results" class="results"></div></td></tr>
		</table>
		<TR><TD>Workflow Diagram: </TD><TD><img title="Workflow Definition Diagram" src="<c:url value="${cmd.theWorkflow.previewImageURI}"/>" /></TD></TR>
	</form>
	<HR/>
	
	<div id="<portlet:namespace />dialog"  title="Wait Dialog"   style="visibility: hidden">Submitted Workflow.  Please Wait...</div>
</div>