package rantonucci.tasklist.db;

import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import rantonucci.tasklist.api.Task;

public class TaskDao extends AbstractDAO<Task>  {

	/**
	 * Create a new DAO
	 * @param sessionFactory
	 */
	public TaskDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	/**
	 * Get a {@link Task} by its id
	 * @param task id
	 * @return
	 */
	public Task getTask(int id){
		return get(id);
	}
	
	/**
	 * Get a list of all the {@link Task} objects
	 * @return
	 */
	public List<Task> getAllTasks(){
		Query query = currentSession().createQuery("from Company");
		return list(query);
	}
	
	/**
	 * Save or update a {@link Task} object to the database
	 * @param task
	 * @throws HibernateException if there was an error
	 */
	public Task saveTask(Task task) throws HibernateException {
		return persist(task);
	}
}
