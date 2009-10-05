<%@ include file="/WEB-INF/jsp/include/includes.jspf" %>
<script type='text/javascript'
        src='/cagridportlets/dwr/interface/CredentialManagerFacade.js'></script>
<script type='text/javascript' src='/cagridportlets/dwr/engine.js'></script>
<script type='text/javascript' src='/cagridportlets/dwr/util.js'></script>


<tags:yui-minimum/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/yui/container/assets/skins/sam/container.css"/>"/>

<script type="text/javascript" src="<c:url value="/js/yui/button/button-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/yui/container/container-min.js"/>"></script>

<c:set var="ns"><portlet:namespace/></c:set>

<div align="left" class="yui-skin-sam">
    <c:choose>
        <c:when test="${empty portalUser}">

            If you have an NIH username and password, then
            select the NCICB AuthenticationService IdP before pressing the Log
            In button. If you do not have an NIH username and password, but you
            have already registered through the portal (or through the GAARDS UI), then select the
            Dorian Identity Provider. If you have not yet registered, you may do so by
            clicking <a href="${ns}showRegisterDialog();">here</a>.
            <br/>
            <br/>

            <portlet:renderURL var="portalAuthnUrl"/>
            <portlet:actionURL var="action">
                <portlet:param name="operation" value="login"/>
            </portlet:actionURL>

            <form:form id="${ns}LoginForm" commandName="directLoginCommand" method="POST">
                <c:if test="${!empty authnErrorMessage}">
                    <span style="color:red"><c:out value="${authnErrorMessage}"/></span>
                </c:if>
                <span style="color:red"><form:errors path="*"/></span>
                <table>
                    <tr>
                        <td style="padding-right:5px; text-align:right">Username:</td>
                        <td><form:input path="username" size="29"/></td>
                    </tr>
                    <tr>
                        <td style="padding-right:5px; text-align:right;">Password:</td>
                        <td><form:password path="password" size="29"/></td>
                    </tr>
                    <tr>
                        <td style="padding-right:5px; text-align:right;">Identity Provider:</td>
                        <td>
                            <input name="idpUrl" id="idpUrl" type="hidden"/>
                            <select id="${ns}idpSelect">
                                <option>Loading...</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td style="padding-top:10px;">

                            <span id="${ns}loginButtonContainer"></span>

                        </td>
                    </tr>
                </table>
                <input type="hidden" name="portalAuthnUrl" value="<c:out value="${portalAuthnUrl}"/>"/>
            </form:form>


        </c:when>
        <c:otherwise>
            <a href="/web/guest/home" style="text-decoration:none;">&lt;&lt; To Full Page</a><br/><br/>
            <%@ include file="/WEB-INF/jsp/directauthn/greeting.jspf" %>
        </c:otherwise>
    </c:choose>

</div>

<script language="JavaScript">

    ${ns}loginButton = null;
    
    function ${ns}listIdPs() {

        CredentialManagerFacade.listIdPsFromDorian(
        {
            callback: function(idps) {

                var idpOpts = "";
                for (var i = 0; i < idps.length; i++) {
                    var idpBean = idps[i];
                    idpOpts += "<option value='" + idpBean.url + "'" + (i == 0 ? " selected" : "") + ">" + idpBean.label + "</option>";
                }
                jQuery("#${ns}idpSelect").html(idpOpts);
              	${ns}loginButton.set("disabled", false);


            },
            errorHandler: function(errorString, exception) {
                alert("Error listing identity providers: " + errorString);
            }
        });
    }

    jQuery(document).ready(function() {


        ${ns}loginButton = new YAHOO.widget.Button({
            label: "Login",
            id: "${ns}loginButton",
            disabled : true,
            container: "${ns}loginButtonContainer"
        });


        ${ns}loginButton.on("click", function(evt) {
            jQuery("#idpUrl").val(jQuery("#${ns}idpSelect :selected").val());
            jQuery("#${ns}LoginForm").attr("action", "${action}");
            jQuery("#${ns}LoginForm").submit();
        }
                );

        ${ns}listIdPs();
    });

</script>