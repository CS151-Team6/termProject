package application.database;

public class Ticket {
	private int id;
	private String projId;
    private String name;
    private String description;

   
    public Ticket(int id, String projId, String name, String description) {
    	this.name = name;
        this.id = id;
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
        return "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description;
    }

	
}
