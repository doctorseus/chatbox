package eu.drseus.cb.backend.web;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;

import eu.drseus.cb.backend.util.Config;
import eu.drseus.cb.backend.web.servlet.CmdServlet;
import eu.drseus.cb.backend.web.servlet.ServServlet;

public class WebServer implements Lifecycle {
	
	private boolean running = false;
	@Autowired
	private Config config;
	
	private Server jetty;
	
	public WebServer(){	}
	
	@Override
	public void start() {
		jetty = new Server(config.web.port);
		
		ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletHandler.addServlet(new ServletHolder(new CmdServlet()), "/cmd");
		servletHandler.addServlet(new ServletHolder(new ServServlet()), "/serv");
		
        jetty.setHandler(servletHandler);
        
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	public void startServer() throws Exception{
		jetty.start();
	}

}
