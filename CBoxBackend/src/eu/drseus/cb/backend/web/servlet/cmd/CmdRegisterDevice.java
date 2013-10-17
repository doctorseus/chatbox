package eu.drseus.cb.backend.web.servlet.cmd;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.Backend;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;

public class CmdRegisterDevice implements ICmd {
	
	@SuppressWarnings("unused")
	private Backend backend;

	public CmdRegisterDevice(Backend backend) {
		this.backend = backend;
	}

	@Override
	public JSONObject processCommand(RequestInformation reqInfo) {
		JSONObject o = new JSONObject(reqInfo.getParameters().getParameterMap());
		return o;
	}

}
