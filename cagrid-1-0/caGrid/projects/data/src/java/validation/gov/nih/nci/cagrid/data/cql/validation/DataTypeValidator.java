package gov.nih.nci.cagrid.data.cql.validation;

import gov.nih.nci.cagrid.data.MalformedQueryException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ValueDomainValidator Validates a value against its ValueDomain
 * 
 * @author <A HREF="MAILTO:ervin@bmi.osu.edu">David W. Ervin</A>
 * 
 * @created Jul 31, 2006
 * @version $Id$
 */
public class DataTypeValidator {
	private static Log LOG = LogFactory.getLog(DataTypeValidator.class);


	public static void validate(String value, String dataType) throws MalformedQueryException {
		if (dataType.equals(String.class.getName())) {
			// this is fairly common, so returning immediatly is a slight
			// performance boost
			return;
		} else if (dataType.equals(Long.class.getName())) {
			validateLong(value);
		} else if (dataType.equals(Integer.class.getName())) {
			validateInteger(value);
		} else if (dataType.equals(Date.class.getName())) {
			validateDate(value);
		} else if (dataType.equals(Boolean.class.getName())) {
			validateBoolean(value);
		} else if (dataType.equals(Character.class.getName()) || dataType.equals("CHARACTER")) {
			validateCharacter(value);
		} else if (dataType.equals(Double.class.getName())) {
		    validateDouble(value);
		} else if (dataType.equals(Float.class.getName())) {
		    validateFloat(value);
		} else {
			LOG.warn("Data type " + dataType + " not recognized; Validated only as a String");
		}
	}


	private static void validateInteger(String value) throws MalformedQueryException {
		// parse the integer
		try {
			Integer.parseInt(value);
		} catch (Exception ex) {
			throw new MalformedQueryException("Value " + value + " does not parse as an Integer");
		}
	}


	private static void validateLong(String value) throws MalformedQueryException {
		// parse the long
		try {
			Long.parseLong(value);
		} catch (Exception ex) {
			throw new MalformedQueryException("Value " + value + " does not parse as a Long");
		}
	}


	private static void validateDate(String value) throws MalformedQueryException {
	    // try short date / time, time, then XSD dateTime, just XSD date
	    List<SimpleDateFormat> formats = new ArrayList<SimpleDateFormat>(4);
	    formats.add((SimpleDateFormat) DateFormat.getInstance());
	    formats.add(new SimpleDateFormat("HH:mm:ss"));
	    formats.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
	    formats.add(new SimpleDateFormat("yyyy-MM-dd"));
	    
	    Date date = null;
	    Iterator<SimpleDateFormat> formatIter = formats.iterator();
	    while (date == null && formatIter.hasNext()) {
	        SimpleDateFormat formatter = formatIter.next();
	        try {
	            date = formatter.parse(value);
	        } catch (ParseException ex) {
	            LOG.debug(value + " was not parsable by pattern " + formatter.toPattern());
	        }
	    }
	    if (date == null) {
	        throw new MalformedQueryException("Value " + value + " does not parse as a Date");
	    }
	}


	private static void validateBoolean(String value) throws MalformedQueryException {
		try {
			Boolean.valueOf(value);
		} catch (Exception ex) {
			throw new MalformedQueryException("Value " + value + " does not parse as a Boolean");
		}
	}


	private static void validateCharacter(String value) throws MalformedQueryException {
		if (value.length() > 1) {
			throw new MalformedQueryException("Value " + value + " is not a single Character or empty");
		}
	}
	
	
	private static void validateDouble(String value) throws MalformedQueryException {
	    try {
	        Double.valueOf(value);
	    } catch (Exception ex) {
	        throw new MalformedQueryException("Value " + value + " does not parse as a Double");
	    }
	}
	
	
	private static void validateFloat(String value) throws MalformedQueryException {
	    try {
	        Float.valueOf(value);
	    } catch (Exception ex) {
	        throw new MalformedQueryException("Value " + value + " does not parse as a Float");
	    }
	}
}
