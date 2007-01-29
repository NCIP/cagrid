<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>

<f:loadBundle basename="Portal-Labels" var="labels"/>

<h:panelGrid columns="2" style="height:100%;" cellpadding="0" cellspacing="0" border="0">
    <h:column>

        <%-- welcome begins --%>
        <h:panelGrid style="height:100%;" cellpadding="0" cellspacing="0" border="0"
                     headerClass="welcomeTitle" columnClasses="welcomeContent">
            <f:facet name="header">
                <h:column>
                    <h:outputText value="#{labels.portalHomeTitle}"/>
                </h:column>
            </f:facet>

            <h:column>
                <h:outputText value="#{labels.portalHomeDescription}"/>
<f:verbatim>
</br>
The tool provides visusal display of services on the caGrid infrastructure and also institutions that are participating in the caBIG program.
</br>
</f:verbatim>
<f:verbatim>
<!-caGrid 1.0 Technical Details start -->
    <p>
    Significant number of enhancements has been incorporated into the caGrid 1.0 infrastructure. To mention a few highlights:
    </p>
<ul>
    <ul>
    <li>
    Migrating the underlying infrastructure for supporting services using standard web service resource framework (WSRF) specification
    </li>
    <li>
    Complete overhaul of federated security infrastructure to satisfy caBIG security needs, incorporating many of the recommedations made in the <a href="https://cabig.nci.nih.gov/workspaces/Architecture/Security_Tech_Eval_White_Paper/" target="blank">caBIG Security White Paper</a>
    </li>
    <li>
    New workflow capabilities to enable orchestration of services using industry standard Business Process Execution Language (BPEL)
    </li>
    <li>
    New Federated Query Processing (FQP) capability built in collaboration with the Cancer Translational Research Informatics Platform (caTRIP) project, a caBIG funded project
    </li>
    <li>
    Performance and scalability improvements to the services by implementing specifications such as <a href="http://www.w3.org/Submission/WS-Enumeration/" target="_blank"><i>WS-Enumeration</i></a> into the underlying Globus Toolkit infrastructure
    </li>
    <li>
    Provision for grid wide object identifier support capability by integrating with The Handle System® service from Corporation for National Research Initiatives
    </li>
    <li>
    Extensive enhacements made to the metadata infrastructure, including standard grid service APIs to Global Model Exchange (GME), Cancer Data Standards Repository (caDSR) and Enterprise Vocabulary Service (EVS)
    </li>
    <li>
    Tighter integration with NCICB components used by caBIG funded projects including Common Security Module (CSM) and caCORE Software Development Kit (SDK)
    </li>
    <li>
    Development of extensive automated system testing framework to validate various components of the infrastructure
    </li>
</ul>
</ul>
</f:verbatim>

<f:verbatim>
    <p>
    In addition to the above mentioned highlights, caGrid 1.0 infrastructure contains the following tools:
    </p>
    <p>
    <b>Introduce Toolkit</b>: is a service creation toolkit built by caGrid team. It supports easy developement and deployment of caBIG compatible grid enabled data and analytical services. Introduce toolkit reduces the service developers needing to manage the low level details of the WSRF specification and integration with the Globus Toolkit.
    </p>
    <p>
    <b>Grid Authentication and Authorization of Reliably Distributed Services (GAARDS)</b>:  provides services and tools for grid wide administration and security enforcement for services that are deployed on caGrid infrastructure.  GAARDS consists of following security components:
    </p>
    <ul>
      <ul>
    <li>
    <b>Dorian</b>:  allows for the provision and management of user accounts, providing an integration point between external security domains and the grid.
    </li>
    <li>
    <b>Grid Grouper</b>: provides a group-based authorization solution, wherein grid services and applications enforce authorization policy based upon group memberships defined and managed at the grid level.
    </li>
    <li>
    <b>Grid Trust Services(GTS)</b>: provides a mechanism for maintaining and provisioning a federated trust fabric of certified authorities in caGrid
    </li>
      </ul>
    </ul>
</f:verbatim>
            </h:column>
        </h:panelGrid>
        <%-- welcome ends --%>
    </h:column>

    <h:column>
        <%-- sidebar begins --%>
        <h:panelGrid style="height:100%;" cellpadding="0" cellspacing="0" border="0">
            <%-- what's new begins --%>
            <h:column>
                <h:panelGrid columnClasses="sidebarContent" headerClass="sidebarTitle" style="height:100%"
                             styleClass="sidebarSection" summary="" cellpadding="0" cellspacing="0" border="0"
                             width="100%">
                    <f:facet name="header">
                        <h:column>
                            <h:outputText value="#{labels.whatsNewTitle}"/>
                        </h:column>
                    </f:facet>

                    <h:column>
                        <h:outputText value="#{labels.whatsNewDescription} " />
                        <h:outputLink value="http://www.cagrid.org">
                            <h:outputText value="#{labels.cagridwiki}"/>
                        </h:outputLink>
                    </h:column>

                </h:panelGrid>
            </h:column>
            <%-- what's new ends --%>

            <%-- did you know? begins --%>
            <h:column>
                <h:panelGrid style="width:100%;height:100%;" styleClass="sidebarSection"
                             headerClass="sidebarTitle" columnClasses="sidebarContent"
                             summary="" cellpadding="2" cellspacing="2" border="0" width="100%">
                    <f:facet name="header">
                        <h:column>
                            <h:outputText value="#{labels.didYouKnowTitle}"/>
                        </h:column>
                    </f:facet>

                    <h:column>
                        <h:outputText value="There are"/>
                        <h:commandLink
                                action="#{directory.navigateToServicesDirectory}">

                            <h:outputText value=" #{services.count} #{labels.services}"
                                          styleClass="sidebarContentLink"/>
                        </h:commandLink>
                        <h:outputText value=" published on caGrid Index Service"/>
                        <f:verbatim><br/></f:verbatim>
                        <h:outputText value="and a total of"/>

                        <h:commandLink
                                action="#{directory.navigateToParticipantDirectory}">
                            <h:outputText value=" #{participants.count} organizations" styleClass="sidebarContentLink"/>
                        </h:commandLink>
                        <h:outputText value=" linked via the caGrid portal."/>
                    </h:column>
                </h:panelGrid>
            </h:column>
            <%-- did you know? ends --%>


        </h:panelGrid>
        <%-- sidebar ends --%>
    </h:column>
</h:panelGrid>
