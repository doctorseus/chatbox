package eu.drseus.cb.backend.web.servlet.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is used as wrapper around the HttpServletRequest-Parameters to
 * provide a layer for security features and the ability to double check
 * parameter types.
 * 
 * @author Lukas Pfeifhofer <lukas.pfeifhofer@devlabs.pro>
 */
public class URLParameters {

	private HashMap<String, URLParameter> parameterMap = new HashMap<String, URLParameters.URLParameter>();

	private URLParameters() {

	}

	public static URLParameters create(HttpServletRequest req) {
		URLParameters parameters = new URLParameters();

		
		// http://stackoverflow.com/a/1130559
		Map<String, String[]> parameterMap = req.getParameterMap();

		Set<String> keySet = parameterMap.keySet();
		Iterator<String> keyIterator = keySet.iterator();

		while (keyIterator.hasNext()) {
			String parameterName = keyIterator.next();
			String[] parameterValue = parameterMap.get(parameterName);
			parameters.parameterMap.put(parameterName, new URLParameter(
					parameterName, parameterValue));
		}

		return parameters;
	}

	public HashMap<String, URLParameter> getParameterMap(){
		return parameterMap;
	}
	
	public Set<String> getParameterNames() {
		return parameterMap.keySet();
	}

	public boolean isExisting(String parameterName) {
		URLParameter urlParameter = parameterMap.get(parameterName);
		if (urlParameter == null)
			return false;
		return urlParameter.isExisting();
	}

	public String getString(String parameterName)
			throws ParameterDoesNotExistException {
		URLParameter urlParameter = parameterMap.get(parameterName);
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getString();
	}
	
	public String[] getStringArray(String parameterName)
			throws ParameterDoesNotExistException {
		URLParameter urlParameter = parameterMap.get(parameterName+"[]");
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getStringArray();
	}


	public int getInteger(String parameterName)
			throws ParameterDoesNotExistException, ParameterParsingException {
		URLParameter urlParameter = parameterMap.get(parameterName);
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getInteger();
	}

	public long getLong(String parameterName)
		throws ParameterDoesNotExistException, ParameterParsingException {
		URLParameter urlParameter = parameterMap.get(parameterName);
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getLong();
	}
	
	public long[] getLongArray(String parameterName)
			throws ParameterDoesNotExistException, ParameterParsingException {
		URLParameter urlParameter = parameterMap.get(parameterName+"[]");
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getLongArray();
	}

	public double getDouble(String parameterName)
			throws ParameterDoesNotExistException, ParameterParsingException {
		URLParameter urlParameter = parameterMap.get(parameterName);
		if (urlParameter == null)
			throw new ParameterDoesNotExistException();
		return urlParameter.getDouble();
	}

	/**
	 * Represents a single Parameter
	 */
	public static class URLParameter {

		String key;
		String[] values;

		public URLParameter(String key, String[] parameterValue) {
			this.key = key;
			this.values = parameterValue;
		}

		public boolean isExisting() {
			if (values == null || values.length == 0) {
				return false;
			}
			return true;
		}

		public String getString() throws ParameterDoesNotExistException {
			if (values == null || values.length == 0)
				throw new ParameterDoesNotExistException();
			return values[0];
		}
		
		public String[] getStringArray() throws ParameterDoesNotExistException {
			if (values == null)
				throw new ParameterDoesNotExistException();
			return values;
		}

		public int getInteger() throws ParameterDoesNotExistException,
				ParameterParsingException {
			if (values == null || values.length == 0)
				throw new ParameterDoesNotExistException();
			int iValue = -1;
			try {
				iValue = Integer.parseInt(values[0]);
			} catch (NumberFormatException e) {
				throw new ParameterParsingException();
			}
			return iValue;
		}
		
		public long getLong() throws ParameterDoesNotExistException,
		ParameterParsingException {
			if (values == null || values.length == 0)
				throw new ParameterDoesNotExistException();
			long lValue = -1;
			try {
				lValue = Long.parseLong(values[0]);
			} catch (NumberFormatException e) {
				throw new ParameterParsingException();
			}
			return lValue;
		}
		
		public long[] getLongArray() throws ParameterDoesNotExistException,
		ParameterParsingException {
			if (values == null)
				throw new ParameterDoesNotExistException();
			long[] lValues = new long[values.length];
			try {
				for(int i = 0; i < values.length; i++)
					lValues[i] = Long.parseLong(values[i]);
			} catch (NumberFormatException e) {
				throw new ParameterParsingException();
			}
			return lValues;
		}

		public double getDouble() throws ParameterDoesNotExistException,
				ParameterParsingException {
			if (values == null || values.length == 0)
				throw new ParameterDoesNotExistException();
			double dValue = -1;
			try {
				dValue = Double.parseDouble(values[0]);
			} catch (NumberFormatException e) {
				throw new ParameterParsingException();
			}
			return dValue;
		}

	}

	public static class ParameterDoesNotExistException extends
			ParameterException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3334377651058373231L;

		public ParameterDoesNotExistException() {
			super("ParameterDoesNotExistException");
		}

	}

	public static class ParameterParsingException extends ParameterException {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1376616971589471433L;

		public ParameterParsingException() {
			super("ParameterParsingException");
		}

	}

	public abstract static class ParameterException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -8922922687892689962L;

		public ParameterException(String msg) {
			super(msg);
		}

	}

}
