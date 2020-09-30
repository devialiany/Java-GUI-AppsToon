package appsToon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;


public class MainForm implements ActionListener, MouseListener{
	//komponen buat MainForm
	private JFrame fr;
	private JInternalFrame bp,c;
	private JMenuBar userMenuBar, staffMenuBar;
	private JMenu transactionUserMenu, logoutMenuStaff, manageMenu, transactionStaffMenu, logoutMenuUser;
	private JMenuItem buyProduct, viewTransactionUser, logoutUser, logoutStaff, user, product, viewTransactionStaff;
	
	//komponen buat BuyProduct
	private JPanel top, middle, bottom,sideE,sideW,pnl,pnlDesc, pnlLabel;
	private JLabel title, lbId, lbName, lbPrice, lbGenre, lbQuantity, lbRating, phId,phName, phPrice, phGenre, phRating, lbImage;
	private ImageIcon picture;
	private JSpinner sQuantity;
	private SpinnerModel sm;
	private JTable productsTable;
	private JScrollPane scrollPane;
	private JButton btnAdd;
	
	//Komponen buat Cart
	private JLabel lbUserid, lbDate, lbUsername, lbTotalPrice, phUserid, phDate, phUsername, phTotalPrice;
	private JButton btnCheckOut;
	
	private int quan;
	private JDesktopPane view = new JDesktopPane();
	
	private JTable cartTable;
	private JScrollPane sc;
	
	private ViewTransaction vt;
	private DBConnect db = new DBConnect();
	private ResultSet rs;
	
	private String userid;

	private String username;
	
	private Date date;
	
	private String TransactionID;
	
	private void initializeComponent() {
		// TODO Auto-generated method stub
		fr = new JFrame("Main Form");
			//user
		userMenuBar = new JMenuBar();
		
		transactionUserMenu = new JMenu("Transaction");
		logoutMenuUser = new JMenu("Logout");
		
		buyProduct = new JMenuItem("Buy Product");
		viewTransactionUser = new JMenuItem("View Transaction");
		logoutUser = new JMenuItem("Logout");
			
			//staff
		staffMenuBar = new JMenuBar();
		manageMenu = new JMenu("Manage");
		transactionStaffMenu = new JMenu("Transaction");
		logoutMenuStaff = new JMenu("Logout");
		
		user = new JMenuItem("User");
		product = new JMenuItem("Product");
		viewTransactionStaff = new JMenuItem("View Transaction");
		logoutStaff = new JMenuItem("Logout");
		
		userMenuBar.add(transactionUserMenu);
		userMenuBar.add(logoutMenuUser);
		
		transactionUserMenu.add(buyProduct);
		transactionUserMenu.add(viewTransactionUser);
		logoutMenuUser.add(logoutUser);
		
		staffMenuBar.add(manageMenu);
		staffMenuBar.add(transactionStaffMenu);
		staffMenuBar.add(logoutMenuStaff);
		
		manageMenu.add(user);
		manageMenu.add(product);
		transactionStaffMenu.add(viewTransactionStaff);
		logoutMenuStaff.add(logoutStaff);
		
		if(LoginForm.getUserName().equals("devia")||LoginForm.getUserName().equals("kevin")||LoginForm.getUserName().equals("kirana")||LoginForm.getUserName().equals("napoleon")){
			fr.setJMenuBar(staffMenuBar);
		}else{
			fr.setJMenuBar(userMenuBar);
		}
		
		
//		JLabel back = new JLabel();
//		picture = new ImageIcon("src/Gambar/background.jpg");
//		back.setIcon(picture);
//		JPanel background = new JPanel();
//		background.add(back);
//		
//		view.add(background);
		
		
	}
	
	public MainForm() {
		// TODO Auto-generated constructor stub
		initializeComponent();
		
		fr.setContentPane(view);
		fr.setSize(1300,600);
		fr.setLocationRelativeTo(null);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		cartInternalFrame();
		c.setVisible(false);
		
		logoutUser.addActionListener(this);
		logoutStaff.addActionListener(this);
		buyProduct.addActionListener(this);
		viewTransactionStaff.addActionListener(this);
		viewTransactionUser.addActionListener(this);
		product.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == buyProduct){
			bpInternalFrame();
			bp.setVisible(true);
			c.setVisible(false);
		}else if(e.getSource() == viewTransactionStaff){
			vt = new ViewTransaction();
			fr.add(vt);
		}else if(e.getSource() == viewTransactionUser){
			vt = new ViewTransaction();
			fr.add(vt);
		}else if(e.getSource() == logoutStaff){
			fr.setVisible(false);
			new LoginForm();
		}else if(e.getSource() == logoutUser){
			fr.setVisible(false);
			new LoginForm();
		}else if(e.getSource()==btnAdd){
			String qt = String.valueOf(sQuantity.getValue());
			int qty = Integer.parseInt(qt);
			if(qty>quan){
				JOptionPane.showMessageDialog(fr, "Quantity cannot be more than available stock");
			}else if(qty == 0){
				JOptionPane.showMessageDialog(fr, "Quantity minimum is 1");
			}else{
				insertCart();
				loadDataCart();
				c.setVisible(true);
			}
		}else if(e.getSource()== btnCheckOut){
			generateTransactionId();
			try {
				db.openCon();
				db.executeUpdate("INSERT INTO HeaderTransaction VALUES('"+TransactionID+"','"+userid+"', NOW())");
				db.closeCon();
			} catch (Exception arg0) {
				arg0.printStackTrace();
			}
			try {
				db.openCon();
				rs = db.executeQuery("SELECT * FROM Cart c JOIN Product p ON c.ProductID = p.ProductID");
				while(rs.next()){
					int stock = rs.getInt("ProductQuantity");
					int qty = rs.getInt("Qty");
					System.out.println(qty);
					int sisa =stock-qty;
					System.out.println(sisa);
					try {
						db.openCon();
						db.executeUpdate("INSERT INTO DetailTransaction VALUES('"+TransactionID+"','"+rs.getString("ProductID")+"',"+rs.getInt("Qty")+")");
						db.executeUpdate("UPDATE Product SET ProductQuantity = "+sisa+" WHERE ProductID LIKE '"+rs.getString("ProductID")+"'");
					} catch (Exception arg0) {
						arg0.printStackTrace();
					}
				}
				db.executeUpdate("DELETE FROM Cart");
				db.closeCon();
			} catch (Exception arg0) {
				arg0.printStackTrace();
			}
			loadDataProduct();
			JOptionPane.showMessageDialog(fr, "Success Check Out");
			c.setVisible(false);
		}else if(e.getSource() == product){
			ManageProductFrom mp = new ManageProductFrom();
			view.add(mp);
			mp.setVisible(true);
		}
	}



	private void generateTransactionId() {
		// TODO Auto-generated method stub
		try {
			//Buat Transaction ID increment 1
			db.openCon();
			rs = db.executeQuery("SELECT * FROM HeaderTransaction ORDER BY TransactionID DESC");
			if(rs.next()){
				TransactionID = rs.getString("TransactionID").substring(2);
				String id = "" + (Integer.parseInt(TransactionID)+1);
				String nol ="";
				if(id.length()==1){
					nol = "00";
				}else if(id.length()==2){
					nol = "0";
				}else if(id.length()==3){
					nol = "";
				}
				TransactionID = "TR" + nol + id;
			}else{
				TransactionID = "TR001";
			}
			db.closeCon();
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}
	}

	private void cartInternalFrame() {
		// TODO Auto-generated method stub
		c = new JInternalFrame("Cart", false, true, true);
		
		JLabel tCart = new JLabel("Cart");
		tCart.setFont(new Font("Arial", Font.BOLD, 18));
		
		lbUserid = new JLabel("User ID : ");
		lbDate = new JLabel("Date     : ");
		lbUsername = new JLabel("Username    : ");
		lbTotalPrice = new JLabel("Total Price   : ");
		phUserid = new JLabel(" ");
		phDate = new JLabel(" ");
		phUsername = new JLabel(" ");
		phTotalPrice = new JLabel(" ");
		
		JLabel tDetail = new JLabel("Detail");
		tDetail.setFont(new Font("Arial", Font.BOLD, 14));
		
		cartTable = new JTable();
		sc =new JScrollPane(cartTable);
		btnCheckOut =new JButton("Check Out");
		JPanel dt = new JPanel(new BorderLayout());
		JPanel main = new JPanel(new GridLayout(2, 1));
		JPanel pnltc = new JPanel();
		pnltc.add(tCart);
		JPanel pnltd = new JPanel();
		pnltd.add(tDetail);
		
		JPanel info = new JPanel(new GridLayout(2, 4));
		info.add(lbUserid);
		info.add(phUserid);
		info.add(lbUsername);
		info.add(phUsername);
		info.add(lbDate);
		info.add(phDate);
		info.add(lbTotalPrice);
		info.add(phTotalPrice);
		
		dt.add(pnltc, BorderLayout.NORTH);
		dt.add(info,BorderLayout.CENTER);
		dt.add(pnltd,BorderLayout.SOUTH);
		dt.add(new JPanel(), BorderLayout.WEST);
		dt.add(new JPanel(), BorderLayout.EAST);
		
		main.add(dt);
		main.add(sc);
		
		c.add(main, BorderLayout.CENTER);
		c.add(btnCheckOut, BorderLayout.SOUTH);
		c.add(new JPanel(), BorderLayout.EAST);
		c.add(new JPanel(), BorderLayout.WEST);
		
		c.setBounds(645, 0, 641,537);
		c.setVisible(true);
		view.add(c);
		
		btnCheckOut.addActionListener(this);
	}

	private void bpInternalFrame() {
		// TODO Auto-generated method stub
		bp = new JInternalFrame("BuyProduct", false, true, true);
		
		title = new JLabel();
		title.setText("Our Product");
		title.setFont(new Font("Arial", Font.BOLD, 18));
		
		productsTable = new JTable();
		scrollPane = new JScrollPane(productsTable);
		
		lbImage =new JLabel();
		picture = new ImageIcon("src/Gambar/noImageSelected.jpg");
		lbImage.setIcon(picture);
		lbId =new JLabel("ProductID");
		lbName = new JLabel("ProductName");
		lbPrice =new JLabel("Product Price");
		lbGenre =new JLabel("Product Genre");
		lbQuantity= new JLabel("Quantity");
		lbRating = new JLabel("Rating");
		phId= new JLabel("-");
		phName = new JLabel("-");
		phPrice = new JLabel("-");
		phGenre = new JLabel("-");
		sQuantity = new JSpinner();
		sQuantity.setValue(1);
		sQuantity.setEnabled(false);
		phRating = new JLabel("-");
		
		btnAdd = new JButton("Add to Cart");
		btnAdd.setEnabled(false);
		
		top = new JPanel();
		top.add(title);
		
		pnlLabel = new JPanel(new GridLayout(6, 1));
		pnlLabel.add(lbId);
		pnlLabel.add(lbName);
		pnlLabel.add(lbPrice);
		pnlLabel.add(lbGenre);
		pnlLabel.add(lbQuantity);
		pnlLabel.add(lbRating);
		
		pnlDesc = new JPanel(new GridLayout(6, 1));
		pnlDesc.add(phId);
		pnlDesc.add(phName);
		pnlDesc.add(phPrice);
		pnlDesc.add(phGenre);
		pnlDesc.add(sQuantity);
		pnlDesc.add(phRating);
		
		pnl = new JPanel(new GridLayout(1, 3));
		pnl.add(lbImage);
		pnl.add(pnlLabel);
		pnl.add(pnlDesc);
		
		middle = new JPanel(new GridLayout(2, 1));
		middle.add(scrollPane);
		middle.add(pnl);
		
		bottom = new JPanel();
		bottom.add(btnAdd);
		
		sideE= new JPanel();
		
		sideW= new JPanel();
		
		//tempelin ke JInternalFrame
		bp.add(top, BorderLayout.NORTH);
		bp.add(middle,BorderLayout.CENTER);
		bp.add(bottom, BorderLayout.SOUTH);
		bp.add(sideE, BorderLayout.EAST);
		bp.add(sideW, BorderLayout.WEST);
		
		loadDataProduct();
		bp.setSize(645,537);
		bp.setVisible(true);
		view.add(bp);
		productsTable.addMouseListener(this);
		btnAdd.addActionListener(this);
	}
	
	private void insertCart() {
		// TODO Auto-generated method stub
		date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			db.openCon();
			//Buat ambil data user
			rs = db.executeQuery("SELECT UserID, UserName FROM User WHERE UserName LIKE'"+LoginForm.getUserName()+"'");
			if(rs.next()){
				userid = rs.getString("UserID");
				username = rs.getString("UserName");
			}
			//Buat masukin data ke cart
			db.executeUpdate("INSERT INTO Cart VALUES('"+userid+"','"+phId.getText()+"',"+sQuantity.getValue()+")");
			db.closeCon();
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}
		phUserid.setText(userid);
		phDate.setText(dateFormat.format(date));
		phUsername.setText(username);
	}

	private void loadDataCart() {
		// TODO Auto-generated method stub
		String tPrice = "";
		try {
			db.openCon();
			rs = db.executeQuery("SELECT p.ProductID, ProductName, ProductPrice, Qty FROM Cart c JOIN Product p ON C.ProductID = p.ProductID WHERE UserID LIKE '"+userid+"'");
			Vector <Vector<Object>> datas = new Vector<Vector<Object>>();
			Vector <String> columnHeaders = new Vector<String>();
			columnHeaders.add("ProductID");
			columnHeaders.add("ProductName");
			columnHeaders.add("ProductPrice");
			columnHeaders.add("Qty");
			while (rs.next()) {
				Vector<Object> rows = new Vector<Object>();
					
				String ProductID = rs.getString("ProductID");
				String ProductName = rs.getString("ProductName");
				int ProductPrice = rs.getInt("ProductPrice");
				int Qty= rs.getInt("Qty");
					
				rows.add(ProductID);
				rows.add(ProductName);
				rows.add(ProductPrice);
				rows.add(Qty);
				datas.add(rows);
					
					//masukin database tabel Sales ke JTable
				DefaultTableModel dtm = new DefaultTableModel(datas,columnHeaders);
				cartTable.setModel(dtm);
			}
			rs = db.executeQuery("SELECT SUM(ProductPrice) AS TotalPrice FROM Cart c JOIN Product p ON c.ProductID=p.ProductID JOIN User u ON u.UserID=c.UserID WHERE UserName LIKE'"+LoginForm.getUserName()+"'");
			if(rs.next()){
				tPrice = rs.getString("TotalPrice");
			}
			db.closeCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
		phTotalPrice.setText(tPrice);
	}

	private void loadDataProduct() {
		// TODO Auto-generated method stub
		try {
			db.openCon();
			rs = db.executeQuery("SELECT ProductID, GenreName,ProductName,ProductPrice,ProductQuantity,ProductImage,ProductRating FROM Product p JOIN Genre g ON p.GenreId = g.GenreId");
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
			db.closeCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		btnAdd.setEnabled(true);
		int selectedRow = productsTable.getSelectedRow();
		String id = productsTable.getModel().getValueAt(selectedRow, 0).toString();
		String genre = productsTable.getModel().getValueAt(selectedRow, 1).toString();
		String name = productsTable.getModel().getValueAt(selectedRow, 2).toString();
		String price  = productsTable.getModel().getValueAt(selectedRow, 3).toString();
		String quantity = productsTable.getModel().getValueAt(selectedRow, 4).toString();
		String image  = productsTable.getModel().getValueAt(selectedRow, 5).toString();
		String rating = productsTable.getModel().getValueAt(selectedRow, 6).toString();
		quan = Integer.parseInt(quantity);
		picture = new ImageIcon("src/Gambar/"+image+"");
		lbImage.setIcon(picture);
		phId.setText(id);
		phName.setText(name);
		phPrice.setText(price);
		phGenre.setText(genre);
		sQuantity.setEnabled(true);
		sm = new SpinnerNumberModel(1, 0, null, 1);
		sQuantity.setModel(sm);
		phRating.setText(rating);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
