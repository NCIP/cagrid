<%@ include file="include.jsp" %>

<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>

<div id="div" class="content">

	<div id="workflow-portlet-menu" >
		<ul class="ui-menu">
			<li class="ui-menu-item" title="View Submitted Workflows" ><a href="${viewInstancesURL}">Workflow Status</a><br/></li>
			<li class="ui-menu-item"  title="View Workflow Definitions" ><a href="${viewDefinitionsURL}">WorkflowDefinitions</a></li>		
		</ul>
	</div>
	<br/>
	<div id="<portlet:namespace/>fmDiv">
		<form id="<portlet:namespace/>fm${cmd.theWorkflow.id}" class="uni-form" action="#" method="post">
			<table border=0 cellspacing=2 cellpadding=2>
				<tr><td colspan="2"><h3>${cmd.theWorkflow.name}</h3></td></tr>
				<tr><TD rowspan="2"><img title="Workflow Definition Diagram"   width="200"  src="<c:url value="${cmd.theWorkflow.previewImageURI}"/>" /></TD><td>Author: ${cmd.theWorkflow.author}</td></tr> 
				<tr><td>${cmd.theWorkflow.description}</td></tr>
			
				<div id="<portlet:namespace/>inputs${cmd.theWorkflow.id}">
					<tr><td  colspan="2">Inputs:</td></tr>
					<c:forEach var="item" items="${cmd.theWorkflow.inputs}">
							<tr>
								<fieldset class="block-labels">
									<td><label  for="<portlet:namespace/>${item.name}">${item.name}</label></td>
									<td><textarea  id="<portlet:namespace/>${item.name}"  name="inputValues" >${item.example}</textarea></td>
								</fieldset>
							</tr>
					</c:forEach>
					</div>
			
				<tr><td><input id="<portlet:namespace/>submitButton" type="submit" value="Submit"  title="Launch Workflow Instance"/></td><td><div id="<portlet:namespace/>results${cmd.theWorkflow.id}" class="results"></div></td></tr>
			</table>
		</form>
		<script type="text/javascript">
			jQuery(function($) {
				var <portlet:namespace/>fm${cmd.theWorkflow.id} =$('#<portlet:namespace/>fm${cmd.theWorkflow.id}');
				<portlet:namespace/>fm${cmd.theWorkflow.id}.bind("submit",
						function(event){
						   event.preventDefault();
						   var dialog = jQuery('#<portlet:namespace />dialog');
						   dialog.show();
						   dialog.dialog({ modal : true, height : 100, width: 200, show: 'explode', hide: 'explode', resizable:false, draggable:false   });
						   var inputs = $("input, textarea, select", <portlet:namespace/>fm${cmd.theWorkflow.id});
						   var url = '<portlet:actionURL windowState="exclusive"><portlet:param name="action" value="newInstance"/><portlet:param name="id" value="${cmd.theWorkflow.id}"/></portlet:actionURL>' + "&" + inputs.serialize();
						   $('#<portlet:namespace/>results${cmd.theWorkflow.id}').load(url, 
								   function() {
							   			var dialog = jQuery('#<portlet:namespace />dialog');
							   			dialog.dialog('close');
							   			dialog.hide();
							   			portlet_util.ajaxPollingUpdate('${ajaxStatusURL} #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , true, 2);
						   			} );
						});
			});
		</script>
	</div>
	<div id="<portlet:namespace/>ajaxDiv"></div>
	<div id="<portlet:namespace />dialog"  title="Please Wait"  style="display: none;cursor:wait;">Submitting Workflow. Please Wait.</div>
	<div id="<portlet:namespace/>progress" ></div>
</div>