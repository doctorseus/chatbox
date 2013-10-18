package eu.drseus.cb.android.util.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import eu.drseus.cb.android.util.AsyncCallback;
import eu.drseus.cb.android.util.AsyncCallback.CallbackException;

public class HttpClient {

	public static void fetchURL(URL url, HttpUrlFetcherCallback httpUrlFetcherCallback) {
		HTTPUrlFetcher fetcher = new HTTPUrlFetcher(httpUrlFetcherCallback);
		fetcher.execute(url);
	}
	
	
	public static abstract class HttpUrlFetcherCallback extends AsyncCallback<HttpUrlFetcherResult> {

		public HttpUrlFetcherCallback(int timeout) {
			super(timeout);
		}
	}
	
	/*
	public static interface HttpUrlFetcherCallback extends Callback<HttpUrlFetcherResult>{
		public void onResult(HttpUrlFetcherResult result);
		public void onError(HttpFetcherException e);
	}
	*/
	

	public static class HttpUrlFetcherResult {

		public String content;
		public String contentType;

		public HttpUrlFetcherResult(String contentType, String content) {
			this.content = content;
			this.contentType = contentType;
		}

	}
	
	@SuppressWarnings("serial")
	public static class HttpFetcherException extends CallbackException {

		public HttpFetcherException(Exception nestedException) {
			super(nestedException);
		}
		
	}

	public static class HTTPUrlFetcher extends AsyncTask<URL, Void, HttpUrlFetcherResult> {

		private HttpUrlFetcherCallback callback;

		public HTTPUrlFetcher(HttpUrlFetcherCallback callback) {
			this.callback = callback;
		}

		protected HttpUrlFetcherResult doInBackground(URL... url) {

			try {
				
				HttpURLConnection urlConnection = (HttpURLConnection) url[0].openConnection();

				String contentType = urlConnection.getContentType();

				BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

				String content = "";
				String line = "";
				while ((line = reader.readLine()) != null)
					content += line;

				return new HttpUrlFetcherResult(contentType, content);

			} catch (IOException e) {
				callback.onError(new HttpFetcherException(e));
			}

			return null;

		}

		protected void onPostExecute(HttpUrlFetcherResult result) {
			if(result != null)
				callback.onResult(result);
        }
	}

}
