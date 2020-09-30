package appsToon;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewTransaction extends JInternalFrame implements MouseListener{
	private JPanel j1,j2,atas,bawah,main;
	private JLabel lbTl, lbTd;
	private JTable listTable, detailTable;
	private JScrollPane scList,scDetail;
	
	private DBConnect dbCon = new DBConnect();
	private ResultSet rs;
	
	private String userid;
	private String TransactionID;

	private void loadDataList() {
		// TODO Auto-generated method stub
		
		
		try {
			dbCon.openCon();
			if(LoginForm.getUserName().equals("devia")||LoginForm.getUserName().equals("kevin")||LoginForm.getUserName().equals("kirana")||LoginForm.getUserName().equals("napoleon")){
				rs = dbCon.executeQuery("SELECT * FROM headertransaction");
			}else{
				rs = dbCon.executeQuery("SELECT * FROM headertransaction WHERE UserID LIKE '"+userid+"'");
			}
			Vector <Vector<Object>> datas = new Vector<Vector<Object>>();
			Vector <String> columnHeaders = new Vector<String>();
			columnHeaders.add("TransactionID");
			columnHeaders.add("UserID");
			columnHeaders.add("Transaction Date");
			while (rs.next()) {
				Vector<Object> rows = new Vector<Object>();
					
				String id = rs.getString("TransactionID");
				String user = rs.getString("UserID");
				String date = rs.getString("TransactionDate");
				rows.add(id);
				rows.add(user);
				rows.add(date);
				datas.add(rows);
					
					//masukin database tabel Sales ke JTable
				DefaultTableModel dtm = new DefaultTableModel(datas,columnHeaders);
				listTable.setModel(dtm);
			}
			dbCon.closeCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeComponent() {
		// TODO Auto-generated method stub
		lbTl = new JLabel("Transaction List");
		lbTl.setFont(new Font("Arial", Font.BOLD, 18));
		listTable = new JTable();
		scList = new JScrollPane(listTable);
		
		lbTd = new JLabel("Transaction Detail");
		lbTd.setFont(new Font("Arial", Font.BOLD, 18));
		detailTable = new JTable();
		scDetail = new JScrollPane(detailTable);
		j1 =new JPanel();
		j2 = new JPanel();
		atas = new JPanel(new BorderLayout(40,0));
		bawah = new JPanel(new BorderLayout(40,0));
		main = new JPanel(new GridLayout(2, 1));
		
		j1.add(lbTl);
		j2.add(lbTd);
		
		atas.add(j1,BorderLayout.NORTH);
		atas.add(scList,BorderLayout.CENTER);
		
		bawah.add(j2,BorderLayout.NORTH);
		bawah.add(scDetail,BorderLayout.CENTER);
		
		main.add(atas);
		main.add(bawah);
		this.add(main);
		
	}
	
	public ViewTransaction() {
		// TODO Auto-generated constructor stub
		lihatDatabase();
		initializeComponent();
		loadDataList();
		//loadDataDetail();
		setTitle("View Transaction");
		setResizable(false);
		setClosable(true);
		setMaximizable(true);
		setSize(1285,537);
		setVisible(true);
		listTable.addMouseListener(this);
	}

	private void lihatDatabase() {
		// TODO Auto-generated method stub
		try {
			dbCon.openCon();
			//Buat ambil data user
			rs = dbCon.executeQuery("SELECT UserID, UserName FROM User WHERE UserName LIKE'"+LoginForm.getUserName()+"'");
			if(rs.next()){
				userid = rs.getString("UserID");
			}
		} catch (Exception arg0) {
			arg0.printStackTrace();
		}
	}

	private void loadDataDetail() {
		// TODO Auto-generated method stub
		try {
			dbCon.openCon();
			rs = dbCon.executeQuery("SELECT * FROM DetailTransaction WHERE TransactionID LIKE '"+TransactionID+"'");
			Vector <Vector<Object>> datas = new Vector<Vector<Object>>();
			Vector <String> columnHeaders = new Vector<String>();
			columnHeaders.add("TransactionID");
			columnHeaders.add("ProductID");
			columnHeaders.add("Qty");
			while (rs.next()) {
				Vector<Object> rows = new Vector<Object>();
					
				String id = rs.getString("TransactionID");
				String product = rs.getString("ProductID");
				int qty = rs.getInt("Qty");
				rows.add(id);
				rows.add(product);
				rows.add(qty);
				datas.add(rows);
					
					//masukin database tabel Sales ke JTable
				DefaultTableModel dtm = new DefaultTableModel(datas,columnHeaders);
				detailTable.setModel(dtm);
			}
			dbCon.closeCon();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		int selectedRow = listTable.getSelectedRow();
		TransactionID = listTable.getModel().getValueAt(selectedRow, 0).toString();
		loadDataDetail();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}