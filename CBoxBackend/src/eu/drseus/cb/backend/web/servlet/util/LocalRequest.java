package eu.drseus.cb.backend.web.servlet.util;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is used to create RequestInformation objects out of
 * ServletRequests.
 * 
 * @author Lukas Pfeifhofer <lukas.pfeifhofer@devlabs.pro>
 * 
 */
public class LocalRequest {

	public static RequestInformation create(HttpServletRequest req) {
		RequestInformation reqInfo = new RequestInformation();

		// URL parameters
		reqInfo.parameters = URLParameters.create(req);

		// Absolute Path
		reqInfo.requestUrl = req.getRequestURL().toString() + ((req.getQueryString() != null) ? "?" + req.getQueryString() : "");
		
		reqInfo.hostUrl = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort();

		reqInfo.referrer = req.getHeader("referer");

		return reqInfo;
	}

	/**
	 * This class holds some RequestInformation. For example, it should be used
	 * to get a fast access to Session Variables or User Information. It will be
	 * created by the LocalRequest class.
	 * 
	 * @author Lukas Pfeifhofer <lukas.pfeifhofer@devlabs.pro>
	 * 
	 */
	public static class RequestInformation {

		private String requestUrl;
		private String hostUrl;
		private URLParameters parameters;

		String referrer;

		public String getRequestUrl() {
			return requestUrl;
		}
		
		public String getHostUrl(){
			return hostUrl;
		}

		public URLParameters getParameters() {
			return parameters;
		}

		public String getReferrer() {
			return referrer;
		}

	}

}
