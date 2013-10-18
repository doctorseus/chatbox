package eu.drseus.cb.android.backend;

import java.util.ArrayList;

import android.util.Log;

import eu.drseus.cb.android.util.AsyncCallback;
import eu.drseus.cb.android.util.net.HttpClient;
import eu.drseus.cb.android.util.net.HttpClient.HttpUrlFetcherCallback;
import eu.drseus.cb.android.util.net.HttpClient.HttpUrlFetcherResult;
import eu.drseus.cb.android.util.net.URLBuilder;
import eu.drseus.cb.android.util.net.URLBuilder.PURL;
import eu.drseus.cb.backend.forum.chat.Message;

public class BackendService {
	
	public String TAG = this.getClass().getName();
	
	private static final String BACKEND_URL = "http://chatbox.natriumhydrogencarbon.at";
	private static final String CMD_SERVICE = "/cmd";
	private static final String SERV_SERVICE = "/serv";

	private static BackendService i;
	
	public static BackendService getInstance(){
		if(i == null)
			return new BackendService();
		return i;
	}

	public void registerDevice(String regId){
		
	}
	
	public void getLatestMessages(long lastMessageId, final MessageQueryCallback callback){
		PURL url = URLBuilder.create(BACKEND_URL+SERV_SERVICE);
		url.add("view", "messages");
		url.add("type", "latest");
		if(lastMessageId > 0)
			url.add("lastMessageId", Long.toString(lastMessageId));
		
		HttpClient.fetchURL(url.getURL(), new HttpUrlFetcherCallback(callback.getTimeout()) {
			
			@Override
			public void onResult(HttpUrlFetcherResult result) {
				ArrayList<Message> messages = new ArrayList<Message>();
				
				Log.i(TAG, result.content);				
				
				callback.onResult(messages);
			}
			
			@Override
			public void onError(CallbackException e) {
				callback.onError(e);
			}
		});
		
	}
	
	public abstract static class MessageQueryCallback extends AsyncCallback<ArrayList<Message>>{

		public MessageQueryCallback(int timeout) {
			super(timeout);
		}


	}
	
}
