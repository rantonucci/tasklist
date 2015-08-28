package rantonucci.tasklist;

import rantonucci.tasklist.resources.CorsResponseFilter;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import rantonucci.tasklist.api.Task;
import rantonucci.tasklist.command.BootstrapCommand;
import rantonucci.tasklist.db.TaskDao;
import rantonucci.tasklist.resources.TaskResource;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

/**
 * The main application class.
 */
public class TaskApplication extends Application<TaskConfiguration> {

    public static void main(String[] args) throws Exception {
        new TaskApplication().run(args);
    }

    @Override
    public String getName() {
        return "Task List Webapp";
    }

    @Override
    public void initialize(Bootstrap<TaskConfiguration> bootstrap) {
    	// Set up the hibernate bundle
    	bootstrap.addBundle(hibernate);
    	// Add our bootstrap command
    	bootstrap.addCommand(new BootstrapCommand());
    	
    	// Serve up assets from the assets directory
    	  bootstrap.addBundle(new AssetsBundle("/assets", "/assets", "index.html"));
    }
    
    // Hibernate connection bundle
    private final HibernateBundle<TaskConfiguration> hibernate =
    		new HibernateBundle<TaskConfiguration>(Task.class) {
		        @Override
		        public DataSourceFactory getDataSourceFactory(TaskConfiguration configuration) {
		            return configuration.getDataSourceFactory();
		        }
		    };

    @Override
    public void run(TaskConfiguration configuration,
                    Environment environment) {
    	
        environment.jersey().register(new CorsResponseFilter(configuration.getRemoteWebServer()));

    	final TaskDao taskDao = new TaskDao(hibernate.getSessionFactory());
        environment.jersey().register(new TaskResource(taskDao));


    }
}