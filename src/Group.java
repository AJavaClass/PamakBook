import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class Group implements Comparable<Group>, Serializable{
	
	private String name;
	private String description;
	private Collection<User> members;
	private static Collection<Group> groups = new TreeSet<Group>();
	
	public String toString() {
		return this.getName();
	}
	
	public int compareTo(Group aGroup) { 
		return 1; 
	}
	
	public Group(String aName, String aDescription) {
		name = aName;
		description = aDescription;
		members = new TreeSet<>();
		Group.addGroupInstance(this);
	}
	//Επιστρέφει το όνομα του group
	public String getName() {
		return this.name;
	}
	//Επιστρέφει την περιγραφή του group
	public String getDescription() {
		return this.description;
	}
	//Επιστρέφει τα μέλη του group
	public Collection<User> getMembers(){
		return this.members;
	}
	
	public static void addGroupInstance(Group aGroup) {
		groups.add(aGroup);
	}
	
	public static Collection<Group> getAllInstances(){
		return groups;
	}
	
	//Ελέγχει αν ένας χρήστης είναι μέλος του group
	public boolean isMember(User user) {
		if (this.getMembers().contains(user)) {
			return true;
		}
		return false;
	}
	//Προσθέτει ένας μέλος στο group
	public void addMember(User user) {
		if(!isMember(user)) {
			this.getMembers().add(user);
			user.addToGroup(this);
			JOptionPane.showMessageDialog(null,user.getName() + " has been successfuly enrolled in group " + this.getName());
		}
		else
			JOptionPane.showMessageDialog(null,user.getName() + " is already member of this group.");
	}
	
	//Εκτιπώνει τα ονόματα των χρηστών που είναι μέλη του group το οποία κάλεσε την μέθοδο
	public void printMembers() {
		System.out.println("*******************************");
		System.out.println("Members of group " + this.getName());
		System.out.println("*******************************");
		for(User member: this.getMembers()) {
			System.out.println(member.getName());
		}
		System.out.println("-----------------------------");
	}
}
