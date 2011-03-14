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
						   			jQuery()('#<portlet:namespace />dialog').dialog('close');
					   			} );
					});
		});
	</script>
	<form id="<portlet:namespace/>fm" class="uni-form" action="#" method="post">
		<table border=0 cellspacing=2 cellpadding=2>
			<tr><td>Portlet Namespace :</td><td> ${ns}</td></tr>
			<tr><td>URL :</td><td> ${newInstanceURL}</td></tr>
			<tr>
				<td>Workflow Name :</td><td> ${cmd.theWorkflow.name}</td>
			</tr>
			<tr>
				<td>Author:</td><td> ${cmd.theWorkflow.author}</td>
			</tr>
			<tr>
				<td>Description:</td> <td>${cmd.theWorkflow.description}</td>
			</tr>
			<tr><td rowspan="2">
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
			<tr><td>
				<input id="<portlet:namespace/>submitButton" type="submit" value="Submit"  title="Launch Workflow Instance"/>
			</td></tr>
		</table>
		<TR>
			<TD><font size="4">Workflow Image: </font></TD>
			<TD><img src="<c:url value="${cmd.theWorkflow.previewImageURI}"/>" width="100%" /></TD>
		</TR>
	</form>
	<HR/>
	<div id="<portlet:namespace/>results" class="result">
	</div>
	<div id="<portlet:namespace />dialog"  title="Wait Dialog">
		<p>Please Wait</p>
	</div>
</div>