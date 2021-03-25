package com.mycompany.quanlyquancf;

import java.util.List;
import java.io.EOFException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class QuanLyBan {

	private List<Ban> dsBan;
	public static Scanner scanner = new Scanner(System.in);
	public static Connection conn;
	public boolean themBan(Ban b,Connection conn) throws SQLException {
		Statement stm = conn.createStatement();
		String sql = "INSERT INTO ban (id,succhua,tinhtrang)";
		String id = b.getMaBan();
		int succhua = b.getSucChua();
		int tinhTrang = b.getTinhTrang();
		String vl = "VALUES ('" + id + "',' " + succhua + "',' " + tinhTrang +  "');";
		int k = stm.executeUpdate(sql + vl);
		if (k != 0)
			return true;
		return false;
	}
	public List<Ban> getDsBan() {
		return dsBan;
	}

	public void setDsBan(List<Ban> dsBan) {
		this.dsBan = dsBan;
	}
	public String timKiemIDBan(Connection conn,String maBan) throws SQLException {
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from ban where id='"+maBan+"'");	
		boolean flag = false;String maBanSQl ="";
		while(rs.next()){
		 maBanSQl  = rs.getString("id");
			flag = true;
		}
		if(flag)
			maBan = maBanSQl;
			else
			maBan = null;
		return maBan;
	}
	public Ban timKiemBanTrong(Connection conn) throws SQLException, EOFException, ClassNotFoundException {
		Ban banTrong = new Ban();
		System.out.println("Nhập mã bàn cần tìm: ");
		String mb = scanner.nextLine();
		String maBan = this.timKiemIDBan(conn,mb);
		boolean flag = true;
		if(maBan!=null) {
			Statement  stm = conn.createStatement(); 
		ResultSet rs = stm.executeQuery("Select * from ban where id='"+maBan+"' and tinhtrang ='"+0+"'");
		try {
			banTrong.setMaBan(maBan);
			while(rs.next()){
				banTrong.setSucChua(rs.getInt("succhua"));
				banTrong.setTinhTrang(rs.getInt("tinhtrang"));
				flag= false;
			}
		} catch (Exception e) {
			throw new EOFException("error");
		}
		}
	else {
		System.out.print("Không thấy mã bàn");
		//throw new EOFException("không tìm thấy mã bàn");
	}
	if(flag)
	   banTrong = null;
	return  banTrong;
	}
	public void timKiemTheoSC(Connection conn) throws SQLException {
		System.out.print("Nhập sức chứa để thực hiện chức năng: ");
		int idSC = scanner.nextInt();
		String sql = "Select * from ban where succhua >='"+idSC+"'";
		String leftAlignFormat = "| %-5s | %-4s | %-3s|\n";
		System.out.println("=====DANH SÁCH BÀN TRỐNG===========");
		System.out.format("+--------------------------------+%n");
		System.out.printf(leftAlignFormat, "Mã bàn", "Sức chứa", "tình trạng");
		System.out.format("+--------------------------------+%n");
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);//trả về resultet
		int dem = 0;																								
		while (rs.next()) {
			String id= rs.getString("id");
			int succhua = rs.getInt("succhua");
			int tinhtrang = rs.getInt("tinhtrang");
			if(tinhtrang==0)
				System.out.printf("| %-6s | %-8d | %-10s|\n", id, succhua, "trống" );
			else
				System.out.printf("| %-6s | %-8d | %-10s|\n", id, succhua, "Không" );
			dem++;
		}
		if (dem == 0)
			System.out.println("Danh sách rỗng");
	}
	public void hienThiBanTrong(Connection conn) throws SQLException {
		String sql = "Select * from ban";
		String leftAlignFormat = "| %-5s | %-4s | %-3s|\n";
		System.out.println("=====DANH SÁCH BÀN TRỐNG===========");
		System.out.format("+--------------------------------+%n");
		System.out.printf(leftAlignFormat, "Mã bàn", "Sức chứa", "tình trạng");
		System.out.format("+--------------------------------+%n");
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);//trả về resultet
		int demSQl = 0;																								
		while (rs.next()) {
			String id= rs.getString("id");
			int succhua = rs.getInt("succhua");
			int tinhtrang = rs.getInt("tinhtrang");
			if(tinhtrang==0) {
				System.out.printf("| %-6s | %-8d | %-10s|\n", id, succhua, "trống" );demSQl++;
			}
		}
		if (demSQl == 0)
			System.out.println("Danh sách rỗng");
	}
	public void capNhanBan(Connection conn,String maBan)throws SQLException, ClassNotFoundException {
		Ban b = new Ban();
		String  id = this.timKiemIDBan(conn,maBan);
		if(id!=null){
			b.setMaBan(id);
			b.nhapBan(scanner);
			int succhua= b.getSucChua();
			System.out.println("nhập tình trạng của bàn: ");
			b.setTinhTrang(scanner.nextInt());
			int tinhtrang = b.getTinhTrang();
			Statement  stm = conn.createStatement();
			String set = "set succhua='" + succhua + "', tinhtrang=' " + tinhtrang + "'";
			int k = stm.executeUpdate("UPDATE ban "+set+"WHERE id= '"+id+"'");
			if(k>=1){
				System.out.println("Cập nhật thành công");
			}
		}
		else
			System.out.println("không tìm thấy mã bàn cập nhật");
	}

	public void xoaBan(Connection conn,String maBan) throws SQLException {
		String id = this.timKiemIDBan(conn,maBan);
		if(id!=null){
			Statement  stm = conn.createStatement();
			int k = stm.executeUpdate("DELETE FROM ban WHERE  id ='"+id+"';");
			if(k>=1){
				System.out.println("xóa thành công thành công");
			}
		}
		else
			System.out.println("không tìm thấy bàn cần xóa");
	}


}
