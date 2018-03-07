package ca.mcgill.ecse321.urlms.view;

import javax.swing.JFrame;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.model.Staff;

import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ResourcePage extends JFrame {
	static ManagerController cont = URLMSApplication.mc;

	private Lab lab;
	private String error = "";
	private JLabel lblError;

	private JSplitPane mainSplitPane;

	private JSplitPane optionSplitPane;

	private JPanel addResourcePanel;
	private JLabel lblNameResource;
	private JTextField resourceNameTextField;
	private JRadioButton rdbtnEquipment;
	private JRadioButton rdbtnSupply;
	private final ButtonGroup resourceBtnGroup = new ButtonGroup();
	private JButton btnNewResource;
	private JLabel lblQuantity;
	private JSpinner spinnerQty;
	private SpinnerNumberModel spinnerModel;

	private JTable itemTable;
	private JScrollPane itemScrollPane;
	private DefaultTableModel itemsDtm;
	private String itemsColumnNames[] = { "Item", "Quantity", "Type" };

	private JPanel displayPanel;
	private JPanel previewPanel;
	private JLabel lblName;
	private JLabel lblEditQuantity;
	private JSpinner editSpinner;
	private JButton btnDelete;
	private JButton btnSave;
	private SpinnerNumberModel editSpinnerModel;
	private JLabel lblErrorEdit;
	private JButton btnBack;

	public ResourcePage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		this.setTitle("Resource Page");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
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
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainSplitPane,
				GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainSplitPane,
				GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE));

		optionSplitPane = new JSplitPane();
		optionSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		optionSplitPane.setDividerLocation(300);
		optionSplitPane.setDividerSize(0);

		mainSplitPane.setLeftComponent(optionSplitPane);

		addResourcePanel = new JPanel();
		optionSplitPane.setLeftComponent(addResourcePanel);

		lblNameResource = new JLabel("Name of the resource :");
		lblNameResource.setBounds(44, 95, 143, 16);

		resourceNameTextField = new JTextField();
		resourceNameTextField.setBounds(205, 90, 230, 26);
		resourceNameTextField.setColumns(10);

		rdbtnEquipment = new JRadioButton("Equipment");
		rdbtnEquipment.setBounds(109, 191, 99, 23);
		rdbtnEquipment.setActionCommand("Equipment");
		resourceBtnGroup.add(rdbtnEquipment);

		rdbtnSupply = new JRadioButton("Supply");
		rdbtnSupply.setBounds(226, 191, 74, 23);
		rdbtnSupply.setActionCommand("Supply");
		resourceBtnGroup.add(rdbtnSupply);

		btnNewResource = new JButton("Add resource");
		btnNewResource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addResourceBtn(e);
			}
		});
		btnNewResource.setBounds(388, 190, 127, 29);

		spinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		spinnerQty = new JSpinner(spinnerModel);
		spinnerQty.setBounds(205, 134, 67, 26);

		lblQuantity = new JLabel("Quantity :");
		lblQuantity.setBounds(44, 139, 62, 16);
		addResourcePanel.setLayout(null);

		lblError = new JLabel("");
		lblError.setBounds(44, 28, 435, 16);
		lblError.setForeground(Color.RED);

		addResourcePanel.add(lblError);
		addResourcePanel.add(rdbtnEquipment);
		addResourcePanel.add(rdbtnSupply);
		addResourcePanel.add(btnNewResource);
		addResourcePanel.add(lblNameResource);
		addResourcePanel.add(lblQuantity);
		addResourcePanel.add(resourceNameTextField);
		addResourcePanel.add(spinnerQty);

		// --------------

		itemTable = new JTable();
		itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				itemTableChanged(event);
			}
		});

		itemScrollPane = new JScrollPane(itemTable);
		itemScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		optionSplitPane.setRightComponent(itemScrollPane);

		displayPanel = new JPanel();
		mainSplitPane.setRightComponent(displayPanel);

		previewPanel = new JPanel();
		previewPanel.setBackground(SystemColor.text);
		previewPanel.setBounds(7, 45, 379, 223);
		previewPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		lblErrorEdit = new JLabel("");
		lblErrorEdit.setBounds(45, 23, 330, 16);
		lblErrorEdit.setForeground(Color.RED);

		lblName = new JLabel("New label");

		lblEditQuantity = new JLabel("Edit quantity");

		editSpinnerModel = new SpinnerNumberModel(1, 1, null, 1);
		displayPanel.setLayout(null);
		editSpinner = new JSpinner(editSpinnerModel);

		btnDelete = new JButton("Delete ");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteResourceAction(e);
			}
		});

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editResource(e);
			}
		});
		GroupLayout gl_previewPanel = new GroupLayout(previewPanel);
		gl_previewPanel.setHorizontalGroup(gl_previewPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_previewPanel.createSequentialGroup().addGap(99).addComponent(lblEditQuantity).addGap(18)
						.addComponent(editSpinner, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(gl_previewPanel.createSequentialGroup().addGap(56).addComponent(btnDelete)
						.addPreferredGap(ComponentPlacement.RELATED, 96, Short.MAX_VALUE).addComponent(btnSave)
						.addGap(62))
				.addGroup(Alignment.LEADING,
						gl_previewPanel.createSequentialGroup().addGap(47)
								.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 306, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(24, Short.MAX_VALUE)));
		gl_previewPanel.setVerticalGroup(gl_previewPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_previewPanel.createSequentialGroup().addGap(29).addComponent(lblName).addGap(41)
						.addGroup(gl_previewPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblEditQuantity)
								.addComponent(editSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(37).addGroup(gl_previewPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSave).addComponent(btnDelete))
						.addGap(53)));
		previewPanel.setLayout(gl_previewPanel);
		displayPanel.add(previewPanel);
		displayPanel.add(lblErrorEdit);

		btnBack = new JButton("Go back to home page");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});
		btnBack.setBounds(197, 639, 195, 29);
		displayPanel.add(btnBack);
		getContentPane().setLayout(groupLayout);

	}

	protected void deleteResourceAction(ActionEvent e) {
		Resource temp = lab.getInventory().getResource(itemTable.getSelectedRow());
		try {
			cont.removeResource(temp);
			cont.saveLab();
			lblErrorEdit.setText(temp.getName() + " has been successfully removed");
			itemTable.clearSelection();
		} catch (InvalidInputException e1) {
			error = e1.getMessage();
		}
		refreshData();

	}

	protected void editResource(ActionEvent e) {
		error = "";
		Resource temp = lab.getInventory().getResource(itemTable.getSelectedRow());
		int newQty = 0;

		try {
			newQty = (int) editSpinner.getValue();
		} catch (IllegalArgumentException e1) {
			error = "please input a value value";
		}

		if (error.length() == 0 && newQty > 0) {
			try {
				cont.editResource(temp, newQty);
				cont.saveLab();
				lblErrorEdit.setText(temp.getName() + " has been successfully saved");
			} catch (InvalidInputException e1) {
				error = e1.getMessage();
			}
		}
		refreshData();
	}

	protected void itemTableChanged(ListSelectionEvent event) {
		if (itemTable.getSelectedRow() >= 0) {
			lblName.setVisible(true);
			lblEditQuantity.setVisible(true);
			editSpinner.setVisible(true);
			btnDelete.setVisible(true);
			btnSave.setVisible(true);

			Resource temp = lab.getInventory().getResource(itemTable.getSelectedRow());

			lblName.setText(temp.getName());
			editSpinnerModel.setValue(temp.getQuantity());

		}
	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}

	protected void addResourceBtn(ActionEvent e) {
		error = "";
		String resourceName = "";
		int qty = 0;
		ButtonModel chosenResource = null;
		try {
			chosenResource = resourceBtnGroup.getSelection();
		} catch (NullPointerException e1) {
			error = "Please select a Resource Type.";
		}

		try {
			resourceName = resourceNameTextField.getText();
		} catch (Exception e2) {
			error = "Please input a resource name";
		}

		if (resourceName.length() == 0) {
			error = "Please input a resource name";
		}

		try {
			qty = (int) spinnerQty.getValue();
		} catch (IllegalArgumentException e3) {
			error = "please input a value value";
		}
		if (qty <= 0) {
			error = "The quantity needs to be positive";
		}
		if (chosenResource == null) {
			error = "Please choose a resource";
		}

		if (error.length() == 0) {
			if (chosenResource.getActionCommand() == "Equipment") {
				try {
					cont.addEquipement(qty, resourceName);
				} catch (InvalidInputException ev) {
					error = ev.getMessage();
				}
			}
			else if (chosenResource.getActionCommand() == "Supply") {
				try {
					cont.addSupply(qty, resourceName);
				} catch (InvalidInputException ev1) {
					error = ev1.getMessage();
				}
			}
			cont.saveLab();
			resourceNameTextField.setText("");
			spinnerQty.setValue(1);
			error = "";
			resourceBtnGroup.clearSelection();
		}
		refreshData();

	}

	private void refreshData() {
		lblError.setText(error);

		itemsDtm = new DefaultTableModel(0, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		itemsDtm.setColumnIdentifiers(itemsColumnNames);
		itemTable.setModel(itemsDtm);
		for (Resource r : URLMSApplication.getURLMS().getSingleLab().getInventory().getResources()) {
			Object[] obj = { r.getName(), r.getQuantity(), r.toString() };
			itemsDtm.addRow(obj);
		}
		Dimension d = itemTable.getPreferredSize();
		itemScrollPane.setPreferredSize(new Dimension(d.width, d.height));

		lblName.setVisible(false);
		lblEditQuantity.setVisible(false);
		editSpinner.setVisible(false);
		btnDelete.setVisible(false);
		btnSave.setVisible(false);
	}
}
