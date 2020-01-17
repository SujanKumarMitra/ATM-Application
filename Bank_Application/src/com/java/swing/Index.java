package com.java.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Index {

	public static void main(String[] args) {
		JFrame f = new JFrame("Banking ATM Application");
		JButton ac_create = new JButton("Create Account");
		JButton bal_check = new JButton("Balance Check");
		JButton wd = new JButton("Balance Withdraw");
		JButton insert = new JButton("Deposit");
		ac_create.setBounds(160, 75, 150, 50);
		bal_check.setBounds(160, 150, 150,50);
		wd.setBounds(160, 225, 150, 50);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		insert.setBounds(160, 300, 150, 50);
		f.add(ac_create);
		f.add(bal_check);
		f.add(wd);
		f.add(insert);
		f.setSize(500,500);
		Container c = f.getContentPane();
		c.setBackground(Color.LIGHT_GRAY);
		f.setLayout(null);
		f.setVisible(true);
		ac_create.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Account_Create.execute();
				
			}
		});
		bal_check.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Balance_Check.execute();
				
			}
		});
		wd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Balance_Withdraw.execute();
				
			}
		});
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Balance_Deposit.execute();
				
			}
		});

	}

}
