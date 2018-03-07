package ca.mcgill.ecse321.urlms.view;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Staff;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProgressUpdatePage extends JFrame {

	static ManagerController cont = URLMSApplication.mc;
	private Lab lab;
	private String error = "";

	private JSplitPane mainSplitPane;

	private JSplitPane optionSplitPane;

	private JPanel writePanel;
	private JComboBox staffNameComboBox;
	private JScrollPane textScrollPane;
	private JTextPane textArea;
	private JLabel lblError;
	private JButton btnAddProgressUpdate;

	private JTable listTable;
	private JScrollPane listScrollPane;
	private DefaultTableModel progressListDtm;
	private String progressListColumnNames[] = { "First Name", "Last Name", "Role", "Date", "Description" };

	private JPanel displayPanel;
	private JScrollPane displayScrollPane;
	private JTextPane displayTextArea;
	private JButton btnBack;

	public ProgressUpdatePage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		this.setTitle("Progress Updates");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);

		try {
			this.lab = cont.loadLab();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainSplitPane = new JSplitPane();
		mainSplitPane.setBackground(SystemColor.window);
		mainSplitPane.setSize(1000, 700);
		mainSplitPane.setDividerSize(0);
		mainSplitPane.setDividerLocation(600);
		mainSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(mainSplitPane, GroupLayout.PREFERRED_SIZE, 1000, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(200, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainSplitPane,
				GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE));

		optionSplitPane = new JSplitPane();
		optionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		optionSplitPane.setDividerLocation(400);
		optionSplitPane.setDividerSize(0);

		mainSplitPane.setLeftComponent(optionSplitPane);

		writePanel = new JPanel();
		optionSplitPane.setLeftComponent(writePanel);

		textArea = new JTextPane();
		textScrollPane = new JScrollPane(textArea);

		staffNameComboBox = new JComboBox();
		String[] staffMembers = new String[lab.getStaff().size()];
		addStaff(staffMembers, lab.getStaff());
		staffNameComboBox.setModel(new DefaultComboBoxModel(staffMembers));

		btnAddProgressUpdate = new JButton("Add Progress Update");
		btnAddProgressUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error = "";
				Staff temp = null;
				if (textArea.getText() == "") {
					error = "The progress update cannot be empty";
				}
				if (staffNameComboBox.getSelectedIndex() == -1) {
					error = "There are no staffs in the lab";
				}
				else {
					temp = lab.getStaff(staffNameComboBox.getSelectedIndex());
				}
				if (URLMSApplication.currentStaff != null) {
					if (URLMSApplication.currentStaff.getRole() instanceof Assistant) {
						temp = URLMSApplication.currentStaff;
					}
					if (URLMSApplication.currentStaff.getRole() instanceof Associate) {
						if (temp.getRole() instanceof Director) {
							error = "You are not allowed to add a progress udpate to a director";
						}
					}
				}
				
				
				if (error.length() == 0) {
					try {
						cont.createProgressUpdate(textArea.getText(),
								temp);
						cont.saveLab();
						textArea.setText("");
						error = "progress udpate successfully added";
					} catch (InvalidInputException e1) {
						error = e1.getMessage();
					}
				}
				refreshData();
			}
		});

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);

		GroupLayout gl_writePanel = new GroupLayout(writePanel);
		gl_writePanel.setHorizontalGroup(gl_writePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_writePanel.createSequentialGroup().addContainerGap()
						.addGroup(gl_writePanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(textScrollPane, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
								.addComponent(btnAddProgressUpdate)
								.addGroup(gl_writePanel.createSequentialGroup().addComponent(lblError)
										.addPreferredGap(ComponentPlacement.RELATED, 296, Short.MAX_VALUE).addComponent(
												staffNameComboBox, GroupLayout.PREFERRED_SIZE, 225,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_writePanel.setVerticalGroup(gl_writePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_writePanel.createSequentialGroup().addGap(11)
						.addGroup(gl_writePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(staffNameComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblError))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(textScrollPane, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnAddProgressUpdate)
						.addContainerGap(9, Short.MAX_VALUE)));
		writePanel.setLayout(gl_writePanel);

		// ------
		listTable = new JTable();
		listTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				boolean found = false;
				ProgressUpdate temp = lab.getProgressUpdate(listTable.getSelectedRow());
				if (listTable.getSelectedRow() >= 0) {
					String role = "";
					for (Staff aStaff : lab.getStaff()) {
						for (ProgressUpdate pu : aStaff.getProgressUpdates()) {
							if (pu == temp) {
								role = aStaff.getRole().toString();
								found = true;
							}
							if (found) {
								break;
							}
						}
						if (found) {
							break;
						}
					}
					if (found) {
						displayTextArea.setText(
								temp.getFirstName() + " " + temp.getLastName() + "\n" + role + "\n" + temp.getDate().toString() + "\n"
										+ "\n" + lab.getProgressUpdate(listTable.getSelectedRow()).getDescription());
					}
					else {
						displayTextArea.setText(
								temp.getFirstName() + " " + temp.getLastName() + "(Terminated)"+ "\n" + "\n" + temp.getDate().toString() + "\n"
										+ "\n" + lab.getProgressUpdate(listTable.getSelectedRow()).getDescription());
					}

				}
			}
		});

		listScrollPane = new JScrollPane(listTable);

		optionSplitPane.setRightComponent(listScrollPane);
		Dimension d = listTable.getPreferredSize();
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		// --------------
		displayPanel = new JPanel();
		mainSplitPane.setRightComponent(displayPanel);

		displayTextArea = new JTextPane();
		displayTextArea.setEditable(false);
		displayScrollPane = new JScrollPane(displayTextArea);

		btnBack = new JButton("Go back to home page");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});

		GroupLayout gl_displayPanel = new GroupLayout(displayPanel);
		gl_displayPanel.setHorizontalGroup(gl_displayPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_displayPanel.createSequentialGroup().addContainerGap()
						.addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_displayPanel.createSequentialGroup()
						.addContainerGap(275, Short.MAX_VALUE).addComponent(btnBack).addContainerGap()));
		gl_displayPanel.setVerticalGroup(gl_displayPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_displayPanel.createSequentialGroup().addContainerGap()
						.addComponent(displayScrollPane, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 289, Short.MAX_VALUE).addComponent(btnBack)
						.addContainerGap()));
		displayPanel.setLayout(gl_displayPanel);

		// --------
		getContentPane().setLayout(groupLayout);
	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}

	private void refreshData() {
		if (URLMSApplication.currentStaff != null) {
			if (URLMSApplication.currentStaff.getRole() instanceof Assistant) {
				staffNameComboBox.setEnabled(false);
				staffNameComboBox.setVisible(false);
			}
		}
		
		boolean found = false;
		lblError.setText(error);
		progressListDtm = new DefaultTableModel(0, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		progressListDtm.setColumnIdentifiers(progressListColumnNames);
		listTable.setModel(progressListDtm);
		for (ProgressUpdate pu : lab.getProgressUpdates()) {
			found = false;
			for (Staff aStaff : lab.getStaff()) {
				for (ProgressUpdate pu2 : aStaff.getProgressUpdates()) {
					if (pu2 == pu) {
						Object[] obj = { aStaff.getFirstName(), aStaff.getLastName(), aStaff.getRole().toString(),
								pu.getDate().toString(), pu.getDescription() };
						progressListDtm.addRow(obj);
						found = true;
						// break;
					}
				}
			}
			if (!found) {
				Object[] obj = { "", "", "", pu.getDate().toString(), pu.getDescription() };
				progressListDtm.addRow(obj);
			}

		}

		Dimension d = listTable.getPreferredSize();
		listScrollPane.setPreferredSize(new Dimension(d.width, d.height));

	}

	private void addStaff(String[] newList, List<Staff> staffs) {
		int i = 0;
		for (Staff aStaff : staffs) {
			newList[i] = aStaff.getFirstName() + " " + aStaff.getLastName();
			i++;
		}
	}
}
