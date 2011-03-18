<%@ include file="include.jsp" %>

<div class="content">
	<script type="text/javascript">
		jQuery(function($) {
			ajaxPollingUpdate('<portlet:renderURL windowState="exclusive"><portlet:param name="action" value="viewInstances"/></portlet:renderURL> #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , true, 2);
		});
	</script>
	
	<ul  class="menu" >
		<li class="menu-item"  title="View Workflow Definitions" ><a href="${viewDefinitionsURL}">View Workflow Definitions</a></li>		
	</ul>
	
	<div id="<portlet:namespace/>ajaxDiv" >
		<TABLE class="statusTable">
			<TR  class="statusTableHeader"><TH> Job ID </TH><TH> Workflow ID </TH><TH> Status </TH><TH> Output </TH></TR>
			<c:if test="${isEmpty}"><tr><td colspan="4">No Active Workflow Instances..</td></tr></c:if>
			<c:forEach var="entry" items="${eprs}">
				<TR><TD>${entry.key}</TD><TD>${entry.value.workflowDesc.id }</TD><TD><b><font size="2" > ${entry.value.status}</font></b></TD><TD>
						<c:if test="${entry.value.status == 'Done'}">
								<a href="<portlet:renderURL windowState="normal"><portlet:param name="action" value="viewOutput"/><portlet:param name="uuid" value="${entry.key}"/></portlet:renderURL>">View Output</a>
						</c:if>
					</TD>
				</TR>
		</c:forEach>
		</TABLE>
		</div>
	</div>
	
</div>
