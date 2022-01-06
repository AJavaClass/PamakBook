import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		
		TreeSet<User> userSet;
		
		Group g1 = new Group("WebGurus","A group for web passionates");
		ClosedGroup g2 = new ClosedGroup("ExamSolutions","Solutions to common exam questions");
		
		/*
		User u1 = new User("Makis", "iis1998@uom.edu.gr");
		User u2 = new User("Petros", "ics1924@uom.edu.gr");
		User u3 = new User("Maria", "iis2012@uom.edu.gr");
		User u4 = new User("Gianna", "iis19133@uom.edu.gr");
		User u5 = new User("Nikos", "dai1758@uom.edu.gr");
		User u6 = new User("Babis", "ics19104@uom.edu.gr");
		User u7 = new User("Stella", "dai1827@uom.edu.gr");
		User u8 = new User("Eleni", "ics25ss@gmail.com");

		u1.addFriend(u2);
		u1.addFriend(u5);
		u5.addFriend(u6);
		u3.addFriend(u4);
		u3.addFriend(u2);
		u4.addFriend(u6);
		u5.addFriend(u3);
		u1.addFriend(u6);
		u5.addFriend(u2);
		u7.addFriend(u1);

		g1.addMember(u4);
		g1.addMember(u3);
		g1.addMember(u2);
		g2.addMember(u4);
		g2.addMember(u5);
		g2.addMember(u6);
		g2.addMember(u5);
		*/
		
		try {
			File file = new File("PamakBookData.ser");
			FileInputStream fInputStream = new FileInputStream(file);
			ObjectInputStream inputStream = new ObjectInputStream(fInputStream);
			userSet = (TreeSet<User>) inputStream.readObject();
			User.addUsers(userSet);
			inputStream.close();
		}
		catch(ClassNotFoundException exc) {
			exc.printStackTrace();
		}
		catch(IOException exc) {
			System.out.println("This is the first run of this program.");
		}
		
		
		new LoginPage();
		
	}
	

}
