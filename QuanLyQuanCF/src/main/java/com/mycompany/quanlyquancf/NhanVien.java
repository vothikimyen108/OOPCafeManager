package com.mycompany.quanlyquancf;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class NhanVien {
	public static Scanner scanner = new Scanner(System.in);
	public static SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
	public static Connection conn ;
	private int maNhanVien;
	{
		try {
			this.setMaNhanVien(0);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	private String hoTen;

	private Date ngaBD;

	private BoPhan thuocBoPhan;

	private String gioiTinh;

	private Date ngaySinh;

	private String queQuan;
	public NhanVien() {

	};

	public NhanVien( String ht,  String gt, Date ns, String qq,Date nbd,BoPhan tbp) {
		this.hoTen = ht;
		this.gioiTinh = gt;
		this.ngaySinh = ns;
		this.queQuan = qq;
		this.ngaBD = nbd;
		this.thuocBoPhan = tbp;
	}

	// nhapthongtin
	public void nhapThongTin( Scanner scanner) throws Exception {
		scanner.nextLine();
		System.out.println("nhập thông tin thông tin: " + this.maNhanVien);
		System.out.print("Tên nhân viên: ");
		this.setHoTen(scanner.nextLine());
		System.out.print("Tên giới tính: ");
		this.setGioiTinh(scanner.nextLine());
		try {
			System.out.print("Ngày sinh: (Định dạng yyyy-mm-dd): ");
			String ns = scanner.nextLine();
			Date nSinh = f.parse(ns);
			this.setNgaySinh(nSinh);
		} catch(Exception e) {
			System.out.println("lỗi nhập: "+e);
			throw new Exception(e);
		}
		System.out.print("Quê quán: ");
		this.setQueQuan(scanner.nextLine());
		try {
			System.out.print("Ngày bắt đầu vào làm (Định dạng yyyy-mm-dd): ");
			String nbd = scanner.nextLine();
			Date ngay = f.parse(nbd);
			this.setNgaBD(ngay);
		} catch (Exception e) {
			 System.out.println("lỗi nhập: "+e);
			 throw new Exception(e);
		}
		QuanLyBoPhan ql = new QuanLyBoPhan();
		int maBP;
		do {
			ql.show(conn);
			System.out.print("\nThuộc bộ phần: ");
			maBP = scanner.nextInt();
			BoPhan bp = new BoPhan(maBP);
			this.setThuocBoPhan(bp);
	 	}while(maBP>ql.getDem(conn) || maBP <0);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		String chuoi = this.maNhanVien + "\t" + this.hoTen + "\t" + this.gioiTinh + "\t" + f.format(this.ngaySinh)
				+ "\t" + this.queQuan;
		String chuoi1 = "\t" + f.format(this.ngaBD) + "\t" + this.thuocBoPhan;
		b.append(chuoi).append(chuoi1);
		return b.toString();
	}
	// cacphuongthucget set

	public String getQueQuan() {
		return queQuan;
	}

	public void setQueQuan(String queQuan) {
		this.queQuan = queQuan;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh( Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public BoPhan getThuocBoPhan() {
		return thuocBoPhan;
	}

	public void setThuocBoPhan(BoPhan bp) throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
		String sql = "SELECT * FROM bophan WHERE mbp like concat(?)";
		PreparedStatement stm1 = conn.prepareStatement(sql);
		stm1.setInt(1, bp.getMaBoPhan());
		ResultSet rs2 = stm1.executeQuery();
			while(rs2.next()){
				String name = rs2.getString("tenBP");
				int id = rs2.getInt("mbp");
				bp.setMaBoPhan(id);
				bp.setTenBoPhan(name);
			}	
	   this.thuocBoPhan = bp;
	}

	public Date getNgaBD() {
		return ngaBD;
	}

	public void setNgaBD(Date ngaBD) {
		this.ngaBD = ngaBD;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public int getMaNhanVien() {
		return maNhanVien;
	}

	public void setMaNhanVien(int maNhanVien) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from nhanvien");
		int maNVSQL =0;
		int sodong = 0;
		boolean flag = true;
		while(rs.next()){
			sodong++;
		   maNVSQL = rs.getInt("maNhanVien");
		   if(maNVSQL==maNhanVien)
			{
				this.maNhanVien = maNVSQL;
				flag = false;
			}
		}
		if(flag){
			if(sodong==0){
				this.maNhanVien = 0;
			}else {
				this.maNhanVien = maNVSQL  +1;
			}
		}
	}

}
