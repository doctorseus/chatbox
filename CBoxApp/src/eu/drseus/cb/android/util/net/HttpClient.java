package eu.drseus.cb.android.util.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import android.os.AsyncTask;
import eu.drseus.cb.android.util.AsyncCallback;
import eu.drseus.cb.android.util.AsyncCallback.CallbackException;

public class HttpClient {

	public static void get(URL url, HttpRequestCallback callback) {
		HttpRequest request = HttpRequest.getGetRequest(url, callback);
		request.execute();
	}
	
	public static void post(URL url, HashMap<String, String> parameters, HttpRequestCallback callback) {
		HttpRequest request = HttpRequest.getPostRequest(url, parameters, callback);
		request.execute();
	}
	
	
	public static abstract class HttpRequestCallback extends AsyncCallback<HttpResult> {

		public HttpRequestCallback(int timeout) {
			super(timeout);
		}
	}

	public static class HttpResult {

		public String content;
		public String contentType;
		public int responseCode;
		
		public HttpResult(int responseCode, String contentType, String content) {
			this.responseCode = responseCode;
			this.content = content;
			this.contentType = contentType;
		}

	}
	
	@SuppressWarnings("serial")
	public static class HttpRequestException extends CallbackException {

		public HttpRequestException(Exception nestedException) {
			super(nestedException);
		}
		
	}

	public static class HttpRequest extends AsyncTask<Void, Void, HttpResult> {

		public enum RequestType {
			GET, POST
		}
		
		private RequestType requestType;
		private HttpRequestCallback callback;
		private URL url;
		private HashMap<String, String> parameters;
		
		public static HttpRequest getPostRequest(URL url, HashMap<String, String> parameters, HttpRequestCallback callback){
			HttpRequest r = new HttpRequest();
			r.requestType = RequestType.POST;
			r.callback = callback;
			r.url = url;
			r.parameters = parameters;
			return r;
		}
		
		public static HttpRequest getGetRequest(URL url, HttpRequestCallback callback){
			HttpRequest r = new HttpRequest();
			r.requestType = RequestType.GET;
			r.callback = callback;
			r.url = url;
			return r;
		}
		
		private HttpRequest(){
			
		}

		public HttpRequest(RequestType type, HttpRequestCallback callback) {
	
		}

		protected HttpResult doInBackground(Void... v) {

			try {
				
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				
				if (requestType.equals(RequestType.GET)) {
					urlConnection.setRequestMethod("GET");
				} else if (requestType.equals(RequestType.POST)) {
					urlConnection.setRequestMethod("POST");

					// Send request
					DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
					wr.writeBytes(encodeParameters(parameters));
					wr.flush();
					wr.close();
				}
				
				int responseCode = urlConnection.getResponseCode();
				String contentType = urlConnection.getContentType();

				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

				String content = "";
				String line = "";
				while ((line = reader.readLine()) != null)
					content += line;

				return new HttpResult(responseCode, contentType, content);

			} catch (IOException e) {
				callback.onError(new HttpRequestException(e));
			}

			return null;

		}

		protected void onPostExecute(HttpResult result) {
			if(result != null)
				callback.onResult(result);
        }

	}
	
	private static String encodeParameters(HashMap<String, String> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for(Entry<String, String> entry:params.entrySet()){
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));	
	    }

	    return result.toString();
	}

}
