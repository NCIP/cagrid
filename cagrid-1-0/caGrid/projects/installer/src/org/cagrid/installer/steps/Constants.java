/**
 * 
 */
package org.cagrid.installer.steps;

import java.util.Properties;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 *
 */
public abstract class Constants {
	
	/**
	 * Is the network available?
	 */
	public static final String NETWORK_AVAILABLE = "network.available";
	
	/**
	 * The name of the file that holds all installer properties.
	 */
	public static final String CAGRID_INSTALLER_PROPERTIES = "cagrid.installer.properties";
	public static final String TOMCAT_ZIP_PATH = "tomcat.zip.path";
	public static final String GLOBUS_ZIP_PATH = "globus.zip.path";
	public static final String TEMP_DIR_PATH = "temp.dir.path";
	public static final String BUILD_FILE_PATH = "build.file.path";
	public static final String TOMCAT_DIR_PATH = "tomcat.dir.path";
	public static final String GLOBUS_DIR_PATH = "globus.dir.path";
	public static final Object TOMCAT_SECURE_PORT = "tomcat.secure.port";
	public static final Object TOMCAT_KEY_PATH = "tomcat.key.path";
	public static final Object TOMCAT_CERT_PATH = "tomcat.cert.path";
	public static final String CA_PRESENT = "ca.present";
	public static final String CA_CERT_PATH = "ca.cert.path";
	public static final String CA_KEY_PATH = "ca.key.path";
	public static final String CA_KEY_PWD = "ca.key.pwd";
	public static final String CA_DN = "ca.dn.input";
	public static final String CA_DAYS_VALID = "ca.days.valid";
	public static final String SERVICE_CERT_PATH = "service.cert.path";
	public static final String SERVICE_KEY_PATH = "service.key.path";
	public static final String SERVICE_KEY_PWD = "service.cert.pwd";
	public static final String SERVICE_CERT_DAYS_VALID = "service.cert.days.valid";
	public static final String SERVICE_CERT_PRESENT = "service.cert.present";
	public static final String GRIDCA_BUILD_FILE_PATH = "gridca.build.file.path";

	public static final String ANT_HOME = "ant.home";

	public static final String TOMCAT_HOME = "tomcat.home";

	public static final String GLOBUS_HOME = "globus.home";

	public static final String CAGRID_HOME = "cagrid.home";

	public static final String ANT_INSTALLED = "ant.installed";

	public static final String TOMCAT_INSTALLED = "tomcat.installed";

	public static final String GLOBUS_INSTALLED = "globus.installed";

	public static final String CAGRID_INSTALLED = "cagrid.installed";

	public static final String MESSAGES = "CaGridInstallerMessages";

	public static final String INSTALL_CAGRID = "install.cagrid";

	public static final String INSTALL_SERVICES = "install.services";

	public static final String INSTALL_ANT = "install.ant";

	public static final String ANT_DOWNLOAD_URL = "ant.download.url";

	public static final String ANT_TEMP_FILE_NAME = "ant.temp.file.name";

	public static final String ANT_INSTALL_DIR_PATH = "ant.install.dir.path";

	public static final String ANT_DIR_NAME = "ant.dir.name";

	public static final String INSTALL_TOMCAT = "install.tomcat";

	public static final String TOMCAT_INSTALL_DIR_PATH = "tomcat.install.dir.path";

	public static final String TOMCAT_DOWNLOAD_URL = "tomcat.download.url";

	public static final String TOMCAT_TEMP_FILE_NAME = "tomcat.temp.file.name";

	public static final String TOMCAT_DIR_NAME = "tomcat.dir.name";

	public static final String INSTALL_GLOBUS = "install.globus";

	public static final String GLOBUS_INSTALL_DIR_PATH = "globus.install.dir.path";

	public static final String GLOBUS_DOWNLOAD_URL = "globus.download.url";

	public static final String GLOBUS_TEMP_FILE_NAME = "globus.temp.file.name";

	public static final String GLOBUS_DIR_NAME = "globus.dir.name";

	public static final String CAGRID_INSTALL_DIR_PATH = "cagrid.install.dir.path";

	public static final String CAGRID_DOWNLOAD_URL = "cagrid.download.url";

	public static final String CAGRID_TEMP_FILE_NAME = "cagrid.temp.file.name";

	public static final String CAGRID_DIR_NAME = "cagrid.dir.name";

	public static final String GLOBUS_DEPLOYED = "globus.deployed";

	public static final String DEPLOY_GLOBUS = "deploy.globus";

	public static final String USE_SECURE_CONTAINER = "use.secure.container";

	public static final String INSTALL_DORIAN = "install.dorian";

	public static final String INSTALL_GTS = "install.gts";

	public static final String INSTALL_AUTHN_SVC = "install.authn.svc";

	public static final String INSTALL_GRID_GROUPER = "install.grid.grouper";

	public static final String INSTALL_GME = "install.gme";

	public static final String INSTALL_EVS = "install.evs";

	public static final String INSTALL_CADSR = "install.cadsr";

	public static final String INSTALL_FQP = "install.fqp";

	public static final String INSTALL_WORKFLOW = "install.workflow";

	public static final String INSTALL_IDENT_SVC = "install.ident.svc";

	public static final String CA_CERT_PRESENT = "ca.cert.present";

//	public static final String GENERATE_SERVICE_CERT = "gen.service.cert";

//	public static final String GENERATE_CA_CERT = "gen.ca.cert";

	public static final String SERVICE_HOSTNAME = "service.hostname";

	public static final String INSTALL_INDEX_SVC = "install.index.svc";

	public static final String TOMCAT_KEY = "tomcat.key";
	
	public static final String TOMCAT_CERT = "tomcat.cert";

	public static final String TOMCAT_KEY_DEST = "tomcat.key.dest";

	public static final String TOMCAT_CERT_DEST = "tomcat.cert.dest";

	public static final String DOWNLOAD_URL = "download.url";

	public static final long CONNECT_TIMEOUT = 5000;

	public static final String JDBC_DRIVER_PATH = "jdbc.driver.path";

	public static final String JDBC_DRIVER_CLASSNAME = "jdbc.driver.classname";

	public static final String DORIAN_DB_HOST = "dorian.db.host";

	public static final String DORIAN_DB_PORT = "dorian.db.port";

	public static final String DORIAN_DB_USERNAME = "dorian.db.username";

	public static final String DORIAN_DB_PASSWORD = "dorian.db.password";

	public static final String DORIAN_DB_ID = "dorian.db.id";

	public static final String DORIAN_IDP_NAME = "dorian.idp.name";

	public static final String DORIAN_IDP_UID_MIN = "dorian.idp.uid.min";

	public static final String DORIAN_IDP_UID_MAX = "dorian.idp.uid.max";

	public static final String DORIAN_IDP_PWD_MIN = "dorian.idp.pwd.min";

	public static final String DORIAN_IDP_PWD_MAX = "dorian.idp.pwd.max";

	public static final String DORIAN_IDP_REGPOLICY = "dorian.idp.regpolicy";

	public static final String DORIAN_IDP_SAML_AUTORENEW = "dorian.idp.saml.autorenew";

	public static final String DORIAN_IDP_SAML_KEYPWD = "dorian.idp.saml.keypwd";

	public static final String DORIAN_IFS_IDPNAME_MIN = "dorian.ifs.idpname.min";

	public static final String DORIAN_IFS_IDPNAME_MAX = "dorian.ifs.idpname.max";

	public static final String DORIAN_IFS_IDPOLICY = "dorian.ifs.idpolicy";

	public static final String DORIAN_IFS_CREDLIFETIME_YEARS = "dorian.ifs.credlifetime.years";

	public static final String DORIAN_IFS_CREDLIFETIME_MONTHS = "dorian.ifs.credlifetime.months";
	
	public static final String DORIAN_IFS_CREDLIFETIME_DAYS = "dorian.ifs.credlifetime.days";
	
	public static final String DORIAN_IFS_CREDLIFETIME_HOURS = "dorian.ifs.credlifetime.hours";
	
	public static final String DORIAN_IFS_CREDLIFETIME_MINUTES = "dorian.ifs.credlifetime.minutes";
	
	public static final String DORIAN_IFS_CREDLIFETIME_SECONDS = "dorian.ifs.credlifetime.seconds";

	public static final String DORIAN_IFS_HOSTCERT_AUTOAPPROVE = "dorian.ifs.hostcert.autoapprove";
	
	public static final String DORIAN_IFS_PROXYLIFETIME_HOURS = "dorian.ifs.proxylifetime.hours";
	
	public static final String DORIAN_IFS_PROXYLIFETIME_MINUTES = "dorian.ifs.proxylifetime.minutes";
	
	public static final String DORIAN_IFS_PROXYLIFETIME_SECONDS = "dorian.ifs.proxylifetime.seconds";	

	public static final String DORIAN_IFS_GTS_URL = "dorian.ifs.gts.url";

	public static final String DORIAN_CA_PRESENT = "dorian.ca.present";

	public static final String DORIAN_CA_CERT_PATH = "dorian.ca.cert.path";

	public static final String DORIAN_CA_KEY_PATH = "dorian.ca.key.path";

	public static final String DORIAN_CA_KEY_PWD = "dorian.ca.key.pwd";

	public static final String DORIAN_CA_OID = "dorian.ca.oid";

	public static final String DORIAN_CA_USERKEY_SIZE = "dorian.ca.userkey.size";

	public static final String DORIAN_CA_AUTORENEW_YEARS = "dorian.ca.autorenew.years";
	
	public static final String DORIAN_CA_AUTORENEW_MONTHS = "dorian.ca.autorenew.months";
	
	public static final String DORIAN_CA_AUTORENEW_DAYS = "dorian.ca.autorenew.days";
	
	public static final String DORIAN_CA_AUTORENEW_HOURS = "dorian.ca.autorenew.hours";
	
	public static final String DORIAN_CA_AUTORENEW_MINUTES = "dorian.ca.autorenew.minutes";
	
	public static final String DORIAN_CA_AUTORENEW_SECONDS = "dorian.ca.autorenew.seconds";

	public static final String DORIAN_CA_CAKEY_SIZE = "dorian.ca.cakey.size";

	public static final String DORIAN_CA_LIFETIME_YEARS = "dorian.ca.lifetime.years";
	
	public static final String DORIAN_CA_LIFETIME_MONTHS = "dorian.ca.lifetime.months";
	
	public static final String DORIAN_CA_LIFETIME_DAYS = "dorian.ca.lifetime.days";
	
	public static final String DORIAN_CA_LIFETIME_HOURS = "dorian.ca.lifetime.hours";
	
	public static final String DORIAN_CA_LIFETIME_MINUTES = "dorian.ca.lifetime.minutes";
	
	public static final String DORIAN_CA_LIFETIME_SECONDS = "dorian.ca.lifetime.seconds";

	public static final String DORIAN_CA_SUBJECT = "dorian.ca.subject";

	public static final String CONTAINER_TYPE = "container.type";

	public static final String DORIAN_USE_GEN_CA = "dorian.use.gen.ca";

	public static final String AVAILABLE_TARGET_GRIDS = "available.target.grids";

	public static final String TARGET_GRID = "target.grid";

	public static final String SERVICE_DEST_DIR = "service.dest.dir";

	public static final String GTS_ADMIN_IDENT = "gts.admin.ident";

	public static final String TOMCAT_SHUTDOWN_PORT = "tomcat.shutdown.port";

	public static final String TOMCAT_HTTP_PORT = "tomcat.http.port";

	public static final String TOMCAT_HTTPS_PORT = "tomcat.https.port";

	public static final String TOMCAT_OLD_HTTP_PORT = "tomcat.old.http.port";

	public static final String TOMCAT_OLD_HTTPS_PORT = "tomcat.old.https.port";

	public static final String AUTHN_SVC_USE_GEN_CA = "authn.svc.use.gen.ca";

	public static final String AUTHN_SVC_CA_PRESENT = "authn.svc.ca.present";

	public static final String AUTHN_SVC_CA_CERT_PATH = "authn.svc.ca.cert.path";

	public static final String AUTHN_SVC_CA_KEY_PATH = "authn.svc.ca.key.path";

	public static final String AUTHN_SVC_CA_KEY_PWD = "authn.svc.ca.key.pwd";

	public static final String AUTHN_SVC_CA_DN = "authn.svc.ca.dn";

	public static final String AUTHN_SVC_CA_DAYS_VALID = "authn.svc.ca.days.valid";

	public static final String AUTHN_SVC_CRED_PROVIDER_TYPE = "authn.svc.cred.provider.type";

	public static final String AUTHN_SVC_CRED_PROVIDER_TYPE_RDBMS = "authn.svc.cred.provider.type.rdbms";

	public static final String AUTHN_SVC_CRED_PROVIDER_TYPE_LDAP = "authn.svc.cred.provider.type.ldap";

	public static final String AUTHN_SVC_CSM_CTX = "authn.svc.csm.ctx";

	public static final String AUTHN_SVC_RDBMS_URL = "authn.svc.rdbms.url";

	public static final String AUTHN_SVC_RDBMS_DRIVER_JAR = "authn.svc.rdbms.driver.jar";

	public static final String AUTHN_SVC_RDBMS_DRIVER = "authn.svc.rdbms.driver";

	public static final String AUTHN_SVC_RDBMS_USERNAME = "authn.svc.rdbms.username";

	public static final String AUTHN_SVC_RDBMS_PASSWORD = "authn.svc.rdbms.password";

	public static final String AUTHN_SVC_RDBMS_TABLE_NAME = "authn.svc.rdbms.table.name";

	public static final String AUTHN_SVC_RDBMS_LOGIN_ID_COLUMN = "authn.svc.rdbms.login.id.column";

	public static final String AUTHN_SVC_RDBMS_PASSWORD_COLUMN = "authn.svc.rdbms.password.column";

	public static final String AUTHN_SVC_RDBMS_FIRST_NAME_COLUMN = "authn.svc.rdbms.first.name.column";

	public static final String AUTHN_SVC_RDBMS_LAST_NAME_COLUMN = "authn.svc.rdbms.last.name.column";

	public static final String AUTHN_SVC_RDBMS_EMAIL_ID_COLUMN = "authn.svc.rdbms.email.id.column";

	public static final String AUTHN_SVC_LDAP_HOSTNAME = "authn.svc.ldap.hostname";

	public static final String AUTHN_SVC_LDAP_SEARCH_BASE = "authn.svc.ldap.search.base";

	public static final String AUTHN_SVC_LDAP_LOGIN_ID_ATTRIBUTE = "authn.svc.ldap.login.id.attribute";

	public static final String AUTHN_SVC_LDAP_FIRST_NAME_ATTRIBUTE = "authn.svc.ldap.first.name.attribute";

	public static final String AUTHN_SVC_LDAP_LAST_NAME_ATTRIBUTE = "authn.svc.ldap.last.name.attribute";

	public static final String AUTHN_SVC_RDBMS_ENCRYPTION_ENABLED = "authn.svc.rdbms.encryption.enabled";

	public static final String AUTHN_SVC_OVERWRITE_JAAS = "authn.svc.overwrite.jaas";

	public static final String AUTHN_SVC_OVERWRITE_JAAS_YES = "authn.svc.overwrite.jaas.yes";

	public static final String AUTHN_SVC_OVERWRITE_JAAS_NO = "authn.svc.overwrite.jaas.no";

	
}
