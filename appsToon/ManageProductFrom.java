package appsToon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class ManageProductFrom extends JInternalFrame implements ActionListener,MouseListener{
	private Vector<String> genreIDs = new Vector<String>();
	private Vector<String> genreNames = new Vector<String>();
	
	private JPanel top, middle;
	private JLabel title, lbId, lbName, lbPrice, lbRating, lbStock, lbGenre, lbImage, lbPic;
	private JTextField phId, phName, phPrice, phRating, phImage;
	private JSpinner sStock;
	private SpinnerModel sm;
	private JComboBox<String> cbGenre = new JComboBox<String>(genreNames);
	private JButton btnChoose, btnDelete,btnUpdate, btnInsert, btnSubmit, btnCancel;
	private JTable productsTable;
	private JScrollPane sc;
	private ImageIcon picture;
	private JFileChooser fc = new JFileChooser("src/Gambar/");
	
	private File picFile;
	
	private DBConnect dbCon = new DBConnect();
	private ResultSet rs;
	
	private String ProductID;
	private String GenreID;
	
	public ManageProductFrom() {
		// TODO Auto-generated constructor stub
		genreIDs.add("");
		genreNames.add("Genre");
		try {
			dbCon.openCon();
			rs = dbCon.executeQuery("SELECT * FROM Genre");
			while(rs.next()){
				genreIDs.add(rs.getString("GenreID"));
				genreNames.add(rs.getString("GenreName"));
			}
			dbCon.closeCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeComponent();
		loadDataProduct();
		setSize(800,500);
		setTitle("Manage Product");
		setClosable(true);
		setMaximizable(true);
		setVisible(true);
		
		productsTable.addMouseListener(this);
		btnInsert.addActionListener(this);
		btnSubmit.addActionListener(this);
		btnChoose.addActionListener(this);
		btnCancel.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
	}

	private void loadDataProduct() {
		// TODO Auto-generated method stub
		try {
			dbCon.openCon();
			rs = dbCon.executeQuery("SELECT ProductID, GenreName,ProductName,ProductPrice,ProductQuantity,ProductImage,ProductRating FROM Product p JOIN Genre g ON p.GenreId = g.GenreId");
			Vector <Vector<Object>> datas = new Vector<Vector<Object>>();
			Vector <String> columnHeaders = new Vector<String>();
			columnHeaders.add("ProductID");
			columnHeaders.add("GenreName");
			columnHeaders.add("ProductName");
			columnHeaders.add("ProductPrice");
			columnHeaders.add("ProductQuantity");
			columnHeaders.add("ProductImage");
			columnHeaders.add("ProductRating");
			while (rs.next()) {
				Vector<Object> rows = new Vector<Object>();
					
				String ProductID = rs.getString("ProductID");
				String GenreName = rs.getString("GenreName");
				String ProductName = rs.getString("ProductName");
				int ProductPrice = rs.getInt("ProductPrice");
				int ProductQuantity = rs.getInt("ProductQuantity");
				String ProductImage = rs.getString("ProductImage");
				int ProductRating = rs.getInt("ProductRating");
					
				rows.add(ProductID);
				rows.add(GenreName);
				rows.add(ProductName);
				rows.add(ProductPrice);
				rows.add(ProductQuantity);
				rows.add(ProductImage);
				rows.add(ProductRating);
				datas.add(rows);
					
					//masukin database tabel Sales ke JTable
				DefaultTableModel dtm = new DefaultTableModel(datas,columnHeaders);
				productsTable.setModel(dtm);
			}
			dbCon.closeCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeComponent() {
		// TODO Auto-generated method stub
		title = new JLabel("ProductList");
		title.setFont(new Font("Arial", Font.BOLD, 20));
		
		productsTable = new JTable();
		sc = new JScrollPane(productsTable);
		
		lbId = new JLabel("ProductID");
		lbName= new JLabel("Product Name");
		lbPrice= new JLabel("Product Price");
		lbRating= new JLabel("Product Rating ");
		lbStock= new JLabel("Product Stock");
		lbGenre = new JLabel("Product Genre");
		lbImage= new JLabel("Product Image");
		lbPic = new JLabel();
		picture = new ImageIcon("src/Gambar/noImageSelected.jpg");
		lbPic.setIcon(picture);
		
		phId = new JTextField();
		phName = new JTextField();
		phPrice = new JTextField();
		phRating = new JTextField();
		phImage = new JTextField();
		
		sStock = new JSpinner();
		sStock.setValue(1);
	
		
		btnChoose = new JButton("Choose");
		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnSubmit = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		
		top =new JPanel();
		top.add(title);
		
		
		JPanel pnImg = new JPanel(new GridLayout(1, 2));
		pnImg.add(phImage);
		pnImg.add(btnChoose);
		
		JPanel lbform = new JPanel(new GridLayout(7, 1));
		lbform.add(lbId);
		lbform.add(lbName);
		lbform.add(lbPrice);
		lbform.add(lbRating);
		lbform.add(lbStock);
		lbform.add(lbGenre);
		lbform.add(lbImage);
		
		JPanel phform = new JPanel(new GridLayout(7, 1));
		phform.add(phId);
		phform.add(phName);
		phform.add(phPrice);
		phform.add(phRating);
		phform.add(sStock);
		phform.add(cbGenre);
		phform.add(pnImg);
		
		JPanel pnlButton = new JPanel(new GridLayout(5, 1,5,5));
		pnlButton.add(btnInsert);
		pnlButton.add(btnUpdate);
		pnlButton.add(btnDelete);
		pnlButton.add(btnSubmit);
		pnlButton.add(btnCancel);
		
		JPanel pnlB= new JPanel(new GridLayout(1, 4,5,5));
		pnlB.add(lbPic);
		pnlB.add(lbform);
		pnlB.add(phform);
		pnlB.add(pnlButton);
		
		
		middle = new JPanel(new GridLayout(2, 1, 5,5));
		middle.add(sc);
		middle.add(pnlB);
		
		add(top, BorderLayout.NORTH);
		add(middle, BorderLayout.CENTER);
		add(new JPanel(), BorderLayout.SOUTH);
		add(new JPanel(), BorderLayout.WEST);
		add(new JPanel(), BorderLayout.EAST);
		
		phId.setEnabled(false);
		phName.setEnabled(false);
		phPrice.setEnabled(false);
		phRating.setEnabled(false);
		cbGenre.setEnabled(false);
		sStock.setEnabled(false);
		phImage.setEnabled(false);
		btnChoose.setEnabled(false);
		btnSubmit.setEnabled(false);
		btnCancel.setEnabled(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int selectedRow = productsTable.getSelectedRow();
		String id = productsTable.getModel().getValueAt(selectedRow, 0).toString();
		String genre = productsTable.getModel().getValueAt(selectedRow, 1).toString();
		String name = productsTable.getModel().getValueAt(selectedRow, 2).toString();
		String price = productsTable.getModel().getValueAt(selectedRow, 3).toString();
		String quantity = productsTable.getModel().getValueAt(selectedRow, 4).toString();
		String image  = productsTable.getModel().getValueAt(selectedRow, 5).toString();
		String rating = productsTable.getModel().getValueAt(selectedRow, 6).toString();
		int stock = Integer.parseInt(quantity);
		picture = new ImageIcon("src/Gambar/"+image+"");
		lbPic.setIcon(picture);
		phId.setText(id);
		phName.setText(name);
		phPrice.setText(price);
		if(genre.equals("Romance")){
			cbGenre.setSelectedIndex(1);
		}else if(genre.equals("Drama")){
			cbGenre.setSelectedIndex(2);
		}else if(genre.equals("Fantasy")){
			cbGenre.setSelectedIndex(3);
		}else if(genre.equals("Comedy")){
			cbGenre.setSelectedIndex(4);
		}else if(genre.equals("Action")){
			cbGenre.setSelectedIndex(5);
		}else if(genre.equals("Horror")){
			cbGenre.setSelectedIndex(6);
		}else if(genre.equals("Slice of Life")){
			cbGenre.setSelectedIndex(7);
		}
		sStock.setValue(stock);
		phRating.setText(rating);
		phImage.setText(image);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		String productImg;
		if(a.getSource() == btnChoose){
			fc.showOpenDialog(this);
			picFile = fc.getSelectedFile();
			productImg = fc.getName(picFile);
			System.out.println(productImg);
			picture = new ImageIcon(productImg);
			lbPic.setIcon(picture);
			phImage.setText(productImg);
			
		}else if (a.getSource() == btnInsert) {
			generateProductId();
			phId.setText(ProductID);
			phName.setEnabled(true);
			phPrice.setEnabled(true);
			phRating.setEnabled(true);
			sStock.setEnabled(true);
			cbGenre.setEnabled(true);
			btnChoose.setEnabled(true);
			btnSubmit.setEnabled(true);
			btnCancel.setEnabled(true);
			btnInsert.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
			phName.setText("");
			phPrice.setText("");
			phRating.setText("");
			
			sm = new SpinnerNumberModel(0, 0, null, 1);
			sStock.setModel(sm);
		}else if(a.getSource() == btnUpdate){
			if(productsTable.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "You must select the data on the table");
			}else{
				phId.setEnabled(true);
				phName.setEnabled(true);
				phPrice.setEnabled(true);
				phRating.setEnabled(true);
				sStock.setEnabled(true);
				cbGenre.setEnabled(true);
				phImage.setEnabled(true);
				btnChoose.setEnabled(true);
				btnSubmit.setEnabled(true);
				btnCancel.setEnabled(true);
				btnInsert.setEnabled(false);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		}else if(a.getSource() == btnDelete){
			if(productsTable.getSelectedRow()<0){
				JOptionPane.showMessageDialog(this, "You must select the data on the table");
			}else{
				try {
					dbCon.openCon();
					dbCon.executeUpdate("DELETE FROM Product WHERE ProductID LIKE '"+phId.getText()+"'");
					dbCon.closeCon();
				} catch (Exception arg0) {
					arg0.printStackTrace();
					
				}
				loadDataProduct();
				JOptionPane.showMessageDialog(this, "Delete Success");
				loadDataProduct();
			}
		}else if(a.getSource() == btnSubmit){
			String st = String.valueOf(sStock.getValue());
			boolean cekNum =true;
			char c;
			String price = phPrice.getText();
			for (int i=0;i<price.length();i++){
				c = price.charAt(i);
				if(!Character.isDigit(c)){
					cekNum = false;
				}
			}
			int stk = Integer.parseInt(st);
			if(phName.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Product Name must fill");
			}else if(phPrice.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Product Price must fill");
			}else if(cekNum == false){
				JOptionPane.showMessageDialog(this, "Product Price must be numeric");
			}else if(phRating.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Product Rating must fill");
			}else if(stk == 0){
				JOptionPane.showMessageDialog(this, "Minimum Product Stock is 1");
			}else if(cbGenre.getSelectedIndex() < 1){
				JOptionPane.showMessageDialog(this, "Product Genre must fill");
			}else if(phImage.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Product Image must fill");
			}else{
				if(phId.isEnabled()==false){
					try {
						dbCon.openCon();
						dbCon.executeUpdate("INSERT INTO Product VALUES('"+phId.getText()+"','"+genreIDs.get(cbGenre.getSelectedIndex())+"','"+phName.getText()+"',"+phPrice.getText()+","+stk+",'"+phImage.getText()+"',"+phRating.getText()+")");
						dbCon.closeCon();
					} catch (Exception arg0) {
						arg0.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "Submit Success");
					loadDataProduct();
				}else{
					try {
						//Buat masukin data Product
						dbCon.openCon();
						dbCon.executeUpdate("UPDATE Product SET GenreID='"+genreIDs.get(cbGenre.getSelectedIndex())+"',ProductName='"+phName.getText()+"',ProductPrice="+phPrice.getText()+",ProductQuantity="+stk+",ProductImage='"+phImage.getText()+"',ProductRating= "+phRating.getText()+" WHERE ProductID = '"+phId.getText()+"'");
						dbCon.closeCon();
					} catch (Exception arg0) {
						arg0.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "Update Success");
					loadDataProduct();
				}
				refreshData();
				
			}
		}else if(a.getSource() == btnCancel){
			refreshData();
		}
	}
	
	private void refreshData() {
		// TODO Auto-generated method stub
		phId.setEnabled(false);
		phName.setEnabled(false);
		phPrice.setEnabled(false);
		phRating.setEnabled(false);
		cbGenre.setEnabled(false);
		sStock.setEnabled(false);
		phImage.setEnabled(false);
		btnChoose.setEnabled(false);
		btnSubmit.setEnabled(false);
		btnCancel.setEnabled(false);
		btnInsert.setEnabled(true);
		btnUpdate.setEnabled(true);
		btnDelete.setEnabled(true);
		phId.setText("");
		phName.setText("");
		phPrice.setText("");
		phRating.setText("");
		sStock.setValue(1);
		cbGenre.setSelectedIndex(0);
		phImage.setText("");
		picture = new ImageIcon("src/Gambar/noImageSelected.jpg");
		lbPic.setIcon(picture);
	}

	private void generateProductId() {
		// TODO Auto-generated method stub
		try {
			//Buat UserID bisa increment 1
			dbCon.openCon();
			rs = dbCon.executeQuery("SELECT * FROM Product ORDER BY ProductID DESC");
			if(rs.next()){
				ProductID = rs.getString("ProductID").substring(2);
				String id = "" + (Integer.parseInt(ProductID)+1);
				String nol ="";
				if(id.length()==1){
					nol = "00";
				}else if(id.length()==2){
					nol = "0";
				}else if(id.length()==3){
					nol = "";
				}
				ProductID = "PD"+ nol+id;
			}else{
				ProductID = "PD001";
			}
			dbCon.closeCon();
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String getGenreID() {
		return GenreID;
	}

	public void setGenreID(String genreID) {
		GenreID = genreID;
	}

	

}
