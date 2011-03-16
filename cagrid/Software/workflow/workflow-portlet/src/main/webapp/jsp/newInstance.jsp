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
					   jQuery('#<portlet:namespace />dialog').dialog({ modal : true, height : 100  });
					   var inputs = $("input, textarea, select", <portlet:namespace/>form);
					   var url = '<portlet:actionURL windowState="exclusive"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.id}"/></portlet:actionURL>' + "&" + inputs.serialize();
					   $('#<portlet:namespace/>results').load(url, 
							   function() {
						   			console.log('AJAX Submittal returned.');
						   			jQuery('#<portlet:namespace />dialog').dialog('close');
					   			} );
					});
		});
	</script>
	
	<ul id="<portlet:namespace/>navigation"  class="navigation" class="menu" title="Navigation">
		<li class="menu-item"><a href="<portlet:renderURL windowState="normal" ><portlet:param name="action" value="viewInstances"/></portlet:renderURL>">View Submitted Workflows</a><br/></li>
		<li class="menu-item"><a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>">View Workflow Definitions</a></li>		
	</ul>
			
	<form id="<portlet:namespace/>fm" class="uni-form" action="#" method="post">
		<table border=0 cellspacing=2 cellpadding=2>
			<tr>
				<td>Workflow Name :</td><td> ${cmd.theWorkflow.name}</td>
			</tr>
			<tr>
				<td>Author:</td><td> ${cmd.theWorkflow.author}</td>
			</tr>
			<tr>
				<td>Description:</td> <td>${cmd.theWorkflow.description}</td>
			</tr>
			<tr><td  colspan="2">
					Workflow inputs:
			</td></tr>
			<c:forEach var="item" items="${cmd.theWorkflow.inputs}">
					<tr>
						<fieldset class="block-labels">
							<td><label  for="<portlet:namespace/>${item.name}">${item.name}</label></td>
							<td><textarea id="<portlet:namespace/>${item.name}"   name="inputValues" rows="2" cols="40">${item.name}</textarea></td>
						</fieldset>
					</tr>
			</c:forEach>
			<tr>
				<td>
					<input id="<portlet:namespace/>submitButton" type="submit" value="Submit"  title="Launch Workflow Instance"/>
				</td>
				<td>
					<div id="<portlet:namespace/>results" class="results"></div>					
				</td>
			</tr>
		</table>
		<TR>
			<TD>Workflow Diagram: </TD>
			<TD><img title="Workflow Definition Diagram" src="<c:url value="${cmd.theWorkflow.previewImageURI}"/>" /></TD>
		</TR>
	</form>
	<HR/>
	<div id="<portlet:namespace />dialog"  title="Wait Dialog"   class="hidden">
		<p>Please Wait</p>
	</div>
</div>