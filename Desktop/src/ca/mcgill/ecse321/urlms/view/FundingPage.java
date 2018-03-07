package ca.mcgill.ecse321.urlms.view;

import javax.swing.JFrame;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;
import ca.mcgill.ecse321.urlms.controller.ManagerController;
import ca.mcgill.ecse321.urlms.model.Director;
import ca.mcgill.ecse321.urlms.model.Expense;
import ca.mcgill.ecse321.urlms.model.Lab;
import ca.mcgill.ecse321.urlms.model.ProgressUpdate;
import ca.mcgill.ecse321.urlms.model.Resource;
import ca.mcgill.ecse321.urlms.model.Staff;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.table.DefaultTableModel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class FundingPage extends JFrame {

	private Lab lab;
	private String error = "";
	private JLabel lblError;

	private JSplitPane mainSplitPane;
	private JSplitPane viewSplitPane; // left

	private JTable expenseTable;
	private JScrollPane expenseScrollPane;
	private DefaultTableModel expenseDtm;
	private String expenseColumnNames[] = { "Description", "Amount", "Type", "Date" };

	private JPanel infoPanel;
	private JLabel lblAllocated;
	private JLabel lblRemaining;
	private JPanel rightPanel;

	private JPanel expenseInfoPanel;
	private JLabel lblDescription;
	private JLabel lblAmount;
	private JLabel lblType;
	private JLabel lblDate;
	private JLabel lblDescriptionContent;
	private JLabel lblAmountContent;
	private JLabel lblTypeContent;
	private JLabel lblDateContent;

	private JPanel breakdownPanel;
	private JLabel lblBreakdown;
	private JLabel lblTravel;
	private JLabel lblSalary;
	private JLabel lblResource;
	private JLabel lblOther;
	private JLabel lblTravelContent;
	private JLabel lblSalaryContent;
	private JLabel lblResourceContent;
	private JLabel lblOtherContent;
	private JLabel lblAllocatedContent;
	private JLabel lblRemainingContent;
	private JButton btnGoBackTo;

	public FundingPage() {
		initComponents();
		refreshData();
	}

	private void initComponents() {
		this.setTitle("Funding Page");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		this.setResizable(false);

		this.lab = URLMSApplication.getURLMS().getSingleLab();

		mainSplitPane = new JSplitPane();
		mainSplitPane.setSize(1000, 700);
		mainSplitPane.setDividerSize(0);
		mainSplitPane.setDividerLocation(600);
		mainSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(mainSplitPane,
				GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addComponent(mainSplitPane, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(15, Short.MAX_VALUE)));

		viewSplitPane = new JSplitPane();
		viewSplitPane.setDividerSize(0);
		viewSplitPane.setDividerLocation(200);
		viewSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);

		mainSplitPane.setLeftComponent(viewSplitPane);

		// ------------------
		expenseTable = new JTable();
		expenseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		expenseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (expenseTable.getSelectedRow() >= 0) {
					Expense temp = lab.getFundingAccount().getExpense(expenseTable.getSelectedRow());
					lblDescriptionContent.setText(temp.getDescription());
					lblAmountContent.setText(String.format("%.2f", temp.getAmount()) + "$");
					lblTypeContent.setText(temp.getTypeFullName()); // TODO: change?
					lblDateContent.setText(temp.getDate().toString());

				}
			}
		});

		expenseScrollPane = new JScrollPane(expenseTable);
		expenseScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// --------

		viewSplitPane.setRightComponent(expenseScrollPane);

		infoPanel = new JPanel();
		viewSplitPane.setLeftComponent(infoPanel);

		lblError = new JLabel("error");
		lblError.setBounds(26, 16, 445, 16);
		lblError.setForeground(Color.RED);

		lblAllocated = new JLabel("Allocated amount:");
		lblAllocated.setBounds(26, 50, 115, 16);

		lblRemaining = new JLabel("Remaining amount:");
		lblRemaining.setBounds(26, 84, 122, 16);

		lblAllocatedContent = new JLabel("");
		lblAllocatedContent.setBounds(160, 44, 174, 22);
		lblAllocatedContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblRemainingContent = new JLabel("");
		lblRemainingContent.setBounds(160, 78, 174, 22);
		lblRemainingContent.setHorizontalAlignment(SwingConstants.RIGHT);
		infoPanel.setLayout(null);
		infoPanel.add(lblRemaining);
		infoPanel.add(lblRemainingContent);
		infoPanel.add(lblAllocated);
		infoPanel.add(lblAllocatedContent);
		infoPanel.add(lblError);

		rightPanel = new JPanel();
		mainSplitPane.setRightComponent(rightPanel);

		expenseInfoPanel = new JPanel();
		expenseInfoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		expenseInfoPanel.setBackground(SystemColor.text);

		breakdownPanel = new JPanel();
		breakdownPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		breakdownPanel.setBackground(SystemColor.text);

		btnGoBackTo = new JButton("Go back to main page");
		btnGoBackTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.w = new WelcomeAdmin();
				URLMSApplication.w.setVisible(true);
				close();
			}
		});

		JButton btnCreateReport = new JButton("Create report");
		btnCreateReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createReport();
					error = "Report created";
				} catch (FileNotFoundException e1) {
				} catch (UnsupportedEncodingException e1) {
				}
				refreshData();
			}
		});
		GroupLayout gl_rightPanel = new GroupLayout(rightPanel);
		gl_rightPanel.setHorizontalGroup(gl_rightPanel.createParallelGroup(Alignment.TRAILING).addGroup(gl_rightPanel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_rightPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(expenseInfoPanel, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE)
						.addComponent(breakdownPanel, GroupLayout.PREFERRED_SIZE, 385, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(7, Short.MAX_VALUE))
				.addGroup(gl_rightPanel.createSequentialGroup().addContainerGap(211, Short.MAX_VALUE)
						.addComponent(btnGoBackTo).addContainerGap())
				.addGroup(gl_rightPanel.createSequentialGroup().addContainerGap(256, Short.MAX_VALUE)
						.addComponent(btnCreateReport).addGap(16)));
		gl_rightPanel.setVerticalGroup(gl_rightPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rightPanel.createSequentialGroup().addGap(30)
						.addComponent(breakdownPanel, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addGap(37)
						.addComponent(expenseInfoPanel, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 149, Short.MAX_VALUE).addComponent(btnCreateReport)
						.addGap(53).addComponent(btnGoBackTo).addContainerGap()));

		lblBreakdown = new JLabel("Breakdown");
		lblBreakdown.setBounds(19, 17, 68, 16);
		lblBreakdown.setFont(new Font("Lucida Grande", Font.PLAIN, 13));

		lblTravel = new JLabel("Travel:");
		lblTravel.setBounds(36, 57, 42, 16);

		lblSalary = new JLabel("Salary:");
		lblSalary.setBounds(36, 91, 41, 16);

		lblResource = new JLabel("Resource:");
		lblResource.setBounds(198, 57, 61, 16);

		lblOther = new JLabel("Other:");
		lblOther.setBounds(198, 91, 39, 16);

		lblTravelContent = new JLabel("");
		lblTravelContent.setBounds(84, 57, 84, 16);
		lblTravelContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblSalaryContent = new JLabel("");
		lblSalaryContent.setBounds(84, 91, 84, 16);
		lblSalaryContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblResourceContent = new JLabel("");
		lblResourceContent.setBounds(265, 57, 84, 16);
		lblResourceContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblOtherContent = new JLabel("");
		lblOtherContent.setBounds(265, 91, 84, 16);
		lblOtherContent.setHorizontalAlignment(SwingConstants.RIGHT);
		breakdownPanel.setLayout(null);
		breakdownPanel.add(lblBreakdown);
		breakdownPanel.add(lblTravel);
		breakdownPanel.add(lblSalary);
		breakdownPanel.add(lblSalaryContent);
		breakdownPanel.add(lblTravelContent);
		breakdownPanel.add(lblResource);
		breakdownPanel.add(lblOther);
		breakdownPanel.add(lblOtherContent);
		breakdownPanel.add(lblResourceContent);

		lblDescription = new JLabel("Description:");
		lblDescription.setBounds(23, 24, 77, 16);

		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(23, 58, 54, 16);

		lblType = new JLabel("Type:");
		lblType.setBounds(23, 92, 34, 16);

		lblDate = new JLabel("Date:");
		lblDate.setBounds(23, 126, 33, 16);

		lblDescriptionContent = new JLabel("");
		lblDescriptionContent.setBounds(127, 24, 173, 16);
		lblDescriptionContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblAmountContent = new JLabel("");
		lblAmountContent.setBounds(127, 58, 173, 16);
		lblAmountContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblTypeContent = new JLabel("");
		lblTypeContent.setBounds(127, 92, 173, 16);
		lblTypeContent.setHorizontalAlignment(SwingConstants.RIGHT);

		lblDateContent = new JLabel("");
		lblDateContent.setBounds(127, 126, 173, 16);
		lblDateContent.setHorizontalAlignment(SwingConstants.RIGHT);
		expenseInfoPanel.setLayout(null);
		expenseInfoPanel.add(lblDescription);
		expenseInfoPanel.add(lblAmount);
		expenseInfoPanel.add(lblType);
		expenseInfoPanel.add(lblDate);
		expenseInfoPanel.add(lblDescriptionContent);
		expenseInfoPanel.add(lblAmountContent);
		expenseInfoPanel.add(lblTypeContent);
		expenseInfoPanel.add(lblDateContent);
		rightPanel.setLayout(gl_rightPanel);
		getContentPane().setLayout(groupLayout);

	}

	private void refreshData() {
		setTable();
		setData();

		lblError.setText(error);
	}

	private void setData() {
		if (URLMSApplication.mc.isFundingAccountSet()) {
//			lblAllocatedContent.setText(Integer.toString(lab.getFundingAccount().getAllocatedAmount()) + "$");
//			lblRemainingContent.setText(Integer.toString(lab.getFundingAccount().getRemainingAmount()) + "$");
			lblAllocatedContent.setText(String.format("%.2f", lab.getFundingAccount().getAllocatedAmount()) + "$");
			lblRemainingContent.setText(String.format("%.2f", lab.getFundingAccount().getRemainingAmount()) + "$");

		}
		else {
			error = "The funding account has not been initialized";
			// lblAllocatedContent.setText("");
			// lblRemainingContent.setText("");
			//
			// lblTravelContent.setText("");
			// lblSalaryContent.setText("");
			// lblResourceContent.setText("");
			// lblOtherContent.setText("");

		}
//		lblTravelContent.setText(Integer.toString(lab.getFundingAccount().getTravelExpensesTotal()) + "$");
//		lblSalaryContent.setText(Integer.toString(lab.getFundingAccount().getSalaryExpensesTotal()) + "$");
//		lblResourceContent.setText(Integer.toString(lab.getFundingAccount().getResourceExpensesTotal()) + "$");
//		lblOtherContent.setText(Integer.toString(lab.getFundingAccount().getOtherExpensesTotal()) + "$");
		lblTravelContent.setText(String.format("%.2f", lab.getFundingAccount().getTravelExpensesTotal()) + "$");
		lblSalaryContent.setText(String.format("%.2f", lab.getFundingAccount().getSalaryExpensesTotal()) + "$");
		lblResourceContent.setText(String.format("%.2f", lab.getFundingAccount().getResourceExpensesTotal()) + "$");
		lblOtherContent.setText(String.format("%.2f", lab.getFundingAccount().getOtherExpensesTotal()) + "$");

	}

	public void close() {
		this.setVisible(false);
		this.dispose();
	}

	private void setTable() {
		expenseDtm = new DefaultTableModel(0, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;// This causes all cells to be not editable
			}
		};
		expenseDtm.setColumnIdentifiers(expenseColumnNames);
		expenseTable.setModel(expenseDtm);
		for (Expense e : URLMSApplication.getURLMS().getSingleLab().getFundingAccount().getExpenses()) {
			Object[] obj = { e.getDescription(), String.format("%.2f", e.getAmount())
					+ "$", e.getType().toString(), e.getDate().toString() };
			expenseDtm.addRow(obj);
		}
		Dimension d = expenseTable.getPreferredSize();
		expenseScrollPane.setPreferredSize(new Dimension(d.width, d.height));

	}

	protected void createReport() throws FileNotFoundException, UnsupportedEncodingException {
		Date newDate = new Date(Calendar.getInstance().getTimeInMillis());
		Staff temp = null;
		float maxAmount = 0;
		Staff maxStaff = null;
		PrintWriter writer = new PrintWriter("FundingReport-" + newDate.toString() + ".txt", "UTF-8");
		writer.println(newDate.toString());
		if (URLMSApplication.getURLMS().getSingleLab().hasDirector()) {
			for (Staff s : URLMSApplication.getURLMS().getSingleLab().getStaff()) {
				if (s.getRole() instanceof Director) {
					temp = s;
					break;
				}
			}
			if (temp != null) {
				writer.println("The current director is: " + temp.getFirstName() + " " + temp.getLastName() + ".");
			}
			else {
				writer.println("there is no current director.");
			}
		}

		writer.println("\n");
		writer.println("\n");
		for (Staff aStaff : URLMSApplication.getURLMS().getSingleLab().getStaff()) {
			writer.println(aStaff.getFirstName() + " " + aStaff.getLastName() + ":");
			float tempTotal = 0;
			for (Expense e : aStaff.getExpenses()) {
				writer.print("\t");
				writer.print(String.format("%.2f", e.getAmount()) + "$\t" + e.getTypeFullName() + ": " + e.getDescription()
						+ " on " + e.getDate().toString() + "\n");
				tempTotal += e.getAmount();
			}
			if (tempTotal > maxAmount) {
				maxStaff = aStaff;
				maxAmount = tempTotal;
			}

			writer.println("\tTotal: " + Float.toString(tempTotal));
		}
		if (maxStaff != null) {
			writer.println("\n");
			writer.println(
					"The Staff member who spent the most: " + maxStaff.getFirstName() + " " + maxStaff.getLastName());
			writer.println("That staff member spent: " + Float.toString(maxAmount) + "$");
		}
		writer.close();
	}
}
