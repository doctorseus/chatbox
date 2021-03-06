package eu.drseus.cb.backend.message;

import java.util.LinkedHashMap;

import eu.drseus.cb.backend.forum.chat.Message;

public class MessageCache {

	@SuppressWarnings("unused")
	private int cacheSize;
	
	private LinkedHashMap<Long, Message> hashMap = new LinkedHashMap<>();

	public MessageCache(int size) {
		this.cacheSize = size;
	}

	public boolean contains(long messageId) {
		return hashMap.containsKey(messageId);
	}

	public Message get(long messageId) {
		return hashMap.get(messageId);
	}

	public void put(Message m) {
		hashMap.put(m.getId(), m);
	}

}
