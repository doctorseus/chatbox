package eu.drseus.cb.backend.web.servlet;

import java.util.HashMap;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.web.servlet.serv.IServ;
import eu.drseus.cb.backend.web.servlet.serv.ServMessages;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;
import eu.drseus.cb.backend.web.servlet.util.URLParameters.ParameterDoesNotExistException;

@SuppressWarnings("serial")
public class ServServlet extends JSONServlet {
	
	private HashMap<String, IServ> servs = new HashMap<>();
	
	public ServServlet() {
		register();
	}
	
	private void register() {
		servs.put("messages", new ServMessages());
	}

	@Override
	protected JSONObject processGet(RequestInformation reqInfo) {
		try {
			String type = reqInfo.getParameters().getString("view");
			IServ serv = servs.get(type);
			if(serv != null){
				return serv.processRequest(reqInfo);
			}else{
				return super.processGet(reqInfo);
			}
		} catch (ParameterDoesNotExistException e) {
			return super.processGet(reqInfo);	
		}		
	}

}
