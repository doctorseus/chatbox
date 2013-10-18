package eu.drseus.cb.backend.gcm;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import eu.drseus.cb.backend.message.MessageManager;

public class GCMManager {
	
	private Log log = LogFactory.getLog(MessageManager.class);
	
	private DeviceStorage storage = new DeviceStorage();
	
	public GCMManager(){
		
	}
	
	public void register(String regId) {
		storage.register(regId);
		log.info("device registered - "+regId);
	}

	public void unregister(String regId) {
		storage.unregister(regId);
		log.info("device unregistered - "+regId);
	}

	public void updateRegistration(String oldId, String newId) {
		storage.updateRegistration(oldId, newId);
		log.info("device updated - "+oldId+" to "+newId);
	}

	public List<String> getAllDevices() {
		return storage.getDevices();
	}	
}
