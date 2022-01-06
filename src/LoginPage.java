import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginPage extends JFrame {

	private JButton loginButton;
	private JButton infectedButton;
	private JButton createUserButton;
	private JButton saveButton;
	private JTextField username;
	private JTextField email;
	private JPanel panel;
	private SpringLayout layout;
	
	public LoginPage() {
		
		String defaultUsername = "Enter Username";
		String defaultEmail = "Enter Email";
		
		panel  = new JPanel();
		username = new JTextField(defaultUsername,9);
		email = new JTextField(defaultEmail, 10);
		loginButton = new JButton("Enter User Page");
		infectedButton = new JButton("Show Potential Infections");
		createUserButton = new JButton("New User");
		saveButton = new JButton("Save PamakBook");
		layout = new SpringLayout();
		
		createUserButton.addActionListener(new createUserButtonListener());
		loginButton.addActionListener(new LoginButtonListener());
		infectedButton.addActionListener(new PotentialInfectionsButtonListener());
		saveButton.addActionListener(new saveButtonListener());
		username.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	if(username.getText().equals(defaultUsername)) {
            		username.setText("");
            	} 
            }
        });
		
		email.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	if(email.getText().equals(defaultEmail)) {
            		email.setText("");
            	}
            }
        });
		
		layout.putConstraint(SpringLayout.NORTH, createUserButton, 30, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, createUserButton, 35, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, loginButton, 35, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, infectedButton, 5, SpringLayout.EAST, loginButton);
		layout.putConstraint(SpringLayout.WEST, username, 5, SpringLayout.EAST, createUserButton);
		layout.putConstraint(SpringLayout.WEST, email, 5, SpringLayout.EAST, username);
		layout.putConstraint(SpringLayout.NORTH, loginButton, 5, SpringLayout.SOUTH, createUserButton);
		layout.putConstraint(SpringLayout.NORTH, infectedButton, 5, SpringLayout.SOUTH, createUserButton);
		layout.putConstraint(SpringLayout.SOUTH, username, 10, SpringLayout.VERTICAL_CENTER, createUserButton);
		layout.putConstraint(SpringLayout.SOUTH, email, 10, SpringLayout.VERTICAL_CENTER, createUserButton);
		layout.putConstraint(SpringLayout.NORTH, saveButton, 5, SpringLayout.SOUTH, loginButton);
		layout.putConstraint(SpringLayout.WEST, saveButton, 125, SpringLayout.WEST, this.getContentPane());
		
		
		panel.setLayout(layout);
		panel.add(createUserButton);
		panel.add(username);
		panel.add(email);
		panel.add(loginButton);
		panel.add(infectedButton);
		panel.add(saveButton);
		
		this.setContentPane(panel);
		this.setVisible(true);
		this.setSize(400, 200);
		this.setResizable(false);
		this.setTitle("Κεντρική Σελίδα");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	
	}
	
	public class LoginButtonListener implements ActionListener{
		
		User selectedUser = null;
		
		public void actionPerformed(ActionEvent e) {
			for (User user: User.getAllInstances()) {
				if(user.getName().equals(username.getText())) {
					selectedUser = user;
					new UserPage(selectedUser, LoginPage.this);
					LoginPage.this.setVisible(false);
					break;
				}
			}
			
			if(username.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Type a valid username.");
			}
			
			else if(selectedUser == null) {
				JOptionPane.showMessageDialog(null,"User " + username.getText() + " Not Found");
			}
			
		}
	}
	
	public class PotentialInfectionsButtonListener implements ActionListener{
		
		User selectedUser = null;
		
		public void actionPerformed(ActionEvent e) {
			for (User user: User.getAllInstances()) {
				if(user.getName().equals(username.getText())) {
					selectedUser = user;
					new InfectedPage(selectedUser, LoginPage.this);
					LoginPage.this.setVisible(false);
					break;
				}
			}
			
			if(username.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Type a Valid Username.");
			}
			
			else if(selectedUser == null) {
				JOptionPane.showMessageDialog(null,"User " + username.getText() + " Not Found");
			}
		}
		
	}
	
	public class createUserButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			String aUsername = username.getText();
			String anEmail = email.getText();
			if(!aUsername.isEmpty() && !anEmail.isEmpty()) {
				if(!User.existsByName(aUsername)) {
					User user = new User(aUsername, anEmail);
					if (user.getName() != null) {
						JOptionPane.showMessageDialog(null,"User " + username.getText() + " Has Been Successfully Created!");
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"User " + username.getText() + " Already Exists");
				}
			}
			else {
				JOptionPane.showMessageDialog(null,"Type Valid Data");
			}
			
			
		}
	}
	
	public class saveButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			try {
				File file = new File("PamakBookData.ser");
				FileOutputStream fOutputStream = new FileOutputStream(file);
				ObjectOutputStream outputStream = new ObjectOutputStream(fOutputStream);
				outputStream.writeObject(User.getAllInstances());
				outputStream.close();
				fOutputStream.close();
			}
			catch(IOException exc) {
				exc.printStackTrace();
			}
		}
	}
	
}
