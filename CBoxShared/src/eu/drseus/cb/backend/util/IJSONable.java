package eu.drseus.cb.backend.util;

import org.json.simple.JSONObject;

public interface IJSONable<T> {
	public JSONObject toJSON();
}
