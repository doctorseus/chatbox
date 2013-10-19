package eu.drseus.cb.android.backend;

import java.util.ArrayList;
import java.util.HashMap;

import android.util.Log;
import eu.drseus.cb.android.util.AsyncCallback;
import eu.drseus.cb.android.util.net.HttpClient;
import eu.drseus.cb.android.util.net.HttpClient.HttpRequestCallback;
import eu.drseus.cb.android.util.net.HttpClient.HttpResult;
import eu.drseus.cb.android.util.net.URLBuilder;
import eu.drseus.cb.android.util.net.URLBuilder.PURL;
import eu.drseus.cb.shared.forum.chat.Message;

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

	public void registerDevice(String regId, final PostCallback callback){
		PURL url = URLBuilder.create(BACKEND_URL+CMD_SERVICE);
		url.add("action", "registerDevice");
		
		HashMap<String, String> p = new HashMap<String, String>();
		p.put("regId", regId);
		HttpClient.post(url.getURL(), p, new HttpRequestCallback(callback.getTimeout()) {
			
			@Override
			public void onResult(HttpResult result) {
				String payload = "";
				//TODO: Parse results...
				callback.onResult(payload);
			}
			
			@Override
			public void onError(CallbackException e) {
				callback.onError(e);
			}
		});
		
	}
	
	public void getLatestMessages(long lastMessageId, final MessageQueryCallback callback){
		PURL url = URLBuilder.create(BACKEND_URL+SERV_SERVICE);
		url.add("view", "messages");
		url.add("type", "latest");
		if(lastMessageId > 0)
			url.add("lastMessageId", Long.toString(lastMessageId));
		
		HttpClient.get(url.getURL(), new HttpRequestCallback(callback.getTimeout()) {
			
			@Override
			public void onResult(HttpResult result) {
				ArrayList<Message> messages = new ArrayList<Message>();
				
				//TODO: Parse results...
				Log.i(TAG, result.content);				
				
				callback.onResult(messages);
			}
			
			@Override
			public void onError(CallbackException e) {
				callback.onError(e);
			}
		});
		
	}
	
	public abstract static class PostCallback extends AsyncCallback<String>{

		public PostCallback(int timeout) {
			super(timeout);
		}
		
	}
	
	public abstract static class MessageQueryCallback extends AsyncCallback<ArrayList<Message>>{

		public MessageQueryCallback(int timeout) {
			super(timeout);
		}

	}
	
}
