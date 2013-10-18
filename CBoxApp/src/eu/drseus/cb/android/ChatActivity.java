package eu.drseus.cb.android;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import eu.drseus.cb.android.backend.BackendService;
import eu.drseus.cb.android.backend.BackendService.MessageQueryCallback;
import eu.drseus.cb.android.gcm.GcmUtils;
import eu.drseus.cb.android.gcm.GcmUtils.DeviceRegisterCallback;
import eu.drseus.cb.backend.forum.chat.Message;

public class ChatActivity extends Activity {
	
	public String TAG = this.getClass().getName();

	public static final String EXTRA_MESSAGE = "message";

    private GoogleCloudMessaging gcm;
    //AtomicInteger msgId = new AtomicInteger();
    //SharedPreferences prefs;
    private Context context;

    private String regid;
    
	private ListView chatListView;
	private ChatListAdapter chatListAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        

        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with
        //  GCM registration.
        if (GcmUtils.checkPlayServices(this)) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = GcmUtils.getRegistrationId(context);

            if (regid.length() == 0) {
            	GcmUtils.registerDevice(this, new DeviceRegisterCallback(10) {
					
					@Override
					public void onResult(String gcmid) {
						Log.i(TAG, "New GCM-ID arrived: "+gcmid);
					}
					
					@Override
					public void onError(CallbackException e) {
						 Log.e(TAG, "Was not able to register the device: "+e.getMessage());
						 e.printStackTrace();
					}
				});

            }else{
            	Log.i(TAG, "Stored GCM-ID found: "+regid);
            }
            
        } else {
            Log.e(TAG, "No valid Google Play Services APK found.");
        }
        
        
        //ListView
        //chatListAdapter = new ChatListAdapter(context, null, false);
        
        chatListView = (ListView) findViewById(R.id.list_message);
        //chatListView.setAdapter(chatListAdapter);
        
        BackendService.getInstance().getLatestMessages(0, new MessageQueryCallback(60) {
			
			@Override
			public void onResult(ArrayList<Message> result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(CallbackException e) {
				// TODO Auto-generated method stub
				Log.e(TAG, e.getMessage());
			}
			
		});
    }

}
