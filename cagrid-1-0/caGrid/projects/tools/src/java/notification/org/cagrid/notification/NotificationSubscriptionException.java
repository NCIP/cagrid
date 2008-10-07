package org.cagrid.notification;


public class NotificationSubscriptionException extends Exception {

    public NotificationSubscriptionException(String message) {
        super(message);
    }
    
    
    public NotificationSubscriptionException(Exception cause) {
        super(cause);
    }
    
    
    public NotificationSubscriptionException(String message, Exception cause) {
        super(message, cause);
    }
}
