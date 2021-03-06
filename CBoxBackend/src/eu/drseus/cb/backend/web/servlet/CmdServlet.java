package eu.drseus.cb.backend.web.servlet;

import java.util.HashMap;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.Backend;
import eu.drseus.cb.backend.web.servlet.cmd.CmdRegisterDevice;
import eu.drseus.cb.backend.web.servlet.cmd.ICmd;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;
import eu.drseus.cb.backend.web.servlet.util.URLParameters.ParameterDoesNotExistException;

@SuppressWarnings("serial")
public class CmdServlet extends JSONServlet {

	private HashMap<String, ICmd> cmds = new HashMap<>();
	
	private Backend backend;
	
	public CmdServlet(Backend backend) {
		this.backend = backend;
		register();
	}
	
	private void register() {
		cmds.put("registerDevice", new CmdRegisterDevice(backend));
	}
	
	@Override
	protected JSONObject processGet(RequestInformation reqInfo) {
		try {
			String type = reqInfo.getParameters().getString("action");
			ICmd serv = cmds.get(type);
			if(serv != null){
				return serv.processCommand(reqInfo);
			}else{
				return super.processGet(reqInfo);
			}
		} catch (ParameterDoesNotExistException e) {
			return super.processGet(reqInfo);	
		}		
	}

}
