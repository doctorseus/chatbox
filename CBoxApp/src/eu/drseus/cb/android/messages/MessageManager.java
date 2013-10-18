package eu.drseus.cb.android.messages;

public class MessageManager {

	public static MessageManager i;
	
	public static MessageManager getInstance(){
		if(i == null)
			return new MessageManager();
		return i;
	}
	
	public MessageManager(){
		
	}
	
}
