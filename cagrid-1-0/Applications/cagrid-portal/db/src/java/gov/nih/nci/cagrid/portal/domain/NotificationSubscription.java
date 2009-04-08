package gov.nih.nci.cagrid.portal.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */

@Entity
@Table(name = "notification_subscriptions")
@GenericGenerator(name = "id-generator", strategy = "native",
        parameters = {
                @Parameter(name = "sequence", value = "seq_noti_subscriptions")
        }
)
public class NotificationSubscription extends AbstractDomainObject {

    private GridService service;
    private NotificationSubscriber subscriber;
    private String statusesContainer;
    private final String TOKEN = ":";

    @ManyToOne
    @JoinColumn(name = "service_id")
    public GridService getService() {
        return service;
    }

    public void setService(GridService service) {
        this.service = service;
    }

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    public NotificationSubscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(NotificationSubscriber subscriber) {
        this.subscriber = subscriber;
    }


    @Transient
    public Set<ServiceStatus> getStatuses() {
        Set<ServiceStatus> _statuses = new HashSet<ServiceStatus>();

        if (statusesContainer != null) {
            StringTokenizer _sTokenizer = new StringTokenizer(statusesContainer, TOKEN);
            while (_sTokenizer.hasMoreElements()) {
                _statuses.add(ServiceStatus.valueOf((String) _sTokenizer.nextElement()));
            }
        }
        return _statuses;
    }

    public void setStatuses(Set<ServiceStatus> status) {
        if (status != null) {
            StringBuilder _statuses = new StringBuilder();

            for (ServiceStatus _status : status) {
                _statuses.append(TOKEN);
                _statuses.append(_status.toString());
            }
            statusesContainer = _statuses.toString();
        }
    }


    protected String getStatusesContainer() {
        return statusesContainer;
    }

    protected void setStatusesContainer(String statusesContainer) {
        this.statusesContainer = statusesContainer;
    }
}
