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
    
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory(){
    	return database;
    }
}

