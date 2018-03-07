package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RootLogIn extends JFrame {

	private JLabel lblPleaseInputThe;
	private JLabel lblError;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JButton btnLogInAs;

	private String error;
	private JButton btnBack;

	public RootLogIn() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		this.setTitle("Root Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
		this.setSize(400, 220);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		lblPleaseInputThe = new JLabel("Please input the admin password");
		lblPleaseInputThe.setBounds(45, 28, 207, 16);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(45, 101, 63, 16);

		lblError = new JLabel();
		lblError.setBounds(55, 62, 197, 16);
		lblError.setForeground(Color.RED);

		passwordField = new JPasswordField();
		passwordField.setBounds(126, 96, 142, 26);

		btnLogInAs = new JButton("Log In as Root");
		btnLogInAs.setBounds(238, 148, 134, 29);
		btnLogInAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(String.valueOf(passwordField.getPassword()));
				// System.out.println(URLMSApplication.getURLMS().getSingleLab().getRootPassword());
				if (String.valueOf(passwordField.getPassword())
						.equals(URLMSApplication.getURLMS().getSingleLab().getRootPassword())) {
					URLMSApplication.w = new WelcomeAdmin();
					URLMSApplication.w.setVisible(true);
					close();
				}
				else {
					error = "The password is wrong";
				}
				refreshData();
			}
		});
		panel.setLayout(null);
		panel.add(lblPleaseInputThe);
		panel.add(lblPassword);
		panel.add(lblError);
		panel.add(passwordField);
		panel.add(btnLogInAs);

		btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.lip = new LogInPage();
				URLMSApplication.lip.setVisible(true);
				close();
			}
		});
		btnBack.setBounds(34, 148, 117, 29);
		panel.add(btnBack);
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
