<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<portlet:actionURL var="action"/>
<form:form action="${action}" commandName="mapBean">
	<b>Categories:</b>&nbsp;
	<form:select onchange="submit()" path="selectedDirectory">
		<form:option value="">----</form:option>
		<%@ include file="/WEB-INF/jsp/disc/directory/directories.jspf" %>
	</form:select>
	<input type="hidden" name="operation" value="selectDirectory"/>
</form:form>

<table width="97%"  border="0" cellpadding="0" cellspacing="0" style="border: 1px solid #996633; position:relative; left:11px; margin-top:7px; margin-bottom: 5px;">
	<tr>
		<td style="padding-left:10px">
			<table width="100%"  border="0">
            	<tr>
                	<td width="5%" style="padding:2px"><img src="<c:url value="/images/data-services.gif"/>" height="20" /></td>
					<td width="30%" style="padding:2px" nowrap="nowrap">Data Service </td>
					<td width="5%" style="padding:2px"><img src="<c:url value="/images/participant_institute.gif"/>" height="20" /></td>
					<td width="35%" nowrap="nowrap">Participant Institute </td>
					<td width="5%" style="padding:2px"><img src="<c:url value="/images/participant_POC.gif"/>" height="20" /></td>
					<td width="20%" style="padding:2px" nowrap="nowrap">Point of Contact</td>
				</tr>
				<tr>
					<td style="padding:2px"><img src="<c:url value="/images/analytical_services.gif"/>" height="20" /></td>
					<td nowrap="nowrap" style="padding:2px">Analytical Service </td>
					<td style="padding:2px"><img src="<c:url value="/images/hosting-research-center.gif"/>" height="20" /></td>
					<td nowrap="nowrap" style="padding:2px">Hosting Research Center </td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<c:set var="selectItemOperationName" value="selectItemForDiscovery"/>
<c:set var="selectItemsOperationName" value="selectItemsForDiscovery"/>
<%@ include file="/WEB-INF/jsp/map/map.jspf" %>
