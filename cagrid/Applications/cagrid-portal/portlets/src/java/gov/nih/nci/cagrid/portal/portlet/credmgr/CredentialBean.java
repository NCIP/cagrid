/**
 * 
 */
package gov.nih.nci.cagrid.portal.portlet.credmgr;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com>Joshua Phillips</a>
 *
 */
public class CredentialBean {
	private String identity;
	private IdPBean idpBean;
	private boolean defaultCredential;
	public CredentialBean(String identity, IdPBean idpBean) {
		this.identity = identity;
		this.idpBean = idpBean;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public IdPBean getIdpBean() {
		return idpBean;
	}
	public void setIdpBean(IdPBean idpBean) {
		this.idpBean = idpBean;
	}
	public boolean isDefaultCredential() {
		return defaultCredential;
	}
	public void setDefaultCredential(boolean defaultCredential) {
		this.defaultCredential = defaultCredential;
	}
	
}
