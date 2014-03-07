package eu.drseus.cb.backend.web.servlet.serv;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import eu.drseus.cb.backend.message.MessageManager;
import eu.drseus.cb.backend.web.servlet.JSONServlet;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;
import eu.drseus.cb.backend.web.servlet.util.URLParameters;
import eu.drseus.cb.backend.web.servlet.util.URLParameters.ParameterDoesNotExistException;
import eu.drseus.cb.shared.forum.chat.Message;

public class ServMessages implements IServ {
	
	@Autowired
	private MessageManager messageManager;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject processRequest(RequestInformation reqInfo) {
		URLParameters parameters = reqInfo.getParameters();
		try {
			String type = parameters.getString("type");
			if(type.equals("latest")){
				Message[] messages = messageManager.getLast(30);
				JSONArray resultArray = new JSONArray();
				for(Message m:messages){
					resultArray.add(m.toJSON());
				}
				JSONObject r = new JSONObject();
				r.put("size", resultArray.size());
				r.put("array", resultArray);
				return r;
			}else{
				return JSONServlet.getError("type parameter invalid");	
			}
		} catch (ParameterDoesNotExistException e) {
			return JSONServlet.getError("type parameter missing");
		} 
	}

}
