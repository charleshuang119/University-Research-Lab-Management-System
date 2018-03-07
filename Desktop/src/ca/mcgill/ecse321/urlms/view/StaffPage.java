package ca.mcgill.ecse321.urlms.view;

import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.Role;
import ca.mcgill.ecse321.urlms.model.Staff;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.border.MatteBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;

public class StaffPage extends JFrame {
	static ManagerController cont = URLMSApplication.mc;
	// private JScrollPane scroll = new JScrollPane(staffPane);

	private JSplitPane splitPane;

	private JPanel optionsPanel;
	public String error = "";
	private JLabel lblError;

	// add staff panel
	private JPanel addStaffPanel;
	private JLabel lblAddStaff;
	private JLabel lblFirstName;
	private JTextField txtFirstName;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JRadioButton rdbtnDirector;
	private JRadioButton rdbtnAssociate;
	private JRadioButton rdbtnAssistant;
	private final ButtonGroup staffBtnGroup = new ButtonGroup();
	private JButton btnAddStaff;

	// remove staff
	private JPanel removeStaffPanel;
	private JLabel lblToRemoveA;
	private JButton btnRemoveStaff;

	// overview
	private JTable overviewTable;
	private JScrollPane overviewScrollPane;
	private DefaultTableModel staffListDtm;
	private String staffListColumnNames[] = { "First Name", "Last Name", "Role", "ID" };
	private JButton btnBack;
	private JPanel panel;
	private JLabel lblToChangeThe;
	private JRadioButton rdbtnAssociate_1;
	private JRadioButton rdbtnAssistant_1;
	private final ButtonGroup editStaffBtnGroup = new ButtonGroup();

	private JButton btnEditStaff;

	public StaffPage() {
		initComponents();
		refreshData();

	}

	private void initComponents() {
		this.setTitle("Staff Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setResizable(false);
		this.setSize(900, 450);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		optionsPanel = new JPanel();
		optionsPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		addStaffPanel = new JPanel();
		addStaffPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		splitPane = new JSplitPane();
		splitPane.setSize(900, 350);
		splitPane.setDividerSize(0);
		splitPane.setDividerLocation(410);
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setRightComponent(optionsPanel);

		txtLastName = new JTextField();
		txtLastName.setText("");
		txtLastName.setColumns(10);

		lblAddStaff = new JLabel("Add a new Staff");

		txtFirstName = new JTextField();
		txtFirstName.setText("");
		txtFirstName.setColumns(10);

		btnAddStaff = new JButton("add Staff");

		rdbtnDirector = new JRadioButton("Director");
		rdbtnDirector.setActionCommand("Director");
		staffBtnGroup.add(rdbtnDirector);

		rdbtnAssociate = new JRadioButton("Associate");
		rdbtnAssociate.setActionCommand("Associate");
		staffBtnGroup.add(rdbtnAssociate);

		rdbtnAssistant = new JRadioButton("Assistant");
		rdbtnAssistant.setActionCommand("Assistant");
		staffBtnGroup.add(rdbtnAssistant);

		// ----------------

		overviewTable = new JTable();
		overviewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		overviewScrollPane = new JScrollPane(overviewTable);

		splitPane.setLeftComponent(overviewScrollPane);
		Dimension d = overviewTable.getPreferredSize();
		overviewScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// -------------
		lblFirstName = new JLabel("First name:");
		lblLastName = new JLabel("Last name:");

		GroupLayout gl_addStaffPanel = new GroupLayout(addStaffPanel);
		gl_addStaffPanel.setHorizontalGroup(gl_addStaffPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(21).addComponent(lblAddStaff))
				.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(71).addComponent(lblFirstName).addGap(25)
						.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(71).addComponent(lblLastName).addGap(27)
						.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(45).addComponent(btnAddStaff))
				.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(47).addComponent(rdbtnDirector).addGap(27)
						.addComponent(rdbtnAssociate).addGap(12).addComponent(rdbtnAssistant)));
		gl_addStaffPanel.setVerticalGroup(gl_addStaffPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(11).addComponent(lblAddStaff).addGap(14)
						.addGroup(gl_addStaffPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(5).addComponent(lblFirstName))
								.addComponent(txtFirstName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(12)
						.addGroup(gl_addStaffPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_addStaffPanel.createSequentialGroup().addGap(5).addComponent(lblLastName))
								.addComponent(txtLastName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAddStaff))
						.addGap(16)
						.addGroup(gl_addStaffPanel.createParallelGroup(Alignment.LEADING).addComponent(rdbtnDirector)
								.addComponent(rdbtnAssociate).addComponent(rdbtnAssistant))));
		addStaffPanel.setLayout(gl_addStaffPanel);

		removeStaffPanel = new JPanel();
		removeStaffPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);

		lblToRemoveA = new JLabel("Select a staff to remove it");

		btnRemoveStaff = new JButton("remove Staff");
		btnRemoveStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = overviewTable.getSelectedRow();
				if (i >= 0) {
					try {
						cont.removeStaff(URLMSApplication.getURLMS().getSingleLab().getStaff(i));
						cont.saveLab();
						overviewTable.clearSelection();
						error = "staff removed";
					} catch (InvalidInputException e1) {
						e1.printStackTrace();
					}
				}
				else {
					error = "There are no staff selected";
				}
				refreshData();
			}
		});
		GroupLayout gl_removeStaffPanel = new GroupLayout(removeStaffPanel);
		gl_removeStaffPanel.setHorizontalGroup(
				gl_removeStaffPanel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_removeStaffPanel.createSequentialGroup().addGap(26).addComponent(lblToRemoveA)
								.addPreferredGap(ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
								.addComponent(btnRemoveStaff).addGap(22)));
		gl_removeStaffPanel
				.setVerticalGroup(
						gl_removeStaffPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(
										gl_removeStaffPanel.createSequentialGroup().addGap(16)
												.addGroup(gl_removeStaffPanel.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnRemoveStaff).addComponent(lblToRemoveA))
												.addGap(8)));
		removeStaffPanel.setLayout(gl_removeStaffPanel);

		btnBack = new JButton("Go back to home page");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_optionsPanel = new GroupLayout(optionsPanel);
		gl_optionsPanel.setHorizontalGroup(gl_optionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionsPanel
						.createSequentialGroup().addGroup(gl_optionsPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_optionsPanel.createSequentialGroup().addGap(16).addComponent(lblError,
										GroupLayout.PREFERRED_SIZE, 452, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_optionsPanel.createSequentialGroup().addGap(6).addComponent(addStaffPanel,
										GroupLayout.PREFERRED_SIZE, 476, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_optionsPanel.createSequentialGroup().addGap(6).addComponent(
										removeStaffPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_optionsPanel.createSequentialGroup().addContainerGap().addComponent(panel,
										GroupLayout.PREFERRED_SIZE, 472, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(4, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_optionsPanel.createSequentialGroup()
						.addContainerGap(295, Short.MAX_VALUE).addComponent(btnBack).addContainerGap()));
		gl_optionsPanel.setVerticalGroup(gl_optionsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_optionsPanel.createSequentialGroup().addGap(5)
						.addComponent(lblError, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE).addGap(2)
						.addComponent(addStaffPanel, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
						.addGap(6)
						.addComponent(removeStaffPanel, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnBack).addGap(13)));

		lblToChangeThe = new JLabel("To change the role of a staff, please select a staff and its new role");

		rdbtnAssociate_1 = new JRadioButton("Associate");
		rdbtnAssociate_1.setActionCommand("Associate");
		editStaffBtnGroup.add(rdbtnAssociate_1);

		rdbtnAssistant_1 = new JRadioButton("Assistant");
		rdbtnAssistant_1.setActionCommand("Assistant");
		editStaffBtnGroup.add(rdbtnAssistant_1);

		btnEditStaff = new JButton("Edit Staff");
		btnEditStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error = "";
				int i = overviewTable.getSelectedRow();
				Staff temp = null;
				ButtonModel chosenRole = null;
				if (i < 0) {
					error = "There are no staff selected";
				}
				else {
					temp = URLMSApplication.getURLMS().getSingleLab().getStaff(i);
				}

				try {
					chosenRole = editStaffBtnGroup.getSelection();
				} catch (NullPointerException e1) {
					error = "Please select a Staff Type.";
				}

				if (temp != null && i >= 0 && chosenRole != null) {
					if (temp.getRole().toString() == chosenRole.getActionCommand()) {
						error = "The role is the same";
					}
				}

				if (error.length() == 0) {
					try {
						cont.editStaffRole(temp, chosenRole.getActionCommand());
						cont.saveLab();
						overviewTable.clearSelection();
						error = "Staff edited";
					} catch (InvalidInputException e1) {
						e1.printStackTrace();
					}
				}
				refreshData();

			}
		});

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(34)
							.addComponent(rdbtnAssociate_1)
							.addGap(32)
							.addComponent(rdbtnAssistant_1)
							.addGap(59)
							.addComponent(btnEditStaff))
						.addComponent(lblToChangeThe))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addComponent(lblToChangeThe)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEditStaff)
						.addComponent(rdbtnAssociate_1)
						.addComponent(rdbtnAssistant_1))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		optionsPanel.setLayout(gl_optionsPanel);
		btnAddStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnAddStaffActionPerformed(evt);
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(splitPane,
				GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(splitPane,
				GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE));
		getContentPane().setLayout(groupLayout);
	}

	private void btnAddStaffActionPerformed(java.awt.event.ActionEvent evt) {
		ButtonModel chosenRole = null;
		String firstName = "";
		String lastName = "";
		error = "";

		try {
			chosenRole = staffBtnGroup.getSelection();
		} catch (NullPointerException e) {
			error = "Please select a Staff Type.";
		}

		if (chosenRole == null) {
			error = "Please select a Staff Type.";
		}

		try {
			firstName = txtFirstName.getText();
			lastName = txtLastName.getText();
		} catch (Exception e) {
			error = "One of the fields is missing";
		}

		if (lastName.length() == 0) {
			error = "The staff needs a last name";
			lblError.setText(error);
		}
		if (firstName.length() == 0) {
			error = "The staff needs a first name";
			lblError.setText(error);
		}

		if (firstName.matches(".*\\d+.*")) {
			error = "The first name cannot contain numbers";
		}
		if (lastName.matches(".*\\d+.*")) {
			error = "The last name cannot contain numbers";
		}

		if (error.length() == 0) {
			try {
				cont.addStaff(firstName, lastName, chosenRole.getActionCommand());
				error = "staff created";
				txtLastName.setText("");
				txtFirstName.setText("");
				staffBtnGroup.clearSelection();
				cont.saveLab();
			} catch (InvalidInputException e) {
				error = e.getMessage();
			}
		}
		refreshData();

	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}

	void refreshData() {

		// if (!(error.length() == 0)) {
		lblError.setText(error);
		// }
		staffListDtm = new DefaultTableModel(0, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		staffListDtm.setColumnIdentifiers(staffListColumnNames);
		overviewTable.setModel(staffListDtm);
		for (Staff aStaff : URLMSApplication.getURLMS().getSingleLab().getStaff()) {
			Object[] obj = { aStaff.getFirstName(), aStaff.getLastName(), aStaff.getRole().toString(), aStaff.getID() };
			staffListDtm.addRow(obj);
		}

		Dimension d = overviewTable.getPreferredSize();
		overviewScrollPane.setPreferredSize(new Dimension(d.width, d.height));

	}
}
