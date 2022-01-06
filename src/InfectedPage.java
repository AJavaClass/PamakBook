import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class InfectedPage extends JFrame{
	
	private JButton backToLoginButton;
	private JTextArea infectedList;
	private JPanel panel;
	private LoginPage previousPage;
	private SpringLayout layout = new SpringLayout();

	public InfectedPage(User aUser, LoginPage aPage) {
		
		previousPage = aPage;
		
		panel  = new JPanel();
		infectedList = new JTextArea("***********************************************************\n"
				+ aUser.getName() + " is infected, the following users have to be tested\n"
				+ "***********************************************************\n"
				);
		for(User user: aUser.getInfectedFriends()) {
			infectedList.append(user.getName() + "\n");
		}
		backToLoginButton = new JButton("Back to Login Screen");
		backToLoginButton.addActionListener(new PreviousPageButtonListener());

		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, infectedList, 0, SpringLayout.HORIZONTAL_CENTER, panel);
		layout.putConstraint(SpringLayout.NORTH, backToLoginButton, 5, SpringLayout.SOUTH, infectedList);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, backToLoginButton, 0, SpringLayout.HORIZONTAL_CENTER, infectedList);
		panel.setLayout(layout);
		panel.add(infectedList);
		panel.add(backToLoginButton);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(350, 300);
		this.setMinimumSize(new Dimension(350, 300));
		this.setTitle("Πιθανή Μετάδοση Ιού");
		this.setLocationRelativeTo(null);
		
	}
	
	public class PreviousPageButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			previousPage.setVisible(true);
			InfectedPage.this.dispose();
		}
	}
	
}
