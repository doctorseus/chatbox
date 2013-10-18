package eu.drseus.cb.backend.message;

import eu.drseus.cb.backend.forum.chat.Message;

public class MessageStorage extends MessageCache {

	public MessageStorage(int size) {
		super(size);
	}
	
	@Override
	public void put(Message m) {
		// TODO Auto-generated method stub
		super.put(m);
	}
	
	public void commit(){
		
	}

}
