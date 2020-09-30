package appsToon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class RegisterForm implements ActionListener {
	//vektor untuk DOB
	private Vector <String> days = new Vector<String>();
	private Vector <String> months = new Vector<String>();
	private Vector <String> years = new Vector<String>();
	
	//Komponen untuk Register Form
	private JFrame fr;
	private JPanel top, middle, bottom, pnlGender, pnlDOB, pnlR,pnlS;
	private JLabel title, lbUsername, lbEmail, lbPassword, lbConfirmPass, lbGender, lbDOB, lbPhoneNumber, lbAddress;
	private JTextField tfUsername, tfEmail, tfPhoneNumber, tfAddress;
	private JPasswordField pfPassword, pfConfirmPass;
	private ButtonGroup bgGender;
	private JRadioButton rbMale, rbFemale;
	private JComboBox <String>cbDay, cbMonth;
	private JComboBox<String> cbYear;
	private JButton btnReset, btnSubmit;
	
	//Komponent connect ke database
	private DBConnect dbCon = new DBConnect();
	private ResultSet rs;
	
	private String UserID="";
	
	public RegisterForm() {
		// TODO Auto-generated constructor stub
		
		dobValue();
		initializeComponent();
		
		fr.setSize(480,510);
		fr.setLocationRelativeTo(null);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		
		//untuk tempelin action listener
		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);
	}
	
	private void dobValue() {
		// TODO Auto-generated method stub
		//days
		days.add("Day");
		for (int d = 1; d < 32; d++) {
			days.add(String.valueOf(d));
		}	
		
		//months
		months.add("Month");
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		
		//years
		years.add("Year");
		for (int y = 1900; y < 2020; y++) {
			years.add(String.valueOf(y));
		}		
	}
	
	private void initializeComponent() {
		// TODO Auto-generated method stub
		
		fr = new JFrame("Apps Toon");
		
		title = new JLabel();
		title.setText("APPS TOON");
		title.setFont(new Font("Arial", Font.BOLD, 18));
		
		lbUsername = new JLabel("Username");
		tfUsername = new JTextField();
		
		lbEmail = new JLabel("Email");
		tfEmail = new JTextField();
		
		lbPassword = new JLabel("Password");
		pfPassword = new JPasswordField();
		
		lbConfirmPass = new JLabel("Confirm Password");
		pfConfirmPass = new JPasswordField();
		
		lbGender = new JLabel("Gender");
		rbMale = new JRadioButton("Male");
		rbFemale =new JRadioButton("Female");
		bgGender = new ButtonGroup();
		bgGender.add(rbMale);
		bgGender.add(rbFemale);
		
		lbDOB = new JLabel("DOB");
		cbDay = new JComboBox<>(days);
		cbMonth = new JComboBox<>(months);
		cbYear = new JComboBox<>(years);
		
		lbPhoneNumber = new JLabel("Phone Number");
		tfPhoneNumber = new JTextField();
		
		lbAddress = new JLabel("Address");
		tfAddress = new JTextField();
		
		btnReset = new JButton("Reset");
		btnSubmit = new JButton("Submit");
		
		pnlGender = new JPanel();
		pnlGender.add(rbMale);
		pnlGender.add(rbFemale);
		
		pnlDOB =new JPanel(new GridLayout(1, 3,10,0));
		pnlDOB.add(cbDay);
		pnlDOB.add(cbMonth);
		pnlDOB.add(cbYear);
		
		pnlR = new JPanel();
		pnlR.add(btnReset);
		pnlS = new JPanel();
		pnlS.add(btnSubmit);
		
		top = new JPanel();
		top.add(title);
		
		middle = new JPanel(new GridLayout(8, 2,0,20));
		middle.add(lbUsername);
		middle.add(tfUsername);
		middle.add(lbEmail);
		middle.add(tfEmail);
		middle.add(lbPassword);
		middle.add(pfPassword);
		middle.add(lbConfirmPass);
		middle.add(pfConfirmPass);
		middle.add(lbGender);
		middle.add(pnlGender);
		middle.add(lbDOB);
		middle.add(pnlDOB);
		middle.add(lbPhoneNumber);
		middle.add(tfPhoneNumber);
		middle.add(lbAddress);
		middle.add(tfAddress);
		
		bottom = new JPanel(new GridLayout(1, 2));
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
		
		String phoneNumber = tfPhoneNumber.getText(),gender = "";;
		String pass = String.valueOf(pfPassword.getPassword());
		String conPass = String.valueOf(pfConfirmPass.getPassword());
		String emailFormat = tfEmail.getText();
		char c;
		boolean cekNum =true;
		boolean cekFormatEmail = true;
		boolean cekHasAlpha = true;
		boolean cekHasNum = true;
		if (e.getSource() == btnSubmit) {
			
			if (!emailFormat.contains("@")||!emailFormat.contains(".")){
				cekFormatEmail = false;
			}
			if (!(emailFormat.indexOf("@") == emailFormat.lastIndexOf("@"))){
				cekFormatEmail = false;
			}
			if (!(emailFormat.indexOf(".") == emailFormat.lastIndexOf("."))){
				cekFormatEmail = false;
			}
			
			for (int i=0;i<pass.length();i++){
				c = pass.charAt(i);
				if(!Character.isLetter(c)){
					cekHasAlpha = false;
				}
				if(!Character.isDigit(c)){
					cekHasNum = false;
				}
			}
			
			for (int i=0;i<phoneNumber.length();i++){
				c = phoneNumber.charAt(i);
				if(!Character.isDigit(c)){
					cekNum = false;
				}
			}
			
			if(tfUsername.getText().isEmpty()){
				JOptionPane.showMessageDialog(fr, "Username field must fill!");
			}else if(tfUsername.getText().length()<5 || tfUsername.getText().length()>20){
				JOptionPane.showMessageDialog(fr, "Username length must between 5 and 20 characters");
			}else if (tfEmail.getText().length()<15){ //jadi min harus 15
				JOptionPane.showMessageDialog(fr, "Email length must more than 14 characters");
			}else if(!tfEmail.getText().endsWith("com")){
				JOptionPane.showMessageDialog(fr, "Email input must end with '.com'");
			}else if(tfEmail.getText().contains("@.")){
				JOptionPane.showMessageDialog(fr, "Email character '@' must not be next to '.'");
			}else if(tfEmail.getText().startsWith("@") || tfEmail.getText().startsWith(".")){
				JOptionPane.showMessageDialog(fr, "Email input must not starts with '@' or '.'");
			}else if(cekFormatEmail == false){
				JOptionPane.showMessageDialog(fr, "Email must not contains more than 1 '@' or '. '");
			}else if(pfPassword.getPassword().length<11){//minimal 11
				JOptionPane.showMessageDialog(fr, "Password length must more than 10 characters");
			}else if(cekHasAlpha == true || cekHasNum == true){
				JOptionPane.showMessageDialog(fr, "Password must alphanumeric");
			}else if(!conPass.equals(pass)){
				JOptionPane.showMessageDialog(fr, "Confirm password must same with password");
			}else if (!rbMale.isSelected() && !rbFemale.isSelected()){
				JOptionPane.showMessageDialog(fr, "One gender must be selected");
			}else if(cbDay.getSelectedIndex()<1){
				JOptionPane.showMessageDialog(fr, "Day must be selected");
			}else if(cbMonth.getSelectedIndex()<1){
				JOptionPane.showMessageDialog(fr, "Month must be selected");
			}else if(cbYear.getSelectedIndex()<1){
				JOptionPane.showMessageDialog(fr, "Year must be selected");
			}else if(cekNum == false){
				JOptionPane.showMessageDialog(fr, "Phone Number must be a numeric");
			}else if(tfPhoneNumber.getText().length() !=12){
				JOptionPane.showMessageDialog(fr, "Phone Number must 12 digits");
			}else if(tfAddress.getText().length()<6 || tfAddress.getText().length()>30){
				JOptionPane.showMessageDialog(fr, "Address length must be between 6 and 30 characters");
			}else if(!tfAddress.getText().endsWith("Street")){
				JOptionPane.showMessageDialog(fr, "Address must ends with 'Street'");
			}else{
				if(rbMale.isSelected()){
					gender = rbMale.getText();
				}else if(rbFemale.isSelected()){
					gender = rbFemale.getText();
				}
				generateUserId();
				try {
					//Buat masukin data User ke DB table User
					dbCon.openCon();
					dbCon.executeUpdate("INSERT INTO User VALUES('"+UserID+"','"+tfUsername.getText()+"','"+tfEmail.getText()+"','"+pass+"','"+gender+"','"+cbYear.getSelectedItem()+"-"+cbMonth.getSelectedIndex()+"-"+cbDay.getSelectedIndex()+"','"+phoneNumber+"','"+tfAddress.getText()+"')");
					dbCon.closeCon();
				} catch (Exception arg0) {
					arg0.printStackTrace();
				}
				JOptionPane.showMessageDialog(fr, "Register Success");
				fr.setVisible(false);
				new LoginForm();
			}
		}else if(e.getSource()==btnReset){
			clearForm();
		}
	}
	
	private void generateUserId() {
		// TODO Auto-generated method stub
		try {
			//Buat UserID bisa increment 1
			dbCon.openCon();
			rs = dbCon.executeQuery("SELECT * FROM User ORDER BY UserID DESC");
			if(rs.next()){
				UserID = rs.getString("UserID").substring(2);
				String id = "" + (Integer.parseInt(UserID)+1);
				String nol ="";
				if(id.length()==1){
					nol = "00";
				}else if(id.length()==2){
					nol = "0";
				}else if(id.length()==3){
					nol = "";
				}
				UserID = "US"+ nol+id;
			}else{
				UserID = "US001";
			}
			dbCon.closeCon();
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}
	}
	
	private void clearForm() {
		// TODO Auto-generated method stub
		tfUsername.setText("");
		tfEmail.setText("");
		pfPassword.setText("");
		pfConfirmPass.setText("");
		bgGender.clearSelection();
		cbDay.setSelectedIndex(0);
		cbMonth.setSelectedIndex(0);
		cbYear.setSelectedIndex(0);
		tfPhoneNumber.setText("");
		tfAddress.setText("");
	}
	
}


//http://uniqosmart.blogspot.com/2016/10/contoh-program-java-membuat-nomor.html