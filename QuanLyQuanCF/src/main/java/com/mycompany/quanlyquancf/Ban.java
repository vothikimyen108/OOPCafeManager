package com.mycompany.quanlyquancf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Ban {
	public static int dem = 0;
	private String maBan;
	{
		dem++;
		try {
			this.setMaBan("");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	private int sucChua;

	private int tinhTrang;

	public Ban() {

	}

	public Ban(int sc, int tt) {
		this.sucChua = sc;
		this.setTinhTrang(tt);
		//this.tinhTrang = tt;
	}

	@Override
	public String toString() {
		return "Mã bàn: " + this.getMaBan() + " sức chứa: " + this.getSucChua() + " Tinh trạng: " + this.getTinhTrang();
	}

	public void nhapBan(Scanner scanner) {
		System.out.println("nhập thông tin bàn có mã: " + this.getMaBan());
		System.out.print("Sức chưa: ");
		this.setSucChua(scanner.nextInt());
		this.setTinhTrang(0);
	}

	public int getTinhTrang() {
		return tinhTrang;
	}

	public void setTinhTrang(int tinhTrang) {
		if (tinhTrang ==0 || tinhTrang==1) {
			this.tinhTrang= tinhTrang;
			
		  }
		  else {
			  System.out.println("sai tình trạng của bàn");
			throw new ArithmeticException("sai tình trạng của bàn");
		  }
	}

	public int getSucChua() {
		return sucChua;
	}

	public void setSucChua(int sucChua) {
		this.sucChua = sucChua;
	}

	public String getMaBan() {

		return maBan;
	}

	public void setMaBan(String maBan) throws ClassNotFoundException, SQLException {
		this.maBan = maBan;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from ban");
		StringBuilder sb = new StringBuilder();	
		String maBanSQl="";
		int sodong = 0;
		boolean flag = true;
		while(rs.next()){
			sodong++;
		   maBanSQl = rs.getString("id");
		   //kiểm tra mã bàn truyền vào nếu có trong bản thì flag bằng flase tìm thấy không cần tạo mã mới
		   if(maBanSQl.equalsIgnoreCase(maBan)) {
			this.maBan = maBan;
			flag = false;
		  }
		}
		if(flag){//tạo mã mới
		if(sodong==0){
			sb.append("B00").append(dem);
			this.maBan = sb.toString();
		}else {
			//xử lí chuổi để lấy mã
			String maSoSQl = maBanSQl.substring(1);
			//chuyển thành số
			int id = Integer.parseInt(maSoSQl);
			int maID = id+dem;
			if(maID<10){
				sb.append("B00").append(maID);
			}
			if(maID>=10) {
				sb.append("B0").append(maID);
			}
			if(maID>=100) {
				sb.append("B").append(maID);
			}
			this.maBan = sb.toString();
		}
	}
		
	}

}
