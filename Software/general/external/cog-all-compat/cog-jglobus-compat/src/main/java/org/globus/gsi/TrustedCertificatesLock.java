package org.globus.gsi;

public class TrustedCertificatesLock {
	private static TrustedCertificatesLock lock;


	private TrustedCertificatesLock() {

	}


	public static synchronized TrustedCertificatesLock getInstance() {
		if (lock == null) {
			lock = new TrustedCertificatesLock();
		}
		return lock;
	}

}
