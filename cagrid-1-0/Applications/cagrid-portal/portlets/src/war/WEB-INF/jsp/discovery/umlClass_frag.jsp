<table>
	<tbody>
		<tr>
			<td colspan="2">
				<input type="button" value="Select For Query" onclick="${prefix}selectUMLClass('${currNode.path}')"/>
			</td>
		</tr>
		<tr>
			<td>Package Name:</td>
			<td><c:out value="${umlClass.packageName}"/></td>
		</tr>
		<tr>
			<td>caDSR ID:</td>
			<td><c:out value="${umlClass.cadsrId}"/></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><c:out value="${umlClass.description}"/></td>
		</tr>
		<tr>
			<td>Project Name:</td>
			<td><c:out value="${umlClass.projectName}"/></td>
		</tr>
		<tr>
			<td>Project Version:</td>
			<td><c:out value="${umlClass.projectVersion}"/></td>
		</tr>
		<tr>
			<td>Superclass:</td>
			<td><c:out value="${umlClass.superClass.packageName}"/>.<c:out value="${umlClass.superClass.className}"/></td>
		</tr>
		
		<tr>
			<td>Sub Classes:</td>
			<td>
				<c:forEach var="subclass" items="${umlClass.subClasses}">
					<c:out value="${subclass.packageName}"/>.<c:out value="${subclass.className}"/><br/>
				</c:forEach>
			</td>
		</tr>
		
	</tbody>
</table>