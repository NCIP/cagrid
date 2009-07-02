<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>

<c:set var="browseURL"><portlet:actionURL/></c:set>


<script type="text/javascript">
function search(e){ //e is event object passed from function invocation
	var characterCode //literal character code will be stored in this variable
	if(e && e.which){ //if which property of event object is supported (NN4)
		e = e;
		characterCode = e.which;//character code is contained in NN4's which property
	} else {
		e = event
		characterCode = e.keyCode; //character code is contained in IE's keyCode property
	}
	if(characterCode == 13){ //if generated character code is equal to ascii 13 (if enter key)
		$("searchKeyword").value = $('keyword').value;
        $("searchForm").action = "${browseURL}";
        $("searchForm").submit();
		return false;
	} else {
		return true
	}
}

jQuery("input.search").click(function(){
	if (jQuery(this).attr("value") == "Search") {
		jQuery(this).css({'color': '#000000'});
		jQuery(this).attr("value", "");
	}
});
jQuery("input.search").blur( function() {
	if (jQuery(this).attr("value")== "") {
		jQuery(this).attr("value", "Search");
		jQuery(this).css({'color' : '#afafaf'});
	}
});		
</script>


<form:form id="searchForm" method="post">
    <div class="searchBox">
        
		
            <input type="hidden" name="searchKeyword" id="searchKeyword"/>
            <input type="hidden" name="operation" id="operation" value="catalogSearch">
			<div class="L-endcap"></div>
            <input id="keyword" class="search" type="text" size="20" value="Search" style="color:#afafaf;" onkeypress="return search(event);"/>
            <%--<div class="R-endcap"></div>--%>
        
    </div>
</form:form>
