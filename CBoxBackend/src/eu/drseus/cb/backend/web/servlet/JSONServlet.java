package eu.drseus.cb.backend.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import eu.drseus.cb.backend.web.servlet.util.LocalRequest;
import eu.drseus.cb.backend.web.servlet.util.LocalRequest.RequestInformation;

@SuppressWarnings("serial")
public class JSONServlet extends HttpServlet {

	@Override
	final protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		JSONObject result = processGet(LocalRequest.create(req));
		if(result == null){
			result = getError("Currently not supported.");
		}
		writer.append(result.toJSONString());
		writer.close();
	}
	
	protected JSONObject processGet(RequestInformation reqInfo){
		return getError("Currently not supported.");
	}
	
	@Override
	final protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		PrintWriter writer = resp.getWriter();
		JSONObject result = processPost(LocalRequest.create(req));
		if(result == null){
			result = getError("Currently not supported.");
		}
		writer.append(result.toJSONString());
		writer.close();
	}
	
	protected JSONObject processPost(RequestInformation reqInfo){
		return getError("Currently not supported.");
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject getError(String errorMsg){
		JSONObject o = new JSONObject();
		o.put("error", errorMsg);
		return o;
	}
	
	@Override
	final protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doDelete(req, resp);
	}
	
	@Override
	final protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doHead(req, resp);
	}
	
	@Override
	final protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		super.doOptions(arg0, arg1);
	}
	
	@Override
	final protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPut(req, resp);
	}
	
	@Override
	final protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		super.doTrace(arg0, arg1);
	}
	
}
