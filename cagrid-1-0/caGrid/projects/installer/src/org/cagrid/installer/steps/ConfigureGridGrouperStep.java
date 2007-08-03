/**
 * 
 */
package org.cagrid.installer.steps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.installer.util.InstallerUtils;
import org.pietschy.wizard.InvalidStateException;

/**
 * @author <a href="joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class ConfigureGridGrouperStep extends PropertyConfigurationStep {

	private static final Log logger = LogFactory
			.getLog(ConfigureGridGrouperStep.class);

	/**
	 * 
	 */
	public ConfigureGridGrouperStep() {
	}

	/**
	 * @param name
	 * @param description
	 */
	public ConfigureGridGrouperStep(String name, String description) {
		super(name, description);
	}

	/**
	 * @param name
	 * @param description
	 * @param icon
	 */
	public ConfigureGridGrouperStep(String name, String description, Icon icon) {
		super(name, description, icon);
	}

	public void applyState() throws InvalidStateException {

		super.applyState();

		//Fix the manager grid identity
		String gridIdent = this.model.getProperty(Constants.GRID_GROUPER_ADMIN_IDENT);
		gridIdent = gridIdent.replaceAll("\\\\", "");
		this.model.setProperty(Constants.GRID_GROUPER_ADMIN_IDENT, gridIdent);
		
		String driverUrl = System.getProperty("user.dir") + "/"
				+ this.model.getProperty(Constants.JDBC_DRIVER_PATH);
		try {
			InstallerUtils.addToClassPath(driverUrl);
		} catch (Exception ex) {
			String msg = "Error adding JDBC driver at '" + driverUrl
					+ "' to classpath: " + ex.getMessage();
			logger.error(msg, ex);
			throw new InvalidStateException(msg, ex);
		}

		String dbName = null;
		String jdbcBase = null;
		String jdbcUrl = this.model.getProperty(Constants.GRID_GROUPER_DB_URL);
		try {
			int idx = jdbcUrl.lastIndexOf("/");
			dbName = jdbcUrl.substring(idx + 1);
			jdbcBase = jdbcUrl.substring(0, idx);
		} catch (Exception ex) {
			String msg = "Error parsing database name from '" + jdbcUrl + "': "
					+ ex.getMessage();
			logger.error(msg, ex);
			throw new InvalidStateException(msg, ex);
		}

		String mysqlDBUrl = jdbcBase + "/mysql";
		logger.info("Attempting to create database '" + dbName
				+ "' using JDBC URL '" + mysqlDBUrl + "'");

		try {
			Class.forName("org.gjt.mm.mysql.Driver");
		} catch (Exception ex) {
			String msg = "Coudn't load driver 'org.gjt.mm.mysql.Driver': "
					+ ex.getMessage();
			logger.error(msg, ex);
			throw new InvalidStateException(msg, ex);
		}

		String usr = this.model.getProperty(Constants.GRID_GROUPER_DB_USERNAME);
		String pwd = this.model.getProperty(Constants.GRID_GROUPER_DB_PASSWORD);
		if (InstallerUtils.isEmpty(pwd)) {
			pwd = "";
		}

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(mysqlDBUrl, usr, pwd);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("create database " + dbName + ";");
		} catch (Exception ex) {
			String msg = "Error creating database '" + dbName + "': " + ex.getMessage();
			logger.error(msg, ex);
			throw new InvalidStateException(msg, ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ex) {
					logger.error("Error closing connection: " + ex.getMessage(), ex);
				}
			}
		}

	}

}
