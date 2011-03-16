<%@ include file="include.jsp" %>

<div class="content">
	<script type="text/javascript">
		jQuery(function($) {
			ajaxPollingUpdate('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstancesNoAJAX"/></portlet:renderURL>', '<portlet:namespace/>ajaxDiv' , true, 2);
		});
	</script>
	<ul id="<portlet:namespace/>navigation"  class="navigation" class="menu" title="Navigation">
		<li class="menu-item"><a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewDefinitions"/></portlet:renderURL>">View Workflow Definitions</a></li>		
	</ul>
	<div id="<portlet:namespace/>ajaxDiv" >
	</div>
</div>
