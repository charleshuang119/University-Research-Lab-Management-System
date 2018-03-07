package ca.mcgill.ecse321.urlms.view;

import javax.swing.JFrame;
import javax.swing.JLabel;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.Staff;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddExpensePage extends JFrame {
	static ManagerController cont = URLMSApplication.mc;

	private Lab lab;
	private String error = "";
	private JLabel lblError;

	private JPanel mainPanel;
	private JComboBox staffComboBox;
	private JLabel lblAmount;
	private JLabel lblDescription;
	private JTextField descriptionTextField;
	private JTextField amountTextField;
	private JButton btnAddExpense;

	private JRadioButton rdbtnTravel;
	private JRadioButton rdbtnSalary;
	private JRadioButton rdbtnResource;
	private JRadioButton rdbtnOther;
	private final ButtonGroup typeBtnGroup = new ButtonGroup();

	public AddExpensePage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		this.setTitle("Add Expense");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 325);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);

		try {
			this.lab = cont.loadLab();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainPanel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(mainPanel, GroupLayout.PREFERRED_SIZE, 303, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(25, Short.MAX_VALUE)));

		staffComboBox = new JComboBox();
		staffComboBox.setBounds(265, 56, 204, 27);
		String[] staffMembers = new String[lab.getStaff().size()];
		addStaff(staffMembers, lab.getStaff());
		staffComboBox.setModel(new DefaultComboBoxModel(staffMembers));

		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(31, 116, 77, 16);

		descriptionTextField = new JTextField();
		descriptionTextField.setBounds(126, 111, 221, 26);
		descriptionTextField.setColumns(10);

		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(31, 154, 54, 16);

		amountTextField = new JTextField();
		amountTextField.setBounds(126, 149, 221, 26);
		amountTextField.setColumns(10);

		btnAddExpense = new JButton("Add Expense");
		btnAddExpense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addExpenseBtn(e);
			}
		});
		btnAddExpense.setBounds(344, 248, 125, 29);

		lblError = new JLabel("");
		lblError.setBounds(31, 22, 388, 16);
		lblError.setForeground(Color.RED);

		rdbtnTravel = new JRadioButton("Travel");
		rdbtnTravel.setActionCommand("Travel");
		typeBtnGroup.add(rdbtnTravel);
		rdbtnTravel.setBounds(47, 193, 70, 23);

		rdbtnSalary = new JRadioButton("Salary");
		rdbtnSalary.setActionCommand("Salary");
		typeBtnGroup.add(rdbtnSalary);
		rdbtnSalary.setBounds(135, 193, 69, 23);

		rdbtnResource = new JRadioButton("Resource");
		rdbtnResource.setActionCommand("Resource");
		typeBtnGroup.add(rdbtnResource);
		rdbtnResource.setBounds(222, 193, 89, 23);

		rdbtnOther = new JRadioButton("Other");
		rdbtnOther.setActionCommand("Other");
		typeBtnGroup.add(rdbtnOther);
		rdbtnOther.setBounds(329, 193, 67, 23);

		mainPanel.setLayout(null);
		mainPanel.add(lblDescription);
		mainPanel.add(descriptionTextField);
		mainPanel.add(lblError);
		mainPanel.add(rdbtnTravel);
		mainPanel.add(rdbtnSalary);
		mainPanel.add(rdbtnResource);
		mainPanel.add(rdbtnOther);
		mainPanel.add(lblAmount);
		mainPanel.add(amountTextField);
		mainPanel.add(btnAddExpense);
		mainPanel.add(staffComboBox);

		JButton btnGoBackTo = new JButton("Go back to main page");
		btnGoBackTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});
		btnGoBackTo.setBounds(28, 248, 171, 29);
		mainPanel.add(btnGoBackTo);
		getContentPane().setLayout(groupLayout);

	}

	protected void addExpenseBtn(ActionEvent e) {
		ButtonModel chosenType = null;
		float value = -1;
		String description = "";
		String amount = "";
		error = "";

		try {
			description = descriptionTextField.getText();
			amount = amountTextField.getText();
		} catch (Exception e1) {
			error = "One of the fields is missing";
		}

		try {
			chosenType = typeBtnGroup.getSelection();
		} catch (NullPointerException e1) {
			error = "Please select an expense type.";
		}

		try {
			value = Float.parseFloat(amount);
			if (value < 0) {
				error = "The value needs to be positive";
			}
		} catch (NumberFormatException e1) {
			error = "The value needs to be a number";
		}

		if (description.length() == 0) {
			error = "The expense needs a description";
		}
		if (chosenType == null) {
			error = "Please select a type of expense";
		}
		if (staffComboBox.getSelectedIndex() == -1) {
			error = "the lab does not have staff members";
		}
		Staff temp = lab.getStaff(staffComboBox.getSelectedIndex());
		if (URLMSApplication.currentStaff != null) {
			if (URLMSApplication.currentStaff.getRole() instanceof Assistant) {
				temp = URLMSApplication.currentStaff;
			}
			if (URLMSApplication.currentStaff.getRole() instanceof Associate) {
				if (temp.getRole() instanceof Director) {
					error = "You are not allowed to add an expense to a director";
				}
			}
		}
		if (error.length() == 0) {
			try {
				
				
				cont.addExpense(temp, description, value,
						chosenType.getActionCommand());
				cont.saveLab();
				error = "The expense has been added";
				descriptionTextField.setText("");
				amountTextField.setText("");
				typeBtnGroup.clearSelection();
			} catch (InvalidInputException e1) {
				error = e1.getMessage();
			}
		}
		refreshData();
	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}

	private void addStaff(String[] newList, List<Staff> staffs) {
		int i = 0;
		for (Staff aStaff : staffs) {
			newList[i] = aStaff.getFirstName() + " " + aStaff.getLastName();
			i++;
		}
	}

	private void refreshData() {
		if (URLMSApplication.currentStaff != null) {
			if (URLMSApplication.currentStaff.getRole() instanceof Assistant) {
				staffComboBox.setVisible(false);
			}
		}
		lblError.setText(error);

	}
}
