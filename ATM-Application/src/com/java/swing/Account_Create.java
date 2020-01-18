package com.java.swing;
import java.util.Random;
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


public class Account_Create {
	static JButton btn1;
	JTextField tf;
	public static Connection con = null;
	public static PreparedStatement ps = null;
	static DBConnect dc = new DBConnect();
	static Random rand = new Random();
	public static void execute() {
		Account_Create.main(null);
	}
	private static void main(String[] args) {
		
		JFrame f = new JFrame("A/C Create");
		JLabel name = new JLabel("Name:");
		JLabel mob_no = new JLabel("Mobile No.:");
		JLabel email = new JLabel("Email:");
		JLabel deposit = new JLabel("Deposit");
		JLabel pin = new JLabel("PIN");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField name_tf = new JTextField();
		JTextField mob_no_tf = new JTextField();
		JTextField email_tf = new JTextField();
		JTextField deposit_tf = new JTextField();
		JTextField pin_tf = new JTextField();
		
		name.setBounds(35,50,80,30);
		mob_no.setBounds(35, 100, 80, 30);
		email.setBounds(35,145,300,40);
		deposit.setBounds(35, 200, 50, 30);
		pin.setBounds(35,250, 50, 30);
		
		name_tf.setBounds(100, 50, 200, 30);
		mob_no_tf.setBounds(100, 100, 200, 30);
		email_tf.setBounds(100,150,200,30);
		deposit_tf.setBounds(100, 200, 200, 30);
		pin_tf.setBounds(50, 250, 50, 30);
		
		btn1 = new JButton("Create Account");
		btn1.setBounds(127, 250, 150, 30);
		
		f.add(mob_no);
		f.add(name);
		f.add(deposit);
		f.add(email);
		
		f.add(name_tf);
		f.add(email_tf);
		f.add(mob_no_tf);
		f.add(deposit_tf);
//		f.add(pin);
//		f.add(pin_tf);
		
		f.add(btn1);
		f.setSize(500,500);
		
		Container c = f.getContentPane();
		c.setBackground(Color.LIGHT_GRAY);
		f.setLayout(null);
		f.setVisible(true);
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name =  name_tf.getText();
				String mobile_temp = mob_no_tf.getText();
				long mobile = Long.parseLong(mobile_temp);
				String email = email_tf.getText();
				String deposit_temp = deposit_tf.getText();
				double deposit = Double.parseDouble(deposit_temp);
				int pin = 0;
				while(deposit<5000.0) {
					deposit_temp = JOptionPane.showInputDialog(f, "Balance too low! Please enter opening balance above Rs.5000/-");
					deposit = Double.parseDouble(deposit_temp);
				}
				try {
					con = dc.dbConnect();
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				long ac_no = 0;
				int temp =1;
				try {
					String temp_pin = JOptionPane.showInputDialog(f, "Enter 4 digit PIN");
					pin = Integer.parseInt(temp_pin);
					while (pin <1000 || pin > 9999) {
						temp_pin = JOptionPane.showInputDialog(f, "Enter only 4 digit PIN");
						pin = Integer.parseInt(temp_pin);
					}
					
					do {
						ac_no = rand.nextInt(1000000);
						String selectQuery = "SELECT * FROM account_details where account_no=?";
						ps = con.prepareStatement(selectQuery);
						ps.setLong(1,ac_no);
						ResultSet rs = ps.executeQuery();
						while(rs.next()) {
							temp = 0;
							break;
						}
					} while (temp == 0);
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				try {
					String insertQuery = "INSERT INTO account_details VALUES(?, ?, ?, ?, ?, ?)";
					ps = con.prepareStatement(insertQuery);
					ps.setLong(1, ac_no);
					ps.setInt(2, pin);
					ps.setString(3, name);
					ps.setString(4, email);
					ps.setLong(5, mobile);
					ps.setDouble(6, deposit);
					
					
					int i = ps.executeUpdate();
					if(i>0) {
					
						JOptionPane.showMessageDialog(f, "Account created suceesfully!\nYour new A/C number is "+ac_no+"\nPlease remember your account number and pin.");
					}
					else {
						JOptionPane.showMessageDialog(f, "Error! Account creation unsuceesfull.");
					}
					f.dispose();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});
	}
}

