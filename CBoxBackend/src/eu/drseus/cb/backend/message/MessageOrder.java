package eu.drseus.cb.backend.message;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageOrder {
	
	private int size;
	private ArrayList<Long> ids = new ArrayList<>();

	public MessageOrder(int size) {
		this.size = size;
	}

	public void add(long id) {
		if (ids.size() >= size) {
			ids.remove(ids.size() - 1);
		}
		ids.add(0, id);
	}

	public boolean contains(long id) {
		return ids.contains(id);
	}
	
	public ArrayList<Long> getLast(int limit){
		if(limit > size)
			limit = size;
		
		ArrayList<Long> lastIds = new ArrayList<>();
		Iterator<Long> iterator = ids.iterator();
		int c = 0;
		while(c < limit && iterator.hasNext()){
			lastIds.add(iterator.next());
			c++;
		}
		return lastIds;
	}

	@Override
	public String toString() {
		return "MessageOrder [size=" + size + ", ids=" + ids + "]";
	}


}
