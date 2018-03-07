package ca.mcgill.ecse321.urlms.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ca.mcgill.ecse321.urlms.application.URLMSApplication;
import ca.mcgill.ecse321.urlms.controller.InvalidInputException;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfirmRootPage extends JFrame{
	
	private JLabel lblThisWillBe;
	private JLabel lblPassword;
	private JButton btnConfirm;
	private JButton btnCancel;

	public ConfirmRootPage(String password) {
		initComponents(password);
	}

	private void initComponents(final String password) {
		this.setTitle("Confirm Admin Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setSize(300, 150);
		this.setAlwaysOnTop(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		lblThisWillBe = new JLabel("This will be your root password");
		lblThisWillBe.setBounds(44, 17, 210, 16);
		
		lblPassword = new JLabel();
		lblPassword.setBounds(30, 45, 241, 24);
		lblPassword.setForeground(Color.RED);
		lblPassword.setText(password);
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					URLMSApplication.load();
					URLMSApplication.mc.createLab(password);
					URLMSApplication.mc.saveLab();
					URLMSApplication.mc.loadLab();
					close();
					URLMSApplication.clr.close();
					URLMSApplication.w = new WelcomeAdmin();
					URLMSApplication.w.setVisible(true);
				} catch (InvalidInputException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnConfirm.setBounds(185, 81, 95, 29);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		btnCancel.setBounds(20, 81, 86, 29);
		panel.setLayout(null);
		panel.add(lblThisWillBe);
		panel.add(btnCancel);
		panel.add(btnConfirm);
		panel.add(lblPassword);
		getContentPane().setLayout(groupLayout);
				
	}
	
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
}
