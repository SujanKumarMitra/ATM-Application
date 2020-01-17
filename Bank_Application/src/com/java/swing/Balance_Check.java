package com.java.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Balance_Check {
	static JButton btn1;
	JTextField tf;
	public static Connection con = null;
	public static PreparedStatement ps = null;
	static DBConnect dc = new DBConnect();
	public static void execute() {
		Balance_Check.main(null);
	}
	private static void main(String[] args) {
		JFrame f = new JFrame("Balance Check");
		JLabel ac = new JLabel("Enter A/C No.:");
		ac.setBounds(35,50,110,30);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel pin = new JLabel("Enter PIN:");
		pin.setBounds(35, 100, 110, 30);
		JTextField ac_tf = new JTextField();
		JTextField pin_tf = new JTextField();
		ac_tf.setBounds(120, 50, 200, 30);
		pin_tf.setBounds(120, 100, 50, 30);
		f.add(ac);
		f.add(ac_tf);
		f.add(pin);
		f.add(pin_tf);
		btn1 = new JButton("Enter");
		btn1.setBounds(185, 100, 100, 30);
		f.add(btn1);
		f.setSize(500,500);
		Container c = f.getContentPane();
		c.setBackground(Color.LIGHT_GRAY);
		f.setLayout(null);
		f.setVisible(true);
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int i=0;
				String ac_no_temp = ac_tf.getText();
				long ac_no = Long.parseLong(ac_no_temp);
				String pin_temp = pin_tf.getText();
				int pin = Integer.parseInt(pin_temp);
				try {
					con = dc.dbConnect();
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					String selectQuery = "SELECT balance FROM account_details where account_no=? and pin=?";
					ps = con.prepareStatement(selectQuery);
					ps.setLong(1, ac_no);
					ps.setInt(2, pin);
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						JOptionPane.showMessageDialog(f, "Your remaining balance is Rs."+rs.getDouble(1)+"/-");
						i++;
					}
					if(i==0) {
						JOptionPane.showMessageDialog(f, "Invalid Login Details");
					}
					f.dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		});
		

	}
	
}
