package eu.drseus.cb.backend.gcm;

import java.util.List;

public class GCMManager {
	
	private DeviceStorage storage = new DeviceStorage();
	
	public GCMManager(){
		
	}
	
	public void register(String regId) {
		storage.register(regId);
	}

	public void unregister(String regId) {
		storage.unregister(regId);
	}

	public void updateRegistration(String oldId, String newId) {
		storage.updateRegistration(oldId, newId);
	}

	public List<String> getDevices() {
		return storage.getDevices();
	}	
}
