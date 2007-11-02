<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/query/tabs.jspf" %>

<script type="text/javascript"><!--
/*
 * resizehandle.js (c) Fil 2007, plugin pour jQuery ecrit
 * a partir du fichier resize.js du projet DotClear
 * (c) 2005 Nicolas Martin & Olivier Meunier and contributors
 */
jQuery.fn.<portlet:namespace/>cqlXmlresizehandle = function() {
  return this.each(function() {
    var me = jQuery(this);
    me.after(
      jQuery('<div class="<portlet:namespace/>cqlXmlresizehandle"></div>')
      .bind('mousedown', function(e) {
        var h = me.height();
        var y = e.clientY;
        var moveHandler = function(e) {
          me
          .height(Math.max(20, e.clientY + h - y));
        };
        var upHandler = function(e) {
          jQuery('html')
          .unbind('mousemove',moveHandler)
          .unbind('mouseup',upHandler);
        };
        jQuery('html')
        .bind('mousemove', moveHandler)
        .bind('mouseup', upHandler);
      })
    );
  });
}


jQuery(document).ready(function(){
  jQuery("#<portlet:namespace/>cqlXml").<portlet:namespace/>cqlXmlresizehandle();
});
// --></script>

<style>
.<portlet:namespace/>cqlXmlresizehandle {
	background:transparent url("http://www.jquery.info/images/resizer.png") no-repeat scroll 45%;
	cursor:s-resize;
	font-size:0.1em;
	height:16px;
	width:100%;
}
</style>

<portlet:actionURL var="action" />
<form:form action="${action}" commandName="cqlQueryCommand">
	<input type="hidden" name="operation" value="submitQuery"/>
	<form:errors path="*"/>
	<table>
		<tr>
			<td>URL</td>
			<td><form:input path="dataServiceUrl" size="100"/><br/>
				<form:errors path="dataServiceUrl"/></td>
		</tr>
		<tr>
			<td valign="top">Query</td>
			<td>
				<div style="width:500px;">
				<c:set var="cqlXmlElId"><portlet:namespace/>cqlXml</c:set>
				<form:textarea id="${cqlXmlElId}" cssStyle="width:100%; height:200px" path="cqlQuery"/>
				</div>
			<br/>
			<form:errors path="cqlQuery"/>
			</td>				
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="Submit Query" />
			</td>
		</tr>
	</table>
</form:form>
