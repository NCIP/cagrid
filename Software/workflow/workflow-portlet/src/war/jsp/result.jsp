<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<style type="text/css">
    <%@ include file="/css/base.css" %>
</style>



<div class="content">
     <div class="result">
           <c:if test="${cmd.formState == 2}">
                <span style="font-weight:bold; font-color:blue">
				<font color="blue" size="2">${cmd.result}</font><BR>
				<hr/>
				</span>
			<form action="<portlet:actionURL/>" method="post">
			<input type="hidden" name="formState" value="3"		
			<input type="submit" value="Views All Submitted Workflows">
			</form>
         </c:if>

			<c:if test="${cmd.result == null}">
				<span style="font-weight:bold; font-color:blue">
				Something went wrong.<BR>
				<hr/>
				</span>
			</c:if>
        </div>
</div>