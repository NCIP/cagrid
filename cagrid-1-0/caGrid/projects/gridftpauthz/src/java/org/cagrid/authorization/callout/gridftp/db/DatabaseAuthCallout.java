package org.cagrid.authorization.callout.gridftp.db;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;

import org.cagrid.authorization.callout.gridftp.AbstractAuthCallout;
import org.cagrid.authorization.callout.gridftp.GridFTPTuple;
import org.cagrid.authorization.callout.gridftp.GridFTPOperation.Operation;



public class DatabaseAuthCallout extends AbstractAuthCallout {

	public static final String DB_CONNECT_STRING="db.connecturi";
	public static final String DB_USER_PROP_NAME="db.user";
	public static final String DB_PASSWORD_PROP_NAME="db.password";
	
	public static final String RESOURCE_LOCATION="org/cagrid/authorization/callout/gridftp/db/db.props";
	
	private DBUtil _util;
	private Object _sync = new Object();
	
	@Override
	public boolean authorizeOperation(String identity, Operation operation, String target) {

		boolean authorized = false;
		
		try {
			synchronized(_sync) {
				if (_util == null) {
					_util = createDBUtil();
				}
			}
			
			GridFTPTuple tuple = new GridFTPTuple(identity, operation, target);
			authorized = _util.tupleExists(tuple);
			if (authorized) {
				String msg = "Authorized request: " + tuple;
				_logger.log(Level.INFO, msg);
			} else {
				String msg = "Denied request: " + tuple;
				_logger.log(Level.INFO, msg);
			}
		} catch (DatabaseException e) {
			String msg = "Could not check if tuple exists";
			_logger.log(Level.SEVERE, msg, e);
			System.out.println(msg);
		} catch (MalformedURLException e) {
			String msg = "Bad URL: " + target;
			_logger.log(Level.SEVERE, msg, e);
			System.out.println(msg);
		} catch (IOException e) {
			String msg = "Could not load db connection properties. Expected to find the following resource on the classpath: " + RESOURCE_LOCATION + ".";
			msg += " Reason: " + e.getMessage();
			_logger.log(Level.SEVERE, msg, e);
			System.out.println(msg);
		} catch (PropertyNotFoundException e) {
			_logger.severe(e.getMessage());
			System.out.println(e.getMessage());
		}

		return authorized;
	}
	
	private DBUtil createDBUtil() throws IOException, PropertyNotFoundException, DatabaseException {
		/* read in properties file containing the db connection parameters */
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(RESOURCE_LOCATION);
		Properties props = new Properties();
		props.load(stream);
		String connectionString = props.getProperty(DB_CONNECT_STRING);
		String dbuser = props.getProperty(DB_USER_PROP_NAME);
		String password = props.getProperty(DB_PASSWORD_PROP_NAME);

		//check that properties aren't null
		if (connectionString == null) {
			String msg = "Could not load property named " + DB_CONNECT_STRING + " from " + RESOURCE_LOCATION + " on the classpath.";
			msg += " Check that your properties file is correct.";
			throw new PropertyNotFoundException(msg);
		}
		if (dbuser == null) {
			String msg = "Could not load property named " + DB_USER_PROP_NAME+ " from " + RESOURCE_LOCATION + " on the classpath.";
			msg += " Check that your properties file is correct.";
			throw new PropertyNotFoundException(msg);
		}
		if (password == null) {
			String msg = "Could not load property named " + DB_PASSWORD_PROP_NAME + " from " + RESOURCE_LOCATION + " on the classpath.";
			msg += " Check that your properties file is correct.";
			throw new PropertyNotFoundException(msg);
		}
		
		return new DBUtil(connectionString, dbuser, password);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseAuthCallout callout = new DatabaseAuthCallout();
		boolean auth = callout.authorize("/O=cagrid.org/OU=training/OU=caBIG User Group/OU=IdP [1]/CN=gridftp", "read", "ftp://irondale/tmp/yayo");
		System.out.println(auth);

	}

}
