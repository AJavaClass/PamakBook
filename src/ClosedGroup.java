import javax.swing.JOptionPane;

public class ClosedGroup extends Group{
	
	public ClosedGroup(String aName, String aDescription) {
		super(aName,aDescription);
	}
	
	//Προσθέτει έναν χρήστη στο group αν και μόνο αν ο χρήστης είναι φίλος με κάποιο ήδη υπάρχον μέλος του group
	public void addMember(User user) {
		boolean added = false;
		if(getMembers().isEmpty()) {
			super.addMember(user);
			added = true;
		}
		else {
			for(User member: getMembers()) {
				if(member.isFriendWith(user)) {
					super.addMember(user);
					added = true;
					break;
				}
			}
		}
		if(!added) 
			JOptionPane.showMessageDialog(null,"FAILED: " + user.getName() + " cannot be enrolled in group " + getName());
	}
}
