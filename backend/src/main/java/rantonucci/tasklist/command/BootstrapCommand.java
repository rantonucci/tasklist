package rantonucci.tasklist.command;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import rantonucci.tasklist.TaskConfiguration;
import rantonucci.tasklist.api.Task;
import rantonucci.tasklist.db.TaskDao;

/**
 * Command class for loading some default data into the database
 */
public class BootstrapCommand extends ConfiguredCommand<TaskConfiguration> {

	public BootstrapCommand() {
		super("bootstrap", "Load default data into the database");
	}
	
    // Hibernate connection bundle
    private final HibernateBundle<TaskConfiguration> hibernate =
            new HibernateBundle<TaskConfiguration>(Task.class) {
		        @Override
		        public DataSourceFactory getDataSourceFactory(TaskConfiguration configuration) {
		        	// Force set the hibernate property to auto-create the tables
		        	configuration.getDataSourceFactory().getProperties().put("hibernate.hbm2ddl.auto", "create");
		        	
		            return configuration.getDataSourceFactory();
		        }
		    };

	@Override
	protected void run(Bootstrap<TaskConfiguration> bootstrap,
			Namespace namespace, TaskConfiguration configuration)
			throws Exception {
		
		SessionFactory sf = hibernate.getSessionFactory();
		if(sf == null){
			try{
				// Create the environment to use
				Environment env = new Environment("Bootstrap", bootstrap.getObjectMapper(),
						bootstrap.getValidatorFactory().getValidator(),
						bootstrap.getMetricRegistry(), bootstrap.getClassLoader());
				// Make hibernate connect
				hibernate.run(configuration, env);
				sf = hibernate.getSessionFactory();
			} catch(Exception e){
				System.exit(-1);
			}
		}
		
		// Manually create a Hibernate session
		// See http://stackoverflow.com/questions/29370233/dropwizard-sessions-at-program-run
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		ManagedSessionContext.bind(session);		
		
		// Load the DAOs
        final TaskDao taskDao = new TaskDao(hibernate.getSessionFactory());
		
        // Populate the database
		Task newTask = new Task();
		newTask.setDescription("Floss cat");
		newTask = taskDao.saveTask(newTask);
		
		
		session.flush();
		transaction.commit();

		try{
		    // Sleep so hibernate can do its thing
		    Thread.sleep(5000);
		} catch(Exception e){
		    // Ignore
		}

		session.disconnect();
		session.close();
		hibernate.getSessionFactory().close();
	}

}
