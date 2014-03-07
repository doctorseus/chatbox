package eu.drseus.cb.backend.web.servlet.cmd;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import eu.drseus.cb.backend.gcm.GCMManager;
import eu.drseus.cb.backend.web.servlet.JSONServlet;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;
import eu.drseus.cb.backend.web.servlet.util.URLParameters;
import eu.drseus.cb.backend.web.servlet.util.URLParameters.ParameterDoesNotExistException;

public class CmdRegisterDevice implements ICmd {
	
	@Autowired
	private GCMManager gcmManager;

	@Override
	public JSONObject processCommand(RequestInformation reqInfo) {
		URLParameters parameters = reqInfo.getParameters();
		
		try {
			String regId = parameters.getString("regId");
			gcmManager.register(regId);			
			return JSONServlet.getSuccess();
		} catch (ParameterDoesNotExistException e) {	
			return JSONServlet.getError("regId parameter missing.");
		}
	}

}
