package rantonucci.tasklist.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.codahale.metrics.annotation.Timed;

import io.dropwizard.hibernate.UnitOfWork;
import rantonucci.tasklist.api.Task;
import rantonucci.tasklist.db.TaskDao;

/**
 * User information resource
 */
@Path("/task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {
	private TaskDao dao;
	
    public TaskResource(TaskDao dao) {
    	this.dao = dao;
    }

    /**
     * Get all the tasks
     * 
     * Path: /task
     */
    @GET
    @Timed
    @UnitOfWork
    public List<Task> getTasks() {
    	// Make some database call to find the company's object
    	List<Task> tasks = dao.getAllTasks();
        return tasks;
    }
    
    /**
     * Get a single task
     * 
     * Path: /task/{id}
     * 
     * @param id the id of the desired task
     */
    @GET
    @Timed
    @UnitOfWork
    @Path("{id}")
    public Task getTask(@PathParam("id") int id) {
    	// Make some database call to find the user's user object
    	Task task = dao.getTask(id);
    	
    	if(task == null){
    		throw new WebApplicationException("Unknown task '"+ id + "'", Response.Status.NOT_FOUND);
    	}

        return task;
    }

    /**
     * Create a new task
     * 
     * Path: /task
     * 
     * @param newTask the new task
     * @return a URL to a REST endpoint to view the new task
     */
    @POST
    @Timed
    @UnitOfWork
    public Response newTask(@Valid Task newTask) {
    	newTask = dao.saveTask(newTask);
    	
    	// Good REST practice to return in the response a URL to view the newly created object 
    	URI location = UriBuilder.fromResource(TaskResource.class).path(TaskResource.class, "getTask").build(newTask.getId());
    	return Response.created(location).status(Status.OK).entity(newTask).build();
    }    
    
    /**
     * Update a task
     * 
     * Path: /task
     * 
     * @param task a task object with the new info to update to
     * @return a URL to a REST endpoint to view the new task
     */
    @PUT
    @Timed
    @UnitOfWork
    public Response updateTask(@Valid Task newTask) {
    	
    	// Verify it's there first.
    	Task existingTask = dao.getTask(newTask.getId());
    	if (existingTask == null) {
    		throw new WebApplicationException("Unknown task '"+ newTask.getId() + "'", Response.Status.NOT_FOUND);
    	}

    	existingTask.copy(newTask);
    	newTask = dao.saveTask(existingTask);
    	
    	// Good REST practice to return in the response a URL to view the newly created object 
    	URI location = UriBuilder.fromResource(TaskResource.class).path(TaskResource.class, "getTask").build(newTask.getId());
    	return Response.created(location).build();
    }    
    
    /**
     * Delete a task
     * 
     * Path: /task/{id}
     * 
     * @param id the id of the task to delete
     */
    @DELETE
    @Timed
    @UnitOfWork
    @Path("{id}")
    public Response deleteTask(@PathParam("id") int id) {
    	Task existingTask = dao.getTask(id);
    	if (existingTask != null) {
    		dao.deleteTask(existingTask);
    	}
    	
    	return Response.noContent().build();
    }

}
