package com.mycompany.quanlyquancf;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuanLyNhanVien {

	private List<NhanVien> dsNhanVien;
	public static Scanner scanner = new Scanner(System.in);
	public static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	public static Connection conn;

	public void hienThiDanhSach(Connection conn) throws SQLException {
		String sql = "Select  * from nhanvien As nv, bophan AS bp where nv.BoPhan_maBP=bp.mbp";
		String leftAlignFormat = "| %-4s | %-20s | %-15s | %-15s | %-15s | %-15s | %-10s |\n";
		System.out.println("==================================================DANH SÁCH NHÂN VIÊN===============================================");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Mã", "Họ và tên", "Giới tính", "Sinh nhật", "Quê quán","Ngày bắt đầu", "Bộ phận");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);//trả về resultet
		int dem = 0;																								
		while (rs.next()) {
			String tenNV = rs.getString("tenNV");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			int id = rs.getInt("maNhanVien");
			String quenquan = rs.getString("quequan");
			Date ngaybd = rs.getDate("ngaybd");
			String tenBP = rs.getString("tenBP");
			System.out.printf(leftAlignFormat, id, tenNV, gioitinh, f.format(ngaysinh), quenquan,
					f.format(ngaybd), tenBP);
			dem++;
		}
		if (dem == 0)
			System.out.println("Danh sách rỗng");
	}

	public List<NhanVien> getDsNhanVien() {
		return dsNhanVien;
	}

	public void setDsNhanVien(List<NhanVien> dsNhanVien) {
		this.dsNhanVien = dsNhanVien;
	}

	public void traCuuNhanVien(String tuKhoa, Connection conn) throws SQLException {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf", "root", "123456789");
		String sql = "Select * from nhanvien As nv, bophan AS bp where nv.BoPhan_maBP=bp.mbp and(tenNV like concat('%',?,'%') or gioitinh like concat('%',?,'%') or ngaysinh like  concat(?) or quequan like concat('%',?,'%')) ;";
		PreparedStatement stm1 = conn.prepareStatement(sql);
		stm1.setString(1, tuKhoa);
		stm1.setString(2, tuKhoa);
		stm1.setString(3, tuKhoa);
		stm1.setString(4, tuKhoa);
		System.out.println("Kết quả tra cứu");
		String leftAlignFormat = "| %-4s | %-20s | %-15s | %-15s | %-15s | %-15s | %-10s |\n";
		System.out.println("==================================================DANH SÁCH NHÂN VIÊN===============================================");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Mã", "Họ và tên", "Giới tính", "Sinh nhật", "Quê quán","Ngày bắt đầu", "Bộ phận");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		int dem = 0;
		ResultSet rs = stm1.executeQuery();																							
		while (rs.next()) {
			String tenNV = rs.getString("tenNV");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			int id = rs.getInt("maNhanVien");
			String quenquan = rs.getString("quequan");
			Date ngaybd = rs.getDate("ngaybd");
			String tenBP = rs.getString("tenBP");
			System.out.printf(leftAlignFormat, id, tenNV, gioitinh, f.format(ngaysinh), quenquan,
					f.format(ngaybd), tenBP);
			dem++;
		}
		if (dem == 0)
			System.out.println("Danh sách rỗng");
		
	}

	public boolean themNhanVien(NhanVien nv, Connection conn) throws ClassNotFoundException, SQLException {
		Statement stm = conn.createStatement();
		String sql = "INSERT INTO nhanvien (tenNV,gioitinh,ngaysinh,quequan,ngaybd,BoPhan_maBP)";
		String tenNV = nv.getHoTen();
		String gioitinh = nv.getGioiTinh();
		Date ngaysinh = nv.getNgaySinh();
		String quequan = nv.getQueQuan();
		Date ngaybd = nv.getNgaBD();
		int mbp = nv.getThuocBoPhan().getMaBoPhan();
		String vl = "VALUES ('" + tenNV + "',' " + gioitinh + "', '" + f.format(ngaysinh) + "','" + quequan + "',' "
				+ f.format(ngaybd) + "','" + mbp + "');";
		int k = stm.executeUpdate(sql + vl);
		if (k != 0)
			return true;
		return false;
	}
	public int timKiemMaNhanVien(NhanVien nv ,Connection conn) throws SQLException, ClassNotFoundException {
		System.out.println("Nhập mã nhân viên cần tìm để thực hiện chức năng");
		nv.setMaNhanVien(scanner.nextInt());
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from nhanvien where maNhanVien='"+nv.getMaNhanVien()+"'");	
		int id = 0;
		while(rs.next()){
		  id = rs.getInt("maNhanVien");
		}
		return id;
	}
	public void capNhanNhanVien(NhanVien nv,Connection conn) throws Exception {
		int id = this.timKiemMaNhanVien(nv,conn);
		if(id>=1){
			Statement  stm = conn.createStatement();
			try {
				
				ResultSet rs = stm.executeQuery("Select * from nhanvien where maNhanVien='"+id+"'");	
				while(rs.next()){
					nv.setHoTen(rs.getString("tenNV"));
					nv.setGioiTinh(rs.getString("gioitinh"));
					nv.setNgaySinh( rs.getDate("ngaysinh"));
					nv.setMaNhanVien(rs.getInt("maNhanVien"));
					nv.setQueQuan(rs.getString("quequan"));
					nv.setNgaBD(rs.getDate("ngaybd"));
					BoPhan bp = new BoPhan(rs.getInt("BoPhan_maBP"));
					nv.setThuocBoPhan(bp);
				} 
			} catch (Exception e) {
				System.out.println("Lỗi " + e);
				throw new Exception(e);
			}
			boolean cont = true;
			do {
				System.out.println("Cập nhật nhân viên");
				System.out.println("1.Sửa tên");
				System.out.println("2.Sửa gioi tinh");
				System.out.println("3.Sửa ngày sinh");
				System.out.println("4.Sửa ngày bắt đâu");
				System.out.println("5.Sửa Quê Quán");
				System.out.println("6.Bộ phận: ");
				System.out.println("7.Thoát chức năng ");
				System.out.println(" -> Chọn chức năng : [1-6]");
				int chon = scanner.nextInt();
				scanner.nextLine();
				switch (chon) {
				case 1:
				  System.out.println(" Thực hiện chức năng 1:");
				  System.out.print("Tên nhân viên: ");
				  nv.setHoTen(scanner.nextLine());
				  break;
				case 2:
				  System.out.println("Thực hiện chức năng 2");
				  System.out.print("Tên giới tính: ");
				  nv.setGioiTinh(scanner.nextLine());
				  break;
				case 3:
				  System.out.println("Thực hiện chức năng 3");
				  try {
					System.out.print("Ngày sinh: (Định dạng yyyy-mm-dd): ");
					String ns = scanner.nextLine();
					Date nSinh = f.parse(ns);
					nv.setNgaySinh(nSinh);
				} catch(Exception e) {
					System.out.println("lỗi nhập: "+e);
					throw new Exception(e);
				}
				  break;
				case 4:
				  System.out.println("Thực hiện chức năng 4");
				  try {
					System.out.print("Ngày bắt đầu vào làm (Định dạng yyyy-mm-dd): ");
					String nbd = scanner.nextLine();
					Date ngay = f.parse(nbd);
					nv.setNgaBD(ngay);
				} catch (Exception e) {
					 System.out.println("lỗi nhập: "+e);
					 throw new Exception(e);
				}
				  break;
				case 5:
				  System.out.println("Thực hiện chức năng 5");
				  System.out.print("Quê quán: ");
				  nv.setQueQuan(scanner.nextLine());
				  break;
				case 6:
				  System.out.println("Thực hiện chức năng 6");
				  QuanLyBoPhan ql = new QuanLyBoPhan();
				  int maBP;
				  do {
					  ql.show(conn);
					  System.out.print("\nThuộc bộ phần: ");
					  maBP = scanner.nextInt();
					  BoPhan bp = new BoPhan(maBP);
					  nv.setThuocBoPhan(bp);
				   }while(maBP>ql.getDem(conn) || maBP <0);
				  break;
				default:
				  System.out.println("Đã thoát chức năng");
				  cont = false;
				  break;
				}
			} while (cont);
			String tenNV = nv.getHoTen();
			String gioitinh= nv.getGioiTinh();
			Date ngaysinh = nv.getNgaySinh();
			Date ngaybd = nv.getNgaBD();
			String quequan = nv.getQueQuan();
			int mbp = nv.getThuocBoPhan().getMaBoPhan();
			String set = "set tenNV='" + tenNV + "', gioitinh=' " + gioitinh + "',ngaysinh='" + f.format(ngaysinh) + "',quequan='" + quequan + "',ngaybd=' "
			+ f.format(ngaybd) + "',BoPhan_maBP='" + mbp + "'";
			int k = stm.executeUpdate("UPDATE nhanvien "+set+"WHERE maNhanVien= '"+id+"'");
			if(k>=1){
				System.out.println("Cập nhật thành công");
			} else throw new Exception("Lỗi");
		}
		else
			System.out.println("không tìm thấy mã nhân viên cần cập nhật");
	}
	public void xoaNhanVien(NhanVien nv,Connection conn) throws SQLException, ClassNotFoundException {
		int id = this.timKiemMaNhanVien(nv,conn);
		if(id>=1){
			Statement  stm = conn.createStatement();
			int k = stm.executeUpdate("DELETE FROM nhanvien WHERE  maNhanVien ='"+id+"';");
			if(k>=1){
				System.out.println("xóa thành công thành công");
			}
		}
		else
			System.out.println("không tìm thấy mã nhân viên cần cập nhật");
	}

	public void toChucSinhNhat(Connection conn) throws SQLException {
		Calendar cal = Calendar.getInstance();
		int thangHT = cal.get(Calendar.MONTH)+1;
		System.out.println("Danh sách nhân viên có sinh nhật trong tháng: "+thangHT);
		String sql="Select  * from nhanvien As nv, bophan AS bp where nv.BoPhan_maBP=bp.mbp and MONTH(ngaysinh) = Month(CURDATE());";
		String leftAlignFormat = "| %-4s | %-20s | %-15s | %-15s | %-15s | %-15s | %-10s |\n";
		System.out.println("==================================================DANH SÁCH NHÂN VIÊN===============================================");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		System.out.printf(leftAlignFormat, "Mã", "Họ và tên", "Giới tính", "Sinh nhật", "Quê quán","Ngày bắt đầu", "Bộ phận");
		System.out.format("+------------------------------------------------------------------------------------------------------------------+%n");
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);//trả về resultet
		int dem = 0;																								
		while (rs.next()) {
			String tenNV = rs.getString("tenNV");
			String gioitinh = rs.getString("gioitinh");
			Date ngaysinh = rs.getDate("ngaysinh");
			int id = rs.getInt("maNhanVien");
			String quenquan = rs.getString("quequan");
			Date ngaybd = rs.getDate("ngaybd");
			String tenBP = rs.getString("tenBP");
			System.out.printf(leftAlignFormat, id, tenNV, gioitinh, f.format(ngaysinh), quenquan,
					f.format(ngaybd), tenBP);
			dem++;
		}
		if (dem == 0)
			System.out.println("Danh sách rỗng");												
	}
}
