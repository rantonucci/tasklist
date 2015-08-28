package rantonucci.tasklist;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The configuration class.
 * This class is loaded and deserialized from the YML file specified when launching the application
 */
public class TaskConfiguration extends Configuration {
	@Valid
	@NotNull
	@JsonProperty
	private DataSourceFactory database = new DataSourceFactory();
	
    /**
     * If the webapp is server by a separate server, the backend needs to explicitly
     * allow CORS requests from that server.  Must match the referrer URL (e.g. https://violation.dci.net or http://localhost:8000).
     */
    private String remoteWebServer;
    

    
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory(){
    	return database;
    }
    
    @JsonProperty
    public String getRemoteWebServer() {
    	// Return the default value if no value is provided.
    	return remoteWebServer;
    }
    
    @JsonProperty
    public void setRemoteWebServer(String url) {
    	this.remoteWebServer = url;
    }


}

