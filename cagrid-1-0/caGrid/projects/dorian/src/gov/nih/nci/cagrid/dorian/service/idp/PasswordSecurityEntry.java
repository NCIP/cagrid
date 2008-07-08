package gov.nih.nci.cagrid.dorian.service.idp;

import gov.nih.nci.cagrid.dorian.idp.bean.PasswordStatus;

public class PasswordSecurityEntry {
	private String uid;
	private long consecutiveInvalidLogins;
	private long lockoutExpiration;
	private long invalidLoginCount;
	private String digestAlgorithm;
	private String digestSalt;
	private PasswordStatus passwordStatus;

	public PasswordStatus getPasswordStatus() {
		return passwordStatus;
	}

	public void setPasswordStatus(PasswordStatus passwordStatus) {
		this.passwordStatus = passwordStatus;
	}

	public PasswordSecurityEntry() {

	}

	public String getUid() {
		return uid;
	}

	public long getConsecutiveInvalidLogins() {
		return consecutiveInvalidLogins;
	}

	public void setConsecutiveInvalidLogins(long consecutiveInvalidLogins) {
		this.consecutiveInvalidLogins = consecutiveInvalidLogins;
	}

	public long getTotalInvalidLogins() {
		return invalidLoginCount;
	}

	public void setTotalInvalidLogins(long invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}

	public long getLockoutExpiration() {
		return lockoutExpiration;
	}

	public void setLockoutExpiration(long lockOutExpiration) {
		this.lockoutExpiration = lockOutExpiration;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDigestAlgorithm() {
		return digestAlgorithm;
	}

	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	public String getDigestSalt() {
		return digestSalt;
	}

	public void setDigestSalt(String digestSalt) {
		this.digestSalt = digestSalt;
	}

}
