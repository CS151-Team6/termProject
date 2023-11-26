package application.database;

public class Project {
	private int id;
    private String name;
    private String startDate;
    private String description;

    public Project(int id, String name, String startDate, String description) {
    	this.id = id;
    	this.name = name;
        this.startDate = startDate;
        this.description = description;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Project{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", startDate='" + startDate + '\'' +
               ", description='" + description + '\'' +
               '}';
    }
}
