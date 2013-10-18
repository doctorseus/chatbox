package eu.drseus.cb.backend.web.servlet.cmd;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.Backend;
import eu.drseus.cb.backend.web.servlet.JSONServlet;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;
import eu.drseus.cb.backend.web.servlet.util.URLParameters;
import eu.drseus.cb.backend.web.servlet.util.URLParameters.ParameterDoesNotExistException;

public class CmdRegisterDevice implements ICmd {
	
	private Backend backend;

	public CmdRegisterDevice(Backend backend) {
		this.backend = backend;
	}

	@Override
	public JSONObject processCommand(RequestInformation reqInfo) {
		URLParameters parameters = reqInfo.getParameters();
		
		try {
			String regId = parameters.getString("regId");
			backend.getGcmManager().register(regId);			
			return JSONServlet.getSuccess();
		} catch (ParameterDoesNotExistException e) {	
			return JSONServlet.getError("regId parameter missing.");
		}
	}

}
