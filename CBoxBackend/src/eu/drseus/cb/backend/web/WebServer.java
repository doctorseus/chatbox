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
	
	public WebServer(){	}
	
	public void init(Backend backend, int port) {
		this.backend = backend;
		
		jetty = new Server(port);
		
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletHandler.addServlet(new ServletHolder(new CmdServlet(this.backend)), "/cmd");
		servletHandler.addServlet(new ServletHolder(new ServServlet(this.backend)), "/serv");
		
        jetty.setHandler(servletHandler);
        
	}

	public void start() throws Exception{
		jetty.start();
	}

}
