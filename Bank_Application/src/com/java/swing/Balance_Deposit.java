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

public class Balance_Deposit {
	static JButton btn1;
	JTextField tf;
	public static Connection con = null;
	public static PreparedStatement ps = null;
	static DBConnect dc = new DBConnect();
	public static void execute() {
		Balance_Deposit.main(null);
	}
	private static void main(String[] args) {
		JFrame f = new JFrame("Balance Deposit");
		JLabel ac = new JLabel("Enter A/C No.:");
		ac.setBounds(35,50,110,30);
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
				double withdraw_amount = 0.0;
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
						double avail_balance = rs.getDouble(1);
						String withdraw= JOptionPane.showInputDialog(f, "Enter amount to deposit:");
						withdraw_amount = Double.parseDouble(withdraw);
						avail_balance += withdraw_amount;
						String balance_update = "UPDATE account_details SET balance = ? where account_no=?";
						ps = con.prepareStatement(balance_update);
						ps.setDouble(1, avail_balance);
						ps.setLong(2, ac_no);
						int j = ps.executeUpdate();
						if (j>0) {
							selectQuery = "SELECT balance FROM account_details where account_no=?";
							ps = con.prepareStatement(selectQuery);
							ps.setLong(1, ac_no);
							ResultSet rs_new = ps.executeQuery();

							while(rs_new.next()) {
								JOptionPane.showMessageDialog(f, "Transaction sucessfull! New Balance:Rs. "+rs_new.getDouble(1)+"/-");
								break;
							}
							if(j == 0) {
								JOptionPane.showMessageDialog(f, "Transaction failed!");
							}
						}
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
