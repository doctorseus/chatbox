package eu.drseus.cb.shared.util;

import org.json.simple.JSONObject;

public interface IJSONable<T> {
	public JSONObject toJSON();
}
