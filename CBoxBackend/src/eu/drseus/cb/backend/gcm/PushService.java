package eu.drseus.cb.backend.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import eu.drseus.cb.backend.Backend;
import eu.drseus.cb.backend.message.IMessageListener;
import eu.drseus.cb.shared.forum.chat.Message;

public class PushService implements IMessageListener {

	private static boolean SINGLE_DEVICE_DEBUG = true;
	
	private Log log = LogFactory.getLog(PushService.class);
	
	private Backend backend;
	
	private Sender sender;
	private static final int MULTICAST_SIZE = 1000;
	private static final Executor threadPool = Executors.newFixedThreadPool(5);
	
	public PushService(){ }
	
	public void init(Backend backend, String key) {
		this.backend = backend;
		this.sender = new Sender(key);
	}	

	@Override
	public void onUpdate(ArrayList<Message> newMessages, ArrayList<Message> updatedMessages) {
		if(newMessages.size() > 0 /*|| updatedMessages.size() > 0*/){
			
			GCMManager gcmManager = backend.getGcmManager();
			List<String> devices = gcmManager.getAllDevices();

			List<String> partialDevices = new ArrayList<String>(MULTICAST_SIZE);
			int counter = 0;
			int tasks = 0;
			for (String device : devices) {
				counter++;
				partialDevices.add(device);
				int partialSize = partialDevices.size();
				if (partialSize == MULTICAST_SIZE || counter == devices.size()) {
					pushUpdateMessage(new ArrayList<String>(partialDevices));
					partialDevices.clear();
					tasks++;
				}
			}

	        log.trace("Asynchronously sending " + tasks + " multicast messages to " + devices.size() + " devices");
			
		}		
	}
	
	private void pushUpdateMessage(List<String> receiverDevices){
		com.google.android.gcm.server.Message message = new com.google.android.gcm.server.Message.Builder().build();
		
		pushMessage(receiverDevices, message);
	}
	
	private void pushMessage(final List<String> receiverDevices, final com.google.android.gcm.server.Message message) {

		threadPool.execute(new Runnable() {
			public void run() {
				try {
					MulticastResult multicastResult = sender.send(message, receiverDevices, 5);

					List<Result> results = multicastResult.getResults();

					for (int i = 0; i < receiverDevices.size(); i++) {
						String regId = receiverDevices.get(i);
						Result result = results.get(i);
						String messageId = result.getMessageId();
						if (messageId != null) {
							if (SINGLE_DEVICE_DEBUG)
								log.trace("Succesfully sent message to device: " + regId + "; messageId = " + messageId);
							String canonicalRegId = result
									.getCanonicalRegistrationId();

							if (canonicalRegId != null) {
								// same device has more than on registration id:
								// update it
								backend.getGcmManager().updateRegistration(regId, canonicalRegId);
							}

						} else {
							String error = result.getErrorCodeName();
							if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
								// application has been removed from device -
								// unregister it
								backend.getGcmManager().unregister(regId);
							} else {
								if (SINGLE_DEVICE_DEBUG)
									log.error("Error sending message to " + regId + ": " + error);
							}
						}

					}
				} catch (IOException e) {
					log.error("Error sending message request: "+e.getMessage());
				}

			}
		});	
        
	}

	@Override
	public void onMessageNew(Message newMessage) {}

	@Override
	public void onMessageUpdate(Message updatedMessage) {}

}