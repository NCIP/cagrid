package gov.nih.nci.security.authentication;

/**
 * Delegates everything useful to the Better Lockout Manager
 * 
 * @author ervin
 */
public class LockoutManager {

    // the real implementation of the lockout manager
    private BetterLockoutManager delegate = null;
    
    // the static lockout manager singleton instance
    private static LockoutManager lockoutManager = null;


    private LockoutManager(String lockoutTime, String allowedLoginTime, String allowedAttempts) {
        long lockoutTimeValue = Long.parseLong(lockoutTime);
        long allowedLoginTimeValue = Long.parseLong(allowedLoginTime);
        int allowedAttemptsValue = Integer.parseInt(allowedAttempts);
        delegate = new BetterLockoutManager(lockoutTimeValue, allowedAttemptsValue, allowedLoginTimeValue);
    }


    public static void initialize(String lockoutTime, String allowedLoginTime, String allowedAttempts) {
        if (null == lockoutManager) {
            lockoutManager = new LockoutManager(lockoutTime, allowedLoginTime, allowedAttempts);
        }
    }
    
    
    public static void initialize(String lockoutTime, String allowedLoginTime, String allowedAttempts, 
        @SuppressWarnings("unused") String lockoutPurgeTime) {
        initialize(lockoutTime, allowedLoginTime, allowedAttempts);
    }


    public static LockoutManager getInstance() {
        if (null == lockoutManager) {
            // Initialize with the following defaults
            lockoutManager = new LockoutManager("1800000", "60000", "3");
        }
        return lockoutManager;
    }


    public boolean isUserLockedOut(String userId) {
        return delegate.isUserLockedOut(userId);
    }


    public boolean setFailedAttempt(String userId) {
        return delegate.setFailedAttempt(userId);
    }
    
    
    public BetterLockoutManager getDelegatedLockoutManager() {
        return delegate;
    }
}