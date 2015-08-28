package rantonucci.tasklist.api;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@NotNull
	private String description = "";
	
	@NotNull
	private boolean completed = false;
	
    @JsonProperty("id")
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

	@JsonProperty("description")
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonProperty("completed")
	public boolean getCompleted(){
		return completed;
	}
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	/**
	 * Copy all information (besides the id) from the other task to this task.
	 * @param other
	 */
	public void copy(Task other) {
		description = other.description;
		completed = other.completed;
	}
	
}
