package eu.drseus.cb.backend.web.servlet.serv;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;

public interface IServ {
	public JSONObject processRequest(RequestInformation reqInfo);
}
