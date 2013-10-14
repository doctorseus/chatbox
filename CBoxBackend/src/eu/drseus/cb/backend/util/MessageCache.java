package eu.drseus.cb.backend.util;

import java.util.LinkedHashMap;

import eu.drseus.cb.backend.forum.chat.Message;

public class MessageCache {

	private LinkedHashMap<Long, Message> hashMap = new LinkedHashMap<>();

	public MessageCache() {

	}

}
