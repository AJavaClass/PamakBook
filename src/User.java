import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

import javax.swing.JOptionPane;

public class User implements Comparable<User>, Serializable{

	private String name;
	private String email;
	private Collection<User> friends;
	private Collection<Group> enrolledGroups;
	private Collection<Post> posts;
	private static Collection<User> users = new TreeSet<>();
	
	public User(String aName, String anEmail) {
		
		if(isValidEmail(anEmail)) {
			name = aName;
			email = anEmail;
			friends = new TreeSet<>();
			enrolledGroups = new TreeSet<>();
			posts = new TreeSet<>();
			User.addUserInstance(this);
		}
		else {
			JOptionPane.showMessageDialog(null,"Type a valid email");
		}
			
	}
	
	public String toString() {
		return name;
	}
	
	public int compareTo(User aUser) {
		User user = aUser;
		return (this.getName().compareTo(user.getName()));
	}
	
	public static void addUserInstance(User aUser) {
		users.add(aUser);
	}
	
	public static void addUsers(Collection<User> listOfUsers) {
		users.addAll(listOfUsers);
	}
	
	public static Collection<User> getAllInstances() {
		return users;
	}
	
	//Ελέγχει αν το email του χρήστη είναι σωστό
	public boolean isValidEmail(String aString) {
		String uomDomain = "@uom.edu.gr";
		if (aString.length() >= 17 && aString.length() < 20) {
			Collection<String> departmentType = new TreeSet<>();
			departmentType.add("ics");
			departmentType.add("iis");
			departmentType.add("dai");
			if(aString.endsWith(uomDomain)) {
				if(departmentType.contains(aString.substring(0, 3))) {
					for(Character c: aString.substring(aString.length()-14, aString.length()-11).toCharArray()) {
						if(Character.isDigit(c)) {
							return true;
						}
					}	
				}
			}
		}
		return false;
	}
	
	//Επιστρέφει το όνομα του χρήστη
	public String getName() {
		return this.name;
	}
	//Επιστρέφει το email του χρήστη
	public String getEmail() {
		return this.email;
	}
	//Επιστρέφει τη λίστα με τους φίλους του χρήστη
	public Collection<User> getFriends() {
		return this.friends;
	}
	//Επιστρέφει τη λίστα με τα group που είναι εγγεγραμένος ο χρήστης
	public Collection<Group> getEnrolledGroups() {
		return this.enrolledGroups;
	}
	
	public static Boolean existsByName(String aName) {
		for (User user: User.getAllInstances()) {
			if(user.getName().equals(aName)) {
				return true;
			}
		}
		return false;
	}
	
	public static Boolean exists(User aUser) {
		if(User.getAllInstances().contains(aUser)) {
			return true;
		}
		return false;
	}
	
	//Προσθέτει ένα group στη λίστα των group του χρήστη
	public void addToGroup(Group group) {
		this.getEnrolledGroups().add(group);
	}

	//Ελέγχει αν ο τρέχον χρήστης είναι φίλος με τον χρήστη που μπαίνει ως παράμετρος
	public boolean isFriendWith(User user) {
		if(this.getFriends().contains(user))
			return true;
		return false;
	}
	//Προσθέτη τον χρήστη που μπαίνει ως παράμετρος, στη λίστα των φίλων του τρέχοντος χρήστη .
	public void addFriend(User user) {
		
		if(user.equals(this)) {
			JOptionPane.showMessageDialog(null,"A user can't be added on his own friends list.");
		}
		else if(this.isFriendWith(user)) {
			JOptionPane.showMessageDialog(null,user.getName() + " is already friend of " + this.getName());
		}
		else {
			this.getFriends().add(user);
			user.getFriends().add(this);
			JOptionPane.showMessageDialog(null,this.getName() + " and " + user.getName() + " are now friends!");
		}
		
	}
	//Επιστρέφει τους κοινούς φίλους του χρήστου και τους χρήστη που μπαίνει ως παράμετρος
	public Collection<User> getCommonFriends(User user) {
		Collection<User> commonFriends = new TreeSet<>(this.getFriends());
		commonFriends.retainAll(user.getFriends());
		return commonFriends;
	}
	
	//Εκτυπώνει τους κοινούς φίλους του χρήστου και τους χρήστη που μπαίνει ως παράμετρος
	public void printCommonFriends(User user) {
		System.out.println("*******************************");
		System.out.println("Common friends of " + this.getName() + " and " + user.getName());
		System.out.println("*******************************");
		for(User u: this.getCommonFriends(user)) {
			System.out.println(u.getName());
		}
		System.out.println("-----------------------------");
	}
	
	//Εκτυπώνει τους φίλους του χρήστη
	public void printFriends() {
		System.out.println("*******************************");
		System.out.println("Friends of " + this.getName());
		System.out.println("*******************************");
		for(User friend: this.getFriends()) {
			System.out.println(friend.getName());
		}
		System.out.println("-----------------------------");
	}
	
	//Εκτυπώνει τα groups στα οποία είναι εγγεγραμένος ο χρήστης
	public void printGroups() {
		System.out.println("*******************************");
		System.out.println("Groups that " + this.getName() + " has been enrolled");
		System.out.println("*******************************");
		for(Group group: this.getEnrolledGroups()) {
			System.out.println(group.getName());
		}
		System.out.println("-----------------------------");
	}
	
	//Επιστρέφει τους φίλους που πρέπει να ελεγχθούν και τους φίλους αυτών
	public Collection<User> getInfectedFriends() {
		Collection<User> infectedFriends = new TreeSet<>(this.getFriends());
		for(User friend: this.getFriends()) {
			infectedFriends.addAll(friend.getFriends());
		}
		infectedFriends.remove(this);
		return infectedFriends;
	}
	
	//Εκτυπώνει τους φίλους που πρέπει να ελεγχθούν και τους φίλους αυτών
	public void printInfectedFriends() {
		System.out.println("*******************************");
		System.out.println(this.getName() + " has been infected. The following users have to be tested");
		System.out.println("*******************************");
		for (User friend: this.getInfectedFriends())
			System.out.println(friend.getName());
		System.out.println("-----------------------------");
	}
	
	public void addPost(Post aPost) {
		posts.add(aPost);
	}
	
	public Collection<Post> getPosts(){
		return posts;
	}
	
	public void deletePosts() {
		posts.clear();
	}
	
	public Collection<Post> getFriendsPosts() {
		Collection<Post> friendsPosts = new TreeSet<>(getPosts());
		for (User friend: getFriends()) {
			friendsPosts.addAll(friend.getPosts());
		}
		return friendsPosts;
	}
	
	public Collection<User> getSuggestedFriends(){
		Collection<User> suggestedFriends = new TreeSet<>();
		Collection<User> friendsOfFriends = new TreeSet<>();
		for (User friend: getFriends()) {
			friendsOfFriends.addAll(friend.getFriends());
			friendsOfFriends.removeAll(this.getFriends());
			suggestedFriends.addAll(friendsOfFriends);
		}
		suggestedFriends.remove(this);
		return suggestedFriends;
	}
	
}


	