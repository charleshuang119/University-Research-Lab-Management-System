package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Staff;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogInPage extends JFrame {

	private JTextField idTextField;
	private JLabel lblInstruction;
	private JLabel lblIdNumber;
	private JButton btnContinue;
	private JLabel lblError;

	private String error;
	private JButton btnAdminLogin;

	public LogInPage() {
		initComponents();
		refreshData();
	}

	public void initComponents() {
		this.setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
		this.setSize(400, 220);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		JPanel mainPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainPanel,
				GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainPanel,
				GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE));

		lblInstruction = new JLabel("Input your ID number to access the system");
		lblInstruction.setBounds(63, 34, 271, 16);

		lblIdNumber = new JLabel("ID number:");
		lblIdNumber.setBounds(82, 105, 70, 16);

		idTextField = new JTextField();
		idTextField.setBounds(164, 100, 130, 26);
		idTextField.setColumns(10);

		btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnContinueActionPerformed(e);
			}
		});
		btnContinue.setBounds(268, 144, 101, 29);

		lblError = new JLabel("This ID number does not exist");
		lblError.setBounds(73, 62, 255, 16);
		lblError.setForeground(Color.RED);
		mainPanel.setLayout(null);
		mainPanel.add(btnContinue);
		mainPanel.add(lblError);
		mainPanel.add(lblInstruction);
		mainPanel.add(lblIdNumber);
		mainPanel.add(idTextField);
		
		btnAdminLogin = new JButton("Admin login");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.rl = new RootLogIn();
				URLMSApplication.rl.setVisible(true);
				close();
			}
		});
		btnAdminLogin.setBounds(51, 144, 117, 29);
		mainPanel.add(btnAdminLogin);
		getContentPane().setLayout(groupLayout);

	}

	protected void btnContinueActionPerformed(ActionEvent e) {
		int value = 0;;
		Staff staff = null;
		error = "";
		if (idTextField.getText().isEmpty()) {
			error = "There needs to be an ID";
		}
		try {
			value = Integer.parseInt(idTextField.getText());
		} catch (NumberFormatException e1) {
			error = "The ID needs to be a number";
		}
		if (error.length() == 0) {
			for (Staff s: URLMSApplication.getURLMS().getSingleLab().getStaff()) {
				if (value == s.getID()) {
					staff = s;
					break;
				}
			}
		}
		
		if (staff == null) {
			error = "This staff member does not exist";
			refreshData();
		}
		else {
//			System.out.println(staff.getPassword());
			if (staff.getPassword().length() == 0) {
				URLMSApplication.npp = new NewPasswordPage(staff);
				URLMSApplication.npp.setVisible(true);
				close();
				
			}
			else {
				URLMSApplication.lpp = new LogPasswordPage(staff);
				URLMSApplication.lpp.setVisible(true);
				close();
			}
		}
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void refreshData() {
		lblError.setText(error);
	}
}
