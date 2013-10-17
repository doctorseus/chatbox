package eu.drseus.cb.backend.web.servlet.util;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

/**
 * This class provides the functionality to create a URL with encoded
 * parameters.
 * 
 * @author Lukas Pfeifhofer <lukas.pfeifhofer@devlabs.pro>
 */
public class URLBuilder {

	public static URL create(String path) {
		return new URL(path);
	}

	/**
	 * Represents a single URL
	 */
	public static class URL {

		String path;

		HashMap<String, String> parameter = new HashMap<String, String>();

		public URL(String path) {
			this.path = path;
		}

		public URL add(String name, String value) {
			parameter.put(name, value);
			return this;
		}

		public String get() {
			String s = "";
			URLCodec codec = new URLCodec();
			try {
				Set<Entry<String, String>> entrySet = parameter.entrySet();
				for (Entry<String, String> entry : entrySet) {
					if (s.isEmpty()) {
						s += "?";
					} else {
						s += "&";
					}
					s += codec.encode(entry.getKey()) + "="
							+ codec.encode(entry.getValue());
				}
			} catch (EncoderException e) {
				throw new URLEncoderException();
			}
			return path + s;
		}
	}

	@SuppressWarnings("serial")
	public static class URLEncoderException extends RuntimeException {
		public URLEncoderException() {
			super();
		}
	}
}
