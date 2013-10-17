package eu.drseus.cb.backend.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import eu.drseus.cb.backend.Backend;
import eu.drseus.cb.backend.web.servlet.CmdServlet;
import eu.drseus.cb.backend.web.servlet.ServServlet;

public class WebServer {
	
	private Backend backend;
	
	private Server jetty;
	
	public WebServer(Backend backend){
		this.backend = backend;
		init();
	}
	
	private void init() {
		jetty = new Server(8080);
		
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletHandler.addServlet(new ServletHolder(new CmdServlet(backend)), "/cmd");
		servletHandler.addServlet(new ServletHolder(new ServServlet(backend)), "/serv");
		
        jetty.setHandler(servletHandler);
        
	}

	public void start() throws Exception{
		jetty.start();
	}

}
