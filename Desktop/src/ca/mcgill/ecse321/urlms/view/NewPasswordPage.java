package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Staff;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewPasswordPage extends JFrame{
	
	private JLabel lblTitle;
	private JLabel lblName;
	private JLabel lblError;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	
	private JButton btnCreatePassword;
	
	private String error;
	
	private Staff aStaff;

	public NewPasswordPage(Staff s) {
		initComponents(s.getFirstName(), s.getLastName(), s.getID());
		aStaff = s;
		refreshData();
	}
	
	public void initComponents(String firstName, String lastName, int iD) {
		this.setTitle("Setup Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
		this.setSize(400, 250);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
		);
		
		lblTitle = new JLabel("This is your first log in, please create a password");
		lblTitle.setBounds(43, 46, 309, 16);
		
		lblName = new JLabel(firstName + " " + lastName + " - " + Integer.toString(iD));
		lblName.setBounds(72, 74, 253, 16);
		lblName.setForeground(Color.RED);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblPassword = new JLabel("password:");
		lblPassword.setBounds(60, 106, 64, 16);
		
		lblConfirmPassword = new JLabel("confirm password:");
		lblConfirmPassword.setBounds(60, 144, 117, 16);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(195, 101, 140, 26);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(195, 139, 140, 26);
		
		lblError = new JLabel("New label");
		lblError.setForeground(Color.RED);
		lblError.setBounds(63, 18, 240, 16);
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.setLayout(null);
		panel.add(lblTitle);
		panel.add(lblError);
		panel.add(lblConfirmPassword);
		panel.add(lblPassword);
		panel.add(passwordField);
		panel.add(passwordField_1);
		panel.add(lblName);
		
		btnCreatePassword = new JButton("Create password");
		btnCreatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error = "";
				System.out.println(passwordField.getPassword());
				System.out.println(passwordField_1.getPassword());
				if (!(String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField_1.getPassword())))) {
					error = "The passwords are not the same";
				}
				if (passwordField.getPassword().length == 0 || passwordField_1.getPassword().length == 0) {
					error = "The password cannot be empty";
				}
				if (error.length() == 0) {
					URLMSApplication.mc.setPassword(aStaff, String.valueOf(passwordField.getPassword()));
					URLMSApplication.mc.saveLab();
					URLMSApplication.currentStaff = aStaff;
					URLMSApplication.w = new WelcomeAdmin();
					URLMSApplication.w.setVisible(true);
					error = "The password has been set";
					passwordField.setText("");
					passwordField_1.setText("");
					close();
				}
				refreshData();
			}
		});
		btnCreatePassword.setBounds(222, 180, 154, 29);
		panel.add(btnCreatePassword);
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
