package application.database;

public class Comment {
	    private int id;
	    private String text;
	    private String timeStamp; 
	    private int ticketId;
	    
	    public Comment(int id, int ticketId, String text, String timeStamp) {
	        this.id = id;
	        this.ticketId = ticketId;
	        this.text = text;
	        this.timeStamp = timeStamp;
	    }
	    
	    public int getId() {
			return id;
		}
	    
	    public int getTicketId() {
			return ticketId;
		}

		public void setTicketId(int ticketId) {
			this.ticketId = ticketId;
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
