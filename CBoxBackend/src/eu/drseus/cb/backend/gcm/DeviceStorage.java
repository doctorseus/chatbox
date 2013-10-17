package eu.drseus.cb.backend.gcm;

import java.util.ArrayList;
import java.util.List;

public class DeviceStorage {

	private final List<String> regIds = new ArrayList<String>();
	
	public DeviceStorage(){
		
	}

	public void register(String regId) {
		synchronized (regIds) {
			regIds.add(regId);
		}
	}

	public void unregister(String regId) {
		synchronized (regIds) {
			regIds.remove(regId);
		}
	}

	public void updateRegistration(String oldId, String newId) {
		synchronized (regIds) {
			regIds.remove(oldId);
			regIds.add(newId);
		}
	}

	public List<String> getDevices() {
		synchronized (regIds) {
			return new ArrayList<String>(regIds);
		}
	}

}
