package rantonucci.tasklist;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    }
    
    @Override
    public void run(TaskConfiguration configuration,
                    Environment environment) {
    }
}