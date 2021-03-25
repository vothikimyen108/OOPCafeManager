package com.mycompany.quanlyquancf;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;
public class DatBan {
	public static Scanner scanner = new Scanner(System.in);
	public static SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
	public static Connection conn;
	public static int dem = 0;
	private int soTTHD;
	{
		{
			try {
				this.setSoTTHD(dem);;
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private Ban datBan;
	private String ta;
	private int soLuongTa;
	private static double thanhTien = 0;
	private Date ngayDatBan;

	public void nhapDatBan(Scanner scanner, Connection conn, QuanLyBan qldb, QuanLyMonAn qlma) throws Exception {
		scanner.nextLine();
		this.datBan = qldb.timKiemBanTrong(conn);
		try {
			if (this.datBan != null) {
				DatBan db = new DatBan();
				java.sql.Statement stm = conn.createStatement();
				this.capNhapTrangThaiDatBan(this.datBan, 1, conn);
				boolean cont = true;
				long millis = System.currentTimeMillis();
				this.ngayDatBan = new java.sql.Date(millis);
				qlma.hienThiDanhSach(conn);
				do {
					System.out.println("Thêm Thức ăn chọn phím 1");
					System.out.println("thêm Thức uống chọn phím 2");
					System.out.println("bấm phím khác để thoát - In hóa đơn");
					int chon = scanner.nextInt();
					scanner.nextLine();
					switch (chon) {
						case 1:
							System.out.println("Nhập tên thức ăn muốn gọi");
							ThucAn ta = qlma.timKiemTenThucAn(conn, scanner.nextLine());
							if (ta != null && ta.getTinhTrang() == 0) {
								try {
									System.out.println("Số lượng " + ta.getTen());
									this.setSoLuongTa(scanner.nextInt());
									String sql = "INSERT INTO datban (iddatban,ten,soluong,ngaydat,gia,tonggia,stt)";
									String ten = ta.getTen();
									double gia = ta.getGiaBan();
									String vl = " VALUES('" + this.datBan.getMaBan() + "',' " + ten + "',' "
											+ this.getSoLuongTa() + "', '" + this.ngayDatBan + "','" + gia + "','"
											+ gia * this.soLuongTa + "','" + db.getSoTTHD() + "');";
									try {
										stm.executeUpdate(sql + vl);
										System.out.println("Thêm thành công!");
									} catch (Exception e) {
										throw new Exception(e);
									}

								} catch (Exception e) {
								}
							} else {
								System.out.println("không tìm thấy/hoặc hết");
							}
							break;
						case 2:

							System.out.println("Nhập tên thức uống muốn gọi");
							ThucUong tu = qlma.timKiemTenThucUong(conn, scanner.nextLine());
							if (tu != null && tu.getTinhTrang() == 0) {
								try {
									System.out.println("Số lượng " + tu.getTen());
									this.setSoLuongTa(scanner.nextInt());
									String sql = "INSERT INTO datban (iddatban,ten,soluong,ngaydat,gia,tonggia,stt)";
									String ten = tu.getTen();
									double gia = tu.getGiaBan();
									String vl = " VALUES('" + this.datBan.getMaBan() + "',' " + ten + "',' "
											+ this.getSoLuongTa() + "', '" + this.ngayDatBan + "','" + gia + "','"
											+ gia * this.soLuongTa + "','" + db.getSoTTHD() + "');";
									try {
										stm.executeUpdate(sql + vl);
										System.out.println("Thêm thành công!");
									} catch (Exception e) {
										throw new Exception(e);
									}
								} catch (Exception e) {
								}
							} else {
								System.out.println("không tìm thấy/hoặc hết");
							}
							break;
						default:
							System.out.println("Thoát chức năng đặt món");
							this.xuatHoaDon(conn, this.datBan, this.ngayDatBan, db.getSoTTHD());
							cont = false;
							break;
					}
				} while (cont);
			} else {
				System.out.println(" --Đã có khách--");
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public int getSoTTHD() {
		return soTTHD;
	}

	public void setSoTTHD(int soTTHD) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/quanlyquancf","root", "123456789");
		Statement  stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("Select * from datban");
		int sttHDSQL=0;
		int sodong = 0;
		while(rs.next()){
			sodong++;
		   sttHDSQL = rs.getInt("stt");
		}
		if(sodong==0){
			this.soTTHD = 0;
		}else {
			this.soTTHD = sttHDSQL +1;
		}
	}

	public void capNhapTrangThaiDatBan(Ban b, int trangThai, Connection conn) throws Exception {
		QuanLyBan qlb = new QuanLyBan();
		String maBanCN = qlb.timKiemIDBan(conn,b.getMaBan());
		if(maBanCN!=null) {
			String sqlCapNhat = "UPDATE `ban` SET `tinhtrang` = '" + trangThai + "' WHERE (`id` = '" + b.getMaBan() + "')";
			java.sql.Statement stm = conn.createStatement();
			try {
				stm.executeUpdate(sqlCapNhat);
				System.out.println("Cập thật thành công");
			} catch (Exception e) {
				throw new Exception(e);
			}
		} else {
			System.out.print("không thấy mã bàn");
		}

	}

	public void xuatHoaDon(Connection conn, Ban b, Date ngay, int sttHD) throws Exception {
		java.sql.Statement stm = conn.createStatement();
		System.out.println("======== Hóa Đơn Bàn: " + b.getMaBan() + " ======= Stt: " + sttHD + "======");
		String leftAlignFormat = "| %-10s | %-10d | %-10.1f | %-10.1f |\n";
		System.out.printf("| %-10s | %-10s | %-10s | %-10s |\n", "Tên", "số Lượng", "Giá", "Tổng Tiền");
		ResultSet rs = stm.executeQuery("Select * from datban where iddatban='" + b.getMaBan() + "'and ngaydat ='"
				+ ngay + "'and stt ='" + sttHD + "'");
		// boolean flag = false;
		try {
			Double giaMotMon;
			Double tongGia;
			while (rs.next()) {
				this.setTa(rs.getString("ten"));
				this.setSoLuongTa(rs.getInt("soluong"));
				giaMotMon = rs.getDouble("gia");
				tongGia = rs.getDouble("tonggia");
				thanhTien = thanhTien + tongGia;
				System.out.printf(leftAlignFormat, this.getTa(), this.soLuongTa, giaMotMon, tongGia);
			}
			System.out.println("Tổng Hóa đơn: " + thanhTien + "VND Ngày: " + ngay);
			System.out.println("Xin cảm ơn! Chúc anh chị một ngày tốt lành! Hẹn gặp lại!");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public void thongKeDoanhThuThang(Connection conn, int thang) throws SQLException {
		thanhTien = 0;
		//rao lại tháng 1-12 thâu 
		String sql = "SELECT * FROM datban WHERE  MONTH(ngaydat)=?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setInt(1, thang);
        ResultSet rs = stm.executeQuery();
        while(rs.next()){
			thanhTien += rs.getDouble("tonggia");
        }
		System.out.println("Doanh thu của tháng:"+ thang + "  Là : " +thanhTien);
	}
	public Date getNgayDatBan() {
		return ngayDatBan;
	}

	public void setNgayDatBan(Date ngayDatBan) {
		this.ngayDatBan = ngayDatBan;
	}
	public int getSoLuongTa() {
		return soLuongTa;
	}

	public void setSoLuongTa(int soLuongTa) {
		this.soLuongTa = soLuongTa;
	}
	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	public Ban getDatBan() {
		return datBan;
	}
	public void setDatBan(Ban datBan) {
		this.datBan = datBan;
	}
}
