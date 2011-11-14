package test.gov.nih.nci.security;

import gov.nih.nci.security.authentication.BetterLockoutManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import junit.framework.TestCase;

public class BetterLockoutManagerTest extends TestCase {
    
    public static long LOCKOUT_DURATION = 2000;
    public static int MAX_ATTEMPTS = 4;
    public static long LOCKOUT_MEMORY = 500;

    private BetterLockoutManager manager = null;
    
    public BetterLockoutManagerTest() {
        manager = new BetterLockoutManager(LOCKOUT_DURATION, MAX_ATTEMPTS, LOCKOUT_MEMORY);
    }
    
    
    private String getFakeUserName() {
        return "Test_User_" + System.currentTimeMillis();
    }
    
    
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void testFastLockout() {
        String user = getFakeUserName();
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            manager.setFailedAttempt(user);
        }
        assertTrue("User wasn't locked out", manager.isUserLockedOut(user));
        sleep(LOCKOUT_DURATION + LOCKOUT_DURATION / 2);
        assertFalse("User was still locked out", manager.isUserLockedOut(user));
    }
    
    
    public void testMultipleAttempts() {
        String user = getFakeUserName();
        for (int i = 0; i < MAX_ATTEMPTS - 1; i++) {
            manager.setFailedAttempt(user);
            assertFalse("User was locked out after only " + (i + 1) + "attempts", manager.isUserLockedOut(user));
        }
        manager.setFailedAttempt(user);
        assertTrue("User was not locked out", manager.isUserLockedOut(user));
    }
    
    
    public void testDoubleExtraAttempts() {
        String user = getFakeUserName();
        for (int i = 0; i < (MAX_ATTEMPTS * 2); i++) {
            manager.setFailedAttempt(user);
            if (i >= MAX_ATTEMPTS - 1) {
                assertTrue("User wasn't locked out on the " + (i + 1) + " attempt", manager.isUserLockedOut(user));
            } else {
                assertFalse("User was locked out after only " + (i + 1) + " attempts", manager.isUserLockedOut(user));
            }
        }
        assertTrue("User was not locked out", manager.isUserLockedOut(user));
        sleep(LOCKOUT_DURATION + LOCKOUT_DURATION / 2);
        assertFalse("User was still locked out", manager.isUserLockedOut(user));
    }
    
    
    public void testTooSlowAttempts() {
        String userId = getFakeUserName();
        for (int i = 0; i < MAX_ATTEMPTS * 2; i++) {
            manager.setFailedAttempt(userId);
            sleep(LOCKOUT_MEMORY / 2);
            assertFalse("User was locked out on the " + (i + 1) + " attempt", manager.isUserLockedOut(userId));
        }
    }
    
    
    public void testUnlock() {
        String userId = getFakeUserName();
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            manager.setFailedAttempt(userId);
        }
        assertTrue("User wasn't locked out", manager.isUserLockedOut(userId));
        Map<String, Date> lockouts = manager.getLockedOutUsers();
        assertTrue("User wasn't found in the locked out users table", lockouts.containsKey(userId));
        System.out.println("Locked out until " + DateFormat.getDateTimeInstance().format(lockouts.get(userId)));
        manager.releaseLockout(userId);
        assertFalse("User was still locked out", manager.isUserLockedOut(userId));
    }
    
    
    public void testDisabledLockoutManager() {
        String userId = getFakeUserName();
        BetterLockoutManager disabledManager = new BetterLockoutManager(0, 0, 0);
        for (int i = 0; i < MAX_ATTEMPTS * 2; i++) {
            disabledManager.setFailedAttempt(userId);
            assertFalse("Disabled lockout manager should never have locked out the account", manager.isUserLockedOut(userId));
        }
    }
    
    
    public void testWhitelist() {
        String userId = getFakeUserName();
        manager.whitelistUser(userId);
        assertTrue("User " + userId + " did not appear on whitelist", manager.getWhitelistedUsers().contains(userId));
        for (int i = 0; i < MAX_ATTEMPTS * 2; i++) {
            manager.setFailedAttempt(userId);
        }
        assertFalse("User was locked out even though user was on whitelist", manager.isUserLockedOut(userId));
        manager.unWhitelistUser(userId);
        assertFalse("User " + userId + " still appears on whitelist", manager.getWhitelistedUsers().contains(userId));
        for (int i = 0; i < MAX_ATTEMPTS * 2; i++) {
            manager.setFailedAttempt(userId);
        }
        assertTrue("User was not locked out", manager.isUserLockedOut(userId));
        manager.whitelistUser(userId);
        assertFalse("User was locked out after being whitelisted", manager.isUserLockedOut(userId));
    }
}
