package application.database;

public class Ticket {
	private int id;
	private String projId;
    private String name;
    private String created;
    private String description;

   
    public Ticket(int id, String projId, String name, String created, String description) {
    	this.name = name;
        this.id = id;
        this.created = created;
        this.description = description;
        this.projId = projId;
    }
    
    public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public int getId() {
    	return id;
    }

    public String getName() {
        return name;
    }
    
    public String getCreated() {
    	return created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", name='" + created + '\'' +
               ", description='" + description + '\'' +
               '}';
    }

	
}
