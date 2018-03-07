package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateLabRoot extends JFrame{
	
	private JTextField rootPassword;
	private JLabel lblCreating;
	private JLabel lblRemember;
	private JLabel lblRootPassword;
	private JButton btnSetPassword;
	private JLabel lblError;
	
	private String error;
	
	
	public CreateLabRoot() {
		initComponents();
		refreshData();
	}
	
	private void initComponents() {
		this.setTitle("Create a new lab");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setSize(450, 350);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		JPanel mainPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		lblCreating = new JLabel("You are creating a new lab. Please choose a password admin.");
		
		lblRemember = new JLabel("Make sure you remember the password.");
		
		lblRootPassword = new JLabel("Root password:");
		
		rootPassword = new JTextField();
		rootPassword.setColumns(10);
		
		btnSetPassword = new JButton("Set Password");
		btnSetPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rootPassword.getText().isEmpty()) {
					error = "the password cannot be empty";
					refreshData();
				}
				else {
					ConfirmRootPage confirm = new ConfirmRootPage(rootPassword.getText());
					confirm.setVisible(true);
				}
				
			}
		});
		
		lblError = new JLabel("This password cannot be empty");
		lblError.setForeground(Color.RED);
		GroupLayout gl_mainPanel = new GroupLayout(mainPanel);
		gl_mainPanel.setHorizontalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addGap(97)
							.addComponent(lblRemember))
						.addGroup(gl_mainPanel.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_mainPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_mainPanel.createSequentialGroup()
									.addComponent(lblRootPassword)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rootPassword, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblCreating)
								.addComponent(lblError))))
					.addContainerGap(34, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_mainPanel.createSequentialGroup()
					.addContainerGap(283, Short.MAX_VALUE)
					.addComponent(btnSetPassword)
					.addGap(49))
		);
		gl_mainPanel.setVerticalGroup(
			gl_mainPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainPanel.createSequentialGroup()
					.addGap(23)
					.addComponent(lblError)
					.addGap(26)
					.addComponent(lblCreating)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblRemember)
					.addGap(55)
					.addGroup(gl_mainPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRootPassword)
						.addComponent(rootPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
					.addComponent(btnSetPassword)
					.addGap(43))
		);
		mainPanel.setLayout(gl_mainPanel);
		getContentPane().setLayout(groupLayout);

		
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	private void refreshData() {
		lblError.setText(error);
	}
}
