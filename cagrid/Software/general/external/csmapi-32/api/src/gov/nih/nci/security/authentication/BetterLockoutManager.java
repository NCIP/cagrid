package gov.nih.nci.security.authentication;

import gov.nih.nci.security.constants.Constants;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * BetterLockoutManager
 * @author ervin
 */
public class BetterLockoutManager {
    
    private static Logger LOG = Logger.getLogger(BetterLockoutManager.class);
    
    // A map of users to a queue of their failed login attempt times
    private Map<String, Deque<Long>> failedLogins = null;
    // A map of users to the time at which they will be unlocked
    private Map<String, Long> lockedOutUsers = null;
    // the amount of time a user is locked out for
    private long lockoutDuration;
    // the maximum number of failed login attempts in _attemptMemoryDuration_ time a user may have before being locked out
    private int maxFailedAttempts;
    // the amount of time in which _maxFailedAttempts_ failed logins must occur to trigger a lockout
    private long attemptMemoryDuration;
    // a flag that indicates the lockout manager is disabled
    private boolean disabled = false;
    
    private static BetterLockoutManager instance = null;
    
    public static void initialize(String lockoutTime, String allowedLoginTime, String allowedAttempts) {
        if (null == instance) {
            long lockoutTimeValue = Long.parseLong(lockoutTime);
            int allowedAttemptsValue = Integer.parseInt(allowedAttempts);
            long allowedLoginTimeValue = Long.parseLong(allowedLoginTime);
            instance = new BetterLockoutManager(lockoutTimeValue, allowedAttemptsValue, allowedLoginTimeValue);
        }
    }


    public static void initialize(String lockoutTime, String allowedLoginTime, String allowedAttempts,
        @SuppressWarnings("unused") String lockoutPurgeFrequency) {
        // lockout purge frequency is ignored - this implementation checks locks whenever
        // a new failed login occurs or a query is made about a user's lockout status
        initialize(lockoutTime, allowedAttempts, allowedLoginTime);
    }


    public static BetterLockoutManager getInstance() {
        // Initialize with the following defaults
        initialize(Constants.LOCKOUT_TIME, Constants.ALLOWED_LOGIN_TIME, Constants.ALLOWED_ATTEMPTS);
        return instance;
    }
    
    
    /**
     * Most functions of the CSM use getInstance() to obtain a singleton lockout manager.
     * This constructor should be only used in settings where the lockout manager instance
     * itself needs to be managed distinctly from the central one that CSM creates and uses.
     * @param lockoutDuration
     * @param maxFailedAttempts
     * @param attemptMemoryDuration
     */
    public BetterLockoutManager(long lockoutDuration, int maxFailedAttempts, long attemptMemoryDuration) {
        this.failedLogins = Collections.synchronizedMap(new HashMap<String, Deque<Long>>());
        this.lockedOutUsers = Collections.synchronizedMap(new HashMap<String, Long>());
        this.lockoutDuration = lockoutDuration;
        this.maxFailedAttempts = maxFailedAttempts;
        this.attemptMemoryDuration = attemptMemoryDuration;
        LOG.debug("Lockout Manager initialized: lockoutDuration = " + lockoutDuration + 
            ", maxFailedAttempts = " + maxFailedAttempts + 
            ", failedAttemptDuration = " + attemptMemoryDuration);
        if (lockoutDuration == 0 || maxFailedAttempts == 0 || attemptMemoryDuration == 0) {
            disabled = true;
            LOG.debug("Lockouts disabled due to initialization with a 0 value");
        }
    }

    /**
     * Determines if the user is currently locked out
     * 
     * @param userId
     * @return
     *      True if the user is locked out, false otherwise
     */
    public boolean isUserLockedOut(String userId) {
        boolean locked = true;
        if (!disabled) {
            LOG.debug("Checking if " + userId + " is locked out");
            revalidateLockouts();
            locked = lockedOutUsers.containsKey(userId);
            LOG.debug("\tUser is " + (locked ? "" : "not ") + "locked");
        } else {
            locked = false;
        }
        return locked;
    }
    
    
    /**
     * Informs the lockout manager that a user has failed to log in.
     * 
     * @param userId
     * @return
     *      True if the user gets locked out, false otherwise
     */
    public boolean setFailedAttempt(String userId) {
        if (!disabled) {
            LOG.debug("Setting failed attempt for user " + userId);
            revalidateLockouts();
            Deque<Long> failedAttempts = failedLogins.get(userId);
            if (failedAttempts == null) {
                failedAttempts = new LinkedList<Long>();
                failedLogins.put(userId, failedAttempts);
            }
            failedAttempts.add(Long.valueOf(System.currentTimeMillis()));
            LOG.debug("\tUser has " + failedAttempts.size() + " failed attempts on record");
        }
        return isUserLockedOut(userId);
    }
    
    
    /**
     * Gets the locked out users in a Map.  The map keys are
     * user IDs, and the values are the times at which the
     * corresponding user will be unlocked.
     * 
     * @return
     */
    public Map<String, Date> getLockedOutUsers() {
        Map<String, Date> lockouts = new HashMap<String, Date>();
        synchronized (lockedOutUsers) {
            for (String user : lockedOutUsers.keySet()) {
                long unlockTime = lockedOutUsers.get(user).longValue();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(unlockTime);
                Date unlockDate = cal.getTime();
                lockouts.put(user, unlockDate);
            }
        }
        return lockouts;
    }
    
    
    /**
     * Releases any lock for the user and clears
     * the failed logins for the user
     * 
     * @param userId
     */
    public synchronized void releaseLockout(String userId) {
        lockedOutUsers.remove(userId);
        failedLogins.remove(userId);
    }
    
    
    private synchronized void revalidateLockouts() {
        LOG.debug("Revalidating lockouts");
        long now = System.currentTimeMillis();
        // clean out any failed logins that are older than _attemptMemoryDuration_
        for (String userId : failedLogins.keySet()) {
            Deque<Long> attempts = failedLogins.get(userId);
            Iterator<Long> reverseAttempts = attempts.descendingIterator();
            while (reverseAttempts.hasNext()) {
                Long attemptTime = reverseAttempts.next();
                if ((now - attemptTime.longValue()) > attemptMemoryDuration) {
                    LOG.debug("Forgetting old failed login attempt for " + userId);
                    reverseAttempts.remove();
                }
            }
            // if the user still has more than _maxFailedAttempts_ bad logins, they get locked out iff they're not ALREADY locked out
            if (attempts.size() >= maxFailedAttempts && !lockedOutUsers.containsKey(userId)) {
                lockedOutUsers.put(userId, Long.valueOf(now + lockoutDuration));
                LOG.debug("Locked out user " + userId + " for " + lockoutDuration + "ms");
            }
        }
        // purge any lockouts older than _lockoutDuration_
        Iterator<String> lockedOutUserIter = lockedOutUsers.keySet().iterator();
        while (lockedOutUserIter.hasNext()) {
            String userId = lockedOutUserIter.next();
            Long endOfLockout = lockedOutUsers.get(userId);
            if (now >= endOfLockout.longValue()) {
                lockedOutUsers.remove(userId);
                LOG.debug("Removed expired lockout for user " + userId);
            } else if (LOG.isDebugEnabled()) {
                LOG.debug("User " + userId + " still locked out for " + (endOfLockout.longValue() - now) + "ms");
            }
        }
    }
}
