import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Post implements Comparable<Post>, Serializable{

	private Date timestamp;
	private String text;
	private User publisher;
	
	public Post(String aText, User aUser) {
		timestamp = new Date();
		text = aText;
		publisher = aUser;
		publisher.addPost(this);
	}
	
	public int compareTo(Post aPost) {
		if(this.getTimestamp().compareTo(aPost.getTimestamp()) == -1) {
			return -1;
		}
		return 1;
	}
	
	public String getTimestampFormated() { 
         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", new Locale("el"));  
         String strDate = dateFormat.format(this.getTimestamp());
         return strDate;
	}
	
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public User getPublisher() {
		return publisher;
	}
	
}
