<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>


<f:subview id="searchResults">

    <t:panelTabbedPane activeTabStyleClass="activeTab"
                       inactiveTabStyleClass="inactiveTab"

                       styleClass="tabbedPane"
                       width="80%"
            >

        <t:panelTab id="services" label="Registered Service  (#{services.listSize})">
            <tiles:insert attribute="serviceSearchResult" flush="false" ignore="true"/>
        </t:panelTab>

        <t:panelTab id="rc" label="Research Center (#{centers.listSize})">
            <tiles:insert attribute="rcSearchResult" flush="false" ignore="true"/>
        </t:panelTab>


        <t:panelTab id="poc" label="People (#{people.listSize})">
            <tiles:insert attribute="pocSearchResult" flush="false" ignore="true"/>
        </t:panelTab>

    </t:panelTabbedPane>
</f:subview>
