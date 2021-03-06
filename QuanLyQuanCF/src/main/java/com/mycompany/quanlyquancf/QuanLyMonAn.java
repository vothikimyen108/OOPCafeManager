package com.mycompany.quanlyquancf;

import java.io.EOFException;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;


public class QuanLyMonAn {

	private List<MonAn> dsThucAn;
	public static Scanner scanner = new Scanner(System.in);
	public void themThucAn(Connection conn, Scanner scanner) throws SQLException, EOFException {
		Statement stm = conn.createStatement();
		boolean cont = true;
        do {
            System.out.println("Nhập thức  ăn chọn phím 1");
            System.out.println("Nhập thức uống chọn phím 2");
            System.out.println("bấm phím khác để thoát");
            int chon = scanner.nextInt();
            scanner.nextLine();
			switch (chon){
                case 1: 
                    ThucAn ta= new ThucAn();
					ta.nhapMonAn(scanner);
					String sql = "INSERT INTO thucan (ten,tinhtrang,thoidiemban,gia,iscd)";
					String ten = ta.getTen();
					int tinhtrang= ta.getTinhTrang();
					double gia = ta.getGiaBan();
					String thoidiemban= ta.getThoiDiemBan();
					int ischay = ta.getAnChay();
					String vl = " VALUES('" + ten + "',' " + tinhtrang + "', '" + thoidiemban+ "','"+gia+ "',' " + ischay +  "');";
					int k = stm.executeUpdate(sql + vl);
					if (k != 0)
						System.out.println("thêm thành công");
					else 
						{System.out.println("không thành công");
						throw new EOFException();
					}
                    break;
                case 2:
				ThucUong tu= new ThucUong();
				tu.nhapMonAn(scanner);
				String sqlTU = "INSERT INTO thucuong(ten,tinhtrang,thoidiemban,gia,iscd)";
				String tentu = tu.getTen();
				int tinhtrangtu= tu.getTinhTrang();
				String thoidiembantu= tu.getThoiDiemBan();
				double giatu = tu.getGiaBan();
				int isda = tu.isIsda();
				String vltu = "VALUES ('" + tentu + "',' " + tinhtrangtu + "', '" + thoidiembantu+ "','"+giatu + "','" + isda + "');";
				int ktu = stm.executeUpdate(sqlTU + vltu);
				if (ktu != 0)
					System.out.println("thêm thành công");
				else 
					{System.out.println("không thành công");
					throw new EOFException();
				}
                    break; 
                default:
                    System.out.println("Thoát chức năng 1");
                    cont = false;
                    break;
                }
            } while (cont);
	}

	public List<MonAn> getDsThucAn() {
		return dsThucAn;
	}

	public void setDsThucAn(List<MonAn> dsThucAn) {
		this.dsThucAn = dsThucAn;
	}

	public void xoaThucAn(Connection conn) throws SQLException {
		String bang = "thucan";
		System.out.println("nhập tên món cần xóa: ");
		String tenMonAn = scanner.nextLine();
		if(tenMonAn!=null){
			Statement  stm = conn.createStatement();
			int k = stm.executeUpdate("delete from "+bang+" where ten='"+tenMonAn+"'");
			if(k>=1){
				System.out.println("xóa thành công thành công");
			}else {
				bang = "thucuong";
			   k = stm.executeUpdate("delete from "+bang+" where ten='"+tenMonAn+"'");
			   if(k>=1)
				System.out.println("xóa thành công thành công!");
			}
		}
		else
			System.out.println("không tìm tên thức ăn cần cập nhật");
	}
	public void hienThiDanhSach(Connection conn,String sql) throws SQLException {
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);//trả về resultet
		int dem = 0;																								
		while (rs.next()) {
			int id= rs.getInt("id");
			String  ten = rs.getString("ten");
			int tinhtrang = rs.getInt("tinhtrang");
			String thoidiemban = rs.getString("thoidiemban");
			int ischayDa = rs.getInt("iscd");
			double gia = rs.getDouble("gia");
			String tinhTrangS;
			String isChayDaS;
			if(tinhtrang==0)
				tinhTrangS = "còn";
			else
				tinhTrangS = "Hết";
			if(ischayDa==0)
				isChayDaS = "Được";
			else
				isChayDaS = "Không";
				System.out.printf("| %-3s | %-15s | %-10s | %-13s | %-6s | %-9s|\n", id, ten, tinhTrangS,thoidiemban,isChayDaS,gia);	
			dem++;
		}
		System.out.format("+------------------------------------------------------------------------+%n");
		if (dem == 0)
			System.out.println("Danh sách rỗng");

	}
	public void hienThiDanhSach(Connection conn) throws SQLException {
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";
		System.out.format("+********************************MENU************************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","chay","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sql = "select *from thucan ;";
		this.hienThiDanhSach(conn, sql);
		System.out.format("+============================THỨC UỐNG===================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","Đá","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sqltu = "select *from thucuong ;";
		this.hienThiDanhSach(conn, sqltu);
	}
	public void timKiemTheoTenTa(Connection conn) throws SQLException {
		System.out.println("Tìm Thức ăn theo tên");
		System.out.println("Nhập tên để thực hiện chức năng");
		String tenMonAn= scanner.nextLine();
		String sqlTA = "Select * from thucan where ten like '%"+tenMonAn+"%'";
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";
		System.out.format("+******************************MENU**************************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","đá","Giá VNĐ");
		this.hienThiDanhSach(conn, sqlTA); 
	}
	public void timKiemTheoTenTu(Connection conn) throws SQLException {
		System.out.println("tìm thức uống theo tên");
		System.out.println("Nhập tên để thực hiện chức năng");
		String tenMonAn= scanner.nextLine();
		String sqlTU = "Select * from thucuong where ten like'%"+tenMonAn+"%'";
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";;
		System.out.format("+******************************MENU**************************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","chay","Giá VNĐ");
		this.hienThiDanhSach(conn, sqlTU); 
	}
	public void timKiemTheoGia(double giaTu, double giaDen,Connection conn ) throws SQLException {
		String sqlTA = "Select * from thucan  where gia BETWEEN '"+giaTu+"' and '"+giaDen+"'";
		String sqlTU = "Select * from thucuong  where gia BETWEEN '"+giaTu+"' and '"+giaDen+"'";
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";
		System.out.format("+******************************MENU**************************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","chay","Giá VNĐ");
		System.out.format("+-----------------------------------------------------------------+%n");
		this.hienThiDanhSach(conn, sqlTA);
		System.out.format("+******************************MENU**************************************+%n");
		System.out.format("+============================THỨC UỐNg=====================================+%n");
		System.out.format("+--------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","Đá","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		this.hienThiDanhSach(conn, sqlTU);
	}
	public ThucAn timKiemTenThucAn(Connection conn,String tenTA) throws SQLException, EOFException {
		ThucAn ta = new ThucAn();
		Statement  stm = conn.createStatement(); boolean flag = true;
		ResultSet rs = stm.executeQuery("Select * from thucan where ten='"+tenTA+"'");
		try {
			ta.setTen(tenTA);
			while(rs.next()){
				flag = false;
				ta.setGiaBan(rs.getDouble("gia"));
				ta.setThoiDiemBan(rs.getString("thoidiemban"));
				ta.setTinhTrang(rs.getInt("tinhtrang"));
				ta.setAnChay(rs.getInt("iscd"));
			}
		} catch (Exception e) {
			throw new Error(e);
		}
		if(flag)
			ta = null;
		return ta;
	}
	public ThucUong timKiemTenThucUong(Connection conn,String tenTU) throws SQLException, EOFException {
		ThucUong tu = new ThucUong();
		Statement  stm = conn.createStatement(); boolean flag = true;
		ResultSet rs = stm.executeQuery("Select * from thucuong where ten='"+tenTU+"'");
		try {
			tu.setTen(tenTU);
			while(rs.next()){
				flag = false;
				tu.setGiaBan(rs.getDouble("gia"));
				tu.setThoiDiemBan(rs.getString("thoidiemban"));
				tu.setTinhTrang(rs.getInt("tinhtrang"));
				tu.setIsda(rs.getInt("iscd"));
			}
		} catch (Exception e) {
			throw new Error(e);
		}
		if(flag)
			tu = null;
		return tu;
	}
	public void sapXepGiamDan(Connection conn) throws SQLException {
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";
		System.out.format("+******************************MENU(GIÁ CẢ GIẢM)*************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","chay","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sql = "SELECT * FROM thucan ORDER BY gia desc;";
		this.hienThiDanhSach(conn, sql);
		System.out.format("+============================THỨC UỐNG===================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","Đá","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sqltu = "SELECT * FROM thucuong ORDER BY gia desc;";
		this.hienThiDanhSach(conn, sqltu);
	}

	public void sapXepTangDan(Connection conn) throws SQLException {
		String leftAlignFormat = "| %-1s | %-15s | %-5s | %-8s | %-6s | %-9s|\n";
		System.out.format("+******************************MENU(GIÁ CẢ TĂNG)*************************+%n");
		System.out.format("+============================THỨC ĂN=====================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","chay","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sql = "SELECT * FROM thucan ORDER BY gia asc;";
		this.hienThiDanhSach(conn, sql);
		System.out.format("+============================THỨC UỐNG===================================+%n");
		System.out.format("+------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Stt", "tên", "Tình trạng","Thời điểm bán","Đá","Giá VNĐ");
		System.out.format("+------------------------------------------------------------------------+%n");
		String sqltu = "SELECT * FROM thucuong ORDER BY gia asc;";
		this.hienThiDanhSach(conn, sqltu);
	}

}
