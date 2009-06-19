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
</script>


<form:form id="searchForm" method="post">
    <div class="searchBox">
        <div>
		
            <input type="hidden" name="searchKeyword" id="searchKeyword"/>
            <input type="hidden" name="operation" id="operation" value="catalogSearch">

            Search: <input id="keyword" type="text" size="20" onkeypress="return search(event);"/>
            
        </div>
    </div>
</form:form>
