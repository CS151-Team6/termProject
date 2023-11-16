package application.database;

public class Comment {
	    private int id;
	    private String text;
	    private String timeStamp; 
	    
	    public Comment(int id, String text, String timeStamp) {
	        this.id = id;
	        this.text = text;
	        this.timeStamp = timeStamp;
	    }
	    
	    public int getId() {
			return id;
		}
	    
	    public String getTimeStamp() {
	        return timeStamp;
	    }

	    public void setTimeStamp(String timeStamp) {
	        this.timeStamp = timeStamp;
	    }

	    public String getText() {
	        return text;
	    }

	    public void setDescription(String text) {
	        this.text = text;
	    }

	    @Override
	    public String toString() {
	        return "Comment{" +
	               "id=" + id +
	               ", text='" + text + '\'' +
	               ", timeStamp='" + timeStamp + '\'' +
	               '}';
	    }

}
