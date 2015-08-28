package rantonucci.tasklist.resources;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

/**
 * Sets CORS headers so that the REST endpoints can be used from a Javascript client
 * hosted somewhere else
 */
public class CorsResponseFilter
implements ContainerResponseFilter {
	
	private String remoteWebServer;
	
	public CorsResponseFilter(String remoteWebServer) {
		this.remoteWebServer = ((remoteWebServer == null) || remoteWebServer.trim().isEmpty() ? "*" : remoteWebServer);
	}

	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		MultivaluedMap<String, Object> headers = responseContext.getHeaders();

		headers.add("Access-Control-Allow-Credentials", "true");
		headers.add("Access-Control-Allow-Origin", remoteWebServer);
		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");			
		headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia, Accept, Authorization");
	}

}