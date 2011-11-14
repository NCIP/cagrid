package test.gov.nih.nci.security;

import gov.nih.nci.security.authentication.LockoutManager;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class LockoutManagerTest extends TestCase {
    
    public static final String LOCKOUT_TIME_MS = "5000"; // 5 sec
    public static final String ALLOWED_LOGIN_TIME_MS = "3000"; // 3 sec
    public static final String MAX_ATTEMPTS = "3"; // 3 logins == locked out
    
    private LockoutManager lockoutManager = null;
    
    public LockoutManagerTest(String name) {
        super(name);
        // lockout manager is a singleton with multiple ways it can be instantiated
        // .... yeah, I know....
        LockoutManager.initialize(LOCKOUT_TIME_MS, ALLOWED_LOGIN_TIME_MS, MAX_ATTEMPTS);
        lockoutManager = LockoutManager.getInstance();
    }
    
    
    public void testLockouts() {
        String userId = "Testing_User_" + System.currentTimeMillis();
        assertFalse("User was locked out before we did anything!", lockoutManager.isUserLockedOut(userId));
        for (int i = 0; i < Integer.valueOf(MAX_ATTEMPTS).intValue() - 1; i++) {
            lockoutManager.setFailedAttempt(userId);
            assertFalse("User was locked out on attempt number " + i + ", should have been " + MAX_ATTEMPTS, 
                lockoutManager.isUserLockedOut(userId));
        }
        lockoutManager.setFailedAttempt(userId);
        assertTrue("User was not locked out after " + MAX_ATTEMPTS + " attempts!", lockoutManager.isUserLockedOut(userId));
        long sleepTime = Long.parseLong(LOCKOUT_TIME_MS) + (2 * Long.parseLong(LOCKOUT_TIME_MS));
        try {
            System.out.println("Sleeping " + sleepTime + " ms to wait for unlock");
            Thread.sleep(sleepTime);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
            fail("Can't sleep; clown will eat me");
        }
        boolean isLocked = lockoutManager.isUserLockedOut(userId);
        assertFalse("After sleeping " + sleepTime + " ms, user was still locked out!", isLocked);
    }
    
    
    public static void main(String[] args) {
        TestRunner runner = new TestRunner();
        TestResult result = runner.doRun(new TestSuite(LockoutManagerTest.class));
        System.exit(result.errorCount() + result.failureCount());
    }

}
