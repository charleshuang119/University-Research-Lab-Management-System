package ca.mcgill.ecse321.urlms.view;

import javax.swing.JFrame;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.model.Assistant;
import ca.mcgill.ecse321.urlms.model.Associate;
import ca.mcgill.ecse321.urlms.model.Director;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WelcomeAdmin extends JFrame {

	private JButton btnProgress;
	private JButton btnStaff;
	private JButton btnAddExpense;
	private JButton btnFundingAccount;
	private JButton btnInventory;

	public WelcomeAdmin() {
		initComponent();
		refreshData();
	}

	private void initComponent() {
		this.setTitle("Menu");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		btnProgress = new JButton("Progress");
		btnProgress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.dp = new ProgressUpdatePage();
				URLMSApplication.dp.setVisible(true);
				close();
			}
		});

		btnStaff = new JButton("Staff");
		btnStaff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.sp = new StaffPage();
				URLMSApplication.sp.setVisible(true);
				close();
			}
		});

		btnInventory = new JButton("Inventory");
		btnInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.rp = new ResourcePage();
				URLMSApplication.rp.setVisible(true);
				close();
			}
		});

		btnFundingAccount = new JButton("Funding Account");
		btnFundingAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.fp = new FundingPage();
				URLMSApplication.fp.setVisible(true);
				close();
			}
		});

		btnAddExpense = new JButton("Add Expense");
		btnAddExpense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				URLMSApplication.ep = new AddExpensePage();
				URLMSApplication.ep.setVisible(true);
				close();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(33)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addComponent(btnInventory).addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnStaff)
										.addPreferredGap(ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
										.addComponent(btnFundingAccount).addGap(104))
								.addGroup(groupLayout.createSequentialGroup().addComponent(btnProgress)
										.addPreferredGap(ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
										.addComponent(btnAddExpense).addGap(115))))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(40)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnProgress)
						.addComponent(btnAddExpense))
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(18).addComponent(btnStaff).addGap(30)
								.addComponent(btnInventory))
						.addGroup(groupLayout.createSequentialGroup().addGap(26).addComponent(btnFundingAccount)))
				.addContainerGap(103, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}

	private void refreshData() {
		if (URLMSApplication.currentStaff != null) {
			if (URLMSApplication.currentStaff.getRole() instanceof Associate) {
				btnFundingAccount.setEnabled(false);
				btnFundingAccount.setVisible(false);
			}
			if (URLMSApplication.currentStaff.getRole() instanceof Assistant) {
				btnFundingAccount.setEnabled(false);
				btnFundingAccount.setVisible(false);
				btnStaff.setEnabled(false);
				btnStaff.setVisible(false);

			}
			if (URLMSApplication.currentStaff.getRole() instanceof Director) {
				if (!URLMSApplication.mc.isFundingAccountSet()) {
					URLMSApplication.sfp = new SetFundingPage();
					URLMSApplication.sfp.setVisible(true);
				}
			}
		}

	}

	private void close() {
		this.setVisible(false);
		this.dispose();
	}
}
