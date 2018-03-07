package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SetFundingPage extends JFrame{
	private JTextField amountTextField;
	
	private JLabel lblError;
	private JLabel lblInstruction;
	private JLabel lblAmount;
	private JButton btnSetUpAllocated;
	
	String error = "";
	private JButton btnClose;
	
	public SetFundingPage() {
		initComponents();
		refreshData();
	}
	
	
	private void initComponents() {
		this.setTitle("Set Up Funding Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(true);
		setResizable(false);
		this.setSize(450, 250);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 229, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(98, Short.MAX_VALUE))
		);
		
		lblInstruction = new JLabel("Please set up the funding account");
		lblInstruction.setBounds(41, 60, 213, 16);
		
		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(41, 128, 54, 16);
		
		amountTextField = new JTextField();
		amountTextField.setBounds(107, 123, 206, 26);
		amountTextField.setColumns(10);
		
		lblError = new JLabel();
		lblError.setBounds(41, 26, 368, 16);
		lblError.setForeground(Color.RED);
		
		btnSetUpAllocated = new JButton("Set up allocated amount");
		btnSetUpAllocated.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amountString = amountTextField.getText();
				int value = Integer.valueOf(amountString);
				if (value <= 0) {
					error = "the amount cannot be 0 or negative";
				}
				if (error.length() == 0) {
					try {
						URLMSApplication.mc.setFundingAccount(value);
						URLMSApplication.mc.saveLab();
						error = "The funding account has been set";
					} catch (InvalidInputException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				refreshData();
			}
		});
		btnSetUpAllocated.setBounds(213, 175, 196, 29);
		panel.setLayout(null);
		panel.add(lblError);
		panel.add(btnSetUpAllocated);
		panel.add(lblAmount);
		panel.add(amountTextField);
		panel.add(lblInstruction);
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnClose.setBounds(29, 175, 117, 29);
		panel.add(btnClose);
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
