package eu.drseus.cb.backend.web.servlet.cmd;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;

public interface ICmd {
	public JSONObject processCommand(RequestInformation reqInfo);
}
