import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class UserPage extends JFrame {
	
	private JButton backToLoginButton;
	private JButton postButton;
	private JButton addFriendButton;
	private JButton joinGroupButton;
	private JTextField username;
	private JTextField email;
	private JTextField addFriendName;
	private JTextArea postFieldTextArea;
	private JTextArea recentPostsTextArea;
	private JTextArea suggestedFriendsList;
	private JPanel panel;
	private JPanel postPanel;
	private JLabel recentPostsLabel;
	private JLabel suggestedFriendsLabel;
	private JScrollPane postField;
	private JScrollPane recentPostsField;
	private JScrollPane groupListScrollPane;
	private JList<Group> groupList;
	private DefaultListModel<Group> groupListModel;
	private SpringLayout layout;
	private LoginPage previousPage;
	private User user;

	public UserPage(User aUser, LoginPage aPage) {
		
		previousPage = aPage;
		user = aUser;
		
		Container contentPane = getContentPane();
		
		layout = new SpringLayout();
		
		panel  = new JPanel();
		postPanel = new JPanel();
		username = new JTextField(user.getName(),10);
		email = new JTextField(user.getEmail(),15);
		addFriendName = new JTextField("Type User" ,10);
		addFriendName.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	addFriendName.setText("");
            }
        });
		addFriendButton = new JButton("Add Friend");
		addFriendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(User.existsByName(addFriendName.getText())) {
					for (User currentUser: User.getAllInstances()) {
						if(currentUser.getName().equals(addFriendName.getText())) {
							user.addFriend(currentUser);
						}
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"User " + addFriendName.getText() + " does not exist.");
				}
			}
		});
		backToLoginButton = new JButton("Back to Login Screen");
		backToLoginButton.addActionListener(new PreviousPageButtonListener());
		postFieldTextArea = new JTextArea(8, 20);
		postField = new JScrollPane(postFieldTextArea);
		postButton = new JButton("POST");
		postButton.addActionListener(new PostButtonListener());
		recentPostsLabel = new JLabel("recent Posts by friends ");
		recentPostsTextArea = new JTextArea(8,20);
		recentPostsTextArea.setLineWrap(true);
		recentPostsTextArea.setWrapStyleWord(true);
		for(Post post: user.getFriendsPosts()) {
			recentPostsTextArea.insert(post.getPublisher() + ", " + post.getTimestampFormated() + ", " + post.getText() + "\n",0);
		}
		recentPostsField = new JScrollPane(recentPostsTextArea);
		suggestedFriendsLabel = new JLabel("Suggested Friends");
		suggestedFriendsList = new JTextArea();
		suggestedFriendsList.setFont(new Font(suggestedFriendsList.getFont().getName(), Font.BOLD, suggestedFriendsList.getFont().getSize()));
		for(User user: user.getSuggestedFriends()) {
			suggestedFriendsList.append(user.getName() + "\n");
		}
		groupList = new JList<Group>();
		groupListScrollPane = new JScrollPane(groupList);
		groupListScrollPane.setPreferredSize(new Dimension(125, 100));
		groupListModel = new DefaultListModel<Group>();
		for(Group group: Group.getAllInstances()) {
			groupListModel.addElement(group);
		}
		groupList.setModel(groupListModel);
		joinGroupButton = new JButton("Join Group");
		joinGroupButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Group group = groupList.getSelectedValue();
				group.addMember(user);
			}
		});
		
		//Ρύθμιση θεσης συστατικών
		layout.putConstraint(SpringLayout.SOUTH, postField, 60, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, postField, 20, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, postButton, -40,SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, postButton, 10, SpringLayout.EAST, postField);
		layout.putConstraint(SpringLayout.NORTH, recentPostsLabel, 80, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, recentPostsLabel, 20, SpringLayout.VERTICAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, recentPostsField, 5, SpringLayout.SOUTH, postField);
		layout.putConstraint(SpringLayout.WEST, recentPostsField, 5, SpringLayout.EAST, recentPostsLabel);
		layout.putConstraint(SpringLayout.NORTH, suggestedFriendsLabel, 5, SpringLayout.SOUTH, recentPostsField);
		layout.putConstraint(SpringLayout.EAST, suggestedFriendsLabel, 10, SpringLayout.HORIZONTAL_CENTER, this.getContentPane());
		layout.putConstraint(SpringLayout.WEST, suggestedFriendsList, 10, SpringLayout.EAST, suggestedFriendsLabel);
		layout.putConstraint(SpringLayout.NORTH, suggestedFriendsList, 5, SpringLayout.SOUTH, recentPostsField);
		layout.putConstraint(SpringLayout.WEST, addFriendName, 21, SpringLayout.WEST, this.getContentPane());
		layout.putConstraint(SpringLayout.NORTH, addFriendName, 0, SpringLayout.SOUTH, username);
		layout.putConstraint(SpringLayout.WEST, addFriendButton, 5, SpringLayout.EAST, addFriendName);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, addFriendButton, 0, SpringLayout.VERTICAL_CENTER, addFriendName);
		layout.putConstraint(SpringLayout.WEST, groupListScrollPane, 10, SpringLayout.EAST, addFriendButton);
		layout.putConstraint(SpringLayout.WEST, joinGroupButton, 5, SpringLayout.EAST, groupListScrollPane);
		layout.putConstraint(SpringLayout.NORTH, groupListScrollPane, 0, SpringLayout.NORTH, addFriendButton);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, joinGroupButton, 0, SpringLayout.VERTICAL_CENTER, addFriendButton);
		
		//Προσθήκη συστατικών σε panel
		panel.add(username);
		panel.add(email);
		panel.add(backToLoginButton);
		
		
		
		postPanel.setLayout(layout);
		postPanel.add(addFriendName);
		postPanel.add(addFriendButton);
		postPanel.add(groupListScrollPane);
		postPanel.add(joinGroupButton);
		postPanel.add(postField);
		postPanel.add(postButton);
		postPanel.add(recentPostsLabel);
		postPanel.add(recentPostsField);
		postPanel.add(suggestedFriendsLabel);
		postPanel.add(suggestedFriendsList);
		
		contentPane.add(panel, BorderLayout.NORTH);
		contentPane.add(postPanel);
		this.setVisible(true);
		this.setSize(500, 600);
		this.setResizable(false);
		this.setTitle("Σελίδα Χρήστη");
		this.setLocationRelativeTo(null);
	}
	
	public class PreviousPageButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			previousPage.setVisible(true);
			UserPage.this.dispose();
		}
	}
	
	public class PostButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			if(!((JTextArea)(postField.getViewport().getView())).getText().isEmpty()) {
				Post newPost = new Post(((JTextArea)(postField.getViewport().getView())).getText(), user);
				recentPostsTextArea.insert(newPost.getPublisher() + ", " + newPost.getTimestampFormated() + ", " + newPost.getText() + "\n",0);
			}
			postFieldTextArea.setText("");
		} 
	}
	
}
