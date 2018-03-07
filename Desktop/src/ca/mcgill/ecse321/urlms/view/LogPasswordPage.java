package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Staff;

import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogPasswordPage extends JFrame {
	private JPasswordField passwordField;
	private Staff aStaff;

	private String error = "";

	private JLabel lblInstruction;
	private JLabel lblName;
	private JLabel lblPassword;
	private JLabel lblError;
	private JButton btnLogIn;

	public LogPasswordPage(Staff s) {
		this.aStaff = s;

		initComponents();
		refreshData();
	}

	public void initComponents() {
		this.setTitle("User Log in");
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
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		lblInstruction = new JLabel("Please enter your password");
		lblInstruction.setBounds(42, 51, 300, 16);

		lblName = new JLabel("New label");
		lblName.setText(aStaff.getFirstName() + " " + aStaff.getLastName());
		lblName.setBounds(42, 73, 312, 16);
		lblName.setForeground(Color.RED);

		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(42, 121, 63, 16);

		passwordField = new JPasswordField();
		passwordField.setBounds(117, 116, 147, 26);
		panel.setLayout(null);
		panel.add(lblPassword);
		panel.add(passwordField);
		panel.add(lblName);
		panel.add(lblInstruction);

		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.currentStaff = aStaff;
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});
		btnLogIn.setBounds(259, 152, 117, 29);
		panel.add(btnLogIn);

		lblError = new JLabel("error lbl");
		lblError.setForeground(Color.RED);
		lblError.setBounds(42, 23, 288, 16);
		panel.add(lblError);
		getContentPane().setLayout(groupLayout);

	}

	public void refreshData() {
		lblError.setText(error);

	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
