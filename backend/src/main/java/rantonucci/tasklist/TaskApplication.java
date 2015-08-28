package rantonucci.tasklist;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import rantonucci.tasklist.api.Task;
import rantonucci.tasklist.command.BootstrapCommand;
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
    }
}