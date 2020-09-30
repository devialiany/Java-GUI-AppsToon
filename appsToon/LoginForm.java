package appsToon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginForm implements ActionListener {
	
	public static String userName;
	
	//Komponen untuk form login
	private JFrame fr;
	private JPanel top, middle, bottom, pnlR, pnlS;
	private JLabel title,usernameLabel,passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton btnRegister, btnSubmit;
	
	private DBConnect dbCon = new DBConnect();
	private ResultSet rs;

	public LoginForm() {
		// TODO Auto-generated constructor stub
		
		initializeComponent();
		
		fr.setSize(400, 170);
		fr.setLocationRelativeTo(null);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		
		//tempel action listener
		btnRegister.addActionListener(this);
		btnSubmit.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new LoginForm();
	}

	private void initializeComponent() {
		// TODO Auto-generated method stub
		fr = new JFrame("Apps Toon");
		
		title = new JLabel();
		title.setText("Login");
		title.setFont(new Font("Arial", Font.BOLD, 18));
		
		usernameLabel = new JLabel("Username");
		usernameField = new JTextField();
		
		passwordLabel = new JLabel("Password");
		passwordField = new JPasswordField();
		
		btnRegister = new JButton("Register");
		btnSubmit = new JButton("Submit");
		
		top = new JPanel();
		top.add(title);
		
		middle = new JPanel(new GridLayout(2,2,10,10));
		middle.add(usernameLabel);
		middle.add(usernameField);
		middle.add(passwordLabel);
		middle.add(passwordField);
		
		bottom = new JPanel(new GridLayout(1, 2));
		pnlR = new JPanel();
		pnlR.add(btnRegister);
		pnlS = new JPanel();
		pnlS.add(btnSubmit);
		bottom.add(pnlR);
		bottom.add(pnlS);
		
		//tempelin JPanel ke JFrame
		fr.add(top, BorderLayout.NORTH);
		fr.add(middle,BorderLayout.CENTER);
		fr.add(bottom, BorderLayout.SOUTH);
		fr.add(new JPanel(), BorderLayout.EAST);
		fr.add(new JPanel(), BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String pass = String.valueOf(passwordField.getPassword());
		
		if(e.getSource() == btnRegister){
			fr.setVisible(false);
			new RegisterForm();
		}else if(e.getSource() == btnSubmit){
			if(usernameField.getText().isEmpty()){
				JOptionPane.showMessageDialog(fr, "Username Field must be filled");
			}else if(pass.isEmpty()){
				JOptionPane.showMessageDialog(fr, "Password Field must be filled");
			}else{
				try {
					dbCon.openCon();
					rs = dbCon.executeQuery("SELECT * FROM User WHERE UserName LIKE '"+usernameField.getText()+"' AND UserPassword LIKE '"+pass+"'");
					if(rs.next()){
						if(usernameField.getText().equals(rs.getString("UserName")) && pass.equals(rs.getString("UserPassword")) ){
							JOptionPane.showMessageDialog(fr, "Login Sucess");
							userName = rs.getString("UserName");
							fr.setVisible(false);
							new MainForm();
						}
					}else{
						JOptionPane.showMessageDialog(fr, "Inputted username and password is invalid");
					}
					dbCon.closeCon();
					
				} catch (Exception arg0) {
					arg0.printStackTrace();
				}
				
//				fr.setVisible(false);
//				new MainForm();
			}
		}
	}
	
	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		LoginForm.userName = userName;
	}

}

