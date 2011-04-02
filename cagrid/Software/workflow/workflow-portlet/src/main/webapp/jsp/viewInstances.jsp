<%@ include file="include.jsp" %>

<div class="content">
	<script type="text/javascript">
		jQuery(document).ready(function($) {
			ajaxPollingUpdate('${ajaxStatusURL} #<portlet:namespace/>ajaxDiv', '<portlet:namespace/>ajaxDiv' , true, 2);
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
								<button id="<portlet:namespace/>viewOutputButton"  onclick="jQuery('<portletnamespace/>output').html('${entry.value.output}')">View Output</button>
						</c:if>
					</TD>
				</TR>
		</c:forEach>
		</TABLE>
		</div>
		<div id="<portlet:namespace/>output" class="output">
		</div>
	</div>
	
</div>
