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
								//var id = ui.newHeader[0].id;
								//var url = ui.newContent[0].title + " #<portlet:namespace/>inputs"+id;
								//jQuery('#<portletnamespace/>inputs'+id).load(url);
							}
				 	} );
			ajaxPollingUpdate('${ajaxStatusURL} #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , true, 2);
		});
	</script>
	
	<h3>Workflow Portlet</h3>
	
	<div id="<portlet:namespace/>scrollPanel"  class="standard"    style="overflow: scroll; height:400px;">
		<div id="<portlet:namespace/>accordion" >
			<c:forEach var="workflow" items="${workflows}">
				<h3 id="${workflow.id}"><a href="#">ID# ${workflow.id} - ${workflow.name}</a></h3>
				<div id="<porlet:namespace/>content${workflow.id}"  title="<portlet:renderURL windowState='exclusive' > <portlet:param  name='action' value='newInstance' /><portlet:param  name='id' value='${workflow.id}'/></portlet:renderURL>">
					
						<table border=0>
							<tr><td align="center"  colspan="2"><h3>${workflow.name}</h3></td></tr>
							<tr><td rowspan="2"><img width="200" src="<c:url value="${workflow.previewImageURI}"/>" /></td><td>Author: ${workflow.author}  </td></tr>
							<tr><td>${workflow.description}</td></tr>
							
							<tr><td><a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="newInstance"/><portlet:param  name='id' value='${workflow.id}'/></portlet:renderURL>">Details</a></td></tr>
						</table>
				</div>
			</c:forEach>
		</div>
	</div>
	<div id="<portlet:namespace/>ajaxDiv"></div>
</div>
